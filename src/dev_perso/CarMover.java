/**
 * 
 */
package dev_perso;

import java.util.List;

/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description Mover of a car
 */
public class CarMover extends Thread
{

    /**
     * Threaded class that manage the moving
     * 
     * @param _movableMap
     *            the map
     * @param _circ
     *            the path to follow
     * @param _car
     *            the car to move
     * @param _listCar
     *            the list of all car on the map
     * @param _step
     *            part of the speed of the car (distance)
     * @param _sleep
     *            part of the speed of the car (time)
     */
    public CarMover(PathRoad _circ, Car _car, List<Car> _listCar, int _step)
    {
	circ = _circ;
	car = _car;
	listCar = _listCar;
	step = _step;
    }

    /**
     * Run the thread run each road part of the path
     */
    public void run()
    {
	List<Road> roadsList = circ.getListCirc();
	for (int i = 1; i <= circ.getListCirc().size() - 1; i++)
	{	    
	    runRoad(roadsList.get(i - 1), roadsList.get(i));
	    roadsList.get(i).go(roadsList.get(i - 1), this);
	}
	listCar.remove(car);
    }

    /**
     * Run from a road part to road part
     * 
     * @param dep
     *            departure
     * @param dest
     *            destination
     */
    private void runRoad(Road dep, Road dest)
    {
	int x = dep.getPosCentX();
	int y = dep.getPosCentY();

	int deltaX = (dest.getPosCentX() - x);
	int deltaY = (dest.getPosCentY() - y);

	for (int i = 1; i <= step; i++)
	{
	    x += deltaX / step;
	    y += deltaY / step;
	    
	    try
	    {
		sleep(SLEEP);
	    }
	    catch (InterruptedException e)
	    {
		e.printStackTrace();
	    }
	    
	    car.setPosition(x, y);
	}
    }

    private PathRoad circ;
    private Car car;
    private List<Car> listCar;
    private int step;
    private static final int SLEEP = 100;
}
