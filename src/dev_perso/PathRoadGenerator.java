/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description Generator of paths.
 */

package dev_perso;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PathRoadGenerator
{
    /**
     * Generate a random path
     * @param waypointsNb	Number of waypoints
     * @param roadsList		List of roads in the scene
     * @return			List of roads which define the path to follow
     */
    public static List<Road> generatePath(int waypointsNb, List<Road> roadsList)
    {
	List<Road> pathToFollow = new ArrayList<Road>();
	Random rand = new Random();
	
	int currentRoadIndex = rand.nextInt(roadsList.size());
	Road currentRoad = roadsList.get(currentRoadIndex);
	int lastRoadIndex = rand.nextInt(currentRoad.getNumberOfConnectedRoads());
	Road lastRoad = roadsList.get(lastRoadIndex);
	
	for (int count = 0; count < waypointsNb; count++)
	{
	    Road nextRoad = null;
	    int nextRoadIndex = -1;
	    
	    pathToFollow.add(currentRoad);
	    
	    do
	    {
		nextRoadIndex = rand.nextInt(currentRoad.getNumberOfConnectedRoads());
		nextRoad = currentRoad.getConnectedRoad(nextRoadIndex);
	    }
	    while(lastRoad == nextRoad);
	    
	    lastRoad = currentRoad;
	    currentRoad = nextRoad;
	}

	return pathToFollow;
    }
    
    /**
     * Generate a random number for the number of waypoints
     * @param max	Max value to generate
     * @return		Random value between 5 and max
     */
    public static int generateNumberOfWayPoints(int max)
    {
	final int min = 5;
	return generateNumberOfWayPoints(min, max);
    }
    
    /**
     * Generate a random number for the number of waypoints
     * @param min	Min value to generate
     * @param max	Max value to generate
     * @return		Random value between min and max
     */
    public static int generateNumberOfWayPoints(int min, int max)
    {
	Random rand = new Random();
	return rand.nextInt(max-min)+min;
    }

    /**
     * Generate a random color
     * @return	Random color
     */
    public static Color generateRandomColor()
    {
	Random rand = new Random();
	float r = rand.nextFloat();
	float g = rand.nextFloat();
	float b = rand.nextFloat();
	
	return new Color(r,g,b);
    }
}
