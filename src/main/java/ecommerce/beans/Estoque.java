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
public class Estoque implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	private BigDecimal quantidade;

	private String tipo;

	private String descricao;

	private String origemEnvento;

	@ManyToOne
	@JoinColumn(name = "produto")
	private Produto produto;

	public Estoque() {
		super();
	}

	public Estoque(Integer codigo, BigDecimal quantidade, String tipo, String descricao, String origemEnvento,
			Produto produto) {
		super();
		this.codigo = codigo;
		this.quantidade = quantidade;
		this.tipo = tipo;
		this.descricao = descricao;
		this.origemEnvento = origemEnvento;
		this.produto = produto;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getOrigemEnvento() {
		return origemEnvento;
	}

	public void setOrigemEnvento(String origemEnvento) {
		this.origemEnvento = origemEnvento;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
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
		Estoque other = (Estoque) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
