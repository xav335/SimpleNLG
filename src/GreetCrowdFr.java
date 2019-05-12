import simplenlg.framework.*;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.french.XMLLexicon;
import simplenlg.realiser.*;
import simplenlg.phrasespec.*;

public class GreetCrowdFr {
	public static void main(String[] args) {
		Lexicon lexicon = new XMLLexicon();
		NLGFactory factory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser();

		NPPhraseSpec theMan = factory.createNounPhrase("le", "homme");
		NPPhraseSpec theCrowd = factory.createNounPhrase("le", "foule");

		SPhraseSpec greeting = factory.createClause(theMan, "saluer", theCrowd);

		String outString = realiser.realiseSentence(greeting);
		System.out.println(outString);
	}
}
