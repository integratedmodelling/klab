package org.integratedmodelling.kactors.ui.contentassist;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class KactorsHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String KNOWN_URN_ID = "knownurn";
	public static final String UNKNOWN_URN_ID = "unknownurn";
	public static final String LIVE_URN_ID = "liveurn";
	public static final String DEAD_URN_ID = "deadurn";
	public static final String UNAUTHORIZED_URN_ID = "unauthorizedurn";
	public static final String KNOWN_FUNCTION_ID = "knownfunction";
	public static final String KNOWN_ANNOTATION_ID = "knownannotation";
	public static final String UNKNOWN_ANNOTATION_ID = "unknownannotation";
	public static final String VOID_MODEL_ID = "voidmodel";
	public static final String KEY_ID = "key";
	public static final String DANGER_ID = "danger";
	public static final String VERSION_NUMBER_ID = "versionNumber";
	public static final String DEFINITION_ID = "definition";
	public static final String CODE_ID = "code";

	/**
	 * TODO FIXME remove most of these, copied from k.IM
	 */
	public static final String QUALITY_ID = "quality";
	public static final String SUBJECT_ID = "subject";
	public static final String EVENT_ID = "event";
	public static final String PROCESS_ID = "process";
	public static final String RELATIONSHIP_ID = "relationship";
	public static final String CONFIGURATION_ID = "configuration";
	public static final String TRAIT_ID = "trait";
	public static final String ROLE_ID = "role";
	public static final String MARKDOWN_ID = "markdown";
	public static final String EXTENT_ID = "extent";
	public static final String ABSTRACT_QUALITY_ID = "abstractquality";
	public static final String ABSTRACT_SUBJECT_ID = "abstractsubject";
	public static final String ABSTRACT_EVENT_ID = "abstractevent";
	public static final String ABSTRACT_PROCESS_ID = "abstractprocess";
	public static final String ABSTRACT_RELATIONSHIP_ID = "abstractrelationship";
	public static final String ABSTRACT_CONFIGURATION_ID = "abstractconfiguration";
	public static final String ABSTRACT_TRAIT_ID = "abstracttrait";
	public static final String ABSTRACT_ROLE_ID = "abstractrole";
	public static final String ABSTRACT_EXTENT_ID = "abstractextent";
	public static final String UNKNOWN_VERB_ID = "unknownverb";
	public static final String VIEW_VERB_ID = "viewverb";
	public static final String SESSION_VERB_ID = "sessionverb";
	public static final String TEST_VERB_ID = "testverb";
	public static final String EXPLORER_VERB_ID = "explorerverb";
	public static final String LOCAL_VERB_ID = "localverb";
	public static final String IMPORTED_VERB_ID = "importedverb";
	public static final String OBJECT_VERB_ID = "objectverb";
	public static final String STATE_VERB_ID = "stateverb";
	public static final String USER_VERB_ID = "userverb";
	public static final String AMBIGUOUS_VERB_ID = "ambiguousverb";

	public static final RGB UNKNOWN_VERB_COLOR = new RGB(100, 100, 100);
	public static final RGB VIEW_VERB_COLOR = new RGB(63, 8, 165);
	public static final RGB SESSION_VERB_COLOR = new RGB(204, 68, 10);
	public static final RGB TEST_VERB_COLOR = new RGB(188, 90, 79);
	public static final RGB EXPLORER_VERB_COLOR = new RGB(218,165,32);
	public static final RGB LOCAL_VERB_COLOR = new RGB(20, 20, 20);
	public static final RGB IMPORTED_VERB_COLOR = new RGB(20, 20, 20);
	public static final RGB OBJECT_VERB_COLOR = new RGB(127, 62, 6);
	public static final RGB STATE_VERB_COLOR = new RGB(6, 127, 16);
	public static final RGB USER_VERB_COLOR = new RGB(57, 69, 127);
	public static final RGB AMBIGUOUS_VERB_COLOR = new RGB(252, 60, 12);

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {

		super.configure(acceptor);

		acceptor.acceptDefaultHighlighting(KNOWN_URN_ID, "Resolved URN", unclassifiedURNTextStyle());
		acceptor.acceptDefaultHighlighting(UNKNOWN_URN_ID, "Unresolved URN", unresolvedURNTextStyle());
		acceptor.acceptDefaultHighlighting(LIVE_URN_ID, "Live URN", liveURNTextStyle());
		acceptor.acceptDefaultHighlighting(DEAD_URN_ID, "Dead URN", deadURNTextStyle());
		acceptor.acceptDefaultHighlighting(UNAUTHORIZED_URN_ID, "Unauthorized URN", inaccessibleURNTextStyle());
		acceptor.acceptDefaultHighlighting(KNOWN_FUNCTION_ID, "Known Function", knownFunctionTextStyle());
		acceptor.acceptDefaultHighlighting(KNOWN_ANNOTATION_ID, "Known Annotation", knownAnnotationTextStyle());
		acceptor.acceptDefaultHighlighting(UNKNOWN_ANNOTATION_ID, "Unknown Annotation", unknownAnnotationTextStyle());
		acceptor.acceptDefaultHighlighting(VOID_MODEL_ID, "VoidModel", voidTextStyle());
		acceptor.acceptDefaultHighlighting(DANGER_ID, "Inconsistent concept", dangerTextStyle());
		acceptor.acceptDefaultHighlighting(VERSION_NUMBER_ID, "Version number", versionTextStyle());
		acceptor.acceptDefaultHighlighting(DEFINITION_ID, "Concept definition", definitionTextStyle());
		acceptor.acceptDefaultHighlighting(CODE_ID, "Expression code", codeTextStyle());
		acceptor.acceptDefaultHighlighting(QUALITY_ID, "Quality", qualityTextStyle());
		acceptor.acceptDefaultHighlighting(MARKDOWN_ID, "Markdown text", markdownTextStyle());
		acceptor.acceptDefaultHighlighting(SUBJECT_ID, "Subject", subjectTextStyle());
		acceptor.acceptDefaultHighlighting(EVENT_ID, "Event", eventTextStyle());
		acceptor.acceptDefaultHighlighting(PROCESS_ID, "Process", processTextStyle());
		acceptor.acceptDefaultHighlighting(RELATIONSHIP_ID, "Relationship", relationshipTextStyle());
		acceptor.acceptDefaultHighlighting(CONFIGURATION_ID, "Configuration", configurationTextStyle());
		acceptor.acceptDefaultHighlighting(TRAIT_ID, "Trait", traitTextStyle());
		acceptor.acceptDefaultHighlighting(ROLE_ID, "Role", roleTextStyle());
		acceptor.acceptDefaultHighlighting(KEY_ID, "Key", keyTextStyle());
		acceptor.acceptDefaultHighlighting(EXTENT_ID, "Extent", extentTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_QUALITY_ID, "Quality", abstractQualityTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_SUBJECT_ID, "Subject", abstractSubjectTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_EVENT_ID, "Event", abstractEventTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_PROCESS_ID, "Process", abstractProcessTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_RELATIONSHIP_ID, "Relationship", abstractRelationshipTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_CONFIGURATION_ID, "Configuration",
				abstractConfigurationTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_TRAIT_ID, "Trait", abstractTraitTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_ROLE_ID, "Role", abstractRoleTextStyle());
		acceptor.acceptDefaultHighlighting(ABSTRACT_EXTENT_ID, "Extent", abstractExtentTextStyle());
		acceptor.acceptDefaultHighlighting(UNKNOWN_VERB_ID, "Unknown action", unknownVerbTextStyle());
		acceptor.acceptDefaultHighlighting(VIEW_VERB_ID, "View action", viewVerbTextStyle());
		acceptor.acceptDefaultHighlighting(SESSION_VERB_ID, "Session action", sessionVerbTextStyle());
		acceptor.acceptDefaultHighlighting(TEST_VERB_ID, "Test action", testVerbTextStyle());
		acceptor.acceptDefaultHighlighting(EXPLORER_VERB_ID, "Explorer action", explorerVerbTextStyle());
		acceptor.acceptDefaultHighlighting(LOCAL_VERB_ID, "Local action", localVerbTextStyle());
		acceptor.acceptDefaultHighlighting(IMPORTED_VERB_ID, "Imported action", importedVerbTextStyle());
		acceptor.acceptDefaultHighlighting(OBJECT_VERB_ID, "Object action", objectVerbTextStyle());
		acceptor.acceptDefaultHighlighting(STATE_VERB_ID, "State action", stateVerbTextStyle());
		acceptor.acceptDefaultHighlighting(USER_VERB_ID, "User action", userVerbTextStyle());
		acceptor.acceptDefaultHighlighting(AMBIGUOUS_VERB_ID, "Ambiguous action", ambiguousVerbTextStyle());

	}

	protected TextStyle unclassifiedURNTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(60, 60, 100));
		textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle unresolvedURNTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(90, 90, 130));
		textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle liveURNTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 102, 0));
		textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle codeTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(90, 90, 90));
		// textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle definitionTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		// textStyle.setColor(new RGB(0, 102, 0));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle deadURNTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 215, 0));
		textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle inaccessibleURNTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 0, 0));
		textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle unknownAnnotationTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 128, 128));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle knownAnnotationTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 128, 128));
		return textStyle;
	}

	protected TextStyle knownFunctionTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle dangerTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 0, 0));
		textStyle.setStyle(SWT.BOLD | SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle voidTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(160, 160, 160));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle keyTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(139, 64, 0));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle versionTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 153, 153));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle qualityTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 204, 0));
		return textStyle;
	}

	protected TextStyle processTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(204, 0, 0));
		return textStyle;
	}

	protected TextStyle subjectTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(153, 76, 0));
		return textStyle;
	}

	protected TextStyle markdownTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(100, 100, 80));
		return textStyle;
	}

	protected TextStyle eventTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(153, 153, 0));
		return textStyle;
	}

	protected TextStyle relationshipTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(210, 170, 0));
		return textStyle;
	}

	protected TextStyle traitTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 102, 204));
		return textStyle;
	}

	protected TextStyle roleTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 86, 163));
		// textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle configurationTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 100, 100));
		// textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle unknownVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(UNKNOWN_VERB_COLOR);
		textStyle.setStyle(SWT.ITALIC | SWT.BOLD);
		return textStyle;
	}

	protected TextStyle stateVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(STATE_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle objectVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(OBJECT_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle userVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(USER_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
	
	protected TextStyle ambiguousVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(AMBIGUOUS_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle viewVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(VIEW_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle sessionVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(SESSION_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle testVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(TEST_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
	
	protected TextStyle explorerVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(EXPLORER_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle localVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(LOCAL_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle importedVerbTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(IMPORTED_VERB_COLOR);
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle extentTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 153, 153));
		return textStyle;
	}

	protected TextStyle abstractQualityTextStyle() {
		TextStyle ret = qualityTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractProcessTextStyle() {
		TextStyle ret = processTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractSubjectTextStyle() {
		TextStyle ret = subjectTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractEventTextStyle() {
		TextStyle ret = eventTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractRelationshipTextStyle() {
		TextStyle ret = relationshipTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractTraitTextStyle() {
		TextStyle ret = traitTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractRoleTextStyle() {
		TextStyle ret = roleTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractConfigurationTextStyle() {
		TextStyle ret = configurationTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}

	protected TextStyle abstractExtentTextStyle() {
		TextStyle ret = extentTextStyle();
		ret.setStyle(SWT.ITALIC);
		return ret;
	}
}
