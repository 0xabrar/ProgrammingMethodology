/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
            
		addComponentListener(this);
                entryList = new ArrayList<NameSurferEntry>();
		
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entryList.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
            if (!entryList.contains(entry)) {
                entryList.add(entry);
            }
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
                addBackground();
                
                /* Iteration repeats and adds to graph every entry. */
                for (int i = 0; i < entryList.size(); i++){
                    NameSurferEntry currentEntry = entryList.get(i);
                    plotEntry(currentEntry);
                }
                
	}
	
        /* This method will go and add every decade of a certain entry onto the graph. */
	private void plotEntry(NameSurferEntry entry) {
            
            Color color = getColor(entry);
            /* The seperator is the amounnt of space that can fit 1000 times between the 2 padded zones. */
            seperatorY = (this.getHeight() - (2 *GRAPH_MARGIN_SIZE)) / (double)MAX_RANK;
            
            
            /* For each decade that their is, a line is created connecting the 2 points. */
            for (int i = 0; i < NDECADES - 1; i++) {
                /* Coordinates of first point on line. */
                int currentDecadeRank = entry.getRank(i);
                double y1 = GRAPH_MARGIN_SIZE + (currentDecadeRank * seperatorY);
                double x1 = i * seperatorX;
                GLabel label = getLabel(entry, i);
                
                /* Coordinates of second point on line. */
                currentDecadeRank = entry.getRank(i + 1);
                double y2 = GRAPH_MARGIN_SIZE + (currentDecadeRank * seperatorY);
                double x2 = (i + 1) * seperatorX;
                
                /* Conditionals which check if either point on the line is 0, in which case the point will be 
                 * at the bottom of the graph. */
                if (entry.getRank(i) == 0) y1 = this.getHeight() - GRAPH_MARGIN_SIZE; 
                if (entry.getRank(i + 1) == 0) y2 = this.getHeight() - GRAPH_MARGIN_SIZE;
                /* Special conditional which is put in so that the last label may be added. */
                if (i == 9) {
                    GLabel labeltmp = getLabel(entry, i+1);
                    add(labeltmp, x2 + (seperatorX / 15), y2);
                }
                
                
                /* Everything is added to the Canvas. */
                GLine line = new GLine(x1, y1, x2, y2);
                line.setColor(color);
                add(line);
                add(label, x1 + (seperatorX / 15), y1);
                
            }
            
            
        }
        
        
        
        /* This method will add all of the background lines on the GCanvas. */
        private void addBackground() {
            /* Top left corner of the screen coordinates, with padding added. */
            int x1 = 0;
            int y1 = GRAPH_MARGIN_SIZE;
            /* Bottom right of the screen coordinates, with padding added. */
            int x2 = this.getWidth();
            int y2 = this.getHeight() - GRAPH_MARGIN_SIZE;
            
            
            add(new GLine(x1, y1, x2, y1));
            add(new GLine(x1, y2, x2, y2));
            
            /* This iterator will add in the vertical bars for the separate decades, as defined by NDECADES, as well as the dates. */
            seperatorX = this.getWidth() / (double) NDECADES;
            for (int i = 0; i < NDECADES; i++) {
                add(new GLine((i + 1) * seperatorX, 0, (i + 1) * seperatorX, this.getHeight()));
                
                
                /* A decade labl is added between the horizontal bars. */
                String decade = Integer.toString(START_DECADE + i * 10);
                double x = (i * seperatorX) + (seperatorX / 12);
                double y = this.getHeight() - (GRAPH_MARGIN_SIZE / 3);
                GLabel label = new GLabel(decade, x, y);
                add(label);
            }
        }
	
        /* Method which returns the name of the label which will be added to every decade entry. */
	private GLabel getLabel(NameSurferEntry entry, int whichDecade) {
            /* Gets the rank information of the entry. */
            String rank = Integer.toString(entry.getRank(whichDecade));
            if (rank.equals("0")) rank = "*";
            GLabel label = new GLabel(entry.getName() + " " + rank);
            return label;
        }
        
        /* Method which will aid in alternating the colours in the lines for the entries. */
        //THIS IS CURRENTLY ONLY A STUB//
        private Color getColor(NameSurferEntry entry) {
            /* Dependant on which number entry it is, the lines will alterante between 4 colours. */
            return Color.BLACK;
        }
        
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
        
        private ArrayList<NameSurferEntry> entryList;
        private double seperatorX;
        private double seperatorY;
        
}
