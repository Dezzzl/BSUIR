package org.dezzzl.zoo.dto.employee;

import lombok.Value;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.employee.MaritalStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
public class EmployeeCreateEditDto {
    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate;

    String phoneNumber;

    EmployeeType employeeType;

    MaritalStatus maritalStatus;

    Integer spouseId;
}
