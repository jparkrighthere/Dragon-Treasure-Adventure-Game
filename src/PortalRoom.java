import java.util.Random;

import processing.core.PImage;

/**
 * The Class PortalRoom represents a portal room.
 * 
 * @author Jeonghyeon Park
 */
public class PortalRoom extends Room {

	/** The rand gen. */
	private Random randGen; // random number generator for location picking

	/** The Constant PORTAL_WARNING. */
	private static final String PORTAL_WARNING = "You feel a distortion in space nearby.\n";

	/** The Constant TELEPORT_MESSAGE. */
	private static final String TELEPORT_MESSAGE = "The space distortion teleported you to another room!\n";

	/** The portal image. */
	private static PImage portalImage; // image of a portal to be shown in all portal rooms

	/**
	 * Constructor for a PortalRoom object. Initializes all instance data fields.
	 *
	 * @param ID          the id
	 * @param description the description
	 * @param image       the image
	 */
	public PortalRoom(int ID, String description, PImage image) {
		super(ID, description, image);
		randGen = new Random();
	}

	/**
	 * Gets the portal warning.
	 *
	 * @return the portal warning
	 */
	public static String getPortalWarning() {
		return PORTAL_WARNING;
	}

	/**
	 * Gets the teleport message.
	 *
	 * @return the teleport message
	 */
	public static String getTeleportMessage() {
		return TELEPORT_MESSAGE;
	}

	/**
	 * Gets the teleport location.
	 *
	 * @return the teleport location
	 */
	public Room getTeleportLocation() {
		return getAdjacentRooms().get(randGen.nextInt(getAdjacentRooms().size()));
	}

	/**
	 * Sets the portal image.
	 *
	 * @param portalImage the new portal image
	 */
	public static void setPortalImage(PImage portalImage) {
		PortalRoom.portalImage = portalImage;
	}

	/**
	 * Draws this PortalRoom to the window by drawing the background image, a
	 * rectangle, some text, and the portal image.
	 */
	@Override
	public void draw() {
		super.draw();
		processing.image(portalImage, 325, 225);
	}

}
