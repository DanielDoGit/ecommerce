package ecommerce.uteis;

import java.io.Serializable;

import ecommerce.beans.Permissao;
import ecommerce.dao.PermissaoDao;
import ecommerce.dto.PermissaoDto;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("converterPermissoesToString")
public class ConverterPermissoes implements Converter<PermissaoDto>, Serializable{
	
	private static final long serialVersionUID = 1L;

	@Override
	public PermissaoDto getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			PermissaoDao pdao = (PermissaoDao) InjectBean.newInstanceCDI(PermissaoDao.class);
			Permissao p = pdao.buscarExatidao("descricao", value);
			return new PermissaoDto(p);
		} catch (Exception e) {
			throw new ConverterException(e.getMessage());
		}
		
	}	

	@Override
	public String getAsString(FacesContext context, UIComponent component, PermissaoDto value) {
		return value.getNomePermissao();
	}

	public ConverterPermissoes(){}
	
	

}
