import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

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
		Container cp;
		JPanel mainPanel;
		
		addBtn = new JButton("Add");
		addBtn.setActionCommand("ADD");
		addBtn.addActionListener(this);
		addBtn.setPreferredSize(new Dimension(100,25));
		
		editBtn = new JButton("Edit");
		editBtn.setActionCommand("EDIT");
		editBtn.addActionListener(this);
		editBtn.setPreferredSize(new Dimension(100,25));
		
		mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,30,30));
		mainPanel.add(addBtn);
		mainPanel.add(editBtn);
	
		cp = getContentPane();
		cp.add(mainPanel, BorderLayout.CENTER);
		
		getRootPane().setDefaultButton(addBtn);
		
		setupMainFrame();
	}
	
	void setupMainFrame()
	{
		setTitle("WorkOrderDialog");
		setMinimumSize(new Dimension(306, 124));
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
			//Uncomment for random generated work order.
			editDialog = new MyDialog(this, WorkOrder.debugLoad());
			
			//Uncomment for loading from file.
//			FileInputStream fis = null;
//			try
//			{
//				fis = new FileInputStream("workorder.dat");
//			}
//			catch (FileNotFoundException e)
//			{
//				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
//			}
//			
//          DataInputStream dis = new DataInputStream(fis);
//          editDialog = new MyDialog(this, new WorkOrder().loadOrder(dis));
//			dis.close();
//			fis.close();
            
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
		addForm();

		JButton saveAddBtn;

		saveAddBtn = new JButton("Save/Another");
		saveAddBtn.setPreferredSize(new Dimension(100,25));
		saveAddBtn.setActionCommand("SAVEANOTHER");
		saveAddBtn.addActionListener(this);

		this.add(saveAddBtn);
	}
	
	public MyDialog(JFrame aFrame, WorkOrder wo)
	{
		super(aFrame, "Edit Work Order");
		addForm();
		
		this.nameField.setText(wo.name);
		this.deptCombobox.setSelectedIndex(wo.department);
		this.dateInField.setText(wo.dateIn);
		this.dateOutField.setText(wo.dateOut);
		this.descField.setText(wo.description);
		this.rateField.setText(wo.rate);
	}
	
	void addForm()
	{
		this.setSize(300,400);
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(10, 1));
		
		JLabel nameLbl = new JLabel(labelNames[0]);
		JLabel deptLbl = new JLabel(labelNames[1]);
		JLabel dateInLbl = new JLabel(labelNames[2]);
		JLabel dateOutLbl = new JLabel(labelNames[3]);
		JLabel descLbl = new JLabel(labelNames[4]);
		JLabel rateLbl = new JLabel(labelNames[5]);
		
		nameField = new JTextField();
		deptCombobox = new JComboBox<String>(comboItems);
		dateInField = new JTextField();
		dateOutField = new JTextField();
		descField = new JTextField();
		rateField = new JTextField();
		
		TextVerifier tv = new TextVerifier();
		DateVerifier dv = new DateVerifier();
		RateVerifier rv = new RateVerifier();
		
		this.add(nameLbl);
		this.add(nameField);
		nameField.setInputVerifier(tv);
		this.add(deptLbl);
		this.add(deptCombobox);
		this.add(dateInLbl);
		this.add(dateInField);
		dateInField.setInputVerifier(dv);
		this.add(dateOutLbl);
		this.add(dateOutField);
		dateOutField.setInputVerifier(dv);
		this.add(descLbl);
		this.add(descField);
		descField.setInputVerifier(tv);
		this.add(rateLbl);
		this.add(rateField);
		rateField.setInputVerifier(rv);
		
		JButton cancelBtn;
		JButton saveCloseBtn;
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(100,25));
		cancelBtn.setActionCommand("CANCEL");
		cancelBtn.addActionListener(this);
		
		saveCloseBtn = new JButton("Save/Close");
		saveCloseBtn.setPreferredSize(new Dimension(100,25));
		saveCloseBtn.setActionCommand("SAVECLOSE");
		saveCloseBtn.addActionListener(this);

		this.add(cancelBtn);
		this.add(saveCloseBtn);
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
			System.out.println("clicked cancel");
			dispose();
		}
		
		else if(event.getActionCommand().equals("SAVECLOSE"))
		{
			System.out.println("clicked save and close");
			
			//Uncomment for debug saving.
			new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), rateField.getText()).debugSave();
			
			//Uncomment for real saving.
//			FileOutputStream fos = null;
//			
//			try
//			{
//				fos = new FileOutputStream("workorder.dat");
//			}
//			catch (FileNotFoundException e)
//			{
//				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
//			}
//			
//          DataOutputStream dos = new DataOutputStream(fos);
//          new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), rateField.getText()).saveOrder(dos);
//			fos.flush();
//			fos.close();
//			dos.close();
			
			
			dispose();
		}
			
		else if(event.getActionCommand().equals("SAVEANOTHER"))
		{
			System.out.println("clicked save and add");
			
			//Uncomment for debug saving.
			new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), rateField.getText()).debugSave();
			
			//Uncomment for real saving.
//			FileOutputStream fos = null;
//			
//			try
//			{
//				fos = new FileOutputStream("workorder.dat");
//			}
//			catch (FileNotFoundException e)
//			{
//				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
//			}
//			
//          DataOutputStream dos = new DataOutputStream(fos);
//          new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), rateField.getText()).saveOrder(dos);
//			fos.flush();
//			fos.close();
//			dos.close();
            
			clearData();
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
	
	static WorkOrder loadOrder(DataInputStream dis)
	{
		String tmpname = null;
		int tmpdepartment = 0;
		String tmpdateIn = null;
		String tmpdateOut = null;
		String tmpdescription = null;
		String tmprate = null;
		
		System.out.println("LOADING DATA.");
		try
		{
			tmpname = dis.readUTF();
			tmpdepartment = dis.readInt();
			tmpdateIn = dis.readUTF();
			tmpdateOut = dis.readUTF();
			tmpdescription = dis.readUTF();
			tmprate = dis.readUTF();
		}
		catch (IOException e)
		{	
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		WorkOrder tmp = new WorkOrder(tmpname, tmpdepartment, tmpdateIn, tmpdateOut, tmpdescription, tmprate);
		System.out.println(tmpdepartment);
		return tmp;
	}
	
	void saveOrder(DataOutputStream dos)
	{
		String newLine = System.getProperty("line.separator");
		
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

