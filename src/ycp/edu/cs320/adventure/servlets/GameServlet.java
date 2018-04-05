package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.cs320.adventure.game.Player;



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

		// False while player is playing

		Player player = new Player();

		String input = req.getParameter("userInput");

		System.out.println("Game Servlet: doPost");
		String response = null;

		//********** Testing **********

		//		req.setAttribute("response",  response);
		//		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
		//********** Testing **********

		//********** Actual Game Code Below **********



		// Player moves down
		if(input.equalsIgnoreCase("move down")) {
			response = input;
		}

		// Player moves left
		else if(input.equalsIgnoreCase("move left")) {
			response = input;
		}

		// Player moves right
		else if(input.equalsIgnoreCase("move right")) {
			response = input;
		}

		// Player moves up
		else if(input.equalsIgnoreCase("move up")) {
			response = input;
		}

		// Player views how much damage they can deal
		else if(input.equalsIgnoreCase("view damage")) {
			response = String.valueOf(player.getBaseDamage());

		}

		// Player views their location 
		else if(input.equalsIgnoreCase("location")) {
			
			// If player has a location 
			if(player.getLocation() != null) {
			response = String.valueOf(player.getLocation());
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
		}

		// Player enters unknown command
		else {
			response = "I'm not quite sure what that means... try again please";
		}

		req.setAttribute("response",  response);

		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}

}
