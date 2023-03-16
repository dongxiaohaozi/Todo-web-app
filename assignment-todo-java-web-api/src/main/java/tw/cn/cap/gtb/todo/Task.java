package tw.cn.cap.gtb.todo;

public class Task {
    public Task () {

    }
    private long id;
    private String name;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private boolean completed;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task(long id, String name, boolean completed) {

        this.id = id;
        this.name = name;
        this.completed = completed;
    }
}
