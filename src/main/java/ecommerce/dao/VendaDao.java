package ecommerce.dao;

import ecommerce.beans.Venda;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class VendaDao extends Dao<Venda> {

	private static final long serialVersionUID = 1L;
	
	public VendaDao() {
		super(Venda.class);
	}

}
