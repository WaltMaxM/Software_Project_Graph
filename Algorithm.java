import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
/*
 * What if ...
 * ... start/end is not in the graph? (search) (=null)
 * ... start is isolated?
 * ...
 * 
 * 

 //does the upload work as update?
 */


public class Algorithm {
	private Graph<Circle, Line> graph;
	private LinkedList<HObject> objList = new LinkedList<HObject>();
	public Algorithm(Graph<Circle, Line> graph) {
		this.graph = graph;
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
}
