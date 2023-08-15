package ecommerce.dao;

import ecommerce.beans.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ProdutoDao extends Dao<Produto> {

	private static final long serialVersionUID = 1L;
	
	public ProdutoDao() {
		super(Produto.class);
	}
	
}
