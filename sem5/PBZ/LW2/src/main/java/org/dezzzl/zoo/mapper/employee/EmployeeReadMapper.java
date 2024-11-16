package org.dezzzl.zoo.mapper.employee;

import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReadMapper implements Mapper<Employee, EmployeeReadDto> {

    @Override
    public EmployeeReadDto map(Employee object) {
        return new EmployeeReadDto(
                object.getId(),
                object.getName(),
                object.getBirthdate(),
                object.getPhoneNumber(),
                object.getEmployeeType(),
                object.getMaritalStatus(),
                null
        );
    }
}
