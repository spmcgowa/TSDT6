import java.awt.event.*;

import javax.swing.*;

import java.util.Scanner;

import java.util.ArrayList;

public class CommandStream implements ActionListener {

	JTextField input;
	JTextArea output;
	Directory currentDirectory;
	JPanel buttons;
	File nanoFile;
	Directory prevDir;
	final Directory root;
	String step = "step0";
	Level1 lv;
	boolean storyReadyToAdvance = true;

	public CommandStream(JTextField input, JTextArea output, Directory cd,
			Directory root, JPanel buttons, String lv1, JTextArea graphicsTextOutput) {
		this.input = input;
		this.output = output;
		currentDirectory = cd;
		this.root = root;
		this.buttons = buttons;
		nanoFile = null;
		prevDir = null;
		lv = new Level1(step, graphicsTextOutput/*, click*/);
		lv.playLevel1(step);
		step = "step1";
	}

	public void actionPerformed(ActionEvent e) {

		Object thing = e.getSource();

		// use this to take the text from the jtextarea and update the file
		if (thing instanceof JButton) {
			if (e.getActionCommand().equals("save")) {
				nanoFile.writeContents(output.getText());
				return;
			}
			output.setEditable(false);
			input.setEditable(true);
			output.setText("");
			input.setText("");
			buttons.setVisible(false);
			return;
		}

		// this retrieves the text from the input field
		String text = input.getText();
		input.setText("");
		ArrayList<Command> commands = Command.GenerateCommands(text);
		
		//Loop through all commands found and do them in order!
		for (int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			if (command.isErrored()) {
				invalid(input, output);
			}
			
			if (command.getCommand().equals("mv")) {
			} else if (command.getCommand().equals("ls")) {
				ls(command);
			} else if (command.getCommand().equals("exit")) {
				exit();
			} else if (command.getCommand().equals("clear")) {
				clear(command);
			} else if (command.getCommand().equals("cd")) {
				cd(command);
			} else if (command.getCommand().equals("cat")) {
				cat(command);
			} else if (command.getCommand().equals("pwd")) {
				pwd(command);
			} else if (command.getCommand().equals("cp")) {
				cp(command);
			} else if (command.getCommand().equals("nano")) {
				nano(command);
			} else if(command.getCommand().equals("")) {
				lv.playLevel1("");
			}
		}
	}
	
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Block of Helper Methods
	public void invalid(JTextField input, JTextArea output) {
		output.append(input.getText() + " is not a valid command.");
	}
	
	
	// finds a file by name in the current working directory or null if no such
	// file exists
	public File findFile(String name, Directory currentDirectory) {
		// File ret = new File("", "");
		for (File f : currentDirectory.getFiles()) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	public Directory validateFilePath(String[] path) {
		boolean thereIsAProblem = false;

		// start at the root
		Directory dir = root;

		// this loop checks each directory name specified in the path argument
		for (int i = 0; i < path.length; i++) {
			if (path[i].equals("") || path[i].equals(root.name())) {
				continue;
			} // end if
			
			// yes, this has a terrible big-oh runtime, but it works; this
			// checks each
			// subdirectory all the way down to make sure the given path is
			// valid
			for (Directory d : dir.getSubDirs()) {
				if (d.name().equals(path[i])) {
					dir = d;
					thereIsAProblem = false;
					break;
				} else {
					thereIsAProblem = true;
				} // end if
			} // next d

			// if there is a problem (i.e. /root/badDir/username, where badDir
			// doesn't exist) return null for no directory found
			if (thereIsAProblem) {
				if(i == path.length - 1) {
					return dir;
				}
				return null;
			}

		} // next i

		return dir;
	}
	public void sendOutput(String out) {
		output.append(out);
	}
	
	protected void setStartingDir(Directory d) {
		currentDirectory = d;
	}
	
	//End block of Helper Methods
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Methods to execute Commands with the Command Class
	
	
	
	//Below is the blank text for a method description, copy and paste it above a new Command Method
	//-----------------------------------------------------------
	// Command:
			// Description:
		// Input
			// Required:
			// Optional:
		// Flags
			// 
	//-----------------------------------------------------------
	
	
	
	
	
	
	//-----------------------------------------------------------
	// Command: Clear
			// Description: Clears the Screen
		// Input
			// Required: None
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void clear(Command command) {
		output.setText("");
		input.setText("");
		lv.playLevel1("clear");
	}
	
	
	//-----------------------------------------------------------
	// Command: mv
			// Description: Moves a file
		// Input
			// Required: 0=File, 1=Destination
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void mv(Command command) {
		
		

		String name = null;
		String path = null;

		// checking file parameter
		if (scan.hasNext()) {
			name = scan.next();
		} else {
			output.append("Missing filename parameter.\n");
			return;
		}

		// locating specified file (could be null if no such file)
		File file = findFile(name, currentDirectory);

		// checking for existence of specified file
		if (file == null) {
			output.append("File " + name + " not found.\n");
			return;
		}

		// checking for filepath destination parameter
		if (scan.hasNext()) {
			path = scan.next();
		} else {
			output.append("Missing parameter\n");
			return;
		}

		// deleting file from current location (file still exists because of
		// "file" variable)
		currentDirectory.delFile(file);

		String[] filePath = path.split("/");
		String[] actualPath = /*new String[filePath.length - 1];*/filePath;
		/*
		for(int i = 0; i < filePath.length - 1; i++) {
			actualPath[i] = filePath[i];
		}
		*/

		// locate final directory destination
		Directory destination = validateFilePath(actualPath);

		// check for a valid destination; if not, add the file back to the
		// current directory
		if (destination == null) {
			output.append("Invalid file path!\n");
			currentDirectory.addFile(file);
			return;
		}

		// mv can be used to rename, this takes care of that
		String newFileName = filePath[filePath.length - 1];

		// this for-each loop looks for a directory with the same name as the
		// file and overwrites it with the file if so,
		// just like actual Linux
		for (Directory d : destination.getSubDirs()) {
			if (d.name().equals(newFileName)) {
				destination.remDir(d);
				break;
			}
		}

		// this updates the file's name and adds it to the appropriate directory
		file.setName(filePath[filePath.length - 1]);
		destination.addFile(file);

		lv.playLevel1("mv luggage");
		
		input.setText("");
	}

	//-----------------------------------------------------------
	// Command: cp
			// Description: Copy a file to a destination
		// Input
			// Required: 0=File, 1=Destinations
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void cp(Command command) {
		
		File f = findFile(fileName, currentDirectory);
		if (f == null) {
			output.append("File " + f + " not found!\n");
			return;
		}
		String path[] = destName.split("/");
		
		Directory destination = validateFilePath(path);
		if (destination == null) {
			output.append("Invalid path!\n");
			return;
		}
		
		String name = f.getName();
		
		//filename specified
		if(!destination.name().equals(path[path.length - 1])) {
			name = path[path.length - 1];
		}
		
		destination.addFile(new File(name, f.getContents()));
		input.setText("");
		output.append("File " + name + " successfully copied to "
				+ destination.name() + "\n");
	}

	
	//-----------------------------------------------------------
	// Command: nano
			// Description: Opens a menu to modify a file via text
		// Input
			// Required: 0=File
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void nano(Command command) {
		input.setText("");
		output.setText(f.getContents());
		input.setEditable(false);
		output.setEditable(true);
		output.requestFocus();
		buttons.setVisible(true);
		nanoFile = f;
	}

	
	//-----------------------------------------------------------
	// Command: pwd
			// Description: Displays the current file path
		// Input
			// Required: None
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void pwd(Command command) {
		lv.playLevel1("pwd");
		String path = "";

		// first use of a do-while loop, I'm so proud!
		do {
			path = "/" + currentDirectory.name() + path;
			currentDirectory = currentDirectory.getParent();
		} while (currentDirectory != null);

		output.append(path + "\n");
		input.setText("");
	}

	//-----------------------------------------------------------
	// Command: ls
			// Description: lists all files and directories in the specified directory, defaults to current
		// Input
			// Required: None
			// Optional: 0=Directory
		// Flags
			// -a
				// Listing includes normally hidden files
	//-----------------------------------------------------------
	public void ls(Command command) {
		lv.playLevel1("ls");
		for (Directory dir : currentDirectory.getSubDirs()) {
			output.append(dir.name() + "\n");
		}
		for (File file : currentDirectory.getFiles()) {
			if (a) {
				output.append(file.getName() + "\n");
			} else {
				if (file.getName().charAt(0) != '.') {
					output.append(file.getName() + "\n");
				}
			}
		}
		input.setText("");
	}

	//-----------------------------------------------------------
	// Command: cd
			// Description: Changes the current working directory
		// Input
			// Required: 0=Path
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void cd(Command command) {
		// first check: does cd have an argument?
		if (scan.hasNext()) {
			String location = scan.next();

			// second check: is the cd argument ..?
			if (location.equals("..")) {
				if (currentDirectory.getParent() != null) {
					prevDir = currentDirectory;
					currentDirectory = currentDirectory.getParent();
					lv.playLevel1("cd");
				}

				input.setText("");
				output.append("Current working directory is now "
						+ currentDirectory.name() + "\n");
			} else if(location.equals("/")) {
				prevDir = currentDirectory;
				currentDirectory = root;
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
				lv.playLevel1("cd");
			} else if(location.equals("-")) {
				
				if(prevDir == null) {
					output.append("No previous directory available.");
					return;
				}
				Directory temp = currentDirectory;
				currentDirectory = prevDir;
				prevDir = temp;
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
				lv.playLevel1("cd");
			} else {
				
				for(Directory dr : currentDirectory.getSubDirs()) {
					if(dr.name().equals(location)) {
						prevDir = currentDirectory;
						currentDirectory = dr;
						output.append("Current working directory is " + dr.name() + "\n");
						input.setText("");
						lv.playLevel1("cd");
						return;
					}
				}
				
				Directory d = validateFilePath(location.split("/"));
				
				if(d == null || !d.equals(location)) {
					output.append("Invalid file path.\n");
					return;
				}
				
				prevDir = currentDirectory;
				currentDirectory = d;
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
				lv.playLevel1("cd");
			} // end else
		} else {
			output.append("Invalid parameters." + "\n");
		} // end scan.hasNext check
	} // end method

	
	//-----------------------------------------------------------
	// Command: cat
			// Description: Outputs a text file to the screen
		// Input
			// Required: 0=File
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void cat(Command command) {
		File file = findFile(filename, dir);
		if (file == null) {
			output.append("No such file.\n");
			return;
		}
		input.setText("");
		output.append(file.getContents() + "\n");
	}

	
	//-----------------------------------------------------------
	// Command: exit
			// Description: Exits the game! [TODO] Probably should confirm with player since this is a game? Or trigger a save?
		// Input
			// Required: None
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	public void exit() {
		System.exit(0);
	}

	// End block of Command Methods
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
}
