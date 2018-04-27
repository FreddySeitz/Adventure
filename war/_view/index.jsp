<!DOCTYPE html>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> -->
<html>
	<head>
		<title>Adventure - Sign In</title>
		<style type="text/css">
		
		
		html { 
  		background: url(_view/osrsLogin2.png) no-repeat center center fixed; 
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
		}

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
		.create{
			    position: absolute;
    			left: 120px;
    			top: 175px;
    			padding:25px 45px;
		}

		.login{
				position: absolute;
			    right: 250px;
			    top: 175px;
       			padding:25px 45px;

		}
		</style>
	</head>
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

	<body>
	<div class="w3-container w3-center w3-animate-bottom">

		<div class = "centered">
			<h1>Adventure Sign In</h1>

		
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		<c:if test="${! empty successMessage}">
			<div class="success">${successMessage}</div>
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
					<td><input type="password" name="password" size="12" value="${password}" /></td>
				</tr>
				
				
			</table>

			<input class="login" type="Submit" name="signin" value="Sign In">
		
			
			</form>
			<form action="${pageContext.servletContext.contextPath}/createAccount" method="get">
						<input class="create" type="Submit" name="create" value="Create Account">
		
			</div>
			</div>
						</div>


	</body>
</html>