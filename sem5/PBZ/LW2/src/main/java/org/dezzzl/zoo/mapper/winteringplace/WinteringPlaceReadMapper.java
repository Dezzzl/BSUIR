package org.dezzzl.zoo.mapper.winteringplace;

import org.dezzzl.zoo.dto.pet.read.WinteringPlaceReadDto;
import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class WinteringPlaceReadMapper implements Mapper<WinteringPlace, WinteringPlaceReadDto> {
    @Override
    public WinteringPlaceReadDto map(WinteringPlace object) {
        return new WinteringPlaceReadDto(
                object.getId(),
                object.getCode(),
                object.getCountry(),
                object.getArrivalDate(),
                object.getDepartureDate()
        );
    }
}
