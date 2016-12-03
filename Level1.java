import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;


public class Level1 extends Level {

	CommandStream cs;
	String dialogStep;
	JTextPane output;
	int stepCount;
	Directory location;

	Directory baggage;
	Directory cat;
	Directory apep;
	Directory ra;
	Directory snake;
	Directory pyramid4;
	Directory gg;
	Directory hh;
	Directory treasure;

	File lugg;
	File slab;
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

	public boolean playLevel(Command cmd) {
		String command = cmd.getCommand();
		if(stepCount == 26 && (location.name().equals("GrandGallery") || location.name().equals("HypostyleHall"))) {
			if(checkWinCons()) {
				output.setText("Once all of the statues have been inserted into the wall, an ancient mechanism deeper in the pyramid spurs to life. A great door opens in the Hypostyle Hall.");
				hh.addDirectory(treasure);
				stepCount++;
			}
		}
		if(stepCount == 0) {
			output.setText("While researching frogs in the Library, Dr. [player name] comes across directions to a previously undiscovered pyramid. The directions make mention of a previously undiscovered treasure that definitely belongs in a museum. Excited by this new information, Dr. [player name] is ready to set off on an adventure! [Enter]");
			stepCount++;
		} else if(command.equals("pwd")) {
			 if (stepCount == 3 && location.name().equals("Library")) {
					output.setText("<b>pwd</b> works like a compass to help us get our bearings in the system. Each slash represents a directory, since the /library directory is to the right of the /city directory it means that the library is contained within the city. This is the basis for the path system. [Enter]");
					stepCount++;
			}
		} else if(command.equals("cd")) {
			if(stepCount == 5 && location.getParent().name().equals("Library")) {
				output.setText("Now that you have yourself in another directory, an easy way to navigate to the parent directory is to use <b>cd ..</b> Use it twice now to get into the library's parent directory, the city.");
				stepCount++;
			} else if (stepCount == 6 && location.name().equals("City")) {
				output.setText("The map says that the undiscovered pyramid is in Giza. That's way too far for a penguin to fly! I'll have to get to the <b>Airport</b>! Use <b>ls</b> and <b>cd</b> to get there.");
				stepCount++;
			} else if (stepCount == 7 && location.name().equals("Airport")) {
				output.setText("Welcome to the airport! The baggage check is here, please move your check bag before boarding your plane. [Enter]");
				stepCount++;
			} else if (stepCount == 11 && location.name().equals("Egypt")) {
				output.setText("Thank goodness for time-saving montages, that felt way shorter than 15 hours. [Enter]");
				stepCount++;
			} else if (stepCount == 17 && location.name().equals(".PyramidOfHeket")) {
				output.setText("Looks like the pyramid is sealed shut, but that slab looks suspiciously like a door. [Enter]");
				stepCount++;
			} else if (stepCount == 13) {
				if (location.name().equals("Giza")) {
					output.setText("Finally made it to Giza, now to find that pyramid your research uncovered. I should look around for it.");
					stepCount++;
				} else {
					output.setText("Whoops! Looks like I took a wrong turn! In linux, you can quickly navigate back to your previous working directory by using the <b>cd</b> command <b>cd -</b>. You'll head back to Egypt then <b>cd</b> to Giza.");
				}
			} else if (stepCount == 21) {
				if (location.name().equals("GrandGallery")) {
					output.setText("To move a file: <b>mv [filename] [destination directory]</b> | To rename a file or directory: <b>mv [old name] [new name]</b> | Go to a specific directory: <b>cd [directory name]</b> | Go to the parent directory: <b>cd ..</b> | Go to the previous working directory: <b>cd -</b> | View directory contents: <b>ls</b> | View all directory contents: <b>ls -a</b>");
					stepCount++;
				} else if (location.name().equals("HypostyleHall")) {
					output.setText("To move a file: <b>mv [filename] [destination directory]</b> | To rename a file or directory: <b>mv [old name] [new name]</b> | Go to a specific directory: <b>cd [directory name]</b> | Go to the parent directory: <b>cd ..</b> | Go to the previous working directory: <b>cd -</b> | View directory contents: <b>ls</b> | View all directory contents: <b>ls -a</b>");
				}
			} else if (stepCount == 25 && location.name().equals("HypostyleHall")) {
				output.setText("To move a file: <b>mv [filename] [destination directory]</b> | To rename a file or directory: <b>mv [old name] [new name]</b> | Go to a specific directory: <b>cd [directory name]</b> | Go to the parent directory: <b>cd ..</b> | Go to the previous working directory: <b>cd -</b> | View directory contents: <b>ls</b> | View all directory contents: <b>ls -a</b>");
				stepCount++;
			} else if (stepCount == 29 && location.name().equals("Library")) {
				output.setText("Congratulations! You have completed Level 1. Hit [Enter] to start Level 2.");
				stepCount++;
			} else if (stepCount == 27 && location.name().equals("TreasureRoom")) {
				output.setText("Inside the Treasure Room, there are many artifacts from ancient Egypt. One amulet, however stands out from the rest. The amulet is in the shape of a frog, a traditional symbol of Heket. There is more to the amulet than what it appears; perhaps there is something in the library about it. [Enter]");
				stepCount++;
			}
		} else if(command.equals("ls")) {
			if(stepCount == 2 && location.name().equals("Library")) {
				output.setText("In Linux, the <b>ls</b> command lists files and directories in the current working directory. A directory is a folder that can hold files and/or other directories. Try the <b>pwd</b> command now.");
				stepCount++;
			} else if (stepCount == 14 && location.name().equals("Giza")) {
				output.setText("Hmm. Nothing new showed up, but looking at the map in the sun, it looks like there's some sort of invisible ink... [Enter]");
				stepCount++;
			} else if (stepCount == 16 && location.name().equals("Giza")) {
				output.setText("Most pyramids are burial chambers for pharaohs, but Heket was the Goddess of Frogs. This definitely seems worth further exploration");
				stepCount++;
			} else if (stepCount == 20 && location.name().equals(".PyramidOfHeket")) {
				output.setText("Remember that you are able to traverse a directory system using the <b>cd</b> command. Try navigating to the GrandGallery or the HypostyleHall now.");
				stepCount++;
			} else if (stepCount == 22 && location.name().equals("GrandGallery") && cmd.getFlags().size() > 0) {
				output.setText("There was a statue hidden in this room! But it looks stuck to the floor. I'll have to make a cast of it so I can copy it. [Enter]");
				stepCount++;
			}
		} else if(command.equals("mv")) {
			if (stepCount == 9 && location.name().equals("Airport") && baggage.getFiles().contains(lugg)) {
				output.setText("Woah there! You shouldn't go through security with your terminal all cluttered! Use the <b>clear</b> command to clean that right up!");
				stepCount++;
			} else if (stepCount == 19 && location.name().equals(".PyramidOfHeket") && slab.getName().equals("door.txt")) {
				output.setText("That did the trick! Now let's check the map to see the pyramid's rooms.");
				pyramid4.addDirectory(gg);
				pyramid4.addDirectory(hh);
				stepCount++;
			}

		} else if(command.equals("clear")) {
			if (stepCount == 10 && location.name().equals("Airport")) {
				output.setText("Giza is located in Egypt, I should get on the direct flight there. (Hint: you'll want to use <b>cd</b> and maybe <b>ls</b>)");
				stepCount++;
			}

		} else if(command.equals("")) {
			if(stepCount == 1 && location.name().equals("Library")) {
				output.setText("The command line (located below) takes in commands and runs them. One of the most useful commands is the <b>ls</b> command. Use <b>ls</b> now to open map");
				stepCount++;
			} else if(stepCount == 4 && location.name().equals("Library")) {
				output.setText("Another useful command is <b>cd</b>. Using it, the user can change their working directory. The syntax, or structure, of <b>cd</b> is <b>cd [directoryName]</b>. Try navigating into one of the directories located in the library. NOTE: The <b>cd</b> command does not inherently know where every directory is in the computer. When inputting the directory name, the user might have to include that directory's path.");
				stepCount++;
			} else if (stepCount == 8 && location.name().equals("Airport")) {
				output.setText("The <b>mv</b> command moves items in Linux directories. <b>mv</b>'s format is: \'<b>mv [file name] [destination directory]</b>\'. Move your luggage to the BaggageClaim.");
				stepCount++;
			} else if (stepCount == 12 && location.name().equals("Egypt")) {
				output.setText("Now to Giza!");
				stepCount++;
			} else if (stepCount == 15 && location.name().equals("Giza")) {
				output.setText("In Linux, files that begin with a dot <b>.</b> do not show up with a normal <b>ls</b> command. Most commands in linux allow arguments to add extra functionalities to the base command. Arguments are added after the base command. An example is <b>ls</b>, adding <b>-a</b> after ls will cause the command to print <b>.</b> files in addition to normal ones. Go, ahead try using <b>ls -a</b>.");
				stepCount++;
			} else if (stepCount == 18 && location.name().equals(".PyramidOfHeket")) {
				output.setText("In Linux, in addition to moving files, the <b>mv</b> command can rename files. The format for this is <b>mv [original filename] [new filename]</b>. Try turning the slab into a door.");
				stepCount++;
			} else if (stepCount == 23 && location.name().equals("GrandGallery")) {
				output.setText("In Linux, you can make a copy of a file or directory with the <b>cp</b> command. The format is <b>cp [source file] [file path for directory you want it copied to]</b>. Try copying the feline statue now to the HypostyleHall. (Hint: you can use the <b>..</b> argument from <b>cd</b> in file paths as well, for example <b>cp foo.txt ../bar</b> This will copy <b>foo.txt</b> to the bar directory contained in <b>foo.txt</b>'s parent directory.)");
				stepCount++;
			} else if (stepCount == 28 && location.name().equals("TreasureRoom")) {
				output.setText("To quickly return to the home directory in Linux, you can <b>cd /</b>. Use it now to end this adventure.");
				stepCount++;
			} else if (stepCount == 30 && location.name().equals("Library")) {
				return true;
			}
		} else if (command.equals("cp")) {
			if (stepCount == 24 && location.name().equals("GrandGallery")) {
				output.setText("We should check HypostyleHall now.");
				stepCount++;
			}
		}
		return false;
	}

	protected void setLocation(Directory d) {
		location = d;
	}

	protected void devMode(int n) {
		this.stepCount = n;
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

		Directory scienceFiction = new Directory("ScienceFiction", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory religion = new Directory("Religion", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory fiction = new Directory("Fiction", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory nonFiction = new Directory("NonFiction", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory entertainment = new Directory("Entertainment", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory academia = new Directory("Academia", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory childrens = new Directory("Childrens", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory art = new Directory("Art", library, new ArrayList<Directory>(), new ArrayList<File>());
    Directory science = new Directory("Science", library, new ArrayList<Directory>(), new ArrayList<File>());

    Directory health = new Directory("Health", science, new ArrayList<Directory>(), new ArrayList<File>());
    Directory math = new Directory("Math", science, new ArrayList<Directory>(), new ArrayList<File>());

    Directory anthology = new Directory("Anthology", art, new ArrayList<Directory>(), new ArrayList<File>());
    Directory poetry = new Directory("Poetry", art, new ArrayList<Directory>(), new ArrayList<File>());

    Directory comics = new Directory("Comics", entertainment, new ArrayList<Directory>(), new ArrayList<File>());
    Directory drama = new Directory("Drama", entertainment, new ArrayList<Directory>(), new ArrayList<File>());
    Directory cookbook = new Directory("Cookbooks", entertainment, new ArrayList<Directory>(), new ArrayList<File>());
    Directory satire = new Directory("Satire", entertainment, new ArrayList<Directory>(), new ArrayList<File>());
    Directory romance = new Directory("Romance", entertainment, new ArrayList<Directory>(), new ArrayList<File>());

    Directory mystery = new Directory("Mystery", fiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory horror = new Directory("Horror", fiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory actionAdventure = new Directory("ActionAdventure", fiction, new ArrayList<Directory>(), new ArrayList<File>());

    Directory journals = new Directory("Journals", academia, new ArrayList<Directory>(), new ArrayList<File>());
    Directory encyclopedia = new Directory("Encyclopedia", academia, new ArrayList<Directory>(), new ArrayList<File>());
    Directory dictionaries = new Directory("Dictionaries", academia, new ArrayList<Directory>(), new ArrayList<File>());
    Directory diaries = new Directory("Diaries", academia, new ArrayList<Directory>(), new ArrayList<File>());
    Directory guide = new Directory("Guide", academia, new ArrayList<Directory>(), new ArrayList<File>());

    Directory biography = new Directory("Biography", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());
    //Directory selfHelp = new Directory("SelfHelp", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory travel = new Directory("Travel", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory history = new Directory("History", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory selfHelp = new Directory("SelfHelp", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());

    Directory fantasy = new Directory("Fantasy", scienceFiction, new ArrayList<Directory>(), new ArrayList<File>());

    library.addDirectory(scienceFiction);
    library.addDirectory(fiction);
    library.addDirectory(nonFiction);
    library.addDirectory(entertainment);
    library.addDirectory(science);
    library.addDirectory(religion);
    library.addDirectory(art);
    library.addDirectory(academia);
    library.addDirectory(childrens);

    scienceFiction.addDirectory(fantasy);

    fiction.addDirectory(mystery);
    fiction.addDirectory(horror);
    fiction.addDirectory(actionAdventure);

    nonFiction.addDirectory(biography);
    nonFiction.addDirectory(travel);
    nonFiction.addDirectory(history);
    //nonFiction.addDirectory(selfHelp);

    entertainment.addDirectory(romance);
    entertainment.addDirectory(comics);
    entertainment.addDirectory(satire);
    entertainment.addDirectory(drama);
    entertainment.addDirectory(cookbook);

    science.addDirectory(health);
    science.addDirectory(math);

    art.addDirectory(anthology);
    art.addDirectory(poetry);

    academia.addDirectory(guide);
    academia.addDirectory(journals);
    academia.addDirectory(encyclopedia);
    academia.addDirectory(dictionaries);
    academia.addDirectory(diaries);


		Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(home);

		Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		lugg = new File("luggage.txt", "Clothes. Toothbrush. Hair brush.");
		airport.addFile(lugg);
		city.addDirectory(airport);

		baggage = new Directory("BaggageClaim", airport, new ArrayList<Directory>(), new ArrayList<File>());
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

		slab = new File("slab.txt", "DO YOU WISH THE ENTAH?");
		pyramid4.addFile(slab);

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

		treasure = new Directory("TreasureRoom", hh, new ArrayList<Directory>(), new ArrayList<File>());

		treasure.addFile(new File("amulet.txt", "Frog frog frog"));


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
	
	public int getStep() {
		return stepCount;
	}
	
}
