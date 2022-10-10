package modelo.dao;

import java.util.Properties;

import javax.mail.Session;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import modelo.beans.AjusteDeEstoque;
import modelo.beans.CategoriaFuncionario;
import modelo.beans.Cidade;
import modelo.beans.Cliente;
import modelo.beans.Fornecedor;
import modelo.beans.Funcionario;
import modelo.beans.GrupoProduto;
import modelo.beans.ItemVenda;
import modelo.beans.Parcelas;
import modelo.beans.Permissao;
import modelo.beans.PermissaoFuncionario;
import modelo.beans.Produto;
import modelo.beans.Recebimento;
import modelo.beans.Venda;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	static {
		Properties p = new Properties();
		p.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		p.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		p.setProperty("hibernate.connection.url", "jdbc:postresql://dev.limasoftware.com.br:5432/ecommerce");
		p.setProperty("hibernate.connection.username", "postgres");
		p.setProperty("hibernate.connection.password", "LSJ6PGFB2000");
		p.setProperty("hibernate.current_session_context_class", "thread");
		p.setProperty("hibernate.show_sql", "false");
		try {
			Configuration config = new Configuration();
			config.addProperties(p);
			config.addClass(Funcionario.class);
			config.addClass(AjusteDeEstoque.class);
			config.addClass(CategoriaFuncionario.class);
			config.addClass(Cidade.class);
			config.addClass(Cliente.class);
			config.addClass(Fornecedor.class);
			config.addClass(GrupoProduto.class);
			config.addClass(ItemVenda.class);
			config.addClass(Parcelas.class);
			config.addClass(Permissao.class);
			config.addClass(PermissaoFuncionario.class);
			config.addClass(Produto.class);
			config.addClass(Recebimento.class);
			config.addClass(Venda.class);
			sessionFactory = config.buildSessionFactory();
		} catch (MappingException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
