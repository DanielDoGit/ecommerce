package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Cidade;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Query;

@Named
@RequestScoped
public class CidadeDao extends Dao<Cidade> {

	private static final long serialVersionUID = 1L;

	public CidadeDao() {
		super(Cidade.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cidade> consultarCidadeNome(String arg){
		String sql = "select * from cidade where nome ilike ?";
		Query q = em.createNativeQuery(sql, Cidade.class);
		q.setParameter(1, "%"+arg+"%");
		return q.getResultList();
	}

	
}
