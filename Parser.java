import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

public class Parser {

	public static void main(String[] args) {
		
		Parser p = new Parser();
		Directory root = p.makeFileStructure();
		
		//this is the window the UI will be housed in
		JFrame window = new JFrame();
		
		JPanel graphicsPanel = new JPanel();
		graphicsPanel.setPreferredSize(new Dimension(735, 400));
		JPanel terminal = new JPanel();
		
		//this is the input box
		JTextField input = new JTextField(42);
		//input.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		//this is the output field
		JTextArea output = new JTextArea(32, 42);
		output.setEditable(false);
		
		//these four lines set the color scheme to match a linux terminal
		input.setBackground(Color.BLACK);
		input.setForeground(Color.WHITE);
		output.setBackground(Color.BLACK);
		output.setForeground(Color.WHITE);
		
		//jpanel is necessary to align elements properly
		JPanel grid = new JPanel();
		//boxlayout contains the input and output elements and aligns them appropriately
		grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
		
		terminal.setLayout(new BoxLayout(terminal, BoxLayout.Y_AXIS));
		terminal.add(input, BorderLayout.NORTH);
		terminal.add(output, BorderLayout.SOUTH);
		
		grid.add(graphicsPanel, BorderLayout.NORTH);
		grid.add(terminal, BorderLayout.SOUTH);
		
		//makes the jpanel accessible through the jframe
		window.add(grid);
		
		input.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
		
		//I don't actually know what pack() does, all I remember is my CS1122 instructor telling me it's good
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//this sets up a listener and adds it to the input box, allowing for the input
		//to be processed when the user presses [ENTER]
		CommandStream cs = new CommandStream(input, output, root);
		input.addActionListener(cs);
	}
	
	/**
	 * This method constructs the basic file structure of a simulated linux system
	 * @return a reference to the root directory
	 */
	private Directory makeFileStructure() {
		Directory root = new Directory("root", null, new ArrayList<Directory>(), new ArrayList<File>());
		Directory username = new Directory("username", root, new ArrayList<Directory>(), new ArrayList<File>());
		root.addDirectory(username);
		File foo = new File("foo", "foo bar foobar");
		root.addFile(foo);
		return root;
	}
	
}
