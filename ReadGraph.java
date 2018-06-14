import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
/*
 * Tests: see DrawPanel
 * test
 */
public class ReadGraph {
	private static Graph<Circle, Line> graph = new Graph<Circle, Line>();
	
	public static Graph<Circle, Line> readGraph(String filename) {
		int n=0;
		int m = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
		    try{
		    	n = Integer.parseInt(br.readLine());
		    }catch(NumberFormatException e){
		    	System.out.println("Format Error in the first line!");
		    	br.close();
		    	return new Graph<Circle, Line>();
		    }
		    try{
				m = Integer.parseInt(br.readLine());
		    }catch(NumberFormatException e){
		    	System.out.println("Format Error in the second line!");
		    	br.close();
		    	return new Graph<Circle, Line>();
		    }
			for(int i=0;i<n;i++) {
				graph.addVertex(createCircle(i));
			}
			String line = "";
			for(int i=0;i<m;i++) {
				line = br.readLine();
				String parts [] = line.split(" ");
				if(!addLineToGraph(parts, i, n)) {
			    	br.close();
			    	return new Graph<Circle, Line>();
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			System.out.println("Some fancy error occured. Unable to read the file!");
		}
		return graph;
	}
	private static Circle createCircle(int number) {
		Random rand = new Random();
		int x = rand.nextInt(500);
		int y = rand.nextInt(500);
		return new Circle(10, x, y, number);
	}
	private static boolean addLineToGraph(String parts [], int number, int size) {
		int information [] = new int [parts.length];
		for(int i=0;i<parts.length;i++) {
		    try{
		    	information[i] = Integer.parseInt(parts[i]);
		    }catch(NumberFormatException e){
		    	System.out.println("Format Error in the line "+number+3+"!");
		    	return false;
		    }
		}
		if(information[0]>size || information[1]>size) {
	    	System.out.println("Format Error in the line! This vertex does not exist (too high number)"+number+3+". This vertex does not exist (too high number)");
			return false;
		}
		Line l = new Line(getByNumber(information[0]), getByNumber(information[1]), information[2]);
		graph.addEdge(l, getByNumber(information[0]), getByNumber(information[1]));
		return true;
	}
    public static Circle getByNumber (int number) {
      	 Set<Circle> vSet = graph.vertexSet();
      	 for (Circle v : vSet) {
      		 if(number == v.getNumber()) {
      			 return v;
      		 }
      	 }
      	 return null;
       }
}
