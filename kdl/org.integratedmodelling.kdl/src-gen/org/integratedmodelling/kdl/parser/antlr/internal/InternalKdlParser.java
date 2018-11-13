package org.integratedmodelling.kdl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper.UnorderedGroupState;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.integratedmodelling.kdl.services.KdlGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalKdlParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_LOWERCASE_ID", "RULE_ANNOTATION_ID", "RULE_INT", "RULE_LOWERCASE_DASHID", "RULE_UPPERCASE_ID", "RULE_SHAPE", "RULE_ID", "RULE_EXPR", "RULE_CAMELCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'@dataflow'", "'@var'", "'@val'", "'@author'", "'@version'", "'@klab'", "'@worldview'", "'@geometry'", "'@endpoint'", "'@namespace'", "'@coverage'", "','", "'@context'", "'('", "')'", "'final'", "'export'", "'optional'", "'import'", "'multiple'", "'+'", "'parameter'", "'for'", "'{'", "'}'", "'as'", "'over'", "'number'", "'boolean'", "'text'", "'list'", "'enum'", "'input'", "'values'", "'default'", "'minimum'", "'maximum'", "'range'", "'to'", "'geometry'", "'units'", "'metadata'", "'class'", "'compute'", "'*'", "'object'", "'process'", "'value'", "'concept'", "'extent'", "'spatialextent'", "'temporalextent'", "'annotation'", "'contextualizer'", "'void'", "'partition'", "'models'", "'concepts'", "'observers'", "'definitions'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'in'", "'unknown'", "'urn:klab:'", "':'", "'#'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'='", "'>>'", "'>'", "'<'", "'!='", "'<='", "'>='", "'-'", "'e'", "'E'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_UPPERCASE_ID=9;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=11;
    public static final int RULE_INT=7;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=15;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=12;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=14;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_SHAPE=10;
    public static final int T__19=19;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_LOWERCASE_DASHID=8;
    public static final int RULE_CAMELCASE_ID=13;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=16;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=17;
    public static final int RULE_ANY_OTHER=18;
    public static final int RULE_ANNOTATION_ID=6;
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__87=87;

    // delegates
    // delegators


        public InternalKdlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalKdlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalKdlParser.tokenNames; }
    public String getGrammarFileName() { return "InternalKdl.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */

     	private KdlGrammarAccess grammarAccess;

        public InternalKdlParser(TokenStream input, KdlGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected KdlGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalKdl.g:72:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getModelAccess().getUnorderedGroup_0()
        	);

        try {
            // InternalKdl.g:76:2: (iv_ruleModel= ruleModel EOF )
            // InternalKdl.g:77:2: iv_ruleModel= ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModel; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalKdl.g:86:1: ruleModel returns [EObject current=null] : ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_authors_8_0=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token lv_worldview_14_0=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token lv_endpoint_18_0=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        AntlrDatatypeRuleToken lv_name_2_1 = null;

        AntlrDatatypeRuleToken lv_name_2_2 = null;

        EObject lv_variables_4_0 = null;

        EObject lv_constants_6_0 = null;

        AntlrDatatypeRuleToken lv_version_10_0 = null;

        AntlrDatatypeRuleToken lv_klabVersion_12_0 = null;

        AntlrDatatypeRuleToken lv_geometry_16_0 = null;

        AntlrDatatypeRuleToken lv_package_20_0 = null;

        EObject lv_scale_22_0 = null;

        EObject lv_scale_24_0 = null;

        EObject lv_contextUrn_26_0 = null;

        EObject lv_actors_27_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getModelAccess().getUnorderedGroup_0()
        	);

        try {
            // InternalKdl.g:95:2: ( ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* ) )
            // InternalKdl.g:96:2: ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* )
            {
            // InternalKdl.g:96:2: ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* )
            // InternalKdl.g:97:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )*
            {
            // InternalKdl.g:97:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) )
            // InternalKdl.g:98:4: ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) )
            {
            // InternalKdl.g:98:4: ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) )
            // InternalKdl.g:99:5: ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getModelAccess().getUnorderedGroup_0());
            // InternalKdl.g:102:5: ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* )
            // InternalKdl.g:103:6: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )*
            {
            // InternalKdl.g:103:6: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )*
            loop6:
            do {
                int alt6=13;
                alt6 = dfa6.predict(input);
                switch (alt6) {
            	case 1 :
            	    // InternalKdl.g:104:4: ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:104:4: ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) )
            	    // InternalKdl.g:105:5: {...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0)");
            	    }
            	    // InternalKdl.g:105:102: ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) )
            	    // InternalKdl.g:106:6: ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0);
            	    // InternalKdl.g:109:9: ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) )
            	    // InternalKdl.g:109:10: {...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:109:19: (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) )
            	    // InternalKdl.g:109:20: otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
            	    {
            	    otherlv_1=(Token)match(input,19,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_1, grammarAccess.getModelAccess().getDataflowKeyword_0_0_0());
            	      								
            	    }
            	    // InternalKdl.g:113:9: ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
            	    // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
            	    {
            	    // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
            	    // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
            	    {
            	    // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
            	    int alt1=2;
            	    alt1 = dfa1.predict(input);
            	    switch (alt1) {
            	        case 1 :
            	            // InternalKdl.g:116:12: lv_name_2_1= rulePath
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getModelAccess().getNamePathParserRuleCall_0_0_1_0_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_4);
            	            lv_name_2_1=rulePath();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getModelRule());
            	              												}
            	              												set(
            	              													current,
            	              													"name",
            	              													lv_name_2_1,
            	              													"org.integratedmodelling.kdl.Kdl.Path");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:132:12: lv_name_2_2= ruleUrnId
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getModelAccess().getNameUrnIdParserRuleCall_0_0_1_0_1());
            	              											
            	            }
            	            pushFollow(FOLLOW_4);
            	            lv_name_2_2=ruleUrnId();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getModelRule());
            	              												}
            	              												set(
            	              													current,
            	              													"name",
            	              													lv_name_2_2,
            	              													"org.integratedmodelling.kdl.Kdl.UrnId");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKdl.g:156:4: ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) )
            	    {
            	    // InternalKdl.g:156:4: ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) )
            	    // InternalKdl.g:157:5: {...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1)");
            	    }
            	    // InternalKdl.g:157:102: ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ )
            	    // InternalKdl.g:158:6: ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1);
            	    // InternalKdl.g:161:9: ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+
            	    int cnt2=0;
            	    loop2:
            	    do {
            	        int alt2=2;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==20) ) {
            	            int LA2_2 = input.LA(2);

            	            if ( ((synpred3_InternalKdl()&&(true))) ) {
            	                alt2=1;
            	            }


            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // InternalKdl.g:161:10: {...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    	    }
            	    	    // InternalKdl.g:161:19: (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) )
            	    	    // InternalKdl.g:161:20: otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) )
            	    	    {
            	    	    otherlv_3=(Token)match(input,20,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_3, grammarAccess.getModelAccess().getVarKeyword_0_1_0());
            	    	      								
            	    	    }
            	    	    // InternalKdl.g:165:9: ( (lv_variables_4_0= ruleParameter ) )
            	    	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
            	    	    {
            	    	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
            	    	    // InternalKdl.g:167:11: lv_variables_4_0= ruleParameter
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      											newCompositeNode(grammarAccess.getModelAccess().getVariablesParameterParserRuleCall_0_1_1_0());
            	    	      										
            	    	    }
            	    	    pushFollow(FOLLOW_4);
            	    	    lv_variables_4_0=ruleParameter();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	    	      											}
            	    	      											add(
            	    	      												current,
            	    	      												"variables",
            	    	      												lv_variables_4_0,
            	    	      												"org.integratedmodelling.kdl.Kdl.Parameter");
            	    	      											afterParserOrEnumRuleCall();
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt2 >= 1 ) break loop2;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(2, input);
            	                throw eee;
            	        }
            	        cnt2++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKdl.g:190:4: ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) )
            	    {
            	    // InternalKdl.g:190:4: ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) )
            	    // InternalKdl.g:191:5: {...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2)");
            	    }
            	    // InternalKdl.g:191:102: ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ )
            	    // InternalKdl.g:192:6: ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2);
            	    // InternalKdl.g:195:9: ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+
            	    int cnt3=0;
            	    loop3:
            	    do {
            	        int alt3=2;
            	        int LA3_0 = input.LA(1);

            	        if ( (LA3_0==21) ) {
            	            int LA3_2 = input.LA(2);

            	            if ( ((synpred5_InternalKdl()&&(true))) ) {
            	                alt3=1;
            	            }


            	        }


            	        switch (alt3) {
            	    	case 1 :
            	    	    // InternalKdl.g:195:10: {...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    	    }
            	    	    // InternalKdl.g:195:19: (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) )
            	    	    // InternalKdl.g:195:20: otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) )
            	    	    {
            	    	    otherlv_5=(Token)match(input,21,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_5, grammarAccess.getModelAccess().getValKeyword_0_2_0());
            	    	      								
            	    	    }
            	    	    // InternalKdl.g:199:9: ( (lv_constants_6_0= ruleParameter ) )
            	    	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
            	    	    {
            	    	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
            	    	    // InternalKdl.g:201:11: lv_constants_6_0= ruleParameter
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      											newCompositeNode(grammarAccess.getModelAccess().getConstantsParameterParserRuleCall_0_2_1_0());
            	    	      										
            	    	    }
            	    	    pushFollow(FOLLOW_4);
            	    	    lv_constants_6_0=ruleParameter();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	    	      											}
            	    	      											add(
            	    	      												current,
            	    	      												"constants",
            	    	      												lv_constants_6_0,
            	    	      												"org.integratedmodelling.kdl.Kdl.Parameter");
            	    	      											afterParserOrEnumRuleCall();
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt3 >= 1 ) break loop3;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(3, input);
            	                throw eee;
            	        }
            	        cnt3++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKdl.g:224:4: ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKdl.g:224:4: ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKdl.g:225:5: {...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3)");
            	    }
            	    // InternalKdl.g:225:102: ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ )
            	    // InternalKdl.g:226:6: ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3);
            	    // InternalKdl.g:229:9: ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+
            	    int cnt4=0;
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==22) ) {
            	            int LA4_2 = input.LA(2);

            	            if ( ((synpred7_InternalKdl()&&(true))) ) {
            	                alt4=1;
            	            }


            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // InternalKdl.g:229:10: {...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    	    }
            	    	    // InternalKdl.g:229:19: (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) )
            	    	    // InternalKdl.g:229:20: otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,22,FOLLOW_6); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_7, grammarAccess.getModelAccess().getAuthorKeyword_0_3_0());
            	    	      								
            	    	    }
            	    	    // InternalKdl.g:233:9: ( (lv_authors_8_0= RULE_STRING ) )
            	    	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
            	    	    {
            	    	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
            	    	    // InternalKdl.g:235:11: lv_authors_8_0= RULE_STRING
            	    	    {
            	    	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_8_0, grammarAccess.getModelAccess().getAuthorsSTRINGTerminalRuleCall_0_3_1_0());
            	    	      										
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElement(grammarAccess.getModelRule());
            	    	      											}
            	    	      											addWithLastConsumed(
            	    	      												current,
            	    	      												"authors",
            	    	      												lv_authors_8_0,
            	    	      												"org.eclipse.xtext.common.Terminals.STRING");
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt4 >= 1 ) break loop4;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(4, input);
            	                throw eee;
            	        }
            	        cnt4++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKdl.g:257:4: ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:257:4: ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKdl.g:258:5: {...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4)");
            	    }
            	    // InternalKdl.g:258:102: ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKdl.g:259:6: ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4);
            	    // InternalKdl.g:262:9: ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) )
            	    // InternalKdl.g:262:10: {...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:262:19: (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) )
            	    // InternalKdl.g:262:20: otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) )
            	    {
            	    otherlv_9=(Token)match(input,23,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_9, grammarAccess.getModelAccess().getVersionKeyword_0_4_0());
            	      								
            	    }
            	    // InternalKdl.g:266:9: ( (lv_version_10_0= ruleVersionNumber ) )
            	    // InternalKdl.g:267:10: (lv_version_10_0= ruleVersionNumber )
            	    {
            	    // InternalKdl.g:267:10: (lv_version_10_0= ruleVersionNumber )
            	    // InternalKdl.g:268:11: lv_version_10_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getVersionVersionNumberParserRuleCall_0_4_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_4);
            	    lv_version_10_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_10_0,
            	      												"org.integratedmodelling.kdl.Kdl.VersionNumber");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalKdl.g:291:4: ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:291:4: ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKdl.g:292:5: {...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5)");
            	    }
            	    // InternalKdl.g:292:102: ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKdl.g:293:6: ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5);
            	    // InternalKdl.g:296:9: ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) )
            	    // InternalKdl.g:296:10: {...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:296:19: (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) )
            	    // InternalKdl.g:296:20: otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) )
            	    {
            	    otherlv_11=(Token)match(input,24,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_11, grammarAccess.getModelAccess().getKlabKeyword_0_5_0());
            	      								
            	    }
            	    // InternalKdl.g:300:9: ( (lv_klabVersion_12_0= ruleVersionNumber ) )
            	    // InternalKdl.g:301:10: (lv_klabVersion_12_0= ruleVersionNumber )
            	    {
            	    // InternalKdl.g:301:10: (lv_klabVersion_12_0= ruleVersionNumber )
            	    // InternalKdl.g:302:11: lv_klabVersion_12_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getKlabVersionVersionNumberParserRuleCall_0_5_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_4);
            	    lv_klabVersion_12_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	      											}
            	      											set(
            	      												current,
            	      												"klabVersion",
            	      												lv_klabVersion_12_0,
            	      												"org.integratedmodelling.kdl.Kdl.VersionNumber");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKdl.g:325:4: ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:325:4: ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) )
            	    // InternalKdl.g:326:5: {...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6)");
            	    }
            	    // InternalKdl.g:326:102: ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) )
            	    // InternalKdl.g:327:6: ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6);
            	    // InternalKdl.g:330:9: ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) )
            	    // InternalKdl.g:330:10: {...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:330:19: (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) )
            	    // InternalKdl.g:330:20: otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) )
            	    {
            	    otherlv_13=(Token)match(input,25,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_13, grammarAccess.getModelAccess().getWorldviewKeyword_0_6_0());
            	      								
            	    }
            	    // InternalKdl.g:334:9: ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) )
            	    // InternalKdl.g:335:10: (lv_worldview_14_0= RULE_LOWERCASE_ID )
            	    {
            	    // InternalKdl.g:335:10: (lv_worldview_14_0= RULE_LOWERCASE_ID )
            	    // InternalKdl.g:336:11: lv_worldview_14_0= RULE_LOWERCASE_ID
            	    {
            	    lv_worldview_14_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_worldview_14_0, grammarAccess.getModelAccess().getWorldviewLOWERCASE_IDTerminalRuleCall_0_6_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getModelRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_14_0,
            	      												"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKdl.g:358:4: ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:358:4: ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:359:5: {...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7)");
            	    }
            	    // InternalKdl.g:359:102: ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:360:6: ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7);
            	    // InternalKdl.g:363:9: ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:363:10: {...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:363:19: (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) )
            	    // InternalKdl.g:363:20: otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) )
            	    {
            	    otherlv_15=(Token)match(input,26,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_15, grammarAccess.getModelAccess().getGeometryKeyword_0_7_0());
            	      								
            	    }
            	    // InternalKdl.g:367:9: ( (lv_geometry_16_0= ruleGeometry ) )
            	    // InternalKdl.g:368:10: (lv_geometry_16_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:368:10: (lv_geometry_16_0= ruleGeometry )
            	    // InternalKdl.g:369:11: lv_geometry_16_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getGeometryGeometryParserRuleCall_0_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_4);
            	    lv_geometry_16_0=ruleGeometry();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	      											}
            	      											set(
            	      												current,
            	      												"geometry",
            	      												lv_geometry_16_0,
            	      												"org.integratedmodelling.kdl.Kdl.Geometry");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 9 :
            	    // InternalKdl.g:392:4: ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:392:4: ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKdl.g:393:5: {...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8)");
            	    }
            	    // InternalKdl.g:393:102: ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) )
            	    // InternalKdl.g:394:6: ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8);
            	    // InternalKdl.g:397:9: ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) )
            	    // InternalKdl.g:397:10: {...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:397:19: (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) )
            	    // InternalKdl.g:397:20: otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) )
            	    {
            	    otherlv_17=(Token)match(input,27,FOLLOW_6); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_17, grammarAccess.getModelAccess().getEndpointKeyword_0_8_0());
            	      								
            	    }
            	    // InternalKdl.g:401:9: ( (lv_endpoint_18_0= RULE_STRING ) )
            	    // InternalKdl.g:402:10: (lv_endpoint_18_0= RULE_STRING )
            	    {
            	    // InternalKdl.g:402:10: (lv_endpoint_18_0= RULE_STRING )
            	    // InternalKdl.g:403:11: lv_endpoint_18_0= RULE_STRING
            	    {
            	    lv_endpoint_18_0=(Token)match(input,RULE_STRING,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_endpoint_18_0, grammarAccess.getModelAccess().getEndpointSTRINGTerminalRuleCall_0_8_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getModelRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"endpoint",
            	      												lv_endpoint_18_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 10 :
            	    // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) )
            	    // InternalKdl.g:426:5: {...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9)");
            	    }
            	    // InternalKdl.g:426:102: ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) )
            	    // InternalKdl.g:427:6: ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9);
            	    // InternalKdl.g:430:9: ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) )
            	    // InternalKdl.g:430:10: {...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:430:19: (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) )
            	    // InternalKdl.g:430:20: otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) )
            	    {
            	    otherlv_19=(Token)match(input,28,FOLLOW_3); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_19, grammarAccess.getModelAccess().getNamespaceKeyword_0_9_0());
            	      								
            	    }
            	    // InternalKdl.g:434:9: ( (lv_package_20_0= rulePathName ) )
            	    // InternalKdl.g:435:10: (lv_package_20_0= rulePathName )
            	    {
            	    // InternalKdl.g:435:10: (lv_package_20_0= rulePathName )
            	    // InternalKdl.g:436:11: lv_package_20_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getPackagePathNameParserRuleCall_0_9_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_4);
            	    lv_package_20_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	      											}
            	      											set(
            	      												current,
            	      												"package",
            	      												lv_package_20_0,
            	      												"org.integratedmodelling.kdl.Kdl.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 11 :
            	    // InternalKdl.g:459:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
            	    {
            	    // InternalKdl.g:459:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
            	    // InternalKdl.g:460:5: {...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10)");
            	    }
            	    // InternalKdl.g:460:103: ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
            	    // InternalKdl.g:461:6: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10);
            	    // InternalKdl.g:464:9: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
            	    // InternalKdl.g:464:10: {...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:464:19: (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
            	    // InternalKdl.g:464:20: otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
            	    {
            	    otherlv_21=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_21, grammarAccess.getModelAccess().getCoverageKeyword_0_10_0());
            	      								
            	    }
            	    // InternalKdl.g:468:9: ( (lv_scale_22_0= ruleFunction ) )
            	    // InternalKdl.g:469:10: (lv_scale_22_0= ruleFunction )
            	    {
            	    // InternalKdl.g:469:10: (lv_scale_22_0= ruleFunction )
            	    // InternalKdl.g:470:11: lv_scale_22_0= ruleFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_scale_22_0=ruleFunction();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	      											}
            	      											add(
            	      												current,
            	      												"scale",
            	      												lv_scale_22_0,
            	      												"org.integratedmodelling.kdl.Kdl.Function");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKdl.g:487:9: (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
            	    loop5:
            	    do {
            	        int alt5=2;
            	        int LA5_0 = input.LA(1);

            	        if ( (LA5_0==30) ) {
            	            alt5=1;
            	        }


            	        switch (alt5) {
            	    	case 1 :
            	    	    // InternalKdl.g:488:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
            	    	    {
            	    	    otherlv_23=(Token)match(input,30,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_23, grammarAccess.getModelAccess().getCommaKeyword_0_10_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKdl.g:492:10: ( (lv_scale_24_0= ruleFunction ) )
            	    	    // InternalKdl.g:493:11: (lv_scale_24_0= ruleFunction )
            	    	    {
            	    	    // InternalKdl.g:493:11: (lv_scale_24_0= ruleFunction )
            	    	    // InternalKdl.g:494:12: lv_scale_24_0= ruleFunction
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_10);
            	    	    lv_scale_24_0=ruleFunction();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      												if (current==null) {
            	    	      													current = createModelElementForParent(grammarAccess.getModelRule());
            	    	      												}
            	    	      												add(
            	    	      													current,
            	    	      													"scale",
            	    	      													lv_scale_24_0,
            	    	      													"org.integratedmodelling.kdl.Kdl.Function");
            	    	      												afterParserOrEnumRuleCall();
            	    	      											
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop5;
            	        }
            	    } while (true);


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;
            	case 12 :
            	    // InternalKdl.g:518:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:518:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
            	    // InternalKdl.g:519:5: {...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11)");
            	    }
            	    // InternalKdl.g:519:103: ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
            	    // InternalKdl.g:520:6: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11);
            	    // InternalKdl.g:523:9: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
            	    // InternalKdl.g:523:10: {...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:523:19: (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
            	    // InternalKdl.g:523:20: otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) )
            	    {
            	    otherlv_25=(Token)match(input,31,FOLLOW_11); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_25, grammarAccess.getModelAccess().getContextKeyword_0_11_0());
            	      								
            	    }
            	    // InternalKdl.g:527:9: ( (lv_contextUrn_26_0= ruleUrn ) )
            	    // InternalKdl.g:528:10: (lv_contextUrn_26_0= ruleUrn )
            	    {
            	    // InternalKdl.g:528:10: (lv_contextUrn_26_0= ruleUrn )
            	    // InternalKdl.g:529:11: lv_contextUrn_26_0= ruleUrn
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getContextUrnUrnParserRuleCall_0_11_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_4);
            	    lv_contextUrn_26_0=ruleUrn();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getModelRule());
            	      											}
            	      											set(
            	      												current,
            	      												"contextUrn",
            	      												lv_contextUrn_26_0,
            	      												"org.integratedmodelling.kdl.Kdl.Urn");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getModelAccess().getUnorderedGroup_0());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getModelAccess().getUnorderedGroup_0());

            }

            // InternalKdl.g:559:3: ( (lv_actors_27_0= ruleActorDefinition ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ANNOTATION_ID||(LA7_0>=34 && LA7_0<=37)||LA7_0==40||(LA7_0>=46 && LA7_0<=50)||(LA7_0>=64 && LA7_0<=74)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKdl.g:560:4: (lv_actors_27_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:560:4: (lv_actors_27_0= ruleActorDefinition )
            	    // InternalKdl.g:561:5: lv_actors_27_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getActorsActorDefinitionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
            	    lv_actors_27_0=ruleActorDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"actors",
            	      						lv_actors_27_0,
            	      						"org.integratedmodelling.kdl.Kdl.ActorDefinition");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleAnnotation"
    // InternalKdl.g:585:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKdl.g:585:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKdl.g:586:2: iv_ruleAnnotation= ruleAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnnotationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnnotation=ruleAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnnotation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnnotation"


    // $ANTLR start "ruleAnnotation"
    // InternalKdl.g:592:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:598:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKdl.g:599:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKdl.g:599:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKdl.g:600:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKdl.g:600:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKdl.g:601:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKdl.g:601:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKdl.g:602:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getAnnotationRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.integratedmodelling.kdl.Kdl.ANNOTATION_ID");
              				
            }

            }


            }

            // InternalKdl.g:618:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalKdl.g:619:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,32,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:623:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( ((LA8_0>=RULE_STRING && LA8_0<=RULE_LOWERCASE_ID)||(LA8_0>=RULE_INT && LA8_0<=RULE_UPPERCASE_ID)||(LA8_0>=RULE_ID && LA8_0<=RULE_CAMELCASE_ID)||LA8_0==30||LA8_0==32||LA8_0==39||LA8_0==42||(LA8_0>=79 && LA8_0<=80)||LA8_0==85||LA8_0==88||LA8_0==101) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // InternalKdl.g:624:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKdl.g:624:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKdl.g:625:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_15);
                            lv_parameters_2_0=ruleParameterList();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getAnnotationRule());
                              						}
                              						set(
                              							current,
                              							"parameters",
                              							lv_parameters_2_0,
                              							"org.integratedmodelling.kdl.Kdl.ParameterList");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_3=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getAnnotationAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnnotation"


    // $ANTLR start "entryRuleActorDefinition"
    // InternalKdl.g:651:1: entryRuleActorDefinition returns [EObject current=null] : iv_ruleActorDefinition= ruleActorDefinition EOF ;
    public final EObject entryRuleActorDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActorDefinition = null;


        try {
            // InternalKdl.g:651:56: (iv_ruleActorDefinition= ruleActorDefinition EOF )
            // InternalKdl.g:652:2: iv_ruleActorDefinition= ruleActorDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActorDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActorDefinition=ruleActorDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActorDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActorDefinition"


    // $ANTLR start "ruleActorDefinition"
    // InternalKdl.g:658:1: ruleActorDefinition returns [EObject current=null] : ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )? ) ) ;
    public final EObject ruleActorDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_final_1_0=null;
        Token lv_exported_2_0=null;
        Token lv_optional_3_0=null;
        Token lv_imported_4_0=null;
        Token lv_multiple_5_0=null;
        Token lv_arity_6_0=null;
        Token lv_minimum_7_0=null;
        Token lv_parameter_8_0=null;
        Token lv_name_10_1=null;
        Token lv_name_10_2=null;
        Token lv_name_10_3=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token lv_docstring_15_0=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_localName_20_0=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token lv_optional_25_0=null;
        Token lv_type_26_1=null;
        Token lv_type_26_2=null;
        Token lv_type_26_3=null;
        Token lv_type_26_4=null;
        Token lv_type_26_5=null;
        Token lv_parameter_27_0=null;
        Token lv_name_28_1=null;
        Token lv_name_28_2=null;
        Token lv_name_28_3=null;
        Token otherlv_29=null;
        Token lv_enumValues_30_0=null;
        Token otherlv_31=null;
        Token lv_enumValues_32_0=null;
        Token lv_docstring_33_0=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_38=null;
        Token otherlv_39=null;
        Token otherlv_41=null;
        Token otherlv_43=null;
        Token otherlv_45=null;
        EObject lv_annotations_0_0 = null;

        AntlrDatatypeRuleToken lv_type_9_0 = null;

        AntlrDatatypeRuleToken lv_targets_12_0 = null;

        AntlrDatatypeRuleToken lv_targets_14_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_coverage_22_0 = null;

        EObject lv_coverage_24_0 = null;

        EObject lv_default_35_0 = null;

        EObject lv_body_37_0 = null;

        EObject lv_rangeMin_40_0 = null;

        EObject lv_rangeMax_42_0 = null;

        EObject lv_rangeMin_44_0 = null;

        EObject lv_rangeMax_46_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:664:2: ( ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )? ) ) )
            // InternalKdl.g:665:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )? ) )
            {
            // InternalKdl.g:665:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )? ) )
            int alt33=2;
            switch ( input.LA(1) ) {
            case RULE_ANNOTATION_ID:
            case 34:
            case 35:
            case 37:
            case 40:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
                {
                alt33=1;
                }
                break;
            case 36:
                {
                int LA33_2 = input.LA(2);

                if ( (LA33_2==51) ) {
                    alt33=2;
                }
                else if ( (LA33_2==37) ) {
                    alt33=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 2, input);

                    throw nvae;
                }
                }
                break;
            case 46:
                {
                int LA33_3 = input.LA(2);

                if ( (LA33_3==51) ) {
                    alt33=2;
                }
                else if ( ((LA33_3>=RULE_STRING && LA33_3<=RULE_LOWERCASE_ID)||LA33_3==RULE_LOWERCASE_DASHID) ) {
                    alt33=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 3, input);

                    throw nvae;
                }
                }
                break;
            case 47:
                {
                int LA33_4 = input.LA(2);

                if ( ((LA33_4>=RULE_STRING && LA33_4<=RULE_LOWERCASE_ID)||LA33_4==RULE_LOWERCASE_DASHID) ) {
                    alt33=1;
                }
                else if ( (LA33_4==51) ) {
                    alt33=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 4, input);

                    throw nvae;
                }
                }
                break;
            case 48:
                {
                int LA33_5 = input.LA(2);

                if ( (LA33_5==51) ) {
                    alt33=2;
                }
                else if ( ((LA33_5>=RULE_STRING && LA33_5<=RULE_LOWERCASE_ID)||LA33_5==RULE_LOWERCASE_DASHID) ) {
                    alt33=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 5, input);

                    throw nvae;
                }
                }
                break;
            case 49:
            case 50:
                {
                alt33=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    // InternalKdl.g:666:3: ( ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:666:3: ( ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:667:4: ( (lv_annotations_0_0= ruleAnnotation ) )* ( (lv_final_1_0= 'final' ) )? ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )? ( (lv_parameter_8_0= 'parameter' ) )? ( (lv_type_9_0= ruleACTOR ) ) ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) ) (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )? ( (lv_docstring_15_0= RULE_STRING ) )? (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )? (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )? (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )?
                    {
                    // InternalKdl.g:667:4: ( (lv_annotations_0_0= ruleAnnotation ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==RULE_ANNOTATION_ID) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // InternalKdl.g:668:5: (lv_annotations_0_0= ruleAnnotation )
                    	    {
                    	    // InternalKdl.g:668:5: (lv_annotations_0_0= ruleAnnotation )
                    	    // InternalKdl.g:669:6: lv_annotations_0_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getAnnotationsAnnotationParserRuleCall_0_0_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_16);
                    	    lv_annotations_0_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"annotations",
                    	      							lv_annotations_0_0,
                    	      							"org.integratedmodelling.kdl.Kdl.Annotation");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    // InternalKdl.g:686:4: ( (lv_final_1_0= 'final' ) )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==34) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalKdl.g:687:5: (lv_final_1_0= 'final' )
                            {
                            // InternalKdl.g:687:5: (lv_final_1_0= 'final' )
                            // InternalKdl.g:688:6: lv_final_1_0= 'final'
                            {
                            lv_final_1_0=(Token)match(input,34,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_final_1_0, grammarAccess.getActorDefinitionAccess().getFinalFinalKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "final", true, "final");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:700:4: ( ( (lv_exported_2_0= 'export' ) ) | ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? ) )?
                    int alt15=3;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==35) ) {
                        alt15=1;
                    }
                    else if ( ((LA15_0>=36 && LA15_0<=37)) ) {
                        alt15=2;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKdl.g:701:5: ( (lv_exported_2_0= 'export' ) )
                            {
                            // InternalKdl.g:701:5: ( (lv_exported_2_0= 'export' ) )
                            // InternalKdl.g:702:6: (lv_exported_2_0= 'export' )
                            {
                            // InternalKdl.g:702:6: (lv_exported_2_0= 'export' )
                            // InternalKdl.g:703:7: lv_exported_2_0= 'export'
                            {
                            lv_exported_2_0=(Token)match(input,35,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_2_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_0_2_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "exported", true, "export");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:716:5: ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? )
                            {
                            // InternalKdl.g:716:5: ( ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )? )
                            // InternalKdl.g:717:6: ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) ) ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )?
                            {
                            // InternalKdl.g:717:6: ( ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) ) )
                            // InternalKdl.g:718:7: ( (lv_optional_3_0= 'optional' ) )? ( (lv_imported_4_0= 'import' ) )
                            {
                            // InternalKdl.g:718:7: ( (lv_optional_3_0= 'optional' ) )?
                            int alt12=2;
                            int LA12_0 = input.LA(1);

                            if ( (LA12_0==36) ) {
                                alt12=1;
                            }
                            switch (alt12) {
                                case 1 :
                                    // InternalKdl.g:719:8: (lv_optional_3_0= 'optional' )
                                    {
                                    // InternalKdl.g:719:8: (lv_optional_3_0= 'optional' )
                                    // InternalKdl.g:720:9: lv_optional_3_0= 'optional'
                                    {
                                    lv_optional_3_0=(Token)match(input,36,FOLLOW_17); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_optional_3_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_0_2_1_0_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									setWithLastConsumed(current, "optional", true, "optional");
                                      								
                                    }

                                    }


                                    }
                                    break;

                            }

                            // InternalKdl.g:732:7: ( (lv_imported_4_0= 'import' ) )
                            // InternalKdl.g:733:8: (lv_imported_4_0= 'import' )
                            {
                            // InternalKdl.g:733:8: (lv_imported_4_0= 'import' )
                            // InternalKdl.g:734:9: lv_imported_4_0= 'import'
                            {
                            lv_imported_4_0=(Token)match(input,37,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              									newLeafNode(lv_imported_4_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_0_2_1_0_1_0());
                              								
                            }
                            if ( state.backtracking==0 ) {

                              									if (current==null) {
                              										current = createModelElement(grammarAccess.getActorDefinitionRule());
                              									}
                              									setWithLastConsumed(current, "imported", true, "import");
                              								
                            }

                            }


                            }


                            }

                            // InternalKdl.g:747:6: ( ( (lv_multiple_5_0= 'multiple' ) ) | ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? ) )?
                            int alt14=3;
                            int LA14_0 = input.LA(1);

                            if ( (LA14_0==38) ) {
                                alt14=1;
                            }
                            else if ( (LA14_0==RULE_INT) ) {
                                alt14=2;
                            }
                            switch (alt14) {
                                case 1 :
                                    // InternalKdl.g:748:7: ( (lv_multiple_5_0= 'multiple' ) )
                                    {
                                    // InternalKdl.g:748:7: ( (lv_multiple_5_0= 'multiple' ) )
                                    // InternalKdl.g:749:8: (lv_multiple_5_0= 'multiple' )
                                    {
                                    // InternalKdl.g:749:8: (lv_multiple_5_0= 'multiple' )
                                    // InternalKdl.g:750:9: lv_multiple_5_0= 'multiple'
                                    {
                                    lv_multiple_5_0=(Token)match(input,38,FOLLOW_16); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_multiple_5_0, grammarAccess.getActorDefinitionAccess().getMultipleMultipleKeyword_0_2_1_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									setWithLastConsumed(current, "multiple", true, "multiple");
                                      								
                                    }

                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:763:7: ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? )
                                    {
                                    // InternalKdl.g:763:7: ( ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )? )
                                    // InternalKdl.g:764:8: ( (lv_arity_6_0= RULE_INT ) ) ( (lv_minimum_7_0= '+' ) )?
                                    {
                                    // InternalKdl.g:764:8: ( (lv_arity_6_0= RULE_INT ) )
                                    // InternalKdl.g:765:9: (lv_arity_6_0= RULE_INT )
                                    {
                                    // InternalKdl.g:765:9: (lv_arity_6_0= RULE_INT )
                                    // InternalKdl.g:766:10: lv_arity_6_0= RULE_INT
                                    {
                                    lv_arity_6_0=(Token)match(input,RULE_INT,FOLLOW_19); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      										newLeafNode(lv_arity_6_0, grammarAccess.getActorDefinitionAccess().getArityINTTerminalRuleCall_0_2_1_1_1_0_0());
                                      									
                                    }
                                    if ( state.backtracking==0 ) {

                                      										if (current==null) {
                                      											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      										}
                                      										setWithLastConsumed(
                                      											current,
                                      											"arity",
                                      											lv_arity_6_0,
                                      											"org.eclipse.xtext.common.Terminals.INT");
                                      									
                                    }

                                    }


                                    }

                                    // InternalKdl.g:782:8: ( (lv_minimum_7_0= '+' ) )?
                                    int alt13=2;
                                    int LA13_0 = input.LA(1);

                                    if ( (LA13_0==39) ) {
                                        alt13=1;
                                    }
                                    switch (alt13) {
                                        case 1 :
                                            // InternalKdl.g:783:9: (lv_minimum_7_0= '+' )
                                            {
                                            // InternalKdl.g:783:9: (lv_minimum_7_0= '+' )
                                            // InternalKdl.g:784:10: lv_minimum_7_0= '+'
                                            {
                                            lv_minimum_7_0=(Token)match(input,39,FOLLOW_16); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              										newLeafNode(lv_minimum_7_0, grammarAccess.getActorDefinitionAccess().getMinimumPlusSignKeyword_0_2_1_1_1_1_0());
                                              									
                                            }
                                            if ( state.backtracking==0 ) {

                                              										if (current==null) {
                                              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                              										}
                                              										setWithLastConsumed(current, "minimum", true, "+");
                                              									
                                            }

                                            }


                                            }
                                            break;

                                    }


                                    }


                                    }
                                    break;

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:800:4: ( (lv_parameter_8_0= 'parameter' ) )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==40) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKdl.g:801:5: (lv_parameter_8_0= 'parameter' )
                            {
                            // InternalKdl.g:801:5: (lv_parameter_8_0= 'parameter' )
                            // InternalKdl.g:802:6: lv_parameter_8_0= 'parameter'
                            {
                            lv_parameter_8_0=(Token)match(input,40,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_parameter_8_0, grammarAccess.getActorDefinitionAccess().getParameterParameterKeyword_0_3_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "parameter", true, "parameter");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:814:4: ( (lv_type_9_0= ruleACTOR ) )
                    // InternalKdl.g:815:5: (lv_type_9_0= ruleACTOR )
                    {
                    // InternalKdl.g:815:5: (lv_type_9_0= ruleACTOR )
                    // InternalKdl.g:816:6: lv_type_9_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_0_4_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
                    lv_type_9_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_9_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:833:4: ( ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) ) )
                    // InternalKdl.g:834:5: ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) )
                    {
                    // InternalKdl.g:834:5: ( (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING ) )
                    // InternalKdl.g:835:6: (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING )
                    {
                    // InternalKdl.g:835:6: (lv_name_10_1= RULE_LOWERCASE_ID | lv_name_10_2= RULE_LOWERCASE_DASHID | lv_name_10_3= RULE_STRING )
                    int alt17=3;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt17=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt17=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt17=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 17, 0, input);

                        throw nvae;
                    }

                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:836:7: lv_name_10_1= RULE_LOWERCASE_ID
                            {
                            lv_name_10_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_10_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_0_5_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_10_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:851:7: lv_name_10_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_10_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_10_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_0_5_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_10_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:866:7: lv_name_10_3= RULE_STRING
                            {
                            lv_name_10_3=(Token)match(input,RULE_STRING,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_10_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_0_5_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_10_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:883:4: (otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )* )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==41) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKdl.g:884:5: otherlv_11= 'for' ( (lv_targets_12_0= ruleTARGET ) ) (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )*
                            {
                            otherlv_11=(Token)match(input,41,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getActorDefinitionAccess().getForKeyword_0_6_0());
                              				
                            }
                            // InternalKdl.g:888:5: ( (lv_targets_12_0= ruleTARGET ) )
                            // InternalKdl.g:889:6: (lv_targets_12_0= ruleTARGET )
                            {
                            // InternalKdl.g:889:6: (lv_targets_12_0= ruleTARGET )
                            // InternalKdl.g:890:7: lv_targets_12_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_6_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_targets_12_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_12_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:907:5: (otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) ) )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==30) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // InternalKdl.g:908:6: otherlv_13= ',' ( (lv_targets_14_0= ruleTARGET ) )
                            	    {
                            	    otherlv_13=(Token)match(input,30,FOLLOW_22); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_13, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_6_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:912:6: ( (lv_targets_14_0= ruleTARGET ) )
                            	    // InternalKdl.g:913:7: (lv_targets_14_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:913:7: (lv_targets_14_0= ruleTARGET )
                            	    // InternalKdl.g:914:8: lv_targets_14_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_6_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_23);
                            	    lv_targets_14_0=ruleTARGET();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"targets",
                            	      									lv_targets_14_0,
                            	      									"org.integratedmodelling.kdl.Kdl.TARGET");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop18;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:933:4: ( (lv_docstring_15_0= RULE_STRING ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==RULE_STRING) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalKdl.g:934:5: (lv_docstring_15_0= RULE_STRING )
                            {
                            // InternalKdl.g:934:5: (lv_docstring_15_0= RULE_STRING )
                            // InternalKdl.g:935:6: lv_docstring_15_0= RULE_STRING
                            {
                            lv_docstring_15_0=(Token)match(input,RULE_STRING,FOLLOW_24); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_15_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_0_7_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_15_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:951:4: (otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}' )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==42) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // InternalKdl.g:952:5: otherlv_16= '{' ( (lv_body_17_0= ruleDataflowBody ) ) otherlv_18= '}'
                            {
                            otherlv_16=(Token)match(input,42,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_16, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_0_8_0());
                              				
                            }
                            // InternalKdl.g:956:5: ( (lv_body_17_0= ruleDataflowBody ) )
                            // InternalKdl.g:957:6: (lv_body_17_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:957:6: (lv_body_17_0= ruleDataflowBody )
                            // InternalKdl.g:958:7: lv_body_17_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_0_8_1_0());
                              						
                            }
                            pushFollow(FOLLOW_26);
                            lv_body_17_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_17_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_18=(Token)match(input,43,FOLLOW_27); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_18, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_0_8_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:980:4: (otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) ) )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==44) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalKdl.g:981:5: otherlv_19= 'as' ( (lv_localName_20_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_19=(Token)match(input,44,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_19, grammarAccess.getActorDefinitionAccess().getAsKeyword_0_9_0());
                              				
                            }
                            // InternalKdl.g:985:5: ( (lv_localName_20_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:986:6: (lv_localName_20_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:986:6: (lv_localName_20_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:987:7: lv_localName_20_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_20_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_20_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_0_9_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_20_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1004:4: (otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )* )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==45) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalKdl.g:1005:5: otherlv_21= 'over' ( (lv_coverage_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )*
                            {
                            otherlv_21=(Token)match(input,45,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_21, grammarAccess.getActorDefinitionAccess().getOverKeyword_0_10_0());
                              				
                            }
                            // InternalKdl.g:1009:5: ( (lv_coverage_22_0= ruleFunction ) )
                            // InternalKdl.g:1010:6: (lv_coverage_22_0= ruleFunction )
                            {
                            // InternalKdl.g:1010:6: (lv_coverage_22_0= ruleFunction )
                            // InternalKdl.g:1011:7: lv_coverage_22_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_10_1_0());
                              						
                            }
                            pushFollow(FOLLOW_29);
                            lv_coverage_22_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_22_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1028:5: (otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) ) )*
                            loop23:
                            do {
                                int alt23=2;
                                int LA23_0 = input.LA(1);

                                if ( (LA23_0==30) ) {
                                    alt23=1;
                                }


                                switch (alt23) {
                            	case 1 :
                            	    // InternalKdl.g:1029:6: otherlv_23= ',' ( (lv_coverage_24_0= ruleFunction ) )
                            	    {
                            	    otherlv_23=(Token)match(input,30,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_23, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_10_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1033:6: ( (lv_coverage_24_0= ruleFunction ) )
                            	    // InternalKdl.g:1034:7: (lv_coverage_24_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:1034:7: (lv_coverage_24_0= ruleFunction )
                            	    // InternalKdl.g:1035:8: lv_coverage_24_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_10_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_29);
                            	    lv_coverage_24_0=ruleFunction();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"coverage",
                            	      									lv_coverage_24_0,
                            	      									"org.integratedmodelling.kdl.Kdl.Function");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop23;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:1056:3: ( ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )? )
                    {
                    // InternalKdl.g:1056:3: ( ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )? )
                    // InternalKdl.g:1057:4: ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) ) ( (lv_parameter_27_0= 'input' ) ) ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) ) (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_33_0= RULE_STRING ) ) (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )? (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )? ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )?
                    {
                    // InternalKdl.g:1057:4: ( ( (lv_optional_25_0= 'optional' ) ) | ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) ) )
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==36) ) {
                        alt26=1;
                    }
                    else if ( ((LA26_0>=46 && LA26_0<=50)) ) {
                        alt26=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);

                        throw nvae;
                    }
                    switch (alt26) {
                        case 1 :
                            // InternalKdl.g:1058:5: ( (lv_optional_25_0= 'optional' ) )
                            {
                            // InternalKdl.g:1058:5: ( (lv_optional_25_0= 'optional' ) )
                            // InternalKdl.g:1059:6: (lv_optional_25_0= 'optional' )
                            {
                            // InternalKdl.g:1059:6: (lv_optional_25_0= 'optional' )
                            // InternalKdl.g:1060:7: lv_optional_25_0= 'optional'
                            {
                            lv_optional_25_0=(Token)match(input,36,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_optional_25_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "optional", true, "optional");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1073:5: ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) )
                            {
                            // InternalKdl.g:1073:5: ( ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) ) )
                            // InternalKdl.g:1074:6: ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) )
                            {
                            // InternalKdl.g:1074:6: ( (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' ) )
                            // InternalKdl.g:1075:7: (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' )
                            {
                            // InternalKdl.g:1075:7: (lv_type_26_1= 'number' | lv_type_26_2= 'boolean' | lv_type_26_3= 'text' | lv_type_26_4= 'list' | lv_type_26_5= 'enum' )
                            int alt25=5;
                            switch ( input.LA(1) ) {
                            case 46:
                                {
                                alt25=1;
                                }
                                break;
                            case 47:
                                {
                                alt25=2;
                                }
                                break;
                            case 48:
                                {
                                alt25=3;
                                }
                                break;
                            case 49:
                                {
                                alt25=4;
                                }
                                break;
                            case 50:
                                {
                                alt25=5;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 25, 0, input);

                                throw nvae;
                            }

                            switch (alt25) {
                                case 1 :
                                    // InternalKdl.g:1076:8: lv_type_26_1= 'number'
                                    {
                                    lv_type_26_1=(Token)match(input,46,FOLLOW_30); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_26_1, grammarAccess.getActorDefinitionAccess().getTypeNumberKeyword_1_0_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_26_1, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1087:8: lv_type_26_2= 'boolean'
                                    {
                                    lv_type_26_2=(Token)match(input,47,FOLLOW_30); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_26_2, grammarAccess.getActorDefinitionAccess().getTypeBooleanKeyword_1_0_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_26_2, null);
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1098:8: lv_type_26_3= 'text'
                                    {
                                    lv_type_26_3=(Token)match(input,48,FOLLOW_30); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_26_3, grammarAccess.getActorDefinitionAccess().getTypeTextKeyword_1_0_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_26_3, null);
                                      							
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:1109:8: lv_type_26_4= 'list'
                                    {
                                    lv_type_26_4=(Token)match(input,49,FOLLOW_30); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_26_4, grammarAccess.getActorDefinitionAccess().getTypeListKeyword_1_0_1_0_3());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_26_4, null);
                                      							
                                    }

                                    }
                                    break;
                                case 5 :
                                    // InternalKdl.g:1120:8: lv_type_26_5= 'enum'
                                    {
                                    lv_type_26_5=(Token)match(input,50,FOLLOW_30); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_26_5, grammarAccess.getActorDefinitionAccess().getTypeEnumKeyword_1_0_1_0_4());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_26_5, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1134:4: ( (lv_parameter_27_0= 'input' ) )
                    // InternalKdl.g:1135:5: (lv_parameter_27_0= 'input' )
                    {
                    // InternalKdl.g:1135:5: (lv_parameter_27_0= 'input' )
                    // InternalKdl.g:1136:6: lv_parameter_27_0= 'input'
                    {
                    lv_parameter_27_0=(Token)match(input,51,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_parameter_27_0, grammarAccess.getActorDefinitionAccess().getParameterInputKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(current, "parameter", true, "input");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1148:4: ( ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) ) )
                    // InternalKdl.g:1149:5: ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) )
                    {
                    // InternalKdl.g:1149:5: ( (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING ) )
                    // InternalKdl.g:1150:6: (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING )
                    {
                    // InternalKdl.g:1150:6: (lv_name_28_1= RULE_LOWERCASE_ID | lv_name_28_2= RULE_LOWERCASE_DASHID | lv_name_28_3= RULE_STRING )
                    int alt27=3;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt27=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt27=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt27=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }

                    switch (alt27) {
                        case 1 :
                            // InternalKdl.g:1151:7: lv_name_28_1= RULE_LOWERCASE_ID
                            {
                            lv_name_28_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_31); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_28_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_2_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_28_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1166:7: lv_name_28_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_28_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_31); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_28_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_1_2_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_28_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1181:7: lv_name_28_3= RULE_STRING
                            {
                            lv_name_28_3=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_28_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_1_2_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_28_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:1198:4: (otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )* )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==52) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalKdl.g:1199:5: otherlv_29= 'values' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )*
                            {
                            otherlv_29=(Token)match(input,52,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_29, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_3_0());
                              				
                            }
                            // InternalKdl.g:1203:5: ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) )
                            // InternalKdl.g:1204:6: (lv_enumValues_30_0= RULE_UPPERCASE_ID )
                            {
                            // InternalKdl.g:1204:6: (lv_enumValues_30_0= RULE_UPPERCASE_ID )
                            // InternalKdl.g:1205:7: lv_enumValues_30_0= RULE_UPPERCASE_ID
                            {
                            lv_enumValues_30_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_enumValues_30_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							addWithLastConsumed(
                              								current,
                              								"enumValues",
                              								lv_enumValues_30_0,
                              								"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1221:5: (otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) ) )*
                            loop28:
                            do {
                                int alt28=2;
                                int LA28_0 = input.LA(1);

                                if ( (LA28_0==30) ) {
                                    alt28=1;
                                }


                                switch (alt28) {
                            	case 1 :
                            	    // InternalKdl.g:1222:6: otherlv_31= ',' ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) )
                            	    {
                            	    otherlv_31=(Token)match(input,30,FOLLOW_32); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_31, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_3_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1226:6: ( (lv_enumValues_32_0= RULE_UPPERCASE_ID ) )
                            	    // InternalKdl.g:1227:7: (lv_enumValues_32_0= RULE_UPPERCASE_ID )
                            	    {
                            	    // InternalKdl.g:1227:7: (lv_enumValues_32_0= RULE_UPPERCASE_ID )
                            	    // InternalKdl.g:1228:8: lv_enumValues_32_0= RULE_UPPERCASE_ID
                            	    {
                            	    lv_enumValues_32_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_33); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								newLeafNode(lv_enumValues_32_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_2_1_0());
                            	      							
                            	    }
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								addWithLastConsumed(
                            	      									current,
                            	      									"enumValues",
                            	      									lv_enumValues_32_0,
                            	      									"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop28;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:1246:4: ( (lv_docstring_33_0= RULE_STRING ) )
                    // InternalKdl.g:1247:5: (lv_docstring_33_0= RULE_STRING )
                    {
                    // InternalKdl.g:1247:5: (lv_docstring_33_0= RULE_STRING )
                    // InternalKdl.g:1248:6: lv_docstring_33_0= RULE_STRING
                    {
                    lv_docstring_33_0=(Token)match(input,RULE_STRING,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_docstring_33_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_4_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"docstring",
                      							lv_docstring_33_0,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1264:4: (otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) ) )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==53) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalKdl.g:1265:5: otherlv_34= 'default' ( (lv_default_35_0= ruleValue ) )
                            {
                            otherlv_34=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_34, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_5_0());
                              				
                            }
                            // InternalKdl.g:1269:5: ( (lv_default_35_0= ruleValue ) )
                            // InternalKdl.g:1270:6: (lv_default_35_0= ruleValue )
                            {
                            // InternalKdl.g:1270:6: (lv_default_35_0= ruleValue )
                            // InternalKdl.g:1271:7: lv_default_35_0= ruleValue
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_36);
                            lv_default_35_0=ruleValue();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"default",
                              								lv_default_35_0,
                              								"org.integratedmodelling.kdl.Kdl.Value");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1289:4: (otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}' )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==42) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalKdl.g:1290:5: otherlv_36= '{' ( (lv_body_37_0= ruleDataflowBody ) ) otherlv_38= '}'
                            {
                            otherlv_36=(Token)match(input,42,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_36, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_6_0());
                              				
                            }
                            // InternalKdl.g:1294:5: ( (lv_body_37_0= ruleDataflowBody ) )
                            // InternalKdl.g:1295:6: (lv_body_37_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1295:6: (lv_body_37_0= ruleDataflowBody )
                            // InternalKdl.g:1296:7: lv_body_37_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_6_1_0());
                              						
                            }
                            pushFollow(FOLLOW_26);
                            lv_body_37_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_37_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_38=(Token)match(input,43,FOLLOW_37); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_38, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_6_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1318:4: ( (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) ) | (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) ) | (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) ) )?
                    int alt32=4;
                    switch ( input.LA(1) ) {
                        case 54:
                            {
                            alt32=1;
                            }
                            break;
                        case 55:
                            {
                            alt32=2;
                            }
                            break;
                        case 56:
                            {
                            alt32=3;
                            }
                            break;
                    }

                    switch (alt32) {
                        case 1 :
                            // InternalKdl.g:1319:5: (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1319:5: (otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) ) )
                            // InternalKdl.g:1320:6: otherlv_39= 'minimum' ( (lv_rangeMin_40_0= ruleNumber ) )
                            {
                            otherlv_39=(Token)match(input,54,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_39, grammarAccess.getActorDefinitionAccess().getMinimumKeyword_1_7_0_0());
                              					
                            }
                            // InternalKdl.g:1324:6: ( (lv_rangeMin_40_0= ruleNumber ) )
                            // InternalKdl.g:1325:7: (lv_rangeMin_40_0= ruleNumber )
                            {
                            // InternalKdl.g:1325:7: (lv_rangeMin_40_0= ruleNumber )
                            // InternalKdl.g:1326:8: lv_rangeMin_40_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_7_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMin_40_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMin",
                              									lv_rangeMin_40_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1345:5: (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1345:5: (otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) ) )
                            // InternalKdl.g:1346:6: otherlv_41= 'maximum' ( (lv_rangeMax_42_0= ruleNumber ) )
                            {
                            otherlv_41=(Token)match(input,55,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_41, grammarAccess.getActorDefinitionAccess().getMaximumKeyword_1_7_1_0());
                              					
                            }
                            // InternalKdl.g:1350:6: ( (lv_rangeMax_42_0= ruleNumber ) )
                            // InternalKdl.g:1351:7: (lv_rangeMax_42_0= ruleNumber )
                            {
                            // InternalKdl.g:1351:7: (lv_rangeMax_42_0= ruleNumber )
                            // InternalKdl.g:1352:8: lv_rangeMax_42_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_7_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMax_42_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMax",
                              									lv_rangeMax_42_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1371:5: (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1371:5: (otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) ) )
                            // InternalKdl.g:1372:6: otherlv_43= 'range' ( (lv_rangeMin_44_0= ruleNumber ) ) otherlv_45= 'to' ( (lv_rangeMax_46_0= ruleNumber ) )
                            {
                            otherlv_43=(Token)match(input,56,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_43, grammarAccess.getActorDefinitionAccess().getRangeKeyword_1_7_2_0());
                              					
                            }
                            // InternalKdl.g:1376:6: ( (lv_rangeMin_44_0= ruleNumber ) )
                            // InternalKdl.g:1377:7: (lv_rangeMin_44_0= ruleNumber )
                            {
                            // InternalKdl.g:1377:7: (lv_rangeMin_44_0= ruleNumber )
                            // InternalKdl.g:1378:8: lv_rangeMin_44_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_7_2_1_0());
                              							
                            }
                            pushFollow(FOLLOW_39);
                            lv_rangeMin_44_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMin",
                              									lv_rangeMin_44_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_45=(Token)match(input,57,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_45, grammarAccess.getActorDefinitionAccess().getToKeyword_1_7_2_2());
                              					
                            }
                            // InternalKdl.g:1399:6: ( (lv_rangeMax_46_0= ruleNumber ) )
                            // InternalKdl.g:1400:7: (lv_rangeMax_46_0= ruleNumber )
                            {
                            // InternalKdl.g:1400:7: (lv_rangeMax_46_0= ruleNumber )
                            // InternalKdl.g:1401:8: lv_rangeMax_46_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_7_2_3_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMax_46_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMax",
                              									lv_rangeMax_46_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActorDefinition"


    // $ANTLR start "entryRuleDataflowBody"
    // InternalKdl.g:1425:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1429:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1430:2: iv_ruleDataflowBody= ruleDataflowBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDataflowBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDataflowBody=ruleDataflowBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDataflowBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleDataflowBody"


    // $ANTLR start "ruleDataflowBody"
    // InternalKdl.g:1439:1: ruleDataflowBody returns [EObject current=null] : ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
    public final EObject ruleDataflowBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_dataflows_1_0 = null;

        AntlrDatatypeRuleToken lv_geometry_4_0 = null;

        EObject lv_units_6_0 = null;

        EObject lv_computations_7_0 = null;

        EObject lv_metadata_9_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_11_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1448:2: ( ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1449:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1449:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1450:3: () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1450:3: ()
            // InternalKdl.g:1451:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getDataflowBodyAccess().getDataflowBodyAction_0(),
              					current);
              			
            }

            }

            // InternalKdl.g:1460:3: ( (lv_dataflows_1_0= ruleActorDefinition ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_ANNOTATION_ID||(LA34_0>=34 && LA34_0<=37)||LA34_0==40||(LA34_0>=46 && LA34_0<=50)||(LA34_0>=64 && LA34_0<=74)) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalKdl.g:1461:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1461:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    // InternalKdl.g:1462:5: lv_dataflows_1_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDataflowBodyAccess().getDataflowsActorDefinitionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_40);
            	    lv_dataflows_1_0=ruleActorDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      					}
            	      					add(
            	      						current,
            	      						"dataflows",
            	      						lv_dataflows_1_0,
            	      						"org.integratedmodelling.kdl.Kdl.ActorDefinition");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            // InternalKdl.g:1479:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1480:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1480:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1481:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());
            // InternalKdl.g:1484:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1485:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1485:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+
            int cnt37=0;
            loop37:
            do {
                int alt37=5;
                alt37 = dfa37.predict(input);
                switch (alt37) {
            	case 1 :
            	    // InternalKdl.g:1486:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1486:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1487:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKdl.g:1487:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1488:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
            	    // InternalKdl.g:1491:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1491:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1491:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1491:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
            	    {
            	    otherlv_3=(Token)match(input,58,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_3, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1495:9: ( (lv_geometry_4_0= ruleGeometry ) )
            	    // InternalKdl.g:1496:10: (lv_geometry_4_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1496:10: (lv_geometry_4_0= ruleGeometry )
            	    // InternalKdl.g:1497:11: lv_geometry_4_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_geometry_4_0=ruleGeometry();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      											}
            	      											set(
            	      												current,
            	      												"geometry",
            	      												lv_geometry_4_0,
            	      												"org.integratedmodelling.kdl.Kdl.Geometry");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKdl.g:1520:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1520:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
            	    // InternalKdl.g:1521:5: {...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKdl.g:1521:109: ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
            	    // InternalKdl.g:1522:6: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
            	    // InternalKdl.g:1525:9: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
            	    // InternalKdl.g:1525:10: {...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1525:19: (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
            	    // InternalKdl.g:1525:20: otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) )
            	    {
            	    otherlv_5=(Token)match(input,59,FOLLOW_42); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_5, grammarAccess.getDataflowBodyAccess().getUnitsKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKdl.g:1529:9: ( (lv_units_6_0= ruleUnit ) )
            	    // InternalKdl.g:1530:10: (lv_units_6_0= ruleUnit )
            	    {
            	    // InternalKdl.g:1530:10: (lv_units_6_0= ruleUnit )
            	    // InternalKdl.g:1531:11: lv_units_6_0= ruleUnit
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getUnitsUnitParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_units_6_0=ruleUnit();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      											}
            	      											set(
            	      												current,
            	      												"units",
            	      												lv_units_6_0,
            	      												"org.integratedmodelling.kdl.Kdl.Unit");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKdl.g:1554:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
            	    {
            	    // InternalKdl.g:1554:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
            	    // InternalKdl.g:1555:5: {...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKdl.g:1555:109: ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
            	    // InternalKdl.g:1556:6: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
            	    // InternalKdl.g:1559:9: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
            	    // InternalKdl.g:1559:10: {...}? => ( (lv_computations_7_0= ruleComputation ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1559:19: ( (lv_computations_7_0= ruleComputation ) )
            	    // InternalKdl.g:1559:20: (lv_computations_7_0= ruleComputation )
            	    {
            	    // InternalKdl.g:1559:20: (lv_computations_7_0= ruleComputation )
            	    // InternalKdl.g:1560:10: lv_computations_7_0= ruleComputation
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_2_2_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_computations_7_0=ruleComputation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      										}
            	      										set(
            	      											current,
            	      											"computations",
            	      											lv_computations_7_0,
            	      											"org.integratedmodelling.kdl.Kdl.Computation");
            	      										afterParserOrEnumRuleCall();
            	      									
            	    }

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKdl.g:1582:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1582:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
            	    // InternalKdl.g:1583:5: {...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKdl.g:1583:109: ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
            	    // InternalKdl.g:1584:6: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3);
            	    // InternalKdl.g:1587:9: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
            	    // InternalKdl.g:1587:10: {...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1587:19: ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
            	    // InternalKdl.g:1587:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
            	    {
            	    // InternalKdl.g:1587:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )?
            	    int alt35=2;
            	    int LA35_0 = input.LA(1);

            	    if ( (LA35_0==60) ) {
            	        int LA35_1 = input.LA(2);

            	        if ( (synpred58_InternalKdl()) ) {
            	            alt35=1;
            	        }
            	    }
            	    switch (alt35) {
            	        case 1 :
            	            // InternalKdl.g:1588:10: otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) )
            	            {
            	            otherlv_8=(Token)match(input,60,FOLLOW_43); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_8, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_2_3_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1592:10: ( (lv_metadata_9_0= ruleMetadata ) )
            	            // InternalKdl.g:1593:11: (lv_metadata_9_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1593:11: (lv_metadata_9_0= ruleMetadata )
            	            // InternalKdl.g:1594:12: lv_metadata_9_0= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_3_0_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_41);
            	            lv_metadata_9_0=ruleMetadata();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	              												}
            	              												set(
            	              													current,
            	              													"metadata",
            	              													lv_metadata_9_0,
            	              													"org.integratedmodelling.kdl.Kdl.Metadata");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }

            	    // InternalKdl.g:1612:9: (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
            	    int alt36=2;
            	    int LA36_0 = input.LA(1);

            	    if ( (LA36_0==61) ) {
            	        int LA36_1 = input.LA(2);

            	        if ( (synpred59_InternalKdl()) ) {
            	            alt36=1;
            	        }
            	    }
            	    switch (alt36) {
            	        case 1 :
            	            // InternalKdl.g:1613:10: otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) )
            	            {
            	            otherlv_10=(Token)match(input,61,FOLLOW_3); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_10, grammarAccess.getDataflowBodyAccess().getClassKeyword_2_3_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1617:10: ( (lv_javaClass_11_0= ruleJavaClass ) )
            	            // InternalKdl.g:1618:11: (lv_javaClass_11_0= ruleJavaClass )
            	            {
            	            // InternalKdl.g:1618:11: (lv_javaClass_11_0= ruleJavaClass )
            	            // InternalKdl.g:1619:12: lv_javaClass_11_0= ruleJavaClass
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_2_3_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_41);
            	            lv_javaClass_11_0=ruleJavaClass();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	              												}
            	              												set(
            	              													current,
            	              													"javaClass",
            	              													lv_javaClass_11_0,
            	              													"org.integratedmodelling.kdl.Kdl.JavaClass");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt37 >= 1 ) break loop37;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(37, input);
                        throw eee;
                }
                cnt37++;
            } while (true);

            if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {
                if (state.backtracking>0) {state.failed=true; return current;}
                throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2())");
            }

            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleDataflowBody"


    // $ANTLR start "entryRuleComputation"
    // InternalKdl.g:1658:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1658:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1659:2: iv_ruleComputation= ruleComputation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getComputationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleComputation=ruleComputation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComputation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComputation"


    // $ANTLR start "ruleComputation"
    // InternalKdl.g:1665:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1671:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1672:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1672:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1673:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,62,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1677:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1678:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1678:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1679:5: lv_functions_1_0= ruleFunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_29);
            lv_functions_1_0=ruleFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getComputationRule());
              					}
              					add(
              						current,
              						"functions",
              						lv_functions_1_0,
              						"org.integratedmodelling.kdl.Kdl.Function");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:1696:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==30) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalKdl.g:1697:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,30,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1701:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1702:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1702:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1703:6: lv_functions_3_0= ruleFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_29);
            	    lv_functions_3_0=ruleFunction();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getComputationRule());
            	      						}
            	      						add(
            	      							current,
            	      							"functions",
            	      							lv_functions_3_0,
            	      							"org.integratedmodelling.kdl.Kdl.Function");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComputation"


    // $ANTLR start "entryRuleGeometry"
    // InternalKdl.g:1725:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1725:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1726:2: iv_ruleGeometry= ruleGeometry EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGeometryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGeometry=ruleGeometry();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGeometry.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGeometry"


    // $ANTLR start "ruleGeometry"
    // InternalKdl.g:1732:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1738:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1739:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1739:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==63) ) {
                alt40=1;
            }
            else if ( (LA40_0==RULE_SHAPE) ) {
                alt40=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // InternalKdl.g:1740:3: kw= '*'
                    {
                    kw=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1746:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1746:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1747:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_29); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:1754:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==30) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // InternalKdl.g:1755:5: kw= ',' this_SHAPE_3= RULE_SHAPE
                    	    {
                    	    kw=(Token)match(input,30,FOLLOW_44); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getGeometryAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    this_SHAPE_3=(Token)match(input,RULE_SHAPE,FOLLOW_29); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_SHAPE_3);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_SHAPE_3, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGeometry"


    // $ANTLR start "entryRuleParameter"
    // InternalKdl.g:1773:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:1773:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:1774:2: iv_ruleParameter= ruleParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // InternalKdl.g:1780:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1786:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:1787:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:1787:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:1788:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:1788:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:1789:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:1789:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:1790:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getParameterAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getParameterRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKdl.g:1806:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:1807:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:1807:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:1808:5: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_45);
            lv_value_1_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParameterRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.integratedmodelling.kdl.Kdl.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:1825:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_STRING) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalKdl.g:1826:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:1826:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:1827:5: lv_docstring_2_0= RULE_STRING
                    {
                    lv_docstring_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_docstring_2_0, grammarAccess.getParameterAccess().getDocstringSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getParameterRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"docstring",
                      						lv_docstring_2_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleACTOR"
    // InternalKdl.g:1847:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:1847:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:1848:2: iv_ruleACTOR= ruleACTOR EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getACTORRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleACTOR=ruleACTOR();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleACTOR.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleACTOR"


    // $ANTLR start "ruleACTOR"
    // InternalKdl.g:1854:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:1860:2: ( (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) )
            // InternalKdl.g:1861:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            {
            // InternalKdl.g:1861:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            int alt42=14;
            switch ( input.LA(1) ) {
            case 64:
                {
                alt42=1;
                }
                break;
            case 65:
                {
                alt42=2;
                }
                break;
            case 66:
                {
                alt42=3;
                }
                break;
            case 46:
                {
                alt42=4;
                }
                break;
            case 67:
                {
                alt42=5;
                }
                break;
            case 47:
                {
                alt42=6;
                }
                break;
            case 48:
                {
                alt42=7;
                }
                break;
            case 68:
                {
                alt42=8;
                }
                break;
            case 69:
                {
                alt42=9;
                }
                break;
            case 70:
                {
                alt42=10;
                }
                break;
            case 71:
                {
                alt42=11;
                }
                break;
            case 72:
                {
                alt42=12;
                }
                break;
            case 73:
                {
                alt42=13;
                }
                break;
            case 74:
                {
                alt42=14;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // InternalKdl.g:1862:3: kw= 'object'
                    {
                    kw=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1868:3: kw= 'process'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:1874:3: kw= 'value'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:1880:3: kw= 'number'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:1886:3: kw= 'concept'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:1892:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:1898:3: kw= 'text'
                    {
                    kw=(Token)match(input,48,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:1904:3: kw= 'extent'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:1910:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:1916:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:1922:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:1928:3: kw= 'contextualizer'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getContextualizerKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:1934:3: kw= 'void'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:1940:3: kw= 'partition'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getPartitionKeyword_13());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleACTOR"


    // $ANTLR start "entryRuleTARGET"
    // InternalKdl.g:1949:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:1949:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:1950:2: iv_ruleTARGET= ruleTARGET EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTARGETRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTARGET=ruleTARGET();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTARGET.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTARGET"


    // $ANTLR start "ruleTARGET"
    // InternalKdl.g:1956:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:1962:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' ) )
            // InternalKdl.g:1963:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' )
            {
            // InternalKdl.g:1963:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' )
            int alt43=4;
            switch ( input.LA(1) ) {
            case 75:
                {
                alt43=1;
                }
                break;
            case 76:
                {
                alt43=2;
                }
                break;
            case 77:
                {
                alt43=3;
                }
                break;
            case 78:
                {
                alt43=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // InternalKdl.g:1964:3: kw= 'models'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1970:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:1976:3: kw= 'observers'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getObserversKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:1982:3: kw= 'definitions'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getDefinitionsKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTARGET"


    // $ANTLR start "entryRuleClassifierRHS"
    // InternalKdl.g:1991:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:1991:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:1992:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassifierRHSRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassifierRHS=ruleClassifierRHS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassifierRHS; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleClassifierRHS"


    // $ANTLR start "ruleClassifierRHS"
    // InternalKdl.g:1998:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
    public final EObject ruleClassifierRHS() throws RecognitionException {
        EObject current = null;

        Token lv_boolean_0_0=null;
        Token lv_boolean_1_0=null;
        Token lv_leftLimit_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_rightLimit_7_0=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token lv_string_12_0=null;
        Token otherlv_14=null;
        Token lv_toResolve_15_0=null;
        Token otherlv_16=null;
        Token lv_toResolve_17_0=null;
        Token otherlv_18=null;
        Token lv_nodata_21_0=null;
        Token lv_star_22_0=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;

        EObject lv_num_9_0 = null;

        EObject lv_set_11_0 = null;

        EObject lv_map_13_0 = null;

        EObject lv_op_19_0 = null;

        EObject lv_expression_20_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2004:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:2005:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:2005:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt48=10;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // InternalKdl.g:2006:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:2006:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==79) ) {
                        alt44=1;
                    }
                    else if ( (LA44_0==80) ) {
                        alt44=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 44, 0, input);

                        throw nvae;
                    }
                    switch (alt44) {
                        case 1 :
                            // InternalKdl.g:2007:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:2007:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:2008:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:2008:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:2009:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_0_0, grammarAccess.getClassifierRHSAccess().getBooleanTrueKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getClassifierRHSRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_0_0, "true");
                              					
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2022:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:2022:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:2023:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:2023:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:2024:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_1_0, grammarAccess.getClassifierRHSAccess().getBooleanFalseKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getClassifierRHSRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_1_0, "false");
                              					
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2038:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:2038:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:2039:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:2039:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:2040:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:2040:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:2041:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
                    lv_int0_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_2_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2058:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt45=3;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==81) ) {
                        alt45=1;
                    }
                    else if ( (LA45_0==82) ) {
                        alt45=2;
                    }
                    switch (alt45) {
                        case 1 :
                            // InternalKdl.g:2059:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2059:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:2060:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:2060:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:2061:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,81,FOLLOW_39); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_3_0, grammarAccess.getClassifierRHSAccess().getLeftLimitInclusiveKeyword_1_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getClassifierRHSRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_3_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2074:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,82,FOLLOW_39); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:2079:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:2080:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,57,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:2086:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:2087:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:2091:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:2092:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
                    lv_int1_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_6_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2109:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt46=3;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==81) ) {
                        alt46=1;
                    }
                    else if ( (LA46_0==82) ) {
                        alt46=2;
                    }
                    switch (alt46) {
                        case 1 :
                            // InternalKdl.g:2110:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2110:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:2111:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:2111:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:2112:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_7_0, grammarAccess.getClassifierRHSAccess().getRightLimitInclusiveKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getClassifierRHSRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_7_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2125:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:2132:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2132:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:2133:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:2133:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:2134:5: lv_num_9_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getClassifierRHSAccess().getNumNumberParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_9_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_9_0,
                      						"org.integratedmodelling.kdl.Kdl.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:2152:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:2152:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:2153:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,83,FOLLOW_48); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:2157:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:2158:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:2158:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:2159:6: lv_set_11_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getSetListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_11_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_11_0,
                      							"org.integratedmodelling.kdl.Kdl.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:2178:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2178:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:2179:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:2179:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:2180:5: lv_string_12_0= RULE_STRING
                    {
                    lv_string_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_12_0, grammarAccess.getClassifierRHSAccess().getStringSTRINGTerminalRuleCall_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRHSRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_12_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:2197:3: ( (lv_map_13_0= ruleMap ) )
                    {
                    // InternalKdl.g:2197:3: ( (lv_map_13_0= ruleMap ) )
                    // InternalKdl.g:2198:4: (lv_map_13_0= ruleMap )
                    {
                    // InternalKdl.g:2198:4: (lv_map_13_0= ruleMap )
                    // InternalKdl.g:2199:5: lv_map_13_0= ruleMap
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getClassifierRHSAccess().getMapMapParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_map_13_0=ruleMap();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      					}
                      					set(
                      						current,
                      						"map",
                      						lv_map_13_0,
                      						"org.integratedmodelling.kdl.Kdl.Map");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKdl.g:2217:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:2217:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:2218:4: otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,32,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:2222:4: ( (lv_toResolve_15_0= RULE_STRING ) )
                    // InternalKdl.g:2223:5: (lv_toResolve_15_0= RULE_STRING )
                    {
                    // InternalKdl.g:2223:5: (lv_toResolve_15_0= RULE_STRING )
                    // InternalKdl.g:2224:6: lv_toResolve_15_0= RULE_STRING
                    {
                    lv_toResolve_15_0=(Token)match(input,RULE_STRING,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_toResolve_15_0, grammarAccess.getClassifierRHSAccess().getToResolveSTRINGTerminalRuleCall_6_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getClassifierRHSRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"toResolve",
                      							lv_toResolve_15_0,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2240:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==30) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalKdl.g:2241:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    {
                    	    // InternalKdl.g:2241:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:2242:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,30,FOLLOW_6); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:2248:5: ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    // InternalKdl.g:2249:6: (lv_toResolve_17_0= RULE_STRING )
                    	    {
                    	    // InternalKdl.g:2249:6: (lv_toResolve_17_0= RULE_STRING )
                    	    // InternalKdl.g:2250:7: lv_toResolve_17_0= RULE_STRING
                    	    {
                    	    lv_toResolve_17_0=(Token)match(input,RULE_STRING,FOLLOW_49); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							newLeafNode(lv_toResolve_17_0, grammarAccess.getClassifierRHSAccess().getToResolveSTRINGTerminalRuleCall_6_2_1_0());
                    	      						
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElement(grammarAccess.getClassifierRHSRule());
                    	      							}
                    	      							addWithLastConsumed(
                    	      								current,
                    	      								"toResolve",
                    	      								lv_toResolve_17_0,
                    	      								"org.eclipse.xtext.common.Terminals.STRING");
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);

                    otherlv_18=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getClassifierRHSAccess().getRightParenthesisKeyword_6_3());
                      			
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:2273:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2273:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:2274:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2274:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:2275:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:2275:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:2276:6: lv_op_19_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
                    lv_op_19_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_19_0,
                      							"org.integratedmodelling.kdl.Kdl.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2293:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:2294:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:2294:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:2295:6: lv_expression_20_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getExpressionNumberParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_20_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_20_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKdl.g:2314:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:2314:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:2315:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:2315:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:2316:5: lv_nodata_21_0= 'unknown'
                    {
                    lv_nodata_21_0=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nodata_21_0, grammarAccess.getClassifierRHSAccess().getNodataUnknownKeyword_8_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRHSRule());
                      					}
                      					setWithLastConsumed(current, "nodata", lv_nodata_21_0, "unknown");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKdl.g:2329:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:2329:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:2330:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:2330:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:2331:5: lv_star_22_0= '*'
                    {
                    lv_star_22_0=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_22_0, grammarAccess.getClassifierRHSAccess().getStarAsteriskKeyword_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRHSRule());
                      					}
                      					setWithLastConsumed(current, "star", true, "*");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleClassifierRHS"


    // $ANTLR start "entryRuleList"
    // InternalKdl.g:2347:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:2347:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:2348:2: iv_ruleList= ruleList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleList=ruleList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleList"


    // $ANTLR start "ruleList"
    // InternalKdl.g:2354:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2360:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:2361:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:2361:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:2362:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:2362:3: ()
            // InternalKdl.g:2363:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getListAccess().getListAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,32,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKdl.g:2376:3: ( (lv_contents_2_0= ruleValue ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( ((LA49_0>=RULE_STRING && LA49_0<=RULE_LOWERCASE_ID)||(LA49_0>=RULE_INT && LA49_0<=RULE_UPPERCASE_ID)||(LA49_0>=RULE_ID && LA49_0<=RULE_CAMELCASE_ID)||LA49_0==30||LA49_0==32||LA49_0==39||LA49_0==42||(LA49_0>=79 && LA49_0<=80)||LA49_0==85||LA49_0==88||LA49_0==101) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalKdl.g:2377:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:2377:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:2378:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_14);
            	    lv_contents_2_0=ruleValue();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getListRule());
            	      					}
            	      					add(
            	      						current,
            	      						"contents",
            	      						lv_contents_2_0,
            	      						"org.integratedmodelling.kdl.Kdl.Value");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            otherlv_3=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getListAccess().getRightParenthesisKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleList"


    // $ANTLR start "entryRuleLiteral"
    // InternalKdl.g:2403:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:2403:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:2404:2: iv_ruleLiteral= ruleLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLiteral=ruleLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // InternalKdl.g:2410:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_string_4_0=null;
        Token lv_boolean_5_1=null;
        Token lv_boolean_5_2=null;
        EObject lv_number_0_0 = null;

        EObject lv_from_1_0 = null;

        EObject lv_to_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2416:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:2417:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:2417:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt51=4;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalKdl.g:2418:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2418:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2419:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2419:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2420:5: lv_number_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_number_0_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLiteralRule());
                      					}
                      					set(
                      						current,
                      						"number",
                      						lv_number_0_0,
                      						"org.integratedmodelling.kdl.Kdl.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2438:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2438:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:2439:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2439:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:2440:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:2440:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:2441:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_39);
                    lv_from_1_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLiteralRule());
                      						}
                      						set(
                      							current,
                      							"from",
                      							lv_from_1_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_2=(Token)match(input,57,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:2462:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:2463:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2463:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:2464:6: lv_to_3_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getToNumberParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_to_3_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLiteralRule());
                      						}
                      						set(
                      							current,
                      							"to",
                      							lv_to_3_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:2483:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2483:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2484:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2484:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2485:5: lv_string_4_0= RULE_STRING
                    {
                    lv_string_4_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_4_0, grammarAccess.getLiteralAccess().getStringSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_4_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:2502:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2502:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:2503:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:2503:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:2504:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:2504:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==79) ) {
                        alt50=1;
                    }
                    else if ( (LA50_0==80) ) {
                        alt50=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 50, 0, input);

                        throw nvae;
                    }
                    switch (alt50) {
                        case 1 :
                            // InternalKdl.g:2505:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_5_1, grammarAccess.getLiteralAccess().getBooleanTrueKeyword_3_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_5_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2516:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_5_2, grammarAccess.getLiteralAccess().getBooleanFalseKeyword_3_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_5_2, null);
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLiteral"


    // $ANTLR start "entryRuleLiteralOrIdOrComma"
    // InternalKdl.g:2533:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:2533:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:2534:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLiteralOrIdOrCommaRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLiteralOrIdOrComma=ruleLiteralOrIdOrComma();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLiteralOrIdOrComma; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLiteralOrIdOrComma"


    // $ANTLR start "ruleLiteralOrIdOrComma"
    // InternalKdl.g:2540:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
    public final EObject ruleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_string_4_0=null;
        Token lv_boolean_5_1=null;
        Token lv_boolean_5_2=null;
        Token lv_id_6_0=null;
        Token lv_comma_7_0=null;
        EObject lv_from_0_0 = null;

        EObject lv_to_2_0 = null;

        EObject lv_number_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2546:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:2547:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:2547:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt53=6;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalKdl.g:2548:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2548:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:2549:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2549:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:2550:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2550:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:2551:6: lv_from_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getFromNumberParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_39);
                    lv_from_0_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLiteralOrIdOrCommaRule());
                      						}
                      						set(
                      							current,
                      							"from",
                      							lv_from_0_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2568:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:2569:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,57,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:2575:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:2576:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:2580:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:2581:6: lv_to_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getToNumberParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_to_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLiteralOrIdOrCommaRule());
                      						}
                      						set(
                      							current,
                      							"to",
                      							lv_to_2_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2600:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2600:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:2601:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2601:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:2602:5: lv_number_3_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getNumberNumberParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_number_3_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLiteralOrIdOrCommaRule());
                      					}
                      					set(
                      						current,
                      						"number",
                      						lv_number_3_0,
                      						"org.integratedmodelling.kdl.Kdl.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:2620:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2620:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2621:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2621:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2622:5: lv_string_4_0= RULE_STRING
                    {
                    lv_string_4_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_4_0, grammarAccess.getLiteralOrIdOrCommaAccess().getStringSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_4_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:2639:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2639:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:2640:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:2640:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:2641:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:2641:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==79) ) {
                        alt52=1;
                    }
                    else if ( (LA52_0==80) ) {
                        alt52=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 52, 0, input);

                        throw nvae;
                    }
                    switch (alt52) {
                        case 1 :
                            // InternalKdl.g:2642:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_5_1, grammarAccess.getLiteralOrIdOrCommaAccess().getBooleanTrueKeyword_3_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_5_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2653:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_5_2, grammarAccess.getLiteralOrIdOrCommaAccess().getBooleanFalseKeyword_3_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_5_2, null);
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:2667:3: ( (lv_id_6_0= RULE_ID ) )
                    {
                    // InternalKdl.g:2667:3: ( (lv_id_6_0= RULE_ID ) )
                    // InternalKdl.g:2668:4: (lv_id_6_0= RULE_ID )
                    {
                    // InternalKdl.g:2668:4: (lv_id_6_0= RULE_ID )
                    // InternalKdl.g:2669:5: lv_id_6_0= RULE_ID
                    {
                    lv_id_6_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_id_6_0, grammarAccess.getLiteralOrIdOrCommaAccess().getIdIDTerminalRuleCall_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"id",
                      						lv_id_6_0,
                      						"org.eclipse.xtext.common.Terminals.ID");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:2686:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:2686:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:2687:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:2687:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:2688:5: lv_comma_7_0= ','
                    {
                    lv_comma_7_0=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_comma_7_0, grammarAccess.getLiteralOrIdOrCommaAccess().getCommaCommaKeyword_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                      					}
                      					setWithLastConsumed(current, "comma", true, ",");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLiteralOrIdOrComma"


    // $ANTLR start "entryRuleLiteralOrID"
    // InternalKdl.g:2704:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:2704:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:2705:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLiteralOrIDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLiteralOrID=ruleLiteralOrID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLiteralOrID; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLiteralOrID"


    // $ANTLR start "ruleLiteralOrID"
    // InternalKdl.g:2711:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2717:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:2718:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:2718:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt55=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 39:
            case 101:
                {
                alt55=1;
                }
                break;
            case RULE_STRING:
                {
                alt55=2;
                }
                break;
            case 79:
            case 80:
                {
                alt55=3;
                }
                break;
            case RULE_ID:
                {
                alt55=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }

            switch (alt55) {
                case 1 :
                    // InternalKdl.g:2719:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2719:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2720:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2720:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2721:5: lv_number_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLiteralOrIDAccess().getNumberNumberParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_number_0_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLiteralOrIDRule());
                      					}
                      					set(
                      						current,
                      						"number",
                      						lv_number_0_0,
                      						"org.integratedmodelling.kdl.Kdl.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2739:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2739:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:2740:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:2740:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:2741:5: lv_string_1_0= RULE_STRING
                    {
                    lv_string_1_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_1_0, grammarAccess.getLiteralOrIDAccess().getStringSTRINGTerminalRuleCall_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralOrIDRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_1_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:2758:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2758:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:2759:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:2759:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:2760:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:2760:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==79) ) {
                        alt54=1;
                    }
                    else if ( (LA54_0==80) ) {
                        alt54=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 54, 0, input);

                        throw nvae;
                    }
                    switch (alt54) {
                        case 1 :
                            // InternalKdl.g:2761:6: lv_boolean_2_1= 'true'
                            {
                            lv_boolean_2_1=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_2_1, grammarAccess.getLiteralOrIDAccess().getBooleanTrueKeyword_2_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIDRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_2_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2772:6: lv_boolean_2_2= 'false'
                            {
                            lv_boolean_2_2=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_2_2, grammarAccess.getLiteralOrIDAccess().getBooleanFalseKeyword_2_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIDRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_2_2, null);
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:2786:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:2786:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:2787:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:2787:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:2788:5: lv_id_3_0= RULE_ID
                    {
                    lv_id_3_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_id_3_0, grammarAccess.getLiteralOrIDAccess().getIdIDTerminalRuleCall_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralOrIDRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"id",
                      						lv_id_3_0,
                      						"org.eclipse.xtext.common.Terminals.ID");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLiteralOrID"


    // $ANTLR start "entryRuleMetadata"
    // InternalKdl.g:2808:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:2808:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:2809:2: iv_ruleMetadata= ruleMetadata EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMetadataRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMetadata=ruleMetadata();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMetadata; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMetadata"


    // $ANTLR start "ruleMetadata"
    // InternalKdl.g:2815:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleMetadata() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_1=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_ids_2_2 = null;

        EObject lv_values_3_1 = null;

        EObject lv_values_3_2 = null;

        EObject lv_values_3_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:2821:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:2822:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:2822:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:2823:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:2823:3: ()
            // InternalKdl.g:2824:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getMetadataAccess().getMetadataAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,42,FOLLOW_50); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMetadataAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:2837:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==RULE_LOWERCASE_ID) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalKdl.g:2838:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:2838:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:2839:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:2839:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:2840:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:2840:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt56=2;
            	    int LA56_0 = input.LA(1);

            	    if ( (LA56_0==RULE_LOWERCASE_ID) ) {
            	        int LA56_1 = input.LA(2);

            	        if ( (LA56_1==RULE_STRING||LA56_1==RULE_INT||LA56_1==RULE_ID||LA56_1==32||LA56_1==39||LA56_1==42||(LA56_1>=79 && LA56_1<=80)||LA56_1==101) ) {
            	            alt56=1;
            	        }
            	        else if ( (LA56_1==86||LA56_1==92) ) {
            	            alt56=2;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 56, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 56, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt56) {
            	        case 1 :
            	            // InternalKdl.g:2841:7: lv_ids_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_ids_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_51); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							newLeafNode(lv_ids_2_1, grammarAccess.getMetadataAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0_0());
            	              						
            	            }
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElement(grammarAccess.getMetadataRule());
            	              							}
            	              							addWithLastConsumed(
            	              								current,
            	              								"ids",
            	              								lv_ids_2_1,
            	              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
            	              						
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:2856:7: lv_ids_2_2= rulePropertyId
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getIdsPropertyIdParserRuleCall_2_0_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_51);
            	            lv_ids_2_2=rulePropertyId();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getMetadataRule());
            	              							}
            	              							add(
            	              								current,
            	              								"ids",
            	              								lv_ids_2_2,
            	              								"org.integratedmodelling.kdl.Kdl.PropertyId");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalKdl.g:2874:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:2875:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:2875:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:2876:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:2876:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt57=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 39:
            	    case 79:
            	    case 80:
            	    case 101:
            	        {
            	        alt57=1;
            	        }
            	        break;
            	    case 42:
            	        {
            	        alt57=2;
            	        }
            	        break;
            	    case 32:
            	        {
            	        alt57=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 57, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt57) {
            	        case 1 :
            	            // InternalKdl.g:2877:7: lv_values_3_1= ruleLiteralOrID
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesLiteralOrIDParserRuleCall_2_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_50);
            	            lv_values_3_1=ruleLiteralOrID();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getMetadataRule());
            	              							}
            	              							add(
            	              								current,
            	              								"values",
            	              								lv_values_3_1,
            	              								"org.integratedmodelling.kdl.Kdl.LiteralOrID");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:2893:7: lv_values_3_2= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesMetadataParserRuleCall_2_1_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_50);
            	            lv_values_3_2=ruleMetadata();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getMetadataRule());
            	              							}
            	              							add(
            	              								current,
            	              								"values",
            	              								lv_values_3_2,
            	              								"org.integratedmodelling.kdl.Kdl.Metadata");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:2909:7: lv_values_3_3= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesListParserRuleCall_2_1_0_2());
            	              						
            	            }
            	            pushFollow(FOLLOW_50);
            	            lv_values_3_3=ruleList();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getMetadataRule());
            	              							}
            	              							add(
            	              								current,
            	              								"values",
            	              								lv_values_3_3,
            	              								"org.integratedmodelling.kdl.Kdl.List");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);

            otherlv_4=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getMetadataAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMetadata"


    // $ANTLR start "entryRuleParameterList"
    // InternalKdl.g:2936:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:2936:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:2937:2: iv_ruleParameterList= ruleParameterList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameterList=ruleParameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameterList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterList"


    // $ANTLR start "ruleParameterList"
    // InternalKdl.g:2943:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_values_0_0 = null;

        EObject lv_values_2_0 = null;

        EObject lv_pairs_3_0 = null;

        EObject lv_pairs_5_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2949:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:2950:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:2950:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt61=2;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INT:
            case RULE_UPPERCASE_ID:
            case RULE_ID:
            case RULE_EXPR:
            case RULE_CAMELCASE_ID:
            case 30:
            case 32:
            case 39:
            case 42:
            case 79:
            case 80:
            case 85:
            case 88:
            case 101:
                {
                alt61=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                int LA61_2 = input.LA(2);

                if ( (LA61_2==EOF||LA61_2==30||(LA61_2>=32 && LA61_2<=33)||LA61_2==44||(LA61_2>=86 && LA61_2<=87)||(LA61_2>=91 && LA61_2<=92)||LA61_2==95) ) {
                    alt61=1;
                }
                else if ( ((LA61_2>=93 && LA61_2<=94)) ) {
                    alt61=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 61, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                int LA61_3 = input.LA(2);

                if ( (LA61_3==EOF||LA61_3==30||LA61_3==33||LA61_3==44||LA61_3==87||(LA61_3>=91 && LA61_3<=92)) ) {
                    alt61=1;
                }
                else if ( ((LA61_3>=93 && LA61_3<=94)) ) {
                    alt61=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 61, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }

            switch (alt61) {
                case 1 :
                    // InternalKdl.g:2951:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:2951:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:2952:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:2952:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:2953:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:2953:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:2954:6: lv_values_0_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
                    lv_values_0_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getParameterListRule());
                      						}
                      						add(
                      							current,
                      							"values",
                      							lv_values_0_0,
                      							"org.integratedmodelling.kdl.Kdl.Value");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2971:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==30) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // InternalKdl.g:2972:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,30,FOLLOW_35); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:2976:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:2977:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:2977:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:2978:7: lv_values_2_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_29);
                    	    lv_values_2_0=ruleValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getParameterListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"values",
                    	      								lv_values_2_0,
                    	      								"org.integratedmodelling.kdl.Kdl.Value");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop59;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2998:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:2998:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:2999:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:2999:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:3000:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:3000:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:3001:6: lv_pairs_3_0= ruleKeyValuePair
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
                    lv_pairs_3_0=ruleKeyValuePair();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getParameterListRule());
                      						}
                      						add(
                      							current,
                      							"pairs",
                      							lv_pairs_3_0,
                      							"org.integratedmodelling.kdl.Kdl.KeyValuePair");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:3018:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==30) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // InternalKdl.g:3019:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:3019:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:3020:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,30,FOLLOW_35); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3026:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:3027:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:3027:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:3028:7: lv_pairs_5_0= ruleKeyValuePair
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_29);
                    	    lv_pairs_5_0=ruleKeyValuePair();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getParameterListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"pairs",
                    	      								lv_pairs_5_0,
                    	      								"org.integratedmodelling.kdl.Kdl.KeyValuePair");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterList"


    // $ANTLR start "entryRuleValue"
    // InternalKdl.g:3051:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:3051:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:3052:2: iv_ruleValue= ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValue=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalKdl.g:3058:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_expression_5_0=null;
        Token lv_enumId_7_0=null;
        EObject lv_literal_0_0 = null;

        EObject lv_function_1_0 = null;

        EObject lv_urn_2_0 = null;

        EObject lv_list_3_0 = null;

        EObject lv_map_4_0 = null;

        EObject lv_table_6_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3064:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:3065:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:3065:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            int alt62=8;
            alt62 = dfa62.predict(input);
            switch (alt62) {
                case 1 :
                    // InternalKdl.g:3066:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:3066:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:3067:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:3067:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:3068:5: lv_literal_0_0= ruleLiteralOrIdOrComma
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralOrIdOrCommaParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_literal_0_0=ruleLiteralOrIdOrComma();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"literal",
                      						lv_literal_0_0,
                      						"org.integratedmodelling.kdl.Kdl.LiteralOrIdOrComma");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:3086:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:3086:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:3087:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:3087:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:3088:5: lv_function_1_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getFunctionFunctionParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_function_1_0=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"function",
                      						lv_function_1_0,
                      						"org.integratedmodelling.kdl.Kdl.Function");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:3106:3: ( (lv_urn_2_0= ruleUrn ) )
                    {
                    // InternalKdl.g:3106:3: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:3107:4: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:3107:4: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:3108:5: lv_urn_2_0= ruleUrn
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_urn_2_0=ruleUrn();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"urn",
                      						lv_urn_2_0,
                      						"org.integratedmodelling.kdl.Kdl.Urn");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:3126:3: ( (lv_list_3_0= ruleList ) )
                    {
                    // InternalKdl.g:3126:3: ( (lv_list_3_0= ruleList ) )
                    // InternalKdl.g:3127:4: (lv_list_3_0= ruleList )
                    {
                    // InternalKdl.g:3127:4: (lv_list_3_0= ruleList )
                    // InternalKdl.g:3128:5: lv_list_3_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_list_3_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"list",
                      						lv_list_3_0,
                      						"org.integratedmodelling.kdl.Kdl.List");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:3146:3: ( (lv_map_4_0= ruleMap ) )
                    {
                    // InternalKdl.g:3146:3: ( (lv_map_4_0= ruleMap ) )
                    // InternalKdl.g:3147:4: (lv_map_4_0= ruleMap )
                    {
                    // InternalKdl.g:3147:4: (lv_map_4_0= ruleMap )
                    // InternalKdl.g:3148:5: lv_map_4_0= ruleMap
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getMapMapParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_map_4_0=ruleMap();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"map",
                      						lv_map_4_0,
                      						"org.integratedmodelling.kdl.Kdl.Map");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:3166:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3166:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    // InternalKdl.g:3167:4: (lv_expression_5_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3167:4: (lv_expression_5_0= RULE_EXPR )
                    // InternalKdl.g:3168:5: lv_expression_5_0= RULE_EXPR
                    {
                    lv_expression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expression_5_0, grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expression",
                      						lv_expression_5_0,
                      						"org.integratedmodelling.kdl.Kdl.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKdl.g:3185:3: ( (lv_table_6_0= ruleLookupTable ) )
                    {
                    // InternalKdl.g:3185:3: ( (lv_table_6_0= ruleLookupTable ) )
                    // InternalKdl.g:3186:4: (lv_table_6_0= ruleLookupTable )
                    {
                    // InternalKdl.g:3186:4: (lv_table_6_0= ruleLookupTable )
                    // InternalKdl.g:3187:5: lv_table_6_0= ruleLookupTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getTableLookupTableParserRuleCall_6_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_table_6_0=ruleLookupTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"table",
                      						lv_table_6_0,
                      						"org.integratedmodelling.kdl.Kdl.LookupTable");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:3205:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3205:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3206:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3206:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:3207:5: lv_enumId_7_0= RULE_UPPERCASE_ID
                    {
                    lv_enumId_7_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_enumId_7_0, grammarAccess.getValueAccess().getEnumIdUPPERCASE_IDTerminalRuleCall_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"enumId",
                      						lv_enumId_7_0,
                      						"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleUrn"
    // InternalKdl.g:3227:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:3227:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:3228:2: iv_ruleUrn= ruleUrn EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUrnRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUrn=ruleUrn();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUrn; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUrn"


    // $ANTLR start "ruleUrn"
    // InternalKdl.g:3234:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:3240:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:3241:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:3241:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:3242:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:3242:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:3243:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:3243:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt63=3;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt63=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                switch ( input.LA(2) ) {
                case 92:
                    {
                    int LA63_5 = input.LA(3);

                    if ( (LA63_5==RULE_LOWERCASE_ID) ) {
                        int LA63_6 = input.LA(4);

                        if ( (LA63_6==86||LA63_6==92) ) {
                            alt63=1;
                        }
                        else if ( (LA63_6==EOF||(LA63_6>=RULE_STRING && LA63_6<=RULE_UPPERCASE_ID)||(LA63_6>=RULE_ID && LA63_6<=RULE_CAMELCASE_ID)||(LA63_6>=19 && LA63_6<=37)||(LA63_6>=39 && LA63_6<=40)||(LA63_6>=42 && LA63_6<=44)||(LA63_6>=46 && LA63_6<=50)||(LA63_6>=54 && LA63_6<=56)||(LA63_6>=58 && LA63_6<=62)||(LA63_6>=64 && LA63_6<=74)||(LA63_6>=79 && LA63_6<=80)||LA63_6==85||(LA63_6>=87 && LA63_6<=88)||LA63_6==101) ) {
                            alt63=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 63, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 63, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 86:
                    {
                    alt63=1;
                    }
                    break;
                case EOF:
                case RULE_STRING:
                case RULE_LOWERCASE_ID:
                case RULE_ANNOTATION_ID:
                case RULE_INT:
                case RULE_LOWERCASE_DASHID:
                case RULE_UPPERCASE_ID:
                case RULE_ID:
                case RULE_EXPR:
                case RULE_CAMELCASE_ID:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 39:
                case 40:
                case 42:
                case 43:
                case 44:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 54:
                case 55:
                case 56:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 79:
                case 80:
                case 85:
                case 87:
                case 88:
                case 91:
                case 101:
                    {
                    alt63=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 63, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt63=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
                {
                alt63=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // InternalKdl.g:3244:5: lv_name_0_1= ruleUrnId
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUrnAccess().getNameUrnIdParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_0_1=ruleUrnId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUrnRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_0_1,
                      						"org.integratedmodelling.kdl.Kdl.UrnId");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:3260:5: lv_name_0_2= RULE_STRING
                    {
                    lv_name_0_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_name_0_2, grammarAccess.getUrnAccess().getNameSTRINGTerminalRuleCall_0_1());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getUrnRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"name",
                      						lv_name_0_2,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:3275:5: lv_name_0_3= ruleLocalFilePath
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUrnAccess().getNameLocalFilePathParserRuleCall_0_2());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_0_3=ruleLocalFilePath();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUrnRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_0_3,
                      						"org.integratedmodelling.kdl.Kdl.LocalFilePath");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }
                    break;

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUrn"


    // $ANTLR start "entryRuleUrnId"
    // InternalKdl.g:3296:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:3296:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:3297:2: iv_ruleUrnId= ruleUrnId EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUrnIdRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUrnId=ruleUrnId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUrnId.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUrnId"


    // $ANTLR start "ruleUrnId"
    // InternalKdl.g:3303:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleUrnId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_11=null;
        AntlrDatatypeRuleToken this_PathName_1 = null;

        AntlrDatatypeRuleToken this_PathName_3 = null;

        AntlrDatatypeRuleToken this_PathName_5 = null;

        AntlrDatatypeRuleToken this_Path_7 = null;

        AntlrDatatypeRuleToken this_VersionNumber_9 = null;



        	enterRule();

        try {
            // InternalKdl.g:3309:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:3310:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:3310:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:3311:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:3311:3: (kw= 'urn:klab:' )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==85) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalKdl.g:3312:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,85,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getUrnKlabKeyword_0());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_52);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,86,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_52);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,86,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_52);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,86,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_53);
            this_Path_7=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_Path_7);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalKdl.g:3373:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==86) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalKdl.g:3374:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
                    {
                    kw=(Token)match(input,86,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_54);
                    this_VersionNumber_9=ruleVersionNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_VersionNumber_9);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:3390:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==87) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalKdl.g:3391:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,87,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    this_LOWERCASE_ID_11=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_11);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_11, grammarAccess.getUrnIdAccess().getLOWERCASE_IDTerminalRuleCall_9_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUrnId"


    // $ANTLR start "entryRuleMap"
    // InternalKdl.g:3408:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKdl.g:3408:44: (iv_ruleMap= ruleMap EOF )
            // InternalKdl.g:3409:2: iv_ruleMap= ruleMap EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMapRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMap=ruleMap();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMap; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMap"


    // $ANTLR start "ruleMap"
    // InternalKdl.g:3415:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3421:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKdl.g:3422:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKdl.g:3422:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKdl.g:3423:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKdl.g:3423:3: ()
            // InternalKdl.g:3424:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getMapAccess().getMapAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,42,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3437:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_STRING||LA68_0==RULE_INT||LA68_0==32||LA68_0==39||LA68_0==42||LA68_0==63||(LA68_0>=79 && LA68_0<=80)||(LA68_0>=83 && LA68_0<=84)||LA68_0==94||(LA68_0>=96 && LA68_0<=101)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalKdl.g:3438:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKdl.g:3438:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKdl.g:3439:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKdl.g:3439:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKdl.g:3440:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    lv_entries_2_0=ruleMapEntry();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMapRule());
                      						}
                      						add(
                      							current,
                      							"entries",
                      							lv_entries_2_0,
                      							"org.integratedmodelling.kdl.Kdl.MapEntry");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:3457:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop67:
                    do {
                        int alt67=2;
                        int LA67_0 = input.LA(1);

                        if ( (LA67_0==30) ) {
                            alt67=1;
                        }


                        switch (alt67) {
                    	case 1 :
                    	    // InternalKdl.g:3458:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKdl.g:3458:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKdl.g:3459:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,30,FOLLOW_57); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3466:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKdl.g:3467:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKdl.g:3467:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKdl.g:3468:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_56);
                    	    lv_entries_4_0=ruleMapEntry();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getMapRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"entries",
                    	      								lv_entries_4_0,
                    	      								"org.integratedmodelling.kdl.Kdl.MapEntry");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop67;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getMapAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMap"


    // $ANTLR start "entryRuleMapEntry"
    // InternalKdl.g:3495:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKdl.g:3495:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKdl.g:3496:2: iv_ruleMapEntry= ruleMapEntry EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMapEntryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMapEntry=ruleMapEntry();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMapEntry; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMapEntry"


    // $ANTLR start "ruleMapEntry"
    // InternalKdl.g:3502:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3508:2: ( ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKdl.g:3509:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKdl.g:3509:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKdl.g:3510:3: ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKdl.g:3510:3: ( (lv_classifier_0_0= ruleClassifierRHS ) )
            // InternalKdl.g:3511:4: (lv_classifier_0_0= ruleClassifierRHS )
            {
            // InternalKdl.g:3511:4: (lv_classifier_0_0= ruleClassifierRHS )
            // InternalKdl.g:3512:5: lv_classifier_0_0= ruleClassifierRHS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierRHSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_52);
            lv_classifier_0_0=ruleClassifierRHS();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMapEntryRule());
              					}
              					set(
              						current,
              						"classifier",
              						lv_classifier_0_0,
              						"org.integratedmodelling.kdl.Kdl.ClassifierRHS");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,86,FOLLOW_35); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:3533:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKdl.g:3534:4: (lv_value_2_0= ruleValue )
            {
            // InternalKdl.g:3534:4: (lv_value_2_0= ruleValue )
            // InternalKdl.g:3535:5: lv_value_2_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMapEntryRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.integratedmodelling.kdl.Kdl.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMapEntry"


    // $ANTLR start "entryRuleLookupTable"
    // InternalKdl.g:3556:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKdl.g:3556:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKdl.g:3557:2: iv_ruleLookupTable= ruleLookupTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLookupTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLookupTable=ruleLookupTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLookupTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLookupTable"


    // $ANTLR start "ruleLookupTable"
    // InternalKdl.g:3563:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3569:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKdl.g:3570:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKdl.g:3570:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKdl.g:3571:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKdl.g:3571:3: ()
            // InternalKdl.g:3572:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getLookupTableAccess().getLookupTableAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,88,FOLLOW_58); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3585:3: ( (lv_table_2_0= ruleTable ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_STRING||LA69_0==RULE_INT||LA69_0==RULE_EXPR||LA69_0==39||LA69_0==63||(LA69_0>=79 && LA69_0<=80)||(LA69_0>=83 && LA69_0<=84)||LA69_0==87||LA69_0==94||(LA69_0>=96 && LA69_0<=101)) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalKdl.g:3586:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKdl.g:3586:4: (lv_table_2_0= ruleTable )
                    // InternalKdl.g:3587:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_59);
                    lv_table_2_0=ruleTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLookupTableRule());
                      					}
                      					set(
                      						current,
                      						"table",
                      						lv_table_2_0,
                      						"org.integratedmodelling.kdl.Kdl.Table");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getLookupTableAccess().getRightCurlyBracketRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLookupTable"


    // $ANTLR start "entryRuleTable"
    // InternalKdl.g:3612:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKdl.g:3612:46: (iv_ruleTable= ruleTable EOF )
            // InternalKdl.g:3613:2: iv_ruleTable= ruleTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTable=ruleTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // InternalKdl.g:3619:1: ruleTable returns [EObject current=null] : ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_rows_0_0 = null;

        EObject lv_rows_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3625:2: ( ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) )
            // InternalKdl.g:3626:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            {
            // InternalKdl.g:3626:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            // InternalKdl.g:3627:3: ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            {
            // InternalKdl.g:3627:3: ( (lv_rows_0_0= ruleTableRow ) )
            // InternalKdl.g:3628:4: (lv_rows_0_0= ruleTableRow )
            {
            // InternalKdl.g:3628:4: (lv_rows_0_0= ruleTableRow )
            // InternalKdl.g:3629:5: lv_rows_0_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_29);
            lv_rows_0_0=ruleTableRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTableRule());
              					}
              					add(
              						current,
              						"rows",
              						lv_rows_0_0,
              						"org.integratedmodelling.kdl.Kdl.TableRow");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:3646:3: (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==30) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // InternalKdl.g:3647:4: otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) )
            	    {
            	    otherlv_1=(Token)match(input,30,FOLLOW_60); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:3651:4: ( (lv_rows_2_0= ruleTableRow ) )
            	    // InternalKdl.g:3652:5: (lv_rows_2_0= ruleTableRow )
            	    {
            	    // InternalKdl.g:3652:5: (lv_rows_2_0= ruleTableRow )
            	    // InternalKdl.g:3653:6: lv_rows_2_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_29);
            	    lv_rows_2_0=ruleTableRow();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTableRule());
            	      						}
            	      						add(
            	      							current,
            	      							"rows",
            	      							lv_rows_2_0,
            	      							"org.integratedmodelling.kdl.Kdl.TableRow");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop70;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleTableRow"
    // InternalKdl.g:3675:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKdl.g:3675:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKdl.g:3676:2: iv_ruleTableRow= ruleTableRow EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableRowRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTableRow=ruleTableRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTableRow; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableRow"


    // $ANTLR start "ruleTableRow"
    // InternalKdl.g:3682:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3688:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKdl.g:3689:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKdl.g:3689:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKdl.g:3690:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKdl.g:3690:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKdl.g:3691:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKdl.g:3691:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKdl.g:3692:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_61);
            lv_elements_0_0=ruleTableClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTableRowRule());
              					}
              					add(
              						current,
              						"elements",
              						lv_elements_0_0,
              						"org.integratedmodelling.kdl.Kdl.TableClassifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:3709:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==90) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // InternalKdl.g:3710:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,90,FOLLOW_60); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:3714:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKdl.g:3715:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKdl.g:3715:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKdl.g:3716:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_61);
            	    lv_elements_2_0=ruleTableClassifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTableRowRule());
            	      						}
            	      						add(
            	      							current,
            	      							"elements",
            	      							lv_elements_2_0,
            	      							"org.integratedmodelling.kdl.Kdl.TableClassifier");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableRow"


    // $ANTLR start "entryRuleTableClassifier"
    // InternalKdl.g:3738:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKdl.g:3738:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKdl.g:3739:2: iv_ruleTableClassifier= ruleTableClassifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableClassifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTableClassifier=ruleTableClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTableClassifier; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableClassifier"


    // $ANTLR start "ruleTableClassifier"
    // InternalKdl.g:3745:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) ;
    public final EObject ruleTableClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_boolean_0_0=null;
        Token lv_boolean_1_0=null;
        Token lv_leftLimit_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_rightLimit_7_0=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token lv_string_12_0=null;
        Token lv_expr_15_0=null;
        Token lv_nodata_16_0=null;
        Token lv_star_17_0=null;
        Token lv_anything_18_0=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;

        EObject lv_num_9_0 = null;

        EObject lv_set_11_0 = null;

        EObject lv_op_13_0 = null;

        EObject lv_expression_14_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3751:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) )
            // InternalKdl.g:3752:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            {
            // InternalKdl.g:3752:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            int alt75=10;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // InternalKdl.g:3753:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:3753:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==79) ) {
                        alt72=1;
                    }
                    else if ( (LA72_0==80) ) {
                        alt72=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 72, 0, input);

                        throw nvae;
                    }
                    switch (alt72) {
                        case 1 :
                            // InternalKdl.g:3754:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:3754:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:3755:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:3755:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:3756:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_0_0, grammarAccess.getTableClassifierAccess().getBooleanTrueKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getTableClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_0_0, "true");
                              					
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:3769:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:3769:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:3770:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:3770:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:3771:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_1_0, grammarAccess.getTableClassifierAccess().getBooleanFalseKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getTableClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_1_0, "false");
                              					
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:3785:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:3785:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:3786:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:3786:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:3787:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3787:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:3788:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
                    lv_int0_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_2_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:3805:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt73=3;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==81) ) {
                        alt73=1;
                    }
                    else if ( (LA73_0==82) ) {
                        alt73=2;
                    }
                    switch (alt73) {
                        case 1 :
                            // InternalKdl.g:3806:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3806:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:3807:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:3807:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:3808:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,81,FOLLOW_39); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_3_0, grammarAccess.getTableClassifierAccess().getLeftLimitInclusiveKeyword_1_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_3_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:3821:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,82,FOLLOW_39); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:3826:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:3827:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,57,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getTableClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:3833:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:3834:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:3838:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:3839:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
                    lv_int1_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_6_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:3856:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt74=3;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==81) ) {
                        alt74=1;
                    }
                    else if ( (LA74_0==82) ) {
                        alt74=2;
                    }
                    switch (alt74) {
                        case 1 :
                            // InternalKdl.g:3857:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3857:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:3858:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:3858:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:3859:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_7_0, grammarAccess.getTableClassifierAccess().getRightLimitInclusiveKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_7_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:3872:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:3879:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3879:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:3880:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:3880:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:3881:5: lv_num_9_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getNumNumberParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_9_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_9_0,
                      						"org.integratedmodelling.kdl.Kdl.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:3899:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:3899:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:3900:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,83,FOLLOW_48); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:3904:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:3905:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:3905:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:3906:6: lv_set_11_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getSetListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_11_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_11_0,
                      							"org.integratedmodelling.kdl.Kdl.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:3925:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3925:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:3926:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:3926:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:3927:5: lv_string_12_0= RULE_STRING
                    {
                    lv_string_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_12_0, grammarAccess.getTableClassifierAccess().getStringSTRINGTerminalRuleCall_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_12_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:3944:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3944:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    // InternalKdl.g:3945:4: ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3945:4: ( (lv_op_13_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:3946:5: (lv_op_13_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:3946:5: (lv_op_13_0= ruleREL_OPERATOR )
                    // InternalKdl.g:3947:6: lv_op_13_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
                    lv_op_13_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_13_0,
                      							"org.integratedmodelling.kdl.Kdl.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:3964:4: ( (lv_expression_14_0= ruleNumber ) )
                    // InternalKdl.g:3965:5: (lv_expression_14_0= ruleNumber )
                    {
                    // InternalKdl.g:3965:5: (lv_expression_14_0= ruleNumber )
                    // InternalKdl.g:3966:6: lv_expression_14_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getExpressionNumberParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_14_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_14_0,
                      							"org.integratedmodelling.kdl.Kdl.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKdl.g:3985:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3985:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    // InternalKdl.g:3986:4: (lv_expr_15_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3986:4: (lv_expr_15_0= RULE_EXPR )
                    // InternalKdl.g:3987:5: lv_expr_15_0= RULE_EXPR
                    {
                    lv_expr_15_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expr_15_0, grammarAccess.getTableClassifierAccess().getExprEXPRTerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expr",
                      						lv_expr_15_0,
                      						"org.integratedmodelling.kdl.Kdl.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:4004:3: ( (lv_nodata_16_0= 'unknown' ) )
                    {
                    // InternalKdl.g:4004:3: ( (lv_nodata_16_0= 'unknown' ) )
                    // InternalKdl.g:4005:4: (lv_nodata_16_0= 'unknown' )
                    {
                    // InternalKdl.g:4005:4: (lv_nodata_16_0= 'unknown' )
                    // InternalKdl.g:4006:5: lv_nodata_16_0= 'unknown'
                    {
                    lv_nodata_16_0=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nodata_16_0, grammarAccess.getTableClassifierAccess().getNodataUnknownKeyword_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "nodata", lv_nodata_16_0, "unknown");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKdl.g:4019:3: ( (lv_star_17_0= '*' ) )
                    {
                    // InternalKdl.g:4019:3: ( (lv_star_17_0= '*' ) )
                    // InternalKdl.g:4020:4: (lv_star_17_0= '*' )
                    {
                    // InternalKdl.g:4020:4: (lv_star_17_0= '*' )
                    // InternalKdl.g:4021:5: lv_star_17_0= '*'
                    {
                    lv_star_17_0=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_17_0, grammarAccess.getTableClassifierAccess().getStarAsteriskKeyword_8_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "star", true, "*");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKdl.g:4034:3: ( (lv_anything_18_0= '#' ) )
                    {
                    // InternalKdl.g:4034:3: ( (lv_anything_18_0= '#' ) )
                    // InternalKdl.g:4035:4: (lv_anything_18_0= '#' )
                    {
                    // InternalKdl.g:4035:4: (lv_anything_18_0= '#' )
                    // InternalKdl.g:4036:5: lv_anything_18_0= '#'
                    {
                    lv_anything_18_0=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_anything_18_0, grammarAccess.getTableClassifierAccess().getAnythingNumberSignKeyword_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "anything", true, "#");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableClassifier"


    // $ANTLR start "entryRuleLocalFilePath"
    // InternalKdl.g:4052:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:4052:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:4053:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLocalFilePathRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLocalFilePath=ruleLocalFilePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLocalFilePath.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalFilePath"


    // $ANTLR start "ruleLocalFilePath"
    // InternalKdl.g:4059:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleLocalFilePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_CAMELCASE_ID_0=null;
        Token this_LOWERCASE_ID_1=null;
        Token this_LOWERCASE_DASHID_2=null;
        Token kw=null;
        Token this_CAMELCASE_ID_4=null;
        Token this_LOWERCASE_ID_5=null;
        Token this_LOWERCASE_DASHID_6=null;
        Token this_LOWERCASE_ID_8=null;
        Token this_LOWERCASE_ID_10=null;


        	enterRule();

        try {
            // InternalKdl.g:4065:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4066:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4066:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4067:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4067:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt76=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt76=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt76=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                alt76=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // InternalKdl.g:4068:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_CAMELCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4076:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:4084:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_2, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4092:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==91) ) {
                    alt78=1;
                }


                switch (alt78) {
            	case 1 :
            	    // InternalKdl.g:4093:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,91,FOLLOW_63); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4098:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    int alt77=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_CAMELCASE_ID:
            	        {
            	        alt77=1;
            	        }
            	        break;
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt77=2;
            	        }
            	        break;
            	    case RULE_LOWERCASE_DASHID:
            	        {
            	        alt77=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 77, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt77) {
            	        case 1 :
            	            // InternalKdl.g:4099:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
            	            {
            	            this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_CAMELCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CAMELCASE_ID_4, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4107:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_5, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:4115:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
            	            {
            	            this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_62); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_DASHID_6);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_DASHID_6, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_1_1_2());
            	              				
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop78;
                }
            } while (true);

            // InternalKdl.g:4124:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==92) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKdl.g:4125:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,92,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getFullStopKeyword_2_0());
                      			
                    }
                    this_LOWERCASE_ID_8=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_8, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4138:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==87) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalKdl.g:4139:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,87,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getNumberSignKeyword_3_0());
                      			
                    }
                    this_LOWERCASE_ID_10=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_10);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_10, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalFilePath"


    // $ANTLR start "entryRuleKeyValuePair"
    // InternalKdl.g:4156:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4156:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4157:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getKeyValuePairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleKeyValuePair=ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleKeyValuePair; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleKeyValuePair"


    // $ANTLR start "ruleKeyValuePair"
    // InternalKdl.g:4163:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_1=null;
        Token lv_name_0_2=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4169:2: ( ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4170:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4170:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4171:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4171:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:4172:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:4172:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:4173:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            {
            // InternalKdl.g:4173:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==RULE_LOWERCASE_ID) ) {
                alt81=1;
            }
            else if ( (LA81_0==RULE_LOWERCASE_DASHID) ) {
                alt81=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // InternalKdl.g:4174:6: lv_name_0_1= RULE_LOWERCASE_ID
                    {
                    lv_name_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_0_1, grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getKeyValuePairRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_0_1,
                      							"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4189:6: lv_name_0_2= RULE_LOWERCASE_DASHID
                    {
                    lv_name_0_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_0_2, grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getKeyValuePairRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_0_2,
                      							"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalKdl.g:4206:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==93) ) {
                alt82=1;
            }
            else if ( (LA82_0==94) ) {
                alt82=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // InternalKdl.g:4207:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4207:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4208:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4208:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4209:6: lv_interactive_1_0= '=?'
                    {
                    lv_interactive_1_0=(Token)match(input,93,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_interactive_1_0, grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getKeyValuePairRule());
                      						}
                      						setWithLastConsumed(current, "interactive", true, "=?");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4222:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,94,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4227:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4228:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4228:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4229:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_3_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getKeyValuePairRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_3_0,
              						"org.integratedmodelling.kdl.Kdl.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleKeyValuePair"


    // $ANTLR start "entryRuleFunction"
    // InternalKdl.g:4250:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4250:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4251:2: iv_ruleFunction= ruleFunction EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunction=ruleFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunction; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunction"


    // $ANTLR start "ruleFunction"
    // InternalKdl.g:4257:1: ruleFunction returns [EObject current=null] : ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token lv_mediated_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token lv_variable_9_0=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_variable_16_0=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_parameters_4_0 = null;

        EObject lv_urn_6_0 = null;

        EObject lv_value_7_0 = null;

        EObject lv_chain_11_0 = null;

        EObject lv_chain_13_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4263:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) ) )
            // InternalKdl.g:4264:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) )
            {
            // InternalKdl.g:4264:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) )
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( ((LA89_0>=RULE_STRING && LA89_0<=RULE_LOWERCASE_ID)||(LA89_0>=RULE_INT && LA89_0<=RULE_LOWERCASE_DASHID)||LA89_0==RULE_CAMELCASE_ID||LA89_0==39||(LA89_0>=79 && LA89_0<=80)||LA89_0==85||LA89_0==101) ) {
                alt89=1;
            }
            else if ( (LA89_0==32) ) {
                alt89=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }
            switch (alt89) {
                case 1 :
                    // InternalKdl.g:4265:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4265:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4266:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4266:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==RULE_LOWERCASE_ID) ) {
                        int LA83_1 = input.LA(2);

                        if ( (LA83_1==95) ) {
                            alt83=1;
                        }
                    }
                    switch (alt83) {
                        case 1 :
                            // InternalKdl.g:4267:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4267:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4268:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4268:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4269:7: lv_mediated_0_0= RULE_LOWERCASE_ID
                            {
                            lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_65); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_mediated_0_0, grammarAccess.getFunctionAccess().getMediatedLOWERCASE_IDTerminalRuleCall_0_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"mediated",
                              								lv_mediated_0_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }

                            otherlv_1=(Token)match(input,95,FOLLOW_66); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4290:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )
                    int alt85=3;
                    alt85 = dfa85.predict(input);
                    switch (alt85) {
                        case 1 :
                            // InternalKdl.g:4291:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            {
                            // InternalKdl.g:4291:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            // InternalKdl.g:4292:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
                            {
                            // InternalKdl.g:4292:6: ( (lv_name_2_0= rulePathName ) )
                            // InternalKdl.g:4293:7: (lv_name_2_0= rulePathName )
                            {
                            // InternalKdl.g:4293:7: (lv_name_2_0= rulePathName )
                            // InternalKdl.g:4294:8: lv_name_2_0= rulePathName
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_48);
                            lv_name_2_0=rulePathName();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getFunctionRule());
                              								}
                              								set(
                              									current,
                              									"name",
                              									lv_name_2_0,
                              									"org.integratedmodelling.kdl.Kdl.PathName");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_3=(Token)match(input,32,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_1_0_1());
                              					
                            }
                            // InternalKdl.g:4315:6: ( (lv_parameters_4_0= ruleParameterList ) )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( ((LA84_0>=RULE_STRING && LA84_0<=RULE_LOWERCASE_ID)||(LA84_0>=RULE_INT && LA84_0<=RULE_UPPERCASE_ID)||(LA84_0>=RULE_ID && LA84_0<=RULE_CAMELCASE_ID)||LA84_0==30||LA84_0==32||LA84_0==39||LA84_0==42||(LA84_0>=79 && LA84_0<=80)||LA84_0==85||LA84_0==88||LA84_0==101) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // InternalKdl.g:4316:7: (lv_parameters_4_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4316:7: (lv_parameters_4_0= ruleParameterList )
                                    // InternalKdl.g:4317:8: lv_parameters_4_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_15);
                                    lv_parameters_4_0=ruleParameterList();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElementForParent(grammarAccess.getFunctionRule());
                                      								}
                                      								set(
                                      									current,
                                      									"parameters",
                                      									lv_parameters_4_0,
                                      									"org.integratedmodelling.kdl.Kdl.ParameterList");
                                      								afterParserOrEnumRuleCall();
                                      							
                                    }

                                    }


                                    }
                                    break;

                            }

                            otherlv_5=(Token)match(input,33,FOLLOW_67); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4340:5: ( (lv_urn_6_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4340:5: ( (lv_urn_6_0= ruleUrn ) )
                            // InternalKdl.g:4341:6: (lv_urn_6_0= ruleUrn )
                            {
                            // InternalKdl.g:4341:6: (lv_urn_6_0= ruleUrn )
                            // InternalKdl.g:4342:7: lv_urn_6_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_67);
                            lv_urn_6_0=ruleUrn();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getFunctionRule());
                              							}
                              							set(
                              								current,
                              								"urn",
                              								lv_urn_6_0,
                              								"org.integratedmodelling.kdl.Kdl.Urn");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:4360:5: ( (lv_value_7_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4360:5: ( (lv_value_7_0= ruleLiteral ) )
                            // InternalKdl.g:4361:6: (lv_value_7_0= ruleLiteral )
                            {
                            // InternalKdl.g:4361:6: (lv_value_7_0= ruleLiteral )
                            // InternalKdl.g:4362:7: lv_value_7_0= ruleLiteral
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getValueLiteralParserRuleCall_0_1_2_0());
                              						
                            }
                            pushFollow(FOLLOW_67);
                            lv_value_7_0=ruleLiteral();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getFunctionRule());
                              							}
                              							set(
                              								current,
                              								"value",
                              								lv_value_7_0,
                              								"org.integratedmodelling.kdl.Kdl.Literal");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:4380:4: (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==44) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // InternalKdl.g:4381:5: otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_8=(Token)match(input,44,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getAsKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4385:5: ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4386:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4386:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4387:7: lv_variable_9_0= RULE_LOWERCASE_ID
                            {
                            lv_variable_9_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_variable_9_0, grammarAccess.getFunctionAccess().getVariableLOWERCASE_IDTerminalRuleCall_0_2_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"variable",
                              								lv_variable_9_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4406:3: (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4406:3: (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4407:4: otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    otherlv_10=(Token)match(input,32,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:4411:4: ( (lv_chain_11_0= ruleFunction ) )
                    // InternalKdl.g:4412:5: (lv_chain_11_0= ruleFunction )
                    {
                    // InternalKdl.g:4412:5: (lv_chain_11_0= ruleFunction )
                    // InternalKdl.g:4413:6: lv_chain_11_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_49);
                    lv_chain_11_0=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getFunctionRule());
                      						}
                      						add(
                      							current,
                      							"chain",
                      							lv_chain_11_0,
                      							"org.integratedmodelling.kdl.Kdl.Function");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:4430:4: (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )*
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( (LA87_0==30) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // InternalKdl.g:4431:5: otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) )
                    	    {
                    	    otherlv_12=(Token)match(input,30,FOLLOW_9); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_12, grammarAccess.getFunctionAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:4435:5: ( (lv_chain_13_0= ruleFunction ) )
                    	    // InternalKdl.g:4436:6: (lv_chain_13_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:4436:6: (lv_chain_13_0= ruleFunction )
                    	    // InternalKdl.g:4437:7: lv_chain_13_0= ruleFunction
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_49);
                    	    lv_chain_13_0=ruleFunction();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"chain",
                    	      								lv_chain_13_0,
                    	      								"org.integratedmodelling.kdl.Kdl.Function");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop87;
                        }
                    } while (true);

                    otherlv_14=(Token)match(input,33,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }
                    // InternalKdl.g:4459:4: (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==44) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // InternalKdl.g:4460:5: otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_15=(Token)match(input,44,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_15, grammarAccess.getFunctionAccess().getAsKeyword_1_4_0());
                              				
                            }
                            // InternalKdl.g:4464:5: ( (lv_variable_16_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4465:6: (lv_variable_16_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4465:6: (lv_variable_16_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4466:7: lv_variable_16_0= RULE_LOWERCASE_ID
                            {
                            lv_variable_16_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_variable_16_0, grammarAccess.getFunctionAccess().getVariableLOWERCASE_IDTerminalRuleCall_1_4_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"variable",
                              								lv_variable_16_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunction"


    // $ANTLR start "entryRuleUnitElement"
    // InternalKdl.g:4488:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:4488:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:4489:2: iv_ruleUnitElement= ruleUnitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnitElement=ruleUnitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnitElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnitElement"


    // $ANTLR start "ruleUnitElement"
    // InternalKdl.g:4495:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) ;
    public final EObject ruleUnitElement() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_1=null;
        Token lv_id_0_2=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_num_1_0 = null;

        EObject lv_unit_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4501:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) )
            // InternalKdl.g:4502:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            {
            // InternalKdl.g:4502:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            int alt91=3;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
            case RULE_CAMELCASE_ID:
                {
                alt91=1;
                }
                break;
            case RULE_INT:
            case 39:
            case 101:
                {
                alt91=2;
                }
                break;
            case 32:
                {
                alt91=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // InternalKdl.g:4503:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    {
                    // InternalKdl.g:4503:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    // InternalKdl.g:4504:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKdl.g:4504:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    // InternalKdl.g:4505:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    {
                    // InternalKdl.g:4505:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==RULE_CAMELCASE_ID) ) {
                        alt90=1;
                    }
                    else if ( (LA90_0==RULE_LOWERCASE_ID) ) {
                        alt90=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 90, 0, input);

                        throw nvae;
                    }
                    switch (alt90) {
                        case 1 :
                            // InternalKdl.g:4506:6: lv_id_0_1= RULE_CAMELCASE_ID
                            {
                            lv_id_0_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_1, grammarAccess.getUnitElementAccess().getIdCAMELCASE_IDTerminalRuleCall_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_1,
                              							"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4521:6: lv_id_0_2= RULE_LOWERCASE_ID
                            {
                            lv_id_0_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_2, grammarAccess.getUnitElementAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_2,
                              							"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4539:3: ( (lv_num_1_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4539:3: ( (lv_num_1_0= ruleNumber ) )
                    // InternalKdl.g:4540:4: (lv_num_1_0= ruleNumber )
                    {
                    // InternalKdl.g:4540:4: (lv_num_1_0= ruleNumber )
                    // InternalKdl.g:4541:5: lv_num_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitElementAccess().getNumNumberParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_1_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUnitElementRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_1_0,
                      						"org.integratedmodelling.kdl.Kdl.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:4559:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    {
                    // InternalKdl.g:4559:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    // InternalKdl.g:4560:4: otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,32,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:4564:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKdl.g:4565:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKdl.g:4565:5: (lv_unit_3_0= ruleUnit )
                    // InternalKdl.g:4566:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_unit_3_0=ruleUnit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getUnitElementRule());
                      						}
                      						set(
                      							current,
                      							"unit",
                      							lv_unit_3_0,
                      							"org.integratedmodelling.kdl.Kdl.Unit");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getUnitElementAccess().getRightParenthesisKeyword_2_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitElement"


    // $ANTLR start "entryRuleREL_OPERATOR"
    // InternalKdl.g:4592:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:4592:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:4593:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getREL_OPERATORRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleREL_OPERATOR=ruleREL_OPERATOR();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleREL_OPERATOR; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleREL_OPERATOR"


    // $ANTLR start "ruleREL_OPERATOR"
    // InternalKdl.g:4599:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
    public final EObject ruleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        Token lv_gt_0_0=null;
        Token lv_lt_1_0=null;
        Token lv_eq_2_0=null;
        Token lv_ne_3_0=null;
        Token lv_le_4_0=null;
        Token lv_ge_5_0=null;


        	enterRule();

        try {
            // InternalKdl.g:4605:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:4606:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:4606:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt92=6;
            switch ( input.LA(1) ) {
            case 96:
                {
                alt92=1;
                }
                break;
            case 97:
                {
                alt92=2;
                }
                break;
            case 94:
                {
                alt92=3;
                }
                break;
            case 98:
                {
                alt92=4;
                }
                break;
            case 99:
                {
                alt92=5;
                }
                break;
            case 100:
                {
                alt92=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // InternalKdl.g:4607:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:4607:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:4608:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:4608:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:4609:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,96,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_gt_0_0, grammarAccess.getREL_OPERATORAccess().getGtGreaterThanSignKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "gt", true, ">");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4622:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:4622:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:4623:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:4623:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:4624:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,97,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_lt_1_0, grammarAccess.getREL_OPERATORAccess().getLtLessThanSignKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "lt", true, "<");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:4637:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:4637:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:4638:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:4638:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:4639:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_eq_2_0, grammarAccess.getREL_OPERATORAccess().getEqEqualsSignKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "eq", true, "=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:4652:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:4652:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:4653:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:4653:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:4654:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_ne_3_0, grammarAccess.getREL_OPERATORAccess().getNeExclamationMarkEqualsSignKeyword_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "ne", true, "!=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:4667:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:4667:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:4668:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:4668:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:4669:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_le_4_0, grammarAccess.getREL_OPERATORAccess().getLeLessThanSignEqualsSignKeyword_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "le", true, "<=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:4682:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:4682:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:4683:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:4683:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:4684:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,100,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_ge_5_0, grammarAccess.getREL_OPERATORAccess().getGeGreaterThanSignEqualsSignKeyword_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "ge", true, ">=");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleREL_OPERATOR"


    // $ANTLR start "entryRuleUnit"
    // InternalKdl.g:4700:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:4700:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:4701:2: iv_ruleUnit= ruleUnit EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnit=ruleUnit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnit; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnit"


    // $ANTLR start "ruleUnit"
    // InternalKdl.g:4707:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4713:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:4714:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:4714:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:4715:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:4715:3: ()
            // InternalKdl.g:4716:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getUnitAccess().getUnitAction_0(),
              					current);
              			
            }

            }

            // InternalKdl.g:4725:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==RULE_LOWERCASE_ID||LA93_0==RULE_INT||LA93_0==RULE_CAMELCASE_ID||LA93_0==32||LA93_0==39||LA93_0==101) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalKdl.g:4726:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:4726:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:4727:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_69);
                    lv_root_1_0=ruleUnitElement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUnitRule());
                      					}
                      					set(
                      						current,
                      						"root",
                      						lv_root_1_0,
                      						"org.integratedmodelling.kdl.Kdl.UnitElement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKdl.g:4744:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==63||LA94_0==91||LA94_0==104) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // InternalKdl.g:4745:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:4745:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:4746:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:4752:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:4753:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:4753:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:4754:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_70);
            	    lv_connectors_2_0=ruleUnitOp();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getUnitRule());
            	      							}
            	      							add(
            	      								current,
            	      								"connectors",
            	      								lv_connectors_2_0,
            	      								"org.integratedmodelling.kdl.Kdl.UnitOp");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalKdl.g:4772:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:4773:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:4773:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:4774:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_69);
            	    lv_units_3_0=ruleUnitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getUnitRule());
            	      						}
            	      						add(
            	      							current,
            	      							"units",
            	      							lv_units_3_0,
            	      							"org.integratedmodelling.kdl.Kdl.UnitElement");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnit"


    // $ANTLR start "entryRuleNumber"
    // InternalKdl.g:4796:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:4796:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:4797:2: iv_ruleNumber= ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNumber=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNumber; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalKdl.g:4803:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
    public final EObject ruleNumber() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_negative_1_0=null;
        Token lv_real_2_0=null;
        Token lv_decimal_3_0=null;
        Token lv_decimalPart_4_0=null;
        Token lv_exponential_5_1=null;
        Token lv_exponential_5_2=null;
        Token otherlv_6=null;
        Token lv_expNegative_7_0=null;
        Token lv_exp_8_0=null;


        	enterRule();

        try {
            // InternalKdl.g:4809:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:4810:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:4810:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:4811:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:4811:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt95=3;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==39) ) {
                alt95=1;
            }
            else if ( (LA95_0==101) ) {
                alt95=2;
            }
            switch (alt95) {
                case 1 :
                    // InternalKdl.g:4812:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,39,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4817:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:4817:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:4818:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:4818:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:4819:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,101,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_negative_1_0, grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNumberRule());
                      						}
                      						setWithLastConsumed(current, "negative", true, "-");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKdl.g:4832:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:4833:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:4837:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:4838:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_real_2_0, grammarAccess.getNumberAccess().getRealINTTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getNumberRule());
              					}
              					setWithLastConsumed(
              						current,
              						"real",
              						lv_real_2_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            // InternalKdl.g:4854:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==92) && (synpred189_InternalKdl())) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalKdl.g:4855:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:4868:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:4869:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:4869:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:4870:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:4870:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:4871:7: lv_decimal_3_0= '.'
                    {
                    lv_decimal_3_0=(Token)match(input,92,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimal_3_0, grammarAccess.getNumberAccess().getDecimalFullStopKeyword_2_0_0_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(current, "decimal", true, ".");
                      						
                    }

                    }


                    }

                    // InternalKdl.g:4883:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:4884:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:4884:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:4885:7: lv_decimalPart_4_0= RULE_INT
                    {
                    lv_decimalPart_4_0=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimalPart_4_0, grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_2_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"decimalPart",
                      								lv_decimalPart_4_0,
                      								"org.eclipse.xtext.common.Terminals.INT");
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalKdl.g:4903:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==102) && (synpred193_InternalKdl())) {
                alt99=1;
            }
            else if ( (LA99_0==103) && (synpred193_InternalKdl())) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // InternalKdl.g:4904:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:4930:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:4931:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:4931:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:4932:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:4932:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:4933:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:4933:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==102) ) {
                        alt97=1;
                    }
                    else if ( (LA97_0==103) ) {
                        alt97=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 97, 0, input);

                        throw nvae;
                    }
                    switch (alt97) {
                        case 1 :
                            // InternalKdl.g:4934:8: lv_exponential_5_1= 'e'
                            {
                            lv_exponential_5_1=(Token)match(input,102,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_5_1, grammarAccess.getNumberAccess().getExponentialEKeyword_3_0_0_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "exponential", true, null);
                              							
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4945:8: lv_exponential_5_2= 'E'
                            {
                            lv_exponential_5_2=(Token)match(input,103,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_5_2, grammarAccess.getNumberAccess().getExponentialEKeyword_3_0_0_0_1());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "exponential", true, null);
                              							
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:4958:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt98=3;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==39) ) {
                        alt98=1;
                    }
                    else if ( (LA98_0==101) ) {
                        alt98=2;
                    }
                    switch (alt98) {
                        case 1 :
                            // InternalKdl.g:4959:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,39,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4964:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:4964:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:4965:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:4965:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:4966:8: lv_expNegative_7_0= '-'
                            {
                            lv_expNegative_7_0=(Token)match(input,101,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_expNegative_7_0, grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_3_0_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "expNegative", true, "-");
                              							
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:4979:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:4980:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:4980:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:4981:7: lv_exp_8_0= RULE_INT
                    {
                    lv_exp_8_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_exp_8_0, grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_3_0_2_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"exp",
                      								lv_exp_8_0,
                      								"org.eclipse.xtext.common.Terminals.INT");
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRulePathName"
    // InternalKdl.g:5003:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:5003:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:5004:2: iv_rulePathName= rulePathName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPathNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePathName=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePathName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePathName"


    // $ANTLR start "rulePathName"
    // InternalKdl.g:5010:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5016:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5017:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5017:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5018:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5025:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( (LA100_0==92) ) {
                    int LA100_2 = input.LA(2);

                    if ( (LA100_2==RULE_LOWERCASE_ID) ) {
                        alt100=1;
                    }


                }


                switch (alt100) {
            	case 1 :
            	    // InternalKdl.g:5026:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,92,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop100;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePathName"


    // $ANTLR start "entryRulePath"
    // InternalKdl.g:5043:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:5043:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:5044:2: iv_rulePath= rulePath EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPathRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePath=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePath.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePath"


    // $ANTLR start "rulePath"
    // InternalKdl.g:5050:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:5056:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5057:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5057:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5058:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5065:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( ((LA102_0>=91 && LA102_0<=92)) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // InternalKdl.g:5066:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:5066:4: (kw= '.' | kw= '/' )
            	    int alt101=2;
            	    int LA101_0 = input.LA(1);

            	    if ( (LA101_0==92) ) {
            	        alt101=1;
            	    }
            	    else if ( (LA101_0==91) ) {
            	        alt101=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 101, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt101) {
            	        case 1 :
            	            // InternalKdl.g:5067:5: kw= '.'
            	            {
            	            kw=(Token)match(input,92,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:5073:5: kw= '/'
            	            {
            	            kw=(Token)match(input,91,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_3, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop102;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePath"


    // $ANTLR start "entryRuleJavaClass"
    // InternalKdl.g:5091:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5091:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5092:2: iv_ruleJavaClass= ruleJavaClass EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJavaClassRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJavaClass=ruleJavaClass();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJavaClass.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJavaClass"


    // $ANTLR start "ruleJavaClass"
    // InternalKdl.g:5098:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5104:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5105:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5105:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5106:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getJavaClassAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_75);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,92,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getJavaClassAccess().getFullStopKeyword_1());
              		
            }
            this_CAMELCASE_ID_2=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_CAMELCASE_ID_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_CAMELCASE_ID_2, grammarAccess.getJavaClassAccess().getCAMELCASE_IDTerminalRuleCall_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJavaClass"


    // $ANTLR start "entryRuleNamespaceId"
    // InternalKdl.g:5132:1: entryRuleNamespaceId returns [String current=null] : iv_ruleNamespaceId= ruleNamespaceId EOF ;
    public final String entryRuleNamespaceId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNamespaceId = null;


        try {
            // InternalKdl.g:5132:51: (iv_ruleNamespaceId= ruleNamespaceId EOF )
            // InternalKdl.g:5133:2: iv_ruleNamespaceId= ruleNamespaceId EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNamespaceIdRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNamespaceId=ruleNamespaceId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNamespaceId.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNamespaceId"


    // $ANTLR start "ruleNamespaceId"
    // InternalKdl.g:5139:1: ruleNamespaceId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleNamespaceId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5145:2: ( (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5146:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5146:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5147:3: this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getNamespaceIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,86,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getNamespaceIdAccess().getColonKeyword_1());
              		
            }
            this_CAMELCASE_ID_2=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_CAMELCASE_ID_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_CAMELCASE_ID_2, grammarAccess.getNamespaceIdAccess().getCAMELCASE_IDTerminalRuleCall_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNamespaceId"


    // $ANTLR start "entryRulePropertyId"
    // InternalKdl.g:5173:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5173:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5174:2: iv_rulePropertyId= rulePropertyId EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPropertyIdRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePropertyId=rulePropertyId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePropertyId.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePropertyId"


    // $ANTLR start "rulePropertyId"
    // InternalKdl.g:5180:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5186:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5187:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5187:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5188:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPropertyIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_52);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,86,FOLLOW_77); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getPropertyIdAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:5203:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==RULE_LOWERCASE_ID) ) {
                alt103=1;
            }
            else if ( (LA103_0==RULE_LOWERCASE_DASHID) ) {
                alt103=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }
            switch (alt103) {
                case 1 :
                    // InternalKdl.g:5204:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPropertyIdAccess().getLOWERCASE_IDTerminalRuleCall_2_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5212:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_3=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_3, grammarAccess.getPropertyIdAccess().getLOWERCASE_DASHIDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePropertyId"


    // $ANTLR start "entryRuleVersionNumber"
    // InternalKdl.g:5224:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5224:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5225:2: iv_ruleVersionNumber= ruleVersionNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionNumber=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionNumber.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionNumber"


    // $ANTLR start "ruleVersionNumber"
    // InternalKdl.g:5231:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleVersionNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_LOWERCASE_ID_6=null;
        Token this_UPPERCASE_ID_7=null;


        	enterRule();

        try {
            // InternalKdl.g:5237:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:5238:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:5238:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:5239:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_78); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5246:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==92) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalKdl.g:5247:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,92,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_78); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKdl.g:5259:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==92) ) {
                        alt104=1;
                    }
                    switch (alt104) {
                        case 1 :
                            // InternalKdl.g:5260:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,92,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_79); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_4);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_4, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_2_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKdl.g:5274:3: (kw= '-' )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==101) ) {
                int LA106_1 = input.LA(2);

                if ( (synpred203_InternalKdl()) ) {
                    alt106=1;
                }
            }
            switch (alt106) {
                case 1 :
                    // InternalKdl.g:5275:4: kw= '-'
                    {
                    kw=(Token)match(input,101,FOLLOW_80); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:5281:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt107=3;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==RULE_LOWERCASE_ID) ) {
                int LA107_1 = input.LA(2);

                if ( (synpred204_InternalKdl()) ) {
                    alt107=1;
                }
            }
            else if ( (LA107_0==RULE_UPPERCASE_ID) ) {
                int LA107_2 = input.LA(2);

                if ( (synpred205_InternalKdl()) ) {
                    alt107=2;
                }
            }
            switch (alt107) {
                case 1 :
                    // InternalKdl.g:5282:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_6);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_6, grammarAccess.getVersionNumberAccess().getLOWERCASE_IDTerminalRuleCall_3_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5290:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_7);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_7, grammarAccess.getVersionNumberAccess().getUPPERCASE_IDTerminalRuleCall_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionNumber"


    // $ANTLR start "ruleUnitOp"
    // InternalKdl.g:5302:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5308:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:5309:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:5309:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt108=3;
            switch ( input.LA(1) ) {
            case 91:
                {
                alt108=1;
                }
                break;
            case 104:
                {
                alt108=2;
                }
                break;
            case 63:
                {
                alt108=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }

            switch (alt108) {
                case 1 :
                    // InternalKdl.g:5310:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:5310:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:5311:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:5318:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:5318:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:5319:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,104,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:5326:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:5326:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:5327:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getSTAREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getUnitOpAccess().getSTAREnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitOp"

    // $ANTLR start synpred2_InternalKdl
    public final void synpred2_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_name_2_1 = null;

        AntlrDatatypeRuleToken lv_name_2_2 = null;


        // InternalKdl.g:104:4: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) )
        // InternalKdl.g:104:4: ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) )
        {
        // InternalKdl.g:104:4: ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) )
        // InternalKdl.g:105:5: {...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred2_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0)");
        }
        // InternalKdl.g:105:102: ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) )
        // InternalKdl.g:106:6: ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0);
        // InternalKdl.g:109:9: ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) )
        // InternalKdl.g:109:10: {...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred2_InternalKdl", "true");
        }
        // InternalKdl.g:109:19: (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) )
        // InternalKdl.g:109:20: otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
        {
        otherlv_1=(Token)match(input,19,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:113:9: ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        {
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        {
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        int alt109=2;
        alt109 = dfa109.predict(input);
        switch (alt109) {
            case 1 :
                // InternalKdl.g:116:12: lv_name_2_1= rulePath
                {
                pushFollow(FOLLOW_2);
                lv_name_2_1=rulePath();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:132:12: lv_name_2_2= ruleUrnId
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getModelAccess().getNameUrnIdParserRuleCall_0_0_1_0_1());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_name_2_2=ruleUrnId();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred2_InternalKdl

    // $ANTLR start synpred3_InternalKdl
    public final void synpred3_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        EObject lv_variables_4_0 = null;


        // InternalKdl.g:161:10: ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )
        // InternalKdl.g:161:10: {...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred3_InternalKdl", "true");
        }
        // InternalKdl.g:161:19: (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) )
        // InternalKdl.g:161:20: otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) )
        {
        otherlv_3=(Token)match(input,20,FOLLOW_5); if (state.failed) return ;
        // InternalKdl.g:165:9: ( (lv_variables_4_0= ruleParameter ) )
        // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        {
        // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        // InternalKdl.g:167:11: lv_variables_4_0= ruleParameter
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getVariablesParameterParserRuleCall_0_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_variables_4_0=ruleParameter();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred3_InternalKdl

    // $ANTLR start synpred4_InternalKdl
    public final void synpred4_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        EObject lv_variables_4_0 = null;


        // InternalKdl.g:156:4: ( ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) )
        // InternalKdl.g:156:4: ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) )
        {
        // InternalKdl.g:156:4: ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) )
        // InternalKdl.g:157:5: {...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred4_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1)");
        }
        // InternalKdl.g:157:102: ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ )
        // InternalKdl.g:158:6: ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1);
        // InternalKdl.g:161:9: ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+
        int cnt110=0;
        loop110:
        do {
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==20) && ((true))) {
                alt110=1;
            }


            switch (alt110) {
        	case 1 :
        	    // InternalKdl.g:161:10: {...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred4_InternalKdl", "true");
        	    }
        	    // InternalKdl.g:161:19: (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) )
        	    // InternalKdl.g:161:20: otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) )
        	    {
        	    otherlv_3=(Token)match(input,20,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:165:9: ( (lv_variables_4_0= ruleParameter ) )
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    {
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    // InternalKdl.g:167:11: lv_variables_4_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getVariablesParameterParserRuleCall_0_1_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_81);
        	    lv_variables_4_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt110 >= 1 ) break loop110;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(110, input);
                    throw eee;
            }
            cnt110++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred4_InternalKdl

    // $ANTLR start synpred5_InternalKdl
    public final void synpred5_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        EObject lv_constants_6_0 = null;


        // InternalKdl.g:195:10: ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )
        // InternalKdl.g:195:10: {...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKdl", "true");
        }
        // InternalKdl.g:195:19: (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) )
        // InternalKdl.g:195:20: otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) )
        {
        otherlv_5=(Token)match(input,21,FOLLOW_5); if (state.failed) return ;
        // InternalKdl.g:199:9: ( (lv_constants_6_0= ruleParameter ) )
        // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        {
        // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        // InternalKdl.g:201:11: lv_constants_6_0= ruleParameter
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getConstantsParameterParserRuleCall_0_2_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_constants_6_0=ruleParameter();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred5_InternalKdl

    // $ANTLR start synpred6_InternalKdl
    public final void synpred6_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        EObject lv_constants_6_0 = null;


        // InternalKdl.g:190:4: ( ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) )
        // InternalKdl.g:190:4: ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) )
        {
        // InternalKdl.g:190:4: ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) )
        // InternalKdl.g:191:5: {...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2)");
        }
        // InternalKdl.g:191:102: ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ )
        // InternalKdl.g:192:6: ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2);
        // InternalKdl.g:195:9: ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+
        int cnt111=0;
        loop111:
        do {
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==21) && ((true))) {
                alt111=1;
            }


            switch (alt111) {
        	case 1 :
        	    // InternalKdl.g:195:10: {...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred6_InternalKdl", "true");
        	    }
        	    // InternalKdl.g:195:19: (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) )
        	    // InternalKdl.g:195:20: otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) )
        	    {
        	    otherlv_5=(Token)match(input,21,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:199:9: ( (lv_constants_6_0= ruleParameter ) )
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    {
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    // InternalKdl.g:201:11: lv_constants_6_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getConstantsParameterParserRuleCall_0_2_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_82);
        	    lv_constants_6_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt111 >= 1 ) break loop111;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(111, input);
                    throw eee;
            }
            cnt111++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred6_InternalKdl

    // $ANTLR start synpred7_InternalKdl
    public final void synpred7_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token lv_authors_8_0=null;

        // InternalKdl.g:229:10: ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )
        // InternalKdl.g:229:10: {...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred7_InternalKdl", "true");
        }
        // InternalKdl.g:229:19: (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) )
        // InternalKdl.g:229:20: otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) )
        {
        otherlv_7=(Token)match(input,22,FOLLOW_6); if (state.failed) return ;
        // InternalKdl.g:233:9: ( (lv_authors_8_0= RULE_STRING ) )
        // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        {
        // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        // InternalKdl.g:235:11: lv_authors_8_0= RULE_STRING
        {
        lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred7_InternalKdl

    // $ANTLR start synpred8_InternalKdl
    public final void synpred8_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token lv_authors_8_0=null;

        // InternalKdl.g:224:4: ( ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKdl.g:224:4: ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKdl.g:224:4: ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) )
        // InternalKdl.g:225:5: {...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred8_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3)");
        }
        // InternalKdl.g:225:102: ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ )
        // InternalKdl.g:226:6: ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3);
        // InternalKdl.g:229:9: ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+
        int cnt112=0;
        loop112:
        do {
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==22) && ((true))) {
                alt112=1;
            }


            switch (alt112) {
        	case 1 :
        	    // InternalKdl.g:229:10: {...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred8_InternalKdl", "true");
        	    }
        	    // InternalKdl.g:229:19: (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) )
        	    // InternalKdl.g:229:20: otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) )
        	    {
        	    otherlv_7=(Token)match(input,22,FOLLOW_6); if (state.failed) return ;
        	    // InternalKdl.g:233:9: ( (lv_authors_8_0= RULE_STRING ) )
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    {
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    // InternalKdl.g:235:11: lv_authors_8_0= RULE_STRING
        	    {
        	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_83); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt112 >= 1 ) break loop112;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(112, input);
                    throw eee;
            }
            cnt112++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred8_InternalKdl

    // $ANTLR start synpred9_InternalKdl
    public final void synpred9_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_version_10_0 = null;


        // InternalKdl.g:257:4: ( ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKdl.g:257:4: ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKdl.g:257:4: ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKdl.g:258:5: {...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4)");
        }
        // InternalKdl.g:258:102: ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) )
        // InternalKdl.g:259:6: ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4);
        // InternalKdl.g:262:9: ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) )
        // InternalKdl.g:262:10: {...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKdl", "true");
        }
        // InternalKdl.g:262:19: (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) )
        // InternalKdl.g:262:20: otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) )
        {
        otherlv_9=(Token)match(input,23,FOLLOW_7); if (state.failed) return ;
        // InternalKdl.g:266:9: ( (lv_version_10_0= ruleVersionNumber ) )
        // InternalKdl.g:267:10: (lv_version_10_0= ruleVersionNumber )
        {
        // InternalKdl.g:267:10: (lv_version_10_0= ruleVersionNumber )
        // InternalKdl.g:268:11: lv_version_10_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getVersionVersionNumberParserRuleCall_0_4_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_10_0=ruleVersionNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred9_InternalKdl

    // $ANTLR start synpred10_InternalKdl
    public final void synpred10_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_klabVersion_12_0 = null;


        // InternalKdl.g:291:4: ( ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKdl.g:291:4: ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKdl.g:291:4: ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKdl.g:292:5: {...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5)");
        }
        // InternalKdl.g:292:102: ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) )
        // InternalKdl.g:293:6: ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5);
        // InternalKdl.g:296:9: ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) )
        // InternalKdl.g:296:10: {...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKdl", "true");
        }
        // InternalKdl.g:296:19: (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) )
        // InternalKdl.g:296:20: otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) )
        {
        otherlv_11=(Token)match(input,24,FOLLOW_7); if (state.failed) return ;
        // InternalKdl.g:300:9: ( (lv_klabVersion_12_0= ruleVersionNumber ) )
        // InternalKdl.g:301:10: (lv_klabVersion_12_0= ruleVersionNumber )
        {
        // InternalKdl.g:301:10: (lv_klabVersion_12_0= ruleVersionNumber )
        // InternalKdl.g:302:11: lv_klabVersion_12_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getKlabVersionVersionNumberParserRuleCall_0_5_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_klabVersion_12_0=ruleVersionNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalKdl

    // $ANTLR start synpred11_InternalKdl
    public final void synpred11_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        Token lv_worldview_14_0=null;

        // InternalKdl.g:325:4: ( ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )
        // InternalKdl.g:325:4: ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) )
        {
        // InternalKdl.g:325:4: ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) )
        // InternalKdl.g:326:5: {...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6)");
        }
        // InternalKdl.g:326:102: ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) )
        // InternalKdl.g:327:6: ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6);
        // InternalKdl.g:330:9: ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) )
        // InternalKdl.g:330:10: {...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKdl", "true");
        }
        // InternalKdl.g:330:19: (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) )
        // InternalKdl.g:330:20: otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) )
        {
        otherlv_13=(Token)match(input,25,FOLLOW_5); if (state.failed) return ;
        // InternalKdl.g:334:9: ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) )
        // InternalKdl.g:335:10: (lv_worldview_14_0= RULE_LOWERCASE_ID )
        {
        // InternalKdl.g:335:10: (lv_worldview_14_0= RULE_LOWERCASE_ID )
        // InternalKdl.g:336:11: lv_worldview_14_0= RULE_LOWERCASE_ID
        {
        lv_worldview_14_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred11_InternalKdl

    // $ANTLR start synpred12_InternalKdl
    public final void synpred12_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_15=null;
        AntlrDatatypeRuleToken lv_geometry_16_0 = null;


        // InternalKdl.g:358:4: ( ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:358:4: ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:358:4: ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:359:5: {...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7)");
        }
        // InternalKdl.g:359:102: ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:360:6: ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7);
        // InternalKdl.g:363:9: ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) )
        // InternalKdl.g:363:10: {...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKdl", "true");
        }
        // InternalKdl.g:363:19: (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) )
        // InternalKdl.g:363:20: otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) )
        {
        otherlv_15=(Token)match(input,26,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:367:9: ( (lv_geometry_16_0= ruleGeometry ) )
        // InternalKdl.g:368:10: (lv_geometry_16_0= ruleGeometry )
        {
        // InternalKdl.g:368:10: (lv_geometry_16_0= ruleGeometry )
        // InternalKdl.g:369:11: lv_geometry_16_0= ruleGeometry
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getGeometryGeometryParserRuleCall_0_7_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_geometry_16_0=ruleGeometry();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred12_InternalKdl

    // $ANTLR start synpred13_InternalKdl
    public final void synpred13_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_17=null;
        Token lv_endpoint_18_0=null;

        // InternalKdl.g:392:4: ( ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKdl.g:392:4: ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKdl.g:392:4: ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) )
        // InternalKdl.g:393:5: {...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8)");
        }
        // InternalKdl.g:393:102: ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) )
        // InternalKdl.g:394:6: ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8);
        // InternalKdl.g:397:9: ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) )
        // InternalKdl.g:397:10: {...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKdl", "true");
        }
        // InternalKdl.g:397:19: (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) )
        // InternalKdl.g:397:20: otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) )
        {
        otherlv_17=(Token)match(input,27,FOLLOW_6); if (state.failed) return ;
        // InternalKdl.g:401:9: ( (lv_endpoint_18_0= RULE_STRING ) )
        // InternalKdl.g:402:10: (lv_endpoint_18_0= RULE_STRING )
        {
        // InternalKdl.g:402:10: (lv_endpoint_18_0= RULE_STRING )
        // InternalKdl.g:403:11: lv_endpoint_18_0= RULE_STRING
        {
        lv_endpoint_18_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred13_InternalKdl

    // $ANTLR start synpred14_InternalKdl
    public final void synpred14_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_19=null;
        AntlrDatatypeRuleToken lv_package_20_0 = null;


        // InternalKdl.g:425:4: ( ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) )
        // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) )
        {
        // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) )
        // InternalKdl.g:426:5: {...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9)");
        }
        // InternalKdl.g:426:102: ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) )
        // InternalKdl.g:427:6: ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9);
        // InternalKdl.g:430:9: ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) )
        // InternalKdl.g:430:10: {...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKdl", "true");
        }
        // InternalKdl.g:430:19: (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) )
        // InternalKdl.g:430:20: otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) )
        {
        otherlv_19=(Token)match(input,28,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:434:9: ( (lv_package_20_0= rulePathName ) )
        // InternalKdl.g:435:10: (lv_package_20_0= rulePathName )
        {
        // InternalKdl.g:435:10: (lv_package_20_0= rulePathName )
        // InternalKdl.g:436:11: lv_package_20_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getPackagePathNameParserRuleCall_0_9_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_package_20_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred14_InternalKdl

    // $ANTLR start synpred16_InternalKdl
    public final void synpred16_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_21=null;
        Token otherlv_23=null;
        EObject lv_scale_22_0 = null;

        EObject lv_scale_24_0 = null;


        // InternalKdl.g:459:4: ( ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) )
        // InternalKdl.g:459:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
        {
        // InternalKdl.g:459:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
        // InternalKdl.g:460:5: {...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10)");
        }
        // InternalKdl.g:460:103: ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
        // InternalKdl.g:461:6: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10);
        // InternalKdl.g:464:9: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
        // InternalKdl.g:464:10: {...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKdl", "true");
        }
        // InternalKdl.g:464:19: (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
        // InternalKdl.g:464:20: otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
        {
        otherlv_21=(Token)match(input,29,FOLLOW_9); if (state.failed) return ;
        // InternalKdl.g:468:9: ( (lv_scale_22_0= ruleFunction ) )
        // InternalKdl.g:469:10: (lv_scale_22_0= ruleFunction )
        {
        // InternalKdl.g:469:10: (lv_scale_22_0= ruleFunction )
        // InternalKdl.g:470:11: lv_scale_22_0= ruleFunction
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_1_0());
          										
        }
        pushFollow(FOLLOW_29);
        lv_scale_22_0=ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:487:9: (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
        loop113:
        do {
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==30) ) {
                alt113=1;
            }


            switch (alt113) {
        	case 1 :
        	    // InternalKdl.g:488:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
        	    {
        	    otherlv_23=(Token)match(input,30,FOLLOW_9); if (state.failed) return ;
        	    // InternalKdl.g:492:10: ( (lv_scale_24_0= ruleFunction ) )
        	    // InternalKdl.g:493:11: (lv_scale_24_0= ruleFunction )
        	    {
        	    // InternalKdl.g:493:11: (lv_scale_24_0= ruleFunction )
        	    // InternalKdl.g:494:12: lv_scale_24_0= ruleFunction
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_29);
        	    lv_scale_24_0=ruleFunction();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop113;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred16_InternalKdl

    // $ANTLR start synpred17_InternalKdl
    public final void synpred17_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_25=null;
        EObject lv_contextUrn_26_0 = null;


        // InternalKdl.g:518:4: ( ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )
        // InternalKdl.g:518:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
        {
        // InternalKdl.g:518:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
        // InternalKdl.g:519:5: {...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11)");
        }
        // InternalKdl.g:519:103: ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
        // InternalKdl.g:520:6: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11);
        // InternalKdl.g:523:9: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
        // InternalKdl.g:523:10: {...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKdl", "true");
        }
        // InternalKdl.g:523:19: (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
        // InternalKdl.g:523:20: otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) )
        {
        otherlv_25=(Token)match(input,31,FOLLOW_11); if (state.failed) return ;
        // InternalKdl.g:527:9: ( (lv_contextUrn_26_0= ruleUrn ) )
        // InternalKdl.g:528:10: (lv_contextUrn_26_0= ruleUrn )
        {
        // InternalKdl.g:528:10: (lv_contextUrn_26_0= ruleUrn )
        // InternalKdl.g:529:11: lv_contextUrn_26_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getContextUrnUrnParserRuleCall_0_11_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_contextUrn_26_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred17_InternalKdl

    // $ANTLR start synpred55_InternalKdl
    public final void synpred55_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_geometry_4_0 = null;


        // InternalKdl.g:1486:4: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1486:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1486:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1487:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred55_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKdl.g:1487:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1488:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
        // InternalKdl.g:1491:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1491:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred55_InternalKdl", "true");
        }
        // InternalKdl.g:1491:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        // InternalKdl.g:1491:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
        {
        otherlv_3=(Token)match(input,58,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1495:9: ( (lv_geometry_4_0= ruleGeometry ) )
        // InternalKdl.g:1496:10: (lv_geometry_4_0= ruleGeometry )
        {
        // InternalKdl.g:1496:10: (lv_geometry_4_0= ruleGeometry )
        // InternalKdl.g:1497:11: lv_geometry_4_0= ruleGeometry
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_2_0_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_geometry_4_0=ruleGeometry();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred55_InternalKdl

    // $ANTLR start synpred56_InternalKdl
    public final void synpred56_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        EObject lv_units_6_0 = null;


        // InternalKdl.g:1520:4: ( ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1520:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1520:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1521:5: {...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred56_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKdl.g:1521:109: ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1522:6: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
        // InternalKdl.g:1525:9: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
        // InternalKdl.g:1525:10: {...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred56_InternalKdl", "true");
        }
        // InternalKdl.g:1525:19: (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
        // InternalKdl.g:1525:20: otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) )
        {
        otherlv_5=(Token)match(input,59,FOLLOW_84); if (state.failed) return ;
        // InternalKdl.g:1529:9: ( (lv_units_6_0= ruleUnit ) )
        // InternalKdl.g:1530:10: (lv_units_6_0= ruleUnit )
        {
        // InternalKdl.g:1530:10: (lv_units_6_0= ruleUnit )
        // InternalKdl.g:1531:11: lv_units_6_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getDataflowBodyAccess().getUnitsUnitParserRuleCall_2_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_units_6_0=ruleUnit();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred56_InternalKdl

    // $ANTLR start synpred57_InternalKdl
    public final void synpred57_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_7_0 = null;


        // InternalKdl.g:1554:4: ( ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) )
        // InternalKdl.g:1554:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
        {
        // InternalKdl.g:1554:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
        // InternalKdl.g:1555:5: {...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred57_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKdl.g:1555:109: ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
        // InternalKdl.g:1556:6: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
        // InternalKdl.g:1559:9: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
        // InternalKdl.g:1559:10: {...}? => ( (lv_computations_7_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred57_InternalKdl", "true");
        }
        // InternalKdl.g:1559:19: ( (lv_computations_7_0= ruleComputation ) )
        // InternalKdl.g:1559:20: (lv_computations_7_0= ruleComputation )
        {
        // InternalKdl.g:1559:20: (lv_computations_7_0= ruleComputation )
        // InternalKdl.g:1560:10: lv_computations_7_0= ruleComputation
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_2_2_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_computations_7_0=ruleComputation();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred57_InternalKdl

    // $ANTLR start synpred58_InternalKdl
    public final void synpred58_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        EObject lv_metadata_9_0 = null;


        // InternalKdl.g:1588:10: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )
        // InternalKdl.g:1588:10: otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) )
        {
        otherlv_8=(Token)match(input,60,FOLLOW_43); if (state.failed) return ;
        // InternalKdl.g:1592:10: ( (lv_metadata_9_0= ruleMetadata ) )
        // InternalKdl.g:1593:11: (lv_metadata_9_0= ruleMetadata )
        {
        // InternalKdl.g:1593:11: (lv_metadata_9_0= ruleMetadata )
        // InternalKdl.g:1594:12: lv_metadata_9_0= ruleMetadata
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_3_0_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_metadata_9_0=ruleMetadata();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred58_InternalKdl

    // $ANTLR start synpred59_InternalKdl
    public final void synpred59_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_javaClass_11_0 = null;


        // InternalKdl.g:1613:10: (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )
        // InternalKdl.g:1613:10: otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) )
        {
        otherlv_10=(Token)match(input,61,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:1617:10: ( (lv_javaClass_11_0= ruleJavaClass ) )
        // InternalKdl.g:1618:11: (lv_javaClass_11_0= ruleJavaClass )
        {
        // InternalKdl.g:1618:11: (lv_javaClass_11_0= ruleJavaClass )
        // InternalKdl.g:1619:12: lv_javaClass_11_0= ruleJavaClass
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_2_3_1_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_javaClass_11_0=ruleJavaClass();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred59_InternalKdl

    // $ANTLR start synpred60_InternalKdl
    public final void synpred60_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_metadata_9_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_11_0 = null;


        // InternalKdl.g:1582:4: ( ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )
        // InternalKdl.g:1582:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
        {
        // InternalKdl.g:1582:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
        // InternalKdl.g:1583:5: {...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred60_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKdl.g:1583:109: ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
        // InternalKdl.g:1584:6: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3);
        // InternalKdl.g:1587:9: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
        // InternalKdl.g:1587:10: {...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred60_InternalKdl", "true");
        }
        // InternalKdl.g:1587:19: ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
        // InternalKdl.g:1587:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
        {
        // InternalKdl.g:1587:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )?
        int alt137=2;
        int LA137_0 = input.LA(1);

        if ( (LA137_0==60) ) {
            alt137=1;
        }
        switch (alt137) {
            case 1 :
                // InternalKdl.g:1588:10: otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) )
                {
                otherlv_8=(Token)match(input,60,FOLLOW_43); if (state.failed) return ;
                // InternalKdl.g:1592:10: ( (lv_metadata_9_0= ruleMetadata ) )
                // InternalKdl.g:1593:11: (lv_metadata_9_0= ruleMetadata )
                {
                // InternalKdl.g:1593:11: (lv_metadata_9_0= ruleMetadata )
                // InternalKdl.g:1594:12: lv_metadata_9_0= ruleMetadata
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_3_0_1_0());
                  											
                }
                pushFollow(FOLLOW_85);
                lv_metadata_9_0=ruleMetadata();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:1612:9: (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
        int alt138=2;
        int LA138_0 = input.LA(1);

        if ( (LA138_0==61) ) {
            alt138=1;
        }
        switch (alt138) {
            case 1 :
                // InternalKdl.g:1613:10: otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) )
                {
                otherlv_10=(Token)match(input,61,FOLLOW_3); if (state.failed) return ;
                // InternalKdl.g:1617:10: ( (lv_javaClass_11_0= ruleJavaClass ) )
                // InternalKdl.g:1618:11: (lv_javaClass_11_0= ruleJavaClass )
                {
                // InternalKdl.g:1618:11: (lv_javaClass_11_0= ruleJavaClass )
                // InternalKdl.g:1619:12: lv_javaClass_11_0= ruleJavaClass
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_2_3_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_javaClass_11_0=ruleJavaClass();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred60_InternalKdl

    // $ANTLR start synpred85_InternalKdl
    public final void synpred85_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2080:5: ( 'to' )
        // InternalKdl.g:2080:6: 'to'
        {
        match(input,57,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred85_InternalKdl

    // $ANTLR start synpred124_InternalKdl
    public final void synpred124_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:3066:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:3066:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:3066:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:3067:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:3067:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:3068:5: lv_literal_0_0= ruleLiteralOrIdOrComma
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralOrIdOrCommaParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_literal_0_0=ruleLiteralOrIdOrComma();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred124_InternalKdl

    // $ANTLR start synpred125_InternalKdl
    public final void synpred125_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:3086:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:3086:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:3086:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:3087:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:3087:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:3088:5: lv_function_1_0= ruleFunction
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getFunctionFunctionParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_function_1_0=ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred125_InternalKdl

    // $ANTLR start synpred126_InternalKdl
    public final void synpred126_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;


        // InternalKdl.g:3106:3: ( ( (lv_urn_2_0= ruleUrn ) ) )
        // InternalKdl.g:3106:3: ( (lv_urn_2_0= ruleUrn ) )
        {
        // InternalKdl.g:3106:3: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:3107:4: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:3107:4: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:3108:5: lv_urn_2_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_urn_2_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred126_InternalKdl

    // $ANTLR start synpred127_InternalKdl
    public final void synpred127_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_3_0 = null;


        // InternalKdl.g:3126:3: ( ( (lv_list_3_0= ruleList ) ) )
        // InternalKdl.g:3126:3: ( (lv_list_3_0= ruleList ) )
        {
        // InternalKdl.g:3126:3: ( (lv_list_3_0= ruleList ) )
        // InternalKdl.g:3127:4: (lv_list_3_0= ruleList )
        {
        // InternalKdl.g:3127:4: (lv_list_3_0= ruleList )
        // InternalKdl.g:3128:5: lv_list_3_0= ruleList
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_list_3_0=ruleList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred127_InternalKdl

    // $ANTLR start synpred146_InternalKdl
    public final void synpred146_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:3827:5: ( 'to' )
        // InternalKdl.g:3827:6: 'to'
        {
        match(input,57,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred146_InternalKdl

    // $ANTLR start synpred169_InternalKdl
    public final void synpred169_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_parameters_4_0 = null;


        // InternalKdl.g:4291:5: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) )
        // InternalKdl.g:4291:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        {
        // InternalKdl.g:4291:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        // InternalKdl.g:4292:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
        {
        // InternalKdl.g:4292:6: ( (lv_name_2_0= rulePathName ) )
        // InternalKdl.g:4293:7: (lv_name_2_0= rulePathName )
        {
        // InternalKdl.g:4293:7: (lv_name_2_0= rulePathName )
        // InternalKdl.g:4294:8: lv_name_2_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
          							
        }
        pushFollow(FOLLOW_48);
        lv_name_2_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_3=(Token)match(input,32,FOLLOW_14); if (state.failed) return ;
        // InternalKdl.g:4315:6: ( (lv_parameters_4_0= ruleParameterList ) )?
        int alt153=2;
        int LA153_0 = input.LA(1);

        if ( ((LA153_0>=RULE_STRING && LA153_0<=RULE_LOWERCASE_ID)||(LA153_0>=RULE_INT && LA153_0<=RULE_UPPERCASE_ID)||(LA153_0>=RULE_ID && LA153_0<=RULE_CAMELCASE_ID)||LA153_0==30||LA153_0==32||LA153_0==39||LA153_0==42||(LA153_0>=79 && LA153_0<=80)||LA153_0==85||LA153_0==88||LA153_0==101) ) {
            alt153=1;
        }
        switch (alt153) {
            case 1 :
                // InternalKdl.g:4316:7: (lv_parameters_4_0= ruleParameterList )
                {
                // InternalKdl.g:4316:7: (lv_parameters_4_0= ruleParameterList )
                // InternalKdl.g:4317:8: lv_parameters_4_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                  							
                }
                pushFollow(FOLLOW_15);
                lv_parameters_4_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_5=(Token)match(input,33,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred169_InternalKdl

    // $ANTLR start synpred170_InternalKdl
    public final void synpred170_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_6_0 = null;


        // InternalKdl.g:4340:5: ( ( (lv_urn_6_0= ruleUrn ) ) )
        // InternalKdl.g:4340:5: ( (lv_urn_6_0= ruleUrn ) )
        {
        // InternalKdl.g:4340:5: ( (lv_urn_6_0= ruleUrn ) )
        // InternalKdl.g:4341:6: (lv_urn_6_0= ruleUrn )
        {
        // InternalKdl.g:4341:6: (lv_urn_6_0= ruleUrn )
        // InternalKdl.g:4342:7: lv_urn_6_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
          						
        }
        pushFollow(FOLLOW_2);
        lv_urn_6_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred170_InternalKdl

    // $ANTLR start synpred188_InternalKdl
    public final void synpred188_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4833:4: ( ( RULE_INT ) )
        // InternalKdl.g:4833:5: ( RULE_INT )
        {
        // InternalKdl.g:4833:5: ( RULE_INT )
        // InternalKdl.g:4834:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred188_InternalKdl

    // $ANTLR start synpred189_InternalKdl
    public final void synpred189_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4855:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:4855:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:4855:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:4856:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:4856:5: ( ( '.' ) )
        // InternalKdl.g:4857:6: ( '.' )
        {
        // InternalKdl.g:4857:6: ( '.' )
        // InternalKdl.g:4858:7: '.'
        {
        match(input,92,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:4861:5: ( ( RULE_INT ) )
        // InternalKdl.g:4862:6: ( RULE_INT )
        {
        // InternalKdl.g:4862:6: ( RULE_INT )
        // InternalKdl.g:4863:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred189_InternalKdl

    // $ANTLR start synpred193_InternalKdl
    public final void synpred193_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4904:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:4904:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:4904:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:4905:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:4905:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:4906:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:4906:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:4907:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=102 && input.LA(1)<=103) ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }


        }

        // InternalKdl.g:4914:5: ( '+' | ( ( '-' ) ) )?
        int alt159=3;
        int LA159_0 = input.LA(1);

        if ( (LA159_0==39) ) {
            alt159=1;
        }
        else if ( (LA159_0==101) ) {
            alt159=2;
        }
        switch (alt159) {
            case 1 :
                // InternalKdl.g:4915:6: '+'
                {
                match(input,39,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:4917:6: ( ( '-' ) )
                {
                // InternalKdl.g:4917:6: ( ( '-' ) )
                // InternalKdl.g:4918:7: ( '-' )
                {
                // InternalKdl.g:4918:7: ( '-' )
                // InternalKdl.g:4919:8: '-'
                {
                match(input,101,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:4923:5: ( ( RULE_INT ) )
        // InternalKdl.g:4924:6: ( RULE_INT )
        {
        // InternalKdl.g:4924:6: ( RULE_INT )
        // InternalKdl.g:4925:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred193_InternalKdl

    // $ANTLR start synpred203_InternalKdl
    public final void synpred203_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:5275:4: (kw= '-' )
        // InternalKdl.g:5275:4: kw= '-'
        {
        kw=(Token)match(input,101,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred203_InternalKdl

    // $ANTLR start synpred204_InternalKdl
    public final void synpred204_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:5282:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:5282:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred204_InternalKdl

    // $ANTLR start synpred205_InternalKdl
    public final void synpred205_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:5290:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:5290:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred205_InternalKdl

    // Delegated rules

    public final boolean synpred204_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred204_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred203_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred203_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred205_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred205_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred188_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred188_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred55_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred55_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred56_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred56_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred85_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred85_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred124_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred124_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred57_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred57_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred125_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred125_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred170_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred170_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred146_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred146_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred189_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred189_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred193_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred193_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred126_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred126_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred59_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred59_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred169_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred169_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred127_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred127_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred60_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred60_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA1 dfa1 = new DFA1(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA48 dfa48 = new DFA48(this);
    protected DFA51 dfa51 = new DFA51(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA62 dfa62 = new DFA62(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA85 dfa85 = new DFA85(this);
    protected DFA109 dfa109 = new DFA109(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\6\15\uffff";
    static final String dfa_4s = "\1\112\15\uffff";
    static final String dfa_5s = "\1\uffff\1\15\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14";
    static final String dfa_6s = "\1\0\15\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\14\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\2\uffff\4\1\2\uffff\1\1\5\uffff\5\1\15\uffff\13\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 103:6: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( (lv_package_20_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_0 = input.LA(1);

                         
                        int index6_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_0==EOF||LA6_0==RULE_ANNOTATION_ID||(LA6_0>=34 && LA6_0<=37)||LA6_0==40||(LA6_0>=46 && LA6_0<=50)||(LA6_0>=64 && LA6_0<=74)) ) {s = 1;}

                        else if ( LA6_0 == 19 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0) ) {s = 2;}

                        else if ( LA6_0 == 20 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1) ) {s = 3;}

                        else if ( LA6_0 == 21 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2) ) {s = 4;}

                        else if ( LA6_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3) ) {s = 5;}

                        else if ( LA6_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4) ) {s = 6;}

                        else if ( LA6_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5) ) {s = 7;}

                        else if ( LA6_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6) ) {s = 8;}

                        else if ( LA6_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7) ) {s = 9;}

                        else if ( LA6_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8) ) {s = 10;}

                        else if ( LA6_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {s = 11;}

                        else if ( LA6_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {s = 12;}

                        else if ( LA6_0 == 31 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {s = 13;}

                         
                        input.seek(index6_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\6\uffff";
    static final String dfa_9s = "\1\uffff\1\4\3\uffff\1\4";
    static final String dfa_10s = "\1\5\1\6\1\uffff\1\5\1\uffff\1\6";
    static final String dfa_11s = "\1\125\1\134\1\uffff\1\5\1\uffff\1\134";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\1\uffff";
    static final String dfa_13s = "\6\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\117\uffff\1\2",
            "\1\4\14\uffff\13\4\1\uffff\1\4\2\uffff\4\4\2\uffff\1\4\5\uffff\5\4\15\uffff\13\4\13\uffff\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\4\14\uffff\13\4\1\uffff\1\4\2\uffff\4\4\2\uffff\1\4\5\uffff\5\4\15\uffff\13\4\13\uffff\1\2\4\uffff\1\4\1\3"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )";
        }
    }
    static final String dfa_15s = "\14\uffff";
    static final String dfa_16s = "\1\2\13\uffff";
    static final String dfa_17s = "\1\53\5\0\6\uffff";
    static final String dfa_18s = "\1\76\5\0\6\uffff";
    static final String dfa_19s = "\6\uffff\2\4\1\5\1\1\1\2\1\3";
    static final String dfa_20s = "\1\2\1\4\1\0\1\3\1\1\1\5\6\uffff}>";
    static final String[] dfa_21s = {
            "\1\1\16\uffff\1\3\1\4\1\6\1\7\1\5",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "()+ loopback of 1485:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_2 = input.LA(1);

                         
                        int index37_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred60_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 8;}

                         
                        input.seek(index37_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_4 = input.LA(1);

                         
                        int index37_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred56_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {s = 10;}

                        else if ( synpred60_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index37_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_0 = input.LA(1);

                         
                        int index37_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_0==43) ) {s = 1;}

                        else if ( (LA37_0==EOF) ) {s = 2;}

                        else if ( (LA37_0==58) ) {s = 3;}

                        else if ( (LA37_0==59) ) {s = 4;}

                        else if ( (LA37_0==62) ) {s = 5;}

                        else if ( LA37_0 == 60 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 6;}

                        else if ( LA37_0 == 61 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index37_0);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_3 = input.LA(1);

                         
                        int index37_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred55_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {s = 9;}

                        else if ( synpred60_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index37_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_1 = input.LA(1);

                         
                        int index37_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred60_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 8;}

                         
                        input.seek(index37_1);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_5 = input.LA(1);

                         
                        int index37_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred57_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 11;}

                        else if ( synpred60_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index37_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_22s = "\25\uffff";
    static final String dfa_23s = "\4\uffff\1\20\14\uffff\1\20\2\uffff\1\20";
    static final String dfa_24s = "\1\4\1\uffff\2\7\1\71\7\uffff\3\7\2\uffff\1\71\2\7\1\71";
    static final String dfa_25s = "\1\145\1\uffff\2\7\1\147\7\uffff\1\7\2\145\2\uffff\1\147\2\7\1\126";
    static final String dfa_26s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\2\1\3\4\uffff";
    static final String dfa_27s = "\25\uffff}>";
    static final String[] dfa_28s = {
            "\1\6\2\uffff\1\4\30\uffff\1\10\6\uffff\1\2\2\uffff\1\7\24\uffff\1\13\17\uffff\2\1\2\uffff\1\5\1\12\11\uffff\1\11\1\uffff\5\11\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\17\27\uffff\2\17\3\uffff\1\20\5\uffff\1\14\11\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\37\uffff\1\22\75\uffff\1\23",
            "\1\24\37\uffff\1\22\75\uffff\1\23",
            "",
            "",
            "\1\17\27\uffff\2\17\3\uffff\1\20\17\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\17\27\uffff\2\17\3\uffff\1\20"
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA48 extends DFA {

        public DFA48(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 48;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "2005:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
        }
    }
    static final String dfa_29s = "\17\uffff";
    static final String dfa_30s = "\3\uffff\1\12\7\uffff\1\12\2\uffff\1\12";
    static final String dfa_31s = "\1\4\2\7\1\4\2\uffff\3\7\2\uffff\1\4\2\7\1\4";
    static final String dfa_32s = "\1\145\2\7\1\147\2\uffff\1\7\2\145\2\uffff\1\147\2\7\1\145";
    static final String dfa_33s = "\4\uffff\1\3\1\4\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_34s = "\17\uffff}>";
    static final String[] dfa_35s = {
            "\1\4\2\uffff\1\3\37\uffff\1\1\47\uffff\2\5\24\uffff\1\2",
            "\1\3",
            "\1\3",
            "\6\12\1\uffff\3\12\5\uffff\23\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\5\12\1\uffff\13\12\4\uffff\2\12\4\uffff\1\12\2\uffff\1\12\3\uffff\1\6\10\uffff\1\12\1\7\1\10",
            "",
            "",
            "\1\13",
            "\1\16\37\uffff\1\14\75\uffff\1\15",
            "\1\16\37\uffff\1\14\75\uffff\1\15",
            "",
            "",
            "\6\12\1\uffff\3\12\5\uffff\23\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\5\12\1\uffff\13\12\4\uffff\2\12\4\uffff\1\12\2\uffff\1\12\14\uffff\1\12\1\7\1\10",
            "\1\16",
            "\1\16",
            "\6\12\1\uffff\3\12\5\uffff\23\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\5\12\1\uffff\13\12\4\uffff\2\12\4\uffff\1\12\2\uffff\1\12\14\uffff\1\12"
    };

    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA51 extends DFA {

        public DFA51(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 51;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "2417:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_36s = "\21\uffff";
    static final String dfa_37s = "\3\uffff\1\13\11\uffff\1\13\2\uffff\1\13";
    static final String dfa_38s = "\1\4\2\7\1\4\4\uffff\3\7\2\uffff\1\4\2\7\1\4";
    static final String dfa_39s = "\1\145\2\7\1\147\4\uffff\1\7\2\145\2\uffff\1\147\2\7\1\145";
    static final String dfa_40s = "\4\uffff\1\3\1\4\1\5\1\6\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_41s = "\21\uffff}>";
    static final String[] dfa_42s = {
            "\1\4\2\uffff\1\3\3\uffff\1\6\22\uffff\1\7\10\uffff\1\1\47\uffff\2\5\24\uffff\1\2",
            "\1\3",
            "\1\3",
            "\6\13\1\uffff\3\13\5\uffff\23\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\5\13\1\uffff\13\13\4\uffff\2\13\4\uffff\1\13\2\uffff\1\13\3\uffff\1\10\10\uffff\1\13\1\11\1\12",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\20\37\uffff\1\16\75\uffff\1\17",
            "\1\20\37\uffff\1\16\75\uffff\1\17",
            "",
            "",
            "\6\13\1\uffff\3\13\5\uffff\23\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\5\13\1\uffff\13\13\4\uffff\2\13\4\uffff\1\13\2\uffff\1\13\14\uffff\1\13\1\11\1\12",
            "\1\20",
            "\1\20",
            "\6\13\1\uffff\3\13\5\uffff\23\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\5\13\1\uffff\13\13\4\uffff\2\13\4\uffff\1\13\2\uffff\1\13\14\uffff\1\13"
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "2547:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )";
        }
    }
    static final String dfa_43s = "\1\4\6\0\2\uffff\5\0\7\uffff";
    static final String dfa_44s = "\1\145\6\0\2\uffff\5\0\7\uffff";
    static final String dfa_45s = "\7\uffff\1\1\6\uffff\1\5\1\6\1\7\1\10\1\2\1\3\1\4";
    static final String dfa_46s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\2\uffff\1\6\1\7\1\10\1\11\1\12\7\uffff}>";
    static final String[] dfa_47s = {
            "\1\4\1\11\1\uffff\1\3\1\14\1\21\1\uffff\1\7\1\17\1\13\20\uffff\1\7\1\uffff\1\15\6\uffff\1\1\2\uffff\1\16\44\uffff\1\5\1\6\4\uffff\1\12\2\uffff\1\20\14\uffff\1\2",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[][] dfa_47 = unpackEncodedStringArray(dfa_47s);

    class DFA62 extends DFA {

        public DFA62(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 62;
            this.eot = dfa_22;
            this.eof = dfa_22;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "3065:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA62_1 = input.LA(1);

                         
                        int index62_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 7;}

                        else if ( (synpred125_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index62_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA62_2 = input.LA(1);

                         
                        int index62_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 7;}

                        else if ( (synpred125_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index62_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA62_3 = input.LA(1);

                         
                        int index62_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 7;}

                        else if ( (synpred125_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index62_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA62_4 = input.LA(1);

                         
                        int index62_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 7;}

                        else if ( (synpred125_InternalKdl()) ) {s = 18;}

                        else if ( (synpred126_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index62_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA62_5 = input.LA(1);

                         
                        int index62_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 7;}

                        else if ( (synpred125_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index62_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA62_6 = input.LA(1);

                         
                        int index62_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 7;}

                        else if ( (synpred125_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index62_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA62_9 = input.LA(1);

                         
                        int index62_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 18;}

                        else if ( (synpred126_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index62_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA62_10 = input.LA(1);

                         
                        int index62_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 18;}

                        else if ( (synpred126_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index62_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA62_11 = input.LA(1);

                         
                        int index62_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 18;}

                        else if ( (synpred126_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index62_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA62_12 = input.LA(1);

                         
                        int index62_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 18;}

                        else if ( (synpred126_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index62_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA62_13 = input.LA(1);

                         
                        int index62_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 18;}

                        else if ( (synpred127_InternalKdl()) ) {s = 20;}

                         
                        input.seek(index62_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 62, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_48s = "\1\4\1\uffff\2\7\1\36\7\uffff\3\7\2\uffff\1\36\2\7\1\36";
    static final String dfa_49s = "\1\145\1\uffff\2\7\1\147\7\uffff\1\7\2\145\2\uffff\1\147\2\7\1\132";
    static final String[] dfa_50s = {
            "\1\6\2\uffff\1\4\4\uffff\1\10\32\uffff\1\2\27\uffff\1\12\17\uffff\2\1\2\uffff\1\5\1\11\2\uffff\1\13\6\uffff\1\7\1\uffff\5\7\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\20\32\uffff\1\17\27\uffff\2\17\6\uffff\2\20\1\uffff\1\14\11\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\37\uffff\1\22\75\uffff\1\23",
            "\1\24\37\uffff\1\22\75\uffff\1\23",
            "",
            "",
            "\1\20\32\uffff\1\17\27\uffff\2\17\6\uffff\2\20\13\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\20\32\uffff\1\17\27\uffff\2\17\6\uffff\2\20"
    };
    static final char[] dfa_48 = DFA.unpackEncodedStringToUnsignedChars(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final short[][] dfa_50 = unpackEncodedStringArray(dfa_50s);

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_48;
            this.max = dfa_49;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_50;
        }
        public String getDescription() {
            return "3752:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )";
        }
    }
    static final String dfa_51s = "\1\4\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_52s = "\1\145\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_53s = "\2\uffff\1\2\3\uffff\1\3\4\uffff\1\1";
    static final String dfa_54s = "\1\uffff\1\0\1\uffff\1\1\10\uffff}>";
    static final String[] dfa_55s = {
            "\1\3\1\1\1\uffff\1\6\1\2\4\uffff\1\2\31\uffff\1\6\47\uffff\2\6\4\uffff\1\2\17\uffff\1\6",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final char[] dfa_51 = DFA.unpackEncodedStringToUnsignedChars(dfa_51s);
    static final char[] dfa_52 = DFA.unpackEncodedStringToUnsignedChars(dfa_52s);
    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[] dfa_54 = DFA.unpackEncodedString(dfa_54s);
    static final short[][] dfa_55 = unpackEncodedStringArray(dfa_55s);

    class DFA85 extends DFA {

        public DFA85(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 85;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_51;
            this.max = dfa_52;
            this.accept = dfa_53;
            this.special = dfa_54;
            this.transition = dfa_55;
        }
        public String getDescription() {
            return "4290:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA85_1 = input.LA(1);

                         
                        int index85_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred169_InternalKdl()) ) {s = 11;}

                        else if ( (synpred170_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index85_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA85_3 = input.LA(1);

                         
                        int index85_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred170_InternalKdl()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index85_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 85, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_56s = "\1\5\1\126\1\uffff\1\5\1\uffff\1\126";
    static final String[] dfa_57s = {
            "\1\1\117\uffff\1\2",
            "\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\2\4\uffff\1\4\1\3"
    };
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final short[][] dfa_57 = unpackEncodedStringArray(dfa_57s);

    class DFA109 extends DFA {

        public DFA109(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 109;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_56;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_57;
        }
        public String getDescription() {
            return "115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L,0x0000000000200000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0007C13CBFF80042L,0x00000000000007FFL});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x8000000000000400L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000081000021B0L,0x0000002000218000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0007C13CFFF80042L,0x00000000000007FFL});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000002130L,0x0000000000200000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0007C13C00000042L,0x00000000000007FFL});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000048340003BB0L,0x0000002001218000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0001C13C00000040L,0x00000000000007FFL});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0001C17C000000C0L,0x00000000000007FFL});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0001C1BC00000040L,0x00000000000007FFL});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000130L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000360000000012L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000007800L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000340040000012L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000340000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x7C07C93C00000040L,0x00000000000007FFL});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000300000000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0010000000000010L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x01E0040000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000048140003BB0L,0x0000002001218000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x01C0040000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x01C0000000000002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000008000000080L,0x0000002000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x7C07C13C00000042L,0x00000000000007FFL});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x7C00000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0xFC000081000020A0L,0x0000012008000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0200000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000060000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000240000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000080000000020L,0x0000000000200000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000048100000890L,0x0000002000018000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000000000C00000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x80000C8100000090L,0x0000003F40198000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000080040000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x8000048100000090L,0x0000003F40198000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x8000008000001090L,0x0000003F42998000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x8000008000001090L,0x0000003F40998000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000018800000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000002120L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x00000080000021B0L,0x0000002000218000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x80000083000020A0L,0x0000012008000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x8000000000000002L,0x0000010008000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x00000081000020A0L,0x0000002000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x000000C010000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000002L,0x000000C000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x0000000018000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000222L,0x0000002010000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000222L,0x0000002000000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000222L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x80000081000020A0L,0x0000012008000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x2000000000000002L});

}
