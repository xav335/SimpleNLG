package demoSNLGBbil;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class NPPanel extends JPanel {

	public JTextField det, nom, modifier1, modifier2;
	public JCheckBox pluriel, pronom;
	public JComboBox posMod1, posMod2;
	public JLabel teteLabel;
	
	public NPPanel(String title) {
		super(new GridLayout(8,2));
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(BorderFactory.createTitledBorder(blackline, title));
		
		add(new JLabel("Déterminant :"));
		det = new JTextField();
		add(det);
		teteLabel = new JLabel("Nom ou pronom :");
		add(teteLabel);
		nom = new JTextField();
		add(nom);
		add(new JLabel("Modificateur 1 :"));
		modifier1 = new JTextField();
		add(modifier1);
		add(new JLabel("Position :"));
		posMod1 = new JComboBox();
		posMod1.addItem("par défaut");
		posMod1.addItem("prémodificateur");
		posMod1.addItem("postmodificateur");
		add(posMod1);
		add(new JLabel("Modificateur 2 :"));
		modifier2 = new JTextField();
		add(modifier2);
		add(new JLabel("Position :"));
		posMod2 = new JComboBox();
		posMod2.addItem("par défaut");
		posMod2.addItem("prémodificateur");
		posMod2.addItem("postmodificateur");
		add(posMod2);
		pluriel = new JCheckBox("Pluriel");
		add(pluriel);
		pronom = new JCheckBox("Remplacer par un pronom");
		add(pronom);		
	}
}
