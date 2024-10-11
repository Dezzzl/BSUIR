package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneCreateEditDto;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.mapper.habitatzone.HabitatZoneCreateEditMapper;
import org.dezzzl.zoo.mapper.habitatzone.HabitatZoneReadMapper;
import org.dezzzl.zoo.repository.HabitatZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitatZoneService {

    private final HabitatZoneRepository habitatZoneRepository;

    private final HabitatZoneCreateEditMapper habitatZoneCreateEditMapper;

    private final HabitatZoneReadMapper habitatZoneReadMapper;

    @Transactional
    public Integer create(HabitatZoneCreateEditDto habitatZoneCreateEditDto) {
        return Optional.of(habitatZoneCreateEditDto)
                .map(dto -> habitatZoneCreateEditMapper.map(habitatZoneCreateEditDto))
                .map(habitatZoneRepository::save)
                .map(HabitatZone::getId)
                .orElseThrow();
    }

    public Optional<HabitatZoneReadDto> findById(Integer id) {
        return habitatZoneRepository.findById(id)
                .map(habitatZoneReadMapper::map);
    }

    public List<HabitatZoneReadDto> findAll() {
        return habitatZoneRepository.findAll()
                .stream()
                .map(habitatZoneReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Integer id) {
        return habitatZoneRepository.findById(id)
                .map(zone -> {
                    habitatZoneRepository.delete(zone);
                    habitatZoneRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<HabitatZoneReadDto> updateZone(Integer id, HabitatZoneCreateEditDto habitatZoneCreateEditDto) {
        return habitatZoneRepository.findById(id)
                .map(zone -> habitatZoneCreateEditMapper.map(habitatZoneCreateEditDto, zone))
                .map(habitatZoneRepository::saveAndFlush)
                .map(habitatZoneReadMapper::map);
    }

    public Page<HabitatZoneReadDto> findAll(Pageable pageable){
        return habitatZoneRepository.findAll(pageable)
                .map(habitatZoneReadMapper::map);
    }
}
