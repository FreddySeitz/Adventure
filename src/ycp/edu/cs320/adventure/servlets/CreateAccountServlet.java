package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.cs320.adventure.database.FakeDatabase;
import ycp.edu.cs320.adventure.game.Account;

public class CreateAccountServlet extends HttpServlet{
private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("CreateAccount Servlet: doGet");
        
        req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        System.out.println("CreateAccount Servlet: doPost");
        String errorMessage = null;
        String successMessage = null;
        
        Account account = new Account();
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        
        account.setUsername(username);
        account.setPassword(password);
        
        // TODO: If database contains username give error message
        
        System.out.println("Password1: " + password);
        System.out.println("Password2: " + password2);
        
        //else{
        if(password.equals(password2)) {
        	successMessage = "Account creation successful";
        	req.setAttribute("successMessage", successMessage);
        	req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
        	// Add new account & password to database
        	// Also redirect to game.jsp
        }
        else {
        	errorMessage = "Error: Account already exists or passwords do not match.";
        	req.setAttribute("errorMessage", errorMessage);
        	req.getRequestDispatcher("/_view/createAccount.jsp").forward(req, resp);
        }
        
    }
        
}
