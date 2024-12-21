package com.revature.TicketReimbursement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Exception.AccountDuplicateException;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;
import com.revature.TicketReimbursement.Service.EmployeeService;

import lombok.extern.java.Log;

@ExtendWith(MockitoExtension.class)
@Log
public class EmployeeServiceTest {
    
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testAccountExists()
    {
        String username = "";
        when(employeeRepository.existsById(username)).thenReturn(true);
        Assertions.assertTrue(employeeService.accountExists(username));
        verify(employeeRepository, times(1)).existsById(username);
    }

    @Test
    public void testAccountExistsFail()
    {
        String username = "";
        when(employeeRepository.existsById(username)).thenReturn(false);
        Assertions.assertFalse(employeeService.accountExists(username));
        verify(employeeRepository, times(1)).existsById(username);
    }
    @Test
    public void testNewRegister()
    {
        Employee emp = new Employee("username", "password", false);
        when(employeeRepository.save(emp)).thenReturn(emp);
        // when(employeeRepository.existsByUsername(emp.getUsername())).thenReturn(false);

        Employee persistedEmployee = employeeService.register(emp);
        log.info(persistedEmployee.toString());
        
        assertEquals(emp, persistedEmployee);
        verify(employeeRepository, times(1)).save(emp);
    }

    @Test
    public void testDuplicateRegister()
    {
        Employee emp = new Employee("username", "password", false);
        
        // Instructs mock what to do
        when(employeeRepository.existsById(emp.getUsername())).thenReturn(true);

        Employee persistedEmployee = employeeService.register(emp);

        verify(employeeRepository, times(0)).save(emp);
    }

    @Test
    public void testNewLogin()
    {
        
        Employee emp = new Employee("username", "password", false);
        
        // Instructs mock what to do
        when(employeeRepository.findById(emp.getUsername())).thenReturn(Optional.of(emp));

        assertEquals(emp, employeeService.login(emp.getUsername()).get());

        verify(employeeRepository, times(1)).findById(emp.getUsername());
    }
    @Test
    public void testFailLogin()
    {
        
    Employee emp = new Employee("username", "password", false);
        
    // Instructs mock what to do
    when(employeeRepository.findById(emp.getUsername())).thenReturn(Optional.empty());

    assertEquals(Optional.empty(), (employeeService.login(emp.getUsername())));

    verify(employeeRepository, times(1)).findById(emp.getUsername());
    }
    //

    @Test
    public void testFindAccount()
    {
        
    Optional<Employee> emp = Optional.of(new Employee("username", "password", false));
        
    // Instructs mock what to do
    when(employeeRepository.findById(emp.get().getUsername())).thenReturn(emp);

    assertEquals(emp, (employeeService.findAccount(emp.get().getUsername())));

    verify(employeeRepository, times(1)).findById(emp.get().getUsername());
    }

    @Test
    public void testFindAccountFail()
    {
        
    Employee emp = new Employee("username", "password", false);
        
    // Instructs mock what to do
    when(employeeRepository.findById(emp.getUsername())).thenReturn(Optional.empty());

    assertEquals(Optional.empty(), (employeeService.findAccount(emp.getUsername())));

    verify(employeeRepository, times(1)).findById(emp.getUsername());
    }

}
