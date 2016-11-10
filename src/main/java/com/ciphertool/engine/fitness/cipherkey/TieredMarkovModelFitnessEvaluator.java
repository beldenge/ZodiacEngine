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

package com.ciphertool.engine.fitness.cipherkey;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.dao.MarkovModelDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.KGramIndexNode;
import com.ciphertool.sherlock.markov.MarkovModel;

public class TieredMarkovModelFitnessEvaluator implements FitnessEvaluator {
	protected Cipher			cipher;

	protected MarkovModelDao	markovModelDao;
	private MarkovModel			model;

	private int					lastRowBegin;
	private int					minimumOrder;

	@PostConstruct
	public void init() {
		model = markovModelDao.getModel();

		if (minimumOrder > model.getOrder()) {
			throw new IllegalArgumentException("Minimum order is set to " + minimumOrder
					+ ", but it must be less than or equal to the Markov model order of " + model.getOrder() + ".");
		}
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		int order = model.getOrder();

		double matches = 0.0;
		KGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order));
			}

			if (match == null) {
				match = model.findLongest(currentSolutionString.substring(i, i + order + 1));
			}

			if (match == null) {
				continue;
			}

			if (match.getLevel() >= minimumOrder) {
				matches += (double) match.getLevel() / (double) (order + 1);
			}

			if (!(match.getLevel() > order)) {
				match = null;
			}
		}

		return (matches / (lastRowBegin - order - 1));
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
	}

	/**
	 * @param markovModelDao
	 *            the markovModelDao to set
	 */
	@Required
	public void setMarkovModelDao(MarkovModelDao markovModelDao) {
		this.markovModelDao = markovModelDao;
	}

	/**
	 * @param minimumOrder
	 *            the minimumOrder to set
	 */
	@Required
	public void setMinimumOrder(int minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	@Override
	public String getDisplayName() {
		return "Tiered Markov Model";
	}
}