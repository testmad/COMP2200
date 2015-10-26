import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class PopupMenu extends JPopupMenu
{
	public PopupMenu(ActionListener listener)
	{
		this.add(newItem("Edit Work Order", "EDIT", listener));
		this.add(newItem("Delete Work Order", "DELETE", listener));
		this.add(newItem("Mark Completed", "COMPLETE", listener));
	}

	private JMenuItem newItem(String label, String actionCommand, ActionListener menuListener)
	{
		JMenuItem m;
		
		m = new JMenuItem(label);
		
		m.setActionCommand(actionCommand);
		m.addActionListener(menuListener);
		
		return m;
	}
}
