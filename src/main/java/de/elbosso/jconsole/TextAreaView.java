package de.elbosso.jconsole;


import javax.management.Notification;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TextAreaView extends Base
{
	private static int MAX_LINE_COUNT=2000;
	private static org.syntax.jedit.SyntaxStyle[] styles = new org.syntax.jedit.SyntaxStyle[org.syntax.jedit.tokenmarker.Token.ID_COUNT];
	static
	{
		styles[org.syntax.jedit.tokenmarker.Token.NULL] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(0,0,0),false,false);
		styles[org.syntax.jedit.tokenmarker.Token.COMMENT1] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(204,0,0),true,false);
		styles[org.syntax.jedit.tokenmarker.Token.COMMENT2] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(255,128,0),true,false);
		styles[org.syntax.jedit.tokenmarker.Token.COMMENT3] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(102,0,204),true,false);
		styles[org.syntax.jedit.tokenmarker.Token.KEYWORD1] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(0,102,153),false,true);
		styles[org.syntax.jedit.tokenmarker.Token.KEYWORD2] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(0,153,102),false,true);
		styles[org.syntax.jedit.tokenmarker.Token.KEYWORD3] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(0,153,255),false,true);
		styles[org.syntax.jedit.tokenmarker.Token.LITERAL1] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(255,0,204),false,true);
		styles[org.syntax.jedit.tokenmarker.Token.LITERAL2] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(153,0,204),false,true);
		styles[org.syntax.jedit.tokenmarker.Token.LABEL] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(153,0,51),true,true);
		styles[org.syntax.jedit.tokenmarker.Token.OPERATOR] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(0,0,0),false,true);
		styles[org.syntax.jedit.tokenmarker.Token.INVALID] = new org.syntax.jedit.SyntaxStyle(new java.awt.Color(255,0,0),false,false);
	};
	private org.syntax.jedit.SyntaxDocument doc;
	private de.netsysit.ui.text.TextEditor ed;
	private java.util.LinkedList<java.lang.String> latch=new java.util.LinkedList();

	TextAreaView()
	{
		super();
		createToolbar();
		de.netsysit.ui.text.AugmentedJEditTextArea ta=new de.netsysit.ui.text.AugmentedJEditTextArea();
		ed=new de.netsysit.ui.text.TextEditor(ta);

		doc=new org.syntax.jedit.SyntaxDocument(styles);
		ed.setDocument(doc);
		doc.setTokenMarker(new org.syntax.jedit.tokenmarker.Log4jTokenMarker());
		ed.setEditable(false);
		add(ed.getTextField());
		ed.getToggleBookmarkAction().putValue(Action.SMALL_ICON,(de.netsysit.util.ResourceLoader.getIcon("toolbarButtonGraphics/general/Bookmarks24.gif")));
		try
		{
			ed.getPasteAfterEraseAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Paste24.gif"), de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Delete16.gif"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
		try
		{
			ed.getGotoPreviousBookmarkAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Bookmarks24.gif"), de.netsysit.util.ResourceLoader.getImgResource("navigation/drawable-mdpi/ic_chevron_left_black_36dp.png"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
		try
		{
			ed.getGotoNextBookmarkAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Bookmarks24.gif"), de.netsysit.util.ResourceLoader.getImgResource("navigation/drawable-mdpi/ic_chevron_right_black_36dp.png"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
		try
		{
			ed.getFindPreviousAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_search_black_48dp.png"), de.netsysit.util.ResourceLoader.getImgResource("navigation/drawable-mdpi/ic_chevron_left_black_36dp.png"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
		try
		{
			ed.getFindNextAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_search_black_48dp.png"), de.netsysit.util.ResourceLoader.getImgResource("navigation/drawable-mdpi/ic_chevron_right_black_36dp.png"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
		ed.getSaveAsAction().putValue(Action.SMALL_ICON,de.netsysit.util.ResourceLoader.getIcon("toolbarButtonGraphics/general/SaveAs24.gif"));
/*		try
		{
			ed.getSaveAsAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Save24.gif"), de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_help_outline_black_36dp.png"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
*/		try
		{
			ed.getGotoLineNumberAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_view_headline_black_48dp.png"), de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_trending_flat_black_36dp.png"), SwingUtilities.SOUTH_WEST)));
		}
		catch(java.lang.Throwable exp)
		{
		}
		tb.add(ed.getSaveAction());
		tb.add(ed.getSaveAsAction());
		tb.addSeparator();
		tb.add(ed.getClearAction());
		tb.add(ed.getCopyAction());
		tb.addSeparator();
		tb.add(ed.getGotoLineNumberAction());
		tb.addSeparator();
		tb.add(ed.getFindPreviousAction());
		tb.add(ed.getFindAction());
		tb.add(ed.getFindNextAction());
		tb.addSeparator();
		tb.add(ed.getHighlightSelectionAction());
		tb.addSeparator();
		tb.add(ed.getGotoPreviousBookmarkAction());
		tb.add(ed.getToggleBookmarkAction());
		tb.add(ed.getGotoNextBookmarkAction());
		tb.addSeparator();
		javax.swing.JToggleButton toggle=new javax.swing.JToggleButton(scrollLockAction);
		toggle.setSelectedIcon((de.netsysit.util.ResourceLoader.getIcon("action/drawable-mdpi/ic_lock_black_48dp.png")));
		tb.add(toggle);
		toggle=new javax.swing.JToggleButton(appendNextLineAction);
		toggle.setSelectedIcon((de.netsysit.util.ResourceLoader.getIcon("toolbarButtonGraphics/navigation/Down24.gif")));
		tb.add(toggle);
		tb.add(configAction);
		add(tb, BorderLayout.NORTH);
	}

	@Override
	protected void appendNextLineActionImpl(ActionEvent e)
	{
		javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable()
		{
			public void run()
			{
				try
				{
					latch.clear();
					ed.clear();
				} catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
		});
//				setAppendNextLine(((java.lang.Boolean)getValue(Action.SELECTED_KEY)).booleanValue());
	}

	private class LogRunnable extends java.lang.Object implements java.lang.Runnable
	{
		private final java.util.List<java.lang.String> le;

		public LogRunnable(java.util.List<java.lang.String> le)
		{
			super();
			this.le =  le;
		}
		public void run()
		{
			try
			{
				if(ed.getTextField().getLineCount()<MAX_LINE_COUNT)
				{
					setStatus(null);
					java.lang.StringBuffer buf = new java.lang.StringBuffer();
					for (java.lang.String l : le)
					{
						buf.append(l);
					}
					java.lang.String bufs = buf.toString();
					if (isAppendNextLine())
					{
						doc.insertString(ed.getTextField().getDocumentLength(), bufs, null);
						if (isScrollLock() == false)
							ed.getTextField().setCaretPosition(ed.getTextField().getLineStartOffset(ed.getTextField().getCaretLine()));
					}
					else
					{
						doc.insertString(0, bufs, null);
						if (isScrollLock() == false)
							ed.getTextField().setCaretPosition(0);
					}
				}
				else
					setStatus("Max line count reached!");
			} catch (BadLocationException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	protected void acknowledgeDataImpl(Notification notification)
	{
		java.lang.String msg = notification.getMessage();
		if (isAppendNextLine())
			latch.addLast(msg);
		else
			latch.addFirst(msg);
	}

	@Override
	protected void updateGUIImpl(Notification notification)
	{
		java.util.List<java.lang.String> toRender = new java.util.LinkedList(latch);
		latch.clear();
		if (isVisible() == true)
			javax.swing.SwingUtilities.invokeLater(new LogRunnable(toRender));
	}
}
