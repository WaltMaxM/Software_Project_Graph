import java.awt.Color;


public class HLine extends HObject{
	public Line l;
	public HLine (Color c, Line l) {
		super(c);
		this.l = l;
		this.whatType = 1;
	}
	public void print() {
		
	}
}