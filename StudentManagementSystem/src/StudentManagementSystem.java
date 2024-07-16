import java.util.ArrayList;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class StudentManagementSystem {
    private ArrayList<Student> studentList = new ArrayList<>();
    private int studentIDCounter = 1; // Unique student ID counter

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }

    public void run() {
        Scanner input = new Scanner(System.in);

        System.out.println("--------------------------------------------------");
        int numStudents;
        while (true) {
            System.out.print("Enter the number of new students to be added: ");
            if (input.hasNextInt()) {
                numStudents = input.nextInt();
                input.nextLine(); // Consume the newline character
                break; // Exit the loop if a valid number is entered
            } else {
                input.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        for (int i = 0; i < numStudents; i++) {
            System.out.println("*******************************");
            System.out.println("Student " + (i + 1) + " details:");

            System.out.print("Enter student name: ");
            String name = input.nextLine();

            int year;
            while (true) {
                System.out.print("Enter year (1-3): ");
                if (input.hasNextInt()) {
                    year = input.nextInt();
                    if (year >= 1 && year <= 3) {
                        input.nextLine(); // Consume the newline character
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Invalid year. Please enter a year between 1 and 3.");
                    }
                } else {
                    input.nextLine(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Now, calculate the student's unique ID with the prefix of the entered year
            int studentID = year * 10000 + studentIDCounter++;

            System.out.println("--------------------------------------------------");
            System.out.println("Select a course:");
            System.out.println("101 - Programming Foundations ($1200)");
            System.out.println("102 - User Interface Design ($1800)");
            System.out.println("103 - User Experience Design ($2000)");
            System.out.println("104 - Database Design and Implementation ($1500)");
            System.out.println("105 - Web Development Foundations ($2000)");
            System.out.println("106 - Capstone Project ($2500)");
            System.out.print("Enter course code: ");
            ArrayList<Integer> selectedCourses = new ArrayList<>();
            while (true) {
                System.out.println("\nSelect courses (101-106) for the student (enter 0 to finish):");
                System.out.print("Enter course code (0 to finish): ");
                if (input.hasNextInt()) {
                    int courseCode = input.nextInt();
                    if (courseCode == 0) {
                        input.nextLine(); // Consume the newline character
                        break;
                    } else if (courseCode >= 101 && courseCode <= 106) {
                        selectedCourses.add(courseCode);
                    } else {
                        System.out.println("Invalid course code. Please enter a valid course code.");
                    }
                } else {
                    input.nextLine(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Create a new student and add them to the list
            Student student = new Student(name, year, selectedCourses, studentID);
            studentList.add(student);
        }

        // Display student details and unique IDs
        System.out.println("\nStudent Details:");
        for (Student student : studentList) {
            System.out.println("--------------------------------------------------");
            System.out.println("Name: " + student.getName());
            System.out.println("Year: " + student.getYear());
            System.out.println("Unique ID: " + student.getStudentID() + "\n");
            System.out.println("Courses:");
            for (Integer courseCode : student.getCourses()) {
                System.out.println(courseCode + " - " + getCourseName(courseCode));
            }
            System.out.println("Total " + student.getName() + " Student Fees: $" + student.calculateTotalFees());
        }
        // Function selection menu
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Function Selection:");
            System.out.println("0. Add student");
            System.out.println("1. Make Payment");
            System.out.println("2. View Student Status");
            System.out.println("3. Send Confirmation Email");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                input.nextLine(); // Consume the newline character

                switch (choice) {
                    case 0:
                        addstudent();
                        break;
                    case 1:
                        makePayment();
                        break;
                    case 2:
                        viewStudentStatus();
                        break;
                    case 3:
                        sendConfirmationEmail();
                        break;
                    case 4:
                        System.out.println("--------------------------------------------------");
                        System.out.println("Exiting the system.");
                        System.out.println("--------------------------------------------------");
                        return;
                    default:
                        System.out.println("--------------------------------------------------");
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } else {
                input.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a valid number (1/2/3/4).");
            }
        }
    }

    public void addstudent(){
        Scanner input = new Scanner(System.in);

        System.out.println("--------------------------------------------------");
        int numStudents;
        while (true) {
            System.out.print("Enter the number of new students to be added: ");
            if (input.hasNextInt()) {
                numStudents = input.nextInt();
                input.nextLine(); // Consume the newline character
                break; // Exit the loop if a valid number is entered
            } else {
                input.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        for (int i = 0; i < numStudents; i++) {
            System.out.println("*******************************");
            System.out.println("Student " + (i + 1) + " details:");

            System.out.print("Enter student name: ");
            String name = input.nextLine();

            int year;
            while (true) {
                System.out.print("Enter year (1-3): ");
                if (input.hasNextInt()) {
                    year = input.nextInt();
                    if (year >= 1 && year <= 3) {
                        input.nextLine(); // Consume the newline character
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Invalid year. Please enter a year between 1 and 3.");
                    }
                } else {
                    input.nextLine(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Now, calculate the student's unique ID with the prefix of the entered year
            int studentID = year * 10000 + studentIDCounter++;

            System.out.println("--------------------------------------------------");
            System.out.println("Select a course:");
            System.out.println("101 - Programming Foundations ($1200)");
            System.out.println("102 - User Interface Design ($1800)");
            System.out.println("103 - User Experience Design ($2000)");
            System.out.println("104 - Database Design and Implementation ($1500)");
            System.out.println("105 - Web Development Foundations ($2000)");
            System.out.println("106 - Capstone Project ($2500)");
            System.out.print("Enter course code: ");
            ArrayList<Integer> selectedCourses = new ArrayList<>();
            while (true) {
                System.out.println("\nSelect courses (101-106) for the student (enter 0 to finish):");
                System.out.print("Enter course code (0 to finish): ");
                if (input.hasNextInt()) {
                    int courseCode = input.nextInt();
                    if (courseCode == 0) {
                        input.nextLine(); // Consume the newline character
                        break;
                    } else if (courseCode >= 101 && courseCode <= 106) {
                        selectedCourses.add(courseCode);
                    } else {
                        System.out.println("Invalid course code. Please enter a valid course code.");
                    }
                } else {
                    input.nextLine(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Create a new student and add them to the list
            Student student = new Student(name, year, selectedCourses, studentID);
            studentList.add(student);
        }

        // Display student details and unique IDs
        System.out.println("\nStudent Details:");
        for (Student student : studentList) {
            System.out.println("--------------------------------------------------");
            System.out.println("Name: " + student.getName());
            System.out.println("Year: " + student.getYear());
            System.out.println("Unique ID: " + student.getStudentID() + "\n");
            System.out.println("Courses:");
            for (Integer courseCode : student.getCourses()) {
                System.out.println(courseCode + " - " + getCourseName(courseCode));
            }
            System.out.println("Total " + student.getName() + " Student Fees: $" + student.calculateTotalFees());
        }

    }





    public String getCourseName(int courseCode) {
        switch (courseCode) {
            case 101:
                return "Programming Foundations";
            case 102:
                return "User Interface Design";
            case 103:
                return "User Experience Design";
            case 104:
                return "Database Design and Implementation";
            case 105:
                return "Web Development Foundations";
            case 106:
                return "Capstone Project";
            default:
                return "Unknown Course";
        }
    }

    // Function to allow students to make payments
    public void makePayment() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------");
        System.out.print("Enter student ID to make a payment: ");
        if (input.hasNextInt()) {
            int studentID = input.nextInt();

            for (Student student : studentList) {
                if (student.getStudentID() == studentID) {
                    System.out.println("--------------------------------------------------");
                    System.out.print("Enter the payment amount: $");
                    do {
                        if (input.hasNextInt()) {
                            int amount = input.nextInt();
                            if (amount >= 0) {
                                student.makePayment(amount);
                                System.out.println("--------------------------------------------------");
                                System.out.println("Payment of $" + amount + " successfully recorded.");
                                if (student.getStudentID() == studentID) {
                                    System.out.println("--------------------------------------------------");
                                    System.out.println("Student ID: " + studentID);
                                    System.out.println("Name: " + student.getName());
                                    System.out.println("Enrolled Courses: " + student.getCourses());
                                    System.out.println("Total Fees: $" + student.calculateTotalFees());
                                    System.out.println("Outstanding Balance: $" + (student.calculateTotalFees() - student.getPaidFees()));
                                    return;
                                }
                                return;
                            } else {
                                System.out.println("Invalid input. Payment amount must be greater than or equal to 0. Please enter a valid number.");
                                System.out.println("--------------------------------------------------");
                                System.out.print("Enter the payment amount: $");
                            }
                        } else {
                            input.nextLine(); // Consume the invalid input
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    } while (true); // Loop until valid input is provided for payment amount
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.println("Student with ID " + studentID + " not found.");
        } else {
            input.nextLine(); // Consume the invalid input
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }


    // Function to view student status by ID
    public void viewStudentStatus() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------");
        System.out.print("Enter student ID to view status: ");
        if (input.hasNextInt()) {
            int studentID = input.nextInt();

            for (Student student : studentList) {
                if (student.getStudentID() == studentID) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("Student ID: " + studentID);
                    System.out.println("Name: " + student.getName());
                    System.out.println("Enrolled Courses: " + student.getCourses());
                    System.out.println("Total Fees: $" + student.calculateTotalFees());
                    System.out.println("Outstanding Balance: $" + (student.calculateTotalFees() - student.getPaidFees()));
                    return;
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.println("Student with ID " + studentID + " not found.");
        } else {
            input.nextLine(); // Consume the invalid input
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    // Function to send confirmation emails
    public void sendConfirmationEmail() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------");
        System.out.print("Enter student ID to send confirmation email: ");
        if (input.hasNextInt()) {
            int studentID = input.nextInt();

            for (Student student : studentList) {
                if (student.getStudentID() == studentID) {
                    input.nextLine(); // Consume the newline character
                    System.out.println("--------------------------------------------------");
                    System.out.println("Selected Student Information for Email Confirmation:");
                    System.out.println("Student ID: " + student.getStudentID());
                    System.out.println("Name: " + student.getName());
                    System.out.println("Enrolled Courses: " + student.getCourses());
                    System.out.println("Total Fees: $" + student.calculateTotalFees());
                    System.out.println("Outstanding Balance: $" + (student.calculateTotalFees() - student.getPaidFees()));
                    System.out.println("--------------------------------------------------");
                    System.out.print("Enter recipient's email address: ");
                    String recipientEmail = input.nextLine();

                    // Configure email properties (e.g., SMTP server, credentials)
                    Properties properties = new Properties();
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");

                    // Create a Session with authentication
                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("Huynhngocanhminh.2024@gmail.com", "dyltcfkcourddryd");
                        }
                    });

                    try {
                        // Create a message
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("Huynhngocanhminh.2024@gmail.com"));
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
                        message.setSubject("Enrollment Confirmation");
                        message.setText("Dear student, your enrollment has been confirmed. Thank you for choosing our courses.\n Selected Student Information for Email Confirmation: \n Student ID: " + student.getStudentID() + "\n Name: " + student.getName() + "\n Enrolled Courses: " + student.getCourses() + "\n Total Fees: $" + student.calculateTotalFees() + "\n Outstanding Balance: $" + (student.calculateTotalFees() - student.getPaidFees()));

                        // Send the message
                        Transport.send(message);
                        System.out.println("--------------------------------------------------");
                        System.out.println("Confirmation email sent successfully.");
                        return;
                    } catch (MessagingException e) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Error sending confirmation email: " + e.getMessage());
                    }
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.println("Student with ID " + studentID + " not found.");
        } else {
            input.nextLine(); // Consume the invalid input
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    
}

class Student {
    private String name;
    private int year;
    private ArrayList<Integer> courses;
    private int studentID;
    private int paidFees; // Track paid fees

    public Student(String name, int year, ArrayList<Integer> courses, int studentID) {
        this.name = name;
        this.year = year;
        this.courses = courses;
        this.studentID = studentID;
        this.paidFees = 0; // Initialize paidFees to 0
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<Integer> getCourses() {
        return courses;
    }

    public int getStudentID() {
        return studentID;
    }

    public int calculateTotalFees() {
        int totalFees = 0;

        for (Integer courseCode : courses) {
            totalFees += getCourseFees(courseCode);
        }
        return totalFees;
    }

    public int getCourseFees(int courseCode) {
        switch (courseCode) {
            case 101:
                return 1200;
            case 102:
                return 1800;
            case 103:
                return 2000;
            case 104:
                return 1500;
            case 105:
                return 2000;
            case 106:
                return 2500;
            default:
                return 0;
        }
    }

    // Added method to get paid fees
    public int getPaidFees() {
        return paidFees;
    }

    // Added method to make a payment
    public void makePayment(int amount) {

        paidFees += amount;
    }
}


