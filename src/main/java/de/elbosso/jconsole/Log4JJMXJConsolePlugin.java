package de.elbosso.jconsole;

import org.apache.log4j.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;

/*
Copyright (c) 2012-2018.
Juergen Key. Alle Rechte vorbehalten.
Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form,
mit oder ohne Veraenderung, sind unter den folgenden Bedingungen zulaessig:
   1. Weiterverbreitete nichtkompilierte Exemplare muessen das obige Copyright,
die Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext
enthalten.
   2. Weiterverbreitete kompilierte Exemplare muessen das obige Copyright,
die Liste der Bedingungen und den folgenden Haftungsausschluss in der
Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
werden, enthalten.
   3. Weder der Name des Autors noch die Namen der Beitragsleistenden
duerfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software
abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet
werden.
DIESE SOFTWARE WIRD VOM AUTOR UND DEN BEITRAGSLEISTENDEN OHNE
JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFUEGUNG GESTELLT, DIE
UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER
SOFTWARE FUER EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL IST DER AUTOR
ODER DIE BEITRAGSLEISTENDEN FUER IRGENDWELCHE DIREKTEN, INDIREKTEN,
ZUFAELLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGENDEN SCHAEDEN (UNTER ANDEREM
VERSCHAFFEN VON ERSATZGUETERN ODER -DIENSTLEISTUNGEN; EINSCHRAENKUNG DER
NUTZUNGSFAEHIGKEIT; VERLUST VON NUTZUNGSFAEHIGKEIT; DATEN; PROFIT ODER
GESCHAEFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER
VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER
UNERLAUBTE HANDLUNG (INKLUSIVE FAHRLAESSIGKEIT) VERANTWORTLICH, AUF WELCHEM
WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR,
WENN SIE AUF DIE MOEGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
*/
public class Log4JJMXJConsolePlugin extends com.sun.tools.jconsole.JConsolePlugin
{

	private final java.util.Map<String, javax.swing.JPanel> tabs = new java.util.LinkedHashMap<String, javax.swing.JPanel>();
	private final TableView tableView;
	private final TextAreaView textAreaView;
	private javax.swing.Action pauseAction;
	private boolean connected;

	static{
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
	}
	
	public Log4JJMXJConsolePlugin()
	{
		de.elbosso.util.Utilities.configureBasicStdoutLogging(Level.TRACE);
//		java.lang.Thread.currentThread().setContextClassLoader(Log4JJMXJConsolePlugin.class.getClassLoader());
		try
		{
			java.lang.String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
					"<java version=\"1.8.0_144\" class=\"java.beans.XMLDecoder\">\n" +
					" <object class=\"de.netsysit.util.validator.rules.ContainsSubstringRule\">\n" +
					"  <void property=\"casesensitive\">\n" +
					"   <boolean>true</boolean>\n" +
					"  </void>\n" +
					"  <void property=\"substring\">\n" +
					"   <string>blocked</string>\n" +
					"  </void>\n" +
					" </object>\n" +
					"</java>";
			java.beans.XMLDecoder dec = new java.beans.XMLDecoder(new java.io.ByteArrayInputStream(xml.getBytes()));
			java.lang.Object o = dec.readObject();
			System.out.println(o);
			System.out.println(xml);
		}
		catch(java.lang.Throwable t)
		{
			t.printStackTrace();
		}
		de.netsysit.util.ResourceLoader.setSize(de.netsysit.util.ResourceLoader.IconSize.small);
		tableView=new TableView();
		tabs.put("table", tableView);
		textAreaView=new TextAreaView();
		tabs.put("textArea", textAreaView);
		createActions();
		textAreaView.addToToolbar(pauseAction);
	}
	private void createActions()
	{
		pauseAction=new javax.swing.AbstractAction(null,(de.netsysit.util.ResourceLoader.getIcon("toolbarButtonGraphics/media/Pause24.gif")))
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textAreaView.setPaused(((java.lang.Boolean)pauseAction.getValue(Action.SELECTED_KEY)).booleanValue());
			}
		};
		pauseAction.putValue(Action.SELECTED_KEY, Boolean.FALSE);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public java.util.Map<String, javax.swing.JPanel> getTabs() {
		return java.util.Collections.unmodifiableMap(tabs);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public javax.swing.SwingWorker<?, ?> newSwingWorker() {
		switch (getContext().getConnectionState()) {
			case CONNECTING:
			case DISCONNECTED:
				connected=false;
				return null;

			default:
				if(connected==false)
				{
					javax.management.MBeanServerConnection connection = getContext().getMBeanServerConnection();
					try
					{
						javax.management.ObjectName name = new javax.management.ObjectName("jmxlogger:type=LogEmitter");
						try
						{
							connection.addNotificationListener(name, tableView, null, null);
							tableView.setStatus(null);
							connection.addNotificationListener(name, textAreaView, null, null);
							textAreaView.setStatus(null);
							connected = true;
						} catch (javax.management.InstanceNotFoundException exp)
						{

						}
					} catch (java.lang.Throwable t)
					{
						t.printStackTrace();
					}
					return new Updater();
				}
				else
					return null;
		}
	}

	private class Updater extends javax.swing.SwingWorker<Object, Object> {
		@Override
		protected Object doInBackground() throws Exception {
			return null;
		}

		@Override
		protected void done() {
			try {
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}
}
