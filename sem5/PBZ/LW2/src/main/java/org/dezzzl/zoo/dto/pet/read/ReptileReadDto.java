package org.dezzzl.zoo.dto.pet.read;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReptileReadDto {
    BigDecimal normalTemperature;
    String sleepPeriod;
}
