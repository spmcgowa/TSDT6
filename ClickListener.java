import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;


public class ClickListener implements MouseListener {

	String next = "dialog0";
	JTextArea graphicsText;
	
	public ClickListener(JTextArea graphicsText) {
		this.graphicsText = graphicsText;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		updateText(next, graphicsText);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateText() {
		updateText(next, graphicsText);
	}
	
	protected void updateText(String next, JTextArea output) {
		if(next.equals("dialog0")) {
			next = "dialog1";
			output.setText("While doing research in the library, <you>\nfind a treasure map tucked away in the stacks.");
		} else if(next.equals("dialog1")) {
			next = "dialog2";
			output.setText("Type ls to open map");
		}
	}

}
