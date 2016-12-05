import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
//import java.util.HashMap;

public class Graphics {

	JLabel graphics;
	int x;
	int y;
	//HashMap<Directory, ImageIcon> map;
	ImageIcon img;
	int level;
	//ImageIcon ...

	public Graphics(JLabel graphics, int x, int y, int lv) {
		this.graphics = graphics;
		this.x = x;
		this.y = (int)((y/2)*1.1);
		//map = new HashMap<Directory, ImageIcon>();
		this.level = lv;
	}

	public void updateGraphics(Command cmd, Directory d, int step) {

		img = new ImageIcon("resources/level" + level + "/" + d.name() + ".png");

		if(level == 1) {
			if(step >= 27 && d.name().equals("HypostyleHall")) {
				img = new ImageIcon("resources/level1/HypostyleHallSolved.png");

			} else if(step >= 23 && d.name().equals("GrandGallery")) {
				img = new ImageIcon("resources/level1/GrandGalleryLS.png");

			} else if(step >= 20 && d.name().equals(".PyramidOfHeket")) {
				img = new ImageIcon("resources/level1/PyramidofHeketDoor.png");

			} else if(d.getParent().name().equals("Library")) {
				img = new ImageIcon("resources/level1/Library.png");

			}
		} else if(level == 2) {
			if(d.getParent().name().equals("Library")) {
				img = new ImageIcon("resources/level2/Library.png");
			}
		}

		if(img == null) {
			System.out.println("Image not found!");
			return;
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

	public void updateLevel(int l) {
		level = l;
	}

}
