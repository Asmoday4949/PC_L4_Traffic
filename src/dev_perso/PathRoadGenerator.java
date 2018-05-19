package dev_perso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dev_perso.Road.RoadType;

public class PathRoadGenerator
{
    public static List<Road> generate(final int pathsCounter, List<Road> roadsList)
    {
	List<Road> pathToFollow = new ArrayList<Road>();
	Random rand = new Random();

	int firstRoadIndex = rand.nextInt(roadsList.size() + 1); // Route de départ
	Road currentRoad = roadsList.get(0); // Route sur laquelle on se trouve
	Road lastRoad = null; // Route sur laquelle on est passé

	pathToFollow.add(currentRoad);

	// Créer autant de chemins que demandé
	for (int count = 0; count < pathsCounter; count++)
	{
	    RoadType type = currentRoad.getType();
	    Iterator<Road> itr = currentRoad.iterator();
	    Road nextRoad = null;

	    if (type == RoadType.line_hori || type == RoadType.line_vert || type == RoadType.turn_left_top
		    || type == RoadType.turn_left_down || type == RoadType.turn_right_top
		    || type == RoadType.turn_right_down)
	    {
		while (itr.hasNext() && nextRoad == null)
		{
		    Road road = itr.next();

		    if (road != lastRoad)
		    {
			nextRoad = road;
		    }
		}
	    }
	    else if (type == RoadType.t_left || type == RoadType.t_top || type == RoadType.t_right
		    || type == RoadType.t_down)
	    {
		int maxItr = rand.nextInt(2) + 1;

		while (itr.hasNext() && (maxItr > 0 || nextRoad == null))
		{
		    Road road = itr.next();

		    if (road != lastRoad)
		    {
			nextRoad = road;
			maxItr--;
		    }
		}
	    }
	    else if (type == RoadType.cross)
	    {
		int maxItr = rand.nextInt(3) + 1;

		while (itr.hasNext() && (maxItr > 0 || nextRoad == null))
		{
		    Road road = itr.next();

		    if (road != lastRoad)
		    {
			nextRoad = road;
			maxItr--;
		    }
		}
	    }

	    lastRoad = currentRoad;
	    currentRoad = nextRoad;
	    pathToFollow.add(currentRoad);
	}

	return pathToFollow;
    }

    private static List<Road> getCirc1(List<Road> roadList)
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

    private static List<Road> getCirc2(List<Road> roadList)
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

    private static List<Road> getCirc3(List<Road> roadList)
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

    private static List<Road> getCirc4(List<Road> roadList)
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

    private static List<Road> getCirc5(List<Road> roadList)
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
