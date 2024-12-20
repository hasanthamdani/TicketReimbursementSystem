package com.revature.TicketReimbursement.DataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository=employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load initial data into the database
        employeeRepository.save(new Employee("testManager", "test", true));
    }
}