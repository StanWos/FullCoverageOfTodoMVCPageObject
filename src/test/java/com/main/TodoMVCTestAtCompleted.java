package com.main;

import com.main.page.TodoMVCPage;
import org.junit.Test;
import static com.main.page.TodoMVCPage.TaskType.*;

/**
 * Created by stan on 25.03.16.
 */
public class TodoMVCTestAtCompleted {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testAddAtCompleted() {
        page.givenAtCompleted(aTask("1", ACTIVE));
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
        page.givenAtCompleted(aTask("1", COMPLETED));

        page.toggle("1");
        page.assertNoVisibleTasks();
        page.assertItemLeft(1);
    }

    @Test
    public void testReopenAllAtCompleted() {
        page.givenAtCompleted(aTask("1", ACTIVE), aTask("2", ACTIVE), aTask("3", COMPLETED));

        page.toggleAll();
        page.assertVisibleTasks("1", "2", "3");
        page.assertItemLeft(0);
    }

    @Test
    public void testEditCancelledAtCompleted() {
        page.givenAtCompleted(aTask("1", COMPLETED));

        page.startEdit("1", "1 edited cancelled").pressEscape();
        page.assertTasks("1");
        page.assertItemLeft(0);
    }

    @Test
    public void testEditByClickOutsideAtCompleted() {
        page.givenAtCompleted(aTask("1", COMPLETED));

        page.startEdit("1", "1 edited");
        page.newTodo.click();
        page.assertTasks("1 edited");
        page.assertItemLeft(0);
    }

    @Test
    public void testEditByClickTabAtCompleted() {
        page.givenAtCompleted(aTask("1", COMPLETED));

        page.startEdit("1", "1 edited").pressTab();
        page.assertTasks("1 edited");
        page.assertItemLeft(0);
    }

    @Test
    public void testDeleteByEmptyingEditedTextAtCompleted() {
        page.givenAtCompleted(aTask("1", COMPLETED));

        page.startEdit("1", "").pressEnter();
        page.assertNoTasks();
    }

    @Test
    public void testDeleteAtCompleted() {
        page.givenAtCompleted(aTask("1", COMPLETED), aTask("2", ACTIVE));

        page.delete("1");
        page.assertNoVisibleTasks();
        page.assertItemLeft(1);
    }

    // *** Changing filter

    @Test
    public void goToActive() {
        page.givenAtCompleted(aTask("1", ACTIVE), aTask("2", COMPLETED));

        page.filterActive();
        page.assertVisibleTasks("1");
        page.assertItemLeft(1);
    }
}
