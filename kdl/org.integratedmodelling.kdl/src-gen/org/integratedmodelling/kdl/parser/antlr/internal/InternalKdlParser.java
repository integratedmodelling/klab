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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_LOWERCASE_ID", "RULE_INT", "RULE_LOWERCASE_DASHID", "RULE_UPPERCASE_ID", "RULE_SHAPE", "RULE_ID", "RULE_EXPR", "RULE_CAMELCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'@dataflow'", "'@var'", "'@val'", "'@author'", "'@version'", "'@klab'", "'@worldview'", "'@geometry'", "'@endpoint'", "'@namespace'", "'@coverage'", "','", "'@context'", "'final'", "'export'", "'optional'", "'import'", "'multiple'", "'+'", "'parameter'", "'for'", "'{'", "'}'", "'as'", "'over'", "'number'", "'boolean'", "'text'", "'list'", "'enum'", "'input'", "'values'", "'default'", "'minimum'", "'maximum'", "'range'", "'to'", "'geometry'", "'units'", "'metadata'", "'class'", "'compute'", "'*'", "'object'", "'process'", "'value'", "'concept'", "'extent'", "'spatialextent'", "'temporalextent'", "'annotation'", "'contextualizer'", "'void'", "'partition'", "'models'", "'concepts'", "'observers'", "'definitions'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'in'", "'('", "')'", "'unknown'", "'urn:klab:'", "':'", "'#'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'='", "'>>'", "'>'", "'<'", "'!='", "'<='", "'>='", "'-'", "'e'", "'E'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_UPPERCASE_ID=8;
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
    public static final int RULE_ID=10;
    public static final int RULE_INT=6;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=14;
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
    public static final int RULE_EXPR=11;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=13;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_SHAPE=9;
    public static final int T__19=19;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_LOWERCASE_DASHID=7;
    public static final int RULE_CAMELCASE_ID=12;
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
    public static final int RULE_SL_COMMENT=15;
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
    public static final int RULE_WS=16;
    public static final int RULE_ANY_OTHER=17;
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
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
            	    otherlv_1=(Token)match(input,18,FOLLOW_3); if (state.failed) return current;
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

            	        if ( (LA2_0==19) ) {
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
            	    	    otherlv_3=(Token)match(input,19,FOLLOW_5); if (state.failed) return current;
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

            	        if ( (LA3_0==20) ) {
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
            	    	    otherlv_5=(Token)match(input,20,FOLLOW_5); if (state.failed) return current;
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

            	        if ( (LA4_0==21) ) {
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
            	    	    otherlv_7=(Token)match(input,21,FOLLOW_6); if (state.failed) return current;
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
            	    otherlv_9=(Token)match(input,22,FOLLOW_7); if (state.failed) return current;
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
            	    otherlv_11=(Token)match(input,23,FOLLOW_7); if (state.failed) return current;
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
            	    otherlv_13=(Token)match(input,24,FOLLOW_5); if (state.failed) return current;
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
            	    otherlv_15=(Token)match(input,25,FOLLOW_8); if (state.failed) return current;
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
            	    otherlv_17=(Token)match(input,26,FOLLOW_6); if (state.failed) return current;
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
            	    otherlv_19=(Token)match(input,27,FOLLOW_3); if (state.failed) return current;
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
            	    otherlv_21=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
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

            	        if ( (LA5_0==29) ) {
            	            alt5=1;
            	        }


            	        switch (alt5) {
            	    	case 1 :
            	    	    // InternalKdl.g:488:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
            	    	    {
            	    	    otherlv_23=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
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
            	    otherlv_25=(Token)match(input,30,FOLLOW_11); if (state.failed) return current;
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

                if ( ((LA7_0>=31 && LA7_0<=34)||LA7_0==37||(LA7_0>=43 && LA7_0<=47)||(LA7_0>=61 && LA7_0<=71)) ) {
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


    // $ANTLR start "entryRuleActorDefinition"
    // InternalKdl.g:585:1: entryRuleActorDefinition returns [EObject current=null] : iv_ruleActorDefinition= ruleActorDefinition EOF ;
    public final EObject entryRuleActorDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActorDefinition = null;


        try {
            // InternalKdl.g:585:56: (iv_ruleActorDefinition= ruleActorDefinition EOF )
            // InternalKdl.g:586:2: iv_ruleActorDefinition= ruleActorDefinition EOF
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
    // InternalKdl.g:592:1: ruleActorDefinition returns [EObject current=null] : ( ( ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )? ) ) ;
    public final EObject ruleActorDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_final_0_0=null;
        Token lv_exported_1_0=null;
        Token lv_optional_2_0=null;
        Token lv_imported_3_0=null;
        Token lv_multiple_4_0=null;
        Token lv_arity_5_0=null;
        Token lv_minimum_6_0=null;
        Token lv_parameter_7_0=null;
        Token lv_name_9_1=null;
        Token lv_name_9_2=null;
        Token lv_name_9_3=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token lv_docstring_14_0=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token lv_localName_19_0=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token lv_optional_24_0=null;
        Token lv_type_25_1=null;
        Token lv_type_25_2=null;
        Token lv_type_25_3=null;
        Token lv_type_25_4=null;
        Token lv_type_25_5=null;
        Token lv_parameter_26_0=null;
        Token lv_name_27_1=null;
        Token lv_name_27_2=null;
        Token lv_name_27_3=null;
        Token otherlv_28=null;
        Token lv_enumValues_29_0=null;
        Token otherlv_30=null;
        Token lv_enumValues_31_0=null;
        Token lv_docstring_32_0=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_38=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token otherlv_44=null;
        AntlrDatatypeRuleToken lv_type_8_0 = null;

        AntlrDatatypeRuleToken lv_targets_11_0 = null;

        AntlrDatatypeRuleToken lv_targets_13_0 = null;

        EObject lv_body_16_0 = null;

        EObject lv_coverage_21_0 = null;

        EObject lv_coverage_23_0 = null;

        EObject lv_default_34_0 = null;

        EObject lv_body_36_0 = null;

        EObject lv_rangeMin_39_0 = null;

        EObject lv_rangeMax_41_0 = null;

        EObject lv_rangeMin_43_0 = null;

        EObject lv_rangeMax_45_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:598:2: ( ( ( ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )? ) ) )
            // InternalKdl.g:599:2: ( ( ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )? ) )
            {
            // InternalKdl.g:599:2: ( ( ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )? ) )
            int alt30=2;
            switch ( input.LA(1) ) {
            case 31:
            case 32:
            case 34:
            case 37:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
                {
                alt30=1;
                }
                break;
            case 33:
                {
                int LA30_2 = input.LA(2);

                if ( (LA30_2==48) ) {
                    alt30=2;
                }
                else if ( (LA30_2==34) ) {
                    alt30=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 2, input);

                    throw nvae;
                }
                }
                break;
            case 43:
                {
                int LA30_3 = input.LA(2);

                if ( ((LA30_3>=RULE_STRING && LA30_3<=RULE_LOWERCASE_ID)||LA30_3==RULE_LOWERCASE_DASHID) ) {
                    alt30=1;
                }
                else if ( (LA30_3==48) ) {
                    alt30=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 3, input);

                    throw nvae;
                }
                }
                break;
            case 44:
                {
                int LA30_4 = input.LA(2);

                if ( (LA30_4==48) ) {
                    alt30=2;
                }
                else if ( ((LA30_4>=RULE_STRING && LA30_4<=RULE_LOWERCASE_ID)||LA30_4==RULE_LOWERCASE_DASHID) ) {
                    alt30=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 4, input);

                    throw nvae;
                }
                }
                break;
            case 45:
                {
                int LA30_5 = input.LA(2);

                if ( ((LA30_5>=RULE_STRING && LA30_5<=RULE_LOWERCASE_ID)||LA30_5==RULE_LOWERCASE_DASHID) ) {
                    alt30=1;
                }
                else if ( (LA30_5==48) ) {
                    alt30=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 5, input);

                    throw nvae;
                }
                }
                break;
            case 46:
            case 47:
                {
                alt30=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // InternalKdl.g:600:3: ( ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:600:3: ( ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:601:4: ( (lv_final_0_0= 'final' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )? ( (lv_parameter_7_0= 'parameter' ) )? ( (lv_type_8_0= ruleACTOR ) ) ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) ) (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )? ( (lv_docstring_14_0= RULE_STRING ) )? (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )? (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )? (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )?
                    {
                    // InternalKdl.g:601:4: ( (lv_final_0_0= 'final' ) )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==31) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // InternalKdl.g:602:5: (lv_final_0_0= 'final' )
                            {
                            // InternalKdl.g:602:5: (lv_final_0_0= 'final' )
                            // InternalKdl.g:603:6: lv_final_0_0= 'final'
                            {
                            lv_final_0_0=(Token)match(input,31,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_final_0_0, grammarAccess.getActorDefinitionAccess().getFinalFinalKeyword_0_0_0());
                              					
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

                    // InternalKdl.g:615:4: ( ( (lv_exported_1_0= 'export' ) ) | ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? ) )?
                    int alt12=3;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==32) ) {
                        alt12=1;
                    }
                    else if ( ((LA12_0>=33 && LA12_0<=34)) ) {
                        alt12=2;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalKdl.g:616:5: ( (lv_exported_1_0= 'export' ) )
                            {
                            // InternalKdl.g:616:5: ( (lv_exported_1_0= 'export' ) )
                            // InternalKdl.g:617:6: (lv_exported_1_0= 'export' )
                            {
                            // InternalKdl.g:617:6: (lv_exported_1_0= 'export' )
                            // InternalKdl.g:618:7: lv_exported_1_0= 'export'
                            {
                            lv_exported_1_0=(Token)match(input,32,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_1_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_0_1_0_0());
                              						
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
                            // InternalKdl.g:631:5: ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? )
                            {
                            // InternalKdl.g:631:5: ( ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )? )
                            // InternalKdl.g:632:6: ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) ) ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )?
                            {
                            // InternalKdl.g:632:6: ( ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) ) )
                            // InternalKdl.g:633:7: ( (lv_optional_2_0= 'optional' ) )? ( (lv_imported_3_0= 'import' ) )
                            {
                            // InternalKdl.g:633:7: ( (lv_optional_2_0= 'optional' ) )?
                            int alt9=2;
                            int LA9_0 = input.LA(1);

                            if ( (LA9_0==33) ) {
                                alt9=1;
                            }
                            switch (alt9) {
                                case 1 :
                                    // InternalKdl.g:634:8: (lv_optional_2_0= 'optional' )
                                    {
                                    // InternalKdl.g:634:8: (lv_optional_2_0= 'optional' )
                                    // InternalKdl.g:635:9: lv_optional_2_0= 'optional'
                                    {
                                    lv_optional_2_0=(Token)match(input,33,FOLLOW_14); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_optional_2_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_0_1_1_0_0_0());
                                      								
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

                            // InternalKdl.g:647:7: ( (lv_imported_3_0= 'import' ) )
                            // InternalKdl.g:648:8: (lv_imported_3_0= 'import' )
                            {
                            // InternalKdl.g:648:8: (lv_imported_3_0= 'import' )
                            // InternalKdl.g:649:9: lv_imported_3_0= 'import'
                            {
                            lv_imported_3_0=(Token)match(input,34,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              									newLeafNode(lv_imported_3_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_0_1_1_0_1_0());
                              								
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

                            // InternalKdl.g:662:6: ( ( (lv_multiple_4_0= 'multiple' ) ) | ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? ) )?
                            int alt11=3;
                            int LA11_0 = input.LA(1);

                            if ( (LA11_0==35) ) {
                                alt11=1;
                            }
                            else if ( (LA11_0==RULE_INT) ) {
                                alt11=2;
                            }
                            switch (alt11) {
                                case 1 :
                                    // InternalKdl.g:663:7: ( (lv_multiple_4_0= 'multiple' ) )
                                    {
                                    // InternalKdl.g:663:7: ( (lv_multiple_4_0= 'multiple' ) )
                                    // InternalKdl.g:664:8: (lv_multiple_4_0= 'multiple' )
                                    {
                                    // InternalKdl.g:664:8: (lv_multiple_4_0= 'multiple' )
                                    // InternalKdl.g:665:9: lv_multiple_4_0= 'multiple'
                                    {
                                    lv_multiple_4_0=(Token)match(input,35,FOLLOW_13); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_multiple_4_0, grammarAccess.getActorDefinitionAccess().getMultipleMultipleKeyword_0_1_1_1_0_0());
                                      								
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
                                    // InternalKdl.g:678:7: ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? )
                                    {
                                    // InternalKdl.g:678:7: ( ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )? )
                                    // InternalKdl.g:679:8: ( (lv_arity_5_0= RULE_INT ) ) ( (lv_minimum_6_0= '+' ) )?
                                    {
                                    // InternalKdl.g:679:8: ( (lv_arity_5_0= RULE_INT ) )
                                    // InternalKdl.g:680:9: (lv_arity_5_0= RULE_INT )
                                    {
                                    // InternalKdl.g:680:9: (lv_arity_5_0= RULE_INT )
                                    // InternalKdl.g:681:10: lv_arity_5_0= RULE_INT
                                    {
                                    lv_arity_5_0=(Token)match(input,RULE_INT,FOLLOW_16); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      										newLeafNode(lv_arity_5_0, grammarAccess.getActorDefinitionAccess().getArityINTTerminalRuleCall_0_1_1_1_1_0_0());
                                      									
                                    }
                                    if ( state.backtracking==0 ) {

                                      										if (current==null) {
                                      											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      										}
                                      										setWithLastConsumed(
                                      											current,
                                      											"arity",
                                      											lv_arity_5_0,
                                      											"org.eclipse.xtext.common.Terminals.INT");
                                      									
                                    }

                                    }


                                    }

                                    // InternalKdl.g:697:8: ( (lv_minimum_6_0= '+' ) )?
                                    int alt10=2;
                                    int LA10_0 = input.LA(1);

                                    if ( (LA10_0==36) ) {
                                        alt10=1;
                                    }
                                    switch (alt10) {
                                        case 1 :
                                            // InternalKdl.g:698:9: (lv_minimum_6_0= '+' )
                                            {
                                            // InternalKdl.g:698:9: (lv_minimum_6_0= '+' )
                                            // InternalKdl.g:699:10: lv_minimum_6_0= '+'
                                            {
                                            lv_minimum_6_0=(Token)match(input,36,FOLLOW_13); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              										newLeafNode(lv_minimum_6_0, grammarAccess.getActorDefinitionAccess().getMinimumPlusSignKeyword_0_1_1_1_1_1_0());
                                              									
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

                    // InternalKdl.g:715:4: ( (lv_parameter_7_0= 'parameter' ) )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==37) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalKdl.g:716:5: (lv_parameter_7_0= 'parameter' )
                            {
                            // InternalKdl.g:716:5: (lv_parameter_7_0= 'parameter' )
                            // InternalKdl.g:717:6: lv_parameter_7_0= 'parameter'
                            {
                            lv_parameter_7_0=(Token)match(input,37,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_parameter_7_0, grammarAccess.getActorDefinitionAccess().getParameterParameterKeyword_0_2_0());
                              					
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

                    // InternalKdl.g:729:4: ( (lv_type_8_0= ruleACTOR ) )
                    // InternalKdl.g:730:5: (lv_type_8_0= ruleACTOR )
                    {
                    // InternalKdl.g:730:5: (lv_type_8_0= ruleACTOR )
                    // InternalKdl.g:731:6: lv_type_8_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_0_3_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_type_8_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_8_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:748:4: ( ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) ) )
                    // InternalKdl.g:749:5: ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) )
                    {
                    // InternalKdl.g:749:5: ( (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING ) )
                    // InternalKdl.g:750:6: (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING )
                    {
                    // InternalKdl.g:750:6: (lv_name_9_1= RULE_LOWERCASE_ID | lv_name_9_2= RULE_LOWERCASE_DASHID | lv_name_9_3= RULE_STRING )
                    int alt14=3;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt14=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt14=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt14=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 0, input);

                        throw nvae;
                    }

                    switch (alt14) {
                        case 1 :
                            // InternalKdl.g:751:7: lv_name_9_1= RULE_LOWERCASE_ID
                            {
                            lv_name_9_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_9_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_0_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_9_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:766:7: lv_name_9_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_9_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_9_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_0_4_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_9_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:781:7: lv_name_9_3= RULE_STRING
                            {
                            lv_name_9_3=(Token)match(input,RULE_STRING,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_9_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_0_4_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_9_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:798:4: (otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )* )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==38) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKdl.g:799:5: otherlv_10= 'for' ( (lv_targets_11_0= ruleTARGET ) ) (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )*
                            {
                            otherlv_10=(Token)match(input,38,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_10, grammarAccess.getActorDefinitionAccess().getForKeyword_0_5_0());
                              				
                            }
                            // InternalKdl.g:803:5: ( (lv_targets_11_0= ruleTARGET ) )
                            // InternalKdl.g:804:6: (lv_targets_11_0= ruleTARGET )
                            {
                            // InternalKdl.g:804:6: (lv_targets_11_0= ruleTARGET )
                            // InternalKdl.g:805:7: lv_targets_11_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_20);
                            lv_targets_11_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_11_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:822:5: (otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) ) )*
                            loop15:
                            do {
                                int alt15=2;
                                int LA15_0 = input.LA(1);

                                if ( (LA15_0==29) ) {
                                    alt15=1;
                                }


                                switch (alt15) {
                            	case 1 :
                            	    // InternalKdl.g:823:6: otherlv_12= ',' ( (lv_targets_13_0= ruleTARGET ) )
                            	    {
                            	    otherlv_12=(Token)match(input,29,FOLLOW_19); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_12, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_5_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:827:6: ( (lv_targets_13_0= ruleTARGET ) )
                            	    // InternalKdl.g:828:7: (lv_targets_13_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:828:7: (lv_targets_13_0= ruleTARGET )
                            	    // InternalKdl.g:829:8: lv_targets_13_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_5_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_20);
                            	    lv_targets_13_0=ruleTARGET();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"targets",
                            	      									lv_targets_13_0,
                            	      									"org.integratedmodelling.kdl.Kdl.TARGET");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop15;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:848:4: ( (lv_docstring_14_0= RULE_STRING ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==RULE_STRING) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:849:5: (lv_docstring_14_0= RULE_STRING )
                            {
                            // InternalKdl.g:849:5: (lv_docstring_14_0= RULE_STRING )
                            // InternalKdl.g:850:6: lv_docstring_14_0= RULE_STRING
                            {
                            lv_docstring_14_0=(Token)match(input,RULE_STRING,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_14_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_0_6_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_14_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:866:4: (otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==39) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKdl.g:867:5: otherlv_15= '{' ( (lv_body_16_0= ruleDataflowBody ) ) otherlv_17= '}'
                            {
                            otherlv_15=(Token)match(input,39,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_15, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_0_7_0());
                              				
                            }
                            // InternalKdl.g:871:5: ( (lv_body_16_0= ruleDataflowBody ) )
                            // InternalKdl.g:872:6: (lv_body_16_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:872:6: (lv_body_16_0= ruleDataflowBody )
                            // InternalKdl.g:873:7: lv_body_16_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_0_7_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_body_16_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_16_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_17=(Token)match(input,40,FOLLOW_24); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_17, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_0_7_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:895:4: (otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) ) )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==41) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKdl.g:896:5: otherlv_18= 'as' ( (lv_localName_19_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_18=(Token)match(input,41,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_18, grammarAccess.getActorDefinitionAccess().getAsKeyword_0_8_0());
                              				
                            }
                            // InternalKdl.g:900:5: ( (lv_localName_19_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:901:6: (lv_localName_19_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:901:6: (lv_localName_19_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:902:7: lv_localName_19_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_19_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_19_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_0_8_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_19_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:919:4: (otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )* )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==42) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // InternalKdl.g:920:5: otherlv_20= 'over' ( (lv_coverage_21_0= ruleFunction ) ) (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )*
                            {
                            otherlv_20=(Token)match(input,42,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_20, grammarAccess.getActorDefinitionAccess().getOverKeyword_0_9_0());
                              				
                            }
                            // InternalKdl.g:924:5: ( (lv_coverage_21_0= ruleFunction ) )
                            // InternalKdl.g:925:6: (lv_coverage_21_0= ruleFunction )
                            {
                            // InternalKdl.g:925:6: (lv_coverage_21_0= ruleFunction )
                            // InternalKdl.g:926:7: lv_coverage_21_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_9_1_0());
                              						
                            }
                            pushFollow(FOLLOW_26);
                            lv_coverage_21_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_21_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:943:5: (otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) ) )*
                            loop20:
                            do {
                                int alt20=2;
                                int LA20_0 = input.LA(1);

                                if ( (LA20_0==29) ) {
                                    alt20=1;
                                }


                                switch (alt20) {
                            	case 1 :
                            	    // InternalKdl.g:944:6: otherlv_22= ',' ( (lv_coverage_23_0= ruleFunction ) )
                            	    {
                            	    otherlv_22=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_22, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_9_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:948:6: ( (lv_coverage_23_0= ruleFunction ) )
                            	    // InternalKdl.g:949:7: (lv_coverage_23_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:949:7: (lv_coverage_23_0= ruleFunction )
                            	    // InternalKdl.g:950:8: lv_coverage_23_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_9_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_26);
                            	    lv_coverage_23_0=ruleFunction();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"coverage",
                            	      									lv_coverage_23_0,
                            	      									"org.integratedmodelling.kdl.Kdl.Function");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop20;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:971:3: ( ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )? )
                    {
                    // InternalKdl.g:971:3: ( ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )? )
                    // InternalKdl.g:972:4: ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) ) ( (lv_parameter_26_0= 'input' ) ) ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) ) (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_32_0= RULE_STRING ) ) (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )? (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )? ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )?
                    {
                    // InternalKdl.g:972:4: ( ( (lv_optional_24_0= 'optional' ) ) | ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) ) )
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==33) ) {
                        alt23=1;
                    }
                    else if ( ((LA23_0>=43 && LA23_0<=47)) ) {
                        alt23=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 0, input);

                        throw nvae;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKdl.g:973:5: ( (lv_optional_24_0= 'optional' ) )
                            {
                            // InternalKdl.g:973:5: ( (lv_optional_24_0= 'optional' ) )
                            // InternalKdl.g:974:6: (lv_optional_24_0= 'optional' )
                            {
                            // InternalKdl.g:974:6: (lv_optional_24_0= 'optional' )
                            // InternalKdl.g:975:7: lv_optional_24_0= 'optional'
                            {
                            lv_optional_24_0=(Token)match(input,33,FOLLOW_27); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_optional_24_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_0_0_0());
                              						
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
                            // InternalKdl.g:988:5: ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) )
                            {
                            // InternalKdl.g:988:5: ( ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) ) )
                            // InternalKdl.g:989:6: ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) )
                            {
                            // InternalKdl.g:989:6: ( (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' ) )
                            // InternalKdl.g:990:7: (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' )
                            {
                            // InternalKdl.g:990:7: (lv_type_25_1= 'number' | lv_type_25_2= 'boolean' | lv_type_25_3= 'text' | lv_type_25_4= 'list' | lv_type_25_5= 'enum' )
                            int alt22=5;
                            switch ( input.LA(1) ) {
                            case 43:
                                {
                                alt22=1;
                                }
                                break;
                            case 44:
                                {
                                alt22=2;
                                }
                                break;
                            case 45:
                                {
                                alt22=3;
                                }
                                break;
                            case 46:
                                {
                                alt22=4;
                                }
                                break;
                            case 47:
                                {
                                alt22=5;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 22, 0, input);

                                throw nvae;
                            }

                            switch (alt22) {
                                case 1 :
                                    // InternalKdl.g:991:8: lv_type_25_1= 'number'
                                    {
                                    lv_type_25_1=(Token)match(input,43,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_25_1, grammarAccess.getActorDefinitionAccess().getTypeNumberKeyword_1_0_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_25_1, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1002:8: lv_type_25_2= 'boolean'
                                    {
                                    lv_type_25_2=(Token)match(input,44,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_25_2, grammarAccess.getActorDefinitionAccess().getTypeBooleanKeyword_1_0_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_25_2, null);
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1013:8: lv_type_25_3= 'text'
                                    {
                                    lv_type_25_3=(Token)match(input,45,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_25_3, grammarAccess.getActorDefinitionAccess().getTypeTextKeyword_1_0_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_25_3, null);
                                      							
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:1024:8: lv_type_25_4= 'list'
                                    {
                                    lv_type_25_4=(Token)match(input,46,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_25_4, grammarAccess.getActorDefinitionAccess().getTypeListKeyword_1_0_1_0_3());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_25_4, null);
                                      							
                                    }

                                    }
                                    break;
                                case 5 :
                                    // InternalKdl.g:1035:8: lv_type_25_5= 'enum'
                                    {
                                    lv_type_25_5=(Token)match(input,47,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_25_5, grammarAccess.getActorDefinitionAccess().getTypeEnumKeyword_1_0_1_0_4());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_25_5, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1049:4: ( (lv_parameter_26_0= 'input' ) )
                    // InternalKdl.g:1050:5: (lv_parameter_26_0= 'input' )
                    {
                    // InternalKdl.g:1050:5: (lv_parameter_26_0= 'input' )
                    // InternalKdl.g:1051:6: lv_parameter_26_0= 'input'
                    {
                    lv_parameter_26_0=(Token)match(input,48,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_parameter_26_0, grammarAccess.getActorDefinitionAccess().getParameterInputKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(current, "parameter", true, "input");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1063:4: ( ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) ) )
                    // InternalKdl.g:1064:5: ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) )
                    {
                    // InternalKdl.g:1064:5: ( (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING ) )
                    // InternalKdl.g:1065:6: (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING )
                    {
                    // InternalKdl.g:1065:6: (lv_name_27_1= RULE_LOWERCASE_ID | lv_name_27_2= RULE_LOWERCASE_DASHID | lv_name_27_3= RULE_STRING )
                    int alt24=3;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt24=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt24=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt24=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 24, 0, input);

                        throw nvae;
                    }

                    switch (alt24) {
                        case 1 :
                            // InternalKdl.g:1066:7: lv_name_27_1= RULE_LOWERCASE_ID
                            {
                            lv_name_27_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_27_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_2_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_27_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1081:7: lv_name_27_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_27_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_27_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_1_2_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_27_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1096:7: lv_name_27_3= RULE_STRING
                            {
                            lv_name_27_3=(Token)match(input,RULE_STRING,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_27_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_1_2_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_27_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:1113:4: (otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )* )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==49) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // InternalKdl.g:1114:5: otherlv_28= 'values' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )*
                            {
                            otherlv_28=(Token)match(input,49,FOLLOW_29); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_28, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_3_0());
                              				
                            }
                            // InternalKdl.g:1118:5: ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) )
                            // InternalKdl.g:1119:6: (lv_enumValues_29_0= RULE_UPPERCASE_ID )
                            {
                            // InternalKdl.g:1119:6: (lv_enumValues_29_0= RULE_UPPERCASE_ID )
                            // InternalKdl.g:1120:7: lv_enumValues_29_0= RULE_UPPERCASE_ID
                            {
                            lv_enumValues_29_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_enumValues_29_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							addWithLastConsumed(
                              								current,
                              								"enumValues",
                              								lv_enumValues_29_0,
                              								"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1136:5: (otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) ) )*
                            loop25:
                            do {
                                int alt25=2;
                                int LA25_0 = input.LA(1);

                                if ( (LA25_0==29) ) {
                                    alt25=1;
                                }


                                switch (alt25) {
                            	case 1 :
                            	    // InternalKdl.g:1137:6: otherlv_30= ',' ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) )
                            	    {
                            	    otherlv_30=(Token)match(input,29,FOLLOW_29); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_30, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_3_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1141:6: ( (lv_enumValues_31_0= RULE_UPPERCASE_ID ) )
                            	    // InternalKdl.g:1142:7: (lv_enumValues_31_0= RULE_UPPERCASE_ID )
                            	    {
                            	    // InternalKdl.g:1142:7: (lv_enumValues_31_0= RULE_UPPERCASE_ID )
                            	    // InternalKdl.g:1143:8: lv_enumValues_31_0= RULE_UPPERCASE_ID
                            	    {
                            	    lv_enumValues_31_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_30); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								newLeafNode(lv_enumValues_31_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_2_1_0());
                            	      							
                            	    }
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								addWithLastConsumed(
                            	      									current,
                            	      									"enumValues",
                            	      									lv_enumValues_31_0,
                            	      									"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop25;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:1161:4: ( (lv_docstring_32_0= RULE_STRING ) )
                    // InternalKdl.g:1162:5: (lv_docstring_32_0= RULE_STRING )
                    {
                    // InternalKdl.g:1162:5: (lv_docstring_32_0= RULE_STRING )
                    // InternalKdl.g:1163:6: lv_docstring_32_0= RULE_STRING
                    {
                    lv_docstring_32_0=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_docstring_32_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_4_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"docstring",
                      							lv_docstring_32_0,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1179:4: (otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) ) )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==50) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalKdl.g:1180:5: otherlv_33= 'default' ( (lv_default_34_0= ruleValue ) )
                            {
                            otherlv_33=(Token)match(input,50,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_33, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_5_0());
                              				
                            }
                            // InternalKdl.g:1184:5: ( (lv_default_34_0= ruleValue ) )
                            // InternalKdl.g:1185:6: (lv_default_34_0= ruleValue )
                            {
                            // InternalKdl.g:1185:6: (lv_default_34_0= ruleValue )
                            // InternalKdl.g:1186:7: lv_default_34_0= ruleValue
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_33);
                            lv_default_34_0=ruleValue();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"default",
                              								lv_default_34_0,
                              								"org.integratedmodelling.kdl.Kdl.Value");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1204:4: (otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}' )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==39) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalKdl.g:1205:5: otherlv_35= '{' ( (lv_body_36_0= ruleDataflowBody ) ) otherlv_37= '}'
                            {
                            otherlv_35=(Token)match(input,39,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_35, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_6_0());
                              				
                            }
                            // InternalKdl.g:1209:5: ( (lv_body_36_0= ruleDataflowBody ) )
                            // InternalKdl.g:1210:6: (lv_body_36_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1210:6: (lv_body_36_0= ruleDataflowBody )
                            // InternalKdl.g:1211:7: lv_body_36_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_6_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_body_36_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_36_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_37=(Token)match(input,40,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_37, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_6_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1233:4: ( (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) ) | (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) ) | (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) ) )?
                    int alt29=4;
                    switch ( input.LA(1) ) {
                        case 51:
                            {
                            alt29=1;
                            }
                            break;
                        case 52:
                            {
                            alt29=2;
                            }
                            break;
                        case 53:
                            {
                            alt29=3;
                            }
                            break;
                    }

                    switch (alt29) {
                        case 1 :
                            // InternalKdl.g:1234:5: (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1234:5: (otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) ) )
                            // InternalKdl.g:1235:6: otherlv_38= 'minimum' ( (lv_rangeMin_39_0= ruleNumber ) )
                            {
                            otherlv_38=(Token)match(input,51,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_38, grammarAccess.getActorDefinitionAccess().getMinimumKeyword_1_7_0_0());
                              					
                            }
                            // InternalKdl.g:1239:6: ( (lv_rangeMin_39_0= ruleNumber ) )
                            // InternalKdl.g:1240:7: (lv_rangeMin_39_0= ruleNumber )
                            {
                            // InternalKdl.g:1240:7: (lv_rangeMin_39_0= ruleNumber )
                            // InternalKdl.g:1241:8: lv_rangeMin_39_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_7_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMin_39_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMin",
                              									lv_rangeMin_39_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1260:5: (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1260:5: (otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) ) )
                            // InternalKdl.g:1261:6: otherlv_40= 'maximum' ( (lv_rangeMax_41_0= ruleNumber ) )
                            {
                            otherlv_40=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_40, grammarAccess.getActorDefinitionAccess().getMaximumKeyword_1_7_1_0());
                              					
                            }
                            // InternalKdl.g:1265:6: ( (lv_rangeMax_41_0= ruleNumber ) )
                            // InternalKdl.g:1266:7: (lv_rangeMax_41_0= ruleNumber )
                            {
                            // InternalKdl.g:1266:7: (lv_rangeMax_41_0= ruleNumber )
                            // InternalKdl.g:1267:8: lv_rangeMax_41_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_7_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMax_41_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMax",
                              									lv_rangeMax_41_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1286:5: (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1286:5: (otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) ) )
                            // InternalKdl.g:1287:6: otherlv_42= 'range' ( (lv_rangeMin_43_0= ruleNumber ) ) otherlv_44= 'to' ( (lv_rangeMax_45_0= ruleNumber ) )
                            {
                            otherlv_42=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_42, grammarAccess.getActorDefinitionAccess().getRangeKeyword_1_7_2_0());
                              					
                            }
                            // InternalKdl.g:1291:6: ( (lv_rangeMin_43_0= ruleNumber ) )
                            // InternalKdl.g:1292:7: (lv_rangeMin_43_0= ruleNumber )
                            {
                            // InternalKdl.g:1292:7: (lv_rangeMin_43_0= ruleNumber )
                            // InternalKdl.g:1293:8: lv_rangeMin_43_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_7_2_1_0());
                              							
                            }
                            pushFollow(FOLLOW_36);
                            lv_rangeMin_43_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMin",
                              									lv_rangeMin_43_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_44=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_44, grammarAccess.getActorDefinitionAccess().getToKeyword_1_7_2_2());
                              					
                            }
                            // InternalKdl.g:1314:6: ( (lv_rangeMax_45_0= ruleNumber ) )
                            // InternalKdl.g:1315:7: (lv_rangeMax_45_0= ruleNumber )
                            {
                            // InternalKdl.g:1315:7: (lv_rangeMax_45_0= ruleNumber )
                            // InternalKdl.g:1316:8: lv_rangeMax_45_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_7_2_3_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMax_45_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMax",
                              									lv_rangeMax_45_0,
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
    // InternalKdl.g:1340:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1344:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1345:2: iv_ruleDataflowBody= ruleDataflowBody EOF
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
    // InternalKdl.g:1354:1: ruleDataflowBody returns [EObject current=null] : ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
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
            // InternalKdl.g:1363:2: ( ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1364:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1364:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1365:3: () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1365:3: ()
            // InternalKdl.g:1366:4: 
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

            // InternalKdl.g:1375:3: ( (lv_dataflows_1_0= ruleActorDefinition ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=31 && LA31_0<=34)||LA31_0==37||(LA31_0>=43 && LA31_0<=47)||(LA31_0>=61 && LA31_0<=71)) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalKdl.g:1376:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1376:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    // InternalKdl.g:1377:5: lv_dataflows_1_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDataflowBodyAccess().getDataflowsActorDefinitionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_37);
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
            	    break loop31;
                }
            } while (true);

            // InternalKdl.g:1394:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1395:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1395:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1396:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());
            // InternalKdl.g:1399:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1400:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1400:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+
            int cnt34=0;
            loop34:
            do {
                int alt34=5;
                alt34 = dfa34.predict(input);
                switch (alt34) {
            	case 1 :
            	    // InternalKdl.g:1401:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1401:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1402:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKdl.g:1402:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1403:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
            	    // InternalKdl.g:1406:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1406:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1406:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1406:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
            	    {
            	    otherlv_3=(Token)match(input,55,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_3, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1410:9: ( (lv_geometry_4_0= ruleGeometry ) )
            	    // InternalKdl.g:1411:10: (lv_geometry_4_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1411:10: (lv_geometry_4_0= ruleGeometry )
            	    // InternalKdl.g:1412:11: lv_geometry_4_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_38);
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
            	    // InternalKdl.g:1435:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1435:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
            	    // InternalKdl.g:1436:5: {...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKdl.g:1436:109: ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
            	    // InternalKdl.g:1437:6: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
            	    // InternalKdl.g:1440:9: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
            	    // InternalKdl.g:1440:10: {...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1440:19: (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
            	    // InternalKdl.g:1440:20: otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) )
            	    {
            	    otherlv_5=(Token)match(input,56,FOLLOW_39); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_5, grammarAccess.getDataflowBodyAccess().getUnitsKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKdl.g:1444:9: ( (lv_units_6_0= ruleUnit ) )
            	    // InternalKdl.g:1445:10: (lv_units_6_0= ruleUnit )
            	    {
            	    // InternalKdl.g:1445:10: (lv_units_6_0= ruleUnit )
            	    // InternalKdl.g:1446:11: lv_units_6_0= ruleUnit
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getUnitsUnitParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_38);
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
            	    // InternalKdl.g:1469:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
            	    {
            	    // InternalKdl.g:1469:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
            	    // InternalKdl.g:1470:5: {...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKdl.g:1470:109: ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
            	    // InternalKdl.g:1471:6: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
            	    // InternalKdl.g:1474:9: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
            	    // InternalKdl.g:1474:10: {...}? => ( (lv_computations_7_0= ruleComputation ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1474:19: ( (lv_computations_7_0= ruleComputation ) )
            	    // InternalKdl.g:1474:20: (lv_computations_7_0= ruleComputation )
            	    {
            	    // InternalKdl.g:1474:20: (lv_computations_7_0= ruleComputation )
            	    // InternalKdl.g:1475:10: lv_computations_7_0= ruleComputation
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_2_2_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_38);
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
            	    // InternalKdl.g:1497:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1497:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
            	    // InternalKdl.g:1498:5: {...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKdl.g:1498:109: ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
            	    // InternalKdl.g:1499:6: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3);
            	    // InternalKdl.g:1502:9: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
            	    // InternalKdl.g:1502:10: {...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1502:19: ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
            	    // InternalKdl.g:1502:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
            	    {
            	    // InternalKdl.g:1502:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )?
            	    int alt32=2;
            	    int LA32_0 = input.LA(1);

            	    if ( (LA32_0==57) ) {
            	        int LA32_1 = input.LA(2);

            	        if ( (synpred55_InternalKdl()) ) {
            	            alt32=1;
            	        }
            	    }
            	    switch (alt32) {
            	        case 1 :
            	            // InternalKdl.g:1503:10: otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) )
            	            {
            	            otherlv_8=(Token)match(input,57,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_8, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_2_3_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1507:10: ( (lv_metadata_9_0= ruleMetadata ) )
            	            // InternalKdl.g:1508:11: (lv_metadata_9_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1508:11: (lv_metadata_9_0= ruleMetadata )
            	            // InternalKdl.g:1509:12: lv_metadata_9_0= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_3_0_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_38);
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

            	    // InternalKdl.g:1527:9: (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
            	    int alt33=2;
            	    int LA33_0 = input.LA(1);

            	    if ( (LA33_0==58) ) {
            	        int LA33_1 = input.LA(2);

            	        if ( (synpred56_InternalKdl()) ) {
            	            alt33=1;
            	        }
            	    }
            	    switch (alt33) {
            	        case 1 :
            	            // InternalKdl.g:1528:10: otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) )
            	            {
            	            otherlv_10=(Token)match(input,58,FOLLOW_3); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_10, grammarAccess.getDataflowBodyAccess().getClassKeyword_2_3_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1532:10: ( (lv_javaClass_11_0= ruleJavaClass ) )
            	            // InternalKdl.g:1533:11: (lv_javaClass_11_0= ruleJavaClass )
            	            {
            	            // InternalKdl.g:1533:11: (lv_javaClass_11_0= ruleJavaClass )
            	            // InternalKdl.g:1534:12: lv_javaClass_11_0= ruleJavaClass
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_2_3_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_38);
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
            	    if ( cnt34 >= 1 ) break loop34;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(34, input);
                        throw eee;
                }
                cnt34++;
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
    // InternalKdl.g:1573:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1573:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1574:2: iv_ruleComputation= ruleComputation EOF
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
    // InternalKdl.g:1580:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1586:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1587:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1587:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1588:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,59,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1592:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1593:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1593:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1594:5: lv_functions_1_0= ruleFunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_26);
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

            // InternalKdl.g:1611:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==29) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalKdl.g:1612:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1616:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1617:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1617:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1618:6: lv_functions_3_0= ruleFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_26);
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
            	    break loop35;
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
    // InternalKdl.g:1640:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1640:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1641:2: iv_ruleGeometry= ruleGeometry EOF
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
    // InternalKdl.g:1647:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1653:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1654:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1654:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==60) ) {
                alt37=1;
            }
            else if ( (LA37_0==RULE_SHAPE) ) {
                alt37=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // InternalKdl.g:1655:3: kw= '*'
                    {
                    kw=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1661:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1661:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1662:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:1669:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==29) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalKdl.g:1670:5: kw= ',' this_SHAPE_3= RULE_SHAPE
                    	    {
                    	    kw=(Token)match(input,29,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getGeometryAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    this_SHAPE_3=(Token)match(input,RULE_SHAPE,FOLLOW_26); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_SHAPE_3);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_SHAPE_3, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
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
    // InternalKdl.g:1688:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:1688:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:1689:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalKdl.g:1695:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1701:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:1702:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:1702:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:1703:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:1703:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:1704:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:1704:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:1705:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_32); if (state.failed) return current;
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

            // InternalKdl.g:1721:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:1722:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:1722:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:1723:5: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_42);
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

            // InternalKdl.g:1740:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_STRING) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalKdl.g:1741:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:1741:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:1742:5: lv_docstring_2_0= RULE_STRING
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
    // InternalKdl.g:1762:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:1762:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:1763:2: iv_ruleACTOR= ruleACTOR EOF
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
    // InternalKdl.g:1769:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:1775:2: ( (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) )
            // InternalKdl.g:1776:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            {
            // InternalKdl.g:1776:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            int alt39=14;
            switch ( input.LA(1) ) {
            case 61:
                {
                alt39=1;
                }
                break;
            case 62:
                {
                alt39=2;
                }
                break;
            case 63:
                {
                alt39=3;
                }
                break;
            case 43:
                {
                alt39=4;
                }
                break;
            case 64:
                {
                alt39=5;
                }
                break;
            case 44:
                {
                alt39=6;
                }
                break;
            case 45:
                {
                alt39=7;
                }
                break;
            case 65:
                {
                alt39=8;
                }
                break;
            case 66:
                {
                alt39=9;
                }
                break;
            case 67:
                {
                alt39=10;
                }
                break;
            case 68:
                {
                alt39=11;
                }
                break;
            case 69:
                {
                alt39=12;
                }
                break;
            case 70:
                {
                alt39=13;
                }
                break;
            case 71:
                {
                alt39=14;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // InternalKdl.g:1777:3: kw= 'object'
                    {
                    kw=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1783:3: kw= 'process'
                    {
                    kw=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:1789:3: kw= 'value'
                    {
                    kw=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:1795:3: kw= 'number'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:1801:3: kw= 'concept'
                    {
                    kw=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:1807:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:1813:3: kw= 'text'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:1819:3: kw= 'extent'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:1825:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:1831:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:1837:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:1843:3: kw= 'contextualizer'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getContextualizerKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:1849:3: kw= 'void'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:1855:3: kw= 'partition'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:1864:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:1864:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:1865:2: iv_ruleTARGET= ruleTARGET EOF
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
    // InternalKdl.g:1871:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:1877:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' ) )
            // InternalKdl.g:1878:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' )
            {
            // InternalKdl.g:1878:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' )
            int alt40=4;
            switch ( input.LA(1) ) {
            case 72:
                {
                alt40=1;
                }
                break;
            case 73:
                {
                alt40=2;
                }
                break;
            case 74:
                {
                alt40=3;
                }
                break;
            case 75:
                {
                alt40=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // InternalKdl.g:1879:3: kw= 'models'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1885:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:1891:3: kw= 'observers'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getObserversKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:1897:3: kw= 'definitions'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:1906:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:1906:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:1907:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
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
    // InternalKdl.g:1913:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
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
            // InternalKdl.g:1919:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:1920:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:1920:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt45=10;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // InternalKdl.g:1921:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:1921:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==76) ) {
                        alt41=1;
                    }
                    else if ( (LA41_0==77) ) {
                        alt41=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 41, 0, input);

                        throw nvae;
                    }
                    switch (alt41) {
                        case 1 :
                            // InternalKdl.g:1922:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:1922:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:1923:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:1923:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:1924:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:1937:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:1937:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:1938:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:1938:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:1939:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:1953:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:1953:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:1954:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:1954:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:1955:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:1955:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:1956:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
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

                    // InternalKdl.g:1973:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt42=3;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==78) ) {
                        alt42=1;
                    }
                    else if ( (LA42_0==79) ) {
                        alt42=2;
                    }
                    switch (alt42) {
                        case 1 :
                            // InternalKdl.g:1974:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:1974:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:1975:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:1975:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:1976:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,78,FOLLOW_36); if (state.failed) return current;
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
                            // InternalKdl.g:1989:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,79,FOLLOW_36); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1994:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:1995:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:2001:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:2002:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:2006:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:2007:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKdl.g:2024:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt43=3;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==78) ) {
                        alt43=1;
                    }
                    else if ( (LA43_0==79) ) {
                        alt43=2;
                    }
                    switch (alt43) {
                        case 1 :
                            // InternalKdl.g:2025:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2025:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:2026:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:2026:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:2027:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2040:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2047:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2047:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:2048:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:2048:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:2049:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:2067:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:2067:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:2068:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,80,FOLLOW_45); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:2072:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:2073:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:2073:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:2074:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:2093:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2093:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:2094:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:2094:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:2095:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:2112:3: ( (lv_map_13_0= ruleMap ) )
                    {
                    // InternalKdl.g:2112:3: ( (lv_map_13_0= ruleMap ) )
                    // InternalKdl.g:2113:4: (lv_map_13_0= ruleMap )
                    {
                    // InternalKdl.g:2113:4: (lv_map_13_0= ruleMap )
                    // InternalKdl.g:2114:5: lv_map_13_0= ruleMap
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
                    // InternalKdl.g:2132:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:2132:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:2133:4: otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,81,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:2137:4: ( (lv_toResolve_15_0= RULE_STRING ) )
                    // InternalKdl.g:2138:5: (lv_toResolve_15_0= RULE_STRING )
                    {
                    // InternalKdl.g:2138:5: (lv_toResolve_15_0= RULE_STRING )
                    // InternalKdl.g:2139:6: lv_toResolve_15_0= RULE_STRING
                    {
                    lv_toResolve_15_0=(Token)match(input,RULE_STRING,FOLLOW_46); if (state.failed) return current;
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

                    // InternalKdl.g:2155:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==29) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // InternalKdl.g:2156:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    {
                    	    // InternalKdl.g:2156:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:2157:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,29,FOLLOW_6); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:2163:5: ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    // InternalKdl.g:2164:6: (lv_toResolve_17_0= RULE_STRING )
                    	    {
                    	    // InternalKdl.g:2164:6: (lv_toResolve_17_0= RULE_STRING )
                    	    // InternalKdl.g:2165:7: lv_toResolve_17_0= RULE_STRING
                    	    {
                    	    lv_toResolve_17_0=(Token)match(input,RULE_STRING,FOLLOW_46); if (state.failed) return current;
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
                    	    break loop44;
                        }
                    } while (true);

                    otherlv_18=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getClassifierRHSAccess().getRightParenthesisKeyword_6_3());
                      			
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:2188:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2188:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:2189:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2189:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:2190:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:2190:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:2191:6: lv_op_19_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
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

                    // InternalKdl.g:2208:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:2209:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:2209:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:2210:6: lv_expression_20_0= ruleNumber
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
                    // InternalKdl.g:2229:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:2229:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:2230:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:2230:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:2231:5: lv_nodata_21_0= 'unknown'
                    {
                    lv_nodata_21_0=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2244:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:2244:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:2245:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:2245:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:2246:5: lv_star_22_0= '*'
                    {
                    lv_star_22_0=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2262:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:2262:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:2263:2: iv_ruleList= ruleList EOF
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
    // InternalKdl.g:2269:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2275:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:2276:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:2276:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:2277:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:2277:3: ()
            // InternalKdl.g:2278:4: 
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

            otherlv_1=(Token)match(input,81,FOLLOW_47); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKdl.g:2291:3: ( (lv_contents_2_0= ruleValue ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=RULE_STRING && LA46_0<=RULE_UPPERCASE_ID)||(LA46_0>=RULE_ID && LA46_0<=RULE_CAMELCASE_ID)||LA46_0==29||LA46_0==36||LA46_0==39||(LA46_0>=76 && LA46_0<=77)||LA46_0==81||LA46_0==84||LA46_0==87||LA46_0==100) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalKdl.g:2292:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:2292:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:2293:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_47);
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
            	    break loop46;
                }
            } while (true);

            otherlv_3=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2318:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:2318:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:2319:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKdl.g:2325:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
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
            // InternalKdl.g:2331:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:2332:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:2332:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt48=4;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // InternalKdl.g:2333:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2333:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2334:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2334:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2335:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:2353:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2353:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:2354:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2354:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:2355:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:2355:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:2356:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    otherlv_2=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:2377:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:2378:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2378:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:2379:6: lv_to_3_0= ruleNumber
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
                    // InternalKdl.g:2398:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2398:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2399:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2399:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2400:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:2417:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2417:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:2418:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:2418:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:2419:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:2419:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==76) ) {
                        alt47=1;
                    }
                    else if ( (LA47_0==77) ) {
                        alt47=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 47, 0, input);

                        throw nvae;
                    }
                    switch (alt47) {
                        case 1 :
                            // InternalKdl.g:2420:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2431:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2448:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:2448:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:2449:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
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
    // InternalKdl.g:2455:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
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
            // InternalKdl.g:2461:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:2462:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:2462:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt50=6;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // InternalKdl.g:2463:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2463:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:2464:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2464:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:2465:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2465:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:2466:6: lv_from_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getFromNumberParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    // InternalKdl.g:2483:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:2484:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:2490:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:2491:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:2495:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:2496:6: lv_to_2_0= ruleNumber
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
                    // InternalKdl.g:2515:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2515:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:2516:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2516:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:2517:5: lv_number_3_0= ruleNumber
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
                    // InternalKdl.g:2535:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2535:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2536:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2536:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2537:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:2554:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2554:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:2555:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:2555:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:2556:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:2556:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==76) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==77) ) {
                        alt49=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 49, 0, input);

                        throw nvae;
                    }
                    switch (alt49) {
                        case 1 :
                            // InternalKdl.g:2557:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2568:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2582:3: ( (lv_id_6_0= RULE_ID ) )
                    {
                    // InternalKdl.g:2582:3: ( (lv_id_6_0= RULE_ID ) )
                    // InternalKdl.g:2583:4: (lv_id_6_0= RULE_ID )
                    {
                    // InternalKdl.g:2583:4: (lv_id_6_0= RULE_ID )
                    // InternalKdl.g:2584:5: lv_id_6_0= RULE_ID
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
                    // InternalKdl.g:2601:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:2601:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:2602:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:2602:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:2603:5: lv_comma_7_0= ','
                    {
                    lv_comma_7_0=(Token)match(input,29,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2619:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:2619:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:2620:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
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
    // InternalKdl.g:2626:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2632:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:2633:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:2633:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt52=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 36:
            case 100:
                {
                alt52=1;
                }
                break;
            case RULE_STRING:
                {
                alt52=2;
                }
                break;
            case 76:
            case 77:
                {
                alt52=3;
                }
                break;
            case RULE_ID:
                {
                alt52=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }

            switch (alt52) {
                case 1 :
                    // InternalKdl.g:2634:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2634:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2635:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2635:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2636:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:2654:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2654:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:2655:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:2655:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:2656:5: lv_string_1_0= RULE_STRING
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
                    // InternalKdl.g:2673:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2673:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:2674:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:2674:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:2675:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:2675:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==76) ) {
                        alt51=1;
                    }
                    else if ( (LA51_0==77) ) {
                        alt51=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 51, 0, input);

                        throw nvae;
                    }
                    switch (alt51) {
                        case 1 :
                            // InternalKdl.g:2676:6: lv_boolean_2_1= 'true'
                            {
                            lv_boolean_2_1=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2687:6: lv_boolean_2_2= 'false'
                            {
                            lv_boolean_2_2=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2701:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:2701:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:2702:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:2702:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:2703:5: lv_id_3_0= RULE_ID
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
    // InternalKdl.g:2723:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:2723:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:2724:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKdl.g:2730:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
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
            // InternalKdl.g:2736:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:2737:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:2737:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:2738:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:2738:3: ()
            // InternalKdl.g:2739:4: 
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

            otherlv_1=(Token)match(input,39,FOLLOW_48); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMetadataAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:2752:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==RULE_LOWERCASE_ID) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalKdl.g:2753:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:2753:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:2754:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:2754:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:2755:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:2755:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt53=2;
            	    int LA53_0 = input.LA(1);

            	    if ( (LA53_0==RULE_LOWERCASE_ID) ) {
            	        int LA53_1 = input.LA(2);

            	        if ( (LA53_1==85||LA53_1==91) ) {
            	            alt53=2;
            	        }
            	        else if ( (LA53_1==RULE_STRING||LA53_1==RULE_INT||LA53_1==RULE_ID||LA53_1==36||LA53_1==39||(LA53_1>=76 && LA53_1<=77)||LA53_1==81||LA53_1==100) ) {
            	            alt53=1;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 53, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 53, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt53) {
            	        case 1 :
            	            // InternalKdl.g:2756:7: lv_ids_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_ids_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_49); if (state.failed) return current;
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
            	            // InternalKdl.g:2771:7: lv_ids_2_2= rulePropertyId
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getIdsPropertyIdParserRuleCall_2_0_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_49);
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

            	    // InternalKdl.g:2789:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:2790:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:2790:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:2791:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:2791:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt54=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 36:
            	    case 76:
            	    case 77:
            	    case 100:
            	        {
            	        alt54=1;
            	        }
            	        break;
            	    case 39:
            	        {
            	        alt54=2;
            	        }
            	        break;
            	    case 81:
            	        {
            	        alt54=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 54, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt54) {
            	        case 1 :
            	            // InternalKdl.g:2792:7: lv_values_3_1= ruleLiteralOrID
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesLiteralOrIDParserRuleCall_2_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_48);
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
            	            // InternalKdl.g:2808:7: lv_values_3_2= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesMetadataParserRuleCall_2_1_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_48);
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
            	            // InternalKdl.g:2824:7: lv_values_3_3= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesListParserRuleCall_2_1_0_2());
            	              						
            	            }
            	            pushFollow(FOLLOW_48);
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
            	    break loop55;
                }
            } while (true);

            otherlv_4=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2851:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:2851:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:2852:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKdl.g:2858:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
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
            // InternalKdl.g:2864:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:2865:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:2865:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt58=2;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INT:
            case RULE_UPPERCASE_ID:
            case RULE_ID:
            case RULE_EXPR:
            case RULE_CAMELCASE_ID:
            case 29:
            case 36:
            case 39:
            case 76:
            case 77:
            case 81:
            case 84:
            case 87:
            case 100:
                {
                alt58=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                int LA58_2 = input.LA(2);

                if ( (LA58_2==EOF||LA58_2==29||LA58_2==41||(LA58_2>=81 && LA58_2<=82)||(LA58_2>=85 && LA58_2<=86)||(LA58_2>=90 && LA58_2<=91)||LA58_2==94) ) {
                    alt58=1;
                }
                else if ( ((LA58_2>=92 && LA58_2<=93)) ) {
                    alt58=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 58, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                int LA58_3 = input.LA(2);

                if ( ((LA58_3>=92 && LA58_3<=93)) ) {
                    alt58=2;
                }
                else if ( (LA58_3==EOF||LA58_3==29||LA58_3==41||LA58_3==82||LA58_3==86||(LA58_3>=90 && LA58_3<=91)) ) {
                    alt58=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 58, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // InternalKdl.g:2866:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:2866:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:2867:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:2867:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:2868:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:2868:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:2869:6: lv_values_0_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
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

                    // InternalKdl.g:2886:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop56:
                    do {
                        int alt56=2;
                        int LA56_0 = input.LA(1);

                        if ( (LA56_0==29) ) {
                            alt56=1;
                        }


                        switch (alt56) {
                    	case 1 :
                    	    // InternalKdl.g:2887:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,29,FOLLOW_32); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:2891:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:2892:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:2892:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:2893:7: lv_values_2_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop56;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2913:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:2913:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:2914:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:2914:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:2915:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:2915:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:2916:6: lv_pairs_3_0= ruleKeyValuePair
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
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

                    // InternalKdl.g:2933:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==29) ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // InternalKdl.g:2934:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:2934:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:2935:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,29,FOLLOW_50); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:2941:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:2942:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:2942:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:2943:7: lv_pairs_5_0= ruleKeyValuePair
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop57;
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
    // InternalKdl.g:2966:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:2966:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:2967:2: iv_ruleValue= ruleValue EOF
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
    // InternalKdl.g:2973:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) ;
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
            // InternalKdl.g:2979:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:2980:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:2980:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            int alt59=8;
            alt59 = dfa59.predict(input);
            switch (alt59) {
                case 1 :
                    // InternalKdl.g:2981:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:2981:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:2982:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:2982:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:2983:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
                    // InternalKdl.g:3001:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:3001:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:3002:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:3002:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:3003:5: lv_function_1_0= ruleFunction
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
                    // InternalKdl.g:3021:3: ( (lv_urn_2_0= ruleUrn ) )
                    {
                    // InternalKdl.g:3021:3: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:3022:4: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:3022:4: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:3023:5: lv_urn_2_0= ruleUrn
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
                    // InternalKdl.g:3041:3: ( (lv_list_3_0= ruleList ) )
                    {
                    // InternalKdl.g:3041:3: ( (lv_list_3_0= ruleList ) )
                    // InternalKdl.g:3042:4: (lv_list_3_0= ruleList )
                    {
                    // InternalKdl.g:3042:4: (lv_list_3_0= ruleList )
                    // InternalKdl.g:3043:5: lv_list_3_0= ruleList
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
                    // InternalKdl.g:3061:3: ( (lv_map_4_0= ruleMap ) )
                    {
                    // InternalKdl.g:3061:3: ( (lv_map_4_0= ruleMap ) )
                    // InternalKdl.g:3062:4: (lv_map_4_0= ruleMap )
                    {
                    // InternalKdl.g:3062:4: (lv_map_4_0= ruleMap )
                    // InternalKdl.g:3063:5: lv_map_4_0= ruleMap
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
                    // InternalKdl.g:3081:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3081:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    // InternalKdl.g:3082:4: (lv_expression_5_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3082:4: (lv_expression_5_0= RULE_EXPR )
                    // InternalKdl.g:3083:5: lv_expression_5_0= RULE_EXPR
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
                    // InternalKdl.g:3100:3: ( (lv_table_6_0= ruleLookupTable ) )
                    {
                    // InternalKdl.g:3100:3: ( (lv_table_6_0= ruleLookupTable ) )
                    // InternalKdl.g:3101:4: (lv_table_6_0= ruleLookupTable )
                    {
                    // InternalKdl.g:3101:4: (lv_table_6_0= ruleLookupTable )
                    // InternalKdl.g:3102:5: lv_table_6_0= ruleLookupTable
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
                    // InternalKdl.g:3120:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3120:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3121:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3121:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:3122:5: lv_enumId_7_0= RULE_UPPERCASE_ID
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
    // InternalKdl.g:3142:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:3142:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:3143:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKdl.g:3149:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:3155:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:3156:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:3156:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:3157:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:3157:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:3158:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:3158:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt60=3;
            switch ( input.LA(1) ) {
            case 84:
                {
                alt60=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case RULE_STRING:
                case RULE_LOWERCASE_ID:
                case RULE_INT:
                case RULE_LOWERCASE_DASHID:
                case RULE_UPPERCASE_ID:
                case RULE_ID:
                case RULE_EXPR:
                case RULE_CAMELCASE_ID:
                case 18:
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
                case 36:
                case 37:
                case 39:
                case 40:
                case 41:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 51:
                case 52:
                case 53:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 76:
                case 77:
                case 81:
                case 82:
                case 84:
                case 86:
                case 87:
                case 90:
                case 100:
                    {
                    alt60=3;
                    }
                    break;
                case 91:
                    {
                    int LA60_5 = input.LA(3);

                    if ( (LA60_5==RULE_LOWERCASE_ID) ) {
                        int LA60_6 = input.LA(4);

                        if ( (LA60_6==85||LA60_6==91) ) {
                            alt60=1;
                        }
                        else if ( (LA60_6==EOF||(LA60_6>=RULE_STRING && LA60_6<=RULE_UPPERCASE_ID)||(LA60_6>=RULE_ID && LA60_6<=RULE_CAMELCASE_ID)||(LA60_6>=18 && LA60_6<=34)||(LA60_6>=36 && LA60_6<=37)||(LA60_6>=39 && LA60_6<=41)||(LA60_6>=43 && LA60_6<=47)||(LA60_6>=51 && LA60_6<=53)||(LA60_6>=55 && LA60_6<=59)||(LA60_6>=61 && LA60_6<=71)||(LA60_6>=76 && LA60_6<=77)||(LA60_6>=81 && LA60_6<=82)||LA60_6==84||(LA60_6>=86 && LA60_6<=87)||LA60_6==100) ) {
                            alt60=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 60, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 60, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 85:
                    {
                    alt60=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 60, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt60=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
                {
                alt60=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // InternalKdl.g:3159:5: lv_name_0_1= ruleUrnId
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
                    // InternalKdl.g:3175:5: lv_name_0_2= RULE_STRING
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
                    // InternalKdl.g:3190:5: lv_name_0_3= ruleLocalFilePath
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
    // InternalKdl.g:3211:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:3211:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:3212:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKdl.g:3218:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:3224:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:3225:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:3225:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:3226:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:3226:3: (kw= 'urn:klab:' )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==84) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalKdl.g:3227:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,84,FOLLOW_3); if (state.failed) return current;
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
            pushFollow(FOLLOW_51);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,85,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_51);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,85,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_51);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,85,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_52);
            this_Path_7=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_Path_7);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalKdl.g:3288:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==85) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalKdl.g:3289:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
                    {
                    kw=(Token)match(input,85,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_53);
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

            // InternalKdl.g:3305:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==86) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalKdl.g:3306:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,86,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:3323:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKdl.g:3323:44: (iv_ruleMap= ruleMap EOF )
            // InternalKdl.g:3324:2: iv_ruleMap= ruleMap EOF
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
    // InternalKdl.g:3330:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3336:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKdl.g:3337:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKdl.g:3337:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKdl.g:3338:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKdl.g:3338:3: ()
            // InternalKdl.g:3339:4: 
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

            otherlv_1=(Token)match(input,39,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3352:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==RULE_STRING||LA65_0==RULE_INT||LA65_0==36||LA65_0==39||LA65_0==60||(LA65_0>=76 && LA65_0<=77)||(LA65_0>=80 && LA65_0<=81)||LA65_0==83||LA65_0==93||(LA65_0>=95 && LA65_0<=100)) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalKdl.g:3353:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKdl.g:3353:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKdl.g:3354:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKdl.g:3354:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKdl.g:3355:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
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

                    // InternalKdl.g:3372:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop64:
                    do {
                        int alt64=2;
                        int LA64_0 = input.LA(1);

                        if ( (LA64_0==29) ) {
                            alt64=1;
                        }


                        switch (alt64) {
                    	case 1 :
                    	    // InternalKdl.g:3373:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKdl.g:3373:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKdl.g:3374:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,29,FOLLOW_56); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3381:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKdl.g:3382:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKdl.g:3382:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKdl.g:3383:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_55);
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
                    	    break loop64;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3410:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKdl.g:3410:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKdl.g:3411:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKdl.g:3417:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3423:2: ( ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKdl.g:3424:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKdl.g:3424:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKdl.g:3425:3: ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKdl.g:3425:3: ( (lv_classifier_0_0= ruleClassifierRHS ) )
            // InternalKdl.g:3426:4: (lv_classifier_0_0= ruleClassifierRHS )
            {
            // InternalKdl.g:3426:4: (lv_classifier_0_0= ruleClassifierRHS )
            // InternalKdl.g:3427:5: lv_classifier_0_0= ruleClassifierRHS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierRHSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_51);
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

            otherlv_1=(Token)match(input,85,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:3448:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKdl.g:3449:4: (lv_value_2_0= ruleValue )
            {
            // InternalKdl.g:3449:4: (lv_value_2_0= ruleValue )
            // InternalKdl.g:3450:5: lv_value_2_0= ruleValue
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
    // InternalKdl.g:3471:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKdl.g:3471:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKdl.g:3472:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKdl.g:3478:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3484:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKdl.g:3485:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKdl.g:3485:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKdl.g:3486:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKdl.g:3486:3: ()
            // InternalKdl.g:3487:4: 
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

            otherlv_1=(Token)match(input,87,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3500:3: ( (lv_table_2_0= ruleTable ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==RULE_STRING||LA66_0==RULE_INT||LA66_0==RULE_EXPR||LA66_0==36||LA66_0==60||(LA66_0>=76 && LA66_0<=77)||LA66_0==80||LA66_0==83||LA66_0==86||LA66_0==93||(LA66_0>=95 && LA66_0<=100)) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalKdl.g:3501:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKdl.g:3501:4: (lv_table_2_0= ruleTable )
                    // InternalKdl.g:3502:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_58);
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

            otherlv_3=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3527:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKdl.g:3527:46: (iv_ruleTable= ruleTable EOF )
            // InternalKdl.g:3528:2: iv_ruleTable= ruleTable EOF
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
    // InternalKdl.g:3534:1: ruleTable returns [EObject current=null] : ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_rows_0_0 = null;

        EObject lv_rows_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3540:2: ( ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) )
            // InternalKdl.g:3541:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            {
            // InternalKdl.g:3541:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            // InternalKdl.g:3542:3: ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            {
            // InternalKdl.g:3542:3: ( (lv_rows_0_0= ruleTableRow ) )
            // InternalKdl.g:3543:4: (lv_rows_0_0= ruleTableRow )
            {
            // InternalKdl.g:3543:4: (lv_rows_0_0= ruleTableRow )
            // InternalKdl.g:3544:5: lv_rows_0_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_26);
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

            // InternalKdl.g:3561:3: (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==29) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalKdl.g:3562:4: otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) )
            	    {
            	    otherlv_1=(Token)match(input,29,FOLLOW_59); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:3566:4: ( (lv_rows_2_0= ruleTableRow ) )
            	    // InternalKdl.g:3567:5: (lv_rows_2_0= ruleTableRow )
            	    {
            	    // InternalKdl.g:3567:5: (lv_rows_2_0= ruleTableRow )
            	    // InternalKdl.g:3568:6: lv_rows_2_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_26);
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
            	    break loop67;
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
    // InternalKdl.g:3590:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKdl.g:3590:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKdl.g:3591:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKdl.g:3597:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3603:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKdl.g:3604:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKdl.g:3604:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKdl.g:3605:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKdl.g:3605:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKdl.g:3606:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKdl.g:3606:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKdl.g:3607:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_60);
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

            // InternalKdl.g:3624:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==89) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // InternalKdl.g:3625:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,89,FOLLOW_59); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:3629:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKdl.g:3630:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKdl.g:3630:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKdl.g:3631:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_60);
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
            	    break loop68;
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
    // InternalKdl.g:3653:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKdl.g:3653:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKdl.g:3654:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKdl.g:3660:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) ;
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
            // InternalKdl.g:3666:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) )
            // InternalKdl.g:3667:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            {
            // InternalKdl.g:3667:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            int alt72=10;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // InternalKdl.g:3668:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:3668:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==76) ) {
                        alt69=1;
                    }
                    else if ( (LA69_0==77) ) {
                        alt69=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 69, 0, input);

                        throw nvae;
                    }
                    switch (alt69) {
                        case 1 :
                            // InternalKdl.g:3669:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:3669:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:3670:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:3670:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:3671:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3684:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:3684:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:3685:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:3685:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:3686:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3700:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:3700:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:3701:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:3701:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:3702:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3702:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:3703:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
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

                    // InternalKdl.g:3720:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt70=3;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==78) ) {
                        alt70=1;
                    }
                    else if ( (LA70_0==79) ) {
                        alt70=2;
                    }
                    switch (alt70) {
                        case 1 :
                            // InternalKdl.g:3721:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3721:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:3722:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:3722:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:3723:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,78,FOLLOW_36); if (state.failed) return current;
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
                            // InternalKdl.g:3736:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,79,FOLLOW_36); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:3741:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:3742:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getTableClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:3748:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:3749:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:3753:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:3754:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKdl.g:3771:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt71=3;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==78) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==79) ) {
                        alt71=2;
                    }
                    switch (alt71) {
                        case 1 :
                            // InternalKdl.g:3772:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3772:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:3773:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:3773:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:3774:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3787:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3794:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3794:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:3795:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:3795:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:3796:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:3814:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:3814:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:3815:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,80,FOLLOW_45); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:3819:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:3820:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:3820:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:3821:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:3840:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3840:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:3841:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:3841:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:3842:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:3859:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3859:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    // InternalKdl.g:3860:4: ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3860:4: ( (lv_op_13_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:3861:5: (lv_op_13_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:3861:5: (lv_op_13_0= ruleREL_OPERATOR )
                    // InternalKdl.g:3862:6: lv_op_13_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
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

                    // InternalKdl.g:3879:4: ( (lv_expression_14_0= ruleNumber ) )
                    // InternalKdl.g:3880:5: (lv_expression_14_0= ruleNumber )
                    {
                    // InternalKdl.g:3880:5: (lv_expression_14_0= ruleNumber )
                    // InternalKdl.g:3881:6: lv_expression_14_0= ruleNumber
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
                    // InternalKdl.g:3900:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3900:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    // InternalKdl.g:3901:4: (lv_expr_15_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3901:4: (lv_expr_15_0= RULE_EXPR )
                    // InternalKdl.g:3902:5: lv_expr_15_0= RULE_EXPR
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
                    // InternalKdl.g:3919:3: ( (lv_nodata_16_0= 'unknown' ) )
                    {
                    // InternalKdl.g:3919:3: ( (lv_nodata_16_0= 'unknown' ) )
                    // InternalKdl.g:3920:4: (lv_nodata_16_0= 'unknown' )
                    {
                    // InternalKdl.g:3920:4: (lv_nodata_16_0= 'unknown' )
                    // InternalKdl.g:3921:5: lv_nodata_16_0= 'unknown'
                    {
                    lv_nodata_16_0=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3934:3: ( (lv_star_17_0= '*' ) )
                    {
                    // InternalKdl.g:3934:3: ( (lv_star_17_0= '*' ) )
                    // InternalKdl.g:3935:4: (lv_star_17_0= '*' )
                    {
                    // InternalKdl.g:3935:4: (lv_star_17_0= '*' )
                    // InternalKdl.g:3936:5: lv_star_17_0= '*'
                    {
                    lv_star_17_0=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3949:3: ( (lv_anything_18_0= '#' ) )
                    {
                    // InternalKdl.g:3949:3: ( (lv_anything_18_0= '#' ) )
                    // InternalKdl.g:3950:4: (lv_anything_18_0= '#' )
                    {
                    // InternalKdl.g:3950:4: (lv_anything_18_0= '#' )
                    // InternalKdl.g:3951:5: lv_anything_18_0= '#'
                    {
                    lv_anything_18_0=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3967:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:3967:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:3968:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
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
    // InternalKdl.g:3974:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:3980:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:3981:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:3981:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:3982:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:3982:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt73=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt73=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt73=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                alt73=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }

            switch (alt73) {
                case 1 :
                    // InternalKdl.g:3983:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_CAMELCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:3991:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:3999:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_2, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4007:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==90) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // InternalKdl.g:4008:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,90,FOLLOW_62); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4013:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    int alt74=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_CAMELCASE_ID:
            	        {
            	        alt74=1;
            	        }
            	        break;
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt74=2;
            	        }
            	        break;
            	    case RULE_LOWERCASE_DASHID:
            	        {
            	        alt74=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 74, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt74) {
            	        case 1 :
            	            // InternalKdl.g:4014:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
            	            {
            	            this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_61); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_CAMELCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CAMELCASE_ID_4, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4022:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_61); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_5, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:4030:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
            	            {
            	            this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_61); if (state.failed) return current;
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
            	    break loop75;
                }
            } while (true);

            // InternalKdl.g:4039:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==91) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalKdl.g:4040:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,91,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getFullStopKeyword_2_0());
                      			
                    }
                    this_LOWERCASE_ID_8=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_8, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4053:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==86) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalKdl.g:4054:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,86,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:4071:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4071:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4072:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKdl.g:4078:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_1=null;
        Token lv_name_0_2=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4084:2: ( ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4085:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4085:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4086:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4086:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:4087:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:4087:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:4088:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            {
            // InternalKdl.g:4088:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==RULE_LOWERCASE_ID) ) {
                alt78=1;
            }
            else if ( (LA78_0==RULE_LOWERCASE_DASHID) ) {
                alt78=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // InternalKdl.g:4089:6: lv_name_0_1= RULE_LOWERCASE_ID
                    {
                    lv_name_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_63); if (state.failed) return current;
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
                    // InternalKdl.g:4104:6: lv_name_0_2= RULE_LOWERCASE_DASHID
                    {
                    lv_name_0_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_63); if (state.failed) return current;
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

            // InternalKdl.g:4121:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==92) ) {
                alt79=1;
            }
            else if ( (LA79_0==93) ) {
                alt79=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }
            switch (alt79) {
                case 1 :
                    // InternalKdl.g:4122:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4122:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4123:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4123:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4124:6: lv_interactive_1_0= '=?'
                    {
                    lv_interactive_1_0=(Token)match(input,92,FOLLOW_32); if (state.failed) return current;
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
                    // InternalKdl.g:4137:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,93,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4142:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4143:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4143:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4144:5: lv_value_3_0= ruleValue
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
    // InternalKdl.g:4165:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4165:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4166:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalKdl.g:4172:1: ruleFunction returns [EObject current=null] : ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) ) ;
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
            // InternalKdl.g:4178:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) ) )
            // InternalKdl.g:4179:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) )
            {
            // InternalKdl.g:4179:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? ) )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( ((LA86_0>=RULE_STRING && LA86_0<=RULE_LOWERCASE_DASHID)||LA86_0==RULE_CAMELCASE_ID||LA86_0==36||(LA86_0>=76 && LA86_0<=77)||LA86_0==84||LA86_0==100) ) {
                alt86=1;
            }
            else if ( (LA86_0==81) ) {
                alt86=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // InternalKdl.g:4180:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4180:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4181:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4181:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )?
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==RULE_LOWERCASE_ID) ) {
                        int LA80_1 = input.LA(2);

                        if ( (LA80_1==94) ) {
                            alt80=1;
                        }
                    }
                    switch (alt80) {
                        case 1 :
                            // InternalKdl.g:4182:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4182:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4183:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4183:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4184:7: lv_mediated_0_0= RULE_LOWERCASE_ID
                            {
                            lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_64); if (state.failed) return current;
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

                            otherlv_1=(Token)match(input,94,FOLLOW_65); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4205:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )
                    int alt82=3;
                    alt82 = dfa82.predict(input);
                    switch (alt82) {
                        case 1 :
                            // InternalKdl.g:4206:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            {
                            // InternalKdl.g:4206:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            // InternalKdl.g:4207:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
                            {
                            // InternalKdl.g:4207:6: ( (lv_name_2_0= rulePathName ) )
                            // InternalKdl.g:4208:7: (lv_name_2_0= rulePathName )
                            {
                            // InternalKdl.g:4208:7: (lv_name_2_0= rulePathName )
                            // InternalKdl.g:4209:8: lv_name_2_0= rulePathName
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_45);
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

                            otherlv_3=(Token)match(input,81,FOLLOW_47); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_1_0_1());
                              					
                            }
                            // InternalKdl.g:4230:6: ( (lv_parameters_4_0= ruleParameterList ) )?
                            int alt81=2;
                            int LA81_0 = input.LA(1);

                            if ( ((LA81_0>=RULE_STRING && LA81_0<=RULE_UPPERCASE_ID)||(LA81_0>=RULE_ID && LA81_0<=RULE_CAMELCASE_ID)||LA81_0==29||LA81_0==36||LA81_0==39||(LA81_0>=76 && LA81_0<=77)||LA81_0==81||LA81_0==84||LA81_0==87||LA81_0==100) ) {
                                alt81=1;
                            }
                            switch (alt81) {
                                case 1 :
                                    // InternalKdl.g:4231:7: (lv_parameters_4_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4231:7: (lv_parameters_4_0= ruleParameterList )
                                    // InternalKdl.g:4232:8: lv_parameters_4_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_66);
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

                            otherlv_5=(Token)match(input,82,FOLLOW_67); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4255:5: ( (lv_urn_6_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4255:5: ( (lv_urn_6_0= ruleUrn ) )
                            // InternalKdl.g:4256:6: (lv_urn_6_0= ruleUrn )
                            {
                            // InternalKdl.g:4256:6: (lv_urn_6_0= ruleUrn )
                            // InternalKdl.g:4257:7: lv_urn_6_0= ruleUrn
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
                            // InternalKdl.g:4275:5: ( (lv_value_7_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4275:5: ( (lv_value_7_0= ruleLiteral ) )
                            // InternalKdl.g:4276:6: (lv_value_7_0= ruleLiteral )
                            {
                            // InternalKdl.g:4276:6: (lv_value_7_0= ruleLiteral )
                            // InternalKdl.g:4277:7: lv_value_7_0= ruleLiteral
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

                    // InternalKdl.g:4295:4: (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==41) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // InternalKdl.g:4296:5: otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_8=(Token)match(input,41,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getAsKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4300:5: ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4301:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4301:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4302:7: lv_variable_9_0= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4321:3: (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4321:3: (otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4322:4: otherlv_10= '(' ( (lv_chain_11_0= ruleFunction ) ) (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )* otherlv_14= ')' (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    otherlv_10=(Token)match(input,81,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:4326:4: ( (lv_chain_11_0= ruleFunction ) )
                    // InternalKdl.g:4327:5: (lv_chain_11_0= ruleFunction )
                    {
                    // InternalKdl.g:4327:5: (lv_chain_11_0= ruleFunction )
                    // InternalKdl.g:4328:6: lv_chain_11_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
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

                    // InternalKdl.g:4345:4: (otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) ) )*
                    loop84:
                    do {
                        int alt84=2;
                        int LA84_0 = input.LA(1);

                        if ( (LA84_0==29) ) {
                            alt84=1;
                        }


                        switch (alt84) {
                    	case 1 :
                    	    // InternalKdl.g:4346:5: otherlv_12= ',' ( (lv_chain_13_0= ruleFunction ) )
                    	    {
                    	    otherlv_12=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_12, grammarAccess.getFunctionAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:4350:5: ( (lv_chain_13_0= ruleFunction ) )
                    	    // InternalKdl.g:4351:6: (lv_chain_13_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:4351:6: (lv_chain_13_0= ruleFunction )
                    	    // InternalKdl.g:4352:7: lv_chain_13_0= ruleFunction
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_46);
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
                    	    break loop84;
                        }
                    } while (true);

                    otherlv_14=(Token)match(input,82,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }
                    // InternalKdl.g:4374:4: (otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) ) )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==41) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // InternalKdl.g:4375:5: otherlv_15= 'as' ( (lv_variable_16_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_15=(Token)match(input,41,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_15, grammarAccess.getFunctionAccess().getAsKeyword_1_4_0());
                              				
                            }
                            // InternalKdl.g:4379:5: ( (lv_variable_16_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4380:6: (lv_variable_16_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4380:6: (lv_variable_16_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4381:7: lv_variable_16_0= RULE_LOWERCASE_ID
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
    // InternalKdl.g:4403:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:4403:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:4404:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKdl.g:4410:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) ;
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
            // InternalKdl.g:4416:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) )
            // InternalKdl.g:4417:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            {
            // InternalKdl.g:4417:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            int alt88=3;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
            case RULE_CAMELCASE_ID:
                {
                alt88=1;
                }
                break;
            case RULE_INT:
            case 36:
            case 100:
                {
                alt88=2;
                }
                break;
            case 81:
                {
                alt88=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // InternalKdl.g:4418:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    {
                    // InternalKdl.g:4418:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    // InternalKdl.g:4419:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKdl.g:4419:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    // InternalKdl.g:4420:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    {
                    // InternalKdl.g:4420:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==RULE_CAMELCASE_ID) ) {
                        alt87=1;
                    }
                    else if ( (LA87_0==RULE_LOWERCASE_ID) ) {
                        alt87=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 87, 0, input);

                        throw nvae;
                    }
                    switch (alt87) {
                        case 1 :
                            // InternalKdl.g:4421:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:4436:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4454:3: ( (lv_num_1_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4454:3: ( (lv_num_1_0= ruleNumber ) )
                    // InternalKdl.g:4455:4: (lv_num_1_0= ruleNumber )
                    {
                    // InternalKdl.g:4455:4: (lv_num_1_0= ruleNumber )
                    // InternalKdl.g:4456:5: lv_num_1_0= ruleNumber
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
                    // InternalKdl.g:4474:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    {
                    // InternalKdl.g:4474:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    // InternalKdl.g:4475:4: otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,81,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:4479:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKdl.g:4480:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKdl.g:4480:5: (lv_unit_3_0= ruleUnit )
                    // InternalKdl.g:4481:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_66);
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

                    otherlv_4=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4507:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:4507:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:4508:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKdl.g:4514:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKdl.g:4520:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:4521:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:4521:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt89=6;
            switch ( input.LA(1) ) {
            case 95:
                {
                alt89=1;
                }
                break;
            case 96:
                {
                alt89=2;
                }
                break;
            case 93:
                {
                alt89=3;
                }
                break;
            case 97:
                {
                alt89=4;
                }
                break;
            case 98:
                {
                alt89=5;
                }
                break;
            case 99:
                {
                alt89=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // InternalKdl.g:4522:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:4522:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:4523:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:4523:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:4524:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4537:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:4537:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:4538:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:4538:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:4539:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,96,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4552:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:4552:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:4553:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:4553:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:4554:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,93,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4567:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:4567:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:4568:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:4568:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:4569:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,97,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4582:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:4582:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:4583:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:4583:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:4584:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4597:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:4597:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:4598:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:4598:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:4599:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4615:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:4615:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:4616:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKdl.g:4622:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4628:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:4629:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:4629:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:4630:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:4630:3: ()
            // InternalKdl.g:4631:4: 
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

            // InternalKdl.g:4640:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( ((LA90_0>=RULE_LOWERCASE_ID && LA90_0<=RULE_INT)||LA90_0==RULE_CAMELCASE_ID||LA90_0==36||LA90_0==81||LA90_0==100) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalKdl.g:4641:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:4641:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:4642:5: lv_root_1_0= ruleUnitElement
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

            // InternalKdl.g:4659:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==60||LA91_0==90||LA91_0==103) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalKdl.g:4660:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:4660:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:4661:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:4667:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:4668:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:4668:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:4669:7: lv_connectors_2_0= ruleUnitOp
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

            	    // InternalKdl.g:4687:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:4688:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:4688:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:4689:6: lv_units_3_0= ruleUnitElement
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
            	    break loop91;
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
    // InternalKdl.g:4711:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:4711:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:4712:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKdl.g:4718:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKdl.g:4724:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:4725:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:4725:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:4726:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:4726:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt92=3;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==36) ) {
                alt92=1;
            }
            else if ( (LA92_0==100) ) {
                alt92=2;
            }
            switch (alt92) {
                case 1 :
                    // InternalKdl.g:4727:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,36,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4732:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:4732:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:4733:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:4733:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:4734:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,100,FOLLOW_7); if (state.failed) return current;
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

            // InternalKdl.g:4747:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:4748:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:4752:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:4753:5: lv_real_2_0= RULE_INT
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

            // InternalKdl.g:4769:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==91) && (synpred186_InternalKdl())) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalKdl.g:4770:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:4783:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:4784:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:4784:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:4785:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:4785:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:4786:7: lv_decimal_3_0= '.'
                    {
                    lv_decimal_3_0=(Token)match(input,91,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:4798:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:4799:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:4799:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:4800:7: lv_decimalPart_4_0= RULE_INT
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

            // InternalKdl.g:4818:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==101) && (synpred190_InternalKdl())) {
                alt96=1;
            }
            else if ( (LA96_0==102) && (synpred190_InternalKdl())) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalKdl.g:4819:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:4845:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:4846:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:4846:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:4847:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:4847:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:4848:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:4848:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==101) ) {
                        alt94=1;
                    }
                    else if ( (LA94_0==102) ) {
                        alt94=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 94, 0, input);

                        throw nvae;
                    }
                    switch (alt94) {
                        case 1 :
                            // InternalKdl.g:4849:8: lv_exponential_5_1= 'e'
                            {
                            lv_exponential_5_1=(Token)match(input,101,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKdl.g:4860:8: lv_exponential_5_2= 'E'
                            {
                            lv_exponential_5_2=(Token)match(input,102,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKdl.g:4873:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt95=3;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==36) ) {
                        alt95=1;
                    }
                    else if ( (LA95_0==100) ) {
                        alt95=2;
                    }
                    switch (alt95) {
                        case 1 :
                            // InternalKdl.g:4874:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,36,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4879:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:4879:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:4880:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:4880:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:4881:8: lv_expNegative_7_0= '-'
                            {
                            lv_expNegative_7_0=(Token)match(input,100,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:4894:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:4895:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:4895:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:4896:7: lv_exp_8_0= RULE_INT
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
    // InternalKdl.g:4918:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:4918:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:4919:2: iv_rulePathName= rulePathName EOF
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
    // InternalKdl.g:4925:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:4931:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:4932:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:4932:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:4933:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:4940:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==91) ) {
                    int LA97_2 = input.LA(2);

                    if ( (LA97_2==RULE_LOWERCASE_ID) ) {
                        alt97=1;
                    }


                }


                switch (alt97) {
            	case 1 :
            	    // InternalKdl.g:4941:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,91,FOLLOW_5); if (state.failed) return current;
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
            	    break loop97;
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
    // InternalKdl.g:4958:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:4958:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:4959:2: iv_rulePath= rulePath EOF
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
    // InternalKdl.g:4965:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:4971:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:4972:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:4972:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:4973:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:4980:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( ((LA99_0>=90 && LA99_0<=91)) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // InternalKdl.g:4981:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:4981:4: (kw= '.' | kw= '/' )
            	    int alt98=2;
            	    int LA98_0 = input.LA(1);

            	    if ( (LA98_0==91) ) {
            	        alt98=1;
            	    }
            	    else if ( (LA98_0==90) ) {
            	        alt98=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 98, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt98) {
            	        case 1 :
            	            // InternalKdl.g:4982:5: kw= '.'
            	            {
            	            kw=(Token)match(input,91,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4988:5: kw= '/'
            	            {
            	            kw=(Token)match(input,90,FOLLOW_5); if (state.failed) return current;
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
            	    break loop99;
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
    // InternalKdl.g:5006:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5006:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5007:2: iv_ruleJavaClass= ruleJavaClass EOF
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
    // InternalKdl.g:5013:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5019:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5020:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5020:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5021:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
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
            kw=(Token)match(input,91,FOLLOW_76); if (state.failed) return current;
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
    // InternalKdl.g:5047:1: entryRuleNamespaceId returns [String current=null] : iv_ruleNamespaceId= ruleNamespaceId EOF ;
    public final String entryRuleNamespaceId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNamespaceId = null;


        try {
            // InternalKdl.g:5047:51: (iv_ruleNamespaceId= ruleNamespaceId EOF )
            // InternalKdl.g:5048:2: iv_ruleNamespaceId= ruleNamespaceId EOF
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
    // InternalKdl.g:5054:1: ruleNamespaceId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleNamespaceId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5060:2: ( (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5061:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5061:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5062:3: this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getNamespaceIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,85,FOLLOW_76); if (state.failed) return current;
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
    // InternalKdl.g:5088:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5088:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5089:2: iv_rulePropertyId= rulePropertyId EOF
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
    // InternalKdl.g:5095:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5101:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5102:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5102:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5103:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPropertyIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_51);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,85,FOLLOW_50); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getPropertyIdAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:5118:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==RULE_LOWERCASE_ID) ) {
                alt100=1;
            }
            else if ( (LA100_0==RULE_LOWERCASE_DASHID) ) {
                alt100=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // InternalKdl.g:5119:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5127:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
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
    // InternalKdl.g:5139:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5139:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5140:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKdl.g:5146:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKdl.g:5152:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:5153:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:5153:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:5154:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_77); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5161:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==91) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // InternalKdl.g:5162:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,91,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_77); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKdl.g:5174:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==91) ) {
                        alt101=1;
                    }
                    switch (alt101) {
                        case 1 :
                            // InternalKdl.g:5175:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,91,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_78); if (state.failed) return current;
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

            // InternalKdl.g:5189:3: (kw= '-' )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==100) ) {
                int LA103_1 = input.LA(2);

                if ( (synpred200_InternalKdl()) ) {
                    alt103=1;
                }
            }
            switch (alt103) {
                case 1 :
                    // InternalKdl.g:5190:4: kw= '-'
                    {
                    kw=(Token)match(input,100,FOLLOW_79); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:5196:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt104=3;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==RULE_LOWERCASE_ID) ) {
                int LA104_1 = input.LA(2);

                if ( (synpred201_InternalKdl()) ) {
                    alt104=1;
                }
            }
            else if ( (LA104_0==RULE_UPPERCASE_ID) ) {
                int LA104_2 = input.LA(2);

                if ( (synpred202_InternalKdl()) ) {
                    alt104=2;
                }
            }
            switch (alt104) {
                case 1 :
                    // InternalKdl.g:5197:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5205:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKdl.g:5217:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5223:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:5224:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:5224:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt105=3;
            switch ( input.LA(1) ) {
            case 90:
                {
                alt105=1;
                }
                break;
            case 103:
                {
                alt105=2;
                }
                break;
            case 60:
                {
                alt105=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }

            switch (alt105) {
                case 1 :
                    // InternalKdl.g:5225:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:5225:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:5226:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:5233:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:5233:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:5234:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,103,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:5241:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:5241:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:5242:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
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
        otherlv_1=(Token)match(input,18,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:113:9: ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        {
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        {
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        int alt106=2;
        alt106 = dfa106.predict(input);
        switch (alt106) {
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
        otherlv_3=(Token)match(input,19,FOLLOW_5); if (state.failed) return ;
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
        int cnt107=0;
        loop107:
        do {
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==19) && ((true))) {
                alt107=1;
            }


            switch (alt107) {
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
        	    otherlv_3=(Token)match(input,19,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:165:9: ( (lv_variables_4_0= ruleParameter ) )
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    {
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    // InternalKdl.g:167:11: lv_variables_4_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getVariablesParameterParserRuleCall_0_1_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_80);
        	    lv_variables_4_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt107 >= 1 ) break loop107;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(107, input);
                    throw eee;
            }
            cnt107++;
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
        otherlv_5=(Token)match(input,20,FOLLOW_5); if (state.failed) return ;
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
        int cnt108=0;
        loop108:
        do {
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==20) && ((true))) {
                alt108=1;
            }


            switch (alt108) {
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
        	    otherlv_5=(Token)match(input,20,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:199:9: ( (lv_constants_6_0= ruleParameter ) )
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    {
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    // InternalKdl.g:201:11: lv_constants_6_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getConstantsParameterParserRuleCall_0_2_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_81);
        	    lv_constants_6_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt108 >= 1 ) break loop108;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(108, input);
                    throw eee;
            }
            cnt108++;
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
        otherlv_7=(Token)match(input,21,FOLLOW_6); if (state.failed) return ;
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
        int cnt109=0;
        loop109:
        do {
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==21) && ((true))) {
                alt109=1;
            }


            switch (alt109) {
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
        	    otherlv_7=(Token)match(input,21,FOLLOW_6); if (state.failed) return ;
        	    // InternalKdl.g:233:9: ( (lv_authors_8_0= RULE_STRING ) )
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    {
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    // InternalKdl.g:235:11: lv_authors_8_0= RULE_STRING
        	    {
        	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_82); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt109 >= 1 ) break loop109;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(109, input);
                    throw eee;
            }
            cnt109++;
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
        otherlv_9=(Token)match(input,22,FOLLOW_7); if (state.failed) return ;
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
        otherlv_11=(Token)match(input,23,FOLLOW_7); if (state.failed) return ;
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
        otherlv_13=(Token)match(input,24,FOLLOW_5); if (state.failed) return ;
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
        otherlv_15=(Token)match(input,25,FOLLOW_8); if (state.failed) return ;
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
        otherlv_17=(Token)match(input,26,FOLLOW_6); if (state.failed) return ;
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
        otherlv_19=(Token)match(input,27,FOLLOW_3); if (state.failed) return ;
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
        otherlv_21=(Token)match(input,28,FOLLOW_9); if (state.failed) return ;
        // InternalKdl.g:468:9: ( (lv_scale_22_0= ruleFunction ) )
        // InternalKdl.g:469:10: (lv_scale_22_0= ruleFunction )
        {
        // InternalKdl.g:469:10: (lv_scale_22_0= ruleFunction )
        // InternalKdl.g:470:11: lv_scale_22_0= ruleFunction
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_1_0());
          										
        }
        pushFollow(FOLLOW_26);
        lv_scale_22_0=ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:487:9: (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
        loop110:
        do {
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==29) ) {
                alt110=1;
            }


            switch (alt110) {
        	case 1 :
        	    // InternalKdl.g:488:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
        	    {
        	    otherlv_23=(Token)match(input,29,FOLLOW_9); if (state.failed) return ;
        	    // InternalKdl.g:492:10: ( (lv_scale_24_0= ruleFunction ) )
        	    // InternalKdl.g:493:11: (lv_scale_24_0= ruleFunction )
        	    {
        	    // InternalKdl.g:493:11: (lv_scale_24_0= ruleFunction )
        	    // InternalKdl.g:494:12: lv_scale_24_0= ruleFunction
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_26);
        	    lv_scale_24_0=ruleFunction();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop110;
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
        otherlv_25=(Token)match(input,30,FOLLOW_11); if (state.failed) return ;
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

    // $ANTLR start synpred52_InternalKdl
    public final void synpred52_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_geometry_4_0 = null;


        // InternalKdl.g:1401:4: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1401:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1401:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1402:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred52_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKdl.g:1402:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1403:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
        // InternalKdl.g:1406:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1406:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred52_InternalKdl", "true");
        }
        // InternalKdl.g:1406:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        // InternalKdl.g:1406:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
        {
        otherlv_3=(Token)match(input,55,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1410:9: ( (lv_geometry_4_0= ruleGeometry ) )
        // InternalKdl.g:1411:10: (lv_geometry_4_0= ruleGeometry )
        {
        // InternalKdl.g:1411:10: (lv_geometry_4_0= ruleGeometry )
        // InternalKdl.g:1412:11: lv_geometry_4_0= ruleGeometry
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
    // $ANTLR end synpred52_InternalKdl

    // $ANTLR start synpred53_InternalKdl
    public final void synpred53_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        EObject lv_units_6_0 = null;


        // InternalKdl.g:1435:4: ( ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1435:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1435:4: ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1436:5: {...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred53_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKdl.g:1436:109: ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1437:6: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
        // InternalKdl.g:1440:9: ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) )
        // InternalKdl.g:1440:10: {...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred53_InternalKdl", "true");
        }
        // InternalKdl.g:1440:19: (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) )
        // InternalKdl.g:1440:20: otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) )
        {
        otherlv_5=(Token)match(input,56,FOLLOW_83); if (state.failed) return ;
        // InternalKdl.g:1444:9: ( (lv_units_6_0= ruleUnit ) )
        // InternalKdl.g:1445:10: (lv_units_6_0= ruleUnit )
        {
        // InternalKdl.g:1445:10: (lv_units_6_0= ruleUnit )
        // InternalKdl.g:1446:11: lv_units_6_0= ruleUnit
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
    // $ANTLR end synpred53_InternalKdl

    // $ANTLR start synpred54_InternalKdl
    public final void synpred54_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_7_0 = null;


        // InternalKdl.g:1469:4: ( ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) )
        // InternalKdl.g:1469:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
        {
        // InternalKdl.g:1469:4: ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) )
        // InternalKdl.g:1470:5: {...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred54_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKdl.g:1470:109: ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) )
        // InternalKdl.g:1471:6: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
        // InternalKdl.g:1474:9: ({...}? => ( (lv_computations_7_0= ruleComputation ) ) )
        // InternalKdl.g:1474:10: {...}? => ( (lv_computations_7_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred54_InternalKdl", "true");
        }
        // InternalKdl.g:1474:19: ( (lv_computations_7_0= ruleComputation ) )
        // InternalKdl.g:1474:20: (lv_computations_7_0= ruleComputation )
        {
        // InternalKdl.g:1474:20: (lv_computations_7_0= ruleComputation )
        // InternalKdl.g:1475:10: lv_computations_7_0= ruleComputation
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
    // $ANTLR end synpred54_InternalKdl

    // $ANTLR start synpred55_InternalKdl
    public final void synpred55_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        EObject lv_metadata_9_0 = null;


        // InternalKdl.g:1503:10: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )
        // InternalKdl.g:1503:10: otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) )
        {
        otherlv_8=(Token)match(input,57,FOLLOW_40); if (state.failed) return ;
        // InternalKdl.g:1507:10: ( (lv_metadata_9_0= ruleMetadata ) )
        // InternalKdl.g:1508:11: (lv_metadata_9_0= ruleMetadata )
        {
        // InternalKdl.g:1508:11: (lv_metadata_9_0= ruleMetadata )
        // InternalKdl.g:1509:12: lv_metadata_9_0= ruleMetadata
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
    // $ANTLR end synpred55_InternalKdl

    // $ANTLR start synpred56_InternalKdl
    public final void synpred56_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_javaClass_11_0 = null;


        // InternalKdl.g:1528:10: (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )
        // InternalKdl.g:1528:10: otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) )
        {
        otherlv_10=(Token)match(input,58,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:1532:10: ( (lv_javaClass_11_0= ruleJavaClass ) )
        // InternalKdl.g:1533:11: (lv_javaClass_11_0= ruleJavaClass )
        {
        // InternalKdl.g:1533:11: (lv_javaClass_11_0= ruleJavaClass )
        // InternalKdl.g:1534:12: lv_javaClass_11_0= ruleJavaClass
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
    // $ANTLR end synpred56_InternalKdl

    // $ANTLR start synpred57_InternalKdl
    public final void synpred57_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_metadata_9_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_11_0 = null;


        // InternalKdl.g:1497:4: ( ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )
        // InternalKdl.g:1497:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
        {
        // InternalKdl.g:1497:4: ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) )
        // InternalKdl.g:1498:5: {...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred57_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKdl.g:1498:109: ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) )
        // InternalKdl.g:1499:6: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3);
        // InternalKdl.g:1502:9: ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) )
        // InternalKdl.g:1502:10: {...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred57_InternalKdl", "true");
        }
        // InternalKdl.g:1502:19: ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? )
        // InternalKdl.g:1502:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
        {
        // InternalKdl.g:1502:20: (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )?
        int alt132=2;
        int LA132_0 = input.LA(1);

        if ( (LA132_0==57) ) {
            alt132=1;
        }
        switch (alt132) {
            case 1 :
                // InternalKdl.g:1503:10: otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) )
                {
                otherlv_8=(Token)match(input,57,FOLLOW_40); if (state.failed) return ;
                // InternalKdl.g:1507:10: ( (lv_metadata_9_0= ruleMetadata ) )
                // InternalKdl.g:1508:11: (lv_metadata_9_0= ruleMetadata )
                {
                // InternalKdl.g:1508:11: (lv_metadata_9_0= ruleMetadata )
                // InternalKdl.g:1509:12: lv_metadata_9_0= ruleMetadata
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_3_0_1_0());
                  											
                }
                pushFollow(FOLLOW_84);
                lv_metadata_9_0=ruleMetadata();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:1527:9: (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )?
        int alt133=2;
        int LA133_0 = input.LA(1);

        if ( (LA133_0==58) ) {
            alt133=1;
        }
        switch (alt133) {
            case 1 :
                // InternalKdl.g:1528:10: otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) )
                {
                otherlv_10=(Token)match(input,58,FOLLOW_3); if (state.failed) return ;
                // InternalKdl.g:1532:10: ( (lv_javaClass_11_0= ruleJavaClass ) )
                // InternalKdl.g:1533:11: (lv_javaClass_11_0= ruleJavaClass )
                {
                // InternalKdl.g:1533:11: (lv_javaClass_11_0= ruleJavaClass )
                // InternalKdl.g:1534:12: lv_javaClass_11_0= ruleJavaClass
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
    // $ANTLR end synpred57_InternalKdl

    // $ANTLR start synpred82_InternalKdl
    public final void synpred82_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:1995:5: ( 'to' )
        // InternalKdl.g:1995:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred82_InternalKdl

    // $ANTLR start synpred121_InternalKdl
    public final void synpred121_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:2981:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:2981:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:2981:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:2982:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:2982:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:2983:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
    // $ANTLR end synpred121_InternalKdl

    // $ANTLR start synpred122_InternalKdl
    public final void synpred122_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:3001:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:3001:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:3001:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:3002:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:3002:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:3003:5: lv_function_1_0= ruleFunction
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
    // $ANTLR end synpred122_InternalKdl

    // $ANTLR start synpred123_InternalKdl
    public final void synpred123_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;


        // InternalKdl.g:3021:3: ( ( (lv_urn_2_0= ruleUrn ) ) )
        // InternalKdl.g:3021:3: ( (lv_urn_2_0= ruleUrn ) )
        {
        // InternalKdl.g:3021:3: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:3022:4: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:3022:4: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:3023:5: lv_urn_2_0= ruleUrn
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
    // $ANTLR end synpred123_InternalKdl

    // $ANTLR start synpred124_InternalKdl
    public final void synpred124_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_3_0 = null;


        // InternalKdl.g:3041:3: ( ( (lv_list_3_0= ruleList ) ) )
        // InternalKdl.g:3041:3: ( (lv_list_3_0= ruleList ) )
        {
        // InternalKdl.g:3041:3: ( (lv_list_3_0= ruleList ) )
        // InternalKdl.g:3042:4: (lv_list_3_0= ruleList )
        {
        // InternalKdl.g:3042:4: (lv_list_3_0= ruleList )
        // InternalKdl.g:3043:5: lv_list_3_0= ruleList
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
    // $ANTLR end synpred124_InternalKdl

    // $ANTLR start synpred143_InternalKdl
    public final void synpred143_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:3742:5: ( 'to' )
        // InternalKdl.g:3742:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred143_InternalKdl

    // $ANTLR start synpred166_InternalKdl
    public final void synpred166_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_parameters_4_0 = null;


        // InternalKdl.g:4206:5: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) )
        // InternalKdl.g:4206:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        {
        // InternalKdl.g:4206:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        // InternalKdl.g:4207:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
        {
        // InternalKdl.g:4207:6: ( (lv_name_2_0= rulePathName ) )
        // InternalKdl.g:4208:7: (lv_name_2_0= rulePathName )
        {
        // InternalKdl.g:4208:7: (lv_name_2_0= rulePathName )
        // InternalKdl.g:4209:8: lv_name_2_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
          							
        }
        pushFollow(FOLLOW_45);
        lv_name_2_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_3=(Token)match(input,81,FOLLOW_47); if (state.failed) return ;
        // InternalKdl.g:4230:6: ( (lv_parameters_4_0= ruleParameterList ) )?
        int alt148=2;
        int LA148_0 = input.LA(1);

        if ( ((LA148_0>=RULE_STRING && LA148_0<=RULE_UPPERCASE_ID)||(LA148_0>=RULE_ID && LA148_0<=RULE_CAMELCASE_ID)||LA148_0==29||LA148_0==36||LA148_0==39||(LA148_0>=76 && LA148_0<=77)||LA148_0==81||LA148_0==84||LA148_0==87||LA148_0==100) ) {
            alt148=1;
        }
        switch (alt148) {
            case 1 :
                // InternalKdl.g:4231:7: (lv_parameters_4_0= ruleParameterList )
                {
                // InternalKdl.g:4231:7: (lv_parameters_4_0= ruleParameterList )
                // InternalKdl.g:4232:8: lv_parameters_4_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                  							
                }
                pushFollow(FOLLOW_66);
                lv_parameters_4_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_5=(Token)match(input,82,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred166_InternalKdl

    // $ANTLR start synpred167_InternalKdl
    public final void synpred167_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_6_0 = null;


        // InternalKdl.g:4255:5: ( ( (lv_urn_6_0= ruleUrn ) ) )
        // InternalKdl.g:4255:5: ( (lv_urn_6_0= ruleUrn ) )
        {
        // InternalKdl.g:4255:5: ( (lv_urn_6_0= ruleUrn ) )
        // InternalKdl.g:4256:6: (lv_urn_6_0= ruleUrn )
        {
        // InternalKdl.g:4256:6: (lv_urn_6_0= ruleUrn )
        // InternalKdl.g:4257:7: lv_urn_6_0= ruleUrn
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
    // $ANTLR end synpred167_InternalKdl

    // $ANTLR start synpred185_InternalKdl
    public final void synpred185_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4748:4: ( ( RULE_INT ) )
        // InternalKdl.g:4748:5: ( RULE_INT )
        {
        // InternalKdl.g:4748:5: ( RULE_INT )
        // InternalKdl.g:4749:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred185_InternalKdl

    // $ANTLR start synpred186_InternalKdl
    public final void synpred186_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4770:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:4770:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:4770:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:4771:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:4771:5: ( ( '.' ) )
        // InternalKdl.g:4772:6: ( '.' )
        {
        // InternalKdl.g:4772:6: ( '.' )
        // InternalKdl.g:4773:7: '.'
        {
        match(input,91,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:4776:5: ( ( RULE_INT ) )
        // InternalKdl.g:4777:6: ( RULE_INT )
        {
        // InternalKdl.g:4777:6: ( RULE_INT )
        // InternalKdl.g:4778:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred186_InternalKdl

    // $ANTLR start synpred190_InternalKdl
    public final void synpred190_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4819:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:4819:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:4819:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:4820:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:4820:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:4821:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:4821:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:4822:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=101 && input.LA(1)<=102) ) {
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

        // InternalKdl.g:4829:5: ( '+' | ( ( '-' ) ) )?
        int alt154=3;
        int LA154_0 = input.LA(1);

        if ( (LA154_0==36) ) {
            alt154=1;
        }
        else if ( (LA154_0==100) ) {
            alt154=2;
        }
        switch (alt154) {
            case 1 :
                // InternalKdl.g:4830:6: '+'
                {
                match(input,36,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:4832:6: ( ( '-' ) )
                {
                // InternalKdl.g:4832:6: ( ( '-' ) )
                // InternalKdl.g:4833:7: ( '-' )
                {
                // InternalKdl.g:4833:7: ( '-' )
                // InternalKdl.g:4834:8: '-'
                {
                match(input,100,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:4838:5: ( ( RULE_INT ) )
        // InternalKdl.g:4839:6: ( RULE_INT )
        {
        // InternalKdl.g:4839:6: ( RULE_INT )
        // InternalKdl.g:4840:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred190_InternalKdl

    // $ANTLR start synpred200_InternalKdl
    public final void synpred200_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:5190:4: (kw= '-' )
        // InternalKdl.g:5190:4: kw= '-'
        {
        kw=(Token)match(input,100,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred200_InternalKdl

    // $ANTLR start synpred201_InternalKdl
    public final void synpred201_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:5197:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:5197:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred201_InternalKdl

    // $ANTLR start synpred202_InternalKdl
    public final void synpred202_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:5205:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:5205:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred202_InternalKdl

    // Delegated rules

    public final boolean synpred202_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred202_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred166_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred166_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred186_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred186_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred122_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred122_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred121_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred121_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred143_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred143_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred185_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred185_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred54_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred54_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred123_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred123_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred53_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred53_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred167_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred167_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred52_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred52_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred82_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred82_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred200_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred200_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred190_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred190_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred201_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred201_InternalKdl_fragment(); // can never throw exception
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
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA48 dfa48 = new DFA48(this);
    protected DFA50 dfa50 = new DFA50(this);
    protected DFA59 dfa59 = new DFA59(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA82 dfa82 = new DFA82(this);
    protected DFA106 dfa106 = new DFA106(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\22\15\uffff";
    static final String dfa_4s = "\1\107\15\uffff";
    static final String dfa_5s = "\1\uffff\1\15\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14";
    static final String dfa_6s = "\1\0\15\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\4\1\2\uffff\1\1\5\uffff\5\1\15\uffff\13\1",
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
                        if ( (LA6_0==EOF||(LA6_0>=31 && LA6_0<=34)||LA6_0==37||(LA6_0>=43 && LA6_0<=47)||(LA6_0>=61 && LA6_0<=71)) ) {s = 1;}

                        else if ( LA6_0 == 18 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0) ) {s = 2;}

                        else if ( LA6_0 == 19 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1) ) {s = 3;}

                        else if ( LA6_0 == 20 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2) ) {s = 4;}

                        else if ( LA6_0 == 21 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3) ) {s = 5;}

                        else if ( LA6_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4) ) {s = 6;}

                        else if ( LA6_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5) ) {s = 7;}

                        else if ( LA6_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6) ) {s = 8;}

                        else if ( LA6_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7) ) {s = 9;}

                        else if ( LA6_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8) ) {s = 10;}

                        else if ( LA6_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {s = 11;}

                        else if ( LA6_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {s = 12;}

                        else if ( LA6_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {s = 13;}

                         
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
    static final String dfa_10s = "\1\5\1\22\1\uffff\1\5\1\uffff\1\22";
    static final String dfa_11s = "\1\124\1\133\1\uffff\1\5\1\uffff\1\133";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\1\uffff";
    static final String dfa_13s = "\6\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\116\uffff\1\2",
            "\13\4\1\uffff\5\4\2\uffff\1\4\5\uffff\5\4\15\uffff\13\4\15\uffff\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\13\4\1\uffff\5\4\2\uffff\1\4\5\uffff\5\4\15\uffff\13\4\15\uffff\1\2\4\uffff\1\4\1\3"
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
    static final String dfa_17s = "\1\50\5\0\6\uffff";
    static final String dfa_18s = "\1\73\5\0\6\uffff";
    static final String dfa_19s = "\6\uffff\2\4\1\5\1\1\1\2\1\3";
    static final String dfa_20s = "\1\5\1\3\1\1\1\2\1\0\1\4\6\uffff}>";
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

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "()+ loopback of 1400:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'units' ( (lv_units_6_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_7_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_8= 'metadata' ( (lv_metadata_9_0= ruleMetadata ) ) )? (otherlv_10= 'class' ( (lv_javaClass_11_0= ruleJavaClass ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_4 = input.LA(1);

                         
                        int index34_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred53_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {s = 10;}

                        else if ( synpred57_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index34_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA34_2 = input.LA(1);

                         
                        int index34_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred57_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 8;}

                         
                        input.seek(index34_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA34_3 = input.LA(1);

                         
                        int index34_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred52_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {s = 9;}

                        else if ( synpred57_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index34_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA34_1 = input.LA(1);

                         
                        int index34_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred57_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 8;}

                         
                        input.seek(index34_1);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA34_5 = input.LA(1);

                         
                        int index34_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 11;}

                        else if ( synpred57_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index34_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA34_0 = input.LA(1);

                         
                        int index34_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA34_0==40) ) {s = 1;}

                        else if ( (LA34_0==EOF) ) {s = 2;}

                        else if ( (LA34_0==55) ) {s = 3;}

                        else if ( (LA34_0==56) ) {s = 4;}

                        else if ( (LA34_0==59) ) {s = 5;}

                        else if ( LA34_0 == 57 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 6;}

                        else if ( LA34_0 == 58 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 3) ) {s = 7;}

                         
                        input.seek(index34_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 34, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_22s = "\25\uffff";
    static final String dfa_23s = "\4\uffff\1\20\14\uffff\1\20\2\uffff\1\20";
    static final String dfa_24s = "\1\4\1\uffff\2\6\1\66\7\uffff\3\6\2\uffff\1\66\2\6\1\66";
    static final String dfa_25s = "\1\144\1\uffff\2\6\1\146\7\uffff\1\6\2\144\2\uffff\1\146\2\6\1\125";
    static final String dfa_26s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\2\1\3\4\uffff";
    static final String dfa_27s = "\25\uffff}>";
    static final String[] dfa_28s = {
            "\1\6\1\uffff\1\4\35\uffff\1\2\2\uffff\1\7\24\uffff\1\13\17\uffff\2\1\2\uffff\1\5\1\10\1\uffff\1\12\11\uffff\1\11\1\uffff\5\11\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\17\27\uffff\2\17\5\uffff\1\20\5\uffff\1\14\11\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\35\uffff\1\22\77\uffff\1\23",
            "\1\24\35\uffff\1\22\77\uffff\1\23",
            "",
            "",
            "\1\17\27\uffff\2\17\5\uffff\1\20\17\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\17\27\uffff\2\17\5\uffff\1\20"
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "1920:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
        }
    }
    static final String dfa_29s = "\17\uffff";
    static final String dfa_30s = "\3\uffff\1\12\7\uffff\1\12\2\uffff\1\12";
    static final String dfa_31s = "\1\4\2\6\1\4\2\uffff\3\6\2\uffff\1\4\2\6\1\4";
    static final String dfa_32s = "\1\144\2\6\1\146\2\uffff\1\6\2\144\2\uffff\1\146\2\6\1\144";
    static final String dfa_33s = "\4\uffff\1\3\1\4\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_34s = "\17\uffff}>";
    static final String[] dfa_35s = {
            "\1\4\1\uffff\1\3\35\uffff\1\1\47\uffff\2\5\26\uffff\1\2",
            "\1\3",
            "\1\3",
            "\5\12\1\uffff\3\12\5\uffff\21\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\5\12\1\uffff\13\12\4\uffff\2\12\3\uffff\2\12\1\uffff\1\12\2\uffff\1\12\3\uffff\1\6\10\uffff\1\12\1\7\1\10",
            "",
            "",
            "\1\13",
            "\1\16\35\uffff\1\14\77\uffff\1\15",
            "\1\16\35\uffff\1\14\77\uffff\1\15",
            "",
            "",
            "\5\12\1\uffff\3\12\5\uffff\21\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\5\12\1\uffff\13\12\4\uffff\2\12\3\uffff\2\12\1\uffff\1\12\2\uffff\1\12\14\uffff\1\12\1\7\1\10",
            "\1\16",
            "\1\16",
            "\5\12\1\uffff\3\12\5\uffff\21\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\5\12\1\uffff\13\12\4\uffff\2\12\3\uffff\2\12\1\uffff\1\12\2\uffff\1\12\14\uffff\1\12"
    };

    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA48 extends DFA {

        public DFA48(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 48;
            this.eot = dfa_29;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "2332:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_36s = "\21\uffff";
    static final String dfa_37s = "\3\uffff\1\13\11\uffff\1\13\2\uffff\1\13";
    static final String dfa_38s = "\1\4\2\6\1\4\4\uffff\3\6\2\uffff\1\4\2\6\1\4";
    static final String dfa_39s = "\1\144\2\6\1\146\4\uffff\1\6\2\144\2\uffff\1\146\2\6\1\144";
    static final String dfa_40s = "\4\uffff\1\3\1\4\1\5\1\6\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_41s = "\21\uffff}>";
    static final String[] dfa_42s = {
            "\1\4\1\uffff\1\3\3\uffff\1\6\22\uffff\1\7\6\uffff\1\1\47\uffff\2\5\26\uffff\1\2",
            "\1\3",
            "\1\3",
            "\5\13\1\uffff\3\13\5\uffff\21\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\5\13\1\uffff\13\13\4\uffff\2\13\3\uffff\2\13\1\uffff\1\13\2\uffff\1\13\3\uffff\1\10\10\uffff\1\13\1\11\1\12",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\20\35\uffff\1\16\77\uffff\1\17",
            "\1\20\35\uffff\1\16\77\uffff\1\17",
            "",
            "",
            "\5\13\1\uffff\3\13\5\uffff\21\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\5\13\1\uffff\13\13\4\uffff\2\13\3\uffff\2\13\1\uffff\1\13\2\uffff\1\13\14\uffff\1\13\1\11\1\12",
            "\1\20",
            "\1\20",
            "\5\13\1\uffff\3\13\5\uffff\21\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\5\13\1\uffff\13\13\4\uffff\2\13\3\uffff\2\13\1\uffff\1\13\2\uffff\1\13\14\uffff\1\13"
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA50 extends DFA {

        public DFA50(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 50;
            this.eot = dfa_36;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "2462:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )";
        }
    }
    static final String dfa_43s = "\1\4\6\0\2\uffff\5\0\7\uffff";
    static final String dfa_44s = "\1\144\6\0\2\uffff\5\0\7\uffff";
    static final String dfa_45s = "\7\uffff\1\1\6\uffff\1\5\1\6\1\7\1\10\1\2\1\3\1\4";
    static final String dfa_46s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\2\uffff\1\6\1\7\1\10\1\11\1\12\7\uffff}>";
    static final String[] dfa_47s = {
            "\1\4\1\11\1\3\1\14\1\21\1\uffff\1\7\1\17\1\13\20\uffff\1\7\6\uffff\1\1\2\uffff\1\16\44\uffff\1\5\1\6\3\uffff\1\15\2\uffff\1\12\2\uffff\1\20\14\uffff\1\2",
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

    class DFA59 extends DFA {

        public DFA59(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 59;
            this.eot = dfa_22;
            this.eof = dfa_22;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "2980:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA59_1 = input.LA(1);

                         
                        int index59_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 7;}

                        else if ( (synpred122_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index59_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA59_2 = input.LA(1);

                         
                        int index59_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 7;}

                        else if ( (synpred122_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index59_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA59_3 = input.LA(1);

                         
                        int index59_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 7;}

                        else if ( (synpred122_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index59_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA59_4 = input.LA(1);

                         
                        int index59_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 7;}

                        else if ( (synpred122_InternalKdl()) ) {s = 18;}

                        else if ( (synpred123_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index59_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA59_5 = input.LA(1);

                         
                        int index59_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 7;}

                        else if ( (synpred122_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index59_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA59_6 = input.LA(1);

                         
                        int index59_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 7;}

                        else if ( (synpred122_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index59_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA59_9 = input.LA(1);

                         
                        int index59_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_InternalKdl()) ) {s = 18;}

                        else if ( (synpred123_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index59_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA59_10 = input.LA(1);

                         
                        int index59_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_InternalKdl()) ) {s = 18;}

                        else if ( (synpred123_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index59_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA59_11 = input.LA(1);

                         
                        int index59_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_InternalKdl()) ) {s = 18;}

                        else if ( (synpred123_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index59_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA59_12 = input.LA(1);

                         
                        int index59_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_InternalKdl()) ) {s = 18;}

                        else if ( (synpred123_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index59_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA59_13 = input.LA(1);

                         
                        int index59_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_InternalKdl()) ) {s = 18;}

                        else if ( (synpred124_InternalKdl()) ) {s = 20;}

                         
                        input.seek(index59_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 59, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_48s = "\4\uffff\1\17\14\uffff\1\17\2\uffff\1\17";
    static final String dfa_49s = "\1\4\1\uffff\2\6\1\35\7\uffff\3\6\2\uffff\1\35\2\6\1\35";
    static final String dfa_50s = "\1\144\1\uffff\2\6\1\146\7\uffff\1\6\2\144\2\uffff\1\146\2\6\1\131";
    static final String dfa_51s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\3\1\2\4\uffff";
    static final String[] dfa_52s = {
            "\1\6\1\uffff\1\4\4\uffff\1\10\30\uffff\1\2\27\uffff\1\12\17\uffff\2\1\2\uffff\1\5\2\uffff\1\11\2\uffff\1\13\6\uffff\1\7\1\uffff\5\7\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\17\30\uffff\1\20\27\uffff\2\20\10\uffff\2\17\1\uffff\1\14\11\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\35\uffff\1\22\77\uffff\1\23",
            "\1\24\35\uffff\1\22\77\uffff\1\23",
            "",
            "",
            "\1\17\30\uffff\1\20\27\uffff\2\20\10\uffff\2\17\13\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\17\30\uffff\1\20\27\uffff\2\20\10\uffff\2\17"
    };
    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final short[] dfa_51 = DFA.unpackEncodedString(dfa_51s);
    static final short[][] dfa_52 = unpackEncodedStringArray(dfa_52s);

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = dfa_22;
            this.eof = dfa_48;
            this.min = dfa_49;
            this.max = dfa_50;
            this.accept = dfa_51;
            this.special = dfa_27;
            this.transition = dfa_52;
        }
        public String getDescription() {
            return "3667:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )";
        }
    }
    static final String dfa_53s = "\1\4\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_54s = "\1\144\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_55s = "\2\uffff\1\2\3\uffff\1\3\4\uffff\1\1";
    static final String dfa_56s = "\1\uffff\1\0\1\uffff\1\1\10\uffff}>";
    static final String[] dfa_57s = {
            "\1\3\1\1\1\6\1\2\4\uffff\1\2\27\uffff\1\6\47\uffff\2\6\6\uffff\1\2\17\uffff\1\6",
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
    static final char[] dfa_53 = DFA.unpackEncodedStringToUnsignedChars(dfa_53s);
    static final char[] dfa_54 = DFA.unpackEncodedStringToUnsignedChars(dfa_54s);
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final short[] dfa_56 = DFA.unpackEncodedString(dfa_56s);
    static final short[][] dfa_57 = unpackEncodedStringArray(dfa_57s);

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_53;
            this.max = dfa_54;
            this.accept = dfa_55;
            this.special = dfa_56;
            this.transition = dfa_57;
        }
        public String getDescription() {
            return "4205:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA82_1 = input.LA(1);

                         
                        int index82_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred166_InternalKdl()) ) {s = 11;}

                        else if ( (synpred167_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index82_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA82_3 = input.LA(1);

                         
                        int index82_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred167_InternalKdl()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index82_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 82, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_58s = "\1\5\1\125\1\uffff\1\5\1\uffff\1\125";
    static final String[] dfa_59s = {
            "\1\1\116\uffff\1\2",
            "\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\2\4\uffff\1\4\1\3"
    };
    static final char[] dfa_58 = DFA.unpackEncodedStringToUnsignedChars(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA106 extends DFA {

        public DFA106(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 106;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_58;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L,0x0000000000100000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0xE000F827DFFC0002L,0x00000000000000FFL});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x1000000000000200L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000010000010F0L,0x0000001000123000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0xE000F827FFFC0002L,0x00000000000000FFL});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000000000010B0L,0x0000000000100000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0xE000F82780000002L,0x00000000000000FFL});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0xE000382780000000L,0x00000000000000FFL});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0xE000382F80000040L,0x00000000000000FFL});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0xE000383780000000L,0x00000000000000FFL});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x00000000000000B0L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000006C000000012L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000F00L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000068020000012L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000068000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0xEF80F92780000000L,0x00000000000000FFL});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000060000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0002000000000010L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x003C008000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000009020001DF0L,0x0000001000923000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0038008000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0038000000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000001000000040L,0x0000001000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0xEF80F82780000002L,0x00000000000000FFL});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0F80000000000002L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x1F80001000001060L,0x0000009004020000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0040000000000000L,0x000000000000C000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000020000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000009020001DF0L,0x0000001000963000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000010000000020L,0x0000000000100000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000009000000450L,0x0000001000023000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000000000600000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x1000019000000050L,0x0000001FA00B3000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000010020000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x1000009000000050L,0x0000001FA00B3000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x1000001000000850L,0x0000001FA1493000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x1000001000000850L,0x0000001FA0493000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x000000000C400000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x00000000000010A0L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000030000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x00000010000010F0L,0x0000001000103000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x1000001000001060L,0x0000009004060000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x1000000000000002L,0x0000008004000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000001000001060L,0x0000001000020000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x0000006008000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000002L,0x0000006000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x000000000C000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000122L,0x0000001008000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000122L,0x0000001000000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000122L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x1000001000001060L,0x0000009004020000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0400000000000002L});

}
