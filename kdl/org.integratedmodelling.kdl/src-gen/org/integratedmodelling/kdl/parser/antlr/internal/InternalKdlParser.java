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
        	grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15()
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
    // InternalKdl.g:682:1: ruleActorDefinition returns [EObject current=null] : ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? ) ) ;
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
        Token lv_final_10_0=null;
        Token lv_optional_11_0=null;
        Token lv_exported_12_0=null;
        Token lv_filter_13_0=null;
        Token lv_imported_14_0=null;
        Token lv_multiple_15_0=null;
        Token lv_arity_16_0=null;
        Token lv_minimum_17_0=null;
        Token lv_parameter_18_0=null;
        Token lv_expression_20_0=null;
        Token lv_name_21_1=null;
        Token lv_name_21_2=null;
        Token lv_name_21_3=null;
        Token lv_name_21_4=null;
        Token otherlv_22=null;
        Token lv_extended_23_1=null;
        Token lv_extended_23_2=null;
        Token lv_extended_23_3=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        Token lv_docstring_28_0=null;
        Token otherlv_29=null;
        Token lv_label_30_0=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_38=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token lv_enumValues_43_1=null;
        Token lv_enumValues_43_2=null;
        Token lv_enumValues_43_3=null;
        Token lv_enumValues_43_4=null;
        Token otherlv_44=null;
        Token lv_enumValues_45_1=null;
        Token lv_enumValues_45_2=null;
        Token lv_enumValues_45_3=null;
        Token lv_enumValues_45_4=null;
        Token otherlv_47=null;
        Token otherlv_49=null;
        Token otherlv_51=null;
        Token lv_localName_52_0=null;
        Token otherlv_53=null;
        Token otherlv_55=null;
        AntlrDatatypeRuleToken lv_type_3_0 = null;

        EObject lv_annotations_8_0 = null;

        AntlrDatatypeRuleToken lv_type_19_0 = null;

        AntlrDatatypeRuleToken lv_targets_25_0 = null;

        AntlrDatatypeRuleToken lv_targets_27_0 = null;

        EObject lv_body_32_0 = null;

        EObject lv_rangeMin_35_0 = null;

        EObject lv_rangeMax_37_0 = null;

        EObject lv_rangeMin_39_0 = null;

        EObject lv_rangeMax_41_0 = null;

        EObject lv_default_48_0 = null;

        EObject lv_unit_50_0 = null;

        EObject lv_coverage_54_0 = null;

        EObject lv_coverage_56_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15()
        	);

        try {
            // InternalKdl.g:691:2: ( ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? ) ) )
            // InternalKdl.g:692:2: ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? ) )
            {
            // InternalKdl.g:692:2: ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? ) )
            int alt41=2;
            alt41 = dfa41.predict(input);
            switch (alt41) {
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
                    // InternalKdl.g:820:3: ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:820:3: ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:821:4: ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )?
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

                    // InternalKdl.g:854:4: ( (lv_final_10_0= 'final' ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==40) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:855:5: (lv_final_10_0= 'final' )
                            {
                            // InternalKdl.g:855:5: (lv_final_10_0= 'final' )
                            // InternalKdl.g:856:6: lv_final_10_0= 'final'
                            {
                            lv_final_10_0=(Token)match(input,40,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_final_10_0, grammarAccess.getActorDefinitionAccess().getFinalFinalKeyword_1_2_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "final", lv_final_10_0 != null, "final");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:868:4: ( (lv_optional_11_0= 'optional' ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==41) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKdl.g:869:5: (lv_optional_11_0= 'optional' )
                            {
                            // InternalKdl.g:869:5: (lv_optional_11_0= 'optional' )
                            // InternalKdl.g:870:6: lv_optional_11_0= 'optional'
                            {
                            lv_optional_11_0=(Token)match(input,41,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_optional_11_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_3_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "optional", lv_optional_11_0 != null, "optional");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:882:4: ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )?
                    int alt21=4;
                    switch ( input.LA(1) ) {
                        case 36:
                            {
                            alt21=1;
                            }
                            break;
                        case 42:
                            {
                            alt21=2;
                            }
                            break;
                        case 37:
                            {
                            alt21=3;
                            }
                            break;
                    }

                    switch (alt21) {
                        case 1 :
                            // InternalKdl.g:883:5: ( (lv_exported_12_0= 'export' ) )
                            {
                            // InternalKdl.g:883:5: ( (lv_exported_12_0= 'export' ) )
                            // InternalKdl.g:884:6: (lv_exported_12_0= 'export' )
                            {
                            // InternalKdl.g:884:6: (lv_exported_12_0= 'export' )
                            // InternalKdl.g:885:7: lv_exported_12_0= 'export'
                            {
                            lv_exported_12_0=(Token)match(input,36,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_12_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "exported", lv_exported_12_0 != null, "export");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:898:5: ( (lv_filter_13_0= 'filter' ) )
                            {
                            // InternalKdl.g:898:5: ( (lv_filter_13_0= 'filter' ) )
                            // InternalKdl.g:899:6: (lv_filter_13_0= 'filter' )
                            {
                            // InternalKdl.g:899:6: (lv_filter_13_0= 'filter' )
                            // InternalKdl.g:900:7: lv_filter_13_0= 'filter'
                            {
                            lv_filter_13_0=(Token)match(input,42,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_filter_13_0, grammarAccess.getActorDefinitionAccess().getFilterFilterKeyword_1_4_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "filter", lv_filter_13_0 != null, "filter");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:913:5: ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? )
                            {
                            // InternalKdl.g:913:5: ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? )
                            // InternalKdl.g:914:6: ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )?
                            {
                            // InternalKdl.g:914:6: ( (lv_imported_14_0= 'import' ) )
                            // InternalKdl.g:915:7: (lv_imported_14_0= 'import' )
                            {
                            // InternalKdl.g:915:7: (lv_imported_14_0= 'import' )
                            // InternalKdl.g:916:8: lv_imported_14_0= 'import'
                            {
                            lv_imported_14_0=(Token)match(input,37,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_imported_14_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_1_4_2_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getActorDefinitionRule());
                              								}
                              								setWithLastConsumed(current, "imported", lv_imported_14_0 != null, "import");
                              							
                            }

                            }


                            }

                            // InternalKdl.g:928:6: ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )?
                            int alt20=3;
                            int LA20_0 = input.LA(1);

                            if ( (LA20_0==43) ) {
                                alt20=1;
                            }
                            else if ( (LA20_0==RULE_INT) ) {
                                alt20=2;
                            }
                            switch (alt20) {
                                case 1 :
                                    // InternalKdl.g:929:7: ( (lv_multiple_15_0= 'multiple' ) )
                                    {
                                    // InternalKdl.g:929:7: ( (lv_multiple_15_0= 'multiple' ) )
                                    // InternalKdl.g:930:8: (lv_multiple_15_0= 'multiple' )
                                    {
                                    // InternalKdl.g:930:8: (lv_multiple_15_0= 'multiple' )
                                    // InternalKdl.g:931:9: lv_multiple_15_0= 'multiple'
                                    {
                                    lv_multiple_15_0=(Token)match(input,43,FOLLOW_18); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_multiple_15_0, grammarAccess.getActorDefinitionAccess().getMultipleMultipleKeyword_1_4_2_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									setWithLastConsumed(current, "multiple", lv_multiple_15_0 != null, "multiple");
                                      								
                                    }

                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:944:7: ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? )
                                    {
                                    // InternalKdl.g:944:7: ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? )
                                    // InternalKdl.g:945:8: ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )?
                                    {
                                    // InternalKdl.g:945:8: ( (lv_arity_16_0= RULE_INT ) )
                                    // InternalKdl.g:946:9: (lv_arity_16_0= RULE_INT )
                                    {
                                    // InternalKdl.g:946:9: (lv_arity_16_0= RULE_INT )
                                    // InternalKdl.g:947:10: lv_arity_16_0= RULE_INT
                                    {
                                    lv_arity_16_0=(Token)match(input,RULE_INT,FOLLOW_23); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      										newLeafNode(lv_arity_16_0, grammarAccess.getActorDefinitionAccess().getArityINTTerminalRuleCall_1_4_2_1_1_0_0());
                                      									
                                    }
                                    if ( state.backtracking==0 ) {

                                      										if (current==null) {
                                      											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      										}
                                      										setWithLastConsumed(
                                      											current,
                                      											"arity",
                                      											lv_arity_16_0,
                                      											"org.eclipse.xtext.common.Terminals.INT");
                                      									
                                    }

                                    }


                                    }

                                    // InternalKdl.g:963:8: ( (lv_minimum_17_0= '+' ) )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==44) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // InternalKdl.g:964:9: (lv_minimum_17_0= '+' )
                                            {
                                            // InternalKdl.g:964:9: (lv_minimum_17_0= '+' )
                                            // InternalKdl.g:965:10: lv_minimum_17_0= '+'
                                            {
                                            lv_minimum_17_0=(Token)match(input,44,FOLLOW_18); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              										newLeafNode(lv_minimum_17_0, grammarAccess.getActorDefinitionAccess().getMinimumPlusSignKeyword_1_4_2_1_1_1_0());
                                              									
                                            }
                                            if ( state.backtracking==0 ) {

                                              										if (current==null) {
                                              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                              										}
                                              										setWithLastConsumed(current, "minimum", lv_minimum_17_0 != null, "+");
                                              									
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

                    // InternalKdl.g:981:4: ( (lv_parameter_18_0= 'parameter' ) )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==45) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalKdl.g:982:5: (lv_parameter_18_0= 'parameter' )
                            {
                            // InternalKdl.g:982:5: (lv_parameter_18_0= 'parameter' )
                            // InternalKdl.g:983:6: lv_parameter_18_0= 'parameter'
                            {
                            lv_parameter_18_0=(Token)match(input,45,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_parameter_18_0, grammarAccess.getActorDefinitionAccess().getParameterParameterKeyword_1_5_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "parameter", lv_parameter_18_0 != null, "parameter");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:995:4: ( (lv_type_19_0= ruleACTOR ) )
                    // InternalKdl.g:996:5: (lv_type_19_0= ruleACTOR )
                    {
                    // InternalKdl.g:996:5: (lv_type_19_0= ruleACTOR )
                    // InternalKdl.g:997:6: lv_type_19_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_1_6_0());
                      					
                    }
                    pushFollow(FOLLOW_24);
                    lv_type_19_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_19_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1014:4: ( (lv_expression_20_0= 'expression' ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==46) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKdl.g:1015:5: (lv_expression_20_0= 'expression' )
                            {
                            // InternalKdl.g:1015:5: (lv_expression_20_0= 'expression' )
                            // InternalKdl.g:1016:6: lv_expression_20_0= 'expression'
                            {
                            lv_expression_20_0=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_expression_20_0, grammarAccess.getActorDefinitionAccess().getExpressionExpressionKeyword_1_7_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "expression", lv_expression_20_0 != null, "expression");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1028:4: ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) )
                    // InternalKdl.g:1029:5: ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) )
                    {
                    // InternalKdl.g:1029:5: ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) )
                    // InternalKdl.g:1030:6: (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' )
                    {
                    // InternalKdl.g:1030:6: (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' )
                    int alt24=4;
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
                    case 47:
                        {
                        alt24=4;
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
                            // InternalKdl.g:1031:7: lv_name_21_1= RULE_LOWERCASE_ID
                            {
                            lv_name_21_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_21_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_8_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_21_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1046:7: lv_name_21_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_21_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_21_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_1_8_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_21_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1061:7: lv_name_21_3= RULE_STRING
                            {
                            lv_name_21_3=(Token)match(input,RULE_STRING,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_21_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_1_8_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_21_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;
                        case 4 :
                            // InternalKdl.g:1076:7: lv_name_21_4= '*'
                            {
                            lv_name_21_4=(Token)match(input,47,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_21_4, grammarAccess.getActorDefinitionAccess().getNameAsteriskKeyword_1_8_0_3());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "name", lv_name_21_4, null);
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:1089:4: (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==48) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // InternalKdl.g:1090:5: otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) )
                            {
                            otherlv_22=(Token)match(input,48,FOLLOW_27); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_22, grammarAccess.getActorDefinitionAccess().getExtendsKeyword_1_9_0());
                              				
                            }
                            // InternalKdl.g:1094:5: ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) )
                            // InternalKdl.g:1095:6: ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) )
                            {
                            // InternalKdl.g:1095:6: ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) )
                            // InternalKdl.g:1096:7: (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING )
                            {
                            // InternalKdl.g:1096:7: (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING )
                            int alt25=3;
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
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 25, 0, input);

                                throw nvae;
                            }

                            switch (alt25) {
                                case 1 :
                                    // InternalKdl.g:1097:8: lv_extended_23_1= RULE_LOWERCASE_ID
                                    {
                                    lv_extended_23_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_28); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_23_1, grammarAccess.getActorDefinitionAccess().getExtendedLOWERCASE_IDTerminalRuleCall_1_9_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_23_1,
                                      									"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1112:8: lv_extended_23_2= RULE_LOWERCASE_DASHID
                                    {
                                    lv_extended_23_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_28); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_23_2, grammarAccess.getActorDefinitionAccess().getExtendedLOWERCASE_DASHIDTerminalRuleCall_1_9_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_23_2,
                                      									"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1127:8: lv_extended_23_3= RULE_STRING
                                    {
                                    lv_extended_23_3=(Token)match(input,RULE_STRING,FOLLOW_28); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_23_3, grammarAccess.getActorDefinitionAccess().getExtendedSTRINGTerminalRuleCall_1_9_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_23_3,
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

                    // InternalKdl.g:1145:4: (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==49) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalKdl.g:1146:5: otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )*
                            {
                            otherlv_24=(Token)match(input,49,FOLLOW_29); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_24, grammarAccess.getActorDefinitionAccess().getForKeyword_1_10_0());
                              				
                            }
                            // InternalKdl.g:1150:5: ( (lv_targets_25_0= ruleTARGET ) )
                            // InternalKdl.g:1151:6: (lv_targets_25_0= ruleTARGET )
                            {
                            // InternalKdl.g:1151:6: (lv_targets_25_0= ruleTARGET )
                            // InternalKdl.g:1152:7: lv_targets_25_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_1_10_1_0());
                              						
                            }
                            pushFollow(FOLLOW_30);
                            lv_targets_25_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_25_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1169:5: (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )*
                            loop27:
                            do {
                                int alt27=2;
                                int LA27_0 = input.LA(1);

                                if ( (LA27_0==31) ) {
                                    alt27=1;
                                }


                                switch (alt27) {
                            	case 1 :
                            	    // InternalKdl.g:1170:6: otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) )
                            	    {
                            	    otherlv_26=(Token)match(input,31,FOLLOW_29); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_26, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_10_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1174:6: ( (lv_targets_27_0= ruleTARGET ) )
                            	    // InternalKdl.g:1175:7: (lv_targets_27_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:1175:7: (lv_targets_27_0= ruleTARGET )
                            	    // InternalKdl.g:1176:8: lv_targets_27_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_1_10_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_30);
                            	    lv_targets_27_0=ruleTARGET();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"targets",
                            	      									lv_targets_27_0,
                            	      									"org.integratedmodelling.kdl.Kdl.TARGET");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop27;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:1195:4: ( (lv_docstring_28_0= RULE_STRING ) )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==RULE_STRING) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalKdl.g:1196:5: (lv_docstring_28_0= RULE_STRING )
                            {
                            // InternalKdl.g:1196:5: (lv_docstring_28_0= RULE_STRING )
                            // InternalKdl.g:1197:6: lv_docstring_28_0= RULE_STRING
                            {
                            lv_docstring_28_0=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_28_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_11_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_28_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1213:4: (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==38) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalKdl.g:1214:5: otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) )
                            {
                            otherlv_29=(Token)match(input,38,FOLLOW_6); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_29, grammarAccess.getActorDefinitionAccess().getLabelKeyword_1_12_0());
                              				
                            }
                            // InternalKdl.g:1218:5: ( (lv_label_30_0= RULE_STRING ) )
                            // InternalKdl.g:1219:6: (lv_label_30_0= RULE_STRING )
                            {
                            // InternalKdl.g:1219:6: (lv_label_30_0= RULE_STRING )
                            // InternalKdl.g:1220:7: lv_label_30_0= RULE_STRING
                            {
                            lv_label_30_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_label_30_0, grammarAccess.getActorDefinitionAccess().getLabelSTRINGTerminalRuleCall_1_12_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"label",
                              								lv_label_30_0,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1237:4: (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==50) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalKdl.g:1238:5: otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}'
                            {
                            otherlv_31=(Token)match(input,50,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_31, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_13_0());
                              				
                            }
                            // InternalKdl.g:1242:5: ( (lv_body_32_0= ruleDataflowBody ) )
                            // InternalKdl.g:1243:6: (lv_body_32_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1243:6: (lv_body_32_0= ruleDataflowBody )
                            // InternalKdl.g:1244:7: lv_body_32_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_13_1_0());
                              						
                            }
                            pushFollow(FOLLOW_34);
                            lv_body_32_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_32_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_33=(Token)match(input,51,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_33, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_13_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1266:4: ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )?
                    int alt36=3;
                    switch ( input.LA(1) ) {
                        case 52:
                        case 53:
                            {
                            alt36=1;
                            }
                            break;
                        case 54:
                            {
                            int LA36_2 = input.LA(2);

                            if ( (LA36_2==RULE_INT||LA36_2==44||LA36_2==113) ) {
                                alt36=1;
                            }
                            }
                            break;
                        case 56:
                            {
                            alt36=2;
                            }
                            break;
                    }

                    switch (alt36) {
                        case 1 :
                            // InternalKdl.g:1267:5: ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) )
                            {
                            // InternalKdl.g:1267:5: ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) )
                            int alt32=3;
                            switch ( input.LA(1) ) {
                            case 52:
                                {
                                alt32=1;
                                }
                                break;
                            case 53:
                                {
                                alt32=2;
                                }
                                break;
                            case 54:
                                {
                                alt32=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 32, 0, input);

                                throw nvae;
                            }

                            switch (alt32) {
                                case 1 :
                                    // InternalKdl.g:1268:6: (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1268:6: (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) )
                                    // InternalKdl.g:1269:7: otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) )
                                    {
                                    otherlv_34=(Token)match(input,52,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_34, grammarAccess.getActorDefinitionAccess().getMinimumKeyword_1_14_0_0_0());
                                      						
                                    }
                                    // InternalKdl.g:1273:7: ( (lv_rangeMin_35_0= ruleNumber ) )
                                    // InternalKdl.g:1274:8: (lv_rangeMin_35_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1274:8: (lv_rangeMin_35_0= ruleNumber )
                                    // InternalKdl.g:1275:9: lv_rangeMin_35_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_14_0_0_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
                                    lv_rangeMin_35_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMin",
                                      										lv_rangeMin_35_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1294:6: (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1294:6: (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) )
                                    // InternalKdl.g:1295:7: otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) )
                                    {
                                    otherlv_36=(Token)match(input,53,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_36, grammarAccess.getActorDefinitionAccess().getMaximumKeyword_1_14_0_1_0());
                                      						
                                    }
                                    // InternalKdl.g:1299:7: ( (lv_rangeMax_37_0= ruleNumber ) )
                                    // InternalKdl.g:1300:8: (lv_rangeMax_37_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1300:8: (lv_rangeMax_37_0= ruleNumber )
                                    // InternalKdl.g:1301:9: lv_rangeMax_37_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_14_0_1_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
                                    lv_rangeMax_37_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMax",
                                      										lv_rangeMax_37_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1320:6: (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1320:6: (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) )
                                    // InternalKdl.g:1321:7: otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) )
                                    {
                                    otherlv_38=(Token)match(input,54,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_38, grammarAccess.getActorDefinitionAccess().getRangeKeyword_1_14_0_2_0());
                                      						
                                    }
                                    // InternalKdl.g:1325:7: ( (lv_rangeMin_39_0= ruleNumber ) )
                                    // InternalKdl.g:1326:8: (lv_rangeMin_39_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1326:8: (lv_rangeMin_39_0= ruleNumber )
                                    // InternalKdl.g:1327:9: lv_rangeMin_39_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_14_0_2_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_38);
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

                                    otherlv_40=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_40, grammarAccess.getActorDefinitionAccess().getToKeyword_1_14_0_2_2());
                                      						
                                    }
                                    // InternalKdl.g:1348:7: ( (lv_rangeMax_41_0= ruleNumber ) )
                                    // InternalKdl.g:1349:8: (lv_rangeMax_41_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1349:8: (lv_rangeMax_41_0= ruleNumber )
                                    // InternalKdl.g:1350:9: lv_rangeMax_41_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_14_0_2_3_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
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

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1370:5: (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* )
                            {
                            // InternalKdl.g:1370:5: (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* )
                            // InternalKdl.g:1371:6: otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )*
                            {
                            otherlv_42=(Token)match(input,56,FOLLOW_39); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_42, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_14_1_0());
                              					
                            }
                            // InternalKdl.g:1375:6: ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) )
                            // InternalKdl.g:1376:7: ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) )
                            {
                            // InternalKdl.g:1376:7: ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) )
                            // InternalKdl.g:1377:8: (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID )
                            {
                            // InternalKdl.g:1377:8: (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID )
                            int alt33=4;
                            switch ( input.LA(1) ) {
                            case RULE_STRING:
                                {
                                alt33=1;
                                }
                                break;
                            case RULE_UPPERCASE_ID:
                                {
                                alt33=2;
                                }
                                break;
                            case RULE_LOWERCASE_ID:
                                {
                                alt33=3;
                                }
                                break;
                            case RULE_CAMELCASE_ID:
                                {
                                alt33=4;
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
                                    // InternalKdl.g:1378:9: lv_enumValues_43_1= RULE_STRING
                                    {
                                    lv_enumValues_43_1=(Token)match(input,RULE_STRING,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_43_1, grammarAccess.getActorDefinitionAccess().getEnumValuesSTRINGTerminalRuleCall_1_14_1_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_43_1,
                                      										"org.eclipse.xtext.common.Terminals.STRING");
                                      								
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1393:9: lv_enumValues_43_2= RULE_UPPERCASE_ID
                                    {
                                    lv_enumValues_43_2=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_43_2, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_14_1_1_0_1());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_43_2,
                                      										"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                                      								
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1408:9: lv_enumValues_43_3= RULE_LOWERCASE_ID
                                    {
                                    lv_enumValues_43_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_43_3, grammarAccess.getActorDefinitionAccess().getEnumValuesLOWERCASE_IDTerminalRuleCall_1_14_1_1_0_2());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_43_3,
                                      										"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                                      								
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:1423:9: lv_enumValues_43_4= RULE_CAMELCASE_ID
                                    {
                                    lv_enumValues_43_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_40); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_43_4, grammarAccess.getActorDefinitionAccess().getEnumValuesCAMELCASE_IDTerminalRuleCall_1_14_1_1_0_3());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_43_4,
                                      										"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
                                      								
                                    }

                                    }
                                    break;

                            }


                            }


                            }

                            // InternalKdl.g:1440:6: (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )*
                            loop35:
                            do {
                                int alt35=2;
                                int LA35_0 = input.LA(1);

                                if ( (LA35_0==31) ) {
                                    alt35=1;
                                }


                                switch (alt35) {
                            	case 1 :
                            	    // InternalKdl.g:1441:7: otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) )
                            	    {
                            	    otherlv_44=(Token)match(input,31,FOLLOW_39); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_44, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_14_1_2_0());
                            	      						
                            	    }
                            	    // InternalKdl.g:1445:7: ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) )
                            	    // InternalKdl.g:1446:8: ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) )
                            	    {
                            	    // InternalKdl.g:1446:8: ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) )
                            	    // InternalKdl.g:1447:9: (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID )
                            	    {
                            	    // InternalKdl.g:1447:9: (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID )
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
                            	            // InternalKdl.g:1448:10: lv_enumValues_45_1= RULE_STRING
                            	            {
                            	            lv_enumValues_45_1=(Token)match(input,RULE_STRING,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_45_1, grammarAccess.getActorDefinitionAccess().getEnumValuesSTRINGTerminalRuleCall_1_14_1_2_1_0_0());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_45_1,
                            	              											"org.eclipse.xtext.common.Terminals.STRING");
                            	              									
                            	            }

                            	            }
                            	            break;
                            	        case 2 :
                            	            // InternalKdl.g:1463:10: lv_enumValues_45_2= RULE_UPPERCASE_ID
                            	            {
                            	            lv_enumValues_45_2=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_45_2, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_14_1_2_1_0_1());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_45_2,
                            	              											"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                            	              									
                            	            }

                            	            }
                            	            break;
                            	        case 3 :
                            	            // InternalKdl.g:1478:10: lv_enumValues_45_3= RULE_LOWERCASE_ID
                            	            {
                            	            lv_enumValues_45_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_45_3, grammarAccess.getActorDefinitionAccess().getEnumValuesLOWERCASE_IDTerminalRuleCall_1_14_1_2_1_0_2());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_45_3,
                            	              											"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                            	              									
                            	            }

                            	            }
                            	            break;
                            	        case 4 :
                            	            // InternalKdl.g:1493:10: lv_enumValues_45_4= RULE_CAMELCASE_ID
                            	            {
                            	            lv_enumValues_45_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_40); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_45_4, grammarAccess.getActorDefinitionAccess().getEnumValuesCAMELCASE_IDTerminalRuleCall_1_14_1_2_1_0_3());
                            	              									
                            	            }
                            	            if ( state.backtracking==0 ) {

                            	              										if (current==null) {
                            	              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                            	              										}
                            	              										addWithLastConsumed(
                            	              											current,
                            	              											"enumValues",
                            	              											lv_enumValues_45_4,
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
                            	    break loop35;
                                }
                            } while (true);


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1513:4: ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) )
                    // InternalKdl.g:1514:5: ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) )
                    {
                    // InternalKdl.g:1514:5: ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) )
                    // InternalKdl.g:1515:6: ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* )
                    {
                    getUnorderedGroupHelper().enter(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15());
                    // InternalKdl.g:1518:6: ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* )
                    // InternalKdl.g:1519:7: ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )*
                    {
                    // InternalKdl.g:1519:7: ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )*
                    loop37:
                    do {
                        int alt37=3;
                        int LA37_0 = input.LA(1);

                        if ( LA37_0 == 57 && getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0) ) {
                            alt37=1;
                        }
                        else if ( LA37_0 == 58 && getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1) ) {
                            alt37=2;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // InternalKdl.g:1520:5: ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) )
                    	    {
                    	    // InternalKdl.g:1520:5: ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) )
                    	    // InternalKdl.g:1521:6: {...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) )
                    	    {
                    	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0)");
                    	    }
                    	    // InternalKdl.g:1521:116: ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) )
                    	    // InternalKdl.g:1522:7: ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) )
                    	    {
                    	    getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0);
                    	    // InternalKdl.g:1525:10: ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) )
                    	    // InternalKdl.g:1525:11: {...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) )
                    	    {
                    	    if ( !((true)) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "true");
                    	    }
                    	    // InternalKdl.g:1525:20: (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) )
                    	    // InternalKdl.g:1525:21: otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) )
                    	    {
                    	    otherlv_47=(Token)match(input,57,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      										newLeafNode(otherlv_47, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_15_0_0());
                    	      									
                    	    }
                    	    // InternalKdl.g:1529:10: ( (lv_default_48_0= ruleValue ) )
                    	    // InternalKdl.g:1530:11: (lv_default_48_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:1530:11: (lv_default_48_0= ruleValue )
                    	    // InternalKdl.g:1531:12: lv_default_48_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      												newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_15_0_1_0());
                    	      											
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_default_48_0=ruleValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      												if (current==null) {
                    	      													current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      												}
                    	      												set(
                    	      													current,
                    	      													"default",
                    	      													lv_default_48_0,
                    	      													"org.integratedmodelling.kdl.Kdl.Value");
                    	      												afterParserOrEnumRuleCall();
                    	      											
                    	    }

                    	    }


                    	    }


                    	    }


                    	    }

                    	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15());

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:1554:5: ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) )
                    	    {
                    	    // InternalKdl.g:1554:5: ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) )
                    	    // InternalKdl.g:1555:6: {...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) )
                    	    {
                    	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1)");
                    	    }
                    	    // InternalKdl.g:1555:116: ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) )
                    	    // InternalKdl.g:1556:7: ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) )
                    	    {
                    	    getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1);
                    	    // InternalKdl.g:1559:10: ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) )
                    	    // InternalKdl.g:1559:11: {...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) )
                    	    {
                    	    if ( !((true)) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "true");
                    	    }
                    	    // InternalKdl.g:1559:20: (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) )
                    	    // InternalKdl.g:1559:21: otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) )
                    	    {
                    	    otherlv_49=(Token)match(input,58,FOLLOW_42); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      										newLeafNode(otherlv_49, grammarAccess.getActorDefinitionAccess().getUnitKeyword_1_15_1_0());
                    	      									
                    	    }
                    	    // InternalKdl.g:1563:10: ( (lv_unit_50_0= ruleUnit ) )
                    	    // InternalKdl.g:1564:11: (lv_unit_50_0= ruleUnit )
                    	    {
                    	    // InternalKdl.g:1564:11: (lv_unit_50_0= ruleUnit )
                    	    // InternalKdl.g:1565:12: lv_unit_50_0= ruleUnit
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      												newCompositeNode(grammarAccess.getActorDefinitionAccess().getUnitUnitParserRuleCall_1_15_1_1_0());
                    	      											
                    	    }
                    	    pushFollow(FOLLOW_37);
                    	    lv_unit_50_0=ruleUnit();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      												if (current==null) {
                    	      													current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      												}
                    	      												set(
                    	      													current,
                    	      													"unit",
                    	      													lv_unit_50_0,
                    	      													"org.integratedmodelling.kdl.Kdl.Unit");
                    	      												afterParserOrEnumRuleCall();
                    	      											
                    	    }

                    	    }


                    	    }


                    	    }


                    	    }

                    	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15());

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);


                    }


                    }

                    getUnorderedGroupHelper().leave(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15());

                    }

                    // InternalKdl.g:1595:4: (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==59) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // InternalKdl.g:1596:5: otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_51=(Token)match(input,59,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_51, grammarAccess.getActorDefinitionAccess().getAsKeyword_1_16_0());
                              				
                            }
                            // InternalKdl.g:1600:5: ( (lv_localName_52_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:1601:6: (lv_localName_52_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:1601:6: (lv_localName_52_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:1602:7: lv_localName_52_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_52_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_43); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_52_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_1_16_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_52_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1619:4: (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==60) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // InternalKdl.g:1620:5: otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )*
                            {
                            otherlv_53=(Token)match(input,60,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_53, grammarAccess.getActorDefinitionAccess().getOverKeyword_1_17_0());
                              				
                            }
                            // InternalKdl.g:1624:5: ( (lv_coverage_54_0= ruleFunction ) )
                            // InternalKdl.g:1625:6: (lv_coverage_54_0= ruleFunction )
                            {
                            // InternalKdl.g:1625:6: (lv_coverage_54_0= ruleFunction )
                            // InternalKdl.g:1626:7: lv_coverage_54_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_1_17_1_0());
                              						
                            }
                            pushFollow(FOLLOW_44);
                            lv_coverage_54_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_54_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1643:5: (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )*
                            loop39:
                            do {
                                int alt39=2;
                                int LA39_0 = input.LA(1);

                                if ( (LA39_0==31) ) {
                                    alt39=1;
                                }


                                switch (alt39) {
                            	case 1 :
                            	    // InternalKdl.g:1644:6: otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) )
                            	    {
                            	    otherlv_55=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_55, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_17_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1648:6: ( (lv_coverage_56_0= ruleFunction ) )
                            	    // InternalKdl.g:1649:7: (lv_coverage_56_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:1649:7: (lv_coverage_56_0= ruleFunction )
                            	    // InternalKdl.g:1650:8: lv_coverage_56_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_1_17_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_44);
                            	    lv_coverage_56_0=ruleFunction();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"coverage",
                            	      									lv_coverage_56_0,
                            	      									"org.integratedmodelling.kdl.Kdl.Function");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop39;
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
    // InternalKdl.g:1677:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1681:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1682:2: iv_ruleDataflowBody= ruleDataflowBody EOF
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
    // InternalKdl.g:1691:1: ruleDataflowBody returns [EObject current=null] : ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
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
            // InternalKdl.g:1700:2: ( ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1701:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1701:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1702:3: () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1702:3: ()
            // InternalKdl.g:1703:4: 
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

            // InternalKdl.g:1712:3: ( (lv_dataflows_1_0= ruleActorDefinition ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==RULE_ANNOTATION_ID||(LA42_0>=35 && LA42_0<=37)||(LA42_0>=39 && LA42_0<=42)||LA42_0==45||LA42_0==54||(LA42_0>=65 && LA42_0<=84)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalKdl.g:1713:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1713:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    // InternalKdl.g:1714:5: lv_dataflows_1_0= ruleActorDefinition
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
            	    break loop42;
                }
            } while (true);

            // InternalKdl.g:1731:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1732:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1732:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1733:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());
            // InternalKdl.g:1736:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1737:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1737:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+
            int cnt46=0;
            loop46:
            do {
                int alt46=4;
                alt46 = dfa46.predict(input);
                switch (alt46) {
            	case 1 :
            	    // InternalKdl.g:1738:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1738:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1739:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKdl.g:1739:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1740:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
            	    // InternalKdl.g:1743:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1743:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1743:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1743:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
            	    {
            	    otherlv_3=(Token)match(input,61,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_3, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1747:9: ( (lv_geometry_4_0= ruleGeometry ) )
            	    // InternalKdl.g:1748:10: (lv_geometry_4_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1748:10: (lv_geometry_4_0= ruleGeometry )
            	    // InternalKdl.g:1749:11: lv_geometry_4_0= ruleGeometry
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
            	    // InternalKdl.g:1772:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
            	    {
            	    // InternalKdl.g:1772:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
            	    // InternalKdl.g:1773:5: {...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKdl.g:1773:109: ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
            	    // InternalKdl.g:1774:6: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
            	    // InternalKdl.g:1777:9: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
            	    // InternalKdl.g:1777:10: {...}? => ( (lv_computations_5_0= ruleComputation ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1777:19: ( (lv_computations_5_0= ruleComputation ) )
            	    // InternalKdl.g:1777:20: (lv_computations_5_0= ruleComputation )
            	    {
            	    // InternalKdl.g:1777:20: (lv_computations_5_0= ruleComputation )
            	    // InternalKdl.g:1778:10: lv_computations_5_0= ruleComputation
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
            	    // InternalKdl.g:1800:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1800:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
            	    // InternalKdl.g:1801:5: {...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKdl.g:1801:109: ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
            	    // InternalKdl.g:1802:6: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
            	    // InternalKdl.g:1805:9: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
            	    // InternalKdl.g:1805:10: {...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1805:19: ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
            	    // InternalKdl.g:1805:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
            	    {
            	    // InternalKdl.g:1805:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )?
            	    int alt43=2;
            	    int LA43_0 = input.LA(1);

            	    if ( (LA43_0==62) ) {
            	        int LA43_1 = input.LA(2);

            	        if ( (synpred69_InternalKdl()) ) {
            	            alt43=1;
            	        }
            	    }
            	    switch (alt43) {
            	        case 1 :
            	            // InternalKdl.g:1806:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
            	            {
            	            otherlv_6=(Token)match(input,62,FOLLOW_47); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_6, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_2_2_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1810:10: ( (lv_metadata_7_0= ruleMetadata ) )
            	            // InternalKdl.g:1811:11: (lv_metadata_7_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1811:11: (lv_metadata_7_0= ruleMetadata )
            	            // InternalKdl.g:1812:12: lv_metadata_7_0= ruleMetadata
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

            	    // InternalKdl.g:1830:9: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
            	    int alt45=2;
            	    int LA45_0 = input.LA(1);

            	    if ( (LA45_0==63) ) {
            	        int LA45_1 = input.LA(2);

            	        if ( (synpred71_InternalKdl()) ) {
            	            alt45=1;
            	        }
            	    }
            	    switch (alt45) {
            	        case 1 :
            	            // InternalKdl.g:1831:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
            	            {
            	            otherlv_8=(Token)match(input,63,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_8, grammarAccess.getDataflowBodyAccess().getClassKeyword_2_2_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1835:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
            	            // InternalKdl.g:1836:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
            	            {
            	            // InternalKdl.g:1836:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
            	            // InternalKdl.g:1837:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
            	            {
            	            // InternalKdl.g:1837:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
            	            int alt44=2;
            	            int LA44_0 = input.LA(1);

            	            if ( (LA44_0==RULE_LOWERCASE_ID) ) {
            	                alt44=1;
            	            }
            	            else if ( (LA44_0==RULE_STRING) ) {
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
            	                    // InternalKdl.g:1838:13: lv_javaClass_9_1= ruleJavaClass
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
            	                    // InternalKdl.g:1854:13: lv_javaClass_9_2= RULE_STRING
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
            	    if ( cnt46 >= 1 ) break loop46;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
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
    // InternalKdl.g:1893:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1893:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1894:2: iv_ruleComputation= ruleComputation EOF
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
    // InternalKdl.g:1900:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1906:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1907:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1907:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1908:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,64,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1912:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1913:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1913:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1914:5: lv_functions_1_0= ruleFunction
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

            // InternalKdl.g:1931:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==31) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalKdl.g:1932:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1936:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1937:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1937:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1938:6: lv_functions_3_0= ruleFunction
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
            	    break loop47;
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
    // InternalKdl.g:1960:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1960:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1961:2: iv_ruleGeometry= ruleGeometry EOF
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
    // InternalKdl.g:1967:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1973:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1974:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1974:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==47) ) {
                alt49=1;
            }
            else if ( (LA49_0==RULE_SHAPE) ) {
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
                    // InternalKdl.g:1975:3: kw= '*'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1981:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1981:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1982:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_44); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:1989:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==31) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // InternalKdl.g:1990:5: kw= ',' this_SHAPE_3= RULE_SHAPE
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
                    	    break loop48;
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
    // InternalKdl.g:2008:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:2008:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:2009:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalKdl.g:2015:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2021:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:2022:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:2022:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:2023:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:2023:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:2024:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:2024:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:2025:5: lv_name_0_0= RULE_LOWERCASE_ID
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

            // InternalKdl.g:2041:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:2042:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:2042:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:2043:5: lv_value_1_0= ruleValue
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

            // InternalKdl.g:2060:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==RULE_STRING) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalKdl.g:2061:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:2061:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:2062:5: lv_docstring_2_0= RULE_STRING
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
    // InternalKdl.g:2082:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:2082:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:2083:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKdl.g:2089:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKdl.g:2095:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKdl.g:2096:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKdl.g:2096:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_LOWERCASE_ID||(LA52_0>=RULE_UPPERCASE_ID && LA52_0<=RULE_CAMELCASE_ID)||LA52_0==RULE_BACKCASE_ID) ) {
                alt52=1;
            }
            else if ( (LA52_0==33) ) {
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
                    // InternalKdl.g:2097:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) )
                    {
                    // InternalKdl.g:2097:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) )
                    // InternalKdl.g:2098:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) )
                    {
                    // InternalKdl.g:2098:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) )
                    // InternalKdl.g:2099:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID )
                    {
                    // InternalKdl.g:2099:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID )
                    int alt51=4;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt51=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt51=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt51=3;
                        }
                        break;
                    case RULE_BACKCASE_ID:
                        {
                        alt51=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 51, 0, input);

                        throw nvae;
                    }

                    switch (alt51) {
                        case 1 :
                            // InternalKdl.g:2100:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:2115:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKdl.g:2130:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                            // InternalKdl.g:2145:6: lv_id_0_4= RULE_BACKCASE_ID
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
                    // InternalKdl.g:2163:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKdl.g:2163:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKdl.g:2164:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:2168:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKdl.g:2169:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKdl.g:2169:5: (lv_unit_2_0= ruleUnit )
                    // InternalKdl.g:2170:6: lv_unit_2_0= ruleUnit
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
    // InternalKdl.g:2196:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:2196:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:2197:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKdl.g:2203:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2209:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:2210:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:2210:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:2211:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:2211:3: ()
            // InternalKdl.g:2212:4: 
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

            // InternalKdl.g:2221:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_LOWERCASE_ID||(LA53_0>=RULE_UPPERCASE_ID && LA53_0<=RULE_CAMELCASE_ID)||LA53_0==RULE_BACKCASE_ID||LA53_0==33) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalKdl.g:2222:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:2222:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:2223:5: lv_root_1_0= ruleUnitElement
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

            // InternalKdl.g:2240:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==47||LA54_0==102||LA54_0==116) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalKdl.g:2241:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:2241:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:2242:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:2248:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:2249:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:2249:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:2250:7: lv_connectors_2_0= ruleUnitOp
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

            	    // InternalKdl.g:2268:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:2269:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:2269:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:2270:6: lv_units_3_0= ruleUnitElement
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
            	    break loop54;
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
    // InternalKdl.g:2292:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:2292:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:2293:2: iv_ruleACTOR= ruleACTOR EOF
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
    // InternalKdl.g:2299:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2305:2: ( (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' ) )
            // InternalKdl.g:2306:2: (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' )
            {
            // InternalKdl.g:2306:2: (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' )
            int alt55=21;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt55=1;
                }
                break;
            case 66:
                {
                alt55=2;
                }
                break;
            case 67:
                {
                alt55=3;
                }
                break;
            case 68:
                {
                alt55=4;
                }
                break;
            case 69:
                {
                alt55=5;
                }
                break;
            case 70:
                {
                alt55=6;
                }
                break;
            case 71:
                {
                alt55=7;
                }
                break;
            case 72:
                {
                alt55=8;
                }
                break;
            case 73:
                {
                alt55=9;
                }
                break;
            case 74:
                {
                alt55=10;
                }
                break;
            case 75:
                {
                alt55=11;
                }
                break;
            case 76:
                {
                alt55=12;
                }
                break;
            case 77:
                {
                alt55=13;
                }
                break;
            case 78:
                {
                alt55=14;
                }
                break;
            case 79:
                {
                alt55=15;
                }
                break;
            case 80:
                {
                alt55=16;
                }
                break;
            case 81:
                {
                alt55=17;
                }
                break;
            case 54:
                {
                alt55=18;
                }
                break;
            case 82:
                {
                alt55=19;
                }
                break;
            case 83:
                {
                alt55=20;
                }
                break;
            case 84:
                {
                alt55=21;
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
                    // InternalKdl.g:2307:3: kw= 'object'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2313:3: kw= 'event'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getEventKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2319:3: kw= 'observation'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObservationKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2325:3: kw= 'value'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2331:3: kw= 'process'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:2337:3: kw= 'number'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:2343:3: kw= 'concept'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:2349:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:2355:3: kw= 'text'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:2361:3: kw= 'list'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getListKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:2367:3: kw= 'table'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTableKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:2373:3: kw= 'map'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getMapKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:2379:3: kw= 'extent'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:2385:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalKdl.g:2391:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    // InternalKdl.g:2397:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    // InternalKdl.g:2403:3: kw= 'enum'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getEnumKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    // InternalKdl.g:2409:3: kw= 'range'
                    {
                    kw=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getRangeKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    // InternalKdl.g:2415:3: kw= 'void'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    // InternalKdl.g:2421:3: kw= 'partition'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getPartitionKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    // InternalKdl.g:2427:3: kw= 'resolve'
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
    // InternalKdl.g:2436:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:2436:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:2437:2: iv_ruleTARGET= ruleTARGET EOF
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
    // InternalKdl.g:2443:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2449:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' ) )
            // InternalKdl.g:2450:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' )
            {
            // InternalKdl.g:2450:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' )
            int alt56=5;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt56=1;
                }
                break;
            case 86:
                {
                alt56=2;
                }
                break;
            case 87:
                {
                alt56=3;
                }
                break;
            case 88:
                {
                alt56=4;
                }
                break;
            case 89:
                {
                alt56=5;
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
                    // InternalKdl.g:2451:3: kw= 'models'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2457:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2463:3: kw= 'observers'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getObserversKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2469:3: kw= 'definitions'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getDefinitionsKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2475:3: kw= 'dependencies'
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
    // InternalKdl.g:2484:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:2484:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:2485:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
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
    // InternalKdl.g:2491:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
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
            // InternalKdl.g:2497:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:2498:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:2498:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt61=10;
            alt61 = dfa61.predict(input);
            switch (alt61) {
                case 1 :
                    // InternalKdl.g:2499:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:2499:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==90) ) {
                        alt57=1;
                    }
                    else if ( (LA57_0==91) ) {
                        alt57=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 57, 0, input);

                        throw nvae;
                    }
                    switch (alt57) {
                        case 1 :
                            // InternalKdl.g:2500:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:2500:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:2501:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:2501:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:2502:6: lv_boolean_0_0= 'true'
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
                            // InternalKdl.g:2515:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:2515:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:2516:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:2516:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:2517:6: lv_boolean_1_0= 'false'
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
                    // InternalKdl.g:2531:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:2531:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:2532:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:2532:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:2533:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:2533:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:2534:6: lv_int0_2_0= ruleNumber
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

                    // InternalKdl.g:2551:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt58=3;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==92) ) {
                        alt58=1;
                    }
                    else if ( (LA58_0==93) ) {
                        alt58=2;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalKdl.g:2552:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2552:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:2553:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:2553:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:2554:7: lv_leftLimit_3_0= 'inclusive'
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
                            // InternalKdl.g:2567:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,93,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:2572:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:2573:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:2579:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:2580:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:2584:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:2585:6: lv_int1_6_0= ruleNumber
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

                    // InternalKdl.g:2602:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
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
                            // InternalKdl.g:2603:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2603:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:2604:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:2604:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:2605:7: lv_rightLimit_7_0= 'inclusive'
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
                            // InternalKdl.g:2618:5: otherlv_8= 'exclusive'
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
                    // InternalKdl.g:2625:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2625:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:2626:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:2626:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:2627:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:2645:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:2645:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:2646:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,94,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:2650:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:2651:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:2651:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:2652:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:2671:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2671:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:2672:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:2672:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:2673:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:2690:3: ( (lv_map_13_0= ruleMap ) )
                    {
                    // InternalKdl.g:2690:3: ( (lv_map_13_0= ruleMap ) )
                    // InternalKdl.g:2691:4: (lv_map_13_0= ruleMap )
                    {
                    // InternalKdl.g:2691:4: (lv_map_13_0= ruleMap )
                    // InternalKdl.g:2692:5: lv_map_13_0= ruleMap
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
                    // InternalKdl.g:2710:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:2710:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:2711:4: otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,33,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:2715:4: ( (lv_toResolve_15_0= RULE_STRING ) )
                    // InternalKdl.g:2716:5: (lv_toResolve_15_0= RULE_STRING )
                    {
                    // InternalKdl.g:2716:5: (lv_toResolve_15_0= RULE_STRING )
                    // InternalKdl.g:2717:6: lv_toResolve_15_0= RULE_STRING
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

                    // InternalKdl.g:2733:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==31) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // InternalKdl.g:2734:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    {
                    	    // InternalKdl.g:2734:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:2735:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,31,FOLLOW_6); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:2741:5: ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    // InternalKdl.g:2742:6: (lv_toResolve_17_0= RULE_STRING )
                    	    {
                    	    // InternalKdl.g:2742:6: (lv_toResolve_17_0= RULE_STRING )
                    	    // InternalKdl.g:2743:7: lv_toResolve_17_0= RULE_STRING
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
                    	    break loop60;
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
                    // InternalKdl.g:2766:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2766:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:2767:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2767:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:2768:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:2768:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:2769:6: lv_op_19_0= ruleREL_OPERATOR
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

                    // InternalKdl.g:2786:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:2787:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:2787:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:2788:6: lv_expression_20_0= ruleNumber
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
                    // InternalKdl.g:2807:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:2807:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:2808:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:2808:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:2809:5: lv_nodata_21_0= 'unknown'
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
                    // InternalKdl.g:2822:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:2822:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:2823:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:2823:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:2824:5: lv_star_22_0= '*'
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
    // InternalKdl.g:2840:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:2840:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:2841:2: iv_ruleList= ruleList EOF
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
    // InternalKdl.g:2847:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2853:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:2854:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:2854:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:2855:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:2855:3: ()
            // InternalKdl.g:2856:4: 
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
            // InternalKdl.g:2869:3: ( (lv_contents_2_0= ruleValue ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( ((LA62_0>=RULE_STRING && LA62_0<=RULE_LOWERCASE_ID)||(LA62_0>=RULE_INT && LA62_0<=RULE_CAMELCASE_ID)||(LA62_0>=RULE_ID && LA62_0<=RULE_EXPR)||LA62_0==31||LA62_0==33||LA62_0==44||LA62_0==50||(LA62_0>=90 && LA62_0<=91)||LA62_0==96||LA62_0==99||LA62_0==113) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalKdl.g:2870:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:2870:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:2871:5: lv_contents_2_0= ruleValue
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
            	    break loop62;
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
    // InternalKdl.g:2896:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:2896:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:2897:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKdl.g:2903:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
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
            // InternalKdl.g:2909:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:2910:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:2910:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt64=4;
            alt64 = dfa64.predict(input);
            switch (alt64) {
                case 1 :
                    // InternalKdl.g:2911:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2911:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2912:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2912:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2913:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:2931:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2931:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:2932:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2932:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:2933:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:2933:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:2934:6: lv_from_1_0= ruleNumber
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
                    // InternalKdl.g:2955:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:2956:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2956:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:2957:6: lv_to_3_0= ruleNumber
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
                    // InternalKdl.g:2976:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2976:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2977:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2977:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2978:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:2995:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2995:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:2996:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:2996:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:2997:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:2997:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==90) ) {
                        alt63=1;
                    }
                    else if ( (LA63_0==91) ) {
                        alt63=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 63, 0, input);

                        throw nvae;
                    }
                    switch (alt63) {
                        case 1 :
                            // InternalKdl.g:2998:6: lv_boolean_5_1= 'true'
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
                            // InternalKdl.g:3009:6: lv_boolean_5_2= 'false'
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
    // InternalKdl.g:3026:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:3026:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:3027:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
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
    // InternalKdl.g:3033:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
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
            // InternalKdl.g:3039:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:3040:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:3040:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt67=6;
            alt67 = dfa67.predict(input);
            switch (alt67) {
                case 1 :
                    // InternalKdl.g:3041:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3041:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:3042:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3042:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:3043:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3043:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:3044:6: lv_from_0_0= ruleNumber
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

                    // InternalKdl.g:3061:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:3062:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:3068:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:3069:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3073:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:3074:6: lv_to_2_0= ruleNumber
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
                    // InternalKdl.g:3093:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3093:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:3094:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3094:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:3095:5: lv_number_3_0= ruleNumber
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
                    // InternalKdl.g:3113:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3113:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3114:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3114:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3115:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3132:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3132:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3133:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3133:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3134:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3134:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==90) ) {
                        alt65=1;
                    }
                    else if ( (LA65_0==91) ) {
                        alt65=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 65, 0, input);

                        throw nvae;
                    }
                    switch (alt65) {
                        case 1 :
                            // InternalKdl.g:3135:6: lv_boolean_5_1= 'true'
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
                            // InternalKdl.g:3146:6: lv_boolean_5_2= 'false'
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
                    // InternalKdl.g:3160:3: ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKdl.g:3160:3: ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKdl.g:3161:4: ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3161:4: ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3162:5: (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3162:5: (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID )
                    int alt66=3;
                    switch ( input.LA(1) ) {
                    case RULE_ID:
                        {
                        alt66=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt66=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt66=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 66, 0, input);

                        throw nvae;
                    }

                    switch (alt66) {
                        case 1 :
                            // InternalKdl.g:3163:6: lv_id_6_1= RULE_ID
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
                            // InternalKdl.g:3178:6: lv_id_6_2= RULE_LOWERCASE_ID
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
                            // InternalKdl.g:3193:6: lv_id_6_3= RULE_UPPERCASE_ID
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
                    // InternalKdl.g:3211:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:3211:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:3212:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:3212:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:3213:5: lv_comma_7_0= ','
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
    // InternalKdl.g:3229:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:3229:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:3230:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
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
    // InternalKdl.g:3236:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3242:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:3243:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:3243:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt69=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 44:
            case 113:
                {
                alt69=1;
                }
                break;
            case RULE_STRING:
                {
                alt69=2;
                }
                break;
            case 90:
            case 91:
                {
                alt69=3;
                }
                break;
            case RULE_ID:
                {
                alt69=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalKdl.g:3244:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3244:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:3245:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3245:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:3246:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:3264:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3264:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:3265:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:3265:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:3266:5: lv_string_1_0= RULE_STRING
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
                    // InternalKdl.g:3283:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3283:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:3284:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:3284:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:3285:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:3285:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==90) ) {
                        alt68=1;
                    }
                    else if ( (LA68_0==91) ) {
                        alt68=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 68, 0, input);

                        throw nvae;
                    }
                    switch (alt68) {
                        case 1 :
                            // InternalKdl.g:3286:6: lv_boolean_2_1= 'true'
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
                            // InternalKdl.g:3297:6: lv_boolean_2_2= 'false'
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
                    // InternalKdl.g:3311:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:3311:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:3312:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:3312:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:3313:5: lv_id_3_0= RULE_ID
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
    // InternalKdl.g:3333:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:3333:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:3334:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKdl.g:3340:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
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
            // InternalKdl.g:3346:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:3347:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:3347:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:3348:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:3348:3: ()
            // InternalKdl.g:3349:4: 
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
            // InternalKdl.g:3362:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==RULE_LOWERCASE_ID) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // InternalKdl.g:3363:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:3363:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:3364:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:3364:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:3365:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:3365:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt70=2;
            	    int LA70_0 = input.LA(1);

            	    if ( (LA70_0==RULE_LOWERCASE_ID) ) {
            	        int LA70_1 = input.LA(2);

            	        if ( (LA70_1==97||LA70_1==103) ) {
            	            alt70=2;
            	        }
            	        else if ( (LA70_1==RULE_STRING||LA70_1==RULE_INT||LA70_1==RULE_ID||LA70_1==33||LA70_1==44||LA70_1==50||(LA70_1>=90 && LA70_1<=91)||LA70_1==113) ) {
            	            alt70=1;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 70, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 70, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt70) {
            	        case 1 :
            	            // InternalKdl.g:3366:7: lv_ids_2_1= RULE_LOWERCASE_ID
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
            	            // InternalKdl.g:3381:7: lv_ids_2_2= rulePropertyId
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

            	    // InternalKdl.g:3399:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:3400:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:3400:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:3401:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:3401:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt71=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 44:
            	    case 90:
            	    case 91:
            	    case 113:
            	        {
            	        alt71=1;
            	        }
            	        break;
            	    case 50:
            	        {
            	        alt71=2;
            	        }
            	        break;
            	    case 33:
            	        {
            	        alt71=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 71, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt71) {
            	        case 1 :
            	            // InternalKdl.g:3402:7: lv_values_3_1= ruleLiteralOrID
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
            	            // InternalKdl.g:3418:7: lv_values_3_2= ruleMetadata
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
            	            // InternalKdl.g:3434:7: lv_values_3_3= ruleList
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
            	    break loop72;
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
    // InternalKdl.g:3461:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:3461:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:3462:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKdl.g:3468:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
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
            // InternalKdl.g:3474:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:3475:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:3475:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt75=2;
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
                alt75=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                int LA75_2 = input.LA(2);

                if ( (LA75_2==EOF||LA75_2==31||(LA75_2>=33 && LA75_2<=34)||(LA75_2>=97 && LA75_2<=98)||(LA75_2>=102 && LA75_2<=103)||(LA75_2>=106 && LA75_2<=107)) ) {
                    alt75=1;
                }
                else if ( ((LA75_2>=104 && LA75_2<=105)) ) {
                    alt75=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 75, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                int LA75_3 = input.LA(2);

                if ( (LA75_3==EOF||LA75_3==31||LA75_3==34||LA75_3==98||(LA75_3>=102 && LA75_3<=103)||LA75_3==106) ) {
                    alt75=1;
                }
                else if ( ((LA75_3>=104 && LA75_3<=105)) ) {
                    alt75=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 75, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // InternalKdl.g:3476:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:3476:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:3477:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:3477:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:3478:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:3478:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:3479:6: lv_values_0_0= ruleValue
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

                    // InternalKdl.g:3496:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==31) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // InternalKdl.g:3497:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,31,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:3501:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:3502:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:3502:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:3503:7: lv_values_2_0= ruleValue
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
                    	    break loop73;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:3523:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:3523:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:3524:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:3524:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:3525:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:3525:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:3526:6: lv_pairs_3_0= ruleKeyValuePair
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

                    // InternalKdl.g:3543:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==31) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // InternalKdl.g:3544:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:3544:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:3545:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,31,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3551:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:3552:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:3552:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:3553:7: lv_pairs_5_0= ruleKeyValuePair
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
                    	    break loop74;
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
    // InternalKdl.g:3576:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:3576:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:3577:2: iv_ruleValue= ruleValue EOF
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
    // InternalKdl.g:3583:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) ;
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
            // InternalKdl.g:3589:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:3590:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:3590:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            int alt76=8;
            alt76 = dfa76.predict(input);
            switch (alt76) {
                case 1 :
                    // InternalKdl.g:3591:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:3591:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:3592:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:3592:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:3593:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
                    // InternalKdl.g:3611:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:3611:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:3612:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:3612:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:3613:5: lv_function_1_0= ruleFunction
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
                    // InternalKdl.g:3631:3: ( (lv_urn_2_0= ruleUrn ) )
                    {
                    // InternalKdl.g:3631:3: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:3632:4: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:3632:4: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:3633:5: lv_urn_2_0= ruleUrn
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
                    // InternalKdl.g:3651:3: ( (lv_list_3_0= ruleList ) )
                    {
                    // InternalKdl.g:3651:3: ( (lv_list_3_0= ruleList ) )
                    // InternalKdl.g:3652:4: (lv_list_3_0= ruleList )
                    {
                    // InternalKdl.g:3652:4: (lv_list_3_0= ruleList )
                    // InternalKdl.g:3653:5: lv_list_3_0= ruleList
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
                    // InternalKdl.g:3671:3: ( (lv_map_4_0= ruleMap ) )
                    {
                    // InternalKdl.g:3671:3: ( (lv_map_4_0= ruleMap ) )
                    // InternalKdl.g:3672:4: (lv_map_4_0= ruleMap )
                    {
                    // InternalKdl.g:3672:4: (lv_map_4_0= ruleMap )
                    // InternalKdl.g:3673:5: lv_map_4_0= ruleMap
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
                    // InternalKdl.g:3691:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3691:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    // InternalKdl.g:3692:4: (lv_expression_5_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3692:4: (lv_expression_5_0= RULE_EXPR )
                    // InternalKdl.g:3693:5: lv_expression_5_0= RULE_EXPR
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
                    // InternalKdl.g:3710:3: ( (lv_table_6_0= ruleLookupTable ) )
                    {
                    // InternalKdl.g:3710:3: ( (lv_table_6_0= ruleLookupTable ) )
                    // InternalKdl.g:3711:4: (lv_table_6_0= ruleLookupTable )
                    {
                    // InternalKdl.g:3711:4: (lv_table_6_0= ruleLookupTable )
                    // InternalKdl.g:3712:5: lv_table_6_0= ruleLookupTable
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
                    // InternalKdl.g:3730:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3730:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3731:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3731:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:3732:5: lv_enumId_7_0= RULE_UPPERCASE_ID
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
    // InternalKdl.g:3752:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:3752:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:3753:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKdl.g:3759:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:3765:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:3766:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:3766:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:3767:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:3767:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:3768:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:3768:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt77=3;
            switch ( input.LA(1) ) {
            case 96:
                {
                alt77=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                switch ( input.LA(2) ) {
                case 103:
                    {
                    int LA77_5 = input.LA(3);

                    if ( (LA77_5==RULE_LOWERCASE_ID) ) {
                        int LA77_6 = input.LA(4);

                        if ( (LA77_6==97||LA77_6==103) ) {
                            alt77=1;
                        }
                        else if ( (LA77_6==EOF||(LA77_6>=RULE_STRING && LA77_6<=RULE_CAMELCASE_ID)||(LA77_6>=RULE_ID && LA77_6<=RULE_EXPR)||(LA77_6>=20 && LA77_6<=37)||(LA77_6>=39 && LA77_6<=42)||(LA77_6>=44 && LA77_6<=45)||(LA77_6>=50 && LA77_6<=51)||LA77_6==54||(LA77_6>=57 && LA77_6<=84)||(LA77_6>=90 && LA77_6<=91)||LA77_6==96||(LA77_6>=98 && LA77_6<=99)||LA77_6==106||LA77_6==113) ) {
                            alt77=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 77, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 97:
                    {
                    alt77=1;
                    }
                    break;
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
                    alt77=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 77, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt77=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
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
                    // InternalKdl.g:3769:5: lv_name_0_1= ruleUrnId
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
                    // InternalKdl.g:3785:5: lv_name_0_2= RULE_STRING
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
                    // InternalKdl.g:3800:5: lv_name_0_3= ruleLocalFilePath
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
    // InternalKdl.g:3821:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:3821:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:3822:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKdl.g:3828:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:3834:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:3835:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:3835:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:3836:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:3836:3: (kw= 'urn:klab:' )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==96) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalKdl.g:3837:4: kw= 'urn:klab:'
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
            // InternalKdl.g:3898:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==97) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKdl.g:3899:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
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

            // InternalKdl.g:3915:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==98) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalKdl.g:3916:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
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
    // InternalKdl.g:3933:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKdl.g:3933:44: (iv_ruleMap= ruleMap EOF )
            // InternalKdl.g:3934:2: iv_ruleMap= ruleMap EOF
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
    // InternalKdl.g:3940:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3946:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKdl.g:3947:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKdl.g:3947:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKdl.g:3948:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKdl.g:3948:3: ()
            // InternalKdl.g:3949:4: 
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
            // InternalKdl.g:3962:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_STRING||LA82_0==RULE_INT||LA82_0==33||LA82_0==44||LA82_0==47||LA82_0==50||(LA82_0>=90 && LA82_0<=91)||(LA82_0>=94 && LA82_0<=95)||LA82_0==105||(LA82_0>=108 && LA82_0<=113)) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalKdl.g:3963:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKdl.g:3963:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKdl.g:3964:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKdl.g:3964:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKdl.g:3965:6: lv_entries_2_0= ruleMapEntry
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

                    // InternalKdl.g:3982:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop81:
                    do {
                        int alt81=2;
                        int LA81_0 = input.LA(1);

                        if ( (LA81_0==31) ) {
                            alt81=1;
                        }


                        switch (alt81) {
                    	case 1 :
                    	    // InternalKdl.g:3983:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKdl.g:3983:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKdl.g:3984:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_64); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3991:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKdl.g:3992:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKdl.g:3992:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKdl.g:3993:7: lv_entries_4_0= ruleMapEntry
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
                    	    break loop81;
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
    // InternalKdl.g:4020:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKdl.g:4020:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKdl.g:4021:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKdl.g:4027:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4033:2: ( ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKdl.g:4034:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKdl.g:4034:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKdl.g:4035:3: ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKdl.g:4035:3: ( (lv_classifier_0_0= ruleClassifierRHS ) )
            // InternalKdl.g:4036:4: (lv_classifier_0_0= ruleClassifierRHS )
            {
            // InternalKdl.g:4036:4: (lv_classifier_0_0= ruleClassifierRHS )
            // InternalKdl.g:4037:5: lv_classifier_0_0= ruleClassifierRHS
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
            // InternalKdl.g:4058:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKdl.g:4059:4: (lv_value_2_0= ruleValue )
            {
            // InternalKdl.g:4059:4: (lv_value_2_0= ruleValue )
            // InternalKdl.g:4060:5: lv_value_2_0= ruleValue
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
    // InternalKdl.g:4081:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKdl.g:4081:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKdl.g:4082:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKdl.g:4088:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4094:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKdl.g:4095:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKdl.g:4095:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKdl.g:4096:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKdl.g:4096:3: ()
            // InternalKdl.g:4097:4: 
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
            // InternalKdl.g:4110:3: ( (lv_table_2_0= ruleTable ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==RULE_STRING||LA83_0==RULE_INT||LA83_0==RULE_EXPR||LA83_0==44||LA83_0==47||(LA83_0>=90 && LA83_0<=91)||(LA83_0>=94 && LA83_0<=95)||LA83_0==98||LA83_0==105||(LA83_0>=108 && LA83_0<=113)) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalKdl.g:4111:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKdl.g:4111:4: (lv_table_2_0= ruleTable )
                    // InternalKdl.g:4112:5: lv_table_2_0= ruleTable
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
    // InternalKdl.g:4137:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKdl.g:4137:46: (iv_ruleTable= ruleTable EOF )
            // InternalKdl.g:4138:2: iv_ruleTable= ruleTable EOF
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
    // InternalKdl.g:4144:1: ruleTable returns [EObject current=null] : ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_rows_0_0 = null;

        EObject lv_rows_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4150:2: ( ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) )
            // InternalKdl.g:4151:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            {
            // InternalKdl.g:4151:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            // InternalKdl.g:4152:3: ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            {
            // InternalKdl.g:4152:3: ( (lv_rows_0_0= ruleTableRow ) )
            // InternalKdl.g:4153:4: (lv_rows_0_0= ruleTableRow )
            {
            // InternalKdl.g:4153:4: (lv_rows_0_0= ruleTableRow )
            // InternalKdl.g:4154:5: lv_rows_0_0= ruleTableRow
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

            // InternalKdl.g:4171:3: (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==31) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalKdl.g:4172:4: otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) )
            	    {
            	    otherlv_1=(Token)match(input,31,FOLLOW_67); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4176:4: ( (lv_rows_2_0= ruleTableRow ) )
            	    // InternalKdl.g:4177:5: (lv_rows_2_0= ruleTableRow )
            	    {
            	    // InternalKdl.g:4177:5: (lv_rows_2_0= ruleTableRow )
            	    // InternalKdl.g:4178:6: lv_rows_2_0= ruleTableRow
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
            	    break loop84;
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
    // InternalKdl.g:4200:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKdl.g:4200:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKdl.g:4201:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKdl.g:4207:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4213:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKdl.g:4214:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKdl.g:4214:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKdl.g:4215:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKdl.g:4215:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKdl.g:4216:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKdl.g:4216:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKdl.g:4217:5: lv_elements_0_0= ruleTableClassifier
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

            // InternalKdl.g:4234:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==101) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // InternalKdl.g:4235:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,101,FOLLOW_67); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4239:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKdl.g:4240:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKdl.g:4240:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKdl.g:4241:6: lv_elements_2_0= ruleTableClassifier
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
    // $ANTLR end "ruleTableRow"


    // $ANTLR start "entryRuleTableClassifier"
    // InternalKdl.g:4263:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKdl.g:4263:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKdl.g:4264:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKdl.g:4270:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) ;
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
            // InternalKdl.g:4276:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) )
            // InternalKdl.g:4277:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            {
            // InternalKdl.g:4277:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            int alt89=10;
            alt89 = dfa89.predict(input);
            switch (alt89) {
                case 1 :
                    // InternalKdl.g:4278:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:4278:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==90) ) {
                        alt86=1;
                    }
                    else if ( (LA86_0==91) ) {
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
                            // InternalKdl.g:4279:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:4279:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:4280:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:4280:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:4281:6: lv_boolean_0_0= 'true'
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
                            // InternalKdl.g:4294:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:4294:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:4295:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:4295:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:4296:6: lv_boolean_1_0= 'false'
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
                    // InternalKdl.g:4310:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:4310:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:4311:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:4311:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:4312:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:4312:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:4313:6: lv_int0_2_0= ruleNumber
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

                    // InternalKdl.g:4330:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt87=3;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==92) ) {
                        alt87=1;
                    }
                    else if ( (LA87_0==93) ) {
                        alt87=2;
                    }
                    switch (alt87) {
                        case 1 :
                            // InternalKdl.g:4331:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:4331:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:4332:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:4332:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:4333:7: lv_leftLimit_3_0= 'inclusive'
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
                            // InternalKdl.g:4346:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,93,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4351:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:4352:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,55,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getTableClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:4358:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:4359:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:4363:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:4364:6: lv_int1_6_0= ruleNumber
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

                    // InternalKdl.g:4381:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
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
                            // InternalKdl.g:4382:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:4382:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:4383:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:4383:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:4384:7: lv_rightLimit_7_0= 'inclusive'
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
                            // InternalKdl.g:4397:5: otherlv_8= 'exclusive'
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
                    // InternalKdl.g:4404:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4404:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:4405:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:4405:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:4406:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:4424:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:4424:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:4425:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,94,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:4429:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:4430:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:4430:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:4431:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:4450:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:4450:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:4451:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:4451:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:4452:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:4469:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:4469:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    // InternalKdl.g:4470:4: ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4470:4: ( (lv_op_13_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:4471:5: (lv_op_13_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:4471:5: (lv_op_13_0= ruleREL_OPERATOR )
                    // InternalKdl.g:4472:6: lv_op_13_0= ruleREL_OPERATOR
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

                    // InternalKdl.g:4489:4: ( (lv_expression_14_0= ruleNumber ) )
                    // InternalKdl.g:4490:5: (lv_expression_14_0= ruleNumber )
                    {
                    // InternalKdl.g:4490:5: (lv_expression_14_0= ruleNumber )
                    // InternalKdl.g:4491:6: lv_expression_14_0= ruleNumber
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
                    // InternalKdl.g:4510:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:4510:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    // InternalKdl.g:4511:4: (lv_expr_15_0= RULE_EXPR )
                    {
                    // InternalKdl.g:4511:4: (lv_expr_15_0= RULE_EXPR )
                    // InternalKdl.g:4512:5: lv_expr_15_0= RULE_EXPR
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
                    // InternalKdl.g:4529:3: ( (lv_nodata_16_0= 'unknown' ) )
                    {
                    // InternalKdl.g:4529:3: ( (lv_nodata_16_0= 'unknown' ) )
                    // InternalKdl.g:4530:4: (lv_nodata_16_0= 'unknown' )
                    {
                    // InternalKdl.g:4530:4: (lv_nodata_16_0= 'unknown' )
                    // InternalKdl.g:4531:5: lv_nodata_16_0= 'unknown'
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
                    // InternalKdl.g:4544:3: ( (lv_star_17_0= '*' ) )
                    {
                    // InternalKdl.g:4544:3: ( (lv_star_17_0= '*' ) )
                    // InternalKdl.g:4545:4: (lv_star_17_0= '*' )
                    {
                    // InternalKdl.g:4545:4: (lv_star_17_0= '*' )
                    // InternalKdl.g:4546:5: lv_star_17_0= '*'
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
                    // InternalKdl.g:4559:3: ( (lv_anything_18_0= '#' ) )
                    {
                    // InternalKdl.g:4559:3: ( (lv_anything_18_0= '#' ) )
                    // InternalKdl.g:4560:4: (lv_anything_18_0= '#' )
                    {
                    // InternalKdl.g:4560:4: (lv_anything_18_0= '#' )
                    // InternalKdl.g:4561:5: lv_anything_18_0= '#'
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
    // InternalKdl.g:4577:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:4577:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:4578:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
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
    // InternalKdl.g:4584:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4590:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4591:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4591:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4592:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4592:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt90=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt90=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt90=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                alt90=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }

            switch (alt90) {
                case 1 :
                    // InternalKdl.g:4593:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
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
                    // InternalKdl.g:4601:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4609:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
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

            // InternalKdl.g:4617:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==102) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalKdl.g:4618:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,102,FOLLOW_70); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4623:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
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
            	            // InternalKdl.g:4624:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
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
            	            // InternalKdl.g:4632:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
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
            	            // InternalKdl.g:4640:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
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
            	    break loop92;
                }
            } while (true);

            // InternalKdl.g:4649:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==103) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalKdl.g:4650:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
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

            // InternalKdl.g:4663:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==98) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalKdl.g:4664:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
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
    // InternalKdl.g:4681:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4681:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4682:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKdl.g:4688:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_1=null;
        Token lv_name_0_2=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4694:2: ( ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4695:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4695:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4696:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4696:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:4697:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:4697:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:4698:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            {
            // InternalKdl.g:4698:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==RULE_LOWERCASE_ID) ) {
                alt95=1;
            }
            else if ( (LA95_0==RULE_LOWERCASE_DASHID) ) {
                alt95=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // InternalKdl.g:4699:6: lv_name_0_1= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4714:6: lv_name_0_2= RULE_LOWERCASE_DASHID
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

            // InternalKdl.g:4731:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==104) ) {
                alt96=1;
            }
            else if ( (LA96_0==105) ) {
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
                    // InternalKdl.g:4732:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4732:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4733:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4733:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4734:6: lv_interactive_1_0= '=?'
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
                    // InternalKdl.g:4747:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,105,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4752:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4753:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4753:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4754:5: lv_value_3_0= ruleValue
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
    // InternalKdl.g:4775:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4775:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4776:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalKdl.g:4782:1: ruleFunction returns [EObject current=null] : ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) ) ;
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
            // InternalKdl.g:4788:2: ( ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) ) )
            // InternalKdl.g:4789:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) )
            {
            // InternalKdl.g:4789:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) )
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( ((LA102_0>=RULE_STRING && LA102_0<=RULE_LOWERCASE_ID)||(LA102_0>=RULE_INT && LA102_0<=RULE_LOWERCASE_DASHID)||LA102_0==RULE_CAMELCASE_ID||LA102_0==RULE_EXPR||LA102_0==44||(LA102_0>=90 && LA102_0<=91)||LA102_0==96||LA102_0==113) ) {
                alt102=1;
            }
            else if ( (LA102_0==33) ) {
                alt102=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }
            switch (alt102) {
                case 1 :
                    // InternalKdl.g:4790:3: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4790:3: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4791:4: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4791:4: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )?
                    int alt97=3;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==RULE_LOWERCASE_ID) ) {
                        int LA97_1 = input.LA(2);

                        if ( (LA97_1==106) ) {
                            int LA97_3 = input.LA(3);

                            if ( (LA97_3==RULE_LOWERCASE_ID) ) {
                                int LA97_5 = input.LA(4);

                                if ( (synpred196_InternalKdl()) ) {
                                    alt97=1;
                                }
                            }
                            else if ( (LA97_3==RULE_STRING||(LA97_3>=RULE_INT && LA97_3<=RULE_LOWERCASE_DASHID)||LA97_3==RULE_CAMELCASE_ID||LA97_3==RULE_EXPR||LA97_3==44||(LA97_3>=90 && LA97_3<=91)||LA97_3==96||LA97_3==113) ) {
                                alt97=1;
                            }
                        }
                        else if ( (LA97_1==107) ) {
                            alt97=2;
                        }
                    }
                    switch (alt97) {
                        case 1 :
                            // InternalKdl.g:4792:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
                            {
                            // InternalKdl.g:4792:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
                            // InternalKdl.g:4793:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4793:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4794:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4794:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4795:8: lv_mediated_0_0= RULE_LOWERCASE_ID
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
                            // InternalKdl.g:4817:5: ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' )
                            {
                            // InternalKdl.g:4817:5: ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' )
                            // InternalKdl.g:4818:6: ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-'
                            {
                            // InternalKdl.g:4818:6: ( (lv_variable_2_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4819:7: (lv_variable_2_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4819:7: (lv_variable_2_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4820:8: lv_variable_2_0= RULE_LOWERCASE_ID
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

                    // InternalKdl.g:4842:4: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) )
                    int alt99=4;
                    alt99 = dfa99.predict(input);
                    switch (alt99) {
                        case 1 :
                            // InternalKdl.g:4843:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
                            {
                            // InternalKdl.g:4843:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
                            // InternalKdl.g:4844:6: ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')'
                            {
                            // InternalKdl.g:4844:6: ( (lv_name_4_0= rulePathName ) )
                            // InternalKdl.g:4845:7: (lv_name_4_0= rulePathName )
                            {
                            // InternalKdl.g:4845:7: (lv_name_4_0= rulePathName )
                            // InternalKdl.g:4846:8: lv_name_4_0= rulePathName
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
                            // InternalKdl.g:4867:6: ( (lv_parameters_6_0= ruleParameterList ) )?
                            int alt98=2;
                            int LA98_0 = input.LA(1);

                            if ( ((LA98_0>=RULE_STRING && LA98_0<=RULE_LOWERCASE_ID)||(LA98_0>=RULE_INT && LA98_0<=RULE_CAMELCASE_ID)||(LA98_0>=RULE_ID && LA98_0<=RULE_EXPR)||LA98_0==31||LA98_0==33||LA98_0==44||LA98_0==50||(LA98_0>=90 && LA98_0<=91)||LA98_0==96||LA98_0==99||LA98_0==113) ) {
                                alt98=1;
                            }
                            switch (alt98) {
                                case 1 :
                                    // InternalKdl.g:4868:7: (lv_parameters_6_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4868:7: (lv_parameters_6_0= ruleParameterList )
                                    // InternalKdl.g:4869:8: lv_parameters_6_0= ruleParameterList
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
                            // InternalKdl.g:4892:5: ( (lv_urn_8_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4892:5: ( (lv_urn_8_0= ruleUrn ) )
                            // InternalKdl.g:4893:6: (lv_urn_8_0= ruleUrn )
                            {
                            // InternalKdl.g:4893:6: (lv_urn_8_0= ruleUrn )
                            // InternalKdl.g:4894:7: lv_urn_8_0= ruleUrn
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
                            // InternalKdl.g:4912:5: ( (lv_value_9_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4912:5: ( (lv_value_9_0= ruleLiteral ) )
                            // InternalKdl.g:4913:6: (lv_value_9_0= ruleLiteral )
                            {
                            // InternalKdl.g:4913:6: (lv_value_9_0= ruleLiteral )
                            // InternalKdl.g:4914:7: lv_value_9_0= ruleLiteral
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
                            // InternalKdl.g:4932:5: ( (lv_expression_10_0= RULE_EXPR ) )
                            {
                            // InternalKdl.g:4932:5: ( (lv_expression_10_0= RULE_EXPR ) )
                            // InternalKdl.g:4933:6: (lv_expression_10_0= RULE_EXPR )
                            {
                            // InternalKdl.g:4933:6: (lv_expression_10_0= RULE_EXPR )
                            // InternalKdl.g:4934:7: lv_expression_10_0= RULE_EXPR
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

                    // InternalKdl.g:4951:4: (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==106) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // InternalKdl.g:4952:5: otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_11=(Token)match(input,106,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4956:5: ( (lv_target_12_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4957:6: (lv_target_12_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4957:6: (lv_target_12_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4958:7: lv_target_12_0= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4977:3: (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' )
                    {
                    // InternalKdl.g:4977:3: (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' )
                    // InternalKdl.g:4978:4: otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')'
                    {
                    otherlv_13=(Token)match(input,33,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:4982:4: ( (lv_chain_14_0= ruleFunction ) )
                    // InternalKdl.g:4983:5: (lv_chain_14_0= ruleFunction )
                    {
                    // InternalKdl.g:4983:5: (lv_chain_14_0= ruleFunction )
                    // InternalKdl.g:4984:6: lv_chain_14_0= ruleFunction
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

                    // InternalKdl.g:5001:4: (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )*
                    loop101:
                    do {
                        int alt101=2;
                        int LA101_0 = input.LA(1);

                        if ( (LA101_0==31) ) {
                            alt101=1;
                        }


                        switch (alt101) {
                    	case 1 :
                    	    // InternalKdl.g:5002:5: otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) )
                    	    {
                    	    otherlv_15=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_15, grammarAccess.getFunctionAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:5006:5: ( (lv_chain_16_0= ruleFunction ) )
                    	    // InternalKdl.g:5007:6: (lv_chain_16_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:5007:6: (lv_chain_16_0= ruleFunction )
                    	    // InternalKdl.g:5008:7: lv_chain_16_0= ruleFunction
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
                    	    break loop101;
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
    // InternalKdl.g:5035:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:5035:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:5036:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKdl.g:5042:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKdl.g:5048:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:5049:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:5049:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt103=6;
            switch ( input.LA(1) ) {
            case 108:
                {
                alt103=1;
                }
                break;
            case 109:
                {
                alt103=2;
                }
                break;
            case 105:
                {
                alt103=3;
                }
                break;
            case 110:
                {
                alt103=4;
                }
                break;
            case 111:
                {
                alt103=5;
                }
                break;
            case 112:
                {
                alt103=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // InternalKdl.g:5050:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:5050:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:5051:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:5051:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:5052:5: lv_gt_0_0= '>'
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
                    // InternalKdl.g:5065:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:5065:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:5066:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:5066:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:5067:5: lv_lt_1_0= '<'
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
                    // InternalKdl.g:5080:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:5080:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:5081:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:5081:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:5082:5: lv_eq_2_0= '='
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
                    // InternalKdl.g:5095:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:5095:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:5096:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:5096:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:5097:5: lv_ne_3_0= '!='
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
                    // InternalKdl.g:5110:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:5110:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:5111:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:5111:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:5112:5: lv_le_4_0= '<='
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
                    // InternalKdl.g:5125:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:5125:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:5126:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:5126:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:5127:5: lv_ge_5_0= '>='
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
    // InternalKdl.g:5143:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:5143:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:5144:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKdl.g:5150:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKdl.g:5156:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:5157:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:5157:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:5158:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:5158:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt104=3;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==44) ) {
                alt104=1;
            }
            else if ( (LA104_0==113) ) {
                alt104=2;
            }
            switch (alt104) {
                case 1 :
                    // InternalKdl.g:5159:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,44,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5164:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:5164:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:5165:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:5165:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:5166:6: lv_negative_1_0= '-'
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

            // InternalKdl.g:5179:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:5180:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:5184:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:5185:5: lv_real_2_0= RULE_INT
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

            // InternalKdl.g:5201:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==103) && (synpred213_InternalKdl())) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalKdl.g:5202:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5215:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:5216:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5216:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:5217:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:5217:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:5218:7: lv_decimal_3_0= '.'
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

                    // InternalKdl.g:5230:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:5231:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:5231:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:5232:7: lv_decimalPart_4_0= RULE_INT
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

            // InternalKdl.g:5250:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==114) && (synpred217_InternalKdl())) {
                alt108=1;
            }
            else if ( (LA108_0==115) && (synpred217_InternalKdl())) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalKdl.g:5251:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5277:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:5278:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5278:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:5279:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:5279:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:5280:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:5280:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==114) ) {
                        alt106=1;
                    }
                    else if ( (LA106_0==115) ) {
                        alt106=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 106, 0, input);

                        throw nvae;
                    }
                    switch (alt106) {
                        case 1 :
                            // InternalKdl.g:5281:8: lv_exponential_5_1= 'e'
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
                            // InternalKdl.g:5292:8: lv_exponential_5_2= 'E'
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

                    // InternalKdl.g:5305:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt107=3;
                    int LA107_0 = input.LA(1);

                    if ( (LA107_0==44) ) {
                        alt107=1;
                    }
                    else if ( (LA107_0==113) ) {
                        alt107=2;
                    }
                    switch (alt107) {
                        case 1 :
                            // InternalKdl.g:5306:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,44,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5311:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:5311:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:5312:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:5312:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:5313:8: lv_expNegative_7_0= '-'
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

                    // InternalKdl.g:5326:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:5327:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:5327:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:5328:7: lv_exp_8_0= RULE_INT
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
    // InternalKdl.g:5350:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:5350:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:5351:2: iv_rulePathName= rulePathName EOF
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
    // InternalKdl.g:5357:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5363:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5364:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5364:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5365:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_78); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5372:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==103) ) {
                    int LA109_2 = input.LA(2);

                    if ( (LA109_2==RULE_LOWERCASE_ID) ) {
                        alt109=1;
                    }


                }


                switch (alt109) {
            	case 1 :
            	    // InternalKdl.g:5373:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
            	    break loop109;
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
    // InternalKdl.g:5390:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:5390:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:5391:2: iv_rulePath= rulePath EOF
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
    // InternalKdl.g:5397:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:5403:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5404:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5404:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5405:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_79); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5412:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop111:
            do {
                int alt111=2;
                int LA111_0 = input.LA(1);

                if ( ((LA111_0>=102 && LA111_0<=103)) ) {
                    alt111=1;
                }


                switch (alt111) {
            	case 1 :
            	    // InternalKdl.g:5413:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:5413:4: (kw= '.' | kw= '/' )
            	    int alt110=2;
            	    int LA110_0 = input.LA(1);

            	    if ( (LA110_0==103) ) {
            	        alt110=1;
            	    }
            	    else if ( (LA110_0==102) ) {
            	        alt110=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 110, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt110) {
            	        case 1 :
            	            // InternalKdl.g:5414:5: kw= '.'
            	            {
            	            kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:5420:5: kw= '/'
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
            	    break loop111;
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
    // InternalKdl.g:5438:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5438:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5439:2: iv_ruleJavaClass= ruleJavaClass EOF
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
    // InternalKdl.g:5445:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5451:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5452:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5452:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5453:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
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
    // InternalKdl.g:5479:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5479:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5480:2: iv_rulePropertyId= rulePropertyId EOF
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
    // InternalKdl.g:5486:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5492:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5493:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5493:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5494:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
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
            // InternalKdl.g:5509:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==RULE_LOWERCASE_ID) ) {
                alt112=1;
            }
            else if ( (LA112_0==RULE_LOWERCASE_DASHID) ) {
                alt112=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }
            switch (alt112) {
                case 1 :
                    // InternalKdl.g:5510:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5518:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
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
    // InternalKdl.g:5530:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5530:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5531:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKdl.g:5537:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKdl.g:5543:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:5544:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:5544:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:5545:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_83); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5552:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==103) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // InternalKdl.g:5553:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
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
                    // InternalKdl.g:5565:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt113=2;
                    int LA113_0 = input.LA(1);

                    if ( (LA113_0==103) ) {
                        alt113=1;
                    }
                    switch (alt113) {
                        case 1 :
                            // InternalKdl.g:5566:5: kw= '.' this_INT_4= RULE_INT
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

            // InternalKdl.g:5580:3: (kw= '-' )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==113) ) {
                int LA115_1 = input.LA(2);

                if ( (synpred227_InternalKdl()) ) {
                    alt115=1;
                }
            }
            switch (alt115) {
                case 1 :
                    // InternalKdl.g:5581:4: kw= '-'
                    {
                    kw=(Token)match(input,113,FOLLOW_85); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:5587:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt116=3;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==RULE_LOWERCASE_ID) ) {
                int LA116_1 = input.LA(2);

                if ( (synpred228_InternalKdl()) ) {
                    alt116=1;
                }
            }
            else if ( (LA116_0==RULE_UPPERCASE_ID) ) {
                int LA116_2 = input.LA(2);

                if ( (synpred229_InternalKdl()) ) {
                    alt116=2;
                }
            }
            switch (alt116) {
                case 1 :
                    // InternalKdl.g:5588:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5596:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKdl.g:5608:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5614:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:5615:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:5615:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt117=3;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt117=1;
                }
                break;
            case 116:
                {
                alt117=2;
                }
                break;
            case 47:
                {
                alt117=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }

            switch (alt117) {
                case 1 :
                    // InternalKdl.g:5616:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:5616:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:5617:4: enumLiteral_0= '/'
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
                    // InternalKdl.g:5624:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:5624:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:5625:4: enumLiteral_1= '^'
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
                    // InternalKdl.g:5632:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:5632:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:5633:4: enumLiteral_2= '*'
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
        int alt118=2;
        alt118 = dfa118.predict(input);
        switch (alt118) {
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
        int cnt119=0;
        loop119:
        do {
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==21) && ((true))) {
                alt119=1;
            }


            switch (alt119) {
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
        	    if ( cnt119 >= 1 ) break loop119;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(119, input);
                    throw eee;
            }
            cnt119++;
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
        int cnt120=0;
        loop120:
        do {
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==22) && ((true))) {
                alt120=1;
            }


            switch (alt120) {
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
        int cnt121=0;
        loop121:
        do {
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==23) && ((true))) {
                alt121=1;
            }


            switch (alt121) {
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
        int alt122=2;
        int LA122_0 = input.LA(1);

        if ( (LA122_0==RULE_LOWERCASE_ID) ) {
            alt122=1;
        }
        else if ( (LA122_0==RULE_STRING) ) {
            alt122=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 122, 0, input);

            throw nvae;
        }
        switch (alt122) {
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
        loop123:
        do {
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==31) ) {
                alt123=1;
            }


            switch (alt123) {
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
        	    break loop123;
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

    // $ANTLR start synpred61_InternalKdl
    public final void synpred61_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_47=null;
        EObject lv_default_48_0 = null;


        // InternalKdl.g:1520:5: ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) )
        // InternalKdl.g:1520:5: ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) )
        {
        // InternalKdl.g:1520:5: ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) )
        // InternalKdl.g:1521:6: {...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred61_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0)");
        }
        // InternalKdl.g:1521:116: ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) )
        // InternalKdl.g:1522:7: ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0);
        // InternalKdl.g:1525:10: ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) )
        // InternalKdl.g:1525:11: {...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred61_InternalKdl", "true");
        }
        // InternalKdl.g:1525:20: (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) )
        // InternalKdl.g:1525:21: otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) )
        {
        otherlv_47=(Token)match(input,57,FOLLOW_41); if (state.failed) return ;
        // InternalKdl.g:1529:10: ( (lv_default_48_0= ruleValue ) )
        // InternalKdl.g:1530:11: (lv_default_48_0= ruleValue )
        {
        // InternalKdl.g:1530:11: (lv_default_48_0= ruleValue )
        // InternalKdl.g:1531:12: lv_default_48_0= ruleValue
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_15_0_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_default_48_0=ruleValue();

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
    // $ANTLR end synpred61_InternalKdl

    // $ANTLR start synpred62_InternalKdl
    public final void synpred62_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_49=null;
        EObject lv_unit_50_0 = null;


        // InternalKdl.g:1554:5: ( ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1554:5: ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1554:5: ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1555:6: {...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred62_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1)");
        }
        // InternalKdl.g:1555:116: ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1556:7: ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1);
        // InternalKdl.g:1559:10: ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) )
        // InternalKdl.g:1559:11: {...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred62_InternalKdl", "true");
        }
        // InternalKdl.g:1559:20: (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) )
        // InternalKdl.g:1559:21: otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) )
        {
        otherlv_49=(Token)match(input,58,FOLLOW_89); if (state.failed) return ;
        // InternalKdl.g:1563:10: ( (lv_unit_50_0= ruleUnit ) )
        // InternalKdl.g:1564:11: (lv_unit_50_0= ruleUnit )
        {
        // InternalKdl.g:1564:11: (lv_unit_50_0= ruleUnit )
        // InternalKdl.g:1565:12: lv_unit_50_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getActorDefinitionAccess().getUnitUnitParserRuleCall_1_15_1_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_unit_50_0=ruleUnit();

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

    // $ANTLR start synpred67_InternalKdl
    public final void synpred67_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_geometry_4_0 = null;


        // InternalKdl.g:1738:4: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1738:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1738:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1739:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred67_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKdl.g:1739:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1740:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
        // InternalKdl.g:1743:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1743:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred67_InternalKdl", "true");
        }
        // InternalKdl.g:1743:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        // InternalKdl.g:1743:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
        {
        otherlv_3=(Token)match(input,61,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1747:9: ( (lv_geometry_4_0= ruleGeometry ) )
        // InternalKdl.g:1748:10: (lv_geometry_4_0= ruleGeometry )
        {
        // InternalKdl.g:1748:10: (lv_geometry_4_0= ruleGeometry )
        // InternalKdl.g:1749:11: lv_geometry_4_0= ruleGeometry
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
    // $ANTLR end synpred67_InternalKdl

    // $ANTLR start synpred68_InternalKdl
    public final void synpred68_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_5_0 = null;


        // InternalKdl.g:1772:4: ( ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) )
        // InternalKdl.g:1772:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
        {
        // InternalKdl.g:1772:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
        // InternalKdl.g:1773:5: {...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred68_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKdl.g:1773:109: ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
        // InternalKdl.g:1774:6: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
        // InternalKdl.g:1777:9: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
        // InternalKdl.g:1777:10: {...}? => ( (lv_computations_5_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred68_InternalKdl", "true");
        }
        // InternalKdl.g:1777:19: ( (lv_computations_5_0= ruleComputation ) )
        // InternalKdl.g:1777:20: (lv_computations_5_0= ruleComputation )
        {
        // InternalKdl.g:1777:20: (lv_computations_5_0= ruleComputation )
        // InternalKdl.g:1778:10: lv_computations_5_0= ruleComputation
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
    // $ANTLR end synpred68_InternalKdl

    // $ANTLR start synpred69_InternalKdl
    public final void synpred69_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_6=null;
        EObject lv_metadata_7_0 = null;


        // InternalKdl.g:1806:10: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )
        // InternalKdl.g:1806:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
        {
        otherlv_6=(Token)match(input,62,FOLLOW_47); if (state.failed) return ;
        // InternalKdl.g:1810:10: ( (lv_metadata_7_0= ruleMetadata ) )
        // InternalKdl.g:1811:11: (lv_metadata_7_0= ruleMetadata )
        {
        // InternalKdl.g:1811:11: (lv_metadata_7_0= ruleMetadata )
        // InternalKdl.g:1812:12: lv_metadata_7_0= ruleMetadata
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
    // $ANTLR end synpred69_InternalKdl

    // $ANTLR start synpred71_InternalKdl
    public final void synpred71_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;


        // InternalKdl.g:1831:10: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )
        // InternalKdl.g:1831:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
        {
        otherlv_8=(Token)match(input,63,FOLLOW_9); if (state.failed) return ;
        // InternalKdl.g:1835:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
        // InternalKdl.g:1836:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
        {
        // InternalKdl.g:1836:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
        // InternalKdl.g:1837:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
        {
        // InternalKdl.g:1837:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
        int alt140=2;
        int LA140_0 = input.LA(1);

        if ( (LA140_0==RULE_LOWERCASE_ID) ) {
            alt140=1;
        }
        else if ( (LA140_0==RULE_STRING) ) {
            alt140=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 140, 0, input);

            throw nvae;
        }
        switch (alt140) {
            case 1 :
                // InternalKdl.g:1838:13: lv_javaClass_9_1= ruleJavaClass
                {
                pushFollow(FOLLOW_2);
                lv_javaClass_9_1=ruleJavaClass();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:1854:13: lv_javaClass_9_2= RULE_STRING
                {
                lv_javaClass_9_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }


        }


        }
    }
    // $ANTLR end synpred71_InternalKdl

    // $ANTLR start synpred72_InternalKdl
    public final void synpred72_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        EObject lv_metadata_7_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;


        // InternalKdl.g:1800:4: ( ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )
        // InternalKdl.g:1800:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
        {
        // InternalKdl.g:1800:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
        // InternalKdl.g:1801:5: {...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred72_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKdl.g:1801:109: ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
        // InternalKdl.g:1802:6: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
        // InternalKdl.g:1805:9: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
        // InternalKdl.g:1805:10: {...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred72_InternalKdl", "true");
        }
        // InternalKdl.g:1805:19: ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
        // InternalKdl.g:1805:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
        {
        // InternalKdl.g:1805:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )?
        int alt141=2;
        int LA141_0 = input.LA(1);

        if ( (LA141_0==62) ) {
            alt141=1;
        }
        switch (alt141) {
            case 1 :
                // InternalKdl.g:1806:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
                {
                otherlv_6=(Token)match(input,62,FOLLOW_47); if (state.failed) return ;
                // InternalKdl.g:1810:10: ( (lv_metadata_7_0= ruleMetadata ) )
                // InternalKdl.g:1811:11: (lv_metadata_7_0= ruleMetadata )
                {
                // InternalKdl.g:1811:11: (lv_metadata_7_0= ruleMetadata )
                // InternalKdl.g:1812:12: lv_metadata_7_0= ruleMetadata
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

        // InternalKdl.g:1830:9: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
        int alt143=2;
        int LA143_0 = input.LA(1);

        if ( (LA143_0==63) ) {
            alt143=1;
        }
        switch (alt143) {
            case 1 :
                // InternalKdl.g:1831:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
                {
                otherlv_8=(Token)match(input,63,FOLLOW_9); if (state.failed) return ;
                // InternalKdl.g:1835:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
                // InternalKdl.g:1836:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
                {
                // InternalKdl.g:1836:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
                // InternalKdl.g:1837:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
                {
                // InternalKdl.g:1837:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
                int alt142=2;
                int LA142_0 = input.LA(1);

                if ( (LA142_0==RULE_LOWERCASE_ID) ) {
                    alt142=1;
                }
                else if ( (LA142_0==RULE_STRING) ) {
                    alt142=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 142, 0, input);

                    throw nvae;
                }
                switch (alt142) {
                    case 1 :
                        // InternalKdl.g:1838:13: lv_javaClass_9_1= ruleJavaClass
                        {
                        pushFollow(FOLLOW_2);
                        lv_javaClass_9_1=ruleJavaClass();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 2 :
                        // InternalKdl.g:1854:13: lv_javaClass_9_2= RULE_STRING
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
    // $ANTLR end synpred72_InternalKdl

    // $ANTLR start synpred112_InternalKdl
    public final void synpred112_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2573:5: ( 'to' )
        // InternalKdl.g:2573:6: 'to'
        {
        match(input,55,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred112_InternalKdl

    // $ANTLR start synpred153_InternalKdl
    public final void synpred153_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:3591:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:3591:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:3591:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:3592:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:3592:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:3593:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
    // $ANTLR end synpred153_InternalKdl

    // $ANTLR start synpred154_InternalKdl
    public final void synpred154_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:3611:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:3611:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:3611:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:3612:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:3612:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:3613:5: lv_function_1_0= ruleFunction
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
    // $ANTLR end synpred154_InternalKdl

    // $ANTLR start synpred155_InternalKdl
    public final void synpred155_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;


        // InternalKdl.g:3631:3: ( ( (lv_urn_2_0= ruleUrn ) ) )
        // InternalKdl.g:3631:3: ( (lv_urn_2_0= ruleUrn ) )
        {
        // InternalKdl.g:3631:3: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:3632:4: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:3632:4: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:3633:5: lv_urn_2_0= ruleUrn
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
    // $ANTLR end synpred155_InternalKdl

    // $ANTLR start synpred156_InternalKdl
    public final void synpred156_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_3_0 = null;


        // InternalKdl.g:3651:3: ( ( (lv_list_3_0= ruleList ) ) )
        // InternalKdl.g:3651:3: ( (lv_list_3_0= ruleList ) )
        {
        // InternalKdl.g:3651:3: ( (lv_list_3_0= ruleList ) )
        // InternalKdl.g:3652:4: (lv_list_3_0= ruleList )
        {
        // InternalKdl.g:3652:4: (lv_list_3_0= ruleList )
        // InternalKdl.g:3653:5: lv_list_3_0= ruleList
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
    // $ANTLR end synpred156_InternalKdl

    // $ANTLR start synpred158_InternalKdl
    public final void synpred158_InternalKdl_fragment() throws RecognitionException {   
        Token lv_expression_5_0=null;

        // InternalKdl.g:3691:3: ( ( (lv_expression_5_0= RULE_EXPR ) ) )
        // InternalKdl.g:3691:3: ( (lv_expression_5_0= RULE_EXPR ) )
        {
        // InternalKdl.g:3691:3: ( (lv_expression_5_0= RULE_EXPR ) )
        // InternalKdl.g:3692:4: (lv_expression_5_0= RULE_EXPR )
        {
        // InternalKdl.g:3692:4: (lv_expression_5_0= RULE_EXPR )
        // InternalKdl.g:3693:5: lv_expression_5_0= RULE_EXPR
        {
        lv_expression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred158_InternalKdl

    // $ANTLR start synpred175_InternalKdl
    public final void synpred175_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4352:5: ( 'to' )
        // InternalKdl.g:4352:6: 'to'
        {
        match(input,55,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred175_InternalKdl

    // $ANTLR start synpred196_InternalKdl
    public final void synpred196_InternalKdl_fragment() throws RecognitionException {   
        Token lv_mediated_0_0=null;
        Token otherlv_1=null;

        // InternalKdl.g:4792:5: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) )
        // InternalKdl.g:4792:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
        {
        // InternalKdl.g:4792:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
        // InternalKdl.g:4793:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
        {
        // InternalKdl.g:4793:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
        // InternalKdl.g:4794:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
        {
        // InternalKdl.g:4794:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
        // InternalKdl.g:4795:8: lv_mediated_0_0= RULE_LOWERCASE_ID
        {
        lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return ;

        }


        }

        otherlv_1=(Token)match(input,106,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred196_InternalKdl

    // $ANTLR start synpred199_InternalKdl
    public final void synpred199_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject lv_parameters_6_0 = null;


        // InternalKdl.g:4843:5: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) )
        // InternalKdl.g:4843:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
        {
        // InternalKdl.g:4843:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
        // InternalKdl.g:4844:6: ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')'
        {
        // InternalKdl.g:4844:6: ( (lv_name_4_0= rulePathName ) )
        // InternalKdl.g:4845:7: (lv_name_4_0= rulePathName )
        {
        // InternalKdl.g:4845:7: (lv_name_4_0= rulePathName )
        // InternalKdl.g:4846:8: lv_name_4_0= rulePathName
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
        // InternalKdl.g:4867:6: ( (lv_parameters_6_0= ruleParameterList ) )?
        int alt160=2;
        int LA160_0 = input.LA(1);

        if ( ((LA160_0>=RULE_STRING && LA160_0<=RULE_LOWERCASE_ID)||(LA160_0>=RULE_INT && LA160_0<=RULE_CAMELCASE_ID)||(LA160_0>=RULE_ID && LA160_0<=RULE_EXPR)||LA160_0==31||LA160_0==33||LA160_0==44||LA160_0==50||(LA160_0>=90 && LA160_0<=91)||LA160_0==96||LA160_0==99||LA160_0==113) ) {
            alt160=1;
        }
        switch (alt160) {
            case 1 :
                // InternalKdl.g:4868:7: (lv_parameters_6_0= ruleParameterList )
                {
                // InternalKdl.g:4868:7: (lv_parameters_6_0= ruleParameterList )
                // InternalKdl.g:4869:8: lv_parameters_6_0= ruleParameterList
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
    // $ANTLR end synpred199_InternalKdl

    // $ANTLR start synpred200_InternalKdl
    public final void synpred200_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_8_0 = null;


        // InternalKdl.g:4892:5: ( ( (lv_urn_8_0= ruleUrn ) ) )
        // InternalKdl.g:4892:5: ( (lv_urn_8_0= ruleUrn ) )
        {
        // InternalKdl.g:4892:5: ( (lv_urn_8_0= ruleUrn ) )
        // InternalKdl.g:4893:6: (lv_urn_8_0= ruleUrn )
        {
        // InternalKdl.g:4893:6: (lv_urn_8_0= ruleUrn )
        // InternalKdl.g:4894:7: lv_urn_8_0= ruleUrn
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
    // $ANTLR end synpred200_InternalKdl

    // $ANTLR start synpred201_InternalKdl
    public final void synpred201_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_value_9_0 = null;


        // InternalKdl.g:4912:5: ( ( (lv_value_9_0= ruleLiteral ) ) )
        // InternalKdl.g:4912:5: ( (lv_value_9_0= ruleLiteral ) )
        {
        // InternalKdl.g:4912:5: ( (lv_value_9_0= ruleLiteral ) )
        // InternalKdl.g:4913:6: (lv_value_9_0= ruleLiteral )
        {
        // InternalKdl.g:4913:6: (lv_value_9_0= ruleLiteral )
        // InternalKdl.g:4914:7: lv_value_9_0= ruleLiteral
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
    // $ANTLR end synpred201_InternalKdl

    // $ANTLR start synpred212_InternalKdl
    public final void synpred212_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5180:4: ( ( RULE_INT ) )
        // InternalKdl.g:5180:5: ( RULE_INT )
        {
        // InternalKdl.g:5180:5: ( RULE_INT )
        // InternalKdl.g:5181:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred212_InternalKdl

    // $ANTLR start synpred213_InternalKdl
    public final void synpred213_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5202:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5202:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5202:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:5203:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:5203:5: ( ( '.' ) )
        // InternalKdl.g:5204:6: ( '.' )
        {
        // InternalKdl.g:5204:6: ( '.' )
        // InternalKdl.g:5205:7: '.'
        {
        match(input,103,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:5208:5: ( ( RULE_INT ) )
        // InternalKdl.g:5209:6: ( RULE_INT )
        {
        // InternalKdl.g:5209:6: ( RULE_INT )
        // InternalKdl.g:5210:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred213_InternalKdl

    // $ANTLR start synpred217_InternalKdl
    public final void synpred217_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5251:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5251:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5251:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:5252:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:5252:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:5253:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:5253:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:5254:7: ( 'e' | 'E' )
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

        // InternalKdl.g:5261:5: ( '+' | ( ( '-' ) ) )?
        int alt165=3;
        int LA165_0 = input.LA(1);

        if ( (LA165_0==44) ) {
            alt165=1;
        }
        else if ( (LA165_0==113) ) {
            alt165=2;
        }
        switch (alt165) {
            case 1 :
                // InternalKdl.g:5262:6: '+'
                {
                match(input,44,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5264:6: ( ( '-' ) )
                {
                // InternalKdl.g:5264:6: ( ( '-' ) )
                // InternalKdl.g:5265:7: ( '-' )
                {
                // InternalKdl.g:5265:7: ( '-' )
                // InternalKdl.g:5266:8: '-'
                {
                match(input,113,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:5270:5: ( ( RULE_INT ) )
        // InternalKdl.g:5271:6: ( RULE_INT )
        {
        // InternalKdl.g:5271:6: ( RULE_INT )
        // InternalKdl.g:5272:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred217_InternalKdl

    // $ANTLR start synpred227_InternalKdl
    public final void synpred227_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:5581:4: (kw= '-' )
        // InternalKdl.g:5581:4: kw= '-'
        {
        kw=(Token)match(input,113,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred227_InternalKdl

    // $ANTLR start synpred228_InternalKdl
    public final void synpred228_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:5588:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:5588:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred228_InternalKdl

    // $ANTLR start synpred229_InternalKdl
    public final void synpred229_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:5596:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:5596:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred229_InternalKdl

    // Delegated rules

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
    public final boolean synpred217_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred217_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred227_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred227_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred199_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred199_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred153_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred153_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred196_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred196_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred175_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred175_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred67_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred67_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred112_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred112_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred61_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred158_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred158_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred71_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred71_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred212_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred212_InternalKdl_fragment(); // can never throw exception
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
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA46 dfa46 = new DFA46(this);
    protected DFA61 dfa61 = new DFA61(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA67 dfa67 = new DFA67(this);
    protected DFA76 dfa76 = new DFA76(this);
    protected DFA89 dfa89 = new DFA89(this);
    protected DFA99 dfa99 = new DFA99(this);
    protected DFA118 dfa118 = new DFA118(this);
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
    static final String dfa_15s = "\32\uffff";
    static final String dfa_16s = "\1\6\1\uffff\1\55\1\7\1\uffff\25\4";
    static final String dfa_17s = "\1\124\1\uffff\2\124\1\uffff\25\57";
    static final String dfa_18s = "\1\uffff\1\1\2\uffff\1\2\25\uffff";
    static final String dfa_19s = "\32\uffff}>";
    static final String[] dfa_20s = {
            "\1\4\34\uffff\1\1\1\2\1\3\1\uffff\4\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4",
            "",
            "\1\4\10\uffff\1\26\12\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\27\1\30\1\31",
            "\1\4\43\uffff\1\4\1\uffff\1\4\10\uffff\1\26\12\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\27\1\30\1\31",
            "",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4",
            "\2\4\1\1\1\uffff\1\4\45\uffff\2\4"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "692:2: ( ( ( (lv_const_0_0= 'const' ) )? ( ( (lv_exported_1_0= 'export' ) ) | ( (lv_imported_2_0= 'import' ) ) ) ( (lv_type_3_0= ruleACTOR ) ) ( (lv_annotationTag_4_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_5_0= RULE_STRING ) )? (otherlv_6= 'label' ( (lv_label_7_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_8_0= ruleAnnotation ) )* ( (lv_abstract_9_0= 'abstract' ) )? ( (lv_final_10_0= 'final' ) )? ( (lv_optional_11_0= 'optional' ) )? ( ( (lv_exported_12_0= 'export' ) ) | ( (lv_filter_13_0= 'filter' ) ) | ( ( (lv_imported_14_0= 'import' ) ) ( ( (lv_multiple_15_0= 'multiple' ) ) | ( ( (lv_arity_16_0= RULE_INT ) ) ( (lv_minimum_17_0= '+' ) )? ) )? ) )? ( (lv_parameter_18_0= 'parameter' ) )? ( (lv_type_19_0= ruleACTOR ) ) ( (lv_expression_20_0= 'expression' ) )? ( ( (lv_name_21_1= RULE_LOWERCASE_ID | lv_name_21_2= RULE_LOWERCASE_DASHID | lv_name_21_3= RULE_STRING | lv_name_21_4= '*' ) ) ) (otherlv_22= 'extends' ( ( (lv_extended_23_1= RULE_LOWERCASE_ID | lv_extended_23_2= RULE_LOWERCASE_DASHID | lv_extended_23_3= RULE_STRING ) ) ) )? (otherlv_24= 'for' ( (lv_targets_25_0= ruleTARGET ) ) (otherlv_26= ',' ( (lv_targets_27_0= ruleTARGET ) ) )* )? ( (lv_docstring_28_0= RULE_STRING ) )? (otherlv_29= 'label' ( (lv_label_30_0= RULE_STRING ) ) )? (otherlv_31= '{' ( (lv_body_32_0= ruleDataflowBody ) ) otherlv_33= '}' )? ( ( (otherlv_34= 'minimum' ( (lv_rangeMin_35_0= ruleNumber ) ) ) | (otherlv_36= 'maximum' ( (lv_rangeMax_37_0= ruleNumber ) ) ) | (otherlv_38= 'range' ( (lv_rangeMin_39_0= ruleNumber ) ) otherlv_40= 'to' ( (lv_rangeMax_41_0= ruleNumber ) ) ) ) | (otherlv_42= 'values' ( ( (lv_enumValues_43_1= RULE_STRING | lv_enumValues_43_2= RULE_UPPERCASE_ID | lv_enumValues_43_3= RULE_LOWERCASE_ID | lv_enumValues_43_4= RULE_CAMELCASE_ID ) ) ) (otherlv_44= ',' ( ( (lv_enumValues_45_1= RULE_STRING | lv_enumValues_45_2= RULE_UPPERCASE_ID | lv_enumValues_45_3= RULE_LOWERCASE_ID | lv_enumValues_45_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_47= 'default' ( (lv_default_48_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= 'unit' ( (lv_unit_50_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_51= 'as' ( (lv_localName_52_0= RULE_LOWERCASE_ID ) ) )? (otherlv_53= 'over' ( (lv_coverage_54_0= ruleFunction ) ) (otherlv_55= ',' ( (lv_coverage_56_0= ruleFunction ) ) )* )? ) )";
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

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "()+ loopback of 1737:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA46_1 = input.LA(1);

                         
                        int index46_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred72_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 7;}

                         
                        input.seek(index46_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA46_2 = input.LA(1);

                         
                        int index46_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred72_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 7;}

                         
                        input.seek(index46_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA46_3 = input.LA(1);

                         
                        int index46_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred67_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {s = 8;}

                        else if ( synpred72_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index46_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA46_0 = input.LA(1);

                         
                        int index46_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA46_0==51) ) {s = 1;}

                        else if ( (LA46_0==EOF) ) {s = 2;}

                        else if ( (LA46_0==61) ) {s = 3;}

                        else if ( (LA46_0==64) ) {s = 4;}

                        else if ( LA46_0 == 62 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 5;}

                        else if ( LA46_0 == 63 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index46_0);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA46_4 = input.LA(1);

                         
                        int index46_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred68_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {s = 9;}

                        else if ( synpred72_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index46_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 46, _s, input);
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

    class DFA61 extends DFA {

        public DFA61(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 61;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "2498:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
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

    class DFA64 extends DFA {

        public DFA64(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 64;
            this.eot = dfa_35;
            this.eof = dfa_36;
            this.min = dfa_37;
            this.max = dfa_38;
            this.accept = dfa_39;
            this.special = dfa_40;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "2910:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
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

    class DFA67 extends DFA {

        public DFA67(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 67;
            this.eot = dfa_42;
            this.eof = dfa_43;
            this.min = dfa_44;
            this.max = dfa_45;
            this.accept = dfa_46;
            this.special = dfa_47;
            this.transition = dfa_48;
        }
        public String getDescription() {
            return "3040:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )";
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

    class DFA76 extends DFA {

        public DFA76(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 76;
            this.eot = dfa_49;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "3590:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA76_1 = input.LA(1);

                         
                        int index76_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index76_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA76_2 = input.LA(1);

                         
                        int index76_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index76_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA76_3 = input.LA(1);

                         
                        int index76_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index76_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA76_4 = input.LA(1);

                         
                        int index76_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred155_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index76_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA76_5 = input.LA(1);

                         
                        int index76_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index76_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA76_6 = input.LA(1);

                         
                        int index76_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index76_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA76_8 = input.LA(1);

                         
                        int index76_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred155_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index76_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA76_9 = input.LA(1);

                         
                        int index76_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 7;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index76_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA76_11 = input.LA(1);

                         
                        int index76_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred155_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index76_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA76_12 = input.LA(1);

                         
                        int index76_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred155_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index76_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA76_13 = input.LA(1);

                         
                        int index76_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred155_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index76_13);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA76_14 = input.LA(1);

                         
                        int index76_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred158_InternalKdl()) ) {s = 21;}

                         
                        input.seek(index76_14);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA76_15 = input.LA(1);

                         
                        int index76_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred154_InternalKdl()) ) {s = 18;}

                        else if ( (synpred156_InternalKdl()) ) {s = 22;}

                         
                        input.seek(index76_15);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 76, _s, input);
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

    class DFA89 extends DFA {

        public DFA89(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 89;
            this.eot = dfa_28;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_33;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "4277:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )";
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

    class DFA99 extends DFA {

        public DFA99(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 99;
            this.eot = dfa_60;
            this.eof = dfa_60;
            this.min = dfa_61;
            this.max = dfa_62;
            this.accept = dfa_63;
            this.special = dfa_64;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "4842:4: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA99_1 = input.LA(1);

                         
                        int index99_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred199_InternalKdl()) ) {s = 12;}

                        else if ( (synpred200_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index99_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA99_3 = input.LA(1);

                         
                        int index99_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred200_InternalKdl()) ) {s = 2;}

                        else if ( (synpred201_InternalKdl()) ) {s = 6;}

                         
                        input.seek(index99_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 99, _s, input);
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

    class DFA118 extends DFA {

        public DFA118(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 118;
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
