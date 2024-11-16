package org.dezzzl.zoo.dto.pet.read;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WinteringPlaceReadDto {
    Integer id;
    String code;
    String country;
    LocalDate arrivalDate;
    LocalDate departureDate;
}
