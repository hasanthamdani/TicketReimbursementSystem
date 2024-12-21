package com.revature.TicketReimbursement;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import com.revature.TicketReimbursement.Repository.EmployeeRepository;
import com.revature.TicketReimbursement.Repository.TicketRepository;
import com.revature.TicketReimbursement.Service.EmployeeService;
import com.revature.TicketReimbursement.Service.TicketService;

@SpringBootTest(classes = TicketReimbursementApplicationTests.class)
class TicketReimbursementApplicationTests {

	ConfigurableApplicationContext applicationContext;

	/*
	 * Class tests for if the beans are configured and recognized
	 */

	// Before each test, this retrieves the application context
	@BeforeEach
	public void setUp() {
		String[] args = new String[] {};
		applicationContext = SpringApplication.run(TicketReimbursementApplication.class, args);
	}

	//Resets the application context after each test
	@AfterEach
	public void reset()
	{
		SpringApplication.exit(applicationContext);
	}

	@Test
	public void getTicketRepositoryBean(){
			TicketRepository bean = applicationContext.getBean(TicketRepository.class);
			Assertions.assertNotNull(bean);
	}

	@Test
	public void getEmployeeRepositoryBean(){
		EmployeeRepository bean = applicationContext.getBean(EmployeeRepository.class);
		Assertions.assertNotNull(bean);
	}

	@Test
	public void getEmployeeServiceBean(){
		EmployeeService bean = applicationContext.getBean(EmployeeService.class);
		Assertions.assertNotNull(bean);
	}

	@Test
	public void getTicketServiceBean(){
		TicketService bean = applicationContext.getBean(TicketService.class);
		Assertions.assertNotNull(bean);
	}
	    /**
     * Verify the functionality of Spring MVC, independently of the project requirement endpoints, by sending a request
     * to an arbitrary endpoint.
     */
    @Test
    public void default404Test() throws IOException, InterruptedException {
        HttpClient webClient = HttpClient.newHttpClient();
        int random = (int) (Math.random()*100000);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/arbitrary"+random))
                .build();
        HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(404, status);
        String body = response.body().toString();
        Assertions.assertTrue(body.contains("timestamp"));
        Assertions.assertTrue(body.contains("status"));
        Assertions.assertTrue(body.contains("error"));
        Assertions.assertTrue(body.contains("path"));
    }


}