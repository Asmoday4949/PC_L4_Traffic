package dev_perso;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TraficLightManager
{

    /**
     * An simple example of a trafic light manager
     * 
     * @param roadList
     */
    public TraficLightManager(List<Road> roadList)
    {
	ScheduledExecutorService execute = Executors.newSingleThreadScheduledExecutor();

	Random r = new Random();
	for (Road road : roadList)
	{
	    List<TraficLight> lights = road.getListTraficLight();
	    for (TraficLight feu : lights)
	    {
		if (r.nextInt() % 2 + 1 == 1)
		    feu.setState(TraficLight.State.OFF);
		else
		    feu.setState(TraficLight.State.ON);
	    }
	}

	for (Road road : roadList)
	{
	    execute.scheduleAtFixedRate(new TraficLightSwitcher(road), 0, 3, TimeUnit.SECONDS);
	}

    }

}
