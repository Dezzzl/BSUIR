package org.dezzzl.zoo.mapper.pet;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.entity.employee.EmployeePet;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.mapper.Mapper;
import org.dezzzl.zoo.repository.id.EmployeePetId;
import org.dezzzl.zoo.repository.EmployeeRepository;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.dezzzl.zoo.repository.HabitatZoneRepository;
import org.springframework.stereotype.Component;

import java.util.List;
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
                .feedingRation(getFeedingRation(object.getFeedingRationId()))
                .build();
        setEmployeePet(object, pet);
        return pet;
    }

    public void setEmployeePet(PetCreateEditDto object, Pet pet) {
        List<EmployeePet> existingEmployeePets = pet.getEmployeePets();
        Employee veterinarian = getEmployee(object.getVeterinarianId());
        updateEmployeePet(existingEmployeePets, veterinarian, pet);
        Employee zookeeper = getEmployee(object.getZookeeperId());
        updateEmployeePet(existingEmployeePets, zookeeper, pet);
    }

    private void updateEmployeePet(List<EmployeePet> existingEmployeePets, Employee employee, Pet pet) {
        if (employee != null) {
            boolean exists = existingEmployeePets.stream()
                    .anyMatch(ep -> ep.getEmployee().getId().equals(employee.getId()));

            if (!exists) {
                EmployeePet employeePet = new EmployeePet();
                employeePet.setEmployee(employee);
                employeePet.setPet(pet);
                employeePet.setEmployeePetId(new EmployeePetId(employee.getId(), pet.getId()));
                existingEmployeePets.add(employeePet);
            }
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
        pet.setFeedingRation(getFeedingRation(object.getFeedingRationId()));
        setEmployeePet(object, pet);
        return pet;
    }
}
