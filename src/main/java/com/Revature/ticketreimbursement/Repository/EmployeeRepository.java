package com.revature.TicketReimbursement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.TicketReimbursement.Entity.*;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
    
    Optional<Employee> findByUsername(String Username);

    Boolean existsByUsername(String Username);
    
    Optional<Employee> findByUsernameAndIsManagerTrue(String Username);
}
