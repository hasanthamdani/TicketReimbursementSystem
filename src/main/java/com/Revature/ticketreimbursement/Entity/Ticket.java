package com.Revature.ticketreimbursement.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Employee Employee;
    private String status;
    private double amount;
    
}
