import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

public class FocusPolicy extends FocusTraversalPolicy
{
	Vector<Component> order;

	public FocusPolicy(Vector<Component> order)
	{
		this.order = new Vector<Component>(order.size());
		this.order.addAll(order);
	}
	
	public Component getComponentAfter(Container focusCycleRoot, Component component)
	{
		int index = (order.indexOf(component) + 1) % order.size();
		return order.get(index);
	}
	
	public Component getComponentBefore(Container focusCycleRoot, Component component)
	{
		int index = order.indexOf(component) - 1;
		if (index < 0)
		{
			index = order.size() - 1;
		}
		return order.get(index);
	}
	
	public Component getDefaultComponent(Container focusCycleRoot)
	{
		return order.get(0);
	}
	
	public Component getLastComponent(Container focusCycleRoot)
	{
		return order.lastElement();
	}
	
	public Component getFirstComponent(Container focusCycleRoot)
	{
		return order.get(0);
	}
}
