package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.bird.Bird;
import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
import org.dezzzl.zoo.mapper.pet.BirdCreateEditMapper;
import org.dezzzl.zoo.mapper.pet.BirdReadMapper;
import org.dezzzl.zoo.mapper.pet.PetReadMapper;
import org.dezzzl.zoo.mapper.winteringplace.WinteringPlaceCreateEditMapper;
import org.dezzzl.zoo.repository.BirdRepository;
import org.dezzzl.zoo.repository.WinteringPlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BirdService {
    private final BirdRepository birdRepository;

    private final WinteringPlaceRepository winteringPlaceRepository;

    private final BirdCreateEditMapper birdCreateEditMapper;

    private final WinteringPlaceCreateEditMapper winteringPlaceCreateEditMapper;

    private final BirdReadMapper birdReadMapper;

    @Transactional
    public Integer create(PetCreateEditDto petCreateEditDto) {
//        Integer winteringPlaceId = Optional.of(petCreateEditDto.getWinteringPlace())
//                .map(winteringPlaceCreateEditMapper::map)
//                .map(winteringPlaceRepository::save)
//                .map(WinteringPlace::getId)
//                .orElseThrow();
//        petCreateEditDto.getWinteringPlace().setId(winteringPlaceId);
        return Optional.of(petCreateEditDto)
                .map(birdCreateEditMapper::map)
                .map(birdRepository::save)
                .map(Bird::getId)
                .orElseThrow();
    }

    public PetReadDto findById(Integer id) {
        return birdRepository.findPetById(id)
                .map(birdReadMapper::map)
                .orElseThrow();
    }

    public List<PetReferencesReadDto> findAll() {
        return birdRepository.findAllBirdsBasicInfo();
    }

    @Transactional
    public Optional<PetReadDto> updateBird(Integer id, PetCreateEditDto petCreateEditDto) {
        return birdRepository.findById(id)
                .map(pet -> birdCreateEditMapper.map(petCreateEditDto, pet))
                .map(birdRepository::saveAndFlush)
                .map(birdReadMapper::map);
    }

    public List<PetReadDto> findByName(String name) {
        return birdRepository.findByName(name)
                .stream().map(birdReadMapper::map)
                .collect(Collectors.toList());
    }
}
