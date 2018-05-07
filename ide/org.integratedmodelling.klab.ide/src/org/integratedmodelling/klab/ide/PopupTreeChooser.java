/*******************************************************************************
 *  Copyright (C) 2007, 2015:
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
package org.integratedmodelling.klab.ide;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;

/**
 * Class for displaying a pop-up control in its own shell. The control behaves
 * similar to a pop-up menu, but is a composite so can contain arbitrary
 * controls.
 * 
 * <p>
 * <b>Sample Usage:</b>
 * 
 * <pre>
 * PopupComposite popup = new PopupComposite(getShell());
 * Text text = new Text(popup, SWT.BORDER);
 * popup.pack();
 * popup.show(shell.toDisplay(new Point(10, 10)));
 * </pre>
 * 
 * @author Kevin McGuinness
 */
public class PopupTreeChooser extends Composite {

	/**
	 * Style of the shell that will house the composite
	 */
	private static final int SHELL_STYLE = SWT.MODELESS | SWT.NO_TRIM | SWT.ON_TOP | SWT.BORDER;

	/**
	 * Shell that will house the composite
	 */
	private final Shell shell;
	private Tree tree;

	private TreeViewer treeViewer;

	/**
	 * Create a Pop-up composite with the default {@link SWT#BORDER} style.
	 * 
	 * @param parent
	 *            The parent shell.
	 * @wbp.parser.constructor
	 */
	public PopupTreeChooser(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider,
			Object content) {
		this(parent, SWT.BORDER, labelProvider, contentProvider, content);
	}

	/**
	 * Redefine to process a double-clicked selection. Default simply hides the
	 * panel. Call super after processing to preserve the behavior.
	 * 
	 * @param object
	 */
	protected void objectSelected(Object object) {
		hide();
	}

	/**
	 * Create a Pop-up composite. The default layout is a fill layout.
	 * 
	 * @param parent
	 *            The parent shell.
	 * @param style
	 *            The composite style.
	 */
	public PopupTreeChooser(Shell parent, int style, ILabelProvider labelProvider, ITreeContentProvider contentProvider,
			Object content) {
		super(new Shell(parent, SHELL_STYLE), style);
		shell = getShell();
		shell.setLayout(new FillLayout());
		;
		shell.addShellListener(new ActivationListener());
		setLayout(createLayout());
		shell.setMinimumSize(new Point(380, 420));

		this.treeViewer = new TreeViewer(this, SWT.BORDER);
		tree = treeViewer.getTree();
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		tree.setLinesVisible(true);
		treeViewer.setInput(content);
		tree.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				objectSelected(tree.getSelection()[0].getData());
			}
		});
	}

	public Tree getTree() {
		return tree;
	}

	/**
	 * Display the composite below the given tool item. The item will be sized
	 * such that it's width is at least the width of the given tool item.
	 * 
	 * @param bar
	 *            The tool bar.
	 * @param item
	 *            The tool item.
	 */
	public void showBelow(ToolBar bar, ToolItem item) {
		Rectangle r = item.getBounds();
		Point p = bar.toDisplay(new Point(r.x, r.y + r.height));
		setSize(computeSize(item));
		show(p);
	}

	/**
	 * Display the composite in its own shell at the given point.
	 * 
	 * @param pt
	 *            The point where the pop-up should appear.
	 */
	public void show(Point pt) {
		// Match shell and component sizes
		shell.setSize(getSize());
		if (pt != null) {
			shell.setLocation(pt);
		}
		/*
		 * TODO see if we want to make this a parameter to use from outside.
		 */
		treeViewer.expandToLevel(2);
		shell.open();
	}

	/**
	 * Display the pop-up where it was last displayed.
	 */
	public void show() {
		show(null);
	}

	/**
	 * Hide the pop-up.
	 */
	public void hide() {
		shell.setVisible(false);
	}

	/**
	 * Returns <code>true</code> if the shell is currently activated.
	 * 
	 * @return <code>true</code> if the shell is visible.
	 */
	public boolean isDisplayed() {
		return shell.isVisible();
	}

	/**
	 * Creates the default layout for the composite.
	 * 
	 * @return the default layout.
	 */
	private FillLayout createLayout() {
		FillLayout layout = new FillLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		return layout;
	}

	/**
	 * Computes the optimal size with respect to the given tool item.
	 * 
	 * @param item
	 *            The tool item.
	 * @return The optimal size.
	 */
	private Point computeSize(ToolItem item) {
		Point s2 = computeSize(item.getWidth(), SWT.DEFAULT);
		Point s1 = computeSize(SWT.DEFAULT, SWT.DEFAULT);
		return s1.x > s2.x ? s1 : s2;
	}

	/**
	 * Class that handles shell appearance and disappearance appropriately.
	 * Specifically, it hides the shell when it becomes de-activated (for
	 * example, when the user clicks on the parent shell). Also, there is a
	 * minimum delay which is enforced between showing and hiding the pop-up, to
	 * prevent undesirable behavior such as hiding and immediately re-displaying
	 * the pop-up when the user selects a button responsible for showing the
	 * tool item.
	 */
	private final class ActivationListener extends ShellAdapter {
		private static final int TIMEOUT = 500;
		private long time = -1;

		@Override
		public void shellDeactivated(ShellEvent e) {
			// Record time of event
			time = (e.time & 0xFFFFFFFFL);

			// Hide
			hide();
		}

		@Override
		public void shellActivated(ShellEvent e) {
			if (time > 0) {
				// Find elapsed time
				long elapsed = ((e.time & 0xFFFFFFFFL) - time);

				// If less than a timeout, don't activate
				if (elapsed < TIMEOUT) {
					hide();

					// Next activation event is fine
					time = -1;
				}
			}
		}
	}
}
