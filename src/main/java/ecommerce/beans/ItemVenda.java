package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemVenda implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@ManyToOne
	@JoinColumn(name="produto")
	private Produto produto;
	
	private BigDecimal quantidade;
	
	private BigDecimal valorUnitario;
	
	private BigDecimal totalUnitario;
	
	public ItemVenda(Integer codigo, Produto produto, BigDecimal quantidade, BigDecimal valorUnitario,
			BigDecimal totalUnitario) {
		super();
		this.codigo = codigo;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.totalUnitario = totalUnitario;
	}
	
	public ItemVenda() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		ItemVenda other = (ItemVenda) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
}
