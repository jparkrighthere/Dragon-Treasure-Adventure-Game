import java.util.ArrayList;

/**
 * The Class Character represents a generic character in the game.
 * @author Jeonghyeon Park
 */
public class Character {

    /** The current room. */
    private Room currentRoom; //current room the character is in
    
    /** The label. */
    private String label; //a label giving a basic description of the character
    
    /**
     * Instantiates a new character.
     *
     * @param currentRoom the current room
     * @param label the label
     * @throws IllegalArgumentException if currentRoom is null.
     */
    public Character(Room currentRoom,
             String label) {
        if(currentRoom == null)
            throw new IllegalArgumentException("The current room cannot be null");
        
        this.currentRoom = currentRoom;
        this.label = label;
    }
    
    /**
     * Gets the current room.
     *
     * @return the current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    
    /**
     * Gets the label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Gets the adjacent rooms.
     *
     * @return the adjacent rooms
     */
    public ArrayList<Room> getAdjacentRooms() {
        return currentRoom.getAdjacentRooms();
    }
    
    /**
     * Sets the current room.
     *
     * @param newRoom the new current room
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }
}
