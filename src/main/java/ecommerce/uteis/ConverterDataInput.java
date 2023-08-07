package ecommerce.uteis;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "converterDataInput")
public class ConverterDataInput implements Converter<Object>, Serializable {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public LocalDate getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		try {
			return !arg2.trim().equals("") ? LocalDate.parse(arg2, formatador) : null;
		} catch (Exception pe) {
			throw new ConverterException(new FacesMessage("A data informada é inválida"));
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if (arg2 != null) {
			LocalDate a = (LocalDate) arg2;
			return a.format(formatador);
		} else {
			return "";
		}
	}

}
