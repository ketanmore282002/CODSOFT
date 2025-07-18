import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int maxAttempts = 7;
        int min = 1, max = 100;
        int roundsPlayed = 0;
        int roundsWon = 0;

        System.out.println(" Welcome to the Number Guessing Game!");

        boolean playAgain = true;

        while (playAgain) 
        {
            int numberToGuess = rand.nextInt(max - min + 1) + min;
            int attempts = 0;
            boolean hasWon = false;

            System.out.println("\n I'm thinking of a number between " + min + " and " + max + ".");
            System.out.println("You have " + maxAttempts + " attempts. Let's go!");

            while (attempts < maxAttempts) 
            {
                System.out.print("Enter your guess: ");
                int guess;

                // Input validation
                if (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    sc.next(); // discard invalid input
                    continue;
                }

                guess = sc.nextInt();
                attempts++;

                if (guess == numberToGuess) {
                    System.out.println(" Correct! You guessed the number in " + attempts + " attempt(s).");
                    hasWon = true;
                    roundsWon++;
                    break;
                } else if (guess < numberToGuess) {
                    System.out.println(" Too low! Try again.");
                } else {
                    System.out.println(" Too high! Try again.");
                }

                System.out.println("Attempts left: " + (maxAttempts - attempts));
            }

            if (!hasWon) {
                System.out.println(" You've run out of attempts. The number was: " + numberToGuess);
            }

            roundsPlayed++;
            System.out.print("\n Do you want to play another round? (yes/no): ");
            String response = sc.next().toLowerCase();
            playAgain = response.equals("yes") || response.equals("y");
        }

        System.out.println("\nGame Over!");
        System.out.println("Total Rounds Played: " + roundsPlayed);
        System.out.println("Rounds Won: " + roundsWon);
        System.out.println("Thanks for playing!");
        sc.close();
    }
}
