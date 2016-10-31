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
		setCurrentDirectory(cd);
		this.root = root;
		this.buttons = buttons;
		nanoFile = null;
		prevDir = null;
		lv = new Level1(step, graphicsTextOutput/*, click*/);
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
		TerminalError error = null;
		//Loop through all commands found and do them in order!
		for (int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			sendOutput("> " + command.getSimpleString() + "\n");
			if (command.isErrored()) {
				invalid(input, output);
			}
			
			if (command.getCommand().equals("mv")) {
			} else if (command.getCommand().equals("ls")) {
				error = ls(command);
			} else if (command.getCommand().equals("exit")) {
				error = exit();
			} else if (command.getCommand().equals("clear")) {
				error = clear(command);
			} else if (command.getCommand().equals("cd")) {
				error = cd(command);
			} else if (command.getCommand().equals("cat")) {
				error = cat(command);
			} else if (command.getCommand().equals("pwd")) {
				error = pwd(command);
			} else if (command.getCommand().equals("cp")) {
				error = cp(command);
			} else if (command.getCommand().equals("nano")) {
				error = nano(command);
			}
			
			
			//Handle Errors!
			if (error != null)  {
				//Output Error!
				sendOutput(error.getString());
			}
		} // End for loop!
		
		if (commands != null) {
			lv.playLevel1(text);
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

	class SearchResults {
		public boolean validPath;
		public Directory lastFoundDir;
		public boolean endsWithFile;
		public String lastToken; //Is a file name or the desired file name
		public SearchResults() {
			this.validPath = true;
			this.lastFoundDir = null;
			this.endsWithFile = false;
			this.lastToken = "";
		}
	}
	
	
	public void setCurrentDirectory(Directory dir) {
		if (dir == null) {
			System.out.println("A method tried to set the current directory to null");
			return;
		}
		currentDirectory = dir;
	}
	
	
	public SearchResults validateFilePath(String path) {
		//We know the current directory, it is part of the command stream
		String[] tokens = path.split("/");
		SearchResults searchR = new SearchResults();
		Directory cDir = currentDirectory;
		for (int i = 0; i < tokens.length; i++) {
			
			if (("").equals(tokens[i]) || root.name().equals(tokens[i])) {
				continue;
			}
			else if (i == 0 && (".").equals(tokens[i])) {
				cDir = root;
			}
			else if (("..").equals(tokens[i])) {
				if (cDir != root) {
					if (cDir.parent == null) {
						System.out.println("Error in file heirarchy! " + cDir.name + " is not root and does not have a Parent\n");
					}
					else if (cDir == cDir.parent) {
						System.out.println("Error in file heirarchy! " + cDir.name + " is its own parent!\n");
					}
					cDir = cDir.parent;
				}
			}
			else {
				boolean found = false;
				for (Directory d : cDir.getSubDirs()) {
					if (d.name().equals(tokens[i])) { 
						cDir = d;
						searchR.lastFoundDir = d;
						found = true;
						break;
					}
				}
				if (found == false) {
					if (i == tokens.length - 1) {
						// We ended with an unknown directory/file
						searchR.endsWithFile = true;
						searchR.lastToken = tokens[i];
					}
					else {
						//UhOh, our path is wrong!
						searchR.validPath = false;
					}
				}
			}
		}
		searchR.lastFoundDir = cDir;
		return searchR;
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
	public TerminalError clear(Command command) {
		output.setText(""); // Special for clear, do not change to sendOutput()
		return null;
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
	public TerminalError mv(Command command) {
		if (command.getInputs().size() < 2) {
			return new TerminalError("Not enough arguments.\n");
		}
		String fileName = command.getInputs().get(0);
		String path = command.getInputs().get(1);

		// locating specified file (could be null if no such file)
		File file = findFile(fileName, currentDirectory);

		// checking for existence of specified file
		if (file == null) {
			return new TerminalError("File " + fileName + " not found.\n");
		}

		// deleting file from current location (file still exists because of
		// "file" variable)
		currentDirectory.delFile(file);

		// locate final directory destination
		SearchResults results = validateFilePath(path);

		// check for a valid destination; if not, add the file back to the
		// current directory
		if (results.validPath == false) {
			currentDirectory.addFile(file);
			return new TerminalError("Invalid file path!\n");
		}

		// mv can be used to rename, this takes care of that
		String newFileName = results.lastToken;

		// this for-each loop looks for a directory with the same name as the
		// file and overwrites it with the file if so,
		// just like actual Linux
		for (Directory d : results.lastFoundDir.getSubDirs()) {
			if (d.name().equals(newFileName)) {
				results.lastFoundDir.remDir(d);
				break;
			}
		}

		// this updates the file's name and adds it to the appropriate directory
		file.setName(newFileName);
		results.lastFoundDir.addFile(file);
		
		input.setText("");
		
		return null;
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
	public TerminalError cp(Command command) {
		if (command.getInputs().size() < 2) {
			return new TerminalError("Not enough Arguments.\n");
		}
		String fileName = command.getInputs().get(0);
		String path = command.getInputs().get(1);
		
		File f = findFile(fileName, currentDirectory);
		if (f == null) {
			return new TerminalError("File " + f + " not found!\n");
		}
		SearchResults results = validateFilePath(path);
		
		if (results.lastFoundDir == null) {
			return new TerminalError("Invalid path!\n");
		}
		
		String name = f.getName();
		
		//filename specified
		if(results.endsWithFile) {
			name = results.lastToken;
		}
		
		results.lastFoundDir.addFile(new File(name, f.getContents()));
		input.setText("");
		sendOutput("File " + name + " successfully copied to "
				+ results.lastFoundDir.name() + "\n");
		
		return null;
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
	public TerminalError nano(Command command) {
		if (command.getInputs().size() < 1) {
			return new TerminalError("Not Enough Arguments.\n");
		}
		
		String fileName = command.getInputs().get(0);
		
		File f = findFile(fileName, currentDirectory);
		if (f == null) {
			return new TerminalError("File " + f + " not found!\n");
		}
		
		input.setText("");
		output.setText(f.getContents());
		input.setEditable(false);
		output.setEditable(true);
		output.requestFocus();
		buttons.setVisible(true);
		nanoFile = f;
		
		return null;
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
	public TerminalError pwd(Command command) {
		String path = currentDirectory.getPath();
		
		sendOutput(path + "\n");
		
		return null;
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
	public TerminalError ls(Command command) {		
		if (currentDirectory == null) {
			System.out.println("NO CURRENT DIRECTORY!");
		}
		for (Directory dir : currentDirectory.getSubDirs()) {
			sendOutput(dir.name() + "\n");
		}
		for (File file : currentDirectory.getFiles()) {
			if (command.getFlags().size() == 1 && command.getFlags().get(0).equals("-a")) {
				sendOutput(file.getName() + "\n");
			} else {
				if (file.getName().charAt(0) != '.') {
					sendOutput(file.getName() + "\n");
				}
			}
		}
		return null;
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
	public TerminalError cd(Command command) {
		if (command.getInputs().size() < 1) {
			return new TerminalError("Not enough Arguments.\n");
		}
		String path = command.getInputs().get(0);
		SearchResults results = validateFilePath(path);
		if (results.validPath) {
			setCurrentDirectory(results.lastFoundDir);
		}
		else {
			return new TerminalError("Invalid File Path");
		}
		return null;
	} // end method

	
	//-----------------------------------------------------------
	// Command: cat
			// Description: Outputs a text file to the screen
		// Input
			// Required: 0=Path
			// Optional: None
		// Flags
			// None
	//-----------------------------------------------------------
	//[TODO] This does not currently use file paths correctly
	public TerminalError cat(Command command) {
		if (command.getInputs().size() < 1) {
			return new TerminalError("Not enough Arguments.\n");
		}
		String fileName = command.getInputs().get(0);
		
		File file = findFile(fileName, currentDirectory);
		if (file == null) {
			return new TerminalError("No such file.\n");
		}
		sendOutput(file.getContents() + "\n");
		
		return null;
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
	public TerminalError exit() {
		System.exit(0);
		return null;
	}

	// End block of Command Methods
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
}
