import java.awt.event.MouseListener;

import javax.swing.JTextArea;


public class Level1  {

	CommandStream cs;
	String step;
	JTextArea output;
	//MouseListener click;
	
	public Level1(String step, JTextArea output/*, MouseListener click*/) {
		this.step = step;
		this.output = output;
		//this.click = click;
	}
	
	public void playLevel1(String step) {
		if(step.equals("step0")) {
			//click.mouseClicked(null);
			output.setText("While doing research in the library,\n<you> find a treasure map tucked away in the stacks.\nType ls to open the map.");
		} else if(step.equals("step1")) {
			output.setText("In Linux, the ls command lists files in the current\nworking directory.  Now go find the treasure!\nTo move around in the terminal, use the cd command.\ncity/library tells us that city is the parent direcory of library.\nType cd - or cd .. to move up to the parent directory.");
		} else if(step.equals("step2")) {
			
		}
		
	}
}
