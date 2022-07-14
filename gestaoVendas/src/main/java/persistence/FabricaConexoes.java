package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import comum.EjetaException;

public class FabricaConexoes {
	
	private static Connection connection;
	private static final String URL = "jdbc:postresql://dev.limasoftware.com.br:5432/";
	private static final String BANCO = "ecommerce";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "LSJ6PGFB2000";
	
	static {
		try {
			connection = DriverManager.getConnection(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new EjetaException(e);
		}
		
		
	}

}
