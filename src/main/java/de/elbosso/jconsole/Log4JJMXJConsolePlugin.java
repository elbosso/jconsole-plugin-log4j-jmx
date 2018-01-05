package de.elbosso.jconsole;

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
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Save24.gif", "device/drawable-mdpi/ic_sd_storage_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/SaveAs24.gif", "toolbarButtonGraphics/general/Save24.gif&action/drawable-mdpi/ic_help_outline_black_36dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Delete16.gif", "action/drawable-mdpi/ic_delete_black_36dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Delete24.gif", "action/drawable-mdpi/ic_delete_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Cut24.gif", "content/drawable-mdpi/ic_content_cut_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Copy24.gif", "content/drawable-mdpi/ic_content_copy_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Paste24.gif", "content/drawable-mdpi/ic_content_paste_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Bookmarks24.gif", "action/drawable-mdpi/ic_bookmark_border_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/general/Find24.gif", "action/drawable-mdpi/ic_search_black_48dp.png");
			iconFallbacks.setProperty("de/netsysit/ressources/gfx/common/HighlightSelection24.gif", "content/drawable-mdpi/ic_select_all_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/media/Pause24.gif", "av/drawable-mdpi/ic_pause_circle_outline_black_48dp.png");
//			iconFallbacks.setProperty("toolbarButtonGraphics/media/Pause24.gif", "av/drawable-mdpi/ic_pause_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/navigation/Down24.gif", "navigation/drawable-mdpi/ic_arrow_downward_black_48dp.png");
			iconFallbacks.setProperty("toolbarButtonGraphics/navigation/Up24.gif", "navigation/drawable-mdpi/ic_arrow_upward_black_48dp.png");
			de.netsysit.util.ResourceLoader.configure(iconFallbacks);
		}
		catch(java.io.IOException ioexp)
		{
			ioexp.printStackTrace();
		}
	}
	
	public Log4JJMXJConsolePlugin()
	{
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
