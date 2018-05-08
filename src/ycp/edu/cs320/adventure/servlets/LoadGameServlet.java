package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import java.util.List;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ycp.edu.cs320.adventure.game.Game;
import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;


//********** Same as game Servlet except loads a game state from database **********//


public class LoadGameServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");
		int account_id = (int)req.getSession(false).getAttribute("id");
		DerbyDatabase database = new DerbyDatabase();
		StringBuilder text = new StringBuilder();
		List<Integer> games = database.getGames(account_id);
		for(int i = 0; i < games.size(); i++){
			text.append(i+1 + "---");
			text.append("Health: " + database.getPlayer(games.get(i)).getHealth() + "---");
			text.append("Damage: " + (database.getPlayer(games.get(i)).getBaseDamage() + database.getPlayer(games.get(i)).getEquippedItem().getDamage()) + "---");
			text.append("Score: " + database.getPlayer(games.get(i)).getHealth() + "<br/><br/>");
		}
		
		String response = text.toString();

		req.setAttribute("response", response);
		req.getRequestDispatcher("/_view/loadGame.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Game Servlet: doPost");
		String response = null;
		String errorMessage = null;

		//********** Testing **********


		//********** Actual Game Code Below **********

		DerbyDatabase database = new DerbyDatabase();
		int account_id = (int)req.getSession(false).getAttribute("id");
		HttpSession ses = req.getSession(true);

		// User input from jsp
		String input = req.getParameter("userInput");

		String button = req.getParameter("viewGames");
		List<Integer> games = database.getGames(account_id);
		System.out.println("games: " + games);
		
		StringBuilder text = new StringBuilder();
		for(int i = 0; i < games.size(); i++){
			text.append(i+1 + "---");
			text.append("Health: " + database.getPlayer(games.get(i)).getHealth() + "---");
			text.append("Damage: " + (database.getPlayer(games.get(i)).getBaseDamage() + database.getPlayer(games.get(i)).getEquippedItem().getDamage()) + "---");
			text.append("Score: " + database.getPlayer(games.get(i)).getHealth() + "<br/><br/>");
		}
		
		response = text.toString();

		int choice = -1;

		try {
			//choice = Integer.parseInt(input) - 1;
			if(Integer.parseInt(input) > 0){
				choice = games.get(Integer.parseInt(input) - 1);
			}
		}
		catch(NumberFormatException e) {
			System.out.println(e);
			//errorMessage = "Please Make a Valid Selection";

			//req.setAttribute("errorMessage", errorMessage);
			//req.getRequestDispatcher("/_view/loadGame.jsp").forward(req, resp);
		}


//		List<Integer> games = database.getGames(account_id);

		// if the choice is valid 
		if(games.contains(choice)) {
			GameEngine engine = new GameEngine();
			Game game = new Game();
			engine.setGame(game);
			engine.loadGame(choice);
			//Map map = new Map();
			//map.buildDefault();
			//populate map from database
			Map map = database.getMap(choice);

			List<Tile> tiles = database.getAllTiles(choice);

			for(int i=0; i<map.getHeight(); i++) {
				for(int j=0; j<map.getWidth(); j++) {
					map.setTile(j, i, tiles.get(i+j));
				}
			}
			
			

			ses.setAttribute("game", game);
			ses.setAttribute("engine", engine);
			ses.setAttribute("map", map);
			ses.setAttribute("player", game.getPlayer());
			ses.setAttribute("playerX", game.getPlayer().getLocation().getX());
			ses.setAttribute("playerY", game.getPlayer().getLocation().getY());
			ses.setAttribute("id", account_id);
			ses.setAttribute("game_id", choice);

			ses = req.getSession(true);

			resp.sendRedirect(req.getContextPath() + "/game");
			return; 

		}

		// if the choice is invalid 
		else {

		}
		
		req.setAttribute("response",  response);

		req.getRequestDispatcher("/_view/loadGame.jsp").forward(req, resp);


	}

}
