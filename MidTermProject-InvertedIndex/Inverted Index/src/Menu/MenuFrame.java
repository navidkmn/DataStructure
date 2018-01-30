package Menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import DataStructures.Stack;

import javax.swing.*;

public class MenuFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7279595727811063481L;

	JTextField fileDirectory;
	JTextField order;
	
	JLabel welcome;
	JLabel selectTree;
	
	Buttons browse;
	Buttons build;
	Buttons run;
	Buttons reset;
	Buttons exit;
	
	JTextArea result;
	
	JScrollPane scroll;
	
	JRadioButton bst;
	JRadioButton tst;
	JRadioButton trie;
	JRadioButton hash;
	ButtonGroup chooseTree;
	
	Stack redo;
	Stack undo;
	

	public MenuFrame(int w, int h) {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setCursor(Cursor.getDefaultCursor());
		setLayout(null);

		setTitle("Inverted Index");
		setSize(w, h);
		setResizable(false);
		setLocationRelativeTo(null);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	
		welcome = new JLabel("Please Enter Your File Address");
		welcome.setFont(new Font("Monospaced", welcome.getFont().getStyle(), 20));
		welcome.setLocation(10,10);
		welcome.setSize(500, 20);
		
		fileDirectory = new JTextField();
		fileDirectory.setText(null);
		fileDirectory.setSize(500, 30);
		fileDirectory.setLocation(10,40);
		fileDirectory.setBorder(border);
		
		browse = new Buttons("Browse",this);
		browse.setSize(100, 30);
		browse.setLocation(520, 40);
		browse.setBorder(border);
		
		result = new JTextArea();
		result.setSize(500, 300);
		result.setLocation(10, 80);
		result.setLineWrap(true);
		result.setEditable(false);
		result.setBorder(border);
		
		scroll = new JScrollPane(result);
		scroll.setBounds(10,80,510,380);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		selectTree = new JLabel("Please Enter Your Command :");
		selectTree.setFont(new Font("Monospaced", selectTree.getFont().getStyle(), 20));
		selectTree.setSize(500, 20);
		selectTree.setLocation(10, 470);
		
		bst = new JRadioButton("BST");
		bst.setSize(60, 25);
		bst.setLocation(350, 470);
		
		tst = new JRadioButton("TST");
		tst.setSize(60, 25);
		tst.setLocation(420, 470);
		
		trie = new JRadioButton("TRIE");
		trie.setSize(60, 25);
		trie.setLocation(490, 470);
		
		hash = new JRadioButton("Hash");
		hash.setSize(60, 25);
		hash.setLocation(560, 470);
		
		chooseTree = new ButtonGroup();
		chooseTree.add(bst);
		chooseTree.add(tst);
		chooseTree.add(trie);
		chooseTree.add(hash);
		
		order = new JTextField();
		order.setSize(500, 30);
		order.setLocation(10, 495);
		order.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
					
				if(arg0.getKeyCode()== KeyEvent.VK_UP){
					
					String command = (String)redo.Pop();
					order.setText(command);
					undo.Push(command);
				}
				
				if(arg0.getKeyCode()== KeyEvent.VK_DOWN){
					String command = (String)undo.Pop();
					order.setText(command);
				}
				
			}
		});
		
		build = new Buttons("Build", this);
		build.setSize(100, 30);
		build.setLocation(10, 530);
		
		run  = new Buttons("Run", this);
		run.setSize(100,30);
		run.setLocation(110, 530);
		run.setEnabled(false);
		
		reset = new Buttons("Reset", this);
		reset.setSize(100, 30);
		reset.setLocation(265, 530);
		
		exit = new Buttons("Exit", this);
		exit.setSize(100, 30);
		exit.setLocation(520, 530);
		
		getContentPane().add(welcome);
		getContentPane().add(fileDirectory);
		getContentPane().add(browse);
		getContentPane().add(scroll);
		getContentPane().add(selectTree);
		getContentPane().add(bst);
		getContentPane().add(tst);
		getContentPane().add(trie);
		getContentPane().add(hash);
		getContentPane().add(build);
		getContentPane().add(exit);
		getContentPane().add(order);
		getContentPane().add(reset);
		getContentPane().add(run);
		setVisible(true);		
	}

	public static void main(String[] args) {
		new MenuFrame(650,600);
	}
}

