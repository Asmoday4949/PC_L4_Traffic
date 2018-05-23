package dev_perso;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description management of all the traffic light (starting threads)
 */
public class TraficLightManager
{

    /**
     * start the thread for every road to manage the traffic light
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
