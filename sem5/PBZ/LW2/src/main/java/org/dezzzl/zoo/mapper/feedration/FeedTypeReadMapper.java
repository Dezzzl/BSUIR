package org.dezzzl.zoo.mapper.feedration;

import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;
import org.dezzzl.zoo.entity.pet.FeedType;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class FeedTypeReadMapper implements Mapper<FeedType, FeedTypeReadDto> {
    @Override
    public FeedTypeReadDto map(FeedType object) {
        return new FeedTypeReadDto(
                object.getId(),
                object.getName()
        );
    }
}
