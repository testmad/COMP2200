import java.io.*;

import javax.swing.*;

@SuppressWarnings({ "serial", "rawtypes" })
public class CustomListModel extends DefaultListModel implements DataManager
{
	CustomTableModel tm;
	MainFrame	frame;
	
	public CustomListModel(CustomTableModel atm, MainFrame jframe)
	{
		tm = atm;
		frame = jframe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addWorkOrder(WorkOrder wo)
	{
		this.addElement(wo);
		MainFrame.hasChanges = true;
		
		tm.fireTableDataChanged();
		
		frame.scrollTo(this.getSize()-1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void replaceWorkOrder(WorkOrder wo, int index)
	{
		this.set(index, wo);
		MainFrame.hasChanges = true;
		
		tm.fireTableDataChanged();
				
		frame.scrollTo(index);
	}

	@SuppressWarnings("unchecked")
	public void load(DataInputStream dis)
	{
		this.clear();
		
		int numOrders = 0;
		try
		{
			numOrders = dis.readInt();
			
			for(int i = 0; i < numOrders; i++)
			{
				this.addElement(new WorkOrder(dis));
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		tm.fireTableDataChanged();
	}
	
	public void save(DataOutputStream dos)
	{
		try
		{
			dos.writeInt(this.getSize());
			
			for(int i = 0; i < this.getSize(); i++)
			{
				((WorkOrder) this.get(i)).saveOrder(dos);
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void saveDebug(DataOutputStream dos)
	{
		int numItems = 40;
		try
		{
			dos.writeInt(numItems);
			
			for(int i = 0; i < numItems; i++)
			{
				WorkOrder.saveOrderDebug(dos, WorkOrder.debug() );
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
