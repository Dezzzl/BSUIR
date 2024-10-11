package org.dezzzl.zoo.mapper.employee;

import org.dezzzl.zoo.dto.employee.EmployeeCreateEditDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCreateEditMapper implements Mapper<EmployeeCreateEditDto, Employee> {
    @Override
    public Employee map(EmployeeCreateEditDto object) {
        return Employee.builder()
                .name(object.getName())
                .birthdate(object.getBirthdate())
                .phoneNumber(object.getPhoneNumber())
                .employeeType(object.getEmployeeType())
                .maritalStatus(object.getMaritalStatus())
                .build();
    }

    public Employee map(EmployeeCreateEditDto object, Employee employee) {
        employee.setName(object.getName());
        employee.setBirthdate(object.getBirthdate());
        employee.setPhoneNumber(object.getPhoneNumber());
        employee.setEmployeeType(object.getEmployeeType());
        employee.setMaritalStatus(object.getMaritalStatus());
        return employee;
    }
}
