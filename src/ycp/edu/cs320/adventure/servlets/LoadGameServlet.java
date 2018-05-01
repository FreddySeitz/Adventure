package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;


//********** Same as game Servlet except loads a game state from database **********//


public class LoadGameServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;	

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("Game Servlet: doGet");
        
        req.getRequestDispatcher("/_view/loadGame.jsp").forward(req, resp);
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		
		System.out.println("Game Servlet: doPost");
        String response = null;
        
        //********** Testing **********

    	
    	//********** Actual Game Code Below **********
    	
    	DerbyDatabase database = new DerbyDatabase();
		int account_id = (int)req.getSession(false).getAttribute("id");
		
		System.out.println("games: " + database.getGames(account_id));
        response = database.getGames(account_id).toString();

        req.setAttribute("response",  response);
    	req.getRequestDispatcher("/_view/loadGame.jsp").forward(req, resp);

	}
	        
}
