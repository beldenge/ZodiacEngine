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

package com.ciphertool.zodiacengine.gui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithmType;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithmType;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithmType;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.util.FitnessEvaluatorType;
import com.ciphertool.zodiacengine.gui.service.CipherSolutionService;

public class ZodiacCipherSolutionController implements CipherSolutionController,
		ApplicationContextAware {
	private Logger log = Logger.getLogger(getClass());
	private ApplicationContext context;
	private CipherSolutionService cipherSolutionService;
	private CipherDao cipherDao;
	private FitnessEvaluator fitnessEvaluatorDefault;
	private CrossoverAlgorithm crossoverAlgorithmDefault;
	private MutationAlgorithm mutationAlgorithmDefault;
	private SelectionAlgorithm selectionAlgorithmDefault;
	private FitnessEvaluator knownSolutionFitnessEvaluator;

	@Override
	public void startServiceThread(final String cipherName, final int populationSize,
			final int lifespan, final int numGenerations, final double survivalRate,
			final double mutationRate, final double crossoverRate,
			final String fitnessEvaluatorName, final String crossoverAlgorithmName,
			final String mutationAlgorithmName, final String selectionAlgorithmName,
			final boolean compareToKnownSolution) {
		if (cipherSolutionService.isRunning()) {
			log.info("Cipher solution service is already running.  Cannot start until current process completes.");
		} else {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					Cipher cipher = cipherDao.findByCipherName(cipherName);

					FitnessEvaluator fitnessEvaluator = getFitnessEvaluator(fitnessEvaluatorName);
					log.info("FitnessEvaluator implementation: " + fitnessEvaluator.getClass());

					CrossoverAlgorithm crossoverAlgorithm = getCrossoverAlgorithm(crossoverAlgorithmName);
					log.info("CrossoverAlgorithm implementation: " + crossoverAlgorithm.getClass());

					MutationAlgorithm mutationAlgorithm = getMutationAlgorithm(mutationAlgorithmName);
					log.info("MutationAlgorithm implementation: " + mutationAlgorithm.getClass());

					SelectionAlgorithm selectionAlgorithm = getSelectionAlgorithm(selectionAlgorithmName);
					log.info("SelectionAlgorithm implementation: " + selectionAlgorithm.getClass());

					GeneticAlgorithmStrategy geneticAlgorithmStrategy;
					if (knownSolutionFitnessEvaluator != null) {
						geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(cipher,
								populationSize, lifespan, numGenerations, survivalRate,
								mutationRate, crossoverRate, fitnessEvaluator, crossoverAlgorithm,
								mutationAlgorithm, selectionAlgorithm,
								knownSolutionFitnessEvaluator, compareToKnownSolution);
					} else {
						geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(cipher,
								populationSize, lifespan, numGenerations, survivalRate,
								mutationRate, crossoverRate, fitnessEvaluator, crossoverAlgorithm,
								mutationAlgorithm, selectionAlgorithm);
					}

					cipherSolutionService.begin(geneticAlgorithmStrategy);
				}
			});

			serviceThread.start();
		}
	}

	@Override
	public void stopServiceThread() {
		if (cipherSolutionService.isRunning()) {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					cipherSolutionService.endImmediately();
				}
			});

			serviceThread.start();
		} else {
			log.info("Cipher solution service is already stopped.  Nothing to do.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.gui.controller.CipherSolutionController#
	 * isServiceThreadActive()
	 */
	@Override
	public boolean isServiceThreadActive() {
		return cipherSolutionService.isRunning();
	}

	private FitnessEvaluator getFitnessEvaluator(String fitnessEvaluatorName) {
		FitnessEvaluator fitnessEvaluator = null;

		try {
			fitnessEvaluator = (FitnessEvaluator) context.getBean(FitnessEvaluatorType.valueOf(
					fitnessEvaluatorName).getType());
		} catch (IllegalArgumentException iae) {
			fitnessEvaluator = fitnessEvaluatorDefault;
		}

		return fitnessEvaluator;
	}

	private CrossoverAlgorithm getCrossoverAlgorithm(String crossoverAlgorithmName) {
		CrossoverAlgorithm crossoverAlgorithm = null;

		try {
			crossoverAlgorithm = (CrossoverAlgorithm) context.getBean(CrossoverAlgorithmType
					.valueOf(crossoverAlgorithmName).getType());
		} catch (IllegalArgumentException iae) {
			crossoverAlgorithm = crossoverAlgorithmDefault;
		}

		return crossoverAlgorithm;
	}

	private MutationAlgorithm getMutationAlgorithm(String mutationAlgorithmName) {
		MutationAlgorithm mutationAlgorithm = null;

		try {
			mutationAlgorithm = (MutationAlgorithm) context.getBean(MutationAlgorithmType.valueOf(
					mutationAlgorithmName).getType());
		} catch (IllegalArgumentException iae) {
			mutationAlgorithm = mutationAlgorithmDefault;
		}

		return mutationAlgorithm;
	}

	private SelectionAlgorithm getSelectionAlgorithm(String selectionAlgorithmName) {
		SelectionAlgorithm selectionAlgorithm = null;

		try {
			selectionAlgorithm = (SelectionAlgorithm) context.getBean(SelectionAlgorithmType
					.valueOf(selectionAlgorithmName).getType());
		} catch (IllegalArgumentException iae) {
			selectionAlgorithm = selectionAlgorithmDefault;
		}

		return selectionAlgorithm;
	}

	/**
	 * @param cipherSolutionService
	 *            the cipherSolutionService to set
	 */
	@Required
	public void setCipherSolutionService(CipherSolutionService cipherSolutionService) {
		this.cipherSolutionService = cipherSolutionService;
	}

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		this.cipherDao = cipherDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	/**
	 * @param fitnessEvaluatorDefault
	 *            the fitnessEvaluatorDefault to set
	 */
	@Required
	public void setFitnessEvaluatorDefault(FitnessEvaluator fitnessEvaluatorDefault) {
		this.fitnessEvaluatorDefault = fitnessEvaluatorDefault;
	}

	/**
	 * @param crossoverAlgorithmDefault
	 *            the crossoverAlgorithmDefault to set
	 */
	@Required
	public void setCrossoverAlgorithmDefault(CrossoverAlgorithm crossoverAlgorithmDefault) {
		this.crossoverAlgorithmDefault = crossoverAlgorithmDefault;
	}

	/**
	 * @param mutationAlgorithmDefault
	 *            the mutationAlgorithmDefault to set
	 */
	@Required
	public void setMutationAlgorithmDefault(MutationAlgorithm mutationAlgorithmDefault) {
		this.mutationAlgorithmDefault = mutationAlgorithmDefault;
	}

	/**
	 * @param selectionAlgorithmDefault
	 *            the selectionAlgorithmDefault to set
	 */
	@Required
	public void setSelectionAlgorithmDefault(SelectionAlgorithm selectionAlgorithmDefault) {
		this.selectionAlgorithmDefault = selectionAlgorithmDefault;
	}

	/**
	 * This is NOT required. We will not always know the solution. In fact, that
	 * should be the rare case.
	 * 
	 * @param knownSolutionFitnessEvaluator
	 *            the knownSolutionFitnessEvaluator to set
	 */
	public void setKnownSolutionFitnessEvaluator(FitnessEvaluator knownSolutionFitnessEvaluator) {
		this.knownSolutionFitnessEvaluator = knownSolutionFitnessEvaluator;
	}
}
