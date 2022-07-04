<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsputeis/includecss.html"></jsp:include>
<meta charset="UTF-8">
<title>Bem-vindo ao Login</title>
</head>
<body>

<form>
  <!-- Email input --><br><br>
  <div style='margin-left: 400px; margin-right: 400px; position: "absolute";'>
			<div class="form-outline mb-4">
				<input type="email" id="form2Example1" class="form-control" /> <label
					class="form-label" for="form2Example1">Usuário</label>
			</div>
			
			

			<!-- Password input -->
			<div class="form-outline mb-4">
				<input type="password" id="form2Example2" class="form-control" /> <label
					class="form-label" for="form2Example2">Password</label>
			</div>


			<!-- Submit button -->
			<button type="button" class="btn btn-primary btn-block mb-4">Sign
				in</button>

			<!-- Register buttons -->
			<div class="text-center">
				<p>
					Not a member? <a href="#!">Register</a>
				</p>
				<p>or sign up with:</p>
				<button type="button" class="btn btn-link btn-floating mx-1">
					<i class="fab fa-facebook-f"></i>
				</button>

				<button type="button" class="btn btn-link btn-floating mx-1">
					<i class="fab fa-google"></i>
				</button>

				<button type="button" class="btn btn-link btn-floating mx-1">
					<i class="fab fa-twitter"></i>
				</button>

				<button type="button" class="btn btn-link btn-floating mx-1">
					<i class="fab fa-github"></i>
				</button>
			</div>

		</div>
</form>
	
</body>
</html>