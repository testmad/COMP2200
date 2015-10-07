import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

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
		
		//newDialog = new MyDialog(this);
		//editDialog = new MyDialog(this, "entry");
		
		getRootPane().setDefaultButton(addBtn);
		
		setupMainFrame();
	}
	
	void setupMainFrame()
	{
		Toolkit tk;
		Dimension d;
		
		tk = Toolkit.getDefaultToolkit();
				
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
			newDialog.setLocationRelativeTo(this);
			newDialog.setVisible(true);
		}
		else if(event.getActionCommand().equals("EDIT"))
		{
			//editDialog.setLocationRelativeTo(this);
			editDialog = new MyDialog(this, "meh");
			editDialog.setLocationRelativeTo(this);
			editDialog.
			editDialog.setVisible(true);
		}
	}
}

class MyDialog extends JDialog implements ActionListener
{
	String[] labelNames = {"Name:", "Department:", "Date Ordered:", "Date Filled:", "Description:", "Billing Rate:"};
	String[] comboItems = {"SALES", "HARDWARE", "ELECTRONICS"}; 
	
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
	
	public MyDialog(JFrame aFrame, String str)
	{
		super(aFrame, "Edit Work Order");
		addForm();
		
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
		
		this.add(nameLbl);
		this.add(nameField);
		this.add(deptLbl);
		this.add(deptCombobox);
		this.add(dateInLbl);
		this.add(dateInField);
		this.add(dateOutLbl);
		this.add(dateOutField);
		this.add(descLbl);
		this.add(descField);
		this.add(rateLbl);
		this.add(rateField);
		
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
		//clear the form data.
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("CANCEL"))
			System.out.println("clicked cancel");
		else if(event.getActionCommand().equals("SAVECLOSE"))
		{
			System.out.println("clicked save and close");
			
			new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), Float.parseFloat(rateField.getText())).debugSave();
		}
			
		else if(event.getActionCommand().equals("SAVEANOTHER"))
		{
			System.out.println("clicked save and add");
			
			new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), Float.parseFloat(rateField.getText())).debugSave();
			clearData();
		}
	}	
}

class WorkOrder
{
	String name;
	int department;
	String dateIn;
	String dateOut;
	String description;
	float rate;
	
	public WorkOrder(DataInputStream dis)
	{
		try {
			name = dis.readUTF();
			department = dis.readInt();
			dateIn = dis.readUTF();
			dateOut = dis.readUTF();
			description = dis.readUTF();
			rate = dis.readFloat();
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public WorkOrder(String strName, int dept, String date1, String date2, String desc, float pRate)
	{
		name = strName;
		department = dept;
		dateIn = date1;
		dateOut = date2;
		description = desc;
		rate = pRate;
		
	}
	
	void loadOrder(DataInputStream dis)
	{
		System.out.println("LOADING DATA.");
		try {
			name = dis.readUTF();
			department = dis.readInt();
			dateIn = dis.readUTF();
			dateOut = dis.readUTF();
			description = dis.readUTF();
			rate = dis.readFloat();
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void saveOrder(DataOutputStream dos)
	{
		System.out.println("SAVING DATA.");
		try {
			dos.writeUTF(name);
			dos.writeInt(department);
			dos.writeUTF(dateIn);
			dos.writeUTF(dateOut);
			dos.writeUTF(description);
			dos.writeFloat(rate);
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void debugLoad()
	{
		Random r = new Random();
		
		name = "" + randString(r.nextInt(10)) + " " + randString(r.nextInt(10));
		department = r.nextInt(3);
		dateIn = LocalDate.parse((CharSequence) new Date()).toString();
		dateOut = LocalDate.parse((CharSequence) new Date()).plusDays(r.nextInt(20)).toString();
		description = "" + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10)) + " " + randString(r.nextInt(10));
		rate = r.nextFloat() * r.nextInt(5);
	}
	
	void debugSave()
	{
		String newLine = System.getProperty("line.separator");
		
		System.out.println(name + newLine + department + newLine + dateIn + newLine + dateOut + newLine + description + newLine + rate);
	}
	
	String randString(int characters)
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
