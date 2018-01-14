package de.elbosso.jconsole;

import javax.management.Notification;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TableView extends Base
{
	private final de.netsysit.ui.components.SophisticatedRenderingTable table;

	TableView()
	{
		super();
		table=new de.netsysit.ui.components.SophisticatedRenderingTable();
		add(new javax.swing.JScrollPane(table));
	}

	@Override
	protected void appendNextLineActionImpl(ActionEvent e)
	{

	}

	@Override
	protected void acknowledgeDataImpl(Notification notification)
	{

	}

	@Override
	protected void updateGUIImpl(Notification notification)
	{

	}

}
