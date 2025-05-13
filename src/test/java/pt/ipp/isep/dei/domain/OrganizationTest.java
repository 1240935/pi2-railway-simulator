package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationTest {

    @Test
    void testEqualsSameObject() {
        Editor organization = new Editor("123456789");
        assertEquals(organization, organization);
    }

    @Test
    void testEqualsDifferentClass() {
        Editor organization = new Editor("123456789");
        assertNotEquals("", organization);
    }

    @Test
    void testEqualsNull() {
        Editor organization = new Editor("123456789");
        assertNotEquals(null, organization);
    }

    @Test
    void testEqualsDifferentObject() {
        Editor organization = new Editor("123456789");
        Editor organization1 = new Editor("123456789");
        assertEquals(organization, organization1);
    }

    @Test
    void testHashCodeSameObject() {
        Editor organization = new Editor("123456789");
        assertEquals(organization.hashCode(), organization.hashCode());
    }

    @Test
    void testHashCodeDifferentObject() {
        Editor organization = new Editor("123456789");
        Editor organization1 = new Editor("123456789");
        assertEquals(organization.hashCode(), organization1.hashCode());
    }

    @Test
        //same hashcode
    void testHashCodeSameObjectSameVATNumber() {
        Editor organization = new Editor("123456789");
        Editor organization1 = new Editor("123456789");
        assertEquals(organization.hashCode(), organization1.hashCode());
    }

    @Test
    void ensureHashCodeFailsForDifferentVatNumbers() {
        Editor organization = new Editor("123456789");
        Editor organization1 = new Editor("123456788");
        assertNotEquals(organization.hashCode(), organization1.hashCode());
    }

    @Test
    void ensureEqualsFailsForDifferentObjectType() {
        Editor organization = new Editor("123456789");
        Editor organization1 = new Editor("123456788");
        assertNotEquals(organization, organization1);
    }

    @Test
    void ensureEqualsFailsWhenComparingNull() {
        Editor organization = new Editor("123456789");
        assertNotEquals(organization, null);
    }

    @Test
    void ensureEqualsSuccessWhenComparingSameObject() {
        Editor organization = new Editor("123456789");
        assertEquals(organization, organization);
    }

    @Test
    void testThatCreateTaskWorks() {
        Editor organization = new Editor("123456789");

        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");

        Task expected = new Task("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, taskCategory, employee);

        Optional<Task> task =
                organization.createTask("Task Description", "Task Category Description", "informal description",
                        "technical description", 1, 1d, taskCategory, employee);

        assertNotNull(task);
        assertTrue(task.isPresent());
        assertEquals(expected, task.get());
    }

    @Test
    void ensureAddingDuplicateTaskFails() {
        //Arrange
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        //Add the first task
        Optional<Task> originalTask =
                organization.createTask("Task Description", "Task Category Description", "informal description",
                        "technical description", 1, 1d, taskCategory, employee);

        //Act
        Optional<Task> duplicateTask =
                organization.createTask("Task Description", "Task Category Description", "informal description",
                        "technical description", 1, 1d, taskCategory, employee);

        //Assert
        assertTrue(duplicateTask.isEmpty());
    }


    @Test
    void ensureEmploysFails() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");

        assertFalse(organization.employs(employee));

    }

    @Test
    void ensureEmploysSuccess() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertTrue(organization.employs(employee));
    }

    @Test
    void ensureAnyEmployeeHasEmailFails() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertFalse(organization.anyEmployeeHasEmail("jane.doe@this.company.com"));
    }

    @Test
    void ensureAnyEmployeeHasEmailWorks() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertTrue(organization.anyEmployeeHasEmail("john.doe@this.company.com"));
    }

    @Test
    void ensureAddDuplicateEmployeeFails() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        assertTrue(organization.addEmployee(employee));
        assertFalse(organization.addEmployee(employee));
    }

    @Test
    void ensureAddEmployeeWorks() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        assertTrue(organization.addEmployee(employee));
    }

    @Test
    void ensureCloneWorks() {
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        organization.createTask("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, new Position("Task Category Description"), employee);

        Editor clone = organization.clone();
        assertEquals(organization, clone);
    }
}