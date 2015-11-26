import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

public class Star
{
	static final int MAX_BRANCHES = 10;
	static final int MIN_BRANCHES = 5;
	
	static final int MAX_INNER_RADIUS = 10;
	static final int MIN_INNER_RADIUS = 5;
	
	static final int MAX_OUTER_RADIUS = 20;
	static final int MIN_OUTER_RADIUS = 15;
	
	double x;
	double y;

	int branches;
	
	double angle;
	
	int innerRadius;
	int outerRadius;
	
	long starLife;
	float currentLife;
	
	Color color;
	
	float red;
	float green;
	float blue;
	float alpha;
	
	boolean hasDied = false;
	boolean hasTarget = false;
	
	double target_x;
	double target_y;
	
	double speed_x = 1;
	double speed_y = 1;
	
	Random r = new Random();
	
	int boundsWidth;
	int boundsHeight;
	double delta_x;
	double delta_y;
	double target_origin_x;
	double target_origin_y;
	String mode;
	private boolean hasSpeed;
	


	public Star(int boundsWidth, int boundsHeight, int starLife)
	{
		this.boundsWidth = boundsWidth;
		this.boundsHeight = boundsHeight;
		
		innerRadius = r.nextInt(MAX_INNER_RADIUS - MIN_INNER_RADIUS +1 ) + MIN_INNER_RADIUS;
		outerRadius = r.nextInt(MAX_OUTER_RADIUS - MIN_OUTER_RADIUS + 1) + MIN_OUTER_RADIUS;
		branches = r.nextInt(MAX_BRANCHES - MIN_BRANCHES) + MIN_BRANCHES;
		
		while(x > this.boundsWidth-outerRadius || x < outerRadius)
			x = r.nextInt(boundsWidth);
		
		while(y > this.boundsHeight-outerRadius || y < outerRadius)
			y = r.nextInt(boundsHeight);
		
		red = r.nextFloat();
		green = r.nextFloat();
		blue = r.nextFloat();
		alpha = 1;
		
		color = new Color(red, green, blue, alpha);
		
		this.starLife = starLife * 300 ;
		currentLife = this.starLife;
		
		target_x = x;
		target_y = y;
	}
	
	void draw(Graphics2D g)
	{
		int[] xPoints = null;
		int[] yPoints = null;
		
		xPoints = new int[2*branches + 1];
		yPoints = new int[2*branches + 1];
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(color);
		
		int i = 0;		
		for ( i = 0; i < 2*branches; i++)
	    {
			xPoints[i] = (int) (x + outerRadius * Math.cos(angle));
			yPoints[i] = (int) (y + outerRadius * Math.sin(angle));
			angle = angle + Math.PI / branches;
			i++;
			xPoints[i] = (int) (x + innerRadius * Math.cos(angle));
			yPoints[i] = (int) (y + innerRadius * Math.sin(angle));
			angle = angle + Math.PI / branches;
	    }
		xPoints[i] = xPoints[0];
		yPoints[i] = yPoints[0];

		g.drawPolyline(xPoints, yPoints, xPoints.length);
	}

	public void update(double timeScale)
	{
		if(currentLife <= 0)
			hasDied = true;
		else
		{
			alpha = currentLife/starLife;
			color = new Color(red, green, blue, alpha);

            if(mode.equals("CHASE"))
			{
            	hasSpeed = false;
            	
            	delta_x = target_x - x;
    	        delta_y = target_y - y;
                
    	        speed_x = delta_x/50;
    	        speed_y = delta_y/50;

	            if (x - outerRadius < 0)
	            {
	            	speed_x = -speed_x;
	            	x = outerRadius;
	            }
	            else if (x + outerRadius > boundsWidth)
	            {
	            	speed_x = -speed_x;
	            	x = boundsWidth - outerRadius;
	            }

	            if (y - outerRadius < 0)
	            {
	            	speed_y = -speed_y;
            		y = outerRadius;
	            }
	            else if (y + outerRadius > boundsHeight)
	            {
	            	speed_y = -speed_y;
            		y = boundsHeight - outerRadius;
	            }

				x += speed_x * timeScale;
	            y += speed_y * timeScale;
	            
	            double distance = Math.sqrt(Math.pow(x- target_x,2) + Math.pow(y- target_y,2));
	            
	            if(distance <= 10 )
	            {
	               	hasTarget = false;
	            }
			}
            
            if(mode.equals("FALL"))
			{
            	hasSpeed = false;
            	
            	delta_x = x - x;
    	        delta_y = boundsHeight - y;
                
    	        speed_x = 0;
    	        speed_y += .05;
    	        
    	        
            	x += speed_x * timeScale;
	            y += speed_y * timeScale;
	            
	            if (x - outerRadius < 0)
	            {
	            	speed_x = -speed_x;
	            	x = outerRadius;
	            }
	            else if (x + outerRadius > boundsWidth)
	            {
	            	speed_x = -speed_x;
	            	x = boundsWidth - outerRadius;
	            }

	            if (y - outerRadius < 0)
	            {
	            	speed_y = -speed_y;
            		y = outerRadius;
	            }
	            else if (y + outerRadius > boundsHeight)
	            {
	            	speed_y = -speed_y/2;
            		y = boundsHeight - outerRadius;
	            }
			}
            
            if(mode.equals("RANDOM"))
			{
				if(!hasSpeed)
				{
					delta_x = r.nextInt(boundsWidth) - x;
	    	        delta_y = r.nextInt(boundsHeight) - y;
	                
	    	        speed_x = delta_x/(r.nextInt(400)+200);
	    	        speed_y = delta_y/(r.nextInt(400)+200);
	    	        hasSpeed = true;
				}

	            if(hasSpeed)
	            {
	            	x += speed_x * timeScale;
		            y += speed_y * timeScale;
		            
		            if (x - outerRadius < 0)
		            {
		            	speed_x = -speed_x;
		            	x = outerRadius;
		            }
		            else if (x + outerRadius > boundsWidth)
		            {
		            	speed_x = -speed_x;
		            	x = boundsWidth - outerRadius;
		            }

		            if (y - outerRadius < 0)
		            {
		            	speed_y = -speed_y;
	            		y = outerRadius;
		            }
		            else if (y + outerRadius > boundsHeight)
		            {
		            	speed_y = -speed_y;
	            		y = boundsHeight - outerRadius;
		            }
	            }
			}
            currentLife -= 1 * timeScale;
		}
	}
	
	void setTarget(double x, double y)
	{
		if(!hasTarget)
		{
			target_origin_x = x;
			target_origin_y = y;
			
			target_x = x + r.nextInt(101) - 50;
			target_y = y + r.nextInt(101) - 50;

			if (target_x - outerRadius <= 0)
			{
				target_x = outerRadius+1;
			}
			else if (target_x + outerRadius >= boundsWidth)
			{
				target_x = boundsWidth - outerRadius-1;
			}
			
			if (target_y - outerRadius <= 0)
			{
				target_y = outerRadius+1;
			}
			else if (target_y + outerRadius >= boundsHeight)
			{
				target_y = boundsHeight - outerRadius-1;
			}
	            
			hasTarget = true;
		}
	}
	
	void setAngle(double rot_x, double rot_y)
	{
		angle = Math.atan2(rot_y - y, rot_x - x);
	}
	
	void setMode(String mode)
	{
		this.mode = mode;
	}
	
	void setBounds(int boundsWidth, int boundsHeight)
	{
		this.boundsWidth = boundsWidth;
		this.boundsHeight = boundsHeight;
	}
}
