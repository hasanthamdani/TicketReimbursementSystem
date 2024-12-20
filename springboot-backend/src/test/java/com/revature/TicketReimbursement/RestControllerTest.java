package com.revature.TicketReimbursement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.TicketReimbursement.Entity.Employee;
import com.revature.TicketReimbursement.Entity.Ticket;
import com.revature.TicketReimbursement.Service.EmployeeService;
import com.revature.TicketReimbursement.Service.TicketService;

@SpringBootTest(classes = RestControllerTest.class)
@AutoConfigureMockMvc
public class RestControllerTest {
    
    @Autowired
    private MockMvc MockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private TicketService ticketService;

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    @Autowired
    private ObjectMapper objectMapper;

@BeforeEach
    public void setup() {
        MockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterHandler() throws Exception {
        Employee employee = new Employee("username", "password", false);
        when(employeeService.register(any(Employee.class))).thenReturn(employee);

        MockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testRegisterHandler_AccountDuplicate() throws Exception {
        Employee employee = new Employee("username", "password", false);
        when(employeeService.register(any(Employee.class))).thenReturn(null);

        MockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void testLoginHandler() throws Exception {
        Employee employee = new Employee("username", "password", true);
        when(employeeService.login("username")).thenReturn(Optional.of(employee));

        MockMvc.perform(MockMvcRequestBuilders
                        .get("/login/username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testLoginHandler_NotFound() throws Exception {
        when(employeeService.login("unknown")).thenReturn(Optional.empty());

        MockMvc.perform(MockMvcRequestBuilders
                        .get("/login/unknown"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateTicketHandler() throws Exception {
        Employee employee = new Employee("username", "password", true);

        when(employeeService.register(employee)).thenReturn(employee);

        Employee persistedEmp = employeeService.register(employee);
        Ticket ticket = new Ticket(employee, "Issue Description", "Pending", 0);
        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket);

        MockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pending"));
    }

    @Test
    public void testViewEmployeeTicketsHandler() throws Exception {
        Employee employee1 = new Employee("username", "password", true);
        Employee employee2 = new Employee("username", "password", true);

        when(employeeService.register(employee1)).thenReturn(employee1);
        when(employeeService.register(employee2)).thenReturn(employee2);

        Employee persistedEmp1 = employeeService.register(employee1);
        Ticket ticket = new Ticket(employee1, "Issue Description", "Pending", 0);

        Employee persistedEmp2 = employeeService.register(employee2);
        Ticket ticket = new Ticket(employee2, "Issue Description", "Pending", 0);


        Ticket ticket1 = new Ticket(employee1, "Issue 1", "Pending", 0);
        Ticket ticket2 = new Ticket(employee2, "Issue 2", "Pending", 0);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketService.viewEmployeeTickets("username")).thenReturn(tickets);

        MockMvc.perform(MockMvcRequestBuilders
                        .get("/tickets/username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ticketId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ticketId").value(2L));
    }

    @Test
    public void testViewTicket() throws Exception {
        Employee employee = new Employee("username", "password", true);

        when(employeeService.register(employee)).thenReturn(employee);

        Employee persistedEmp = employeeService.register(employee);
        Ticket ticket = new Ticket(employee, "Issue Description", "Pending", 0);
        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket);
        MockMvc.perform(MockMvcRequestBuilders
                        .get("/ticket/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pending"));
    }

    @Test
    public void testViewTicket_NotFound() throws Exception {
        when(ticketService.findTicket(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ticket/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testViewAllPendingTicketsHandler() throws Exception {
        Employee employee1 = new Employee("username", "password", true);
        Employee employee2 = new Employee("username", "password", true);

        when(employeeService.register(employee1)).thenReturn(employee1);
        when(employeeService.register(employee2)).thenReturn(employee2);

        Employee persistedEmp1 = employeeService.register(employee1);
        Ticket ticket1 = new Ticket(employee1, "Issue Description", "Pending", 0);

        Employee persistedEmp2 = employeeService.register(employee2);
        Ticket ticket2 = new Ticket(employee2, "Issue Description", "Pending", 0);

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketService.viewAllPendingTickets()).thenReturn(tickets);

        MockMvc.perform(MockMvcRequestBuilders
                        .get("/manager/tickets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ticketId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ticketId").value(2L));
    }

    @Test
    public void testUpdateStatusHandler() throws Exception {
        when(ticketService.updateTicketStatus(any(), anyLong())).thenReturn(1);

        MockMvc.perform(MockMvcRequestBuilders
                        .patch("/manager/tickets/")
                        .param("ticketId", "1")
                        .param("status", "Resolved"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    public void testDuplicateUsernameHandler() throws Exception {
        MockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"duplicate\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
