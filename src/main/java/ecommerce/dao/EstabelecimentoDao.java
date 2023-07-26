package ecommerce.dao;

import ecommerce.beans.Estabelecimento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class EstabelecimentoDao extends Dao<Estabelecimento>{

	private static final long serialVersionUID = 1L;

	public EstabelecimentoDao() {
		super(Estabelecimento.class);
	}
	
	
}
