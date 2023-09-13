package ecommerce.dao;

import java.util.List;

import ecommerce.beans.CondicaoPagamento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CondicaoPagamentoDao extends Dao<CondicaoPagamento> {

	private static final long serialVersionUID = 1L;

	public CondicaoPagamentoDao() {
		super(CondicaoPagamento.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CondicaoPagamento> buscarTodos(){
		return em.createNativeQuery("select * from condicaopagamento order by descricao", CondicaoPagamento.class).getResultList();
	}
	
}
