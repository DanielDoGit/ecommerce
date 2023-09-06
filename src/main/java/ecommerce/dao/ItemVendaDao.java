package ecommerce.dao;

import java.util.List;

import ecommerce.beans.ItemVenda;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class ItemVendaDao extends Dao<ItemVenda>{

	private static final long serialVersionUID = 1L;

	public ItemVendaDao() {
		super(ItemVenda.class);
	}
	
	public void cadastrar(List<ItemVenda> lista) {
		lista.forEach(i -> super.cadastrar(i));
	}
}
