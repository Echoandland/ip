import java.util.Scanner;
import java.util.ArrayList;
public class ChatBot {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm ChatBot");
        System.out.println("What can I do for you?");
        Scanner scanner = new Scanner(System.in);

        Storage storage = new Storage("data/duke.txt");
        ArrayList<Task> tasks = storage.load();
        
        while (true) {

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                storage.save(tasks);
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks.get(index).markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks.get(index));
                storage.save(tasks);
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks.get(index).unmarkDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks.get(index));
                storage.save(tasks);
                continue;
            }
            if (input.startsWith("mark")) {
                String[] parts = input.trim().split("\\s+"); // 按空格切
                if (parts.length < 2) {
                    System.out.println("Oops! Please specify which task to mark. Usage: mark <task number>");
                    continue;
                }

                int taskNumber;
                try {
                    taskNumber = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Oops! Task number must be an integer. Usage: mark <task number>");
                    continue;
                }

                int index = taskNumber - 1;
                if (index < 0 || index >= tasks.size()) {
                    System.out.println("Oops! That task number is out of range.");
                    continue;
                }

                tasks.get(index).markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks.get(index));
                storage.save(tasks);
                continue;
            }

            if (input.equalsIgnoreCase("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("No tasks yet.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));

                    }
                }
                continue;
            }
            if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                tasks.add(new Todo(desc));
                System.out.println("Added: " + tasks.get(tasks.size() - 1));
                storage.save(tasks);
                continue;
            }

            if (input.startsWith("deadline ")) {
                String rest = input.substring(9).trim();
                String[] parts = rest.split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                tasks.add(new Deadline(desc, by));
                System.out.println("Added: " + tasks.get(tasks.size() - 1));
                storage.save(tasks);
                continue;
            }

            if (input.startsWith("event ")) {
                String rest = input.substring(6).trim();
                String[] p1 = rest.split(" /from ", 2);
                String desc = p1[0].trim();
                String[] p2 = p1[1].split(" /to ", 2);
                String from = p2[0].trim();
                String to = p2[1].trim();
                tasks.add(new Event(desc, from, to));
                System.out.println("Added: " + tasks.get(tasks.size() - 1));
                storage.save(tasks);
                continue;
            }
            if (input.startsWith("delete ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                Task removed = tasks.remove(index);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removed);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                storage.save(tasks);
                continue;
            }


            // fallback：暂时当 todo（可选）
            tasks.add(new Todo(input));
            System.out.println("Added: " + tasks.get(tasks.size() - 1));
            storage.save(tasks);


            if (input.trim().equalsIgnoreCase("bye")) {
                storage.save(tasks);
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            System.out.println(input);
            // System.out.println(input);
        }
        scanner.close();
    }
    
}
