import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class CommandStream implements ActionListener {

	JTextField input;
	JTextArea output;
	Scanner scan;
	Directory currentDirectory;
	
	public CommandStream(JTextField input, JTextArea output, Directory cd) {
		this.input = input;
		this.output = output;
		currentDirectory = cd;
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
		} else if(command.equals("cd")) {
			//System.out.println("DEBUG: command is " + command);
			if(scan.hasNext()) {
				String location = scan.next();
				//System.out.println("DEBUG: input has next, and it's " + location);
					if(location.equals("..")) {
						if(currentDirectory.getParent() != null) {
							currentDirectory = currentDirectory.getParent();
						}  //end root check
						
						output.append("Current working directory is now " + currentDirectory.name() + "\n");
						input.setText("");
					} //else check directory path
			}  //end cd check
		} else if(command.equals("cat")) {
			System.out.println(scan.hasNext());
			if(scan.hasNext()) {
				cat(scan.next(), currentDirectory, output);
			} else {
				invalid(input, output);
			}
		} else if(command.equals("chmod")) {
			
		} else if(command.equals("ssh")) {
			
		} else if(command.equals("nano")) {
			
		} else if(command.equals("scp")) {
			
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
		for(Directory dir : currentDirectory.getSubDirs()) {
			output.append(dir.name() + "\n");
		}
		for(File file : currentDirectory.getFiles()) {
			output.append(file.getName() + "\n");
		}
		//output.append(input.getText() + "\n");
		input.setText("");
	}
	
	public void invalid(JTextField input, JTextArea output) {
		output.append(input.getText() + " is not a valid command.\n");
	}
	
	public void cat(String filename, Directory dir, JTextArea output) {
		File file = findFile(filename, dir);
		if(file == null) {
			output.append("No such file.\n");
			return;
		}
		output.append(file.getContents() + "\n");
	}
	
	public void exit() {
		System.exit(0);
	}
	//-----------------------------------------------------
	
	public File findFile(String name, Directory currentDirectory) {
		//File ret = new File("", "");
		for(File f : currentDirectory.getFiles()) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
}
