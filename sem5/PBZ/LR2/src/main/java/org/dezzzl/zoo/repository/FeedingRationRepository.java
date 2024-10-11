package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.entity.pet.FeedingRation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedingRationRepository {
    Optional<FeedingRation> findById(Integer id);

    List<FeedingRation> findAll();

    FeedingRation save(FeedingRation feedingRation);

    Integer update(FeedingRation feedingRation);

    Integer delete(Integer id);
}
