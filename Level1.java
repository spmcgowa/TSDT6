import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;


public class Level1 {

	CommandStream cs;
	String dialogStep;
	JTextPane output;
	int stepCount;
	Directory location;
	
	Directory cat;
	Directory apep;
	Directory ra;
	Directory snake;
	Directory pyramid4;
	Directory gg;
	Directory hh;
	
	File dotFeline;
	File apepp;
	File rra;
	File serpent;
	
	public Level1(JTextPane output) {
		this.output = output;
		stepCount = 0;
		output.setContentType("text/html");
		output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));
	}
	
	public boolean playLevel1(Command cmd) {
		String command = cmd.getCommand();
		if(stepCount == 34 && (location.name().equals("GrandGallery") || location.name().equals("HypostyleHall"))) {
			if(checkWinCons()) {
				output.setText("After moving the statues to their appropriate locations, a small door opened in the wall.<br>It contained a priceless Frog Amulet, made of pure gold and inlaid with gemstones.<br>Let's take this back to the library for some more research!");
				return true;
			}
		}
		if(stepCount == 0) {
			output.setText("While doing research in the library, Dr. [you] finds a treasure map tucked away in the stacks.<br>It appears to lead to a previously undiscovered pyramid in Giza that contains relics that belong<br>in a museum.  Let's go on an adventure!<br>[Enter]");
			stepCount++;
		} else if(command.equals("pwd") && stepCount == 5 && location.name().equals("Library")) {
			output.setText("pwd works like a compass to help us get our bearings in the system.<br>Each slash represents a directory level. /library is to the right of /city, so that tells us the library is in the city.<br>[Enter]");
			stepCount++;
		} else if(command.equals("cd")) {
			if(stepCount == 8 && location.name().equals("City")) {
				stepCount++;
				output.setText("The map says the new pyramid is in Giza.  That's<br>way too far for a penguin to fly, so let's go to the airport.<br>[Enter]");
			} else if(stepCount == 19 && location.name().equals("Egypt")) {
				output.setText("Thank goodness for time-saving montages. That felt way shorter than 15 hours.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 11 && location.name().equals("Airport")) {
				output.setText("Before we take off, let's get our baggage to the baggage check.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 23) {
				if(location.name().equals("Alexandria")) {
					output.setText("Whoops. Took a wrong turn. In Linux, to get back to<br>your previous working directory, use the command cd -");
				} else if(location.name().equals("Giza")) {
					output.setText("Finally made it to Giza, now to find that pyramid your research uncovered.<br>Try the ls command to pull up the map and see what's here.");
					stepCount++;
				} else if(location.name().equals("Egypt")) {
					output.setText("From the airport you can get to Giza or Alexandria. Type cd Giza to get to Giza.");
				}
			} else if(stepCount == 33 && (location.equals("HypostyleHall") || location.equals("GrandGallery"))) {
				output.setText("To move a file: mv [filename] [destination directory]<br>To rename a file or directory: mv [old name] [optional file path][new name]<br>"
						+ "Go to a specific directory: cd [directory name]<br>Go to the parent directory: cd ..<br>Go to the previous directory: cd -<br>View directory contents: ls<br>View all directory contents: ls -a");
			} else if(stepCount == 31 && location.name().equals(".PyramidOfHeket")) {
				output.setText("We're in! It looks nobody has set foot in here in centuries. Let's see what's here. Why don't you open your map again?");
				stepCount++;
			}
		} else if(command.equals("ls")) {
			if(stepCount == 2 && location.name().equals("Library")) {
				output.setText("In Linux, the ls command lists files and other directories in your current working directory.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 10 && location.name().equals("City")) {
				output.setText("You can move into listed sub-directories in the<br>city by using the cd command again.<br>By typing cd <directory name> you can move directly to that directory.<br>So to get to the airport, type cd Airport.");
				stepCount++;
			} else if(stepCount == 22 && location.name().equals("Egypt")) {
				output.setText("Huh. Looks like your luggage was lost. Guess it'll show up eventually.<br>From the airport you can get to Giza or Alexandria.");
				stepCount++;
			} else if(stepCount == 24 && location.name().equals("Giza")) {
				output.setText("Hmm. Nothing new showed up, but looking at the map in the sun, it looks like there's some<br> kind of invisible ink...<br>[Enter]");
				stepCount++;
			} else if(stepCount == 26 && location.name().equals("Giza")) {
				output.setText("Most pyramids are burial chambers for pharaohs, but Heket was the Goddess of Frogs.<br>This definitely seems worth further exploration.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 32 && location.name().equals(".PyramidOfHeket")) {
				output.setText("The GrandGallery and HypostyleHall are the subdirectories in .PyramidOfHeket.<br>They can be opened with the cd [directory name] command.<br>If you want to explore the GrandGallery, type cd GrandGallery.<br>If you want to explore the HypostyleHall, type cd HypostyleHall.");
				stepCount++;
			} else if(stepCount == 33 && location.name().equals("GrandGallery") && cmd.getFlags().get(0).equals("0")) {
				
			}
		} else if(stepCount == 12 && location.name().equals("Airport")) {
			output.setText("To move files, Linux uses the mv command.<br>[Enter]");
			stepCount++;
		} else if(command.equals("mv")) {
			if(stepCount == 15 && location.name().equals("Airport")) {
				output.setText("Now we can head to Egypt using the cd command.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 29 && location.name().equals("Giza")) {
				output.setText("That did the trick! Now we can gain access to the pyramid rooms.<br>[Enter]");
				pyramid4.addDirectory(gg);
				pyramid4.addDirectory(hh);
				stepCount++;
			}
		} else if(command.equals("clear") && stepCount == 17 && location.name().equals("Airport")) {
			output.setText("Great! Now let's see if you can take the red-eye to Egypt.<br>[Enter]");
			stepCount++;
		} else if(command.equals("")) {
			if(stepCount == 1 && location.name().equals("Library")) {
				output.setText("Type ls to open the map.");
				stepCount++;
			} else if(stepCount == 3 && location.name().equals("Library")) {
				output.setText("So now we know everything that's in the library, but what about<br>what's outside the library?<br>[Enter]");
				stepCount++;
			} else if(stepCount == 4 && location.name().equals("Library")) {
				output.setText("To find out where we currently are, we can use the command pwd.");
				stepCount++;
			} else if(stepCount == 6 && location.name().equals("Library")) {
				output.setText("To move around in the terminal, use cd.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 7 && location.name().equals("Library")) {
				output.setText("Type cd .. to move up to the parent directory.  In this case, the city is the parent directory for the<br>library, which is where we want to go!");
				stepCount++;
			} else if(stepCount == 9 && location.name().equals("City")) {
				output.setText("Type ls to open the map.");
				stepCount++;
			} else if(stepCount == 13 && location.name().equals("Airport")) {
				output.setText("The format for the mv command is: mv [file name] [directory path].  You can type pwd to see your current directory path<br>if you forget.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 14 && location.name().equals("Airport")) {
				output.setText("So, to move our luggage to the baggage check type: mv Luggage.txt BaggageCheck<br>(Hint: you can use ls at any time to see file and directory names at your location.");
				stepCount++;
			} else if(stepCount == 16 && location.name().equals("Airport")) {
				output.setText("By now the terminal screen is probably full of text. Let's declutter. To clear the terminal at any time, type clear.");
				stepCount++;
			} else if(stepCount == 18 && location.name().equals("Airport")) {
				output.setText("Looks like there's a direct flight, let's go! Use the cd command to fly to Egypt.");
				stepCount++;
			} else if(stepCount == 20 && location.name().equals("Egypt")) {
				output.setText("Our research pointed us toward the hidden pyramid in Giza.<br>[Enter]");
				stepCount++;
			} else if(stepCount == 21 && location.name().equals("Egypt")) {
				output.setText("Finding Giza works the same way as flying to Egypt from the airport.<br>Type ls to pull up the map and see what places there are to go.");
				stepCount++;
			} else if(stepCount == 23) {
				if(location.equals("Egypt")) {
					output.setText("Type cd Giza to go to Giza.");
				} else if(location.equals("Alexandria")) {
					output.setText("Type cd - to return to your previous working directory.");
				}
			} else if(stepCount == 25 && location.name().equals("Giza")) {
				output.setText("In Linux, .[name] files don't show up with a normal ls command. To see everything in a directory, type ls -a.");
				stepCount++;
			} else if(stepCount == 30 && location.name().equals("Giza")) {
				output.setText("Let's step inside. Use the cd command to enter Heket's pyramid.");
				stepCount++;
			} else if(stepCount == 27 && location.name().equals("Giza")) {
				output.setText("Looks like the pyramid is sealed shut, but that slab looks suspiciously like a door.<br>In Linux, in addition to moving files, the mv command can rename files. The format for this is mv [filename] [new filename].<br>[Enter]");
				stepCount++;
			} else if(stepCount == 28 && location.name().equals("Giza")) {
				output.setText("To turn the slab into a door, use the mv command: mv .slab.txt door.txt");
				stepCount++;
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
		pyramid4 = new Directory(".PyramidOfHeket", giza, new ArrayList<Directory>(), new ArrayList<File>());
		
		giza.addDirectory(pyramid1);
		giza.addDirectory(pyramid2);
		giza.addDirectory(pyramid3);
		giza.addDirectory(pyramid4);
		
		giza.addFile(new File(".slab.txt", ""));
		
		gg = new Directory("GrandGallery", pyramid4, new ArrayList<Directory>(), new ArrayList<File>());
		hh = new Directory("HypostyleHall", pyramid4, new ArrayList<Directory>(), new ArrayList<File>());
		
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
		if(snake.getFiles().contains(serpent) && apep.getFiles().contains(apepp) && ra.getFiles().contains(rra)) {
			for(File f : cat.getFiles()) {
				if(f.getName().toLowerCase().equals("cat.txt") || f.getName().toLowerCase().equals("cat")) {
					return true;
				}
			}
		}
		return false;
	}
}
