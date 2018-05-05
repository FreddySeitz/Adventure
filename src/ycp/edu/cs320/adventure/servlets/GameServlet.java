package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import java.util.List;

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
		engine.getGame().setGameId(game_id);
		//int player_id = database.getPlayer(game_id).getPlayerId();

		//Tile nextMove = new Tile(); 

		//********** Play Game Below **********

		System.out.println("GAME ID : " + game_id);
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
						engine.blankSpaceDamage(database.getPlayer(game_id));
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
					
					engine.update();

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
						engine.blankSpaceDamage(database.getPlayer(game_id));
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

					engine.update();
					
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
						engine.blankSpaceDamage(database.getPlayer(game_id));
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

					engine.update();
					
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
						engine.blankSpaceDamage(database.getPlayer(game_id));
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

					engine.update();
					
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
				database.addGameLog(game_id, Integer.toString(database.getPlayer(game_id).getBaseDamage() + database.getPlayer(game_id).getEquippedItem().getDamage()));
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

			// Player views an inventory item
			else if(input.toLowerCase().contains("inspect") || input.toLowerCase().contains("view")) {
					database.addGameLog(game_id, engine.inspectItem(input, database.getPlayer(game_id)));
					response = database.getGameLog(game_id);
			}

			// Player views their inventory
			else if(input.equalsIgnoreCase("view inventory") || input.equalsIgnoreCase("inventory") || input.equalsIgnoreCase("inv")) {
				// If inventory is NOT empty
				List<Item> inventory = database.getPlayerInventory(database.getPlayer(game_id).getPlayerId());
				if(inventory.size() > 0){
					//constructing the list of items
					for(int i = 0; i < inventory.size(); i++){
						text.append(inventory.get(i).getName());
						if(i+1 < inventory.size()){
							text.append("<br/>");
						}
					}
					database.addGameLog(game_id, text.toString());
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
				database.addGameLog(game_id, Integer.toString(database.getPlayer(game_id).getScore()));
				response = database.getGameLog(game_id);
			}

			// Player picks up item from tile 
			else if(input.equalsIgnoreCase("pick up item") || input.equalsIgnoreCase("pick up") ||
					input.equalsIgnoreCase("take")) {

				// If tile has an item 
				if(database.getTileInventory(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getTileId()).size() > 0){
					engine.pickupItem(database.getPlayer(game_id), database.getTileInventory(database.getTile(game_id, (int)req.getSession(false).getAttribute("playerX"), (int)req.getSession(false).getAttribute("playerY")).getTileId()).get(0));

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
				player = database.getPlayer(game_id);
				if(player.getLocation().getType() == 3) {
					String exitMessage = "Congrats You Escaped! Final Score: ";
					String finalScore = String.valueOf(database.getPlayer(game_id).getScore());
					database.addGameLog(game_id, exitMessage.concat(finalScore));
					response = database.getGameLog(game_id);
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

			// Player view the game log
			else if(input.equalsIgnoreCase("view log") || input.equalsIgnoreCase("log")){
				response = database.getGameLog(game_id);
			}
			
			// Player views the tiles around them
			else if(input.equalsIgnoreCase("view area") || input.equalsIgnoreCase("scan") || 
					input.equalsIgnoreCase("look") || input.equalsIgnoreCase("glance")
					|| input.equalsIgnoreCase("look around")){
				
				text.append("You observe your surroundings");
				
				//list items at the location
				player = database.getPlayer(game_id);
				List<Tile> tiles = database.getAllTiles(game_id);
				int playerLoc = player.getLocation().getY() * map.getWidth() + player.getLocation().getX();
				List<Item> items = database.getTileInventory(tiles.get(playerLoc).getTileId());
				if(items.size() > 0){
					if(items.size() > 1){
						text.append("<br/>You spot some items:");
					}
					else{
						text.append("<br/>you spot an item:");
					}
					for(Item item : items){
						text.append("<br/>" + item.getName());
					}
				}
				//data entry so player knows they looked around.
				database.addGameLog(game_id, text.toString());
				
				//not adding whole description to game log, would be too wordy
				response = engine.lookAround(player);
			}
			
			// Player equips item
			else if(input.toLowerCase().contains("equip ") || input.toLowerCase().contains("hold ")){
				String itemName = engine.equipItem(input, database.getPlayer(game_id));
				if(!itemName.equals("")){
					text.append(itemName + " was equipped.");
				}
				else{
					text.append(itemName + " was not found in inventory.");
				}
				
				database.addGameLog(game_id, text.toString());

				response = database.getGameLog(game_id);
			}
			
			//unequip
			else if(input.equalsIgnoreCase("unequip") || input.equalsIgnoreCase("take off") || 
					input.equalsIgnoreCase("unhand") || input.equalsIgnoreCase("put away")){
				player = database.getPlayer(game_id);
				//if an item is equipped
				if(player.getEquippedItem().getItemId() != 0){
					database.updatePlayerEquippedItem(player.getPlayerId(), 0);
					text.append("The item has been unequipped.");
				}
				else{	//if no item is equipped
					text.append("You are not holding an item.");
				}
				
				database.addGameLog(game_id, text.toString());
				response = database.getGameLog(game_id);
			}
			
			//drop item
			else if(input.toLowerCase().contains("drop ") || input.toLowerCase().contains("release ") || 
					input.toLowerCase().contains("dispose ")){
				text.append(engine.dropItem(input, database.getPlayer(game_id)));
				
				database.addGameLog(game_id, text.toString());

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
		//an attempt to make gamelog show up at start of game
		if(response.length() == 0){
			response = database.getGameLog(game_id);
		}
		req.setAttribute("response",  response);

		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}

}
