package org.dezzzl.zoo.entity.pet.bird;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.dezzzl.zoo.entity.employee.EmployeePet;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.entity.pet.Sex;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
@SuperBuilder
public class Bird extends Pet {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WinteringPlace winteringPlace;

    public Bird(Integer id, String name, LocalDate birthdate, Sex sex, HabitatZone habitatZone, FeedingRation feedingRation, List<EmployeePet> employeePets, WinteringPlace winteringPlace) {
        super(id, name, birthdate, sex, habitatZone, feedingRation, employeePets);
        this.winteringPlace = winteringPlace;
    }

    public Bird() {

    }
}
