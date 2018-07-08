/*
 * Copyright (c) 2010 3l33t Software Developers, L.L.C.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.integratedmodelling.klab.clitool.contrib.console;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.clitool.console.CommandHistory;

/**
 * DragonConsoleFrame is designed to act as a simple interface if the programmer
 * just wishes to create a single JFrame for the console. This class does
 * everything the JFrame controls DragonConsole class used to do before it was
 * altered from an extension from JFrame to JPanel. The Default Constructor for
 * DragonConsoleFrame creates a basic DragonConsole and adds it to the JFrame
 * and the other Constructor takes in a pre-initialized DragonConsole and adds
 * it to the JFrame.
 * 
 * @author Brandon E Buck
 */
public class DragonConsoleFrame extends JFrame {

	private static final long serialVersionUID = 8851272088780841859L;
	public DragonConsole console;

	/**
	 * Default Constructor that uses a default title and creates a basic console.
	 * This Constructor makes a basic title which is "DragonConsole " plus the
	 * version number of the Console. It also creates a basic Console and adds it to
	 * the JFrame.
	 * 
	 * @param title
	 * @param useInlineInput
	 * @param history
	 */
	public DragonConsoleFrame(String title, boolean useInlineInput, CommandHistory history) {
		this.console = new DragonConsole(useInlineInput, history);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		this.setResizable(true);
		this.add(console);
		this.pack();
		console.setInputFocus();

		try {
			this.setIconImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("icons/logo_white_64.jpg")));
		} catch (Throwable e) {
			// OK, next time.
			Logging.INSTANCE.warn(e);
		}

		this.centerWindow();
	}

	/**
	 * Centers the window based on screen size and window size. Determines the
	 * Screen Size and then centers the Window. This can cause funky problems on
	 * multi-screen display systems.
	 */
	private void centerWindow() {
		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = defaultToolkit.getScreenSize();
		this.setLocation((int) ((screenSize.getWidth() / 2) - (this.getWidth() / 2)),
				(int) ((screenSize.getHeight() / 2) - (this.getHeight() / 2)));
	}
}
