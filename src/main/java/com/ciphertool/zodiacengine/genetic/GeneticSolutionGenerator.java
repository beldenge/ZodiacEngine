package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class GeneticSolutionGenerator implements SolutionGenerator {
	private Cipher cipher;
	private GeneListDao wordGeneListDao;
	private Logger log = Logger.getLogger(getClass());

	public GeneticSolutionGenerator(String cipherName, CipherDao cipherDao) {
		cipher = cipherDao.findByCipherName(cipherName);
	}

	@Override
	public Solution generateSolution() {
		// Set confidence levels to lowest possible
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0);

		solution.setCipher(cipher);

		List<Gene> words = getWords(solution);

		solution.setGenes(words);

		log.debug(solution);

		return solution;
	}

	private List<Gene> getWords(Solution solution) {
		List<Gene> wordList = new ArrayList<Gene>();
		WordGene nextWord;
		int length = 0;

		do {
			nextWord = (WordGene) wordGeneListDao.findRandomGene();

			length += nextWord.size();

			wordList.add(nextWord);
		} while (length < cipher.length());

		return wordList;
	}

	/**
	 * @param geneListDao
	 *            geneListDao wordListDao to set
	 */
	@Required
	public void setGeneListDao(GeneListDao geneListDao) {
		this.wordGeneListDao = geneListDao;
	}
}
