package br.com.ebusiness.beans;

public class Cidade {
	
	private Integer idCidade;
	private String nomeCidade;
	private char[] ufcidade;
	
	public Integer getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public char[] getUfcidade() {
		return ufcidade;
	}
	public void setUfcidade(char[] ufcidade) {
		this.ufcidade = ufcidade;
	}
	public Cidade(Integer idCidade, String nomeCidade, char[] ufcidade) {
		super();
		this.idCidade = idCidade;
		this.nomeCidade = nomeCidade;
		this.ufcidade = ufcidade;
	}
	public Cidade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
