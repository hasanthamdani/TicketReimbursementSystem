package com.revature.TicketReimbursement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.TicketReimbursement.Entity.*;

import io.micrometer.common.lang.NonNull;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
    
    
    Employee save(Employee employee);

    Optional<Employee> findById(String Username);

    Boolean existsByUsername(String Username);
    

}
