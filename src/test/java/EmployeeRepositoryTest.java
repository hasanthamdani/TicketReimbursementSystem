import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.Revature.ticketreimbursement.Entity.Employee;
import com.Revature.ticketreimbursement.Repository.EmployeeRepository;

@DataJpaTest
class EmployeeRepositoryTest {
    
    @Autowired
    // Implementation of JPA Interfaces
    private TestEntityManager entityManager; // 

    @Autowired
    private EmployeeRepository employeeRepository;

    
    //First Test: Make sure save persists data
    @Test
    public void testSaveUser()
    {
        Employee emp = new Employee("testuser", "testpassword", false);
        Employee persistedEmp = employeeRepository.save(emp);
        
        assert(persistedEmp.getUsername().equals("testuser"));
        assert(persistedEmp.getPassword().equals("testuser@example.com"));
    }
    
    // Test the ability to find a username by ID
    @Test
    public void testFindEmployee()
    {
        // Persist Base Employee to be Found
        Employee emp = new Employee("testuser", "testpassword", false);

        Employee persistedEmp = entityManager.persistAndFlush(emp);

        Optional<Employee> retrievedEmp = employeeRepository.findByUsername(persistedEmp.getUsername());

        assert(retrievedEmp.isPresent());
        assert(retrievedEmp.get().getUsername().equals("testuser"));
        assert(retrievedEmp.get().getPassword().equals("testpassword"));
        assert(!retrievedEmp.get().isManager());
    }

    // Test the ability to find if a username exists
    @Test
    public void testExistsEmployee()
    {
        // Persist Base Employee to be Found
        Employee emp = new Employee("testuser", "testpassword", false);

        Employee persistedEmp = entityManager.persistAndFlush(emp);

        Boolean retrievedEmp = employeeRepository.existsByUsername(persistedEmp.getUsername());

        assert(retrievedEmp);

        Boolean retrievedEmp2 = employeeRepository.existsByUsername("not");
        assert(retrievedEmp2.equals(false));
    }

    //Test the Ability to find if the employee is both a username and manager
    @Test
    public void testFindManagerUsername()
    {
        // Persist Base Employee to be Found
        Employee emp = new Employee("testuser", "testpassword", false);

        Employee persistedEmp = entityManager.persistAndFlush(emp);

        Optional<Employee> retrievedEmp = employeeRepository.findByUsernameAndIsManager(persistedEmp.getUsername(), persistedEmp.isManager());

        assert(!retrievedEmp.isPresent());

        Employee man = new Employee("tester", "testpassword", true);

        Employee persistedMan = entityManager.persistAndFlush(man);

        assert(persistedMan.getUsername().equals("testuser"));
        assert(persistedMan.getPassword().equals("testpassword"));
        assert(persistedMan.isManager());
    }

    //Test if duplicate usernames would cause an error.
    @Test
    public void testduplicateUsername()
    {
        Employee emp1 = new Employee("testuser", "testpassword", false);
        Employee emp2 = new Employee("testuser", "testpassword", false);

        Employee perEmp1 = entityManager.persistAndFlush(emp1);
        Employee perEmp2 = entityManager.persistAndFlush(emp2);

        assert(perEmp2.equals(null));
    }

    
}
