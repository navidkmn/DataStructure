package Menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.border.Border;

import DataStructures.LinkList;

import javax.swing.*;

public class MenuFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7279595727811063481L;

	JTextField nodes;
	JTextField startState;
    JTextField finalStates;
	JTextField inputString;
	
	JTable table;

	JTextArea haveCycle;
	JTextArea checkValid;
	JTextArea List;
	
	JLabel stringChecker;
	JLabel starts,finals;
	JLabel numberOfNodes;
	JLabel cycle;
	
	Buttons makeTable;
	Buttons confirmTable;
	Buttons showList;
	Buttons createImg;
	Buttons isValid;
	Buttons removeCycle;
	Buttons run;
	Buttons exit;

	LinkList[] adjacent;
	Vector<Integer> FinalNodes;
    Vector<int[]> cycles;
    boolean[] isVisited;
	int  start;

	public MenuFrame() {

        FinalNodes = new Vector<Integer>();
        cycles = new Vector<int []>();
        
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setCursor(Cursor.getDefaultCursor());
		setLayout(null);

		setTitle("Graph");
		setSize(getMaximumSize().height, getMaximumSize().width);
		setResizable(false);
		setLocationRelativeTo(null);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	
		numberOfNodes = new JLabel("Number of nodes");
		numberOfNodes.setFont(new Font("Monospaced", numberOfNodes.getFont().getStyle(), 20));
		numberOfNodes.setLocation(10,10);
		numberOfNodes.setSize(500, 20);
		
		nodes = new JTextField();
		nodes.setText(null);
		nodes.setSize(30, 30);
		nodes.setLocation(10,40);
		nodes.setBorder(border);
		nodes.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if((arg0.getKeyCode() < 48 || arg0.getKeyCode() > 57) && arg0.getKeyCode() != KeyEvent.VK_BACK_SPACE){
					nodes.setText(null);
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if((arg0.getKeyCode() < 48 || arg0.getKeyCode() > 57) && arg0.getKeyCode() != KeyEvent.VK_BACK_SPACE){
					nodes.setText(null);
				}
			}
		});
		
		makeTable  = new Buttons("Make Table", this);
		makeTable.setSize(150,30);
		makeTable.setLocation(10, 100);		
		makeTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int tableSize = Integer.parseInt(nodes.getText());
				table = new JTable(tableSize, tableSize);
				table.setSize(400,400);
				table.setLocation(10, 200);
		
				getContentPane().add(table);
				adjacent = new LinkList[tableSize];
			}
		});
		
		starts = new JLabel("Start Nodes");
		starts.setSize(100,30);
		starts.setLocation(10, 620);
		        
        startState = new JTextField();
        startState.setSize(20,20);
        startState.setLocation(10,655);
        
        finals =new JLabel("Final Nodes");
        finals.setSize(100, 30);
        finals.setLocation(130, 620);
		
        finalStates = new JTextField();
        finalStates.setSize(50,20);
        finalStates.setLocation(130,655);
        
        confirmTable = new Buttons("Confirm Table", this);
        confirmTable.setSize(170, 30);
        confirmTable.setLocation(230, 650);
        confirmTable.setEnabled(false);
        
        cycle = new JLabel("Have Cycle");
        cycle.setSize(100, 30);
        cycle.setLocation(450,10);
        
        haveCycle = new JTextArea();
        haveCycle.setEditable(false);
        haveCycle.setSize(100, 30);
        haveCycle.setLocation(450, 40);
        
        removeCycle = new Buttons("Remove Cycle", this);
        removeCycle.setSize(150, 30);
        removeCycle.setLocation(450, 90);
        removeCycle.setEnabled(false);
        
        stringChecker = new JLabel("Write Your String");
        stringChecker.setSize(300, 30);
        stringChecker.setLocation(450, 140);
        
        inputString = new JTextField();
        inputString.setSize(300, 30);
        inputString.setLocation(450, 180);
        
        
        isValid = new Buttons("Is Valid", this);
        isValid.setSize(150, 30);
        isValid.setLocation(450, 220);
        isValid.setEnabled(false);
        
        checkValid = new JTextArea();
        checkValid.setSize(300, 30);
        checkValid.setLocation(450, 270);
        
        showList = new Buttons("Show List", this);
        showList.setSize(150, 30);
        showList.setLocation(450, 310);
        showList.setEnabled(false);
        
        List = new JTextArea();
        List.setSize(300, 300);
        List.setLocation(450, 360);
        List.setEditable(false);
        
        createImg = new Buttons("Create Image", this);
        createImg.setSize(150, 30);
        createImg.setLocation(450, 680);
        createImg.setEnabled(false);
        
        exit = new Buttons("Exit", this);
        exit.setSize(100, 30);
        exit.setLocation(1250, 700);

        getContentPane().add(numberOfNodes);
		getContentPane().add(nodes);
		getContentPane().add(exit);
		getContentPane().add(makeTable);
		getContentPane().add(confirmTable);
        getContentPane().add(finalStates);
        getContentPane().add(startState);
        getContentPane().add(haveCycle);
        getContentPane().add(starts);
        getContentPane().add(removeCycle);
        getContentPane().add(inputString);
        getContentPane().add(List);
        getContentPane().add(checkValid);
        getContentPane().add(cycle);
        getContentPane().add(isValid);
        getContentPane().add(showList);
        getContentPane().add (createImg);
        getContentPane().add(stringChecker);
        getContentPane().add(finals);
		setVisible(true);		
	}

	public static void main(String[] args) {
		new MenuFrame();
	}
}

