import java.util.ArrayList;
import java.util.Scanner;

public class Command {
	TerminalError error;
	String command;
	ArrayList<String> flags;
	ArrayList<String> inputs;
	
	public static Command emptyCommand() {
		Command ret = new Command();
		ret.setCommand("");
		return ret;
	}

	//[TODO] Currently does not start a new command if the ; or && touching another block.
			//Might want to switch to doing split on ; and && to break everything up first?
	public static ArrayList<Command> GenerateCommands(String input) {
		Scanner read = new Scanner(input);
		String[] validCommands = new String[] {"find","mv","ls","exit","clear","cd","cat","pwd","cp","chmod","ssh","nano","scp","head","tail","mkdir"};
		
		ArrayList<Command> commands = new ArrayList<Command>();
		Command newComm = null;
		while (read.hasNext()) {
			String token = read.next();
			//if we are not working on a command, start a new one
			if (newComm == null) {
				//Check to see if the command is a valid one
				boolean isValid = false;
				for (int i = 0; i < validCommands.length; i++) {
					if (validCommands[i].equals(token)) {
						isValid = true;
					}
				}
		
				//Throw error if command does not exist
				newComm = new Command();
				if (isValid == false) {
					newComm.error = new TerminalError("Command does not exist.");
					commands.add(newComm);
					break;
				}
				newComm.setCommand(token);
				commands.add(newComm);
			}
			else {
				if ((";").equals(token) || ("&&").equals(token)) {
					// We are done with our previous command!
					newComm = null;
				}
				else {
					boolean endIt = false;
					if (token.charAt(token.length()-1) == ';') {
						token = token.substring(0, token.length()-1);
						endIt = true;						
					}
					if ((token.charAt(0) == '-' && token.length() > 1)) {
						if (token.equals("-maxdepth") || token.equals("-name") || token.equals("-iname") || token.equals("-type")) {
							// We need to read the next token and add it to this flag?
							if (read.hasNext()) {
								String token2 = read.next();
								newComm.addFlag(token+token2);
							} else {
								newComm.error = new TerminalError("Not enough arguments for " + token +"\n");
								break;
							}
						}
						else {
							newComm.addFlag(token);
						}
					}
					else {
						//this is some other form of input. ?
						newComm.addInput(token);
					}
					if (endIt) newComm = null;
				}	
			}
		}
		read.close();
		return commands;
	}
	
	public String getSimpleString() {
		if (isErrored()) {
			return getError().getString();
		}
		String ret = "";
		
		ret += getCommand();
		//Loop through all flags and add them
		for (int i = 0; i < flags.size(); i++) {
			ret += " " + flags.get(i);
		}
		//Loop through all inputs and add them
		for (int i = 0; i < inputs.size(); i++) {
			ret += " " + inputs.get(i);
		}
		
		return ret;
	}
	
	//Compile this object into a string that we can read!
	public String toString() {
		//Output the error text if it is an error
		if (isErrored()) {
			return getError().getString();
		}
		
		String ret = "";
		ret += getSimpleString() + "\n"; // Adds the read command
		ret += "Command: ";
		ret += getCommand();
		ret += "\n";
		//Loop through all flags and add them
		for (int i = 0; i < flags.size(); i++) {
			ret += "Flag:\t ";
			ret += flags.get(i);
			ret += "\n";
		}
		//Loop through all inputs and add them
		for (int i = 0; i < inputs.size(); i++) {
			ret += "Input:\t ";
			ret += inputs.get(i);
			ret += "\n";
		}
		ret += "END---\n";
		return ret;
	}
	
	//Compare the two commands by looking at their parts
	public static boolean compare(Command a, Command b) {
		boolean ret = true; // Set flag to true, try to make it false.
		
		//Check the main Commands
		if (a.getCommand().equals(b.getCommand()) == false)
			ret = false;
		
		//Get all of the flags
		ArrayList<String> flagsA = a.getFlags();
		ArrayList<String> flagsB = b.getFlags();
		
		//Check if there is the same number of flags
		if (flagsA.size() != flagsB.size()) {
			ret = false;
		}
		else {
			//We can now assume that they have the same number, now we check each one to see if they are the same.
			for (int i = 0; i < flagsA.size(); i++) {
				if ( flagsA.get(i).equals(flagsB.get(i)) == false)
					ret = false;
			}
		}
		
		//Get all of the inputs
		ArrayList<String> inputsA = a.getInputs();
		ArrayList<String> inputsB = b.getInputs();
		
		//Check if there is the same number of inputs
		if (inputsA.size() != inputsB.size()) {
			ret = false;
		}
		else {
			//We can now assume that they have the same number, now we check each one to see if they are the same.
			for (int i = 0; i < inputsA.size(); i++) {
				if ( inputsA.get(i).equals(inputsB.get(i)) == false)
					ret = false;
			}
		}
		
		
		return ret;
	}
	
	
	//Checks if the command threw an error
	public boolean isErrored() {
		return error != null;
	}
	
	
	
	//constructor
	public Command() {
		command = "";
		flags = new ArrayList<String>();
		inputs = new ArrayList<String>();
		error = null;
	}
	
	// Getters and setters below
	public void setError(TerminalError err) {
		error = err;
	}
	public TerminalError getError() {
		return error;
	}
	
	public void setCommand(String comm) {
		command = comm;
	}
	public String getCommand() {
		return command;
	}
	
	public void addFlag(String flag) {
		flags.add(flag);
	}
	public ArrayList<String> getFlags() {
		return flags;
	}
	
	public void addInput(String input) {
		inputs.add(input);
	}
	public ArrayList<String> getInputs() {
		return inputs;
	}

}