import javax.swing.JTextArea;


public class Level1  {

	CommandStream cs;
	String step;
	JTextArea output;
	
	public Level1(String step, JTextArea output) {
		this.step = step;
		this.output = output;
	}
	
	public void playLevel1(String step) {
		if(step.equals("step0")) {
			output.setText("Type ls to open map.\n In Linux, the ls command lists files in the current working directory.\n Now go find the treasure!\n");
			//In Linux, the ls command lists files in the current working directory.
			//Now go find the treasure!
			//To move around in the terminal use the cd command
			//blah, blah, blah, etc., ...
		} else if(step.equals("step1")) {
			output.setText("Wait!  It's dangerous to go alone. Take this! [compass]\nUse the pwd command like a compass to find out what directory\nyou are currently in.  Type pwd");
			//Wait!  It's dangerous to go alone.  Take this! [compass]
			//use the pwd command like a compass to fin dout what directory you are currently in.  Type pwd
		} else if(step.equals("step2")) {
			
		}
		
	}
}
