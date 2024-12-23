package com.revature.TicketReimbursement;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;

import jakarta.persistence.EntityExistsException;
import lombok.extern.java.Log;

@DataJpaTest
@Log
class EmployeeRepositoryTest {
    
    @Autowired
    // Implementation of JPA Interfaces
    private TestEntityManager entityManager; // 

    @Autowired
    private EmployeeRepository employeeRepository;

    
    //First Test: Make sure save persists data
    @Test
    public void testSaveUser()
    {
        Employee emp = new Employee("testuser", "testpassword", false);
        Employee persistedEmp = employeeRepository.save(emp);
        
        Assertions.assertTrue(persistedEmp.getUsername().equals("testuser"));
        Assertions.assertTrue(persistedEmp.getPassword().equals("testpassword"));
    }
    
    // Test the ability to find a username by ID
    @Test
    public void testFindEmployee()
    {
        // Persist Base Employee to be Found
        Employee emp = new Employee("testuser", "testpassword", false);

        Employee persistedEmp = entityManager.persistAndFlush(emp);

        Optional<Employee> retrievedEmp = employeeRepository.findById(persistedEmp.getUsername());

        Assertions.assertTrue(retrievedEmp.isPresent());
        Assertions.assertTrue(retrievedEmp.get().getUsername().equals("testuser"));
        Assertions.assertTrue(retrievedEmp.get().getPassword().equals("testpassword"));
        Assertions.assertTrue(!retrievedEmp.get().isManager());
    }

    // Test the ability to find if a username exists
    @Test
    public void testExistsEmployee()
    {
        // Persist Base Employee to be Found
        Employee emp = new Employee("testuser", "testpassword", false);

        Employee persistedEmp = entityManager.persistAndFlush(emp);

        Boolean retrievedEmp = employeeRepository.existsByUsername(persistedEmp.getUsername());

        Assertions.assertTrue(retrievedEmp);

        Boolean retrievedEmp2 = employeeRepository.existsByUsername("not");
        Assertions.assertTrue(retrievedEmp2.equals(false));
    }
    //Test if duplicate usernames would cause an error.
    @Test
    public void testduplicateUsername()
    {
        Employee emp1 = new Employee("testuser", "testpassword", false);
        Employee emp2 = new Employee("testuser", "testpassword", false);

        Employee perEmp1 = entityManager.persistAndFlush(emp1);

        Assertions.assertThrowsExactly(EntityExistsException.class, () -> {entityManager.persistAndFlush(emp2);});
    }

    
}
