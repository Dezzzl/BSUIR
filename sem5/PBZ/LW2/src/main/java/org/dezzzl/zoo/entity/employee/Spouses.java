package org.dezzzl.zoo.entity.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dezzzl.zoo.repository.id.SpouseId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Spouses {
    @EmbeddedId
    private SpouseId id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("firstSpouseId") // Связывает id первого супруга с составным ключом
    @JoinColumn(name = "first_spouse_id", nullable = false)
    private Employee firstSpouse;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("secondSpouseId") // Связывает id второго супруга с составным ключом
    @JoinColumn(name = "second_spouse_id", nullable = false)
    private Employee secondSpouse;
}


