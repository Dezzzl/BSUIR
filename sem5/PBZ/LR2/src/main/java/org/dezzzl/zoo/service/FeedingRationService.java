package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneCreateEditDto;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.dto.pet.editcreate.FeedingRationCreateEditDto;
import org.dezzzl.zoo.dto.pet.read.FeedingRationReadDto;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.dezzzl.zoo.mapper.feedration.FeedingRationCreateEditMapper;
import org.dezzzl.zoo.mapper.feedration.FeedingRationReadMapper;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedingRationService {
    private final FeedingRationRepository feedingRationRepository;

    private final FeedingRationReadMapper feedingRationReadMapper;

    private final FeedingRationCreateEditMapper feedingRationCreateEditMapper;

    public Optional<FeedingRationReadDto> findById(Integer id) {
        return feedingRationRepository.findById(id)
                .map(feedingRationReadMapper::map);
    }

    public List<FeedingRationReadDto> findAll() {
        return feedingRationRepository.findAll().stream()
                .map(feedingRationReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer create(FeedingRationCreateEditDto feedingRationCreateEditDto) {
        return Optional.of(feedingRationCreateEditDto)
                .map(dto -> feedingRationCreateEditMapper.map(feedingRationCreateEditDto))
                .map(feedingRationRepository::save)
                .map(FeedingRation::getId)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Integer id) {
        return feedingRationRepository.findById(id)
                .map(feedingRation -> {
                    feedingRationRepository.delete(id);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Integer update(Integer id, FeedingRationCreateEditDto feedingRationCreateEditDto) {
        return feedingRationRepository.findById(id)
                .map(feedingRation -> feedingRationCreateEditMapper.map(feedingRationCreateEditDto, feedingRation))
                .map(feedingRationRepository::update)
                .orElse(null);
    }

    @Transactional
    public Page<FeedingRationReadDto> findAll(PageRequest pageRequest) {
        return feedingRationRepository.findAll(pageRequest)
                .map(feedingRationReadMapper::map);
    }


}
