package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {


    @Query("SELECT p FROM Pet p " +
           "LEFT JOIN FETCH p.habitatZone hz " +
           "LEFT JOIN FETCH p.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH p.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "WHERE p.id = :id")
    Optional<Pet> findPetById(@Param("id") Integer id);

    @Query("SELECT new org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto(p.id, p.name, p.birthdate, p.sex, 'pet', p.feedingRation.name) FROM Pet p WHERE TYPE(p) = Pet")
    List<PetReferencesReadDto> findAllSimplePetsBasicInfo();


    @Query("SELECT p FROM Pet p " +
           "LEFT JOIN FETCH p.habitatZone hz " +
           "LEFT JOIN FETCH p.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH p.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "WHERE TYPE(p) = Pet AND p.name = :name")
    List<Pet> findByName(@Param("name") String name);
}
