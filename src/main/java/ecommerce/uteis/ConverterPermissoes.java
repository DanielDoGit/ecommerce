package ecommerce.uteis;

import java.io.Serializable;

import ecommerce.beans.Permissao;
import ecommerce.dao.PermissaoDao;
import ecommerce.dto.PermissaoDto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@FacesConverter("converterPermissoesToString")
@RequestScoped
@Named
public class ConverterPermissoes implements Converter<PermissaoDto>, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Instance<PermissaoDao> permissaoDao;
	
	@Override
	public PermissaoDto getAsObject(FacesContext context, UIComponent component, String value) {
		Permissao p = permissaoDao.get().buscarExatidao("descricao", value);
		return new PermissaoDto(p);
	}	

	@Override
	public String getAsString(FacesContext context, UIComponent component, PermissaoDto value) {
		return value.getNomePermissao();
	}

	public ConverterPermissoes(){}
	
	

}
