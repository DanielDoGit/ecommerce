package ecommerce.uteis;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.EnumConverter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "customEnumConverter")
public class CustomEnumConverter extends EnumConverter {

	public CustomEnumConverter() {
		super(TipoPessoa.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return super.getAsObject(context, component, value);
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		return super.getAsString(context, component, value);
	}
	
}
