package com.todomvc;

import pages.TodoMVCPage;
import org.junit.Test;
import static pages.TodoMVCPage.TaskType.*;


public class TodoMVCAtActiveTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testEditAtActive() {
        page.givenAtActive(page.aTask("1", ACTIVE));

        page.startEdit("1", "1 edited").pressEnter();
        page.assertTasks("1 edited");
        page.assertItemLeft(1);
    }

    @Test
    public void testDeleteAtActive() {
        page.givenAtActive(page.aTask("1", ACTIVE));

        page.delete("1");
        page.assertNoTasks();
    }

    @Test
    public void testCompleteAtActive() {
        page.givenAtActive(page.aTask("1", COMPLETED), page.aTask("2", ACTIVE));

        page.toggle("2");
        page.assertNoVisibleTasks();
        page.assertItemLeft(0);
    }

    @Test
    public void testReopenAllAtActive() {
        page.givenAtActive(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED));

        page.toggleAll();
        page.assertNoVisibleTasks();
        page.assertItemLeft(0);
    }

    @Test
    public void testEditCancelledAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.startEdit("1", "1 edited cancelled").pressEscape();
        page.assertTasks("1", "2");
        page.assertItemLeft(2);
    }

    @Test
    public void testClearCompletedAtActive() {
        page.givenAtActive(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED));

        page.clearCompleted();
        page.assertVisibleTasks("1");
        page.assertItemLeft(1);
    }

    @Test
    public void testEditByClickOutsideAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.startEdit("1", "1 edited");
        page.newTodo.click();
        page.assertTasks("1 edited", "2");
        page.assertItemLeft(2);
    }

    @Test
    public void testEditByClickTabAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.startEdit("1", "1 edited").pressTab();
        page.assertVisibleTasks("1 edited", "2");
        page.assertItemLeft(2);
    }

    @Test
    public void testDeleteByEmptyingEditedTextAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.startEdit("1", "").pressEnter();
        page.assertVisibleTasks("2");
        page.assertItemLeft(1);
    }

    // *** Changing filter

    @Test
    public void goToAll() {
        page.givenAtActive(page.aTask("1", COMPLETED));

        page.filterAll();
        page.assertTasks("1");
        page.assertItemLeft(0);
    }
}
