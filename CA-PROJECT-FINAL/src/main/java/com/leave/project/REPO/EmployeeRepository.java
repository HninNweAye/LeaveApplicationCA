package com.leave.project.REPO;


import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.project.MODELS.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	List<Employee> findByReportsTo(Employee emp);

}
