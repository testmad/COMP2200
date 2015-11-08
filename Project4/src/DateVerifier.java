import java.text.*;
import java.util.*;

import javax.swing.*;

public class DateVerifier extends InputVerifier
{
	public DateVerifier()
	{
		
	}
	
	public boolean verify(JComponent input)
	{
		DateFormat df1 = new SimpleDateFormat("MM/dd/yy");
		DateFormat df2 = new SimpleDateFormat("MM-dd-yy");
		DateFormat df3 = new SimpleDateFormat("MM-dd-yyyy");
		
		ParsePosition pos = new ParsePosition(0);
		
		df1.setLenient(false);
		df2.setLenient(false);
		
		String strDate;
		strDate = ((JTextField)input).getText().trim();
		
		Date date;
		
		Date today = Calendar.getInstance().getTime();
		
		if(strDate.equals(""))
		{
			System.out.println("Empty Date.");
			return true;
		}
		else
		{
			try
			{
				date = df1.parse(strDate, pos);
				if(pos.getIndex() == strDate.length() && date != null)
					((JTextField)input).setText(df3.format(date));
				else
					throw new ParseException(strDate, 0);
			}
			catch(ParseException e1)
			{
				try
				{
					pos.setIndex(0);
					date = df2.parse(strDate, pos);
					if(pos.getIndex() == strDate.length() && date != null)
						((JTextField)input).setText(df3.format(date));
					else
						throw new ParseException(strDate, 0);
				}
				catch(ParseException e2)
				{
					JOptionPane.showMessageDialog(null, "Invalid date format." + System.getProperty("line.separator") + "Format example: 3-26-2012 or 03/26/2012", "Error!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
			
		if(date.after(today))
		{
			JOptionPane.showMessageDialog(null, "Future dates not valid", "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
		{
			return true;
		}
	}
}