import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class GUI_List_Manipulation
{
	public static void main(String[] args)
	{
		new MyFrameClass();
	}
}

class MyFrameClass extends JFrame implements ActionListener
{
	JList myListBox;
	DefaultListModel myListModel;
	JScrollPane sp;
	JFileChooser fileChooser;
	
	MyFrameClass()
	{
		myListModel = new DefaultListModel();
		myListBox = new JList(myListModel);
		sp = new JScrollPane(myListBox);
		
		Container cp;
		
		JPanel pnlList;
		JPanel pnlButton;
		
		JButton btnLoad;
		JButton btnSave;
		JButton btnSaveAs;
		JButton btnAdd;
		JButton btnDelete;
		JButton btnExit;
		
		pnlList = new JPanel();
		pnlList.add(sp);
		
		btnLoad = new JButton("Load");
		btnLoad.setToolTipText("Load file.");
		btnLoad.setActionCommand("LOAD");
		btnLoad.addActionListener(this);
		
		btnSave = new JButton("Save");
		btnSave.setToolTipText("Save file.");
		btnSave.setActionCommand("SAVE");
		btnSave.addActionListener(this);
		
		btnSaveAs = new JButton("Save As");
		btnSaveAs.setToolTipText("Save file as...");
		btnSaveAs.setActionCommand("SAVEAS");
		btnSaveAs.addActionListener(this);
		
		btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Add an item.");
		btnAdd.setActionCommand("NEW");
		btnAdd.addActionListener(this);
		
		btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("Delete selected item(s).");
		btnDelete.setActionCommand("DELETE");
		btnDelete.addActionListener(this);
		
		btnExit = new JButton("Exit");
		btnExit.setActionCommand("EXIT");
		btnExit.addActionListener(this);
		
		pnlButton = new JPanel(new GridLayout(0,1));
		pnlButton.add(btnLoad);
		pnlButton.add(btnSave);
		pnlButton.add(btnSaveAs);
		pnlButton.add(btnAdd);
		pnlButton.add(btnDelete);
		pnlButton.add(btnExit);
		
		cp = getContentPane();
		cp.add(pnlList, BorderLayout.WEST);
		cp.add(pnlButton, BorderLayout.EAST);
		
		setJMenuBar(newMenuBar());
		
		
		fileChooser = new JFileChooser();
		//fileChooser.showOpenDialog(this);
		//fileChooser.showSaveDialog(this);
		//fileChooser.showDialog(this, "Save as...");
		
		setupMainFrame();
	}

	private JMenuBar newMenuBar()
	{
		JMenuBar menuBar;
		JMenu subMenu;
		
		menuBar = new JMenuBar();
		
		subMenu = new JMenu("File");
		subMenu.setMnemonic(KeyEvent.VK_F);
		subMenu.add(newItem("Load...", "LOAD", this, KeyEvent.VK_L, KeyEvent.VK_L, "Load file.", 0));
		subMenu.add(newItem("Save...", "SAVE", this, KeyEvent.VK_S, KeyEvent.VK_S, "Save file.", 1));
		subMenu.add(newItem("Save as...", "SAVEAS", this, KeyEvent.VK_S, KeyEvent.VK_S, "Save file as...", 3));
		subMenu.add(newItem("Exit...", "EXIT", this, KeyEvent.VK_X, KeyEvent.VK_X, "Exit.", 0));
				
		menuBar.add(subMenu);
		
		subMenu = new JMenu("Item");
		subMenu.setMnemonic(KeyEvent.VK_I);
		subMenu.add(newItem("New...", "NEW", this, KeyEvent.VK_N, KeyEvent.VK_N, "Add new item.", 0));
		subMenu.add(newItem("Delete...", "DELETE", this, KeyEvent.VK_D, KeyEvent.VK_D, "Delete current item.", 0));
		subMenu.add(newItem("Delete all...", "DELETEALL", this, KeyEvent.VK_D, KeyEvent.VK_D, "Delete all.", 3));
		
		menuBar.add(subMenu);
		
		return menuBar;
	}
	
	private JMenuItem newItem(String label,
							  String actionCommand,
							  ActionListener menuListener, 
							  int mnemonic,
							  int keyEvent, 
							  String toolTipText,
							  int type)
	{
		JMenuItem m;
		
		m = new JMenuItem(label, mnemonic);
		
		if(type == 0)
			m.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.ALT_MASK));
		else if(type == 1)
			m.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.CTRL_MASK));
		else
			m.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		
		m.getAccessibleContext().setAccessibleDescription(toolTipText);
		m.setToolTipText(toolTipText);
		m.setActionCommand(actionCommand);
		m.addActionListener(menuListener);
		
		return m;
	}

	void setupMainFrame()
	{
		Toolkit tk;
		Dimension d;
		
		setTitle("GUI List Manipulation");
		
		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		//setSize(d.width/2, d.height/2);
		pack();
		setLocation(d.width/4, d.height/4);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("LOAD"))
		{
			int response = fileChooser.showOpenDialog(this);
			if( response == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				
				System.out.println("Opening: " + file.getName() + ".");
			}
			else
			{
				System.out.println("Cancelled by user.");
			}
		}
		else if(e.getActionCommand().equals("SAVE"))
		{
			int response = fileChooser.showSaveDialog(this);
			if( response == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				
				System.out.println("Opening: " + file.getName() + ".");
			}
			else
			{
				System.out.println("Cancelled by user.");
			}
		}
		else if(e.getActionCommand().equals("SAVEAS"))
		{
			int response = fileChooser.showDialog(this, "Save as...");
			if( response == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				
				System.out.println("Opening: " + file.getName() + ".");
			}
			else
			{
				System.out.println("Cancelled by user.");
			}
		}
		else if(e.getActionCommand().equals("NEW"))
		{
			String str = JOptionPane.showInputDialog("Enter string...");
			System.out.println("String: " + str);
		}
		else if(e.getActionCommand().equals("DELETE"))
		{
			System.out.println("Delete Item.");
		}
		else if(e.getActionCommand().equals("DELETEALL"))
		{
			System.out.println("Delete all items.");
		}
		else if(e.getActionCommand().equals("EXIT"))
		{
			System.exit(0);
		}
				
	}
	
}
