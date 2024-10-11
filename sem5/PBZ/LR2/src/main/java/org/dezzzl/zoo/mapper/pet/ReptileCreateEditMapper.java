package org.dezzzl.zoo.mapper.pet;

import org.dezzzl.zoo.converter.StringToPeriodConverter;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.entity.pet.reptile.Reptile;
import org.dezzzl.zoo.repository.EmployeeRepository;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.dezzzl.zoo.repository.HabitatZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReptileCreateEditMapper extends PetCreateEditMapper {

    private final StringToPeriodConverter stringToPeriodConverter;
    @Autowired
    public ReptileCreateEditMapper(HabitatZoneRepository habitatZoneRepository, FeedingRationRepository feedingRationRepository, EmployeeRepository employeeRepository, StringToPeriodConverter stringToPeriodConverter) {
        super(habitatZoneRepository, feedingRationRepository, employeeRepository);
        this.stringToPeriodConverter = stringToPeriodConverter;
    }

    @Override
    public Reptile map(PetCreateEditDto object) {
        Reptile pet = Reptile.builder()
                .name(object.getName())
                .sex(object.getSex())
                .birthdate(object.getBirthdate())
                .habitatZone(getHabitatZone(object.getHabitatZoneId()))
                .feedingRation(getFeedingRation(object.getFeedingRationCreateEditDto().getFeedTypeId()))
                .normalTemperature(object.getReptileCreateEditDto().getNormalTemperature())
                .sleepPeriod(stringToPeriodConverter.convert(object.getReptileCreateEditDto().getSleepPeriod()))
                .build();
        setEmployeePet(object, pet);
        return pet;
    }

    public Reptile map(PetCreateEditDto object, Reptile pet) {
        pet.setName(object.getName());
        pet.setSex(object.getSex());
        pet.setSleepPeriod(stringToPeriodConverter.convert(object.getReptileCreateEditDto().getSleepPeriod()));
        pet.setNormalTemperature(object.getReptileCreateEditDto().getNormalTemperature());
        pet.setBirthdate(object.getBirthdate());
        pet.setHabitatZone(getHabitatZone(object.getHabitatZoneId()));
        pet.setFeedingRation(getFeedingRation(object.getFeedingRationCreateEditDto().getFeedTypeId()));
        pet.setName(object.getName());
        setEmployeePet(object, pet);
        return pet;
    }
}
