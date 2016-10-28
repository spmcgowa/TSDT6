import javax.swing.JTextArea;

public class Level1  {

	CommandStream cs;
	String command;
	JTextArea output;
	//MouseListener click;

	public Level1(String command, JTextArea output/*, MouseListener click*/) {
		this.command = command;
		this.output = output;
		//this.click = click;
	}

	public void playLevel1(String command) {
		int stepCount = 0;
		if (stepCount == 0) {
			output.setText("While doing research in the library, Harold finds a treasure map tucked away in the stacks.");
			stepCount++;
			System.out.println(stepCount);
		} else if(command.equals("next") || stepCount == 1) {
			output.setText("Type ls to open the map.");
			stepCount++;
		} else if (command.equals("ls")) {
			output.setText("In Linux, the ls command lists files and other directories in your current working directory");
		}


	}
}
