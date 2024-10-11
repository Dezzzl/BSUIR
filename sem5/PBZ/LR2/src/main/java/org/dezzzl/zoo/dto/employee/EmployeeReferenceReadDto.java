package org.dezzzl.zoo.dto.employee;

import lombok.Value;
import org.dezzzl.zoo.entity.employee.EmployeeType;

@Value
public class EmployeeReferenceReadDto {
    Integer id;
    String name;
    EmployeeType employeeType;
}
