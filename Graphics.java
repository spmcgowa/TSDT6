import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.util.HashMap;

public class Graphics {
	
	JLabel graphics;
	int x;
	int y;
	HashMap<Directory, ImageIcon> map;
	ImageIcon img;
	//ImageIcon ...
	
	public Graphics(JLabel graphics, int x, int y) {
		this.graphics = graphics;
		this.x = x;
		this.y = (int)((y/2)*0.8);
		map = new HashMap<Directory, ImageIcon>();
	}
	
	public void updateGraphics(Command cmd, Directory d) throws Exception {
		img = new ImageIcon(d.name() + ".jpg");
		
		if(img == null) {
			throw new Exception("Image not found!");
		}

		img = verifyScale(img);
		graphics.setIcon(img);
		
	}
	
	public ImageIcon verifyScale(ImageIcon i) {
		
		BufferedImage foo = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bar = foo.createGraphics();
		
		bar.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		bar.drawImage(i.getImage(), 0, 0, x, y, null);
		
		ImageIcon scaled = new ImageIcon(foo);
		
		bar.dispose();
		return scaled;
	}

}
