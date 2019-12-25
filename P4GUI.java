/*Vamsi Veeramasu
 * 12/15/2019
 * This class generates the GUI, and contains the main method which runs the whole program. It also contains a method to build the graph from the user specified input file. 
 * The code looks out for certain exceptions, catches them, and displays the appopriate dialogue box to alert the user as to the error. 
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class P4GUI extends JFrame {
	private JLabel inputFile = new JLabel("Input file name: ");
	private JLabel classToRecompile = new JLabel("Class to recompile ");
	private JTextField fileInput = new JTextField("");
	private JTextField recompileInput = new JTextField("");
	private JTextField output = new JTextField("");
	private JButton build = new JButton("Build Directed Graph");
	private JButton order = new JButton("Topological Order");
	
	public P4GUI() {
		super("Class Dependency Graph");
		setLayout(new GridLayout(2, 1, 0, 5));
		setSize(700,400);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		
		JPanel labels = new JPanel();
		labels.setLayout(new BorderLayout());
		labels.add(inputFile, BorderLayout.NORTH);
		labels.add(classToRecompile, BorderLayout.SOUTH);
		
		JPanel textBoxes = new JPanel();
		textBoxes.setLayout(new BorderLayout());
		fileInput.setColumns(20);
		recompileInput.setColumns(20);
		textBoxes.add(fileInput, BorderLayout.NORTH);
		textBoxes.add(recompileInput, BorderLayout.SOUTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		buttons.add(build, BorderLayout.NORTH);
		buttons.add(order, BorderLayout.SOUTH);
		
		top.add(labels);
		top.add(textBoxes);
		top.add(buttons);
		
		Border topBorder = BorderFactory.createTitledBorder("");
		top.setBorder(topBorder);
		add(top);
		
		JPanel bottom = new JPanel();

		output.setEditable(false);
		output.setBackground(getBackground());
		output.setColumns(50);	//To give the textfield more room to display the topological sort. 
		
		
		bottom.add(output);
		String title = "Recompilation Order";
		Border bottomBorder = BorderFactory.createTitledBorder(title);
		bottom.setBorder(bottomBorder);
		add(bottom);
		
		build.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DGraph<String> graph = new DGraph<String>(); 
				
				try {
					graph = buildDGraphFromFile(fileInput.getText()); //Building the graph from the input file
					JOptionPane.showMessageDialog(null, "Graph Built Successfully");
				}
				catch(EmptyInputException e1) { //Custom OptionPanes to show the user what error was caught, and for what reason. 
					JOptionPane.showMessageDialog(null, e1);
				}
				catch(FileNotFoundException e2) {
					JOptionPane.showMessageDialog(null, "File Did Not Open");
				}
			}
		});
		
		order.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DGraph<String> graph = new DGraph<String>();
				try {
					graph = buildDGraphFromFile(fileInput.getText()); //Building the graph again so that the program has access to the built graph in this method. 
									}
				catch(EmptyInputException e1) { //Custom OptionPanes to show the user what error was caught, and for what reason. 
					JOptionPane.showMessageDialog(null, e1);
				}
				catch(FileNotFoundException e2) {
					JOptionPane.showMessageDialog(null, "File Did Not Open");
				}
				try {
					output.setText(graph.topSort(recompileInput.getText())); //Outputting the topological sort to the GUI. 
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, "Invalid Class Name: ");
				} catch (ContainsCycleException e1) {
					JOptionPane.showMessageDialog(null, "Graph Contains Cycle");
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void display() {
		setVisible(true);
	}
	public DGraph<String> buildDGraphFromFile(String file) throws FileNotFoundException, EmptyInputException{ //The method to build the graph from the user provided input file. 
		DGraph<String> graph = new DGraph<String>();
		
		Scanner infile = new Scanner(new File(file));
		while (infile.hasNextLine()) {
			String line = infile.nextLine();
			String[] vertices = line.split(" "); //Breaking up the vertices through the space delimeter. 
			
			graph.addVertex(vertices[0]);  //Adding the first Vertex outside the for loop so I can avoid complicated code to put everything in the loop, since I won't be calling
											//addEdge after reading just the first Vertex. 
			for (int i = 1; i < vertices.length; i++) {
				graph.addVertex(vertices[i]);
				graph.addEdge(vertices[0], vertices[i]);
			}
		}
		
		//System.out.println("done");
		return graph;
		
	}
	public static void main(String[] args) {
		P4GUI gui = new P4GUI();
		gui.display();
	}
}
