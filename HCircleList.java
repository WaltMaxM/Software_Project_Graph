import java.awt.Color;

public class HCircleList extends HObject {
	public Circle [] newVertices;
	public HCircleList (Color c, int size) {
		super(c);
		this.newVertices = new Circle [size];
		this.whatType = 2;
	}
	public void print() {
		for(int i=0;i<newVertices.length;i++) {
			System.out.print(newVertices[i].getX()+"  ");
			System.out.println(newVertices[i].getY());
		}
	}
	public void setIthCircle(int x, int y, int i) {
		newVertices[i] = new Circle(10, x, y, i);
	}
}
