import javax.swing.JTextArea;


public class Level1 {

	CommandStream cs;
	String step;
	String dialogStep;
	JTextArea output;
	boolean advanceable = false;
	int stepCount;
	
	public Level1(String step, JTextArea output) {
		this.step = step;
		this.output = output;
		dialogStep = "1";
		stepCount = 0;
	}
	
	public void playLevel1(String command) {
		if(stepCount == 0) {
			output.setText("While doing research in the library, Dr. <you> finds a treasure map tucked away in the stacks.\nIt appears to lead to <insert cool location here> that contains relics that belong\nin a museum.  Let's go on an adventure!");
			stepCount++;
		} else if(command.equals("ls") && stepCount == 2) {
			output.setText("In Linux, the ls command lists files and other directories in your current working directory");
			stepCount++;
		} else if(command.equals("pwd") && stepCount == 5) {
			output.setText("pwd works like a compass to help us get our bearings in the system.\nEach slash represents a directory level. /library is to the right of /city, so that tells us the library is in the city.");
			stepCount++;
		} else if(command.equals("cd")) {
			if(stepCount == 8) {
				stepCount++;
				output.setText("The map says the <insert same cool location here> is in Giza.  That's\nway too far for a penguin to fly, so let's go to the airport.");
			} else if(stepCount == 19) {
				output.setText("Thank goodness for time-saving montages. That felt way shorter than 15 hours.");
				stepCount++;
			} else if(stepCount == 11) {
				output.setText("Before we take off, let's get our baggage to the baggage check.");
				stepCount++;
			}
		} else if(command.equals("ls")) {
			if(stepCount == 10) {
				output.setText("You can move into listed sub-directories in the\ncity by using the cd command again.\nBy typing cd <directory name> you can move directly to that directory.\nSo to get to the airport, type cd Airport.");
				stepCount++;
			} else if(stepCount == 22) {
				
			}
		} else if(stepCount == 12) {
			output.setText("To move files, Linux uses the mv command.");
			stepCount++;
		} else if(command.equals("mv luggage") && stepCount == 15) {
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
				output.setText("So now we know everything that's in the library, but what about\nwhat's outside the library?");
				stepCount++;
			} else if(stepCount == 4) {
				output.setText("To find out where we currently are, we can use the command pwd.");
				stepCount++;
			} else if(stepCount == 6) {
				output.setText("To move around in the terminal, use cd.");
				stepCount++;
			} else if(stepCount == 7) {
				output.setText("Type cd .. to move up to the parent directory.  In\nthis case, the city is the parent directory for the\nlibrary, which is where we want to go!");
				stepCount++;
			} else if(stepCount == 9) {
				output.setText("Type ls to open the map.");
				stepCount++;
			} else if(stepCount == 13) {
				output.setText("The format for the mv command is: mv <file name> <directory path>.  Type pwd to see your current\ndirectory path.");
				stepCount++;
			} else if(stepCount == 14) {
				output.setText("So, to move our luggage to the baggage check type: mv Luggage.txt /root/Level1/City/Airport/BaggageCheck/Luggage.txt\n(Hint: you can use ls at any time to see file and directory names at your location.");
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
				output.setText("You can find your way to Giza similar to the way we found Egypt from\nthe airport. Type ls to pull up the map and see what\nplaces we can go.");
				stepCount++;
			}
		}
	}
}
