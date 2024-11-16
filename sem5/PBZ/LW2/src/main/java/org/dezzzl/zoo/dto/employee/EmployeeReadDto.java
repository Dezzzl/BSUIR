package org.dezzzl.zoo.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.employee.MaritalStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Setter
public class EmployeeReadDto {
    Integer id;

    String name;

    LocalDate birthdate;

    String phoneNumber;

    EmployeeType employeeType;

    MaritalStatus maritalStatus;

    EmployeeReferenceReadDto spouse;
}
