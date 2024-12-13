package com.revature.TicketReimbursement.Entity;
import com.revature.TicketReimbursement.Entity.Employee;
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
@Data public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne // Assuming one employee can have many tickets
    @JoinColumn(name = "username", nullable = false)
    private Employee employee;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private double amount;

    public Ticket(Employee employee, String description, String status, double amount)
    {
        this.employee=employee;
        this.description=description;
        this.status=status;
        this.amount = amount;
    }
    
}
