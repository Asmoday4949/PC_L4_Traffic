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
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(roadList.size());

	
	for (Road road : roadList)
	{
	    executor.scheduleAtFixedRate(road, 3, 3, TimeUnit.SECONDS);
	}
    }

}
