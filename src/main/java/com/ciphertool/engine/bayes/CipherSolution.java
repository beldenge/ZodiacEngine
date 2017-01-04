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

package com.ciphertool.engine.bayes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.entities.Cipher;

public class CipherSolution {
	private static Logger		log			= LoggerFactory.getLogger(CipherSolution.class);

	private static final int	KEY_SIZE	= 54;

	protected Cipher			cipher;

	private BigDecimal			score		= BigDecimal.ZERO;

	private Map<String, String>	ciphertextToPlaintextMappings;

	public CipherSolution() {
		ciphertextToPlaintextMappings = new HashMap<>();
	}

	/**
	 * @param cipherId
	 *            the cipherId to set
	 * @param rows
	 *            the rows to set
	 * @param columns
	 *            the columns to set
	 */
	public CipherSolution(Cipher cipher, int numGenes) {
		if (cipher == null) {
			throw new IllegalArgumentException("Cannot construct CipherSolution with null cipher.");
		}

		this.cipher = cipher;

		ciphertextToPlaintextMappings = new HashMap<>(numGenes);
	}

	/**
	 * @return the cipher
	 */
	public Cipher getCipher() {
		return this.cipher;
	}

	/**
	 * @param cipher
	 *            the cipher to set
	 */
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Map<String, String> getMappings() {
		return Collections.unmodifiableMap(ciphertextToPlaintextMappings);
	}

	public void putMapping(String key, String plaintext) {
		if (null == plaintext) {
			log.warn("Attempted to insert a null Gene to CipherSolution.  Returning. " + this);

			return;
		}

		if (this.ciphertextToPlaintextMappings.get(key) != null) {
			log.warn("Attempted to insert a Gene to CipherSolution with key " + key
					+ ", but the key already exists.  If this was intentional, please use replaceGene() instead.  Returning. "
					+ this);

			return;
		}

		this.ciphertextToPlaintextMappings.put(key, plaintext);
	}

	public String removeMapping(String key) {
		if (null == this.ciphertextToPlaintextMappings || null == this.ciphertextToPlaintextMappings.get(key)) {
			log.warn("Attempted to remove a Gene from CipherSolution with key " + key
					+ ", but this key does not exist.  Returning null.");

			return null;
		}

		return this.ciphertextToPlaintextMappings.remove(key);
	}

	/*
	 * This does the same thing as putMapping(), and exists solely for semantic consistency.
	 */
	public void replaceMapping(String key, String newPlaintext) {
		if (null == newPlaintext) {
			log.warn("Attempted to replace a Gene from CipherSolution, but the supplied Gene was null.  Cannot continue. "
					+ this);

			return;
		}

		if (null == this.ciphertextToPlaintextMappings || null == this.ciphertextToPlaintextMappings.get(key)) {
			log.warn("Attempted to replace a Gene from CipherSolution with key " + key
					+ ", but this key does not exist.  Cannot continue.");

			return;
		}

		this.ciphertextToPlaintextMappings.put(key, newPlaintext);
	}

	public Integer actualSize() {
		return this.ciphertextToPlaintextMappings.size();
	}

	public Integer targetSize() {
		return KEY_SIZE;
	}

	public CipherSolution clone() {
		CipherSolution copySolution = new CipherSolution(this.cipher, this.ciphertextToPlaintextMappings.size());

		String nextGene = null;
		for (String key : this.ciphertextToPlaintextMappings.keySet()) {
			nextGene = this.ciphertextToPlaintextMappings.get(key);

			copySolution.putMapping(key, nextGene);
		}

		// We need to set these values last to maintain whether evaluation is needed on the clone
		copySolution.setScore(this.score);

		return copySolution;
	}

	/*
	 * Prints the properties of the solution and then outputs the entire plaintext list in block format.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Solution [score=" + String.format("%1$,.2f", score) + "]\n");

		if (this.cipher != null) {
			String nextPlaintext = null;
			int actualSize = this.cipher.getCiphertextCharacters().size();
			for (int i = 0; i < actualSize; i++) {

				nextPlaintext = this.ciphertextToPlaintextMappings.get(this.cipher.getCiphertextCharacters().get(i).getValue());

				sb.append(" ");
				sb.append(nextPlaintext);
				sb.append(" ");

				/*
				 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function doesn't
				 * break.
				 */
				if (((i + 1) % this.cipher.getColumns()) == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}

		return sb.toString();
	}
}
