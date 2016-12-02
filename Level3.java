import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

public class Level3 {

  CommandStream cs;
  String dialogStep;
  JTextPane output;
  int stepCount;
  Directory location;

  public Level3(JTextPane output) {
		this.output = output;
		stepCount = 0;
		output.setContentType("text/html");
		output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));

  }

  public boolean playLevel3(Command cmd) {
    String command = cmd.getCommand();

    if (stepCount == 0) {
      output.setText("After extensive research on the Frog Amulet from Heket, Doctor Jones determined that Snake Island is the location of something pertaining to it. [Enter]");
      stepCount++;
    } else if (command.equals("")) {
      if (stepCount == 1 && location.name().equals("Airport")) {
        output.setText("I hope there aren’t any snakes on the plane. I would like to keep my snake encounters to a minimum. To Snake Island!");
        stepCount++;
      } else if (stepCount == 3 && location.name().equals("SnakeIsland")) {
        output.setText("")
      }

    } else if (command.equals("ls")) {

    } else if (command.equals("find")) {

    } else if (command.equals("cat")) {

    } else if (command.equals("cd")) {
      if (stepCount == 2 && location.name().equals("SnakeIsland")) {
        output.setText("I hope you have good reason for coming here, Doctor Jones. Not many are even allowed here on Snake Island. Before you go, I have words of warning for you, there are four trails here on the Island. I would recommend sticking to them unless you want an early death. [Enter]");
        stepCount++;
      }

    }

      return false;

  }

  protected void setLocation(Directory d) {
    location = d;
  }

  protected Directory buildLevel3(Directory root) {
    Directory lv3 = new Directory("Level3", root, new ArrayList<Directory>(), new ArrayList<File>());
    Directory city = new Directory("City", lv3, new ArrayList<Directory>(), new ArrayList<File>());
    lv3.addDirectory(city);

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


    File frog = new File("frog.txt", "August 14th, 1734 </br> Since my discovery of the fact that there is another hidden pyramid, I petitioned for an archeological dig in the area. The committee denied my request- the nerve! I have resorted to asking the locals about the Heqet’s Pyramid. Most had no clue what I was asking about, but a few retorted a legend of the pyramid. The legend goes that the great snake Apep, the god of chaos and darkness, bit Heqet and poisoned her. Heqet called on the aid of Serket, the goddess of scorpions and healing venomous bites. Serket removed snake’s venom from Heqet and stored it in one of Heqet’s amulets. The amulet was then given to the pharaoh for protection from Apep. In recognition of her gift, the pharaoh built a pyramid for Heqet and stored the amulet there for safety. All this talk of frogs, snakes, and scorpions reminds me of a Aesop tale! I wonder if there is some ancient connection between the fables and the Egyptian mythology. </br> April 7th, 1749 </br> From looking deeper into Aesop tale of the scorpion and the frog, I believe I have found the location to unearth another amulet.");
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

    File snake = new File("snakeIsland.txt", "Island in Brazil that is home to the World’s deadliest snakes. For a long time, the snakes did not mind anyone visiting the island. Around 1736, the Island was closed to the public due to a severe increase in casualties from snake venom. Scientists suspect that something is agitating the snakes, but with no sure way of preventing their venom, traveling there is out of the question.");
    travel.addFile(snake);

    Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
    city.addDirectory(airport);

    Directory baggage = new Directory("BaggageClaim", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory egypt = new Directory("Egypt", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory giza = new Directory("Giza", egypt, new ArrayList<Directory>(), new ArrayList<File>());
    Directory alexandria = new Directory("Alexandria", egypt, new ArrayList<Directory>(), new ArrayList<File>());
    Directory brazil = new Directory("Brazil", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory bathroom = new Directory("Bathrooms", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory snake = new Directory("SnakeIsland", brazil, new ArrayList<Directory>(), new ArrayList<File>());
  //  Directory china = new Directory("China", airport, new ArrayList<Directory>(), new ArrayList<File>());
  //  Directory russia = new Directory("Russia", airport, new ArrayList<Directory>(), new ArrayList<File>());

		airport.addDirectory(bathroom);
		airport.addDirectory(baggage);
		airport.addDirectory(egypt);
    airport.addDirectory(brazil);
    brazil.addDirectory(snake);
		egypt.addDirectory(giza);
		egypt.addDirectory(alexandria);

    return library;


  }


}
