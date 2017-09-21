package it.infinity.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.infinity.application.Memory;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 8888L;
	
	private JLabel labelFreePhysicalMemory;
	private JLabel labelTotalPhysicalMemory;
	
	public MainFrame() {
		initialize();
		
		new Memory().start();
	}
	
	private void initialize() {
		labelFreePhysicalMemory = new JLabel("Free physical memory: -");
		labelTotalPhysicalMemory = new JLabel("Total physical memory: -");
		
		JPanel mainPanel = new JPanel();
		mainPanel.add(labelFreePhysicalMemory);
		mainPanel.add(labelTotalPhysicalMemory);
		
		this.getContentPane().add(mainPanel);
		this.pack();
	}
	
}
