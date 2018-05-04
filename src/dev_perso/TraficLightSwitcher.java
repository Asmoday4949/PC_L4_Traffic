package dev_perso;

import java.util.List;

public class TraficLightSwitcher extends Thread
{

    /**
     * An simple example of a switch on all light in a Road
     * 
     * @param _road
     */
    public TraficLightSwitcher(Road _road)
    {
	road = _road;
    }

    /**
     * Thread that switch all light in a Road
     */
    public void run()
    {
	List<TraficLight> lights = road.getListTraficLight();
	for (TraficLight feu : lights)
	{
	    feu.switchLight();
	}
	road.repaint();
    }

    private Road road;
}
