<%@page import="java.lang.ProcessBuilder.Redirect"%>
<%@page import="comum.Redirecionador"%>
<%@page import="controler.LoginAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page %>
    
<!DOCTYPE html>
<html>

<head>
<jsp:include page="/jsputeis/includecss.html"></jsp:include>
 <jsp:include page="/jsputeis/includejs.html"></jsp:include>
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
				<input type="text" id="form2Example1" class="form-control" name="Campousuario" required="required"/> <label
					class="form-label" for="form2Example1" >Usuário</label>
			</div>

			<!-- Password input -->
			<div class="form-outline mb-4">
				<input type="password" id="form2Example2" class="form-control" name="Password" required="required"/> <label
					class="form-label" for="form2Example2">Password</label>
			</div>

			<!-- Submit button -->
			<input type="submit" class="btn btn-primary btn-block mb-4" style='margin-left:auto;' name='Acesso' value='Acessar'/>
			<input type="reset" class="btn btn-primary btn-block mb-4 " style='margin-left:auto;' value='Limpar'>


		</div>

	</form>
	
	
		<div id="exampleModalLive" class="modal fade" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalLiveLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLiveLabel">Título do
							modal</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Fechar">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>O campo usuário e/ou campo senha estão em branco</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							data-dismiss="modal">OK</button>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			
		$(document).ready(function () {
			$('#exampleModalLive').modal('show');
		})	
</script>

</body>
</html>