package org.dezzzl.zoo.repository.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePetId implements Serializable {
    private Integer employeeId;
    private Integer petId;
}
