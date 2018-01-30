package Menu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Buttons  extends JButton implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7716424686020088858L;

	MenuFrame mf;
	Commands command;
	
	public Buttons(String name,MenuFrame mf) {
		super(name);
		this.mf = mf;
		setFont(new Font("Monospaced",getFont().getStyle(), 15));
		addActionListener(this);
		command = new Commands(mf);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if("Make Table".equals(getText())){
			mf.setVisible(true);
			mf.confirmTable.setEnabled(true);
		}
		
		if("Confirm Table".equals(getText())){
            mf.start = Integer.parseInt(mf.startState.getText());
            String [] finalstates = mf.finalStates.getText().toString().split("[^0-9/a-zA-Z]");
            for (int i=0;i<finalstates.length;i++)
                mf.FinalNodes.add(Integer.parseInt(finalstates[i]));

            command.fillArray(mf.table);
           if(command.haveCircle(mf.start)){
            	mf.haveCycle.setText("Yes");
            	mf.removeCycle.setEnabled(true);
            }
            else
            	mf.haveCycle.setText("No");
            
            mf.isValid.setEnabled(true);
            mf.showList.setEnabled(true);
            mf.createImg.setEnabled(true);
			}
		
		if("Remove Cycle".equals(getText()))
			command.removeCycle();
	
		if("Create Image".equals(getText()))
			command.createImg();
		
		if("Is Valid".equals(getText())){
			if(command.isFinal(mf.start, mf.inputString.getText(), 0))
				mf.checkValid.setText("Yes");
			else
				mf.checkValid.setText("No");
		}
		
		if("Show List".equals(getText()))
			mf.List.setText(command.getList());
			
		if("Exit".equals(getText()))
			System.exit(0);
	}
}
