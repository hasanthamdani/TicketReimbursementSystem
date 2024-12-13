package com.Revature.ticketreimbursement.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne // Assuming one employee can have many tickets
    @JoinColumn(name = "username", nullable = false)
    private Employee Employee;
    private String description;
    private String status;
    private double amount;

    public Ticket(Employee Employee, String description, String status, double amount)
    {
        this.Employee=Employee;
        this.description=description;
        this.status=status;
        this.amount = amount;
    }
    
}
