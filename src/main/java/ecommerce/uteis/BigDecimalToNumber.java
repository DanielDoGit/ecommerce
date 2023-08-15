package ecommerce.uteis;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.convert.NumberConverter;

@FacesConverter(value = "bigDecimalToNumber")
public class BigDecimalToNumber extends NumberConverter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		setType("number");
		value = removerFormatacao(value);
		return super.getAsString(context, component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		setType("number");
		return super.getAsString(context, component, value);
	}

	private String removerFormatacao(String arg) {
		StringBuilder st = new StringBuilder();
		for (char c : arg.toCharArray()) {
			int i = (int) c;
			if (i >= 48 && i <= 57) {
				st.append(c);
			}else if (i == 44) {
				st.append(".");
			}
		}
		return st.toString();
	}
}
