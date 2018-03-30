package org.integratedmodelling.kim.ui.elink;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.xtext.ui.editor.hyperlinking.DefaultHyperlinkDetector;

public class KimLinkDetector extends DefaultHyperlinkDetector {

	public static interface LinkOpenListener {
		void openLink(String text);
	}

	private static LinkOpenListener listener;

	public static void setListener(LinkOpenListener l) {
		listener = l;
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
	  
		int start = region.getOffset();
		int end = region.getOffset();
		try {
			if (textViewer.getDocument() != null && isConceptChar(textViewer.getDocument().getChar(start))) {
				while (isConceptChar(textViewer.getDocument().getChar(start))) {
					start--;
				}
				while (isConceptChar(textViewer.getDocument().getChar(end))) {
					end++;
				}
				if ((end - start) > 3) {

					start++;

					String cid = textViewer.getDocument().get(start, end - start);
					if ((Character.isLowerCase(cid.charAt(0)) && (cid.contains(":") || cid.contains("/"))) ||
					    (Character.isUpperCase(cid.charAt(0)) && !(cid.contains(":") || cid.contains("/")))
					// TODO add detection of local concept and non-semantic state
					// TODO use logics from superclass which can access resource
					) {
						final IRegion linkRegion = new Region(start, end - start);
						return new IHyperlink[] { new IHyperlink() {

							@Override
							public void open() {
								if (listener != null) {
									listener.openLink(cid);
								}
							}

							@Override
							public String getTypeLabel() {
								return "KIM.URN";
							}

							@Override
							public String getHyperlinkText() {
								return cid;
							}

							@Override
							public IRegion getHyperlinkRegion() {
								return linkRegion;
							}
						} };
					} else if (!cid.contains(":")) {

						/*
						 * find namespace
						 */

						if (Character.isUpperCase(cid.charAt(0))) {
							/*
							 * find concept definition
							 */
						} else {

							/*
							 * check for namespace
							 */

							/*
							 * check for non-semantic state model
							 */

							/*
							 * check for function
							 */
						}
					}
				}
			}
		} catch (BadLocationException e) {
			// move on
		}
		return super.detectHyperlinks(textViewer, region, canShowMultipleHyperlinks);
	}

	private boolean isConceptChar(char char1) {
		return char1 == ':' || char1 == '.' || char1 == '#' || char1 == '/' || char1 == '-' || char1 == '_' || Character.isLetter(char1)
				|| Character.isDigit(char1);
	}

}
