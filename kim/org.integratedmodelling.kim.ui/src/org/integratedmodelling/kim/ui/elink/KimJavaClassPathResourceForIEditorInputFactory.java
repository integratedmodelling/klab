package org.integratedmodelling.kim.ui.elink;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.JavaClassPathResourceForIEditorInputFactory;

public class KimJavaClassPathResourceForIEditorInputFactory extends JavaClassPathResourceForIEditorInputFactory {

	@Override
	protected Resource createResource(java.net.URI uri) {
		XtextResource resource = (XtextResource) super.createResource(uri);
		resource.setValidationDisabled(false);
		return resource;
	}
}
