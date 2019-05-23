package com.leave.project.CONTROLLERS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.leave.project.MODELS.Employee;
import com.leave.project.MODELS.LeaveHistoryDetails;
import com.leave.project.MODELS.Role;
import com.leave.project.REPO.EmployeeRepository;
import com.leave.project.REPO.LeaveDetailsRepository;
import com.leave.project.REPO.LeaveTypeRepository;
import com.leave.project.UTILITIES.Status;

@Controller
public class LeaveDetailsController {

	private LeaveDetailsRepository leaveRepo;
    private List<LeaveHistoryDetails> leaveList;
    @Autowired
    public void setEmployeeRepository(LeaveDetailsRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }
    
    private EmployeeRepository employeeRepo;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    
    private LeaveTypeRepository leaveTypeRepo;

    @Autowired
    public void setLeaveTypeRepository(LeaveTypeRepository leaveTypeRepo) {
        this.leaveTypeRepo = leaveTypeRepo;
    }
    
    @RequestMapping(path = "/leave_approval/list", method = RequestMethod.GET)
    public String showEmployeeList(Model model) {
    	Role role = new Role(1,"manager");
//    	Employee emp = new Employee (5,"smith@gmail.com","Smith","smith","smith",role);
    	Employee emp = new Employee (1,"aaa@gmail.com","AAA","aaa","aaa",role);

    	List<Employee> empList = employeeRepo.findByReportsTo(emp);
    	leaveList = new ArrayList<LeaveHistoryDetails>();
    	List<LeaveHistoryDetails> temp = new ArrayList<LeaveHistoryDetails>();

    	Iterator<Employee> i = empList.iterator();
		while (i.hasNext()) {
			temp = leaveRepo.findByEmployeeAndLeaveStatus(i.next(),Status.APPLIED );
			if(temp.size() != 0)leaveList.addAll(temp);

		}
	
        model.addAttribute("leave_list",leaveList);

        return "approval_list";
    }
    

    @RequestMapping(path = "/leave/leave_history", method = RequestMethod.GET)
    public String showLeaveHistory(Model model) {
    	List<Employee> empList = getEmployeeList();
    	leaveList = new ArrayList<LeaveHistoryDetails>();
    	List<LeaveHistoryDetails> temp = new ArrayList<LeaveHistoryDetails>();

    	Iterator<Employee> i = empList.iterator();
		while (i.hasNext()) {
			leaveList.addAll( leaveRepo.findByEmployee(i.next()));
		}
        model.addAttribute("status","ALL");
        model.addAttribute("leave_type","ALL");
        model.addAttribute("leave_list",leaveList);
        model.addAttribute("leave_type_lists",leaveTypeRepo.findAll());

        return "leave_history";
    }
    
    private List<Employee> getEmployeeList(){
    	Role role = new Role(1,"manager");
//    	Employee emp = new Employee (5,"smith@gmail.com","Smith","smith","smith",role);
    	Employee emp = new Employee (1,"aaa@gmail.com","AAA","aaa","aaa",role);
    	List<Employee> empList = employeeRepo.findByReportsTo(emp);
		return empList;
    }
    //@Param("end_date")
    @RequestMapping(path = "/leave/filter", method = RequestMethod.GET)
    public String filterLeaveHistory(@RequestParam String leave_type,@RequestParam String status,@RequestParam String start_date,@RequestParam String end_date,Model model) {
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    	Date startDate = sdf.parse(start_date);
//    	Date endDate = sdf.parse(end_date);

    	System.out.println("**************************");
    	System.out.println("StartDate :"+start_date);

    	System.out.println(leaveList.size());

    	boolean dateFilter = false;
    	if(start_date != "" && end_date != "") {
    	dateFilter = true;
    	 leaveList = leaveList.stream().filter(leave -> leave.check(start_date,end_date)).collect(Collectors.toList());
    	}
    	System.out.println(leaveList.size());

    	
    	if(leave_type.equals("ALL") && status.equals("ALL")) {
    		if(!dateFilter) {
    			return "redirect:/leave/leave_history";
    		}
    	}else if(!leave_type.equals("ALL") && !status.equals("ALL")) {
    		leaveList = leaveList.stream().filter(leave -> (leave.getLeaveType().getType().equals(leave_type) && leave.getLeaveStatus().getStatus().equals(status))).collect(Collectors.toList());
    	}else if(status.equals("ALL")) {
    		leaveList = leaveList.stream().filter(leave -> leave.getLeaveType().getType().equals(leave_type)).collect(Collectors.toList());
    	}else if(leave_type.equals("ALL") ) {
    		leaveList = leaveList.stream().filter(leave -> leave.getLeaveStatus().getStatus().equals(status)).collect(Collectors.toList());
    	}
        model.addAttribute("status",status); 
        model.addAttribute("leave_type",leave_type);
    	model.addAttribute("leave_list",leaveList);
        model.addAttribute("leave_type_lists",leaveTypeRepo.findAll());

    	return "leave_history";
    }
	
}
