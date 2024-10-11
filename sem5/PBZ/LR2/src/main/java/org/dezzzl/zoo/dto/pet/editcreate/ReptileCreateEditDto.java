package org.dezzzl.zoo.dto.pet.editcreate;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;

@Data
public class ReptileCreateEditDto {
     BigDecimal normalTemperature;
     String sleepPeriod;
}
