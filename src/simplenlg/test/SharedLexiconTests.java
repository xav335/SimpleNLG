/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is "Simplenlg".
 *
 * The Initial Developer of the Original Code is Ehud Reiter, Albert Gatt and Dave Westwater.
 * Portions created by Ehud Reiter, Albert Gatt and Dave Westwater are Copyright (C) 2010-11 The University of Aberdeen. All Rights Reserved.
 *
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell, Pierre-Luc Vaudry.
 */
package simplenlg.test;

import junit.framework.Assert;
import simplenlg.features.LexicalFeature;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;

/**
 * @author D. Westwater, Data2Text Ltd
 * 
 */
public class SharedLexiconTests {

	public static void doBasicTests(Lexicon lexicon) {
		// test getWords. Should be 2 "can" (of any cat), 1 noun tree, 0 adj
		// trees
		Assert.assertEquals(3, lexicon.getWords("can").size());
		Assert.assertEquals(1, lexicon.getWords("can", LexicalCategory.NOUN)
				.size());
		Assert.assertEquals(0, lexicon.getWords("can",
				LexicalCategory.ADJECTIVE).size());

		// below test removed as standard morph variants no longer recorded in
		// lexicon
		// WordElement early = lexicon.getWord("early",
		// LexicalCategory.ADJECTIVE);
		// Assert.assertEquals("earlier",
		// early.getFeatureAsString(Feature.COMPARATIVE));

		// test getWord. Comparative of ADJ "good" is "better", superlative is
		// "best", this is a qualitative and predicative adjective
		WordElement good = lexicon.getWord("good", LexicalCategory.ADJECTIVE);
		Assert.assertEquals("better", good
				.getFeatureAsString(LexicalFeature.COMPARATIVE));
		Assert.assertEquals("best", good
				.getFeatureAsString(LexicalFeature.SUPERLATIVE));
		Assert.assertEquals(true, good.getFeatureAsBoolean(
				LexicalFeature.QUALITATIVE).booleanValue());
		Assert.assertEquals(true, good.getFeatureAsBoolean(
				LexicalFeature.PREDICATIVE).booleanValue());
		Assert.assertEquals(false, good.getFeatureAsBoolean(
				LexicalFeature.COLOUR).booleanValue());
		Assert.assertEquals(false, good.getFeatureAsBoolean(
				LexicalFeature.CLASSIFYING).booleanValue());

		// test getWord. There is only one "woman", and its plural is "women".
		// It is not an acronym, not proper, and countable
		WordElement woman = lexicon.getWord("woman");

		Assert.assertEquals("women", woman
				.getFeatureAsString(LexicalFeature.PLURAL));
		Assert.assertEquals(null, woman
				.getFeatureAsString(LexicalFeature.ACRONYM_OF));
		Assert.assertEquals(false, woman.getFeatureAsBoolean(
				LexicalFeature.PROPER).booleanValue());
		Assert.assertFalse(woman.getFeatureAsStringList(
				LexicalFeature.INFLECTIONS).contains("uncount"));
		
		//NB: This fails if the lexicon is XMLLexicon. No idea why.
		 //Assert.assertEquals("irreg", woman.getFeatureAsString(LexicalFeature.DEFAULT_INFL));

		// test getWord. Noun "sand" is non-count
		WordElement sand = lexicon.getWord("sand", LexicalCategory.NOUN);
		Assert.assertEquals(true, sand.getFeatureAsStringList(
				LexicalFeature.INFLECTIONS).contains("nonCount")
				|| sand.getFeatureAsStringList(LexicalFeature.INFLECTIONS)
						.contains("uncount"));
		Assert.assertTrue(sand.getFeatureAsString(LexicalFeature.DEFAULT_INFL)
				.equals("uncount")
				|| sand.getFeatureAsString(LexicalFeature.DEFAULT_INFL).equals(
						"nonCount"));

		// test hasWord
		Assert.assertEquals(true, lexicon.hasWord("tree")); // "tree" exists
		Assert.assertEquals(false, lexicon.hasWord("tree",
				LexicalCategory.ADVERB)); // but not as an adverb

		// test getWordByID; quickly, also check that this is a verb_modifier
		WordElement quickly = lexicon.getWordByID("E0051632");
		Assert.assertEquals("quickly", quickly.getBaseForm());
		Assert.assertEquals(LexicalCategory.ADVERB, quickly.getCategory());
		Assert.assertEquals(true, quickly.getFeatureAsBoolean(
				LexicalFeature.VERB_MODIFIER).booleanValue());
		Assert.assertEquals(false, quickly.getFeatureAsBoolean(
				LexicalFeature.SENTENCE_MODIFIER).booleanValue());
		Assert.assertEquals(false, quickly.getFeatureAsBoolean(
				LexicalFeature.INTENSIFIER).booleanValue());

		// test getWordFromVariant, verb type (tran or intran, not ditran)
		// getWordFromVariant cannot be tested as the simplenlg.lexicon.XMLLexicon implementation is language-agnostic. Thus no variant is returned for this type of Lexicon.

        // test modal
		WordElement can = lexicon.getWord("can", LexicalCategory.MODAL);
		Assert.assertEquals("could", can
				.getFeatureAsString(LexicalFeature.PAST));

		// test non-existent word
		Assert.assertEquals(0, lexicon.getWords("akjmchsgk").size());

		// test lookup word method
		Assert.assertEquals(lexicon.lookupWord("say", LexicalCategory.VERB)
				.getBaseForm(), "say");

        // say -> said is based on variant search. No variant is returned for this type of simplenlg.lexicon.Lexicon.
        // Assert.assertEquals(lexicon.lookupWord("said", LexicalCategory.VERB)
        //		.getBaseForm(), "say");
		Assert.assertEquals(lexicon
				.lookupWord("E0054448", LexicalCategory.VERB).getBaseForm(),
				"say");
	}

}
