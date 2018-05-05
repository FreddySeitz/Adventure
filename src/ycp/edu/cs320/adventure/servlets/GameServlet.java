package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		GameEngine engine = new GameEngine();

		engine.setGame((Game) req.getSession(false).getAttribute("game"));
		engine = (GameEngine) req.getSession(false).getAttribute("engine");

		HttpSession ses = req.getSession(true);

		Map map = (Map) req.getSession(false).getAttribute("map");
		System.out.println("map: " + map.toString());

		Player player = (Player) req.getSession(false).getAttribute("player");

		// gets player x y coordinates
		//ses = req.getSession(true);
		int playerX = 0;
		int playerY = 0;
		try {
			playerX = (int)req.getSession(false).getAttribute("playerX");
			playerY = (int)req.getSession(false).getAttribute("playerY");
			System.out.println("X: " + playerX);
			System.out.println("Y: " + playerY);
		}
		catch(NullPointerException e){
			System.out.println(e);
			System.out.println((int)req.getSession(false).getAttribute("playerX"));
			System.out.println((int)req.getSession(false).getAttribute("playerY"));

		}
		// User input from jsp
		String input = req.getParameter("userInput");

		// The text that gets added to the database
		StringBuilder text = new StringBuilder();

		// Games response to user
		String response = null;

		System.out.println("Game Servlet: doPost");


		//List<Integer> game_ids = database.getGames((int)account_id);
		int game_id = (int)req.getSession(false).getAttribute("game_id");

		//int player_id = database.getPlayer(game_id).getPlayerId();

		//Tile nextMove = new Tile(); 

		//********** Play Game Below **********


		/* Handling user input */
	
		//location before movement from this turn, used for traps
		Tile previousPlayerTile = database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));
		//on an active trap
		if(previousPlayerTile.getActive()== true && 
				previousPlayerTile.getPrompt() == true){
			System.out.println("Going through trap");
			engine.promptTrap(database.getPlayer(game_id), previousPlayerTile, input, text);
			
			database.addGameLog(game_id, text.toString());
			response = database.getGameLog(game_id);
		}
		else{
			// Player moves down
			if(input.equalsIgnoreCase("move down") || input.equalsIgnoreCase("down") || input.equalsIgnoreCase("south")) {
				// If valid move
				if((int)req.getSession(false).getAttribute("playerY") < map.getHeight() - 1) {
					// Move player
					int newY = (int)req.getSession(false).getAttribute("playerY") + 1;

					ses.setAttribute("playerY",newY);

					System.out.println("Move down");
					System.out.println("X: " + (int)req.getSession(false).getAttribute("playerX"));
					System.out.println("Y: " + (int)req.getSession(false).getAttribute("playerY"));

					engine.movePlayer((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));

					//action and tile description
					text.append("You Moved Down.<br/>" + map.getTile((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getDescription());
					
					//damage from blank space
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getType() == 0){
						text.append("<br/>You walked through a prickly mass.  -5 HP");
						engine.blankSpaceDamage(player);
					}

					//if walking on a trap
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getActive() == true){
						Tile tile = database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));
						
						database.updateTilePrompt(true, tile.getTileId());
						database.updateTileHidden(false, tile.getTileId());
						text.append("<br/>You walked on a trap! Answer the question to disable the trap.");
						text.append("<br/>" + tile.getRiddle());
					}

					//setting tile visibility
					//if they are not at the bottom of the map
					if((int)req.getSession(false).getAttribute("playerY") < map.getHeight() - 1){
						//if there is space to the left
						if((int)req.getSession(false).getAttribute("playerX") > 0){
							database.updateTileVisible(true, database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX")-1, newY+1).getTileId());
						}
						//makes visible the space below them
						database.updateTileVisible(true, database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), newY+1).getTileId());

						//if there is space to the right
						if((int)req.getSession(false).getAttribute("playerX") < map.getWidth()-1){
							database.updateTileVisible(true, database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX")+1, newY+1).getTileId());
						}
					}

					database.addGameLog(game_id, text.toString());
					response = database.getGameLog(game_id);

				}
				else {

					database.addGameLog(game_id, "You can't move there!");
					response = database.getGameLog(game_id);
				}
			}

			// Player moves left
			else if(input.equalsIgnoreCase("move left") || input.equalsIgnoreCase("left") || input.equalsIgnoreCase("west")) {
				// If valid move
				if((int)req.getSession(false).getAttribute("playerX") > 0) {
					// Move player
					int newX = (int)req.getSession(false).getAttribute("playerX") - 1;
					// These:
					//database.updatePlayerX(player_id, newX);
					//database.updatePlayerY(player_id, player.getLocation().getY());
					// Do This:
					//player.setLocation(map.getTile(newX,  player.getLocation().getY()));
					//nextMove = map.getTile(newX, player.getLocation().getY());

					ses.setAttribute("playerX",newX);
					//ses.setAttribute("playerY",player.getLocation().getY());

					System.out.println("Move left");
					System.out.println("X: " + (int)req.getSession(false).getAttribute("playerX"));
					System.out.println("Y: " + (int)req.getSession(false).getAttribute("playerY"));

					engine.movePlayer((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));

					//action and tile description
					text.append("You Moved Left.<br/>" + map.getTile((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getDescription());

					//damage from blank space
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getType() == 0){
						text.append("<br/>You walked through a prickly mass.  -5 HP");
						engine.blankSpaceDamage(player);
					}

					//if walking on a trap
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getActive() == true){
						Tile tile = database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));
						
						database.updateTilePrompt(true, tile.getTileId());
						database.updateTileHidden(false, tile.getTileId());
						text.append("<br/>You walked on a trap! Answer the question to disable the trap.");
						text.append("<br/>" + tile.getRiddle());
					}

					//visibility
					if((int)req.getSession(false).getAttribute("playerX") > 0){
						//if there is space above them
						if((int)req.getSession(false).getAttribute("playerY") > 0){
							database.updateTileVisible(true, database.getTile(game_id, newX-1, (int)req.getSession(false).getAttribute("playerY")-1).getTileId());
						}
						//makes visible the space to their left
						database.updateTileVisible(true, database.getTile(game_id, newX-1, (int)req.getSession(false).getAttribute("playerY")).getTileId());

						//if there is space below them
						if((int)req.getSession(false).getAttribute("playerX") < map.getWidth()-1){
							database.updateTileVisible(true, database.getTile(game_id, newX-1, (int)req.getSession(false).getAttribute("playerY")+1).getTileId());
						}
					}

					database.addGameLog(game_id, text.toString());
					response = database.getGameLog(game_id);
				}
				else {
					database.addGameLog(game_id, "You can't move there!");
					response = database.getGameLog(game_id);
				}		
			}

			// Player moves right
			else if(input.equalsIgnoreCase("move right") || input.equalsIgnoreCase("right") || input.equalsIgnoreCase("east")) {
				// If valid move
				if((int)req.getSession(false).getAttribute("playerX") < map.getWidth()-1) {
					// Move player
					int newX = (int)req.getSession(false).getAttribute("playerX") + 1;

					ses.setAttribute("playerX",newX);
					//ses.setAttribute("playerY",player.getLocation().getY());

					System.out.println("Move right");
					System.out.println("X: " + (int)req.getSession(false).getAttribute("playerX"));
					System.out.println("Y: " + (int)req.getSession(false).getAttribute("playerY"));

					engine.movePlayer((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));

					//action and tile description
					text.append("You Moved Right.<br/>" + map.getTile((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getDescription());

					//damage from blank space
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getType() == 0){
						text.append("<br/>You walked through a prickly mass.  -5 HP");
						engine.blankSpaceDamage(player);
					}

					//if walking on a trap
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getActive() == true){
						Tile tile = database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));
						
						database.updateTilePrompt(true, tile.getTileId());
						database.updateTileHidden(false, tile.getTileId());
						text.append("<br/>You walked on a trap! Answer the question to disable the trap.");
						text.append("<br/>" + tile.getRiddle());
					}

					//visibility
					if((int)req.getSession(false).getAttribute("playerX") < map.getWidth()-1){
						//if there is space above them
						if((int)req.getSession(false).getAttribute("playerY") > 0){
							database.updateTileVisible(true, database.getTile(game_id, newX+1, (int)req.getSession(false).getAttribute("playerY")-1).getTileId());
						}
						//makes visible the space to their left
						database.updateTileVisible(true, database.getTile(game_id, newX+1, (int)req.getSession(false).getAttribute("playerY")).getTileId());

						//if there is space below them
						if((int)req.getSession(false).getAttribute("playerX") < map.getWidth()-1){
							database.updateTileVisible(true, database.getTile(game_id, newX+1, (int)req.getSession(false).getAttribute("playerY")+1).getTileId());
						}
					}

					database.addGameLog(game_id, text.toString());
					response = database.getGameLog(game_id);
				}
				else {
					database.addGameLog(game_id, "You can't move there!");
					response = database.getGameLog(game_id);
				}
			}

			// Player moves up
			else if(input.equalsIgnoreCase("move up") || input.equalsIgnoreCase("up") || input.equalsIgnoreCase("north")) {
				// If valid move
				if((int)req.getSession(false).getAttribute("playerY") > 0) {
					// Move player
					int newY = (int)req.getSession(false).getAttribute("playerY") - 1;
					ses.setAttribute("playerY",newY);

					System.out.println("Move up");
					System.out.println("X: " + (int)req.getSession(false).getAttribute("playerX"));
					System.out.println("Y: " + (int)req.getSession(false).getAttribute("playerY"));

					engine.movePlayer((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));

					//action and tile description
					text.append("You Moved Up.<br/>" + map.getTile((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getDescription());

					//damage from blank space
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getType() == 0){
						text.append("<br/>You walked through a prickly mass.  -5 HP");
						engine.blankSpaceDamage(player);
					}

					//if walking on a trap
					if(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getActive() == true){
						Tile tile = database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY"));
						
						database.updateTilePrompt(true, tile.getTileId());
						database.updateTileHidden(false, tile.getTileId());
						text.append("<br/>You walked on a trap! Answer the question to disable the trap.");
						text.append("<br/>" + tile.getRiddle());
					}

					//visibility
					if((int)req.getSession(false).getAttribute("playerY") > 0){
						//if there is space to the left
						if((int)req.getSession(false).getAttribute("playerX") > 0){
							database.updateTileVisible(true, database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX")-1, newY-1).getTileId());
						}
						//makes visible the space in front of them
						database.updateTileVisible(true, database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), newY-1).getTileId());

						//if there is space to the right
						if((int)req.getSession(false).getAttribute("playerX") < map.getWidth()-1){
							database.updateTileVisible(true, database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX")+1, newY-1).getTileId());
						}
					}

					database.addGameLog(game_id, text.toString());
					response = database.getGameLog(game_id);
				}
				else {
					database.addGameLog(game_id, "You can't move there!");
					response = database.getGameLog(game_id);
				}
			}

			// Player views how much damage they can deal
			else if(input.equalsIgnoreCase("view damage") || input.equalsIgnoreCase("damage")) {
				database.addGameLog(game_id, String.valueOf(player.getBaseDamage()));
				response = database.getGameLog(game_id);

			}

			// Player views their location 
			else if(input.equalsIgnoreCase("view location") || input.equalsIgnoreCase("location")) {

				// If player has a location 
				//if(player.getLocation() != null) {
				//database.addGameLog(game_id,  String.valueOf(player.getLocation().getX()) + "," + String.valueOf(player.getLocation().getY()));
				//response = database.getGameLog(game_id);
				String location = (req.getSession(false).getAttribute("playerX")).toString() + " , " + ((String)req.getSession(false).getAttribute("playerY").toString());
				response = location;
				//}

				// If player does not have a location 
				//else {
				//	database.addGameLog(game_id, "Somehow you have no location?");
				//	response = database.getGameLog(game_id);
				//}

			}

			// Player views their health
			else if(input.equalsIgnoreCase("view health") || input.equalsIgnoreCase("health")) {
				//database.addGameLog(game_id, String.valueOf(player.getHealth()));
				text.append("Health: ");
				text.append(Integer.toString(database.getPlayer(game_id).getHealth()));
				database.addGameLog(game_id, text.toString());
				response = database.getGameLog(game_id);
			}

			// Player views current equipped item 
			else if(input.equalsIgnoreCase("inspect item") || input.equalsIgnoreCase("view item") || input.equalsIgnoreCase("item")) {

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
			else if(input.equalsIgnoreCase("view inventory") || input.equalsIgnoreCase("inventory")) {

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
			else if(input.equalsIgnoreCase("view score") || input.equalsIgnoreCase("score")) {
				database.addGameLog(game_id, String.valueOf(player.getScore()));
				response = database.getGameLog(game_id);
			}

			// Player picks up item from tile 
			else if(input.equalsIgnoreCase("pick up item") || input.equalsIgnoreCase("pick up")) {

				// If tile has an item 
				if(database.getTileInventory(map.getTile((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getTileId()).size() > 0){
					engine.pickupItem(player, database.getTileInventory(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getTileId()).get(0));

					//				database.addToPlayerInventory(player_id, map.getTile((int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getItemList().get(0).getItemId());

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

			// Player views their map
			else if(input.equalsIgnoreCase("view map") || input.equalsIgnoreCase("map")) {
				response = engine.viewMap();
			}

			else if(input.equalsIgnoreCase("view log") || input.equalsIgnoreCase("log")){
				response = database.getGameLog(game_id);
			}

			else if(input.equalsIgnoreCase("view area") || input.equalsIgnoreCase("scan") || 
					input.equalsIgnoreCase("look") || input.equalsIgnoreCase("glance")){
				//return long descriptions of bordering tiles
				database.addGameLog(game_id, "unimplemented.  TODO: FEED THE HAMSTERS!");
				response = database.getGameLog(game_id);
			}

			// Player enters unknown command
			else {
				database.addGameLog(game_id, "I'm not quite sure what that means, please try again.");

				response = database.getGameLog(game_id);
			}

			/* Checking for environment conditions */

			// If player steps on trap
			//if(player.getLocation() == map.getTile(player.getLocation().getX(), player.getLocation().getY()) && map.getTile(player.getLocation().getX(), player.getLocation().getY()).getType() == 2) {
			//	player.hurt((map.getTile(player.getLocation().getX(), player.getLocation().getY())).getDamage());
			//}
		}

		req.setAttribute("response",  response);

		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}

}
