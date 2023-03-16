package tw.cn.cap.gtb.todo;

public class DeserializationException extends RuntimeException {
    public DeserializationException(String message) {
        super("Failed to deserialize :"+ message);
    }
}
