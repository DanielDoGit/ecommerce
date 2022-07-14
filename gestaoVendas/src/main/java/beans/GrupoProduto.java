package beans;

public class GrupoProduto {
	
	private Integer idProduto;
	private String nomeProduto;
	
	public GrupoProduto(Integer idProduto, String nomeProduto) {
		super();
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
	}
	public GrupoProduto() {
		super();
		//TODO Auto-generated constructor stub
	}
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	

}
