package beans;

import java.math.BigDecimal;

public class Run {

	
	public static void main(String[] args) {
		
		
		GrupoProduto grupoProduto = new GrupoProduto();
		grupoProduto.setIdProduto(1);
		grupoProduto.setNomeProduto("Gerais");
		
		Produto produto = new Produto();
		produto.setIdProduto(1);
		produto.setDescricao("Teste");
		produto.setPrecoVenda(new BigDecimal(45.00));
		produto.setQtdadeAtual(new BigDecimal(45));
		try {
			produto.subtrairQTDEstoque("46");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(produto.getQtdadeAtual());
		
	}
}
