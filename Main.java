import java.util.*;

// Simple Student Class
class Student {
    String id;
    String name;
    String programme;
    double marks;

    public Student(String id, String name, String programme, double marks) {
        this.id = id;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Programme: " + programme + " | Marks: " + marks;
    }
}

public class Main {
    // Standard Java Collections to satisfy data structure requirements
    private static LinkedList<Student> studentLinkedList = new LinkedList<>(); // Linked List
    private static Stack<String> actionStack = new Stack<>();                  // Stack
    private static Queue<String> serviceQueue = new LinkedList<>();            // Queue
    private static HashMap<String, Student> studentHashTable = new HashMap<>();// Hash Table
    
    // Graph representation using Adjacency List (Map of Location Name -> List of Connected Locations)
    private static Map<String, List<String>> campusGraph = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Pre-fill some initial data for testing
        initializeData();

        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Add Student Record");
            System.out.println("2. Update Student Record");
            System.out.println("3. Delete Student Record");
            System.out.println("4. Display Records (Linked List)");
            System.out.println("5. Add Service Request (Queue)");
            System.out.println("6. Process Next Service Request");
            System.out.println("7. Display Recent Actions (Stack)");
            System.out.println("8. Display Students Sorted (BST Simulation)");
            System.out.println("9. Search Student (Hashing)");
            System.out.println("10. Add Campus Location");
            System.out.println("11. Remove Campus Location");
            System.out.println("12. Add Campus Connection/Road");
            System.out.println("13. Remove Campus Connection/Road");
            System.out.println("14. Display Campus Connections");
            System.out.println("15. Traverse Campus Locations (BFS)");
            System.out.println("16. Exit");
            System.out.print("Enter choice (1-16): ");

            int choice = readInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: displayLinkedList(); break;
                case 5: addRequest(); break;
                case 6: processRequest(); break;
                case 7: displayStack(); break;
                case 8: displayBST(); break;
                case 9: searchHash(); break;
                case 10: addLocation(); break;
                case 11: removeLocation(); break;
                case 12: addConnection(); break;
                case 13: removeConnection(); break;
                case 14: displayGraph(); break;
                case 15: traverseBFS(); break;
                case 16:
                    System.out.println("Exiting Program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private static void initializeData() {
        Student s1 = new Student("S101", "Alice", "IT", 85.0);
        studentLinkedList.add(s1);
        studentHashTable.put(s1.id.toUpperCase(), s1);

        campusGraph.put("LIBRARY", new ArrayList<>(Arrays.asList("HALL")));
        campusGraph.put("HALL", new ArrayList<>(Arrays.asList("LIBRARY", "LAB")));
        campusGraph.put("LAB", new ArrayList<>(Arrays.asList("HALL")));
    }

    // 1. Add Student
    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        if (studentHashTable.containsKey(id.toUpperCase())) {
            System.out.println("Error: Student ID already exists!");
            return;
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Programme: ");
        String prog = scanner.nextLine().trim();
        System.out.print("Enter Marks (0-100): ");
        double marks = readDouble();

        Student s = new Student(id, name, prog, marks);
        studentLinkedList.add(s);
        studentHashTable.put(id.toUpperCase(), s);
        actionStack.push("Added Student: " + id);
        System.out.println("Student added successfully.");
    }

    // 2. Update Student
    private static void updateStudent() {
        System.out.print("Enter Student ID to Update: ");
        String id = scanner.nextLine().trim().toUpperCase();
        Student s = studentHashTable.get(id);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.print("Enter New Name: ");
        s.name = scanner.nextLine().trim();
        System.out.print("Enter New Programme: ");
        s.programme = scanner.nextLine().trim();
        System.out.print("Enter New Marks: ");
        s.marks = readDouble();

        actionStack.push("Updated Student: " + id);
        System.out.println("Student updated successfully.");
    }

    // 3. Delete Student
    private static void deleteStudent() {
        System.out.print("Enter Student ID to Delete: ");
        String id = scanner.nextLine().trim().toUpperCase();
        Student s = studentHashTable.get(id);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        studentLinkedList.remove(s);
        studentHashTable.remove(id);
        actionStack.push("Deleted Student: " + id);
        System.out.println("Student deleted successfully.");
    }

    // 4. Display Linked List
    private static void displayLinkedList() {
        if (studentLinkedList.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("--- Records in Linked List ---");
        for (Student s : studentLinkedList) {
            System.out.println(s);
        }
    }

    // 5. Add Service Request (Queue)
    private static void addRequest() {
        System.out.print("Enter Service Request: ");
        String req = scanner.nextLine().trim();
        serviceQueue.add(req);
        actionStack.push("Queued Request: " + req);
        System.out.println("Request added to queue.");
    }

    // 6. Process Service Request
    private static void processRequest() {
        if (serviceQueue.isEmpty()) {
            System.out.println("No requests in queue.");
            return;
        }
        String req = serviceQueue.poll();
        actionStack.push("Processed Request: " + req);
        System.out.println("Processed Request: " + req);
    }

    // 7. Display Stack
    private static void displayStack() {
        if (actionStack.isEmpty()) {
            System.out.println("No recent actions.");
            return;
        }
        System.out.println("--- Recent Actions (Stack) ---");
        for (int i = actionStack.size() - 1; i >= 0; i--) {
            System.out.println("- " + actionStack.get(i));
        }
    }

    // 8. BST Representation
    private static void displayBST() {
        if (studentLinkedList.isEmpty()) {
            System.out.println("No records to display.");
            return;
        }
        List<Student> sortedList = new ArrayList<>(studentLinkedList);
        sortedList.sort(Comparator.comparing(s -> s.id));
        System.out.println("--- Students Sorted by ID (BST Order) ---");
        for (Student s : sortedList) {
            System.out.println(s);
        }
    }

    // 9. Search (Hashing)
    private static void searchHash() {
        System.out.print("Enter Student ID to Search: ");
        String id = scanner.nextLine().trim().toUpperCase();
        Student s = studentHashTable.get(id);
        if (s != null) {
            System.out.println("Found (O(1) Hash Lookup): " + s);
        } else {
            System.out.println("Student ID not found.");
        }
    }

    // 10. Add Campus Location
    private static void addLocation() {
        System.out.print("Enter Location Name: ");
        String loc = scanner.nextLine().trim().toUpperCase();
        if (campusGraph.containsKey(loc)) {
            System.out.println("Location already exists.");
            return;
        }
        campusGraph.put(loc, new ArrayList<>());
        actionStack.push("Added Location: " + loc);
        System.out.println("Location added.");
    }

    // 11. Remove Campus Location
    private static void removeLocation() {
        System.out.print("Enter Location Name: ");
        String loc = scanner.nextLine().trim().toUpperCase();
        if (!campusGraph.containsKey(loc)) {
            System.out.println("Location not found.");
            return;
        }
        campusGraph.remove(loc);
        for (List<String> neighbours : campusGraph.values()) {
            neighbours.remove(loc);
        }
        actionStack.push("Removed Location: " + loc);
        System.out.println("Location removed.");
    }

    // 12. Add Campus Connection
    private static void addConnection() {
        System.out.print("Enter Location 1: ");
        String loc1 = scanner.nextLine().trim().toUpperCase();
        System.out.print("Enter Location 2: ");
        String loc2 = scanner.nextLine().trim().toUpperCase();

        if (!campusGraph.containsKey(loc1) || !campusGraph.containsKey(loc2)) {
            System.out.println("Error: One or both locations do not exist.");
            return;
        }
        if (!campusGraph.get(loc1).contains(loc2)) campusGraph.get(loc1).add(loc2);
        if (!campusGraph.get(loc2).contains(loc1)) campusGraph.get(loc2).add(loc1);

        actionStack.push("Connected: " + loc1 + " <-> " + loc2);
        System.out.println("Connection added.");
    }

    // 13. Remove Campus Connection
    private static void removeConnection() {
        System.out.print("Enter Location 1: ");
        String loc1 = scanner.nextLine().trim().toUpperCase();
        System.out.print("Enter Location 2: ");
        String loc2 = scanner.nextLine().trim().toUpperCase();

        if (campusGraph.containsKey(loc1) && campusGraph.containsKey(loc2)) {
            campusGraph.get(loc1).remove(loc2);
            campusGraph.get(loc2).remove(loc1);
            actionStack.push("Disconnected: " + loc1 + " <-> " + loc2);
            System.out.println("Connection removed.");
        } else {
            System.out.println("Error: Locations not found.");
        }
    }

    // 14. Display Campus Connections
    private static void displayGraph() {
        if (campusGraph.isEmpty()) {
            System.out.println("No locations in graph.");
            return;
        }
        System.out.println("--- Campus Graph (Adjacency List) ---");
        for (String loc : campusGraph.keySet()) {
            System.out.println(loc + " -> " + campusGraph.get(loc));
        }
    }

    // 15. BFS Traversal
    private static void traverseBFS() {
        System.out.print("Enter Starting Location: ");
        String start = scanner.nextLine().trim().toUpperCase();

        if (!campusGraph.containsKey(start)) {
            System.out.println("Location not found.");
            return;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();

        visited.add(start);
        q.add(start);

        System.out.print("BFS Traversal: ");
        while (!q.isEmpty()) {
            String curr = q.poll();
            System.out.print(curr + " ");
            for (String neighbor : campusGraph.get(curr)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    q.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // Input Helpers
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                if (v >= 0 && v <= 100) return v;
                System.out.print("Enter marks between 0 and 100: ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid decimal. Try again: ");
            }
        }
    }
}