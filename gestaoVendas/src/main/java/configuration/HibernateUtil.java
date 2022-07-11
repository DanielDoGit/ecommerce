package configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import beans.Cidade;
import comum.EjetaException;


public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	static {
		try {
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		config.setProperty("hibernate.connection.url", "jdbc:postgresql://dev.limasoftware.com.br:5432/ecommerce");
		config.setProperty("hibernate.connection.username", "postgres");
		config.setProperty("hibernate.connection.password", "LSJ6PGFB2000");
		config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		config.setProperty("hibernate.current_session_context_class", "thread");
		config.setProperty("hibernate.show_sql", "true");
		config.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		config.addClass(Cidade.class);
		
			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new EjetaException(e);
		}
		
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	

}
