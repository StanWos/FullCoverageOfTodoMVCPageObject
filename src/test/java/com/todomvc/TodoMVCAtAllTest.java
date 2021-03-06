package com.todomvc;

import pages.TodoMVCPage;
import org.junit.Test;
import static pages.TodoMVCPage.TaskType.*;



public class TodoMVCAtAllTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testCompleteAtAll() {
        page.givenAtAll(page.aTask("1", ACTIVE));

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
        page.givenAtAll(page.aTask("1", COMPLETED), page.aTask("2", ACTIVE));

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
        page.givenAtAll(page.aTask("1", ACTIVE));

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
        page.givenAtAll(page.aTask("1", ACTIVE));

        page.startEdit("1", "1 edited");
        page.newTodo.click();
        page.assertVisibleTasks("1 edited");
        page.assertItemLeft(1);
    }

    @Test
    public void testEditByClickTabAtAll() {
        page.givenAtAll(page.aTask("1", ACTIVE));

        page.startEdit("1", "1 edited").pressTab();
        page.assertVisibleTasks("1 edited");
        page.assertItemLeft(1);
    }

    @Test
    public void testDeleteByEmptyingEditedTextAtAll() {
        page.givenAtAll(page.aTask("1", ACTIVE));

        page.startEdit("1", "").pressEnter();
        page.assertNoTasks();
    }

    // *** Changing filter

    @Test
    public void goAtCompleted() {
        page.givenAtAll(page.aTask("1", COMPLETED));

        page.filterCompleted();
        page.assertVisibleTasks("1");
        page.assertItemLeft(0);
    }
}
