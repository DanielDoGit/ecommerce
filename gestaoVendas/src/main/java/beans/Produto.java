package beans;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.swing.JOptionPane;

public class Produto {
	
	private Integer idProduto;
	private String descricao;
	private BigDecimal precoVenda, precoCusto, precoMinimo, valorMargem, qtdadeAtual, qtdadeMinima;
	private GrupoProduto grupoProduto;
	
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}
	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}
	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}
	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}
	public BigDecimal getPrecoMinimo() {
		return precoMinimo;
	}
	public void setPrecoMinimo(BigDecimal precoMinimo) {
		this.precoMinimo = precoMinimo;
	}
	public BigDecimal getValorMargem() {
		return valorMargem;
	}
	public void setValorMargem(BigDecimal valorMargem) {
		this.valorMargem = valorMargem;
	}
	public BigDecimal getQtdadeAtual() {
		return qtdadeAtual;
	}
	public void setQtdadeAtual(BigDecimal qtdadeAtual) {
		this.qtdadeAtual = qtdadeAtual;
	}
	public BigDecimal getQtdadeMinima() {
		return qtdadeMinima;
	}
	public void setQtdadeMinima(BigDecimal qtdadeMinima) {
		this.qtdadeMinima = qtdadeMinima;
	}
	public GrupoProduto getGrupoProduto() {
		return grupoProduto;
	}
	public void setGrupoProduto(GrupoProduto grupoProduto) {
		this.grupoProduto = grupoProduto;
	}
	
	public void subtrairQTDEstoque(String quantidadesubtraida ) throws Exception{
		
		BigDecimal resultado = qtdadeAtual.subtract(new BigDecimal(quantidadesubtraida));
		if (resultado.doubleValue() < 0) {
			throw new Exception("Não é permitido realizar subtração com o resultado inferior a zero");
		}else {
			qtdadeAtual = resultado;
		}
	}
	
	

}
