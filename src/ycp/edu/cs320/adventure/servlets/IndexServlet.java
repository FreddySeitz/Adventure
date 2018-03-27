package ycp.edu.cs320.adventure.servlets;

import java.io.IOException;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        
        //numController.setFirst(getDoubleFromParameter(req.getParameter("first")));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        String testUserName = "username";
        String testPassword = "password";
        
        // username and password match
        if((username.equals(testUserName)) && (password.equals(testPassword))) {
        	errorMessage = "Successful login";
        	req.setAttribute("errorMessage",  errorMessage);
        	req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
        }
        
        // username matches but password does not
        else if((username.equals(testUserName)) && (!password.equals(testPassword))) {
        	errorMessage = "Error: Username Exists with different password.";
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


