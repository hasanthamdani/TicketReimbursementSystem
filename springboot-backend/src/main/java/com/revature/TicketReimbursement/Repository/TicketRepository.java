package com.revature.TicketReimbursement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.TicketReimbursement.Entity.*;

import jakarta.transaction.Transactional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    // For the Employee
    List<Ticket> findByEmployee_Username(String username);

    // For the Manager
    @Query("SELECT t FROM Ticket t WHERE t.status = 'pending'")
    List<Ticket> findAllPending();
    
    @Transactional
    @Modifying()
    @Query("update Ticket t set t.status = LOWER(:status) where t.id = :id")
    int findByIdAndUpdateStatus(@Param("status") String status, @Param("id")Long id);
    
    //For Both
    Ticket save(Ticket t);


}
