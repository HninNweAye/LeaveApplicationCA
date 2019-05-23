package com.leave.project.REPO;


import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.project.MODELS.LeaveType;



@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

}
