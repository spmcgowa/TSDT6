import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

public class Level3 extends Level {

  CommandStream cs;
  String dialogStep;
  JTextPane output;
  int stepCount;
  Directory location;

  Directory box;
  Directory island;
  Directory hole;
  Directory tree;
  Directory hill;
  Directory compartment;
  Directory cave;
  Directory path1;
  Directory chest;

  File riddle;
  File riddle2;
  File riddle3;
  File door;
  File path4Ans;
  File goat;
  File monkey;
  File sheep;
  File hippo;
  File serpent;
  File stone;

  public Level3(JTextPane output) {
		this.output = output;
		stepCount = 0;
		output.setContentType("text/html");
		output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));

  }

  public boolean playLevel(Command cmd) {
    String command = cmd.getCommand();

    if (stepCount == 0) {
      output.setText("After extensive research on the Frog Amulet from Heket, Doctor Jones determined that Snake Island is the location of something pertaining to it. [Enter]");
      stepCount++;
    } else if (command.equals("")) {
      if (stepCount == 1 && location.name().equals("Airport")) {
        output.setText("I hope there aren’t any snakes on the plane. I would like to keep my snake encounters to a minimum. To Snake Island!");
        stepCount++;
      } else if (stepCount == 3 && location.name().equals("SnakeIsland")) {
        output.setText("Before long, Doctor Jones stumbles upon a sign with a riddle on it.");
        stepCount++;
      } else if (stepCount == 10 && location.name().equals(".FloorBoard")) {
        output.setText("You are able to create a new directory using the <b>mkdir</b>, or make directory, command. Simply type out its desired path and name: <b>mkdir [parentDirectory(s)/newDirectory]</b>. Try making one in the SnakeIsland Directory.");
        stepCount++;
      } else if (stepCount == 19 && location.name().equals("SmallBoxWithLock")) {
        output.setText("The <b>ln</b>, command is used to create links between directories and files. There are two kinds of links: soft links and hard links. We will be creating a soft link, a symbolic path indicating the abstract location of a file. The syntax for creating a soft link is <b>ln -s [file] [path]</b>. Try linking the file here with your directory on SnakeIsland.");
        stepCount++;
      } else if (stepCount == 30 && location.name().equals("Library")) {
        return true;
      }
    } else if (command.equals("ls")) {
      if (stepCount == 8 && location.name().equals("Shack") && cmd.getFlags().size() > 0) {
        output.setText("The floor of the shack with a floor board just a little different from the rest.");
        stepCount++;
      } else if (stepCount == 9 && location.name().equals(".FloorBoard")) {
        output.setText("Knowing me, I’d probably lose this key in an instant! I should make a container back at base to hold on to what I find here. After it’s made, I can simply move things to it. [Enter]");
        stepCount++;
      } else if (stepCount == 16 && location.name().equals("RockTwo")) {
        output.setText("I think that the key.txt would be useful.");
        rock3.addDirectory(box);
        stepCount++;
      }
    } else if (command.equals("ln")) {
      if (stepCount == 20 && location.name().equals("SmallBoxWithLock")) {
        output.setText("Time to head down another path.");
        stepCount++;
      }
    } else if (command.equals("cat")) {
      if (stepCount == 6 && location.name().equals("Shack") && cmd.getInputs().get(0).equals("soYouWantTo.txt")) {
        output.setText("soYouWantTo.txt contains too much text to read over. To read the last 10 lines of a text file, we can use the <b>tail [file_name]</b> command. Try using it now.");
        stepCount++;
      } else if (stepCount == 14 && location.name().equals("SnakeIsland") && cmd.getInputs().get(0).equals("riddle2.txt")) {
        output.setText("You know what to do.");
        stepCount++;
      } else if (stepCount == 18 && location.name().equals("SmallBoxWithLock")) {
        output.setText("This information will be useful, but I won’t be able to remember that! Since it’s stuck, I’ll just have to make a link to it. [Enter]");
        stepCount++;
      } else if (stepCount == 28 && location.name().equals("PathOne")) {
        output.setText("I think that we need that stone.txt.");
        stepCount++;
      }
    } else if (command.equals("cd")) {
      if (stepCount == 2 && location.name().equals("SnakeIsland")) {
        output.setText("I hope you have good reason for coming here, Doctor Jones. Not many are even allowed here on Snake Island. Before you go, I have words of warning for you, there are four trails here on the Island. I would recommend sticking to them unless you want an early death. [Enter]");
        stepCount++;
      } else if (stepCount == 4 && location.name().equals("PathTwo")) {
        output.setText("That shack looks scary. We should go in there.");
        stepCount++;
      } else if (stepCount == 5 && location.name().equals("Shack")) {
        output.setText("Let's look around.");
        stepCount++;
      } else if (stepCount == 13 && location.name().equals("SnakeIsland")) {
        output.setText("What path do we head down next?");
        island.delFile(riddle);
        island.addFile(riddle2);
        stepCount++;
      } else if (stepCount == 15 && location.name().equals("PathThree")) {
        output.setText("We should look around; maybe check out those weird rocks.");
        stepCount++;
      } else if (stepCount == 21 && location.name().equals("SnakeIsland")) {
        output.setText("What path do we head down next?");
        island.delFile(riddle2);
        island.addFile(riddle3);
        stepCount++;
      } else if (stepCount == 22 && location.name().equals("PathFour")) {
        output.setText("I just really enjoy looking in things that are suspicious looking.");
        stepCount++;
      } else if (stepCount == 23 && location.name().equals("TreasureChest")) {
        output.setText("Remember! mkdir makes a new directory. | To delete files in Linux, simply use the Remove command rm. The format of rm looks like this: rm file_name. Use it to finish Path Four’s puzzle.");
        stepCount++;
      } else if (stepCount == 27 && location.name().equals("PathOne")) {
        output.setText("Hmm, the door looks like it has something written on it.");
        stepCount++;
      } else if (stepCount == 25 && location.name().equals("SmallCompartment")) {
        output.setText("I should put this stone in my container for safe-keeping.");
        stepCount++;
      } else if (stepCount == 30 && location.name().equals("Cave")) {
        output.setText("Another amulet? I understand the frog amulet, but now there’s two? This adventure did not help me in the slightest. I didn’t realise I’d be so happy to say this, but with the snakes, I think it’s time to head back to the library for some research!");
        stepCount++;
      } else if (stepCount == 31 && location.name().equals("Library")) {
        output.setText("Congratulations! You've completed level 3. Hit [Enter] to continue to level 4.");
        stepCount++;
      }

    } else if (command.equals("tail")) {
      if (stepCount == 7 && location.name().equals("Shack")) {
        output.setText("What was the argument for the <b>ls</b> command to also print <b>.</b> files again? <b>a</b> something? <b>-</b> something?");
        stepCount++;
      }
    } else if (command.equals("mkdir")) {
      if (stepCount == 11 && location.name().equals(".FloorBoard")) {
        output.setText("Just as you specified the path for the new directory, you are also able to specify the path you want to move files using the <b>mv</b> command.");
        stepCount++;
      }
    } else if (command.equals("mv")) {
      if (stepCount == 12 && location.name().equals(".FloorBoard")) {
        output.setText("Let's head back and see what path we need to head down next.");
        stepCount++;
      } else if (stepCount == 17 && location.name().equals("RockTwo")) {
        output.setText("Let's see what's in the box.");
        box.addFile(path4Ans);
        stepCount++;
      } else if (stepCount == 26 && location.name().equals("TreasureChest")) {
        output.setText("Let's head back and go to the next path - we've only got one path left.");
        stepCount++;
      } else if (stepCount == 29 && location.name().equals("PathOne") && path1.getFiles().contains(stone)) {
        output.setText("Let's check out the cave.");
        path1.addDirectory(cave);
        stepCount++;
      }
    } else if (stepCount == 24 && checkChest() == true) {
      output.setText("It looks like a small compartment opened up!");
      chest.addDirectory(compartment);
      stepCount++;
    }

      return false;

  }

  protected void setLocation(Directory d) {
    location = d;
  }

  protected Directory buildLevel(Directory root) {
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
    island = new Directory("SnakeIsland", brazil, new ArrayList<Directory>(), new ArrayList<File>());
    Directory china = new Directory("China", airport, new ArrayList<Directory>(), new ArrayList<File>());
    Directory russia = new Directory("Russia", airport, new ArrayList<Directory>(), new ArrayList<File>());

    airport.addDirectory(bathroom);
		airport.addDirectory(baggage);
		airport.addDirectory(egypt);
    airport.addDirectory(brazil);
    airport.addDirectory(china);
    airport.addDirectory(russia);
    brazil.addDirectory(island);
		egypt.addDirectory(giza);
		egypt.addDirectory(alexandria);

    riddle = new File("riddle.txt", "path = 1\n if path == 4 \n     path = 1\n else\n     path++\n Which way should I go?");
    riddle2 = new File("riddle2.txt", "path = 2\n if path == 4 \n     path = 1\n else\n     path++\n Which way should I go?");
    riddle3 = new File("riddle3.txt", "path = 3\n if path == 4 \n     path = 1\n else\n     path++\n Which way should I go?");
    door = new File("door.txt", "I seek eight faces\nfour is their origin\nonly with the eight from four\nwill I open this door.");

    island.addFile(riddle);

    path1 = new Directory("PathOne", island, new ArrayList<Directory>(), new ArrayList<File>());
    Directory path2 = new Directory("PathTwo", island, new ArrayList<Directory>(), new ArrayList<File>());
    Directory path3 = new Directory("PathThree", island, new ArrayList<Directory>(), new ArrayList<File>());
    Directory path4 = new Directory("PathFour", island, new ArrayList<Directory>(), new ArrayList<File>());

    island.addDirectory(path1);
    island.addDirectory(path2);
    island.addDirectory(path3);
    island.addDirectory(path4);

    File snake2 = new File("snakeIslandInfo.txt", "Snake Island is a small island, almost completely unknown to most, outside of Brazil. Only true travelers ever make it to the island, and most do not leave with what they came for. Snake Island tends to contain peculiar puzzles that no one has ever been able to complete. Mostly because they do not contain the skills necessary to complete the tasks. If you are choosing to proceed to the puzzles - good luck.");
    File log = new File("travelLog.txt", "I’ve been on Snake Island for a while now - and I still don’t know how to get the stone door on path one opened. What does it mean eight from four? Are there four people with two faces? I’ve been trying to read through the SoYouWantTo.txt book but it’s just so much gibberish. I keep trying to figure out what it is trying to tell me. I’ve gone through more than half of it and nothing. Maybe there is stuff towards the end. I’ll have to keep reading.");
    File penguin = new File("penguinAnatomy.txt", "They can't fly.");
    File want = new File("soYouWantTo.txt", "What are you doing?\nHow are you doing?\nIt is hot today.\nWhy is it so hot today?\nI don't get it.\n" +
      "What is that in the tree?\nThat's a weird looking bird.\nI wish I wasn't so hot.\nBut still, why so hot?\nStop rambling.\nYou aren't talking about anything important.\n" +
      "I can name a lot of birds.\n" +
      "Like there are cardinals.\n" +
      "I guess an eagle.\n" +
      "Wow I am tired.\n" +
      "I wish there was a nice place to sleep.\n" +
      "It just gets so dark out here.\n" +
      "I don't like it at all.\n" +
      "At least I am away from a big city.\n" +
      "This is all for distraction.\n" +
      "Are you ready to know what is really going on?\n" +
      "Hold on to your socks.\n" +
      "Or toes if you are not wearing socks.\n" +
      "Or your pants. Incase you don't have toes.\n" +
      "Ahh, so you’ve made it this far. Congrats.\n" +
      "It is nice to know someone has even made it this far.\n" +
      "So you’re looking for the amulet?\n" +
      "I can’t help you with the rest, but I can help you here.\n" +
      "Getting to the amulet is tough but just remember.\n" +
      "To get through the next path, you will need a key.\n" +
      "Where is the key?\n" +
      "What book doesn’t seem to fit?\n" +
      "Check under the floorboard.\n" +
      "Head to the next path with the key and continue from there.");
    File key = new File("key.txt", "");

    Directory shack = new Directory("Shack", path2, new ArrayList<Directory>(), new ArrayList<File>());
    Directory board = new Directory(".FloorBoard", shack, new ArrayList<Directory>(), new ArrayList<File>());

    path2.addDirectory(shack);
    shack.addDirectory(board);
    shack.addFile(snake2);
    shack.addFile(log);
    shack.addFile(penguin);
    shack.addFile(want);

    board.addFile(key);

    Directory rock1 = new Directory("RockOne", path3, new ArrayList<Directory>(), new ArrayList<File>());
    Directory rock2 = new Directory("RockTwo", path3, new ArrayList<Directory>(), new ArrayList<File>());
    Directory rock3 = new Directory("RockThree", path3, new ArrayList<Directory>(), new ArrayList<File>());
    box = new Directory("SmallBoxWithLock", rock2, new ArrayList<Directory>(), new ArrayList<File>());

    path3.addDirectory(rock1);
    path3.addDirectory(rock2);
    path3.addDirectory(rock3);
    //rock3.addDirectory(box);

    path4Ans = new File("pathFourAns.txt", "Goat to Tree\n	Monkey to Rope\n Snake to Hole\n Sheep to Hill\n But the remaing will have to go.");

    chest = new Directory("TreasureChest", path4, new ArrayList<Directory>(), new ArrayList<File>());
    path4.addDirectory(chest);

    goat = new File("goat.txt", "");
    monkey = new File("monkey.txt", "");
    sheep = new File("sheep.txt", "");
    hippo = new File("hippo.txt", "");
    serpent = new File("snake.txt", "");

    hole = new Directory("Hole", chest, new ArrayList<Directory>(), new ArrayList<File>());
    tree = new Directory("Tree", chest, new ArrayList<Directory>(), new ArrayList<File>());
    hill = new Directory("Hill", chest, new ArrayList<Directory>(), new ArrayList<File>());

    chest.addDirectory(hole);
    chest.addDirectory(tree);
    chest.addDirectory(hill);
    chest.addFile(goat);
    chest.addFile(monkey);
    chest.addFile(sheep);
    chest.addFile(hippo);
    chest.addFile(serpent);

    compartment = new Directory("SmallCompartment", chest, new ArrayList<Directory>(), new ArrayList<File>());
    stone = new File("stone.txt", "");

    cave = new Directory("Cave", path1, new ArrayList<Directory>(), new ArrayList<File>());
    path1.addFile(door);

    return airport;

  }

  public boolean checkChest() {
    if (hole.getFiles().contains(serpent) && tree.getFiles().contains(goat) && hill.getFiles().contains(sheep) && !chest.getFiles().contains(hippo) && !chest.getFiles().contains(monkey)) {
      return true;
    }

    return false;
  }

  public void devMode(int n) {
    stepCount = n;
  }


}
