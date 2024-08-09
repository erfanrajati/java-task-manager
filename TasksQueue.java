import java.util.ArrayList;

public class TasksQueue {
    ArrayList<RootTask> tasksList;

    public TasksQueue() {
        this.tasksList = new ArrayList<>();
    }

    public void addTask(RootTask task) {
        this.tasksList.add(task);
    }

    public void removeTaskFromList(int id) {
        for (RootTask rt : tasksList) {
            if (rt.root.id == id) {
                tasksList.remove(rt);
                return;
            }
        }
    }

    public ArrayList<Task> bubbleTaskSort(ArrayList<Task> tasks) {
        Task first;
        Task second;
        boolean switchPerformed = true;
        while (switchPerformed) {
            switchPerformed = false;
            for (int i = 0; i < tasks.size() - 1; i++) {
                first = tasks.get(i);
                second = tasks.get(i+1);
                if (first.priority < second.priority) {
                    tasks.set(i+1, first);
                    tasks.set(i, second);
                    switchPerformed = true;
                } else if (first.priority == second.priority && first.id > second.id) {
                        tasks.set(i+1, first);
                        tasks.set(i, second);
                        switchPerformed = true;
                }
            }
        }
        return tasks;
    }

    // Probably one of the shittiest algorithms I wrote :/
    public ArrayList<Task> getOrderedTasks() {
        ArrayList<Task> sortedTasks = new ArrayList<>();

        for (RootTask root : this.tasksList) {
            sortedTasks.addAll(root.getOrderedTasks());
        }

        return this.bubbleTaskSort(sortedTasks);
    }

    public String getTasksTree() {
        String result = "";
        for (RootTask task : this.tasksList) {
            String temp = task.getTasksTree();
            result += temp + " ";
        }
        return result;
    }
}
