package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.bird.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BirdRepository extends JpaRepository<Bird, Integer> {
    @Query("SELECT b FROM Bird b " +
           "LEFT JOIN FETCH b.habitatZone hz " +
           "LEFT JOIN FETCH b.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH b.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "LEFT JOIN FETCH b.winteringPlace w " +
           "WHERE b.id = :id")
    Optional<Bird> findPetById(Integer id);

    @Query("SELECT new org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto(b.id, b.name, b.birthdate, b.sex, 'bird', b.feedingRation.name) FROM Bird b")
    List<PetReferencesReadDto> findAllBirdsBasicInfo();

    @Query("SELECT b FROM Bird b " +
           "LEFT JOIN FETCH b.habitatZone hz " +
           "LEFT JOIN FETCH b.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH b.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "LEFT JOIN FETCH b.winteringPlace w " +
           "WHERE TYPE(b) = Bird AND b.name = :name")
    List<Bird> findByName(@Param("name") String name);
}
