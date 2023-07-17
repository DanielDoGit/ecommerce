package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recebimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	private BigDecimal valor;
	
	private LocalDate dataEmissao;
	
	private LocalDate dataVencimento;
	
	private boolean quitado;
	
	@ManyToOne
	@JoinColumn(name="venda")
	private Venda venda;
	
	@OneToMany(mappedBy = "recebimento", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Parcela> listaParcelas = new ArrayList<>();

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public boolean isQuitado() {
		return quitado;
	}

	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Parcela> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<Parcela> listaParcelas) {
		this.listaParcelas = listaParcelas;
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
		Recebimento other = (Recebimento) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
}
