package org.dezzzl.zoo.dto.pet.editcreate;

import lombok.Data;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class WinteringPlaceCreateEditDto {
    Integer id;
    String code;
    String country;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate arrivalDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate departureDate;
}
