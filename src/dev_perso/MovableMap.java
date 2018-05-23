package dev_perso;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

public class MovableMap extends JPanel
{

    private static final long serialVersionUID = 1L;

    /**
     * JPanel where the car move (above the grid map)
     * 
     * @param map
     *            the grid map
     */
    public MovableMap(MyMap map)
    {
	this.setOpaque(false);
	this.setPreferredSize(map.getPreferredSize());
	listCar = new ArrayList<Car>();

	executor = Executors.newFixedThreadPool(20);

	this.createTimer();
    }

    private void createTimer()
    {
	this.drawingTimer = new Timer();

	TimerTask task = new TimerTask()
	{

	    @Override
	    public void run()
	    {
		repaint();
	    }

	};
	
	this.drawingTimer.scheduleAtFixedRate(task, 0, DRAWING_DELTA);

    }

    /**
     * initialize a car move
     * 
     * @param circ
     *            the path
     * @param car
     *            a car
     */
    public void runCar(PathRoad circ, Car car)
    {
	Road init = circ.getListCirc().get(0);
	car.setPosition(init.getPosCentX(), init.getPosCentY());
	listCar.add(car);
	executor.execute(new CarMover(this, circ, car, listCar, 20));
    }

    /**
     * Add a Car on the list of displayed Car
     * 
     * @param car
     *            a car
     */
    public void addCar(Car car)
    {
	listCar.add(car);
    }

    /**
     * Move a car
     * 
     * @param car
     *            a car
     */
    public void setCarPos(Car car, int x, int y)
    {
	int indice = listCar.indexOf(car);
	car.setPosition(x, y);
	listCar.set(indice, car);
    }

    /**
     * Remove a Car on the list of displayed Car
     * 
     * @param car
     *            a car
     */
    public void removeCar(Car car)
    {
	listCar.remove(car);
    }

    public void paintComponent(Graphics g)
    {
	super.paintComponent(g);

	for (Car car : listCar)
	{
	    g.setColor(car.getColor());
	    g.fillRect(car.getX(), car.getY(), 8, 8);
	    g.setColor(Color.BLACK);
	    g.drawRect(car.getX(), car.getY(), 8, 8);
	}
    }

    private List<Car> listCar;
    private Executor executor;
    private static final int DRAWING_DELTA = 1000 / 60;
    private Timer drawingTimer;
}
