package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TitleScreenServlet extends HttpServlet{
	    private static final long serialVersionUID = 1L;
	    
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        
	        System.out.println("Title Servlet: doGet");
	        
	        req.getRequestDispatcher("/_view/titleScreen.jsp").forward(req, resp);
	    }
	    
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        
	        System.out.println("Title Servlet: doPost");
        	
	        String button = req.getParameter("button");
	        
	        if (button.equals("New Game")) {
	          	resp.sendRedirect(req.getContextPath() + "/_view/game.jsp");
	            return; 
	        } 
	        
	        else if (button.equals("Edit Game")) {
	          	resp.sendRedirect(req.getContextPath() + "/_view/game.jsp");
	            return; 
	        } 
	        
	        else if (button.equals("Load Game")){
	          	resp.sendRedirect(req.getContextPath() + "/_view/loadGame.jsp");
	          	return; 
	        }
	    }
}
