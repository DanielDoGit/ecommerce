package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AjusteEstoque implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	private LocalDate dataAjuste;
	
	private String motivo;
	
	@ManyToOne
	@JoinColumn(name="produto")
	private Produto produto;
	
	private BigDecimal quantidade;
	
	private String tipo;
	
	public AjusteEstoque(Integer codigo, LocalDate dataAjuste, String motivo, Produto produto, BigDecimal quantidade,
			String tipo) {
		super();
		this.codigo = codigo;
		this.dataAjuste = dataAjuste;
		this.motivo = motivo;
		this.produto = produto;
		this.quantidade = quantidade;
		this.tipo = tipo;
	}

	public AjusteEstoque() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public LocalDate getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(LocalDate dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		AjusteEstoque other = (AjusteEstoque) obj;
		return Objects.equals(codigo, other.codigo);
	} 
	

}
