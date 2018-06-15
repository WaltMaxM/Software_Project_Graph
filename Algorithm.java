import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
/*
 * What if ...
 * ... start/end is not in the graph? (search) (=null)
 * ... start is isolated?
 * ...
 * 
 * 
 */


public class Algorithm {
	private Graph<Circle, Line> graph;
	private LinkedList<HObject> objList = new LinkedList<HObject>();
	public Algorithm(Graph<Circle, Line> graph) {
		this.graph = graph;
	}
	
	
	public LinkedList<HObject> forceDirectedLayout() {
		//Careful! not initilised!
		LinkedList<double[][]> endList = new LinkedList<double[][]>();
		double [][]sumOfForces = new double[graph.getVSize()][2];
		Circle[] cArray = new Circle[graph.getVSize()];
		for(int i=0;i<graph.getVSize();i++) {
			cArray[i]=getByNumber(i);
			sumOfForces [i][0]=0;
			sumOfForces [i][1]=0;
		}
		boolean stable = false;
		int zaehler = 0;
		while (!stable) {
			zaehler++;
			stable = true;
			for(int i=0;i<graph.getVSize();i++) {
				for(int j=i+1;j<graph.getVSize();j++) {
					double [] hArray = computeVertexForce(cArray[i].getX(), cArray[i].getY(), cArray[j].getX(), cArray[j].getY(),i, j);
					sumOfForces[i][0]+=hArray[0];
					sumOfForces[i][1]+=hArray[1];
					sumOfForces[j][0]+=hArray[2];
					sumOfForces[j][1]+=hArray[3];
				}
			}
			for(Line l:graph.edgeSet()) {
				double [] hArray = computeEdgeForce(l.getC1().getX(), l.getC1().getY(), l.getC2().getX(), l.getC2().getY());
				sumOfForces[l.getC1().getNumber()][0]+=hArray[0];
				sumOfForces[l.getC1().getNumber()][1]+=hArray[1];
				sumOfForces[l.getC2().getNumber()][0]+=hArray[2];
				sumOfForces[l.getC2().getNumber()][1]+=hArray[3];
			}
			endList.addLast(sumOfForces);
			for(int i=0;i<cArray.length;i++) {
				if(cArray[i].getX() != cArray[i].getX()+(int)sumOfForces[i][0] || cArray[i].getY() != (int)sumOfForces[i][1]) {
					stable = false;
				}
				sumOfForces[i][0]=0;
				sumOfForces[i][1]=0;
			}
			if(zaehler>15) {
				stable = true;
			}
		}
		while(!endList.isEmpty()) {
			objList.addLast(new HCircleList(Color.black, endList.removeFirst()));
		}
		return objList;
	}
	public double [] computeVertexForce(int fromX, int fromY, int toX, int toY, int number, int number2) {
		double e_0 = 6*Math.pow(10, -5);
		double distance =  (Math.sqrt(Math.pow(toX - fromX, 2) + Math.pow(toY-fromY, 2)));
		double force =(1/(4*Math.PI*e_0*Math.pow(distance, 2)));
		double force_from_x = -  (((force))*(toX-fromX));
		double force_from_y = -  ((force)*(toY-fromY));
		double force_to_x = - ((force)*(fromX-toX));
		double force_to_y = -  ((force)*(fromY-toY));
		double [] d = {force_from_x,force_from_y, force_to_x, force_to_y};
		double [] zero = {0,0,0,0};
		return d;
	}	/*
	 * force > 0: Kr�fte nach innen
	 * force < 0: Kr�fte nach au�en
	 */
	
	public double [] computeEdgeForce(int fromX, int fromY, int toX, int toY) {
		double distance =  (Math.sqrt(Math.pow(toX - fromX, 2) + Math.pow(toY-fromY, 2)));
		double force = distance - 120;
		double force_from_x =   (((force/8000d))*(toX-fromX));
		double force_from_y = ((force/8000d)*(toY-fromY));
		double force_to_x =  ((force/8000d)*(fromX-toX));
		double force_to_y =  ((force/8000d)*(fromY-toY));
		double [] d = {force_from_x,force_from_y, force_to_x, force_to_y};
		double [] zero = {0,0,0,0};
		return d;
}
	
	
	
	// je nachdem ob man Brieten- oder Tiefensuche m�chte, muss man wahr oder falsch
	// �bergeben
	public LinkedList<HObject> search(Circle start, Circle end, boolean whichSearch) {
		LinkedList<Circle> pendingVertices = new LinkedList<Circle>();
		Circle active, neighbor;
		for (Circle i : graph.vertexSet()) {
			i.setVisited(false);
		}
		if(start == null || end == null) {
			throw new NullPointerException();
		}
		pendingVertices.addLast(start);
		// in objList Start einf�gen
		
		start.setVisited(true);
		while (!pendingVertices.isEmpty()) {
			if (whichSearch) {
				active = pendingVertices.removeFirst();
			} else {
				active = pendingVertices.removeLast();
			}
			// active vertex is green
			objList.addLast(new HCircle(Color.green, active));
			for (Line l : graph.edgesOf(active)) {
				objList.addLast(new HLine(Color.green, l));
				neighbor = graph.getEdgeTarget(l);
				if (!neighbor.getVisited()) {
					// in objList Kante und Knoten einf�gen
					pendingVertices.addLast(neighbor);
					if (neighbor == end) {
						objList.addLast(new HCircle(Color.red, start));
						objList.addLast(new HCircle(Color.red,  end));
						return objList;
					}
					objList.addLast(new HCircle(Color.blue, neighbor));
					neighbor.setVisited(true);
				}
			}
			objList.addLast(new HCircle(Color.black, active));
		}
		objList.addLast(new HCircle(Color.red, start));
		return objList;
		// end vertex was not found
	}

/*
 * 
 * 
 */
	public LinkedList<HObject> TopSort() {
		TreeMap<Integer, ArrayList<Circle>> sorting = new TreeMap<Integer, ArrayList<Circle>>();
		ArrayList<Circle> vertices = new ArrayList<Circle>();
		int incomingEdges[] = new int[graph.getVSize()];
		for (Line l : graph.edgeSet()) {
			incomingEdges[graph.getEdgeTarget(l).getNumber()]++;
			// l highlighten
			// highlight target ohne Pause (gleichzeitig)
		}
		int minimum = incomingEdges[0];
		int index = -1;
		for (int i=0;i<incomingEdges.length; i++) {
			for (int j=0;j<incomingEdges.length;j++) {
				if(incomingEdges[j] < minimum) {
					minimum = incomingEdges[j];
					index = j;
				}
			}
			
			incomingEdges[index] = graph.getEdgeSize()+1;
			// "i+1" int HObject einf�gen, Circle c = graph.get
		}
		// nun noch die nach Anzahl der eingehenden Kanten geordneten Knoten in
		// HObjectList einf�gen
		return objList;
	}

/*	// m�ssen noch Gewichte den Kanten zuordnen in der GUI
	public LinkedList<HObject> MinSpanningTree() {
		ArrayList<Line> edges = new ArrayList<Line>();
		TreeMap<Integer, ArrayList<Line>> sorting = new TreeMap<Integer, ArrayList<Line>>();
		ArrayList<Circle> vertices = new ArrayList<Circle>();
		for (Line l : graph.edgeSet()) {
			if (sorting.containsKey(graph.getWeight(l))) {
				sorting.get(graph.getWeight(l)).add(l);
			} else {
				edges.clear();
				edges.add(l);
				sorting.put((graph.getWeight(l)), edges);
			}
		}
		for (int weight : sorting.keySet()) {
			for (Line l : sorting.get(weight)) {
				if (!vertices.contains(graph.getEdgeSource(l)) && !vertices.contains(graph.getEdgeTarget(l))) {
					vertices.add(graph.getEdgeSource(l));
					vertices.add(graph.getEdgeTarget(l));
					// beide Knoten und Kante highlighten (am besten alle gleichzeitig?!)
				} else {
					if (vertices.contains(graph.getEdgeSource(l)) && !vertices.contains(graph.getEdgeTarget(l))) {
						vertices.add(graph.getEdgeTarget(l));
						// Target Knoten und Kante highlighten
					}
					if (!vertices.contains(graph.getEdgeSource(l)) && vertices.contains(graph.getEdgeTarget(l))) {
						vertices.add(graph.getEdgeSource(l));
						// Source Knoten und Kante highlighten
					}
					if (vertices.contains(graph.getEdgeSource(l)) && vertices.contains(graph.getEdgeTarget(l))) {
						// Kante highlighten und danach verwerfen
					}
				}
			}
		}
		return objList;
	}

	public LinkedList<HObject> maxMatching(boolean whichProblem) {
		ArrayList<Circle> vertices = new ArrayList<Circle>();
		for (Line l : graph.edgeSet()) {
			if (!vertices.contains(graph.getEdgeSource(l)) && !vertices.contains(graph.getEdgeTarget(l))) {
				vertices.add(graph.getEdgeSource(l));
				vertices.add(graph.getEdgeTarget(l));
				// alles highlighten
			} else {
				if (vertices.contains(graph.getEdgeSource(l)) && !vertices.contains(graph.getEdgeTarget(l))) {
					// Target Knoten und Kante highlighten und Kante verwerfen
				}
				if (!vertices.contains(graph.getEdgeSource(l)) && vertices.contains(graph.getEdgeTarget(l))) {
					// Source Knoten und Kante highlighten und Kante verwerfen
				}
				if (vertices.contains(graph.getEdgeSource(l)) && vertices.contains(graph.getEdgeTarget(l))) {
					// Kante highlighten und danach verwerfen
				}
			}
		}
		return objList;
	}
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
