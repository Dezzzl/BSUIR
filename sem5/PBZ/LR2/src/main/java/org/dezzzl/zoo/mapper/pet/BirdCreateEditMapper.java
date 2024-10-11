package org.dezzzl.zoo.mapper.pet;

import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.entity.pet.bird.Bird;
import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
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

    @Autowired
    public BirdCreateEditMapper(HabitatZoneRepository habitatZoneRepository, FeedingRationRepository feedingRationRepository, EmployeeRepository employeeRepository, WinteringPlaceRepository winteringPlaceRepository) {
        super(habitatZoneRepository, feedingRationRepository, employeeRepository);
        this.winteringPlaceRepository = winteringPlaceRepository;
    }

    @Override
    public Bird map(PetCreateEditDto object) {
        Bird pet = Bird.builder()
                .name(object.getName())
                .sex(object.getSex())
                .birthdate(object.getBirthdate())
                .habitatZone(getHabitatZone(object.getHabitatZoneId()))
                .feedingRation(getFeedingRation(object.getFeedingRationCreateEditDto().getFeedTypeId()))
                .winteringPlace(getWinteringPlace(object.getWinteringPlace().getId()))
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
        pet.setName(object.getName());
        pet.setSex(object.getSex());
        pet.setBirthdate(object.getBirthdate());
        pet.setHabitatZone(getHabitatZone(object.getHabitatZoneId()));
        pet.setFeedingRation(getFeedingRation(object.getFeedingRationCreateEditDto().getFeedTypeId()));
        pet.setWinteringPlace(getWinteringPlace(object.getVeterinarianId()));
        pet.setName(object.getName());
        setEmployeePet(object, pet);
        return pet;
    }
}
