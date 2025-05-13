package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Position;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskCategoryRepositoryTest {

    @Test
    void getTaskCategoryByDescriptionEmptyList() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        assertThrows(IllegalArgumentException.class,
                () -> taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription));
    }

    @Test
    void getTaskCategoryByDescriptionNullList() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        assertThrows(IllegalArgumentException.class,
                () -> taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription));
    }

    @Test
    void ensureNewTaskCategorySuccessfullyAdded() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        Position taskCategory = new Position(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
    }

    @Test
    void ensureGetTaskCategoryForExistingTaskCategory() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        Position taskCategory = new Position(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
        Position taskCategory1 = taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription);
        assertEquals(taskCategory, taskCategory1);
    }

    @Test
    void ensureGetTaskCategoryFailsForNonExistingTaskCategory() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        Position taskCategory = new Position(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
        String taskCategoryDescription1 = "Task Category Description 1";
        assertThrows(IllegalArgumentException.class,
                () -> taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription1));

    }

    @Test
    void ensureGetTaskCategoriesReturnsAnImmutableList() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        Position taskCategory = new Position(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);

        assertThrows(UnsupportedOperationException.class,
                () -> taskCategoryRepository.getTaskCategories().add(new Position("Task Category Description 1")));

    }

    @Test
    void ensureGetTaskCategoriesReturnsTheCorrectList() {
        //Arrange
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        Position taskCategory = new Position(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
        int expectedSize = 1;

        //Act
        int size = taskCategoryRepository.getTaskCategories().size();

        //Assert
        assertEquals(expectedSize, size);
        assertEquals(taskCategory, taskCategoryRepository.getTaskCategories().get(size - 1));
    }

    @Test
    void ensureAddingDuplicateTaskCategoryFails() {
        //Arrange
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        Position taskCategory = new Position("Task Category Description");
        //Add the first task
        taskCategoryRepository.add(taskCategory);

        //Act
        Optional<Position> duplicateTaskCategory = taskCategoryRepository.add(taskCategory);

        //Assert
        assertTrue(duplicateTaskCategory.isEmpty());
    }

    @Test
    void ensureAddingDifferentTaskCategoriesWorks() {
        //Arrange
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        Position taskCategoryOne = new Position("Task Category Description One");
        Position taskCategoryTwo = new Position("Task Category Description Two");
        //Add the first task
        taskCategoryRepository.add(taskCategoryOne);

        //Act
        Optional<Position> result = taskCategoryRepository.add(taskCategoryTwo);

        //Assert
        assertEquals(taskCategoryTwo, result.get());
    }
}