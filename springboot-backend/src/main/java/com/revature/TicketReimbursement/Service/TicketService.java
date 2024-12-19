package com.revature.TicketReimbursement.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Repository.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {

    private TicketRepository ticketRepository;

    /*
     * All methods tested via Data Integration Tests.
     */

    public Ticket createTicket(Ticket t)
    {
        // The React App will format the JSON correctly
        return ticketRepository.save(t);
    }
    public List<Ticket> viewAllPendingTickets()
    {   
        return ticketRepository.findAllPending();
    }
    public List<Ticket> viewEmployeeTickets(Employee emp)
    {
        return ticketRepository.findByEmployee_Username(emp.getUsername());
    }
    public int updateTicketStatus(String status, Long Id)
    {
        /*
         * Input will be buttons, there will be no room for error.
         */
        return ticketRepository.findByIdAndUpdateStatus(status, Id);
    }
    
}
