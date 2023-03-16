package tw.cn.cap.gtb.todo;

public class SerializationException extends RuntimeException {
    public SerializationException(String message) {
        super("Failed to serialize :"+ message);
    }
}
