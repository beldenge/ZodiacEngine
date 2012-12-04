/**
 * Copyright 2012 George Belden
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

package com.ciphertool.zodiacengine.genetic.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.algorithms.ConservativeCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.algorithms.LowestCommonGroupCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.MutationAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class CrossoverAlgorithmTest {
	private static Logger log = Logger.getLogger(CrossoverAlgorithmTest.class);

	private static ApplicationContext context;
	private static CrossoverAlgorithm crossoverAlgorithm;
	private static Population population;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic-test.xml");
		log.info("Spring context created successfully!");

		GeneticAlgorithm geneticAlgorithm = (GeneticAlgorithm) context.getBean("geneticAlgorithm");

		CipherDao cipherDao = (CipherDao) context.getBean("cipherDao");

		FitnessEvaluator fitnessEvaluator = (FitnessEvaluator) context
				.getBean("defaultFitnessEvaluator");

		crossoverAlgorithm = (CrossoverAlgorithm) context.getBean("defaultCrossoverAlgorithm");

		MutationAlgorithm mutationAlgorithm = (MutationAlgorithm) context
				.getBean("defaultMutationAlgorithm");

		Cipher cipher = cipherDao.findByCipherName("zodiac340");
		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(cipher,
				100, -1, 50, 0.9, 0.001, 0.05, fitnessEvaluator, crossoverAlgorithm,
				mutationAlgorithm);

		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);

		population = (Population) context.getBean("population");

		population.populateIndividuals(100);
		population.evaluateFitness(null);
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		((ClassPathXmlApplicationContext) context).close();
		crossoverAlgorithm = null;
		population = null;
		context = null;
	}

	@Test
	public void testCrossover() {
		Chromosome mom = population.spinObjectRouletteWheel();
		log.info("Mom: " + mom);

		Chromosome dad = population.spinObjectRouletteWheel();
		log.info("Dad: " + dad);

		Chromosome child = crossoverAlgorithm.crossover(mom, dad);

		assertNotNull(child);

		int genesBefore = child.getGenes().size();

		log.info("Child: " + child);

		int count = 0;
		for (Gene gene : child.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(((SolutionChromosome) child).getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				assertEquals(((SolutionChromosome) child).getPlaintextCharacters().get(count)
						.getId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(child.actualSize().intValue(), ((SolutionChromosome) child)
				.getPlaintextCharacters().size());

		log.info("Solution size: " + ((SolutionChromosome) child).getPlaintextCharacters().size());

		assertEquals(genesBefore, child.getGenes().size());

		/*
		 * For LowestCommonGroupCrossoverAlgorithm and
		 * ConservativeCrossoverAlgorithm only
		 */
		if (crossoverAlgorithm instanceof LowestCommonGroupCrossoverAlgorithm
				|| crossoverAlgorithm instanceof ConservativeCrossoverAlgorithm) {
			for (Plaintext plaintext : ((SolutionChromosome) child).getPlaintextCharacters()) {
				if ((!plaintext.getValue().equals(
						((SolutionChromosome) mom).getPlaintextCharacters().get(
								plaintext.getId().getCiphertextId()).getValue()))
						&& (!plaintext.getValue().equals(
								((SolutionChromosome) dad).getPlaintextCharacters().get(
										plaintext.getId().getCiphertextId()).getValue()))) {
					fail("Plaintext value from child does not match Plaintext from either parent: "
							+ plaintext);
				}
			}
		}
	}
}
