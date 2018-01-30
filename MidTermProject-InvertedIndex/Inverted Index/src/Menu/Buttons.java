package Menu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Buttons  extends JButton implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7716424686020088858L;

	MenuFrame mf;
	static Commands command;
	
	public Buttons(String name,MenuFrame mf) {
		super(name);
		this.mf = mf;
		setFont(new Font("Monospaced",getFont().getStyle(), 15));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if("Browse".equals(getText())){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.showOpenDialog(this);
			if(fileChooser.getSelectedFile() != null)
				mf.fileDirectory.setText(fileChooser.getSelectedFile().getPath());
		}
		
		if("Build".equals(getText())){
			
				boolean choose = false;
			
				if(mf.fileDirectory.getText().isEmpty())
					mf.result.setText("Please choose your directory");
				else{
				    for (Enumeration<AbstractButton> buttons = mf.chooseTree.getElements(); buttons.hasMoreElements();) {
				    	AbstractButton button = buttons.nextElement();
				        
					if (button.isSelected()	) {
						mf.browse.setEnabled(false);
						mf.build.setEnabled(false);
						mf.run.setEnabled(true);
						mf.result.setText(null);
						mf.bst.setEnabled(false);
						mf.tst.setEnabled(false);
						mf.trie.setEnabled(false);
						mf.hash.setEnabled(false);
						
						choose = true;
						
						File file = new File(mf.fileDirectory.getText());
						if(file.isDirectory() && file.listFiles().length>0){
							command = new Commands(mf,file,button.getText());
						}
						else
							mf.result.setText("Your directory doesn't have file");
					}
					else
						if(!choose)
						mf.result.setText("Please choose a Tree");
					}
				}
		}
		
		if("Reset".equals(getText())){
			mf.fileDirectory.setText(null);
			mf.order.setText(null);
			mf.chooseTree.clearSelection();
			mf.result.setText(null);
			mf.browse.setEnabled(true);
			mf.build.setEnabled(true);
			mf.bst.setEnabled(true);
			mf.tst.setEnabled(true);
			mf.trie.setEnabled(true);
			mf.hash.setEnabled(true);
			mf.run.setEnabled(false);
		}
		
		if("Run".equals(getText())){
			if(mf.order.getText().isEmpty())
				mf.result.setText("Please Enter your Command");
			else{
				command.runCommand(mf.order.getText());
			}
		}
		
		if("Exit".equals(getText()))
			System.exit(0);
	}
}
