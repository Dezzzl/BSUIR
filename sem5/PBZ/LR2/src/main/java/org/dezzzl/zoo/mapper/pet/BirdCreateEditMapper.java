package org.dezzzl.zoo.mapper.pet;

import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.entity.pet.bird.Bird;
import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
import org.dezzzl.zoo.mapper.winteringplace.WinteringPlaceCreateEditMapper;
import org.dezzzl.zoo.repository.EmployeeRepository;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.dezzzl.zoo.repository.HabitatZoneRepository;
import org.dezzzl.zoo.repository.WinteringPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BirdCreateEditMapper extends PetCreateEditMapper {
    private final WinteringPlaceRepository winteringPlaceRepository;

    private final WinteringPlaceCreateEditMapper winteringPlaceCreateEditMapper;

    @Autowired
    public BirdCreateEditMapper(HabitatZoneRepository habitatZoneRepository, FeedingRationRepository feedingRationRepository, EmployeeRepository employeeRepository, WinteringPlaceRepository winteringPlaceRepository, WinteringPlaceCreateEditMapper winteringPlaceCreateEditMapper) {
        super(habitatZoneRepository, feedingRationRepository, employeeRepository);
        this.winteringPlaceRepository = winteringPlaceRepository;
        this.winteringPlaceCreateEditMapper = winteringPlaceCreateEditMapper;
    }

    @Override
    public Bird map(PetCreateEditDto object) {
        Bird pet = Bird.builder()
                .name(object.getName())
                .sex(object.getSex())
                .birthdate(object.getBirthdate())
                .habitatZone(getHabitatZone(object.getHabitatZoneId()))
                .feedingRation(getFeedingRation(object.getFeedingRationId()))
                .winteringPlace(getWinteringPlace(object.getWinteringPlaceId()))
                .build();
        setEmployeePet(object, pet);
        return pet;
    }

    public WinteringPlace getWinteringPlace(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(winteringPlaceRepository::findById)
                .orElse(null);
    }

    public Bird map(PetCreateEditDto object, Bird pet) {
        if (pet.getWinteringPlace() != null) {
            winteringPlaceRepository.delete(pet.getWinteringPlace());
        }

        pet.setName(object.getName());
        pet.setSex(object.getSex());
        pet.setBirthdate(object.getBirthdate());
        pet.setHabitatZone(getHabitatZone(object.getHabitatZoneId()));
        pet.setFeedingRation(getFeedingRation(object.getFeedingRationId()));
        pet.setWinteringPlace(getWinteringPlace(object.getWinteringPlaceId()));
        pet.setName(object.getName());
        setEmployeePet(object, pet);
        return pet;
    }
}
