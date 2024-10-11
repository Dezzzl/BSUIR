package org.dezzzl.zoo.mapper.feedration;

import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;
import org.dezzzl.zoo.dto.pet.read.FeedingRationReadDto;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class FeedingRationReadMapper implements Mapper<FeedingRation, FeedingRationReadDto> {
    @Override
    public FeedingRationReadDto map(FeedingRation object) {
        return new FeedingRationReadDto(
                object.getId(),
                object.getName(),
                new FeedTypeReadDto(
                        object.getFeedType().getId(),
                        object.getFeedType().getName()
                )
        );
    }

}
