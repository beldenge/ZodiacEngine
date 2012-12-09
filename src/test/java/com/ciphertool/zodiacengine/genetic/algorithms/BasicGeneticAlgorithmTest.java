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
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.algorithms.BasicGeneticAlgorithm;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithm;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class BasicGeneticAlgorithmTest {
	private static Logger log = Logger.getLogger(BasicGeneticAlgorithmTest.class);

	private static ApplicationContext context;
	private static Population population;
	private static GeneticAlgorithm geneticAlgorithm;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic-test.xml");
		log.info("Spring context created successfully!");

		population = (Population) context.getBean("population");
		geneticAlgorithm = (GeneticAlgorithm) context.getBean("geneticAlgorithm");

		CipherDao cipherDao = (CipherDao) context.getBean("cipherDao");

		FitnessEvaluator fitnessEvaluator = (FitnessEvaluator) context
				.getBean("defaultFitnessEvaluator");

		CrossoverAlgorithm crossoverAlgorithm = (CrossoverAlgorithm) context
				.getBean("defaultCrossoverAlgorithm");

		MutationAlgorithm mutationAlgorithm = (MutationAlgorithm) context
				.getBean("defaultMutationAlgorithm");

		SelectionAlgorithm selectionAlgorithm = (SelectionAlgorithm) context
				.getBean("defaultSelectionAlgorithm");

		Cipher cipher = cipherDao.findByCipherName("zodiac340");
		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(cipher,
				100, -1, 50, 0.9, 0.001, 0.05, fitnessEvaluator, crossoverAlgorithm,
				mutationAlgorithm, selectionAlgorithm);

		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);

		population.populateIndividuals(10);
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
		population = null;
		geneticAlgorithm = null;
		context = null;
	}

	@Test
	public void testSelect() {
		((BasicGeneticAlgorithm) geneticAlgorithm).getStrategy().setSurvivalRate(0.9);
		((BasicGeneticAlgorithm) geneticAlgorithm).getStrategy().setPopulationSize(10);

		SolutionChromosome bestChromosome = (SolutionChromosome) population.getBestFitIndividual();
		log.info("Best fit individual before: " + bestChromosome);

		geneticAlgorithm.select();

		assertEquals(population.size(), 9);

		log.info("Best fit individual after: "
				+ ((SolutionChromosome) population.getBestFitIndividual()));
		assertTrue(bestChromosome == (SolutionChromosome) population.getBestFitIndividual());
	}
}
