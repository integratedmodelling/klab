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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_LOWERCASE_ID", "RULE_ANNOTATION_ID", "RULE_INT", "RULE_LOWERCASE_DASHID", "RULE_UPPERCASE_ID", "RULE_CAMELCASE_ID", "RULE_SHAPE", "RULE_BACKCASE_ID", "RULE_ID", "RULE_EXPR", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'@dataflow'", "'@var'", "'@val'", "'@author'", "'@version'", "'@klab'", "'@worldview'", "'@geometry'", "'@endpoint'", "'@namespace'", "'@coverage'", "','", "'@context'", "'('", "')'", "'const'", "'export'", "'import'", "'label'", "'abstract'", "'final'", "'optional'", "'filter'", "'multiple'", "'+'", "'parameter'", "'expression'", "'*'", "'extends'", "'for'", "'{'", "'}'", "'minimum'", "'maximum'", "'range'", "'to'", "'values'", "'default'", "'unit'", "'as'", "'over'", "'geometry'", "'metadata'", "'class'", "'compute'", "'object'", "'event'", "'observation'", "'value'", "'process'", "'number'", "'concept'", "'boolean'", "'text'", "'list'", "'table'", "'map'", "'extent'", "'spatialextent'", "'temporalextent'", "'annotation'", "'enum'", "'void'", "'partition'", "'resolve'", "'models'", "'concepts'", "'observers'", "'definitions'", "'dependencies'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'in'", "'unknown'", "'urn:klab:'", "':'", "'#'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'='", "'>>'", "'<-'", "'>'", "'<'", "'!='", "'<='", "'>='", "'-'", "'e'", "'E'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_BACKCASE_ID=12;
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
    public static final int RULE_ID=13;
    public static final int RULE_INT=7;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=16;
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
    public static final int RULE_EXPR=14;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=15;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_SHAPE=11;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_LOWERCASE_DASHID=8;
    public static final int RULE_CAMELCASE_ID=10;
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
    public static final int RULE_SL_COMMENT=17;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__114=114;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__116=116;
    public static final int T__80=80;
    public static final int T__111=111;
    public static final int T__81=81;
    public static final int T__110=110;
    public static final int T__82=82;
    public static final int T__113=113;
    public static final int T__83=83;
    public static final int T__112=112;
    public static final int RULE_WS=18;
    public static final int RULE_ANY_OTHER=19;
    public static final int RULE_ANNOTATION_ID=6;
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;

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
    // InternalKdl.g:86:1: ruleModel returns [EObject current=null] : ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* ) ;
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
        Token lv_package_20_2=null;
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

        AntlrDatatypeRuleToken lv_package_20_1 = null;

        EObject lv_scale_22_0 = null;

        EObject lv_scale_24_0 = null;

        EObject lv_contextUrn_26_0 = null;

        EObject lv_actors_27_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getModelAccess().getUnorderedGroup_0()
        	);

        try {
            // InternalKdl.g:95:2: ( ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* ) )
            // InternalKdl.g:96:2: ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* )
            {
            // InternalKdl.g:96:2: ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )* )
            // InternalKdl.g:97:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) ) ( (lv_actors_27_0= ruleActorDefinition ) )*
            {
            // InternalKdl.g:97:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) ) )
            // InternalKdl.g:98:4: ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) )
            {
            // InternalKdl.g:98:4: ( ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* ) )
            // InternalKdl.g:99:5: ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getModelAccess().getUnorderedGroup_0());
            // InternalKdl.g:102:5: ( ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )* )
            // InternalKdl.g:103:6: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )*
            {
            // InternalKdl.g:103:6: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )*
            loop7:
            do {
                int alt7=13;
                alt7 = dfa7.predict(input);
                switch (alt7) {
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
            	    otherlv_1=(Token)match(input,20,FOLLOW_3); if (state.failed) return current;
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

            	        if ( (LA2_0==21) ) {
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
            	    	    otherlv_3=(Token)match(input,21,FOLLOW_5); if (state.failed) return current;
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

            	        if ( (LA3_0==22) ) {
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
            	    	    otherlv_5=(Token)match(input,22,FOLLOW_5); if (state.failed) return current;
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

            	        if ( (LA4_0==23) ) {
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
            	    	    otherlv_7=(Token)match(input,23,FOLLOW_6); if (state.failed) return current;
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
            	    otherlv_9=(Token)match(input,24,FOLLOW_7); if (state.failed) return current;
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
            	    otherlv_11=(Token)match(input,25,FOLLOW_7); if (state.failed) return current;
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
            	    otherlv_13=(Token)match(input,26,FOLLOW_5); if (state.failed) return current;
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
            	    otherlv_15=(Token)match(input,27,FOLLOW_8); if (state.failed) return current;
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
            	    otherlv_17=(Token)match(input,28,FOLLOW_6); if (state.failed) return current;
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
            	    // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKdl.g:426:5: {...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9)");
            	    }
            	    // InternalKdl.g:426:102: ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) )
            	    // InternalKdl.g:427:6: ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9);
            	    // InternalKdl.g:430:9: ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) )
            	    // InternalKdl.g:430:10: {...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:430:19: (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) )
            	    // InternalKdl.g:430:20: otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) )
            	    {
            	    otherlv_19=(Token)match(input,29,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_19, grammarAccess.getModelAccess().getNamespaceKeyword_0_9_0());
            	      								
            	    }
            	    // InternalKdl.g:434:9: ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) )
            	    // InternalKdl.g:435:10: ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) )
            	    {
            	    // InternalKdl.g:435:10: ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) )
            	    // InternalKdl.g:436:11: (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING )
            	    {
            	    // InternalKdl.g:436:11: (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING )
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==RULE_LOWERCASE_ID) ) {
            	        alt5=1;
            	    }
            	    else if ( (LA5_0==RULE_STRING) ) {
            	        alt5=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 5, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // InternalKdl.g:437:12: lv_package_20_1= rulePathName
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getModelAccess().getPackagePathNameParserRuleCall_0_9_1_0_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_4);
            	            lv_package_20_1=rulePathName();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getModelRule());
            	              												}
            	              												set(
            	              													current,
            	              													"package",
            	              													lv_package_20_1,
            	              													"org.integratedmodelling.kdl.Kdl.PathName");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:453:12: lv_package_20_2= RULE_STRING
            	            {
            	            lv_package_20_2=(Token)match(input,RULE_STRING,FOLLOW_4); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_package_20_2, grammarAccess.getModelAccess().getPackageSTRINGTerminalRuleCall_0_9_1_0_1());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getModelRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"package",
            	              													lv_package_20_2,
            	              													"org.eclipse.xtext.common.Terminals.STRING");
            	              											
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
            	case 11 :
            	    // InternalKdl.g:476:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
            	    {
            	    // InternalKdl.g:476:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
            	    // InternalKdl.g:477:5: {...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10)");
            	    }
            	    // InternalKdl.g:477:103: ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
            	    // InternalKdl.g:478:6: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10);
            	    // InternalKdl.g:481:9: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
            	    // InternalKdl.g:481:10: {...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:481:19: (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
            	    // InternalKdl.g:481:20: otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
            	    {
            	    otherlv_21=(Token)match(input,30,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_21, grammarAccess.getModelAccess().getCoverageKeyword_0_10_0());
            	      								
            	    }
            	    // InternalKdl.g:485:9: ( (lv_scale_22_0= ruleFunction ) )
            	    // InternalKdl.g:486:10: (lv_scale_22_0= ruleFunction )
            	    {
            	    // InternalKdl.g:486:10: (lv_scale_22_0= ruleFunction )
            	    // InternalKdl.g:487:11: lv_scale_22_0= ruleFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
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

            	    // InternalKdl.g:504:9: (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
            	    loop6:
            	    do {
            	        int alt6=2;
            	        int LA6_0 = input.LA(1);

            	        if ( (LA6_0==31) ) {
            	            alt6=1;
            	        }


            	        switch (alt6) {
            	    	case 1 :
            	    	    // InternalKdl.g:505:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
            	    	    {
            	    	    otherlv_23=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_23, grammarAccess.getModelAccess().getCommaKeyword_0_10_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKdl.g:509:10: ( (lv_scale_24_0= ruleFunction ) )
            	    	    // InternalKdl.g:510:11: (lv_scale_24_0= ruleFunction )
            	    	    {
            	    	    // InternalKdl.g:510:11: (lv_scale_24_0= ruleFunction )
            	    	    // InternalKdl.g:511:12: lv_scale_24_0= ruleFunction
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_11);
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
            	    	    break loop6;
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
            	    // InternalKdl.g:535:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:535:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
            	    // InternalKdl.g:536:5: {...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11)");
            	    }
            	    // InternalKdl.g:536:103: ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
            	    // InternalKdl.g:537:6: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11);
            	    // InternalKdl.g:540:9: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
            	    // InternalKdl.g:540:10: {...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleModel", "true");
            	    }
            	    // InternalKdl.g:540:19: (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
            	    // InternalKdl.g:540:20: otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) )
            	    {
            	    otherlv_25=(Token)match(input,32,FOLLOW_12); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_25, grammarAccess.getModelAccess().getContextKeyword_0_11_0());
            	      								
            	    }
            	    // InternalKdl.g:544:9: ( (lv_contextUrn_26_0= ruleUrn ) )
            	    // InternalKdl.g:545:10: (lv_contextUrn_26_0= ruleUrn )
            	    {
            	    // InternalKdl.g:545:10: (lv_contextUrn_26_0= ruleUrn )
            	    // InternalKdl.g:546:11: lv_contextUrn_26_0= ruleUrn
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
            	    break loop7;
                }
            } while (true);


            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getModelAccess().getUnorderedGroup_0());

            }

            // InternalKdl.g:576:3: ( (lv_actors_27_0= ruleActorDefinition ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ANNOTATION_ID||(LA8_0>=35 && LA8_0<=37)||(LA8_0>=39 && LA8_0<=42)||LA8_0==45||LA8_0==54||(LA8_0>=65 && LA8_0<=84)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKdl.g:577:4: (lv_actors_27_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:577:4: (lv_actors_27_0= ruleActorDefinition )
            	    // InternalKdl.g:578:5: lv_actors_27_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getActorsActorDefinitionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_13);
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
            	    break loop8;
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
    // InternalKdl.g:602:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKdl.g:602:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKdl.g:603:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKdl.g:609:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:615:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKdl.g:616:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKdl.g:616:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKdl.g:617:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKdl.g:617:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKdl.g:618:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKdl.g:618:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKdl.g:619:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_14); if (state.failed) return current;
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

            // InternalKdl.g:635:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==33) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalKdl.g:636:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:640:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( ((LA9_0>=RULE_STRING && LA9_0<=RULE_LOWERCASE_ID)||(LA9_0>=RULE_INT && LA9_0<=RULE_CAMELCASE_ID)||(LA9_0>=RULE_ID && LA9_0<=RULE_EXPR)||LA9_0==31||LA9_0==33||LA9_0==44||LA9_0==50||(LA9_0>=90 && LA9_0<=91)||LA9_0==96||LA9_0==99||LA9_0==113) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // InternalKdl.g:641:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKdl.g:641:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKdl.g:642:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_16);
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

                    otherlv_3=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:668:1: entryRuleActorDefinition returns [EObject current=null] : iv_ruleActorDefinition= ruleActorDefinition EOF ;
    public final EObject entryRuleActorDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActorDefinition = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16()
        	);

        try {
            // InternalKdl.g:672:2: (iv_ruleActorDefinition= ruleActorDefinition EOF )
            // InternalKdl.g:673:2: iv_ruleActorDefinition= ruleActorDefinition EOF
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

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleActorDefinition"


    // $ANTLR start "ruleActorDefinition"
    // InternalKdl.g:682:1: ruleActorDefinition returns [EObject current=null] : ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? ) ) ;
    public final EObject ruleActorDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_const_0_0=null;
        Token lv_exported_1_0=null;
        Token lv_imported_2_0=null;
        Token lv_annotationTag_4_0=null;
        Token lv_docstring_5_0=null;
        Token otherlv_6=null;
        Token lv_label_7_0=null;
        Token lv_abstract_9_0=null;
        Token lv_const_10_0=null;
        Token lv_final_11_0=null;
        Token lv_optional_12_0=null;
        Token lv_exported_13_0=null;
        Token lv_filter_14_0=null;
        Token lv_imported_15_0=null;
        Token lv_multiple_16_0=null;
        Token lv_arity_17_0=null;
        Token lv_minimum_18_0=null;
        Token lv_parameter_19_0=null;
        Token lv_expression_21_0=null;
        Token lv_name_22_1=null;
        Token lv_name_22_2=null;
        Token lv_name_22_3=null;
        Token lv_name_22_4=null;
        Token otherlv_23=null;
        Token lv_extended_24_1=null;
        Token lv_extended_24_2=null;
        Token lv_extended_24_3=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token lv_docstring_29_0=null;
        Token otherlv_30=null;
        Token lv_label_31_0=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_39=null;
        Token otherlv_41=null;
        Token otherlv_43=null;
        Token lv_enumValues_44_1=null;
        Token lv_enumValues_44_2=null;
        Token lv_enumValues_44_3=null;
        Token lv_enumValues_44_4=null;
        Token otherlv_45=null;
        Token lv_enumValues_46_1=null;
        Token lv_enumValues_46_2=null;
        Token lv_enumValues_46_3=null;
        Token lv_enumValues_46_4=null;
        Token otherlv_48=null;
        Token otherlv_50=null;
        Token otherlv_52=null;
        Token lv_localName_53_0=null;
        Token otherlv_54=null;
        Token otherlv_56=null;
        AntlrDatatypeRuleToken lv_type_3_0 = null;

        EObject lv_annotations_8_0 = null;

        AntlrDatatypeRuleToken lv_type_20_0 = null;

        AntlrDatatypeRuleToken lv_targets_26_0 = null;

        AntlrDatatypeRuleToken lv_targets_28_0 = null;

        EObject lv_body_33_0 = null;

        EObject lv_rangeMin_36_0 = null;

        EObject lv_rangeMax_38_0 = null;

        EObject lv_rangeMin_40_0 = null;

        EObject lv_rangeMax_42_0 = null;

        EObject lv_default_49_0 = null;

        EObject lv_unit_51_0 = null;

        EObject lv_coverage_55_0 = null;

        EObject lv_coverage_57_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16()
        	);

        try {
            // InternalKdl.g:691:2: ( ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? ) ) )
            // InternalKdl.g:692:2: ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? ) )
            {
            // InternalKdl.g:692:2: ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? ) )
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // InternalKdl.g:693:3: ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? )
                    {
                    // InternalKdl.g:693:3: ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? )
                    // InternalKdl.g:694:4: ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )?
                    {
                    // InternalKdl.g:694:4: ( (lv_const_0_0= 'const' ) )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==35) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalKdl.g:695:5: (lv_const_0_0= 'const' )
                            {
                            // InternalKdl.g:695:5: (lv_const_0_0= 'const' )
                            // InternalKdl.g:696:6: lv_const_0_0= 'const'
                            {
                            lv_const_0_0=(Token)match(input,35,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_const_0_0, grammarAccess.getActorDefinitionAccess().getConstConstKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "const", lv_const_0_0 != null, "const");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:708:4: ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==36) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==37) ) {
                        alt12=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalKdl.g:709:5: ( (lv_exported_1_0= 'export' ) )
                            {
                            // InternalKdl.g:709:5: ( (lv_exported_1_0= 'export' ) )
                            // InternalKdl.g:710:6: (lv_exported_1_0= 'export' )
                            {
                            // InternalKdl.g:710:6: (lv_exported_1_0= 'export' )
                            // InternalKdl.g:711:7: lv_exported_1_0= 'export'
                            {
                            lv_exported_1_0=(Token)match(input,36,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_1_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_0_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "exported", lv_exported_1_0 != null, "export");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:724:5: ( (lv_imported_2_0= 'import' ) )
                            {
                            // InternalKdl.g:724:5: ( (lv_imported_2_0= 'import' ) )
                            // InternalKdl.g:725:6: (lv_imported_2_0= 'import' )
                            {
                            // InternalKdl.g:725:6: (lv_imported_2_0= 'import' )
                            // InternalKdl.g:726:7: lv_imported_2_0= 'import'
                            {
                            lv_imported_2_0=(Token)match(input,37,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_imported_2_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_0_1_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "imported", lv_imported_2_0 != null, "import");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:739:4: ( (lv_type_3_0= ruleACTOR ) )
                    // InternalKdl.g:740:5: (lv_type_3_0= ruleACTOR )
                    {
                    // InternalKdl.g:740:5: (lv_type_3_0= ruleACTOR )
                    // InternalKdl.g:741:6: lv_type_3_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_19);
                    lv_type_3_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_3_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:758:4: ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) )
                    // InternalKdl.g:759:5: (lv_annotationTag_4_0= RULE_ANNOTATION_ID )
                    {
                    // InternalKdl.g:759:5: (lv_annotationTag_4_0= RULE_ANNOTATION_ID )
                    // InternalKdl.g:760:6: lv_annotationTag_4_0= RULE_ANNOTATION_ID
                    {
                    lv_annotationTag_4_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_annotationTag_4_0, grammarAccess.getActorDefinitionAccess().getAnnotationTagANNOTATION_IDTerminalRuleCall_0_3_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"annotationTag",
                      							lv_annotationTag_4_0,
                      							"org.integratedmodelling.kdl.Kdl.ANNOTATION_ID");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:776:4: ( (lv_docstring_5_0= RULE_STRING ) )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==RULE_STRING) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalKdl.g:777:5: (lv_docstring_5_0= RULE_STRING )
                            {
                            // InternalKdl.g:777:5: (lv_docstring_5_0= RULE_STRING )
                            // InternalKdl.g:778:6: lv_docstring_5_0= RULE_STRING
                            {
                            lv_docstring_5_0=(Token)match(input,RULE_STRING,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_5_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_0_4_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_5_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:794:4: (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==38) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalKdl.g:795:5: otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) )
                            {
                            otherlv_6=(Token)match(input,38,FOLLOW_6); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_6, grammarAccess.getActorDefinitionAccess().getLabelKeyword_0_5_0());
                              				
                            }
                            // InternalKdl.g:799:5: ( (lv_label_7_0= RULE_STRING ) )
                            // InternalKdl.g:800:6: (lv_label_7_0= RULE_STRING )
                            {
                            // InternalKdl.g:800:6: (lv_label_7_0= RULE_STRING )
                            // InternalKdl.g:801:7: lv_label_7_0= RULE_STRING
                            {
                            lv_label_7_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_label_7_0, grammarAccess.getActorDefinitionAccess().getLabelSTRINGTerminalRuleCall_0_5_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"label",
                              								lv_label_7_0,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
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
                    // InternalKdl.g:820:3: ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:820:3: ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:821:4: ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )?
                    {
                    // InternalKdl.g:821:4: ( (lv_annotations_8_0= ruleAnnotation ) )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==RULE_ANNOTATION_ID) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalKdl.g:822:5: (lv_annotations_8_0= ruleAnnotation )
                    	    {
                    	    // InternalKdl.g:822:5: (lv_annotations_8_0= ruleAnnotation )
                    	    // InternalKdl.g:823:6: lv_annotations_8_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getAnnotationsAnnotationParserRuleCall_1_0_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_18);
                    	    lv_annotations_8_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"annotations",
                    	      							lv_annotations_8_0,
                    	      							"org.integratedmodelling.kdl.Kdl.Annotation");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    // InternalKdl.g:840:4: ( (lv_abstract_9_0= 'abstract' ) )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==39) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKdl.g:841:5: (lv_abstract_9_0= 'abstract' )
                            {
                            // InternalKdl.g:841:5: (lv_abstract_9_0= 'abstract' )
                            // InternalKdl.g:842:6: lv_abstract_9_0= 'abstract'
                            {
                            lv_abstract_9_0=(Token)match(input,39,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_abstract_9_0, grammarAccess.getActorDefinitionAccess().getAbstractAbstractKeyword_1_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "abstract", lv_abstract_9_0 != null, "abstract");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:854:4: ( (lv_const_10_0= 'const' ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==35) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:855:5: (lv_const_10_0= 'const' )
                            {
                            // InternalKdl.g:855:5: (lv_const_10_0= 'const' )
                            // InternalKdl.g:856:6: lv_const_10_0= 'const'
                            {
                            lv_const_10_0=(Token)match(input,35,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_const_10_0, grammarAccess.getActorDefinitionAccess().getConstConstKeyword_1_2_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "const", lv_const_10_0 != null, "const");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:868:4: ( (lv_final_11_0= 'final' ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==40) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKdl.g:869:5: (lv_final_11_0= 'final' )
                            {
                            // InternalKdl.g:869:5: (lv_final_11_0= 'final' )
                            // InternalKdl.g:870:6: lv_final_11_0= 'final'
                            {
                            lv_final_11_0=(Token)match(input,40,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_final_11_0, grammarAccess.getActorDefinitionAccess().getFinalFinalKeyword_1_3_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "final", lv_final_11_0 != null, "final");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:882:4: ( (lv_optional_12_0= 'optional' ) )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==41) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKdl.g:883:5: (lv_optional_12_0= 'optional' )
                            {
                            // InternalKdl.g:883:5: (lv_optional_12_0= 'optional' )
                            // InternalKdl.g:884:6: lv_optional_12_0= 'optional'
                            {
                            lv_optional_12_0=(Token)match(input,41,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_optional_12_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_4_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "optional", lv_optional_12_0 != null, "optional");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:896:4: ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )?
                    int alt22=4;
                    switch ( input.LA(1) ) {
                        case 36:
                            {
                            alt22=1;
                            }
                            break;
                        case 42:
                            {
                            alt22=2;
                            }
                            break;
                        case 37:
                            {
                            alt22=3;
                            }
                            break;
                    }

                    switch (alt22) {
                        case 1 :
                            // InternalKdl.g:897:5: ( (lv_exported_13_0= 'export' ) )
                            {
                            // InternalKdl.g:897:5: ( (lv_exported_13_0= 'export' ) )
                            // InternalKdl.g:898:6: (lv_exported_13_0= 'export' )
                            {
                            // InternalKdl.g:898:6: (lv_exported_13_0= 'export' )
                            // InternalKdl.g:899:7: lv_exported_13_0= 'export'
                            {
                            lv_exported_13_0=(Token)match(input,36,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_13_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_1_5_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "exported", lv_exported_13_0 != null, "export");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:912:5: ( (lv_filter_14_0= 'filter' ) )
                            {
                            // InternalKdl.g:912:5: ( (lv_filter_14_0= 'filter' ) )
                            // InternalKdl.g:913:6: (lv_filter_14_0= 'filter' )
                            {
                            // InternalKdl.g:913:6: (lv_filter_14_0= 'filter' )
                            // InternalKdl.g:914:7: lv_filter_14_0= 'filter'
                            {
                            lv_filter_14_0=(Token)match(input,42,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_filter_14_0, grammarAccess.getActorDefinitionAccess().getFilterFilterKeyword_1_5_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "filter", lv_filter_14_0 != null, "filter");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:927:5: ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? )
                            {
                            // InternalKdl.g:927:5: ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? )
                            // InternalKdl.g:928:6: ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )?
                            {
                            // InternalKdl.g:928:6: ( (lv_imported_15_0= 'import' ) )
                            // InternalKdl.g:929:7: (lv_imported_15_0= 'import' )
                            {
                            // InternalKdl.g:929:7: (lv_imported_15_0= 'import' )
                            // InternalKdl.g:930:8: lv_imported_15_0= 'import'
                            {
                            lv_imported_15_0=(Token)match(input,37,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_imported_15_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_1_5_2_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getActorDefinitionRule());
                              								}
                              								setWithLastConsumed(current, "imported", lv_imported_15_0 != null, "import");
                              							
                            }

                            }


                            }

                            // InternalKdl.g:942:6: ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )?
                            int alt21=3;
                            int LA21_0 = input.LA(1);

                            if ( (LA21_0==43) ) {
                                alt21=1;
                            }
                            else if ( (LA21_0==RULE_INT) ) {
                                alt21=2;
                            }
                            switch (alt21) {
                                case 1 :
                                    // InternalKdl.g:943:7: ( (lv_multiple_16_0= 'multiple' ) )
                                    {
                                    // InternalKdl.g:943:7: ( (lv_multiple_16_0= 'multiple' ) )
                                    // InternalKdl.g:944:8: (lv_multiple_16_0= 'multiple' )
                                    {
                                    // InternalKdl.g:944:8: (lv_multiple_16_0= 'multiple' )
                                    // InternalKdl.g:945:9: lv_multiple_16_0= 'multiple'
                                    {
                                    lv_multiple_16_0=(Token)match(input,43,FOLLOW_18); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_multiple_16_0, grammarAccess.getActorDefinitionAccess().getMultipleMultipleKeyword_1_5_2_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									setWithLastConsumed(current, "multiple", lv_multiple_16_0 != null, "multiple");
                                      								
                                    }

                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:958:7: ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? )
                                    {
                                    // InternalKdl.g:958:7: ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? )
                                    // InternalKdl.g:959:8: ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )?
                                    {
                                    // InternalKdl.g:959:8: ( (lv_arity_17_0= RULE_INT ) )
                                    // InternalKdl.g:960:9: (lv_arity_17_0= RULE_INT )
                                    {
                                    // InternalKdl.g:960:9: (lv_arity_17_0= RULE_INT )
                                    // InternalKdl.g:961:10: lv_arity_17_0= RULE_INT
                                    {
                                    lv_arity_17_0=(Token)match(input,RULE_INT,FOLLOW_23); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      										newLeafNode(lv_arity_17_0, grammarAccess.getActorDefinitionAccess().getArityINTTerminalRuleCall_1_5_2_1_1_0_0());
                                      									
                                    }
                                    if ( state.backtracking==0 ) {

                                      										if (current==null) {
                                      											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      										}
                                      										setWithLastConsumed(
                                      											current,
                                      											"arity",
                                      											lv_arity_17_0,
                                      											"org.eclipse.xtext.common.Terminals.INT");
                                      									
                                    }

                                    }


                                    }

                                    // InternalKdl.g:977:8: ( (lv_minimum_18_0= '+' ) )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==44) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // InternalKdl.g:978:9: (lv_minimum_18_0= '+' )
                                            {
                                            // InternalKdl.g:978:9: (lv_minimum_18_0= '+' )
                                            // InternalKdl.g:979:10: lv_minimum_18_0= '+'
                                            {
                                            lv_minimum_18_0=(Token)match(input,44,FOLLOW_18); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              										newLeafNode(lv_minimum_18_0, grammarAccess.getActorDefinitionAccess().getMinimumPlusSignKeyword_1_5_2_1_1_1_0());
                                              									
                                            }
                                            if ( state.backtracking==0 ) {

                                              										if (current==null) {
                                              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                              										}
                                              										setWithLastConsumed(current, "minimum", lv_minimum_18_0 != null, "+");
                                              									
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

                    // InternalKdl.g:995:4: ( (lv_parameter_19_0= 'parameter' ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==45) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKdl.g:996:5: (lv_parameter_19_0= 'parameter' )
                            {
                            // InternalKdl.g:996:5: (lv_parameter_19_0= 'parameter' )
                            // InternalKdl.g:997:6: lv_parameter_19_0= 'parameter'
                            {
                            lv_parameter_19_0=(Token)match(input,45,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_parameter_19_0, grammarAccess.getActorDefinitionAccess().getParameterParameterKeyword_1_6_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "parameter", lv_parameter_19_0 != null, "parameter");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1009:4: ( (lv_type_20_0= ruleACTOR ) )
                    // InternalKdl.g:1010:5: (lv_type_20_0= ruleACTOR )
                    {
                    // InternalKdl.g:1010:5: (lv_type_20_0= ruleACTOR )
                    // InternalKdl.g:1011:6: lv_type_20_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_1_7_0());
                      					
                    }
                    pushFollow(FOLLOW_24);
                    lv_type_20_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_20_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1028:4: ( (lv_expression_21_0= 'expression' ) )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==46) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalKdl.g:1029:5: (lv_expression_21_0= 'expression' )
                            {
                            // InternalKdl.g:1029:5: (lv_expression_21_0= 'expression' )
                            // InternalKdl.g:1030:6: lv_expression_21_0= 'expression'
                            {
                            lv_expression_21_0=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_expression_21_0, grammarAccess.getActorDefinitionAccess().getExpressionExpressionKeyword_1_8_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "expression", lv_expression_21_0 != null, "expression");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1042:4: ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) )
                    // InternalKdl.g:1043:5: ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) )
                    {
                    // InternalKdl.g:1043:5: ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) )
                    // InternalKdl.g:1044:6: (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' )
                    {
                    // InternalKdl.g:1044:6: (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' )
                    int alt25=4;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt25=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt25=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt25=3;
                        }
                        break;
                    case 47:
                        {
                        alt25=4;
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
                            // InternalKdl.g:1045:7: lv_name_22_1= RULE_LOWERCASE_ID
                            {
                            lv_name_22_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_22_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_9_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_22_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1060:7: lv_name_22_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_22_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_22_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_1_9_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_22_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1075:7: lv_name_22_3= RULE_STRING
                            {
                            lv_name_22_3=(Token)match(input,RULE_STRING,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_22_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_1_9_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_22_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;
                        case 4 :
                            // InternalKdl.g:1090:7: lv_name_22_4= '*'
                            {
                            lv_name_22_4=(Token)match(input,47,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_22_4, grammarAccess.getActorDefinitionAccess().getNameAsteriskKeyword_1_9_0_3());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "name", lv_name_22_4, null);
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:1103:4: (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==48) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalKdl.g:1104:5: otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) )
                            {
                            otherlv_23=(Token)match(input,48,FOLLOW_27); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_23, grammarAccess.getActorDefinitionAccess().getExtendsKeyword_1_10_0());
                              				
                            }
                            // InternalKdl.g:1108:5: ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) )
                            // InternalKdl.g:1109:6: ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) )
                            {
                            // InternalKdl.g:1109:6: ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) )
                            // InternalKdl.g:1110:7: (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING )
                            {
                            // InternalKdl.g:1110:7: (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING )
                            int alt26=3;
                            switch ( input.LA(1) ) {
                            case RULE_LOWERCASE_ID:
                                {
                                alt26=1;
                                }
                                break;
                            case RULE_LOWERCASE_DASHID:
                                {
                                alt26=2;
                                }
                                break;
                            case RULE_STRING:
                                {
                                alt26=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 26, 0, input);

                                throw nvae;
                            }

                            switch (alt26) {
                                case 1 :
                                    // InternalKdl.g:1111:8: lv_extended_24_1= RULE_LOWERCASE_ID
                                    {
                                    lv_extended_24_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_28); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_24_1, grammarAccess.getActorDefinitionAccess().getExtendedLOWERCASE_IDTerminalRuleCall_1_10_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_24_1,
                                      									"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1126:8: lv_extended_24_2= RULE_LOWERCASE_DASHID
                                    {
                                    lv_extended_24_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_28); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_24_2, grammarAccess.getActorDefinitionAccess().getExtendedLOWERCASE_DASHIDTerminalRuleCall_1_10_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_24_2,
                                      									"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1141:8: lv_extended_24_3= RULE_STRING
                                    {
                                    lv_extended_24_3=(Token)match(input,RULE_STRING,FOLLOW_28); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_24_3, grammarAccess.getActorDefinitionAccess().getExtendedSTRINGTerminalRuleCall_1_10_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_24_3,
                                      									"org.eclipse.xtext.common.Terminals.STRING");
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1159:4: (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==49) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalKdl.g:1160:5: otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )*
                            {
                            otherlv_25=(Token)match(input,49,FOLLOW_29); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_25, grammarAccess.getActorDefinitionAccess().getForKeyword_1_11_0());
                              				
                            }
                            // InternalKdl.g:1164:5: ( (lv_targets_26_0= ruleTARGET ) )
                            // InternalKdl.g:1165:6: (lv_targets_26_0= ruleTARGET )
                            {
                            // InternalKdl.g:1165:6: (lv_targets_26_0= ruleTARGET )
                            // InternalKdl.g:1166:7: lv_targets_26_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_1_11_1_0());
                              						
                            }
                            pushFollow(FOLLOW_30);
                            lv_targets_26_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_26_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1183:5: (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )*
                            loop28:
                            do {
                                int alt28=2;
                                int LA28_0 = input.LA(1);

                                if ( (LA28_0==31) ) {
                                    alt28=1;
                                }


                                switch (alt28) {
                            	case 1 :
                            	    // InternalKdl.g:1184:6: otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) )
                            	    {
                            	    otherlv_27=(Token)match(input,31,FOLLOW_29); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_27, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_11_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1188:6: ( (lv_targets_28_0= ruleTARGET ) )
                            	    // InternalKdl.g:1189:7: (lv_targets_28_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:1189:7: (lv_targets_28_0= ruleTARGET )
                            	    // InternalKdl.g:1190:8: lv_targets_28_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_1_11_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_30);
                            	    lv_targets_28_0=ruleTARGET();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"targets",
                            	      									lv_targets_28_0,
                            	      									"org.integratedmodelling.kdl.Kdl.TARGET");
                            	      								afterParserOrEnumRuleCall();
                            	      							
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

                    // InternalKdl.g:1209:4: ( (lv_docstring_29_0= RULE_STRING ) )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==RULE_STRING) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalKdl.g:1210:5: (lv_docstring_29_0= RULE_STRING )
                            {
                            // InternalKdl.g:1210:5: (lv_docstring_29_0= RULE_STRING )
                            // InternalKdl.g:1211:6: lv_docstring_29_0= RULE_STRING
                            {
                            lv_docstring_29_0=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_29_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_12_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_29_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1227:4: (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==38) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalKdl.g:1228:5: otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) )
                            {
                            otherlv_30=(Token)match(input,38,FOLLOW_6); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_30, grammarAccess.getActorDefinitionAccess().getLabelKeyword_1_13_0());
                              				
                            }
                            // InternalKdl.g:1232:5: ( (lv_label_31_0= RULE_STRING ) )
                            // InternalKdl.g:1233:6: (lv_label_31_0= RULE_STRING )
                            {
                            // InternalKdl.g:1233:6: (lv_label_31_0= RULE_STRING )
                            // InternalKdl.g:1234:7: lv_label_31_0= RULE_STRING
                            {
                            lv_label_31_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_label_31_0, grammarAccess.getActorDefinitionAccess().getLabelSTRINGTerminalRuleCall_1_13_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"label",
                              								lv_label_31_0,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1251:4: (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==50) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // InternalKdl.g:1252:5: otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}'
                            {
                            otherlv_32=(Token)match(input,50,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_32, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_14_0());
                              				
                            }
                            // InternalKdl.g:1256:5: ( (lv_body_33_0= ruleDataflowBody ) )
                            // InternalKdl.g:1257:6: (lv_body_33_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1257:6: (lv_body_33_0= ruleDataflowBody )
                            // InternalKdl.g:1258:7: lv_body_33_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_14_1_0());
                              						
                            }
                            pushFollow(FOLLOW_34);
                            lv_body_33_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_33_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_34=(Token)match(input,51,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_34, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_14_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1280:4: ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )?
                    int alt37=3;
                    switch ( input.LA(1) ) {
                        case 52:
                        case 53:
                            {
                            alt37=1;
                            }
                            break;
                        case 54:
                            {
                            int LA37_2 = input.LA(2);

                            if ( (LA37_2==RULE_INT||LA37_2==44||LA37_2==113) ) {
                                alt37=1;
                            }
                            }
                            break;
                        case 56:
                            {
                            alt37=2;
                            }
                            break;
                    }

                    switch (alt37) {
                        case 1 :
                            // InternalKdl.g:1281:5: ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) )
                            {
                            // InternalKdl.g:1281:5: ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) )
                            int alt33=3;
                            switch ( input.LA(1) ) {
                            case 52:
                                {
                                alt33=1;
                                }
                                break;
                            case 53:
                                {
                                alt33=2;
                                }
                                break;
                            case 54:
                                {
                                alt33=3;
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
                                    // InternalKdl.g:1282:6: (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1282:6: (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) )
                                    // InternalKdl.g:1283:7: otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) )
                                    {
                                    otherlv_35=(Token)match(input,52,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_35, grammarAccess.getActorDefinitionAccess().getMinimumKeyword_1_15_0_0_0());
                                      						
                                    }
                                    // InternalKdl.g:1287:7: ( (lv_rangeMin_36_0= ruleNumber ) )
                                    // InternalKdl.g:1288:8: (lv_rangeMin_36_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1288:8: (lv_rangeMin_36_0= ruleNumber )
                                    // InternalKdl.g:1289:9: lv_rangeMin_36_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_15_0_0_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
                                    lv_rangeMin_36_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMin",
                                      										lv_rangeMin_36_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1308:6: (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1308:6: (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) )
                                    // InternalKdl.g:1309:7: otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) )
                                    {
                                    otherlv_37=(Token)match(input,53,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_37, grammarAccess.getActorDefinitionAccess().getMaximumKeyword_1_15_0_1_0());
                                      						
                                    }
                                    // InternalKdl.g:1313:7: ( (lv_rangeMax_38_0= ruleNumber ) )
                                    // InternalKdl.g:1314:8: (lv_rangeMax_38_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1314:8: (lv_rangeMax_38_0= ruleNumber )
                                    // InternalKdl.g:1315:9: lv_rangeMax_38_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_15_0_1_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
                                    lv_rangeMax_38_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMax",
                                      										lv_rangeMax_38_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1334:6: (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1334:6: (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) )
                                    // InternalKdl.g:1335:7: otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) )
                                    {
                                    otherlv_39=(Token)match(input,54,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_39, grammarAccess.getActorDefinitionAccess().getRangeKeyword_1_15_0_2_0());
                                      						
                                    }
                                    // InternalKdl.g:1339:7: ( (lv_rangeMin_40_0= ruleNumber ) )
                                    // InternalKdl.g:1340:8: (lv_rangeMin_40_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1340:8: (lv_rangeMin_40_0= ruleNumber )
                                    // InternalKdl.g:1341:9: lv_rangeMin_40_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_15_0_2_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_38);
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

                                    otherlv_41=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_41, grammarAccess.getActorDefinitionAccess().getToKeyword_1_15_0_2_2());
                                      						
                                    }
                                    // InternalKdl.g:1362:7: ( (lv_rangeMax_42_0= ruleNumber ) )
                                    // InternalKdl.g:1363:8: (lv_rangeMax_42_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1363:8: (lv_rangeMax_42_0= ruleNumber )
                                    // InternalKdl.g:1364:9: lv_rangeMax_42_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_15_0_2_3_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
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

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1384:5: (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* )
                            {
                            // InternalKdl.g:1384:5: (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* )
                            // InternalKdl.g:1385:6: otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )*
                            {
                            otherlv_43=(Token)match(input,56,FOLLOW_39); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_43, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_15_1_0());
                              					
                            }
                            // InternalKdl.g:1389:6: ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) )
                            // InternalKdl.g:1390:7: ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) )
                            {
                            // InternalKdl.g:1390:7: ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) )
                            // InternalKdl.g:1391:8: (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID )
                            {
                            // InternalKdl.g:1391:8: (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID )
                            int alt34=4;
                            switch ( input.LA(1) ) {
                            case RULE_STRING:
                                {
                                alt34=1;
                                }
                                break;
                            case RULE_UPPERCASE_ID:
                                {
                                alt34=2;
                                }
                                break;
                            case RULE_LOWERCASE_ID:
                                {
                                alt34=3;
                                }
                                break;
                            case RULE_CAMELCASE_ID:
                                {
                                alt34=4;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 34, 0, input);

                                throw nvae;
                            }

                            switch (alt34) {
                                case 1 :
                                    // InternalKdl.g:1392:9: lv_enumValues_44_1= RULE_STRING
                                    {
                                    lv_enumValues_44_1=(Token)match(input,RULE_STRING,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_44_1, grammarAccess.getActorDefinitionAccess().getEnumValuesSTRINGTerminalRuleCall_1_15_1_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_44_1,
                                      										"org.eclipse.xtext.common.Terminals.STRING");
                                      								
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1407:9: lv_enumValues_44_2= RULE_UPPERCASE_ID
                                    {
                                    lv_enumValues_44_2=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_44_2, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_15_1_1_0_1());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_44_2,
                                      										"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                                      								
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1422:9: lv_enumValues_44_3= RULE_LOWERCASE_ID
                                    {
                                    lv_enumValues_44_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_44_3, grammarAccess.getActorDefinitionAccess().getEnumValuesLOWERCASE_IDTerminalRuleCall_1_15_1_1_0_2());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_44_3,
                                      										"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                                      								
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:1437:9: lv_enumValues_44_4= RULE_CAMELCASE_ID
                                    {
                                    lv_enumValues_44_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_44_4, grammarAccess.getActorDefinitionAccess().getEnumValuesCAMELCASE_IDTerminalRuleCall_1_15_1_1_0_3());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_44_4,
                                      										"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
                                      								
                                    }

                                    }
                                    break;

                            }


                            }


                            }

                            // InternalKdl.g:1454:6: (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )*
                            loop36:
                            do {
                                int alt36=2;
                                int LA36_0 = input.LA(1);

                                if ( (LA36_0==31) ) {
                                    alt36=1;
                                }


                                switch (alt36) {
                            	case 1 :
                            	    // InternalKdl.g:1455:7: otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) )
                            	    {
                            	    otherlv_45=(Token)match(input,31,FOLLOW_39); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_45, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_15_1_2_0());
                            	      						
                            	    }
                            	    // InternalKdl.g:1459:7: ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) )
                            	    // InternalKdl.g:1460:8: ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) )
                            	    {
                            	    // InternalKdl.g:1460:8: ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) )
                            	    // InternalKdl.g:1461:9: (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID )
                            	    {
                            	    // InternalKdl.g:1461:9: (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID )
                            	    int alt35=4;
                            	    switch ( input.LA(1) ) {
                            	    case RULE_STRING:
                            	        {
                            	        alt35=1;
                            	        }
                            	        break;
                            	    case RULE_UPPERCASE_ID:
                            	        {
                            	        alt35=2;
                            	        }
                            	        break;
                            	    case RULE_LOWERCASE_ID:
                            	        {
                            	        alt35=3;
                            	        }
                            	        break;
                            	    case RULE_CAMELCASE_ID:
                            	        {
                            	        alt35=4;
                            	        }
                            	        break;
                            	    default:
                            	        if (state.backtracking>0) {state.failed=true; return current;}
                            	        NoViableAltException nvae =
                            	            new NoViableAltException("", 35, 0, input);

                            	        throw nvae;
                            	    }

                            	    switch (alt35) {
                            	        case 1 :
                            	            // InternalKdl.g:1462:10: lv_enumValues_46_1= RULE_STRING
                            	            {
                            	            lv_enumValues_46_1=(Token)match(input,RULE_STRING,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_46_1, grammarAccess.getActorDefinitionAccess().getEnumValuesSTRINGTerminalRuleCall_1_15_1_2_1_0_0());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_46_1,
                            	              											"org.eclipse.xtext.common.Terminals.STRING");
                            	              									
                            	            }

                            	            }
                            	            break;
                            	        case 2 :
                            	            // InternalKdl.g:1477:10: lv_enumValues_46_2= RULE_UPPERCASE_ID
                            	            {
                            	            lv_enumValues_46_2=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_46_2, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_15_1_2_1_0_1());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_46_2,
                            	              											"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                            	              									
                            	            }

                            	            }
                            	            break;
                            	        case 3 :
                            	            // InternalKdl.g:1492:10: lv_enumValues_46_3= RULE_LOWERCASE_ID
                            	            {
                            	            lv_enumValues_46_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_46_3, grammarAccess.getActorDefinitionAccess().getEnumValuesLOWERCASE_IDTerminalRuleCall_1_15_1_2_1_0_2());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_46_3,
                            	              											"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                            	              									
                            	            }

                            	            }
                            	            break;
                            	        case 4 :
                            	            // InternalKdl.g:1507:10: lv_enumValues_46_4= RULE_CAMELCASE_ID
                            	            {
                            	            lv_enumValues_46_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_46_4, grammarAccess.getActorDefinitionAccess().getEnumValuesCAMELCASE_IDTerminalRuleCall_1_15_1_2_1_0_3());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_46_4,
                            	              											"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
                            	              									
                            	            }

                            	            }
                            	            break;

                            	    }


                            	    }


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

                    // InternalKdl.g:1527:4: ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) )
                    // InternalKdl.g:1528:5: ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) )
                    {
                    // InternalKdl.g:1528:5: ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) )
                    // InternalKdl.g:1529:6: ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* )
                    {
                    getUnorderedGroupHelper().enter(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16());
                    // InternalKdl.g:1532:6: ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* )
                    // InternalKdl.g:1533:7: ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )*
                    {
                    // InternalKdl.g:1533:7: ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )*
                    loop38:
                    do {
                        int alt38=3;
                        int LA38_0 = input.LA(1);

                        if ( LA38_0 == 57 && getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0) ) {
                            alt38=1;
                        }
                        else if ( LA38_0 == 58 && getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1) ) {
                            alt38=2;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // InternalKdl.g:1534:5: ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) )
                    	    {
                    	    // InternalKdl.g:1534:5: ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) )
                    	    // InternalKdl.g:1535:6: {...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) )
                    	    {
                    	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0)");
                    	    }
                    	    // InternalKdl.g:1535:116: ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) )
                    	    // InternalKdl.g:1536:7: ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) )
                    	    {
                    	    getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0);
                    	    // InternalKdl.g:1539:10: ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) )
                    	    // InternalKdl.g:1539:11: {...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) )
                    	    {
                    	    if ( !((true)) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "true");
                    	    }
                    	    // InternalKdl.g:1539:20: (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) )
                    	    // InternalKdl.g:1539:21: otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) )
                    	    {
                    	    otherlv_48=(Token)match(input,57,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      										newLeafNode(otherlv_48, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_16_0_0());
                    	      									
                    	    }
                    	    // InternalKdl.g:1543:10: ( (lv_default_49_0= ruleValue ) )
                    	    // InternalKdl.g:1544:11: (lv_default_49_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:1544:11: (lv_default_49_0= ruleValue )
                    	    // InternalKdl.g:1545:12: lv_default_49_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      												newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_16_0_1_0());
                    	      											
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_default_49_0=ruleValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      												if (current==null) {
                    	      													current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      												}
                    	      												set(
                    	      													current,
                    	      													"default",
                    	      													lv_default_49_0,
                    	      													"org.integratedmodelling.kdl.Kdl.Value");
                    	      												afterParserOrEnumRuleCall();
                    	      											
                    	    }

                    	    }


                    	    }


                    	    }


                    	    }

                    	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16());

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:1568:5: ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) )
                    	    {
                    	    // InternalKdl.g:1568:5: ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) )
                    	    // InternalKdl.g:1569:6: {...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) )
                    	    {
                    	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1)");
                    	    }
                    	    // InternalKdl.g:1569:116: ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) )
                    	    // InternalKdl.g:1570:7: ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) )
                    	    {
                    	    getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1);
                    	    // InternalKdl.g:1573:10: ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) )
                    	    // InternalKdl.g:1573:11: {...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) )
                    	    {
                    	    if ( !((true)) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "true");
                    	    }
                    	    // InternalKdl.g:1573:20: (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) )
                    	    // InternalKdl.g:1573:21: otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) )
                    	    {
                    	    otherlv_50=(Token)match(input,58,FOLLOW_42); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      										newLeafNode(otherlv_50, grammarAccess.getActorDefinitionAccess().getUnitKeyword_1_16_1_0());
                    	      									
                    	    }
                    	    // InternalKdl.g:1577:10: ( (lv_unit_51_0= ruleUnit ) )
                    	    // InternalKdl.g:1578:11: (lv_unit_51_0= ruleUnit )
                    	    {
                    	    // InternalKdl.g:1578:11: (lv_unit_51_0= ruleUnit )
                    	    // InternalKdl.g:1579:12: lv_unit_51_0= ruleUnit
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      												newCompositeNode(grammarAccess.getActorDefinitionAccess().getUnitUnitParserRuleCall_1_16_1_1_0());
                    	      											
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_unit_51_0=ruleUnit();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      												if (current==null) {
                    	      													current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      												}
                    	      												set(
                    	      													current,
                    	      													"unit",
                    	      													lv_unit_51_0,
                    	      													"org.integratedmodelling.kdl.Kdl.Unit");
                    	      												afterParserOrEnumRuleCall();
                    	      											
                    	    }

                    	    }


                    	    }


                    	    }


                    	    }

                    	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16());

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

                    getUnorderedGroupHelper().leave(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16());

                    }

                    // InternalKdl.g:1609:4: (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==59) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // InternalKdl.g:1610:5: otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_52=(Token)match(input,59,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_52, grammarAccess.getActorDefinitionAccess().getAsKeyword_1_17_0());
                              				
                            }
                            // InternalKdl.g:1614:5: ( (lv_localName_53_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:1615:6: (lv_localName_53_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:1615:6: (lv_localName_53_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:1616:7: lv_localName_53_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_53_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_43); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_53_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_1_17_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_53_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1633:4: (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==60) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // InternalKdl.g:1634:5: otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )*
                            {
                            otherlv_54=(Token)match(input,60,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_54, grammarAccess.getActorDefinitionAccess().getOverKeyword_1_18_0());
                              				
                            }
                            // InternalKdl.g:1638:5: ( (lv_coverage_55_0= ruleFunction ) )
                            // InternalKdl.g:1639:6: (lv_coverage_55_0= ruleFunction )
                            {
                            // InternalKdl.g:1639:6: (lv_coverage_55_0= ruleFunction )
                            // InternalKdl.g:1640:7: lv_coverage_55_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_1_18_1_0());
                              						
                            }
                            pushFollow(FOLLOW_44);
                            lv_coverage_55_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_55_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1657:5: (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )*
                            loop40:
                            do {
                                int alt40=2;
                                int LA40_0 = input.LA(1);

                                if ( (LA40_0==31) ) {
                                    alt40=1;
                                }


                                switch (alt40) {
                            	case 1 :
                            	    // InternalKdl.g:1658:6: otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) )
                            	    {
                            	    otherlv_56=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_56, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_18_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1662:6: ( (lv_coverage_57_0= ruleFunction ) )
                            	    // InternalKdl.g:1663:7: (lv_coverage_57_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:1663:7: (lv_coverage_57_0= ruleFunction )
                            	    // InternalKdl.g:1664:8: lv_coverage_57_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_1_18_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_44);
                            	    lv_coverage_57_0=ruleFunction();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"coverage",
                            	      									lv_coverage_57_0,
                            	      									"org.integratedmodelling.kdl.Kdl.Function");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop40;
                                }
                            } while (true);


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

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleActorDefinition"


    // $ANTLR start "entryRuleDataflowBody"
    // InternalKdl.g:1691:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1695:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1696:2: iv_ruleDataflowBody= ruleDataflowBody EOF
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
    // InternalKdl.g:1705:1: ruleDataflowBody returns [EObject current=null] : ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
    public final EObject ruleDataflowBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        EObject lv_dataflows_1_0 = null;

        AntlrDatatypeRuleToken lv_geometry_4_0 = null;

        EObject lv_computations_5_0 = null;

        EObject lv_metadata_7_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1714:2: ( ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1715:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1715:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1716:3: () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1716:3: ()
            // InternalKdl.g:1717:4: 
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

            // InternalKdl.g:1726:3: ( (lv_dataflows_1_0= ruleActorDefinition ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==RULE_ANNOTATION_ID||(LA43_0>=35 && LA43_0<=37)||(LA43_0>=39 && LA43_0<=42)||LA43_0==45||LA43_0==54||(LA43_0>=65 && LA43_0<=84)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalKdl.g:1727:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1727:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    // InternalKdl.g:1728:5: lv_dataflows_1_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDataflowBodyAccess().getDataflowsActorDefinitionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_45);
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
            	    break loop43;
                }
            } while (true);

            // InternalKdl.g:1745:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1746:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1746:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1747:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());
            // InternalKdl.g:1750:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1751:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1751:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+
            int cnt47=0;
            loop47:
            do {
                int alt47=4;
                alt47 = dfa47.predict(input);
                switch (alt47) {
            	case 1 :
            	    // InternalKdl.g:1752:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1752:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1753:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKdl.g:1753:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1754:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
            	    // InternalKdl.g:1757:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1757:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1757:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1757:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
            	    {
            	    otherlv_3=(Token)match(input,61,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_3, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1761:9: ( (lv_geometry_4_0= ruleGeometry ) )
            	    // InternalKdl.g:1762:10: (lv_geometry_4_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1762:10: (lv_geometry_4_0= ruleGeometry )
            	    // InternalKdl.g:1763:11: lv_geometry_4_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_46);
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
            	    // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
            	    {
            	    // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
            	    // InternalKdl.g:1787:5: {...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKdl.g:1787:109: ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
            	    // InternalKdl.g:1788:6: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
            	    // InternalKdl.g:1791:9: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
            	    // InternalKdl.g:1791:10: {...}? => ( (lv_computations_5_0= ruleComputation ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1791:19: ( (lv_computations_5_0= ruleComputation ) )
            	    // InternalKdl.g:1791:20: (lv_computations_5_0= ruleComputation )
            	    {
            	    // InternalKdl.g:1791:20: (lv_computations_5_0= ruleComputation )
            	    // InternalKdl.g:1792:10: lv_computations_5_0= ruleComputation
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_2_1_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_46);
            	    lv_computations_5_0=ruleComputation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      										}
            	      										set(
            	      											current,
            	      											"computations",
            	      											lv_computations_5_0,
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
            	case 3 :
            	    // InternalKdl.g:1814:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1814:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
            	    // InternalKdl.g:1815:5: {...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKdl.g:1815:109: ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
            	    // InternalKdl.g:1816:6: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
            	    // InternalKdl.g:1819:9: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
            	    // InternalKdl.g:1819:10: {...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1819:19: ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
            	    // InternalKdl.g:1819:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
            	    {
            	    // InternalKdl.g:1819:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )?
            	    int alt44=2;
            	    int LA44_0 = input.LA(1);

            	    if ( (LA44_0==62) ) {
            	        int LA44_1 = input.LA(2);

            	        if ( (synpred70_InternalKdl()) ) {
            	            alt44=1;
            	        }
            	    }
            	    switch (alt44) {
            	        case 1 :
            	            // InternalKdl.g:1820:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
            	            {
            	            otherlv_6=(Token)match(input,62,FOLLOW_47); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_6, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_2_2_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1824:10: ( (lv_metadata_7_0= ruleMetadata ) )
            	            // InternalKdl.g:1825:11: (lv_metadata_7_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1825:11: (lv_metadata_7_0= ruleMetadata )
            	            // InternalKdl.g:1826:12: lv_metadata_7_0= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_2_0_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_46);
            	            lv_metadata_7_0=ruleMetadata();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	              												}
            	              												set(
            	              													current,
            	              													"metadata",
            	              													lv_metadata_7_0,
            	              													"org.integratedmodelling.kdl.Kdl.Metadata");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }

            	    // InternalKdl.g:1844:9: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
            	    int alt46=2;
            	    int LA46_0 = input.LA(1);

            	    if ( (LA46_0==63) ) {
            	        int LA46_1 = input.LA(2);

            	        if ( (synpred72_InternalKdl()) ) {
            	            alt46=1;
            	        }
            	    }
            	    switch (alt46) {
            	        case 1 :
            	            // InternalKdl.g:1845:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
            	            {
            	            otherlv_8=(Token)match(input,63,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_8, grammarAccess.getDataflowBodyAccess().getClassKeyword_2_2_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1849:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
            	            // InternalKdl.g:1850:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
            	            {
            	            // InternalKdl.g:1850:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
            	            // InternalKdl.g:1851:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
            	            {
            	            // InternalKdl.g:1851:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
            	            int alt45=2;
            	            int LA45_0 = input.LA(1);

            	            if ( (LA45_0==RULE_LOWERCASE_ID) ) {
            	                alt45=1;
            	            }
            	            else if ( (LA45_0==RULE_STRING) ) {
            	                alt45=2;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return current;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 45, 0, input);

            	                throw nvae;
            	            }
            	            switch (alt45) {
            	                case 1 :
            	                    // InternalKdl.g:1852:13: lv_javaClass_9_1= ruleJavaClass
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      													newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_2_2_1_1_0_0());
            	                      												
            	                    }
            	                    pushFollow(FOLLOW_46);
            	                    lv_javaClass_9_1=ruleJavaClass();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      													if (current==null) {
            	                      														current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	                      													}
            	                      													set(
            	                      														current,
            	                      														"javaClass",
            	                      														lv_javaClass_9_1,
            	                      														"org.integratedmodelling.kdl.Kdl.JavaClass");
            	                      													afterParserOrEnumRuleCall();
            	                      												
            	                    }

            	                    }
            	                    break;
            	                case 2 :
            	                    // InternalKdl.g:1868:13: lv_javaClass_9_2= RULE_STRING
            	                    {
            	                    lv_javaClass_9_2=(Token)match(input,RULE_STRING,FOLLOW_46); if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      													newLeafNode(lv_javaClass_9_2, grammarAccess.getDataflowBodyAccess().getJavaClassSTRINGTerminalRuleCall_2_2_1_1_0_1());
            	                      												
            	                    }
            	                    if ( state.backtracking==0 ) {

            	                      													if (current==null) {
            	                      														current = createModelElement(grammarAccess.getDataflowBodyRule());
            	                      													}
            	                      													setWithLastConsumed(
            	                      														current,
            	                      														"javaClass",
            	                      														lv_javaClass_9_2,
            	                      														"org.eclipse.xtext.common.Terminals.STRING");
            	                      												
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


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt47 >= 1 ) break loop47;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(47, input);
                        throw eee;
                }
                cnt47++;
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
    // InternalKdl.g:1907:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1907:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1908:2: iv_ruleComputation= ruleComputation EOF
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
    // InternalKdl.g:1914:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1920:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1921:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1921:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1922:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,64,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1926:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1927:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1927:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1928:5: lv_functions_1_0= ruleFunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_44);
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

            // InternalKdl.g:1945:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==31) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalKdl.g:1946:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1950:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1951:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1951:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1952:6: lv_functions_3_0= ruleFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop48;
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
    // InternalKdl.g:1974:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1974:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1975:2: iv_ruleGeometry= ruleGeometry EOF
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
    // InternalKdl.g:1981:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1987:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1988:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1988:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==47) ) {
                alt50=1;
            }
            else if ( (LA50_0==RULE_SHAPE) ) {
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
                    // InternalKdl.g:1989:3: kw= '*'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1995:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1995:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1996:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_44); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:2003:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop49:
                    do {
                        int alt49=2;
                        int LA49_0 = input.LA(1);

                        if ( (LA49_0==31) ) {
                            alt49=1;
                        }


                        switch (alt49) {
                    	case 1 :
                    	    // InternalKdl.g:2004:5: kw= ',' this_SHAPE_3= RULE_SHAPE
                    	    {
                    	    kw=(Token)match(input,31,FOLLOW_48); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getGeometryAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    this_SHAPE_3=(Token)match(input,RULE_SHAPE,FOLLOW_44); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_SHAPE_3);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_SHAPE_3, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop49;
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
    // InternalKdl.g:2022:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:2022:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:2023:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalKdl.g:2029:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2035:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:2036:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:2036:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:2037:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:2037:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:2038:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:2038:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:2039:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_41); if (state.failed) return current;
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

            // InternalKdl.g:2055:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:2056:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:2056:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:2057:5: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_49);
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

            // InternalKdl.g:2074:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_STRING) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalKdl.g:2075:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:2075:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:2076:5: lv_docstring_2_0= RULE_STRING
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


    // $ANTLR start "entryRuleUnitElement"
    // InternalKdl.g:2096:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:2096:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:2097:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKdl.g:2103:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
    public final EObject ruleUnitElement() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_1=null;
        Token lv_id_0_2=null;
        Token lv_id_0_3=null;
        Token lv_id_0_4=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_unit_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2109:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKdl.g:2110:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKdl.g:2110:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_LOWERCASE_ID||(LA53_0>=RULE_UPPERCASE_ID && LA53_0<=RULE_CAMELCASE_ID)||LA53_0==RULE_BACKCASE_ID) ) {
                alt53=1;
            }
            else if ( (LA53_0==33) ) {
                alt53=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // InternalKdl.g:2111:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) )
                    {
                    // InternalKdl.g:2111:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) )
                    // InternalKdl.g:2112:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) )
                    {
                    // InternalKdl.g:2112:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) )
                    // InternalKdl.g:2113:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID )
                    {
                    // InternalKdl.g:2113:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID )
                    int alt52=4;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt52=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt52=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt52=3;
                        }
                        break;
                    case RULE_BACKCASE_ID:
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
                            // InternalKdl.g:2114:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:2129:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                        case 3 :
                            // InternalKdl.g:2144:6: lv_id_0_3= RULE_UPPERCASE_ID
                            {
                            lv_id_0_3=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_3, grammarAccess.getUnitElementAccess().getIdUPPERCASE_IDTerminalRuleCall_0_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_3,
                              							"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                              					
                            }

                            }
                            break;
                        case 4 :
                            // InternalKdl.g:2159:6: lv_id_0_4= RULE_BACKCASE_ID
                            {
                            lv_id_0_4=(Token)match(input,RULE_BACKCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_4, grammarAccess.getUnitElementAccess().getIdBACKCASE_IDTerminalRuleCall_0_0_3());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_4,
                              							"org.integratedmodelling.kdl.Kdl.BACKCASE_ID");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:2177:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKdl.g:2177:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKdl.g:2178:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:2182:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKdl.g:2183:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKdl.g:2183:5: (lv_unit_2_0= ruleUnit )
                    // InternalKdl.g:2184:6: lv_unit_2_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_16);
                    lv_unit_2_0=ruleUnit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getUnitElementRule());
                      						}
                      						set(
                      							current,
                      							"unit",
                      							lv_unit_2_0,
                      							"org.integratedmodelling.kdl.Kdl.Unit");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getUnitElementAccess().getRightParenthesisKeyword_1_2());
                      			
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


    // $ANTLR start "entryRuleUnit"
    // InternalKdl.g:2210:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:2210:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:2211:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKdl.g:2217:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2223:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:2224:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:2224:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:2225:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:2225:3: ()
            // InternalKdl.g:2226:4: 
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

            // InternalKdl.g:2235:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_LOWERCASE_ID||(LA54_0>=RULE_UPPERCASE_ID && LA54_0<=RULE_CAMELCASE_ID)||LA54_0==RULE_BACKCASE_ID||LA54_0==33) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalKdl.g:2236:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:2236:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:2237:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_51);
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

            // InternalKdl.g:2254:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==47||LA55_0==102||LA55_0==116) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalKdl.g:2255:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:2255:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:2256:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:2262:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:2263:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:2263:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:2264:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_52);
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

            	    // InternalKdl.g:2282:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:2283:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:2283:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:2284:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
            	    break loop55;
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


    // $ANTLR start "entryRuleACTOR"
    // InternalKdl.g:2306:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:2306:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:2307:2: iv_ruleACTOR= ruleACTOR EOF
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
    // InternalKdl.g:2313:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2319:2: ( (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' ) )
            // InternalKdl.g:2320:2: (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' )
            {
            // InternalKdl.g:2320:2: (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' )
            int alt56=21;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt56=1;
                }
                break;
            case 66:
                {
                alt56=2;
                }
                break;
            case 67:
                {
                alt56=3;
                }
                break;
            case 68:
                {
                alt56=4;
                }
                break;
            case 69:
                {
                alt56=5;
                }
                break;
            case 70:
                {
                alt56=6;
                }
                break;
            case 71:
                {
                alt56=7;
                }
                break;
            case 72:
                {
                alt56=8;
                }
                break;
            case 73:
                {
                alt56=9;
                }
                break;
            case 74:
                {
                alt56=10;
                }
                break;
            case 75:
                {
                alt56=11;
                }
                break;
            case 76:
                {
                alt56=12;
                }
                break;
            case 77:
                {
                alt56=13;
                }
                break;
            case 78:
                {
                alt56=14;
                }
                break;
            case 79:
                {
                alt56=15;
                }
                break;
            case 80:
                {
                alt56=16;
                }
                break;
            case 81:
                {
                alt56=17;
                }
                break;
            case 54:
                {
                alt56=18;
                }
                break;
            case 82:
                {
                alt56=19;
                }
                break;
            case 83:
                {
                alt56=20;
                }
                break;
            case 84:
                {
                alt56=21;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }

            switch (alt56) {
                case 1 :
                    // InternalKdl.g:2321:3: kw= 'object'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2327:3: kw= 'event'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getEventKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2333:3: kw= 'observation'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObservationKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2339:3: kw= 'value'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2345:3: kw= 'process'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:2351:3: kw= 'number'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:2357:3: kw= 'concept'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:2363:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:2369:3: kw= 'text'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:2375:3: kw= 'list'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getListKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:2381:3: kw= 'table'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTableKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:2387:3: kw= 'map'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getMapKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:2393:3: kw= 'extent'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:2399:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalKdl.g:2405:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    // InternalKdl.g:2411:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    // InternalKdl.g:2417:3: kw= 'enum'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getEnumKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    // InternalKdl.g:2423:3: kw= 'range'
                    {
                    kw=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getRangeKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    // InternalKdl.g:2429:3: kw= 'void'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    // InternalKdl.g:2435:3: kw= 'partition'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getPartitionKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    // InternalKdl.g:2441:3: kw= 'resolve'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getResolveKeyword_20());
                      		
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
    // InternalKdl.g:2450:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:2450:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:2451:2: iv_ruleTARGET= ruleTARGET EOF
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
    // InternalKdl.g:2457:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2463:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' ) )
            // InternalKdl.g:2464:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' )
            {
            // InternalKdl.g:2464:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' )
            int alt57=5;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt57=1;
                }
                break;
            case 86:
                {
                alt57=2;
                }
                break;
            case 87:
                {
                alt57=3;
                }
                break;
            case 88:
                {
                alt57=4;
                }
                break;
            case 89:
                {
                alt57=5;
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
                    // InternalKdl.g:2465:3: kw= 'models'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2471:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2477:3: kw= 'observers'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getObserversKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2483:3: kw= 'definitions'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getDefinitionsKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2489:3: kw= 'dependencies'
                    {
                    kw=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getDependenciesKeyword_4());
                      		
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
    // InternalKdl.g:2498:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:2498:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:2499:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
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
    // InternalKdl.g:2505:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
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
            // InternalKdl.g:2511:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:2512:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:2512:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt62=10;
            alt62 = dfa62.predict(input);
            switch (alt62) {
                case 1 :
                    // InternalKdl.g:2513:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:2513:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==90) ) {
                        alt58=1;
                    }
                    else if ( (LA58_0==91) ) {
                        alt58=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 58, 0, input);

                        throw nvae;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalKdl.g:2514:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:2514:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:2515:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:2515:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:2516:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2529:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:2529:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:2530:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:2530:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:2531:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2545:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:2545:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:2546:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:2546:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:2547:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:2547:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:2548:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
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

                    // InternalKdl.g:2565:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt59=3;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==92) ) {
                        alt59=1;
                    }
                    else if ( (LA59_0==93) ) {
                        alt59=2;
                    }
                    switch (alt59) {
                        case 1 :
                            // InternalKdl.g:2566:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2566:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:2567:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:2567:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:2568:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,92,FOLLOW_38); if (state.failed) return current;
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
                            // InternalKdl.g:2581:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,93,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:2586:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:2587:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:2593:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:2594:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:2598:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:2599:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_54);
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

                    // InternalKdl.g:2616:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt60=3;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==92) ) {
                        alt60=1;
                    }
                    else if ( (LA60_0==93) ) {
                        alt60=2;
                    }
                    switch (alt60) {
                        case 1 :
                            // InternalKdl.g:2617:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2617:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:2618:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:2618:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:2619:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2632:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,93,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2639:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2639:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:2640:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:2640:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:2641:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:2659:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:2659:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:2660:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,94,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:2664:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:2665:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:2665:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:2666:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:2685:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2685:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:2686:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:2686:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:2687:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:2704:3: ( (lv_map_13_0= ruleMap ) )
                    {
                    // InternalKdl.g:2704:3: ( (lv_map_13_0= ruleMap ) )
                    // InternalKdl.g:2705:4: (lv_map_13_0= ruleMap )
                    {
                    // InternalKdl.g:2705:4: (lv_map_13_0= ruleMap )
                    // InternalKdl.g:2706:5: lv_map_13_0= ruleMap
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
                    // InternalKdl.g:2724:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:2724:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:2725:4: otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,33,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:2729:4: ( (lv_toResolve_15_0= RULE_STRING ) )
                    // InternalKdl.g:2730:5: (lv_toResolve_15_0= RULE_STRING )
                    {
                    // InternalKdl.g:2730:5: (lv_toResolve_15_0= RULE_STRING )
                    // InternalKdl.g:2731:6: lv_toResolve_15_0= RULE_STRING
                    {
                    lv_toResolve_15_0=(Token)match(input,RULE_STRING,FOLLOW_56); if (state.failed) return current;
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

                    // InternalKdl.g:2747:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==31) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // InternalKdl.g:2748:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    {
                    	    // InternalKdl.g:2748:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:2749:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,31,FOLLOW_6); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:2755:5: ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    // InternalKdl.g:2756:6: (lv_toResolve_17_0= RULE_STRING )
                    	    {
                    	    // InternalKdl.g:2756:6: (lv_toResolve_17_0= RULE_STRING )
                    	    // InternalKdl.g:2757:7: lv_toResolve_17_0= RULE_STRING
                    	    {
                    	    lv_toResolve_17_0=(Token)match(input,RULE_STRING,FOLLOW_56); if (state.failed) return current;
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
                    	    break loop61;
                        }
                    } while (true);

                    otherlv_18=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getClassifierRHSAccess().getRightParenthesisKeyword_6_3());
                      			
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:2780:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2780:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:2781:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2781:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:2782:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:2782:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:2783:6: lv_op_19_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    // InternalKdl.g:2800:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:2801:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:2801:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:2802:6: lv_expression_20_0= ruleNumber
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
                    // InternalKdl.g:2821:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:2821:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:2822:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:2822:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:2823:5: lv_nodata_21_0= 'unknown'
                    {
                    lv_nodata_21_0=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2836:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:2836:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:2837:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:2837:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:2838:5: lv_star_22_0= '*'
                    {
                    lv_star_22_0=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_22_0, grammarAccess.getClassifierRHSAccess().getStarAsteriskKeyword_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRHSRule());
                      					}
                      					setWithLastConsumed(current, "star", lv_star_22_0 != null, "*");
                      				
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
    // InternalKdl.g:2854:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:2854:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:2855:2: iv_ruleList= ruleList EOF
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
    // InternalKdl.g:2861:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2867:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:2868:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:2868:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:2869:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:2869:3: ()
            // InternalKdl.g:2870:4: 
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

            otherlv_1=(Token)match(input,33,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKdl.g:2883:3: ( (lv_contents_2_0= ruleValue ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( ((LA63_0>=RULE_STRING && LA63_0<=RULE_LOWERCASE_ID)||(LA63_0>=RULE_INT && LA63_0<=RULE_CAMELCASE_ID)||(LA63_0>=RULE_ID && LA63_0<=RULE_EXPR)||LA63_0==31||LA63_0==33||LA63_0==44||LA63_0==50||(LA63_0>=90 && LA63_0<=91)||LA63_0==96||LA63_0==99||LA63_0==113) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalKdl.g:2884:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:2884:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:2885:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
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
            	    break loop63;
                }
            } while (true);

            otherlv_3=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2910:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:2910:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:2911:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKdl.g:2917:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
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
            // InternalKdl.g:2923:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:2924:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:2924:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt65=4;
            alt65 = dfa65.predict(input);
            switch (alt65) {
                case 1 :
                    // InternalKdl.g:2925:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2925:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2926:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2926:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2927:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:2945:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2945:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:2946:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2946:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:2947:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:2947:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:2948:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
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

                    otherlv_2=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:2969:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:2970:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2970:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:2971:6: lv_to_3_0= ruleNumber
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
                    // InternalKdl.g:2990:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2990:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2991:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2991:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2992:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3009:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3009:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3010:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3010:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3011:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3011:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt64=2;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==90) ) {
                        alt64=1;
                    }
                    else if ( (LA64_0==91) ) {
                        alt64=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 64, 0, input);

                        throw nvae;
                    }
                    switch (alt64) {
                        case 1 :
                            // InternalKdl.g:3012:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3023:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3040:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:3040:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:3041:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
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
    // InternalKdl.g:3047:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
    public final EObject ruleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_string_4_0=null;
        Token lv_boolean_5_1=null;
        Token lv_boolean_5_2=null;
        Token lv_id_6_1=null;
        Token lv_id_6_2=null;
        Token lv_id_6_3=null;
        Token lv_comma_7_0=null;
        EObject lv_from_0_0 = null;

        EObject lv_to_2_0 = null;

        EObject lv_number_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3053:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:3054:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:3054:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt68=6;
            alt68 = dfa68.predict(input);
            switch (alt68) {
                case 1 :
                    // InternalKdl.g:3055:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3055:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:3056:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3056:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:3057:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3057:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:3058:6: lv_from_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getFromNumberParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
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

                    // InternalKdl.g:3075:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:3076:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:3082:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:3083:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3087:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:3088:6: lv_to_2_0= ruleNumber
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
                    // InternalKdl.g:3107:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3107:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:3108:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3108:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:3109:5: lv_number_3_0= ruleNumber
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
                    // InternalKdl.g:3127:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3127:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3128:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3128:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3129:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3146:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3146:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3147:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3147:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3148:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3148:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==90) ) {
                        alt66=1;
                    }
                    else if ( (LA66_0==91) ) {
                        alt66=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 66, 0, input);

                        throw nvae;
                    }
                    switch (alt66) {
                        case 1 :
                            // InternalKdl.g:3149:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3160:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3174:3: ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKdl.g:3174:3: ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKdl.g:3175:4: ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3175:4: ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3176:5: (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3176:5: (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID )
                    int alt67=3;
                    switch ( input.LA(1) ) {
                    case RULE_ID:
                        {
                        alt67=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt67=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt67=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 67, 0, input);

                        throw nvae;
                    }

                    switch (alt67) {
                        case 1 :
                            // InternalKdl.g:3177:6: lv_id_6_1= RULE_ID
                            {
                            lv_id_6_1=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_6_1, grammarAccess.getLiteralOrIdOrCommaAccess().getIdIDTerminalRuleCall_4_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_6_1,
                              							"org.eclipse.xtext.common.Terminals.ID");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:3192:6: lv_id_6_2= RULE_LOWERCASE_ID
                            {
                            lv_id_6_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_6_2, grammarAccess.getLiteralOrIdOrCommaAccess().getIdLOWERCASE_IDTerminalRuleCall_4_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_6_2,
                              							"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:3207:6: lv_id_6_3= RULE_UPPERCASE_ID
                            {
                            lv_id_6_3=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_6_3, grammarAccess.getLiteralOrIdOrCommaAccess().getIdUPPERCASE_IDTerminalRuleCall_4_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_6_3,
                              							"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:3225:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:3225:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:3226:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:3226:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:3227:5: lv_comma_7_0= ','
                    {
                    lv_comma_7_0=(Token)match(input,31,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_comma_7_0, grammarAccess.getLiteralOrIdOrCommaAccess().getCommaCommaKeyword_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralOrIdOrCommaRule());
                      					}
                      					setWithLastConsumed(current, "comma", lv_comma_7_0 != null, ",");
                      				
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
    // InternalKdl.g:3243:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:3243:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:3244:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
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
    // InternalKdl.g:3250:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3256:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:3257:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:3257:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt70=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 44:
            case 113:
                {
                alt70=1;
                }
                break;
            case RULE_STRING:
                {
                alt70=2;
                }
                break;
            case 90:
            case 91:
                {
                alt70=3;
                }
                break;
            case RULE_ID:
                {
                alt70=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // InternalKdl.g:3258:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3258:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:3259:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3259:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:3260:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:3278:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3278:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:3279:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:3279:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:3280:5: lv_string_1_0= RULE_STRING
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
                    // InternalKdl.g:3297:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3297:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:3298:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:3298:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:3299:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:3299:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==90) ) {
                        alt69=1;
                    }
                    else if ( (LA69_0==91) ) {
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
                            // InternalKdl.g:3300:6: lv_boolean_2_1= 'true'
                            {
                            lv_boolean_2_1=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3311:6: lv_boolean_2_2= 'false'
                            {
                            lv_boolean_2_2=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3325:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:3325:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:3326:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:3326:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:3327:5: lv_id_3_0= RULE_ID
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
    // InternalKdl.g:3347:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:3347:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:3348:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKdl.g:3354:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
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
            // InternalKdl.g:3360:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:3361:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:3361:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:3362:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:3362:3: ()
            // InternalKdl.g:3363:4: 
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

            otherlv_1=(Token)match(input,50,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMetadataAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3376:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==RULE_LOWERCASE_ID) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // InternalKdl.g:3377:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:3377:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:3378:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:3378:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:3379:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:3379:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt71=2;
            	    int LA71_0 = input.LA(1);

            	    if ( (LA71_0==RULE_LOWERCASE_ID) ) {
            	        int LA71_1 = input.LA(2);

            	        if ( (LA71_1==97||LA71_1==103) ) {
            	            alt71=2;
            	        }
            	        else if ( (LA71_1==RULE_STRING||LA71_1==RULE_INT||LA71_1==RULE_ID||LA71_1==33||LA71_1==44||LA71_1==50||(LA71_1>=90 && LA71_1<=91)||LA71_1==113) ) {
            	            alt71=1;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 71, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 71, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt71) {
            	        case 1 :
            	            // InternalKdl.g:3380:7: lv_ids_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_ids_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_58); if (state.failed) return current;
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
            	            // InternalKdl.g:3395:7: lv_ids_2_2= rulePropertyId
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getIdsPropertyIdParserRuleCall_2_0_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_58);
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

            	    // InternalKdl.g:3413:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:3414:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:3414:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:3415:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:3415:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt72=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 44:
            	    case 90:
            	    case 91:
            	    case 113:
            	        {
            	        alt72=1;
            	        }
            	        break;
            	    case 50:
            	        {
            	        alt72=2;
            	        }
            	        break;
            	    case 33:
            	        {
            	        alt72=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 72, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt72) {
            	        case 1 :
            	            // InternalKdl.g:3416:7: lv_values_3_1= ruleLiteralOrID
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesLiteralOrIDParserRuleCall_2_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_57);
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
            	            // InternalKdl.g:3432:7: lv_values_3_2= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesMetadataParserRuleCall_2_1_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_57);
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
            	            // InternalKdl.g:3448:7: lv_values_3_3= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesListParserRuleCall_2_1_0_2());
            	              						
            	            }
            	            pushFollow(FOLLOW_57);
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
            	    break loop73;
                }
            } while (true);

            otherlv_4=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3475:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:3475:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:3476:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKdl.g:3482:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
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
            // InternalKdl.g:3488:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:3489:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:3489:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt76=2;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INT:
            case RULE_UPPERCASE_ID:
            case RULE_CAMELCASE_ID:
            case RULE_ID:
            case RULE_EXPR:
            case 31:
            case 33:
            case 44:
            case 50:
            case 90:
            case 91:
            case 96:
            case 99:
            case 113:
                {
                alt76=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                int LA76_2 = input.LA(2);

                if ( ((LA76_2>=104 && LA76_2<=105)) ) {
                    alt76=2;
                }
                else if ( (LA76_2==EOF||LA76_2==31||(LA76_2>=33 && LA76_2<=34)||(LA76_2>=97 && LA76_2<=98)||(LA76_2>=102 && LA76_2<=103)||(LA76_2>=106 && LA76_2<=107)) ) {
                    alt76=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 76, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                int LA76_3 = input.LA(2);

                if ( (LA76_3==EOF||LA76_3==31||LA76_3==34||LA76_3==98||(LA76_3>=102 && LA76_3<=103)||LA76_3==106) ) {
                    alt76=1;
                }
                else if ( ((LA76_3>=104 && LA76_3<=105)) ) {
                    alt76=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 76, 3, input);

                    throw nvae;
                }
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
                    // InternalKdl.g:3490:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:3490:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:3491:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:3491:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:3492:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:3492:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:3493:6: lv_values_0_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKdl.g:3510:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==31) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // InternalKdl.g:3511:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,31,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:3515:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:3516:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:3516:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:3517:7: lv_values_2_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_44);
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
                    	    break loop74;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:3537:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:3537:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:3538:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:3538:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:3539:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:3539:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:3540:6: lv_pairs_3_0= ruleKeyValuePair
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKdl.g:3557:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==31) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // InternalKdl.g:3558:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:3558:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:3559:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,31,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3565:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:3566:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:3566:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:3567:7: lv_pairs_5_0= ruleKeyValuePair
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_44);
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
                    	    break loop75;
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
    // InternalKdl.g:3590:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:3590:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:3591:2: iv_ruleValue= ruleValue EOF
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
    // InternalKdl.g:3597:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) ;
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
            // InternalKdl.g:3603:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:3604:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:3604:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            int alt77=8;
            alt77 = dfa77.predict(input);
            switch (alt77) {
                case 1 :
                    // InternalKdl.g:3605:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:3605:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:3606:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:3606:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:3607:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
                    // InternalKdl.g:3625:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:3625:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:3626:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:3626:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:3627:5: lv_function_1_0= ruleFunction
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
                    // InternalKdl.g:3645:3: ( (lv_urn_2_0= ruleUrn ) )
                    {
                    // InternalKdl.g:3645:3: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:3646:4: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:3646:4: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:3647:5: lv_urn_2_0= ruleUrn
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
                    // InternalKdl.g:3665:3: ( (lv_list_3_0= ruleList ) )
                    {
                    // InternalKdl.g:3665:3: ( (lv_list_3_0= ruleList ) )
                    // InternalKdl.g:3666:4: (lv_list_3_0= ruleList )
                    {
                    // InternalKdl.g:3666:4: (lv_list_3_0= ruleList )
                    // InternalKdl.g:3667:5: lv_list_3_0= ruleList
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
                    // InternalKdl.g:3685:3: ( (lv_map_4_0= ruleMap ) )
                    {
                    // InternalKdl.g:3685:3: ( (lv_map_4_0= ruleMap ) )
                    // InternalKdl.g:3686:4: (lv_map_4_0= ruleMap )
                    {
                    // InternalKdl.g:3686:4: (lv_map_4_0= ruleMap )
                    // InternalKdl.g:3687:5: lv_map_4_0= ruleMap
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
                    // InternalKdl.g:3705:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3705:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    // InternalKdl.g:3706:4: (lv_expression_5_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3706:4: (lv_expression_5_0= RULE_EXPR )
                    // InternalKdl.g:3707:5: lv_expression_5_0= RULE_EXPR
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
                    // InternalKdl.g:3724:3: ( (lv_table_6_0= ruleLookupTable ) )
                    {
                    // InternalKdl.g:3724:3: ( (lv_table_6_0= ruleLookupTable ) )
                    // InternalKdl.g:3725:4: (lv_table_6_0= ruleLookupTable )
                    {
                    // InternalKdl.g:3725:4: (lv_table_6_0= ruleLookupTable )
                    // InternalKdl.g:3726:5: lv_table_6_0= ruleLookupTable
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
                    // InternalKdl.g:3744:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3744:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3745:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3745:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:3746:5: lv_enumId_7_0= RULE_UPPERCASE_ID
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
    // InternalKdl.g:3766:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:3766:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:3767:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKdl.g:3773:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:3779:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:3780:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:3780:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:3781:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:3781:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:3782:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:3782:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt78=3;
            switch ( input.LA(1) ) {
            case 96:
                {
                alt78=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case RULE_STRING:
                case RULE_LOWERCASE_ID:
                case RULE_ANNOTATION_ID:
                case RULE_INT:
                case RULE_LOWERCASE_DASHID:
                case RULE_UPPERCASE_ID:
                case RULE_CAMELCASE_ID:
                case RULE_ID:
                case RULE_EXPR:
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
                case 41:
                case 42:
                case 44:
                case 45:
                case 50:
                case 51:
                case 54:
                case 57:
                case 58:
                case 59:
                case 60:
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
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 90:
                case 91:
                case 96:
                case 98:
                case 99:
                case 102:
                case 106:
                case 113:
                    {
                    alt78=3;
                    }
                    break;
                case 103:
                    {
                    int LA78_5 = input.LA(3);

                    if ( (LA78_5==RULE_LOWERCASE_ID) ) {
                        int LA78_6 = input.LA(4);

                        if ( (LA78_6==EOF||(LA78_6>=RULE_STRING && LA78_6<=RULE_CAMELCASE_ID)||(LA78_6>=RULE_ID && LA78_6<=RULE_EXPR)||(LA78_6>=20 && LA78_6<=37)||(LA78_6>=39 && LA78_6<=42)||(LA78_6>=44 && LA78_6<=45)||(LA78_6>=50 && LA78_6<=51)||LA78_6==54||(LA78_6>=57 && LA78_6<=84)||(LA78_6>=90 && LA78_6<=91)||LA78_6==96||(LA78_6>=98 && LA78_6<=99)||LA78_6==106||LA78_6==113) ) {
                            alt78=3;
                        }
                        else if ( (LA78_6==97||LA78_6==103) ) {
                            alt78=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 78, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 78, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 97:
                    {
                    alt78=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 78, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt78=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
                {
                alt78=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // InternalKdl.g:3783:5: lv_name_0_1= ruleUrnId
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
                    // InternalKdl.g:3799:5: lv_name_0_2= RULE_STRING
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
                    // InternalKdl.g:3814:5: lv_name_0_3= ruleLocalFilePath
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
    // InternalKdl.g:3835:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:3835:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:3836:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKdl.g:3842:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:3848:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:3849:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:3849:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:3850:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:3850:3: (kw= 'urn:klab:' )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==96) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKdl.g:3851:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,96,FOLLOW_3); if (state.failed) return current;
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
            pushFollow(FOLLOW_59);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,97,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_59);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,97,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_59);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,97,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_60);
            this_Path_7=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_Path_7);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalKdl.g:3912:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==97) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalKdl.g:3913:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
                    {
                    kw=(Token)match(input,97,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_61);
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

            // InternalKdl.g:3929:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==98) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalKdl.g:3930:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,98,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:3947:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKdl.g:3947:44: (iv_ruleMap= ruleMap EOF )
            // InternalKdl.g:3948:2: iv_ruleMap= ruleMap EOF
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
    // InternalKdl.g:3954:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3960:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKdl.g:3961:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKdl.g:3961:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKdl.g:3962:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKdl.g:3962:3: ()
            // InternalKdl.g:3963:4: 
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

            otherlv_1=(Token)match(input,50,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3976:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==RULE_STRING||LA83_0==RULE_INT||LA83_0==33||LA83_0==44||LA83_0==47||LA83_0==50||(LA83_0>=90 && LA83_0<=91)||(LA83_0>=94 && LA83_0<=95)||LA83_0==105||(LA83_0>=108 && LA83_0<=113)) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalKdl.g:3977:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKdl.g:3977:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKdl.g:3978:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKdl.g:3978:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKdl.g:3979:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
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

                    // InternalKdl.g:3996:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop82:
                    do {
                        int alt82=2;
                        int LA82_0 = input.LA(1);

                        if ( (LA82_0==31) ) {
                            alt82=1;
                        }


                        switch (alt82) {
                    	case 1 :
                    	    // InternalKdl.g:3997:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKdl.g:3997:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKdl.g:3998:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_64); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:4005:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKdl.g:4006:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKdl.g:4006:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKdl.g:4007:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_63);
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
                    	    break loop82;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4034:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKdl.g:4034:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKdl.g:4035:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKdl.g:4041:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4047:2: ( ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKdl.g:4048:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKdl.g:4048:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKdl.g:4049:3: ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKdl.g:4049:3: ( (lv_classifier_0_0= ruleClassifierRHS ) )
            // InternalKdl.g:4050:4: (lv_classifier_0_0= ruleClassifierRHS )
            {
            // InternalKdl.g:4050:4: (lv_classifier_0_0= ruleClassifierRHS )
            // InternalKdl.g:4051:5: lv_classifier_0_0= ruleClassifierRHS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierRHSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_59);
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

            otherlv_1=(Token)match(input,97,FOLLOW_41); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:4072:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKdl.g:4073:4: (lv_value_2_0= ruleValue )
            {
            // InternalKdl.g:4073:4: (lv_value_2_0= ruleValue )
            // InternalKdl.g:4074:5: lv_value_2_0= ruleValue
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
    // InternalKdl.g:4095:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKdl.g:4095:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKdl.g:4096:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKdl.g:4102:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4108:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKdl.g:4109:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKdl.g:4109:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKdl.g:4110:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKdl.g:4110:3: ()
            // InternalKdl.g:4111:4: 
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

            otherlv_1=(Token)match(input,99,FOLLOW_65); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:4124:3: ( (lv_table_2_0= ruleTable ) )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==RULE_STRING||LA84_0==RULE_INT||LA84_0==RULE_EXPR||LA84_0==44||LA84_0==47||(LA84_0>=90 && LA84_0<=91)||(LA84_0>=94 && LA84_0<=95)||LA84_0==98||LA84_0==105||(LA84_0>=108 && LA84_0<=113)) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalKdl.g:4125:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKdl.g:4125:4: (lv_table_2_0= ruleTable )
                    // InternalKdl.g:4126:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_66);
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

            otherlv_3=(Token)match(input,100,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4151:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKdl.g:4151:46: (iv_ruleTable= ruleTable EOF )
            // InternalKdl.g:4152:2: iv_ruleTable= ruleTable EOF
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
    // InternalKdl.g:4158:1: ruleTable returns [EObject current=null] : ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_rows_0_0 = null;

        EObject lv_rows_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4164:2: ( ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) )
            // InternalKdl.g:4165:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            {
            // InternalKdl.g:4165:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            // InternalKdl.g:4166:3: ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            {
            // InternalKdl.g:4166:3: ( (lv_rows_0_0= ruleTableRow ) )
            // InternalKdl.g:4167:4: (lv_rows_0_0= ruleTableRow )
            {
            // InternalKdl.g:4167:4: (lv_rows_0_0= ruleTableRow )
            // InternalKdl.g:4168:5: lv_rows_0_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_44);
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

            // InternalKdl.g:4185:3: (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==31) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // InternalKdl.g:4186:4: otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) )
            	    {
            	    otherlv_1=(Token)match(input,31,FOLLOW_67); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4190:4: ( (lv_rows_2_0= ruleTableRow ) )
            	    // InternalKdl.g:4191:5: (lv_rows_2_0= ruleTableRow )
            	    {
            	    // InternalKdl.g:4191:5: (lv_rows_2_0= ruleTableRow )
            	    // InternalKdl.g:4192:6: lv_rows_2_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop85;
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
    // InternalKdl.g:4214:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKdl.g:4214:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKdl.g:4215:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKdl.g:4221:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4227:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKdl.g:4228:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKdl.g:4228:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKdl.g:4229:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKdl.g:4229:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKdl.g:4230:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKdl.g:4230:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKdl.g:4231:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_68);
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

            // InternalKdl.g:4248:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==101) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // InternalKdl.g:4249:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,101,FOLLOW_67); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4253:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKdl.g:4254:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKdl.g:4254:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKdl.g:4255:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_68);
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
            	    break loop86;
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
    // InternalKdl.g:4277:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKdl.g:4277:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKdl.g:4278:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKdl.g:4284:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) ;
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
            // InternalKdl.g:4290:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) )
            // InternalKdl.g:4291:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            {
            // InternalKdl.g:4291:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            int alt90=10;
            alt90 = dfa90.predict(input);
            switch (alt90) {
                case 1 :
                    // InternalKdl.g:4292:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:4292:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==90) ) {
                        alt87=1;
                    }
                    else if ( (LA87_0==91) ) {
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
                            // InternalKdl.g:4293:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:4293:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:4294:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:4294:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:4295:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:4308:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:4308:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:4309:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:4309:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:4310:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4324:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:4324:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:4325:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:4325:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:4326:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:4326:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:4327:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
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

                    // InternalKdl.g:4344:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt88=3;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==92) ) {
                        alt88=1;
                    }
                    else if ( (LA88_0==93) ) {
                        alt88=2;
                    }
                    switch (alt88) {
                        case 1 :
                            // InternalKdl.g:4345:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:4345:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:4346:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:4346:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:4347:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,92,FOLLOW_38); if (state.failed) return current;
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
                            // InternalKdl.g:4360:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,93,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4365:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:4366:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getTableClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:4372:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:4373:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:4377:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:4378:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_54);
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

                    // InternalKdl.g:4395:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt89=3;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==92) ) {
                        alt89=1;
                    }
                    else if ( (LA89_0==93) ) {
                        alt89=2;
                    }
                    switch (alt89) {
                        case 1 :
                            // InternalKdl.g:4396:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:4396:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:4397:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:4397:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:4398:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:4411:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,93,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4418:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4418:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:4419:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:4419:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:4420:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:4438:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:4438:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:4439:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,94,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:4443:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:4444:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:4444:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:4445:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:4464:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:4464:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:4465:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:4465:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:4466:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:4483:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:4483:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    // InternalKdl.g:4484:4: ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4484:4: ( (lv_op_13_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:4485:5: (lv_op_13_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:4485:5: (lv_op_13_0= ruleREL_OPERATOR )
                    // InternalKdl.g:4486:6: lv_op_13_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    // InternalKdl.g:4503:4: ( (lv_expression_14_0= ruleNumber ) )
                    // InternalKdl.g:4504:5: (lv_expression_14_0= ruleNumber )
                    {
                    // InternalKdl.g:4504:5: (lv_expression_14_0= ruleNumber )
                    // InternalKdl.g:4505:6: lv_expression_14_0= ruleNumber
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
                    // InternalKdl.g:4524:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:4524:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    // InternalKdl.g:4525:4: (lv_expr_15_0= RULE_EXPR )
                    {
                    // InternalKdl.g:4525:4: (lv_expr_15_0= RULE_EXPR )
                    // InternalKdl.g:4526:5: lv_expr_15_0= RULE_EXPR
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
                    // InternalKdl.g:4543:3: ( (lv_nodata_16_0= 'unknown' ) )
                    {
                    // InternalKdl.g:4543:3: ( (lv_nodata_16_0= 'unknown' ) )
                    // InternalKdl.g:4544:4: (lv_nodata_16_0= 'unknown' )
                    {
                    // InternalKdl.g:4544:4: (lv_nodata_16_0= 'unknown' )
                    // InternalKdl.g:4545:5: lv_nodata_16_0= 'unknown'
                    {
                    lv_nodata_16_0=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4558:3: ( (lv_star_17_0= '*' ) )
                    {
                    // InternalKdl.g:4558:3: ( (lv_star_17_0= '*' ) )
                    // InternalKdl.g:4559:4: (lv_star_17_0= '*' )
                    {
                    // InternalKdl.g:4559:4: (lv_star_17_0= '*' )
                    // InternalKdl.g:4560:5: lv_star_17_0= '*'
                    {
                    lv_star_17_0=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_17_0, grammarAccess.getTableClassifierAccess().getStarAsteriskKeyword_8_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "star", lv_star_17_0 != null, "*");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKdl.g:4573:3: ( (lv_anything_18_0= '#' ) )
                    {
                    // InternalKdl.g:4573:3: ( (lv_anything_18_0= '#' ) )
                    // InternalKdl.g:4574:4: (lv_anything_18_0= '#' )
                    {
                    // InternalKdl.g:4574:4: (lv_anything_18_0= '#' )
                    // InternalKdl.g:4575:5: lv_anything_18_0= '#'
                    {
                    lv_anything_18_0=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_anything_18_0, grammarAccess.getTableClassifierAccess().getAnythingNumberSignKeyword_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "anything", lv_anything_18_0 != null, "#");
                      				
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
    // InternalKdl.g:4591:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:4591:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:4592:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
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
    // InternalKdl.g:4598:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4604:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4605:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4605:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4606:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4606:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt91=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt91=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt91=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
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
                    // InternalKdl.g:4607:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_CAMELCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4615:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:4623:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_2, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4631:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( (LA93_0==102) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalKdl.g:4632:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,102,FOLLOW_70); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4637:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    int alt92=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_CAMELCASE_ID:
            	        {
            	        alt92=1;
            	        }
            	        break;
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt92=2;
            	        }
            	        break;
            	    case RULE_LOWERCASE_DASHID:
            	        {
            	        alt92=3;
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
            	            // InternalKdl.g:4638:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
            	            {
            	            this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_69); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_CAMELCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CAMELCASE_ID_4, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4646:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_5, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:4654:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
            	            {
            	            this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_69); if (state.failed) return current;
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
            	    break loop93;
                }
            } while (true);

            // InternalKdl.g:4663:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==103) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalKdl.g:4664:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getFullStopKeyword_2_0());
                      			
                    }
                    this_LOWERCASE_ID_8=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_8, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4677:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==98) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // InternalKdl.g:4678:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,98,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:4695:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4695:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4696:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKdl.g:4702:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_1=null;
        Token lv_name_0_2=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4708:2: ( ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4709:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4709:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4710:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4710:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:4711:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:4711:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:4712:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            {
            // InternalKdl.g:4712:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==RULE_LOWERCASE_ID) ) {
                alt96=1;
            }
            else if ( (LA96_0==RULE_LOWERCASE_DASHID) ) {
                alt96=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }
            switch (alt96) {
                case 1 :
                    // InternalKdl.g:4713:6: lv_name_0_1= RULE_LOWERCASE_ID
                    {
                    lv_name_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_71); if (state.failed) return current;
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
                    // InternalKdl.g:4728:6: lv_name_0_2= RULE_LOWERCASE_DASHID
                    {
                    lv_name_0_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_71); if (state.failed) return current;
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

            // InternalKdl.g:4745:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==104) ) {
                alt97=1;
            }
            else if ( (LA97_0==105) ) {
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
                    // InternalKdl.g:4746:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4746:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4747:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4747:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4748:6: lv_interactive_1_0= '=?'
                    {
                    lv_interactive_1_0=(Token)match(input,104,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_interactive_1_0, grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getKeyValuePairRule());
                      						}
                      						setWithLastConsumed(current, "interactive", lv_interactive_1_0 != null, "=?");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4761:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,105,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4766:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4767:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4767:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4768:5: lv_value_3_0= ruleValue
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
    // InternalKdl.g:4789:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4789:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4790:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalKdl.g:4796:1: ruleFunction returns [EObject current=null] : ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token lv_mediated_0_0=null;
        Token otherlv_1=null;
        Token lv_variable_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_expression_10_0=null;
        Token otherlv_11=null;
        Token lv_target_12_0=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_urn_8_0 = null;

        EObject lv_value_9_0 = null;

        EObject lv_chain_14_0 = null;

        EObject lv_chain_16_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4802:2: ( ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) ) )
            // InternalKdl.g:4803:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) )
            {
            // InternalKdl.g:4803:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) )
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( ((LA103_0>=RULE_STRING && LA103_0<=RULE_LOWERCASE_ID)||(LA103_0>=RULE_INT && LA103_0<=RULE_LOWERCASE_DASHID)||LA103_0==RULE_CAMELCASE_ID||LA103_0==RULE_EXPR||LA103_0==44||(LA103_0>=90 && LA103_0<=91)||LA103_0==96||LA103_0==113) ) {
                alt103=1;
            }
            else if ( (LA103_0==33) ) {
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
                    // InternalKdl.g:4804:3: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4804:3: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4805:4: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4805:4: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )?
                    int alt98=3;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==RULE_LOWERCASE_ID) ) {
                        int LA98_1 = input.LA(2);

                        if ( (LA98_1==106) ) {
                            int LA98_3 = input.LA(3);

                            if ( (LA98_3==RULE_LOWERCASE_ID) ) {
                                int LA98_5 = input.LA(4);

                                if ( (synpred197_InternalKdl()) ) {
                                    alt98=1;
                                }
                            }
                            else if ( (LA98_3==RULE_STRING||(LA98_3>=RULE_INT && LA98_3<=RULE_LOWERCASE_DASHID)||LA98_3==RULE_CAMELCASE_ID||LA98_3==RULE_EXPR||LA98_3==44||(LA98_3>=90 && LA98_3<=91)||LA98_3==96||LA98_3==113) ) {
                                alt98=1;
                            }
                        }
                        else if ( (LA98_1==107) ) {
                            alt98=2;
                        }
                    }
                    switch (alt98) {
                        case 1 :
                            // InternalKdl.g:4806:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
                            {
                            // InternalKdl.g:4806:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
                            // InternalKdl.g:4807:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4807:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4808:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4808:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4809:8: lv_mediated_0_0= RULE_LOWERCASE_ID
                            {
                            lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_mediated_0_0, grammarAccess.getFunctionAccess().getMediatedLOWERCASE_IDTerminalRuleCall_0_0_0_0_0());
                              							
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

                            otherlv_1=(Token)match(input,106,FOLLOW_73); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_0_0_1());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4831:5: ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' )
                            {
                            // InternalKdl.g:4831:5: ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' )
                            // InternalKdl.g:4832:6: ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-'
                            {
                            // InternalKdl.g:4832:6: ( (lv_variable_2_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4833:7: (lv_variable_2_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4833:7: (lv_variable_2_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4834:8: lv_variable_2_0= RULE_LOWERCASE_ID
                            {
                            lv_variable_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_variable_2_0, grammarAccess.getFunctionAccess().getVariableLOWERCASE_IDTerminalRuleCall_0_0_1_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getFunctionRule());
                              								}
                              								setWithLastConsumed(
                              									current,
                              									"variable",
                              									lv_variable_2_0,
                              									"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              							
                            }

                            }


                            }

                            otherlv_3=(Token)match(input,107,FOLLOW_73); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLessThanSignHyphenMinusKeyword_0_0_1_1());
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:4856:4: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) )
                    int alt100=4;
                    alt100 = dfa100.predict(input);
                    switch (alt100) {
                        case 1 :
                            // InternalKdl.g:4857:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
                            {
                            // InternalKdl.g:4857:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
                            // InternalKdl.g:4858:6: ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')'
                            {
                            // InternalKdl.g:4858:6: ( (lv_name_4_0= rulePathName ) )
                            // InternalKdl.g:4859:7: (lv_name_4_0= rulePathName )
                            {
                            // InternalKdl.g:4859:7: (lv_name_4_0= rulePathName )
                            // InternalKdl.g:4860:8: lv_name_4_0= rulePathName
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_55);
                            lv_name_4_0=rulePathName();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getFunctionRule());
                              								}
                              								set(
                              									current,
                              									"name",
                              									lv_name_4_0,
                              									"org.integratedmodelling.kdl.Kdl.PathName");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_5=(Token)match(input,33,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_1_0_1());
                              					
                            }
                            // InternalKdl.g:4881:6: ( (lv_parameters_6_0= ruleParameterList ) )?
                            int alt99=2;
                            int LA99_0 = input.LA(1);

                            if ( ((LA99_0>=RULE_STRING && LA99_0<=RULE_LOWERCASE_ID)||(LA99_0>=RULE_INT && LA99_0<=RULE_CAMELCASE_ID)||(LA99_0>=RULE_ID && LA99_0<=RULE_EXPR)||LA99_0==31||LA99_0==33||LA99_0==44||LA99_0==50||(LA99_0>=90 && LA99_0<=91)||LA99_0==96||LA99_0==99||LA99_0==113) ) {
                                alt99=1;
                            }
                            switch (alt99) {
                                case 1 :
                                    // InternalKdl.g:4882:7: (lv_parameters_6_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4882:7: (lv_parameters_6_0= ruleParameterList )
                                    // InternalKdl.g:4883:8: lv_parameters_6_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_16);
                                    lv_parameters_6_0=ruleParameterList();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElementForParent(grammarAccess.getFunctionRule());
                                      								}
                                      								set(
                                      									current,
                                      									"parameters",
                                      									lv_parameters_6_0,
                                      									"org.integratedmodelling.kdl.Kdl.ParameterList");
                                      								afterParserOrEnumRuleCall();
                                      							
                                    }

                                    }


                                    }
                                    break;

                            }

                            otherlv_7=(Token)match(input,34,FOLLOW_75); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4906:5: ( (lv_urn_8_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4906:5: ( (lv_urn_8_0= ruleUrn ) )
                            // InternalKdl.g:4907:6: (lv_urn_8_0= ruleUrn )
                            {
                            // InternalKdl.g:4907:6: (lv_urn_8_0= ruleUrn )
                            // InternalKdl.g:4908:7: lv_urn_8_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_75);
                            lv_urn_8_0=ruleUrn();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getFunctionRule());
                              							}
                              							set(
                              								current,
                              								"urn",
                              								lv_urn_8_0,
                              								"org.integratedmodelling.kdl.Kdl.Urn");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:4926:5: ( (lv_value_9_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4926:5: ( (lv_value_9_0= ruleLiteral ) )
                            // InternalKdl.g:4927:6: (lv_value_9_0= ruleLiteral )
                            {
                            // InternalKdl.g:4927:6: (lv_value_9_0= ruleLiteral )
                            // InternalKdl.g:4928:7: lv_value_9_0= ruleLiteral
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getValueLiteralParserRuleCall_0_1_2_0());
                              						
                            }
                            pushFollow(FOLLOW_75);
                            lv_value_9_0=ruleLiteral();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getFunctionRule());
                              							}
                              							set(
                              								current,
                              								"value",
                              								lv_value_9_0,
                              								"org.integratedmodelling.kdl.Kdl.Literal");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 4 :
                            // InternalKdl.g:4946:5: ( (lv_expression_10_0= RULE_EXPR ) )
                            {
                            // InternalKdl.g:4946:5: ( (lv_expression_10_0= RULE_EXPR ) )
                            // InternalKdl.g:4947:6: (lv_expression_10_0= RULE_EXPR )
                            {
                            // InternalKdl.g:4947:6: (lv_expression_10_0= RULE_EXPR )
                            // InternalKdl.g:4948:7: lv_expression_10_0= RULE_EXPR
                            {
                            lv_expression_10_0=(Token)match(input,RULE_EXPR,FOLLOW_75); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_expression_10_0, grammarAccess.getFunctionAccess().getExpressionEXPRTerminalRuleCall_0_1_3_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"expression",
                              								lv_expression_10_0,
                              								"org.integratedmodelling.kdl.Kdl.EXPR");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:4965:4: (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==106) ) {
                        alt101=1;
                    }
                    switch (alt101) {
                        case 1 :
                            // InternalKdl.g:4966:5: otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_11=(Token)match(input,106,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4970:5: ( (lv_target_12_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4971:6: (lv_target_12_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4971:6: (lv_target_12_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4972:7: lv_target_12_0= RULE_LOWERCASE_ID
                            {
                            lv_target_12_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_target_12_0, grammarAccess.getFunctionAccess().getTargetLOWERCASE_IDTerminalRuleCall_0_2_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"target",
                              								lv_target_12_0,
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
                    // InternalKdl.g:4991:3: (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' )
                    {
                    // InternalKdl.g:4991:3: (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' )
                    // InternalKdl.g:4992:4: otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')'
                    {
                    otherlv_13=(Token)match(input,33,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:4996:4: ( (lv_chain_14_0= ruleFunction ) )
                    // InternalKdl.g:4997:5: (lv_chain_14_0= ruleFunction )
                    {
                    // InternalKdl.g:4997:5: (lv_chain_14_0= ruleFunction )
                    // InternalKdl.g:4998:6: lv_chain_14_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    lv_chain_14_0=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getFunctionRule());
                      						}
                      						add(
                      							current,
                      							"chain",
                      							lv_chain_14_0,
                      							"org.integratedmodelling.kdl.Kdl.Function");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:5015:4: (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )*
                    loop102:
                    do {
                        int alt102=2;
                        int LA102_0 = input.LA(1);

                        if ( (LA102_0==31) ) {
                            alt102=1;
                        }


                        switch (alt102) {
                    	case 1 :
                    	    // InternalKdl.g:5016:5: otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) )
                    	    {
                    	    otherlv_15=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_15, grammarAccess.getFunctionAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:5020:5: ( (lv_chain_16_0= ruleFunction ) )
                    	    // InternalKdl.g:5021:6: (lv_chain_16_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:5021:6: (lv_chain_16_0= ruleFunction )
                    	    // InternalKdl.g:5022:7: lv_chain_16_0= ruleFunction
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_56);
                    	    lv_chain_16_0=ruleFunction();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"chain",
                    	      								lv_chain_16_0,
                    	      								"org.integratedmodelling.kdl.Kdl.Function");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop102;
                        }
                    } while (true);

                    otherlv_17=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_17, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_1_3());
                      			
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


    // $ANTLR start "entryRuleREL_OPERATOR"
    // InternalKdl.g:5049:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:5049:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:5050:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKdl.g:5056:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKdl.g:5062:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:5063:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:5063:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt104=6;
            switch ( input.LA(1) ) {
            case 108:
                {
                alt104=1;
                }
                break;
            case 109:
                {
                alt104=2;
                }
                break;
            case 105:
                {
                alt104=3;
                }
                break;
            case 110:
                {
                alt104=4;
                }
                break;
            case 111:
                {
                alt104=5;
                }
                break;
            case 112:
                {
                alt104=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // InternalKdl.g:5064:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:5064:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:5065:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:5065:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:5066:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,108,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_gt_0_0, grammarAccess.getREL_OPERATORAccess().getGtGreaterThanSignKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "gt", lv_gt_0_0 != null, ">");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:5079:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:5079:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:5080:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:5080:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:5081:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,109,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_lt_1_0, grammarAccess.getREL_OPERATORAccess().getLtLessThanSignKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "lt", lv_lt_1_0 != null, "<");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:5094:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:5094:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:5095:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:5095:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:5096:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,105,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_eq_2_0, grammarAccess.getREL_OPERATORAccess().getEqEqualsSignKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "eq", lv_eq_2_0 != null, "=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:5109:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:5109:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:5110:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:5110:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:5111:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,110,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_ne_3_0, grammarAccess.getREL_OPERATORAccess().getNeExclamationMarkEqualsSignKeyword_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "ne", lv_ne_3_0 != null, "!=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:5124:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:5124:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:5125:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:5125:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:5126:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,111,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_le_4_0, grammarAccess.getREL_OPERATORAccess().getLeLessThanSignEqualsSignKeyword_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "le", lv_le_4_0 != null, "<=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:5139:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:5139:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:5140:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:5140:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:5141:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,112,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_ge_5_0, grammarAccess.getREL_OPERATORAccess().getGeGreaterThanSignEqualsSignKeyword_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "ge", lv_ge_5_0 != null, ">=");
                      				
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


    // $ANTLR start "entryRuleNumber"
    // InternalKdl.g:5157:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:5157:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:5158:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKdl.g:5164:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKdl.g:5170:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:5171:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:5171:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:5172:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:5172:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt105=3;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==44) ) {
                alt105=1;
            }
            else if ( (LA105_0==113) ) {
                alt105=2;
            }
            switch (alt105) {
                case 1 :
                    // InternalKdl.g:5173:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,44,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5178:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:5178:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:5179:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:5179:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:5180:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,113,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_negative_1_0, grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNumberRule());
                      						}
                      						setWithLastConsumed(current, "negative", lv_negative_1_0 != null, "-");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKdl.g:5193:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:5194:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:5198:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:5199:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_76); if (state.failed) return current;
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

            // InternalKdl.g:5215:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==103) && (synpred214_InternalKdl())) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // InternalKdl.g:5216:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5229:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:5230:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5230:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:5231:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:5231:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:5232:7: lv_decimal_3_0= '.'
                    {
                    lv_decimal_3_0=(Token)match(input,103,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimal_3_0, grammarAccess.getNumberAccess().getDecimalFullStopKeyword_2_0_0_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(current, "decimal", lv_decimal_3_0 != null, ".");
                      						
                    }

                    }


                    }

                    // InternalKdl.g:5244:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:5245:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:5245:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:5246:7: lv_decimalPart_4_0= RULE_INT
                    {
                    lv_decimalPart_4_0=(Token)match(input,RULE_INT,FOLLOW_77); if (state.failed) return current;
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

            // InternalKdl.g:5264:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==114) && (synpred218_InternalKdl())) {
                alt109=1;
            }
            else if ( (LA109_0==115) && (synpred218_InternalKdl())) {
                alt109=1;
            }
            switch (alt109) {
                case 1 :
                    // InternalKdl.g:5265:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5291:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:5292:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5292:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:5293:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:5293:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:5294:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:5294:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( (LA107_0==114) ) {
                        alt107=1;
                    }
                    else if ( (LA107_0==115) ) {
                        alt107=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 107, 0, input);

                        throw nvae;
                    }
                    switch (alt107) {
                        case 1 :
                            // InternalKdl.g:5295:8: lv_exponential_5_1= 'e'
                            {
                            lv_exponential_5_1=(Token)match(input,114,FOLLOW_36); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_5_1, grammarAccess.getNumberAccess().getExponentialEKeyword_3_0_0_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "exponential", lv_exponential_5_1 != null, null);
                              							
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5306:8: lv_exponential_5_2= 'E'
                            {
                            lv_exponential_5_2=(Token)match(input,115,FOLLOW_36); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_5_2, grammarAccess.getNumberAccess().getExponentialEKeyword_3_0_0_0_1());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "exponential", lv_exponential_5_2 != null, null);
                              							
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:5319:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt108=3;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==44) ) {
                        alt108=1;
                    }
                    else if ( (LA108_0==113) ) {
                        alt108=2;
                    }
                    switch (alt108) {
                        case 1 :
                            // InternalKdl.g:5320:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,44,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5325:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:5325:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:5326:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:5326:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:5327:8: lv_expNegative_7_0= '-'
                            {
                            lv_expNegative_7_0=(Token)match(input,113,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_expNegative_7_0, grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_3_0_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "expNegative", lv_expNegative_7_0 != null, "-");
                              							
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:5340:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:5341:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:5341:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:5342:7: lv_exp_8_0= RULE_INT
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
    // InternalKdl.g:5364:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:5364:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:5365:2: iv_rulePathName= rulePathName EOF
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
    // InternalKdl.g:5371:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5377:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5378:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5378:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5379:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_78); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5386:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==103) ) {
                    int LA110_2 = input.LA(2);

                    if ( (LA110_2==RULE_LOWERCASE_ID) ) {
                        alt110=1;
                    }


                }


                switch (alt110) {
            	case 1 :
            	    // InternalKdl.g:5387:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_78); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop110;
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
    // InternalKdl.g:5404:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:5404:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:5405:2: iv_rulePath= rulePath EOF
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
    // InternalKdl.g:5411:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:5417:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5418:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5418:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5419:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_79); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5426:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop112:
            do {
                int alt112=2;
                int LA112_0 = input.LA(1);

                if ( ((LA112_0>=102 && LA112_0<=103)) ) {
                    alt112=1;
                }


                switch (alt112) {
            	case 1 :
            	    // InternalKdl.g:5427:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:5427:4: (kw= '.' | kw= '/' )
            	    int alt111=2;
            	    int LA111_0 = input.LA(1);

            	    if ( (LA111_0==103) ) {
            	        alt111=1;
            	    }
            	    else if ( (LA111_0==102) ) {
            	        alt111=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 111, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt111) {
            	        case 1 :
            	            // InternalKdl.g:5428:5: kw= '.'
            	            {
            	            kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:5434:5: kw= '/'
            	            {
            	            kw=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_79); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_3, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop112;
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
    // InternalKdl.g:5452:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5452:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5453:2: iv_ruleJavaClass= ruleJavaClass EOF
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
    // InternalKdl.g:5459:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5465:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5466:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5466:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5467:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getJavaClassAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_80);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,103,FOLLOW_81); if (state.failed) return current;
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


    // $ANTLR start "entryRulePropertyId"
    // InternalKdl.g:5493:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5493:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5494:2: iv_rulePropertyId= rulePropertyId EOF
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
    // InternalKdl.g:5500:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5506:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5507:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5507:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5508:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPropertyIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_59);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,97,FOLLOW_82); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getPropertyIdAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:5523:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==RULE_LOWERCASE_ID) ) {
                alt113=1;
            }
            else if ( (LA113_0==RULE_LOWERCASE_DASHID) ) {
                alt113=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }
            switch (alt113) {
                case 1 :
                    // InternalKdl.g:5524:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5532:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
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
    // InternalKdl.g:5544:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5544:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5545:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKdl.g:5551:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKdl.g:5557:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:5558:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:5558:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:5559:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_83); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5566:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==103) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // InternalKdl.g:5567:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,103,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_83); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKdl.g:5579:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==103) ) {
                        alt114=1;
                    }
                    switch (alt114) {
                        case 1 :
                            // InternalKdl.g:5580:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,103,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_84); if (state.failed) return current;
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

            // InternalKdl.g:5594:3: (kw= '-' )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==113) ) {
                int LA116_1 = input.LA(2);

                if ( (synpred228_InternalKdl()) ) {
                    alt116=1;
                }
            }
            switch (alt116) {
                case 1 :
                    // InternalKdl.g:5595:4: kw= '-'
                    {
                    kw=(Token)match(input,113,FOLLOW_85); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:5601:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt117=3;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==RULE_LOWERCASE_ID) ) {
                int LA117_1 = input.LA(2);

                if ( (synpred229_InternalKdl()) ) {
                    alt117=1;
                }
            }
            else if ( (LA117_0==RULE_UPPERCASE_ID) ) {
                int LA117_2 = input.LA(2);

                if ( (synpred230_InternalKdl()) ) {
                    alt117=2;
                }
            }
            switch (alt117) {
                case 1 :
                    // InternalKdl.g:5602:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5610:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKdl.g:5622:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5628:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:5629:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:5629:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt118=3;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt118=1;
                }
                break;
            case 116:
                {
                alt118=2;
                }
                break;
            case 47:
                {
                alt118=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }

            switch (alt118) {
                case 1 :
                    // InternalKdl.g:5630:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:5630:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:5631:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,102,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:5638:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:5638:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:5639:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,116,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:5646:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:5646:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:5647:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
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
        otherlv_1=(Token)match(input,20,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:113:9: ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        {
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        {
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        int alt119=2;
        alt119 = dfa119.predict(input);
        switch (alt119) {
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
        otherlv_3=(Token)match(input,21,FOLLOW_5); if (state.failed) return ;
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
        int cnt120=0;
        loop120:
        do {
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==21) && ((true))) {
                alt120=1;
            }


            switch (alt120) {
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
        	    otherlv_3=(Token)match(input,21,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:165:9: ( (lv_variables_4_0= ruleParameter ) )
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    {
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    // InternalKdl.g:167:11: lv_variables_4_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getVariablesParameterParserRuleCall_0_1_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_86);
        	    lv_variables_4_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt120 >= 1 ) break loop120;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(120, input);
                    throw eee;
            }
            cnt120++;
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
        otherlv_5=(Token)match(input,22,FOLLOW_5); if (state.failed) return ;
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
        int cnt121=0;
        loop121:
        do {
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==22) && ((true))) {
                alt121=1;
            }


            switch (alt121) {
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
        	    otherlv_5=(Token)match(input,22,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:199:9: ( (lv_constants_6_0= ruleParameter ) )
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    {
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    // InternalKdl.g:201:11: lv_constants_6_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getConstantsParameterParserRuleCall_0_2_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_87);
        	    lv_constants_6_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt121 >= 1 ) break loop121;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(121, input);
                    throw eee;
            }
            cnt121++;
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
        otherlv_7=(Token)match(input,23,FOLLOW_6); if (state.failed) return ;
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
        int cnt122=0;
        loop122:
        do {
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==23) && ((true))) {
                alt122=1;
            }


            switch (alt122) {
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
        	    otherlv_7=(Token)match(input,23,FOLLOW_6); if (state.failed) return ;
        	    // InternalKdl.g:233:9: ( (lv_authors_8_0= RULE_STRING ) )
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    {
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    // InternalKdl.g:235:11: lv_authors_8_0= RULE_STRING
        	    {
        	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_88); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt122 >= 1 ) break loop122;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(122, input);
                    throw eee;
            }
            cnt122++;
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
        otherlv_9=(Token)match(input,24,FOLLOW_7); if (state.failed) return ;
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
        otherlv_11=(Token)match(input,25,FOLLOW_7); if (state.failed) return ;
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
        otherlv_13=(Token)match(input,26,FOLLOW_5); if (state.failed) return ;
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
        otherlv_15=(Token)match(input,27,FOLLOW_8); if (state.failed) return ;
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
        otherlv_17=(Token)match(input,28,FOLLOW_6); if (state.failed) return ;
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

    // $ANTLR start synpred15_InternalKdl
    public final void synpred15_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_19=null;
        Token lv_package_20_2=null;
        AntlrDatatypeRuleToken lv_package_20_1 = null;


        // InternalKdl.g:425:4: ( ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKdl.g:425:4: ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) )
        // InternalKdl.g:426:5: {...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9)");
        }
        // InternalKdl.g:426:102: ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) )
        // InternalKdl.g:427:6: ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9);
        // InternalKdl.g:430:9: ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) )
        // InternalKdl.g:430:10: {...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKdl", "true");
        }
        // InternalKdl.g:430:19: (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) )
        // InternalKdl.g:430:20: otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) )
        {
        otherlv_19=(Token)match(input,29,FOLLOW_9); if (state.failed) return ;
        // InternalKdl.g:434:9: ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) )
        // InternalKdl.g:435:10: ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) )
        {
        // InternalKdl.g:435:10: ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) )
        // InternalKdl.g:436:11: (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING )
        {
        // InternalKdl.g:436:11: (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING )
        int alt123=2;
        int LA123_0 = input.LA(1);

        if ( (LA123_0==RULE_LOWERCASE_ID) ) {
            alt123=1;
        }
        else if ( (LA123_0==RULE_STRING) ) {
            alt123=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 123, 0, input);

            throw nvae;
        }
        switch (alt123) {
            case 1 :
                // InternalKdl.g:437:12: lv_package_20_1= rulePathName
                {
                pushFollow(FOLLOW_2);
                lv_package_20_1=rulePathName();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:453:12: lv_package_20_2= RULE_STRING
                {
                lv_package_20_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred15_InternalKdl

    // $ANTLR start synpred17_InternalKdl
    public final void synpred17_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_21=null;
        Token otherlv_23=null;
        EObject lv_scale_22_0 = null;

        EObject lv_scale_24_0 = null;


        // InternalKdl.g:476:4: ( ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) )
        // InternalKdl.g:476:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
        {
        // InternalKdl.g:476:4: ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) )
        // InternalKdl.g:477:5: {...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10)");
        }
        // InternalKdl.g:477:103: ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) )
        // InternalKdl.g:478:6: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10);
        // InternalKdl.g:481:9: ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) )
        // InternalKdl.g:481:10: {...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKdl", "true");
        }
        // InternalKdl.g:481:19: (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* )
        // InternalKdl.g:481:20: otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
        {
        otherlv_21=(Token)match(input,30,FOLLOW_10); if (state.failed) return ;
        // InternalKdl.g:485:9: ( (lv_scale_22_0= ruleFunction ) )
        // InternalKdl.g:486:10: (lv_scale_22_0= ruleFunction )
        {
        // InternalKdl.g:486:10: (lv_scale_22_0= ruleFunction )
        // InternalKdl.g:487:11: lv_scale_22_0= ruleFunction
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_1_0());
          										
        }
        pushFollow(FOLLOW_44);
        lv_scale_22_0=ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:504:9: (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
        loop124:
        do {
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==31) ) {
                alt124=1;
            }


            switch (alt124) {
        	case 1 :
        	    // InternalKdl.g:505:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
        	    {
        	    otherlv_23=(Token)match(input,31,FOLLOW_10); if (state.failed) return ;
        	    // InternalKdl.g:509:10: ( (lv_scale_24_0= ruleFunction ) )
        	    // InternalKdl.g:510:11: (lv_scale_24_0= ruleFunction )
        	    {
        	    // InternalKdl.g:510:11: (lv_scale_24_0= ruleFunction )
        	    // InternalKdl.g:511:12: lv_scale_24_0= ruleFunction
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getModelAccess().getScaleFunctionParserRuleCall_0_10_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_44);
        	    lv_scale_24_0=ruleFunction();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop124;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred17_InternalKdl

    // $ANTLR start synpred18_InternalKdl
    public final void synpred18_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_25=null;
        EObject lv_contextUrn_26_0 = null;


        // InternalKdl.g:535:4: ( ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )
        // InternalKdl.g:535:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
        {
        // InternalKdl.g:535:4: ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) )
        // InternalKdl.g:536:5: {...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11)");
        }
        // InternalKdl.g:536:103: ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) )
        // InternalKdl.g:537:6: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11);
        // InternalKdl.g:540:9: ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) )
        // InternalKdl.g:540:10: {...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKdl", "true");
        }
        // InternalKdl.g:540:19: (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) )
        // InternalKdl.g:540:20: otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) )
        {
        otherlv_25=(Token)match(input,32,FOLLOW_12); if (state.failed) return ;
        // InternalKdl.g:544:9: ( (lv_contextUrn_26_0= ruleUrn ) )
        // InternalKdl.g:545:10: (lv_contextUrn_26_0= ruleUrn )
        {
        // InternalKdl.g:545:10: (lv_contextUrn_26_0= ruleUrn )
        // InternalKdl.g:546:11: lv_contextUrn_26_0= ruleUrn
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
    // $ANTLR end synpred18_InternalKdl

    // $ANTLR start synpred62_InternalKdl
    public final void synpred62_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_48=null;
        EObject lv_default_49_0 = null;


        // InternalKdl.g:1534:5: ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) )
        // InternalKdl.g:1534:5: ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) )
        {
        // InternalKdl.g:1534:5: ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) )
        // InternalKdl.g:1535:6: {...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred62_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0)");
        }
        // InternalKdl.g:1535:116: ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) )
        // InternalKdl.g:1536:7: ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 0);
        // InternalKdl.g:1539:10: ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) )
        // InternalKdl.g:1539:11: {...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred62_InternalKdl", "true");
        }
        // InternalKdl.g:1539:20: (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) )
        // InternalKdl.g:1539:21: otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) )
        {
        otherlv_48=(Token)match(input,57,FOLLOW_41); if (state.failed) return ;
        // InternalKdl.g:1543:10: ( (lv_default_49_0= ruleValue ) )
        // InternalKdl.g:1544:11: (lv_default_49_0= ruleValue )
        {
        // InternalKdl.g:1544:11: (lv_default_49_0= ruleValue )
        // InternalKdl.g:1545:12: lv_default_49_0= ruleValue
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_16_0_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_default_49_0=ruleValue();

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
    // $ANTLR end synpred62_InternalKdl

    // $ANTLR start synpred63_InternalKdl
    public final void synpred63_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_50=null;
        EObject lv_unit_51_0 = null;


        // InternalKdl.g:1568:5: ( ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1568:5: ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1568:5: ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1569:6: {...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred63_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1)");
        }
        // InternalKdl.g:1569:116: ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1570:7: ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_16(), 1);
        // InternalKdl.g:1573:10: ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) )
        // InternalKdl.g:1573:11: {...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred63_InternalKdl", "true");
        }
        // InternalKdl.g:1573:20: (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) )
        // InternalKdl.g:1573:21: otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) )
        {
        otherlv_50=(Token)match(input,58,FOLLOW_89); if (state.failed) return ;
        // InternalKdl.g:1577:10: ( (lv_unit_51_0= ruleUnit ) )
        // InternalKdl.g:1578:11: (lv_unit_51_0= ruleUnit )
        {
        // InternalKdl.g:1578:11: (lv_unit_51_0= ruleUnit )
        // InternalKdl.g:1579:12: lv_unit_51_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getActorDefinitionAccess().getUnitUnitParserRuleCall_1_16_1_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_unit_51_0=ruleUnit();

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
    // $ANTLR end synpred63_InternalKdl

    // $ANTLR start synpred68_InternalKdl
    public final void synpred68_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_geometry_4_0 = null;


        // InternalKdl.g:1752:4: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1752:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1752:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1753:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred68_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKdl.g:1753:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1754:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
        // InternalKdl.g:1757:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1757:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred68_InternalKdl", "true");
        }
        // InternalKdl.g:1757:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        // InternalKdl.g:1757:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
        {
        otherlv_3=(Token)match(input,61,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1761:9: ( (lv_geometry_4_0= ruleGeometry ) )
        // InternalKdl.g:1762:10: (lv_geometry_4_0= ruleGeometry )
        {
        // InternalKdl.g:1762:10: (lv_geometry_4_0= ruleGeometry )
        // InternalKdl.g:1763:11: lv_geometry_4_0= ruleGeometry
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
    // $ANTLR end synpred68_InternalKdl

    // $ANTLR start synpred69_InternalKdl
    public final void synpred69_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_5_0 = null;


        // InternalKdl.g:1786:4: ( ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) )
        // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
        {
        // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
        // InternalKdl.g:1787:5: {...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred69_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKdl.g:1787:109: ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
        // InternalKdl.g:1788:6: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
        // InternalKdl.g:1791:9: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
        // InternalKdl.g:1791:10: {...}? => ( (lv_computations_5_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred69_InternalKdl", "true");
        }
        // InternalKdl.g:1791:19: ( (lv_computations_5_0= ruleComputation ) )
        // InternalKdl.g:1791:20: (lv_computations_5_0= ruleComputation )
        {
        // InternalKdl.g:1791:20: (lv_computations_5_0= ruleComputation )
        // InternalKdl.g:1792:10: lv_computations_5_0= ruleComputation
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_2_1_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_computations_5_0=ruleComputation();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred69_InternalKdl

    // $ANTLR start synpred70_InternalKdl
    public final void synpred70_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_6=null;
        EObject lv_metadata_7_0 = null;


        // InternalKdl.g:1820:10: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )
        // InternalKdl.g:1820:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
        {
        otherlv_6=(Token)match(input,62,FOLLOW_47); if (state.failed) return ;
        // InternalKdl.g:1824:10: ( (lv_metadata_7_0= ruleMetadata ) )
        // InternalKdl.g:1825:11: (lv_metadata_7_0= ruleMetadata )
        {
        // InternalKdl.g:1825:11: (lv_metadata_7_0= ruleMetadata )
        // InternalKdl.g:1826:12: lv_metadata_7_0= ruleMetadata
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_2_0_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_metadata_7_0=ruleMetadata();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred70_InternalKdl

    // $ANTLR start synpred72_InternalKdl
    public final void synpred72_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;


        // InternalKdl.g:1845:10: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )
        // InternalKdl.g:1845:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
        {
        otherlv_8=(Token)match(input,63,FOLLOW_9); if (state.failed) return ;
        // InternalKdl.g:1849:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
        // InternalKdl.g:1850:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
        {
        // InternalKdl.g:1850:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
        // InternalKdl.g:1851:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
        {
        // InternalKdl.g:1851:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
        int alt141=2;
        int LA141_0 = input.LA(1);

        if ( (LA141_0==RULE_LOWERCASE_ID) ) {
            alt141=1;
        }
        else if ( (LA141_0==RULE_STRING) ) {
            alt141=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 141, 0, input);

            throw nvae;
        }
        switch (alt141) {
            case 1 :
                // InternalKdl.g:1852:13: lv_javaClass_9_1= ruleJavaClass
                {
                pushFollow(FOLLOW_2);
                lv_javaClass_9_1=ruleJavaClass();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:1868:13: lv_javaClass_9_2= RULE_STRING
                {
                lv_javaClass_9_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }


        }


        }
    }
    // $ANTLR end synpred72_InternalKdl

    // $ANTLR start synpred73_InternalKdl
    public final void synpred73_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        EObject lv_metadata_7_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;


        // InternalKdl.g:1814:4: ( ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )
        // InternalKdl.g:1814:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
        {
        // InternalKdl.g:1814:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
        // InternalKdl.g:1815:5: {...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred73_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKdl.g:1815:109: ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
        // InternalKdl.g:1816:6: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
        // InternalKdl.g:1819:9: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
        // InternalKdl.g:1819:10: {...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred73_InternalKdl", "true");
        }
        // InternalKdl.g:1819:19: ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
        // InternalKdl.g:1819:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
        {
        // InternalKdl.g:1819:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )?
        int alt142=2;
        int LA142_0 = input.LA(1);

        if ( (LA142_0==62) ) {
            alt142=1;
        }
        switch (alt142) {
            case 1 :
                // InternalKdl.g:1820:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
                {
                otherlv_6=(Token)match(input,62,FOLLOW_47); if (state.failed) return ;
                // InternalKdl.g:1824:10: ( (lv_metadata_7_0= ruleMetadata ) )
                // InternalKdl.g:1825:11: (lv_metadata_7_0= ruleMetadata )
                {
                // InternalKdl.g:1825:11: (lv_metadata_7_0= ruleMetadata )
                // InternalKdl.g:1826:12: lv_metadata_7_0= ruleMetadata
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_2_0_1_0());
                  											
                }
                pushFollow(FOLLOW_90);
                lv_metadata_7_0=ruleMetadata();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:1844:9: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
        int alt144=2;
        int LA144_0 = input.LA(1);

        if ( (LA144_0==63) ) {
            alt144=1;
        }
        switch (alt144) {
            case 1 :
                // InternalKdl.g:1845:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
                {
                otherlv_8=(Token)match(input,63,FOLLOW_9); if (state.failed) return ;
                // InternalKdl.g:1849:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
                // InternalKdl.g:1850:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
                {
                // InternalKdl.g:1850:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
                // InternalKdl.g:1851:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
                {
                // InternalKdl.g:1851:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
                int alt143=2;
                int LA143_0 = input.LA(1);

                if ( (LA143_0==RULE_LOWERCASE_ID) ) {
                    alt143=1;
                }
                else if ( (LA143_0==RULE_STRING) ) {
                    alt143=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 143, 0, input);

                    throw nvae;
                }
                switch (alt143) {
                    case 1 :
                        // InternalKdl.g:1852:13: lv_javaClass_9_1= ruleJavaClass
                        {
                        pushFollow(FOLLOW_2);
                        lv_javaClass_9_1=ruleJavaClass();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 2 :
                        // InternalKdl.g:1868:13: lv_javaClass_9_2= RULE_STRING
                        {
                        lv_javaClass_9_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                        }
                        break;

                }


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
    // $ANTLR end synpred73_InternalKdl

    // $ANTLR start synpred113_InternalKdl
    public final void synpred113_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2587:5: ( 'to' )
        // InternalKdl.g:2587:6: 'to'
        {
        match(input,55,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred113_InternalKdl

    // $ANTLR start synpred154_InternalKdl
    public final void synpred154_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:3605:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:3605:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:3605:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:3606:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:3606:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:3607:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
    // $ANTLR end synpred154_InternalKdl

    // $ANTLR start synpred155_InternalKdl
    public final void synpred155_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:3625:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:3625:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:3625:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:3626:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:3626:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:3627:5: lv_function_1_0= ruleFunction
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
    // $ANTLR end synpred155_InternalKdl

    // $ANTLR start synpred156_InternalKdl
    public final void synpred156_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;


        // InternalKdl.g:3645:3: ( ( (lv_urn_2_0= ruleUrn ) ) )
        // InternalKdl.g:3645:3: ( (lv_urn_2_0= ruleUrn ) )
        {
        // InternalKdl.g:3645:3: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:3646:4: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:3646:4: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:3647:5: lv_urn_2_0= ruleUrn
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
    // $ANTLR end synpred156_InternalKdl

    // $ANTLR start synpred157_InternalKdl
    public final void synpred157_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_3_0 = null;


        // InternalKdl.g:3665:3: ( ( (lv_list_3_0= ruleList ) ) )
        // InternalKdl.g:3665:3: ( (lv_list_3_0= ruleList ) )
        {
        // InternalKdl.g:3665:3: ( (lv_list_3_0= ruleList ) )
        // InternalKdl.g:3666:4: (lv_list_3_0= ruleList )
        {
        // InternalKdl.g:3666:4: (lv_list_3_0= ruleList )
        // InternalKdl.g:3667:5: lv_list_3_0= ruleList
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
    // $ANTLR end synpred157_InternalKdl

    // $ANTLR start synpred159_InternalKdl
    public final void synpred159_InternalKdl_fragment() throws RecognitionException {   
        Token lv_expression_5_0=null;

        // InternalKdl.g:3705:3: ( ( (lv_expression_5_0= RULE_EXPR ) ) )
        // InternalKdl.g:3705:3: ( (lv_expression_5_0= RULE_EXPR ) )
        {
        // InternalKdl.g:3705:3: ( (lv_expression_5_0= RULE_EXPR ) )
        // InternalKdl.g:3706:4: (lv_expression_5_0= RULE_EXPR )
        {
        // InternalKdl.g:3706:4: (lv_expression_5_0= RULE_EXPR )
        // InternalKdl.g:3707:5: lv_expression_5_0= RULE_EXPR
        {
        lv_expression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred159_InternalKdl

    // $ANTLR start synpred176_InternalKdl
    public final void synpred176_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4366:5: ( 'to' )
        // InternalKdl.g:4366:6: 'to'
        {
        match(input,55,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred176_InternalKdl

    // $ANTLR start synpred197_InternalKdl
    public final void synpred197_InternalKdl_fragment() throws RecognitionException {   
        Token lv_mediated_0_0=null;
        Token otherlv_1=null;

        // InternalKdl.g:4806:5: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) )
        // InternalKdl.g:4806:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
        {
        // InternalKdl.g:4806:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
        // InternalKdl.g:4807:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
        {
        // InternalKdl.g:4807:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
        // InternalKdl.g:4808:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
        {
        // InternalKdl.g:4808:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
        // InternalKdl.g:4809:8: lv_mediated_0_0= RULE_LOWERCASE_ID
        {
        lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return ;

        }


        }

        otherlv_1=(Token)match(input,106,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred197_InternalKdl

    // $ANTLR start synpred200_InternalKdl
    public final void synpred200_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject lv_parameters_6_0 = null;


        // InternalKdl.g:4857:5: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) )
        // InternalKdl.g:4857:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
        {
        // InternalKdl.g:4857:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
        // InternalKdl.g:4858:6: ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')'
        {
        // InternalKdl.g:4858:6: ( (lv_name_4_0= rulePathName ) )
        // InternalKdl.g:4859:7: (lv_name_4_0= rulePathName )
        {
        // InternalKdl.g:4859:7: (lv_name_4_0= rulePathName )
        // InternalKdl.g:4860:8: lv_name_4_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
          							
        }
        pushFollow(FOLLOW_55);
        lv_name_4_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_5=(Token)match(input,33,FOLLOW_15); if (state.failed) return ;
        // InternalKdl.g:4881:6: ( (lv_parameters_6_0= ruleParameterList ) )?
        int alt161=2;
        int LA161_0 = input.LA(1);

        if ( ((LA161_0>=RULE_STRING && LA161_0<=RULE_LOWERCASE_ID)||(LA161_0>=RULE_INT && LA161_0<=RULE_CAMELCASE_ID)||(LA161_0>=RULE_ID && LA161_0<=RULE_EXPR)||LA161_0==31||LA161_0==33||LA161_0==44||LA161_0==50||(LA161_0>=90 && LA161_0<=91)||LA161_0==96||LA161_0==99||LA161_0==113) ) {
            alt161=1;
        }
        switch (alt161) {
            case 1 :
                // InternalKdl.g:4882:7: (lv_parameters_6_0= ruleParameterList )
                {
                // InternalKdl.g:4882:7: (lv_parameters_6_0= ruleParameterList )
                // InternalKdl.g:4883:8: lv_parameters_6_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                  							
                }
                pushFollow(FOLLOW_16);
                lv_parameters_6_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_7=(Token)match(input,34,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred200_InternalKdl

    // $ANTLR start synpred201_InternalKdl
    public final void synpred201_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_8_0 = null;


        // InternalKdl.g:4906:5: ( ( (lv_urn_8_0= ruleUrn ) ) )
        // InternalKdl.g:4906:5: ( (lv_urn_8_0= ruleUrn ) )
        {
        // InternalKdl.g:4906:5: ( (lv_urn_8_0= ruleUrn ) )
        // InternalKdl.g:4907:6: (lv_urn_8_0= ruleUrn )
        {
        // InternalKdl.g:4907:6: (lv_urn_8_0= ruleUrn )
        // InternalKdl.g:4908:7: lv_urn_8_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
          						
        }
        pushFollow(FOLLOW_2);
        lv_urn_8_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred201_InternalKdl

    // $ANTLR start synpred202_InternalKdl
    public final void synpred202_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_value_9_0 = null;


        // InternalKdl.g:4926:5: ( ( (lv_value_9_0= ruleLiteral ) ) )
        // InternalKdl.g:4926:5: ( (lv_value_9_0= ruleLiteral ) )
        {
        // InternalKdl.g:4926:5: ( (lv_value_9_0= ruleLiteral ) )
        // InternalKdl.g:4927:6: (lv_value_9_0= ruleLiteral )
        {
        // InternalKdl.g:4927:6: (lv_value_9_0= ruleLiteral )
        // InternalKdl.g:4928:7: lv_value_9_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          							newCompositeNode(grammarAccess.getFunctionAccess().getValueLiteralParserRuleCall_0_1_2_0());
          						
        }
        pushFollow(FOLLOW_2);
        lv_value_9_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred202_InternalKdl

    // $ANTLR start synpred213_InternalKdl
    public final void synpred213_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5194:4: ( ( RULE_INT ) )
        // InternalKdl.g:5194:5: ( RULE_INT )
        {
        // InternalKdl.g:5194:5: ( RULE_INT )
        // InternalKdl.g:5195:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred213_InternalKdl

    // $ANTLR start synpred214_InternalKdl
    public final void synpred214_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5216:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5216:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5216:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:5217:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:5217:5: ( ( '.' ) )
        // InternalKdl.g:5218:6: ( '.' )
        {
        // InternalKdl.g:5218:6: ( '.' )
        // InternalKdl.g:5219:7: '.'
        {
        match(input,103,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:5222:5: ( ( RULE_INT ) )
        // InternalKdl.g:5223:6: ( RULE_INT )
        {
        // InternalKdl.g:5223:6: ( RULE_INT )
        // InternalKdl.g:5224:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred214_InternalKdl

    // $ANTLR start synpred218_InternalKdl
    public final void synpred218_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5265:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5265:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5265:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:5266:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:5266:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:5267:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:5267:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:5268:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=114 && input.LA(1)<=115) ) {
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

        // InternalKdl.g:5275:5: ( '+' | ( ( '-' ) ) )?
        int alt166=3;
        int LA166_0 = input.LA(1);

        if ( (LA166_0==44) ) {
            alt166=1;
        }
        else if ( (LA166_0==113) ) {
            alt166=2;
        }
        switch (alt166) {
            case 1 :
                // InternalKdl.g:5276:6: '+'
                {
                match(input,44,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5278:6: ( ( '-' ) )
                {
                // InternalKdl.g:5278:6: ( ( '-' ) )
                // InternalKdl.g:5279:7: ( '-' )
                {
                // InternalKdl.g:5279:7: ( '-' )
                // InternalKdl.g:5280:8: '-'
                {
                match(input,113,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:5284:5: ( ( RULE_INT ) )
        // InternalKdl.g:5285:6: ( RULE_INT )
        {
        // InternalKdl.g:5285:6: ( RULE_INT )
        // InternalKdl.g:5286:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred218_InternalKdl

    // $ANTLR start synpred228_InternalKdl
    public final void synpred228_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:5595:4: (kw= '-' )
        // InternalKdl.g:5595:4: kw= '-'
        {
        kw=(Token)match(input,113,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred228_InternalKdl

    // $ANTLR start synpred229_InternalKdl
    public final void synpred229_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:5602:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:5602:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred229_InternalKdl

    // $ANTLR start synpred230_InternalKdl
    public final void synpred230_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:5610:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:5610:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred230_InternalKdl

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
    public final boolean synpred213_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred213_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred214_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred214_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred228_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred228_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred230_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred230_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred218_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred218_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred229_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred229_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred155_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred155_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred176_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred176_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred197_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred197_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred154_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred154_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred15_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred156_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred156_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred68_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred68_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred113_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred113_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred73_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred73_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred157_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred157_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred72_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred72_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred63_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred63_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred69_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred69_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred159_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred159_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred62_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred62_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred70_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred70_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalKdl_fragment(); // can never throw exception
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


    protected DFA7 dfa7 = new DFA7(this);
    protected DFA1 dfa1 = new DFA1(this);
    protected DFA42 dfa42 = new DFA42(this);
    protected DFA47 dfa47 = new DFA47(this);
    protected DFA62 dfa62 = new DFA62(this);
    protected DFA65 dfa65 = new DFA65(this);
    protected DFA68 dfa68 = new DFA68(this);
    protected DFA77 dfa77 = new DFA77(this);
    protected DFA90 dfa90 = new DFA90(this);
    protected DFA100 dfa100 = new DFA100(this);
    protected DFA119 dfa119 = new DFA119(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\6\15\uffff";
    static final String dfa_4s = "\1\124\15\uffff";
    static final String dfa_5s = "\1\uffff\1\15\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14";
    static final String dfa_6s = "\1\0\15\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\15\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\2\uffff\3\1\1\uffff\4\1\2\uffff\1\1\10\uffff\1\1\12\uffff\24\1",
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

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 103:6: ( ({...}? => ( ({...}? => (otherlv_1= '@dataflow' ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= '@var' ( (lv_variables_4_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_5= '@val' ( (lv_constants_6_0= ruleParameter ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_7= '@author' ( (lv_authors_8_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_9= '@version' ( (lv_version_10_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= '@klab' ( (lv_klabVersion_12_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= '@worldview' ( (lv_worldview_14_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= '@geometry' ( (lv_geometry_16_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= '@endpoint' ( (lv_endpoint_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= '@namespace' ( ( (lv_package_20_1= rulePathName | lv_package_20_2= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= '@coverage' ( (lv_scale_22_0= ruleFunction ) ) (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_25= '@context' ( (lv_contextUrn_26_0= ruleUrn ) ) ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_0 = input.LA(1);

                         
                        int index7_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA7_0==EOF||LA7_0==RULE_ANNOTATION_ID||(LA7_0>=35 && LA7_0<=37)||(LA7_0>=39 && LA7_0<=42)||LA7_0==45||LA7_0==54||(LA7_0>=65 && LA7_0<=84)) ) {s = 1;}

                        else if ( LA7_0 == 20 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0) ) {s = 2;}

                        else if ( LA7_0 == 21 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1) ) {s = 3;}

                        else if ( LA7_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2) ) {s = 4;}

                        else if ( LA7_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3) ) {s = 5;}

                        else if ( LA7_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4) ) {s = 6;}

                        else if ( LA7_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5) ) {s = 7;}

                        else if ( LA7_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6) ) {s = 8;}

                        else if ( LA7_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7) ) {s = 9;}

                        else if ( LA7_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8) ) {s = 10;}

                        else if ( LA7_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {s = 11;}

                        else if ( LA7_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {s = 12;}

                        else if ( LA7_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {s = 13;}

                         
                        input.seek(index7_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\6\uffff";
    static final String dfa_9s = "\1\uffff\1\4\3\uffff\1\4";
    static final String dfa_10s = "\1\5\1\6\1\uffff\1\5\1\uffff\1\6";
    static final String dfa_11s = "\1\140\1\147\1\uffff\1\5\1\uffff\1\147";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\1\uffff";
    static final String dfa_13s = "\6\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\132\uffff\1\2",
            "\1\4\15\uffff\13\4\1\uffff\1\4\2\uffff\3\4\1\uffff\4\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4\14\uffff\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\4\15\uffff\13\4\1\uffff\1\4\2\uffff\3\4\1\uffff\4\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4\14\uffff\1\2\4\uffff\1\4\1\3"
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
    static final String dfa_15s = "\33\uffff";
    static final String dfa_16s = "\1\6\1\44\1\55\1\7\1\uffff\25\4\1\uffff";
    static final String dfa_17s = "\4\124\1\uffff\25\57\1\uffff";
    static final String dfa_18s = "\4\uffff\1\2\25\uffff\1\1";
    static final String dfa_19s = "\33\uffff}>";
    static final String[] dfa_20s = {
            "\1\4\34\uffff\1\1\1\2\1\3\1\uffff\4\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4",
            "\1\2\1\3\2\uffff\3\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4",
            "\1\4\10\uffff\1\26\12\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\27\1\30\1\31",
            "\1\4\43\uffff\1\4\1\uffff\1\4\10\uffff\1\26\12\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\27\1\30\1\31",
            "",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\32\1\uffff\1\4\45\uffff\2\4",
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "692:2: ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_const_10_0= 'const' ) )? ( (lv_final_11_0= 'final' ) )? ( (lv_optional_12_0= 'optional' ) )? ( ( (lv_exported_13_0= 'export' ) ) | ( (lv_filter_14_0= 'filter' ) ) | ( ( (lv_imported_15_0= 'import' ) ) ( ( (lv_multiple_16_0= 'multiple' ) ) | ( ( (lv_arity_17_0= RULE_INT ) ) ( (lv_minimum_18_0= '+' ) )? ) )? ) )? ( (lv_parameter_19_0= 'parameter' ) )? ( (lv_type_20_0= ruleACTOR ) ) ( (lv_expression_21_0= 'expression' ) )? ( ( (lv_name_22_1= RULE_LOWERCASE_ID | lv_name_22_2= RULE_LOWERCASE_DASHID | lv_name_22_3= RULE_STRING | lv_name_22_4= '*' ) ) ) (otherlv_23= 'extends' ( ( (lv_extended_24_1= RULE_LOWERCASE_ID | lv_extended_24_2= RULE_LOWERCASE_DASHID | lv_extended_24_3= RULE_STRING ) ) ) )? (otherlv_25= 'for' ( (lv_targets_26_0= ruleTARGET ) ) (otherlv_27= ',' ( (lv_targets_28_0= ruleTARGET ) ) )* )? ( (lv_docstring_29_0= RULE_STRING ) )? (otherlv_30= 'label' ( (lv_label_31_0= RULE_STRING ) ) )? (otherlv_32= '{' ( (lv_body_33_0= ruleDataflowBody ) ) otherlv_34= '}' )? ( ( (otherlv_35= 'minimum' ( (lv_rangeMin_36_0= ruleNumber ) ) ) | (otherlv_37= 'maximum' ( (lv_rangeMax_38_0= ruleNumber ) ) ) | (otherlv_39= 'range' ( (lv_rangeMin_40_0= ruleNumber ) ) otherlv_41= 'to' ( (lv_rangeMax_42_0= ruleNumber ) ) ) ) | (otherlv_43= 'values' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) (otherlv_45= ',' ( ( (lv_enumValues_46_1= RULE_STRING | lv_enumValues_46_2= RULE_UPPERCASE_ID | lv_enumValues_46_3= RULE_LOWERCASE_ID | lv_enumValues_46_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_48= 'default' ( (lv_default_49_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_50= 'unit' ( (lv_unit_51_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_52= 'as' ( (lv_localName_53_0= RULE_LOWERCASE_ID ) ) )? (otherlv_54= 'over' ( (lv_coverage_55_0= ruleFunction ) ) (otherlv_56= ',' ( (lv_coverage_57_0= ruleFunction ) ) )* )? ) )";
        }
    }
    static final String dfa_21s = "\12\uffff";
    static final String dfa_22s = "\1\2\11\uffff";
    static final String dfa_23s = "\1\63\4\0\5\uffff";
    static final String dfa_24s = "\1\100\4\0\5\uffff";
    static final String dfa_25s = "\5\uffff\2\3\1\4\1\1\1\2";
    static final String dfa_26s = "\1\3\1\0\1\1\1\2\1\4\5\uffff}>";
    static final String[] dfa_27s = {
            "\1\1\11\uffff\1\3\1\5\1\6\1\4",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "()+ loopback of 1751:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA47_1 = input.LA(1);

                         
                        int index47_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred73_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 7;}

                         
                        input.seek(index47_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA47_2 = input.LA(1);

                         
                        int index47_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred73_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 7;}

                         
                        input.seek(index47_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA47_3 = input.LA(1);

                         
                        int index47_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred68_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {s = 8;}

                        else if ( synpred73_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index47_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA47_0 = input.LA(1);

                         
                        int index47_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA47_0==51) ) {s = 1;}

                        else if ( (LA47_0==EOF) ) {s = 2;}

                        else if ( (LA47_0==61) ) {s = 3;}

                        else if ( (LA47_0==64) ) {s = 4;}

                        else if ( LA47_0 == 62 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 5;}

                        else if ( LA47_0 == 63 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index47_0);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA47_4 = input.LA(1);

                         
                        int index47_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred69_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {s = 9;}

                        else if ( synpred73_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index47_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 47, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_28s = "\25\uffff";
    static final String dfa_29s = "\4\uffff\1\17\14\uffff\1\17\2\uffff\1\17";
    static final String dfa_30s = "\1\4\1\uffff\2\7\1\67\7\uffff\3\7\2\uffff\1\67\2\7\1\67";
    static final String dfa_31s = "\1\161\1\uffff\2\7\1\163\7\uffff\1\7\2\161\2\uffff\1\163\2\7\1\141";
    static final String dfa_32s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\3\1\2\4\uffff";
    static final String dfa_33s = "\25\uffff}>";
    static final String[] dfa_34s = {
            "\1\6\2\uffff\1\4\31\uffff\1\10\12\uffff\1\2\2\uffff\1\13\2\uffff\1\7\47\uffff\2\1\2\uffff\1\5\1\12\11\uffff\1\11\2\uffff\5\11\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\20\44\uffff\2\20\3\uffff\1\17\5\uffff\1\14\12\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\44\uffff\1\22\104\uffff\1\23",
            "\1\24\44\uffff\1\22\104\uffff\1\23",
            "",
            "",
            "\1\20\44\uffff\2\20\3\uffff\1\17\20\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\20\44\uffff\2\20\3\uffff\1\17"
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[][] dfa_34 = unpackEncodedStringArray(dfa_34s);

    class DFA62 extends DFA {

        public DFA62(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 62;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "2512:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
        }
    }
    static final String dfa_35s = "\17\uffff";
    static final String dfa_36s = "\3\uffff\1\12\7\uffff\1\12\2\uffff\1\12";
    static final String dfa_37s = "\1\4\2\7\1\4\2\uffff\3\7\2\uffff\1\4\2\7\1\4";
    static final String dfa_38s = "\1\161\2\7\1\163\2\uffff\1\7\2\161\2\uffff\1\163\2\7\1\161";
    static final String dfa_39s = "\4\uffff\1\3\1\4\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_40s = "\17\uffff}>";
    static final String[] dfa_41s = {
            "\1\4\2\uffff\1\3\44\uffff\1\1\55\uffff\2\5\25\uffff\1\2",
            "\1\3",
            "\1\3",
            "\7\12\2\uffff\2\12\5\uffff\22\12\1\uffff\4\12\1\uffff\2\12\4\uffff\2\12\2\uffff\1\12\1\11\1\uffff\34\12\5\uffff\2\12\4\uffff\1\12\2\uffff\1\12\3\uffff\1\6\2\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "",
            "",
            "\1\13",
            "\1\16\44\uffff\1\14\104\uffff\1\15",
            "\1\16\44\uffff\1\14\104\uffff\1\15",
            "",
            "",
            "\7\12\2\uffff\2\12\5\uffff\22\12\1\uffff\4\12\1\uffff\2\12\4\uffff\2\12\2\uffff\1\12\1\11\1\uffff\34\12\5\uffff\2\12\4\uffff\1\12\2\uffff\1\12\6\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "\1\16",
            "\1\16",
            "\7\12\2\uffff\2\12\5\uffff\22\12\1\uffff\4\12\1\uffff\2\12\4\uffff\2\12\2\uffff\1\12\1\11\1\uffff\34\12\5\uffff\2\12\4\uffff\1\12\2\uffff\1\12\6\uffff\1\12\6\uffff\1\12"
    };

    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[][] dfa_41 = unpackEncodedStringArray(dfa_41s);

    class DFA65 extends DFA {

        public DFA65(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 65;
            this.eot = dfa_35;
            this.eof = dfa_36;
            this.min = dfa_37;
            this.max = dfa_38;
            this.accept = dfa_39;
            this.special = dfa_40;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "2924:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_42s = "\21\uffff";
    static final String dfa_43s = "\3\uffff\1\13\11\uffff\1\13\2\uffff\1\13";
    static final String dfa_44s = "\1\4\2\7\1\4\4\uffff\3\7\2\uffff\1\4\2\7\1\4";
    static final String dfa_45s = "\1\161\2\7\1\163\4\uffff\1\7\2\161\2\uffff\1\163\2\7\1\161";
    static final String dfa_46s = "\4\uffff\1\3\1\4\1\5\1\6\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_47s = "\21\uffff}>";
    static final String[] dfa_48s = {
            "\1\4\1\6\1\uffff\1\3\1\uffff\1\6\3\uffff\1\6\21\uffff\1\7\14\uffff\1\1\55\uffff\2\5\25\uffff\1\2",
            "\1\3",
            "\1\3",
            "\7\13\2\uffff\2\13\5\uffff\22\13\1\uffff\4\13\1\uffff\2\13\4\uffff\2\13\2\uffff\1\13\1\14\1\uffff\34\13\5\uffff\2\13\4\uffff\1\13\2\uffff\1\13\3\uffff\1\10\11\uffff\1\13\1\11\1\12",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\20\44\uffff\1\16\104\uffff\1\17",
            "\1\20\44\uffff\1\16\104\uffff\1\17",
            "",
            "",
            "\7\13\2\uffff\2\13\5\uffff\22\13\1\uffff\4\13\1\uffff\2\13\4\uffff\2\13\2\uffff\1\13\1\14\1\uffff\34\13\5\uffff\2\13\4\uffff\1\13\2\uffff\1\13\15\uffff\1\13\1\11\1\12",
            "\1\20",
            "\1\20",
            "\7\13\2\uffff\2\13\5\uffff\22\13\1\uffff\4\13\1\uffff\2\13\4\uffff\2\13\2\uffff\1\13\1\14\1\uffff\34\13\5\uffff\2\13\4\uffff\1\13\2\uffff\1\13\15\uffff\1\13"
    };

    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final char[] dfa_45 = DFA.unpackEncodedStringToUnsignedChars(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final short[][] dfa_48 = unpackEncodedStringArray(dfa_48s);

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = dfa_42;
            this.eof = dfa_43;
            this.min = dfa_44;
            this.max = dfa_45;
            this.accept = dfa_46;
            this.special = dfa_47;
            this.transition = dfa_48;
        }
        public String getDescription() {
            return "3054:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )";
        }
    }
    static final String dfa_49s = "\27\uffff";
    static final String dfa_50s = "\1\4\6\0\1\uffff\2\0\1\uffff\5\0\7\uffff";
    static final String dfa_51s = "\1\161\6\0\1\uffff\2\0\1\uffff\5\0\7\uffff";
    static final String dfa_52s = "\7\uffff\1\1\10\uffff\1\5\1\7\1\2\1\3\1\10\1\6\1\4";
    static final String dfa_53s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\uffff\1\6\1\7\1\uffff\1\10\1\11\1\12\1\13\1\14\7\uffff}>";
    static final String[] dfa_54s = {
            "\1\4\1\10\1\uffff\1\3\1\15\1\11\1\14\2\uffff\1\7\1\16\20\uffff\1\7\1\uffff\1\17\12\uffff\1\1\5\uffff\1\20\47\uffff\1\5\1\6\4\uffff\1\13\2\uffff\1\21\15\uffff\1\2",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
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

    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final char[] dfa_51 = DFA.unpackEncodedStringToUnsignedChars(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[][] dfa_54 = unpackEncodedStringArray(dfa_54s);

    class DFA77 extends DFA {

        public DFA77(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 77;
            this.eot = dfa_49;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "3604:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA77_1 = input.LA(1);

                         
                        int index77_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index77_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA77_2 = input.LA(1);

                         
                        int index77_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index77_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA77_3 = input.LA(1);

                         
                        int index77_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index77_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA77_4 = input.LA(1);

                         
                        int index77_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred156_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index77_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA77_5 = input.LA(1);

                         
                        int index77_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index77_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA77_6 = input.LA(1);

                         
                        int index77_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index77_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA77_8 = input.LA(1);

                         
                        int index77_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred156_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index77_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA77_9 = input.LA(1);

                         
                        int index77_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 7;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index77_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA77_11 = input.LA(1);

                         
                        int index77_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred156_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index77_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA77_12 = input.LA(1);

                         
                        int index77_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred156_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index77_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA77_13 = input.LA(1);

                         
                        int index77_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred156_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index77_13);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA77_14 = input.LA(1);

                         
                        int index77_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred159_InternalKdl()) ) {s = 21;}

                         
                        input.seek(index77_14);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA77_15 = input.LA(1);

                         
                        int index77_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKdl()) ) {s = 18;}

                        else if ( (synpred157_InternalKdl()) ) {s = 22;}

                         
                        input.seek(index77_15);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 77, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_55s = "\4\uffff\1\20\14\uffff\1\20\2\uffff\1\20";
    static final String dfa_56s = "\1\4\1\uffff\2\7\1\37\7\uffff\3\7\2\uffff\1\37\2\7\1\37";
    static final String dfa_57s = "\1\161\1\uffff\2\7\1\163\7\uffff\1\7\2\161\2\uffff\1\163\2\7\1\145";
    static final String dfa_58s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\2\1\3\4\uffff";
    static final String[] dfa_59s = {
            "\1\6\2\uffff\1\4\6\uffff\1\10\35\uffff\1\2\2\uffff\1\12\52\uffff\2\1\2\uffff\1\5\1\11\2\uffff\1\13\6\uffff\1\7\2\uffff\5\7\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\20\27\uffff\1\17\44\uffff\2\17\6\uffff\2\20\1\uffff\1\14\12\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\44\uffff\1\22\104\uffff\1\23",
            "\1\24\44\uffff\1\22\104\uffff\1\23",
            "",
            "",
            "\1\20\27\uffff\1\17\44\uffff\2\17\6\uffff\2\20\14\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\20\27\uffff\1\17\44\uffff\2\17\6\uffff\2\20"
    };
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final char[] dfa_57 = DFA.unpackEncodedStringToUnsignedChars(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA90 extends DFA {

        public DFA90(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 90;
            this.eot = dfa_28;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_33;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "4291:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )";
        }
    }
    static final String dfa_60s = "\15\uffff";
    static final String dfa_61s = "\1\4\1\0\1\uffff\1\0\11\uffff";
    static final String dfa_62s = "\1\161\1\0\1\uffff\1\0\11\uffff";
    static final String dfa_63s = "\2\uffff\1\2\3\uffff\1\3\4\uffff\1\4\1\1";
    static final String dfa_64s = "\1\uffff\1\0\1\uffff\1\1\11\uffff}>";
    static final String[] dfa_65s = {
            "\1\3\1\1\1\uffff\1\6\1\2\1\uffff\1\2\3\uffff\1\13\35\uffff\1\6\55\uffff\2\6\4\uffff\1\2\20\uffff\1\6",
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
            "",
            ""
    };

    static final short[] dfa_60 = DFA.unpackEncodedString(dfa_60s);
    static final char[] dfa_61 = DFA.unpackEncodedStringToUnsignedChars(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final short[] dfa_63 = DFA.unpackEncodedString(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[][] dfa_65 = unpackEncodedStringArray(dfa_65s);

    class DFA100 extends DFA {

        public DFA100(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 100;
            this.eot = dfa_60;
            this.eof = dfa_60;
            this.min = dfa_61;
            this.max = dfa_62;
            this.accept = dfa_63;
            this.special = dfa_64;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "4856:4: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA100_1 = input.LA(1);

                         
                        int index100_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred200_InternalKdl()) ) {s = 12;}

                        else if ( (synpred201_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index100_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA100_3 = input.LA(1);

                         
                        int index100_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred201_InternalKdl()) ) {s = 2;}

                        else if ( (synpred202_InternalKdl()) ) {s = 6;}

                         
                        input.seek(index100_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 100, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_66s = "\1\5\1\141\1\uffff\1\5\1\uffff\1\141";
    static final String[] dfa_67s = {
            "\1\1\132\uffff\1\2",
            "\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\2\4\uffff\1\4\1\3"
    };
    static final char[] dfa_66 = DFA.unpackEncodedStringToUnsignedChars(dfa_66s);
    static final short[][] dfa_67 = unpackEncodedStringArray(dfa_67s);

    class DFA119 extends DFA {

        public DFA119(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 119;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_66;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_67;
        }
        public String getDescription() {
            return "115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L,0x0000000100000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x004027B97FF00042L,0x00000000001FFFFEL});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000800000000800L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000030L,0x0000000100000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00001002000045B0L,0x000200010C000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x004027B9FFF00042L,0x00000000001FFFFEL});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000530L,0x0000000100000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x004027B800000042L,0x00000000001FFFFEL});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00041006800067B0L,0x000200090C000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000003000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x004027B800000040L,0x00000000001FFFFEL});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00402FB8000000C0L,0x00000000001FFFFEL});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x004037B800000040L,0x00000000001FFFFEL});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000C00000000130L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000800000000130L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x1F77004000000012L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000130L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x1F76004000000012L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000000L,0x0000000003E00000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x1F74004080000012L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x1F74004000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x1F74000000000002L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0xE04827B800000040L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x1F70000000000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000100000000080L,0x0002000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x1E00000000000002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000630L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x1E00000080000002L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x00041002800067B0L,0x000200090C000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x1E00800200001620L,0x0010004000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0xE04027B800000042L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0xE000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000800600001620L,0x0010004000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000800000000002L,0x0010004000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000200001620L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0080000000000000L,0x0000000030000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000030000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0008000000000020L,0x0000000100000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0004100200002090L,0x000200000C000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000600000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x000C900200000090L,0x0003F200CC000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0008000080000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0004900200000090L,0x0003F200CC000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000900000004090L,0x0003F214CC000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000900000004090L,0x0003F204CC000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x000000C400000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000520L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000030000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x00001000000045B0L,0x000200010C000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x0000040000000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000002L,0x000C008000000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000002L,0x000C000000000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000002L,0x000000C000000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000222L,0x0002008000000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000222L,0x0002000000000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000222L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000800200001620L,0x0010004000000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x8000000000000002L});

}
