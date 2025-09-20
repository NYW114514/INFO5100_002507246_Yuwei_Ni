import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int choice;

        do {
            System.out.println("\n===== Number Guessing Game =====");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    int numberToGuess = random.nextInt(10) + 1;
                    boolean guessedCorrectly = false;

                    System.out.println("I have chosen a number between 1 and 10. You have 5 attempts!");

                    for (int i = 1; i <= 5; i++) {
                        System.out.print("Attempt " + i + ": Enter your guess: ");
                        int guess = scanner.nextInt();

                        if (guess == numberToGuess) {
                            System.out.println("Correct! You guessed the number!");
                            guessedCorrectly = true;
                            break;
                        } else if (guess > numberToGuess) {
                            System.out.println("Too High!");
                        } else {
                            System.out.println("Too Low!");
                        }
                    }

                    if (!guessedCorrectly) {
                        System.out.println("Sorry! The correct number was: " + numberToGuess);
                    }
                    break;

                case 2:
                    System.out.println("Exiting the game. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);

        scanner.close();
    }
}
