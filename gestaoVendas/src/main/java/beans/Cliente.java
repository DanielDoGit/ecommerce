package beans;

public abstract class Cliente {
	
	protected Integer idCliente;
	protected String celular, telefone, endereco, numeroendereco, bairro;
	protected String contato, observacoes, cep;
	protected Cidade cidade;
	protected char ativo;
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public char getAtivo() {
		return ativo;
	}
	public void setAtivo(char ativo) {
		this.ativo = ativo;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumeroendereco() {
		return numeroendereco;
	}
	public void setNumeroendereco(String numeroendereco) {
		this.numeroendereco = numeroendereco;
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
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Cliente(Integer idCliente, String celular, String telefone, String endereco, String numeroendereco,
			String bairro, String contato, String observacoes, String cep, Cidade cidade, char ativo) {
		super();
		this.idCliente = idCliente;
		this.celular = celular;
		this.telefone = telefone;
		this.endereco = endereco;
		this.numeroendereco = numeroendereco;
		this.bairro = bairro;
		this.contato = contato;
		this.observacoes = observacoes;
		this.cep = cep;
		this.cidade = cidade;
		this.ativo = ativo;
	}
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
