/**
 * 
 */
package demoSNLGBbil;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import simplenlg.features.DiscourseFunction;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.InternalFeature;
import simplenlg.features.InterrogativeType;
import simplenlg.features.Tense;
import simplenlg.features.french.FrenchFeature;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

/**
 * @author pierre-luc
 *
 */
@SuppressWarnings("serial")
public class DemoMain extends JFrame {
	
	private static final List<LexicalCategory> determinerCategories =
		Arrays.asList(LexicalCategory.DETERMINER, LexicalCategory.ADVERB);

	private NPPanel panelSujet, panelObjetDirect;
	private PPPanel panelObjetIndirect;
	private VerbPanel panelVerbe;
	private RealisationPanel panelRealisation;
	
	private Lexicon frenchLexicon, englishLexicon;
	private NLGFactory frenchFactory, englishFactory;
	private Realiser realiser;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(
				UIManager.getSystemLookAndFeelClassName());

        // lancer l'interface graphique à partir
        // d'Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                new DemoMain();
            }
        });
	}

	/**
	 * Construct the main structure of the interface
	 * using other classes the panels.
	 * 
	 * @throws HeadlessException
	 */
	public DemoMain() throws HeadlessException {
		// Titre de la fenêtre principale
		super("Démo de SimpleNLG-EnFr 1.1 par Pierre-Luc Vaudry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelSujet = new NPPanel("Sujet");
		panelObjetDirect = new NPPanel("Objet direct ou attribut");
		panelObjetDirect.teteLabel.setText("Nom, pronom ou adjectif :");
		panelObjetIndirect = new PPPanel("Objet indirect");
		panelVerbe = new VerbPanel("Verbe");
		panelRealisation = new RealisationPanel();
		
		JPanel panneauCentral = new JPanel();
		panneauCentral.setLayout(new GridLayout(2,2,20,20));
		panneauCentral.add(panelSujet);
		panneauCentral.add(panelVerbe);
		panneauCentral.add(panelObjetDirect);
		panneauCentral.add(panelObjetIndirect);
		
		add(panneauCentral, BorderLayout.CENTER);
		add(panelRealisation, BorderLayout.SOUTH);
		
		panelRealisation.realiserButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed( ActionEvent e ) {
        		realise();
        	}
        });
		
		initNLG();
		
        // faire calculer les dimensions du JFrame
        pack();
        // afficher
        setVisible(true);
	}

	/**
	 * Initialize the SimpleNLG-EnFr components.
	 */
	private void initNLG() {
		frenchLexicon = new simplenlg.lexicon.french.XMLLexicon();
		englishLexicon = new simplenlg.lexicon.english.XMLLexicon();
		
		frenchFactory = new NLGFactory(frenchLexicon);
		englishFactory = new NLGFactory(englishLexicon);
		
		realiser = new Realiser();
	}

	/**
	 * Get the information the user as entered in the interface
	 * and realize the sentence.
	 */
	protected void realise() {
		Lexicon lexicon;
		NLGFactory factory;
		
		if (panelRealisation.radioEnglish.isSelected()) {
			lexicon = englishLexicon;
			factory = englishFactory;
		} else {
			lexicon = frenchLexicon;
			factory = frenchFactory;
		}
		
		PhraseElement sujet, objetIndirect;
		sujet = createNP(panelSujet, factory);
		objetIndirect = createNP(panelObjetIndirect, factory);
		List<LexicalCategory> prepositionCategories = Arrays.asList(LexicalCategory.PREPOSITION);
		String preposition = getText(panelObjetIndirect.preposition, prepositionCategories, lexicon);
		if (preposition != null && objetIndirect != null) {
			objetIndirect = factory.createPrepositionPhrase(preposition, objetIndirect);
		}
		
		List<LexicalCategory> verbCategories = Arrays.asList(LexicalCategory.VERB);
		String verbe = getText(panelVerbe.verb, verbCategories, lexicon);
		SPhraseSpec proposition = factory.createClause(sujet, verbe);
		setObjetDirect(panelObjetDirect, factory, proposition); 
		if (objetIndirect != null) proposition.setIndirectObject(objetIndirect);
		List<LexicalCategory> modifierCategories = Arrays.asList(LexicalCategory.ADVERB);
		addModifier(proposition, panelVerbe.modifier1, panelVerbe.posMod1, modifierCategories, lexicon);
		addModifier(proposition, panelVerbe.modifier2, panelVerbe.posMod2, modifierCategories, lexicon);
		proposition.setFeature(Feature.NEGATED, panelVerbe.negation.isSelected());
		proposition.setFeature(Feature.PROGRESSIVE, panelVerbe.progressive.isSelected());
		proposition.setFeature(Feature.PERFECT, panelVerbe.perfect.isSelected());
		proposition.setFeature(Feature.PASSIVE, panelVerbe.passive.isSelected());
		
		Tense tense;	
		Object tenseString = panelVerbe.tense.getSelectedItem();
		if (tenseString == VerbPanel.PAST) tense = Tense.PAST;
		else if (tenseString == VerbPanel.FUTURE) tense = Tense.FUTURE;
		else if (tenseString == VerbPanel.CONDITIONAL) tense = Tense.CONDITIONAL;
		else tense = Tense.PRESENT;
		proposition.setFeature(Feature.TENSE, tense);
		
		Form mood;
		Object moodString = panelVerbe.mood.getSelectedItem();
		if (moodString == VerbPanel.SUBJUNCTIVE) mood = Form.SUBJUNCTIVE;
		else if (moodString == VerbPanel.IMPERATIVE) mood = Form.IMPERATIVE;
		else if (moodString == VerbPanel.INFINITIVE) mood = Form.INFINITIVE;
		else mood = Form.NORMAL;
		proposition.setFeature(Feature.FORM, mood);
		
		String realisationString;
		
		if (panelRealisation.radioInterrogative.isSelected()) {
			InterrogativeType interrogativeType = null;
			Object selectedString = panelRealisation.interrogativeComboBox.getSelectedItem();
			
			if (selectedString == RealisationPanel.HOW) interrogativeType = InterrogativeType.HOW;
			else if (selectedString == RealisationPanel.HOW_MANY) interrogativeType = InterrogativeType.HOW_MANY;
			else if (selectedString == RealisationPanel.WHAT_OBJECT) interrogativeType = InterrogativeType.WHAT_OBJECT;
			else if (selectedString == RealisationPanel.WHERE) interrogativeType = InterrogativeType.WHERE;
			else if (selectedString == RealisationPanel.WHO_INDIRECT_OBJECT) interrogativeType = InterrogativeType.WHO_INDIRECT_OBJECT;
			else if (selectedString == RealisationPanel.WHO_OBJECT) interrogativeType = InterrogativeType.WHO_OBJECT;
			else if (selectedString == RealisationPanel.WHO_SUBJECT) interrogativeType = InterrogativeType.WHO_SUBJECT;
			else if (selectedString == RealisationPanel.WHY) interrogativeType = InterrogativeType.WHY;
			else if (selectedString == RealisationPanel.YES_NO) interrogativeType = InterrogativeType.YES_NO;
			
			proposition.setFeature(Feature.INTERROGATIVE_TYPE, interrogativeType);
			realisationString = realiser.realiseSentence(proposition);
			
		} else if (panelRealisation.radioRelative.isSelected()) {
			DiscourseFunction function = null;
			Object selectedString = panelRealisation.relativeComboBox.getSelectedItem();
			
			if (selectedString == RealisationPanel.SUJECT_REL) function = DiscourseFunction.SUBJECT;
			else if (selectedString == RealisationPanel.OBJECT_REL) function = DiscourseFunction.OBJECT;
			else if (selectedString == RealisationPanel.INDIRECT_OBJECT_REL) function = DiscourseFunction.INDIRECT_OBJECT;
			
			PhraseElement relativePhrase;
			if ((function == DiscourseFunction.INDIRECT_OBJECT) && (objetIndirect instanceof PPPhraseSpec)) {
				relativePhrase = objetIndirect;
			} else {
				relativePhrase = factory.createNounPhrase();
				relativePhrase.setFeature(InternalFeature.DISCOURSE_FUNCTION, function);
			}
			proposition.setFeature(FrenchFeature.RELATIVE_PHRASE, relativePhrase);
			realisationString = realiser.realise(proposition).getRealisation();
			
		} else {
			realisationString = realiser.realiseSentence(proposition);
		}
		
		panelRealisation.realisationTextArea.setText(realisationString);
	}

	private void setObjetDirect(NPPanel panelObjetDirect,
			NLGFactory factory, SPhraseSpec proposition) {
		List<LexicalCategory> modifierCategories = Arrays.asList(LexicalCategory.ADJECTIVE);
		List<LexicalCategory> nounCategories = Arrays.asList(LexicalCategory.NOUN,
				LexicalCategory.PRONOUN, LexicalCategory.ADJECTIVE);
		Lexicon lexicon = factory.getLexicon();
		PhraseElement objet = null;
		
		String detTexte = getText(panelObjetDirect.det, determinerCategories, lexicon);
		String nomTexte = getText(panelObjetDirect.nom, nounCategories, lexicon);
		if (nomTexte != null) {
			if (detTexte == null) {
				proposition.setObject(nomTexte);
				objet = (PhraseElement) proposition.getObject();
				addModifier(objet, panelObjetDirect.modifier1, panelObjetDirect.posMod1, modifierCategories, lexicon);
				addModifier(objet, panelObjetDirect.modifier2, panelObjetDirect.posMod2, modifierCategories, lexicon);
			} else {
				objet = createNP(panelObjetDirect, factory);
				proposition.setObject(objet);
			}
		}
	}

	private NPPhraseSpec createNP(NPPanel panel, NLGFactory factory) {
		List<LexicalCategory> modifierCategories = Arrays.asList(LexicalCategory.ADJECTIVE);
		List<LexicalCategory> nounCategories = Arrays.asList(LexicalCategory.NOUN, LexicalCategory.PRONOUN);
		NPPhraseSpec np = null;
		Lexicon lexicon = factory.getLexicon();
		
		String nounString = getText(panel.nom, nounCategories, lexicon);
		if (nounString != null) {
			String detString = getText(panel.det, determinerCategories, lexicon);
			if (detString != null) {
				np = factory.createNounPhrase(detString, nounString);
			} else {
				np = factory.createNounPhrase(nounString);
			}
			
			addModifier(np, panel.modifier1, panel.posMod1, modifierCategories, lexicon);
			addModifier(np, panel.modifier2, panel.posMod2, modifierCategories, lexicon);
			
			if (panel.pluriel.isSelected()) np.setPlural(true);
			if (panel.pronom.isSelected()) np.setFeature(Feature.PRONOMINAL, true);
		}
		
		return np;
	}

	private void addModifier(PhraseElement phrase, JTextField modTextField,
			JComboBox posMod,  List<LexicalCategory> categories, Lexicon lexicon) {
		String modifierString = getText(modTextField, categories, lexicon);
		if (modifierString != null) {
			Object selection = posMod.getSelectedItem();
			if ("prémodificateur".equals(selection)) {
				phrase.addPreModifier(modifierString);
			} else if ("postmodificateur".equals(selection)) {
				phrase.addPostModifier(modifierString);
			} else if ("\"frontmodifier\"".equals(selection)) {
				phrase.addFrontModifier(modifierString);
			} else {
				phrase.addModifier(modifierString);
			}
		}
	}

	private String getText(JTextField textfield, List<LexicalCategory> categories, Lexicon lexicon) {
		String text = textfield.getText();
		if (text != null) {
			if (text.trim().isEmpty()) text = null;
			else text = addStarToUnknownWords(text, categories, lexicon); 
		}		
		return text;
	}
	
	/**
	 * Add a star (*) to words not found in the lexicon,
	 * to demonstrate what kind of words can be found in the lexicon.
	 * This is an extra in the demo and doesn't happen
	 * when directly using SimpleNLG-EnFr.
	 * 
	 * @param word
	 * @param categories
	 * @param lexicon
	 * @return
	 */
	private String addStarToUnknownWords(String word, List<LexicalCategory> categories, Lexicon lexicon) {
//		if (panelRealisation.unknownWordsCheckbox.isSelected()) {
			boolean found = false;
			for (LexicalCategory category : categories) {
				if (lexicon.hasWordFromVariant(word, category)) {
					found = true;
					break;
				}
			}
			if (!found) word = "*" + word;
//		}
		return word;		
	}
}
