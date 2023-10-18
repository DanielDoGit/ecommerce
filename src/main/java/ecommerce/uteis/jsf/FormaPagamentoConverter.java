package ecommerce.uteis.jsf;

import ecommerce.beans.FormaPagamento;
import ecommerce.dao.FormaPagamentoDao;
import ecommerce.dto.FormaPagamentoDto;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "formaPagamentoConverter")
public class FormaPagamentoConverter implements Converter<FormaPagamentoDto> {

	@Override
	public FormaPagamentoDto getAsObject(FacesContext context, UIComponent component, String value) {
		 FormaPagamento c = InjectBean.newInstanceCDI(FormaPagamentoDao.class).buscarExatidao("descricao", value);
		return new FormaPagamentoDto(c);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, FormaPagamentoDto value) {
		return value.getDescricao();
	}

}
