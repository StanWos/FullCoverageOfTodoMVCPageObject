package com.main;

import com.main.page.TodoMVCPage;
import org.junit.Test;
import static com.main.page.TodoMVCPage.TaskType.*;

/**
 * Created by stan on 25.03.16.
 */
public class TodoMVCTestAtAll {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testCompleteAtAll() {
        page.givenAtAll(aTask("1", ACTIVE));

        page.toggle("1");
        page.assertTasks("1");
        page.assertItemLeft(0);
    }

    @Test
    public void testCompleteAllAtAll() {
        page.givenAtAll(ACTIVE, "1", "2");

        page.toggleAll();
        page.assertTasks("1", "2");
        page.assertItemLeft(0);
    }

    @Test
    public void testReopenAtAll() {
        page.givenAtAll(aTask("1", COMPLETED), aTask("2", ACTIVE));

        page.toggle("1");
        page.assertTasks("1", "2");
        page.assertItemLeft(2);
    }

    @Test
    public void testReopenAllAtAll() {
        page.givenAtAll(COMPLETED, "1", "2");

        page.toggleAll();
        page.assertTasks("1", "2");
        page.assertItemLeft(2);
    }

    @Test
    public void testEditCancelledAtAll() {
        page.givenAtAll(aTask("1", ACTIVE));

        page.startEdit("1", "1 edited cancelled").pressEscape();
        page.assertTasks("1");
        page.assertItemLeft(1);
    }

    @Test
    public void testClearCompletedAtAll() {
        page.givenAtAll(COMPLETED, "1", "2");

        page.clearCompleted();
        page.assertNoTasks();
    }

    @Test
    public void testEditByClickOutsideAtAll() {
        page.givenAtAll(aTask("1", ACTIVE));

        page.startEdit("1", "1 edited");
        page.newTodo.click();
        page.assertVisibleTasks("1 edited");
        page.assertItemLeft(1);
    }

    @Test
    public void testEditByClickTabAtAll() {
        page.givenAtAll(aTask("1", ACTIVE));

        page.startEdit("1", "1 edited").pressTab();
        page.assertVisibleTasks("1 edited");
        page.assertItemLeft(1);
    }

    @Test
    public void testDeleteByEmptyingEditedTextAtAll() {
        page.givenAtAll(aTask("1", ACTIVE));

        page.startEdit("1", "").pressEnter();
        page.assertNoTasks();
    }

    // *** Changing filter

    @Test
    public void goAtCompleted() {
        page.givenAtAll(aTask("1", COMPLETED));

        page.filterCompleted();
        page.assertVisibleTasks("1");
        page.assertItemLeft(0);
    }
}
