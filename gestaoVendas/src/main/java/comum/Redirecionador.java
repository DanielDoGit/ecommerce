package comum;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Redirecionador  {

  
	public static void redirecionar(HttpServletRequest request, HttpServletResponse response, String nomePagina) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(nomePagina);
		dispatcher.forward(request, response);
		
	}


}
