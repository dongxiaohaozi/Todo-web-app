package tw.cn.cap.gtb.todo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static tw.cn.cap.gtb.todo.TodoApp.logger;

public class TaskRepository {

    public static Connection createConn() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:13306/todoapp", "root", "p@ssword");

    }

    public boolean check_table_exist() throws SQLException, ClassNotFoundException {
        try (
                Connection conn = createConn()
        ) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tasks = metaData.getTables(null, null, "tasks", null);
            return tasks.next();
        }
    }
    public void create_table() {
        try {
            try (
                    Connection conn = createConn();
                    PreparedStatement preparedStatement = conn.prepareStatement("" +
                            "create table if not exists tasks \n" +
                            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, \n" +
                            "name VARCHAR(128) NOT NULL, \n" +
                            "completed BOOLEAN NOT NULL DEFAULT(0))")
            ) {
                preparedStatement.execute();
            }
        } catch (SQLException  | ClassNotFoundException e) {
            logger.severe("Failed to create table" + e.getMessage());
        }
    }

    public List<Task> fetch_getTasks() throws SQLException, ClassNotFoundException {
        try (
                Connection conn = createConn();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "select * from tasks"
                )
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Task> tasks = new ArrayList<>();
            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                boolean completed = resultSet.getBoolean("completed");
                tasks.add(new Task(id, name, completed));
            }
            return tasks;
        }
    }

    public Long fetch_post_Task(Task task) throws SQLException, ClassNotFoundException {
        try (
                Connection conn = createConn();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "insert into tasks (name) values (?)", Statement.RETURN_GENERATED_KEYS
                )
        ) {
            preparedStatement.setString(1, task.getName());
             preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong(1);
        }
    }

    public Task fetch_change_status(Task task) throws SQLException, ClassNotFoundException {
        try (
                Connection conn = createConn();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "update tasks set completed = ? where id = ?"
                )
        ) {
            preparedStatement.setBoolean(1,task.isCompleted());
            preparedStatement.setLong(2,task.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IDNotFoundException();
            }
            return task;

        }
    }

    public void fetch_delete_task(Long id) throws SQLException, ClassNotFoundException {
        try (
                Connection conn = createConn();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "delete from tasks where id = ?"
                )
        ) {
            preparedStatement.setLong(1,id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new IDNotFoundException();
            }
        }
    }
}
