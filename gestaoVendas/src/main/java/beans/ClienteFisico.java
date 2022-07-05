package beans;

public class ClienteFisico extends Cliente {

	private String nome, apelido;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public ClienteFisico(Integer idCliente, String celular, String telefone, String endereco, String numeroendereco,
			String bairro, String contato, String observacoes, String cep, Cidade cidade, char ativo, String nome,
			String apelido) {
		super(idCliente, celular, telefone, endereco, numeroendereco, bairro, contato, observacoes, cep, cidade, ativo);
		this.nome = nome;
		this.apelido = apelido;
	}

	public ClienteFisico() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
