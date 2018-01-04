package de.elbosso.jconsole;

public class TextAreaView extends javax.swing.JPanel
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

	TextAreaView()
	{
		super(new java.awt.BorderLayout());
		de.netsysit.ui.text.AugmentedJEditTextArea ta=new de.netsysit.ui.text.AugmentedJEditTextArea();
		ed=new de.netsysit.ui.text.TextEditor(ta);

		doc=new org.syntax.jedit.SyntaxDocument(styles);
		ed.setDocument(doc);
		doc.setTokenMarker(new org.syntax.jedit.tokenmarker.Log4jTokenMarker());
		ed.setEditable(false);
		add(ed.getTextField());
	}
}
