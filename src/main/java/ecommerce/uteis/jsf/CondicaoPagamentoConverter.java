package ecommerce.uteis.jsf;

import ecommerce.beans.CondicaoPagamento;
import ecommerce.dao.CondicaoPagamentoDao;
import ecommerce.dto.CondicaoPagamentoDto;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "condicaoPagamentoConverter")
public class CondicaoPagamentoConverter implements Converter<CondicaoPagamentoDto> {

	@Override
	public CondicaoPagamentoDto getAsObject(FacesContext context, UIComponent component, String value) {
		CondicaoPagamento c = InjectBean.newInstanceCDI(CondicaoPagamentoDao.class).buscarExatidao("descricao", value);
		return new CondicaoPagamentoDto(c);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, CondicaoPagamentoDto value) {
		return value.getDescricao();
	}

}
