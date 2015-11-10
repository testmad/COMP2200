import java.awt.*;
import java.awt.print.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements Printable
{
	Image image;
	String fileName = null;
	String[] pathStrings;
	String result;
	int resultWidth;
	
	public ImagePanel()
	{
		setBackground(Color.white);
	}
	
	public void setImage(String imagePath, Image img)
	{
		fileName = imagePath;
		image = img;
		repaint();
	}
	
	public Dimension calcDimension(Image img)
	{		
		int newWidth = getWidth() - 10;
		int newHeight = getHeight() - 10;
		
		if(img.getWidth(null) > newWidth || img.getHeight(null) > newHeight )
		{
			double panelRatio = (double) newWidth / (double) newHeight;
			
			int imageWidth = img.getWidth(null);
	        int imageHeight = img.getHeight(null);
	        double aspectRatio = (double) imageWidth / (double) imageHeight;
			
	        if (panelRatio < aspectRatio) {
	            newHeight = (int) (newWidth / aspectRatio);
	        } else {
	            newWidth = (int) (newHeight * aspectRatio);
	        }

		    return new Dimension(newWidth, newHeight);
		}
		else
			return new Dimension(img.getWidth(null), img.getHeight(null));
	}
	
	public Dimension calcDimension(Image img, double width, double height)
	{		
		double newWidth = width;
		double newHeight = height;
		
		if(img.getWidth(null) > newWidth || img.getHeight(null) > newHeight )
		{
			double panelRatio = (double) newWidth / (double) newHeight;
			
			int imageWidth = img.getWidth(null);
	        int imageHeight = img.getHeight(null);
	        double aspectRatio = (double) imageWidth / (double) imageHeight;
			
	        if (panelRatio < aspectRatio) {
	            newHeight = (int) (newWidth / aspectRatio);
	        } else {
	            newWidth = (int) (newHeight * aspectRatio);
	        }

		    return new Dimension((int)newWidth, (int)newHeight);
		}
		else
			return new Dimension(img.getWidth(null), img.getHeight(null));
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		if(image != null)
		{
			Dimension imageSize = calcDimension(image);
			System.out.println(imageSize + " or " + imageSize.getWidth());
			g.drawImage(
					image,
					getWidth()/2 - imageSize.width/2,
					getHeight()/2 - imageSize.height/2,
					imageSize.width,
					imageSize.height,
					this);
			
			g.setXORMode(Color.white);
			
			Font font = g.getFont().deriveFont( 8 );
			g.setFont(font);
			int fileNameWidth = g.getFontMetrics(font).stringWidth(fileName);
			
			if(fileNameWidth > imageSize.width)
			{
				pathStrings = fileName.split("\\\\");
				result = pathStrings[0] + "\\...\\" + pathStrings[pathStrings.length - 1];
				resultWidth = g.getFontMetrics(font).stringWidth(result);
				
				//TODO:
				//Add another if for truncating to just a few letters if needed.
			}
			else
			{
				result = fileName;
				resultWidth = g.getFontMetrics(font).stringWidth(result);
			}

			g.drawString(
							result,
							getWidth()/2 + imageSize.width/2 - resultWidth - 5,
							getHeight()/2 + imageSize.height/2 - 5
						);
		}
		else
			g.drawImage(image, 0, 0, this);
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException
	{
		Graphics2D g2;
		
		if(pageIndex > 0)
			return Printable.NO_SUCH_PAGE;
		
		g2 = (Graphics2D) g;
		
		g2.translate(pf.getImageableX(),  pf.getImageableY());
		
		Dimension imageSize = calcDimension(image, pf.getImageableWidth(), pf.getImageableHeight());
		
		g2.drawImage(image,
					(int) (pf.getImageableWidth()/2 - imageSize.width / 2),
					(int) (pf.getImageableHeight()/2 - imageSize.height/2),
					imageSize.width,
					imageSize.height,
					this);
		
		g.setXORMode(Color.white);
		
		Font font = g2.getFont().deriveFont( 6 );
		g2.setFont(font);
		int fileNameWidth = g2.getFontMetrics(font).stringWidth(fileName);
		
		if(fileNameWidth > imageSize.width)
		{
			pathStrings = fileName.split("\\\\");
			result = pathStrings[0] + "\\...\\" + pathStrings[pathStrings.length - 1];
			resultWidth = g.getFontMetrics(font).stringWidth(result);
			
			//TODO:
			//Add another if for truncating to just a few letters if needed.
		}
		else
		{
			result = fileName;
			resultWidth = g.getFontMetrics(font).stringWidth(result);
		}

		g.drawString(
						result,
						(int)(pf.getImageableWidth()/2 + imageSize.width/2 - resultWidth - 5),
						(int)(pf.getImageableHeight()/2 + imageSize.height/2 - 5)
					);
		return Printable.PAGE_EXISTS;		
	}
}
