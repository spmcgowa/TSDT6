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
		Directory startingDir = p.buildLvls(p.root);

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
			necessaryEvil.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
			
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
		CommandStream cs = new CommandStream(input, output, p.root, p.root, buttons, "", storyOutput);
		cs.setStartingDir(startingDir);
		input.addActionListener(cs);
		save.addActionListener(cs);
		exit.addActionListener(cs);
		//Level1 lv = new Level1(cs, "");
		input.requestFocus();
	}

	/**
	 * This method constructs the basic file structure of a simulated linux system
	 * @return a reference to the root directory
	 */
	private Directory buildLvls(Directory root) {
		Directory lv1 = new Directory("Level1", root, new ArrayList<Directory>(), new ArrayList<File>());
		//Directory lv2 = new Directory("Level2", root, new ArrayList<Directory>(), new ArrayList<File>());
		root.addDirectory(lv1);
		//root.addDirectory(lv2);
		return buildLv1(lv1);
	}

	private Directory buildLv1(Directory lv1) {
		Directory city = new Directory("City", lv1, new ArrayList<Directory>(), new ArrayList<File>());
		lv1.addDirectory(city);
		
		Directory library = new Directory("Library", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(library);
		library.addFile(new File("archaeology.txt", ""));
		library.addFile(new File("HowToLinux.txt", ""));
		library.addFile(new File("heket.txt", ""));
		library.addFile(new File("frogs.txt", ""));
		
		Directory home = new Directory("Home", city, new ArrayList<Directory>(), new ArrayList<File>());
		city.addDirectory(home);
		
		Directory airport = new Directory("Airport", city, new ArrayList<Directory>(), new ArrayList<File>());
		airport.addFile(new File("Luggage.txt", ""));
		city.addDirectory(airport);
		
		Directory baggage = new Directory("BaggageCheck", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory egypt = new Directory("Egypt", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory giza = new Directory("Giza", egypt, new ArrayList<Directory>(), new ArrayList<File>());
		Directory alexandria = new Directory("Alexandria", egypt, new ArrayList<Directory>(), new ArrayList<File>());
		Directory minnesota = new Directory("Minnesota", airport, new ArrayList<Directory>(), new ArrayList<File>());
		Directory bathroom = new Directory("Bathrooms", airport, new ArrayList<Directory>(), new ArrayList<File>());
		
		airport.addDirectory(minnesota);
		airport.addDirectory(bathroom);
		airport.addDirectory(baggage);
		airport.addDirectory(egypt);
		egypt.addDirectory(giza);
		egypt.addDirectory(alexandria);
		
		Directory pyramid1 = new Directory("PyramidOofMenKaure", giza, new ArrayList<Directory>(), new ArrayList<File>());
		Directory pyramid2 = new Directory("PyramidOfKhafre", giza, new ArrayList<Directory>(), new ArrayList<File>());
		Directory pyramid3 = new Directory("PyramidOfKhufu", giza, new ArrayList<Directory>(), new ArrayList<File>());
		Directory pyramid4 = new Directory(".PyramidOfHeket", giza, new ArrayList<Directory>(), new ArrayList<File>());
		
		giza.addDirectory(pyramid1);
		giza.addDirectory(pyramid2);
		giza.addDirectory(pyramid3);
		giza.addDirectory(pyramid4);
		
		pyramid4.addFile(new File("slab.txt", ""));
		Directory gg = new Directory("GrandGallery", pyramid4, new ArrayList<Directory>(), new ArrayList<File>());
		Directory hh = new Directory("HypostyleHall", pyramid4, new ArrayList<Directory>(), new ArrayList<File>());
		pyramid4.addDirectory(gg);
		pyramid4.addDirectory(hh);
		
		gg.addFile(new File(".feline.txt", ""));
		hh.addFile(new File("Serpent.txt", ""));
		hh.addFile(new File("Ra.txt", ""));
		hh.addFile(new File("Apep.txt", ""));
		
		hh.addDirectory(new Directory("Cat", hh, new ArrayList<Directory>(), new ArrayList<File>()));
		hh.addDirectory(new Directory("Snake", hh, new ArrayList<Directory>(), new ArrayList<File>()));
		hh.addDirectory(new Directory("Ra", hh,  new ArrayList<Directory>(), new ArrayList<File>()));
		hh.addDirectory(new Directory("Apep", hh, new ArrayList<Directory>(), new ArrayList<File>()));
		
		
		
		return library;
	}

}
