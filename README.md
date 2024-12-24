# Project: Ticket Reimbursement System

## Background 

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for 
reimbursement and view their past tickets and pending requests. Financial managers can log in and view all reimbursement requests and past history for all employees in the company. Similarly, Financial
Managers, as a type of  Employee, are allowed to create tickets for themselves. Most importantly, only financial managers are authorized to approve and deny requests for expense reimbursement.
This data schema was designed to hold this information and allow for efficient querying:

### Employee
```
username varchar(255) primary key,
password varchar(255) not null,
manager boolean
```

### Ticket
```
id integer primary key auto_increment,
username varchar(255) not null,
description varchar(255),
status string,
amount double
foreign key (username) references Employee(username)
```

# Technical Requirement

The project is built from scratch, and must utilize the following technologies: Spring MVC, Spring Data, Spring Boot, React, consumption of a RESTful API, hosting the postgreSQL database in Amazon RDS, version management using Git, unit tests for
all back-end features using JUnit and Mockito along with Spring Boot Test, and API testing via Postman.

# User Stories

## 1: The application should allow for a user to register a new account or login to a pre-existing account

As an employee, I should be able to register a new account. 
As an employee, i should be able to login to my account.
As a manager, I should be able to only login to a pre-existing account on the database.

## 2: The application should allow for both managers and employees to submit tickets

As a user, I should be able to submit a ticket with a description and amount. 
As a manager, I should be able to do the same, but my ticket must not be resolved by myself.

## 3: The application should allow for managers to accept or deny ticket requests

As a manager, I should be able to view all pending tickets from employees other than myself.
As a manager, I can approve or deny tickets and the decision cannot be changed afterwards.

## 4: The application should allow for employees and manager to view past ticket requests

As an employee, I should see my past tickets, their amount, description, and current status.
As a manager, I should be able to see the same history, and not be able to change it.
