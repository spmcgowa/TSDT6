import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class CommandStream implements ActionListener {

	JTextField input;
	JTextArea output;
	Scanner scan;
	
	public CommandStream(JTextField input, JTextArea output) {
		this.input = input;
		this.output = output;
	}
	
	public void actionPerformed(ActionEvent e) {
		//this retrieves the text from the input field
		String text = input.getText();
		
		//this is to process the string and determine the command entered
		scan = new Scanner(text);
		
		String command = scan.next();
		
		//block of if statements to find the command the user entered
		if(command.equals("mv")) {
			mv(input, output);
		} else if(command.equals("ls")) {
			ls(input, output);
		} else if(command.equals("exit")) {
			exit();
		} else if(command.equals("clear")) {
			output.setText("");
			input.setText("");
		} else {
			invalid(input, output);
		}
	}
	
	//these methods execute the proper command based on the command identified above in the ifs block
	//-----------------------------------------------------
	public void mv(JTextField input, JTextArea output) {
		output.append(input.getText() + "\n");
		input.setText("");
	}
	
	public void ls(JTextField input, JTextArea output) {
		output.append(input.getText() + "\n");
		input.setText("");
	}
	
	public void invalid(JTextField input, JTextArea output) {
		output.append(input.getText() + " is not a valid command.\n");
	}
	
	public void exit() {
		System.exit(0);
	}
	//-----------------------------------------------------
	
}
