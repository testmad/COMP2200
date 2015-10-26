import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TableMouseListener extends MouseAdapter
{
	private JTable table;
    
    public TableMouseListener(JTable table)
    {
        this.table = table;
    }
     
    @Override
    public void mousePressed(MouseEvent event)
    {
        Point point = event.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);
    }
}
