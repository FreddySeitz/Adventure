package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.cs320.adventure.database.FakeDatabase;
import ycp.edu.cs320.adventure.game.Account;

public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("Index Servlet: doGet");
        
        req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("index Servlet: doPost");
        String errorMessage = null;
        String successMessage = "Login Successful.";
        // TODO: replace username & password with an account object
        // TODO: test if username/password are contained in the database
        // TODO: if successful login, go to next page
        
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        //Account account = new Account(username, password);
        
        //database query
        FakeDatabase database = new FakeDatabase();
        int accountId = database.accountExists(username);        
        
        // username and password match
        if(accountId >= 0 && database.login(username, password)) {
        	errorMessage = "Successful login";
        	System.out.println("Successful log in :)");
    		// redirect to /titleScreen page
        	resp.sendRedirect(req.getContextPath() + "/_view/titleScreen.jsp");
        	return;
        }
        
        //username matches but password does not
        else if(accountId == -2/*(account.getUsername().equals(testUserName)) && (!account.getPassword().equals(testPassword))*/) {
        	errorMessage = "Error: Username or password is incorrect.";
        	req.setAttribute("errorMessage", errorMessage);
        	req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
        }
        
        else {
        	errorMessage = "Error: account does not exist";
        	req.setAttribute("errorMessage", errorMessage);
        	req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
        }
    }
}


