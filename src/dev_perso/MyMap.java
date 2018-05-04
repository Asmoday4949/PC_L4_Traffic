package dev_perso;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;

public class MyMap extends JPanel
{

    private static final long serialVersionUID = 1L;

    /**
     * Generate the grid with the list of road
     * 
     * @param row
     *            nb row
     * @param cols
     *            nb cols
     * @param listRoad
     *            list of the road that are in the grid
     */
    public MyMap(int row, int cols, List<Road> listRoad)
    {

	JPanel roadPanel = new JPanel();

	roadPanel.setLayout(new GridLayout(row, cols));
	for (Road road : listRoad)
	{
	    roadPanel.add(road);
	}

	this.add(roadPanel);
    }
}
