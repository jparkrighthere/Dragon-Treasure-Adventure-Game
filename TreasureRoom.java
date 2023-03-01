import processing.core.PImage;

/**
 * The Class TreasureRoom represents a reasure room
 * 
 * @author Jeonghyeon Park
 */
public class TreasureRoom extends Room{

    /** The Constant TREASURE_WARNING. */
    private static final String TREASURE_WARNING = "You sense that there is treasure nearby.\n";
    
    /** The treasure background. */
    private static PImage treasureBackground; //the image ALWAYS used for treasure rooms
    
    /**
     * Instantiates a new treasure room.
     *
     * @param ID the id
     */
    public TreasureRoom(int ID) {
        super(ID, "In the back of this room, you spot a treasure chest. It is locked...", treasureBackground);
    }
    
    /**
     * Gets the treasure warning.
     *
     * @return the treasure warning
     */
    public static String getTreasureWarning() {
        return TREASURE_WARNING;
    }
    
    
    /**
     * Determines whether or not the player can open the treasure chest in the room.
     *
     * @param p  the Player to check if they can open the chest
     * @return true if the player has the key and is in this TreasureRoom, false otherwise
     */
    public boolean playerCanGrabTreasure(Player p) {
        return p.hasKey() && p.getCurrentRoom().equals(this);
    }
    
    /**
     * Sets the treasure background.
     *
     * @param treasureBackground the new treasure background
     */
    public static void setTreasureBackground(PImage treasureBackground) {
        TreasureRoom.treasureBackground = treasureBackground;
    }
}

