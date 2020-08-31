package org.integratedmodelling.klab.ide.kim;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.common.Prototype;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.organizer.Organizer;
import org.integratedmodelling.klab.rest.BehaviorReference;
import org.integratedmodelling.klab.rest.BehaviorReference.Action;
import org.integratedmodelling.klab.utils.Path;

/**
 * A singleton holding all synchronized k.IM-relevant data that come from the
 * engine, such as function prototypes and URN resolution data. These can be
 * read with Jackson as synchronized file catalogs.
 * 
 * @author ferdinando.villa
 *
 */
public enum KimData {

	INSTANCE;

	private FileCatalog<IPrototype> prototypes;
	private FileCatalog<IPrototype> annotations;
	private FileCatalog<BehaviorReference> behaviors;
	private Organizer bookmarks = new Organizer("Bookmarks");
	private File bookmarkFile;

	KimData() {

		File protoFile = new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator
				+ "language" + File.separator + "prototypes.json");
		File annotFile = new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator
				+ "language" + File.separator + "annotations.json");
		File behaviorFile = new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator
				+ "language" + File.separator + "behaviors.json");
		prototypes = new FileCatalog<IPrototype>(protoFile, IPrototype.class, Prototype.class);
		annotations = new FileCatalog<IPrototype>(annotFile, IPrototype.class, Prototype.class);
		behaviors = new FileCatalog<BehaviorReference>(behaviorFile, BehaviorReference.class, BehaviorReference.class);

		/*
		 * fill in the catalog in the parser helper
		 */
		KActors.INSTANCE.getBehaviorManifest().putAll(behaviors);

		this.bookmarkFile = new File(
				System.getProperty("user.home") + File.separator + ".klab" + File.separator + "bookmarks.json");
		if (this.bookmarkFile.exists()) {
			this.bookmarks = JsonUtils.load(this.bookmarkFile, Organizer.class);
		} else {
			this.bookmarks.setDescription(
					"This is a configurable palette where you can drop concepts, models and observations for future reference. "
							+ "You can also create folders to improve organization. Your palette is saved in the configuration every"
							+ " time you make a change and is shared with the Explorer. You can export palettes to the network and"
							+ " share them with others. You can save palettes with names and switch between them as you please.");
		}
	}

	public IPrototype getFunctionPrototype(String name) {
		return prototypes.get(name);
	}

	public IPrototype getAnnotationPrototype(String name) {
		return annotations.get(name);
	}

	public BehaviorReference getBehavior(String name) {
		return behaviors.get(name);
	}

	public IKimNamespace getNamespace(IFile file) {
		String nsId = Eclipse.INSTANCE.getNamespaceIdFromIFile(file);
		return nsId == null ? null : Kim.INSTANCE.getNamespace(nsId);
	}

	public ENavigatorItem findObjectAt(int caret, IKimNamespace namespace) {

		Object focus = namespace;
		ENavigatorItem ret = null;

		for (IKimStatement child : namespace.getAllStatements()) {
			if (child.getFirstCharOffset() > caret) {
				break;
			}
			focus = child;
		}

		if (focus != null) {

			ret = EKimObject.create(focus);
			if (ret != null) {
				// shouldn't happen but does
				ENavigatorItem current = ret;
				while (true) {
					Object kimParent = focus instanceof IKimProject ? null
							: (focus instanceof IKimNamespace ? ((IKimNamespace) focus).getProject()
									: ((IKimStatement) focus).getParent());
					if (kimParent == null) {
						break;
					}
					ENavigatorItem parent = EKimObject.create(kimParent);
					if (parent == null) {
						break;
					}
					current.setParent(parent);
					current = parent;
					focus = kimParent;
				}
			}
		}
		return ret;
	}

	public Organizer getBookmarks() {
		return bookmarks;
	}

	Map<String, Set<BehaviorReference>> actionCatalog = null;

	public Set<BehaviorReference> getBehaviorFor(String call) {
		
		if (call.contains(".")) {
			BehaviorReference ref = behaviors.get(Path.getFirst(call, "."));
			if (ref != null) {
				return Collections.singleton(ref);
			}
		}

		if (actionCatalog == null) {
			actionCatalog = Collections.synchronizedMap(new HashMap<>());
			for (BehaviorReference behavior : behaviors.values()) {
				for (Action action : behavior.getActions()) {
					if (!actionCatalog.containsKey(action.getName())) {
						actionCatalog.put(action.getName(), new HashSet<>());
					}
					actionCatalog.get(action.getName()).add(behavior);
				}
			}
		}

		return actionCatalog.get(call);
	}

}
