package com.todomvc;

import pages.TodoMVCPage;
import org.junit.Test;


public class TodoMVCLifeCycleTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testTaskLifeCycle() {

        page.givenAtAll();
        page.add("1");
        page.startEdit("1", "1 edited").pressEnter();
        page.assertTasks("1 edited");

        page.filterActive();
        page.assertTasks("1 edited");
        page.toggleAll();
        page.add("2");
        page.assertVisibleTasks("2");

        page.filterCompleted();
        page.assertVisibleTasks("1 edited");
        page.clearCompleted();
        page.assertNoVisibleTasks();

        page.filterAll();
        page.assertItemLeft(1);
        page.assertTasks("2");

        page.delete("2");
        page.assertNoTasks();

    }

}
