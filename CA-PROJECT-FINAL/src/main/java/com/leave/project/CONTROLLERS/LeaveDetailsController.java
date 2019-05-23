package com.leave.project.CONTROLLERS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leave.project.MODELS.Employee;
import com.leave.project.MODELS.LeaveHistoryDetails;
import com.leave.project.MODELS.Role;
import com.leave.project.REPO.EmployeeRepository;
import com.leave.project.REPO.LeaveDetailsRepository;
import com.leave.project.UTILITIES.Status;

@Controller
public class LeaveDetailsController {

	private LeaveDetailsRepository leaveRepo;

    @Autowired
    public void setEmployeeRepository(LeaveDetailsRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }
    
    private EmployeeRepository employeeRepo;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    
    @RequestMapping(path = "/leave_approval/list", method = RequestMethod.GET)
    public String showEmployeeList(Model model) {
    	Role role = new Role(1,"manager");
//    	Employee emp = new Employee (5,"smith@gmail.com","Smith","smith","smith",role);
    	Employee emp = new Employee (1,"aaa@gmail.com","AAA","aaa","aaa",role);

    	List<Employee> empList = employeeRepo.findByReportsTo(emp);
    	List<LeaveHistoryDetails> leaveList = new ArrayList<LeaveHistoryDetails>();
    	List<LeaveHistoryDetails> temp = new ArrayList<LeaveHistoryDetails>();

    	Iterator<Employee> i = empList.iterator();
		while (i.hasNext()) {
			temp = leaveRepo.findByEmployeeAndLeaveStatus(i.next(),Status.APPLIED );
			if(temp.size() != 0)leaveList.addAll(temp);

		}
        model.addAttribute("leave_list",leaveList);

        return "approval_list";
    }
	
}
