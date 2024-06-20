import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagement {
    private static final String FILE_NAME = "students.txt";
    private List<String[]> students;

    public StudentManagement() {
        students = new ArrayList<>();
        loadStudents();
    }

    public void addStudent(String id, String name) {
        students.add(new String[]{id, name});
        saveStudents();
    }

    public void deleteStudent(String studentId) {
        students.removeIf(student -> student[0].equals(studentId));
        saveStudents();
    }

    public void updateStudent(String studentId, String newName) {
        for (String[] student : students) {
            if (student[0].equals(studentId)) {
                student[1] = newName;
                saveStudents();
                break;
            }
        }
    }

    public void displayAllStudents() {
        for (String[] student : students) {
            System.out.println("Student ID: " + student[0] + ", Name: " + student[1]);
        }
    }

    private void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String[] student : students) {
                writer.write(student[0] + "," + student[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] student = line.split(",");
                students.add(student);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing student records found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagement sms = new StudentManagement();

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    sms.addStudent(id, name);
                    break;
                case 2:
                    System.out.print("Enter Student ID to delete: ");
                    id = scanner.nextLine();
                    sms.deleteStudent(id);
                    break;
                case 3:
                    System.out.print("Enter Student ID to update: ");
                    id = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    name = scanner.nextLine();
                    sms.updateStudent(id, name);
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
