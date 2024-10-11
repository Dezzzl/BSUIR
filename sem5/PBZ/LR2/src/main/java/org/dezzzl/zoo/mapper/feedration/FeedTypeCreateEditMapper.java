package org.dezzzl.zoo.mapper.feedration;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.feedtype.FeedTypeCreateEditDto;
import org.dezzzl.zoo.entity.pet.FeedType;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedTypeCreateEditMapper implements Mapper<FeedTypeCreateEditDto, FeedType> {
    @Override
    public FeedType map(FeedTypeCreateEditDto object) {
        return FeedType.builder()
                .name(object.getName())
                .build();
    }

    public FeedType map(FeedTypeCreateEditDto object, FeedType feedType){
        feedType.setName(object.getName());
        return feedType;
    }
}
