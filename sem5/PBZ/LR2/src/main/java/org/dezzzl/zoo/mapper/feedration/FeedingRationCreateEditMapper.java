package org.dezzzl.zoo.mapper.feedration;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.FeedingRationCreateEditDto;
import org.dezzzl.zoo.entity.pet.FeedType;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.mapper.Mapper;
import org.dezzzl.zoo.repository.FeedTypeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeedingRationCreateEditMapper implements Mapper<FeedingRationCreateEditDto, FeedingRation> {

    private final FeedTypeRepository feedTypeRepository;

    @Override
    public FeedingRation map(FeedingRationCreateEditDto object) {
        return FeedingRation.builder()
                .name(object.getFeedRationName())
                .feedType(getFeedType(object.getFeedTypeId()))
                .build();
    }

    private FeedType getFeedType(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(feedTypeRepository::findById)
                .orElse(null);
    }

    public FeedingRation map(FeedingRationCreateEditDto object, FeedingRation feedingRation){
        feedingRation.setName(object.getFeedRationName());
        feedingRation.setFeedType(getFeedType(object.getFeedTypeId()));
        return feedingRation;
    }
}
