package com.revature.TicketReimbursement;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Repository.TicketRepository;

import lombok.extern.java.Log;

@DataJpaTest
@Log
public class TicketRepositoryTest
{

    @Autowired
    // Implementation of JPA Interfaces
    private TestEntityManager entityManager; // 

    @Autowired
    private TicketRepository ticketRepository;


    // Test 1: Check that save persists data and that the ID is created.
    @Test
    public void testSaveTicket()
    {
        Employee emp = new Employee("username", "password", false);
        Ticket tick1 = new Ticket(emp, "description", "pending", 0.0);
        
        entityManager.persist(emp);
        Ticket persistedTicket = ticketRepository.save(tick1);
        assert(persistedTicket.getEmployee().equals(tick1.getEmployee()));
        assert(persistedTicket.getDescription().equals(tick1.getDescription()));
        // assert(persistedTicket.getId().equals(null));

    }

    // Find tickets by Employee Username
    @Test
    public void testfindByUsername()
    {
        Employee emp = new Employee("username0", "password", false);
        Ticket tick1 = new Ticket(emp, "description", "pending", 0.0);
        Ticket tick2 = new Ticket(emp, "description", "pending", 0.0);

        entityManager.persist(emp);
        entityManager.persistAndFlush(tick1);
        entityManager.persistAndFlush(tick2);

        List<Ticket> retrievedTickets = ticketRepository.findByEmployee_Username(emp.getUsername());
       
        assert(retrievedTickets.size()==2);
        assert(retrievedTickets.get(0).getEmployee().getUsername().equals("username0"));
        assert(retrievedTickets.get(1).getEmployee().getUsername().equals("username0"));
    }

    // Delete a ticket object
    @Test
    public void testDeleteById()
    {
        Employee emp = new Employee("username", "password", false);
        Ticket tick1 = new Ticket(emp, "description", "pending", 0.0);
        entityManager.persist(emp);
        Ticket tick2 = entityManager.persistAndFlush(tick1);

        
        ticketRepository.delete(tick2);
        assert(ticketRepository.findById(tick2.getId())).isEmpty();
    }

    //Finding all tickets by status
    @Test
    public void testFindPendingTickets()
    {
        Employee emp1 = new Employee("username1", "password", false);
        Ticket ticket1 = new Ticket(emp1, "description", "pending" , 0.0);
        entityManager.persist(emp1);
        entityManager.persistAndFlush(ticket1);

        Employee emp2 = new Employee("username2", "password", false);
        Ticket ticket2 = new Ticket(emp2, "description", "approved" , 0.0);
        entityManager.persist(emp2);
        entityManager.persistAndFlush(ticket2);
        
        List<Ticket> ticketList = ticketRepository.findAllPending();
        
        assert(ticketList.size()==1);
        assert(ticketList.get(0).getStatus().equals("pending"));
    }

    @Test
    // Updating a ticket's status
    public void testUpdatingStatus()
    {
        Employee emp1 = new Employee("username1", "password", false);
        Ticket ticket1 = new Ticket(emp1, "description", "pending" , 0.0);

        entityManager.persist(emp1);
        entityManager.persistAndFlush(ticket1);

        int numUpdate = ticketRepository.findByIdAndUpdateStatus("approved", ticket1.getId());

        //Ensures persistence context accurately reflect database change
        entityManager.refresh(ticket1);

        Optional<Ticket> ticket2 = ticketRepository.findById(ticket1.getId());

        log.info(ticket2.toString());
        Assertions.assertEquals(1, numUpdate);
        Assertions.assertEquals("approved", ticket2.get().getStatus());
    }
}