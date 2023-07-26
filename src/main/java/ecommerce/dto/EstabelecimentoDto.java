package ecommerce.dto;

import java.io.Serializable;

import ecommerce.beans.Estabelecimento;
import ecommerce.dao.CidadeDao;

public class EstabelecimentoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String nome;

	private String contato;

	private String endereco;

	private String bairro;

	private String cnpj;

	private String idCidade;

	private String nomeCidade;

	public EstabelecimentoDto(Estabelecimento e) {
		this.codigo = e.getCodigo();
		this.nome = e.getNome();
		this.bairro = e.getBairro();
		this.cnpj = e.getCnpj();
		this.contato = e.getContato();
		this.endereco = e.getEndereco();
		this.idCidade = Integer.toString(e.getCidade().getCodigo());
		this.nomeCidade = e.getCidade().getNome();
	}
	
	public Estabelecimento toEstabelecimento(CidadeDao cidadeDao) {
		Estabelecimento e = new Estabelecimento();
		e.setCodigo(codigo);
		e.setBairro(bairro);
		e.setCidade(cidadeDao.getById(Integer.valueOf(idCidade)));
		e.setCnpj(cnpj);
		e.setContato(contato);
		e.setEndereco(endereco);
		e.setNome(nome);
		return e;
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

}
