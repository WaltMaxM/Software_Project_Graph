
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawPanel extends JPanel {
	
	private static final long serialVersionUID = 2290581907674913004L;
/**
	 * 
/*
 * Timer is used to visualize the alorithm.
 * Algorithm contains the funcionality of our algorithms.
 * The graph stores the graph structure.
 * circleArray saves only the vertices and is needed for adjusting the vertices.
 * 
 * 
 * 
 */	private Circle [] circleArray;
 	private boolean onObjectClicked;
	private Timer timer;
	private Algorithm alg;
	private Graph<Circle, Line> graph = new Graph<Circle, Line>();
/*
 * Constructor
 */
	public DrawPanel() {
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));
		addListeners();
		alg = new Algorithm(graph);
		circleArray = new Circle[0];
	}
	/*
	 * What if pathName is wrong (no file available)?
	 * What if file is wrong format?
	 * (wrong file format, too high/too low numbers in line 1/2, format errors in line 3+)
	 */
	
	/*
	 * updating graph. We have to update algorithm as well, since graph is not modified but replaced by a new graph
	 * We also update circleArray.
	 */
	public void readIn(String pathname) {
		graph = ReadGraph.readGraph(pathname);
		alg = new Algorithm(graph);
		circleArray = new Circle[graph.getVSize()];
		int i=0;
		for(Circle c : graph.vertexSet()) {
			circleArray[i] = c;
			i++;
		}
		repaint();
	}
	public void removeGraph() {
		graph = new Graph<Circle,Line>();
		circleArray = new Circle[0];
		alg = new Algorithm(graph);
		repaint();
	}
	
	/*
	 * What if circleArray is null?
	 * What if circleArray is empty?
	 */
	private void addListeners() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for (int i = 0; i < circleArray.length; i++) {
					/* 	Wir sehen nach, worauf wir geklickt haben.
						Falls auf ein Quadrat geklickt wurde, setzen wir dieses Quadrat an die erste Stelle.
						Das bringt, dass immer der Knoten, der auf einen anderen Knoten drauf geschoben wird
						wieder als erstes bewegt werden kann.
					*/	
					if (circleArray[i].contains(e.getX(), e.getY())) {
						if (i > 0) {
							Circle hSq = circleArray[i];
							circleArray[i] = circleArray[0];
							circleArray[0] = hSq;
							}
						i = 10;
						// i= 10 ist Abbruchbedingung. Ohne i=10 bewegt man alle Quadrate gleichzeitig.
						onObjectClicked = true;
						// wir merken uns, dass wir auf das Quadrat geklickt haben. Noch ist nix bewegt.
						// Das geklickte Quadrat befindet sich bei i[0].
					} else {
						onObjectClicked = false;
					}
				}
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			// Falls die geklickte Maus, die auf ein Quadrat geklickt haben muss, sich bewegt
			// wird moveSomething ausgef�hrt.
			public void mouseDragged(MouseEvent e) {
				if (onObjectClicked) {
					moveVertex(e.getX(), e.getY());
				}
			}
		});
	}
	/*
	 * will only be called, if klicked on object --> no Test necessery.
	 */
	public void moveVertex(int x, int y) {
		circleArray[0].setX(x);
		circleArray[0].setY(y);
		repaint();
	}

	public int createRandomGraph() {
		graph = new Graph<Circle, Line>();

		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			int x = rand.nextInt(500);
			int y = rand.nextInt(500);
			graph.addVertex(new Circle(10, x, y, graph.getVSize()));
		}
		for (int i = 0; i < 10; i++) {
			for (int j = i + 1; j < 10; j++) {
				int r = rand.nextInt(100);
				if (r > 85) {
					try {
						graph.addEdge(new Line(getByNumber(i), getByNumber(j), rand.nextInt(10)), getByNumber(i), getByNumber(j));
					} catch (NullPointerException e){
						System.out.println("A Scource vertice of an edge does not exist!");
						return 1;
					}
					catch (IllegalArgumentException e) {
						System.out.println("A Target vertice of an edge does not exist!");
						return 2;
					}
				}
			}
		}
		for (int i = 9; i >= 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				int r = rand.nextInt(100);
				if (r > 85) {
					try {
						graph.addEdge(new Line(getByNumber(i), getByNumber(j), rand.nextInt(10)), getByNumber(i), getByNumber(j));
					} catch (NullPointerException e){
						System.out.println("A Scource vertice of an edge does not exist!");
						return 1;
					}
					catch (IllegalArgumentException e) {
						System.out.println("A Target vertice of an edge does not exist!");
						return 2;
					}
				}
			}
		}
		alg = new Algorithm(graph);
		circleArray = new Circle[graph.getVSize()];
		int i=0;
		for(Circle c : graph.vertexSet()) {
			circleArray[i] = c;
			i++;
		}
		repaint();
		return 0;
	}

	private class TimerListener implements ActionListener {
		private LinkedList<HObject> objList;

		public TimerListener(LinkedList<HObject> objList) {
			super();
			this.objList = objList;
		}

		public void actionPerformed(ActionEvent e) {

			HObject o = objList.removeFirst();
			if (o.isVertex) {
				Circle circ = ((HCircle) o).circ;
				circ.setColor(o.c);
			} else {
				Line l = ((HLine) o).l;
				l.setColor(o.c);
			}
			if (objList.isEmpty()) {
				timer.stop();
			}
			repaint();
		}
	}
/*
 * What if target of src does not exist?
 * What if vertex numbers are not unique?
 */
	public void addLine(int src, int tar, int weight) {
		if (src < 0 || tar < 0 || src >= graph.getVSize() || tar >= graph.getVSize()) {
			throw new IllegalArgumentException("Ids must be valid");
		}
		graph.addEdge(new Line(getByNumber(src), getByNumber(tar), weight), getByNumber(src), getByNumber(tar));
		repaint();
	}
/*
 * What if algNumber does not exist?
 */
	public boolean performAlgorithm(int algNumber) {
		
		if(graph.getVSize() != 0) {
			LinkedList<HObject> objList = alg.search(getByNumber(0), getByNumber(1), false);
			timer = new Timer(1000, new TimerListener(objList));
			timer.start();
			return true;
		} else {
			return false;
		}
	}
/*
 * (non-Javadoc)
 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
 * What if graph is empty?
 * What if graph is null?
 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(graph.getVSize()>0) {
			for (Line l : graph.edgeSet()) {
				l.draw(g);
			}

			for (int i = 0;i<graph.vertexSet().size();i++) {
				circleArray[i].draw(g);
				g.setColor(Color.black);
				g.setFont(new Font(null, 1, 12));
				g.drawString(String.valueOf(circleArray[i].getNumber()), circleArray[i].getX() - 3, circleArray[i].getY() + 3);
			}		
		}
	}
/*
 * What if number is redundant?
 */
	public Circle getByNumber(int number) {
		Set<Circle> vSet = graph.vertexSet();
		for (Circle v : vSet) {
			if (number == v.getNumber()) {
				return v;
			}
		}
		return null;
	}

}
