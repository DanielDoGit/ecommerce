package modelo.beans;

import java.math.BigDecimal;

public class Produto {

	private Integer id;
	private String descricao;
	private BigDecimal estoque;
	private BigDecimal valorcusto;
	private BigDecimal valorvenda;
	private GrupoProduto grupo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getEstoque() {
		return estoque;
	}
	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}
	public BigDecimal getValorcusto() {
		return valorcusto;
	}
	public void setValorcusto(BigDecimal valorcusto) {
		this.valorcusto = valorcusto;
	}
	public BigDecimal getValorvenda() {
		return valorvenda;
	}
	public void setValorvenda(BigDecimal valorvenda) {
		this.valorvenda = valorvenda;
	}
	public GrupoProduto getGrupo() {
		return grupo;
	}
	public void setGrupo(GrupoProduto grupo) {
		this.grupo = grupo;
	}
	
	
	
}