package de.elbosso.jconsole;


import javax.management.Notification;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class TextAreaView extends javax.swing.JPanel implements javax.management.NotificationListener
{
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
	private boolean appendNextLine;
	private boolean scrollLock;
	private final javax.swing.JLabel statusLabel;

	TextAreaView()
	{
		super(new java.awt.BorderLayout());
		statusLabel=new javax.swing.JLabel("not Connected!");
		add(statusLabel, BorderLayout.SOUTH);
		de.netsysit.ui.text.AugmentedJEditTextArea ta=new de.netsysit.ui.text.AugmentedJEditTextArea();
		ed=new de.netsysit.ui.text.TextEditor(ta);

		doc=new org.syntax.jedit.SyntaxDocument(styles);
		ed.setDocument(doc);
		doc.setTokenMarker(new org.syntax.jedit.tokenmarker.Log4jTokenMarker());
		ed.setEditable(false);
		add(ed.getTextField());
		ed.getToggleBookmarkAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Bookmarks24.gif")));
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
		try
		{
			ed.getSaveAsAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("toolbarButtonGraphics/general/Save24.gif"), de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_help_outline_black_36dp.png"))));
		}
		catch(java.lang.Throwable exp)
		{
		}
		try
		{
			ed.getGotoLineNumberAction().putValue(Action.SMALL_ICON,new javax.swing.ImageIcon(de.netsysit.ui.image.DecoratedImageProducer.produceImage(de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_view_headline_black_48dp.png"), de.netsysit.util.ResourceLoader.getImgResource("action/drawable-mdpi/ic_trending_flat_black_36dp.png"), SwingUtilities.SOUTH_WEST)));
		}
		catch(java.lang.Throwable exp)
		{
		}
		javax.swing.JToolBar tb=new de.netsysit.ui.components.AdaptiveToolBar();
		tb.setFloatable(false);
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
		add(tb, BorderLayout.NORTH);
	}

	void setStatus(java.lang.String newStatus)
	{
		statusLabel.setText(newStatus);
	}

	public synchronized boolean isAppendNextLine()
	{
		return appendNextLine;
	}

	public synchronized void setAppendNextLine(boolean appendNextLine)
	{
		if(this.appendNextLine!=appendNextLine)
		{
			javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable()
			{
				public void run()
				{
					try
					{
						ed.clear();
					} catch (BadLocationException e)
					{
						e.printStackTrace();
					}
				}
			});
		}
		this.appendNextLine = appendNextLine;
	}

	public synchronized boolean isScrollLock()
	{
		return scrollLock;
	}

	public synchronized void setScrollLock(boolean scrollLock)
	{
		this.scrollLock = scrollLock;
	}

	@Override
	public void handleNotification(Notification notification, Object handback)
	{
		javax.swing.SwingUtilities.invokeLater(new LogRunnable(notification.getMessage()));
	}
	private class LogRunnable extends java.lang.Object implements java.lang.Runnable
	{
		private final java.lang.String le;

		public LogRunnable(java.lang.String le)
		{
			super();
			this.le = le;
		}
		public void run()
		{
			try
			{
				if(isAppendNextLine())
				{
					doc.insertString(ed.getTextField().getDocumentLength(), le, null);
					if(isScrollLock()==false)
						ed.getTextField().setCaretPosition(ed.getTextField().getLineStartOffset(ed.getTextField().getCaretLine()));
				}
				else
				{
					doc.insertString(0, le, null);
					if(isScrollLock()==false)
						ed.getTextField().setCaretPosition(0);
				}
			} catch (BadLocationException ex)
			{
				ex.printStackTrace();
			}
		}
	}

}
