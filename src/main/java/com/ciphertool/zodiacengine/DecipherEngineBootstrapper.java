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

package com.ciphertool.zodiacengine;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DecipherEngineBootstrapper {
	private static Logger log = Logger.getLogger(DecipherEngineBootstrapper.class);

	@SuppressWarnings("unused")
	private static ApplicationContext context;

	public static void main(String[] args) {
		initializeContext();
	}

	/**
	 * Spins up the Spring application context
	 */
	protected static void initializeContext() {
		log.info("Starting Spring application context");

		long start = System.currentTimeMillis();

		context = new ClassPathXmlApplicationContext("bootstrapContext.xml");

		log.info("Spring application context started successfully in " + (System.currentTimeMillis() - start) + "ms.");
	}
}