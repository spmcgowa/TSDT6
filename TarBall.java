import java.util.ArrayList;


//A tarball is a special kind of file, but we are cheating a little so there does not have to be any actual
// sticking of files together.
public class TarBall extends File {

	public ArrayList<Object> items;
	
	public TarBall(String name, ArrayList<Object> items) {
		super(name, "A Tarfile");
		this.items = items;
	}
	
	public ArrayList<Object> getItems() {
		return items;
	}
	
}
