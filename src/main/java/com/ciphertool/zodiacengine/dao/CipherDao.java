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

package com.ciphertool.zodiacengine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciphertool.zodiacengine.entities.Cipher;

@Component
public class CipherDao {
	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String nameParameter = "name";

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Cipher findByCipherName(String name) {
		Session session = sessionFactory.getCurrentSession();

		Cipher cipher = (Cipher) session.createQuery(
				"from Cipher where name = " + separator + nameParameter).setParameter(
				nameParameter, name).uniqueResult();

		return cipher;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Cipher> findAll() {
		Session session = sessionFactory.getCurrentSession();

		@SuppressWarnings("unchecked")
		List<Cipher> ciphers = session.createQuery("from Cipher").list();

		return ciphers;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
