import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Random;

public class Star
{
	static final int MAX_BRANCHES = 15;
	static final int MIN_BRANCHES = 5;
	
	static final int MAX_INNER_RADIUS = 20;
	static final int MIN_INNER_RADIUS = 10;
	
	static final int MAX_OUTER_RADIUS = 40;
	static final int MIN_OUTER_RADIUS = 20;
	
	double posX;
	double posY;

	int branches;
	
	double angle;
	
	int innerRadius;
	int outerRadius;
	
	Color color;
	
	Graphics2D g;

	public Star(Graphics2D g2)
	{
		g = g2;
		
		posX = 100;
		posY = 100;
		
		Random iRand = new Random();
		Random oRand = new Random();
		
		innerRadius = iRand.nextInt(MAX_INNER_RADIUS - MIN_INNER_RADIUS) + MIN_INNER_RADIUS;
		outerRadius = oRand.nextInt(MAX_OUTER_RADIUS - MIN_OUTER_RADIUS) + MIN_OUTER_RADIUS;
		branches = 10;
		
		color = new Color(2, 132, 130, 255);
	}
	
	void draw(Graphics2D g )
	{
		int[] x = null;
		int[] y = null;
		
		x = new int[2*branches + 1];
		y = new int[2*branches + 1];
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(color);
		
		double angle = Math.PI / branches;
		
		int i = 0;		
		for ( i = 0; i < 2*branches; i++)
	    {
			x[i] = (int) (posX + outerRadius * Math.cos(angle));
			y[i] = (int) (posY + outerRadius * Math.sin(angle));
			angle = angle + Math.PI / branches;
			i++;
			x[i] = (int) (posX + innerRadius * Math.cos(angle));
			y[i] = (int) (posY + innerRadius * Math.sin(angle));
			angle = angle + Math.PI / branches;
	    }
		x[i] = x[0];
		y[i] = y[0];
		
		g.drawPolyline(x, y, x.length);
	}

	public void update()
	{
		draw(g);
	}
}
