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
public class VerbPanel extends JPanel {

	public JTextField verb, modifier1, modifier2;
	public JCheckBox negation, progressive, perfect, passive;
	public JComboBox posMod1, posMod2, tense, mood;
	
	// Moods
	public static final String INDICATIVE = "indicatif";
	public static final String SUBJUNCTIVE = "subjonctif";
	public static final String IMPERATIVE = "impératif";
	public static final String INFINITIVE = "infinitif";
	
	// Tenses
	public static final String PAST = "passé";
	public static final String PRESENT = "présent";
	public static final String FUTURE = "futur";
	public static final String CONDITIONAL = "conditionnel";	
	
	public VerbPanel(String title) {
		super(new GridLayout(9,2));
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(BorderFactory.createTitledBorder(blackline, title));
		
		add(new JLabel("Verbe :"));
		verb = new JTextField();
		add(verb);
		
		add(new JLabel("Modificateur 1 :"));
		modifier1 = new JTextField();
		add(modifier1);
		
		add(new JLabel("Position :"));
		posMod1 = new JComboBox();
		posMod1.addItem("par défaut");
		posMod1.addItem("prémodificateur");
		posMod1.addItem("postmodificateur");
		posMod1.addItem("\"frontmodifier\"");
		add(posMod1);
		
		add(new JLabel("Modificateur 2 :"));
		modifier2 = new JTextField();
		add(modifier2);
		
		add(new JLabel("Position :"));
		posMod2 = new JComboBox();
		posMod2.addItem("par défaut");
		posMod2.addItem("prémodificateur");
		posMod2.addItem("postmodificateur");
		posMod2.addItem("\"frontmodifier\"");
		add(posMod2);
		
		add(new JLabel("Mode :"));
		mood = new JComboBox();
		mood.addItem(INDICATIVE);
		mood.addItem(SUBJUNCTIVE);
		mood.addItem(IMPERATIVE);
		mood.addItem(INFINITIVE);
		add(mood);
		
		add(new JLabel("Temps :"));
		tense = new JComboBox();
		tense.addItem(PAST);
		tense.addItem(PRESENT);
		tense.addItem(FUTURE);
		tense.addItem(CONDITIONAL);
		tense.setSelectedItem(PRESENT);
		add(tense);
		
		negation = new JCheckBox("Négation");
		add(negation);
		progressive = new JCheckBox("Progressif");
		add(progressive);
		perfect = new JCheckBox("Parfait");
		add(perfect);
		passive = new JCheckBox("Passif");
		add(passive);
	}
}
