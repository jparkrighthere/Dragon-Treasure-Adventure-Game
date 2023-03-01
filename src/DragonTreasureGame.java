import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Class that sets up the Dragon Treasure Adventure Game and handles control
 * flow and game logic.
 * 
 * @author Jeonghyeon Park
 */
public class DragonTreasureGame extends PApplet {

	/** The room list. */
	private static ArrayList<Room> roomList; // list of rooms in the cave
	
	/** The room info. */
	private File roomInfo;
	
	/** The map info. */
	private File mapInfo;
	
	/** The characters. */
	private ArrayList<Character> characters;

	/** The is dragon turn. */
	private boolean isDragonTurn = false;
	
	/** The game state. */
	private int gameState = 0;

	/**
	 * Loads in room info using the file stored in roomInfo.
	 *
	 */
	private void loadRoomInfo() {
		System.out.println("Loading rooms...");
		Scanner fileReader = null;
		try {

			// scanner to read from file
			fileReader = new Scanner(roomInfo);

			// read line by line until none left
			while (fileReader.hasNext()) {
				String nextLine = fileReader.nextLine();

				// parse info and create new room
				String[] parts = nextLine.split(" \\| ");
				int ID = Integer.parseInt(parts[1].trim()); // get the room id
				String imageName = null;
				String description = null;
				PImage image = null;
				Room newRoom = null;

				if (parts.length >= 3) {
					imageName = parts[2].trim();
					image = this.loadImage("images" + File.separator + imageName);

				}

				if (parts.length == 4) {
					description = parts[3].trim(); // get the room description
				}

				switch (parts[0].trim()) {
				case "S":
					newRoom = new StartRoom(ID, image);
					break;
				case "R":
					newRoom = new Room(ID, description, image);
					break;
				case "P":
					newRoom = new PortalRoom(ID, description, image);
					break;
				case "T":
					newRoom = new TreasureRoom(ID);
					break;
				default:
					break;
				}

				if (newRoom != null) {
					roomList.add(newRoom);
				}
			}
		} catch (IOException e) { // handle checked exception
			e.printStackTrace();
		} finally {
			if (fileReader != null)
				fileReader.close(); // close scanner regardless of what happened for security reasons :)
		}
	}

	/**
	 * Loads in room connections using the file stored in mapInfo.
	 *
	 */
	private void loadMap() {
		System.out.println("Loading map...");
		Scanner fileReader = null;
		try {
			// scanner to read from file
			fileReader = new Scanner(mapInfo);

			// read line by line until none left
			while (fileReader.hasNext()) {

				// parse info
				String nextLine = fileReader.nextLine();
				String parts[] = nextLine.split(" ");
				int id = Integer.parseInt(parts[0]);

				Room toEdit = getRoomByID(id); // get the room we need to update info for adjacent rooms

				// add all the rooms to the adj room list of toEdit
				for (int i = 1; i < parts.length; i++) {
					Room toAdjAdd = getRoomByID(Integer.parseInt(parts[i]));
					toEdit.addToAdjacentRooms(toAdjAdd);
				}
			}
		} catch (IOException e) { // handle checked exception
			e.printStackTrace();
		} finally { // close scanner regardless of what happened for security reasons :)
			if (fileReader != null)
				fileReader.close();
		}
	}

	/**
	 * Get the room objected associated with the given ID.
	 *
	 * @param id the ID of the room to retrieve
	 * @return the Room that corresponds to that id
	 */
	private Room getRoomByID(int id) {
		int indexToEdit = roomList.indexOf(new Room(id, "dummy", null));
		Room toEdit = roomList.get(indexToEdit);
		return toEdit;
	}

	/**
	 * Settings to set size to 800 x 60.
	 */
	@Override
	public void settings() {
		size(800, 600);
	}

	/**
	 * Setup the game state.
	 */
	@Override
	public void setup() {

		this.getSurface().setTitle("Dragon Treasure Adventure"); // sets the title of the window
		this.imageMode(PApplet.CORNER); // Images are drawn using the x,y-coordinate
		// as the top-left corner
		this.rectMode(PApplet.CORNERS); // When drawing rectangles interprets args
		// as top-left corner and bottom-right corner respectively
		this.focused = true; // window will be active upon running program
		this.textAlign(CENTER); // sets the text alignment to center
		this.textSize(20); // sets the font size for the text

		roomList = new ArrayList<>();
		Room.setProcessing(this);
		TreasureRoom.setTreasureBackground(this.loadImage("images/treasure.jpg"));
		PortalRoom.setPortalImage(this.loadImage("images/portal.png"));

		roomInfo = new File("roominfo.txt");
		mapInfo = new File("map.txt");

		loadRoomInfo();
		loadMap();

		characters = new ArrayList<>();
		loadCharacters();

	}

	/**
	 * Load characters into the list (keyholder, dragon and player).
	 */
	private void loadCharacters() {
		System.out.println("Adding characters...");
		characters.add(new Character(getRoomByID(5), "KEYHOLDER"));
		characters.add(new Player(getRoomByID(1)));
		characters.add(new Dragon(getRoomByID(9)));
	}

	/**
	 * Draw the current state if the game.
	 */
	@Override
	public void draw() {

		if (gameState != 0)
			return;

		Player player = getPlayer();
		Dragon dragon = getDragon();
		Character keyholder = getKeyHolder();

		player.getCurrentRoom().draw();

		if (isDragonTurn) {
			while (!dragon.changeRoom(dragon.pickRoom()))
				;
			isDragonTurn = false;
		}

		if (player.isPortalNearby())
			System.out.println(PortalRoom.getPortalWarning());
		if (player.isTreasureNearby())
			System.out.println(TreasureRoom.getTreasureWarning());

		if (player.isDragonNearby(dragon))
			System.out.println(Dragon.getDragonWarning());

		if (player.teleport())
			System.out.println(PortalRoom.getTeleportMessage());

		if (player.getCurrentRoom().equals(keyholder.getCurrentRoom()) && !player.hasKey()) {
			player.obtainKey();
			System.out.println("KEY OBTAINED");
		}

		if (player.getCurrentRoom().equals(dragon.getCurrentRoom())) {
			System.out.println(Dragon.getDragonEncounter());
			gameState = 2;
		} else {
			TreasureRoom room = getTreasureRoom();
			if (room.playerCanGrabTreasure(player)) {
				System.out.println("[YOU WIN] You found and unlocked the treasure!");
				gameState = 1;
			}

		}

	}

	/**
	 * Try to Change room on key press.
	 */
	@Override
	public void keyPressed() {

		if (gameState == 0 && !isDragonTurn) {
			int id = key - '0';
			Room room = getRoomByID(id);
			if (room != null) {
				for (Character character : characters) {
					if (character instanceof Player) {
						boolean movedTo = ((Player) character).changeRoom(room);
						if (movedTo)
							isDragonTurn = true;
						else
							System.out.println("NOT A VALID MOVE!");
						break;
					}
				}
			}
		}

	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	private Player getPlayer() {
		for (Character character : characters) {
			if (character instanceof Player) {
				return (Player) character;
			}
		}

		return null;
	}

	/**
	 * Gets the dragon.
	 *
	 * @return the dragon
	 */
	private Dragon getDragon() {
		for (Character character : characters) {
			if (character instanceof Dragon) {
				return (Dragon) character;
			}
		}

		return null;
	}

	/**
	 * Gets the key holder.
	 *
	 * @return the key holder
	 */
	private Character getKeyHolder() {
		for (Character character : characters) {
			if (character.getLabel().equals("KEYHOLDER")) {
				return character;
			}
		}

		return null;
	}

	/**
	 * Gets the treasure room.
	 *
	 * @return the treasure room
	 */
	private TreasureRoom getTreasureRoom() {
		for (Room room : roomList) {
			if (room instanceof TreasureRoom)
				return (TreasureRoom) room;
		}

		return null;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		PApplet.main("DragonTreasureGame");

	}

}
