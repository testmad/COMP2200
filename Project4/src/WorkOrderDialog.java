import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.GroupLayout.*;

@SuppressWarnings("serial")
public class WorkOrderDialog extends JDialog implements ActionListener
{
	String[] labelNames = {"Employee Name:", "Department:", "Date Ordered:", "Date Filled:", "Job Description:", "Hourly Rate:"};
	String[] comboItems = {"Select...", "SALES", "HARDWARE", "ELECTRONICS"}; 
	
	private JTextField nameField;
	private JComboBox<String> deptCombobox;
	private JTextField dateInField;
	private JTextField dateOutField;
	private JTextField descField;
	private JTextField rateField;
	
	int editIndex = -1;

	DataManager dm;
	
	FocusPolicy newPolicy;
	
	public WorkOrderDialog(DataManager datamanager, JFrame frame)
	{
		super(frame, "New Work Order");
		dm = datamanager;
		editIndex = -1;
		addForm(1);
	}

	public WorkOrderDialog(DataManager datamanager, JFrame frame, int index, WorkOrder wo)
	{
		super(frame, "Edit Work Order");
		dm = datamanager;
		editIndex = index;
		addForm(0);
		
		this.nameField.setText(wo.name);
		this.deptCombobox.setSelectedIndex(wo.department);
		this.dateInField.setText(wo.dateIn);
		this.dateOutField.setText(wo.dateOut);
		this.descField.setText(wo.description);
		this.rateField.setText(wo.rate.toString());
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
		
		JButton cancelBtn;
		JButton saveCloseBtn;
		JButton saveAddBtn;
		
		saveAddBtn = new JButton("Add Another");
		saveAddBtn.setActionCommand("SAVEANOTHER");
		saveAddBtn.addActionListener(this);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setActionCommand("CANCEL");
		cancelBtn.addActionListener(this);
		
		saveCloseBtn = new JButton("Save");
		saveCloseBtn.setActionCommand("SAVECLOSE");
		saveCloseBtn.addActionListener(this);
		
//		JPanel labelPnl = new JPanel();
//		labelPnl.add(nameLbl);
//		labelPnl.add(deptLbl);
//		labelPnl.add(dateInLbl);
//		labelPnl.add(dateOutLbl);
//		labelPnl.add(descLbl);
//		labelPnl.add(nameLbl);

		TextVerifier tv = new TextVerifier();
		DateVerifier dv = new DateVerifier();
		RateVerifier rv = new RateVerifier();
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(nameLbl)
				.addComponent(deptLbl)
				.addComponent(dateInLbl)
				.addComponent(dateOutLbl)
				.addComponent(descLbl)
				.addComponent(rateLbl)
		);
		
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(nameField)
				.addComponent(deptCombobox)
				.addComponent(dateInField)
				.addComponent(dateOutField)
				.addComponent(descField)
				.addComponent(rateField)
		);
		
		if(type == 1)
		{
			hGroup.addGroup(layout.createParallelGroup()
				.addComponent(saveAddBtn)
				.addComponent(saveCloseBtn)
				.addComponent(cancelBtn)
			);
		}
		else
		{
			hGroup.addGroup(layout.createParallelGroup()
				.addComponent(saveCloseBtn)
				.addComponent(cancelBtn)
			);
		}
		layout.setHorizontalGroup(hGroup);
		
		if(type == 1)
			layout.linkSize(SwingConstants.HORIZONTAL, saveAddBtn, saveCloseBtn, cancelBtn);
		else
			layout.linkSize(SwingConstants.HORIZONTAL, saveCloseBtn, cancelBtn);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
	   
		if(type == 1)
		{
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(nameLbl)
				.addComponent(nameField)
				.addComponent(saveAddBtn)
			);
			
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(deptLbl)
				.addComponent(deptCombobox)
				.addComponent(saveCloseBtn)
			);
			
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(dateInLbl)
				.addComponent(dateInField)
				.addComponent(cancelBtn)
			);
		}
		else
		{
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(nameLbl)
				.addComponent(nameField)
				.addComponent(saveCloseBtn)
			);
			
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(deptLbl)
				.addComponent(deptCombobox)
				.addComponent(cancelBtn)
			);
			
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(dateInLbl)
				.addComponent(dateInField)
			);
		}
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
			.addComponent(dateOutLbl)
			.addComponent(dateOutField)
		);
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
			.addComponent(descLbl)
			.addComponent(descField)
		);
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
			.addComponent(rateLbl)
			.addComponent(rateField)
		);
		
		layout.setVerticalGroup(vGroup);

		Vector<Component> orderAdd = new Vector<Component>(9);
		orderAdd.add(nameField);
		orderAdd.add(deptCombobox);
		orderAdd.add(dateInField);
		orderAdd.add(dateOutField);
		orderAdd.add(descField);
		orderAdd.add(rateField);
		orderAdd.add(saveAddBtn);
		orderAdd.add(saveCloseBtn);
		orderAdd.add(cancelBtn);
	    
	    Vector<Component> orderEdit = new Vector<Component>(8);
	    orderEdit.add(nameField);
	    orderEdit.add(deptCombobox);
	    orderEdit.add(dateInField);
	    orderEdit.add(dateOutField);
	    orderEdit.add(descField);
	    orderEdit.add(rateField);
	    orderEdit.add(saveCloseBtn);
	    orderEdit.add(cancelBtn);
	    
	    if(type==1)
	    	newPolicy = new FocusPolicy(orderAdd);
	    else
	    	newPolicy = new FocusPolicy(orderEdit);
	    
	    
	    this.setFocusTraversalPolicy(newPolicy);
		
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
				WorkOrder tmp = new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), Double.parseDouble(rateField.getText()));
				dm.addWorkOrder(tmp);
				clearData();
				//dm.hasChanged();
				nameField.requestFocus();
			}
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
				WorkOrder tmp = new WorkOrder(nameField.getText(), deptCombobox.getSelectedIndex(), dateInField.getText(), dateOutField.getText(), descField.getText(), Double.parseDouble(rateField.getText()));
				if(editIndex == -1)
				{
					dm.addWorkOrder(tmp);
				}
				else
				{
					dm.replaceWorkOrder(tmp, editIndex);
				}
				
				//dm.hasChanged();
				dispose();
			}
		}
	}
	
	
}

class FocusPolicy
extends FocusTraversalPolicy
{
Vector<Component> order;

public FocusPolicy(Vector<Component> order) {
this.order = new Vector<Component>(order.size());
this.order.addAll(order);
}
public Component getComponentAfter(Container focusCycleRoot,
                         Component aComponent)
{
int idx = (order.indexOf(aComponent) + 1) % order.size();
return order.get(idx);
}

public Component getComponentBefore(Container focusCycleRoot,
                          Component aComponent)
{
int idx = order.indexOf(aComponent) - 1;
if (idx < 0) {
idx = order.size() - 1;
}
return order.get(idx);
}

public Component getDefaultComponent(Container focusCycleRoot) {
return order.get(0);
}

public Component getLastComponent(Container focusCycleRoot) {
return order.lastElement();
}

public Component getFirstComponent(Container focusCycleRoot) {
return order.get(0);
}
}
