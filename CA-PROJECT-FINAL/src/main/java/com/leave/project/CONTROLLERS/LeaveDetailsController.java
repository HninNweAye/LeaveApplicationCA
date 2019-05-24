package com.leave.project.CONTROLLERS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.leave.project.MODELS.Employee;
import com.leave.project.MODELS.LeaveHistoryDetails;
import com.leave.project.MODELS.LeaveType;
import com.leave.project.MODELS.Role;
import com.leave.project.REPO.EmployeeRepository;
import com.leave.project.REPO.LeaveDetailsRepository;
import com.leave.project.REPO.LeaveTypeRepository;
import com.leave.project.UTILITIES.Status;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Controller
public class LeaveDetailsController {

	private LeaveDetailsRepository leaveRepo;
    private List<LeaveHistoryDetails> leaveList;
    private List<LeaveHistoryDetails> toExportList = new ArrayList<LeaveHistoryDetails>();

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
    
    @RequestMapping(path = "/leave/approval_list", method = RequestMethod.GET)
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
    
    
    @RequestMapping(path = "/leave/filter", method = RequestMethod.GET)
    public String filterLeaveHistory(@RequestParam String leave_type,@RequestParam String status,@RequestParam String start_date,@RequestParam String end_date,Model model) {
    	boolean dateFilter = false;
    	toExportList.clear();
    	if(start_date != "" && end_date != "") {
    	dateFilter = true;
    	toExportList = leaveList.stream().filter(leave -> leave.check(start_date,end_date)).collect(Collectors.toList());
    	}
    	
    	if(leave_type.equals("ALL") && status.equals("ALL")) {
    		if(!dateFilter) {
    			return "redirect:/leave/leave_history";
    		}
    	}else if(!leave_type.equals("ALL") && !status.equals("ALL")) {
    		toExportList = leaveList.stream().filter(leave -> (leave.getLeaveType().getType().equals(leave_type) && leave.getLeaveStatus().getStatus().equals(status))).collect(Collectors.toList());
    	}else if(status.equals("ALL")) {
    		toExportList = leaveList.stream().filter(leave -> leave.getLeaveType().getType().equals(leave_type)).collect(Collectors.toList());
    	}else if(leave_type.equals("ALL") ) {
    		toExportList = leaveList.stream().filter(leave -> leave.getLeaveStatus().getStatus().equals(status)).collect(Collectors.toList());
    	}
        model.addAttribute("status",status); 
        model.addAttribute("leave_type",leave_type);
    	model.addAttribute("leave_list",toExportList);
        model.addAttribute("leave_type_lists",leaveTypeRepo.findAll());

    	return "leave_history";
    }
    
    
  //Export  to CSV file
    @GetMapping("/leave/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        //set file name and content type
        String filename = "LeaveList.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<LeaveHistoryDetails> writer = new StatefulBeanToCsvBuilder<LeaveHistoryDetails>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER) 
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        // leaveRepo.findAll(new Sort(Sort.Direction.DESC, "id"));
               
        writer.write(toExportList);
        }
    
    @RequestMapping(path = "leave/edit_leave/{leave_history_id}", method = RequestMethod.GET)
	public String updateleave(Model model,@PathVariable(value = "leave_history_id") String leave_history_id) {
		LeaveHistoryDetails lhd = leaveRepo.findById(Integer.valueOf(leave_history_id)).orElse(null);
		model.addAttribute("updateleave", lhd); 
		return "edit_leave";
	}
    
    @RequestMapping(path = "leave/leave_detail/{leave_history_id}", method = RequestMethod.GET)
   	public String leaveDetail(Model model,@PathVariable(value = "leave_history_id") String leave_history_id) {
   		LeaveHistoryDetails lhd = leaveRepo.findById(Integer.valueOf(leave_history_id)).orElse(null);
   		model.addAttribute("leave_detail", lhd); 
   		return "leave_detail";
   	}
    
	@RequestMapping(path = "leave/edit_leave/{leave_id}", method=RequestMethod.POST)
	public String saveleavestatus(String action,@PathVariable(value = "leave_id") String leave_history_id,@RequestParam("reasons") String reasons ) {
		LeaveHistoryDetails ldh = leaveRepo.findById(Integer.valueOf(leave_history_id)).orElse(null);
		Employee emp = ldh.getEmployee();
		String leave_type =ldh.getLeaveType().getType();
		
		
		if(action.equals("2"))
		{
			ldh.setLeaveStatus(Status.REJECTED);
			ldh.setRejectionReason(reasons);
		}
		else if (action.equals("1"))
		{
			int count = ldh.getLeaveCount();
			if(leave_type.equals("AnnualLeave")) {
				emp.setAnnualLeaveCount(emp.getAnnualLeaveCount()-count);
			}else if(leave_type.equals("MedicalLeave")) {
				emp.setMedicalLeaveCount(emp.getMedicalLeaveCount()-count);
			}else {
				emp.setCompensationLeaveCount(emp.getCompensationLeaveCount()-count);
			}
			ldh.setLeaveStatus(Status.APPROVED);
			employeeRepo.save(emp);
		}
		//else 
		leaveRepo.save(ldh);
		return "redirect:/leave/approval_list";
	}
}
