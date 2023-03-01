import java.util.Random;

/**
 * The Class Dragon represents a dragon in the game.
 * 
 * @author Jeonghyeon Park
 */
public class Dragon extends Character implements Moveable{
    
    /** The rand gen. */
    private Random randGen; //random num generator used for moving
    
    /** The Constant DRAGON_WARNING. */
    private static final String DRAGON_WARNING = "You hear a fire breathing nearby!\n";
    
    /** The Constant DRAGON_ENCOUNTER. */
    private static final String DRAGON_ENCOUNTER = "Oh no! You ran into the fire breathing dragon!\n";

    
    /**
     * Constructor for a Dragon object. Initializes all instance fields. The label should be "DRAGON" by default.
     *
     * @param currentRoom the current room
     * @throws IllegalArgumentException if currentRoom is not a TreasureRoom
     */
    public Dragon(Room currentRoom) {
        super(currentRoom, "DRAGON");
        
        if(!(currentRoom instanceof TreasureRoom)) {
            throw new IllegalArgumentException("Dragon should be placed in a treasure room");
        }
        
        randGen = new Random();
        
    }
    
    /**
     * Moves the Dragon to the destination room.
     *
     * @param destination the Room to change it to
     * @return true if the change was successful, false otherwise
     */
    public boolean changeRoom(Room destination) {
        if(canMoveTo(destination)) {
            setCurrentRoom(destination);
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if the dragon can move to the given destination. A valid move is the destination not a PortalRoom.
     *
     * @param destination the destination
     * @return true, if they can, false otherwise
     */
    public boolean canMoveTo(Room destination) {
        return !(destination instanceof PortalRoom) && getAdjacentRooms().contains(destination);
    }
    
    /**
     * Picks randomly ONCE an adjacent room to move into.
     *
     * @return the room that this Dragon should try to move into
     */
    public Room pickRoom() {
        return getAdjacentRooms().get(randGen.nextInt(getAdjacentRooms().size()));
    }
    
    /**
     * Gets the dragon warning.
     *
     * @return the dragon warning
     */
    public static String getDragonWarning() {
        return DRAGON_WARNING;
    }
    
    /**
     * Gets the dragon encounter.
     *
     * @return the dragon encounter
     */
    public static String getDragonEncounter() {
        return DRAGON_ENCOUNTER;
    }
}
