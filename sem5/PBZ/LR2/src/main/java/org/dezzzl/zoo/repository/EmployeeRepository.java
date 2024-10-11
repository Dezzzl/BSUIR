package org.dezzzl.zoo.repository;

import org.dezzzl.zoo.entity.employee.Employee;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("select e from Employee e " +
           "where e.id not in (select s.firstSpouse.id from Spouses s) " +
           "and e.id not in (select s.secondSpouse.id from Spouses s)")
    Page<Employee> findEmployeesWithoutSpouses(Pageable pageable);

    Page<Employee>findAllByEmployeeType(Pageable pageable, EmployeeType employeeType);

}
