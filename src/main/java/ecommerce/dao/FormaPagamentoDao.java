package ecommerce.dao;

import ecommerce.beans.FormaPagamento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class FormaPagamentoDao extends Dao<FormaPagamento>{

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoDao() {
		super(FormaPagamento.class);
	}

}
