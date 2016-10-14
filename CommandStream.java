import java.awt.event.*;

import javax.swing.*;

import java.util.Scanner;

public class CommandStream implements ActionListener {

	JTextField input;
	JTextArea output;
	Scanner scan;
	Directory currentDirectory;
	final Directory root;
	
	public CommandStream(JTextField input, JTextArea output, Directory cd, Directory root) {
		this.input = input;
		this.output = output;
		currentDirectory = cd;
		this.root = root;
	}
	
	public void actionPerformed(ActionEvent e) {
		//this retrieves the text from the input field
		String text = input.getText();
		
		//this is to process the string and determine the command entered
		scan = new Scanner(text);
		
		String command = scan.next();
		
		//block of if statements to find the command the user entered
		if(command.equals("mv")) {
			mv(input, output, scan, root);
		} else if(command.equals("ls")) {
			ls(input, output);
		} else if(command.equals("exit")) {
			exit();
		} else if(command.equals("clear")) {
			output.setText("");
			input.setText("");
		} else if(command.equals("cd")) {
			cd(input, output, scan);
		} else if(command.equals("cat")) {
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
	public void mv(JTextField input, JTextArea output, Scanner scan, Directory root) {
		
		String name = null;
		String path = null;
		
		//checking file parameter
		if(scan.hasNext()) {
			name = scan.next();
		} else {
			output.append("Missing filename parameter.\n");
			return;
		}
		
		//locating specified file
		File file = findFile(name, currentDirectory);
		
		//checking for existence of specified file
		if(file == null) {
			output.append("File " + name + " not found.\n");
			return;
		}
		
		//checking for filepath destination parameter
		if(scan.hasNext()) {
			path = scan.next();
		} else {
			output.append("Missing parameter\n");
			return;
		}
		
		//deleting file from current location (file still exists)
		currentDirectory.delFile(file);
		
		String[] filePath = path.split("/");
		
		Directory destination = validateFilePath(filePath);
		
		if(destination == null) {
			output.append("Invalid file path!\n");
			currentDirectory.addFile(file);
			return;
		}
		
		
		//---------------------------------
		//UNTESTED SECTION!!
		String newFileName = filePath[filePath.length - 1];
		
		for(Directory d : destination.getSubDirs()) {
			if(d.name().equals(newFileName)) {
				Directory newDestination = destination.getParent();
				newDestination.remDir(destination);
				break;
			}
		}
		//----------------------------------
		
		/*
		if(destination.name().equals(file.getName())) {
			Directory newDest = destination.getParent();
			newDest.remDir(destination);
		}
		*/
		
		file.setName(filePath[filePath.length - 1]);
		destination.addFile(file);
	}

	public void ls(JTextField input, JTextArea output) {
		for(Directory dir : currentDirectory.getSubDirs()) {
			output.append(dir.name() + "\n");
		}
		for(File file : currentDirectory.getFiles()) {
			output.append(file.getName() + "\n");
		}
		input.setText("");
	}
	
	public void cd(JTextField input, JTextArea output, Scanner scan) {
		//first check: does cd have an argument?
		if(scan.hasNext()) {
			String location = scan.next();
			
			//second check: is the cd argument ..?
			if(location.equals("..")) {
				if(currentDirectory.getParent() != null) {
					currentDirectory = currentDirectory.getParent();
				}
				
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
			} else {
				//third check: is the directory in the argument a valid subdirectory?
				for(Directory dir : currentDirectory.getSubDirs()) {
					if(dir.name().equals(location)) {
						currentDirectory = dir;
						input.setText("");
						output.append("Current working directory is now " + currentDirectory.name() + "\n");
						return;
					}  //end if
				}  //end for
				output.append("Directory \"" + location + "\" not found.\n");
			}  //end else
		} else {
			output.append("Invalid parameters." + "\n");
		}  //end scan.hasNext check
	}  //end method
	
	public void invalid(JTextField input, JTextArea output) {
		output.append(input.getText() + " is not a valid command.\n");
	}
	
	public void cat(String filename, Directory dir, JTextArea output) {
		File file = findFile(filename, dir);
		if(file == null) {
			output.append("No such file.\n");
			return;
		}
		input.setText("");
		output.append(file.getContents() + "\n");
	}
	
	public void exit() {
		System.exit(0);
	}
	//-----------------------------------------------------
	
	//finds a file by name in the current working directory or null if no such file exists
	public File findFile(String name, Directory currentDirectory) {
		//File ret = new File("", "");
		for(File f : currentDirectory.getFiles()) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
	public Directory validateFilePath(String[] path) {
		boolean thereIsAProblem = false;
		
		Directory dir = root;
		
		for(int i = 0; i < path.length  - 1; i++) {
			if(path[i].equals("") || path[i].equals(root.name())) {
				continue;
			}  //end if
			
			for(Directory d : dir.getSubDirs()) {
				if(d.name().equals(path[i])) {
					dir = d;
					thereIsAProblem = false;
					break;
				} else {
					thereIsAProblem = true;
				}  //end if
			}  //next d
			
			if(thereIsAProblem) {
				return null;
			}
			
		}  //next i
		
		return dir;
	}
	
}