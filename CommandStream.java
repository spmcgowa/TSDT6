import java.awt.event.*;

import javax.swing.*;

import java.util.*;

public class CommandStream implements ActionListener {

	JTextField input;
	JTextArea output;
	Scanner scan;
	Directory currentDirectory;
	JPanel buttons;
	File nanoFile;
	Directory prevDir;
	final Directory root;
	String step = "step0";
	Level1 lv;
	boolean storyReadyToAdvance = true;

	public CommandStream(JTextField input, JTextArea output, Directory cd,
			Directory root, JPanel buttons, String lv1, JTextPane graphicsTextOutput) {
		this.input = input;
		this.output = output;
		setCurrentDirectory(cd);
		this.root = root;
		this.buttons = buttons;
		nanoFile = null;
		prevDir = null;
		lv = new Level1(step, graphicsTextOutput);
		currentDirectory = lv.buildLevel(root);
		lv.setLocation(currentDirectory);
		//lv.playLevel1(step);
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

		String text = input.getText();
		
		//-----------------------------------------------------------------
		//Dev Mode
				
		Scanner temp = new Scanner(text);
		
		if(temp.hasNext()) {
			if(temp.next().equals("setstage")) {
				lv.devMode(Integer.parseInt(temp.next()));
			}
		}		
		temp.close();
				
		//-----------------------------------------------------------------
				
		
		// this retrieves the text from the input field
		input.setText("");
		ArrayList<Command> commands = Command.GenerateCommands(text);
		TerminalError error = null;
		//Loop through all commands found and do them in order!
		for(int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			sendOutput("> " + command.getSimpleString() + "\n");
			if(command.isErrored()) {
				invalid(input, output);
			}
			
			if (command.getCommand().equals("mv")) {
				error = mv(command);
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
			if(error != null) {
				//Output Error!
				sendOutput(error.getString());
			}
			
			if(commands != null) {
				if(lv.playLevel1(command)) {
					//build level 2
				}
			}
		} //End for loop!
		
		/*
		//TODO: examine to see if this will propely advance gameplay
		if(commands != null) {
			lv.playLevel1(text);
		}
		*/
		if(text.equals("")) {
			if(lv.playLevel1(new Command())) {
				//build level 2
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
		for(File f : currentDirectory.getFiles()) {
			if(f.getName().equals(name)) {
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
		if(dir == null) {
			throw new NullPointerException();
		}
		currentDirectory = dir;
	}
	
	public SearchResults validateFilePath(String path) {
		//We know the current directory, it is part of the command stream
		String[] tokens = path.split("/");
		SearchResults searchR = new SearchResults(); //Create our class to stuff information into!
		Directory cDir = currentDirectory;
		for(int i = 0; i < tokens.length; i++) {
			
			if(("").equals(tokens[i]) || root.name().equals(tokens[i])) {
				continue;
			} else if(i == 0 && (".").equals(tokens[i])) {
				cDir = root;
			} else if(("..").equals(tokens[i])) {
				if(cDir != root) {
					if(cDir.parent == null) {
						throw new NullPointerException("Directory has no parent!");
					} else if(cDir == cDir.parent) {
						throw new IllegalArgumentException("Directory is its own parent!?");
					}
					prevDir = cDir;
					currentDirectory = cDir.parent;
					cDir = currentDirectory;
				}
			} else if(("-").equals(tokens[i])) {
				if(prevDir == null) {
					output.append("No previous directory available.");
				} else {
					Directory temp = currentDirectory;
					currentDirectory = prevDir;
					prevDir = temp;
					cDir = currentDirectory;
				}
			} else {
				boolean found = false;
				for(Directory d : cDir.getSubDirs()) {
					if(d.name().equals(tokens[i])) {
						prevDir = cDir;
						currentDirectory = d;
						//searchR.lastFoundDir = d;
						cDir = d;
						found = true;
						break;  //THIS BREAKS THE FOR LOOP! FRRRREEEEEDOM!
					}
				}
				if(found == false) {
					if(i == tokens.length - 1) {
						//Weended with an unknown directory/file
						searchR.endsWithFile = true;
						searchR.lastToken = tokens[i];
						break;
					} else {
						//Uh-oh, our path is wrong!
						searchR.validPath = false;
						break;
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
	// ----------------------------------------------------------------------------------------------------------------------
	
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Methods to execute Commands with the Command Class
	
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
		}//End Method
		
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
			String newFileName = fileName;
			if (results.endsWithFile) newFileName = results.lastToken;
			
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
		}//End Method
		
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
		}//End Method
		
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
			
			File file = findFile(fileName, currentDirectory);
			if (file == null) {
				file = new File(fileName, "");
				currentDirectory.addFile(file);
			}
			
			input.setText("");
			output.setText(file.getContents());
			input.setEditable(false);
			output.setEditable(true);
			output.requestFocus();
			buttons.setVisible(true);
			nanoFile = file;
			
			return null;
		}//End Method
		
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
		}//End Method
		
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
		}//End Method
		
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
			if (results.validPath == true) {
				if (results.endsWithFile) {
					return new TerminalError(results.lastToken + " is not a valid directory.");
				}
				setCurrentDirectory(results.lastFoundDir);
			}
			else {
				return new TerminalError("Invalid File Path");
			}
			lv.setLocation(currentDirectory);
			return null;
		} //End Method
		
		//-----------------------------------------------------------
		// Command: cat
				// Description: Outputs a text file to the screen
			// Input
				// Required: 0=Path
				// Optional: None
			// Flags
				// None
		//-----------------------------------------------------------
		public TerminalError cat(Command command) {
			if (command.getInputs().size() < 1) {
				return new TerminalError("Not enough Arguments.\n");
			}
			
			String filePath = command.getInputs().get(0);
			SearchResults results = validateFilePath(filePath);
			
			if (results.validPath == false) {
				return new TerminalError("Invalid File Path.");
			}
			
			File file = findFile(results.lastToken, results.lastFoundDir);		
			if (results.endsWithFile == false) {
				return new TerminalError( results.lastToken + " is a Directory.");
			}
			
			if (file == null) {
				return new TerminalError("No such file.\n");
			}
			sendOutput(file.getContents() + "\n");
			
			return null;
		} //End Method
		
		//-----------------------------------------------------------
		// Command: exit
				// Description: Exits the game! 
			// Input
				// Required: None
				// Optional: None
			// Flags
				// None
		//-----------------------------------------------------------
		//[TODO] Probably should confirm with player since this is a game? Or trigger a save?
		public TerminalError exit() {
			System.exit(0);
			return new TerminalError("Closing Console!");
		}//End Method

		// End block of Command Methods
		// ----------------------------------------------------------------------------------------------------------------------------------------------------
}
