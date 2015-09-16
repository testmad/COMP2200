import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI_Conversion_Calc
{
	public static void main (String[] args)
	{
		new MyFrameClass();
		
	}
}
	
class MyFrameClass extends JFrame implements ActionListener, DocumentListener
{
	JTextField		inputField;
	JTextField		outputField;
	
	ButtonGroup		inputRadioGroup;
	ButtonGroup		outputRadioGroup;
	
	JRadioButton	radio1;
	JRadioButton	radio2;
	JRadioButton	radio3;
	JRadioButton	radio4;
	JRadioButton	radio5;
	JRadioButton	radio6;
	JRadioButton	radio7;
	JRadioButton	radio8;
	JRadioButton	radio9;
	JRadioButton	radio10;
	JRadioButton	radio11;
	JRadioButton	radio12;
	JRadioButton	radio13;
	JRadioButton	radio14;
	JRadioButton	radio15;
	JRadioButton	radio16;
	
	static String radio1String = "FIN";
	static String radio2String = "FFT";
	static String radio3String = "FYD";
	static String radio4String = "FMI";
	static String radio5String = "FMM";
	static String radio6String = "FCM";
	static String radio7String = "FM";
	static String radio8String = "FKM";
	static String radio9String = "TIN";
	static String radio10String = "TFT";
	static String radio11String = "TYD";
	static String radio12String = "TMI";
	static String radio13String = "TMM";
	static String radio14String = "TCM";
	static String radio15String = "TM";
	static String radio16String = "TKM";
	
	static double INCHES = 39.3700787;
	static double FEET = 3.280839895;
	static double YARDS = 1.0936133;
	static double MILES = 0.0006213711;
	static double MILLIMETERS = 1000;
	static double CENTIMETERS = 100;
	static double METERS = 1;
	static double KILOMETERS = 0.001;
	
	double fromUnit = INCHES;
	double toUnit = INCHES;
	
	MyFrameClass()
	{
		Container	container;
		JPanel		inputPanel;
		JPanel		outputPanel;
		JPanel		inputRadioPanel;
		JPanel		outputRadioPanel;
		
		inputField = new JTextField(50);
		inputField.getDocument().addDocumentListener(this);
		
		radio1 = new JRadioButton("in");
		radio1.setSelected(true);
		radio2 = new JRadioButton("ft");
		radio3 = new JRadioButton("yd");
		radio4 = new JRadioButton("mi");
		radio5 = new JRadioButton("mm");
		radio6 = new JRadioButton("cm");
		radio7 = new JRadioButton("m");
		radio8 = new JRadioButton("km");
		
		radio1.setActionCommand(radio1String);
		radio2.setActionCommand(radio2String);
		radio3.setActionCommand(radio3String);
		radio4.setActionCommand(radio4String);
		radio5.setActionCommand(radio5String);
		radio6.setActionCommand(radio6String);
		radio7.setActionCommand(radio7String);
		radio8.setActionCommand(radio8String);
		
		inputRadioGroup = new ButtonGroup();
		inputRadioGroup.add(radio1);
		inputRadioGroup.add(radio2);
		inputRadioGroup.add(radio3);
		inputRadioGroup.add(radio4);
		inputRadioGroup.add(radio5);
		inputRadioGroup.add(radio6);
		inputRadioGroup.add(radio7);
		inputRadioGroup.add(radio8);
		
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
		radio4.addActionListener(this);
		radio5.addActionListener(this);
		radio6.addActionListener(this);
		radio7.addActionListener(this);
		radio8.addActionListener(this);
		
		inputRadioPanel = new JPanel(new FlowLayout());
		
		inputRadioPanel.add(radio1);
		inputRadioPanel.add(radio2);
		inputRadioPanel.add(radio3);
		inputRadioPanel.add(radio4);
		inputRadioPanel.add(radio5);
		inputRadioPanel.add(radio6);
		inputRadioPanel.add(radio7);
		inputRadioPanel.add(radio8);
		
		inputPanel = new JPanel(new GridLayout(0,1));
		inputPanel.add(inputField);
		inputPanel.add(inputRadioPanel);
		
		outputField = new JTextField(50);
		outputField.setEditable(false);
		
		radio9 = new JRadioButton("in");
		radio9.setSelected(true);
		radio10 = new JRadioButton("ft");
		radio11 = new JRadioButton("yd");
		radio12 = new JRadioButton("mi");
		radio13 = new JRadioButton("mm");
		radio14 = new JRadioButton("cm");
		radio15 = new JRadioButton("m");
		radio16 = new JRadioButton("km");
		
		radio9.setActionCommand(radio9String);
		radio10.setActionCommand(radio10String);
		radio11.setActionCommand(radio11String);
		radio12.setActionCommand(radio12String);
		radio13.setActionCommand(radio13String);
		radio14.setActionCommand(radio14String);
		radio15.setActionCommand(radio15String);
		radio16.setActionCommand(radio16String);
		
		outputRadioGroup = new ButtonGroup();
		outputRadioGroup.add(radio9);
		outputRadioGroup.add(radio10);
		outputRadioGroup.add(radio11);
		outputRadioGroup.add(radio12);
		outputRadioGroup.add(radio13);
		outputRadioGroup.add(radio14);
		outputRadioGroup.add(radio15);
		outputRadioGroup.add(radio16);
		
		radio9.addActionListener(this);
		radio10.addActionListener(this);
		radio11.addActionListener(this);
		radio12.addActionListener(this);
		radio13.addActionListener(this);
		radio14.addActionListener(this);
		radio15.addActionListener(this);
		radio16.addActionListener(this);
		
		outputRadioPanel = new JPanel(new FlowLayout());
		
		outputRadioPanel.add(radio9);
		outputRadioPanel.add(radio10);
		outputRadioPanel.add(radio11);
		outputRadioPanel.add(radio12);
		outputRadioPanel.add(radio13);
		outputRadioPanel.add(radio14);
		outputRadioPanel.add(radio15);
		outputRadioPanel.add(radio16);
		
		outputPanel = new JPanel(new GridLayout(0,1));
		
		outputPanel.add(outputRadioPanel);
		outputPanel.add(outputField);
		
		container = getContentPane();
		container.add(inputPanel, BorderLayout.NORTH);
		container.add(outputPanel, BorderLayout.SOUTH);
		
		setupMainFrame();
		inputField.requestFocus();
	}
	
	void setupMainFrame()
	{
		Toolkit		toolkit;
		Dimension	dimension;
		
		setTitle("Unit Conversion Calculator");
		
		toolkit = Toolkit.getDefaultToolkit();
		dimension = toolkit.getScreenSize();
		
		setLocation(dimension.width/3, dimension.height/3);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(radio1String))
		{
			fromUnit = INCHES;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio2String))
		{
			fromUnit = FEET;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio3String))
		{
			fromUnit = YARDS;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio4String))
		{
			fromUnit = MILES;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio5String))
		{
			fromUnit = MILLIMETERS;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio6String))
		{
			fromUnit = CENTIMETERS;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio7String))
		{
			fromUnit = METERS;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio8String))
		{
			fromUnit = KILOMETERS;
			System.out.println(fromUnit);
		}
		else if(e.getActionCommand().equals(radio9String))
		{
			toUnit = INCHES;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio10String))
		{
			toUnit = FEET;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio11String))
		{
			toUnit = YARDS;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio12String))
		{
			toUnit = MILES;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio13String))
		{
			toUnit = MILLIMETERS;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio14String))
		{
			toUnit = CENTIMETERS;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio15String))
		{
			toUnit = METERS;
			System.out.println(toUnit);
		}
		else if(e.getActionCommand().equals(radio16String))
		{
			toUnit = KILOMETERS;
			System.out.println(toUnit);
		}
		else
		{
			
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		String string = inputField.getText();
		
		try
		{
			if(string.equals(""))
				outputField.setText("");
			else
			{
				double d = Double.parseDouble(string.trim());
			
				outputField.setText("" + String.format("%.8f", convertedInput(d)));
			}
		}
		catch(NumberFormatException nfe)
		{
			outputField.setText("ERROR");
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		String string = inputField.getText();
		
		try
		{
			if(string.equals(""))
				outputField.setText("");
			else
			{
				double d = Double.parseDouble(string.trim());
			
				outputField.setText("" + String.format("%.8f", convertedInput(d)));
			}
		}
		catch(NumberFormatException nfe)
		{
			outputField.setText("ERROR");
		}
	}
	
	public double convertedInput(double val)
	{
		double converted;
		
		converted = (val / fromUnit) * toUnit;
		
		return converted;
	}
}
