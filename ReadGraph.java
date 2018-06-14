import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
/*
 * Tests: see DrawPanel
 * 
 */
public class ReadGraph {
	private static Graph<Circle, Line> graph = new Graph<Circle, Line>();
	
	public static Graph<Circle, Line> readGraph(String filename) throws Exception {
		int n=0;
		int m = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
		    try{
		    	n = Integer.parseInt(br.readLine());
		    }catch(NumberFormatException e){
		    	br.close();
		    	throw new Exception("Format Error in the first line!");
		    }
		    try{
				m = Integer.parseInt(br.readLine());
		    }catch(NumberFormatException e){
		    	br.close();
		    	throw new Exception("Format Error in the second line!");
		    }
			for(int i=0;i<n;i++) {
				graph.addVertex(createCircle(i));
			}
			String line = "";
			for(int i=0;i<m;i++) {
				line = br.readLine();
				if(line == null) {
					br.close();
					throw new Exception("There is not enaugh edge data in the file.\n"
							+ "Either put "+(m-i)+" lines of edge data into your file or change \""+m+"\" in line 2 to \""+i+"\"!");
				}
				String parts [] = line.split(" ");
				try {
					addLineToGraph(parts, i, n);
				}
				catch (Exception e){
					br.close();
					throw e;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new Exception("File not found!");
		} catch (IOException e) {
			throw new Exception("Some fancy error occured. Unable to read the file!");
		}
		return graph;
	}
	private static Circle createCircle(int number) {
		Random rand = new Random();
		int x = rand.nextInt(500);
		int y = rand.nextInt(500);
		return new Circle(10, x, y, number);
	}
	private static void addLineToGraph(String parts [], int number, int size) throws Exception {
		int information [] = new int [parts.length];
		for(int i=0;i<parts.length;i++) {
		    try{
		    	information[i] = Integer.parseInt(parts[i]);
		    }catch(NumberFormatException e){
		    	throw new Exception("Format Error in the line "+(number+3)+"!");

		    }
		}
		if(information[0]>size || information[1]>size) {
	    	throw new Exception("Format Error in the line "+(number+3)+"!");
		}
		Line l = new Line(getByNumber(information[0]), getByNumber(information[1]), information[2]);
		graph.addEdge(l, getByNumber(information[0]), getByNumber(information[1]));
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
