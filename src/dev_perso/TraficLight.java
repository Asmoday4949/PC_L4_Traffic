/**
 * @author Lucas Bulloni, Malik Fleury
 * @date 23.05.2017
 * @description Represent a trafic light for T and cross roads.
 */

package dev_perso;

import java.awt.Color;

import java.awt.Graphics;

public class TraficLight
{
    public enum State
    {
	OFF, ON
    };

    public enum Position
    {
	UP, RIGHT, DOWN, LEFT
    };

    /**
     * Init and draw a TraficLight
     * 
     * @param _dim
     *            the dimention of the Road
     * @param _position
     *            the position of the TraficLight on the Road
     */
    public TraficLight(int _dim, Position _position, State state)
    {
	dim = _dim;
	position = _position;
	this.state = state;
    }

    /**
     * Draw a TraficLight
     * 
     * @param g	Graphic context
     */
    public void traficLightDraw(Graphics g)
    {

	int pos1X = 0;
	int pos1Y = 0;
	int pos2X = 0;
	int pos2Y = 0;

	if (position == Position.UP)
	{
	    pos1X = dim / 2 - 20;
	    pos1Y = dim / 2 - 25;
	    pos2X = dim / 2 - 10;
	    pos2Y = dim / 2 - 25;
	}
	else if (position == Position.RIGHT)
	{
	    pos1X = dim / 2 + 10;
	    pos1Y = dim / 2 - 10;
	    pos2X = dim / 2 + 20;
	    pos2Y = dim / 2 - 10;
	}
	else if (position == Position.DOWN)
	{
	    pos1X = dim / 2 - 20;
	    pos1Y = dim / 2 + 10;
	    pos2X = dim / 2 - 10;
	    pos2Y = dim / 2 + 10;
	}
	else if (position == Position.LEFT)
	{
	    pos1X = dim / 2 - 25;
	    pos1Y = dim / 2 - 10;
	    pos2X = dim / 2 - 15;
	    pos2Y = dim / 2 - 10;
	}

	g.setColor(Color.RED);
	g.drawOval(pos1X, pos1Y, 6, 6);
	g.setColor(Color.GREEN);
	g.drawOval(pos2X, pos2Y, 6, 6);

	if (state == State.OFF)
	{
	    g.setColor(Color.RED);
	    g.fillOval(pos1X, pos1Y, 6, 6);
	}
	else if (state == State.ON)
	{
	    g.setColor(Color.GREEN);
	    g.fillOval(pos2X, pos2Y, 6, 6);
	}
    }

    /**
     * Switch light from on to off or off to on
     */
    public void switchLight()
    {
	if (state == State.ON)
	    setState(State.OFF);
	else
	    setState(State.ON);
    }

    /**
     * Get the current state of the trafic light
     * @return State of the trafic light (ON, OFF)
     */
    public State getState()
    {
	return state;
    }

    /**
     * Set the state of the traficlight
     * @param _state	State (ON, OFF)
     */
    public void setState(State _state)
    {
	state = _state;
    }

    private State state;		// State of the traficlight
    private int dim;			// Dimension of the road
    private Position position;		// Position of the road

}
