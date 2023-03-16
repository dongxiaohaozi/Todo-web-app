package tw.cn.cap.gtb.todo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskService {
    public TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks(String query) throws SQLException, ClassNotFoundException {
        var allTask = taskRepository.fetch_getTasks();
        return filter_tasks(query, allTask);
    }

    private List<Task> filter_tasks(String query, List<Task> allTask) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (Objects.isNull(query)) {
            return allTask;
        }
        if (query.contains("completed=true")) {
            return allTask.stream().filter(Task::isCompleted).collect(Collectors.toList());
        }
        if (query.contains("completed=false")) {
            return allTask.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList());
        }
        return allTask;
    }

    public Task postTask(Task task) throws SQLException, ClassNotFoundException {
        Long id = taskRepository.fetch_post_Task(task);
        return new Task(id, task.getName(), false);
    }

    public Task change_status(Task task) throws SQLException, ClassNotFoundException {
        return taskRepository.fetch_change_status(task);
    }

    public void deleteTask(long id) throws SQLException, ClassNotFoundException {
        taskRepository.fetch_delete_task(id);
    }
}
