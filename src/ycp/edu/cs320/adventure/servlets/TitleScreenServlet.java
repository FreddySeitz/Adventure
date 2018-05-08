package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ycp.edu.cs320.adventure.game.GameEngine;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

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
	        HttpSession ses = req.getSession(true);
	        
	        // gets account_id of the account that was signed in from the index servlet 
	        Object account_id = req.getSession(false).getAttribute("id");
	        GameEngine engine = new GameEngine();
	        
	        DerbyDatabase database = new DerbyDatabase();
	        
	        System.out.println("ID: " + String.valueOf(account_id));
	        
	        Map map = new Map();
	        map.buildDefault();
			//map.buildSmallDefault(engine);
	        
	        if (button.equals("New Game")) {
	        	ses = req.getSession(true);
	        	
	        	List<Integer> game_ids = database.getGames((int)account_id);
				//int game_id = game_ids.size() - 1;
	        	
	        	ses.setAttribute("map", map);
	        	ses.setAttribute("id",(int)account_id);
	        	int game_id = engine.createGame((int)account_id);
	        	ses.setAttribute("engine",engine);
	        	ses.setAttribute("playerX", 0);
	        	ses.setAttribute("playerY", 0);
	        	ses.setAttribute("game_id", game_id);
	        	Player player = engine.getGame().getPlayer();
	        	ses.setAttribute("player", player);
	        	ses.setAttribute("playing", true);
	        	
	          	resp.sendRedirect(req.getContextPath() + "/game");
	            return; 
	        } 
	        
	        else if (button.equals("Edit Game")) {
	        	ses = req.getSession(true);
	        	ses.setAttribute("id",(int)account_id);
	          	resp.sendRedirect(req.getContextPath() + "/_view/game.jsp");
	            return; 
	        } 
	        
	        else if (button.equals("Load Game")){
	        	ses = req.getSession(true);
	        	ses.setAttribute("id",(int)account_id);
	          	resp.sendRedirect(req.getContextPath() + "/loadGame");
	          	return; 
	        }
	    }
}
