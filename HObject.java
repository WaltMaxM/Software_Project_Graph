import java.awt.Color;


public class HObject {
	public Color c;
	public boolean isVertex = false;
	public boolean stayActive;
	public HObject (Color c) {
		this.c = c;
	}
	public void print() {
		System.out.println("isVertex: "+isVertex);
	}
}