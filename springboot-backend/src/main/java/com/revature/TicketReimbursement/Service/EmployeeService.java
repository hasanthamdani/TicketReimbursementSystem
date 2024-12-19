package com.revature.TicketReimbursement.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Exception.AccountDuplicateException;
import com.revature.TicketReimbursement.Exception.UnauthorizedLoginException;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;

    public Boolean accountExists(String username)
    {
        return employeeRepository.existsById(username);
    }

    public Employee register(Employee emp) throws AccountDuplicateException
    {
        /*
         * React app will ensure the json object is properly formatted
         * If the username exists, exception is thrown
         */
        if(accountExists(emp.getUsername()))
        {
            throw new AccountDuplicateException("This username has already been registered");
        }
        return employeeRepository.save(emp);
    }
    public Optional<Employee> login(Employee emp){
        Optional<Employee> returnedEmp = employeeRepository.findById(emp.getUsername());
        if(!returnedEmp.isPresent())
        {
            return null;
        }
        // else if(returnedEmp.get().isManager())
        // {
        //     throw new UnauthorizedLoginException("Your account's privileges do not match this form");
        // }
        return returnedEmp;
    }

    public Optional<Employee> findAccount(String username)
    {
        return employeeRepository.findById(username);
    } 

}
