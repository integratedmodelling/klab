package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewComponent.Tree;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * SO there should be one of these in the knowledge search view to provide the
 * USER application (defaulting to a provided one) and nothing more, with a
 * button to edit it and one to reset to defaults.
 * <p>
 * THEN there should be a specialized k.Apps view for anything else, where the
 * "dropped" ones end up, with a chooser for the active ones and another for the
 * apps gathered in the workspace.
 * <p>
 * 
 * @author Ferd
 *
 */
public class AppView extends Composite {

	private Composite parent;
	private Map<String, Composite> containers = new HashMap<>();
	private Layout currentLayout;
	private Map<String, ViewComponent> components = new HashMap<>();
	private Map<String, StructuredViewer> viewers = new HashMap<>();

	private class TreeWrapper {

		Tree tree;

		public TreeWrapper(Tree tree) {
			this.tree = tree;
			for (int i = 0; i < tree.getValues().size(); i++) {
				tree.getValues().get(i).put("__IID", i + "");
			}
		}

		public Object[] getChildren(Object item) {

			List<Map<String, String>> ret = new ArrayList<>();
			if (item instanceof TreeWrapper) {
				for (Pair<Integer, Integer> link : tree.getLinks()) {
					if (link.getSecond() == tree.getRootId()) {
						ret.add(tree.getValues().get(link.getFirst()));
					}
				}
			} else {
				@SuppressWarnings("unchecked")
				int i = Integer.parseInt(((Map<String, String>) item).get("__IID"));
				for (Pair<Integer, Integer> link : tree.getLinks()) {
					if (link.getSecond() == i) {
						ret.add(tree.getValues().get(link.getFirst()));
					}
				}
			}
			return ret.toArray();
		}

		public Object getParent(Object parent) {

			if (parent instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, String> mparent = (Map<String, String>) parent;
				int i = Integer.parseInt(mparent.get("__IID"));
				if (i == tree.getRootId()) {
					return this;
				}
				for (Pair<Integer, Integer> link : tree.getLinks()) {
					if (link.getFirst() == i) {
						return tree.getValues().get(link.getSecond());
					}
				}
			}
			return new Object[] {};
		}
	}

	private class TreeLabelProvider implements ILabelProvider, IColorProvider, IFontProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return true;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Font getFont(Object element) {
			return null;
		}

		@Override
		public Color getForeground(Object element) {
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			return null;
		}

//		@Override
//		public Image getColumnImage(Object element, int columnIndex) {
//			return null;
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		public String getColumnText(Object element, int columnIndex) {
//			return null;
//		}

		@Override
		public Image getImage(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public String getText(Object element) {
			if (element instanceof Map) {
				return ((Map<String, String>) element).get("label");
			}
			return null;
		}

	}

	private class TreeContentProvider implements ITreeContentProvider {

		TreeWrapper tree;

		public TreeContentProvider(TreeWrapper tree) {
			this.tree = tree;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return tree.getChildren(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			return tree.getParent(element);
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

	}

	public AppView(Composite parent, int style) {
		super(parent, style);
		this.parent = parent;
		setLayout(gridLayout(1, true));
	}

	public AppView(Layout layout, Composite parent, int style) {
		super(parent, style);
		this.parent = parent;
		setLayout(gridLayout(1, true));
		setup(layout);
	}

	private GridLayout gridLayout(int cols, boolean equalWidth) {
		GridLayout ret = new GridLayout(cols, equalWidth);
		ret.marginWidth = 0;
		ret.verticalSpacing = 0;
		ret.marginHeight = 0;
		ret.horizontalSpacing = 2;
		return ret;
	}

	private Composite makeView(Layout view, Composite parent) {

		Composite ret = new Composite(parent, SWT.NONE);
		Composite header = null;
		Composite footer = null;
		Composite center = null;

		int ncols = 3;
		if (view.getLeftPanels().size() > 0) {
			ncols++;
		}
		if (view.getRightPanels().size() > 0) {
			ncols++;
		}

		ret.setLayout(gridLayout(1, true));

		if (view.getHeader() != null) {
			header = new Composite(ret, SWT.NONE);
			header.setLayout(gridLayout(view.getHeader().getComponents().size(), false));
			header.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		center = new Composite(ret, SWT.NONE);
		center.setLayout(gridLayout(ncols, false));
		center.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (view.getFooter() != null) {
			footer = new Composite(ret, SWT.NONE);
			footer.setLayout(gridLayout(view.getFooter().getComponents().size(), false));
			footer.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		Composite leftArea = null;
		Composite rightArea = null;
		Composite centerArea = null;

		if (view.getLeftPanels().size() > 0) {
			leftArea = makePanel(view.getLeftPanels(), center);
			leftArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		centerArea = makePanel(view.getPanels(), center);
		centerArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));

		if (view.getRightPanels().size() > 0) {
			rightArea = makePanel(view.getRightPanels(), center);
			rightArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		return ret;
	}

	private Composite makePanel(List<ViewPanel> panels, Composite parent) {

		Composite ret = new Composite(parent, SWT.NONE);
		ret.setLayout(gridLayout(1, false));

		if (panels.size() > 1) {

			TabFolder tabFolder = new TabFolder(ret, SWT.NONE);
			tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			for (ViewPanel panel : panels) {
				TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
				tbtmNewItem.setText(StringUtil.capitalize(panel.getName()));
				Composite innerFrame = new Composite(tabFolder, SWT.NONE);
				innerFrame.setLayout(gridLayout(1, false));
				tbtmNewItem.setControl(innerFrame);
				for (ViewComponent component : panel.getComponents()) {
					makeComponent(component, innerFrame);
				}
				innerFrame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
			}

		} else if (panels.size() > 0) {
			Composite innerFrame = new Composite(ret, SWT.NONE);
			innerFrame.setLayout(gridLayout(1, false));
			for (ViewComponent component : panels.get(0).getComponents()) {
				makeComponent(component, innerFrame);
			}
			innerFrame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		return ret;

	}

	/**
	 * TODO add:
	 * <ul>
	 * <li>A log table (add text lines or notifications, show last first);</li>
	 * <li>A pager for numbered subcomponents</li>
	 * <li>Link text</li>
	 * <li>A palette/bookmark component</li>
	 * </ul>
	 * 
	 * @param component
	 * @param parent
	 */
	private void makeComponent(ViewComponent component, Composite parent) {

		components.put(component.getId(), component);

		switch (component.getType()) {
		case CheckButton:
			break;
		case Combo:
			break;
		case Container:
		case MultiContainer:
		case Group:

			Composite group = component.getName() == null ? new Composite(parent, SWT.NONE)
					: new Group(parent, SWT.NONE);
			if (component.getName() != null) {
				((Group) group).setText(component.getName());
			}

			int tcols = isTable(component);
			if (tcols > 0) {
				group.setLayout(gridLayout(tcols, false));
				for (ViewComponent row : component.getComponents()) {
					for (int col = 0; col < row.getComponents().size(); col++) {
						makeComponent(row.getComponents().get(col), group);
					}
					for (int i = row.getComponents().size(); i < tcols; i++) {
						new Label(group, SWT.NONE);
					}
				}

			} else {
				group.setLayout(gridLayout(component.getComponents().size(), false));
				for (ViewComponent child : component.getComponents()) {
					makeComponent(child, group);
				}
			}
			group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			break;
		case Map:
			break;
		case Panel:
			makePanel(Collections.singletonList((ViewPanel) component), parent);
			break;
		case Label:
			Label label = new Label(parent, SWT.NONE);
			label.setText(component.getContent());
			label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
			break;
		case PushButton:
			Button button = new Button(parent, SWT.NONE);
			button.setText(component.getName() == null ? "Button" : component.getName());
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
							new ViewAction(component));
				}
			});
			button.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
			break;
		case RadioButton:
			break;
		case TextInput:
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			if (component.getContent() != null) {
				text.setText(component.getContent());
			}
			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
							new ViewAction(component, text.getText()));
				}
			});
			break;
		case Tree:
			TreeViewer viewer = new TreeViewer(parent, SWT.CHECK);
			viewers.put(component.getId(), viewer);
			viewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			viewer.setLabelProvider(new TreeLabelProvider());
			TreeWrapper tree = new TreeWrapper(component.getTree());
			viewer.setContentProvider(new TreeContentProvider(tree));
			viewer.setLabelProvider(new TreeLabelProvider());
			// TODO add needed listeners based on metadata and options
			viewer.setInput(tree);
			break;
		case Table:
			break;
		case Text:
			break;
		case TreeItem:
			break;
		case View:
			makeView((Layout) component, parent);
			break;
		default:
			break;
		}

	}

	/**
	 * If this group is a group of groups (representing a table) return the max
	 * number of items per row, otherwise return 0.
	 * 
	 * @param component
	 * @return
	 */
	private int isTable(ViewComponent component) {
		if (component.getType() == ViewComponent.Type.Group) {
			int ret = 0;
			for (ViewComponent child : component.getComponents()) {
				if (child.getType() != ViewComponent.Type.Group) {
					return 0;
				}
				if (ret < child.getComponents().size()) {
					ret = child.getComponents().size();
				}
			}
			return ret;
		}
		return 0;
	}

	public void setup(Layout layout) {

		if (this.currentLayout != null) {

			/*
			 * remove everything - TODO this isn't called recursively, probably needs a
			 * different way
			 */
			Display.getDefault().asyncExec(() -> {
				for (Control control : this.getChildren()) {
					control.dispose();
				}
			});
		}

		this.currentLayout = layout;
		this.containers.clear();
		this.components.clear();

		Display.getDefault().asyncExec(() -> {
			refreshView();
		});
	}

	private void refreshView() {
		this.setLayout(gridLayout(1, true));
		Composite app = makeView(this.currentLayout, this);
		app.setLayout(gridLayout(1, true));
		app.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// parent.setSize(this.view.get);
		parent.pack();
		parent.layout(true);
	}

	public synchronized void addWidget(IMessage message) {
		ViewComponent component = message.getPayload(ViewComponent.class);
		if (component.getType() == ViewComponent.Type.Alert) {
			Eclipse.INSTANCE.alert(component.getContent());
		} else if (component.getType() == ViewComponent.Type.Alert) {
			boolean choice = Eclipse.INSTANCE.confirm(component.getContent());
			Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
					new ViewAction(component, choice));
		} else if (component.getParentId() != null && this.containers.containsKey(component.getParentId())) {
			Display.getDefault().asyncExec(() -> {
				makeComponent(component, this.containers.get(component.getParentId()));
				refreshView();
			});

		} else {
			System.err.println("INTERNAL: got widget outside of known container");
		}

		// TODO parent must be a known container

		// save message ID for responding when interacted with
//		widget.getData().put(MESSAGE_ID_KEY, message.getId());
//		this.widgets.add(widget);
//		Display.getDefault().asyncExec(() -> refresh());
	}

}
