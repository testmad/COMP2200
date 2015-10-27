//David Walker - 2200
//Project 2c

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener, ListSelectionListener, TableModelListener, DropTargetListener, WindowListener, MouseListener
{
	JFileChooser fileChooser;
	String fileName;
	
	WorkOrderDialog newDialog;
	WorkOrderDialog editDialog;
	
	JButton loadBtn;
	JButton saveBtn;
	JButton addBtn;
	JButton editBtn;
	JButton delBtn;
	JButton exitBtn;
	
	CustomTableModel ctm;
	JTable table;
	
	DropTarget dropTarget;
	
	String fileSignature = "This is my file";
	byte[] sigData = fileSignature.getBytes();
	static boolean hasChanges;
		
	public MainFrame()
	{
		addWindowListener(this);
		hasChanges = false;
		setJMenuBar(new MenuBar(this));
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		loadBtn = new JButton("Load");
		loadBtn.setActionCommand("LOAD");
		loadBtn.addActionListener(this);
		loadBtn.setPreferredSize(new Dimension(100,25));
		
		saveBtn = new JButton("Save");
		saveBtn.setActionCommand("SAVE");
		saveBtn.addActionListener(this);
		saveBtn.setPreferredSize(new Dimension(100,25));
		saveBtn.setEnabled(false);
		
		addBtn = new JButton("Add");
		addBtn.setActionCommand("ADD");
		addBtn.addActionListener(this);
		addBtn.setPreferredSize(new Dimension(100,25));
		
		editBtn = new JButton("Edit");
		editBtn.setActionCommand("EDIT");
		editBtn.addActionListener(this);
		editBtn.setPreferredSize(new Dimension(100,25));
		editBtn.setEnabled(false);
		
		delBtn = new JButton("Delete");
		delBtn.setActionCommand("DELETE");
		delBtn.addActionListener(this);
		delBtn.setPreferredSize(new Dimension(100,25));
		delBtn.setEnabled(false);
		
		exitBtn = new JButton("Exit");
		exitBtn.setActionCommand("EXIT");
		exitBtn.addActionListener(this);
		exitBtn.setPreferredSize(new Dimension(100,25));
		
		ctm = new CustomTableModel();
		table = new JTable(ctm);
		ctm.addTableModelListener(this);

		//table.setRowSelectionAllowed(true);
		//table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
		table.addMouseListener(this);
		
		table.setComponentPopupMenu(new PopupMenu(this));
		
		JScrollPane scrollpane = new JScrollPane(table);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		
		hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addComponent(scrollpane)
			
			.addGroup(layout.createSequentialGroup()
				.addComponent(loadBtn).addGap(12)
				.addComponent(saveBtn).addGap(12)
				.addComponent(addBtn).addGap(12)
				.addComponent(editBtn).addGap(12)
				.addComponent(delBtn).addGap(12)
				.addComponent(exitBtn)
			)
		);

		layout.setHorizontalGroup(hGroup);
		
		layout.linkSize(SwingConstants.HORIZONTAL, addBtn, editBtn, loadBtn, exitBtn, saveBtn, delBtn);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
	   
		vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			.addComponent(scrollpane)
		);
		
		vGroup.addGroup(layout.createParallelGroup()
			.addGap(11)
		);
		
		vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                .addComponent(loadBtn)
	                .addComponent(saveBtn)
	                .addComponent(addBtn)
	                .addComponent(editBtn)
	                .addComponent(delBtn)
	                .addComponent(exitBtn)
                )
			)
		);

		layout.setVerticalGroup(vGroup);
		
		pack();
		
	    table.setRowSorter(new TableRowSorter<CustomTableModel>(ctm));
		
		getRootPane().setDefaultButton(addBtn);
		
		fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		dropTarget = new DropTarget(scrollpane, this);
		setupMainFrame();
	}

	private void setupMainFrame()
	{
		setTitle("Work Order Table");
		setMinimumSize(new Dimension(700,400));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	@SuppressWarnings("resource")
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("LOAD"))
		{
			if(hasChanges)
			{
				int response = JOptionPane.showConfirmDialog (null, "The current table has unsaved changes.\nAre you sure you want to continue?","Confirm",JOptionPane.OK_CANCEL_OPTION);

				if(response == JOptionPane.OK_OPTION)
				{
					response = fileChooser.showOpenDialog(this);
					if( response == JFileChooser.APPROVE_OPTION)
					{
						try
						{
							File file = fileChooser.getSelectedFile();
							fileName = file.getAbsolutePath();
							
							DataInputStream dis = new DataInputStream(new FileInputStream(fileName));
							
							byte[] buffer = new byte[sigData.length];
							dis.read(buffer);
							
							if(Arrays.equals(sigData, buffer))
							{
								ctm.customListModel.load(dis);
								dis.close();
								ctm.customListModel.trimToSize();
								hasChanges = false;
								ctm.fireTableDataChanged();
							}
							else
								throw new IOException("Unsupported File Type: " + fileName);
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
					
				}
			}
			else
			{
				int response = fileChooser.showOpenDialog(this);
				if( response == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						File file = fileChooser.getSelectedFile();
						fileName = file.getAbsolutePath();
						
						DataInputStream dis = new DataInputStream(new FileInputStream(fileName));
						
						byte[] buffer = new byte[sigData.length];
						dis.read(buffer);
						
						if(Arrays.equals(sigData, buffer))
						{
							ctm.customListModel.load(dis);
							dis.close();
							ctm.customListModel.trimToSize();
							hasChanges = false;
							ctm.fireTableDataChanged();
						}
						else
							throw new IOException("Unsupported File Type: " + fileName);
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
						
						DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));
	
						dos.write(sigData);
	
						ctm.customListModel.save(dos);
						dos.close();
						hasChanges = false;
						ctm.fireTableDataChanged();
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
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));

					dos.write(sigData);

					ctm.customListModel.save(dos);
					dos.close();
					hasChanges = false;
					ctm.fireTableDataChanged();
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
					
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));

					dos.write(sigData);

					ctm.customListModel.save(dos);
					dos.close();
					hasChanges = false;
					ctm.fireTableDataChanged();
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
		
		else if(event.getActionCommand().equals("ADD"))
		{
			newDialog = new WorkOrderDialog(ctm.customListModel, this);
			newDialog.setLocationRelativeTo(this);
			newDialog.setVisible(true);
			
		}
		else if(event.getActionCommand().equals("EDIT"))
		{
			int index = table.convertRowIndexToModel(table.getSelectedRow());
			editDialog = new WorkOrderDialog(ctm.customListModel, this, index, (WorkOrder) ctm.customListModel.get(index));
			editDialog.setLocationRelativeTo(this);
			editDialog.setVisible(true);
			
		}
		
		else if(event.getActionCommand().equals("COMPLETE"))
		{
			int index = table.convertRowIndexToModel(table.getSelectedRow());
		
			WorkOrder tmp = (WorkOrder) ctm.customListModel.get(index);
			
			if(tmp.dateOut.equals(""))
			{
				DateFormat df3 = new SimpleDateFormat("MM-dd-yyyy");
				
				Date date = Calendar.getInstance().getTime();
				
				tmp.dateOut = df3.format(date);
				hasChanges = true;
				ctm.fireTableDataChanged();
			}
			else
			{
				int response = JOptionPane.showConfirmDialog (null, "Change completed date to today?","Confirm",JOptionPane.OK_CANCEL_OPTION);

				if(response == JOptionPane.OK_OPTION)
				{
					DateFormat df3 = new SimpleDateFormat("MM-dd-yyyy");
					
					Date date = Calendar.getInstance().getTime();
					
					tmp.dateOut = df3.format(date);
					hasChanges = true;
					ctm.fireTableDataChanged();
				}
				else
				{
				
				}
			}
		}
		
		else if(event.getActionCommand().equals("DELETE"))
		{
			int[] index = table.getSelectedRows();
			
			int response = JOptionPane.showConfirmDialog (null, "Delete the selected item(s)?","Confirm",JOptionPane.OK_CANCEL_OPTION);

			if(response == JOptionPane.OK_OPTION)
			{
				for(int i=index.length - 1; i >= 0;i--)
					ctm.customListModel.remove(table.convertRowIndexToModel(index[i]));

				hasChanges = true;
				ctm.fireTableDataChanged();
			}
			else
			{
			
			}
		}
		
		else if(event.getActionCommand().equals("DELETEALL"))
		{
			int response = JOptionPane.showConfirmDialog (null, "Delete all items?","Confirm",JOptionPane.OK_CANCEL_OPTION);

			if(response == JOptionPane.OK_OPTION)
			{
				ctm.customListModel.clear();
				ctm.customListModel.trimToSize();
				hasChanges = true;
				ctm.fireTableDataChanged();
			}
			else
			{
			
			}
		}
		
		else if(event.getActionCommand().equals("EXIT"))
		{
			if(hasChanges)
			{
				int response = JOptionPane.showConfirmDialog (null, "The current table has unsaved changes.\nAre you sure you want to exit?","Confirm",JOptionPane.OK_CANCEL_OPTION);

				if(response == JOptionPane.OK_OPTION)
				{
					System.exit(0);
				}
				else
				{
					
				}
			}
			else
			{
				System.exit(0);
			}
		}
		
		else if(event.getActionCommand().equals("DEBUG"))
		{
			int response = fileChooser.showSaveDialog(this);
			if( response == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					File file = fileChooser.getSelectedFile();
					fileName = file.getAbsolutePath();
					
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));

					dos.write(sigData);
					
					ctm.customListModel.saveDebug(dos);
					dos.close();
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
			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event)
	{
		int[] selectedRows = table.getSelectedRows();
		
		if(selectedRows.length == 1)
		{
			editBtn.setEnabled(true);
			delBtn.setEnabled(true);
			getJMenuBar().getMenu(1).getItem(1).setEnabled(true);
			getJMenuBar().getMenu(1).getItem(2).setEnabled(true);
			table.getComponentPopupMenu().getComponent(0).setEnabled(true);
		}
		else if(selectedRows.length > 1)
		{
			editBtn.setEnabled(false);
			delBtn.setEnabled(true);
			getJMenuBar().getMenu(1).getItem(1).setEnabled(false);
			getJMenuBar().getMenu(1).getItem(2).setEnabled(true);
			table.getComponentPopupMenu().getComponent(0).setEnabled(false);
		}
		else
		{
			editBtn.setEnabled(false);
			delBtn.setEnabled(false);
			getJMenuBar().getMenu(1).getItem(1).setEnabled(false);
			getJMenuBar().getMenu(1).getItem(2).setEnabled(false);
			table.getComponentPopupMenu().getComponent(0).setEnabled(false);
		}
	}

	@Override
	public void tableChanged(TableModelEvent event)
	{
		if(hasChanges)
		{
			saveBtn.setEnabled(true);
			getJMenuBar().getMenu(0).getItem(1).setEnabled(true);
		}
		else
		{
			saveBtn.setEnabled(false);
			getJMenuBar().getMenu(0).getItem(1).setEnabled(false);
		}
		
		if(ctm.customListModel.isEmpty())
		{
			saveBtn.setEnabled(false);
			getJMenuBar().getMenu(0).getItem(1).setEnabled(false);
			getJMenuBar().getMenu(0).getItem(2).setEnabled(false);
			getJMenuBar().getMenu(1).getItem(3).setEnabled(false);
		}
		else
		{
			getJMenuBar().getMenu(0).getItem(2).setEnabled(true);
			getJMenuBar().getMenu(1).getItem(3).setEnabled(true);
		}
		
		if(event.getType() == TableModelEvent.INSERT)
		{
			
			//int index = table.getRowSorter().convertRowIndexToView(event.getFirstRow());
			//System.out.println(event.getFirstRow());
			//System.out.println(ctm.customListModel.get(event.getFirstRow()));
			table.scrollRectToVisible(table.getCellRect(table.convertRowIndexToView(event.getFirstRow()), 0, true));
			//table.scrollRectToVisible(table.getCellRect(event.getFirstRow(),0, true));
			//table.setRowSelectionInterval(table.convertRowIndexToView(event.getFirstRow()), table.convertRowIndexToView(event.getFirstRow()));
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent event)
	{

	}

	@Override
	public void dragExit(DropTargetEvent event)
	{

	}

	@Override
	public void dragOver(DropTargetDragEvent event)
	{

	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public void drop(DropTargetDropEvent event)
	{
		List<File> fileList;
		Transferable transferableData;
		
		transferableData = event.getTransferable();

		if(hasChanges)
		{
			int response = JOptionPane.showConfirmDialog (null, "The current table has unsaved changes.\nAre you sure you want to continue?","Confirm",JOptionPane.OK_CANCEL_OPTION);

			if(response == JOptionPane.OK_OPTION)
			{
				try
				{
					if(transferableData.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
					{
						event.acceptDrop(DnDConstants.ACTION_COPY);
						
						fileList = (List<File>)(transferableData.getTransferData(DataFlavor.javaFileListFlavor));
						
						if(fileList.size() > 1)
							throw new IOException("Only one file can be dropped at a time.");
						else
						{
							DataInputStream dis = new DataInputStream(new FileInputStream(fileList.get(0).getAbsoluteFile()));
							
							byte[] buffer = new byte[sigData.length];
							dis.read(buffer);
							
							if(Arrays.equals(sigData, buffer))
							{
								ctm.customListModel.load(dis);
								dis.close();
								ctm.customListModel.trimToSize();
								hasChanges = false;
								ctm.fireTableDataChanged();
							}
							else
								throw new IOException("Unsupported File Type: " + fileName);
						}
					}
				}
				catch (UnsupportedFlavorException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				
			}
		}
		else
		{
			try
			{
				if(transferableData.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
				{
					event.acceptDrop(DnDConstants.ACTION_COPY);
					
					fileList = (List<File>)(transferableData.getTransferData(DataFlavor.javaFileListFlavor));
					
					if(fileList.size() > 1)
						throw new IOException("Only one file can be dropped at a time.");
					else
					{
						DataInputStream dis = new DataInputStream(new FileInputStream(fileList.get(0).getAbsoluteFile()));
						
						byte[] buffer = new byte[sigData.length];
						dis.read(buffer);
						
						if(Arrays.equals(sigData, buffer))
						{
							ctm.customListModel.load(dis);
							dis.close();
							ctm.customListModel.trimToSize();
							hasChanges = false;
							ctm.fireTableDataChanged();
						}
						else
							throw new IOException("Unsupported File Type: " + fileName);
					}
				}
			}
			catch (UnsupportedFlavorException e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event)
	{
		
	}

	@Override
	public void windowActivated(WindowEvent event)
	{

	}

	@Override
	public void windowClosed(WindowEvent event)
	{

	}

	@Override
	public void windowClosing(WindowEvent event)
	{
	    this.actionPerformed(new ActionEvent(this, getDefaultCloseOperation(), "EXIT"));
	}

	@Override
	public void windowDeactivated(WindowEvent event)
	{
	
	}

	@Override
	public void windowDeiconified(WindowEvent event)
	{

	}

	@Override
	public void windowIconified(WindowEvent event)
	{

	}

	@Override
	public void windowOpened(WindowEvent event)
	{

	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
		if (event.getClickCount() == 2)
		{
	        this.actionPerformed(new ActionEvent(this,0, "EDIT"));
        }
	}

	@Override
	public void mouseEntered(MouseEvent event)
	{
	
	}

	@Override
	public void mouseExited(MouseEvent event)
	{
	
	}

	@Override
	public void mousePressed(MouseEvent event)
	{
		if (event.getButton() == MouseEvent.BUTTON3)
		{
			if(table.getSelectedRowCount() < 1)
			{
				Point point = event.getPoint();
		        int currentRow = table.rowAtPoint(point);
		        table.setRowSelectionInterval(currentRow, currentRow);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{

	}
	
	public int scrollTo(int index)
	{
		int sortedIndex = table.convertRowIndexToView(index);
		//System.out.println(index);
		//table.scrollRectToVisible(table.getCellRect(sortedIndex, 0, true));
		//table.scrollRectToVisible(table.getCellRect(event.getFirstRow(),0, true));
		//table.setRowSelectionInterval(event.getFirstRow(), event.getFirstRow());
		return sortedIndex;
	}
	
	
}
