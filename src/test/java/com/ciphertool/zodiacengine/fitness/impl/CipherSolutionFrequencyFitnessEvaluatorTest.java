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

package com.ciphertool.zodiacengine.fitness.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CipherSolutionFrequencyFitnessEvaluatorTest extends FrequencyFitnessEvaluatorTestBase {
	@Test
	public void testEvaluate() {
		CipherSolutionFrequencyFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyFitnessEvaluator();
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertTrue(knownSolution.isEvaluationNeeded());
		knownSolution.setFitness(fitness);
		assertFalse(knownSolution.isEvaluationNeeded());

		assertEquals(new Double(259.52572941176464), fitness);
		assertEquals(354, knownSolution.getTotalMatches());
		assertEquals(53, knownSolution.getUniqueMatches());
		assertEquals(407, knownSolution.getAdjacentMatches());
	}
}
