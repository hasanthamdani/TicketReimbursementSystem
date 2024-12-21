package com.revature.TicketReimbursement;

import com.revature.TicketReimbursement.Controller.TicketReimbursementController;
import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Exception.AccountDuplicateException;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;
import com.revature.TicketReimbursement.Repository.TicketRepository;
import com.revature.TicketReimbursement.Service.EmployeeService;
import com.revature.TicketReimbursement.Service.TicketService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(TicketReimbursementController.class)
@ExtendWith(MockitoExtension.class)
@Log
public class RestControllerTest {

    @Autowired
    private MockMvc MockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @MockitoBean
    private TicketService ticketService;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    private ObjectMapper objectMapper = new ObjectMapper();

        @Test
        public void testRegisterUser() throws Exception
        {   
            Employee emp = new Employee("username", "password", false);
            when(employeeService.register(employeeCaptor.capture())).thenReturn(emp);

            log.info("Jackson Test: " + objectMapper.writeValueAsString(emp));

            MockMvc.perform(MockMvcRequestBuilders
            .post("/register")
            .content(objectMapper.writeValueAsString(emp)).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.manager").value(false));

            verify(employeeService).register(employeeCaptor.capture());
            assertThat(employeeCaptor.getValue()).isEqualTo(emp);
        }

        @Test
        public void testDuplicateRegister() throws Exception
        {   
            Employee emp = new Employee("dupusername", "password", false);

            when(employeeService.register(employeeCaptor.capture())).thenReturn(null);

            MockMvc.perform(MockMvcRequestBuilders
            .post("/register")
            .content(objectMapper.writeValueAsString(emp)).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
            .andExpect(status().isConflict());

            verify(employeeService).register(employeeCaptor.capture());
            assertThat(employeeCaptor.getValue()).isEqualTo(emp);
        }

        
        @Test
        public void testLogin() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            when(employeeService.login(emp.getUsername())).thenReturn(Optional.of(emp));

            MockMvc.perform(MockMvcRequestBuilders
            .get("/login/{username}", emp.getUsername()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("logusername"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("logpassword"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.manager").value(false));

            verify(employeeService).login(emp.getUsername());
        }

        @Test
        public void testLoginFail() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            when(employeeService.login(emp.getUsername())).thenReturn(Optional.empty());

            MockMvc.perform(MockMvcRequestBuilders
            .get("/login/{username}", emp.getUsername()))
            .andExpect(status().isNotFound());

            verify(employeeService).login(emp.getUsername());
        }

        @Test
        public void testCreateTicket() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            Ticket tick = new Ticket(emp, "description", "pending", 0.0);

            when(ticketService.createTicket(ticketCaptor.capture())).thenReturn(tick);

            MockMvc.perform(MockMvcRequestBuilders
            .post("/create")
            .content(objectMapper.writeValueAsString(tick))
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("pending"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(0.0));

            verify(ticketService).createTicket(ticketCaptor.capture());
            assertThat(ticketCaptor.getValue()).isEqualTo(tick);
        }
        
        @Test
        public void testViewEmployeeTickets() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            Ticket tick = new Ticket(emp, "description", "pending", 0.0);
            
            List<Ticket> ticketList = new ArrayList<Ticket>();
            ticketList.add(tick);

            when(ticketService.viewEmployeeTickets(emp.getUsername())).thenReturn(ticketList);

            MockMvc.perform(MockMvcRequestBuilders
            .get("/tickets/{username}",emp.getUsername()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("description"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("pending"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(0.0));

            verify(ticketService).viewEmployeeTickets(emp.getUsername());
        }
        @Test
        public void testViewTicket() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            Ticket tick = new Ticket(emp, "description", "pending", 0.0);

            when(ticketService.findTicket((0L))).thenReturn(Optional.of(tick));

            MockMvc.perform(MockMvcRequestBuilders
            .get("/ticket/{id}",0))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("pending"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(0.0));

            verify(ticketService).findTicket(0L);
        }

        @Test
        public void testViewPendingTickets() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            Ticket tick = new Ticket(emp, "description", "pending", 0.0);

            List<Ticket> ticketList = new ArrayList<Ticket>();
            ticketList.add(tick);

            when(ticketService.viewAllPendingTickets()).thenReturn(ticketList);

            MockMvc.perform(MockMvcRequestBuilders
            .get("/manager/tickets"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("description"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("pending"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(0.0));

            verify(ticketService).viewAllPendingTickets();
        }

        @Test
        public void testUpdateStatus() throws Exception
        {   
            Employee emp = new Employee("logusername", "logpassword", false);
            Ticket tick = new Ticket(emp, "description", "pending", 0.0);

            when(ticketService.updateTicketStatus("accepted",0L)).thenReturn(1);

            MockMvc.perform(MockMvcRequestBuilders.patch("/manager/tickets").param("status", "accepted").param("ticketId", "0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

            verify(ticketService).updateTicketStatus("accepted", 0L);
        }
}
