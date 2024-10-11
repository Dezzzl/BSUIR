package org.dezzzl.zoo.mapper.pet;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.entity.employee.EmployeePet;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.mapper.Mapper;
import org.dezzzl.zoo.repository.EmployeeRepository;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.dezzzl.zoo.repository.HabitatZoneRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PetCreateEditMapper implements Mapper<PetCreateEditDto, Pet> {

    private final HabitatZoneRepository habitatZoneRepository;
    private final FeedingRationRepository feedingRationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Pet map(PetCreateEditDto object) {
        Pet pet = Pet.builder()
                .name(object.getName())
                .sex(object.getSex())
                .birthdate(object.getBirthdate())
                .habitatZone(getHabitatZone(object.getHabitatZoneId()))
                .feedingRation(getFeedingRation(object.getFeedingRationCreateEditDto().getFeedTypeId()))
                .build();
        setEmployeePet(object, pet);
        return pet;
    }

    public void setEmployeePet(PetCreateEditDto object, Pet pet) {
        Employee veterinarian = getEmployee(object.getVeterinarianId());
        if (veterinarian != null) {
            EmployeePet employeePetVeterinarian = new EmployeePet();
            employeePetVeterinarian.setEmployee(veterinarian);
            employeePetVeterinarian.setPet(pet);
        }

        Employee zookeeper = getEmployee(object.getZookeeperId());
        if (zookeeper != null) {
            EmployeePet employeePetZookeeper = new EmployeePet();
            employeePetZookeeper.setEmployee(zookeeper);
            employeePetZookeeper.setPet(pet);
        }
    }

    public HabitatZone getHabitatZone(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(habitatZoneRepository::findById)
                .orElse(null);
    }

    public Employee getEmployee(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(employeeRepository::findById)
                .orElse(null);
    }

    public FeedingRation getFeedingRation(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(feedingRationRepository::findById)
                .orElse(null);
    }


    public Pet map(PetCreateEditDto object, Pet pet) {
        pet.setName(object.getName());
        pet.setSex(object.getSex());
        pet.setBirthdate(object.getBirthdate());
        pet.setHabitatZone(getHabitatZone(object.getHabitatZoneId()));
        pet.setFeedingRation(getFeedingRation(object.getFeedingRationCreateEditDto().getFeedTypeId()));
        pet.setName(object.getName());
        setEmployeePet(object, pet);
        return pet;
    }
}
