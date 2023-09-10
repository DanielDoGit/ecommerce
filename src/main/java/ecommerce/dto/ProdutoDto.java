package ecommerce.dto;

import java.math.BigDecimal;

import ecommerce.beans.Produto;

public class ProdutoDto {

	private Integer codigo;
	private String descricao;
	private BigDecimal valorProduto;
	
	public ProdutoDto(Produto p) {
		this.codigo = p.getCodigo();
		this.descricao = p.getDescricao();
		this.valorProduto = p.getPrecoVenda();
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValorProduto() {
		return valorProduto;
	}
	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}
	
	
}
