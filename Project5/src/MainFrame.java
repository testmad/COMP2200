import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener
{
	BufferedImage image;
	
	ImagePanel imagePanel;
	JPanel buttonPanel;
	
	JButton buttonLoad;
	JButton buttonPrint;
	
	JFileChooser fileChooser;
	String fileName;
	
	public MainFrame()
	{
		buildGUI();
		
		pack();
		
		image = null;
		
		fileChooser = new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg","gif","png");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		setupMainFrame();
	}

	private void buildGUI()
	{
		Container cp;
		cp = getContentPane();
		
		imagePanel = new ImagePanel();
		buttonPanel = new JPanel();
		
		//imagePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		//imagePanel.setBackground(Color.white);
		
		buttonLoad = new JButton("Load");
		buttonLoad.addActionListener(this);
		buttonLoad.setActionCommand("LOAD");
		
		buttonPrint = new JButton("Print");
		buttonPrint.addActionListener(this);
		buttonPrint.setActionCommand("PRINT");
		
		buttonPanel.add(buttonLoad);
		buttonPanel.add(buttonPrint);
		
		cp.add(imagePanel, BorderLayout.CENTER);
		cp.add(buttonPanel, BorderLayout.SOUTH);
		
	}

	void setupMainFrame()
	{
		Toolkit tk;
		tk = Toolkit.getDefaultToolkit();
		
		setTitle("ImageViewer");
		setSize(new Dimension(tk.getScreenSize().width/2,tk.getScreenSize().height/2));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("LOAD"))
		{
			loadImage();
		}
		else if(event.getActionCommand().equals("PRINT"))
		{
			try
			{
				printImage();
			}
			catch(PrinterException e)
			{
				System.out.println("Error printing...");
			}
			
		}
	}

	private void printImage() throws PrinterException
	{
		PrinterJob pj;
		PageFormat pageFormat;
		
		pj = PrinterJob.getPrinterJob();
		pageFormat = pj.pageDialog(pj.defaultPage());
		pj.setPrintable(imagePanel, pageFormat);
		
		if(pj.printDialog())
		{
			System.out.println("Ready to print...");
			pj.print();
		}
		
	}

	public void loadImage()
	{
		int response = fileChooser.showOpenDialog(this);
		if( response == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File file = fileChooser.getSelectedFile();
				fileName = file.getAbsolutePath();

				try
				{
					image = ImageIO.read(file);
					
					imagePanel.setImage(fileName, image);
				}
				catch(IOException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error1!", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error2!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			fileName = null;
			System.out.println("Cancelled by user.");
		}
		
	}
	
	
}
