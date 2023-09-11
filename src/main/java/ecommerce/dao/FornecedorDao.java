package ecommerce.dao;

import ecommerce.beans.Fornecedor;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class FornecedorDao extends Dao<Fornecedor> {

	private static final long serialVersionUID = 1L;
	
	public FornecedorDao() {
		super(Fornecedor.class);
	}
	
	
	

}
