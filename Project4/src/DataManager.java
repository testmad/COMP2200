import javax.swing.JTable;

public interface DataManager
{
	void addWorkOrder(WorkOrder wo);
	void replaceWorkOrder(WorkOrder wo, int index);
	//void hasChanged();
}
