package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.opal.header.Header;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.nebula.widgets.richtext.RichTextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.ui.AppView.GroupLayout.Type;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewComponent.Tree;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(AppView.class);
    
	private Composite parent;
	private Map<String, Composite> containers = new HashMap<>();
	private Layout currentLayout;
	private Map<String, ViewComponent> components = new HashMap<>();
	private Map<String, StructuredViewer> viewers = new HashMap<>();
	private Map<String, Control> reactors = new HashMap<>();

	private static Set<ViewComponent.Type> containerTypes;

	static {
		containerTypes = EnumSet.of(ViewComponent.Type.Container, ViewComponent.Type.Group, ViewComponent.Type.Panel,
				ViewComponent.Type.MultiContainer);
	}

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
				for (Pair<String, String> link : tree.getLinks()) {
					if (link.getSecond().equals(tree.getRootId())) {
						ret.add(tree.getValues().get(link.getFirst()));
					}
				}
			} else {
				@SuppressWarnings("unchecked")
				String i = ((Map<String, String>) item).get("__IID");
				for (Pair<String, String> link : tree.getLinks()) {
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
				String i = mparent.get("__IID");
				if (i.equals(tree.getRootId())) {
					return this;
				}
				for (Pair<String, String> link : tree.getLinks()) {
					if (link.getFirst().equals(i)) {
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
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
//				Point ps = parent.getParent().getSize();
//				if (getSize().x < ps.x || getSize().y < ps.y) {
//					setSize(ps);
//				}
			}
		});
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

		int ncols = 0;
		if (view.getLeftPanels().size() > 0) {
			ncols++;
		}
		if (view.getRightPanels().size() > 0) {
			ncols++;
		}
		if (view.getPanels().size() > 0) {
			ncols++;
		}

		ret.setLayout(gridLayout(1, true));

		/**
		 * We give it a default header unless one is specified. TODO limit this to the
		 * root layout in case of composite panels.
		 */
		if (view.getHeader() != null) {
			header = new Composite(ret, SWT.NONE);
			header.setLayout(gridLayout(view.getHeader().getComponents().size(), false));
			header.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		} else if (view.getLabel() != null && view.getDescription() != null) {
			final Header description = new Header(ret, SWT.NONE);
			description.setTitle(view.getLabel());
			Image logo = null;
			if (view.getLogo() != null && view.getProjectId() != null) {
				logo = Eclipse.INSTANCE.getImageResource(view.getProjectId(), view.getLogo());
			}
			if (logo == null) {
				logo = ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/action-item-login.png");
			}
			description.setImage(logo);
			description.setDescription(view.getDescription());
			description.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, ncols == 0 ? 1 : ncols, 1));
		}

		if (ncols > 0) {
			center = new Composite(ret, SWT.NONE);
			center.setLayout(gridLayout(ncols, false));
			center.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			Composite leftArea = null;
			Composite rightArea = null;
			Composite centerArea = null;

			if (view.getLeftPanels().size() > 0) {
				leftArea = makePanel(view.getLeftPanels(), center);
				leftArea.setLayoutData(new GridData(ncols == 1 ? SWT.FILL : SWT.LEFT, SWT.TOP, true, true));
			}

			if (view.getPanels().size() > 0) {
				centerArea = makePanel(view.getPanels(), center);
				centerArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
			}

			if (view.getRightPanels().size() > 0) {
				rightArea = makePanel(view.getRightPanels(), center);
				rightArea.setLayoutData(new GridData(ncols == 1 ? SWT.FILL : SWT.LEFT, SWT.TOP, true, true));
			}

			if (view.getFooter() != null) {
				footer = new Composite(ret, SWT.NONE);
				footer.setLayout(gridLayout(view.getFooter().getComponents().size(), false));
				footer.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
			}
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
					makeComponent(component, innerFrame, panel.getAttributes());
				}
				innerFrame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
			}

		} else if (panels.size() > 0) {
			Composite innerFrame = new Composite(ret, SWT.NONE);
			innerFrame.setLayout(gridLayout(1, false));
			for (ViewComponent component : panels.get(0).getComponents()) {
				makeComponent(component, innerFrame, panels.get(0).getAttributes());
			}
			innerFrame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		}

		return ret;

	}

	/**
	 * TODO add:
	 * <ul>
	 * <li>A log table (add text lines or notifications, show last first);</li>
	 * <li>A pager for numbered subcomponents, firing the index when advanced</li>
	 * <li>Link text</li>
	 * <li>A palette/bookmark component</li>
	 * <li>Image from the project resources (URL from engine)</li>
	 * </ul>
	 * 
	 * TODO use parent component for layout defaults (set in the annotation)
	 * 
	 * @param component
	 * @param parent
	 */
	private void makeComponent(ViewComponent component, Composite parent, Map<String, String> defaults) {

		components.put(component.getId(), component);

		switch (component.getType()) {
		case CheckButton:
			Button checkbutton = new Button(parent, SWT.CHECK);
			checkbutton.setText(component.getName() == null ? "Button" : component.getName());
			checkbutton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
							new ViewAction(component, checkbutton.getSelection()));
				}
			});
			if (getTag(component) != null) {
				reactors.put(getTag(component), checkbutton);
			}
			setAttributes(checkbutton, component.getAttributes());
			break;
		case Combo:
			break;
		case Container:
		case MultiContainer:
		case Group:
			makeContainer(component, parent, defaults);
			break;
		case Map:
			break;
		case Panel:
			makePanel(Collections.singletonList((ViewPanel) component), parent);
			break;
		case Label:
			Label label = new Label(parent, SWT.NONE);
			label.setText(component.getContent());
			label.setLayoutData(getGridData(component, SWT.LEFT, SWT.CENTER, false, false, defaults));
			if (getTag(component) != null) {
				reactors.put(getTag(component), label);
			}
			setAttributes(label, component.getAttributes());
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
			if (getTag(component) != null) {
				reactors.put(getTag(component), button);
			}
			setAttributes(button, component.getAttributes());
			button.setLayoutData(getGridData(component, SWT.LEFT, SWT.TOP, false, false, defaults));
			break;
		case RadioButton:
			Button radiobutton = new Button(parent, SWT.RADIO);
			radiobutton.setText(component.getName() == null ? "Button" : component.getName());
			radiobutton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.ViewAction,
							new ViewAction(component, radiobutton.getSelection()));
				}
			});
			if (getTag(component) != null) {
				reactors.put(getTag(component), radiobutton);
			}
			setAttributes(radiobutton, component.getAttributes());
			break;
		case TextInput:
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(getGridData(component, SWT.FILL, SWT.TOP, true, false, defaults));
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
			if (getTag(component) != null) {
				reactors.put(getTag(component), text);
			}
			setAttributes(text, component.getAttributes());
			break;
		case Tree:
			TreeViewer viewer = new TreeViewer(parent, getSWTFlags(component));
			viewers.put(component.getId(), viewer);
			viewer.getTree().setLayoutData(getGridData(component, SWT.FILL, SWT.FILL, true, false, defaults));
			viewer.setLabelProvider(new TreeLabelProvider());
			TreeWrapper tree = new TreeWrapper(component.getTree());
			viewer.setContentProvider(new TreeContentProvider(tree));
			viewer.setLabelProvider(new TreeLabelProvider());
			viewer.getTree().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					logger.debug("Selection " + e);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			viewer.setInput(tree);
			if (getTag(component) != null) {
				reactors.put(getTag(component), viewer.getTree());
			}
			setAttributes(viewer.getTree(), component.getAttributes());
			break;
		case Table:
			break;
		case Text:
			RichTextViewer textViewer = new RichTextViewer(parent, SWT.WRAP);
			textViewer.setText(component.getContent());
			textViewer.setLayoutData(getGridData(component, SWT.FILL, SWT.CENTER, true, false, defaults));
			if (getTag(component) != null) {
				reactors.put(getTag(component), textViewer);
			}
			setAttributes(textViewer, component.getAttributes());
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

	private String getTag(ViewComponent component) {
		return component.getAttributes().get("tag");
	}

	private int getSWTFlags(ViewComponent component) {

		int ret = SWT.NONE;
		switch (component.getType()) {
		case Alert:
			break;
		case CheckButton:
			break;
		case Combo:
			break;
		case Confirm:
			break;
		case Container:
			break;
		case Group:
			break;
		case Label:
			break;
		case Map:
			break;
		case MultiContainer:
			break;
		case Notification:
			break;
		case Panel:
			break;
		case PushButton:
			break;
		case RadioButton:
			break;
		case Table:
			break;
		case Text:
			break;
		case TextInput:
			String border = component.getAttributes().get("border");
			if (border == null) {
				// default
				ret |= SWT.BORDER;
			}
			break;
		case Tree:
			if (component.getAttributes().containsKey("check")) {
				ret |= SWT.CHECK;
			} else if (component.getAttributes().containsKey("radio")) {
				ret |= SWT.RADIO;
			}
			break;
		case TreeItem:
			break;
		case View:
			break;
		default:
			break;
		}

		return ret;
	}

	private Control makeContainer(ViewComponent component, Composite parent, Map<String, String> defaults) {

		GroupLayout gl = classifyGroup(component);

		// first switch, make the container
		Control container = null;
		switch (gl.type) {
		case Table:
		case Hbox:
		case Vbox:
			container = gl.name == null ? new Composite(parent, SWT.NONE) : new Group(parent, SWT.NONE);
			((Composite) container).setLayoutData(getGridData(component, SWT.LEFT, SWT.TOP, false, false, defaults));
			if (gl.name != null) {
				((Group) container).setText(gl.name);
			}
			((Composite) container).setLayout(new GridLayout(gl.nCols, gl.equal));
			if (gl.type == Type.Table) {
				for (ViewComponent row : component.getComponents()) {
					int reached = 0;
					for (int col = 0; col < row.getComponents().size(); col++) {
						makeComponent(row.getComponents().get(col), (Composite) container, defaults);
						reached += computeColumnSize(row.getComponents().get(col));
					}
					for (int i = reached; i < gl.nCols; i++) {
						new Label((Composite) container, SWT.NONE);
					}
				}
			} else {
				for (ViewComponent child : component.getComponents()) {
					makeComponent(child, (Composite) container, defaults);
				}
			}
			break;
		case Pager:
			container = new Pager(parent, SWT.NONE);
			for (ViewComponent panel : component.getComponents()) {
				// TODO
			}
			break;
		case Shelf:
			container = new PShelf(parent, SWT.NONE);
			((PShelf) container).setLayoutData(getGridData(component, SWT.LEFT, SWT.TOP, false, false, defaults));
			((PShelf) container).setRenderer(new RedmondShelfRenderer());
			int n = 1;
			for (ViewComponent panel : component.getComponents()) {
				PShelfItem item = new PShelfItem((PShelf) container, SWT.NONE);
				item.getBody().setLayout(new GridLayout(1, false));
				item.setText(panel.getName() == null ? ("Panel " + n) : panel.getName());
				makeComponent(panel, item.getBody(), defaults);
				n++;
			}
			break;
		case Tabs:
			container = new CTabFolder(parent, SWT.NONE);
			for (ViewComponent panel : component.getComponents()) {

			}
			break;
		}

		return container;
	}

	/**
	 * View messages. Uses metadata for layout control
	 * <p>
	 * Metadata this far:
	 * <p>
	 * No argument:
	 * <ul>
	 * <li>:right, :left, :top, :bottom</li>
	 * <li>:hfill, :vfill, :fill</li>
	 * <li>:disabled {!disabled for completeness}</li>
	 * <li>:hidden {!hidden}</li>
	 * <li>:hbox :vbox :pager :shelf :tabs [:table is the default] to specify the
	 * type of arrangement in a group</li>
	 * </ul>
	 * <p>
	 * With argument:
	 * <ul>
	 * <li>:cspan, :rspan (columns and rows spanned in grid)</li>
	 * <li>:fg, :bg (color name for now?)</li>
	 * <li>:bstyle {?HTML solid dotted}</li>
	 * <li>:bwidth <n> border width (always solid for now)</li>
	 * <li>:fstyle {bold|italic|strike|normal}</li>
	 * <li>:fsize <n></li>
	 * <li>:symbol {font awesome char code}</li>
	 * <li>:class (CSS class)</li>
	 * <li>:wmin, :hmin (minimum height and width)</li>
	 * <li>:cols, :equal for panel grids</li>
	 * </ul>
	 * 
	 */
	private GridData getGridData(ViewComponent component, int fill, int center, boolean grabX, boolean grabY,
			Map<String, String> defaults) {

		int cspan = 1;
		int rspan = 1;
		int wmin = -1;
		int hmin = -1;

		if (isContainer(component)) {
			for (String key : defaults.keySet()) {
				if (!component.getAttributes().containsKey(key)) {
					component.getAttributes().put(key, defaults.get(key));
				}
			}
		}

		/*
		 * TODO these should probably only apply to containers; in other types of
		 * components these should define the SWT flags instead.
		 */
		for (String attribute : component.getAttributes().keySet()) {
			switch (attribute) {
			case "right":
				fill = SWT.RIGHT;
				break;
			case "left":
				fill = SWT.LEFT;
				break;
			case "top":
				center = SWT.TOP;
				break;
			case "bottom":
				center = SWT.BOTTOM;
				break;
			case "hfill":
				fill = SWT.FILL;
				break;
			case "vfill":
				center = SWT.FILL;
				break;
			case "fill":
				center = SWT.FILL;
				fill = SWT.FILL;
				break;
			case "cspan":
				cspan = Integer.parseInt(component.getAttributes().get(attribute));
				break;
			case "rspan":
				rspan = Integer.parseInt(component.getAttributes().get(attribute));
				break;
			case "wmin":
			case "width":
				wmin = Integer.parseInt(component.getAttributes().get(attribute));
				break;
			case "hmin":
			case "height":
				hmin = Integer.parseInt(component.getAttributes().get(attribute));
				break;
			}
		}
		GridData ret = new GridData(fill, center, grabX, grabY);
		ret.horizontalSpan = cspan;
		ret.verticalSpan = rspan;
		if (wmin >= 0) {
			ret.widthHint = wmin;
		}
		if (hmin >= 0) {
			ret.heightHint = hmin;
		}
		return ret;
	}

	private boolean isContainer(ViewComponent component) {
		return containerTypes.contains(component.getType());
	}

	/**
	 * If this group is a group of groups (representing a table) return the max
	 * number of items per row, otherwise return 0.
	 * 
	 * @param component
	 * @return
	 */
	private int computeColumns(ViewComponent component) {
		if (component.getType() == ViewComponent.Type.Group) {
			int ret = 0;
			for (ViewComponent child : component.getComponents()) {
				if (child.getType() != ViewComponent.Type.Group) {
					return 0;
				}
				int cols = computeColumnSize(child);
				if (ret < cols) {
					ret = cols;
				}
			}
			return ret;
		}
		return 0;
	}

	private int computeColumnSize(ViewComponent child) {
		int ret = 0;
		for (ViewComponent component : child.getComponents()) {
			ret += getComponentSpan(component);
		}
		return ret == 0 ? 1 : ret;
	}

	private int getComponentSpan(ViewComponent component) {
		int ret = 1;
		String sspan = component.getAttributes().get("cspan");
		if (sspan != null) {
			ret = Integer.parseInt(sspan);
		}
		return ret;
	}

	static class GroupLayout {

		enum Type {
			Shelf, Vbox, Hbox, Table, Tabs, Pager
		}

		int nRows = 1;
		int nCols = 1;
		Type type = Type.Hbox;
		String name = null;
		boolean equal; // equal column width
	}
	
	/**
	 * Group may have tags that specify the layout or not. If they don't have any of
	 * :hbox, :vbox or :shelf (for now), they will be arranged as grids with all the
	 * element in a row unless the components are all groups. In that case, the
	 * groups represent rows and the columns will be filled with the max number of
	 * column occupied in the largest row, taking into account also any :cspan tag
	 * in the inner components.
	 * 
	 * @param component
	 */
	private GroupLayout classifyGroup(ViewComponent component) {

		GroupLayout ret = new GroupLayout();

		ret.type = Type.Table;

		if (component.getAttributes().containsKey("shelf")) {
			ret.type = Type.Shelf;
		} else if (component.getAttributes().containsKey("hbox")) {
			ret.type = Type.Hbox;
			ret.nCols = component.getComponents().size();
		} else if (component.getAttributes().containsKey("vbox")) {
			ret.type = Type.Vbox;
			ret.nRows = component.getComponents().size();
		} else if (component.getAttributes().containsKey("tabs")) {
			ret.type = Type.Tabs;
		} else if (component.getAttributes().containsKey("pager")) {
			ret.type = Type.Pager;
		}

		ret.name = component.getName();
		ret.equal = component.getAttributes().containsKey("equal");

		if (ret.type == Type.Table) {
			ret.nCols = computeColumns(component);
			ret.nRows = component.getComponents().size();
			if (ret.nCols == 0) {
				// not a table, make it a hbox
				ret.type = Type.Hbox;
				ret.nCols = ret.nRows;
				ret.nRows = 1;
			}
		}

		return ret;
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
		this.reactors.clear();

		Display.getDefault().asyncExec(() -> {
			refreshView();
		});
	}

	private void setAttributes(Control control, Map<String, String> attributes) {
		for (String attribute : attributes.keySet()) {
			switch (attribute) {
			
			}
		}
	}
	
	private void refreshView() {
		Point ps = getParent().getParent().getSize();
		this.setLayout(gridLayout(1, true));
		Composite app = makeView(this.currentLayout, this);
		app.setLayout(gridLayout(1, true));
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		layoutData.heightHint = ps.y;
		layoutData.widthHint = ps.x;
		app.setLayoutData(layoutData);
		this.parent.setSize(ps);
		this.setSize(ps);
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
				makeComponent(component, this.containers.get(component.getParentId()), component.getAttributes());
				refreshView();
			});

		} else {
			logger.error("INTERNAL: got widget outside of known container");
		}

		// TODO parent must be a known container

		// save message ID for responding when interacted with
//		widget.getData().put(MESSAGE_ID_KEY, message.getId());
//		this.widgets.add(widget);
//		Display.getDefault().asyncExec(() -> refresh());
	}

	public void updateWidget(ViewAction component) {
		
		Control control = reactors.get(component.getComponentTag());
		if (control != null) {
//			switch (component.getOperation()) {
//			case Enable:
//				control.setEnabled(component.isBooleanValue());
//				break;
//			case Hide:
//				control.setVisible(!component.isBooleanValue());
//				break;
//			case Update:
				if (control instanceof Label) {
					// TODO images
					if (component.getStringValue() != null) {
						((Label)control).setText(component.getStringValue());
					}
				}
				if (control instanceof Text) {
					if (component.getStringValue() != null) {
						((Text)control).setText(component.getStringValue());
					}
				}
				if (component.getData() != null) {
					setAttributes(control, component.getData());
				}
//				break;
//			case UserAction:
//				break;
//			default:
//				break;
//
//			}
		}
	}

}
