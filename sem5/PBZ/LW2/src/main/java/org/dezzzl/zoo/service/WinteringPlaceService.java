package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.WinteringPlaceCreateEditDto;
import org.dezzzl.zoo.dto.pet.read.WinteringPlaceReadDto;
import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
import org.dezzzl.zoo.mapper.winteringplace.WinteringPlaceCreateEditMapper;
import org.dezzzl.zoo.mapper.winteringplace.WinteringPlaceReadMapper;
import org.dezzzl.zoo.repository.WinteringPlaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WinteringPlaceService {
    private final WinteringPlaceRepository winteringPlaceRepository;

    private final WinteringPlaceCreateEditMapper winteringPlaceCreateEditMapper;

    private final WinteringPlaceReadMapper winteringPlaceReadMapper;

    public Optional<WinteringPlaceReadDto> findById(Integer id) {
        return winteringPlaceRepository.findById(id)
                .map(winteringPlaceReadMapper::map);
    }

    public List<WinteringPlaceReadDto> findAll() {
        return winteringPlaceRepository.findAll().stream()
                .map(winteringPlaceReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer create(WinteringPlaceCreateEditDto winteringPlaceCreateEditDto) {
        return Optional.of(winteringPlaceCreateEditDto)
                .map(dto -> winteringPlaceCreateEditMapper.map(winteringPlaceCreateEditDto))
                .map(winteringPlaceRepository::save)
                .map(WinteringPlace::getId)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Integer id) {
        return winteringPlaceRepository.findById(id)
                .map(winteringPlace -> {
                    winteringPlaceRepository.delete(winteringPlace);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<WinteringPlaceReadDto> update(Integer id, WinteringPlaceCreateEditDto winteringPlaceCreateEditDto) {
        return winteringPlaceRepository.findById(id)
                .map(winteringPlace -> winteringPlaceCreateEditMapper.map(winteringPlaceCreateEditDto, winteringPlace))
                .map(winteringPlaceRepository::saveAndFlush)
                .map(winteringPlaceReadMapper::map);
    }

    @Transactional
    public Page<WinteringPlaceReadDto> findAll(PageRequest pageRequest) {
        return winteringPlaceRepository.findAll(pageRequest)
                .map(winteringPlaceReadMapper::map);
    }

}
