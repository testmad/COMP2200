import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener, ChangeListener
{
	DrawPanel drawingPanel;
	JPanel buttonPanel;
	
	JButton buttonAddOne;
	JButton buttonAddMany;
	JButton buttonClear;
	JButton buttonChase;
	JButton buttonFall;
	JButton buttonRandom;
	JButton buttonPause;
	
	JSlider lifeSlider;
	JSlider timeSlider;
	
	Timer timer;
	boolean isPaused;
	
	public MainFrame()
	{
		buildGUI();
		pack();
		setupMainFrame();
		
		timer = new Timer(1000/60, this);
		timer.setActionCommand("UPDATE");
		timer.setCoalesce(true);
	    timer.start();
	}

	private void buildGUI()
	{
		Container cp;
		cp = getContentPane();
		
		drawingPanel = new DrawPanel();
		
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
		
		buttonChase = new JButton("Chase");
		buttonChase.setMinimumSize(new Dimension(100,25));
		buttonChase.setMaximumSize(new Dimension(100,25));
		buttonChase.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonChase.setActionCommand("CHASE");
		buttonChase.addActionListener(this);
		buttonPanel.add(buttonChase);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonFall = new JButton("Fall");
		buttonFall.setMinimumSize(new Dimension(100,25));
		buttonFall.setMaximumSize(new Dimension(100,25));
		buttonFall.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonFall.setActionCommand("FALL");
		buttonFall.addActionListener(this);
		buttonPanel.add(buttonFall);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		buttonRandom = new JButton("Random");
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
		
		lifeSlider = new JSlider(0,120,60);
		lifeSlider.setPaintTicks(true);
		lifeSlider.setSnapToTicks(true);
		lifeSlider.setMajorTickSpacing(30);
		lifeSlider.setMinorTickSpacing(15);
		lifeSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Star Life"));
		((TitledBorder) lifeSlider.getBorder()).setTitleJustification(TitledBorder.CENTER);
		lifeSlider.setPreferredSize(new Dimension(100,25));
		lifeSlider.addChangeListener(this);
		buttonPanel.add(lifeSlider);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		timeSlider = new JSlider(0,200,100);
		timeSlider.setPaintTicks(true);
		timeSlider.setSnapToTicks(true);
		timeSlider.setMajorTickSpacing(100);
		timeSlider.setMinorTickSpacing(10);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("ONE"))
		{
			drawingPanel.addStar();
		}
		
		if(e.getActionCommand().equals("MANY"))
		{
			drawingPanel.addStars();
		}
		
		if(e.getActionCommand().equals("CLEAR"))
		{
			drawingPanel.clearStars();
		}
		
		if(e.getActionCommand().equals("CHASE"))
		{
			drawingPanel.setMode("CHASE");
		}
		
		if(e.getActionCommand().equals("FALL"))
		{
			drawingPanel.setMode("FALL");
		}
		
		if(e.getActionCommand().equals("RANDOM"))
		{
			drawingPanel.setMode("RANDOM");
		}
		
		if(e.getActionCommand().equals("PAUSE"))
		{
			if(isPaused)
			{
				isPaused = false;
				buttonPause.setText("Pause");
				drawingPanel.setTimeScale(timeSlider.getValue());
			}
			else
			{
				isPaused = true;
				buttonPause.setText("Unpause");
				drawingPanel.setTimeScale(0);
			}
			
		}
		
		if(e.getActionCommand().equals("UPDATE"))
		{
			drawingPanel.update();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource().equals(lifeSlider))
			drawingPanel.setStarLife(lifeSlider.getValue());
		
		if(e.getSource().equals(timeSlider))
		{
			if(timeSlider.getValue() == 0)
			{
				buttonPause.setText("Unpause");
				isPaused = true;
			}
			else
			{
				buttonPause.setText("Pause");
				isPaused = false;
			}
			
			drawingPanel.setTimeScale(timeSlider.getValue());
		}
	}
}
