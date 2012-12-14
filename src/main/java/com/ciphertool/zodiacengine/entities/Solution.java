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

package com.ciphertool.zodiacengine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "solution")
@AssociationOverrides(@AssociationOverride(name = "id.solutionSet", joinColumns = @JoinColumn(name = "solution_set_id")))
public class Solution implements Serializable {
	private static final long serialVersionUID = -1293349461638306782L;

	@EmbeddedId
	protected SolutionId id;

	@Column(name = "total_matches")
	protected int totalMatches;

	@Column(name = "unique_matches")
	protected int uniqueMatches;

	@Column(name = "adjacent_matches")
	protected int adjacentMatchCount;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.solution", cascade = CascadeType.ALL)
	protected List<Plaintext> plaintextCharacters = new ArrayList<Plaintext>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cipher_id")
	protected Cipher cipher;

	@Column(name = "created_timestamp", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Transient
	protected boolean needsEvaluation = true;

	public Solution() {
		this.id = new SolutionId();
	}

	public Solution(SolutionSet solutionSet, int solutionId) {
		this.id = new SolutionId(solutionSet, solutionId);
	}

	public Solution(SolutionSet solutionSet, int solutionId, Cipher cipher) {
		this.id = new SolutionId(solutionSet, solutionId);
		this.cipher = cipher;
	}

	public Solution(Cipher cipher, int totalMatches, int uniqueMatches, int adjacentMatches) {
		this.id = new SolutionId();
		this.cipher = cipher;
		this.totalMatches = totalMatches;
		this.uniqueMatches = uniqueMatches;
		this.adjacentMatchCount = adjacentMatches;
	}

	/**
	 * @return the id
	 */
	public SolutionId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(SolutionId id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public int getTotalMatches() {
		return totalMatches;
	}

	/**
	 * @param totalMatches
	 */
	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}

	/**
	 * @return the uniqueMatches
	 */
	public int getUniqueMatches() {
		return uniqueMatches;
	}

	/**
	 * @param uniqueMatches
	 *            the uniqueMatches to set
	 */
	public void setUniqueMatches(int uniqueMatches) {
		this.uniqueMatches = uniqueMatches;
	}

	/**
	 * @return the adjacentMatchCount
	 */
	public int getAdjacentMatchCount() {
		return adjacentMatchCount;
	}

	/**
	 * @param adjacentMatchCount
	 *            the adjacentMatchCount to set
	 */
	public void setAdjacentMatchCount(int adjacentMatchCount) {
		this.adjacentMatchCount = adjacentMatchCount;
	}

	public List<Plaintext> getPlaintextCharacters() {
		return Collections.unmodifiableList(this.plaintextCharacters);
	}

	public void addPlaintext(Plaintext plaintext) {
		needsEvaluation = true;

		plaintext.getId().setSolution(this);

		this.plaintextCharacters.add(plaintext);
	}

	public void insertPlaintext(int index, Plaintext plaintext) {
		needsEvaluation = true;

		this.plaintextCharacters.add(index, plaintext);
	}

	public void removePlaintext(Plaintext plaintext) {
		needsEvaluation = true;

		this.plaintextCharacters.remove(plaintext);
	}

	public void resetPlaintextCharacters() {
		needsEvaluation = true;

		this.plaintextCharacters = new ArrayList<Plaintext>();
	}

	public Cipher getCipher() {
		return cipher;
	}

	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * This should only be called for purposes of hydrating the entity. The
	 * createdDate should never be modified.
	 * 
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the needsEvaluation
	 */
	public boolean isNeedsEvaluation() {
		return needsEvaluation;
	}

	/**
	 * @param needsEvaluation
	 *            the needsEvaluation to set
	 */
	public void setNeedsEvaluation(boolean needsEvaluation) {
		this.needsEvaluation = needsEvaluation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * We must not use the Plaintext characters else we may run into a stack
	 * overflow. It shouldn't be necessary anyway since the id makes the
	 * solution unique.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adjacentMatchCount;
		result = prime * result + ((cipher == null) ? 0 : cipher.getId());
		result = prime * result + id.hashCode();
		result = prime * result + totalMatches;
		result = prime * result + uniqueMatches;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Solution other = (Solution) obj;
		if (adjacentMatchCount != other.adjacentMatchCount) {
			return false;
		}
		if (cipher == null) {
			if (other.cipher != null) {
				return false;
			}
		} else if (!cipher.equals(other.cipher)) {
			return false;
		}

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (totalMatches != other.totalMatches) {
			return false;
		}
		if (uniqueMatches != other.uniqueMatches) {
			return false;
		}

		if (this.plaintextCharacters == null) {
			if (other.plaintextCharacters != null) {
				return false;
			}
		} else if (other.plaintextCharacters == null) {
			return false;
		} else {
			if (this.plaintextCharacters.size() != other.plaintextCharacters.size()) {
				return false;
			}

			for (int i = 0; i < this.plaintextCharacters.size(); i++) {
				if (!this.plaintextCharacters.get(i).equals(other.plaintextCharacters.get(i))) {
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * Prints the properties of the solution and then outputs the entire
	 * plaintext list in block format.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Solution [id=" + id + ", cipherId=" + cipher.getId() + ", totalMatches="
				+ totalMatches + ", unique matches=" + uniqueMatches + ", adjacent matches="
				+ adjacentMatchCount + "]\n");

		/*
		 * Start at 1 instead of 0 so that the modulus function below isn't
		 * messed up.
		 */
		if (this.cipher != null) {
			Plaintext nextPlaintext = null;
			for (int i = 0; i < this.plaintextCharacters.size(); i++) {

				nextPlaintext = plaintextCharacters.get(i);

				// subtract 1 since the get method begins with 0
				if (nextPlaintext.getHasMatch()) {
					sb.append("[");
					sb.append(nextPlaintext.getValue());
					sb.append("]");
				} else {
					sb.append(" ");
					sb.append(nextPlaintext.getValue());
					sb.append(" ");
				}

				/*
				 * Print a newline if we are at the end of the row. Add 1 to the
				 * index so the modulus function doesn't break.
				 */
				if (((i + 1) % cipher.getColumns()) == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}

		return sb.toString();
	}
}
