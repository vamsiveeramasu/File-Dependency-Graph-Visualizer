/*
 * Vamsi Veeramasu
 * 12/15/2019
 * This class represents the Directed Graph. It uses an adjacency list composed of an arraylist of an arraylist of integers. 
 * I use a hashmap to convert the string vertices to integers to facilitate easier manipulation for topological sorting and cycle detection. I use another hasmap to convert back
 * from integer to vertex T (string) so that I can return the topological sort in terms of the vertices instead of their integer representations. 
 * The class also has a method to check for cycles, and calls that  method when the topological sort method is called. 
 */
import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DGraph<T> {
	private ArrayList<ArrayList<Integer>> adjacencyList;
	private HashMap<T, Integer> mapToInteger;
	private HashMap<Integer, T> mapToVertex;
	private int count = 0;
	
	public DGraph() {
		adjacencyList = new ArrayList<ArrayList<Integer>>();
		mapToInteger = new HashMap<T, Integer>(); //I use two maps so that I can convert either way, which I use when I want to display the vertices in the topological sort. 
		mapToVertex = new HashMap<Integer, T>();
	}
	
	public void addVertex(T vertex) {
		if (!mapToInteger.containsKey(vertex)) { //Ensuring the vertex doesn't already exist,and updating all the representations of the graph. 
			mapToInteger.put(vertex, count);
			mapToVertex.put(count , vertex);
			count++;
			adjacencyList.add((new ArrayList<Integer>()));
		}
	}
	
	public void addEdge(T vertexFrom, T vertexTo) {
		adjacencyList.get(mapToInteger.get(vertexFrom)).add(mapToInteger.get(vertexTo));
	}
	
	public void topSortUtil(int vertex, boolean[] visited, Stack<Integer> stack) { //recursive helper method for topSort
		if(visited[vertex])
			return;
		
		visited[vertex] = true;
		for (int i = 0; i < adjacencyList.get(vertex).size(); i++) {
			if (!visited[adjacencyList.get(vertex).get(i)]) {
				topSortUtil(adjacencyList.get(vertex).get(i), visited, stack);
			}
		}
		stack.push(new Integer(vertex));
	}
	
	public String topSort(T vertex) throws InvalidInputException, ContainsCycleException {//the method to find the topological sort, which calls the recursive helper method. 
		if(!mapToInteger.containsKey(vertex))
			throw new InvalidInputException();
		if(hasCycle())
			throw new ContainsCycleException();
		Stack<Integer> stack = new Stack<Integer>();
		boolean visited[] = new boolean[adjacencyList.size()];
		
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		
		topSortUtil(mapToInteger.get(vertex), visited, stack);
				
		String sorted = "";
		while (!stack.empty()) {
			sorted += mapToVertex.get(stack.pop()) + " ";
		}
		System.out.println(sorted);
		return sorted;
	}
	public boolean hasCycle() { //The method to determine whether the graph has a cycle. 
		boolean[] visited = new boolean[count];
		boolean[] recursionStack = new boolean[count]; //Uses two boolean arrays to check whether a vertex has already been visited, and whether it's already been in the recursive stack.
		
		for(int k = 0; k < count; k++) {
			if(hasCycleHelper(k, visited, recursionStack))
				return true;
		}
		return false;
	}
	public boolean hasCycleHelper(int i, boolean[] visited, boolean[] recursionStack) { //The recursive helper method to determine whether the graph contains a cycle. 
		if(recursionStack[i])
			return true;
		
		if(visited[i])
			return false;
		
		visited[i] = true;
		recursionStack[i] = true;
		
		for(int k = 0; k < adjacencyList.get(i).size(); k++) {
			int l = adjacencyList.get(i).get(k);
			if(hasCycleHelper(l, visited, recursionStack))
				return true;
		}
		
		recursionStack[i] = false;
		return false;
	} 
}
