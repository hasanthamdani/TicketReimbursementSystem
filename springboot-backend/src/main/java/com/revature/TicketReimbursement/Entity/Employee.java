package com.revature.TicketReimbursement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="employee")
@NoArgsConstructor
@AllArgsConstructor
@Generated
@Data public class Employee {
    @Id
    private String username;

    @Column(name = "password")
    private String password;
    
    @Column(name = "isManager")
    private boolean isManager;
    
}

