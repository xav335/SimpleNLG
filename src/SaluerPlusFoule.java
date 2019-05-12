import simplenlg.framework.*;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.french.XMLLexicon;
import simplenlg.realiser.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import simplenlg.features.french.*;

public class SaluerPlusFoule {
	public static void main(String[] args) {
		Lexicon lexicon = new XMLLexicon();
		NLGFactory factory = new NLGFactory(lexicon);
		
		NPPhraseSpec snAgent = factory.createNounPhrase("le", "agent");
		snAgent.addModifier("immobilier");
		NPPhraseSpec snMaison = factory.createNounPhrase("le", "maison");
		snMaison.setPlural(true);
		NPPhraseSpec snCliente = factory.createNounPhrase("le", "plupart");
		NPPhraseSpec snBureau = factory.createNounPhrase("son", "compétence");
		PPPhraseSpec spDansBureau = factory.createPrepositionPhrase("par", snBureau);
		SPhraseSpec proposition6 = factory.createClause(snAgent, "vendre", snMaison);
		proposition6.setIndirectObject(snCliente);
		proposition6.addComplement(spDansBureau);
		proposition6.setFeature(Feature.TENSE, Tense.PAST);
		outln(proposition6);
//		proposition6.setFeature(FrenchFeature.RELATIVE_PHRASE, snAgent);
//		outln(proposition6);
//		proposition6.setFeature(FrenchFeature.RELATIVE_PHRASE, snMaison);
//		outln(proposition6);
//		proposition6.setFeature(FrenchFeature.RELATIVE_PHRASE, snCliente);
//		outln(proposition6);
//		proposition6.setFeature(FrenchFeature.RELATIVE_PHRASE, spDansBureau);
//		outln(proposition6);
	}

	public static void outln(NLGElement outElement) {
		Realiser realiser = new Realiser();
		NLGElement realisedElement = realiser.realise(outElement);
		String realisation = realisedElement.getRealisation();
		System.out.println(realisation);
	}
}