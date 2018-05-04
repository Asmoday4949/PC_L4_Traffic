package dev_perso;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Road extends JPanel
{

    private static final long serialVersionUID = 1L;

    public enum RoadType
    {
	none, line_hori, line_vert, turn_left_top, turn_left_down, turn_right_down, turn_right_top, t_left, t_top, t_right, t_down, cross
    };

    /**
     * Init and draw a Road element
     * 
     * @param _type
     *            the type of the Road
     * @param _posX
     *            x position of the Road
     * @param _posY
     *            y position of the Road
     * @param _dim
     *            dimension of the Road
     * @param _nom
     *            display name of the Road
     */
    public Road(RoadType _type, int _posX, int _posY, int _dim, String _nom)
    {
	type = _type;
	posX = _posX;
	posY = _posY;
	dim = _dim;
	nom = _nom;

	setPreferredSize(new Dimension(dim, dim));

	listTraficLight = new ArrayList<TraficLight>();

	if (type == RoadType.t_left)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT));
	}
	else if (type == RoadType.t_top)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT));
	}
	else if (type == RoadType.t_right)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN));
	}
	else if (type == RoadType.t_down)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT));
	}
	else if (type == RoadType.cross)
	{
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.UP));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.RIGHT));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.DOWN));
	    listTraficLight.add(new TraficLight(dim, TraficLight.Position.LEFT));
	}
    }

    public void paintComponent(Graphics g)
    {
	super.paintComponent(g); // paint parent's background
	setBackground(Color.WHITE); // set background color for this JPanel
	g.setColor(Color.BLACK);

	if (type == RoadType.line_hori)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.line_vert)
	{
	    g.drawLine(dim / 2, 0, dim / 2, dim);
	}
	else if (type == RoadType.turn_left_top)
	{
	    g.drawLine(0, dim / 2, dim / 2, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, 0);
	}
	else if (type == RoadType.turn_left_down)
	{
	    g.drawLine(0, dim / 2, dim / 2, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, dim);
	}
	else if (type == RoadType.turn_right_top)
	{
	    g.drawLine(dim / 2, dim / 2, dim / 2, 0);
	    g.drawLine(dim / 2, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.turn_right_down)
	{
	    g.drawLine(dim / 2, dim / 2, dim / 2, dim);
	    g.drawLine(dim / 2, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.t_left)
	{
	    g.drawLine(dim / 2, 0, dim / 2, dim);
	    g.drawLine(0, dim / 2, dim / 2, dim / 2);
	}
	else if (type == RoadType.t_top)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, 0);
	}
	else if (type == RoadType.t_right)
	{
	    g.drawLine(dim / 2, 0, dim / 2, dim);
	    g.drawLine(dim / 2, dim / 2, dim, dim / 2);
	}
	else if (type == RoadType.t_down)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	    g.drawLine(dim / 2, dim / 2, dim / 2, dim);
	}
	else if (type == RoadType.cross)
	{
	    g.drawLine(0, dim / 2, dim, dim / 2);
	    g.drawLine(dim / 2, 0, dim / 2, dim);

	}

	for (TraficLight light : listTraficLight)
	{
	    light.traficLightDraw(g);
	}

	g.setColor(Color.BLACK);
	g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
	g.drawString(nom, dim / 2 + 5, dim / 2 + 10);
    }

    public int getPosCentX()
    {
	return posX * dim + dim / 2;
    }

    public int getPosCentY()
    {
	return posY * dim + dim / 2;
    }

    public String getNom()
    {
	return nom;
    }

    public void setType(RoadType _type)
    {
	type = _type;
	repaint();
    }

    public List<TraficLight> getListTraficLight()
    {
	return listTraficLight;
    }

    public boolean hasTraficLight()
    {
	return listTraficLight.size() > 0;
    }

    private List<TraficLight> listTraficLight;

    private RoadType type;
    private int posX;
    private int posY;
    private int dim;
    private String nom;
}
