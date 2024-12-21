package com.revature.TicketReimbursement.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log
public class EmployeeService {
    
    @Autowired
    private final EmployeeRepository employeeRepository;

    public Boolean accountExists(String username)
    {
        return employeeRepository.existsById(username);
    }

    public Employee register(Employee emp)
    {
        /*
         * React app will ensure the json object is properly formatted
         * If the username exists, exception is thrown
         */
        
        log.info("Service Username: " + emp.getUsername());
        if(accountExists(emp.getUsername()))
        {
           return null;
        }

        Employee savedEmployee = employeeRepository.save(emp);
        log.info("Saved Employee: " + savedEmployee.toString());
        return savedEmployee;
    }

    public Optional<Employee> login(String username){
        Optional<Employee> returnedEmp = employeeRepository.findById(username);
        if(!returnedEmp.isPresent())
        {
            return Optional.empty();
        }
        return returnedEmp;
    }

    public Optional<Employee> findAccount(String username)
    {
        return employeeRepository.findById(username);
    } 

}
