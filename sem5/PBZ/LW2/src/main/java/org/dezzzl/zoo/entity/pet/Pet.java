package org.dezzzl.zoo.entity.pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dezzzl.zoo.entity.employee.EmployeePet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne(fetch = FetchType.LAZY)
    private HabitatZone habitatZone;

    @OneToOne(fetch = FetchType.LAZY)
    private FeedingRation feedingRation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pet", cascade = CascadeType.ALL)
    @Builder.Default
    private List<EmployeePet> employeePets = new ArrayList<>();
}
