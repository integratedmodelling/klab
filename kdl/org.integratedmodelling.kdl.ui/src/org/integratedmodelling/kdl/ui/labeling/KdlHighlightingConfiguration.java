package org.integratedmodelling.kdl.ui.labeling;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class KdlHighlightingConfiguration extends DefaultHighlightingConfiguration {

  public static final String KNOWN_URN_ID          = "knownurn";
  public static final String UNKNOWN_URN_ID        = "unknownurn";
  public static final String LIVE_URN_ID           = "liveurn";
  public static final String DEAD_URN_ID           = "deadurn";
  public static final String UNAUTHORIZED_URN_ID   = "unauthorizedurn";
  public static final String KNOWN_FUNCTION_ID     = "knownfunction";
  public static final String ANNOTATION_ID   = "knownannotation";
    public static final String FUNCTION_ID = "unknownannotation";
  public static final String VOID_MODEL_ID         = "voidmodel";
  public static final String QUALITY_ID            = "quality";
  public static final String SUBJECT_ID            = "subject";
  public static final String EVENT_ID              = "event";
  public static final String PROCESS_ID            = "process";
  public static final String RELATIONSHIP_ID       = "relationship";
  public static final String CONFIGURATION_ID      = "configuration";
  public static final String TRAIT_ID              = "trait";
  public static final String ROLE_ID               = "role";
  public static final String DANGER_ID             = "danger";
  public static final String VERSION_NUMBER_ID     = "versionNumber";
  public static final String EXTENT_ID             = "extent";
  public static final String DEFINITION_ID         = "definition";
  public static final String CODE_ID               = "code";

  @Override
  public void configure(IHighlightingConfigurationAcceptor acceptor) {

    super.configure(acceptor);

    acceptor.acceptDefaultHighlighting(KNOWN_URN_ID, "Resolved URN", unclassifiedURNTextStyle());
    acceptor.acceptDefaultHighlighting(UNKNOWN_URN_ID, "Unresolved URN", unresolvedURNTextStyle());
    acceptor.acceptDefaultHighlighting(LIVE_URN_ID, "Live URN", liveURNTextStyle());
    acceptor.acceptDefaultHighlighting(DEAD_URN_ID, "Dead URN", deadURNTextStyle());
    acceptor.acceptDefaultHighlighting(UNAUTHORIZED_URN_ID, "Unauthorized URN",
        inaccessibleURNTextStyle());
    acceptor.acceptDefaultHighlighting(KNOWN_FUNCTION_ID, "Known Function",
        knownFunctionTextStyle());
    acceptor.acceptDefaultHighlighting(ANNOTATION_ID, "Known Annotation",
        knownAnnotationTextStyle());
    acceptor.acceptDefaultHighlighting(FUNCTION_ID, "Function",
        functionTextStyle());
    acceptor.acceptDefaultHighlighting(VOID_MODEL_ID, "VoidModel", voidTextStyle());
    acceptor.acceptDefaultHighlighting(QUALITY_ID, "Quality", qualityTextStyle());
    acceptor.acceptDefaultHighlighting(SUBJECT_ID, "Subject", subjectTextStyle());
    acceptor.acceptDefaultHighlighting(EVENT_ID, "Event", eventTextStyle());
    acceptor.acceptDefaultHighlighting(PROCESS_ID, "Process", processTextStyle());
    acceptor.acceptDefaultHighlighting(RELATIONSHIP_ID, "Relationship", relationshipTextStyle());
    acceptor.acceptDefaultHighlighting(CONFIGURATION_ID, "Configuration", configurationTextStyle());
    acceptor.acceptDefaultHighlighting(TRAIT_ID, "Trait", traitTextStyle());
    acceptor.acceptDefaultHighlighting(ROLE_ID, "Role", roleTextStyle());
    acceptor.acceptDefaultHighlighting(DANGER_ID, "Inconsistent concept", dangerTextStyle());
    acceptor.acceptDefaultHighlighting(VERSION_NUMBER_ID, "Version number", versionTextStyle());
    acceptor.acceptDefaultHighlighting(EXTENT_ID, "Extent", extentTextStyle());
    acceptor.acceptDefaultHighlighting(DEFINITION_ID, "Concept definition", definitionTextStyle());
    acceptor.acceptDefaultHighlighting(CODE_ID, "Expression code", codeTextStyle());
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

  protected TextStyle functionTextStyle() {
    TextStyle textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(0, 76, 153));
//    textStyle.setStyle(SWT.ITALIC);
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
    textStyle.setStyle(SWT.ITALIC);
    return textStyle;
  }

  protected TextStyle configurationTextStyle() {
    TextStyle textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(98, 98, 98));
    textStyle.setStyle(SWT.ITALIC);
    return textStyle;
  }

  protected TextStyle dangerTextStyle() {
    TextStyle textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(255, 0, 0));
    return textStyle;
  }

  protected TextStyle voidTextStyle() {
    TextStyle textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(160, 160, 160));
    textStyle.setStyle(SWT.ITALIC);
    return textStyle;
  }

  protected TextStyle versionTextStyle() {
    TextStyle textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(0, 153, 153));
    textStyle.setStyle(SWT.BOLD);
    return textStyle;
  }

  protected TextStyle extentTextStyle() {
    TextStyle textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(0, 153, 153));
    textStyle.setStyle(SWT.ITALIC);
    return textStyle;
  }

}
