package org.integratedmodelling.kim.ui.contentassist;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.builder.nature.NatureAddingEditorCallback;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.validation.AnnotationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.editor.validation.MarkerIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.ValidationJob;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.kim.utils.ResourceSorter;

import com.google.inject.Inject;

public class KimNatureAddingEditorCallback extends NatureAddingEditorCallback {

	@Inject
	private XtextResourceSet resourceSet;

	@Inject
	private IResourceValidator resourceValidator;

	@Inject
	private MarkerCreator markerCreator;

	@Inject
	private MarkerTypeProvider markerTypeProvider;

	@Inject
	private IssueResolutionProvider issueResolutionProvider;

	private boolean initialBuildDone;

	@Override
	public void afterCreatePartControl(XtextEditor editor) {
		super.afterCreatePartControl(editor);
		validate(editor);
	}

	@Override
	public void afterSetInput(XtextEditor xtextEditor) {
		super.afterSetInput(xtextEditor);
		if (!Kim.INSTANCE.initialBuildDone()) {
			/*
			 * Force a full build so that each known namespace is validated at least once
			 * and all concepts are known before we start.
			 */
			try {
				// il secondo lo taglia
				ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());

				// e il terzo gode
				// ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD,
				// new NullProgressMonitor());

			} catch (CoreException e) {
				// TODO log something
			}
			Kim.INSTANCE.setInitialBuildDone(true);
		}
	}

	@Override
	public void beforeSetInput(XtextEditor xtextEditor) {

		// TODO override from ws locations
		if (!Kim.INSTANCE.isLibraryInitialized()) {

			KimWorkspace lib = Kim.INSTANCE.getLibrary("worldview");
			if (lib != null) {

				lib.readProjects();

				ResourceSorter sorter = new ResourceSorter();
				// load and validate all external libraries. The concepts will be resolved in
				// the catalog and disappear
				// from the linker.
				resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
				for (File file : lib.getAllKimResources()) {
					sorter.add(resourceSet.getResource(URI.createFileURI(file.toString()), true));
				}
				for (Resource resource : sorter.getResources()) {
					List<Issue> issues = resourceValidator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
					for (Issue issue : issues) {
						if (issue.getSeverity() == Severity.ERROR && issue.isSyntaxError()) {
							/**
							 * FIXME this won't resolve the namespace references, for unknown reasons. The
							 * errors are not reported because of the isSyntaxError() condition. The
							 * topological sort makes the problem harmless.
							 */
							Kim.INSTANCE.reportLibraryError(issue);
						}
					}
				}
			}
			Kim.INSTANCE.setLibraryInitialized(true);

			// You need BOTH this and the one in afterCreatePartControl for dependencies to
			// be solved, I guess for
			// the same reason as before - linked namespaces are (this time) resolved, but
			// not validated in topological
			// order. Would be lots easier to find out how to have Xtext validate the
			// imported namespaces instead.
			try {
				// il primo solleva il pelo
				ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO log something
			}
		}

	}

	private void validate(XtextEditor xtextEditor) {

		if (xtextEditor == null) {
			return;
		}
		if (xtextEditor.getInternalSourceViewer() == null) {
			return;
		}
		IValidationIssueProcessor issueProcessor;
		IXtextDocument xtextDocument = xtextEditor.getDocument();
		IResource resource = xtextEditor.getResource();
		if (resource != null)
			issueProcessor = new MarkerIssueProcessor(resource, markerCreator, markerTypeProvider);
		else
			issueProcessor = new AnnotationIssueProcessor(xtextDocument,
					xtextEditor.getInternalSourceViewer().getAnnotationModel(), issueResolutionProvider);
		ValidationJob validationJob = new ValidationJob(resourceValidator, xtextDocument, issueProcessor,
				CheckMode.ALL);
		validationJob.schedule();
	}
}
