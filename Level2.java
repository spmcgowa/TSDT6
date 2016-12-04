import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

public class Level2 extends Level {

  CommandStream cs;
  String dialogStep;
  JTextPane output;
  int stepCount;
  Directory location;



  public Level2(JTextPane output) {
		this.output = output;
		stepCount = 0;
		output.setContentType("text/html");
		output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));
	}

  public boolean playLevel(Command cmd) {
    String command = cmd.getCommand();

    if (stepCount == 0) {
      output.setText("Happy Feet: Welcome back Doctor Jones! Congratulations discovering Heqet's Pyramid and recovering the Frog Amulet! This will be a fine addition to the museum! [Enter]");
      stepCount++;
    } else if (command.equals("")) {
      if (location.name().equals("Library") && stepCount == 1) {
        output.setText("Dr. Jones: It will be; but, there is more to this relic than that. There's something that seems... off about it. I will have to do some research into it. But first, I need to find the proper book. Something pertaining to this Frog Amulet.");
        stepCount++;
      } else if (stepCount == 12 && location.name().equals("Airport")) {
        return true;
      }
    } else if (command.equals("ls")) {
      if (stepCount == 2 && location.name().equals("Library")) {
        output.setText("Yikes. I don't think you want to check every directory. You can use the <b>find</b> command to show every directory and subdirectory in the current working directory.");
        stepCount++;
      }
    } else if (command.equals("find")) {
      if (stepCount == 3 && location.name().equals("Library")) {
        output.setText("To limit your search, you can add a specific directory or path to the <b>find</b> command! Try <b>find [directory/path]</b> to search one directory and its subdirectories. ");
        stepCount++;
      } else if (stepCount == 4 && location.name().equals("Library")) {
        output.setText("It's possible to search for a specific file by adding the <b>-name</b> argument to the command. <b>find [directory/path] -name [filename]</b>");
        stepCount++;
      } else if (stepCount == 5 && location.name().equals("Library") && cmd.getFlags().size() > 0) {
        output.setText("Yes there's more! You can generalize your search by replacing a part of the filename with <b>*</b> to search a wild card. <b>find [directory/path] -name *.[format/extension]</b> OR <b>find [directory/path] -name [filename].*</b>");
        stepCount++;
      } else if (stepCount == 6 && location.name().equals("Library") && cmd.getFlags().size() > 0) {
        output.setText("Great! Now that we know the path of the journal we are looking for, let's head to that directory.");
        stepCount++;
      }
    } else if (command.equals("cat")) {
      if (stepCount == 8 && location.name().equals("Journals")) {
        output.setText("This archeologist thought that the Aesop Fable of the scorpion and the frog had something to do with this amulet. I should look around for that tale and read it.");
        stepCount++;
      } else if (stepCount == 9 && location.name().equals("Fantasy")) {
        output.setText("What's with the random capital letters? They spell out something? S-N-A-K-E-I-S-L-A-N-D. Snake Island? I should look that up.");
        stepCount++;
      } else if (stepCount == 10 && location.name().equals("Travel")) {
        output.setText("Hmm, I think we have enough information to head to our next adventure. We can head to the airport; as I suspected, there is more to the artifact than I originally thought. I'm off to Brazil to visit an island dubbed: ‘Snake Island'.");
        stepCount++;
      }
    } else if (command.equals("cd")) {
      if (stepCount == 7 && location.name().equals("Journals")) {
        output.setText("Next, we want to read it. How you ask? Use the <b>cat [filename]</b> command to read a file on the command line.");
        stepCount++;
      } else if (stepCount == 11 && location.name().equals("Airport")) {
        output.setText("Congratulations! You finished Level 2. Hit [Enter] to head to Level 3.");
        stepCount++;
      }
    }

      return false;

  }

  protected void setLocation(Directory d) {
    location = d;
  }

  protected Directory buildLevel(Directory root) {
    Directory lv2 = new Directory("Level2", root, new ArrayList<Directory>(), new ArrayList<File>());
		Directory city = new Directory("City", lv2, new ArrayList<Directory>(), new ArrayList<File>());
		lv2.addDirectory(city);

		Directory library = new Directory("Library", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(library);

    Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(home);

		Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		File lugg = new File("luggage.txt", "Clothes. Toothbrush. Hair brush.");
		airport.addFile(lugg);
		city.addDirectory(airport);

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


    File frog = new File("frog.txt", "August 14th, 1734 </br> Since my discovery of the fact that there is another hidden pyramid, I petitioned for an archeological dig in the area. The committee denied my request- the nerve! I have resorted to asking the locals about the Heqet's Pyramid. Most had no clue what I was asking about, but a few retorted a legend of the pyramid. The legend goes that the great snake Apep, the god of chaos and darkness, bit Heqet and poisoned her. Heqet called on the aid of Serket, the goddess of scorpions and healing venomous bites. Serket removed snake's venom from Heqet and stored it in one of Heqet's amulets. The amulet was then given to the pharaoh for protection from Apep. In recognition of her gift, the pharaoh built a pyramid for Heqet and stored the amulet there for safety. All this talk of frogs, snakes, and scorpions reminds me of a Aesop tale! I wonder if there is some ancient connection between the fables and the Egyptian mythology. </br> April 7th, 1749 </br> From looking deeper into Aesop tale of the scorpion and the frog, I believe I have found the location to unearth another amulet.");
    //File frog = new File("FrogAmulet.txt", "");
    journals.addFile(frog);

    File aesop1 = new File("aesopAndrocles.txt", "");
    File aesop2 = new File("aesopEagleArrow.txt", "");
    File aesop3 = new File("aesopKidWolf.txt", "");
    File aesop = new File("aesopScorpionFrog.txt", "a ScorpioN and A frog meet on the banK of a strEam and the scorpion asks the frog to carry him across on its back. the frog asks, 'how do I know you won't sting me?' the scorpion says, 'because if I do, I will die too.' </br> the frog is satisfied, and they set out, but in midstream, the scorpion stIngs the frog. the frog feels the onSet of paraLysis and stArts to sink, kNowing they both will Drown, but has just enough time to gasp 'why?'</br>replies the scorpion: 'its my nature...'");
    File aesop4 = new File("aesopYoungTheifMother.txt", "");

    fantasy.addFile(aesop1);
    fantasy.addFile(aesop2);
    fantasy.addFile(aesop3);
    fantasy.addFile(aesop4);
    fantasy.addFile(aesop);

    File snake = new File("snakeIsland.txt", "Island in Brazil that is home to the World's deadliest snakes. For a long time, the snakes did not mind anyone visiting the island. Around 1736, the Island was closed to the public due to a severe increase in casualties from snake venom. Scientists suspect that something is agitating the snakes, but with no sure way of preventing their venom, traveling there is out of the question.");
    travel.addFile(snake);

    return library;


  }

  public int getStep() {
	  return stepCount;
  }


  public void devMode(int n) {
	  stepCount = n;
  }

}
