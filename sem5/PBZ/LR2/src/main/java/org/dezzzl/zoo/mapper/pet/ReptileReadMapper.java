package org.dezzzl.zoo.mapper.pet;

import org.dezzzl.zoo.converter.PeriodToStringConverter;
import org.dezzzl.zoo.converter.StringToPeriodConverter;
import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.ReptileReadDto;
import org.dezzzl.zoo.dto.pet.read.WinteringPlaceReadDto;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.entity.pet.bird.Bird;
import org.dezzzl.zoo.entity.pet.reptile.Reptile;
import org.dezzzl.zoo.mapper.employee.EmployeeReferenceReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReptileReadMapper extends PetReadMapper {

    @Autowired
    public ReptileReadMapper(EmployeeReferenceReadMapper employeeReferenceReadMapper) {
        super(employeeReferenceReadMapper);
    }

    @Override
    public PetReadDto map(Pet object) {
        Reptile reptile = (Reptile) object;
        return new PetReadDto(
                reptile.getId(),
                reptile.getName(),
                reptile.getSex(),
                reptile.getBirthdate(),
                null,
                mapReptileToDto(reptile),
                mapFeedingRation(reptile),
                mapHabitatZone(reptile),
                mapEmployeeToDto(getEmployeeByType(reptile, EmployeeType.ZOOKEEPER)),
                mapEmployeeToDto(getEmployeeByType(reptile, EmployeeType.VETERINARIAN)),
                "reptile"
        );
    }

    private ReptileReadDto mapReptileToDto(Reptile reptile) {
        return new ReptileReadDto(
                reptile.getNormalTemperature(),
                reptile.getSleepPeriod().toString()
        );
    }

}
