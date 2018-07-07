/*******************************************************************************
 *  Copyright (C) 2007, 2016:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.clitool.console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.collections.ImmutableList;

/**
 * Command history array. Use like any list of strings but call append() to add
 * a line, which will bring any existing line to the front of the list instead
 * of adding another copy, and transparently persist the history to the history
 * file in .thinklab/client.history. Calling add() will throw an exception.
 * 
 * @author ferdinando.villa
 *
 */
public class CommandHistory extends ImmutableList<String> {

	List<String> history = new ArrayList<>();
	HashSet<String> commands = new HashSet<>();
	File historyFile;
	int currentPosition = -1;

	/**
	 * Return the current position (0 = most recent) or -1 if there is no history.
	 * 
	 * @return
	 */
	int getPosition() {
		return currentPosition;
	}

	public CommandHistory() {
		historyFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + "client-history.txt");
		if (historyFile.exists()) {
			readHistory();
		}
	}

	/**
	 * Pass the file name for the history (will be put in SHOME/.thinklab).
	 * 
	 * @param fileName
	 */
	public CommandHistory(String fileName) {
		historyFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + fileName);
		if (historyFile.exists()) {
			readHistory();
		}
	}

	private void readHistory() {
		try {
			history = FileUtils.readLines(historyFile);
			for (String h : history) {
				commands.add(h);
			}
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	private void writeHistory() {
		try {
			FileUtils.writeLines(historyFile, history);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}

	/**
	 * Add a line or bring it to the forefront if it already exists, saving the
	 * resulting history right away.
	 * 
	 * @param line
	 */
	public void append(String line) {

		line = line.trim();

		if (commands.contains(line)) {
			List<String> ll = new ArrayList<>();
			for (String s : history) {
				if (!s.equals(line)) {
					ll.add(s);
				}
			}
			history = ll;
		} else {
			commands.add(line);
		}
		history.add(0, line);
		currentPosition = -1;
		writeHistory();
	}

	@Override
	public int size() {
		return history.size();
	}

	@Override
	public boolean contains(Object o) {
		return history.contains(o);
	}

	@Override
	public Iterator<String> iterator() {
		return history.iterator();
	}

	@Override
	public Object[] toArray() {
		return history.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return history.toArray(a);
	}

	@Override
	public String get(int index) {
		return history.get(index);
	}

	public String getPrevious() {
		return currentPosition > 0 ? history.get(--currentPosition) : null;
	}

	public String getNext() {
		return currentPosition < (history.size() - 1) ? history.get(++currentPosition) : null;
	}

}
