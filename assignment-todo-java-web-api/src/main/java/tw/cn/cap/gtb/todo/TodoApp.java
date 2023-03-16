package tw.cn.cap.gtb.todo;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TodoApp {

    static final Logger logger = Logger.getLogger(TodoApp.class.getName());

    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepository();
        TaskService taskService = new TaskService(taskRepository);
        if (!initialize(taskRepository)) {
            return;
        }
        logger.info("Initialized successfully!");
        if (!start_server(taskService)) {
            return;
        }
        logger.info("start server successfully!");

    }

    private static boolean start_server(TaskService taskService) {
        boolean result = false;
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8081), 0);
            httpServer.createContext("/tasks", new TaskHandler(taskService));
            httpServer.start();
            result = true;
        } catch (IOException e) {
            logger.severe("Failed to start the server" + e.getMessage());
        }
        return result;
    }

    private static boolean initialize(TaskRepository taskRepository) {
        var result = false;
        try {
            if (!taskRepository.check_table_exist()) {
                logger.info("table exists:"+!taskRepository.check_table_exist());
                taskRepository.create_table();
            }
            result = true;
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Failed to initialize: " + e.getMessage());
        }
        return result;
    }
}
