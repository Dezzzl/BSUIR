package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneCreateEditDto;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.dezzzl.zoo.entity.pet.Pet;
import org.dezzzl.zoo.mapper.feedration.FeedingRationCreateEditMapper;
import org.dezzzl.zoo.mapper.pet.PetCreateEditMapper;
import org.dezzzl.zoo.mapper.pet.PetReadMapper;
import org.dezzzl.zoo.repository.FeedingRationRepository;
import org.dezzzl.zoo.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;

    private final PetCreateEditMapper petCreateEditMapper;

    private final PetReadMapper petReadMapper;

    private final FeedingRationRepository feedingRationRepository;

    private final FeedingRationCreateEditMapper feedingRationCreateEditMapper;

    @Transactional
    public Integer create(PetCreateEditDto petCreateEditDto) {
//        Integer feedingRationId = Optional.of(petCreateEditDto.getFeedingRationCreateEditDto())
//                .map(dto -> feedingRationCreateEditMapper.map(petCreateEditDto.getFeedingRationCreateEditDto()))
//                .map(feedingRationRepository::save)
//                .map(FeedingRation::getId)
//                .orElseThrow();
//
//        petCreateEditDto.getFeedingRationCreateEditDto().setFeedingRationId(feedingRationId);

        return Optional.of(petCreateEditDto)
                .map(dto -> petCreateEditMapper.map(petCreateEditDto))
                .map(petRepository::save)
                .map(Pet::getId)
                .orElseThrow();
    }

    public PetReadDto findById(Integer id) {
        return petRepository.findPetById(id)
                .map(petReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Integer id) {
        return petRepository.findById(id)
                .map(pet -> {
                    petRepository.delete(pet);
                    petRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<PetReadDto> updatePet(Integer id, PetCreateEditDto petCreateEditDto) {
        return petRepository.findById(id)
                .map(pet -> petCreateEditMapper.map(petCreateEditDto, pet))
                .map(petRepository::saveAndFlush)
                .map(petReadMapper::map);
    }

    public List<PetReferencesReadDto> findAll() {
        return petRepository.findAllSimplePetsBasicInfo();
    }

    public List<PetReadDto> findByName(String name) {
        return petRepository.findByName(name)
                .stream().map(petReadMapper::map)
                .collect(Collectors.toList());

    }
}
