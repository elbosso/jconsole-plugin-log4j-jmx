package de.elbosso.jconsole;

import javax.swing.event.TableModelEvent;

public class LevelModel extends StringBooleanModel
{

	public LevelModel()
	{
		super();
		map.put("DEBUG",java.lang.Boolean.FALSE);
		map.put("TRACE",java.lang.Boolean.FALSE);
		map.put("INFO",java.lang.Boolean.FALSE);
		map.put("ERROR",java.lang.Boolean.TRUE);
		map.put("FATAL",java.lang.Boolean.TRUE);
		map.put("WARN",java.lang.Boolean.TRUE);
	}


	@Override
	public int getRowCount()
	{
		return 6;
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return columnIndex==0?"Level":"active";
	}

	protected java.lang.String getString(int index)
	{
		java.lang.String level="TRACE";
		switch(index)
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
			map.put(getString(rowIndex),(java.lang.Boolean)aValue);
		TableModelEvent tme=new TableModelEvent(this,rowIndex);
		fireTableChanged(tme);
	}
}
