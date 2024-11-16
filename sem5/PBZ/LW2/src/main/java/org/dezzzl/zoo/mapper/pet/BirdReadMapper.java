package org.dezzzl.zoo.mapper.pet;

import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.WinteringPlaceReadDto;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.entity.pet.bird.Bird;
import org.dezzzl.zoo.mapper.employee.EmployeeReferenceReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BirdReadMapper extends PetReadMapper {
    @Autowired
    public BirdReadMapper(EmployeeReferenceReadMapper employeeReferenceReadMapper) {
        super(employeeReferenceReadMapper);
    }

    @Override
    public PetReadDto map(Pet object) {
            Bird bird = (Bird) object;
        return new PetReadDto(
                bird.getId(),
                bird.getName(),
                bird.getSex(),
                bird.getBirthdate(),
                mapWinteringPlaceToDto(bird),
                null,
                mapFeedingRation(bird),
                mapHabitatZone(bird),
                mapEmployeeToDto(getEmployeeByType(bird, EmployeeType.ZOOKEEPER)),
                mapEmployeeToDto(getEmployeeByType(bird, EmployeeType.VETERINARIAN)),
                "bird"
        );
    }

    private WinteringPlaceReadDto mapWinteringPlaceToDto(Bird bird) {
        return new WinteringPlaceReadDto(
                bird.getWinteringPlace().getId(),
                bird.getWinteringPlace().getCode(),
                bird.getWinteringPlace().getCountry(),
                bird.getWinteringPlace().getArrivalDate(),
                bird.getWinteringPlace().getDepartureDate()
        );
    }
}
