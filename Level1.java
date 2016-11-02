import java.awt.Dimension;

import javax.swing.*;


public class Level1 {

	CommandStream cs;
	String step;
	String dialogStep;
	JTextPane output;
	boolean advanceable = false;
	int stepCount;
	Directory location;
	
	public Level1(String step, JTextPane output) {
		this.step = step;
		this.output = output;
		dialogStep = "1";
		stepCount = 0;
		output.setContentType("text/html");
		output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));
	}
	
	public void playLevel1(String command) {
		if(stepCount == 0) {
			output.setText("While doing research in the library, Dr. [you] finds a treasure map tucked away in the stacks.<br>It appears to lead to [insert cool location here] that contains relics that belong<br>in a museum.  Let's go on an adventure!");
			stepCount++;
		} else if(command.equals("ls") && stepCount == 2) {
			output.setText("In Linux, the ls command lists files and other directories in your current working directory");
			stepCount++;
		} else if(command.equals("pwd") && stepCount == 5) {
			output.setText("pwd works like a compass to help us get our bearings in the system.<br>Each slash represents a directory level. /library is to the right of /city, so that tells us the library is in the city.");
			stepCount++;
		} else if(command.equals("cd")) {
			if(stepCount == 8) {
				stepCount++;
				output.setText("The map says the <insert same cool location here> is in Giza.  That's<br>way too far for a penguin to fly, so let's go to the airport.");
			} else if(stepCount == 19) {
				output.setText("Thank goodness for time-saving montages. That felt way shorter than 15 hours.");
				stepCount++;
			} else if(stepCount == 11) {
				output.setText("Before we take off, let's get our baggage to the baggage check.");
				stepCount++;
			} else if(stepCount == 23) {
				if(location.name().equals("Alexandria")) {
					output.setText("Whoops. Took a wrong turn. In Linux, to get back to<br>your previous working directory, use the command cd -");
				} else if(location.name().equals("Giza")) {
					output.setText("Finally made it to Giza, now to find that <insert same cool location here> your research uncovered.<br>Try the ls command to pull up the map and see what's here.");
					stepCount++;
				} else if(location.name().equals("Egypt")) {
					output.setText("From the airport you can get to Giza or Alexandria. Type cd Giza to get to Giza.");
				}
			}
		} else if(command.equals("ls")) {
			if(stepCount == 10) {
				output.setText("You can move into listed sub-directories in the<br>city by using the cd command again.<br>By typing cd <directory name> you can move directly to that directory.<br>So to get to the airport, type cd Airport.");
				stepCount++;
			} else if(stepCount == 22) {
				output.setText("Huh. Looks like your luggage was lost. Guess it'll show up eventually.<br>From the airport you can get to Giza or Alexandria.");
				stepCount++;
			} else if(stepCount == 25) {
				output.setText("");
			}
		} else if(stepCount == 12) {
			output.setText("To move files, Linux uses the mv command.");
			stepCount++;
		} else if(command.equals("mv") && stepCount == 15) {
			output.setText("Now we can head to Egypt using the cd command.");
			stepCount++;
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
				output.setText("Type cd Giza to go to Giza.");
			} else if(stepCount == 24) {
				output.setText("Finally made it to Giza. Now to find that <insert same cool location here>.<br>Try the ls command to pull up the map and see what's here.");
				stepCount++;
			}
		}
	}
	
	protected void setLocation(Directory d) {
		location = d;
	}
}