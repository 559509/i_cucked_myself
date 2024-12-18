import java.util.Scanner;

/**
 * The class serves as the entry point for the Blackjack game
 * It manages the game flow, including initializing the game, handling user input, 
 * processing game rounds, and determining whether to play again
 */
public class runningman {

    /**
     * The main method is the entry point of the program. It initializes the game, 
     * handles the gameplay loop, and allows the user to play multiple rounds
     * 
     * @param args I don't know what this does
     */
    public static void main(String[] args) {
        // Display the welcome message
        black.greeting();
        
        // Scanner for user input
        Scanner sc = new Scanner(System.in);
        boolean playAgain = true;

        // Main gameplay loop
        while (playAgain) {
            // Create a new game instance
            black game = new black();
            
            // Handle deposit of funds
            game.deposit();

            // Place a bet and proceed with the game if successful
            if (game.bet()) { // Ensure a valid bet is placed
                game.createEnt();      // Initialize entities (deck, dealer, player)
                game.assignCards();    // Populate the deck with cards
                game.dealFirst();      // Deal initial cards to dealer and player

                // Player's turn
                game.playerTurn();
                // Dealer's turn if the player did not bust
                if (game.calculateHandValue(game.getYou()) <= 21) {
                    game.dealerTurn();
                }
                // Evaluate and announce the winner
                game.evalWinner();

                // Handle withdrawal of balance
                game.withdraw();
            } else {
                System.out.println("Unable to place a valid bet. Ending round.");
            }

            // Prompt the player to decide whether to play another round
            System.out.print("Play again? (yes/no): ");
            String again = sc.nextLine();
            if (again.contains("y")) {
                playAgain = true;
            } else {
                playAgain = false;
            }

            // Display the player's current balance
            System.out.println("Your balance: " + game.getBal());
        }

        // Thank the player for participating
        System.out.println("Thank you for playing!");
    }
}
