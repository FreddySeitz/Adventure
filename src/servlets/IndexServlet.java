package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
import edu.ycp.cs320.aloehr.controller.GuessingGameController;
import edu.ycp.cs320.aloehr.controller.NumbersController;
import edu.ycp.cs320.aloehr.model.GuessingGame;
import edu.ycp.cs320.aloehr.model.Numbers;
*/

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
        
        /*
        GuessingGame model = new GuessingGame();
        GuessingGameController controller = new GuessingGameController();
        controller.setModel(model);
        
        Numbers numbers = new Numbers();
        NumbersController numController = new NumbersController();
        numController.setModel(numbers);
        
        boolean game = false;
        // holds the error message text, if there is any
        String errorMessage = null;

        // result of calculation goes here
        Double result = null;
        
        // decode POSTed form parameters and dispatch to controller
        
        //Add Numbers
        
        try {
            // "first" & "second" are names from addNumbers.jsp, line 28 & 32
            numController.setFirst(getDoubleFromParameter(req.getParameter("first")));
            numController.setSecond(getDoubleFromParameter(req.getParameter("second")));
            numController.setThird(getDoubleFromParameter(req.getParameter("third")));
    
            // check for errors in the form data before using is in a calculation
            if ((numController.getFirst() == null || numController.getSecond() == null || numController.getThird() == null) && ((req.getParameter("add") != null) || (req.getParameter("multiply") != null)) ) {
                errorMessage = "Please specify 3 numbers";
            }
            // otherwise, data is good, do the calculation
            // must create the controller each time, since it doesn't persist between POSTs
            // the view does not alter data, only controller methods should be used for that
            // thus, always call a controller method to operate on the data
            
            // Add numbers 
            else if(req.getParameter("add") != null)
            {
                game = false;
                //NumbersController numController = new NumbersController();
                result = numController.add();
            }
            
            // Multiply numbers
            else if(req.getParameter("multiply") != null) {
                game = false;
                //NumbersController numController = new NumbersController();
                result = numController.multiply();
            }
            
            // Guessing game 
            else if (req.getParameter("startGame") != null) {
                
                //if (req.getParameter("startGame") != null) {
                game = true;
                controller.startGame();
            }
                
                // otherwise, user is already playing the game - get the old min and max
                // from the posted form
                // without persistence, we must pass the values back and forth between the
                // client and the server every time in order to remember them
            else if(game = true){
                    // get min and max from the Posted form data
                    Integer curMin = getInteger(req, "min");
                    Integer curMax = getInteger(req, "max");
                    
                    // initialize model with the old min, max values
                    // since the data does not persist between posts, we need to 
                    // recreate and re-initialize the model each time
                    model.setMin(curMin);
                    model.setMax(curMax);

                    // now check to see which button the user pressed
                    // and adjust min, max, and guess accordingly
                    // must call controller methods to do this since the
                    // view only reads the model data, it never changes
                    // the model - only the controller can change the model
                    if (req.getParameter("gotIt") != null) {
                        controller.setNumberFound();
                    } else if (req.getParameter("less") != null) {
                        controller.setNumberIsLessThanGuess();
                    } else if (req.getParameter("more") != null) {
                        controller.setNumberIsGreaterThanGuess();
                    } else {
                        throw new ServletException("Unknown command");
                    }
                }
                
            if(game) {
                // set "game" attribute to the model reference
                // the JSP will reference the model elements through "game"
                req.setAttribute("game", model);
                
                // now call the JSP to render the new page
                req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
            }

            
            
        } catch (NumberFormatException e) {
            errorMessage = "Invalid double";
        }
            
        // Add parameters as request attributes
        // this creates attributes named "first" and "second for the response, and grabs the
        // values that were originally assigned to the request attributes, also named "first" and "second"
        // they don't have to be named the same, but in this case, since we are passing them back
        // and forth, it's a good idea
        // puts first and second back into the HTML file 
        
        if(req.getParameter("guess") == null) {
            req.setAttribute("first", req.getParameter("first"));
            req.setAttribute("second", req.getParameter("second"));
            req.setAttribute("third", req.getParameter("third"));
        
            // add result objects as attributes
            // this adds the errorMessage text and the result to the response
            req.setAttribute("errorMessage", errorMessage);
            req.setAttribute("result", result);
            
            // Forward to view to render the result HTML document
            req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
        }*/
    }
    
    /*
    // gets double from the request with attribute named s
    private Double getDoubleFromParameter(String s) {
        if (s == null || s.equals("")) {
            return null;
        } else {
            return Double.parseDouble(s);
        }
    }
    
    // gets an Integer from the Posted form data, for the given attribute name
        private int getInteger(HttpServletRequest req, String name) {
            return Integer.parseInt(req.getParameter(name));
        }*/
}


