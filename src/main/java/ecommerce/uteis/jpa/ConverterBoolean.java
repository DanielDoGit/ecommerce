package ecommerce.uteis.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ConverterBoolean implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		return attribute.equals(Boolean.TRUE) ? "T" : "F";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return dbData.equals("T") ? Boolean.TRUE : Boolean.FALSE;
	}

	
	
}
