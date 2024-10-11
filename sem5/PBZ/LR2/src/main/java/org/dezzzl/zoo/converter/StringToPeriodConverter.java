package org.dezzzl.zoo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringToPeriodConverter implements Converter<String, Period> {

    private static final Pattern PERIOD_PATTERN = Pattern.compile(
            "(\\d+)\\s*months?\\s*(\\d+)?\\s*days?"
    );

    @Override
    public Period convert(String source) {
        Matcher matcher = PERIOD_PATTERN.matcher(source.trim());

        if (matcher.matches()) {
            int months = Integer.parseInt(matcher.group(1));
            int days = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;  // Получаем количество дней, если они есть

            return Period.ofMonths(months).plusDays(days);
        } else {
            throw new IllegalArgumentException("Invalid format for period: " + source);
        }
    }
}
