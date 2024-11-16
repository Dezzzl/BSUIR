package org.dezzzl.zoo.dto.pet.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dezzzl.zoo.entity.pet.Sex;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PetReferencesReadDto {
    private Integer Id;
    private String name;
    private LocalDate birthdate;
    private Sex sex;
    private String petType;
    private String feedingRationName;
}
