import java.util.ArrayList;
import java.util.Stack;

public class RootTask {
    public Task root;
    ArrayList<Task> storedIDs = new ArrayList<>();

    public RootTask() {
        this.root = null;
    }
    
    // Specify the root of the tree (Command 1)
    public void insertRoot(int id, int priority) {
        Task task = new Task(id, priority);
        if (root == null) {    
            this.root = task;
            this.storedIDs.add(task);
        }
    }

    public void removeID(int id) {
        for (int i = 0; i < storedIDs.size(); i++) {
            if (storedIDs.get(i).id == id) {
                storedIDs.remove(i);
                return;
            }
        }
        System.out.println("ID not found.");
        System.out.println(id);
    }

    // Insert a sub-task that is already initialized (Command 2)
    public Task insertSubTask(int parentID, Task child) {
        if (this.root == null) {
            return null;
        }
    
        Stack<Task> stack = new Stack<>();
        stack.push(this.root);
    
        while (!stack.isEmpty()) {
            Task current = stack.pop();
    
            if (current.id == parentID) {
                if (current.left == null) {
                    // System.out.print("Child is set: ");
                    // System.out.println(parentID);
                    current.left = child;
                    storedIDs.add(child);
                    return child;
                } else if (current.right == null) {
                    // System.out.print("Child is set: ");
                    // System.out.println(parentID);
                    current.right = child;
                    storedIDs.add(child);
                    return child;
                } else {
                    System.out.print("No more subtasks allowed for this parent: ");
                    System.out.println(parentID);
                    return null;
                }
            }
    
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        System.out.println("Parent task not found.");
        System.out.println(parentID);
        return null;
    }
    

    // Recursive implementation for task deleting with pre-order traversal approach (Command 3)
    public void deleteTask(Task node, int id) {
        Stack<Task> stack = new Stack<>();
        stack.push(this.root);
        while (!stack.isEmpty()) {
            Task current = stack.pop();

            try {
                if (current.left.id == id) {
                    current.left = null;
                    removeID(id);
                    return;
                } 
                if (current.right.id == id) {
                    current.right = null;
                    removeID(id);
                    return;
                }
            } catch (NullPointerException e) {}

            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        System.out.print("Task not found: ");
        System.out.println(id);
    }
    // Overloaded deleteTask for single argument call.
    public void deleteTask(int id) { 
        try {
            if (this.root.id == id) {
                this.root = null;
            } else {
                deleteTask(root, id);
            }
        } catch (NullPointerException e) {}
    }

    // Command 3
    public void changePriority(Task node, int id, int newPriority) {
        if (node == null) {
            // System.out.println("Task ID not found.");
            return;
        }

        Stack<Task> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            node = stack.pop();

            if (node.id == id) {
                node.priority = newPriority;
                for (Task task : this.storedIDs) {
                    if (task.id == id) {
                        task.priority = newPriority;
                    }
                }
                // System.out.println("Priority changed.");
                return;
            }

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        // System.out.println("Task ID not found.");
    }
    // Overloaded changePriority for double argument call.
    public void changePriority(int id, int newPriority) {
        changePriority(this.root, id, newPriority);
    }

    // Obvious, used many times in UI.
    public boolean hasID(int id) {
        for (Task task : this.storedIDs) {
            if (task.id == id) {
                return true;
            }
        }
        return false;
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
                }
            }
        }
        return tasks;
    }


    // This method returns all of the tasks in the tree in an ArrayList with respect to priority order (Command 6)
    public ArrayList<Task> getOrderedTasks() {
        ArrayList<Task> result = new ArrayList<>();
        if (this.root == null) {
            return result;
        }

        Stack<Task> stack = new Stack<>();
        stack.push(this.root);

        while (!stack.isEmpty()) {
            Task node = stack.pop();
            result.add(node);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
        // return bubbleTaskSort(result);
    }

    // This method returns a string of all tasks stores in the tree with respect to parent-child relation (Command 5)
    public String getTasksTree(Task node, String input) {
        String result = input;
        
        if (node == null) 
            return "";
        else 
            result = result.concat(Integer.toString(node.id));
        
        if (node.left == null && node.right == null) 
            return result;
        
        result = result.concat(" [");
        if (node.right != null && node.left != null) {
            if (node.right.id < node.left.id) {
                result = getTasksTree(node.right, result);
                result += " ";
                result = getTasksTree(node.left, result);
            } else {
                result = getTasksTree(node.left, result);
                result += " ";
                result = getTasksTree(node.right, result);
            }
        } else {
            Task temp = (node.right == null) ? node.left : node.right;
            result = getTasksTree(temp, result);
        } 
        result = result.concat("]");
        return result;
    }
    // Overloaded for easier use.
    public String getTasksTree() {
        return getTasksTree(this.root, "");
    }
}
