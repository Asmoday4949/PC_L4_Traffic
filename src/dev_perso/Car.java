package dev_perso;

import java.awt.Color;
/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description graphical representation of the car
 */
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
    
    /**
     * set the position x y of the car
     * @param _x
     * @param _y
     */
    public void setPosition(int _x, int _y)
    {
	x = _x;
	y = _y;
    }
    
    /**
     * @return x position
     */
    public int getX()
    {
	return x;
    }
    
    /**
     * @return y position
     */
    public int getY()
    {
	return y;
    }
    
    /**
     * @return color of the car
     */
    public Color getColor()
    {
	return color;
    }

    private Color color;
    private int x;
    private int y;

}
