package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import ecommerce.beans.Cliente;
import ecommerce.dao.CidadeDao;

public class ClienteDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String nome;

	private String cpf;

	private String endereco;

	private String bairro;

	private String contato;

	private String idCidade;

	private String nomeCidade;

	private BigDecimal credito;

	private boolean ativo;

	private LocalDate dataCadastro;

	public ClienteDto(Cliente cli) {
		this.codigo = cli.getCodigo();
		this.nome = cli.getNome();
		this.cpf = cli.getCpf();
		this.endereco = cli.getEndereco();
		this.bairro = cli.getBairro();
		this.contato = cli.getContato();
		this.idCidade = cli.getCidade().getCodigo().toString();
		this.nomeCidade = cli.getCidade().getNome();
		this.credito = cli.getCredito();
		this.ativo = cli.isAtivo();
		this.dataCadastro = cli.getDataCadastro();
	}

	public Cliente toCliente(CidadeDao cidadeDao) {
		return new Cliente(codigo, nome, cpf, endereco, bairro, contato, cidadeDao.getById(Integer.valueOf(idCidade)),
				credito, ativo, dataCadastro);
	}

	public ClienteDto() {
		super();
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public BigDecimal getCredito() {
		return credito;
	}

	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
