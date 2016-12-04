import java.util.ArrayList;

import javax.swing.*;

public class Level5 extends Level {

  JTextPane output;
	int stepCount;
	Directory location;
	Directory red;
	String place;

  Directory library;

  File journey;

  public Level5(JTextPane output) {
		this.output = output;
		stepCount = 0;

	}

  public boolean playLevel(Command cmd) {
		String command = cmd.getCommand();
		place = location.name();

    if (stepCount == 0 && place.equals("Library")) {
      output.setText("This is pointless! I can’t find anything that ties all of these amulets together completely! There’s always just mentions about here or there. They just seem to turn up randomly at different times and locations! [Enter]");
      stepCount++;
    }
		/* Checking for empty command to advance dialogue */
		 else if(command.equals("")) {
      if (stepCount == 1 && place.equals("Library")) {
        output.setText("At that moment, a hole in space-time opens and a note falls out of it before the hole disappears. [Enter]");
        stepCount++;
      } else if (stepCount == 2 && place.equals("Library")) {
        output.setText("What’s this? journey.txt?");
        library.addFile(journey);
        stepCount++;
      } else if (stepCount == 4 && place.equals("Library")) {
        output.setText("In linux, you can easily group and compress files using tape archives, or <b>tar</b>. To manipulate a tar file, simply use the tar command: <b>tar [operation] [options]</b>! The option to create a tar file is <b>-c</b>. Try <b>tar -c pouch.tar frogAmulet snakeAmulet foxAmulet</b> now.");
        stepCount++;
      } else if (stepCount == 7 && place.equals("China")) {
        output.setText("Doctor Jones: Titan, what can you tell me of <b>journey</b>? [Enter]");
        stepCount++;
      } else if (stepCount == 8 && place.equals("China")) {
        output.setText("Titan: You don’t mean… … I’m sorry, my friend, for whatever gave you that text. The original scroll is cursed. Those who gaze upon it and follow its words are lost forever. I do not advise you to go seeking it. [Enter]");
        stepCount++;
      } else if (stepCount == 9 && place.equals("China")) {
        output.setText("Doctor Jones: Titan, please, I must know. It may be the only answer to these amulets! These amulets contain mystical abilities. Those who wear them are gods amongst men! We have to make sure that we know everything about them. [Enter]");
        stepCount++;
      } else if (stepCount == 10 && place.equals("China")) {
        output.setText("Titan: Alright, fine. The original scroll is located in the RoyalLibrary here. You’ll have to find some way to get past the guards. Once inside, seek the end of the journey; that will should have to information you seek. I’ll provide you all that I can, but be careful, my friend. I don’t want to lose you; you have given too much to this world.");
        stepCount++;
      } else if (stepCount == 13 && place.equals("RoyalLibrary")) {

      }

		/* Checking for chmod command */
    } else if(command.equals("cat")) {
      if (stepCount == 3 && place.equals("Library")) {
        output.setText("My friend in China should know something about this. I should take all of the amulets with me this time. Good thing I have this pouch to hold them easily. [Enter]");
        stepCount++;
      }
		/* Checking for cd command */
		} else if(command.equals("cd")) {
      if (stepCount == 6 && place.equals("China")) {
        output.setText("Titan: Doctor Jones! My friend, it’s good to see you here! What brings you to Beijing? [Enter]");
        stepCount++;
      } else if (stepCount == 11 && place.equals("RoyalLibrary")) {
        output.setText("Let's look around.");
        stepCount++;
      }

		} else if (command.equals("tar")) {
      if (stepCount == 5 && place.equals("Library")) {
        output.setText("Let's head to China!");
        stepCount++;
      }
    } else if (command.equals("ls")) {
      if (stepCount == 12 && place.equals("RoyalLibrary")) {
        output.setText("There have to be a hundred parts to this scroll, and they are all over the place! I’ll have to look through the whole thing to find the end. [Enter]");
        stepCount++;
      }
    }

    return false;

	}

  protected Directory buildLevel(Directory root) {
    Directory lv5 = new Directory("Level5", root, new ArrayList<Directory>(), new ArrayList<File>());
		Directory city = new Directory("City", lv5, new ArrayList<Directory>(), new ArrayList<File>());
		lv5.addDirectory(city);

		library = new Directory("Library", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(library);

    Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(home);

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

    Directory travel = new Directory("Travel", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory history = new Directory("History", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());
    Directory selfHelp = new Directory("SelfHelp", nonFiction, new ArrayList<Directory>(), new ArrayList<File>());

    Directory fantasy = new Directory("Fantasy", scienceFiction, new ArrayList<Directory>(), new ArrayList<File>());

    journey = new File("journey.txt", "places the frogAmulet… ….foxAmulet… ...Once all three are in their proper place… ");

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

    Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(airport);

    Directory baggage = new Directory("BaggageClaim", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory egypt = new Directory("Egypt", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory giza = new Directory("Giza", egypt, new ArrayList<Directory>(), new ArrayList<File>());
    Directory alexandria = new Directory("Alexandria", egypt, new ArrayList<Directory>(), new ArrayList<File>());
    Directory brazil = new Directory("Brazil", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory bathroom = new Directory("Bathrooms", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory island = new Directory("SnakeIsland", brazil, new ArrayList<Directory>(), new ArrayList<File>());
    Directory china = new Directory("China", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory russia = new Directory("Russia", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory royal = new Directory("RoyalLibrary", china, new ArrayList<Directory>(), new ArrayList<File>());
    Directory shang = new Directory("ShangHai", china, new ArrayList<Directory>(), new ArrayList<File>());
    Directory bamboo = new Directory("BambooForest", china, new ArrayList<Directory>(), new ArrayList<File>());
    Directory rice = new Directory("RiceFields", china, new ArrayList<Directory>(), new ArrayList<File>());
    Directory wall = new Directory("GreatWall", china, new ArrayList<Directory>(), new ArrayList<File>());
    Directory post1 = new Directory("GuradPostOne", wall, new ArrayList<Directory>(), new ArrayList<File>());
    Directory post2 = new Directory("GuardPostTwo", wall, new ArrayList<Directory>(), new ArrayList<File>());
    Directory post3 = new Directory("GuardPostThree", wall, new ArrayList<Directory>(), new ArrayList<File>());
    Directory gift = new Directory("GiftShop", wall, new ArrayList<Directory>(), new ArrayList<File>());
    Directory altar = new Directory(".AltarRoom", wall, new ArrayList<Directory>(), new ArrayList<File>());

    airport.addDirectory(bathroom);
		airport.addDirectory(baggage);
		airport.addDirectory(egypt);
    airport.addDirectory(brazil);
    airport.addDirectory(china);
    airport.addDirectory(russia);
    brazil.addDirectory(island);
		egypt.addDirectory(giza);
		egypt.addDirectory(alexandria);
    china.addDirectory(shang);
    china.addDirectory(royal);
    china.addDirectory(bamboo);
    china.addDirectory(rice);
    china.addDirectory(wall);
    wall.addDirectory(post1);
    wall.addDirectory(post2);
    wall.addDirectory(post3);
    wall.addDirectory(gift);
    wall.addDirectory(altar);

    return library;
  }
}
