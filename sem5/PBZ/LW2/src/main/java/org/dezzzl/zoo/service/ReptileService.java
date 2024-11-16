package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.reptile.Reptile;
import org.dezzzl.zoo.mapper.pet.ReptileCreateEditMapper;
import org.dezzzl.zoo.mapper.pet.ReptileReadMapper;
import org.dezzzl.zoo.repository.ReptileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReptileService {

    private final ReptileRepository reptileRepository;

    private final ReptileCreateEditMapper reptileCreateEditMapper;

    private final ReptileReadMapper reptileReadMapper;

    @Transactional
    public Integer create(PetCreateEditDto petCreateEditDto) {
        return Optional.of(petCreateEditDto)
                .map(reptileCreateEditMapper::map)
                .map(reptileRepository::save)
                .map(Reptile::getId)
                .orElseThrow();
    }

    public PetReadDto findById(Integer id) {
        return reptileRepository.findPetById(id)
                .map(reptileReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<PetReadDto> updateReptile(Integer id, PetCreateEditDto petCreateEditDto) {
        return reptileRepository.findById(id)
                .map(pet -> reptileCreateEditMapper.map(petCreateEditDto, pet))
                .map(reptileRepository::saveAndFlush)
                .map(reptileReadMapper::map);
    }

    public List<PetReferencesReadDto> findAll() {
        return reptileRepository.findAllReptilesBasicInfo();
    }

    public List<PetReadDto> findByName(String name) {
        return reptileRepository.findByName(name)
                .stream().map(reptileReadMapper::map)
                .collect(Collectors.toList());
    }
}
