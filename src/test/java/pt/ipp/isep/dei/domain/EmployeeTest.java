package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void ensureTwoEmployeesWithSameEmailEquals() {
        Player employee1 = new Player("john.doe@this.company.com");
        Player employee2 = new Player("john.doe@this.company.com");
        assertEquals(employee1, employee2);
    }

    @Test
    void ensureEmployeeWithDifferentEmailNotEquals() {
        Player employee1 = new Player("john.doe@this.company.com");
        Player employee2 = new Player("jane.doe@this.company.com");
        assertNotEquals(employee1, employee2);
    }

    @Test
    void ensureEmployeeDoesNotEqualNull() {
        Player employee1 = new Player("john.doe@this.company.com");
        assertNotEquals(employee1, null);
    }

    @Test
    void ensureEmployeeDoesNotEqualOtherObject() {
        Player employee1 = new Player("john.doe@this.company.com");
        assertNotEquals(employee1, new Object());
    }

    @Test
    void ensureTheSameObjectIsEqual() {
        Player employee1 = new Player("john.doe@this.company.com");
        assertEquals(employee1, employee1);
    }

    @Test
    void ensureHashCodeIsEqualForEqualObjects() {
        String email = "john.doe@this.company.com";
        Player employee1 = new Player(email);
        Player employee2 = new Player(email);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void ensureHashCodeIsNotEqualForDifferentObjects() {

        Player employee1 = new Player("john.doe@this.company.com");
        Player employee2 = new Player("jane.doe@this.company.com");
        assertNotEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void ensureHasEmailWorksForTheSameEmail() {
        String email = "john.doe@this.compay.org";
        Player employee = new Player(email);
        assertTrue(employee.hasEmail(email));

    }

    @Test
    void ensureHasEmailFailsForDifferentEmails() {
        String email = "john.doe@this.company.com";
        Player employee = new Player(email);
        assertFalse(employee.hasEmail("jane.doe@this.company.com"));

    }

    @Test
    void ensureCloneWorks() {
        String email = "john.doe@this.company.com";
        Player employee = new Player(email);
        Player clone = employee.clone();
        assertEquals(employee, clone);
    }
}