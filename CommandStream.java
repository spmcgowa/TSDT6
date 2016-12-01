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
			} else if (command.getCommand().equals("find")) {
				error = find(command);
			} else if (command.getCommand().equals("rm")) {
				error = rm(command);
			} else if (command.getCommand().equals("tar")) {
				error = tar(command);
			} else if (command.getCommand().equals("zip")) {
				error = zip(command);
			} else if (command.getCommand().equals("unzip")) {
				error = unzip(command);
			} else {
				error = new TerminalError("Command was read as valid, but the CommandStream did not recognize it.");
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
					output.append("No previous directory available.\n");
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
	
	public boolean compareSearchResults(SearchResults a, SearchResults b) {
		//Begin comparing
		if (a.lastFoundDir.equals(b.lastFoundDir)) {
			//They share the exact same result directory.
			if (a.endsWithFile != b.endsWithFile) {
				return false;
			}
			else if (a.endsWithFile) {
				// the case that both are files
				File f1 = findFile(a.lastToken,a.lastFoundDir);
				File f2 = findFile(b.lastToken,b.lastFoundDir);
				if (f1.equals(f2)) {
					return true;
				}
			}
			else {
				//the case that both are directories, we already checked if they are equal to get this far
				return true;
			}
		}
		
		return false;
	}
	
	public void sendOutput(String out) {
		output.append(out);
	}
	
	protected void setStartingDir(Directory d) {
		currentDirectory = d;
	}
	
	public boolean compareWithWildcards(String a, String b) {
		return a.matches(b.replaceAll("\\*",".*")) || b.matches(a.replaceAll("\\*",".*"));
	}
	
	//End block of Helper Methods
	// ----------------------------------------------------------------------------------------------------------------------
	
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Methods to execute Commands with the Command Class

	//----------------------------------------------------------
		// Command: zip
				// Description: bundles a group of files and directories, basically a simplified version of tar in our setup
			// Input
				// Required: bundled name, at least one file to add
				// Optional: None
			// Flags
				// [TODO] fill this in
		//-----------------------------------------------------------
	public TerminalError zip(Command command) {
		if (command.getInputs().size() < 2) {
			return new TerminalError("Not enough arguments.\n");
		}
		String ret = "tar -czf ";
		for (String s : command.getInputs()) {
			ret += s;
			ret += " ";
		}
		System.out.println("Converted zip command to: " + ret);
		return tar(Command.GenerateCommands(ret).get(0));
	}
	
	public TerminalError unzip(Command command) {
		if (command.getInputs().size() < 1) {
			return new TerminalError("Not enough arguments.\n");
		}
		String ret = "tar -xf ";
		for (String s : command.getInputs()) {
			ret += s;
			ret += " ";
		}
		System.out.println("Converted unzip command to: " + ret);
		return tar(Command.GenerateCommands(ret).get(0));
	}
	
	
	
	
	//----------------------------------------------------------
	// Command: tar
			// Description: bundles a group of files and directories
		// Input
			// Required: bundled name, at least one file to add
			// Optional: None
		// Flags
			// [TODO] fill this in
	//-----------------------------------------------------------
	public TerminalError tar(Command command) {
		if (command.getInputs().size() < 1) {
			return new TerminalError("Not enough arguments.\n");
		}
		
		String flags = "";
		//Cram all of these flags together
		for (int i = 0; i < command.getFlags().size(); i++) {
			flags += command.getFlags().get(i);
		}
		/*
		-c Create a tar // Exclusive with x
		-x Extract tar Contents // Exclusive with c

		-f Display what is going on while running the command

		-t Display a finished tarball's contents
		-v Display Verbose information for a tarball's contents 

		-z Create a gzip file // Useless for our thing, but needs to be accepted
		-j create a bzip file // useless for our thing, but needs to be accepted as valid
		
		7 Possible flags
		*/
										//c		x		f		t		v	z		j
		boolean[] bools = new boolean[] {false, false, false, false, false, false, false};
		
		for (int i = 0; i < flags.length(); i++) {
			if (flags.charAt(i) == 'c') {
				if (bools[1] == true || bools[3] == true || bools[4] == true)
					return new TerminalError("You can only have one type of modification flag {c,x,t,v}.\n");
				bools[0] = true;
			}
			else if (flags.charAt(i) == 'x') {
				if (bools[0] == true || bools[3] == true || bools[4] == true)
					return new TerminalError("You can only have one type of modification flag {c,x,t,v}.\n");
				bools[1] = true;
			}
			else if (flags.charAt(i) == 'f') {
				bools[2] = true;
			}
			else if (flags.charAt(i) == 't') {
				if (bools[0] == true || bools[1] == true || bools[4] == true)
					return new TerminalError("You can only have one type of modification flag {c,x,t,v}.\n");
				bools[3] = true;
			}
			else if (flags.charAt(i) == 'v') {
				bools[4] = true;
			}
			else if (flags.charAt(i) == 'z') {
				if (bools[6] == true)
					return new TerminalError("You can only have one type of modification flag {c,x,t,v}.\n");
				bools[5] = true;
			}
			else if (flags.charAt(i) == 'j') {
				if (bools[5] == true)
					return new TerminalError("Invalid flags.");
				bools[6] = true;
			}
			
			
		}
		//compile how much information are we supposed to output, this will either be 0 for non, 1 for basic, 2 for verbose
		int outputLevel = 0 + ( (bools[3] || bools[2]) ? 1 : 0) + (( (bools[4]) ? 2 : 0));
		System.out.println("Decided to output at level " + outputLevel);
		//Now do the right thing
		if (bools[0] == true) {
			System.out.println("Bundling");
			bundleTar(command, outputLevel);
		}
		else if (bools[1] == true)  {
			System.out.println("Unbundling");
			unbundleTar(command, outputLevel);
		}
		else if (bools[3] == true) {
			System.out.println("Displaying");
			//Print a tarball's contents, check t of v for level of output
			String path = command.getInputs().get(0);
			SearchResults results = validateFilePath(path);
			if (results.validPath)
				return new TerminalError("Invalid file path.\n");
			
			File file = findFile(results.lastToken,results.lastFoundDir);
			if (file == null) 
				return new TerminalError("Invalid file path.\n");
			if (file instanceof TarBall == false)
				return new TerminalError("Not a tar/zip file.\n");
			outputBundle(((TarBall) file).getItems(), outputLevel);
		}
		else {
			return  new TerminalError("Invalid flags.");
		}
		
		return null;
	}
	
	private void outputBundle(ArrayList<Object> items, int outputLevel) {
		if (outputLevel == 0) return;
		System.out.println("Attemtping to output bundle, item count " + items.size());
		if (outputLevel == 1) {
			for (Object o : items) {
				if (o instanceof File) {
					sendOutput( ((File)o).getName() + "\n");
				}
				else {
					sendOutput( ((Directory)o).name() + "\n");
				}
			}
		}
		else if (outputLevel > 1) {
			//[TODO] do something here?
		}
	}
	
	private TerminalError unbundleTar(Command command, int outputLevel) {
		//analyze the path
		String path = command.getInputs().get(0);
		SearchResults results = validateFilePath(path);
		//Check if path is valid
		if (results.endsWithFile == false)
			return new TerminalError("Invalid file path.\n");
		File file = findFile(results.lastToken,results.lastFoundDir);
		//Check if file is valid
		if (file == null)
			return new TerminalError("Not a valid file.\n");
		// check if this is actually a bundle file
		if (file instanceof TarBall == false)
			return new TerminalError("Not a tar/zip file.\n");
		TarBall bundle = (TarBall) file;
		outputBundle(bundle.getItems(),outputLevel);
		//Unpack everything
		System.out.println("Items to unbundle: " + bundle.getItems().size());
		for (int i = 0; i < bundle.getItems().size(); i++) {
			if (bundle.getItems().get(i) instanceof File) {
				File f = (File)bundle.getItems().get(i);
				currentDirectory.addFile(f);
			}
			else {
				Directory d = (Directory)bundle.getItems().get(i);
				currentDirectory.addDirectory(d);
			}
		}
		return null;
	}
	
	
	private TerminalError bundleTar(Command command, int outputLevel) {
		//The path for our new file
		String outputPath = command.getInputs().get(0);
		SearchResults outputSearch = validateFilePath(outputPath);
		
		if (outputSearch.validPath == false)
			return new TerminalError("Invalid output path.");
		
		//We need to prep the inputs a little, because we can name both a file and that files parent directory,
		//but we only really need to include the directory and we do not want to add a directory first;
		ArrayList<String> prepInputs = command.getInputs();
		prepInputs.remove(0); //remove the output path that is included in the command inputs
		
		for (int i = prepInputs.size() - 1; i >= 0 ; i--) {
			//Get the path for the item
			String itemPath = command.getInputs().get(i);
			SearchResults itemSearch = validateFilePath(itemPath);
			if (itemSearch.validPath == false)
				return new TerminalError("Invalid file path.");
			//First check for duplicates
			//Loop through all other inputs, check if they match the current one and remove it if they do 
			for (int j = prepInputs.size() - 1; j >= 0 ; j--) {
				if (i==j) continue; // Dont delete the original thing we were checking
				
				//Prep the second items information
				String itemPath2 = command.getInputs().get(j);
				SearchResults itemSearch2 = validateFilePath(itemPath2);
				if (itemSearch2.validPath == false)
					return new TerminalError("Invalid file path.");
				
				//Compare them
				if (compareSearchResults(itemSearch,itemSearch2) == true) {
					System.out.println("Pruned " + prepInputs.get(j) + " from the inputs.");
					prepInputs.remove(j);
				}
				
			}
				
			//Now as an additional step of prep, we need to run through all inputs and see if 
			//they are a higher level in the hierarchy and remove the lower levels
			//For example, if we want to bundle dir1 and dir1/file1, file1 is already getting included
			// at a higher point in the hierarchy so we want to remove it from the inputs to avoid errors.
			if (itemSearch.endsWithFile == false) { //do not need to check files
				String truePath1 = itemSearch.lastFoundDir.getPath();
				for (int j = prepInputs.size() - 1; j >= 0 ; j--) {
					if (i==j) continue; // Dont delete the original thing we were checking
					//Prep the second items information
					String itemPath2 = command.getInputs().get(j);
					SearchResults itemSearch2 = validateFilePath(itemPath2);
					if (itemSearch2.validPath == false)
						return new TerminalError("Invalid file path.");
					String truePath2 = itemSearch.lastFoundDir.getPath();
					//We add a wildcard to the end of the first file path, 
					//so if this is higher up the hierarchy they will be equal!
					if (compareWithWildcards(truePath1 + "*", truePath2)) {
						//This means we can remove the lower hierarchy one.
						prepInputs.remove(j);
					}
				}	
			}
		}
		System.out.println("Final file count: " + prepInputs.size());
		//the inputs should now be properly prepped to avoid issues. 
		
		//This array list will hold all the items we are putting into the bundle
		ArrayList<Object> items = new ArrayList<Object>();
		for (int i = 0; i < prepInputs.size(); i++) {
			//Get the path for the item
			String itemPath = command.getInputs().get(i);
			SearchResults itemSearch = validateFilePath(itemPath);
			if (itemSearch.validPath == false)
				return new TerminalError("Invalid file path.");
			if (itemSearch.endsWithFile) {
				File file = findFile(itemSearch.lastToken,itemSearch.lastFoundDir);
				if (file != null) {
					items.add(file);
				}
			}
			else {
				items.add(itemSearch.lastFoundDir);
			}
		}
		
		
		System.out.println("Items to bundle: " + items.size());
		TarBall bundle = new TarBall(outputSearch.lastToken,items);
		outputSearch.lastFoundDir.addFile(bundle);
		
		outputBundle(items, outputLevel);
		return null;
	}
	
	//-----------------------------------------------------------
	// Command: rm
			// Description: removes a file or directory
		// Input
			// Required: path to file/direcotory
			// Optional: None
		// Flags
			// -d -r
	//-----------------------------------------------------------
	public TerminalError rm(Command command) {
		if (command.getInputs().size() < 1) {
			return new TerminalError("Not enough arguments.\n");
		}
		
		String path = command.getInputs().get(0);
		SearchResults results = validateFilePath(path);
		
		if (results.validPath == false)
			return new TerminalError("Invalid Path.");
		
		if (results.endsWithFile == false) {
			boolean deleteDir = false; // Look for the two flags we are currently using
			boolean  deleteRecur = false; 
			for (int i = 0; i < command.getFlags().size();i++) 
				if (compareWithWildcards(command.getFlags().get(i),"-d")) 
					deleteDir = true;
			for (int i = 0; i < command.getFlags().size();i++) 
				if (compareWithWildcards(command.getFlags().get(i),"-r")) 
					deleteRecur = true;
			
			if (deleteDir == false) {
				return new TerminalError("rm: cannot remove ‘" + results.lastToken + "’: Is a directory. To remove a directory, use the -d flag.\n");
			}
			
			//So because this is java, a recursive delete and a non recursive delete work out the same
			// So we just need to check the one case when we do not want to delete this directory
			// which is when it is not empty and we are not deleting recursively
			if (results.lastFoundDir.isEmpty() == false && deleteRecur == false) {
				return new TerminalError("rm: cannot remove ‘" + results.lastToken + "’: Is not empty. Use -r flag to remove an non empty directory, it will delete all things in the directory.\n");
			}
			
			results.lastFoundDir.parent.remDir(results.lastFoundDir); // drop the directory
			
		}
		else {
			File file = findFile(results.lastToken, results.lastFoundDir);
			if (file != null) {
				results.lastFoundDir.delFile(file);
			}
			else {
				return new TerminalError("rm: cannot remove "+ results.lastToken + ": No such file or directory\n");
			}
		}
		
		return null; 
	}
	
	//-----------------------------------------------------------
		// Command: Find
				// Description: Finds directorys and files that fit the definitions, prints them to output
			// Input
				// Required: None
				// Optional: path
			// Flags
				// None: maxdepth (i), iname (str), name (str), type (d/f), ! (yes!no), not, -o
	//-----------------------------------------------------------
	public TerminalError find(Command command) {
		//Determine the path we are using, if it is input or if it is default
		String path = "";
		if (command.getInputs().size() > 0) {
			path = command.getInputs().get(0);
		}
		else {
			path = "";
		}
		
		SearchResults results = validateFilePath(path); // Get if the path is valid
		if (results.validPath == false) {
			return new TerminalError("Invalid file path!\n");
		}
		
		if (results.endsWithFile == true) { // If this is just one file we can skip alot of the hard work
			File endpoint = findFile(results.lastToken, results.lastFoundDir);
			if (endpoint != null) {
				sendOutput(results.lastFoundDir.getPath() + endpoint.getName() + "\n"); // Print out just this file
			}
		}
		
		int maxdepth = Integer.MAX_VALUE;
		char type = 'a'; // a for all
		@SuppressWarnings("unchecked") // So the compiler doesn't complain about this
		ArrayList<String> rules = (ArrayList<String>) command.getFlags().clone();
		//remove the settings that change the search it self, IE those that are not constraints.
		for (int i = 0; i < command.getFlags().size(); i++) {
			//First lets check for flags that modify the search itself, and are not constraints
			if (compareWithWildcards(command.getFlags().get(i),"-maxdepth*")) {
				maxdepth = new Integer(command.getFlags().get(i).substring("-maxdepth".length()));
				rules.remove(i); // Remove these from the rules
			}
			else if (compareWithWildcards(command.getFlags().get(i),"-type*")) {
				type = command.getFlags().get(i).charAt(5);
				rules.remove(i);
				if (type != 'f' && type != 'd') return new TerminalError("Invalid type.\n");
			}
		}
		//Check things recursively for those rules.
		ArrayList<Directory> dirs = new ArrayList<Directory>();
		dirs.add(results.lastFoundDir);
		int cdepth = 0;
		Directory depthTracker = dirs.get(0);
		while (dirs.isEmpty() == false) {
			for (Directory d : dirs.get(0).subDirectories) {
				if (depthTracker != d.parent) {
					cdepth++;
					depthTracker = d; // This should work, because this is the first directory we will hit.
				}
				if (cdepth > maxdepth) {
					break; // Exit!
				}
				dirs.add(d);
			}
			//Print this dir if it is valid, and type is a/d;
			if (type != 'f') {
				//Check the rules!
				if (checkWithRules(dirs.get(0).name(), rules)) {
					sendOutput(dirs.get(0).getPath()+"\n");
				}
			}
			for (File f : dirs.get(0).files) {
				//Print the file if it is valid, and type is a/f
				if (type != 'd') {
					//check the rules!
					if (checkWithRules(f.getName(),rules)) {
						sendOutput(dirs.get(0).getPath() + f.getName() +"\n");
					}
				}
			}
			dirs.remove(0);
		}
		
		return null; // Exit successfully
	}
	// iname (str), name (str), ! (yes!no), not, -o
	public boolean checkWithRules(String inputname, ArrayList<String> rules) {
		//ORder of operations, -o, !, not, name, iname
		boolean[] results = new boolean[rules.size()]; // tracks the result of the rule in this slot
		boolean[] checked = new boolean[rules.size()]; // Tracks if we have checked this rule yet
		for (int i = 0; i < results.length; i++) results[i] = true; // Set them all to true
		for (int i = 0; i < checked.length; i++) checked[i] = false; // Set them all to true
		
		int tooManyCheck = 10000;
		while (tooManyCheck-- > 0) {
			//Check if we have checked all the rules
			boolean checkedAll = true;
			for (int i = 0; i < checked.length; i++) if (checked[i] == false) checkedAll = false;
			if (checkedAll) break;
			
			//Find the next rule in the order of operations: -name -iname -not ! -o
			for (int i = 0; i < rules.size(); i++) {
				if (compareWithWildcards(rules.get(i),"-name*") == false) continue;
				if (checked[i]) continue; // dont check this rule again if we have checked it
				String name = rules.get(i).substring("-name".length()); // get just the name
				results[i] = compareWithWildcards(inputname, name);
				checked[i] = true; // we have checked this rule!
			}
			for (int i = 0; i < rules.size(); i++) {
				if (compareWithWildcards(rules.get(i),"-iname*") == false) continue;
				if (checked[i]) continue; // dont check this rule again if we have checked it
				String name = rules.get(i).substring("-iname".length()); // get just the name
				results[i] = compareWithWildcards(inputname.toLowerCase(), name.toLowerCase());
				checked[i] = true; // we have checked this rule!
			}
			for (int i = 0; i < rules.size(); i++) {
				if (compareWithWildcards(rules.get(i),"!")  == false && compareWithWildcards(rules.get(i),"-not") == false) continue;
				if (checked[i]) continue; // dont check this rule again if we have checked it
				
				if (i+1 >= rules.size()) return false;  //We need there to be a  next rule
				if (results[i+1] == false) { // We want the next one to be false, so if the next one is false we override it to true
					results[i+1] = true;
				}
				else {
					results[i+1] = false;
				}
				results[i] = true;
				checked[i] = true; // we have checked this rule!
			}
			for (int i = 0; i < rules.size(); i++) {
				if (compareWithWildcards(rules.get(i), "-o") == false) continue;
				if (checked[i]) continue; // dont check this rule again if we have checked it
				
				if (i+1 >= rules.size() || i-1 < 0) return false;  //we need there to be rules on either side of this one
				if (results[i-1] == true || results[i+1] == true) { // if one of our sides is true, both are 
					results[i-1] = true;
					results[i]   = true;
					results[i+1] = true;
				}
				checked[i] = true; // we have checked this rule!
			}
		}
		if (tooManyCheck < 0) System.out.println("EMERGENCY STOPPED THE FIND.");
		
		//Compile if there was a rule that did not follow
		boolean totalresult = true;
		for (int i = 0; i < results.length; i++) if (results[i] == false) totalresult = false; // Set the result to false if one of these ends up false
		return totalresult;
	}
	//END METHOD
	
	
	
	
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
