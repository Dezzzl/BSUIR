package org.dezzzl.zoo.mapper.habitatzone;

import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class HabitatZoneReadMapper implements Mapper<HabitatZone, HabitatZoneReadDto> {
    @Override
    public HabitatZoneReadDto map(HabitatZone object) {
        return new HabitatZoneReadDto(
                object.getId(),
                object.getName(),
                object.getDescription()
        );
    }
}
