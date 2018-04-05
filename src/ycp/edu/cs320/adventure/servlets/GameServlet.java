package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ycp.edu.cs320.adventure.game.*;

public class GameServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");

		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Necessary objects & game setup
		Tile start = new Tile();
		start.setX(0);
		start.setY(0);

		Game game = new Game();
		GameEngine engine = new GameEngine();
		engine.setGame(game);
		
		Map map = new Map();
		map.buildSmallDefault(engine);

		Player player = new Player();
		player.setLocation(start);

		// User input from jsp
		String input = req.getParameter("userInput");

		// Games response to user
		String response = null;

		System.out.println("Game Servlet: doPost");

		//********** Play Game Below **********


		/* Handling user input */

		// Player moves down
		if(input.equalsIgnoreCase("move down")) {
			// If valid move
			System.out.println("Player X pos - " + player.getLocation().getX());
			System.out.println("Player Y pos - " + player.getLocation().getY());
			if(player.getLocation().getY() < map.getHeight() - 1) {
				// Move player
				System.out.println("Before-" + player.getLocation().getY());
				int newY = player.getLocation().getY() + 1;
				System.out.println("After-" + newY);
				Tile newLoc = new Tile();
				newLoc.setX(player.getLocation().getX());
				newLoc.setY(newY);

				player.setLocation(map.getTile(player.getLocation().getX(), newY));

				response = "You moved down.";
			}
			else {
				response = "You can't move there!";
			}
		}

		// Player moves left
		else if(input.equalsIgnoreCase("move left")) {
			// If valid move
			if(player.getLocation().getY() > 0) {
				// Move player
				int newX = player.getLocation().getX() - 1;

				Tile newLoc = new Tile();
				newLoc.setY(player.getLocation().getY());
				newLoc.setX(newX);

				player.setLocation(newLoc);

				response = "You moved left.";
			}
			else {
				response = "You can't move there!";
			}		
		}

		// Player moves right
		else if(input.equalsIgnoreCase("move right")) {
			// If valid move
			//System.out.println("WIDTH: " + map.getWidth());
			//System.out.println("HEIGHT: " + map.getHeight());
			System.out.println("Player X pos - " + player.getLocation().getX());
			System.out.println("Player Y pos - " + player.getLocation().getY());
			if(player.getLocation().getX() < map.getWidth()) {
				// Move player
				int newX = player.getLocation().getX() + 1;

				Tile newLoc = new Tile();
				newLoc.setY(player.getLocation().getY());
				newLoc.setX(newX);

				player.setLocation(map.getTile(newX,  player.getLocation().getY()));

				response = "You moved right.";
			}
			else if(player.getLocation().getX() == map.getWidth()){
				response = "You can't move there!";
			}		
		}

	// Player moves up
	else if(input.equalsIgnoreCase("move up")) {
		// If valid move
		if(player.getLocation().getY() < 0) {
			// Move player
			int newY = player.getLocation().getY() - 1;

			Tile newLoc = new Tile();
			newLoc.setX(player.getLocation().getX());
			newLoc.setY(newY);

			player.setLocation(newLoc);

			response = "You moved up.";
		}
		else {
			response = "You can't move there!";
		}
	}

	// Player views how much damage they can deal
	else if(input.equalsIgnoreCase("view damage")) {
		response = String.valueOf(player.getBaseDamage());

	}

	// Player views their location 
	else if(input.equalsIgnoreCase("location")) {

		// If player has a location 
		if(player.getLocation() != null) {
			
			response = String.valueOf(player.getLocation().getX()) + "," + String.valueOf(player.getLocation().getY());
			
		}

		// If player does not have a location 
		else {
			response = "Somehow you have no location ???";
		}

	}

	// Player views their health
	else if(input.equalsIgnoreCase("health")) {
		response = String.valueOf(player.getHealth());
	}

	// Player views current equipped item 
	else if(input.equalsIgnoreCase("item")) {

		// If player actually has an item 
		if(player.getEquippedItem() != null) {
			response = String.valueOf(player.getEquippedItem());
		}

		// If player has no item equipped
		else {
			response = "No item equipped.";
		}
	}

	// Player views their inventory
	else if(input.equalsIgnoreCase("view inventory")) {

		// If inventory is NOT empty
		if(player.getInventory() != null) {
			response = String.valueOf(player.getInventory());
		}

		// Inventory is empty
		else {
			response = "Inventory is Empty!";
		}
	}

	// Player views their score 
	else if(input.equalsIgnoreCase("score")) {
		response = String.valueOf(player.getScore());
	}

	// Player picks up item from tile 
	else if(input.equalsIgnoreCase("pick up item")) {

		// If tile has an item 
		if(map.getTile(player.getLocation().getX(), player.getLocation().getY()).getItemList() != null){
			Inventory newInv = new Inventory();
			newInv.addMultipleToInventory(player.getInventory().getInventory());
			newInv.addMultipleToInventory(map.getTile(player.getLocation().getX(), player.getLocation().getY()).getItemList());
			player.setInventory(newInv);
			response = "You found an item! View Inventory to see it.";
		}

		else {
			response = "Theres nothing here but dirt!";
		}
	}


	// Player attempts to exit game 
	else if(input.equalsIgnoreCase("exit")) {

		// If player is on an exit tile
		if(player.getLocation() == map.getTile(player.getLocation().getX(), player.getLocation().getY()) && map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription().contains("exit")) {
			String exitMessage = "Congrats You Escaped! Final Score: ";
			String finalScore = String.valueOf(player.getScore());
			response = exitMessage.concat(finalScore);
			engine.endGame();
		}
		else {
			response = "This is not an exit tile!";
		}
	}

	// Player enters unknown command
	else {
		response = "I'm not quite sure what that means... try again please";
	}

	/* Checking for environment conditions */

	// If player steps on trap
	if(player.getLocation() == map.getTile(player.getLocation().getX(), player.getLocation().getY()) && map.getTile(player.getLocation().getX(), player.getLocation().getY()).getType() == 2) {
		player.hurt((map.getTile(player.getLocation().getX(), player.getLocation().getY())).getDamage());
	}


	req.setAttribute("response",  response);

	req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
}

}
