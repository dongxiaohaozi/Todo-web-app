package tw.cn.cap.gtb.todo;

public class IDNotFoundException extends RuntimeException {
    public IDNotFoundException() {
        super("ID not found!");
    }
}
