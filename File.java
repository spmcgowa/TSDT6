
public class File {

	protected String name;
	protected String contents;
	
	public File(String name, String contents) {
		this.name = name;
		this.contents = contents;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContents() {
		return contents;
	}
	
}
