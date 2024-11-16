package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.entity.employee.Spouses;
import org.dezzzl.zoo.repository.id.SpouseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpousesRepository extends JpaRepository<Spouses, SpouseId> {
    @Query("select s from Spouses s " +
           "join fetch s.firstSpouse s1 " +
           "join fetch s.secondSpouse s2 " +
           "where s1.id = :spouseId or s2.id = :spouseId")
    Optional<Spouses> findSpouseForEmployeeById(@Param("spouseId") Integer spouseId);


    @Query("select s from Spouses s " +
           "join fetch s.firstSpouse s1 " +
           "join fetch s.secondSpouse s2")
    List<Spouses> findAllSpouses();

    @Modifying
    @Query(value = "CALL add_spouse(:firstSpouseId, :secondSpouseId)", nativeQuery = true)
    void callAddSpouse(@Param("firstSpouseId") Integer firstSpouseId, @Param("secondSpouseId") Integer secondSpouseId);
}
