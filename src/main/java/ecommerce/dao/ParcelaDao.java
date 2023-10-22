package ecommerce.dao;

import ecommerce.beans.Parcela;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ParcelaDao extends Dao<Parcela>{

	private static final long serialVersionUID = 1L;
	
	public ParcelaDao() {
		super(Parcela.class);
	}
	
}
