package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import java.util.List;

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
		
		List<Integer> game_ids = database.getGames((int)account_id);
		int game_id = game_ids.get(0);

		//********** Play Game Below **********


		/* Handling user input */

		// Player moves down
		if(input.equalsIgnoreCase("move down")) {
			// If valid move
			if(player.getLocation().getY() < map.getHeight() - 1) {
				// Move player
				int newY = player.getLocation().getY() + 1;


				player.setLocation(map.getTile(player.getLocation().getX(), newY));
				
				database.addGameLog(game_id, " You Moved Down." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				
				
				response = database.getGameLog(game_id);
				
			}
			else {
				
				database.addGameLog(game_id, "You can't move there!");
				response = database.getGameLog(game_id);
			}
		}

		// Player moves left
		else if(input.equalsIgnoreCase("move left")) {
			// If valid move
			if(player.getLocation().getY() > 0) {
				// Move player
				int newX = player.getLocation().getX() - 1;

				player.setLocation(map.getTile(newX, player.getLocation().getY()));

				database.addGameLog(game_id, " You Moved Left." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				
				response = database.getGameLog(game_id);
			}
			else {
				database.addGameLog(game_id, "You can't move there!");
				response = database.getGameLog(game_id);
			}		
		}

		// Player moves right
		else if(input.equalsIgnoreCase("move right")) {
			// If valid move
			if(player.getLocation().getX() < map.getWidth()) {
				// Move player
				int newX = player.getLocation().getX() + 1;


				player.setLocation(map.getTile(newX,  player.getLocation().getY()));

				database.addGameLog(game_id, "You Moved Right." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				
				response = database.getGameLog(game_id);
			}
			else if(player.getLocation().getX() == map.getWidth()){
				database.addGameLog(game_id, "You can't move there!");
				response = database.getGameLog(game_id);
			}		
		}

		// Player moves up
		else if(input.equalsIgnoreCase("move up")) {
			// If valid move
			if(player.getLocation().getY() < 0) {
				// Move player
				int newY = player.getLocation().getY() - 1;

				player.setLocation(map.getTile(player.getLocation().getX(), newY));

				database.addGameLog(game_id, "You Moved Up." + map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription());
				response = database.getGameLog(game_id);
			}
			else {
				database.addGameLog(game_id, "You can't move there!");
				response = database.getGameLog(game_id);
			}
		}

		// Player views how much damage they can deal
		else if(input.equalsIgnoreCase("view damage")) {
			database.addGameLog(game_id, String.valueOf(player.getBaseDamage()));
			response = database.getGameLog(game_id);

		}

		// Player views their location 
		else if(input.equalsIgnoreCase("location")) {

			// If player has a location 
			if(player.getLocation() != null) {
				database.addGameLog(game_id,  String.valueOf(player.getLocation().getX()) + "," + String.valueOf(player.getLocation().getY()));
				response = database.getGameLog(game_id);

			}

			// If player does not have a location 
			else {
				database.addGameLog(game_id, "Somehow you have no location?");
				response = database.getGameLog(game_id);
			}

		}

		// Player views their health
		else if(input.equalsIgnoreCase("health")) {
			database.addGameLog(game_id, String.valueOf(player.getHealth()));
			response = database.getGameLog(game_id);
		}

		// Player views current equipped item 
		else if(input.equalsIgnoreCase("item")) {

			// If player actually has an item 
			try {
				if(player.getEquippedItem().getName().equalsIgnoreCase("")) {
					database.addGameLog(game_id, player.getEquippedItem().getName());
					response = database.getGameLog(game_id);
				}
			}
			// If player has no item equipped
			catch(NullPointerException e) {
				database.addGameLog(game_id, "No item equipped.");
				response = database.getGameLog(game_id);
			}
		}

		// Player views their inventory
		else if(input.equalsIgnoreCase("view inventory")) {

			// If inventory is NOT empty
			if(!player.getInventory().getInventory().isEmpty()) {
				database.addGameLog(game_id, String.valueOf(player.getInventory().getInventory()));
				response = database.getGameLog(game_id);
			}

			// Inventory is empty
			else {
				database.addGameLog(game_id, "Inventory is empty!");
				response = database.getGameLog(game_id);
			}
		}

		// Player views their score 
		else if(input.equalsIgnoreCase("score")) {
			database.addGameLog(game_id, String.valueOf(player.getScore()));
			response = database.getGameLog(game_id);
		}

		// Player picks up item from tile 
		else if(input.equalsIgnoreCase("pick up item")) {

			// If tile has an item 
			if(!map.getTile(player.getLocation().getX(), player.getLocation().getY()).getItemList().isEmpty()){
				Inventory newInv = new Inventory();
				newInv.addMultipleToInventory(player.getInventory().getInventory());
				newInv.addMultipleToInventory(map.getTile(player.getLocation().getX(), player.getLocation().getY()).getItemList());
				player.setInventory(newInv);
				database.addGameLog(game_id, "You found an item! View inventory to see it.");
				response = database.getGameLog(game_id);
			}

			else {
				database.addGameLog(game_id, "Theres nothing here but dirt!");
				response = database.getGameLog(game_id);
			}
		}


		// Player attempts to exit game 
		else if(input.equalsIgnoreCase("exit")) {

			// If player is on an exit tile
			if(player.getLocation() == map.getTile(player.getLocation().getX(), player.getLocation().getY()) && map.getTile(player.getLocation().getX(), player.getLocation().getY()).getDescription().contains("exit")) {
				String exitMessage = "Congrats You Escaped! Final Score: ";
				String finalScore = String.valueOf(player.getScore());
				database.addGameLog(game_id, exitMessage.concat(finalScore));
				engine.endGame();
			}
			else {
				database.addGameLog(game_id, "This is not an exit tile !");
				response = database.getGameLog(game_id);
			}
		}

		// Player enters unknown command
		else {
			database.addGameLog(game_id, "I'm not quite sure what that means, please try again.");
			
			response = database.getGameLog(game_id);
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
