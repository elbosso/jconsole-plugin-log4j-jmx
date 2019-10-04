package de.elbosso.jconsole;

public class Helper
{
	public static void main(java.lang.String[] args)
	{
		try
		{
			java.util.Properties iconFallbacks = new java.util.Properties();
			java.io.InputStream is=de.netsysit.util.ResourceLoader.getResource("de/elbosso/ressources/data/icon_trans_material.properties").openStream();
			iconFallbacks.load(is);
			is.close();
			de.netsysit.util.ResourceLoader.configure(iconFallbacks);
		}
		catch(java.io.IOException ioexp)
		{
			ioexp.printStackTrace();
		}

		de.netsysit.util.ResourceLoader.setSize(de.netsysit.util.ResourceLoader.IconSize.small);
		TableView tableView=new TableView();
		System.out.println("TableView created");
		TextAreaView textAreaView=new TextAreaView();
		System.out.println("TextAreaView created");
		//Log4JJMXJConsolePlugin plugin=new Log4JJMXJConsolePlugin();
		//plugin.getTabs();
		javax.swing.JFrame f=new javax.swing.JFrame();
		f.setContentPane(tableView);
		f.setContentPane(textAreaView);
		f.pack();
		f.setLocation(0,0);
		f.setSize(1024,700);
		f.setVisible(true);
	}
}
