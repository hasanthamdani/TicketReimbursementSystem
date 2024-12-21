package com.revature.TicketReimbursement.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Repository.TicketRepository;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log
public class TicketService {

    private TicketRepository ticketRepository;

    /*
     * All methods tested via Data Repository Tests.
     */

    public Ticket createTicket(Ticket t)
    {
        // The React App will format the JSON correctly
        
        Ticket t_saved = ticketRepository.save(t);
        return t_saved;
    }

    public Optional<Ticket> findTicket(long id)
    {
        return ticketRepository.findById(id);
    }


    
    public List<Ticket> viewAllPendingTickets()
    {   
        return ticketRepository.findAllPending();
    }

    public List<Ticket> viewEmployeeTickets(String username)
    {
        return ticketRepository.findByEmployee_Username(username);
    }

    public int updateTicketStatus(String status, Long Id)
    {
        /*
         * Input will be buttons, there will be no room for error.
         */
        return ticketRepository.findByIdAndUpdateStatus(status, Id);
    }
    
}
