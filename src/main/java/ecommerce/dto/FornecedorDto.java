package ecommerce.dto;

import java.io.Serializable;
import java.util.Objects;

import ecommerce.beans.Fornecedor;
import ecommerce.dao.CidadeDao;
import ecommerce.beans.TipoPessoa;

public class FornecedorDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String nome;

	private String cpfCnpj;

	private String bairro;

	private String endereco;

	private String contato;

	private String idCidade;

	private String nomeCidade;

	private boolean ativo;

	private TipoPessoa pessoaFisicaJuridica;

	public FornecedorDto() {}
	
	public FornecedorDto(Fornecedor fornecedor) {
		this.codigo = fornecedor.getCodigo();
		this.ativo = fornecedor.isAtivo();
		this.bairro = fornecedor.getBairro();
		this.contato = fornecedor.getContato();
		this.cpfCnpj = fornecedor.getCpfCnpj();
		this.endereco = fornecedor.getEndereco();
		this.idCidade = String.valueOf(fornecedor.getCidade().getCodigo());
		this.nomeCidade = fornecedor.getCidade().getNome();
		this.nome = fornecedor.getNome();
		this.pessoaFisicaJuridica = fornecedor.getFiscajuridica();
	}

	public Fornecedor toFornecedor(CidadeDao cidadeDao) {
		return new Fornecedor(codigo, nome, cpfCnpj, bairro, endereco, contato,
				cidadeDao.getById(Integer.valueOf(idCidade)), ativo, pessoaFisicaJuridica);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(String idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public TipoPessoa getPessoaFisicaJuridica() {
		return pessoaFisicaJuridica;
	}

	public void setPessoaFisicaJuridica(TipoPessoa pessoaFisicaJuridica) {
		this.pessoaFisicaJuridica = pessoaFisicaJuridica;
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
		FornecedorDto other = (FornecedorDto) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
