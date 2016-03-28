package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.url;


public class TodoMVCPage {

    public ElementsCollection tasks = $$("#todo-list li");
    public SelenideElement newTodo = $("#new-todo");

    public void add(String... taskTexts) {
        for (String text : taskTexts) {
            newTodo.setValue(text).pressEnter();
        }
    }

    public SelenideElement startEdit(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).$(".edit").setValue(newTaskText);
    }

    public void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    public void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    public void toggleAll() {
        $("#toggle-all").click();
    }

    public void clearCompleted() {
        $("#clear-completed").click();
    }

    public void filterAll() {
        $(By.linkText("All")).click();
    }

    public void filterActive() {
        $(By.linkText("Active")).click();
    }

    public void filterCompleted() {
        $(By.linkText("Completed")).click();
    }

    public void assertTasks(String... tasksTexts) {
        tasks.shouldHave(exactTexts(tasksTexts));
    }

    public void assertNoTasks() {
        tasks.shouldHave(empty);
    }

    public void assertVisibleTasks(String... tasksTexts) {
        tasks.filter(visible).shouldHave(exactTexts(tasksTexts));
    }

    public void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    public void assertItemLeft(int numberOfTasks) {
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(numberOfTasks)));
    }

    public void ensureCurrentUrl() {
        if (!url().equals("https://todomvc4tasj.herokuapp.com/")) {
            open("https://todomvc4tasj.herokuapp.com/");
        }
    } // instead of using @Before for opening url


    //  ******* Implementing Precondition-helpers *******

    public enum TaskType {
        ACTIVE("false"), COMPLETED("true");

        public String flag;

        TaskType(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }
    }

    public class Task {
        String taskText;
        TaskType taskType;

        public Task(String taskText, TaskType taskType) {
            this.taskText = taskText;
            this.taskType = taskType;
        }
    }

    public Task aTask(String taskText, TaskType taskType) {
        return new Task(taskText, taskType);
    }

    public void givenAtActive(Task... tasks) {
        givenAtAll(tasks);
        filterActive();
    }

    public void givenAtCompleted(Task... tasks) {
        givenAtAll(tasks);
        filterCompleted();
    }

    public void givenAtAll(TaskType mainType, String... taskTexts) {
        Task[] tasks = new Task[taskTexts.length];
        for (int i = 0; i < taskTexts.length; i++) {
            tasks[i] = aTask(taskTexts[i], mainType);
        }
        givenAtAll(tasks);
    }

    public void givenAtActive(TaskType mainType, String... taskTexts) {
        givenAtAll(mainType, taskTexts);
        filterActive();
    }

    public void givenAtCompleted(TaskType mainType, String... taskTexts) {
        givenAtAll(mainType, taskTexts);
        filterCompleted();
    }

    public void givenAtAll(Task... tasks) {
        ensureCurrentUrl();

        String JavaS = "localStorage.setItem('todos-troopjs', '[";

        for (Task task : tasks) {
            JavaS += "{\"completed\":" + task.taskType.getFlag() + ", \"title\":\"" + task.taskText + "\"},";
        }
        if (tasks.length > 0) {
            JavaS = JavaS.substring(0, (JavaS.length() - 1));
        }
        JavaS += "]');";
        executeJavaScript(JavaS);
        refresh();
    }
}
