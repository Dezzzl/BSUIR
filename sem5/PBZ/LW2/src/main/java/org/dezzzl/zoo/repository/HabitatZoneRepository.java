package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.pet.HabitatZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitatZoneRepository extends JpaRepository<HabitatZone, Integer> {
}
