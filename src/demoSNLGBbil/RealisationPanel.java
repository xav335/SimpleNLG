package demoSNLGBbil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class RealisationPanel extends JPanel {

	public JButton realiserButton;
	public JRadioButton radioEnglish, radioFrench;
	public JTextArea realisationTextArea;
//	public JCheckBox unknownWordsCheckbox;
	public JRadioButton radioDeclarative, radioInterrogative, radioRelative;
	public JComboBox interrogativeComboBox, relativeComboBox;
	
	public static final String DECLARATIVE = "phrase déclarative";
	public static final String INTERROGATIVE = "phrase interrogative";
	public static final String RELATIVE = "proposition relative";
	
	public static final String HOW = "comment?";
	public static final String HOW_MANY = "combien?";
	public static final String WHAT_OBJECT = "quoi? (objet direct)";
	public static final String WHERE = "où?";
	public static final String WHO_INDIRECT_OBJECT = "qui? (objet indirect)";
	public static final String WHO_OBJECT = "qui? (objet direct)";
	public static final String WHO_SUBJECT = "qui? (sujet)";
	public static final String WHY = "pourquoi?";
	public static final String YES_NO = "oui ou non?";

	public static final String SUJECT_REL = "sujet relativisé";
	public static final String OBJECT_REL = "objet direct relativisé";
	public static final String INDIRECT_OBJECT_REL = "objet indirect relativisé";	
	
	
	public RealisationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel firstRowPanel = new JPanel();
		JPanel secondRowPanel = new JPanel();
		firstRowPanel.setLayout(new BoxLayout(firstRowPanel, BoxLayout.X_AXIS));
		secondRowPanel.setLayout(new BoxLayout(secondRowPanel, BoxLayout.X_AXIS));
		add(firstRowPanel);
		add(secondRowPanel);
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		
		JPanel radioPanelType = new JPanel(new GridLayout(3,1));
		radioDeclarative = new JRadioButton(DECLARATIVE);
		radioInterrogative = new JRadioButton(INTERROGATIVE);
		radioRelative = new JRadioButton(RELATIVE);
		radioPanelType.add(radioDeclarative);
		radioDeclarative.setSelected(true);
		ButtonGroup radioGroupType = new ButtonGroup();
		radioGroupType.add(radioDeclarative);
		radioGroupType.add(radioInterrogative);
		radioGroupType.add(radioRelative);
		radioPanelType.setBorder(BorderFactory.createTitledBorder(blackline, "Type de phrase"));
		firstRowPanel.add(radioPanelType);

		JPanel interrogativePanel = new JPanel(new GridLayout(1,2));
		interrogativeComboBox = new JComboBox();
		interrogativeComboBox.addItem(YES_NO);
		interrogativeComboBox.addItem(WHO_SUBJECT);
		interrogativeComboBox.addItem(WHO_OBJECT);
		interrogativeComboBox.addItem(WHO_INDIRECT_OBJECT);
		interrogativeComboBox.addItem(WHAT_OBJECT);
		interrogativeComboBox.addItem(WHERE);
		interrogativeComboBox.addItem(HOW);
		interrogativeComboBox.addItem(HOW_MANY);
		interrogativeComboBox.addItem(WHY);
		interrogativeComboBox.setEnabled(false);
		interrogativePanel.add(radioInterrogative);
		interrogativePanel.add(interrogativeComboBox);
		radioPanelType.add(interrogativePanel);
		
		JPanel relativePanel = new JPanel(new GridLayout(1,2));
		relativeComboBox = new JComboBox();
		relativeComboBox.addItem(SUJECT_REL);
		relativeComboBox.addItem(OBJECT_REL);
		relativeComboBox.addItem(INDIRECT_OBJECT_REL);
		relativeComboBox.setEnabled(false);
		relativePanel.add(radioRelative);
		relativePanel.add(relativeComboBox);
		radioPanelType.add(relativePanel);
		
		ActionListener radioTypeListener =  new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean interrogativeEnable = false, relativeEnable = false;
				
				if (radioInterrogative.isSelected()) interrogativeEnable = true;
				else if (radioRelative.isSelected()) relativeEnable = true;
				
				interrogativeComboBox.setEnabled(interrogativeEnable);
				relativeComboBox.setEnabled(relativeEnable);
			}
		};
		radioDeclarative.addActionListener(radioTypeListener);
		radioInterrogative.addActionListener(radioTypeListener);
		radioRelative.addActionListener(radioTypeListener);
		
		JPanel radioPanelLang = new JPanel(new GridLayout(2,1));
		radioEnglish = new JRadioButton("anglais");
		radioFrench = new JRadioButton("français");
		radioPanelLang.add(radioEnglish);
		radioPanelLang.add(radioFrench);
		radioFrench.setSelected(true);
		ButtonGroup radioGroupLang = new ButtonGroup();
		radioGroupLang.add(radioEnglish);
		radioGroupLang.add(radioFrench);
		radioPanelLang.setBorder(BorderFactory.createTitledBorder(blackline, "Langue"));
		firstRowPanel.add(Box.createHorizontalGlue());
		firstRowPanel.add(radioPanelLang);
		
		radioEnglish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				radioRelative.setEnabled(false);
				radioDeclarative.setSelected(true);
			}			
		});
		
		radioFrench.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioRelative.setEnabled(true);
			}	
		});
		
		realiserButton = new JButton("  Réaliser  ");
		realiserButton.setFont(realiserButton.getFont().deriveFont(Font.BOLD, 16));
		realiserButton.setMaximumSize(new Dimension(100,50));
		firstRowPanel.add(Box.createHorizontalGlue());
		firstRowPanel.add(realiserButton);
		firstRowPanel.add(Box.createHorizontalStrut(2));
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		realisationTextArea = new JTextArea(2,50);
		realisationTextArea.setEditable(false);
		realisationTextArea.setLineWrap(true);
		realisationTextArea.setWrapStyleWord(true);
		JScrollPane scroollPane = new JScrollPane(realisationTextArea);
		textPanel.add(scroollPane);
//		unknownWordsCheckbox = new JCheckBox("Ajouter un astérisque (*) devant les mots absents du lexique", false);
//		textPanel.add(unknownWordsCheckbox);
		textPanel.add(new JLabel("Un astérisque (*) est affiché devant les mots absents du lexique."));
		secondRowPanel.add(Box.createHorizontalStrut(2));
		secondRowPanel.add(textPanel);
		secondRowPanel.add(Box.createHorizontalStrut(2));
	}
}
