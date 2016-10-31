import java.util.ArrayList;

public class Directory {
	
	public String name;
	public Directory parent;
	public ArrayList<Directory> subDirectories;
	public ArrayList<File> files;
	
	public Directory(String name, Directory parent, ArrayList<Directory> contents, ArrayList<File> files) {
		this.name = name;
		this.parent = parent;
		subDirectories = contents;
		this.files = files;
		
		
		if (parent == null) {
			System.out.println(name + " directory does not have a parent.\n");
		}
	}
	
	public String getPath() {
		String ret = "";
		Directory c = this;
		while (c != null) {
			ret = c.name + "/" + ret;
			c = c.parent;
		}
		return ret;
	}
	
	public ArrayList<Directory> getSubDirs() {
		return subDirectories;
	}
	
	public ArrayList<File> getFiles() {
		return files;
	}
	
	public Directory getParent() {
		return parent;
	}
	
	public String name() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addDirectory(Directory dir) {
		subDirectories.add(dir);
	}
	
	public void remDir(Directory dir) {
		subDirectories.remove(dir);
	}
	
	public void addFile(File fil) {
		files.add(fil);
	}
	
	public void delFile(File file) {
		files.remove(file);
	}
	
}