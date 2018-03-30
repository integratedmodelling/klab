package org.integratedmodelling.kim.ui.elink;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.nodemodel.impl.CompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;

public class KimHyperlinkHelper extends	 HyperlinkHelper {

	@Override
	public void createHyperlinksByOffset(XtextResource resource, int offset,
			IHyperlinkAcceptor acceptor) {


		EObjectAtOffsetHelper eObjectAtOffsetHelper = new EObjectAtOffsetHelper();
		EObject eObject = eObjectAtOffsetHelper.resolveElementAt(resource, offset);

//		if (eObject instanceof variableName) {
//
//			variableName variableName = (variableName) eObject;
//			CompositeNode adapter = (CompositeNode) NodeModelUtils.getNode(variableName);
//
//			Region region = new Region(adapter.getOffset(), adapter.getLength());
//			
//			final URIConverter uriConverter = resource.getResourceSet().getURIConverter();
//			final String hyperlinkText = "Variable";
//			
//			final URI uri = EcoreUtil.getURI( *** );
//			final URI normalized = uri.isPlatformResource() ? uri : uriConverter.normalize(uri);
//
//			XtextHyperlink result = new XtextHyperlink(); // Does not show link with getHyperlinkProvider().get()
//			result.setHyperlinkRegion(region);
//			result.setURI(normalized);
//			result.setHyperlinkText(hyperlinkText);
//			acceptor.accept(result);
//		}
		
		super.createHyperlinksByOffset(resource, offset, acceptor);
	}
	
}
