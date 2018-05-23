package dev_perso;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import dev_perso.TraficLight.State;

public class Road extends JPanel implements Runnable, Iterable<Road>
{

    private static final long serialVersionUID = 1L;

    public enum RoadType
    {
	none, line_hori, line_vert, turn_left_top, turn_left_down, turn_right_down, turn_right_top, t_left, t_top, t_right, t_down, cross
    };

    /**
     * Init and draw a Road element
     * 
     * @param _type
     *            the type of the Road
     * @param _posX
     *            x position of the Road
     * @param _posY
     *            y position of the Road
     * @param _dim
     *            dimension of the Road
     * @param _nom
     *            display name of the Road
     */
    public Road(RoadType _type, int _posX, int _posY, int _dim, String _nom)
    {
	type = _type;
	posX = _posX;
	posY = _posY;
	dim = _dim;
	nom = _nom;

	this.lock = new ReentrantLock();
	this.isGreen = this.lock.newCondition();

	this.listConnectedRoad = new ArrayList<Road>();

	setPreferredSize(new Dimension(dim, dim));

	List<TraficLight> listTraficLight = new ArrayList<TraficLight>();

	if (type == RoadType.t_left)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP, State.ON));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN, State.OFF));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT, State.OFF));
	}
	else if (type == RoadType.t_top)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP, State.ON));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT, State.OFF));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT, State.OFF));
	}
	else if (type == RoadType.t_right)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP, State.ON));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT, State.OFF));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN, State.OFF));
	}
	else if (type == RoadType.t_down)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT, State.ON));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN, State.OFF));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT, State.OFF));
	}
	else if (type == RoadType.cross)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP, State.ON));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT, State.OFF));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN, State.OFF));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT, State.OFF));
	}

	this.circularBuffer = new CircularBuffer(listTraficLight);
	this.listQueuesTrafficLight = new ArrayList<ConcurrentLinkedQueue<CarMover>>(listTraficLight.size());

	for (int i = 0; i < listTraficLight.size(); i++)
	{
	    this.listQueuesTrafficLight.add(new ConcurrentLinkedQueue<CarMover>());
	}
    }

    public void paintComponent(Graphics g)
    {
	super.paintComponent(g); // paint parent's background
	setBackground(Color.WHITE); // set background color for this JPanel
	g.setColor(Color.BLACK);

	if (type == RoadType.line_hori)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.line_vert)
	{
	    g.drawLine(dim / 2, 0, dim / 2, dim);
	}
	else if (type == RoadType.turn_left_top)
	{
	    g.drawLine(0, dim / 2, dim / 2, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, 0);
	}
	else if (type == RoadType.turn_left_down)
	{
	    g.drawLine(0, dim / 2, dim / 2, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, dim);
	}
	else if (type == RoadType.turn_right_top)
	{
	    g.drawLine(dim / 2, dim / 2, dim / 2, 0);
	    g.drawLine(dim / 2, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.turn_right_down)
	{
	    g.drawLine(dim / 2, dim / 2, dim / 2, dim);
	    g.drawLine(dim / 2, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.t_left)
	{
	    g.drawLine(dim / 2, 0, dim / 2, dim);
	    g.drawLine(0, dim / 2, dim / 2, dim / 2);
	}
	else if (type == RoadType.t_top)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, 0);
	}
	else if (type == RoadType.t_right)
	{
	    g.drawLine(dim / 2, 0, dim / 2, dim);
	    g.drawLine(dim / 2, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.t_down)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, dim);
	}
	else if (type == RoadType.cross)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	    g.drawLine(dim / 2, 0, dim / 2, dim);

	}

	circularBuffer.draw(g);

	g.setColor(Color.BLACK);
	g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
	g.drawString(nom, dim / 2 + 5, dim / 2 + 10);
    }

    public void connect(Road road)
    {
	this.listConnectedRoad.add(road);
    }

    public int getPosCentX()
    {
	return posX * dim + dim / 2;
    }

    public int getPosCentY()
    {
	return posY * dim + dim / 2;
    }

    public String getNom()
    {
	return nom;
    }

    public void setType(RoadType _type)
    {
	type = _type;
	repaint();
    }

    public RoadType getType()
    {
	return type;
    }

    public boolean hasTraficLight()
    {
	return circularBuffer.size() > 0;
    }

    public void go(Road from, CarMover mover)
    {
	int indexRoad = this.listConnectedRoad.indexOf(from);

	if (indexRoad == -1)
	{
	    return;
	}

	if (this.listConnectedRoad.size() > 2)
	{
	    this.lock.lock();

	    ConcurrentLinkedQueue<CarMover> queue = this.listQueuesTrafficLight.get(indexRoad);

	    queue.add(mover);

	    while (this.circularBuffer.getTraficLight(indexRoad) != this.circularBuffer.getCurrent()
		    || queue.element() != mover)
	    {
		 
		
		try
		{
		    this.isGreen.await();
		}
		catch (InterruptedException e)
		{
		    e.printStackTrace();
		}
	    }

	    this.listQueuesTrafficLight.get(indexRoad).remove(mover);
	    this.isGreen.signal();

	    lock.unlock();
	}
    }

    @Override
    public void run()
    {
	circularBuffer.setNextGreen();

	lock.lock();
	try
	{
	    this.isGreen.signal();
	}
	finally
	{
	    lock.unlock();
	}
	this.repaint();
    }

    @Override
    public Iterator<Road> iterator()
    {
	return listConnectedRoad.iterator();
    }

    public Road getConnectedRoad(int index)
    {
	return listConnectedRoad.get(index);
    }

    public int getNumberOfConnectedRoads()
    {
	return this.listConnectedRoad.size();
    }

    private CircularBuffer circularBuffer;
    private List<ConcurrentLinkedQueue<CarMover>> listQueuesTrafficLight;
    private List<Road> listConnectedRoad;
    private RoadType type;
    private int posX;
    private int posY;
    private int dim;
    private String nom;
    private Condition isGreen;
    private Lock lock;
}
