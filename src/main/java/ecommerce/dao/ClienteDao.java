package ecommerce.dao;

import ecommerce.beans.Cliente;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class ClienteDao extends Dao<Cliente> {

	private static final long serialVersionUID = 1L;

	public ClienteDao() {
		super(Cliente.class);
	}

}
