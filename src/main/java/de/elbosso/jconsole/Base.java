package de.elbosso.jconsole;

import javax.management.Notification;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;

abstract class Base extends javax.swing.JPanel implements javax.management.NotificationListener
{
	private final javax.swing.JLabel statusLabel;
	protected AbstractAction scrollLockAction;
	protected AbstractAction appendNextLineAction;
	protected AbstractAction configAction;
	protected javax.swing.JToolBar tb;
	private boolean paused;
	private long lastUpdate=System.currentTimeMillis();
	private de.netsysit.ui.dialog.GeneralPurposeInfoDialog gpid;
	private javax.swing.JPanel preferencesPanel;
	protected final LevelModel levelModel=new LevelModel();
	protected final LoggerModel loggerModel=new LoggerModel();

	protected Base()
	{
		super(new java.awt.BorderLayout());
		createActions();
		createToolbar();
		statusLabel=new javax.swing.JLabel("not Connected!");
		add(statusLabel, BorderLayout.SOUTH);

	}
	private void createToolbar()
	{
		tb=new de.netsysit.ui.components.AdaptiveToolBar();
		tb.setFloatable(false);
		add(tb, BorderLayout.NORTH);
	}
	void addToToolbar(javax.swing.JComponent comp)
	{
		tb.add(comp);
	}
	void addToToolbar(javax.swing.Action action)
	{
		tb.add(action);
	}
	void addSeparatorToToolbar()
	{
		tb.addSeparator();
	}
	private void createActions()
	{
		scrollLockAction=new javax.swing.AbstractAction(null,(de.netsysit.util.ResourceLoader.getIcon("action/drawable-mdpi/ic_lock_open_black_48dp.png")))
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//setScrollLock(((java.lang.Boolean)getValue(Action.SELECTED_KEY)).booleanValue());
			}
		};
		scrollLockAction.putValue(Action.SELECTED_KEY, Boolean.FALSE);
		appendNextLineAction=new javax.swing.AbstractAction(null,(de.netsysit.util.ResourceLoader.getIcon("toolbarButtonGraphics/navigation/Up24.gif")))
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				appendNextLineActionImpl(e);
			}
		};
		appendNextLineAction.putValue(Action.SELECTED_KEY, Boolean.FALSE);
		configAction=new javax.swing.AbstractAction(null,(de.netsysit.util.ResourceLoader.getIcon("toolbarButtonGraphics/general/Preferences24.gif")))
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(gpid==null)
				{
					gpid=de.netsysit.ui.dialog.GeneralPurposeInfoDialog.create(Base.this,"Preferences");
					preferencesPanel=new javax.swing.JPanel(new java.awt.BorderLayout());
					javax.swing.JPanel levelContainer=new javax.swing.JPanel(new java.awt.BorderLayout());
					javax.swing.JTable t=new javax.swing.JTable(levelModel);
					levelContainer.add(new javax.swing.JScrollPane(t));
					javax.swing.JToolBar tb=new javax.swing.JToolBar();
					tb.setFloatable(false);
					tb.add(levelModel.getSelectAllAction());
					tb.add(levelModel.getSelectNoneAction());
					tb.add(levelModel.getToggleSelectionAction());
					levelContainer.add(tb,BorderLayout.NORTH);
					preferencesPanel.add(levelContainer,BorderLayout.NORTH);
					javax.swing.JPanel loggerContainer=new javax.swing.JPanel(new java.awt.BorderLayout());
					t=new javax.swing.JTable(loggerModel);
					loggerContainer.add(new javax.swing.JScrollPane(t));
					tb=new javax.swing.JToolBar();
					tb.setFloatable(false);
					tb.add(loggerModel.getSelectAllAction());
					tb.add(loggerModel.getSelectNoneAction());
					tb.add(loggerModel.getToggleSelectionAction());
					loggerContainer.add(tb,BorderLayout.NORTH);
					preferencesPanel.add(loggerContainer);
				}
				gpid.showDialog(preferencesPanel);
			}
		};
	}
	protected abstract void appendNextLineActionImpl(ActionEvent e);
	void setStatus(java.lang.String newStatus)
	{
		statusLabel.setText(newStatus);
	}

	public synchronized boolean isAppendNextLine()
	{
		return ((java.lang.Boolean)appendNextLineAction.getValue(Action.SELECTED_KEY)).booleanValue();
	}

	public synchronized boolean isScrollLock()
	{
		return ((java.lang.Boolean)scrollLockAction.getValue(Action.SELECTED_KEY)).booleanValue();
	}

	public synchronized boolean isPaused()
	{
		return paused;
	}

	public void setPaused(boolean paused)
	{
		this.paused = paused;
	}
	@Override
	public void handleNotification(Notification notification, Object handback)
	{
		try
		{
			javax.management.openmbean.CompositeData data = (javax.management.openmbean.CompositeData) notification.getUserData();
			java.lang.String loggerName=data.get("loggerName").toString();
			loggerModel.add(loggerName);
			if (isPaused() == false)
			{
				acknowledgeDataImpl(notification);
				long now = System.currentTimeMillis();
				if (now - lastUpdate > 1000)
				{
					lastUpdate = now;
					updateGUIImpl(notification);
				}
			}
		}
		catch(java.lang.Throwable t)
		{
			de.elbosso.util.Utilities.handleException(null,t);
		}
	}
	protected abstract void acknowledgeDataImpl(Notification notification);
	protected abstract void updateGUIImpl(Notification notification);
}
