import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.JOptionPane;

public class WorkOrder
{
	String name = null;
	int department = 0;
	String dateIn = null;
	String dateOut = null;
	String description = null;
	Double rate = null;
	
	public WorkOrder(String strName, int dept, String date1, String date2, String desc, Double tmprate)
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
		String tname = null ;
		int tdepartment = 0;
		String tdateIn = null;
		String tdateOut = null;
		String tdescription = null ;
		Double trate = null;
		
		try
		{
			tname = dis.readUTF();
			tdepartment = dis.readInt();
			tdateIn = dis.readUTF();
			tdateOut = dis.readUTF();
			tdescription = dis.readUTF();
			trate = dis.readDouble();
		}
		catch (IOException e)
		{	
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		WorkOrder wo = new WorkOrder(tname, tdepartment, tdateIn, tdateOut, tdescription, trate);
		return wo;
	}
	
	static void saveOrder(DataOutputStream dos, WorkOrder wo)
	{
		try
		{
			dos.writeUTF(wo.name);
			dos.writeInt(wo.department);
			dos.writeUTF(wo.dateIn);
			dos.writeUTF(wo.dateOut);
			dos.writeUTF(wo.description);
			dos.writeDouble(wo.rate);
		}
		catch (IOException e)
		{	
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	static WorkOrder debug()
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
		Double tmprate = Double.parseDouble(decim.format(r.nextDouble() * 10));
		
		WorkOrder tmp = new WorkOrder(tmpname, tmpdepartment, tmpdateIn, tmpdateOut, tmpdescription, tmprate);
		
		return tmp;
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
