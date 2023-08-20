package ecommerce.uteis;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.BooleanConverter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "booleanConverter")
public class CustomBooleanConverter extends BooleanConverter{

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Boolean a = Boolean.valueOf(super.getAsString(context, component, value));
		if (a) {
			return "Sim";
		}
		return "Não";
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if ("Sim".equals(value)) {
			value = Boolean.valueOf(true).toString();
		}else {
			value = Boolean.valueOf(false).toString();
		}
		return super.getAsObject(context, component, value);
	}
	
}
