<!DOCTYPE html>
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
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		
		<!-- TODO: INDEX SERVLET -->
		
		<form action="${pageContext.servletContext.contextPath}/index" method="post">
			<table>
				<tr>
					<td class="label">USERNAME: </td>
					<td><input type="text" name="username" size="12" value="${username}" /></td>
				</tr>
				<tr>
					<td class="label">PASSWORD: </td>
					<td><input type="text" name="password" size="12" value="${password}" /></td>
				</tr>
				
				
			</table>
			<input type="Submit" name="Sign In" value="signin">
			
			
			<!-- Guessing Game -->
			
			</form>
	</body>
</html>