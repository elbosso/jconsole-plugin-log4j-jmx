package de.elbosso.jconsole;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class StringBooleanModel extends de.netsysit.model.table.EventHandlingSupport
{
	private javax.swing.Action selectAllAction;
	private javax.swing.Action selectNoneAction;
	private javax.swing.Action toggleSelectionAction;
	protected final java.util.Map<java.lang.String,java.lang.Boolean> map;

	StringBooleanModel()
	{
		super();
		map =new java.util.HashMap();
		createActions();
	}
	private void createActions()
	{
		selectAllAction=new javax.swing.AbstractAction("select all")
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(java.lang.String key:map.keySet())
				{
					map.put(key,Boolean.TRUE);
				}
				fireTableChanged();
			}
		};
		selectNoneAction=new javax.swing.AbstractAction("select none")
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(java.lang.String key:map.keySet())
				{
					map.put(key,Boolean.FALSE);
				}
				fireTableChanged();
			}
		};
		toggleSelectionAction=new javax.swing.AbstractAction("toggle selection")
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(java.lang.String key:map.keySet())
				{
					map.put(key,map.get(key).booleanValue()?Boolean.FALSE:Boolean.TRUE);
				}
				fireTableChanged();
			}
		};
	}

	public Action getSelectAllAction()
	{
		return selectAllAction;
	}

	public Action getSelectNoneAction()
	{
		return selectNoneAction;
	}

	public Action getToggleSelectionAction()
	{
		return toggleSelectionAction;
	}

	public boolean isActive(java.lang.String level)
	{
		return map.get(level).booleanValue();
	}

	@Override
	public int getColumnCount()
	{
		return 2;
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

		java.lang.String level = getString(rowIndex);

		rv=columnIndex==0?level:isActive(level);

		return rv;
	}
	protected abstract java.lang.String getString(int index);

}
