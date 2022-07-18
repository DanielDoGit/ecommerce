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
		
		String usuario = request.getParameter("Campousuario");
		String senha = request.getParameter("Password");
		
		if (usuario != null && senha != null) {
			
			usuario.replaceAll("", "");
			senha.replaceAll("", "");
			if(!usuario.isEmpty() && !senha.isEmpty()) {
				Redirecionador.redirecionar(request, response, "Inicial.jsp");
			}else {
				
			}

		}else {
			throw new NullPointerException("O campo usuário ou senha esta nulo");
		}
	}

}
