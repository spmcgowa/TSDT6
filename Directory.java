import java.util.ArrayList;

public class Directory {

	public String name;
	protected Directory parent;
	protected ArrayList<Directory> subDirectories;
	protected ArrayList<File> files;
	protected String otherWritePerm;
	protected String otherReadPerm;
	protected String otherExecutePerm;
	protected String allWritePerm;
	protected String allReadPerm;
	protected String allExecutePerm;

	public Directory(String name, Directory parent, ArrayList<Directory> contents, ArrayList<File> files) {
		this.name = name;
		this.parent = parent;
		subDirectories = contents;
		this.files = files;
		otherWritePerm = "w";
		otherReadPerm = "r";
		otherExecutePerm = "x";
		allWritePerm = "w";
		allReadPerm = "r";
		allExecutePerm = "x";
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

	public boolean isEmpty() {
		boolean ret = true;
		if (subDirectories.size() > 0) ret = false;
		if (files.size() > 0) ret = false;
		return ret;
	}

	public void delFile(File file) {
		files.remove(file);
	}

	public String allWritePerm() {
		return allWritePerm;
	}

	public String allReadPerm() {
		return allReadPerm;
	}

	public String allExecutePerm() {
		return allExecutePerm;
	}

	public String otherWritePerm() {
		return otherWritePerm;
	}

	public String otherReadPerm() {
		return otherReadPerm;
	}

	public String otherExecutePerm() {
		return otherExecutePerm;
	}

	protected void setAllRead(String str) {
		this.allReadPerm = str;
	}

	protected void setAllWrite(String str) {
		this.allWritePerm = str;
	}

	protected void setAllExecute(String str) {
		this.allExecutePerm = str;
	}

	protected void setOtherWrite(String str) {
		this.otherWritePerm = str;
	}

	protected void setOtherRead(String str) {
		this.otherReadPerm = str;
	}

	protected void setOtherExecute(String str) {
		this.otherExecutePerm = str;
	}

}
