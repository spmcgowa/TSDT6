import java.util.ArrayList;

import javax.swing.*;

public class Level6 extends Level {

  JTextPane output;
	int stepCount;
	Directory location;
	Directory red;
	String place;

  File journey;

  public Level6(JTextPane output) {
		this.output = output;
		stepCount = 0;

	}

  public boolean playLevel(Command cmd) {
		String command = cmd.getCommand();
		//place = location.name();

    if (stepCount == 0) {
      output.setText("Bleepblop: I knew you’d be around soon enough. I’m pleased to see you well. [Enter]");
      stepCount++;
    }
		/* Checking for empty command to advance dialogue */
		 else if(command.equals("")) {
       if (stepCount == 1 ) {
         output.setText("Doctor Jones: “W-Where am I? [Enter]");
         stepCount++;
       } else if (stepCount == 2) {
         output.setText("Bleepblop: It is not simply where you are, it is also when. [Enter]");
         stepCount++;
       } else if (stepCount == 3) {
         output.setText("Doctor Jones: “Who are you? [Enter]");
         stepCount++;
       } else if (stepCount == 4) {
         output.setText("Bleepblop: “My face name is Bleepblop. I am what you would call an interdimensional being. I exist here and there. I am as old as the universe and as young as a newborn. This place is your home now, the amulets bound you here. It is your local system. [Enter]");
         stepCount++;
       }

		/* Checking for chmod command */
    } else if(command.equals("cat")) {

		/* Checking for cd command */
		}

    return false;

	}

  protected Directory buildLevel(Directory root) {

    //Directory lv6 = new Directory("Level6", root, new ArrayList<Directory>(), new ArrayList<File>());
		//root.addDirectory(lv6);

    Directory dim = new Directory("DimensionX", root, new ArrayList<Directory>(), new ArrayList<File>());
    Directory spoksHouse = new Directory("AlienHouse", dim, new ArrayList<Directory>(), new ArrayList<File>());
    Directory grandCentralStation = new Directory("GrandCentralStation", dim, new ArrayList<Directory>(), new ArrayList<File>());

    root.addDirectory(dim);
    dim.addDirectory(spoksHouse);
    dim.addDirectory(grandCentralStation);

    return spoksHouse;

  }
}
