import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/*
 * A graph object <tt>G<V, E></tt> contains a set <tt>V</tt> of vertices and a set <tt>E</tt>
 * of edges.  Each edge e=(v, u) in E connects v to u. 
 * The graph being implemented is directed meaning that (v,u) in E does not 
 * necessarily mean that (u,v) in E.
 * 
 * For more information
 * about graphs and their related definitions see 
 * <a href="http://mathworld.wolfram.com/Graph.html">
 * http://mathworld.wolfram.com/Graph.html</a>.
 * 
 * <p>Through generics, a graph can be typed to specific classes for vertices
 * <code>V</code> and edges <code>E</code>. Such a graph can contain
 * vertices of type <code>V</code> and Edges of type <code>
 * E</code>.</p>
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class Graph <V, E> {

	/*
	 * Hashmap containing all vertices as keys, and their respective adjacency lists
	 * as elements.
	 */
	public HashMap <V, ArrayList<E>> neighbors;
	private int edgeSize = 0;
	private int vSize = 0;
	/*
	 * Edge container storing the endpoints of an edge.
	 */
	private class EdgeContainer {
		V src, tar;
		public EdgeContainer(V src, V tar) {
			this.src = src;
			this.tar = tar;
		}
	}
	
	/*
	 * Hashmap storing all edges as keys and their respective endpoints as 
	 * elements.
	 */
	private HashMap <E, EdgeContainer> edges;
	
	public Graph() {
		neighbors = new HashMap<V, ArrayList<E>>();
		edges = new HashMap<E, EdgeContainer>();
	}
	
	/**
     * Returns an edge connecting source vertex to target vertex if such
     * vertices and such edge exist in this graph. Otherwise returns <code>
     * null</code>. If any of the specified vertices is <code>null</code>
     * returns <code>null</code>
     *
     *
     * @param src source vertex of the edge.
     * @param tar target vertex of the edge.
     *
     * @return an edge connecting source vertex to target vertex.
     */
	public E getEdge(V src, V tar) {
		for (E e : edges.keySet()) {
			EdgeContainer ep = edges.get(e);
			if (src.equals(ep.src) && tar.equals(ep.tar)) {
				return e;
			}
		}
		return null;
	}
	
	/**
     * Adds the specified edge to this graph, going from the source vertex to
     * the target vertex. More formally, adds the specified edge, <code>
     * e</code>, to this graph if this graph contains no edge <code>e2</code>
     * such that <code>e2.equals(e)</code>. If this graph already contains such
     * an edge, the call leaves this graph unchanged and returns <tt>false</tt>.
     * If the edge was added to the graph, returns <code>
     * true</code>.
     *
     * <p>The source and target vertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.</p>
     *
     * @param src source vertex of the edge.
     * @param tar target vertex of the edge.
     * @param data edge to be added to this graph.
     *
     * @return <tt>true</tt> if this graph did not already contain the specified
     * edge.
     *
     * @throws IllegalArgumentException if source or target vertices are not
     * found in the graph.
     * @throws NullPointerException if any of the specified vertices is <code>
     * null</code>.
     *
     */
    public boolean addEdge(E data, V src, V tar) {
    	if (src == null) {
    		throw new NullPointerException();
    	}
    	
    	if (neighbors.get(tar) == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	if (edges.containsKey(data)) {
    		return true;
    	}
    	
    	edges.put(data, new EdgeContainer(src, tar));
    	neighbors.get(src).add(data);
    	edgeSize++;
    	return true;
    }
    
    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, <code>v</code>, to this graph if
     * this graph contains no vertex <code>u</code> such that <code>
     * u.equals(v)</code>. If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns <tt>false</tt>. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate vertices.
     *
     * @param v vertex to be added to this graph.
     *
     * @return <tt>true</tt> if this graph did not already contain the specified
     * vertex.
     *
     * @throws NullPointerException if the specified vertex is <code>
     * null</code>.
     */
    public boolean addVertex(V v) {
    	if (v == null) {
    		throw new NullPointerException();
    	}
    	
    	if (neighbors.containsKey(v)) {
    		return false;
    	}
    	
    	neighbors.put(v, new ArrayList<E>());
    	vSize++;
    	return true;
    }
    
    /**
     * Returns <tt>true</tt> if and only if this graph contains an edge going
     * from the source vertex to the target vertex. In undirected graphs the
     * same result is obtained when source and target are inverted. If any of
     * the specified vertices does not exist in the graph, or if is <code>
     * null</code>, returns <code>false</code>.
     *
     * @param src source vertex of the edge.
     * @param tar target vertex of the edge.
     *
     * @return <tt>true</tt> if this graph contains the specified edge.
     */
    public boolean containsEdge(V src, V tar) {
    	for (EdgeContainer ec : edges.values()) {
    		if (ec.src.equals(src) && ec.tar.equals(tar)) {
    			return true;
    		}
    	}
    	return true;
    }
    
    /**
     * Returns <tt>true</tt> if this graph contains the specified vertex. More
     * formally, returns <tt>true</tt> if and only if this graph contains a
     * vertex <code>u</code> such that <code>u.equals(v)</code>. If the
     * specified vertex is <code>null</code> returns <code>false</code>.
     *
     * @param v vertex whose presence in this graph is to be tested.
     *
     * @return <tt>true</tt> if this graph contains the specified vertex.
     */
    public boolean containsVertex(V v) {
    	return neighbors.containsKey(v);
    }
    
    /**
     * Returns a set of the edges contained in this graph. The set is backed by
     * the graph, so changes to the graph are reflected in the set. If the graph
     * is modified while an iteration over the set is in progress, the results
     * of the iteration are undefined.
     *
     *
     * @return a set of the edges contained in this graph.
     */
     public Set<E> edgeSet() {
    	 return edges.keySet();
     }
     
     /**
      * Returns a set of all edges touching the specified vertex. If no edges are
      * touching the specified vertex returns an empty set.
      *
      * @param vertex the vertex for which a set of touching edges is to be
      * returned.
      *
      * @return a set of all edges touching the specified vertex.
      *
      * @throws IllegalArgumentException if vertex is not found in the graph.
      * @throws NullPointerException if vertex is <code>null</code>.
      */
     public ArrayList<E> edgesOf(V v) {
    	 return neighbors.get(v);
     }
     
     /**
      * Removes the specified edge from the graph. Removes the specified edge
      * from this graph if it is present. More formally, removes an edge <code>
      * e2</code> such that <code>e2.equals(e)</code>, if the graph contains such
      * edge. Returns <tt>true</tt> if the graph contained the specified edge.
      * (The graph will not contain the specified edge once the call returns).
      *
      * <p>If the specified edge is <code>null</code> returns <code>
      * false</code>.</p>
      *
      * @param e edge to be removed from this graph, if present.
      *
      * @return <code>true</code> if and only if the graph contained the
      * specified edge.
      */
     public boolean removeEdge(E e) {

    	 if (!edges.containsKey(e)) {
    		 return false;
    	 }
    	 
    	 edges.remove(e);
    	 edgeSize--;
    	 return true;
     }
     
     /**
      * Removes the specified vertex from this graph including all its touching
      * edges if present. More formally, if the graph contains a vertex <code>
      * u</code> such that <code>u.equals(v)</code>, the call removes all edges
      * that touch <code>u</code> and then removes <code>u</code> itself. If no
      * such <code>u</code> is found, the call leaves the graph unchanged.
      * Returns <tt>true</tt> if the graph contained the specified vertex. (The
      * graph will not contain the specified vertex once the call returns).
      *
      * <p>If the specified vertex is <code>null</code> returns <code>
      * false</code>.</p>
      *
      * @param v vertex to be removed from this graph, if present.
      *
      * @return <code>true</code> if the graph contained the specified vertex;
      * <code>false</code> otherwise.
      */
     public boolean removeVertex(V v) {
    	 if (!neighbors.containsKey(v)) {
    		 return false;
    	 }
    	 
    	 neighbors.remove(v);
    	 vSize--;
    	 ArrayList<E> edgesToRemove = new ArrayList<E>();
    	 for (E e : edges.keySet()) {
    		 EdgeContainer ec = edges.get(e);
    		 if (ec.src.equals(v) || ec.tar.equals(v)) {
    			 edgesToRemove.add(e);
    		 }
    	 }
    	 for (E e : edgesToRemove) {
    		 edges.remove(e);
    	 }
    	 return true;
     }
     
     /**
      * Returns a set of the vertices contained in this graph. The set is backed
      * by the graph, so changes to the graph are reflected in the set. If the
      * graph is modified while an iteration over the set is in progress, the
      * results of the iteration are undefined.
      *
      *
      * @return a set view of the vertices contained in this graph.
      */
     public Set<V> vertexSet() {
    	 return neighbors.keySet();
     }
     
     /**
      * Returns the source vertex of an edge. For an undirected graph, source and
      * target are distinguishable designations (but without any mathematical
      * meaning).
      *
      * @param e edge of interest
      *
      * @return source vertex
      * 
      * @throws IllegalArgumentException if edge is not found in the graph.
      */
     public V getEdgeSource(E e) {
    	 if (!edges.containsKey(e)) {
    		 throw new IllegalArgumentException();
    	 }
    	 return edges.get(e).src;
     }

     /**
      * Returns the target vertex of an edge. For an undirected graph, source and
      * target are distinguishable designations (but without any mathematical
      * meaning).Adds the specified edge to this graph, going from the source vertex to the target vertex. More formally, adds the specified edge, e, to this graph if this graph contains no edge e2 such that e2.equals(e). If this graph already contains such an edge, the call leaves this graph unchanged and returns false. If the edge was added to the graph, returns true.

The source and target vertices must already be contained in this graph. If they are not found in graph IllegalArgumentException is thrown.
      *
      * @param e edge of interest
      *
      * @return target vertex
      * 
      * @throws IllegalArgumentException if edge is not found in the graph.
      */
     public V getEdgeTarget(E e) {
    	 if (!edges.containsKey(e)) {
    		 throw new IllegalArgumentException();
    	 }
    	 return edges.get(e).tar;
     }

     public int getVSize() { return vSize; }
     public int getEdgeSize ()  { return edgeSize; }
     
         
}