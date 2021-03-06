package org.integratedmodelling.klab.ide.navigator.e3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.dnd.URLTransfer;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.resources.ResourceDropAdapterAssistant;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EFolder;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtil;

public class KlabDropAssistant extends ResourceDropAdapterAssistant {

	public KlabDropAssistant() {
	}

	@Override
	public boolean isSupportedType(TransferData aTransferType) {
		if (URLTransfer.getInstance().isSupportedType(aTransferType)) {
			// succeeds but doesn't let drop URLs anyway.
			return true;
		}
		return super.isSupportedType(aTransferType);
	}

	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		if (target instanceof EResourceFolder || target instanceof EResource) {
			return Status.OK_STATUS;
		} else if (target instanceof EFolder) {
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent event, Object target) {

		List<Object> eventData = new ArrayList<>();
		if (event.data instanceof IStructuredSelection) {
			eventData.addAll(((IStructuredSelection) event.data).toList());
		} else if (event.data instanceof String[]) {
			for (String s : ((String[]) event.data)) {
				eventData.add(s);
			}
		}

		if (eventData.size() > 1) {

			// TODO collect other items
			List<EResource> resources = new ArrayList<>();
			for (Object eventItem : eventData) {
				if (eventItem instanceof EResource) {
					resources.add((EResource) eventItem);
				}
			}

			if (target instanceof EResourceFolder && resources.size() > 0) {
				KlabNavigatorActions.copyResource(resources, (EResourceFolder) target,
						(event.detail & DND.DROP_COPY) == 0);
			}

		} else {

			// useless loop -- remove
			for (Object eventItem : eventData) {

				if (eventItem instanceof ENavigatorItem) {

					if (eventItem instanceof EResource && target instanceof EResourceFolder) {
						KlabNavigatorActions.copyResource(Collections.singleton((EResource) eventItem),
								(EResourceFolder) target, (event.detail & DND.DROP_COPY) == 0);
					}

				} else if ((target instanceof EResourceFolder || target instanceof EFolder)
						&& eventItem instanceof String) {

					if (StringUtil.containsAny(eventItem.toString(), StringUtil.WHITESPACE)) {
						Eclipse.INSTANCE.alert("Imported identifier " + eventItem
								+ " contains whitespace: please correct names before trying importing again.");
					} else {

						File file = new File(eventItem.toString());
						if (file.exists() && file.isFile()) {

							if (target instanceof EFolder) {
								File ftarget = new File(((EFolder)target).getFolder() + File.separator + MiscUtilities.getFileName(file));
								try {
									Files.copy(file.toPath(), ftarget.toPath(), StandardCopyOption.REPLACE_EXISTING);
									KlabNavigator.refresh();
								} catch (IOException e) {
									Eclipse.INSTANCE.handleException(e);
								}
							} else {
								if (Activator.engineMonitor().isRunning()) {
									Activator.session().importFileResource(file,
											((EResourceFolder) target).getEParent(EProject.class).getName());
								} else {
									Eclipse.INSTANCE.alert("You must be connected to an engine to import resources.");
								}
							}
						} else {
							/*
							 * Check for URL - either
							 */
						}
					}
				} else if (target instanceof EResource && eventItem instanceof String) {

					if (StringUtil.containsAny(eventItem.toString(), StringUtil.WHITESPACE)) {
						Eclipse.INSTANCE.alert("Imported identifier " + eventItem
								+ " contains whitespace: please correct names before trying importing again.");
					} else {

						File file = new File(eventItem.toString());
						if (file.exists() && file.isFile()) {
							if (Activator.engineMonitor().isRunning()) {
								Activator.session().importFileIntoResource(file, ((EResource) target));
							} else {
								Eclipse.INSTANCE.alert("You must be connected to an engine to import into resources.");
							}
						} else {
							/*
							 * Check for URL - either
							 */
						}
					}
				}
			}
		}
		return Status.OK_STATUS;

	}

}
