package de.elbosso.jconsole;

public class TableView extends javax.swing.JPanel
{
	private final de.netsysit.ui.components.SophisticatedRenderingTable table;

	TableView()
	{
		super(new java.awt.BorderLayout());
		table=new de.netsysit.ui.components.SophisticatedRenderingTable();
		add(table);
	}
}
