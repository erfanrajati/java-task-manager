import java.util.ArrayList;
import java.util.Scanner;

public class DailyTasks {
    public static void main(String[] args) {
        TasksQueue dailyTasks = new TasksQueue();
        boolean continueProgram = true;
        String message = "";

        try (Scanner userIn = new Scanner(System.in)) {
            while (continueProgram) {
                int command = userIn.nextInt();
                switch (command) {
                    case 1 -> { // Add task
                        int taskID = userIn.nextInt();
                        int priority = userIn.nextInt();
                        RootTask root = new RootTask();
                        root.insertRoot(taskID, priority);
                        dailyTasks.addTask(root);
                        continue;
                    }
                    case 2 -> { // Assign existing task as a child to another task
                        int parentID = userIn.nextInt();
                        int childID = userIn.nextInt();
                        Task child = null;
                        for (RootTask rt : dailyTasks.tasksList) {
                            if (rt.root.id == childID) {
                                child = rt.root;
                                dailyTasks.removeTaskFromList(childID);
                                break;
                            } else if (rt.hasID(childID)) {
                                for (Task t : rt.storedIDs) {
                                    if (t.id == childID) {
                                        child = t;
                                        rt.deleteTask(childID);
                                        break;
                                    }
                                }
                            }
                        }
                        if (child != null) {
                            // System.out.println("Child Found");
                            for (RootTask task : dailyTasks.tasksList) {
                                if (task.hasID(parentID)) {
                                    // System.out.println("Child About to be set");
                                    task.insertSubTask(parentID, child);
                                }
                            }      
                        } else {
                            System.out.print("Child not found: ");
                            System.out.println(childID);
                        } 
                        continue;
                    }
                    case 3 -> { // Delete existing task
                        int taskID = userIn.nextInt();
                        for (RootTask task : dailyTasks.tasksList) {
                            if (task.hasID(taskID)) {
                                task.deleteTask(taskID);
                                dailyTasks.removeTaskFromList(taskID);
                                break;
                            }
                        }
                        continue;
                    }
                    case 4 -> { // Changing the priority of existing task
                    
                        int taskID = userIn.nextInt();
                        int newPriority = userIn.nextInt();
                        for (RootTask task : dailyTasks.tasksList) {
                            task.changePriority(taskID, newPriority);
                        }
                        continue;
                    }
                    case 5 -> { // Printing a String illustration of all tasks considering their parent-child relation
                        message += dailyTasks.getTasksTree();
                        message += "\n";
                        continue;
                    }
                    case 6 -> { // Printing all tasks with respect to priority
                        ArrayList<Task> orderedTaskList = dailyTasks.getOrderedTasks();
                        for (Task task : orderedTaskList) {
                            message += Integer.toString(task.id);
                            message += " ";
                        }
                        message += "\n";
                        continue;
                    }
                    case 0 -> {
                        continueProgram = false;
                        System.out.print(message);
                    }
                }
            }
        }
    }
}