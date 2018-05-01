<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
	
	<head>
		<title>Play Adventure</title>
		
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
  				transform: translate(-50%, -50%);
			}
			.success{
				color:green;
			}
			.response{
				color:black;
			}			
		</style>
	</head>

	<body>
		<div class = "centered">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>
			<c:if test="${! empty response}">
				<div class="response">${response}</div>
			</c:if>
		
			<form action="${pageContext.servletContext.contextPath}/loadGame" method="post">
				<input type="Submit" name="viewGames" value="View Games">
			
				<table>
					<td class="label">Select Game: </td>
					<td><input type="text" name="userInput" size="12" value="${userInput}" /></td>
				</table>
				
				<input type="Submit" name="gameSelection" value="Load Game">

			</form>
		</div>
	</body>
</html>