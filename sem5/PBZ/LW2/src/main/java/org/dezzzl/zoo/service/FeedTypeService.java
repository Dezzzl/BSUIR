package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.feedtype.FeedTypeCreateEditDto;
import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;
import org.dezzzl.zoo.entity.pet.FeedType;
import org.dezzzl.zoo.mapper.feedration.FeedTypeCreateEditMapper;
import org.dezzzl.zoo.mapper.feedration.FeedTypeReadMapper;
import org.dezzzl.zoo.repository.FeedTypeRepository;
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
public class FeedTypeService {
    private final FeedTypeRepository feedTypeRepository;

    private final FeedTypeCreateEditMapper feedTypeCreateEditMapper;

    private final FeedTypeReadMapper feedTypeReadMapper;

    @Transactional
    public Integer create(FeedTypeCreateEditDto feedTypeCreateEditDto) {
        return Optional.of(feedTypeCreateEditDto)
                .map(dto -> feedTypeCreateEditMapper.map(feedTypeCreateEditDto))
                .map(feedTypeRepository::save)
                .map(FeedType::getId)
                .orElseThrow();
    }

    public Optional<FeedTypeReadDto> findById(Integer id) {
        return feedTypeRepository.findById(id)
                .map(feedTypeReadMapper::map);
    }

    public List<FeedTypeReadDto> findAll() {
        return feedTypeRepository.findAll()
                .stream()
                .map(feedTypeReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Integer id) {
        return feedTypeRepository.findById(id)
                .map(zone -> {
                    feedTypeRepository.delete(zone);
                    feedTypeRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<FeedTypeReadDto> update(Integer id, FeedTypeCreateEditDto feedTypeCreateEditDto) {
        return feedTypeRepository.findById(id)
                .map(feedType -> feedTypeCreateEditMapper.map(feedTypeCreateEditDto, feedType))
                .map(feedTypeRepository::saveAndFlush)
                .map(feedTypeReadMapper::map);
    }

    public Page<FeedTypeReadDto> findAll(Pageable pageable) {
        return feedTypeRepository.findAll(pageable)
                .map(feedTypeReadMapper::map);
    }
}
