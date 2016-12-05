import java.util.*;


public class DimensionX {

	Directory dimRoot = null;

	public DimensionX() {
		//dimRoot = buildDimX();
	}

	public Directory buildDimX() {
		Directory root = new Directory("Dimension X", null, new ArrayList<Directory>(), new ArrayList<File>());
		Directory spoksHouse = new Directory("AlienHouse", root, new ArrayList<Directory>(), new ArrayList<File>());
		Directory grandCentralStation = new Directory("GrandCentralStation", root, new ArrayList<Directory>(), new ArrayList<File>());

		root.addDirectory(spoksHouse);
		root.addDirectory(grandCentralStation);

		return spoksHouse;
	}

}
