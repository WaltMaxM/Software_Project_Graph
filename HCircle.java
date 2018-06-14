import java.awt.Color;


public class HCircle extends HObject {
	public Circle circ;
	public HCircle (Color c, Circle circ) {
		super(c);
		this.circ = circ;
		this.isVertex = true;
	}
}
