import javax.swing.JTextArea;
import java.awt.*;
import javax.swing.*;


public class Level1 {

	CommandStream cs;
	String step;
	String dialogStep;
	JTextPane output;
	boolean advanceable = false;
	int stepCount;

	public Level1(String step, JTextPane output) {
		this.step = step;
		this.output = output;
		dialogStep = "1";
		stepCount = 0;
		output.setContentType("text/html");
	  output.setEditable(false);
		output.setPreferredSize(new Dimension(200, 200));

	}

	public int getLevel1Count() {
		return stepCount;
	}

	public void playLevel1(String command) {
		if(stepCount == 0) {
			String step0 = "> While doing research in the library, Dr. HAROLD finds a treasure map tucked away in the stacks. \nIt appears to lead to GIZA that contains relics that belong\nin a museum.  Let's go on an adventure!";
			output.setText("<p style='width: 95%; padding: 5px; border-style: solid; border-width: 2px; margin-bottom: 5px'" + step0 + "</p >");
			stepCount++;
		} else if(command.equals("") && stepCount == 1) {
			String step1 = "Type ls to open the map.";
			output.setText(step1);
			stepCount++;
		} else if(command.equals("ls") && stepCount == 2) {
			output.setText("In Linux, the ls command lists files and other directories in your current working directory");
			stepCount++;
		} else if(command.equals("") && stepCount == 3) {
			output.setText("In Linux, the ls command lists files in the current working directory.");
			stepCount++;
		} else if(command.equals("") && stepCount == 4) {
			output.setText("So now we know everything that's in the library, but what about\nwhat's outside the library?");
			stepCount++;
		} else if(command.equals("") && stepCount == 5) {
			output.setText("To find out where we currently are, we can use the command pwd.");
			stepCount++;
		} else if(command.equals("pwd") && stepCount == 6) {
			output.setText("pwd works like a compass.");
		}
		/*
		if(step.equals("step0")) {
			output.setText("While doing research in the library,\n<you> find a treasure map tucked away in the stacks. Type ls to open the map.");
			advanceDialog();
		} else if(step.equals("step1")) {
			advanceDialog();
			//output.setText("");
		} else if(step.equals("step2")) {
			advanceable = true;
			advanceDialog();
		} else if(step.equals("step3")) {
			advanceable = true;
			advanceDialog();
		}
		*/

	}

	/*
	public void advanceDialog() {
		if(!advanceable) {
			return;
		}
		if(dialogStep.equals("1")) {
			//output.setText("Type ls to open the map.");
			output.setText("In Linux, the ls command lists files in the current working directory.");
			dialogStep = "2";
		} else if(dialogStep.equals("2")) {
			output.setText("Now go find the treasure!");
			dialogStep = "3";
		} else if(dialogStep.equals("3")) {
			output.setText("To move around in the terminal use the cd command.");
			dialogStep = "4";
		} else if(dialogStep.equals("4")) {
			output.setText("Type cd .. to move up to the parent directory.");
			dialogStep = "5";
			advanceable = false;
		} else if(dialogStep.equals("5")) {
			output.setText("Wait! It's dangerous to go alone.\nTake this!");
			dialogStep = "6";
		} else if(dialogStep.equals("6")) {
			output.setText("Use the pwd command like a compass to find out what\ndirectory you are currently in.\nType pwd.");
			dialogStep = "7";
			advanceable = false;
		} else if(dialogStep.equals("7")) {
			output.setText("The treasure map leads to a pyramid in Giza,\nso let's go to the airport and get to Egypt!");
			dialogStep = "8";
		} else if(dialogStep.equals("8")) {
			dialogStep = "9";
			output.setText("Let's use the map again.  Type ls, unless you\nalready know where you're going!");

		}
	}
	*/
}
