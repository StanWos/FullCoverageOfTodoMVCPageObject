package com.todomvc;

import pages.TodoMVCPage;
import org.junit.Test;
import static pages.TodoMVCPage.TaskType.*;


public class TodoMVCAtCompletedTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testAddAtCompleted() {
        page.givenAtCompleted(page.aTask("1", ACTIVE));
        page.add("2");

        page.assertNoVisibleTasks();
        page.assertItemLeft(2);
    }

    @Test
    public void testEditAtCompleted() {
        page.givenAtCompleted(COMPLETED, "1", "2");

        page.startEdit("2", "2 edited").pressEnter();
        page.assertVisibleTasks("1", "2 edited");
        page.assertItemLeft(0);
    }

    @Test
    public void testCompleteAllAtCompleted() {
        page.givenAtCompleted(ACTIVE, "1", "2");

        page.toggleAll();
        page.assertVisibleTasks("1", "2");
        page.assertItemLeft(0);
    }

    @Test
    public void testReopenAtCompleted() {
        page.givenAtCompleted(page.aTask("1", COMPLETED));

        page.toggle("1");
        page.assertNoVisibleTasks();
        page.assertItemLeft(1);
    }

    @Test
    public void testReopenAllAtCompleted() {
        page.givenAtCompleted(page.aTask("1", ACTIVE), page.aTask("2", ACTIVE), page.aTask("3", COMPLETED));

        page.toggleAll();
        page.assertVisibleTasks("1", "2", "3");
        page.assertItemLeft(0);
    }

    @Test
    public void testEditCancelledAtCompleted() {
        page.givenAtCompleted(page.aTask("1", COMPLETED));

        page.startEdit("1", "1 edited cancelled").pressEscape();
        page.assertTasks("1");
        page.assertItemLeft(0);
    }

    @Test
    public void testEditByClickOutsideAtCompleted() {
        page.givenAtCompleted(page.aTask("1", COMPLETED));

        page.startEdit("1", "1 edited");
        page.newTodo.click();
        page.assertTasks("1 edited");
        page.assertItemLeft(0);
    }

    @Test
    public void testEditByClickTabAtCompleted() {
        page.givenAtCompleted(page.aTask("1", COMPLETED));

        page.startEdit("1", "1 edited").pressTab();
        page.assertTasks("1 edited");
        page.assertItemLeft(0);
    }

    @Test
    public void testDeleteByEmptyingEditedTextAtCompleted() {
        page.givenAtCompleted(page.aTask("1", COMPLETED));

        page.startEdit("1", "").pressEnter();
        page.assertNoTasks();
    }

    @Test
    public void testDeleteAtCompleted() {
        page.givenAtCompleted(page.aTask("1", COMPLETED), page.aTask("2", ACTIVE));

        page.delete("1");
        page.assertNoVisibleTasks();
        page.assertItemLeft(1);
    }

    // *** Changing filter

    @Test
    public void goToActive() {
        page.givenAtCompleted(page.aTask("1", ACTIVE), page.aTask("2", COMPLETED));

        page.filterActive();
        page.assertVisibleTasks("1");
        page.assertItemLeft(1);
    }
}
