package beans;

public class Funcionario {
	
	private Integer idFuncionario;
	private String nomeFuncionario;
	private boolean tempermissao;
	private ControlePermissoes controlePermissoes;
	private CategoriaFuncionario categoriaFuncionario;
	
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public boolean isTempermissao() {
		return tempermissao;
	}
	public void setTempermissao(boolean tempermissao) {
		this.tempermissao = tempermissao;
	}
	public ControlePermissoes getControlePermissoes() {
		return controlePermissoes;
	}
	public void setControlePermissoes(ControlePermissoes controlePermissoes) {
		this.controlePermissoes = controlePermissoes;
	}
	public CategoriaFuncionario getCategoriaFuncionario() {
		return categoriaFuncionario;
	}
	public void setCategoriaFuncionario(CategoriaFuncionario categoriaFuncionario) {
		this.categoriaFuncionario = categoriaFuncionario;
	} 
	
	
	

}
