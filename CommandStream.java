import java.awt.event.*;

import javax.swing.*;

import java.util.Scanner;

public class CommandStream implements ActionListener {

	JTextField input;
	JTextArea output;
	Scanner scan;
	Directory currentDirectory;
	JPanel buttons;
	File nanoFile;
	Directory prevDir;
	final Directory root;

	public CommandStream(JTextField input, JTextArea output, Directory cd,
			Directory root, JPanel buttons) {
		this.input = input;
		this.output = output;
		currentDirectory = cd;
		this.root = root;
		this.buttons = buttons;
		nanoFile = null;
		prevDir = null;
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

		// this is to process the string and determine the command entered
		scan = new Scanner(text);
		String command = "";

		if (scan.hasNext()) {
			command = scan.next();
		}

		if (command.equals("")) {
			output.requestFocus();
			return;
		}

		// block of if statements to find the command the user entered
		if (command.equals("mv")) {
			mv(input, output, scan, root);
		} else if (command.equals("ls")) {
			boolean a = false;
			if (scan.hasNext()) {
				a = true;
			}
			ls(input, output, a);
		} else if (command.equals("exit")) {
			exit();
		} else if (command.equals("clear")) {
			output.setText("");
			input.setText("");
		} else if (command.equals("cd")) {
			cd(input, output, scan);
		} else if (command.equals("cat")) {
			if (scan.hasNext()) {
				cat(scan.next(), currentDirectory, output);
			} else {
				invalid(input, output);
			}
		} else if (command.equals("pwd")) {
			pwd(output, currentDirectory);
		} else if (command.equals("cp")) {
			String file = "";
			String dest = "";

			if (scan.hasNext()) {
				file = scan.next();
			} else {
				output.append("Missing parameter!");
				return;
			}
			if (scan.hasNext()) {
				dest = scan.next();
			} else {
				output.append("Missing parameter!");
				return;
			}
			cp(input, output, file, dest);
		} else if (command.equals("chmod")) {

		} else if (command.equals("ssh")) {

		} else if (command.equals("nano")) {

			if (!scan.hasNext()) {
				output.append("Missing file parameter!");
				return;
			}

			File f = findFile(scan.next(), currentDirectory);
			if (f == null) {
				output.append("File " + f + " not found.\n");
				return;
			}

			nano(input, output, buttons, f);
		} else if (command.equals("scp")) {

		} else {
			invalid(input, output);
		}
	}

	// these methods execute the proper command based on the command identified
	// above in the ifs block
	// -----------------------------------------------------
	public void mv(JTextField input, JTextArea output, Scanner scan,
			Directory root) {

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
		String[] actualPath = new String[filePath.length - 1];
		for(int i = 0; i < filePath.length - 1; i++) {
			actualPath[i] = filePath[i];
		}

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

		input.setText("");
	}

	public void cp(JTextField input, JTextArea output, String file, String dest) {
		File f = findFile(file, currentDirectory);
		if (f == null) {
			output.append("File " + f + " not found!\n");
			return;
		}
		String path[] = dest.split("/");
		Directory destination = validateFilePath(path);
		if (destination == null) {
			output.append("Invalid path!\n");
			return;
		}
		destination.addFile(f);
		input.setText("");
		output.append("File " + f.getName() + " successfully copied to "
				+ destination.name() + "\n");
	}

	public void nano(JTextField input, JTextArea output, JPanel buttons, File f) {
		input.setText("");
		output.setText(f.getContents());
		input.setEditable(false);
		output.setEditable(true);
		output.requestFocus();
		// save.setVisible(true);
		// exit.setVisible(true);
		buttons.setVisible(true);
		nanoFile = f;
	}

	public void pwd(JTextArea output, Directory currentDirectory) {
		String path = "";

		// first use of a do-while loop, I'm so proud!
		do {
			path = "/" + currentDirectory.name() + path;
			currentDirectory = currentDirectory.getParent();
		} while (currentDirectory != null);

		output.append(path + "\n");
		input.setText("");
	}

	public void ls(JTextField input, JTextArea output, boolean a) {
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

	public void cd(JTextField input, JTextArea output, Scanner scan) {
		// first check: does cd have an argument?
		if (scan.hasNext()) {
			String location = scan.next();

			// second check: is the cd argument ..?
			if (location.equals("..")) {
				if (currentDirectory.getParent() != null) {
					prevDir = currentDirectory;
					currentDirectory = currentDirectory.getParent();
				}

				input.setText("");
				output.append("Current working directory is now "
						+ currentDirectory.name() + "\n");
			} else if(location.equals("/")) {
				prevDir = currentDirectory;
				currentDirectory = root;
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
			} else if(location.equals("-")) {
				Directory temp = currentDirectory;
				currentDirectory = prevDir;
				prevDir = temp;
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
			} else {
				
				for(Directory dr : currentDirectory.getSubDirs()) {
					if(dr.name().equals(location)) {
						prevDir = currentDirectory;
						currentDirectory = dr;
						output.append("Current working directory is " + dr.name() + "\n");
						input.setText("");
						return;
					}
				}
				
				Directory d = validateFilePath(location.split("/"));
				if(d == null) {
					output.append("Invalid file path.\n");
					return;
				}
				
				prevDir = currentDirectory;
				currentDirectory = d;
				input.setText("");
				output.append("Current working directory is now " + currentDirectory.name() + "\n");
			} // end else
		} else {
			output.append("Invalid parameters." + "\n");
		} // end scan.hasNext check
	} // end method

	public void invalid(JTextField input, JTextArea output) {
		output.append(input.getText() + " is not a valid command.\n");
	}

	public void cat(String filename, Directory dir, JTextArea output) {
		File file = findFile(filename, dir);
		if (file == null) {
			output.append("No such file.\n");
			return;
		}
		input.setText("");
		output.append(file.getContents() + "\n");
	}

	public void exit() {
		System.exit(0);
	}

	// -----------------------------------------------------

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
			// and ignores the /root part, starting with a subdirectory of root
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
				return null;
			}

		} // next i

		return dir;
	}

}
