package dev_perso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dev_perso.Road.RoadType;

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
     * @param max	Max value to generate (not include)
     * @return		Random value between 0 and max
     */
    public static int generateNumberOfWayPoints(int max)
    {
	Random rand = new Random();
	return rand.nextInt(max);
    }

    public static List<Road> getCirc1(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(0));
	listCirc.add(roadList.get(1));
	listCirc.add(roadList.get(2));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(14));

	return listCirc;
    }

    public static List<Road> getCirc2(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(4));
	listCirc.add(roadList.get(9));
	listCirc.add(roadList.get(14));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(8));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(8));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(6));

	return listCirc;
    }

    public static List<Road> getCirc3(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(2));
	listCirc.add(roadList.get(1));
	listCirc.add(roadList.get(0));
	listCirc.add(roadList.get(5));
	listCirc.add(roadList.get(6));
	listCirc.add(roadList.get(7));

	return listCirc;
    }

    public static List<Road> getCirc4(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(8));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(11));
	listCirc.add(roadList.get(10));
	listCirc.add(roadList.get(5));
	listCirc.add(roadList.get(0));
	listCirc.add(roadList.get(1));
	listCirc.add(roadList.get(2));

	return listCirc;
    }

    public static List<Road> getCirc5(List<Road> roadList)
    {
	List<Road> listCirc = new ArrayList<Road>();
	listCirc.add(roadList.get(2));
	listCirc.add(roadList.get(3));
	listCirc.add(roadList.get(4));
	listCirc.add(roadList.get(9));
	listCirc.add(roadList.get(14));
	listCirc.add(roadList.get(13));
	listCirc.add(roadList.get(12));
	listCirc.add(roadList.get(7));
	listCirc.add(roadList.get(2));

	return listCirc;
    }
}
