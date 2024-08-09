public class Task {
    int id;
    int priority;
    Task left;
    Task right;

    public Task(int id, int priority) {
        this.id = id;
        this.priority = priority;
        this.left = null;
        this.right = null;
    }
}