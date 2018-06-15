import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle {
	private int x, y, r, number;
	private boolean visited;
	private Color color = Color.BLACK;
	
	public Circle(int r, int x, int y, int number) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.visited = false;
		this.number = number;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean contains (int x, int y) {
		if(Math.abs(x-this.x)<r && Math.abs(y-this.y)<r) {
			return true;
		}
		return false;
	}
	public int getNumber() { return number; }

	public int getX() { return x; }
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() { return y; }
	
	public int getRadius() { return r; }
	
	public void setVisited(boolean visited) { this.visited = visited; }
	
	public boolean getVisited() { return visited; }
	
	public void draw(Graphics g) {
		Graphics2D gx = (Graphics2D) g.create();
		gx.setStroke(new BasicStroke(2));
		gx.setColor(this.color);
		gx.drawOval(x-r, y-r, r*2, r*2);
	}
}
