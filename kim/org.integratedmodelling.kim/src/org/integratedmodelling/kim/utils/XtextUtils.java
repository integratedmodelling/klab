package org.integratedmodelling.kim.utils;

import org.eclipse.xtend.expression.Resource;

public class XtextUtils {

	public String getNamespaceId(Resource resource) {
        // crazy logics to extract the actual namespace ID from the linked reference,
        // and while this works, the whole system still manages to not find references
        // when run standalone, so I'll just brute-force everything and skip the xtext
        // cross-reference mechanism.
//      EObject proxy = dio.getName();
//      /*
//       * the following is null if the NS has been already loaded, i.e. it's not a
//       * proxy anymore. TODO I guess there's a more idiomatic way to know if the
//       * object is a proxy or not.
//       */
//      String nsId = proxy instanceof Namespace ? ((Namespace) proxy).getName() : null;
//      URI proxyURI = ((InternalEObject) proxy).eProxyURI();
//      if (nsId == null && proxyURI != null) {
//          Triple<EObject, EReference, INode> data = ((LazyLinkingResource) resource).getEncoder()
//                  .decode(resource, proxyURI.fragment());
//          nsId = NodeModelUtils.getTokenText(data.getThird());
//      }
		return null;
	}
	
}
