/**
 * Copyright 2013 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.zodiacengine.dao;

import com.ciphertool.genetics.dao.SequenceDao;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;
import com.ciphertool.sentencebuilder.util.LetterUtils;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;

public class PlaintextSequenceDao implements SequenceDao {

	@Override
	public Sequence findRandomSequence(Gene gene, int sequenceIndex) {
		Sequence sequence = new PlaintextSequence(sequenceIndex, String.valueOf(LetterUtils
				.getRandomLetter()), gene);

		return sequence;
	}
}
