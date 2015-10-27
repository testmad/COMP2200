import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class CustomTableModel extends AbstractTableModel
{
	CustomListModel customListModel;
	
	private String[] columnNames = new String[]{"Employee Name","Department","Date Ordered","Date Filled","Job Description", "Hourly Rate"};
	
	private String[] deptNames = new String[]{"DUMMY", "SALES", "HARDWARE", "ELECTRONICS"};
	
	public CustomTableModel()
	{
		customListModel = new CustomListModel(this);
	}
	
	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

	@Override
	public int getRowCount()
	{
		return customListModel.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		WorkOrder w;
		w = (WorkOrder) customListModel.elementAt(rowIndex);
		
		if(columnIndex == 0)
			return w.name;
		else if(columnIndex == 1)
			return deptNames[w.department];
		else if(columnIndex == 2)
			return w.dateIn;
		else if(columnIndex == 3)
			return w.dateOut;
		else if(columnIndex == 4)
			return w.description;
		else if(columnIndex == 5)
		{
			DecimalFormat decim = new DecimalFormat("0.00");
			String tmp = decim.format(w.rate);
			return tmp;
		}
			
		else
			return null;
	}
	
	public Class getColumnClass(int columnIndex)
	{
		if(columnIndex == 0)
			return String.class;
		else if(columnIndex == 1)
			return Integer.class;
		else if(columnIndex == 2)
			return String.class;
		else if(columnIndex == 3)
			return String.class;
		else if(columnIndex == 4)
			return String.class;
		else if(columnIndex == 5)
			return String.class;
		else
			return null;
	}
	
	public String getColumnName(int col)
	{
        return columnNames[col];
    }
}
