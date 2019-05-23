package com.leave.project.REPO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leave.project.MODELS.Employee;
import com.leave.project.MODELS.LeaveHistoryDetails;
import com.leave.project.UTILITIES.Status;


public interface LeaveDetailsRepository extends JpaRepository<LeaveHistoryDetails, Long> {
	List<LeaveHistoryDetails> findByEmployeeAndLeaveStatus(Employee emp,Status leaveStatus);

}

