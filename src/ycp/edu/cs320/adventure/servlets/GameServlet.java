package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ycp.edu.cs320.adventure.game.*;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

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
		
		// gets account_id of the account that was signed in from the index servlet 
        Object account_id = req.getSession(false).getAttribute("id");
        
        System.out.println("ID: " + String.valueOf(account_id));
		
		DerbyDatabase database = new DerbyDatabase();

		Game game = new Game();
		GameEngine engine = new GameEngine();
		engine.setGame(game);

		Map map = new Map();
		map.buildSmallDefault(engine);

		Tile start = new Tile();
		start = map.getTile(0, 0);

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
			if(player.getLocation().getY() < map.getHeight() - 1) {
				// Move player
				int newY = player.getLocation().getY() + 1;


				player.setLocation(map.getTile(player.getLocation().getX(), newY));
				
				database.addGameLog()
				
				engine.setGameLog(engine.getGame().getGameLog() + "\n You Moved Down." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				response = engine.getGameLog();
				
			}
			else {
				
				engine.setGameLog(engine.getGameLog() + " \n You can't move there!");
				response = engine.getGameLog();
			}
		}

		// Player moves left
		else if(input.equalsIgnoreCase("move left")) {
			// If valid move
			if(player.getLocation().getY() > 0) {
				// Move player
				int newX = player.getLocation().getX() - 1;

				player.setLocation(map.getTile(newX, player.getLocation().getY()));

				engine.setGameLog(engine.getGameLog() + "\n You Moved Left." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				response = engine.getGameLog();
			}
			else {
				engine.setGameLog(engine.getGameLog() + " \n You can't move there!");
			}		
		}

		// Player moves right
		else if(input.equalsIgnoreCase("move right")) {
			// If valid move
			if(player.getLocation().getX() < map.getWidth()) {
				// Move player
				int newX = player.getLocation().getX() + 1;


				player.setLocation(map.getTile(newX,  player.getLocation().getY()));

				engine.setGameLog(engine.getGameLog() + "\n You Moved Right." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				response = engine.getGameLog();
			}
			else if(player.getLocation().getX() == map.getWidth()){
				engine.setGameLog(engine.getGameLog() + " \n You can't move there!");
				response = engine.getGameLog();
			}		
		}

		// Player moves up
		else if(input.equalsIgnoreCase("move up")) {
			// If valid move
			if(player.getLocation().getY() < 0) {
				// Move player
				int newY = player.getLocation().getY() - 1;

				player.setLocation(map.getTile(player.getLocation().getX(), newY));

				engine.setGameLog(engine.getGameLog() + "\n You Moved Up." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				response = engine.getGameLog();
			}
			else {
				engine.setGameLog(engine.getGameLog() + " \n You can't move there!");
				response = engine.getGameLog();
			}
		}

		// Player views how much damage they can deal
		else if(input.equalsIgnoreCase("view damage")) {
			engine.setGameLog(engine.getGameLog() + " \n " + String.valueOf(player.getBaseDamage()));
			response = engine.getGameLog();

		}

		// Player views their location 
		else if(input.equalsIgnoreCase("location")) {

			// If player has a location 
			if(player.getLocation() != null) {
				engine.setGameLog(engine.getGameLog() + " \n " + String.valueOf(player.getLocation().getX()) + "," + String.valueOf(player.getLocation().getY()));
				response = engine.getGameLog();

			}

			// If player does not have a location 
			else {
				engine.setGameLog(engine.getGameLog() + " \n Somehow you have no location?");
				response = engine.getGameLog();
			}

		}

		// Player views their health
		else if(input.equalsIgnoreCase("health")) {
			engine.setGameLog(engine.getGameLog() + " \n" + String.valueOf(player.getHealth()));
			response = engine.getGameLog();
		}

		// Player views current equipped item 
		else if(input.equalsIgnoreCase("item")) {

			// If player actually has an item 
			try {
				if(player.getEquippedItem().getName().equalsIgnoreCase("")) {
					engine.setGameLog(engine.getGameLog() + " \n" + player.getEquippedItem().getName());
					response = engine.getGameLog();
				}
			}
			// If player has no item equipped
			catch(NullPointerException e) {
				engine.setGameLog(engine.getGameLog() + " \n No item equipped.");
				response = engine.getGameLog();
			}
		}

		// Player views their inventory
		else if(input.equalsIgnoreCase("view inventory")) {

			// If inventory is NOT empty
			if(!player.getInventory().getInventory().isEmpty()) {
				engine.setGameLog(engine.getGameLog() + " \n" + String.valueOf(player.getInventory().getInventory()));
				response = engine.getGameLog();
			}

			// Inventory is empty
			else {
				engine.setGameLog(engine.getGameLog() + " \n Inventory is empty!");
				response = engine.getGameLog();
			}
		}

		// Player views their score 
		else if(input.equalsIgnoreCase("score")) {
			engine.setGameLog(engine.getGameLog() + " \n Score: " + String.valueOf(player.getScore()));
			response = engine.getGameLog();
		}

		// Player picks up item from tile 
		else if(input.equalsIgnoreCase("pick up item")) {

			// If tile has an item 
			if(!map.getTile(player.getLocation().getX(), player.getLocation().getY()).getItemList().isEmpty()){
				Inventory newInv = new Inventory();
				newInv.addMultipleToInventory(player.getInventory().getInventory());
				newInv.addMultipleToInventory(map.getTile(player.getLocation().getX(), player.getLocation().getY()).getItemList());
				player.setInventory(newInv);
				engine.setGameLog(engine.getGameLog() + " \n You found an item! View inventory to see it.");
				response = engine.getGameLog();
			}

			else {
				engine.setGameLog(engine.getGameLog() + "\n Theres nothing here but dirt!");
				response = engine.getGameLog();
			}
		}


		// Player attempts to exit game 
		else if(input.equalsIgnoreCase("exit")) {

			// If player is on an exit tile
			if(player.getLocation() == map.getTile(player.getLocation().getX(), player.getLocation().getY()) && map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription().contains("exit")) {
				String exitMessage = "Congrats You Escaped! Final Score: ";
				String finalScore = String.valueOf(player.getScore());
				engine.setGameLog(engine.getGameLog() + "\n" + exitMessage.concat(finalScore));
				engine.endGame();
			}
			else {
				engine.setGameLog(engine.getGameLog() + " \n This is not an exit tile !");
				response = engine.getGameLog();
			}
		}

		// Player enters unknown command
		else {
			engine.setGameLog(engine.getGameLog() + "\n I'm not quite sure what that means, please try again.");
			response = engine.getGameLog();
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
