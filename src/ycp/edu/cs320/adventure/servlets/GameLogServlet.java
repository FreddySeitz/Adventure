package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import java.util.List;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ycp.edu.cs320.adventure.game.*;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class GameLogServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");

		req.getRequestDispatcher("/_view/gameLog.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Necessary objects & game setup

		// gets account_id of the account that was signed in from the index servlet 
		Object account_id = req.getSession(false).getAttribute("id");

		DerbyDatabase database = new DerbyDatabase();
		// Games response to user
		String response = null;

		List<Integer> game_ids = database.getGames((int)account_id);
		int game_id = game_ids.get(game_ids.size() - 1);
		response = database.getGameLog(game_id);

		req.setAttribute("response",  response);

		req.getRequestDispatcher("/_view/gameLog.jsp").forward(req, resp);
	}
}
