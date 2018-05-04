package dev_perso;

import java.awt.Color;

public class Car
{

    /**
     * Constructor of Car
     * 
     * @param _color
     *            the display color of a Car
     */
    public Car(Color _color)
    {
	color = _color;
	x = 0;
	y = 0;
    }

    public void setPosition(int _x, int _y)
    {
	x = _x;
	y = _y;
    }

    public int getX()
    {
	return x;
    }

    public int getY()
    {
	return y;
    }

    public Color getColor()
    {
	return color;
    }

    private Color color;
    private int x;
    private int y;

}
