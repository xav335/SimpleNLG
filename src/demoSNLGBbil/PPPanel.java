package demoSNLGBbil;

import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PPPanel extends NPPanel {
	
	public JTextField preposition;

	public PPPanel(String title) {
		super(title);
		add(new JLabel("Préposition :"), 0);
		preposition = new JTextField();
		add(preposition, 1);
	}

}
