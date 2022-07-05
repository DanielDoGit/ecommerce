package beans;

public class ControlePermissoes {
	
	private Integer idpermissao;
	private String nomePermissao;
	
	public Integer getIdpermissao() {
		return idpermissao;
	}
	public void setIdpermissao(Integer idpermissao) {
		this.idpermissao = idpermissao;
	}
	public String getNomePermissao() {
		return nomePermissao;
	}
	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}
	public ControlePermissoes(Integer idpermissao, String nomePermissao) {
		super();
		this.idpermissao = idpermissao;
		this.nomePermissao = nomePermissao;
	}
	public ControlePermissoes() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
