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
	Level1 lv;
	Level2 lv2;
	Level3 lv3;
	Level4 lv4;
	Level5 lv5;
	boolean sshActive = false;
	int currentLevel;
	DimensionX dimension = new DimensionX();
	Graphics g;
	Directory preservation;

	public CommandStream(JTextField input, JTextArea output, Directory cd,
			Directory root, JPanel buttons, String lv1, JTextPane graphicsTextOutput, JLabel graphics, int x, int y) {
		this.input = input;
		this.output = output;
		setCurrentDirectory(cd);
		this.root = root;
		this.buttons = buttons;
		nanoFile = null;
		prevDir = null;
		lv = new Level1(graphicsTextOutput);
		currentDirectory = lv.buildLevel(root);
		lv.setLocation(currentDirectory);
		lv.playLevel1(new Command());
		currentLevel = 1;
		g = new Graphics(graphics, x, y);
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
		ArrayList<Command> commands = Command.GenerateCommands(text);
		TerminalError error = null;
		//Loop through all commands found and do them in order!
		for(int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			sendOutput("> " + command.getSimpleString() + "\n");
			if(command.isErrored()) {
				invalid(input, output);
				return;
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
			} else if (command.getCommand().equals("head")) {
				error = headTail(command, 1);
			} else if (command.getCommand().equals("tail")) {
				error = headTail(command, -1);
			} else if (command.getCommand().equals("mkdir")) {
				error = mkdir(command);
			} else if (command.getCommand().equals("chmod")) {
				error = chmod(command);
			} else if (command.getCommand().equals("ssh")) {
				error = ssh(command);
			}
			
			//Handle Errors!
			if(error != null) {
				//Output Error!
				sendOutput(error.getString());
			}
			
			try {
				g.updateGraphics(command, currentDirectory);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			
			if(commands != null) {
				if(currentLevel == 1) {
					if(lv.playLevel1(command)) {
						lv2 = new Level2();
						currentLevel = 2;
					}
				} else if(currentLevel == 2) {
					//if(lv2.playLevel2(command)) {
						//lv3 = new Level3();
						//currentLevel = 3;
					//}
				}
			}
		} //End for loop!
		
		if(text.equals("")) {
			if(currentLevel == 1) {
				if(lv.playLevel1(new Command())) {
					lv2 = new Level2();
					currentLevel = 2;
				}
			}
		}
		input.setText("");
	}

	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Block of Helper Methods
	public void invalid(JTextField input, JTextArea output) {
		output.append(input.getText() + " is not a valid command.\n");
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
					cDir = cDir.parent;
				}
			} else if(("-").equals(tokens[i])) {
				if(prevDir == null) {
					output.append("No previous directory available.");
				} else {
					//Directory temp = currentDirectory;
					cDir = prevDir;
					prevDir = currentDirectory;
				}
			} else {
				boolean found = false;
				for(Directory d : cDir.getSubDirs()) {
					if(d.name().equals(tokens[i])) {
						prevDir = cDir;
						cDir = d;
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
			
			//verifying given path
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
			
			//checking for file; if no such file, create it
			File file = findFile(fileName, currentDirectory);
			if (file == null) {
				file = new File(fileName, "");
				currentDirectory.addFile(file);
			}
			
			//turn the output pane into an editor
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
			
			
			//checking for the -a flag; if found, list all files including .* files
			if(command.getFlags().size() == 1 && command.getFlags().get(0).equals("-a")) {
				for(Directory dir : currentDirectory.getSubDirs()) {
					sendOutput(dir.name() + "\n");
				}
				
				for(File f : currentDirectory.getFiles()) {
					sendOutput(f.getName() + "\n");
				}
				
				return null;
				
			//checking for the -l flag; -l lists the permissions for the 'other' group for all
			//files and directories in the current working directory
			} else if(command.getFlags().size() > 0 && command.getFlags().get(0).equals("-l")) {
				if(command.getInputs().size() == 1) {
					String arg = command.getInputs().get(0);
					File f = findFile(arg, currentDirectory);
					if(f == null) {
						SearchResults r = validateFilePath(arg);
						Directory d = r.lastFoundDir;
						if(d == null) {
							return new TerminalError(arg + " not found!\n");
						} else {
							String output = "";
							output += "d";
							output += d.otherReadPerm();
							output += d.otherWritePerm();
							output += d.otherExecutePerm();
							sendOutput(output + "\n");
							return null;
							
						}
					} else {
						String output = "";
						output += "-";
						output += f.otherReadPerm();
						output += f.otherWritePerm();
						output += f.otherExecutePerm();
						sendOutput(output + "\n");
						return null;
					}
				}

				String output = "";
				
				for(File f : currentDirectory.getFiles()) {
					output += "-";
					output += f.otherReadPerm();
					output += f.otherWritePerm();
					output += f.otherExecutePerm();
					output += "      ";
					output += f.getName() + "\n";
				}
				sendOutput(output);
				output = "";
				for(Directory d : currentDirectory.getSubDirs()) {
					output += "d";
					output += d.otherReadPerm();
					output += d.otherWritePerm();
					output += d.otherExecutePerm();
					output += "      ";
					output += d.name() + "\n";
				}
				sendOutput(output);
				return null;
			}
			
			//no flags, normal ls
			for (Directory dir : currentDirectory.getSubDirs()) {
				if (dir.name().charAt(0) != '.') {
					sendOutput(dir.name() + "\n");
				}
			}
			
			for (File file : currentDirectory.getFiles()) {
				if (file.getName().charAt(0) != '.') {
					sendOutput(file.getName() + "\n");
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
			if(!sshActive) {
				System.exit(0);
				return new TerminalError("Closing Console!");
			} else {
				sshActive = false;
				currentDirectory = preservation;
				preservation = null;
			}
			return null;
		}//End Method
		
		//-----------------------------------------------------------
		// Command: head
				// Description: Prints the first 10 lines of a file to the console
			// Input
				// Required: something
				// Optional: none
			// Flags
				// None
		// ----------------------------------------------------------
		public TerminalError headTail(Command comm, int arg) {
			
			//checking for a valid number of arguments
			if(comm.getInputs().size() != 1) {
				return new TerminalError("One argument required!\n");
			}
			
			//locate specified file
			File f = findFile(comm.getInputs().get(0), currentDirectory);
			
			String[] text = f.getContents().split("\n");
			
			//determining whether head or tail was entered
			if(arg < 0) {
				//looping through the last 10 lines of the file if they exist, terminating early if not
				for(int i = (Math.max(text.length - 10, 0)); i < text.length; i++) {
					output.append(text[i] + "\n");
				}
			} else {
				//looping throught the first 10 lines of the file if they exist, terminating early if not
				for(int i = 0; i < (Math.min(10, text.length)); i++) {
					output.append(text[i] + "\n");
				}
			}
			
			return null;
		}
		
		/**
		 * I'm so sorry for what follows this comment.  When you can't understand what the heck I've done here, ask me and hopefully
		 * I'll be able to remember and tell you.
		 * 
		 */
		public TerminalError chmod(Command cmd) {
			if(cmd.getInputs().size() < 2) {
				return new TerminalError("Not enough arguments.\n");
			}

			String permissions = cmd.getInputs().get(1);
			
			String file = cmd.getInputs().get(0);
			Object f = findFile(file, currentDirectory);
			
			for(Directory d : currentDirectory.getSubDirs()) {
				if(d.name().equals(file)) {
					f = d;
					break;
				}
			}
			
			if(f == null) {
				return new TerminalError(f + " not found!\n");
			}
			
			if(permissions.equals("")) {
				return new TerminalError("Invalid permissions argument.\n");
			}
			// parse permissions string
			String identifiers = "";
			String accessModifiers = "";
			String setModifier = "";
			int aCount = 0;
			int oCount = 0;
			int setModifierCount = 0;
			int wCount = 0;
			int rCount = 0;
			int xCount = 0;
			char[] perms = permissions.toCharArray();
			String next = "";
			for(int i = 0; i < perms.length; i++) {
				
				next = perms[i] + "";
				
				if(next.equals("a") && aCount == 0) {
					aCount++;
					identifiers += "a";
				} else if(next.equals("o") && oCount == 0) {
					oCount++;
					identifiers += "o";
				} else if(next.equals("+") && setModifierCount == 0) {
					setModifier = "+";
					setModifierCount++;
				} else if(next.equals("-") && setModifierCount == 0) {
					setModifier = "-";
					setModifierCount++;
				} else if(next.equals("=") && setModifierCount == 0) {
					setModifier = "=";
					setModifierCount++;
				} else if(next.equals("w") && wCount == 0) {
					accessModifiers += "w";
					wCount++;
				} else if(next.equals("r") && rCount == 0) {
					accessModifiers += "r";
					rCount++;
				} else if(next.equals("x") && xCount == 0) {
					accessModifiers += "x";
					xCount++;
				} else {
					if(aCount > 1 || oCount > 1) {
						return new TerminalError("Each identifier can only appear once.\n");
					} else if(setModifierCount != 1) {
						return new TerminalError("Exactly one permissions modifier required.\n");
					} else if(wCount > 1 || rCount > 1 || xCount > 1) {
						return new TerminalError("Access modifiers can only appear once.\n");
					}
				}
				
			}
			
			if(identifiers.contains("a")) {
				if(setModifier.equals("+")) {
					if(accessModifiers.contains("w")) {
						if(f instanceof File) {
							((File)f).setAllWrite("w");
							((File)f).setOtherWrite("w");
						} else {
							((Directory)f).setAllWrite("w");
							((Directory)f).setOtherWrite("w");
						}
					}
					if(accessModifiers.contains("r")) {
						if(f instanceof File) {
							((File)f).setAllRead("r");
							((File)f).setOtherRead("r");
						} else {
							((Directory)f).setAllRead("r");
							((Directory)f).setOtherRead("r");
						}
					}
					if(accessModifiers.contains("x")) {
						if(f instanceof File) {
							((File)f).setAllExecute("x");
							((File)f).setOtherExecute("x");
						} else {
							((Directory)f).setAllExecute("x");
							((Directory)f).setOtherExecute("x");
						}
					}
				} else if(setModifier.equals("-")) {
					if(accessModifiers.contains("w")) {
						if(f instanceof File) {
							((File)f).setAllWrite("-");
							((File)f).setOtherWrite("-");
						} else {
							((Directory)f).setAllWrite("-");
							((Directory)f).setOtherWrite("-");
						}
					}
					if(accessModifiers.contains("r")) {
						if(f instanceof File) {
							((File)f).setAllRead("-");
							((File)f).setOtherRead("-");
						} else {
							((Directory)f).setAllRead("-");
							((Directory)f).setOtherRead("-");
						}
					}
					if(accessModifiers.contains("x")) {
						if(f instanceof File) {
							((File)f).setAllExecute("-");
							((File)f).setOtherExecute("-");
						} else {
							((Directory)f).setAllExecute("-");
							((Directory)f).setOtherExecute("-");
						}
					}
				} else if(setModifier.equals("=")) {
					if(accessModifiers.contains("w")) {
						if(f instanceof File) {
							((File)f).setAllWrite("w");
							((File)f).setOtherWrite("w");
						} else {
							((Directory)f).setAllWrite("w");
							((Directory)f).setOtherWrite("w");
						}
					} else {
						if(f instanceof File) {
							((File)f).setAllWrite("-");
							((File)f).setOtherWrite("-");
						} else {
							((Directory)f).setAllWrite("-");
							((Directory)f).setOtherWrite("-");
						}
					}
					if(accessModifiers.contains("r")) {
						if(f instanceof File) {
							((File)f).setAllRead("r");
							((File)f).setOtherRead("r");
						} else {
							((Directory)f).setAllRead("r");
							((Directory)f).setOtherRead("r");
						}
					} else {
						if(f instanceof File) {
							((File)f).setAllRead("-");
							((File)f).setOtherRead("-");
						} else {
							((Directory)f).setAllRead("-");
							((Directory)f).setOtherRead("-");
						}
					}
					if(accessModifiers.contains("x")) {
						if(f instanceof File) {
							((File)f).setAllExecute("x");
							((File)f).setOtherExecute("x");
						} else {
							((Directory)f).setAllExecute("x");
							((Directory)f).setOtherExecute("x");
						}
					} else {
						if(f instanceof File) {
							((File)f).setAllExecute("-");
							((File)f).setOtherExecute("-");
						} else {
							((Directory)f).setAllExecute("-");
							((Directory)f).setOtherExecute("-");
						}
					}
				}
			} else if(identifiers.equals("o")) {
				if(setModifier.equals("+")) {
					if(accessModifiers.contains("w")) {
						if(f instanceof File) {
							((File)f).setOtherWrite("w");
						} else {
							((Directory)f).setOtherWrite("w");
						}
					}
					if(accessModifiers.contains("r")) {
						if(f instanceof File) {
							((File)f).setOtherRead("r");
						} else {
							((Directory)f).setOtherRead("r");
						}
					}
					if(accessModifiers.contains("x")) {
						if(f instanceof File) {
							((File)f).setOtherExecute("x");
						} else {
							((Directory)f).setOtherExecute("x");
						}
					}
				} else if(setModifier.equals("=")) {
					if(accessModifiers.contains("w")) {
						if(f instanceof File) {
							((File)f).setOtherWrite("w");
						} else {
							((Directory)f).setOtherWrite("w");
						}
					} else {
						if(f instanceof File) {
							((File)f).setOtherWrite("-");
						} else {
							((Directory)f).setOtherWrite("-");
						}
					}
					if(accessModifiers.contains("r")) {
						if(f instanceof File) {
							((File)f).setOtherRead("r");
						} else {
							((Directory)f).setOtherRead("r");
						}
					} else {
						if(f instanceof File) {
							((File)f).setOtherRead("-");
						} else {
							((Directory)f).setOtherRead("-");
						}
					}
					if(accessModifiers.contains("x")) {
						if(f instanceof File) {
							((File)f).setOtherExecute("x");
						} else {
							((Directory)f).setOtherExecute("x");
						}
					} else {
						if(f instanceof File) {
							((File)f).setOtherExecute("-");
						} else {
							((Directory)f).setOtherExecute("-");
						}
					}
				} else if(setModifier.equals("-")) {
					if(accessModifiers.contains("w")) {
						if(f instanceof File) {
							((File)f).setOtherWrite("-");
						} else {
							((Directory)f).setOtherWrite("-");
						}
					}
					if(accessModifiers.contains("r")) {
						if(f instanceof File) {
							((File)f).setOtherRead("-");
						} else {
							((Directory)f).setOtherRead("-");
						}
					}
					if(accessModifiers.contains("x")) {
						if(f instanceof File) {
							((File)f).setOtherExecute("-");
						} else {
							((Directory)f).setOtherExecute("-");
						}
					}
				}
			}
			
			return null;
		}
		
		/**
		 * ssh transfers the current working directory to a new simulated system called 'DimensionX'
		 * 
		 * @param comm Last command entered.  Doesn't actually do anything in this method.
		 * @return null; no terminal error should ever happen given that ssh cannot fail.
		 */
		public TerminalError ssh(Command comm) {
			sshActive = true;
			preservation = currentDirectory;
			
			DimensionX d = new DimensionX();
			currentDirectory = d.buildDimX();
			return null;
		}
		
		public TerminalError mkdir(Command comm) {
			
			//checking for appropriate number of inputs
			if(comm.getInputs().size() != 1) {
				return new TerminalError("Exactly 1 argument required!");
			}
			
			String dirName = comm.getInputs().get(0);
			
			//creating and adding the new directory to the current working directory
			Directory newDir = new Directory(dirName, currentDirectory, new ArrayList<Directory>(), new ArrayList<File>());
			currentDirectory.addDirectory(newDir);
			
			return null;
		}

		// End block of Command Methods
		// ----------------------------------------------------------------------------------------------------------------------------------------------------
		
}
