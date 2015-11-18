import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame implements ActionListener, ChangeListener
{
	JPanel drawingPanel;
	JPanel buttonPanel;
	
	JButton buttonAddOne;
	JButton buttonAddMany;
	JButton buttonClear;
	JButton buttonFall;
	JButton buttonRandom;
	JButton buttonPause;
	
	JSlider lifeSlider;
	JSlider timeSlider;
	
	public MainFrame()
	{
		buildGUI();
		
		pack();
		
		setupMainFrame();
	}

	private void buildGUI()
	{
		Container cp;
		cp = getContentPane();
		
		drawingPanel = new JPanel();
		drawingPanel.setBackground(Color.white);
		
		buttonPanel = new JPanel();
		
		buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		BoxLayout layout = new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS);
		buttonPanel.setLayout(layout);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(120, 10)));
		
		buttonAddOne = new JButton("Add One");
		buttonAddOne.setMinimumSize(new Dimension(100,25));
		buttonAddOne.setMaximumSize(new Dimension(100,25));
		buttonAddOne.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonAddOne.setActionCommand("ONE");
		buttonAddOne.addActionListener(this);
		buttonPanel.add(buttonAddOne);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonAddMany = new JButton("Add Many");
		buttonAddMany.setMinimumSize(new Dimension(100,25));
		buttonAddMany.setMaximumSize(new Dimension(100,25));
		buttonAddMany.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonAddMany.setActionCommand("MANY");
		buttonAddMany.addActionListener(this);
		buttonPanel.add(buttonAddMany);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonClear = new JButton("Clear");
		buttonClear.setMinimumSize(new Dimension(100,25));
		buttonClear.setMaximumSize(new Dimension(100,25));
		buttonClear.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonClear.setActionCommand("CLEAR");
		buttonClear.addActionListener(this);
		buttonPanel.add(buttonClear);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonFall = new JButton("Fall Mode");
		buttonFall.setMinimumSize(new Dimension(100,25));
		buttonFall.setMaximumSize(new Dimension(100,25));
		buttonFall.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonFall.setActionCommand("FALL");
		buttonFall.addActionListener(this);
		buttonPanel.add(buttonFall);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonRandom = new JButton("Random Mode");
		buttonRandom.setMinimumSize(new Dimension(100,25));
		buttonRandom.setMaximumSize(new Dimension(100,25));
		buttonRandom.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonRandom.setActionCommand("RANDOM");
		buttonRandom.addActionListener(this);
		buttonPanel.add(buttonRandom);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonPause = new JButton("Pause");
		buttonPause.setMinimumSize(new Dimension(100,25));
		buttonPause.setMaximumSize(new Dimension(100,25));
		buttonPause.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPause.setActionCommand("PAUSE");
		buttonPause.addActionListener(this);
		buttonPanel.add(buttonPause);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		lifeSlider = new JSlider(0,100,50);
		lifeSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Star Life"));
		((TitledBorder) lifeSlider.getBorder()).setTitleJustification(TitledBorder.CENTER);
		lifeSlider.setPreferredSize(new Dimension(100,25));
		lifeSlider.addChangeListener(this);
		buttonPanel.add(lifeSlider);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		timeSlider = new JSlider(0,100,50);
		timeSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Demo Speed"));
		((TitledBorder) timeSlider.getBorder()).setTitleJustification(TitledBorder.CENTER);
		timeSlider.setPreferredSize(new Dimension(100,25));
		timeSlider.addChangeListener(this);
		buttonPanel.add(timeSlider);
		
		cp.add(drawingPanel, BorderLayout.CENTER);
		cp.add(buttonPanel, BorderLayout.EAST);
	}

	private void setupMainFrame()
	{
		Toolkit tk;
		tk = Toolkit.getDefaultToolkit();

		setTitle("Star Demo");
		setSize(new Dimension(tk.getScreenSize().width/2,tk.getScreenSize().height/2));
		setLocationRelativeTo(null);
		//setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

}
