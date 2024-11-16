package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.pet.reptile.Reptile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReptileRepository extends JpaRepository<Reptile, Integer> {
    @Query("SELECT r FROM Reptile r " +
           "LEFT JOIN FETCH r.habitatZone hz " +
           "LEFT JOIN FETCH r.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH r.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "WHERE r.id = :id")
    Optional<Reptile> findPetById(Integer id);

    @Query("SELECT new org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto(r.id, r.name, r.birthdate, r.sex, 'reptile', r.feedingRation.name) FROM Reptile r")
    List<PetReferencesReadDto> findAllReptilesBasicInfo();

    @Query("SELECT r FROM Reptile r " +
           "LEFT JOIN FETCH r.habitatZone hz " +
           "LEFT JOIN FETCH r.feedingRation fr " +
           "LEFT JOIN FETCH fr.feedType ft " +
           "LEFT JOIN FETCH r.employeePets ep " +
           "LEFT JOIN FETCH ep.employee e " +
           "WHERE TYPE(r) = Reptile AND r.name = :name")
    List<Reptile> findByName(@Param("name") String name);
}
