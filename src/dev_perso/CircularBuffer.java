package dev_perso;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import dev_perso.TraficLight.State;

public class CircularBuffer
{

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

    public synchronized TraficLight getCurrent()
    {
	return this.current;
    }
    
    public int size()
    {
	return this.listTraficLights.size();
    }
    
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
