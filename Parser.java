import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

public class Parser {
	
	protected Directory dir;
	
	public static void main(String[] args) {
		
		Parser p = new Parser();
		Directory root = p.buildLvls();
		
		//this is the window the UI will be housed in
		JFrame window = new JFrame();
		
		JPanel graphicsPanel = new JPanel();
		graphicsPanel.setPreferredSize(new Dimension(735, 400));
		
		JPanel terminal = new JPanel();
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		
		JButton save = new JButton("Ctrl + O");
		JButton exit = new JButton("Ctrl + X");
		save.setActionCommand("save");
		exit.setActionCommand("exit");
		
		buttons.add(save);
		buttons.add(exit);
		
		buttons.setVisible(false);
		
		/*
		JLabel prompt = new JLabel();
		prompt.setBackground(Color.BLACK);
		prompt.setForeground(Color.WHITE);
		prompt.setText(">");
		*/
		
		//this is the input box
		JTextField input = new JTextField(42);
		
		//this is the output field
		JTextArea output = new JTextArea(20, 35);
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
		//terminal.add(prompt, BorderLayout.NORTH);
		terminal.add(input, BorderLayout.NORTH);
		terminal.add(output, BorderLayout.SOUTH);
		terminal.add(buttons, BorderLayout.SOUTH);
		//terminal.add(save, BorderLayout.SOUTH);
		//terminal.add(exit, BorderLayout.SOUTH);
				
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
		CommandStream cs = new CommandStream(input, output, root, root, buttons);
		input.addActionListener(cs);
		save.addActionListener(cs);
		exit.addActionListener(cs);
	}
	
	/**
	 * This method constructs the basic file structure of a simulated linux system
	 * @return a reference to the root directory
	 */
	private Directory buildLvls() {
		Directory root = new Directory("root", null, new ArrayList<Directory>(), new ArrayList<File>());
		Directory lv1 = new Directory("Level1", root, new ArrayList<Directory>(), new ArrayList<File>());
		Directory lv2 = new Directory("Level2", root, new ArrayList<Directory>(), new ArrayList<File>());
		root.addDirectory(lv1);
		root.addDirectory(lv2);
		buildLv1(lv1);
		//File foo = new File("foo", "foo bar foobar");
		//File foo2 = new File(".foo", "ftp, css, php, pen testing and other cs jargon here");
		//root.addFile(foo);
		//root.addFile(foo2);
		return root;
	}
	
	private void buildLv1(Directory lv1) {
		Directory city = new Directory("City", lv1, new ArrayList<Directory>(), new ArrayList<File>());
		Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		lv1.addDirectory(city);
		city.addDirectory(home);
		Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(airport);
		airport.addFile(new File("Luggage.txt", ""));
		Directory baggage = new Directory("BaggageCheck", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory egypt = new Directory("Egypt", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory giza = new Directory("Giza", egypt, new ArrayList<Directory>(), new ArrayList<File>());
		airport.addDirectory(baggage);
		airport.addDirectory(egypt);
		egypt.addDirectory(giza);
		giza.addFile(new File("slab.txt", ""));
	}
	
}
