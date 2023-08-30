package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estoque_transiente")
public class EstoqueTransiente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	@Column(name = "quantidade_disponivel")
	private BigDecimal quantidadeDisponivel;

	@Column(name = "quantidade_acesso")
	private Integer quantidadeAcesso;

	@ManyToOne
	@JoinColumn(name = "produto")
	private Produto produto;

	public EstoqueTransiente() {
		super();
	}
	
	public EstoqueTransiente(Integer codigo, BigDecimal quantidadeDisponivel, Integer quantidadeAcesso,
			Produto produto) {
		super();
		this.codigo = codigo;
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.quantidadeAcesso = quantidadeAcesso;
		this.produto = produto;
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
		EstoqueTransiente other = (EstoqueTransiente) obj;
		return Objects.equals(codigo, other.codigo);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public Integer getQuantidadeAcesso() {
		return quantidadeAcesso;
	}

	public void setQuantidadeAcesso(Integer quantidadeAcesso) {
		this.quantidadeAcesso = quantidadeAcesso;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}