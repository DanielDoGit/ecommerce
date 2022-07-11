<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page %>
    
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsputeis/includecss.html"></jsp:include>
<meta charset="UTF-8">
<title>Bem-vindo ao Ecommerce</title>
</head>
<body>

 	<form  action="/gestaoVendas/LoginAction" method="post">
 

		<div style='margin-top: 90px; font-size: 20px;'>
			<p class="text-center">Bem-vindo(a) ao login!</p>
		</div>

		<div style='margin-left: 400px; margin-right: 400px; position: "relative"; margin-top:90px;'>
			
		
			<div class="form-outline mb-4">
				<input type="text" id="form2Example1" class="form-control" name="Campousuario"/> <label
					class="form-label" for="form2Example1" >Usuário</label>
			</div>

			<!-- Password input -->
			<div class="form-outline mb-4">
				<input type="password" id="form2Example2" class="form-control" name="Password"/> <label
					class="form-label" for="form2Example2">Password</label>
			</div>

			<!-- Submit button -->
			<input type="submit" class="btn btn-primary btn-block mb-4" style='margin-left:auto;' name='Acesso' value='Acessar'/>
			<input type="reset" class="btn btn-primary btn-block mb-4 " style='margin-left:auto;' value='Limpar'>

			

		</div>
</form>
	
</body>
</html>