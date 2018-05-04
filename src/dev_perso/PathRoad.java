package dev_perso;

import java.util.List;

public class PathRoad {

	/**
	 * Aggregate a list of Road to a PathRoad object
	 * @param _listCirc
	 */
	public PathRoad(List<Road> _listCirc) {
		listCirc = _listCirc;
	}

	public List<Road> getListCirc() {
		return listCirc;
	}

	private List<Road> listCirc;

}
