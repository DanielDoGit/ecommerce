package ecommerce.uteis.interseptor;

import java.io.Serializable;
import java.util.List;

import ecommerce.beans.Produto;
import ecommerce.controller.ItemVendaController;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.ProdutoDao;
import ecommerce.dto.ItemVendaDto;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.AroundTimeout;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
public class ItemVendaInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ItemVendaController itemVendaController;
	
	@Inject
	private ProdutoDao produtoDao;
	
	@Inject
	private EstoqueTransienteDao estoqueDao;
	
	@AroundInvoke
	public Object limparEstoque(InvocationContext context) throws Exception {
		List<ItemVendaDto> lista = itemVendaController.getListItemsVenda();
		for (ItemVendaDto i : lista) {
			Produto p = produtoDao.getById(i.getIdProduto());
			estoqueDao.processarRemocaoEstoqueTransiente(p, i.getQuantidade());
		}
		return context.proceed();
	}
	
	@AroundTimeout
	public Object limparEstoqueTempoLimite(InvocationContext context) throws Exception {
		return limparEstoque(context);
	}
	
}
