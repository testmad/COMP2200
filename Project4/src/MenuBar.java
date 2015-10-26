import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar
{
	public MenuBar(ActionListener listener)
	{
		JMenu subMenu;
		
		subMenu = new JMenu("File");
		subMenu.setMnemonic(KeyEvent.VK_F);
		subMenu.add(newItem("Load...", "LOAD", listener, KeyEvent.VK_L, KeyEvent.VK_L, "Load file.", 0));
		subMenu.add(newItem("Save...", "SAVE", listener, KeyEvent.VK_S, KeyEvent.VK_S, "Save file.", 1)).setEnabled(false);
		subMenu.add(newItem("Save as...", "SAVEAS", listener, KeyEvent.VK_S, KeyEvent.VK_S, "Save file as...", 3)).setEnabled(false);
		subMenu.add(newItem("Exit...", "EXIT", listener, KeyEvent.VK_X, KeyEvent.VK_X, "Exit.", 0));
				
		this.add(subMenu);
		
		subMenu = new JMenu("Item");
		subMenu.setMnemonic(KeyEvent.VK_I);
		subMenu.add(newItem("Add...", "ADD", listener, KeyEvent.VK_N, KeyEvent.VK_N, "Add new item.", 0));
		subMenu.add(newItem("Edit...", "EDIT", listener, KeyEvent.VK_E, KeyEvent.VK_E, "Edit current item.", 0)).setEnabled(false);
		subMenu.add(newItem("Delete...", "DELETE", listener, KeyEvent.VK_D, KeyEvent.VK_D, "Delete current item.", 0)).setEnabled(false);
		subMenu.add(newItem("Delete all...", "DELETEALL", listener, KeyEvent.VK_D, KeyEvent.VK_D, "Delete all.", 3)).setEnabled(false);;
		
		this.add(subMenu);
		
		subMenu = new JMenu("Debug");
		subMenu.setMnemonic(KeyEvent.VK_D);
		subMenu.add(newItem("Add...", "DEBUG", listener, KeyEvent.VK_N, KeyEvent.VK_N, "Add new items.", 3));
		
		this.add(subMenu);
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
}