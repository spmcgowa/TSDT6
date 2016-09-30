import javax.swing.*;

import java.awt.*;

public class Parser {

	public static void main(String[] args) {
		
		//this is the window the UI will be housed in
		JFrame window = new JFrame();
		
		//this is the input box
		JTextField input = new JTextField(42);
		
		//this is the output field
		JTextArea output = new JTextArea(16, 42);
		
		//these four lines set the color scheme to match a linux terminal
		input.setBackground(Color.BLACK);
		input.setForeground(Color.GREEN);
		output.setBackground(Color.BLACK);
		output.setForeground(Color.GREEN);
		
		//jpanel is necessary to align elements properly
		JPanel grid = new JPanel();
		//boxlayout contains the input and output elements and aligns them appropriately
		grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
		grid.add(input, BorderLayout.NORTH);
		grid.add(output, BorderLayout.SOUTH);
		
		//makes the jpanel accessible through the jframe
		window.add(grid);
		
		//I don't actually know what pack() does, all I remember is my CS1122 instructor telling me it's good
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//this sets up a listener and adds it to the input box, allowing for the input
		//to be processed when the user presses [ENTER]
		CommandStream cs = new CommandStream(input, output);
		input.addActionListener(cs);
	}
	
}
