package ecommerce.dao;

import ecommerce.beans.AjusteEstoque;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class AjusteEstoqueDao extends Dao<AjusteEstoque>{

	private static final long serialVersionUID = 1L;

	public AjusteEstoqueDao() {
		super(AjusteEstoque.class);
	}
	
}
