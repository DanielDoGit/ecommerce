package beans;

public class Cidade {
	
	private Integer idCidade;
	private String nomeCidade;
	private String ufCidade;
	
	public Integer getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Integer id) {
		this.idCidade = id;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public String getUfCidade() {
		return this.ufCidade;
	}
	public void setUfCidade(String ufcidade) {
		this.ufCidade = ufcidade;
	}
	public Cidade(Integer idCidade, String nomeCidade,String ufcidade) {
		super();
		this.idCidade = idCidade;
		this.nomeCidade = nomeCidade;
		this.ufCidade = ufcidade;
	}
	public Cidade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
