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

package com.ciphertool.engine.fitness;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.Ciphertext;
import com.ciphertool.engine.fitness.SolutionEvaluatorBase;

public class SolutionEvaluatorBaseTest {
	private static Cipher simpleCipher = new Cipher("simpleCipher", 1, 10);

	static {
		simpleCipher.setId(BigInteger.ZERO);
		simpleCipher.setHasKnownSolution(true);

		simpleCipher.addCiphertextCharacter(new Ciphertext(0, "a"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(1, "b"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(2, "b"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(3, "c"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(4, "d"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(5, "d"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(6, "d"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(7, "e"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(8, "f"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(9, "g"));

	}

	@Test
	public void testCreateKeyFromCiphertext() {
		SolutionEvaluatorBase solutionEvaluatorBase = new SolutionEvaluatorBase();
		solutionEvaluatorBase.setGeneticStructure(simpleCipher);

		HashMap<String, List<Ciphertext>> keyFromCiphertext = solutionEvaluatorBase.createKeyFromCiphertext();

		assertEquals(7, keyFromCiphertext.size());

		int totalElements = 0;
		for (List<Ciphertext> ciphertextCharacters : keyFromCiphertext.values()) {
			totalElements += ciphertextCharacters.size();
		}

		assertEquals(10, totalElements);

		assertEquals(1, keyFromCiphertext.get("a").size());
		assertEquals(0, keyFromCiphertext.get("a").get(0).getCiphertextId().intValue());

		assertEquals(2, keyFromCiphertext.get("b").size());
		assertEquals(1, keyFromCiphertext.get("b").get(0).getCiphertextId().intValue());
		assertEquals(2, keyFromCiphertext.get("b").get(1).getCiphertextId().intValue());

		assertEquals(1, keyFromCiphertext.get("c").size());
		assertEquals(3, keyFromCiphertext.get("c").get(0).getCiphertextId().intValue());

		assertEquals(3, keyFromCiphertext.get("d").size());
		assertEquals(4, keyFromCiphertext.get("d").get(0).getCiphertextId().intValue());
		assertEquals(5, keyFromCiphertext.get("d").get(1).getCiphertextId().intValue());
		assertEquals(6, keyFromCiphertext.get("d").get(2).getCiphertextId().intValue());

		assertEquals(1, keyFromCiphertext.get("e").size());
		assertEquals(7, keyFromCiphertext.get("e").get(0).getCiphertextId().intValue());

		assertEquals(1, keyFromCiphertext.get("f").size());
		assertEquals(8, keyFromCiphertext.get("f").get(0).getCiphertextId().intValue());

		assertEquals(1, keyFromCiphertext.get("g").size());
		assertEquals(9, keyFromCiphertext.get("g").get(0).getCiphertextId().intValue());
	}
}