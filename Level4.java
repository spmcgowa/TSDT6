import java.util.ArrayList;

import javax.swing.*;


public class Level4 {

	JTextPane output;
	int stepCount;
	Directory location;
	Directory red;
	String place;
	boolean caught;
	
	public Level4(JTextPane output) {
		this.output = output;
		stepCount = 0;
		caught = false;
	}
	
	public boolean playLevel4(Command cmd) {
		String command = cmd.getCommand();
		place = location.name();
		/* Checking for empty command to advance dialogue */
		if(command.equals("")) {
			if(stepCount == 0 && place.equals("Library")) {
				output.setText("Doctor [PLAYERNAME], having recovered both the frog amulet and the snake amulet, has been "
						+ "in the library conducting research into them.  After many hours of searching, Doctor [PLAYERNAME]"
						+ " stumbles upon a document that might be the answer.");
				stepCount++;
			} else if(stepCount == 1 && place.equals("Library")) {
				output.setText("Doctor [PLAYERNAME]: \"Ah ha! Finally! After all this time, I found <i>something</i> pertaining to both amulets. "
						+ "Strange that it was located in this book <b>russianRoyalty.txt</b>.\"");
					stepCount++;
			} else if(stepCount == 2 && place.equals("Library")) {
				output.setText("Doctor [PLAYERNAME]: \"Before I head out, I should lock these amulets up.\"");
				stepCount++;
			} else if(stepCount == 3 && place.equals("Library")) {
				output.setText("After you create a directory, you can alter its permissions so that specific people can do specific commands to it. "
						+ "The Change Mode, <b>chmod</b>, command is used to change permissions. <b>chmod o-rwx [fileName]</b> will prevent <b>[fileName]</b> from being able to be <b>r</b>ead, <b>w</b>ritten, and e<b>x</b>ecuted by <b>o</b>thers. Make a directory and use the <b>chmod</b> command to remove (<b>-</b>) <b>o</b>ther permissions <b>rwx</b>.");
				stepCount++;
			} else if(stepCount == 5 && place.equals("Library")) {
				output.setText("The format for <b>chmod</b> is: \'<b>chmod [ugoa...][+-=][perms...]\'</b> where <b>u</b> stands for <b>u</b>ser, <b>g</b> stands for members of the <b>g</b>roup, and <b>o</b> stands for <b>o</b>thers. Using \'<b>=</b>\' sets the person's permissions exactly to what you specify. In the last command you used, <b>r</b> stood for <b>r</b>eading, <b>w</b> stood for <b>w</b>riting, and <b>x</b> stood for e<b>x</b>ecuting.");
				stepCount++;
			} else if(stepCount == 6 && place.equals("Library")) {
				output.setText("Doctor [PLAYERNAME]: \"I better get to the airport before I miss my flight to Russia! I'll bring my <b>permissions.txt</b> so that I have something handy in case I forget <b>chmod</b>!\"");
				stepCount++;
			} else if(stepCount == 8 && place.equals("Russia")) {
				output.setText("Doctor [PLAYERNAME]: \"Uh-oh, I forgot about the security. There's no way they'll simply let me in!\"");
				stepCount++;
			} else if(stepCount == 9 && place.equals("Russia")) {
				output.setText("Calvin Johnson: \"No permission, no access! That's the law! Now scram!\"");
				stepCount++;
			} else if(stepCount == 10 && place.equals("Russia")) {
				output.setText("Doctor [PLAYERNAME]: \"No permission, huh? I know a way to change that!\"");
				stepCount++;
			} else if(stepCount == 11 && place.equals("Russia")) {
				output.setText("To edit permissions for everyone, simply use <b>a</b> for <b>a</b>nybody.  Since you only want to add permissions to the door file, you simply need to use <b>+</b> instead of <b>=</b> in your <b>chmod</b> command. Try adding <b>r</b>eading, <b>w</b>riting, and e<b>x</b>ecuting to the cave directory now.");
				stepCount++;
			} else if(stepCount == 13 && place.equals("Russia")) {
				output.setText("Calvin Johnson: \"Huh, I honestly didn't expect to be letting anyone inside here. Enjoy your time, Doctor.\"");
				stepCount++;
			} else if(stepCount == 15 && place.equals("Cave")) {
				output.setText("Doctor [PLAYERNAME]: \"I did hear about that. I was just on my way to secure another section of the base.\"");
				stepCount++;
			} else if(stepCount == 16 && place.equals("Cave")) {
				output.setText("Romeo: \"Hmm. Very well, go ahead and swipe your ID on the pad there next to the door. But, be mindful, I'm on the lookout for anyone granting access to <b>a</b>nybody.\"");
				stepCount++;
			} else if(stepCount == 17 && place.equals("Cave")) {
				output.setText("To not raise the alarm with the guard, give only <b>o</b>thers the permission to <b>r</b>ead and <b>w</b>rite.");
				stepCount++;
			} else if(stepCount == 404) {
				output.setText("Doctor [PLAYERNAME]: \"Whew, that was the worst year of my life. Now where was I? Ah yes, the amulet. Back to the cave.\"");
				setLocation(red);
				stepCount = 7;
			} else if(stepCount == 7 && place.equals("Russia") && caught) {
				output.setText("Calvin Johnson: \"Halt! Only those authorized may enter here. Show me your identification!\"");
				caught = false;
				stepCount++;
			}
		/* Checking for chmod command */
		} else if(command.equals("chmod")) {
			if(stepCount == 4 && place.equals("Library")) {
				output.setText("Doctor [PLAYERNAME]: \"There, now I am able to access the amulets and no one else is.\"");
				stepCount++;
			} else if(stepCount == 12 && place.equals("Russia") && cmd.getInputs().get(1).equals("Cave")) {
				output.setText("Doctor [PLAYERNAME]: \"Here is my information, I'm sure that you'll find it satisfactory.\"");
				stepCount++;
			} else if(stepCount == 18 && place.equals("Cave") && !cmd.getInputs().get(0).contains("a")) {
				output.setText("");
				stepCount++;
			} else if(stepCount == 18 && place.equals("Cave") && cmd.getInputs().get(0).contains("a")) {
				output.setText("Romeo: \"Aha, it was you! Off to the salt mines with you!");
				caught = true;
				stepCount = 404;
			}
		/* Checking for cd command */
		} else if(command.equals("cd")) {
			if(stepCount == 7 && place.equals("Russia")) {
				output.setText("Calvin Johnson: \"Halt! Only those authorized may enter here. Show me your identification!\"");
				stepCount++;
			} else if(stepCount == 14 && place.equals("Cave")) {
				output.setText("Romeo: \"Whoooo are you? There's talk that our sentry outside let someone in without proper clearance. Was it you?\"");
				stepCount++;
			}
		}
		
		return checkWinCons();
	}
	
	protected boolean checkWinCons() {
		
		return false;
	}
	
	public Directory buildLevel(Directory root) {
		Directory lv4 = new Directory("Level4", root, new ArrayList<Directory>(), new ArrayList<File>());
		root.addDirectory(lv4);
		
		Directory city = new Directory("City", lv4, new ArrayList<Directory>(), new ArrayList<File>());
		lv4.addDirectory(city);
		
		Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		Directory library = new Directory("Library", city, new ArrayList<Directory>(), new ArrayList<File>());
			library.addFile(new File("frogAmulet.txt", ""));
			library.addFile(new File("snakeAmulet.txt", ""));
			library.addFile(new File("russianRoyalty.txt", "One of the earliest Czars in Prussia was said to be blessed with supernatural powers. "
					+ "Many common folk claim that the source of her power came from a fox amulet she carried with her all the time. "
					+ "Stories spoke of the mystical abilities it gave: Appearing in multiple places, Foresight, and Long Life. "
					+ "Historians have since tried to experiment on this amulet, but the Russian government will not allow anyone near it."));
		
		Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		
		city.addDirectory(airport);
		city.addDirectory(library);
		city.addDirectory(home);
		
		red = new Directory("Russia", airport, new ArrayList<Directory>(), new ArrayList<File>());
		airport.addDirectory(red);
		
		Directory abyss = new Directory("TheAbyss", red, new ArrayList<Directory>(), new ArrayList<File>());
		Directory cave = new Directory("Cave", red, new ArrayList<Directory>(), new ArrayList<File>());
		red.addDirectory(abyss);
		red.addDirectory(cave);
		
		Directory base = new Directory("RussianBase", cave, new ArrayList<Directory>(), new ArrayList<File>());
		cave.addDirectory(base);
		
		Directory chest = new Directory("Chest", base, new ArrayList<Directory>(), new ArrayList<File>());
		Directory room = new Directory("Room", base, new ArrayList<Directory>(), new ArrayList<File>());
		Directory box = new Directory("Box", base, new ArrayList<Directory>(), new ArrayList<File>());
		Directory desk = new Directory("Desk", base, new ArrayList<Directory>(), new ArrayList<File>());
		Directory cellar = new Directory("Cellar", base, new ArrayList<Directory>(), new ArrayList<File>());
		base.addDirectory(chest);
		base.addDirectory(room);
		base.addDirectory(box);
		base.addDirectory(desk);
		base.addDirectory(cellar);
		
		Directory box2 = new Directory("Box", room, new ArrayList<Directory>(), new ArrayList<File>());
		Directory shelf = new Directory("BookShelf", room, new ArrayList<Directory>(), new ArrayList<File>());
		Directory cabinet = new Directory("Cabinet", room, new ArrayList<Directory>(), new ArrayList<File>());
		room.addDirectory(box2);
		room.addDirectory(shelf);
		room.addDirectory(cabinet);
		
		Directory td = new Directory("TopDrawer", desk, new ArrayList<Directory>(), new ArrayList<File>());
		Directory bd = new Directory("BottomDrawer", desk, new ArrayList<Directory>(), new ArrayList<File>());
		desk.addDirectory(td);
		desk.addDirectory(bd);
		
		Directory box3 = new Directory("Box", cellar, new ArrayList<Directory>(), new ArrayList<File>());
		Directory rack = new Directory("Rack", cellar, new ArrayList<Directory>(), new ArrayList<File>());
		Directory closet = new Directory("Closet", cellar, new ArrayList<Directory>(), new ArrayList<File>());
		Directory vodka = new Directory("Vodka", cellar, new ArrayList<Directory>(), new ArrayList<File>());
		cellar.addDirectory(box3);
		cellar.addDirectory(rack);
		cellar.addDirectory(closet);
		cellar.addDirectory(vodka);
		
		Directory cont = new Directory("Container", closet, new ArrayList<Directory>(), new ArrayList<File>());
		closet.addDirectory(cont);
		
		box.addFile(new File("foxAmulet.txt", ""));
		
		setLocation(library);
		
		return library;
	}
	
	public void setLocation(Directory d) {
		this.location = d;
	}
	
}
