import java.io.*;
import java.util.*;

public class StudentManagemant {

    private static List<Student> students = new ArrayList<>();
    private static final String FILE_PATH = "students.txt";

    public static void main(String[] args) {
        loadStudents();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Display Students");
            System.out.println("5. Calculate GPA");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    deleteStudent(scanner);
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    displayStudents();
                    break;
                case 5:
                    calculateGPA(scanner);
                    break;
                case 6:
                    generateReport(scanner);
                    break;
                case 7:
                    saveStudents();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Courses : ");
        String id = scanner.nextLine();
        students.add(new Student(name, id, new ArrayList<>()));
        System.out.println("Student added successfully!");
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();
        students.removeIf(student -> student.id.equals(id));
        System.out.println("Student deleted successfully!");
    }

    private static void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine();
        for (Student student : students) {
            if (student.id.equals(id)) {
                System.out.print("Enter new name: ");
                student.name = scanner.nextLine();
                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void displayStudents() {
        for (Student student : students) {
            System.out.println("Name: " + student.name + ", ID: " + student.id);
            for (Course course : student.courses) {
                System.out.println("  Course: " + course.name + ", Grade: " + course.grade);
            }
        }
    }

    private static void calculateGPA(Scanner scanner) {
        System.out.print("Enter student ID to calculate GPA: ");
        String id = scanner.nextLine();
        for (Student student : students) {
            if (student.id.equals(id)) {
                double totalPoints = 0;
                int totalCourses = student.courses.size();
                for (Course course : student.courses) {
                    totalPoints += Grade.getGradePoint(course.grade);
                }
                double gpa = totalCourses == 0 ? 0 : totalPoints / totalCourses;
                System.out.println("GPA: " + gpa);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void generateReport(Scanner scanner) {
        System.out.print("Enter student ID to generate report: ");
        String id = scanner.nextLine();
        for (Student student : students) {
            if (student.id.equals(id)) {
                System.out.println("Report for " + student.name + " (ID: " + student.id + ")");
                for (Course course : student.courses) {
                    System.out.println("  Course: " + course.name + ", Grade: " + course.grade);
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                String id = data[1];
                List<Course> courses = new ArrayList<>();
                for (int i = 2; i < data.length; i += 2) {
                    courses.add(new Course(data[i], Grade.valueOf(data[i + 1])));
                }
                students.add(new Student(name, id, courses));
            }
        } catch (IOException e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                writer.write(student.name + "," + student.id);
                for (Course course : student.courses) {
                    writer.write("," + course.name + "," + course.grade);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    static class Student {
        String name;
        String id;
        List<Course> courses;

        Student(String name, String id, List<Course> courses) {
            this.name = name;
            this.id = id;
            this.courses = courses;
        }
    }

    static class Course {
        String name;
        Grade grade;

        Course(String name, Grade grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    enum Grade {
        A, B, C, D, F;

        public static double getGradePoint(Grade grade) {
            switch (grade) {
                case A: return 4.0;
                case B: return 3.0;
                case C: return 2.0;
                case D: return 1.0;
                case F: return 0.0;
                default: return 0.0;
            }
        }
    }
}
