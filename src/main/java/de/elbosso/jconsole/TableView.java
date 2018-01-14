package de.elbosso.jconsole;

import javax.management.Notification;
import java.awt.*;
import java.awt.event.ActionEvent;

class TableView extends Base
{
	private final de.netsysit.ui.components.SophisticatedRenderingTable table;
	private java.util.List<javax.management.openmbean.CompositeData> latch=new java.util.LinkedList();
	private TableModel model;

	TableView()
	{
		super();
		model=new TableModel();
		table=new de.netsysit.ui.components.SophisticatedRenderingTable();
		de.netsysit.model.table.TableSorter sorter=new de.netsysit.model.table.TableSorter(model);
		sorter.addMouseListenerToHeaderInTable(table);
		table.setModel(sorter);
		add(new javax.swing.JScrollPane(table));
		javax.swing.JToggleButton toggle=new javax.swing.JToggleButton(scrollLockAction);
		toggle.setSelectedIcon((de.netsysit.util.ResourceLoader.getIcon("action/drawable-mdpi/ic_lock_black_48dp.png")));
		tb.add(toggle);
		tb.add(configAction);
	}

	@Override
	protected void appendNextLineActionImpl(ActionEvent e)
	{
		javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable()
		{
			public void run()
			{
//				model.invertDisplayOrder();
			}
		});

	}

	@Override
	protected void acknowledgeDataImpl(Notification notification)
	{
		javax.management.openmbean.CompositeData data = (javax.management.openmbean.CompositeData) notification.getUserData();
		latch.add(data);
	}

	@Override
	protected void updateGUIImpl(Notification notification)
	{
		model.addData(latch);
		latch.clear();
	}
	class TableModel extends de.netsysit.model.table.EventHandlingSupport
	{
		private java.util.List<javax.management.openmbean.CompositeData> store=new java.util.LinkedList();
		private java.util.List<javax.management.openmbean.CompositeData> filtered=new java.util.LinkedList();

		void addData(java.util.List<javax.management.openmbean.CompositeData> newdata)
		{
			store.addAll(newdata);
			filter();
//			fireTableChanged();
		}
		void filter()
		{
			filtered.clear();
			for(javax.management.openmbean.CompositeData data:store)
			{
				java.lang.String level = data.get("level").toString();
				java.lang.String loggerName=data.get("loggerName").toString();
				if ((levelModel.isActive(level))&&(loggerModel.isActive(loggerName)))
				{
					filtered.add(data);
				}
			}
			fireTableChanged();
		}

		@Override
		public int getRowCount()
		{
			return filtered.size();
		}

		@Override
		public int getColumnCount()
		{
			return 4;
		}

		@Override
		public String getColumnName(int columnIndex)
		{
			java.lang.String rv=null;
			switch(columnIndex)
			{
				case 0:
				{
					rv="Timestamp";
					break;
				}
				case 1:
				{
					rv="Level";
					break;
				}
				case 2:
				{
					rv="Logger";
					break;
				}
				case 3:
				{
					rv="Message";
					break;
				}
			}
			return rv;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			return columnIndex==0?java.util.Date.class:java.lang.String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex)
		{
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{
			javax.management.openmbean.CompositeData item=filtered.get(rowIndex);
			java.lang.Object rv=null;
			switch(columnIndex)
			{
				case 0:
				{
					rv=new java.util.Date(((java.lang.Long)item.get("timeStamp")).longValue());
					break;
				}
				case 1:
				{
					rv=item.get("level").toString();
					break;
				}
				case 2:
				{
					rv=item.get("loggerName").toString();
					break;
				}
				case 3:
				{
					rv=item.get("message").toString();
					break;
				}
			}
			return rv;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex)
		{

		}
	}
}
