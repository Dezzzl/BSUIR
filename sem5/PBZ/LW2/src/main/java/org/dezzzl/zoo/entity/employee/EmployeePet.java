package org.dezzzl.zoo.entity.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.repository.id.EmployeePetId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_pet")
@Data
public class EmployeePet {

    @EmbeddedId
    private EmployeePetId employeePetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", nullable = false)
    Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("petId")
    @JoinColumn(name = "pet_id", nullable = false)
    Pet pet;

    public void setPet(Pet pet){
        this.pet = pet;
        this.pet.getEmployeePets().add(this);
    }
}
