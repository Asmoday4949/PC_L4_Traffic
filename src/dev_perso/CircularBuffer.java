package dev_perso;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import dev_perso.TraficLight.State;

/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description circle buffer of traffic light
 */

public class CircularBuffer
{
    /**
     * Constructor
     * @param list of traffic light of a road
     */
    public CircularBuffer(List<TraficLight> list)
    {
	this.listTraficLights = list;
	
	if(!this.listTraficLights.isEmpty())
	{
	    this.current = list.get(0);  
	}
	else
	{
	    this.current = null;
	}
    }
    
    /**
     * change to green the next traffic light
     * only one green per road
     */
    public synchronized void setNextGreen()
    {
	Iterator<TraficLight> it = listTraficLights.iterator();
	
	while (it.hasNext())
	{
	    TraficLight traficLight = it.next();
	    
	    traficLight.setState(State.OFF);
	    
	    if(traficLight == this.current && it.hasNext())
	    {
		traficLight = it.next();
		traficLight.setState(State.ON);
		this.current = traficLight;
	    }
	    else if(traficLight == this.current)
	    {
		listTraficLights.get(0).setState(State.ON);
		this.current = listTraficLights.get(0);
	    }
	}
    }
    
    /**
     * get the current green light
     * @return the traffic ligh
     */
    public synchronized TraficLight getCurrent()
    {
	return this.current;
    }
    
    /**
     * get the traffic light at the specified index
     * @param index
     * @return 
     */
    public synchronized TraficLight getTraficLight(int index)
    {
	return this.listTraficLights.get(index);
    }
    
    
    /**
     * return the number of traffic light
     * @return number of traffic light
     */
    public int size()
    {
	return this.listTraficLights.size();
    }
    
    /**
     * draw all the traffic lights
     * @param g graphic context
     */
    public void draw(Graphics g)
    {
	for (TraficLight light : listTraficLights)
	{
	    light.traficLightDraw(g);
	}
    }
    
    private TraficLight current;
    private List<TraficLight> listTraficLights;
}
