import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Star
{
	static final int MAX_BRANCHES = 15;
	static final int MIN_BRANCHES = 5;
	static final int MAX_RADIUS = 100;
	static final int MIN_RADIUS = 50;
	
	double posX;
	double posY;
	
	
	
	int branches;
	
	double angle;
	
	int innerRadius;
	int outerRadius;
	
	Graphics2D g;

	public Star(Graphics2D g2)
	{

		posX = 100;
		posY = 100;

		innerRadius = 20;
		outerRadius = 30;
		branches = 10;
		
		g = g2;
	}
	
	void paint(Graphics2D g )
	{
		int[] x = null;
		int[] y = null;
		x = new int[2*branches + 1];
		y = new int[2*branches + 1];
		g.setColor(Color.red);
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

	public void update() {

		//draw(posX, posY, innerRadius, outerRadius, branches);
		paint(g);
	}
	
}
