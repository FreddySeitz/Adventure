package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class GameServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;	

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("Game Servlet: doGet");
        
        req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		
		System.out.println("Game Servlet: doPost");
        String response = null;
        
        //********** Testing **********
        response = "test response from game successful.";
        req.setAttribute("response",  response);
    	req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
    	//********** Testing **********
    	
    	//********** Actual Game Code Below **********
    	
	}
	        
}
