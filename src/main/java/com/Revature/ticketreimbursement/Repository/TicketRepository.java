package com.revature.TicketReimbursement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.TicketReimbursement.Entity.*;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    // For the Employee
    List<Optional<Ticket>> findByEmployee_Username(String username);

    // For the Manager
    @Query("SELECT t FROM Ticket t WHERE LOWER(t.status) = LOWER(:status)")
    List<Ticket> findAllByStatusIgnoreCase(@Param("status") String status);

}
