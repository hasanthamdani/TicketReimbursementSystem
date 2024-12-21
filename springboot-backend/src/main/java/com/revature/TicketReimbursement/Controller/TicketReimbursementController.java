package com.revature.TicketReimbursement.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import lombok.extern.java.Log;

@RestController
@ResponseBody
@Log
@CrossOrigin(origins = "http://localhost:3000")
public class TicketReimbursementController {

    private TicketService ticketService;
    private EmployeeService employeeService;
    
    @Autowired
    public TicketReimbursementController(TicketService ticketService, EmployeeService employeeService)
    {
        this.ticketService = ticketService;
        this.employeeService = employeeService;
    } 
    

    /*
    * Account Registration
    * @param @RequestBody Employee
    * @return ResponseEntity<Employee> 
    * 
    * AccountDuplicateException will be thrown if account details exist

    ONLY EMPLOYEES
    */

    @PostMapping(value="/register")
    public ResponseEntity<Employee> registerHandler(@RequestBody Employee employee) throws AccountDuplicateException
    {
        Employee emp = employeeService.register(employee);

        if(emp == null)
        {
            throw new AccountDuplicateException("This username has already been registered");
        }
        log.info("Controller Register Method: " + emp.toString());
        return ResponseEntity.status(201).body(emp);
    }

    /*
     * Manager's credentials are in database for security reasons, they can only login.
     */
    @GetMapping("login/{username}")
    public ResponseEntity<Employee> loginHandler(@PathVariable String username)
    {   
        // log.info("Login Username: " + username);
        Optional<Employee> emp = employeeService.login(username);

        if(emp.isEmpty())
        {
            //Status Code: 404 implies the resource is not there
            return ResponseEntity.status(404).build();
        }
        // log.info("Login Account " + emp);
        return ResponseEntity.status(200).body(emp.get());
    }

    /*
     * As an Employee I can create tickets and view all of my tickets.
     */
    @PostMapping("create")
    public ResponseEntity<Ticket> createTicketHandler(@RequestBody Ticket ticket)
    {
        Ticket newTicket = ticketService.createTicket(ticket);
        log.info(newTicket.toString());
        return ResponseEntity.status(201).body(newTicket);
    }
    
    @GetMapping("tickets/{username}")
    public ResponseEntity<List<Ticket>> viewEmployeeTicketsHandler(@PathVariable String username)
    {
        List<Ticket> employeeTickets = ticketService.viewEmployeeTickets(username);
        return ResponseEntity.status(200).body(employeeTickets);
    }
    /*
     * As a Manager I can view all pending tickets irrespective of employee and I can update their status.
     */

     @GetMapping("ticket/{id}")
     public ResponseEntity<Ticket> viewTicket(@PathVariable long id)
     {
         Optional<Ticket> ticket = ticketService.findTicket(id);
         log.info(ticket.get().toString());
         return ResponseEntity.status(200).body(ticket.get());
     }

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
        log.info(String.valueOf(numUpdated));
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
