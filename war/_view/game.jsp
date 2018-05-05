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
				text-align: center;
			}
			.input {
  				position: fixed;
  				top: 5px;
  				left: 5px;
			}
			.textbox{
				width: 500px;
				height: 400px;
   				border: 25px solid green;
    			padding: 25px;
    			margin: 25px;
    			overflow-x: visible;
    			overflow-y: scroll;
			}
			.success{
				color:green;
			}
			.response{
				color:black;
				position: fixed;
  				top: 10%;
  				left: 0%;
  				
			}			
		</style>
	</head>

	<body>
		<div class = "response">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>
			<c:if test="${! empty response}">
				<div class="response"><div class="textbox">${response}
				</div>
				</div>
			</c:if>
		</div>
		<div class = "input">
			<form action="${pageContext.servletContext.contextPath}/game" method="post">
				<table>
					<td class="label">Enter Command: </td>
					<td><input type="text" name="userInput" size="12" value="${userInput}" /></td>
				</table>
	
				<input type="Submit" name="Submit" value="Submit">
		
			
				</form>
			</form>
		</div>
	</body>
</html>