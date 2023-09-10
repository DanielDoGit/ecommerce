package ecommerce.beans;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FormaPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	private String descricao;

	@Convert(converter = TrueFalseConverter.class)
	private boolean aparecerCaixa;

	@Convert(converter = TrueFalseConverter.class)
	private boolean compensado;

	public FormaPagamento(Integer codigo, String descricao, boolean aparecerCaixa, boolean compensado) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.aparecerCaixa = aparecerCaixa;
		this.compensado = compensado;
	}

	public FormaPagamento() {
		super();
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

	public boolean isAparecerCaixa() {
		return aparecerCaixa;
	}

	public void setAparecerCaixa(boolean aparecerCaixa) {
		this.aparecerCaixa = aparecerCaixa;
	}

	public boolean isCompensado() {
		return compensado;
	}

	public void setCompensado(boolean compensado) {
		this.compensado = compensado;
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
		FormaPagamento other = (FormaPagamento) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
