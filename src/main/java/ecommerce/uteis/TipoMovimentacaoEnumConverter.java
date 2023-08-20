package ecommerce.uteis;

import ecommerce.beans.TipoMovimentacao;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.EnumConverter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "tipoMovimentacaoEnumConverter")
public class TipoMovimentacaoEnumConverter extends EnumConverter {
	
	public TipoMovimentacaoEnumConverter() {
		super(TipoMovimentacao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return super.getAsObject(context, component, value);
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return super.getAsString(context, component, value);
	}
	
}
