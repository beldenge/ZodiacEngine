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

package com.ciphertool.engine.printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.ChromosomePrinter;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sherlock.wordgraph.IndexNode;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;
import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.dao.cipherkey.TopWordsFacade;
import com.ciphertool.engine.entities.cipherkey.CipherKeyChromosome;

public class CipherKeyIndexedNGramChromosomePrinter implements ChromosomePrinter {
	protected TopWordsFacade topWordsFacade;

	@Override
	public String print(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (((CipherKeyChromosome) chromosome).getCipher().getColumns()
				* (((CipherKeyChromosome) chromosome).getCipher().getRows() - 1));

		String fullSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome);

		String currentSolutionString = fullSolutionString.substring(0, lastRowBegin);

		String longestMatch;
		IndexNode rootNode = topWordsFacade.getIndexedWordsAndNGrams();

		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = WordGraphUtils.findLongestWordMatch(rootNode, 0, currentSolutionString.substring(i), null);

			if (longestMatch != null) {
				if (!matchMap.containsKey(i)) {
					matchMap.put(i, new ArrayList<Match>());
				}

				matchMap.get(i).add(new Match(i, i + longestMatch.length() - 1, longestMatch));
			}
		}

		List<MatchNode> rootNodes = new ArrayList<MatchNode>();
		int beginPos;
		for (beginPos = 0; beginPos < lastRowBegin; beginPos++) {
			if (matchMap.containsKey(beginPos)) {
				if (WordGraphUtils.nonOverlapping(beginPos, rootNodes)) {
					break;
				}

				for (Match match : matchMap.get(beginPos)) {
					rootNodes.add(new MatchNode(match));
				}
			}
		}

		List<String> branches = new ArrayList<String>();
		for (MatchNode node : rootNodes) {
			WordGraphUtils.findOverlappingChildren(node.getSelf().getEndPos() + 1, lastRowBegin, matchMap, node);

			branches.addAll(node.printBranches());
		}

		long score;
		long highestScore = 0;

		String bestBranch = "";

		for (String branch : branches) {
			score = 0;

			for (String word : branch.split(", ")) {
				score += Math.pow(2, word.length());
			}

			if (score > highestScore) {
				highestScore = score;
				bestBranch = branch;
			}
		}

		StringBuffer sb = new StringBuffer();

		sb.append("Solution [id=" + chromosome.getId() + ", cipherId="
				+ ((CipherKeyChromosome) chromosome).getCipher().getId() + ", fitness="
				+ String.format("%1$,.2f", chromosome.getFitness()) + ", age=" + chromosome.getAge()
				+ ", numberOfChildren=" + chromosome.getNumberOfChildren() + ", evaluationNeeded="
				+ chromosome.isEvaluationNeeded() + "]\n");

		List<String> words = new ArrayList<String>();

		// In the off chance that no words were found at all
		if (!bestBranch.isEmpty()) {
			words.addAll(Arrays.asList(bestBranch.split(", ")));
		}

		String word = words.isEmpty() ? null : words.get(0);
		for (int i = 0; i < fullSolutionString.length(); i++) {
			if (!words.isEmpty() && i < lastRowBegin && word.equals(fullSolutionString.substring(i, i
					+ word.length()))) {
				sb.append("[");

				for (int j = 0; j < word.length(); j++) {
					if (j > 0) {
						sb.append(" ");
					}

					sb.append(fullSolutionString.charAt(i + j));

					if (j < word.length() - 1) {
						sb.append(" ");
					} else if (j == word.length() - 1) {
						sb.append("]");
					}

					/*
					 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function
					 * doesn't break.
					 */
					if (((i + j + 1) % ((CipherKeyChromosome) chromosome).getCipher().getColumns()) == 0) {
						sb.append("\n");
					} else {
						sb.append(" ");
					}

					// Prevent ArrayIndexOutOfBoundsException
					if (i + j >= fullSolutionString.length()) {
						break;
					}
				}

				i += word.length() - 1;

				words.remove(0);

				if (!words.isEmpty()) {
					word = words.get(0);
				}

				continue;
			}

			sb.append(" ");
			sb.append(fullSolutionString.charAt(i));
			sb.append(" ");

			/*
			 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function doesn't
			 * break.
			 */
			if (((i + 1) % ((CipherKeyChromosome) chromosome).getCipher().getColumns()) == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	/**
	 * @param topWordsFacade
	 *            the topWordsFacade to set
	 */
	@Required
	public void setTopWordsFacade(TopWordsFacade topWordsFacade) {
		this.topWordsFacade = topWordsFacade;
	}
}