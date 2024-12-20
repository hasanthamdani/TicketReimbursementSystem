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

import lombok.AllArgsConstructor;

@SpringBootTest(classes = RestControllerTest.class)
@AutoConfigureMockMvc
@AllArgsConstructor
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
}
