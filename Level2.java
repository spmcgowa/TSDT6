import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

public class Level2 {

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

  public boolean playLevel2(Command cmd) {
    String command = cmd.getCommand();

    if (stepCount == 0) {
      output.setText("Happy Feet: “Welcome back Doctor [PLAYERNAME]! Congratulations discovering Heqet’s Pyramid and recovering the Frog Amulet! This will be a fine addition to the museum!” [Enter]");
      stepCount++;
    } else if (command.equals("")) {
      if (location.name().equals("Library") && stepCount == 1) {
        output.setText("Dr. [you]: “It will be; but, there is more to this relic than that. There’s something that seems… off about it. I will have to do some research into it. But first, I need to find the proper book. Something pertaining to this Frog Amulet.”");
        stepCount++;
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
        output.setText("It’s possible to search for a specific file by adding the <b>-name</b> argument to the command. <b>find [directory/path] -name [filename]</b>");
        stepCount++;
      } else if (stepCount == 5 && location.name().equals("Library")) {
        output.setText("Yes there's more! You can generalize your search by replacing a part of the filename with <b>*</b> to search a wild card. <b>find [directory/path] -name *.[format/extension]</b> OR <b>find [directory/path] -name [filename].*</b>");
        stepCount++;
      } else if (stepCount == 6 && location.name().equals("Library")) {
        output.setText("Great! Now that we know the path of the journal we are looking for, let's head to that directory.");
        stepCount++;
      }
    } else if (command.equals("cd")) {
      if (stepCount == 7 && location.name().equals("Journals")) {
        output.setText("Next, we want to read it. How you ask? Use the <b>cat [filename]</b> command to read a file on the command line.");
        stepCount++;
      }
    } else if (command.equals("cat")) {
      if (stepCount == 8 && location.name().equals("Journals")) {
        output.setText("You can use <b>nano [filename]</b> command to create your own text file! Try it now and create a file that you can use for reference. <b>nano [filename]</b>");
        stepCount++;
      }
    }

      return false;

  }

  protected void setLocation(Directory d) {
    location = d;
  }

  protected Directory buildLevel2(Directory root) {
    Directory lv2 = new Directory("Level2", root, new ArrayList<Directory>(), new ArrayList<File>());
		Directory city = new Directory("City", lv2, new ArrayList<Directory>(), new ArrayList<File>());
		lv2.addDirectory(city);

		Directory library = new Directory("Library", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(library);

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


    File frog = new File("frog.txt", "August 14th, 1734 <br> My expedition was a success! When I first arrived at the pyramids, something felt off. I decided to do some research into the construction of the pyramids. From my search, I discovered that there was, in fact, another pyramid that was lost in the sands of time. That pyramid was constructed for the fertility god Heqet. Digging deeper into the mythos of Heqet, I found that frogs were used to represent her. I will request an archeological dig in the area so that we may find this pyramid.");
    //File frog = new File("FrogAmulet.txt", "");
    journals.addFile(frog);

    return library;


  }


}
