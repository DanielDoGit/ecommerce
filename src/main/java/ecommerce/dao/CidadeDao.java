package ecommerce.dao;

import ecommerce.beans.Cidade;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CidadeDao extends Dao<Cidade> {

	private static final long serialVersionUID = 1L;

	public CidadeDao() {
		super(Cidade.class);
	}

	
}
