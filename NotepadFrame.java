import java.awt.*;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class NotepadFrame extends JFrame {

	private JPanel contentPane;
	private boolean filenameProvided,modified;
	private File file;
	private JTextArea textArea;
	private Clipboard clip;
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotepadFrame frame = new NotepadFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public NotepadFrame() {
		clip = getToolkit().getSystemClipboard();
		modified=true;
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				exitMethod();
			}
		});
			
		
		setBounds(100, 100, 659, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNew = new JMenuItem("new");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newMethod();
			}
		});
		mnNewMenu.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openMethod();
			}
		});
		mnNewMenu.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				saveMethod();
			}
		});
		mnNewMenu.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exitMethod();
			}
		});
		
		mnNewMenu.add(mntmExit);
		
		JMenu mnEdit = new JMenu("edit");
		mnEdit.setBackground(Color.WHITE);
		mnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(mnEdit);
		
		JMenuItem mntmCopy = new JMenuItem("copy");
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sel = textArea.getSelectedText();
	             StringSelection clipString = new StringSelection(sel);
	             clip.setContents(clipString,clipString);
			}
		});
		mnEdit.add(mntmCopy);
		
		JMenuItem mntmCut = new JMenuItem("cut");
		mntmCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sel = textArea.getSelectedText();
	             StringSelection ss = new StringSelection(sel);
	             clip.setContents(ss,ss);
	            textArea.replaceRange(" ",textArea.getSelectionStart(),textArea.getSelectionEnd());
	         
			}
		});
		mnEdit.add(mntmCut);
		
		JMenuItem mntmPaste = new JMenuItem("paste");
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

	             Transferable cliptran = clip.getContents(NotepadFrame.this);
	             try
	                 {
	                 String sel = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
	                 textArea.replaceRange(sel,textArea.getSelectionStart(),textArea.getSelectionEnd());
	             }
	             catch(Exception exc)
	                 {
	                JOptionPane.showMessageDialog(NotepadFrame.this,"Nothig to Paste");
	             }
			}
		});
		mnEdit.add(mntmPaste);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
		);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Verdana", Font.PLAIN, 15));
		scrollPane_1.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
		
		
		
	}
	

public boolean saveConfirm()
{
	int flag=JOptionPane.showConfirmDialog(this, "Contents not saved Do you want to save?","save",JOptionPane.YES_NO_OPTION);
	if(flag==JOptionPane.YES_OPTION)
	return true;
	else
	return false;
	
}


public boolean getFileName(int flag) {
	JFileChooser jfc=new JFileChooser();
	int retval;
	boolean x=false;
	if(flag==1)
		retval=jfc.showOpenDialog(this);
		else
		retval=jfc.showSaveDialog(this);
		
		if(retval==JFileChooser.APPROVE_OPTION)
		{
			file=jfc.getSelectedFile();
			filenameProvided=true;
			x=true;
		}
		return x;
	
}


public void saveMethod()
{
	if(modified)
	{
		if(!filenameProvided)
		{
			if(!getFileName(2))
			{
				return;
			}
	}
}
	try
	{
		FileOutputStream fout=new FileOutputStream(file);
		fout.write(textArea.getText().getBytes());
		fout.close();
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	modified=false;
}
public void newMethod() {
	if(modified)
	{
		if(saveConfirm())
		{
			saveMethod();
		}
	}
	textArea.setText("");
	filenameProvided=false;
	modified=true;
}
public void exitMethod()
{
	if(modified)
	{
		if(saveConfirm())
		{
			saveMethod();
		}
	}
	System.exit(0);
}
public void openMethod()
{
	if(modified)
	{
		if(saveConfirm())
		{
			saveMethod();
		}
	}
if(!getFileName(1))
{
	return;
}
FileInputStream fin = null;
try {
	fin = new FileInputStream(file);
} catch (FileNotFoundException e) {
	
	e.printStackTrace();
}
byte[] b=new byte[(int)file.length()];
try {
	fin.read(b);
} catch (IOException e) {
	
	e.printStackTrace();
}
try {
	fin.close();
} catch (IOException e) {
	
	e.printStackTrace();
}
String s=new String(b);
textArea.setText(s);
modified=false;
}
}