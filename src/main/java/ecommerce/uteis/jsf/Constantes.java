package ecommerce.uteis.jsf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ecommerce.beans.AjusteEstoque;
import ecommerce.beans.ItemVenda;

public class Constantes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public final static List<String> origemEvento = Arrays.asList("Ajuste de estoque", "Venda", "Ordem de servico");

	public static String getDescricaoAjusteEstoqueExcluido(AjusteEstoque e) {
		StringBuilder st = new StringBuilder();
		st.append(origemEvento.get(0));
		st.append(": ");
		st.append(e.getCodigo());
		st.append(" - ");
		st.append("Produto: ");
		st.append(e.getProduto().getDescricao());
		st.append(" - ");
		st.append("Motivo: ");
		st.append(e.getMotivo());
		st.append(" excluido");
		return st.toString();
	}
	
	public static String getDescricaoAjusteEstoqueInclusao(AjusteEstoque e) {
		StringBuilder st = new StringBuilder();
		st.append(origemEvento.get(0));
		st.append(": ");
		st.append(e.getCodigo());
		st.append(" - ");
		st.append("Produto: ");
		st.append(e.getProduto().getDescricao());
		st.append(" - ");
		st.append("Motivo: ");
		st.append(e.getMotivo());
		return st.toString();
	}
	
	public static String getDescricaoProdutoVendaInclusao(ItemVenda e) {
		StringBuilder st = new StringBuilder();
		st.append(origemEvento.get(1));
		st.append(": ");
		st.append(e.getVenda().getCodigo());
		st.append(" - ");
		st.append("Produto: ");
		st.append(e.getProduto().getDescricao());
		return st.toString();
	}
	
	public static String getDescricaoProdutoVendaExclusão(ItemVenda e) {
		StringBuilder st = new StringBuilder();
		st.append(origemEvento.get(1));
		st.append(": ");
		st.append(e.getVenda().getCodigo());
		st.append(" - ");
		st.append("Produto: ");
		st.append(e.getProduto().getDescricao());
		st.append(" excluido");
		return st.toString();
	}

}
