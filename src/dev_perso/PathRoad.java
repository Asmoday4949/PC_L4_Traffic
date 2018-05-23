package dev_perso;

import java.util.List;

/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description representation of a list of road that represent a path
 */
public class PathRoad
{

    /**
     * Aggregate a list of Road to a PathRoad object
     * 
     * @param _listCirc
     */
    public PathRoad(List<Road> _listCirc)
    {
	listCirc = _listCirc;
    }
    
    /**
     * @return list of road
     */
    public List<Road> getListCirc()
    {
	return listCirc;
    }

    private List<Road> listCirc;

}
