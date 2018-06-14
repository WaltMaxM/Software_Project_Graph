import junit.framework.TestCase;

public class GraphTest extends TestCase {
	
	class Vertex {
		int x, y;
		public Vertex(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	class Edge {
		String label;
		public Edge(String label) {
			this.label = label;
		}
	}
	
	 public void testEmptyConstructor() {
		 Graph<Vertex, Edge> g = new Graph<Vertex,Edge>();
		 assertNotNull("Graph should != null after call to constructor", g);
		 assertEquals("Size on vertexSet should return 0", 0, g.vertexSet().size());
		 assertEquals("Size on edgeSet should return 0", 0, g.edgeSet().size());
	 }

	public void testAddVertex() {
		Graph<Vertex, Edge> g = new Graph<Vertex, Edge>();
		Vertex v1 = new Vertex(1, 2);
		Vertex v2 = new Vertex(1, 2);
		boolean success = g.addVertex(v1);
		
		assertTrue("Graph.addVertex(v) did not return true when adding a new unique vertex.",
				success);
		assertEquals("Graph.addVertex(v) did not update the size of its vertex set.", 
				g.vertexSet().size(), 1);
		
		success = g.addVertex(v1);
		assertFalse("Graph.addVertex(v) returned true even though v was already added.",
				success);
		
		success = g.addVertex(v2);
		assertTrue("Graph.addVertex(v) did not return true when adding a new unique vertex.",
				success);
		assertEquals("Graph.addVertex(v) did not update the size of its vertex set.", 
				g.vertexSet().size(), 2);
	}
	
	public void testEdgesOf() {
		Graph<Vertex, Edge> g = new Graph<Vertex, Edge>();
		Vertex v1 = new Vertex(1, 2);
		assertNull("Graph.getEdgesOf(v1) did not return null for empty edge set", g.edgesOf(v1));
	}
}
