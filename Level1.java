import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;


public class Level1 {

	CommandStream cs;
	String step;
	String dialogStep;
	JTextPane output;
	boolean advanceable = false;
	int stepCount;
	Directory location;
	
	Directory cat;
	Directory apep;
	Directory ra;
	Directory snake;
	
	File dotFeline;
	File apepp;
	File rra;
	File serpent;
	
	public Level1(String step, JTextPane output) {
		this.step = step;
		this.output = output;
		dialogStep = "1";
		stepCount = 0;
		output.setContentType("text/html");
		output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));
	}
	
	public boolean playLevel1(Command cmd) {
		String command = cmd.getCommand();
		if(stepCount == 34) {
			if(command.equals("cp")) {
				//check for copy .feline.txt and update the
				//reference to dotFeline
			}
			if(checkWinCons()) {
				System.out.println("You were able to successfully loot the treasure room of Heket! Let's hope that a terrible curse\ndoesn't follow...");
				return true;
				//System.exit(0);
			}
		}
		if(stepCount == 0) {
			output.setText("While doing research in the library, Dr. [you] finds a treasure map tucked away in the stacks.<br>It appears to lead to [insert cool location here] that contains relics that belong<br>in a museum.  Let's go on an adventure!<br>Enter a blank command (press [Enter] with nothing in the terminal) to advance the dialogue.");
			stepCount++;
		} else if(command.equals("pwd") && stepCount == 5) {
			output.setText("pwd works like a compass to help us get our bearings in the system.<br>Each slash represents a directory level. /library is to the right of /city, so that tells us the library is in the city.<br>[Enter]");
			stepCount++;
		} else if(command.equals("cd")) {
			if(stepCount == 8) {
				stepCount++;
				output.setText("The map says the [insert same cool location here] is in Giza.  That's<br>way too far for a penguin to fly, so let's go to the airport.<br>[Enter]");
			} else if(stepCount == 19) {
				output.setText("Thank goodness for time-saving montages. That felt way shorter than 15 hours.[Enter]");
				stepCount++;
			} else if(stepCount == 11) {
				output.setText("Before we take off, let's get our baggage to the baggage check.[Enter]");
				stepCount++;
			} else if(stepCount == 23) {
				if(location.name().equals("Alexandria")) {
					output.setText("Whoops. Took a wrong turn. In Linux, to get back to<br>your previous working directory, use the command cd -");
				} else if(location.name().equals("Giza")) {
					output.setText("Finally made it to Giza, now to find that [insert same cool location here] your research uncovered.<br>Try the ls command to pull up the map and see what's here.");
					stepCount++;
				} else if(location.name().equals("Egypt")) {
					output.setText("From the airport you can get to Giza or Alexandria. Type cd Giza to get to Giza.");
				}
			} else if(stepCount == 28 && location.name().equals(".PyramidOfHeket")) {
				output.setText("Looks like the pyramid is sealed shut, but that slab looks suspiciously like a door.<br>In Linux, in addition to moving files, the mv command can rename files.<br>The format for this is mv [filename] [new filename]. You can find the original file name<br>for the slab with ls.");
				stepCount++;
			} else if(stepCount == 33) {
				output.setText("Remember what you've learned.  Use this knowledge well, and the temple will yield its secrets!");
				stepCount++;
			}
		} else if(command.equals("ls")) {
			if(stepCount == 2) {
				output.setText("In Linux, the ls command lists files and other directories in your current working directory.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 10) {
				output.setText("You can move into listed sub-directories in the<br>city by using the cd command again.<br>By typing cd <directory name> you can move directly to that directory.<br>So to get to the airport, type cd Airport.");
				stepCount++;
			} else if(stepCount == 22) {
				output.setText("Huh. Looks like your luggage was lost. Guess it'll show up eventually.<br>From the airport you can get to Giza or Alexandria.");
				stepCount++;
			} else if(stepCount == 25) {
				output.setText("Hmm. Nothing new showed up, but looking at the map in the sun, it looks like there's some<br> kind of invisible ink...<br>[Enter]");
				stepCount++;
			} else if(stepCount == 27 && location.name().equals("Giza")) {
				output.setText("Most pyramids are burial chambers for pharaohs, but Heket was the Goddess of Frogs.<br>This definitely seems worth further exploration.");
				stepCount++;
			} else if(stepCount == 29) {
				output.setText("To turn the slab into a door, use the mv command: mv slab.txt door.txt");
				stepCount++;
			} else if(stepCount == 31) {
				output.setText("Use cd to move to a room.");
				stepCount++;
			}
		} else if(stepCount == 12) {
			output.setText("To move files, Linux uses the mv command.");
			stepCount++;
		} else if(command.equals("mv")) {
			if(stepCount == 15) {
				output.setText("Now we can head to Egypt using the cd command.");
				stepCount++;
			} else if(stepCount == 30) {
				output.setText("That did the trick! Now the map will show pyramid rooms.");
				stepCount++;
			}
		} else if(command.equals("clear") && stepCount == 17) {
			output.setText("Great! Now let's see if you can take the red-eye to Egypt.");
			stepCount++;
		} else if(command.equals("")) {
			if(stepCount == 1) {
				output.setText("Type ls to open the map.");
				stepCount++;
			} else if(stepCount == 3) {
				output.setText("So now we know everything that's in the library, but what about<br>what's outside the library?");
				stepCount++;
			} else if(stepCount == 4) {
				output.setText("To find out where we currently are, we can use the command pwd.");
				stepCount++;
			} else if(stepCount == 6) {
				output.setText("To move around in the terminal, use cd.");
				stepCount++;
			} else if(stepCount == 7) {
				output.setText("Type cd .. to move up to the parent directory.  In this case, the city is the parent directory for the<br>library, which is where we want to go!");
				stepCount++;
			} else if(stepCount == 9) {
				output.setText("Type ls to open the map.");
				stepCount++;
			} else if(stepCount == 13) {
				output.setText("The format for the mv command is: mv [file name] [directory path].  Type pwd to see your current<br>directory path.");
				stepCount++;
			} else if(stepCount == 14) {
				output.setText("So, to move our luggage to the baggage check type: mv Luggage.txt /root/Level1/City/Airport/BaggageCheck/Luggage.txt<br>(Hint: you can use ls at any time to see file and directory names at your location.");
				stepCount++;
			} else if(stepCount == 16) {
				output.setText("By now the terminal screen is probably full of text. Let's declutter. To clear the terminal at any time, type clear.");
				stepCount++;
			} else if(stepCount == 18) {
				output.setText("Looks like there's a direct flight, let's go!");
				stepCount++;
			} else if(stepCount == 20) {
				output.setText("Our research pointed us toward the <insert same cool location here> in Giza.");
				stepCount++;
			} else if(stepCount == 21) {
				output.setText("Finding Giza works the same way as flying to Egypt from the airport.<br>Type ls to pull up the map and see what places there are to go.");
				stepCount++;
			} else if(stepCount == 23) {
				if(location.equals("Egypt")) {
					output.setText("Type cd Giza to go to Giza.");
				} else if(location.equals("Alexandria")) {
					output.setText("Type cd - to return to your previous working directory.");
				}
			} else if(stepCount == 24) {
				output.setText("Finally made it to Giza. Now to find that [insert same cool location here].<br>Try the ls command to pull up the map and see what's here.");
				stepCount++;
			} else if(stepCount == 26) {
				output.setText("In Linux, .[name] files don't show up with a normal ls command. To see everything in a directory, type ls -a.");
				stepCount++;
			} else if(stepCount == 31) {
				output.setText("Type ls to open the map.");
			} else if(stepCount == 32) {
				output.setText("The GrandGallery and HypostyleHall are the subdirectories in HeketsPyramid.<br>They can be opened with the cd [directory name] command.<br>If you want to explore the GrandGallery, type cd GrandGallery.<br>If you want to explore the HypostyleHall, type cd HypostyleHall.");
			}
		}
		return false;
	}
	
	protected void setLocation(Directory d) {
		location = d;
	}
	
	protected void devMode(int n) {
		stepCount = n;
	}
	
	protected Directory buildLevel(Directory root) {
		Directory lv1 = new Directory("Level1", root, new ArrayList<Directory>(), new ArrayList<File>());
		Directory city = new Directory("City", lv1, new ArrayList<Directory>(), new ArrayList<File>());
		lv1.addDirectory(city);
		
		Directory library = new Directory("Library", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(library);
		library.addFile(new File("archaeology.txt", ""));
		library.addFile(new File("HowToLinux.txt", ""));
		library.addFile(new File("heket.txt", ""));
		library.addFile(new File("frogs.txt", ""));
		
		Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(home);
		
		Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		airport.addFile(new File("Luggage.txt", ""));
		city.addDirectory(airport);
		
		Directory baggage = new Directory("BaggageCheck", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory egypt = new Directory("Egypt", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory giza = new Directory("Giza", egypt, new ArrayList<Directory>(), new ArrayList<File>());
		Directory alexandria = new Directory("Alexandria", egypt, new ArrayList<Directory>(), new ArrayList<File>());
		Directory minnesota = new Directory("Minnesota", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory bathroom = new Directory("Bathrooms", airport, new ArrayList<Directory>(), new ArrayList<File>());
		
		airport.addDirectory(minnesota);
		airport.addDirectory(bathroom);
		airport.addDirectory(baggage);
		airport.addDirectory(egypt);
		egypt.addDirectory(giza);
		egypt.addDirectory(alexandria);
		
		Directory pyramid1 = new Directory("PyramidOofMenKaure", giza, new ArrayList<Directory>(), new ArrayList<File>());
		Directory pyramid2 = new Directory("PyramidOfKhafre", giza, new ArrayList<Directory>(), new ArrayList<File>());
		Directory pyramid3 = new Directory("PyramidOfKhufu", giza, new ArrayList<Directory>(), new ArrayList<File>());
		Directory pyramid4 = new Directory(".PyramidOfHeket", giza, new ArrayList<Directory>(), new ArrayList<File>());
		
		giza.addDirectory(pyramid1);
		giza.addDirectory(pyramid2);
		giza.addDirectory(pyramid3);
		giza.addDirectory(pyramid4);
		
		pyramid4.addFile(new File("slab.txt", ""));
		Directory gg = new Directory("GrandGallery", pyramid4, new ArrayList<Directory>(), new ArrayList<File>());
		Directory hh = new Directory("HypostyleHall", pyramid4, new ArrayList<Directory>(), new ArrayList<File>());
		pyramid4.addDirectory(gg);
		pyramid4.addDirectory(hh);
		
		dotFeline = new File(".feline.txt", "");
		serpent = new File("Serpent.txt", "");
		rra = new File("Ra.txt", "");
		apepp = new File("Apep.txt", "");
		
		gg.addFile(dotFeline);
		hh.addFile(serpent);
		hh.addFile(rra);
		hh.addFile(apepp);
		
		cat = new Directory("Cat", hh, new ArrayList<Directory>(), new ArrayList<File>());
		snake = new Directory("Snake", hh, new ArrayList<Directory>(), new ArrayList<File>());
		ra = new Directory("Ra", hh,  new ArrayList<Directory>(), new ArrayList<File>());
		apep = new Directory("Apep", hh, new ArrayList<Directory>(), new ArrayList<File>());
		
		hh.addDirectory(cat);
		hh.addDirectory(snake);
		hh.addDirectory(ra);
		hh.addDirectory(apep);
		
		
		
		return library;
	}
	
	private boolean checkWinCons() {
		//if(cat.contains(""))
		return false;
	}
}
