package modelo.beans;

import java.util.List;

public class Funcionario {
	
	private Integer idFuncionario;
	private String nomeFuncionario;
	private CategoriaFuncionario categoriaFuncionario;
	private Cidade cidade;
	private List<PermissaoFuncionario> listaPermissao;
	
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
	public CategoriaFuncionario getCategoriaFuncionario() {
		return categoriaFuncionario;
	}
	public void setCategoriaFuncionario(CategoriaFuncionario categoriaFuncionario) {
		this.categoriaFuncionario = categoriaFuncionario;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<PermissaoFuncionario> getListaPermissao() {
		return listaPermissao;
	}
	public void setListaPermissao(List<PermissaoFuncionario> listaPermissao) {
		this.listaPermissao = listaPermissao;
	}
	
}
