package org.dezzzl.zoo.mapper.pet;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.dto.employee.EmployeeReferenceReadDto;
import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.dto.pet.read.FeedingRationReadDto;
import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.entity.employee.EmployeePet;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.mapper.Mapper;
import org.dezzzl.zoo.mapper.employee.EmployeeReferenceReadMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PetReadMapper implements Mapper<Pet, PetReadDto> {

    private final EmployeeReferenceReadMapper employeeReferenceReadMapper;


    @Override
    public PetReadDto map(Pet object) {
        return new PetReadDto(
                object.getId(),
                object.getName(),
                object.getSex(),
                object.getBirthdate(),
                null, // Поскольку вы не указали, как обрабатывать winteringPlace
                null, // Поскольку вы не указали, как обрабатывать reptile
                mapFeedingRation(object),
                mapHabitatZone(object),
                mapEmployeeToDto(getEmployeeByType(object, EmployeeType.ZOOKEEPER)),
                mapEmployeeToDto(getEmployeeByType(object, EmployeeType.VETERINARIAN)),
                "pet"
        );

    }

    protected FeedingRationReadDto mapFeedingRation(Pet pet) {
        return new FeedingRationReadDto(
                pet.getFeedingRation().getId(),
                pet.getFeedingRation().getName(),
                new FeedTypeReadDto(
                        pet.getFeedingRation().getFeedType().getId(),
                        pet.getFeedingRation().getFeedType().getName()
                )
        );
    }

    protected HabitatZoneReadDto mapHabitatZone(Pet pet) {
        return new HabitatZoneReadDto(
                pet.getHabitatZone().getId(),
                pet.getHabitatZone().getName(),
                pet.getHabitatZone().getDescription()
        );
    }

    protected EmployeeReferenceReadDto mapEmployeeToDto(Optional<Employee> employee) {
        return employee.map(employeeReferenceReadMapper::map)
                .orElse(null);
    }

    protected Optional<Employee> getEmployeeByType(Pet pet, EmployeeType employeeType) {
        return pet.getEmployeePets()
                .stream()
                .map(EmployeePet::getEmployee)
                .filter(employee -> Objects.equals(employeeType, employee.getEmployeeType()))
                .findFirst();
    }
}
