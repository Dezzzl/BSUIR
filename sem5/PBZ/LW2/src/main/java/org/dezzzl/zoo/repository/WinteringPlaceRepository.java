package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.entity.pet.bird.WinteringPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinteringPlaceRepository extends JpaRepository<WinteringPlace, Integer> {

}
