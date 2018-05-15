package de.elbosso.jconsole;

import javax.swing.event.TableModelEvent;

public class LoggerModel extends StringBooleanModel
{
	private final java.util.List<java.lang.String> loggerNames;

	public LoggerModel()
	{
		super();
		loggerNames=new java.util.LinkedList();
	}

	public void add(java.lang.String loggerName)
	{
		if(map.containsKey(loggerName)==false)
		{
			map.put(loggerName, Boolean.FALSE);
			loggerNames.add(loggerName);
			java.util.Collections.sort(loggerNames);
			fireTableChanged();
		}
	}

	@Override
	public int getRowCount()
	{
		return map.size();
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return columnIndex == 0 ? "Logger" : "active";
	}

	@Override
	protected java.lang.String getString(int index)
	{
		return loggerNames.get(index);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if (columnIndex == 1)
			map.put(getString(rowIndex), (java.lang.Boolean) aValue);
		TableModelEvent tme=new TableModelEvent(this,rowIndex);
		fireTableChanged(tme);
	}
}