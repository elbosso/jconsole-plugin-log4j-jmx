package de.elbosso.jconsole;

public class Helper
{
	public static void main(java.lang.String[] args)
	{
		TableView tableView=new TableView();
		System.out.println("TableView created");
		//Log4JJMXJConsolePlugin plugin=new Log4JJMXJConsolePlugin();
		//plugin.getTabs();
		javax.swing.JFrame f=new javax.swing.JFrame();
		f.setContentPane(new javax.swing.JScrollPane(tableView));
		f.pack();
		f.setLocation(0,0);
		f.setSize(1024,700);
		f.setVisible(true);
	}
}
