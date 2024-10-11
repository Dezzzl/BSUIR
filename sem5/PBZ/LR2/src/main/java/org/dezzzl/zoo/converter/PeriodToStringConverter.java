package org.dezzzl.zoo.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Period;

@Converter(autoApply = true)
public class PeriodToStringConverter implements AttributeConverter<Period, String> {

    @Override
    public String convertToDatabaseColumn(Period period) {
        if (period == null) {
            return null;
        }
        return period.toString();
    }

    @Override
    public Period convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Period.parse(dbData);
    }
}

