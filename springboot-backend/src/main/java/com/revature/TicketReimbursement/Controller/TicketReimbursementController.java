package com.revature.TicketReimbursement.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Exception.AccountDuplicateException;
import com.revature.TicketReimbursement.Service.EmployeeService;
import com.revature.TicketReimbursement.Service.TicketService;

import lombok.AllArgsConstructor;

@RestController
@ResponseBody
@AllArgsConstructor
public class TicketReimbursementController {
    
    @Autowired
    private TicketService ticketService;
    private EmployeeService employeeService;

    /*
    * Account Registration
    * @param @RequestBody Employee
    * @return ResponseEntity<Employee> 
    * 
    * AccountDuplicateException will be thrown if account details exist

    ONLY EMPLOYEES
    */

    @PostMapping("register")
    public ResponseEntity<Employee> registerHandler(@RequestBody Employee employee) throws AccountDuplicateException
    {
        if(employeeService.register(employee) == null)
        {
            //Status Code: 400 implies a bad request
            return ResponseEntity.status(400).build();
        }
        // Throws AccountDuplicateException if the username already exists.
        Employee emp = employeeService.register(employee);
        return ResponseEntity.status(200).body(emp);
    }

    /*
     * Manager's credentials are in database for security reasons, they can only login.
     */
    @PostMapping("login")
    public ResponseEntity<Employee> loginHandler(@RequestBody Employee employee)
    {   
        Optional<Employee> emp = employeeService.login(employee);

        if(emp.isEmpty())
        {
            //Status Code: 404 implies the resource is not there
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(emp.get());
    }

    /*
     * As an Employee I can create tickets and view all of my tickets.
     */
    @PostMapping("create")
    public ResponseEntity<Ticket> createTicketHandler(@RequestBody Ticket ticket)
    {
        Ticket newTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(201).body(newTicket);
    }
    
    @GetMapping("tickets")
    public ResponseEntity<List<Ticket>> viewEmployeeTicketsHandler(@RequestBody Employee employee)
    {
        List<Ticket> employeeTickets = ticketService.viewEmployeeTickets(employee);
        return ResponseEntity.status(200).body(employeeTickets);
    }
    /*
     * As a Manager I can view all pending tickets irrespective of employee and I can update their status.
     */

    @GetMapping("manager/tickets")
    public ResponseEntity<List<Ticket>> viewAllPendingTicketsHandler()
    {
        List<Ticket> employeeTickets = ticketService.viewAllPendingTickets();
        return ResponseEntity.status(200).body(employeeTickets);
    }

    @PatchMapping("manager/tickets")
    public ResponseEntity<Integer> updateStatusHandler(@RequestParam Long ticketId, @RequestParam String status)
    {
        int numUpdated = ticketService.updateTicketStatus(status, ticketId);
        return ResponseEntity.status(200).body(numUpdated);
    }



    
    /*
    * Account Duplicate Exception
    * @return ResponseEntity<String> 
    * 
    * AccountDuplicateException will be thrown if account details exist
    */
    @ExceptionHandler(AccountDuplicateException.class)
    public ResponseEntity<String> duplicateUsernameHandler() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
