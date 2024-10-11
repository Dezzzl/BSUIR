package org.dezzzl.zoo.dto.pet.editcreate;

import lombok.Data;
import org.dezzzl.zoo.entity.pet.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PetCreateEditDto {
    String name;

    Sex sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate;

    String petType;

    WinteringPlaceCreateEditDto winteringPlace = new WinteringPlaceCreateEditDto();

    ReptileCreateEditDto reptileCreateEditDto = new ReptileCreateEditDto();

    FeedingRationCreateEditDto feedingRationCreateEditDto = new FeedingRationCreateEditDto();

    Integer habitatZoneId;

    Integer zookeeperId;

    Integer veterinarianId;
}
