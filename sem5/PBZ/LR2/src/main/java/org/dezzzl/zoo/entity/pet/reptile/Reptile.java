package org.dezzzl.zoo.entity.pet.reptile;

import jakarta.persistence.Convert;
import lombok.experimental.SuperBuilder;
import org.dezzzl.zoo.converter.PeriodToStringConverter;
import org.dezzzl.zoo.entity.employee.EmployeePet;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.entity.pet.Sex;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
@SuperBuilder
public class Reptile extends Pet {
    private BigDecimal normalTemperature;
    @Convert(converter = PeriodToStringConverter.class)
    private Period sleepPeriod;

    public Reptile(Integer id, String name, LocalDate birthdate, Sex sex, HabitatZone habitatZone, FeedingRation feedingRation, List<EmployeePet> employeePets, BigDecimal normalTemperature, Period sleepPeriod) {
        super(id, name, birthdate, sex, habitatZone, feedingRation, employeePets);
        this.normalTemperature = normalTemperature;
        this.sleepPeriod = sleepPeriod;
    }

    public Reptile() {

    }
}
