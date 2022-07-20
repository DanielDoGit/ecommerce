<%@page import="org.w3c.dom.html.HTMLFormElement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsputeis/includecss.html"></jsp:include>
 <jsp:include page="/jsputeis/includejs.html"></jsp:include>
 <link rel="stylesheet" href="css/cssInicial.css">
<meta charset="ISO-8859-1">
<title>Welcome to Ebusines</title>
</head>
<body>

 <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="/gestaoVendas/Inicial.jsp">E-Commerce</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-5 mb-lg-0">
       
       <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle"  id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Cadastros
          </a>
          <ul class="dropdown-menu " aria-labelledby="navbarDropdown">
            
            				<li><a class="dropdown-item" href="#">Estabelecimento</a></li>

							<li><hr class="dropdown-divider"></li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Cliente</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
								
							</li>
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Fornecedor</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Funcionario</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Cidade</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
							<li><hr class="dropdown-divider"></li>

							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Formas de Pagamento</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
							
							<li><hr class="dropdown-divider"></li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Produto</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Grupo</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
          				  
            
            
          </ul>
       
         <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle"  id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Processos
          </a>
          <ul class="dropdown-menu " aria-labelledby="navbarDropdown">
            
            				<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Venda</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Excluir</a></li>
										
									</ul>
								
							</li>
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Compra</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Excluir</a></li>
										
									</ul>
								
							</li>
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Orçamento</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Excluir</a></li>
										
									</ul>
								
							</li>

							<li><hr class="dropdown-divider"></li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Ajuste de Estoque</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Exluir</a></li>
										
									</ul>
								
							</li>
							
							<li><hr class="dropdown-divider"></li>
							
							<li><a class="dropdown-item" href="#">Caixa</a></li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Contas a Pagar</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										
									</ul>
							</li>
							
							<li><a class="dropdown-item" href="#">Ficha do cliente</a></li>

							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Recebimento</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
									</ul>
							</li>
							
							<li><hr class="dropdown-divider"></li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Orçamento de Serviço</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
									</ul>
							</li>
							
							<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="#">Ordem de Serviço</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Cadastrar</a></li>
										<li><a class="dropdown-item" href="#">Pesquisar</a></li>
										<li><a class="dropdown-item" href="#">Refatorar</a></li>
									</ul>
							</li>
          				  
            
            
          </ul>
          
          <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle"  id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Relatórios
          </a>
          <ul class="dropdown-menu " aria-labelledby="navbarDropdown">
            	<li class="dropdown-submenu" >
									<a class="dropdown-item dropdown-toggle" href="">Cliente</a>
									<ul class="dropdown-menu">
										<li><a class="dropdown-item" href="#">Data Nascimento</a></li>
										<li><a class="dropdown-item" href="#">Data de Cadastro</a></li>
										<li><a class="dropdown-item" href="#">Todos</a></li>
										
									</ul>
								
							</li>
          </ul>
       
      </ul>
      
				<form class="d-flex ms-auto" style='position:relative; margin-left:760px;'>
					<div class="input-group">
						<input class="form-control border-0 mr-2" type="search"
							placeholder="Pesquisar" aria-label="Pesquisar" >
						<button class="btn btn-outline-success border-0" type="submit" >Pesquisar</button>
					</div>
				</form>
			</div>
		
  </div>
</nav>



</body>


</html>