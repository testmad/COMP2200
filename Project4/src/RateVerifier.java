import java.text.*;

import javax.swing.*;

public class RateVerifier extends InputVerifier
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
				DecimalFormat formatter = new DecimalFormat("0.00");
				Double tmp1 = Double.parseDouble(((JTextField)input).getText().trim().replaceAll(",",""));
				if(tmp1 < 0 || tmp1 > 500  )
					throw new NumberFormatException();
				else
				{
					String tmp2 = formatter.format(tmp1);
					((JTextField)input).setText(tmp2);
				}
			}
			catch(NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "Invalid rate amount." + System.getProperty("line.separator") + "Amount example: 312.50 or 312.5", "Error!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		return true;
		}
	}
}