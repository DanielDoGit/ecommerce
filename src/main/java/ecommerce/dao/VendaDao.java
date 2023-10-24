package ecommerce.dao;

import ecommerce.beans.Venda;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Query;

@RequestScoped
@Named
public class VendaDao extends Dao<Venda> {

	private static final long serialVersionUID = 1L;
	
	public VendaDao() {
		super(Venda.class);
	}
	
	public Integer getLastCodigoVendaByCliente(String codigo) {
		String sql = "select codigo from venda where codigo = (select max(codigo) from venda where cliente = ?)";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1,Integer.valueOf(codigo));
		return (Integer) q.getSingleResult();
	}

}
