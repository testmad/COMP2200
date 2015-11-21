import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel
{		
	Star star;
	ArrayList<Star> stars;
	
	public DrawPanel()
	{	
		stars = new ArrayList<Star>();
		//Graphics2D g2d = (Graphics2D)g;
		setBackground(Color.white);
		//star = new Star(g);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2;
		g2 = (Graphics2D) g;
		
		if(!stars.isEmpty())
			stars.get(0).draw(g2);
	}
	
	void addStars(int numToAdd)
	{
		for(int i = 0; i < numToAdd; i++)
		{
			stars.add(new Star());
			repaint();
		}
	}
}
