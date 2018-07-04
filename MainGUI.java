import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainGUI {

	private static DrawPanel drawPanel;

	public static void initGUI() {
		JFrame frame = new JFrame("GraphGUI");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLayout(new BorderLayout(5, 5));

		drawPanel = new DrawPanel();
		frame.add(drawPanel);

		JPanel buttonPane = new JPanel();
		JButton btn = new JButton("Add Line");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createAddLineFrame();
			}
		});
		JButton btnRunAlgorithm = new JButton("run Algorithm");
		btnRunAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean works = drawPanel.performAlgorithm(0);
				if(!works) {
					JOptionPane.showMessageDialog(null, "Please enter a graph first!");
				}
			}
		});

		final JButton btnReadFile = new JButton("read Graph File");
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(btnReadFile);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					drawPanel.readIn(selectedFile.getAbsolutePath());
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					// drawPanel.drawGraph(selectedFile.getAbsolutePath());
				} else {
					System.out.println("Open command cancelled by user.");
				}

			}
		});
		JButton btnCreateRandomGraph = new JButton("Create Random Graph");
		btnCreateRandomGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = drawPanel.createRandomGraph();
				switch(error) {
				case 1: {
					JOptionPane.showMessageDialog(null, "Source vertice of an edge does not exist!");
					break;
					}
				case 2:  {
					JOptionPane.showMessageDialog(null, "A Target vertice of an edge does not exist!");
					break;		
					}
				}
			}
				
		});
		JButton btnRemoveGraph = new JButton("Remove Graph");
		btnRemoveGraph.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawPanel.removeGraph();		
			}
		});
		buttonPane.add(btnRemoveGraph);
		buttonPane.add(btnCreateRandomGraph);
		buttonPane.add(btnReadFile);
		buttonPane.add(btn);
		buttonPane.add(btnRunAlgorithm);
		frame.add(buttonPane, BorderLayout.NORTH);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // Note 1
			public void run() {
				initGUI();
			}
		});
	}

	public static void createAddLineFrame() {
		final JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());

		final JTextField txtC1 = new JTextField(1);
		final JTextField txtC2 = new JTextField(1);
		final JTextField txtC3 = new JTextField(1);

		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnAddLine = new JButton("add");
		btnAddLine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int cid1 = Integer.parseInt(txtC1.getText());
				int cid2 = Integer.parseInt(txtC2.getText());
				int cid3 = Integer.parseInt(txtC3.getText());
				drawPanel.addLine(cid1, cid2, cid3);
				frame.dispose();
			}
		});

		frame.add(txtC1);
		frame.add(txtC2);
		frame.add(txtC3);
		frame.add(btnCancel);
		frame.add(btnAddLine);
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
	/*
	 * 0 = cancel
	 * 1 = bfs
	 * 2 = dfs
	 *
	public static void createAlgorithmFrame() {
		final JFrame frame = new JFrame ();
		frame.setLayout(new FlowLayout());

		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnBFS = new JButton("Breadth-First Search");
		btnBFS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawPanel.performAlgorithm(1);
				frame.dispose();
			}
		});

		frame.add(txtC1);
		frame.add(txtC2);
		frame.add(btnCancel);
		frame.add(btnAddLine);
		frame.setSize(300, 100);
		frame.setVisible(true);
	} */
	
}
