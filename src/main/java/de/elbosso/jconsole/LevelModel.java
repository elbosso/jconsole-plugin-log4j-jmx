package de.elbosso.jconsole;

import javax.swing.event.TableModelEvent;
import java.util.Map;

public class LevelModel extends de.netsysit.model.table.EventHandlingSupport
{
	private final java.util.Map<java.lang.String,java.lang.Boolean> levelMap;

	public LevelModel()
	{
		super();
		levelMap=new java.util.HashMap();
		levelMap.put("DEBUG",java.lang.Boolean.FALSE);
		levelMap.put("TRACE",java.lang.Boolean.FALSE);
		levelMap.put("INFO",java.lang.Boolean.FALSE);
		levelMap.put("ERROR",java.lang.Boolean.TRUE);
		levelMap.put("FATAL",java.lang.Boolean.TRUE);
		levelMap.put("WARN",java.lang.Boolean.TRUE);
	}

	public boolean isActive(java.lang.String level)
	{
		return levelMap.get(level).booleanValue();
	}

	@Override
	public int getRowCount()
	{
		return 6;
	}

	@Override
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return columnIndex==0?"Level":"active";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return columnIndex==0?java.lang.String.class:java.lang.Boolean.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex==1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		java.lang.Object rv=null;

		java.lang.String level = getLevel(rowIndex);

		rv=columnIndex==0?level:isActive(level);

		return rv;
	}

	private String getLevel(int rowIndex)
	{
		java.lang.String level="TRACE";
		switch(rowIndex)
		{
			case 1:
			{
				level="DEBUG";
				break;
			}
			case 2:
			{
				level="INFO";
				break;
			}
			case 3:
			{
				level="WARN";
				break;
			}
			case 4:
			{
				level="ERROR";
				break;
			}
			case 5:
			{
				level="FATAL";
				break;
			}
		}
		return level;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if(columnIndex==1)
			levelMap.put(getLevel(rowIndex),(java.lang.Boolean)aValue);
		TableModelEvent tme=new TableModelEvent(this,rowIndex);
		fireTableChanged(tme);
	}
}
