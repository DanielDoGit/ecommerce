package ecommerce.dao;

import ecommerce.beans.Caixa;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CaixaDao extends Dao<Caixa>{

	private static final long serialVersionUID = 1L;
	
	public CaixaDao() {
		super(Caixa.class);
	}
	
}
