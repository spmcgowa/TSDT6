public class Level {
	
	int stepCount;
	
	public Level() {
		stepCount = 0;
	}

	public int getStep() {
		return stepCount;
	}
	
	protected Directory buildLevel(Directory d) {
		return null;
	}
	
	public boolean playLevel(Command cmd) {
		return false;
	}
	
	protected void setLocation(Directory d) {
		
	}
	
	protected void devMode(int n) {
		
	}
}
