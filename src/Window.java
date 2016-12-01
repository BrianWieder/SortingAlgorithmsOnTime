import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import layout.SpringUtilities;

public class Window {
	JFrame f;
	
	public Window(){
		f = new JFrame("Effect of Sorting Algorithm on Time");
		f.setSize(500, 150);
		
		JPanel p = new JPanel(new SpringLayout());
		
		//Setting up trials in the GUI
		JLabel trials = new JLabel("How many trials?", JLabel.TRAILING);
		p.add(trials);
		JTextField trialsTextField = new JTextField(10);
		trials.setLabelFor(trialsTextField);
		p.add(trialsTextField);
		
		//Setting up excel spreadsheet name
		JLabel excelName = new JLabel("What should the name of the excel file be?", JLabel.TRAILING);
		p.add(excelName);
		JTextField excelNameTextField = new JTextField(10);
		excelName.setLabelFor(excelNameTextField);
		p.add(excelNameTextField);
		
		JLabel amountOfData = new JLabel("How many numbers should the data be?", JLabel.TRAILING);
		p.add(amountOfData);
		JTextField amountOfDataTextField = new JTextField(10);
		amountOfData.setLabelFor(amountOfDataTextField);
		p.add(amountOfDataTextField);
		
		
		JButton submitBtn = new JButton("Start Testing");
		submitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Main.startTesting(Integer.parseInt(trialsTextField.getText()), excelNameTextField.getText(), Integer.parseInt(amountOfDataTextField.getText()));
			}
		});
	
		p.add(submitBtn);
		JLabel empty = new JLabel();
		p.add(empty);
		SpringUtilities.makeCompactGrid(p, 4, 2, 6, 6, 6, 6);
		f.setVisible(true);
		f.add(p);
	}
	
	public void closeWinder(){
		f.dispose();
	}
	
}
