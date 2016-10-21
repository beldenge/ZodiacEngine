/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.mocks;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.dao.WordGeneDao;
import com.ciphertool.engine.entities.SolutionChromosome;
import com.ciphertool.engine.entities.WordGene;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sherlock.entities.Word;
import com.ciphertool.sherlock.enumerations.PartOfSpeechType;

public class WordGeneDaoMock extends WordGeneDao {
	private static Logger log = LoggerFactory.getLogger(WordGeneDaoMock.class);

	@Override
	public Gene findRandomGene(Chromosome chromosome) {
		WordGene wordGene = new WordGene(new Word("$$$", PartOfSpeechType.NONE), (SolutionChromosome) chromosome);

		return wordGene;
	}

	@Override
	public Gene findRandomGeneOfLength(Chromosome chromosome, int length) {
		if (length < 1) {
			log.warn("Unable to find random gene of length " + length
					+ " since it is less than 1, which is obviously the minimum length.  Returning null.");

			return null;
		}

		if (length <= 10) {
			return findRandomGeneWithinTen(chromosome, length);
		}

		return findRandomGeneOverTen(chromosome, length);
	}

	public Gene findRandomGeneWithinTen(Chromosome chromosome, int length) {
		Map<Integer, Gene> geneLengthMap = new HashMap<Integer, Gene>();

		Word word1 = new Word("I", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, (SolutionChromosome) chromosome);
		geneLengthMap.put(1, wordGene1);

		Word word2 = new Word("am", PartOfSpeechType.PLURAL);
		WordGene wordGene2 = new WordGene(word2, (SolutionChromosome) chromosome);
		geneLengthMap.put(2, wordGene2);

		Word word3 = new Word("the", PartOfSpeechType.NOUN_PHRASE);
		WordGene wordGene3 = new WordGene(word3, (SolutionChromosome) chromosome);
		geneLengthMap.put(3, wordGene3);

		Word word4 = new Word("best", PartOfSpeechType.VERB_PARTICIPLE);
		WordGene wordGene4 = new WordGene(word4, (SolutionChromosome) chromosome);
		geneLengthMap.put(4, wordGene4);

		Word word5 = new Word("homie", PartOfSpeechType.VERB_TRANSITIVE);
		WordGene wordGene5 = new WordGene(word5, (SolutionChromosome) chromosome);
		geneLengthMap.put(5, wordGene5);

		Word word6 = new Word("hollah", PartOfSpeechType.VERB_INTRANSITIVE);
		WordGene wordGene6 = new WordGene(word6, (SolutionChromosome) chromosome);
		geneLengthMap.put(6, wordGene6);

		Word word7 = new Word("seventy", PartOfSpeechType.ADJECTIVE);
		WordGene wordGene7 = new WordGene(word7, (SolutionChromosome) chromosome);
		geneLengthMap.put(7, wordGene7);

		Word word8 = new Word("trillion", PartOfSpeechType.ADVERB);
		WordGene wordGene8 = new WordGene(word8, (SolutionChromosome) chromosome);
		geneLengthMap.put(8, wordGene8);

		Word word9 = new Word("benjamins", PartOfSpeechType.CONJUNCTION);
		WordGene wordGene9 = new WordGene(word9, (SolutionChromosome) chromosome);
		geneLengthMap.put(9, wordGene9);

		Word word10 = new Word("investment", PartOfSpeechType.PREPOSITION);
		WordGene wordGene10 = new WordGene(word10, (SolutionChromosome) chromosome);
		geneLengthMap.put(10, wordGene10);

		return geneLengthMap.get(length);
	}

	public Gene findRandomGeneOverTen(Chromosome chromosome, int length) {
		StringBuffer word = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			word.append("$");
		}

		WordGene wordGene = new WordGene(new Word(word.toString(), PartOfSpeechType.NONE),
				(SolutionChromosome) chromosome);

		return wordGene;
	}
}