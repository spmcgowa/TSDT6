import javax.swing.*;

import java.util.ArrayList;
import java.awt.*;

public class Parser {

	protected Directory dir;
	protected Directory root;

	public Parser() {
		root = new Directory("root", null, new ArrayList<Directory>(), new ArrayList<File>());
	}
	
	public static void main(String[] args) {
		
		Parser p = new Parser();
		//Directory startingDir = p.buildLvls(p.root);

		final double SCREEN_X = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		final double SCREEN_Y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(SCREEN_X / 2);
		int y = (int)SCREEN_Y - 70;
		
		//this is the window the UI will be housed in
		JFrame window = new JFrame();
		window.setResizable(false);

		JPanel house = new JPanel();
		house.setPreferredSize(new Dimension(x, y));
		house.setLayout(new BoxLayout(house, BoxLayout.Y_AXIS));
		
		
		JPanel display = new JPanel();
			
			JLabel graphics = new JLabel();
			
			graphics.setPreferredSize(new Dimension(x, (int)((y/2)*0.8)));
			
			display.add(graphics);
			house.add(display);
			
		//JTextPane to display story-advancing dialogue
		JPanel dialogue = new JPanel();
		dialogue.setLayout(new BoxLayout(dialogue, BoxLayout.Y_AXIS));
		JTextPane storyOutput = new JTextPane();
			storyOutput.setEditable(false);
			storyOutput.setVisible(true);
			
			JScrollPane necessaryEvil = new JScrollPane(storyOutput);
			necessaryEvil.setPreferredSize(new Dimension(x, (int)((y/2)*0.2)));
			//necessaryEvil.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
			
			dialogue.add(necessaryEvil);
			
			house.add(dialogue);
			
		//terminal is the JPanel that will contain all terminal-related elements
		JPanel terminal = new JPanel();
			//buttons functionality for nano
			terminal.setPreferredSize(new Dimension(x, y/2));
		
			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout());
		
			JButton save = new JButton("Ctrl + O");
			JButton exit = new JButton("Ctrl + X");
		
			save.setActionCommand("save");
			exit.setActionCommand("exit");
		
			buttons.add(save);
			buttons.add(exit);
		
			buttons.setVisible(false);
			
			//the input box
			JTextField input = new JTextField();
			input.setPreferredSize(new Dimension(x, y/20));
			input.setFont(new Font("Courier", Font.PLAIN, 14));
			input.setCaretColor(Color.WHITE);
			input.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			//output area
			JTextArea output = new JTextArea();
			output.setPreferredSize(new Dimension(x, (int)(y*0.95)));
			output.setEditable(false);
			output.setFont(new Font("Courier", Font.PLAIN, 14));
			
			input.setBackground(Color.BLACK);
			input.setForeground(Color.WHITE);
			output.setBackground(Color.BLACK);
			output.setForeground(Color.WHITE);
			
				JScrollPane scrollGoal = new JScrollPane(output);
				scrollGoal.getVerticalScrollBar().setVisible(false);
				scrollGoal.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
				scrollGoal.setBorder(null);
				
			terminal.setLayout(new BoxLayout(terminal, BoxLayout.Y_AXIS));
			terminal.add(input, BorderLayout.NORTH);
			terminal.add(scrollGoal, BorderLayout.SOUTH);
			terminal.add(buttons, BorderLayout.SOUTH);

			house.add(terminal, BorderLayout.SOUTH);
			
		window.add(house);

		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//this sets up a listener and adds it to the input box, allowing for the input
		//to be processed when the user presses [ENTER]
		CommandStream cs = new CommandStream(input, output, p.root, p.root, buttons, "", storyOutput, graphics, x, y);
		//cs.setStartingDir(startingDir);
		input.addActionListener(cs);
		save.addActionListener(cs);
		exit.addActionListener(cs);
		//Level1 lv = new Level1(cs, "");
		input.requestFocus();
	}

	/**
	 * This method constructs the basic file structure of a simulated linux system
	 * @return a reference to the root directory
	private Directory buildLvls(Directory root) {
		Directory lv1 = new Directory("Level1", root, new ArrayList<Directory>(), new ArrayList<File>());
		//Directory lv2 = new Directory("Level2", root, new ArrayList<Directory>(), new ArrayList<File>());
		root.addDirectory(lv1);
		//root.addDirectory(lv2);
		return buildLv1(lv1);
	}
	*/

}
