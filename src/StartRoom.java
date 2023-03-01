import processing.core.PImage;

/**
 * The Class StartRoom represents a start room
 * 
 * @author Jeonghyeon Park
 */
public class StartRoom extends Room{

    /**
     * Instantiates a new start room.
     *
     * @param ID the id
     * @param image the image
     */
    public StartRoom(int ID, PImage image){
        super(ID, "You find yourself in the entrance to a cave holding treasure.", image);
    }
}
