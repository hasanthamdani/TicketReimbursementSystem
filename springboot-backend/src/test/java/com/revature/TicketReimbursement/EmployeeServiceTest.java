package com.revature.TicketReimbursement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
    public void testNewRegister()
    {
        Employee emp = new Employee("username", "password", false);
        when(employeeRepository.save(emp)).thenReturn(emp);
        when(employeeRepository.existsByUsername(emp.getUsername())).thenReturn(false);

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

        assertThrowsExactly(AccountDuplicateException.class, () -> {employeeService.register(emp);});

        verify(employeeRepository, times(0)).save(emp);
    }

    @Test
    public void testNewLogin()
    {
        
        Optional<Employee> emp = Optional.of(new Employee("username", "password", false));
        
        // Instructs mock what to do
        when(employeeRepository.findById(emp.get().getUsername())).thenReturn(emp);

        assertEquals(emp.get(), employeeService.login(emp.get()).get());

        verify(employeeRepository, times(1)).findById(emp.get().getUsername());
    }
    @Test
    public void testFailLogin()
    {
        
        Employee emp = new Employee("username", "password", false);
        
        // Instructs mock what to do
        when(employeeRepository.findById(emp.getUsername())).thenReturn(Optional.empty());

        assertEquals(null, (employeeService.login(emp)));

        verify(employeeRepository, times(1)).findById(emp.getUsername());
    }

    /*
     * All other methods are simply returning repostiory methods that have been tested.
     */


}
