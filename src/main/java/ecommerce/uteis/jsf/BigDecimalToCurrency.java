package ecommerce.uteis.jsf;

import jakarta.faces.component.EditableValueHolder;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.convert.NumberConverter;

@FacesConverter(value = "bigDecimalToCurrency")
public class BigDecimalToCurrency extends NumberConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        value = removerFormatacao(value);
        if (value.isBlank() || value.isEmpty()) {
            if (component instanceof EditableValueHolder) {
                ((EditableValueHolder) component).setSubmittedValue(null);
                return null;
            }
        }
        setCurrencySymbol("R$");
        setType("currency");
        return super.getAsString(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        setCurrencySymbol("R$");
        setType("currency");
        return super.getAsString(context, component, value);
    }

    private String removerFormatacao(String arg) {
        StringBuilder st = new StringBuilder();
        for (char c : arg.toCharArray()) {
            int i = (int) c;
            if (i >= 48 && i <= 57) {
                st.append(c);
            } else if (i == 44) {
                st.append(".");
            }
        }
        return st.toString();
    }
}
