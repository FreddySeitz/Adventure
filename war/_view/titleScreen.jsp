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
		<!--<form action="${pageContext.servletContext.contextPath}/loadGame" method="get">-->
    		<input type="submit" name="button" value="Load Game" />
		<!--</form>-->
		<!--<form action="${pageContext.servletContext.contextPath}/game" method="get">-->
    		<input type="submit" name="button" value="New Game" />
		<!--</form>-->
		<!--<form action="${pageContext.servletContext.contextPath}/editGame" method="get">-->
    		<input type="submit" name="button" value="Edit Game" />
    	<!--</form>-->
	</form>
	</div>
	</body>
</html>