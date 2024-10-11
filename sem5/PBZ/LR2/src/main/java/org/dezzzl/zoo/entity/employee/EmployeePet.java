package org.dezzzl.zoo.entity.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dezzzl.zoo.entity.pet.Pet;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_pet")
@Data
public class EmployeePet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    Pet pet;

    public void setPet(Pet pet){
        this.pet = pet;
        this.pet.getEmployeePets().add(this);
    }
}
