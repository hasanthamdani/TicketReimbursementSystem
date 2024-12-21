package com.revature.TicketReimbursement.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ticket")
@Generated
@Data public class Ticket {
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id;

    @ManyToOne // Assuming one employee can have many tickets
    @JoinColumn(name = "username", nullable = false)
    private Employee employee;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private double amount;

    public Ticket() {}
    public Ticket(Employee employee, String description, String status, double amount)
    {
        this.employee=employee;
        this.description=description;
        this.status=status;
        this.amount = amount;
    }
    
}
