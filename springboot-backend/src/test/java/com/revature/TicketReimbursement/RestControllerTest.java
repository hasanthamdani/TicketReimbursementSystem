package com.revature.TicketReimbursement;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

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


}
