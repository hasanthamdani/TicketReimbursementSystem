package com.Revature.ticketreimbursement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Revature.ticketreimbursement.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
    
    Employee findByUsername(String Username);
    Boolean existsByUsername(String Username);
    Employee findByUsernameAndIsManager(String Username, Boolean isManager);
}
