/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

        /* Required main method. */
        public static void main(String[] args) {
            new NameSurfer().start(args);
        }
        
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {

		/*
		 * All of the labels at the bottom of the screen are added. It is
		 * important to note that name's ActionCommand is set.
		 */
		add(new JLabel("Name: "), SOUTH);
		name = new JTextField(16);
		name.setActionCommand("Graph");
		add(name, SOUTH);
		add(new JButton("Graph"), SOUTH);
		add(new JButton("Clear"), SOUTH);
                
                /* Initializes the name database, and adds the NameSurgerGraph. */
                graph = new NameSurferGraph();
                graph.update();
                add(graph);
                database = new NameSurferDataBase(NAMES_DATA_FILE);
                
		/*
		 * ActionListeners are added in so that the buttons in the program may
		 * function.
		 */
		name.addActionListener(this);
		addActionListeners();    
                
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		/* Action to be performed once the user decides to graph a name. */
		if (e.getActionCommand().equals("Graph")) {
			/* New NameSurferEntry is created based on which name was entered. */
			NameSurferEntry entry = database.findEntry(name.getText());
                            if (entry != null) {
                                graph.addEntry(entry);
                                graph.update();
                            }
                            
                        
                        
		}
		/* Remove all the graphed material. */
		if (e.getActionCommand().equals("Clear")) {
			graph.clear();
                        graph.update();
		}
	}

	
        private NameSurferDataBase database;
        private NameSurferGraph graph;
	private JTextField name;    
}
