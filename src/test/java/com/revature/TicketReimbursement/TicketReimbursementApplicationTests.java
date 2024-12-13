package com.revature.TicketReimbursement;

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


	void contextLoads() {
	}



}