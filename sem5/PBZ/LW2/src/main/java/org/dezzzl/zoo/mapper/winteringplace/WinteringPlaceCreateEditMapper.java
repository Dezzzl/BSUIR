package org.dezzzl.zoo.mapper.winteringplace;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.WinteringPlaceCreateEditDto;
import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WinteringPlaceCreateEditMapper implements Mapper<WinteringPlaceCreateEditDto, WinteringPlace> {
    @Override
    public WinteringPlace map(WinteringPlaceCreateEditDto object) {
        return WinteringPlace.builder()
                .code(object.getCode())
                .country(object.getCountry())
                .departureDate(object.getDepartureDate())
                .arrivalDate(object.getArrivalDate())
                .build();
    }

    public WinteringPlace map(WinteringPlaceCreateEditDto object, WinteringPlace winteringPlace) {
        winteringPlace.setId(object.getId());
        winteringPlace.setCode(object.getCode());
        winteringPlace.setCountry(object.getCountry());
        winteringPlace.setArrivalDate(object.getArrivalDate());
        winteringPlace.setDepartureDate(object.getDepartureDate());
        return winteringPlace;
    }
}
