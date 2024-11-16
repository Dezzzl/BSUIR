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

    public static String convert(Period period) {
        int months = period.getMonths();
        int days = period.getDays();

        StringBuilder result = new StringBuilder();
        if (months > 0) {
            result.append(months).append(" month").append(months > 1 ? "s" : "").append(" ");
        }
        if (days > 0) {
            result.append(days).append(" day").append(days > 1 ? "s" : "");
        }

        return result.toString().trim();
    }
}

