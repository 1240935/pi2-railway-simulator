package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void ensureTaskIsCreatedSuccessfully() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
    }

    @Test
    void ensureTaskReferenceIsNotNull() {
        //Arrange
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");

        //Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Task(null, "description", "informal description", "technical description", 1, 1d,
                        taskCategory, employee));
    }

    @Test
    void testEqualsSameObject() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertEquals(task, task);
    }

    @Test
    void testEqualsDifferentClass() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertNotEquals(task, new Object());
    }

    @Test
    void testEqualsNull() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertNotEquals(task, null);
    }

    @Test
    void testEqualsDifferentObject() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference1", "description1", "informal description1", "technical description1", 2, 2d,
                taskCategory, employee);

        assertNotEquals(task, task1);
    }

    @Test
    void testEqualsSameObjectDifferentDescription() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference1", "description", "informal description1", "technical description1", 2, 2d,
                taskCategory, employee);

        assertNotEquals(task, task1);
    }

    @Test
    void testEqualsSameObjectSameDescription() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertEquals(task, task1);
    }

    @Test
    void testHashCodeSameObject() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertEquals(task.hashCode(), task.hashCode());
    }

    @Test
    void testHashCodeDifferentObject() {
        Player employee = new Player("john.doe@this.company.com");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference1", "description1", "informal description1", "technical description1", 2, 2d,
                taskCategory, employee);

        assertNotEquals(task.hashCode(), task1.hashCode());
    }

    @Test
    void ensureCloneWorks() {
        Player employee = new Player("john.doe@this.company.org");
        Position taskCategory = new Position("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task clone = task.clone();
        assertEquals(task, clone);
    }
}