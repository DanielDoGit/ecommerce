package br.com.ebusiness.beans;

public class ClienteJuridico extends Cliente {

	private String nomeFantasia;
	private String razaoSocial;
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public ClienteJuridico(Integer idCliente, String celular, String telefone, String endereco, String numeroendereco,
			String bairro, String contato, String observacoes, String cep, Cidade cidade, char ativo,
			String nomeFantasia, String razaoSocial) {
		super(idCliente, celular, telefone, endereco, numeroendereco, bairro, contato, observacoes, cep, cidade, ativo);
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
	}

	public ClienteJuridico() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
