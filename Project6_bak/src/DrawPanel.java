import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener
{		
	Star star;
	ArrayList<Star> stars;
	
	double lastUpdateTime;
	int starLife = 15;
	double timeScale = 1;
	
	boolean mouseIsDown = false;
	
	double mouse_x = 0;
	double mouse_y = 0;
	private String mode;
	private long lastMillis;
	
	
	public DrawPanel()
	{	
		stars = new ArrayList<Star>();
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setMode("CHASE");
		
		lastUpdateTime = System.nanoTime();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2;
		g2 = (Graphics2D) g;
		
		if(!stars.isEmpty())
		{
			for(int i = 0; i < stars.size(); i++)
				stars.get(i).draw(g2);
		}
	}
	
	void addStar()
	{
		stars.add(new Star(this.getWidth(), this.getHeight(), starLife));
		repaint();
	}
	
	void addStars()
	{
		int numToAdd = (int) (Math.random()*100 + 1);
		for(int i = 0; i < numToAdd; i++)
		{
			stars.add(new Star(this.getWidth(), this.getHeight(), starLife));
			repaint();
		}
	}
	
	void clearStars()
	{
		stars.removeAll(stars);
		repaint();
	}
	
	void update()
	{
		final long millis = System.currentTimeMillis();
		final long delta = millis - lastMillis;
		lastMillis = millis;

		if(timeScale == 0)
		{
			//paused
		}
		else //if(now - lastUpdateTime >= (1000000000 / 60))
		{
			if(!stars.isEmpty())
			{
				for(int i = 0; i < stars.size(); i++)
				{
					stars.get(i).setMode(mode);
					stars.get(i).setBounds(getWidth(), getHeight());
					
					if(mouseIsDown)
					{
						stars.get(i).setTarget(mouse_x, mouse_y);
					}

					stars.get(i).update(delta, timeScale);
					
					if(stars.get(i).hasDied)
						stars.remove(i);
				}
			}
			lastUpdateTime = System.nanoTime();
		}
		repaint();
	}
	
	void setStarLife(int starLife)
	{
		this.starLife = starLife;
	}
	
	void setTimeScale(int timeScale)
	{
		this.timeScale = (double)(timeScale)/100;
	}
	
	void setMode(String mode)
	{
		this.mode = mode;
	}

	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e)
	{
		mouseIsDown = false;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			mouse_x = e.getX();
			mouse_y = e.getY();
			mouseIsDown = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			mouse_x = e.getX();
			mouse_y = e.getY();
			mouseIsDown = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouse_x = e.getX();
		mouse_y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e){
		if(!stars.isEmpty())
		{
			for(int i = 0; i < stars.size(); i++)
			{
				stars.get(i).setAngle(e.getX(), e.getY() );
			}
		}

	}
}
