package org.dezzzl.zoo.mapper.habitatzone;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneCreateEditDto;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.mapper.Mapper;
import org.dezzzl.zoo.service.HabitatZoneService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitatZoneCreateEditMapper implements Mapper<HabitatZoneCreateEditDto, HabitatZone> {
    @Override
    public HabitatZone map(HabitatZoneCreateEditDto object) {
        return HabitatZone.builder()
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }

    public HabitatZone map(HabitatZoneCreateEditDto object, HabitatZone zone){
        zone.setName(object.getName());
        zone.setDescription(object.getDescription());
        return zone;
    }
}
