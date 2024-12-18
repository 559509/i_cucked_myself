import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The class implements a simplified Blackjack game
 * It manages the deck, player, and dealer entities, as well as the game flow, including betting, dealing cards, and evaluating the winner
 */
public class black {
    private Entity deck; // Represents the deck of cards
    private Entity dealer; // Represents the dealer's hand
    private Entity you; // Represents the player's hand
    private Random rand = new Random(); // Random number generator for shuffling and drawing cards
    private double bal = 0; // Balance for the player
    private double betAmount = 0; // The bet placed by the player

    /**
     * Constructor to initialize the game by creating entities and assigning cards to the deck
     */
    public black() {
        createEnt();
        assignCards();
    }

    /**
     * Prints a welcome message at the start of the game
     */
    public static void greeting() {
        System.out.println("Welcome to Gamba!");
        System.out.println();
    }

    /**
     * Allows the player to deposit funds into their balance
     */
    public void deposit() {
        Scanner balsc = new Scanner(System.in);
        System.out.println("How much would you like to deposit?");
        double depo = balsc.nextDouble();
        System.out.println("Added $" + depo);
        bal += depo;
        System.out.println("Your balance has been set to $" + bal);
    }

    /**
     * Handles placing a bet by the player. Ensures the bet is valid and within the player's balance
     * 
     * @return if the bet is valid, {@code false} otherwise
     */
    public boolean bet() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your bet amount: ");
        betAmount = sc.nextDouble();

        if (betAmount > 0 && betAmount <= bal) {
            bal -= betAmount;
            System.out.println("Bet placed: $" + betAmount);
            return true;
        } else {
            System.out.println("Invalid bet amount. Bet must be a positive value and within your balance.");
            return false;
        }
    }

    /**
     * Initializes the deck, dealer, and player entities
     */
    public void createEnt() {
        deck = new Entity();
        dealer = new Entity();
        you = new Entity();
    }

    /**
     * Populates the deck with all standard playing cards
     */
    public void assignCards() {
        ArrayList<String> allCards = new ArrayList<>(
            Arrays.asList("1Ah", "1Ad", "1Ac", "1As",
                          "2h", "2d", "2c", "2s",
                          "3h", "3d", "3c", "3s",
                          "4h", "4d", "4c", "4s",
                          "5h", "5d", "5c", "5s",
                          "6h", "6d", "6c", "6s",
                          "7h", "7d", "7c", "7s",
                          "8h", "8d", "8c", "8s",
                          "9h", "9d", "9c", "9s",
                          "10h", "10d", "10c", "10s",
                          "10Jh", "10Jd", "10Jc", "10Js",
                          "10Qh", "10Qd", "10Qc", "10Qs",
                          "10Kh", "10Kd", "10Kc", "10Ks")
        );
        
        // Add all cards to the deck
        for (String card : allCards) {
            deck.addCard(card);
        }
    }

    /**
     * Draws a random card from the deck
     * 
     * @return The card drawn from the deck as a String
     */
    private String drawCard() {
        int temp = rand.nextInt(deck.sizeOf());
        String cardDealt = deck.cardAt(temp);
        deck.remCard(cardDealt);
        return cardDealt;
    }

    /**
     * Deals the initial two cards to both the dealer and the player
     */
    public void dealFirst() {
        for (int i = 0; i < 2; i++) {
            dealer.addCard(drawCard());
            you.addCard(drawCard());
        }

        System.out.println("Cards have been dealt.");
        System.out.println("Dealer cards: [" + dealer.cardAt(0) + ", ??]");
        System.out.println("Your cards: " + you.getInv());
    }

    /**
     * Calculates the numeric value of a card based on its rank
     * 
     * @param card The card whose value needs to be calculated
     * @return The numeric value of the card
     */
    private int getCardValue(String card) {
        String numberPart = card.replaceAll("[^0-9]", "");
        if (numberPart.isEmpty()) {
            // Handle face cards (J, Q, K) as 10
            return 10;
        }
        return Integer.parseInt(numberPart);
    }

    /**
     * Calculates and displays the current card values for both the dealer and the player
     */
    public void calculateValue() {
        int dealerCardValue1 = getCardValue(dealer.cardAt(0));
        int dealerCardValue2 = getCardValue(dealer.cardAt(1));
        int yourCardValue1 = getCardValue(you.cardAt(0));
        int yourCardValue2 = getCardValue(you.cardAt(1));

        int dealerTotal = dealerCardValue1 + dealerCardValue2;
        int yourTotal = yourCardValue1 + yourCardValue2;

        System.out.println("Dealer's visible card value: " + dealerCardValue1);
        System.out.println("Your card value: " + yourTotal);

        if (dealerTotal == 21) {
            System.out.println("Dealer got 21! Blackjack!");
        }
        if (yourTotal == 21) {
            System.out.println("You got 21! Blackjack!");
        }
    }

    /**
     * Handles the player's turn, allowing them to hit or stay
     */
    public void playerTurn() {
        Scanner sc = new Scanner(System.in);
        while (calculateHandValue(you) < 21) {
            System.out.print("Your total: " + calculateHandValue(you) + ". Do you want to hit (y/n)? ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                hit(you);
                System.out.println("Your cards: " + you.getInv());
            } else {
                break;
            }
        }
    }

    /**
     * Calculates the total value of a hand for a given entity.
     * 
     * @param entity The entity whose hand value needs to be calculated
     * @return The total hand value.
     */
    public int calculateHandValue(Entity entity) {
        int total = 0;
        for (String card : entity.getInv()) {
            total += getCardValue(card);
        }
        return total;
    }

    /**
     * Handles the dealer's turn. The dealer hits until their total value reaches at least 17 (arbitrary value)
     */
    public void dealerTurn() {
        while (calculateHandValue(dealer) < 17) {
            hit(dealer);
            System.out.println("Dealer's cards: " + dealer.getInv());
        }
    }

    /**
     * Evaluates the winner of the game based on the hand values of the player and dealer
     */
    public void evalWinner() {
        int dealerTotal = calculateHandValue(dealer);
        int playerTotal = calculateHandValue(you);

        System.out.println("Dealer's total: " + dealerTotal);
        System.out.println("Your total: " + playerTotal);

        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
        } else if (dealerTotal > 21) {
            System.out.println("Dealer busted! You win.");
        } else if (playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (dealerTotal > playerTotal) {
            System.out.println("Dealer wins.");
        } else {
            System.out.println("It's a tie!");
        }
    }

    /**
     * Handles the action of drawing a card (hit) for a given entity
     * 
     * @param entityHit The entity (dealer or player) receiving the card
     */
    public void hit(Entity entityHit) {
        if (deck.sizeOf() > 0) {
            String cardDealt = drawCard();
            entityHit.addCard(cardDealt);
        } else {
            System.out.println("No cards left in the deck!");
        }
    }

    /**
     * Allows the player to withdraw their balance at the end of the game
     */
    public void withdraw() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you want to withdraw your balance? (yes/no): ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Withdrawing $" + bal);
        }
    }

    /**
     * Retrieves the player's current balance
     * 
     * @return The player's balance as a {@code double}
     */
    public double getBal() {
        return bal;
    }

    /**
     * Retrieves the deck entity
     * 
     * @return The entity representing the deck
     */
    public Entity getDeck() {
        return deck;
    }

    /**
     * Retrieves the dealer entity
     * 
     * @return The {@code Entity} representing the dealer
     */
    public Entity getDealer() {
        return dealer;
    }

    /**
     * Retrieves the player's entity
     * 
     * @return The entity representing the player
     */
    public Entity getYou() {
        return you;
    }
}
