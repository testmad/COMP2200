import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class ImagePanel extends JPanel implements Printable
{
	Image image;
	Image tmpImage;
	String fileName = null;
	String[] pathStrings;
	String result;
	int resultWidth;
	
	public ImagePanel()
	{
		//setBorder(new LineBorder(Color.DARK_GRAY, 1));
		setBackground(Color.white);
	}
	
	public void setImage(String imagePath, Image img)
	{
		fileName = imagePath;
		image = img;
		tmpImage = resizeImage(img);
		repaint();
	}
	
	public Image resizeImage(Image img)
	{
		int newWidth = getWidth() - 10;
		int newHeight = getHeight()- 10;
		
		double panelRatio = (double) newWidth / (double) newHeight;
		
		int imageWidth = img.getWidth(null);
        int imageHeight = img.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;
		
        if (panelRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
        }
		
	    Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		
		return newImg;
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		if(tmpImage != null)
		{
			g.drawImage(
					tmpImage,
					getWidth()/2 - tmpImage.getWidth(null)/2,
					getHeight()/2 - tmpImage.getHeight(null)/2,
					this);
			
			g.setXORMode(Color.white);
			
			Font font = g.getFont().deriveFont( 8 );
			g.setFont(font);
			int fileNameWidth = g.getFontMetrics(font).stringWidth(fileName);
			
			if(fileNameWidth > tmpImage.getWidth(null))
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
							getWidth()/2 + tmpImage.getWidth(null)/2 - resultWidth - 5,
							getHeight()/2 + tmpImage.getHeight(null)/2 - 5
						);
		}
		else
			g.drawImage(tmpImage, 0, 0, this);
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException
	{
		//return 0;

		Graphics2D g2;
		
		if(pageIndex > 0)
			return Printable.NO_SUCH_PAGE;
		
		g2 = (Graphics2D) g;
		
		g2.translate(pf.getImageableX(),  pf.getImageableY());
		
		int newWidth = (int)Math.round(pf.getImageableWidth());
		int newHeight = (int)Math.round(pf.getImageableHeight());
		
		System.out.println(newWidth + ", " + newHeight);
		
		double paperRatio = (double) newWidth / (double) newHeight;
		
		int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;
		
        if (paperRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
        }
		
	    Image newImg = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		
		g2.drawImage(newImg, (int) ((pf.getImageableWidth() - newImg.getWidth(null)) / 2), (int)((pf.getImageableHeight() - newImg.getHeight(null))/2), this);
		
//		g2.setXORMode(Color.white);
//		Font font = g.getFont().deriveFont( 8 );
//		g2.setFont(font);
//		
//		g2.drawString(result, (int) (pf.getImageableWidth()/2 + image.getWidth(null)/2 - resultWidth - 5),  (int) (pf.getImageableHeight()/2 + image.getHeight(null)/2 - 5));
//		
		return Printable.PAGE_EXISTS;
		
		
	}

}
