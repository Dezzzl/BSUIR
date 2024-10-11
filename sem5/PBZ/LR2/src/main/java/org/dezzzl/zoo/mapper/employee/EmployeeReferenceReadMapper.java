package org.dezzzl.zoo.mapper.employee;

import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.dto.employee.EmployeeReferenceReadDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReferenceReadMapper implements Mapper<EmployeeReadDto, EmployeeReferenceReadDto> {
    @Override
    public EmployeeReferenceReadDto map(EmployeeReadDto object) {
        return new EmployeeReferenceReadDto(
                object.getId(),
                object.getName(),
                object.getEmployeeType()
        );
    }

    public EmployeeReferenceReadDto map(Employee object){
        return new EmployeeReferenceReadDto(
                object.getId(),
                object.getName(),
                object.getEmployeeType()
        );
    }

}
