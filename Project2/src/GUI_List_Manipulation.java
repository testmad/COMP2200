// David Walker
// Comp2200
// Project 2

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class GUI_List_Manipulation
{
	public static void main(String[] args)
	{
		new MyFrameClass();
	}
}

class MyFrameClass extends JFrame implements ActionListener, ListSelectionListener
{
	JList<String> myListBox;
	DefaultListModel<String> myListModel;
	JScrollPane sp;
	JFileChooser fileChooser;
	JButton btnDelete;
	String fileName;
	
	MyFrameClass()
	{
		myListModel = new DefaultListModel<String>();
		myListBox = new JList<String>(myListModel);
		sp = new JScrollPane(myListBox);
		sp.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,5), new LineBorder(Color.darkGray)));
		
		Container cp;

		JPanel pnlButton;
		
		JButton btnLoad;
		JButton btnSave;
		JButton btnSaveAs;
		JButton btnAdd;
		
		JButton btnExit;
		
		myListBox.addListSelectionListener(this);
		
		btnLoad = new JButton("Load");
		btnLoad.setMinimumSize(new Dimension(100,25));
		btnLoad.setMaximumSize(new Dimension(100,25));
		btnLoad.setToolTipText("Load file.");
		btnLoad.setActionCommand("LOAD");
		btnLoad.addActionListener(this);
		
		btnSave = new JButton("Save");
		btnSave.setMinimumSize(new Dimension(100,25));
		btnSave.setMaximumSize(new Dimension(100,25));
		btnSave.setToolTipText("Save file.");
		btnSave.setActionCommand("SAVE");
		btnSave.addActionListener(this);
		
		btnSaveAs = new JButton("Save As");
		btnSaveAs.setMinimumSize(new Dimension(100,25));
		btnSaveAs.setMaximumSize(new Dimension(100,25));
		btnSaveAs.setToolTipText("Save file as...");
		btnSaveAs.setActionCommand("SAVEAS");
		btnSaveAs.addActionListener(this);
		
		btnAdd = new JButton("Add");
		btnAdd.setMinimumSize(new Dimension(100,25));
		btnAdd.setMaximumSize(new Dimension(100,25));
		btnAdd.setToolTipText("Add an item.");
		btnAdd.setActionCommand("NEW");
		btnAdd.addActionListener(this);
		
		btnDelete = new JButton("Delete");
		btnDelete.setMinimumSize(new Dimension(100,25));
		btnDelete.setMaximumSize(new Dimension(100,25));
		btnDelete.setToolTipText("Delete selected item(s).");
		btnDelete.setActionCommand("DELETE");
		btnDelete.addActionListener(this);
		btnDelete.setEnabled(false);
		
		btnExit = new JButton("Exit");
		btnExit.setMinimumSize(new Dimension(100,25));
		btnExit.setMaximumSize(new Dimension(100,25));
		btnExit.setActionCommand("EXIT");
		btnExit.addActionListener(this);
		
		pnlButton = new JPanel();
		pnlButton.setLayout(new BoxLayout(pnlButton,BoxLayout.Y_AXIS));
		pnlButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		
		pnlButton.add(Box.createRigidArea(new Dimension(10, 10)));
		pnlButton.add(btnLoad);
		pnlButton.add(Box.createRigidArea(new Dimension(10, 10)));
		pnlButton.add(btnSave);
		pnlButton.add(Box.createRigidArea(new Dimension(10, 10)));
		pnlButton.add(btnSaveAs);
		pnlButton.add(Box.createRigidArea(new Dimension(10, 10)));
		pnlButton.add(btnAdd);
		pnlButton.add(Box.createRigidArea(new Dimension(10, 10)));
		pnlButton.add(btnDelete);
		pnlButton.add(Box.createRigidArea(new Dimension(10, 10)));
		pnlButton.add(btnExit);
		
		cp = getContentPane();
		cp.add(sp, BorderLayout.CENTER);
		cp.add(pnlButton, BorderLayout.EAST);
		
		setJMenuBar(newMenuBar());

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

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
		subMenu.add(newItem("Delete...", "DELETE", this, KeyEvent.VK_D, KeyEvent.VK_D, "Delete current item.", 0)).setEnabled(false);
		subMenu.add(newItem("Delete all...", "DELETEALL", this, KeyEvent.VK_D, KeyEvent.VK_D, "Delete all.", 3)).setEnabled(false);;
		
		menuBar.add(subMenu);
		
		return menuBar;
	}
	
	//Added a type arg so that I could have different key masks based on a given int.
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
		
		setMinimumSize(new Dimension(500, 282));
		
		pack();
		setLocation(d.width/4, d.height/4);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("LOAD"))
		{
			int response = fileChooser.showOpenDialog(this);
			if( response == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					File file = fileChooser.getSelectedFile();
					fileName = file.getAbsolutePath();
					BufferedReader br = new BufferedReader(new FileReader(file));
					String str = null;
					
					while ((str = br.readLine()) != null)
					{
						if(str.trim().length() > 0)
							myListModel.addElement(str);
					}
					
					br.close();
					myListModel.trimToSize();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				System.out.println("Cancelled by user.");
			}
		}

		else if(event.getActionCommand().equals("SAVE"))
		{
			if(fileName == null)
			{
				int response = fileChooser.showSaveDialog(this);
				if( response == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						File file = fileChooser.getSelectedFile();
						fileName = file.getAbsolutePath();
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						
						//This took too long for me to figure out.  I feel dumb for not getting it sooner.
						for(int i = 0; i < myListModel.getSize(); i++) {
			                String str = (String)myListModel.getElementAt(i);
			                bw.write(str, 0, str.length());
			                bw.newLine();
			            }
			            bw.close();
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					System.out.println("Cancelled by user.");
				}
			}
			else
			{
				try
				{
					File file = new File(fileName);
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					
					//This took too long for me to figure out.  I feel dumb for not getting it sooner.
					for(int i = 0; i < myListModel.getSize(); i++) {
		                String str = (String)myListModel.getElementAt(i);
		                bw.write(str, 0, str.length());
		                bw.newLine();
		            }
		            bw.close();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}

		else if(event.getActionCommand().equals("SAVEAS"))
		{
			int response = fileChooser.showDialog(this, "Save as...");
			if( response == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					File file = fileChooser.getSelectedFile();
					fileName = file.getAbsolutePath();
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					
					//This took too long for me to figure out.  I feel dumb for not getting it sooner.
					for(int i = 0; i < myListModel.getSize(); i++) {
		                String str = (String)myListModel.getElementAt(i);
		                bw.write(str, 0, str.length());
		                bw.newLine();
		            }
		            bw.close();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				System.out.println("Cancelled by user.");
			}
		}

		else if(event.getActionCommand().equals("NEW"))
		{
			String str = JOptionPane.showInputDialog("Enter string...");
			if(str != null)
			{
				if(str.trim().length() > 0)
				{
					myListModel.addElement(str);
					myListModel.trimToSize();
				}
				else
					JOptionPane.showMessageDialog(null, "Input invalid.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				
			}
		}

		else if(event.getActionCommand().equals("DELETE"))
		{
			if(myListBox.getSelectedIndices().length > 0)
			{
				int response = JOptionPane.showConfirmDialog (null, "Delete the selected item(s)?","Confirm",JOptionPane.OK_CANCEL_OPTION);

				if(response == JOptionPane.OK_OPTION)
				{
					int[] selectedIndices = myListBox.getSelectedIndices();
					for (int i = selectedIndices.length-1; i >=0; i--)
					{
						myListModel.removeElementAt(selectedIndices[i]);
						myListModel.trimToSize();
					}
					btnDelete.setEnabled(false);
					getJMenuBar().getMenu(1).getItem(1).setEnabled(false);
				}
				else
				{
					
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No item(s) selected to delete.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}

		else if(event.getActionCommand().equals("DELETEALL"))
		{
			int response = JOptionPane.showConfirmDialog (null, "Delete all item(s)?","Confirm",JOptionPane.OK_CANCEL_OPTION);

			if(response == JOptionPane.OK_OPTION)
			{
				myListModel.clear();
				myListModel.trimToSize();
			}
			else
			{
				
			}
		}

		else if(event.getActionCommand().equals("EXIT"))
		{
			System.exit(0);
		}
		
		if(myListModel.isEmpty())
		{
			getJMenuBar().getMenu(1).getItem(2).setEnabled(false);
		}
		else
		{
			getJMenuBar().getMenu(1).getItem(2).setEnabled(true);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event)
	{
		if(myListBox.getSelectedIndices().length > 0)
		{
			btnDelete.setEnabled(true);
			getJMenuBar().getMenu(1).getItem(1).setEnabled(true);
		}
		else
		{
			btnDelete.setEnabled(false);
			getJMenuBar().getMenu(1).getItem(1).setEnabled(false);
		}
		
		if(myListModel.isEmpty())
		{
			getJMenuBar().getMenu(1).getItem(2).setEnabled(false);
		}
		else
		{
			getJMenuBar().getMenu(1).getItem(2).setEnabled(true);
		}
	}
}
