package tw.cn.cap.gtb.todo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static tw.cn.cap.gtb.todo.TodoApp.logger;

public class TaskHandler implements HttpHandler {

    public TaskService taskService;
    //private static HttpExchange exchange;

    public TaskHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("ready to handle "+exchange.getRequestMethod());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://127.0.0.1:1234");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "*");
        switch (exchange.getRequestMethod().toUpperCase()) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            case "PUT":
                handlePut(exchange);
                break;
            case "OPTIONS":
                handleOption(exchange);
                break;
            case "DELETE":
                handleDelete(exchange);
                break;
            default:
                break;
        }
    }


    private static void send_response(HttpExchange exchange,int rCode, String response) throws IOException {
        OutputStream responseBody = null;
        exchange.sendResponseHeaders(rCode, response.getBytes().length);
        if (!response.isEmpty()) {
            responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes());
            responseBody.flush();
        }
        responseBody.close();
        exchange.close();
    }

    private static void send_error_header(HttpExchange exchange, int rCode, Exception e) throws IOException {
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        String error = JsonUtil.ObjectToJson(errorResult);
        exchange.sendResponseHeaders(rCode, error.getBytes().length);
    }
    private void handleGet(HttpExchange exchange) throws IOException {
        logger.info("handle get");
        try {
            String query = exchange.getRequestURI().getQuery();
            List<Task> tasks = taskService.getTasks(query);
            var response = JsonUtil.ObjectToJson(tasks);
            send_response(exchange,200, response);

        } catch (SQLException | ClassNotFoundException e) {
            send_error_header(exchange,500, e);

        }
    }
    private void handleOption(HttpExchange exchange) throws IOException {
        logger.info("handle options");
        send_response(exchange,204, "");
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        logger.info("handle post");
        try {
            Task task = getTaskFromRequestBody(exchange);
            Task addedTask = taskService.postTask(task);
            String response = JsonUtil.ObjectToJson(addedTask);
            send_response(exchange,201, response);
        } catch (DeserializationException e) {
            send_error_header(exchange,400, e);
        } catch (Exception e) {
            send_error_header(exchange,500, e);
        }
    }

    private static Task getTaskFromRequestBody(HttpExchange exchange) {
        InputStream requestBody = exchange.getRequestBody();
        Task task = JsonUtil.JsonToObject(requestBody, Task.class);
        return task;
    }



    private void handlePut(HttpExchange exchange) throws IOException {
        try {
            long id = getIdFromRequestURI(exchange);
            Task task = getTaskFromRequestBody(exchange);
            task.setId(id);
            Task changedTask = taskService.change_status(task);
            var response = JsonUtil.ObjectToJson(changedTask);
            send_response(exchange,200, response);
        } catch (DeserializationException e) {
            send_error_header(exchange,400, e);
        } catch (IDNotFoundException e) {
            send_error_header(exchange,404, e);
        } catch (Exception e) {
            send_error_header(exchange,500, e);
        }

    }

    private static long getIdFromRequestURI(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        long id = Long.parseLong(path.substring(7));
        return id;
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        try {
            long id = getIdFromRequestURI(exchange);
            taskService.deleteTask(id);
            send_response(exchange,204, "");
        } catch (IDNotFoundException e) {
            send_error_header(exchange,404, e);
        } catch (Exception e) {
            send_error_header(exchange,500, e);
        }
    }
}
