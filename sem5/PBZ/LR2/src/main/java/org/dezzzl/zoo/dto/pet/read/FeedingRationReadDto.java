package org.dezzzl.zoo.dto.pet.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;

@Data
@AllArgsConstructor
public class FeedingRationReadDto {
    Integer feedRationId;

    String feedRationName;

    FeedTypeReadDto feedTypeReadDto;
}
