package com.revature.TicketReimbursement;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;
import com.revature.TicketReimbursement.Repository.TicketRepository;
import com.revature.TicketReimbursement.Service.EmployeeService;
import com.revature.TicketReimbursement.Service.TicketService;

import lombok.extern.java.Log;

@ExtendWith(MockitoExtension.class)
@Log
public class TicketServiceTest {


    @Mock
    private TicketRepository ticketRepository;

     @InjectMocks
    private TicketService ticketService;

    @Test
    public void testCreateTicket()
    {
        Employee e = new Employee("username","password",false);
        Ticket t = new Ticket(e, "description", "pending", 1);
        when(ticketRepository.save(t)).thenReturn(t);
        Ticket t_saved = ticketService.createTicket(t);
        Assertions.assertEquals(t_saved, t);
        verify(ticketRepository, times(1)).save(t);
    }

    @Test
    public void testCreateTicketFail()
    {
        when(ticketRepository.save(null)).thenReturn(null);
        Ticket t_saved = ticketService.createTicket(null);
        Assertions.assertNull(t_saved);
        verify(ticketRepository, times(1)).save(null);
    }
    
    @Test
    public void testFindTicket()
    {
        long id = 1L;
        Employee e = new Employee("username","password",false);
        Ticket t = new Ticket(e, "description", "pending", 1);
        when(ticketRepository.findById(id)).thenReturn(Optional.of(t));

        Optional<Ticket> t_saved = ticketService.findTicket(id);

        Assertions.assertEquals(t_saved, Optional.of(t));
        verify(ticketRepository, times(1)).findById(id);
    }

    @Test
    public void testFindTicketFail()
    {
        long id = 1L;
        Employee e = new Employee("username","password",false);
        Ticket t = new Ticket(e, "description", "pending", 1);
        when(ticketRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Ticket> t_saved = ticketService.findTicket(id);

        Assertions.assertEquals(t_saved, Optional.empty());
        verify(ticketRepository, times(1)).findById(id);
    }

    @Test
    public void testViewPendingTickets()
    {
        Employee e1 = new Employee("username","password",false);
        Ticket t1 = new Ticket(e1, "description", "pending", 1);
        Employee e2 = new Employee("username2","password",false);
        Ticket t2 = new Ticket(e2, "description", "pending", 1);
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(t1);
        ticketList.add(t2);

        when(ticketRepository.findAllPending()).thenReturn(ticketList);
    
        List<Ticket> persistedList = ticketService.viewAllPendingTickets();
        Assertions.assertEquals(persistedList, ticketList);
        verify(ticketRepository, times(1)).findAllPending();
    }
    @Test
    public void testViewPendingTicketsFail()
    {
        Employee e1 = new Employee("username","password",false);
        Ticket t1 = new Ticket(e1, "description", "accepted", 1);
        Employee e2 = new Employee("username2","password",false);
        Ticket t2 = new Ticket(e2, "description", "denied", 1);
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(t1);
        ticketList.add(t2);

        when(ticketRepository.findAllPending()).thenReturn(new ArrayList<Ticket>());
    
        List<Ticket> persistedList = ticketService.viewAllPendingTickets();
        Assertions.assertEquals(new ArrayList<Ticket>(), persistedList);
        verify(ticketRepository, times(1)).findAllPending();
    }

    @Test
    public void testViewEmployeeTickets()
    {
        Employee e1 = new Employee("username","password",false);
        Ticket t1 = new Ticket(e1, "description", "accepted", 1);

       String username = "username";
       List<Ticket> ticketList = new ArrayList<Ticket>();
       ticketList.add(t1);
        	
        when(ticketRepository.findByEmployee_Username(username)).thenReturn(ticketList);
    
        List<Ticket> persistedList = ticketService.viewEmployeeTickets(username);
        Assertions.assertEquals(ticketList, persistedList);
        verify(ticketRepository, times(1)).findByEmployee_Username(username);
    }
    @Test
    public void testViewEmployeeTicketsFail()
    {
        Employee e1 = new Employee("username","password",false);
        Ticket t1 = new Ticket(e1, "description", "accepted", 1);

       String username = "";
       List<Ticket> ticketList = new ArrayList<Ticket>();
        	
        when(ticketRepository.findByEmployee_Username(username)).thenReturn(ticketList);
    
        List<Ticket> persistedList = ticketService.viewEmployeeTickets(username);
        Assertions.assertEquals(ticketList, persistedList);
        verify(ticketRepository, times(1)).findByEmployee_Username(username);
    }

    @Test
    public void testUpdateTicketStatus()
    {
        Employee e1 = new Employee("username","password",false);
        Ticket t1 = new Ticket(e1, "description", "accepted", 1);

       String status = "accepted";
       long Id = 1;
        	
        when(ticketRepository.findByIdAndUpdateStatus(status, Id)).thenReturn(1);
    
        int updatedTicketNum = ticketService.updateTicketStatus(status, Id);
        Assertions.assertEquals(1, updatedTicketNum);
        verify(ticketRepository, times(1)).findByIdAndUpdateStatus(status, Id);
    }

    @Test
    public void testUpdateTicketStatusFail()
    {
        Employee e1 = new Employee("username","password",false);
        Ticket t1 = new Ticket(e1, "description", "accepted", 1);

       String status = "accepted";
       long Id = 1;
        	
        when(ticketRepository.findByIdAndUpdateStatus(status, Id)).thenReturn(0);
    
        int updatedTicketNum = ticketService.updateTicketStatus(status, Id);
        Assertions.assertEquals(0, updatedTicketNum);
        verify(ticketRepository, times(1)).findByIdAndUpdateStatus(status, Id);
    }
    
}
