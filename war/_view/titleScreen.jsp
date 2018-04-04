<!-- User selects between New Game, Load Game, Edit Game -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Adventure - Sign In</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		.centered {
  			position: fixed;
  			top: 50%;
  			left: 50%;
  			/* bring your own prefixes */
  			transform: translate(-50%, -50%);
		}
		.success{
			color:green;
			}
		.user{
			color:black;
			}
		</style>
	</head>


	<body>
	<div class="user">Welcome, ${username}</div>
	
	<div class = "centered">
	<h3>make a selection</h3><h1> PLEASE</h1>
	<form action="${pageContext.servletContext.contextPath}/titleScreen" method="post">
		
    		<input type="submit" name="button" value="Load Game" />


    		<input type="submit" name="button" value="New Game" />
		

    		<input type="submit" name="button" value="Edit Game" />
	</form>
	</div>
	</body>
</html>