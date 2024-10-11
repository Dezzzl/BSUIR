package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.reptile.Reptile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReptileRepository extends JpaRepository<Reptile, Integer> {
    @Query("SELECT p FROM Pet p " +
           "LEFT JOIN FETCH p.habitatZone hz " +
           "LEFT JOIN FETCH p.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH p.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "WHERE p.id = :id")
    Optional<Reptile> findPetById(Integer id);

    @Query("SELECT new org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto(r.id, r.name, r.birthdate, r.sex, 'reptile') FROM Reptile r")
    List<PetReferencesReadDto> findAllReptilesBasicInfo();
}
