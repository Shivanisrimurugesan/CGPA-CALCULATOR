import java.util.ArrayList;
import java.util.Scanner;

class Course {
    String courseCode;
    int credits;
    double gradePoints;

    public Course(String courseCode, int credits, double gradePoints) {
        this.courseCode = courseCode;
        this.credits = credits;
        this.gradePoints = gradePoints;
    }
}

class Semester {
    ArrayList<Course> courses;

    public Semester() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public double calculateGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Course course : courses) {
            totalPoints += course.credits * course.gradePoints;
            totalCredits += course.credits;
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0;
    }

    public boolean hasFailedSubject() {
        for (Course course : courses) {
            if (course.gradePoints == 0.0) {
                return true;
            }
        }
        return false;
    }

    public int getTotalCredits() {
        int totalCredits = 0;

        for (Course course : courses) {
            totalCredits += course.credits;
        }

        return totalCredits;
    }

    public double getTotalPoints() {
        double totalPoints = 0;

        for (Course course : courses) {
            totalPoints += course.credits * course.gradePoints;
        }

        return totalPoints;
    }
}

public class GPA_CGPA_Calculator {

    public static double calculateGradePoints(double marks) {
        if (marks >= 91) return 10.0; // O (Outstanding)
        else if (marks >= 81) return 9.0; // A+ (Excellent)
        else if (marks >= 71) return 8.0; // A (Very Good)
        else if (marks >= 61) return 7.0; // B+ (Good)
        else if (marks >= 50) return 6.0; // B (Average)
        else return 0.0; // RA (Reappear)
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Semester> semesters = new ArrayList<>();
        boolean futureCGPAInvalid = false; // Tracks if CGPA should be invalidated after a failed semester
        double cumulativePoints = 0;
        int cumulativeCredits = 0;

        System.out.println("Welcome to GPA and CGPA Calculator!");

        while (true) {
            Semester semester = new Semester();
            System.out.print("Enter the number of courses in this semester: ");
            int numCourses = scanner.nextInt();

            for (int i = 0; i < numCourses; i++) {
                System.out.print("Enter course code: ");
                String courseCode = scanner.next();
                System.out.print("Enter course credits: ");
                int credits = scanner.nextInt();
                System.out.print("Enter marks obtained: ");
                double marks = scanner.nextDouble();

                double gradePoints = calculateGradePoints(marks);
                Course course = new Course(courseCode, credits, gradePoints);
                semester.addCourse(course);
            }

            if (semester.hasFailedSubject()) {
                System.out.println("GPA for this semester: --");
                futureCGPAInvalid = true; // Mark future CGPA as invalid
            } else if (!futureCGPAInvalid) {
                double gpa = semester.calculateGPA();
                cumulativePoints += semester.getTotalPoints();
                cumulativeCredits += semester.getTotalCredits();
                double cgpa = cumulativeCredits > 0 ? cumulativePoints / cumulativeCredits : 0;

                System.out.printf("GPA for this semester: %.2f%n", gpa);
                System.out.printf("Cumulative CGPA: %.2f%n", cgpa);
            } else {
                double gpa = semester.calculateGPA();
                System.out.printf("GPA for this semester: %.2f%n", gpa);
                System.out.println("Cumulative CGPA: --");
            }

            semesters.add(semester);

            System.out.print("Do you want to add another semester? (yes/no): ");
            String choice = scanner.next();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }
}
