package de.elbosso.jconsole;

public class LoggerModel extends de.netsysit.model.table.EventHandlingSupport
{
	private final java.util.Map<java.lang.String, java.lang.Boolean> loggerMap;
	private final java.util.List<java.lang.String> loggerNames;

	public LoggerModel()
	{
		super();
		loggerMap = new java.util.HashMap();
		loggerNames=new java.util.LinkedList();
	}

	public void add(java.lang.String loggerName)
	{
		if(loggerMap.containsKey(loggerName)==false)
		{
			loggerMap.put(loggerName, Boolean.FALSE);
			loggerNames.add(loggerName);
			java.util.Collections.sort(loggerNames);
			fireTableChanged();
		}
	}

	public boolean isActive(java.lang.String logger)
	{
		return loggerMap.get(logger).booleanValue();
	}

	@Override
	public int getRowCount()
	{
		return loggerMap.size();
	}

	@Override
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return columnIndex == 0 ? "Logger" : "active";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return columnIndex == 0 ? java.lang.String.class : java.lang.Boolean.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex == 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		java.lang.Object rv = null;

		java.lang.String logger = loggerNames.get(rowIndex);

		rv = columnIndex == 0 ? logger : isActive(logger);

		return rv;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if (columnIndex == 1)
			loggerMap.put(loggerNames.get(rowIndex), (java.lang.Boolean) aValue);
	}
}