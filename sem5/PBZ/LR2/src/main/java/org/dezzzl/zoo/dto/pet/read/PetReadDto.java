package org.dezzzl.zoo.dto.pet.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dezzzl.zoo.dto.employee.EmployeeReferenceReadDto;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.entity.pet.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PetReadDto {
    Integer id;
    String name;

    Sex sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate;

    WinteringPlaceReadDto winteringPlaceReadDto;

    ReptileReadDto reptileReadDto;

    FeedingRationReadDto feedingRationReadDto;

    HabitatZoneReadDto habitatZoneReadDto;

    EmployeeReferenceReadDto zookeeperReadDto;

    EmployeeReferenceReadDto veterinarianReadDto;

    String petType;
}
