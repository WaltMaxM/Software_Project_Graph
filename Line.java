import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Line {
	private Circle c1, c2;
	private int weight;
	private Color color = Color.BLACK;
	public Line(Circle c1, Circle c2, int weight) {
		this.c1 = c1;
		this.c2 = c2;
		this.weight = weight;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void draw(Graphics g) {
		Graphics2D gx = (Graphics2D) g.create();
		gx.setStroke(new BasicStroke(2));
		gx.setColor(this.color);
		double length = Math.sqrt(Math.pow((c1.getX() - c2.getX()), 2) + Math.pow(c1.getY() - c2.getY(), 2));
		int xStart = (int) (c1.getX() + (10.0 / length) * (c2.getX() - c1.getX()));
		int yStart = (int) (c1.getY() + (10.0 / length) * (c2.getY() - c1.getY()));
		int xEnd = (int) (c1.getX() + (1 - (15.0 / length)) * (c2.getX() - c1.getX()));
		int yEnd = (int) (c1.getY() + (1 - (15.0 / length)) * (c2.getY() - c1.getY()));	
		gx.drawLine(xStart, yStart, xEnd, yEnd);
		drawDirections(gx, xEnd, yEnd);
	}
	
	public Circle getC1() {
		return c1;
	}

	public Circle getC2() {
		return c2;
	}

	public void drawDirections(Graphics2D gx,  int xEnd, int yEnd) {
		gx.setColor(this.color);
		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, 5);
		arrowHead.addPoint(-5, -5);
		arrowHead.addPoint(5, -5);
		double angle = Math.atan2(c2.getY() - c1.getY(), c2.getX() - c1.getX());
		gx.translate(xEnd, yEnd);
		gx.rotate((angle - Math.PI / 2d));
		gx.fill(arrowHead);
		gx.translate(10, -18);
		gx.rotate(-(angle - Math.PI / 2d));
		gx.drawString(Integer.toString(weight), 0,0);
	}
}