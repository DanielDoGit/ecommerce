package modelo.beans;

import java.util.List;

public class Recebimento {
	
	private Integer id;
	private Venda venda;
	private List<Parcelas> listaParcelas;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Venda getVenda() {
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public List<Parcelas> getListaParcelas() {
		return listaParcelas;
	}
	public void setListaParcelas(List<Parcelas> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}
	
}
