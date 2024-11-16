package org.dezzzl.zoo.entity.employee;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@EqualsAndHashCode(of = "phoneNumber")
@NoArgsConstructor
@Builder
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDate birthdate;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
}
