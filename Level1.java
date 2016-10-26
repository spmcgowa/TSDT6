import javax.swing.JTextArea;


public class Level1 implements Runnable {

	CommandStream cs;
	String step;
	String dialogStep;
	JTextArea output;
	boolean advanceable = false;
	//MouseListener click;
	
	public Level1(String step, JTextArea output/*, MouseListener click*/) {
		this.step = step;
		this.output = output;
		dialogStep = "1";
		//this.click = click;
	}
	
	public void playLevel1(String step) {
		if(step.equals("step0")) {
			//click.mouseClicked(null);
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
		
	}

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
	
	@Override
	public void run() {
		advanceDialog();
	}
	
	protected void setAdvanceable(boolean b) {
		advanceable = b;
	}
}
