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
	
	public void playLevel1(String input) {
		//[TODO] AddScript!
		output.setText("No, this is Patrick!");
	}
}
