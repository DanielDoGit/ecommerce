package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import ecommerce.beans.ItemVenda;
import ecommerce.beans.Venda;
import ecommerce.dao.ProdutoDao;

public class ItemVendaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private Integer idProduto;
	
	private String nomeProduto;

	private BigDecimal quantidade;

	private BigDecimal valorUnitario;

	private BigDecimal totalUnitario;

	public ItemVendaDto (ProdutoDto produto) {
		this.idProduto = produto.getCodigo();
		this.nomeProduto = produto.getDescricao();
		this.quantidade = BigDecimal.ONE;
		this.valorUnitario = produto.getValorProduto();
		this.totalUnitario = produto.getValorProduto();
	}
	
	public ItemVendaDto() {
		this.idProduto = null;
		this.nomeProduto = null;
		this.quantidade = null;
		this.valorUnitario = null;
		this.totalUnitario = null;
	}
	
	public ItemVenda toItemVenda(ProdutoDao produtoDao, Venda venda) {
		return new ItemVenda(codigo, produtoDao.getById(idProduto), quantidade, valorUnitario, totalUnitario, venda);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getTotalUnitario() {
		return totalUnitario;
	}

	public void setTotalUnitario(BigDecimal totalUnitario) {
		this.totalUnitario = totalUnitario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVendaDto other = (ItemVendaDto) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
