/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description Entry point of the program. Create the simulator.
 */

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
    private static int maxWayPoint = 30;

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
	
	// Add the map to the main panel
	pan.add(map);

	// Button for adding a new car in the scene
	// with random color and a random path (minimum of 5 waypoints)
	JButton btnAddCar = new JButton("Add car");
	btnAddCar.addActionListener(new ActionListener()
	{

	    @Override
	    public void actionPerformed(ActionEvent arg0)
	    {
		Color color = PathRoadGenerator.generateRandomColor();
		Car car = new Car(color);
		int waypointsNb = PathRoadGenerator.generateNumberOfWayPoints(maxWayPoint);
		List<Road> path = PathRoadGenerator.generatePath(waypointsNb, roadList);
		PathRoad circ3 = new PathRoad(path);
		movableMap.runCar(circ3, car);
	    }
	});
	pan.add(btnAddCar);
	// ---------------------------
	
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
	
	Road gare = new Road(RoadType.turn_right_down, posX, posY, size, "Gare");
	roadList.add(gare);
	posX++;
	
	Road hopital = new Road(RoadType.line_hori, posX, posY, size, "Hopital");
	roadList.add(hopital);
	posX++;

	Road marche = new Road(RoadType.t_down, posX, posY, size, "Marché");
	roadList.add(marche);
	posX++;
	
	Road bureau = new Road(RoadType.line_hori, posX, posY, size, "Bureau");
	roadList.add(bureau);
	posX++;

	Road banque = new Road(RoadType.turn_left_down, posX, posY, size, "Banque");
	roadList.add(banque);
	posX = 0;
	posY++;

	Road ecole = new Road(RoadType.t_right, posX, posY, size, "Ecole");
	roadList.add(ecole);
	posX++;
	
	Road hotel = new Road(RoadType.line_hori, posX, posY, size, "Hôtel");
	roadList.add(hotel);
	posX++;

	Road fitness = new Road(RoadType.cross, posX, posY, size, "Fitness");
	roadList.add(fitness);
	posX++;

	Road magasin = new Road(RoadType.turn_left_down, posX, posY, size, "Magasin");
	roadList.add(magasin);
	posX++;

	Road kiosque = new Road(RoadType.line_vert, posX, posY, size, "Kiosque");
	roadList.add(kiosque);
	posX = 0;
	posY++;

	Road pharmacie = new Road(RoadType.turn_right_top, posX, posY, size, "Pharmacie");
	roadList.add(pharmacie);
	posX++;

	Road bibliotheque = new Road(RoadType.line_hori, posX, posY, size, "Bibliothèque");
	roadList.add(bibliotheque);
	posX++;

	Road police = new Road(RoadType.t_top, posX, posY, size, "Police");
	roadList.add(police);
	posX++;

	Road quincaillerie = new Road(RoadType.t_top, posX, posY, size, "Quincaillerie");
	roadList.add(quincaillerie);
	posX++;

	Road fleuriste = new Road(RoadType.turn_left_top, posX, posY, size, "Fleuriste");
	roadList.add(fleuriste);
	
	// Connect each road with its neighbors
	gare.connect(hopital);
	gare.connect(ecole);
	
	hopital.connect(marche);
	hopital.connect(gare);
	
	marche.connect(bureau);
	marche.connect(fitness);
	marche.connect(hopital);
	
	bureau.connect(banque);
	bureau.connect(marche);
	
	banque.connect(kiosque);
	banque.connect(bureau);
	
	ecole.connect(gare);
	ecole.connect(hotel);
	ecole.connect(pharmacie);
	
	hotel.connect(fitness);
	hotel.connect(ecole);
	
	fitness.connect(marche);
	fitness.connect(magasin);
	fitness.connect(police);
	fitness.connect(hotel);
	
	magasin.connect(quincaillerie);
	magasin.connect(fitness);
	
	kiosque.connect(banque);
	kiosque.connect(fleuriste);
	
	pharmacie.connect(ecole);
	pharmacie.connect(bibliotheque);
	
	bibliotheque.connect(police);
	bibliotheque.connect(pharmacie);
	
	police.connect(fitness);
	police.connect(quincaillerie);
	police.connect(bibliotheque);
	
	quincaillerie.connect(magasin);
	quincaillerie.connect(fleuriste);
	quincaillerie.connect(police);
	
	fleuriste.connect(kiosque);
	fleuriste.connect(quincaillerie);

	return roadList;
    }
}
