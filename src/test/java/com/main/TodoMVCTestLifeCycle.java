package com.main;

import com.main.page.TodoMVCPage;
import org.junit.Test;

/**
 * Created by stan on 21.03.16.
 */
public class TodoMVCTestLifeCycle {

    @Test
    public void testTaskLifeCycle() {
        TodoMVCPage page = new TodoMVCPage();

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
