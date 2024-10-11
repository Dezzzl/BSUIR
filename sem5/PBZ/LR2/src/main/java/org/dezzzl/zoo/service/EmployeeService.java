package org.dezzzl.zoo.service;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.employee.EmployeeCreateEditDto;
import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.dto.employee.EmployeeReferenceReadDto;
import org.dezzzl.zoo.dto.feedtype.FeedTypeCreateEditDto;
import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;
import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.employee.MaritalStatus;
import org.dezzzl.zoo.entity.employee.Spouses;
import org.dezzzl.zoo.entity.pet.FeedType;
import org.dezzzl.zoo.mapper.employee.EmployeeCreateEditMapper;
import org.dezzzl.zoo.mapper.employee.EmployeeReadMapper;
import org.dezzzl.zoo.mapper.employee.EmployeeReferenceReadMapper;
import org.dezzzl.zoo.repository.EmployeeRepository;
import org.dezzzl.zoo.repository.SpousesRepository;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final SpousesRepository spousesRepository;

    private final EmployeeCreateEditMapper employeeCreateEditMapper;

    private final EmployeeReadMapper employeeReadMapper;

    private final EmployeeReferenceReadMapper employeeReferenceReadMapper;

    private final SpousesService spousesService;


    @Transactional
    public Integer create(EmployeeCreateEditDto employeeCreateEditDto) {
        Employee employee = Optional.of(employeeCreateEditDto)
                .map(employeeCreateEditMapper::map)
                .map(employeeRepository::save)
                .orElseThrow();

        if (employeeCreateEditDto.getSpouseId() != null &&
            employeeCreateEditDto.getMaritalStatus() != MaritalStatus.MARRIED) {
            Integer spouseId = employeeCreateEditDto.getSpouseId();
            Employee spouse = employeeRepository.findById(spouseId)
                    .orElseThrow(() -> new IllegalArgumentException("Spouse with id " + spouseId + " not found"));
            Spouses spouses = new Spouses();
            spouses.setFirstSpouse(employee);
            spouses.setSecondSpouse(spouse);
            spousesRepository.save(spouses);
        }
        return employee.getId();
    }

    public Optional<EmployeeReadDto> findById(Integer id) {
        return employeeRepository.findById(id)
                .map(employeeReadMapper::map)
                .map(employee -> {
                    spousesService.findSpouseForEmployee(id)
                            .map(employeeReferenceReadMapper::map)
                            .ifPresent(employee::setSpouse);
                    return employee;
                });
    }


    @Transactional
    public boolean delete(Integer id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employeeRepository.delete(employee);
                    employeeRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public List<EmployeeReadDto> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<EmployeeReadDto> update(Integer id, EmployeeCreateEditDto employeeCreateEditDto) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employeeCreateEditMapper.map(employeeCreateEditDto, employee);
                    if (employeeCreateEditDto.getSpouseId() != null) {
                        Integer spouseId = employeeCreateEditDto.getSpouseId();
                        if (spouseId.equals(employee.getId())) return employee;
                        spousesRepository.findSpouseForEmployeeById(id)
                                .ifPresentOrElse(spouses -> {
                                            if (spouses.getFirstSpouse().getId().equals(id)) {
                                                spouses.setSecondSpouse(employeeRepository.findById(spouseId).orElseThrow());
                                            } else {
                                                spouses.setFirstSpouse(employeeRepository.findById(spouseId).orElseThrow());
                                            }
                                            spousesRepository.save(spouses);
                                        },
                                        () -> {
                                            Spouses newSpouses = new Spouses();
                                            newSpouses.setFirstSpouse(employee);
                                            newSpouses.setSecondSpouse(employeeRepository.findById(employeeCreateEditDto.getSpouseId()).orElseThrow());
                                            spousesRepository.save(newSpouses);
                                        });
                    } else {
                        spousesRepository.findSpouseForEmployeeById(id).ifPresent(spousesRepository::delete);
                    }
                    return employee;
                })
                .map(employeeRepository::saveAndFlush)
                .map(employeeReadMapper::map);
    }

    public Page<EmployeeReferenceReadDto> findEmployeesWithoutSpouses(Pageable pageable) {
        return employeeRepository.findEmployeesWithoutSpouses(pageable)
                .map(employeeReadMapper::map)
                .map(employeeReferenceReadMapper::map);
    }

    public Page<EmployeeReadDto> findAllByEmployeeType(Pageable pageable, EmployeeType employeeType) {
        return employeeRepository.findAllByEmployeeType(pageable, employeeType)
                .map(employeeReadMapper::map);
    }
}
