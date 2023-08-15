package ecommerce.uteis;

import ecommerce.beans.TipoPessoa;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.EnumConverter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "tipoPessoaEnumConverter")
public class TipoPessoaEnumConverter extends EnumConverter {
	
	public TipoPessoaEnumConverter() {
		super(TipoPessoa.class);
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
