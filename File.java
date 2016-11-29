
public class File {

	protected String name;
	protected String contents;
	protected String otherWritePerm;
	protected String otherReadPerm;
	protected String otherExecutePerm;
	protected String allWritePerm;
	protected String allReadPerm;
	protected String allExecutePerm;
	
	public File(String name, String contents) {
		this.name = name;
		this.contents = contents;
		otherWritePerm = "w";
		otherReadPerm = "r";
		otherExecutePerm = "x";
		allWritePerm = "w";
		allReadPerm = "r";
		allExecutePerm = "x";
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void writeContents(String newContents) {
		contents = newContents;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContents() {
		return contents;
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
