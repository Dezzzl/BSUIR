package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.entity.pet.FeedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedTypeRepository extends JpaRepository<FeedType, Integer> {
}
