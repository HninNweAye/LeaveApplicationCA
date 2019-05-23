package com.leave.project.CONTROLLERS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leave.project.REPO.EmployeeRepository;



@Controller
@RequestMapping("/employees")
public class EmployeeController {
	private EmployeeRepository employeeRepo;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    

    @RequestMapping(path = "/{id}/list", method = RequestMethod.GET)
    public String showEmployeeList(Model model,@PathVariable(value = "id") String id) {
    	
//        model.addAttribute("employees",employeeRepo.find);
        return "approval_list";
    }
	
}
