package de.elbosso.jconsole;

import javax.management.Notification;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.event.ActionEvent;

class TableView extends Base implements javax.swing.event.TableModelListener
{
	private final de.netsysit.ui.components.SophisticatedRenderingTable table;
	private java.util.List<javax.management.openmbean.CompositeData> latch=new java.util.LinkedList();
	private TableModel model;
	private AbstractAction clearAction;

	TableView()
	{
		super();
		createActions();
		model=new TableModel();
		table=new de.netsysit.ui.components.SophisticatedRenderingTable();
		de.netsysit.model.table.TableSorter sorter=new de.netsysit.model.table.TableSorter(model);
		sorter.addMouseListenerToHeaderInTable(table);
		table.setModel(sorter);
		de.elbosso.ui.renderer.table.SortableTableRenderer tableHeaderRenderer=new de.elbosso.ui.renderer.table.SortableTableRenderer();
		table.getTableHeader().setDefaultRenderer(tableHeaderRenderer);
		table.setDefaultRenderer(java.util.Date.class,de.elbosso.ui.renderer.table.DateRenderer.create(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")));
		add(new javax.swing.JScrollPane(table));
		tb.add(clearAction);
		tb.add(configAction);
		levelModel.addTableModelListener(this);
		loggerModel.addTableModelListener(this);
	}

	private void createActions()
	{
		clearAction =
				new javax.swing.AbstractAction(null, new javax.swing.ImageIcon(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Delete24.gif")))
				{
					public void actionPerformed(java.awt.event.ActionEvent evt)
					{
						model.clear();
					}
				};
	}
	@Override
	protected void appendNextLineActionImpl(ActionEvent e)
	{
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

	@Override
	public void tableChanged(TableModelEvent e)
	{
		model.filter();
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
		void clear()
		{
			store.clear();
			filter();
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
