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
public class SpouseId implements Serializable {
    private Integer firstSpouseId;
    private Integer secondSpouseId;
}
