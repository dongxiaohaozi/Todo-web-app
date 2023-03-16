package tw.cn.cap.gtb.todo;

import java.sql.SQLException;

public class ErrorResult {
    private String message;

    public ErrorResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
