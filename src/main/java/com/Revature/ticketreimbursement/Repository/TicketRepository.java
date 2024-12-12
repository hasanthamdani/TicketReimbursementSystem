package com.Revature.ticketreimbursement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Revature.ticketreimbursement.Entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

    // For the Employee
    List<Optional<Ticket>> findByEmployee_Username(String username);

    // For the Manager
    
    void deleteById(long id);

    List<Optional<Ticket>> findByStatus(String Status);


}
