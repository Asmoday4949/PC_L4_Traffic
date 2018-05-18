package dev_perso;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import dev_perso.Road.RoadType;

public class Simu
{

    private static int size = 200;

    public static void main(String[] args)
    {
	JFrame myGui = new JFrame();

	// Generate the grid
	List<Road> roadList = genRoad();

	JLayeredPane layeredPane = new JLayeredPane();
	JPanel pan = new JPanel();

	// Create the map
	MyMap map = new MyMap(3, 5, roadList);

	// The exemple light manager
	TraficLightManager tfm = new TraficLightManager(roadList);

	// Create the map where the Car move
	MovableMap movableMap = new MovableMap(map);

	// ---------------------------
	// Exemple d'utilisation feu on/off
	pan.add(map);
	JButton button = new JButton("Feu 1 ON");
	button.addActionListener(new ActionListener()
	{

	    @Override
	    public void actionPerformed(ActionEvent arg0)
	    {
		Road r6 = roadList.get(5);
		TraficLight tr = r6.getListTraficLight().get(0);
		tr.setState(TraficLight.State.ON);
		r6.repaint();
	    }
	});
	pan.add(button);

	JButton button2 = new JButton("Feu 1 OFF");
	button2.addActionListener(new ActionListener()
	{

	    @Override
	    public void actionPerformed(ActionEvent arg0)
	    {
		Road r6 = roadList.get(5);
		TraficLight tr = r6.getListTraficLight().get(0);
		tr.setState(TraficLight.State.OFF);
		r6.repaint();
	    }
	});
	pan.add(button2);

	// Exemple d'utilisation
	// Moving car CYAN
	Car car1 = new Car(Color.CYAN);
	List<Road> listCirc1 = getCirc2(roadList);
	PathRoad circ1 = new PathRoad(listCirc1);
	movableMap.runCar(circ1, car1);

	pan.setBounds(0, 0, pan.getPreferredSize().width, pan.getPreferredSize().height);
	pan.setOpaque(false);
	layeredPane.add(pan, 0, Integer.valueOf(0));

	movableMap.setBounds(5, 5, movableMap.getPreferredSize().width, movableMap.getPreferredSize().height);
	layeredPane.add(movableMap, 0, Integer.valueOf(1));
	layeredPane.moveToFront(movableMap);
	layeredPane.setPreferredSize(pan.getPreferredSize());

	myGui.getContentPane().add(layeredPane);
	myGui.setTitle("Traffic Simulator");
	myGui.pack();
	myGui.setVisible(true);
    }

    /**
     * Generate the grid
     * 
     * @return list of Road
     */
    private static List<Road> genRoad()
    {
	List<Road> roadList = new ArrayList<Road>();

	int posX = 0;
	int posY = 0;
	roadList.add(new Road(RoadType.turn_right_down, posX, posY, size, "Gare"));
	posX++;

	roadList.add(new Road(RoadType.line_hori, posX, posY, size, "Hopital"));
	posX++;

	roadList.add(new Road(RoadType.t_down, posX, posY, size, "Marché"));
	posX++;

	roadList.add(new Road(RoadType.line_hori, posX, posY, size, "Bureau"));
	posX++;

	roadList.add(new Road(RoadType.turn_left_down, posX, posY, size, "Banque"));
	posX = 0;
	posY++;

	roadList.add(new Road(RoadType.t_right, posX, posY, size, "Ecole"));
	posX++;

	roadList.add(new Road(RoadType.line_hori, posX, posY, size, "Hôtel"));
	posX++;

	roadList.add(new Road(RoadType.cross, posX, posY, size, "Fitness"));
	posX++;

	roadList.add(new Road(RoadType.turn_left_down, posX, posY, size, "Magasin"));
	posX++;

	roadList.add(new Road(RoadType.line_vert, posX, posY, size, "Kiosque"));
	posX = 0;
	posY++;

	roadList.add(new Road(RoadType.turn_right_top, posX, posY, size, "Pharmacie"));
	posX++;

	roadList.add(new Road(RoadType.line_hori, posX, posY, size, "Bibliothèque"));
	posX++;

	roadList.add(new Road(RoadType.t_top, posX, posY, size, "Police"));
	posX++;

	roadList.add(new Road(RoadType.t_top, posX, posY, size, "Quincaillerie"));
	posX++;

	roadList.add(new Road(RoadType.turn_left_top, posX, posY, size, "Fleuriste"));

	return roadList;
    }

    /**
     * Exemple of a generate path
     * 
     * @param roadList
     * @return
     */
    private static List<Road> getCirc1(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(0));
	listCirc.add(roadList.get(1));
	listCirc.add(roadList.get(2));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(14));

	return listCirc;
    }

    private static List<Road> getCirc2(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(4));
	listCirc.add(roadList.get(9));
	listCirc.add(roadList.get(14));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(8));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(8));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(6));
	
	return listCirc;
    }
    
    private static List<Road> getCirc3(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(2));
	listCirc.add(roadList.get(1));
	listCirc.add(roadList.get(0));
	listCirc.add(roadList.get(5));
	listCirc.add(roadList.get(6));
	listCirc.add(roadList.get(7));

	return listCirc;
    }
    
    private static List<Road> getCirc4(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(8));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(11));
	listCirc.add(roadList.get(10));
	listCirc.add(roadList.get(5));
	listCirc.add(roadList.get(0));
	listCirc.add(roadList.get(1));
	listCirc.add(roadList.get(2));

	return listCirc;
    }
    
    private static List<Road> getCirc5(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(2));
	listCirc.add(roadList.get(3));
	listCirc.add(roadList.get(4));
	listCirc.add(roadList.get(9));
	listCirc.add(roadList.get(14));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(2));

	return listCirc;
    }
}
