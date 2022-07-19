package controler;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comum.Redirecionador;

@WebServlet(urlPatterns =  "/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public LoginAction() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String usuario = request.getParameter("Campousuario").replaceAll("\\s+", "");
		String senha =  request.getParameter("Password").replaceAll("\\s+", "");
	
		
		if ( (usuario != null && usuario.isEmpty()) && (senha != null && senha.isEmpty()) ){
		 
			
			Redirecionador.redirecionar(request, response, "loginPopup.jsp");
			
		}else {
			Redirecionador.redirecionar(request, response, "Inicial.jsp");
		}
			
	}
	
}
