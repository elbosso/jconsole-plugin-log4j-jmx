package de.elbosso.jconsole;

import javax.management.Notification;
import java.awt.*;

public class TableView extends javax.swing.JPanel implements javax.management.NotificationListener
{
	private final de.netsysit.ui.components.SophisticatedRenderingTable table;
	private final javax.swing.JLabel statusLabel;

	TableView()
	{
		super(new java.awt.BorderLayout());
		statusLabel=new javax.swing.JLabel("not Connected!");
		add(statusLabel, BorderLayout.SOUTH);
		table=new de.netsysit.ui.components.SophisticatedRenderingTable();
		add(new javax.swing.JScrollPane(table));
	}

	@Override
	public void handleNotification(Notification notification, Object handback)
	{

	}
	void setStatus(java.lang.String newStatus)
	{
		statusLabel.setText(newStatus);
	}
}
