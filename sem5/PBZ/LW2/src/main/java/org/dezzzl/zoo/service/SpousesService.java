package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.dto.employee.EmployeeReferenceReadDto;
import org.dezzzl.zoo.entity.employee.Spouses;
import org.dezzzl.zoo.mapper.employee.EmployeeReadMapper;
import org.dezzzl.zoo.mapper.employee.EmployeeReferenceReadMapper;
import org.dezzzl.zoo.repository.SpousesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpousesService {
    private final SpousesRepository spousesRepository;

    private final EmployeeReadMapper employeeReadMapper;

    private final EmployeeReferenceReadMapper employeeReferenceReadMapper;

    public Optional<EmployeeReadDto> findSpouseForEmployee(Integer id) {
        return spousesRepository.findSpouseForEmployeeById(id)
                .flatMap(spouses -> Stream.of(spouses.getFirstSpouse(), spouses.getSecondSpouse())
                        .filter(spouse -> !spouse.getId().equals(id))
                        .findFirst()
                )
                .map(employeeReadMapper::map);
    }

    public List<EmployeeReadDto> findAllSpouses() {
        return spousesRepository.findAllSpouses()
                .stream()
                .map(spouses -> {
                    EmployeeReadDto firstSpouseDto = employeeReadMapper.map(spouses.getFirstSpouse());
                    firstSpouseDto.setSpouse(employeeReferenceReadMapper.map(spouses.getSecondSpouse()));
                    return firstSpouseDto;
                })
                .collect(Collectors.toList());
    }



}
