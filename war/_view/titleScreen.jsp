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
		</style>
	</head>


	<body>
		<form action="load.html">
    		<input type="submit" value="Load Game" />
		</form>

		<form action="new.html">
    		<input type="submit" value="New Game" />
		</form>
		
		<form action="edit.html">
    		<input type="submit" value="Edit Game" />
		</form>
	</body>
</html>