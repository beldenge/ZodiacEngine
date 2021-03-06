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

package com.ciphertool.engine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.service.CipherSolutionService;
import com.ciphertool.engine.view.GenericCallback;
import com.ciphertool.genetics.GeneticAlgorithmStrategy;

public class ZodiacCipherSolutionController implements CipherSolutionController {
	private Logger					log	= LoggerFactory.getLogger(getClass());
	private CipherSolutionService	cipherSolutionService;

	@Override
	public void startServiceThread(final GeneticAlgorithmStrategy geneticAlgorithmStrategy, final GenericCallback uiCallback, final boolean debugMode) {
		if (cipherSolutionService.isRunning()) {
			log.info("Cipher solution service is already running.  Cannot start until current process completes.");
		} else {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					if (geneticAlgorithmStrategy == null) {
						throw new IllegalArgumentException("The geneticAlgorithmStrategy cannot be null.");
					}

					cipherSolutionService.begin(geneticAlgorithmStrategy, uiCallback, debugMode);
				}
			});

			serviceThread.start();
		}
	}

	@Override
	public void stopServiceThread(final boolean inDebugMode) {
		if (cipherSolutionService.isRunning()) {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					cipherSolutionService.endImmediately(inDebugMode);
				}
			});

			serviceThread.start();
		} else {
			log.info("Cipher solution service is already stopped.  Nothing to do.");
		}
	}

	@Override
	public void continueServiceThread() {
		if (cipherSolutionService.isRunning()) {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					cipherSolutionService.resume();
				}
			});

			serviceThread.start();
		} else {
			log.info("Cipher solution service is already stopped.  Nothing to do.");
		}
	}

	@Override
	public boolean isServiceThreadActive() {
		return cipherSolutionService.isRunning();
	}

	/**
	 * @param cipherSolutionService
	 *            the cipherSolutionService to set
	 */
	@Required
	public void setCipherSolutionService(CipherSolutionService cipherSolutionService) {
		this.cipherSolutionService = cipherSolutionService;
	}
}
