import java.awt.Color;

public class HCircleList extends HObject {
	public double [][] sumOfForces;
	public HCircleList (Color c, double sumOfForces [][]) {
		super(c);
		this.sumOfForces = sumOfForces;
		this.whatType = 2;
	}
	public void print() {
		for(int i=0;i<sumOfForces.length;i++) {
			System.out.println((int)sumOfForces[i][0]);
		}
	}
}
