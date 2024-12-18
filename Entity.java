import java.util.ArrayList;

/**
 * The class represents an entity in the game
 * It maintains an inventory of cards and provides methods for managing the cards
 */
public class Entity {
    private ArrayList<String> inv; // Inventory of cards

    /**
     * Constructs an empty {@code Entity} with an initialized inventory of cards
     */
    public Entity() {
        inv = new ArrayList<String>();
    }

    /**
     * Retrieves the inventory of cards for this entity
     * 
     * @return An String representing the inventory of cards
     */
    public ArrayList<String> getInv() {
        return inv;
    }
    
    /**
     * Retrieves the card at a specific index in the inventory
     * 
     * @param index The index of the card to retrieve
     * @return The card as a String at the specified index
     */
    public String cardAt(int index) {
        return inv.get(index);
    }

    /**
     * Adds a card to the inventory
     * 
     * @param cardAdded The card to be added, represented as a String
     */
    public void addCard(String cardAdded) {
        inv.add(cardAdded);
    }
    
    /**
     * Removes a card from the inventory
     * 
     * @param cardRem The card to be removed, represented as a String
     */
    public void remCard(String cardRem) {
        inv.remove(cardRem);
    }
    
    /**
     * Retrieves the size of the inventory
     * 
     * @return The size of the inventory as an Int
     */
    public int sizeOf() {
        return inv.size();
    }

    /**
     * Returns a string representation of the inventory
     * 
     * @return A String containing all cards in the inventory in a human-readable format
     */
    @Override
    public String toString() {
        return inv.toString(); // Convert the list of cards to a string
    }
}
