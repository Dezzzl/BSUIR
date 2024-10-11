package org.dezzzl.zoo.entity.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Spouses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_spouse_id")
    private Employee firstSpouse;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_spouse_id")
    private Employee secondSpouse;
}

