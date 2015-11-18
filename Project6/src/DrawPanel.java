import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel
{	
		
	Star star;
	
	public DrawPanel()
	{
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
		star = new Star(g2);
		star.update();
	}
}
