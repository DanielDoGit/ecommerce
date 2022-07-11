package controler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comum.Redirecionador;
import configuration.HibernateUtil;

@WebServlet(urlPatterns =  "/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public LoginAction() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if (request.getParameter("Campousuario") != null 
			&& request.getParameter("Password") != null) {
			
			Redirecionador.redirecionar(request, response, "Inicial.jsp");
		
			
		}else {
			throw new NullPointerException("O campo usuário ou senha esta nulo");
		}
	}

}
