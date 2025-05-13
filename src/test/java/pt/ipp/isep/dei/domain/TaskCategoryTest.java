package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskCategoryTest {

    //Tests for equals and hashcode
    @Test
    void testEqualsSameObject() {
        Position taskCategory = new Position("Task Category Description");
        assertEquals(taskCategory, taskCategory);
    }

    @Test
    void testEqualsDifferentClass() {
        Position taskCategory = new Position("Task Category Description");
        assertNotEquals("", taskCategory);
    }

    @Test
    void testEqualsNull() {
        Position taskCategory = new Position("Task Category Description");
        assertNotEquals(null, taskCategory);
    }

    @Test
    void testEqualsDifferentObject() {
        Position taskCategory = new Position("Task Category Description");
        Position taskCategory1 = new Position("Task Category Description 1");
        assertNotEquals(taskCategory, taskCategory1);
    }

    @Test
    void testEqualsSameObjectDifferentDescription() {
        Position taskCategory = new Position("Task Category Description");
        Position taskCategory1 = new Position("Task Category Description 1");
        assertNotEquals(taskCategory, taskCategory1);
    }

    @Test
    void testEqualsSameObjectSameDescription() {
        Position taskCategory = new Position("Task Category Description");
        Position taskCategory1 = new Position("Task Category Description");
        assertEquals(taskCategory, taskCategory1);
    }

    @Test
    void testHashCodeSameObject() {
        Position taskCategory = new Position("Task Category Description");
        assertEquals(taskCategory.hashCode(), taskCategory.hashCode());
    }

    @Test
    void testHashCodeDifferentObject() {
        Position taskCategory = new Position("Task Category Description");
        Position taskCategory1 = new Position("Task Category Description 1");
        assertNotEquals(taskCategory.hashCode(), taskCategory1.hashCode());
    }

    @Test
    void testHashCodeSameObjectDifferentDescription() {
        Position taskCategory = new Position("Task Category Description");
        Position taskCategory1 = new Position("Task Category Description 1");
        assertNotEquals(taskCategory.hashCode(), taskCategory1.hashCode());
    }

    @Test
    void testHashCodeSameObjectSameDescription() {
        Position taskCategory = new Position("Task Category Description");
        Position taskCategory1 = new Position("Task Category Description");
        assertEquals(taskCategory.hashCode(), taskCategory1.hashCode());
    }

    @Test
    void testEqualsForDifferentObjectType() {
        Position taskCategory = new Position("Task Category Description");
        assertNotEquals(taskCategory, new Object());
    }

    @Test
    void ensureGetDescriptionWorks() {
        Position taskCategory = new Position("Task Category Description");

        assertEquals("Task Category Description", taskCategory.getDescription());
    }

    @Test
    void ensureCloneWorks() {
        Position taskCategory = new Position("Task Category Description");
        Position clone = taskCategory.clone();
        assertEquals(taskCategory, clone);
    }
}