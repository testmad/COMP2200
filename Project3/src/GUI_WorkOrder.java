//David Walker - 2200
//Project 2b

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class GUI_WorkOrder
{
	public static void main(String args[])
	{
		new MyFrameClass();
	}
}

class MyFrameClass extends JFrame implements ActionListener
{
	MyDialog newDialog;
	MyDialog editDialog;
	JButton addBtn;
	JButton editBtn;
	
	public MyFrameClass()
	{
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		 
		addBtn = new JButton("Add");
		addBtn.setActionCommand("ADD");
		addBtn.addActionListener(this);
		addBtn.setPreferredSize(new Dimension(100,25));
		
		editBtn = new JButton("Edit");
		editBtn.setActionCommand("EDIT");
		editBtn.addActionListener(this);
		editBtn.setPreferredSize(new Dimension(100,25));
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		
		hGroup.addGroup(layout.createParallelGroup().
					addComponent(addBtn));
		hGroup.addGroup(layout.createParallelGroup().
				addGap(30));
		hGroup.addGroup(layout.createParallelGroup().
				addComponent(editBtn));
		layout.setHorizontalGroup(hGroup);
		
		layout.linkSize(SwingConstants.HORIZONTAL, addBtn, editBtn);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
	   
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(addBtn).addComponent(editBtn));

		layout.setVerticalGroup(vGroup);
		
		pack();
		
		getRootPane().setDefaultButton(addBtn);
		
		setupMainFrame();
	}
	
	void setupMainFrame()
	{
		setTitle("WorkOrder");
		setMinimumSize(new Dimension(270,90));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("ADD"))
		{
			newDialog = new MyDialog(this);
			newDialog.setLocationRelativeTo(this);
			newDialog.setVisible(true);
		}
		else if(event.getActionCommand().equals("EDIT"))
		{
			editDialog = new MyDialog(this, WorkOrder.debugLoad());
			editDialog.setLocationRelativeTo(this);
			editDialog.setVisible(true);
		}
	}
}

class TextVerifier extends InputVerifier
{
	public TextVerifier()
	{
	}
	
	public boolean verify(JComponent input)
	{
		return true;
	}
}

class RateVerifier extends InputVerifier
{
	public RateVerifier()
	{
	}
	
	public boolean verify(JComponent input)
	{
		if(((JTextField)input).getText().trim().equals(""))
		{
			System.out.println("Empty Rate.");
			return true;
		}
		else
		{
			try
			{
				DecimalFormat decim = new DecimalFormat("0.00");
				Double tmp1 = Double.parseDouble(((JTextField)input).getText().trim().replaceAll(",",""));
				String tmp2 = decim.format(tmp1);
				((JTextField)input).setText(tmp2);
			}
			catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "Invalid rate amount." + System.getProperty("line.separator") + "Amount example: 3,012.50 or 3012.5", "Error!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		return true;
		}
	}
}

class DateVerifier extends InputVerifier
{
	public DateVerifier()
	{
	}
	
	public boolean verify(JComponent input)
	{
		DateFormat df1 = new SimpleDateFormat("MM/dd/yy");
		DateFormat df2 = new SimpleDateFormat("MM-dd-yy");
		DateFormat df3 = new SimpleDateFormat("MM-dd-yyyy");
		
		df1.setLenient(false);
		df2.setLenient(false);
		
		String strDate;
		strDate = ((JTextField)input).getText().trim();
		
		Date date;
		
		if(strDate.equals(""))
		{
			System.out.println("Empty Date.");
			return true;
		}
		else
		{
			try
			{
				date = df1.parse(strDate);
				((JTextField)input).setText(df3.format(date));
			}
			catch(ParseException e1)
			{
				try
				{
					date = df2.parse(strDate);
					((JTextField)input).setText(df3.format(date));
				}
				catch(ParseException e2)
				{
					JOptionPane.showMessageDialog(null, "Invalid date format." + System.getProperty("line.separator") + "Format example: 3-26-2012 or 03/26/2012", "Error!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		return true;
		}
	}
}

class MyDialog extends JDialog implements ActionListener
{
	String[] labelNames = {"Name:", "Department:", "Date Ordered:", "Date Filled:", "Description:", "Billing Rate:"};
	String[] comboItems = {"Select...", "SALES", "HARDWARE", "ELECTRONICS"}; 
	
	private JTextField nameField;
	private JComboBox<String> deptCombobox;
	private JTextField dateInField;
	private JTextField dateOutField;
	private JTextField descField;
	private JTextField rateField;
	
	public MyDialog(JFrame aFrame)
	{
		super(aFrame, "New Work Order");
		addForm(1);
	}
	
	public MyDialog(JFrame aFrame, WorkOrder wo)
	{
		super(aFrame, "Edit Work Order");
		addForm(0);
		
		this.nameField.setText(wo.name);
		this.deptCombobox.setSelectedIndex(wo.department);
		this.dateInField.setText(wo.dateIn);
		this.dateOutField.setText(wo.dateOut);
		this.descField.setText(wo.description);
		this.rateField.setText(wo.rate);
	}
	
	void addForm(int type)
	{
		this.setMinimumSize(new Dimension(400,250));
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Dimension tfSize = new Dimension(100,27);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel nameLbl = new JLabel(labelNames[0]);
		JLabel deptLbl = new JLabel(labelNames[1]);
		JLabel dateInLbl = new JLabel(labelNames[2]);
		JLabel dateOutLbl = new JLabel(labelNames[3]);
		JLabel descLbl = new JLabel(labelNames[4]);
		JLabel rateLbl = new JLabel(labelNames[5]);
		
		JButton cancelBtn;
		JButton saveCloseBtn;
		JButton saveAddBtn;

		saveAddBtn = new JButton("Save/Another");
		saveAddBtn.setActionCommand("SAVEANOTHER");
		saveAddBtn.addActionListener(this);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setActionCommand("CANCEL");
		cancelBtn.addActionListener(this);
		
		saveCloseBtn = new JButton("Save/Close");
		saveCloseBtn.setActionCommand("SAVECLOSE");
		saveCloseBtn.addActionListener(this);
		
		nameField = new JTextField();
		nameField.setPreferredSize(tfSize);
		deptCombobox = new JComboBox<String>(comboItems);
		dateInField = new JTextField();
		dateInField.setPreferredSize(tfSize);
		dateOutField = new JTextField();
		dateOutField.setPreferredSize(tfSize);
		descField = new JTextField();
		descField.setPreferredSize(tfSize);
		rateField = new JTextField();
		rateField.setPreferredSize(tfSize);

		TextVerifier tv = new TextVerifier();
		DateVerifier dv = new DateVerifier();
		RateVerifier rv = new RateVerifier();
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		
		hGroup.addGroup(layout.createParallelGroup().
	            addComponent(nameLbl).addComponent(deptLbl).addComponent(dateInLbl).addComponent(dateOutLbl).addComponent(descLbl).addComponent(rateLbl));
		hGroup.addGroup(layout.createParallelGroup().
	            addComponent(nameField).addComponent(deptCombobox).addComponent(dateInField).addComponent(dateOutField).addComponent(descField).addComponent(rateField));
		
		if(type == 1)
		{
			hGroup.addGroup(layout.createParallelGroup().
				addComponent(saveAddBtn).addComponent(saveCloseBtn).addComponent(cancelBtn));
		}
		else
		{
			hGroup.addGroup(layout.createParallelGroup().
					addComponent(saveCloseBtn).addComponent(cancelBtn));
		}
		layout.setHorizontalGroup(hGroup);
		
		if(type == 1)
			layout.linkSize(SwingConstants.HORIZONTAL, saveAddBtn, saveCloseBtn, cancelBtn);
		else
			layout.linkSize(SwingConstants.HORIZONTAL, saveCloseBtn, cancelBtn);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
	   
		if(type == 1)
		{
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(nameLbl).addComponent(nameField).addComponent(saveAddBtn));
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(deptLbl).addComponent(deptCombobox).addComponent(saveCloseBtn));
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(dateInLbl).addComponent(dateInField).addComponent(cancelBtn));
		}
		else
		{
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(nameLbl).addComponent(nameField).addComponent(saveCloseBtn));
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(deptLbl).addComponent(deptCombobox).addComponent(cancelBtn));
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(dateInLbl).addComponent(dateInField));
		}
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(dateOutLbl).addComponent(dateOutField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(descLbl).addComponent(descField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(rateLbl).addComponent(rateField));
		layout.setVerticalGroup(vGroup);

		nameField.setInputVerifier(tv);
		dateInField.setInputVerifier(dv);
		dateOutField.setInputVerifier(dv);
		descField.setInputVerifier(tv);
		rateField.setInputVerifier(rv);
	}
	
	void clearData()
	{
		nameField.setText("");
		deptCombobox.setSelectedIndex(0);
		dateInField.setText("");
		dateOutField.setText("");
		descField.setText("");
		rateField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("CANCEL"))
		{
			dispose();
		}
		
		else if(event.getActionCommand().equals("SAVECLOSE"))
		{
			boolean dCheck = true;
			boolean cCheck = true;
			
			if(!dateOutField.getText().trim().isEmpty())
			{
				try	
				{
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		            Date dateIn = sdf.parse(((JTextField)dateInField).getText().trim());
		            Date dateOut = sdf.parse(((JTextField)dateOutField).getText().trim());
		            
		            if(dateIn.after(dateOut))
		            {
		            	dCheck = false;
						JOptionPane.showMessageDialog(null, "Date ordered cannot be after date filled.", "Error!", JOptionPane.ERROR_MESSAGE);
		            }
		            else
		            {
		            	dCheck = true;
		            }
				}
				catch(ParseException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
	
			if(nameField.getText().trim().isEmpty() ||
				deptCombobox.getSelectedIndex() == 0 ||
				dateInField.getText().trim().isEmpty() ||
				rateField.getText().trim().isEmpty())
			{
				cCheck = false;
				JOptionPane.showMessageDialog(null, "Required field(s) is empty.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			else if(dCheck && cCheck)
			{
				new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), rateField.getText()).debugSave();
				dispose();
			}
			
		}
			
		else if(event.getActionCommand().equals("SAVEANOTHER"))
		{
			boolean dCheck = true;
			boolean cCheck = true;
			
			if(!dateOutField.getText().trim().isEmpty())
			{
				try	
				{
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		            Date dateIn = sdf.parse(((JTextField)dateInField).getText().trim());
		            Date dateOut = sdf.parse(((JTextField)dateOutField).getText().trim());
		            
		            if(dateIn.after(dateOut))
		            {
		            	dCheck = false;
						JOptionPane.showMessageDialog(null, "Date ordered cannot be after date filled.", "Error!", JOptionPane.ERROR_MESSAGE);
		            }
		            else
		            {
		            	dCheck = true;
		            }
				}
				catch(ParseException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
	
			if(nameField.getText().trim().isEmpty() ||
				deptCombobox.getSelectedIndex() == 0 ||
				dateInField.getText().trim().isEmpty() ||
				rateField.getText().trim().isEmpty())
			{
				cCheck = false;
				JOptionPane.showMessageDialog(null, "Required field(s) is empty.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			else if(dCheck && cCheck)
			{
				new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), rateField.getText()).debugSave();
				clearData();
				nameField.requestFocus();
			}
		}
	}	
}

class WorkOrder
{
	String name = null;
	int department = 0;
	String dateIn = null;
	String dateOut = null;
	String description = null;
	String rate = null;
	
	public WorkOrder()
	{
		
	}
	
	//Redundant, but leaving it here to look back on.
//	public WorkOrder(DataInputStream dis)
//	{
//		try
//		{
//			name = dis.readUTF();
//			department = dis.readInt();
//			dateIn = dis.readUTF();
//			dateOut = dis.readUTF();
//			description = dis.readUTF();
//			rate = dis.readUTF();
//		}
//		catch (IOException e)
//		{			
//			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
//		}
//	}
	
	public WorkOrder(String strName, int dept, String date1, String date2, String desc, String tmprate)
	{
		name = strName;
		department = dept;
		dateIn = date1;
		dateOut = date2;
		description = desc;
		rate = tmprate;
	}
	
	public WorkOrder loadOrder(DataInputStream dis)
	{
		try
		{
			name = dis.readUTF();
			department = dis.readInt();
			dateIn = dis.readUTF();
			dateOut = dis.readUTF();
			description = dis.readUTF();
			rate = dis.readUTF();
		}
		catch (IOException e)
		{	
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		WorkOrder tmp = new WorkOrder(name, department, dateIn, dateOut, description, rate);
		return tmp;
	}
	
	void saveOrder(DataOutputStream dos)
	{
		System.out.println("SAVING DATA.");
		try
		{
			dos.writeUTF(name);
			dos.writeInt(department);
			dos.writeUTF(dateIn);
			dos.writeUTF(dateOut);
			dos.writeUTF(description);
			dos.writeUTF(rate);
		}
		catch (IOException e)
		{	
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	static WorkOrder debugLoad()
	{
		DecimalFormat decim = new DecimalFormat("0.00");
		Random r = new Random();
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		String tmpdate = sdf.format(cal.getTime());
		
		cal.add(Calendar.DATE, r.nextInt(31));
		String tmpdate2 = sdf.format(cal.getTime());
		
		String tmpname = "" + randString(r.nextInt(10)) + " " + randString(r.nextInt(10));
		int tmpdepartment = r.nextInt(3)+1;
		String tmpdateIn = tmpdate;
		String tmpdateOut = tmpdate2;
		String tmpdescription = "" + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10));
		String tmprate = decim.format(r.nextDouble() * r.nextInt(5));
		
		WorkOrder tmp = new WorkOrder(tmpname, tmpdepartment, tmpdateIn, tmpdateOut, tmpdescription, tmprate);
		
		return tmp;
	}
	
	void debugSave()
	{
		String newLine = System.getProperty("line.separator");
		
		System.out.println(	name + newLine +
							department + newLine +
							dateIn + newLine +
							dateOut + newLine +
							description + newLine +
							rate);
	}
	
	static String randString(int characters)
	{
		Random r = new Random();
		String temp = "" + (char)(r.nextInt(26) + 'A');
		
		for(int i = 0; i < characters; i++)
		{
			temp += "" + (char)(r.nextInt(26) + 'a');
		}
		return temp;
	}
}
