package com.leave.project.REPO;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leave.project.MODELS.Employee;
import com.leave.project.MODELS.LeaveHistoryDetails;
import com.leave.project.UTILITIES.Status;


public interface LeaveDetailsRepository extends JpaRepository<LeaveHistoryDetails, Integer> {

	List<LeaveHistoryDetails> findByEmployeeAndLeaveStatus(Employee emp,Status leaveStatus);
	List<LeaveHistoryDetails> findByEmployee(Employee emp);
	
//	@Query(value = "SELECT l FROM LeaveHistoryDetails l WHERE  l.startDate AND l.endDate between :startDate and :endDate ")
//    public List<LeaveHistoryDetails> queryByStartDateAndEndDate(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
//

}

