package de.elbosso.jconsole;

import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PreferencesPart extends javax.swing.JPanel implements javax.swing.event.ListSelectionListener
{
	private javax.swing.Action selectAllAction;
	private javax.swing.Action selectNoneAction;
	private javax.swing.Action toggleSelectionAction;
	private javax.swing.JTable t;

	PreferencesPart(StringBooleanModel model)
	{
		super(new java.awt.BorderLayout());
		createActions();
		t=new javax.swing.JTable(model);
		add(new javax.swing.JScrollPane(t));
		javax.swing.JToolBar tb=new javax.swing.JToolBar();
		tb.setFloatable(false);
		tb.add(model.getSelectAllAction());
		tb.add(model.getSelectNoneAction());
		tb.add(model.getToggleSelectionAction());
		tb.addSeparator();
		tb.add(selectAllAction);
		tb.add(selectNoneAction);
		tb.add(toggleSelectionAction);
		add(tb,BorderLayout.NORTH);
		t.getSelectionModel().addListSelectionListener(this);
	}
	private void createActions()
	{
		try
		{
			selectAllAction = new javax.swing.AbstractAction("select all", new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("de/elbosso/ressources/gfx/eb/select all_48.png"), de.netsysit.util.ResourceLoader.getImgResource("content/drawable-mdpi/ic_select_all_black_36dp.png"))))
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					for (int i = 0; i < t.getRowCount(); ++i)
					{
						if (t.getSelectionModel().isSelectedIndex(i))
							t.setValueAt(Boolean.TRUE, i, 1);
					}
				}
			};
			selectAllAction.setEnabled(false);
			selectNoneAction = new javax.swing.AbstractAction("select none", new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("de/elbosso/ressources/gfx/eb/deselect all_48.png"), de.netsysit.util.ResourceLoader.getImgResource("content/drawable-mdpi/ic_select_all_black_36dp.png"))))
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					for (int i = 0; i < t.getRowCount(); ++i)
					{
						if (t.getSelectionModel().isSelectedIndex(i))
							t.setValueAt(Boolean.FALSE, i, 1);
					}
				}
			};
			selectNoneAction.setEnabled(false);
			toggleSelectionAction = new javax.swing.AbstractAction("toggle selection", new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("de/elbosso/ressources/gfx/eb/toggle_selection_48.png"), de.netsysit.util.ResourceLoader.getImgResource("content/drawable-mdpi/ic_select_all_black_36dp.png"))))
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					for (int i = 0; i < t.getRowCount(); ++i)
					{
						if (t.getSelectionModel().isSelectedIndex(i))
							t.setValueAt(((java.lang.Boolean) t.getValueAt(i, 1)).booleanValue() ? Boolean.FALSE : Boolean.TRUE, i, 1);
					}
				}
			};
			toggleSelectionAction.setEnabled(false);
		}
		catch(java.io.IOException ioexp)
		{
			ioexp.printStackTrace();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting()==false)
		{
			selectAllAction.setEnabled(t.getSelectionModel().isSelectionEmpty()==false);
			selectNoneAction.setEnabled(t.getSelectionModel().isSelectionEmpty()==false);
			toggleSelectionAction.setEnabled(t.getSelectionModel().isSelectionEmpty()==false);
		}
	}
}
