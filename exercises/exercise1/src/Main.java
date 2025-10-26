import java.util.*;

// Base Class
abstract class Student {
    protected String name;
    protected double[] quizzes = new double[15];

    public Student(String name, double[] quizzes) {
        this.name = name;
        this.quizzes = quizzes;
    }

    public String getName() {
        return name;
    }

    public double getAverageQuizScore() {
        double sum = 0;
        for (double q : quizzes) sum += q;
        return sum / quizzes.length;
    }

    public double[] getQuizzes() {
        return quizzes;
    }

    protected static double[] randomScores(int n) {
        Random rand = new Random();
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) arr[i] = 40 + rand.nextDouble() * 60;
        return arr;
    }
}

// Part-Time Student
class PartTimeStudent extends Student {
    public PartTimeStudent(String name, double[] quizzes) {
        super(name, quizzes);
    }
}

// Full-Time Student
class FullTimeStudent extends Student {
    private double exam1;
    private double exam2;

    public FullTimeStudent(String name, double[] quizzes, double exam1, double exam2) {
        super(name, quizzes);
        this.exam1 = exam1;
        this.exam2 = exam2;
    }

    public double[] getExams() {
        return new double[]{exam1, exam2};
    }
}

class Session {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        students.add(s);
    }

    public void calculateAverageQuizScores() {
        System.out.println("\nAverage Quiz Scores:");
        for (Student s : students) {
            System.out.printf("%-15s : %.2f\n", s.getName(), s.getAverageQuizScore());
        }
    }

    public void printQuizScoresAscending() {
        ArrayList<Double> allQuizzes = new ArrayList<>();
        for (Student s : students) {
            for (double q : s.getQuizzes()) {
                allQuizzes.add(q);
            }
        }

        Collections.sort(allQuizzes);
        System.out.println("\nAll Quiz Scores (Ascending):");

        int count = 0;
        for (double score : allQuizzes) {
            System.out.printf("%6.2f ", score);
            count++;
            if (count % 10 == 0) System.out.println();
        }
        System.out.println();
    }

    public void printPartTimeStudentNames() {
        System.out.println("\nPart-Time Students:");
        for (Student s : students) {
            if (s instanceof PartTimeStudent) {
                System.out.println("- " + s.getName());
            }
        }
    }

    public void printFullTimeExamScores() {
        System.out.println("\nFull-Time Students' Exam Scores:");
        for (Student s : students) {
            if (s instanceof FullTimeStudent) {
                FullTimeStudent f = (FullTimeStudent) s;
                double[] exams = f.getExams();
                System.out.printf("%-15s : Exam1 = %.1f, Exam2 = %.1f\n",
                        f.getName(), exams[0], exams[1]);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Session session = new Session();

        for (int i = 1; i <= 10; i++) {
            session.addStudent(new PartTimeStudent(
                    "PartTime_" + i, Student.randomScores(15)));
        }
        for (int i = 1; i <= 10; i++) {
            double[] quizzes = Student.randomScores(15);
            double exam1 = 50 + Math.random() * 50;
            double exam2 = 50 + Math.random() * 50;
            session.addStudent(new FullTimeStudent(
                    "FullTime_" + i, quizzes, exam1, exam2));
        }

        session.calculateAverageQuizScores();
        session.printQuizScoresAscending();
        session.printPartTimeStudentNames();
        session.printFullTimeExamScores();
    }
}
