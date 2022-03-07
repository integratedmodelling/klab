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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_LOWERCASE_ID", "RULE_ANNOTATION_ID", "RULE_INT", "RULE_LOWERCASE_DASHID", "RULE_UPPERCASE_ID", "RULE_CAMELCASE_ID", "RULE_SHAPE", "RULE_BACKCASE_ID", "RULE_ID", "RULE_EXPR", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'@dataflow'", "'@var'", "'@val'", "'@author'", "'@version'", "'@klab'", "'@worldview'", "'@geometry'", "'@endpoint'", "'@namespace'", "'@coverage'", "','", "'@context'", "'('", "')'", "'export'", "'import'", "'label'", "'abstract'", "'final'", "'optional'", "'filter'", "'multiple'", "'+'", "'parameter'", "'expression'", "'*'", "'extends'", "'for'", "'{'", "'}'", "'minimum'", "'maximum'", "'range'", "'to'", "'values'", "'default'", "'unit'", "'as'", "'over'", "'geometry'", "'metadata'", "'class'", "'compute'", "'object'", "'event'", "'observation'", "'value'", "'process'", "'number'", "'concept'", "'boolean'", "'text'", "'list'", "'table'", "'map'", "'extent'", "'spatialextent'", "'temporalextent'", "'annotation'", "'enum'", "'void'", "'partition'", "'resolve'", "'models'", "'concepts'", "'observers'", "'definitions'", "'dependencies'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'in'", "'unknown'", "'urn:klab:'", "':'", "'#'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'='", "'>>'", "'<-'", "'>'", "'<'", "'!='", "'<='", "'>='", "'-'", "'e'", "'E'", "'^'"
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

                if ( (LA8_0==RULE_ANNOTATION_ID||(LA8_0>=35 && LA8_0<=36)||(LA8_0>=38 && LA8_0<=41)||LA8_0==44||LA8_0==53||(LA8_0>=64 && LA8_0<=83)) ) {
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

                    if ( ((LA9_0>=RULE_STRING && LA9_0<=RULE_LOWERCASE_ID)||(LA9_0>=RULE_INT && LA9_0<=RULE_CAMELCASE_ID)||(LA9_0>=RULE_ID && LA9_0<=RULE_EXPR)||LA9_0==31||LA9_0==33||LA9_0==43||LA9_0==49||(LA9_0>=89 && LA9_0<=90)||LA9_0==95||LA9_0==98||LA9_0==112) ) {
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
    // InternalKdl.g:682:1: ruleActorDefinition returns [EObject current=null] : ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? ) ) ;
    public final EObject ruleActorDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_exported_0_0=null;
        Token lv_imported_1_0=null;
        Token lv_annotationTag_3_0=null;
        Token lv_docstring_4_0=null;
        Token otherlv_5=null;
        Token lv_label_6_0=null;
        Token lv_abstract_8_0=null;
        Token lv_final_9_0=null;
        Token lv_optional_10_0=null;
        Token lv_exported_11_0=null;
        Token lv_filter_12_0=null;
        Token lv_imported_13_0=null;
        Token lv_multiple_14_0=null;
        Token lv_arity_15_0=null;
        Token lv_minimum_16_0=null;
        Token lv_parameter_17_0=null;
        Token lv_expression_19_0=null;
        Token lv_name_20_1=null;
        Token lv_name_20_2=null;
        Token lv_name_20_3=null;
        Token lv_name_20_4=null;
        Token otherlv_21=null;
        Token lv_extended_22_1=null;
        Token lv_extended_22_2=null;
        Token lv_extended_22_3=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token lv_docstring_27_0=null;
        Token otherlv_28=null;
        Token lv_label_29_0=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_39=null;
        Token otherlv_41=null;
        Token lv_enumValues_42_1=null;
        Token lv_enumValues_42_2=null;
        Token lv_enumValues_42_3=null;
        Token lv_enumValues_42_4=null;
        Token otherlv_43=null;
        Token lv_enumValues_44_1=null;
        Token lv_enumValues_44_2=null;
        Token lv_enumValues_44_3=null;
        Token lv_enumValues_44_4=null;
        Token otherlv_46=null;
        Token otherlv_48=null;
        Token otherlv_50=null;
        Token lv_localName_51_0=null;
        Token otherlv_52=null;
        Token otherlv_54=null;
        AntlrDatatypeRuleToken lv_type_2_0 = null;

        EObject lv_annotations_7_0 = null;

        AntlrDatatypeRuleToken lv_type_18_0 = null;

        AntlrDatatypeRuleToken lv_targets_24_0 = null;

        AntlrDatatypeRuleToken lv_targets_26_0 = null;

        EObject lv_body_31_0 = null;

        EObject lv_rangeMin_34_0 = null;

        EObject lv_rangeMax_36_0 = null;

        EObject lv_rangeMin_38_0 = null;

        EObject lv_rangeMax_40_0 = null;

        EObject lv_default_47_0 = null;

        EObject lv_unit_49_0 = null;

        EObject lv_coverage_53_0 = null;

        EObject lv_coverage_55_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15()
        	);

        try {
            // InternalKdl.g:691:2: ( ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? ) ) )
            // InternalKdl.g:692:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? ) )
            {
            // InternalKdl.g:692:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? ) )
            int alt40=2;
            alt40 = dfa40.predict(input);
            switch (alt40) {
                case 1 :
                    // InternalKdl.g:693:3: ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? )
                    {
                    // InternalKdl.g:693:3: ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? )
                    // InternalKdl.g:694:4: ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )?
                    {
                    // InternalKdl.g:694:4: ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) )
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==35) ) {
                        alt11=1;
                    }
                    else if ( (LA11_0==36) ) {
                        alt11=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 0, input);

                        throw nvae;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalKdl.g:695:5: ( (lv_exported_0_0= 'export' ) )
                            {
                            // InternalKdl.g:695:5: ( (lv_exported_0_0= 'export' ) )
                            // InternalKdl.g:696:6: (lv_exported_0_0= 'export' )
                            {
                            // InternalKdl.g:696:6: (lv_exported_0_0= 'export' )
                            // InternalKdl.g:697:7: lv_exported_0_0= 'export'
                            {
                            lv_exported_0_0=(Token)match(input,35,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_0_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_0_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "exported", lv_exported_0_0 != null, "export");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:710:5: ( (lv_imported_1_0= 'import' ) )
                            {
                            // InternalKdl.g:710:5: ( (lv_imported_1_0= 'import' ) )
                            // InternalKdl.g:711:6: (lv_imported_1_0= 'import' )
                            {
                            // InternalKdl.g:711:6: (lv_imported_1_0= 'import' )
                            // InternalKdl.g:712:7: lv_imported_1_0= 'import'
                            {
                            lv_imported_1_0=(Token)match(input,36,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_imported_1_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_0_0_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "imported", lv_imported_1_0 != null, "import");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:725:4: ( (lv_type_2_0= ruleACTOR ) )
                    // InternalKdl.g:726:5: (lv_type_2_0= ruleACTOR )
                    {
                    // InternalKdl.g:726:5: (lv_type_2_0= ruleACTOR )
                    // InternalKdl.g:727:6: lv_type_2_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
                    lv_type_2_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_2_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:744:4: ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) )
                    // InternalKdl.g:745:5: (lv_annotationTag_3_0= RULE_ANNOTATION_ID )
                    {
                    // InternalKdl.g:745:5: (lv_annotationTag_3_0= RULE_ANNOTATION_ID )
                    // InternalKdl.g:746:6: lv_annotationTag_3_0= RULE_ANNOTATION_ID
                    {
                    lv_annotationTag_3_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_annotationTag_3_0, grammarAccess.getActorDefinitionAccess().getAnnotationTagANNOTATION_IDTerminalRuleCall_0_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"annotationTag",
                      							lv_annotationTag_3_0,
                      							"org.integratedmodelling.kdl.Kdl.ANNOTATION_ID");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:762:4: ( (lv_docstring_4_0= RULE_STRING ) )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==RULE_STRING) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalKdl.g:763:5: (lv_docstring_4_0= RULE_STRING )
                            {
                            // InternalKdl.g:763:5: (lv_docstring_4_0= RULE_STRING )
                            // InternalKdl.g:764:6: lv_docstring_4_0= RULE_STRING
                            {
                            lv_docstring_4_0=(Token)match(input,RULE_STRING,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_4_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_0_3_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_4_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:780:4: (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==37) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalKdl.g:781:5: otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) )
                            {
                            otherlv_5=(Token)match(input,37,FOLLOW_6); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_5, grammarAccess.getActorDefinitionAccess().getLabelKeyword_0_4_0());
                              				
                            }
                            // InternalKdl.g:785:5: ( (lv_label_6_0= RULE_STRING ) )
                            // InternalKdl.g:786:6: (lv_label_6_0= RULE_STRING )
                            {
                            // InternalKdl.g:786:6: (lv_label_6_0= RULE_STRING )
                            // InternalKdl.g:787:7: lv_label_6_0= RULE_STRING
                            {
                            lv_label_6_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_label_6_0, grammarAccess.getActorDefinitionAccess().getLabelSTRINGTerminalRuleCall_0_4_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"label",
                              								lv_label_6_0,
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
                    // InternalKdl.g:806:3: ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:806:3: ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:807:4: ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )?
                    {
                    // InternalKdl.g:807:4: ( (lv_annotations_7_0= ruleAnnotation ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==RULE_ANNOTATION_ID) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalKdl.g:808:5: (lv_annotations_7_0= ruleAnnotation )
                    	    {
                    	    // InternalKdl.g:808:5: (lv_annotations_7_0= ruleAnnotation )
                    	    // InternalKdl.g:809:6: lv_annotations_7_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getAnnotationsAnnotationParserRuleCall_1_0_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_17);
                    	    lv_annotations_7_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"annotations",
                    	      							lv_annotations_7_0,
                    	      							"org.integratedmodelling.kdl.Kdl.Annotation");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    // InternalKdl.g:826:4: ( (lv_abstract_8_0= 'abstract' ) )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==38) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKdl.g:827:5: (lv_abstract_8_0= 'abstract' )
                            {
                            // InternalKdl.g:827:5: (lv_abstract_8_0= 'abstract' )
                            // InternalKdl.g:828:6: lv_abstract_8_0= 'abstract'
                            {
                            lv_abstract_8_0=(Token)match(input,38,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_abstract_8_0, grammarAccess.getActorDefinitionAccess().getAbstractAbstractKeyword_1_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "abstract", lv_abstract_8_0 != null, "abstract");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:840:4: ( (lv_final_9_0= 'final' ) )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==39) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKdl.g:841:5: (lv_final_9_0= 'final' )
                            {
                            // InternalKdl.g:841:5: (lv_final_9_0= 'final' )
                            // InternalKdl.g:842:6: lv_final_9_0= 'final'
                            {
                            lv_final_9_0=(Token)match(input,39,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_final_9_0, grammarAccess.getActorDefinitionAccess().getFinalFinalKeyword_1_2_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "final", lv_final_9_0 != null, "final");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:854:4: ( (lv_optional_10_0= 'optional' ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==40) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:855:5: (lv_optional_10_0= 'optional' )
                            {
                            // InternalKdl.g:855:5: (lv_optional_10_0= 'optional' )
                            // InternalKdl.g:856:6: lv_optional_10_0= 'optional'
                            {
                            lv_optional_10_0=(Token)match(input,40,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_optional_10_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_3_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "optional", lv_optional_10_0 != null, "optional");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:868:4: ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )?
                    int alt20=4;
                    switch ( input.LA(1) ) {
                        case 35:
                            {
                            alt20=1;
                            }
                            break;
                        case 41:
                            {
                            alt20=2;
                            }
                            break;
                        case 36:
                            {
                            alt20=3;
                            }
                            break;
                    }

                    switch (alt20) {
                        case 1 :
                            // InternalKdl.g:869:5: ( (lv_exported_11_0= 'export' ) )
                            {
                            // InternalKdl.g:869:5: ( (lv_exported_11_0= 'export' ) )
                            // InternalKdl.g:870:6: (lv_exported_11_0= 'export' )
                            {
                            // InternalKdl.g:870:6: (lv_exported_11_0= 'export' )
                            // InternalKdl.g:871:7: lv_exported_11_0= 'export'
                            {
                            lv_exported_11_0=(Token)match(input,35,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_11_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "exported", lv_exported_11_0 != null, "export");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:884:5: ( (lv_filter_12_0= 'filter' ) )
                            {
                            // InternalKdl.g:884:5: ( (lv_filter_12_0= 'filter' ) )
                            // InternalKdl.g:885:6: (lv_filter_12_0= 'filter' )
                            {
                            // InternalKdl.g:885:6: (lv_filter_12_0= 'filter' )
                            // InternalKdl.g:886:7: lv_filter_12_0= 'filter'
                            {
                            lv_filter_12_0=(Token)match(input,41,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_filter_12_0, grammarAccess.getActorDefinitionAccess().getFilterFilterKeyword_1_4_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "filter", lv_filter_12_0 != null, "filter");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKdl.g:899:5: ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? )
                            {
                            // InternalKdl.g:899:5: ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? )
                            // InternalKdl.g:900:6: ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )?
                            {
                            // InternalKdl.g:900:6: ( (lv_imported_13_0= 'import' ) )
                            // InternalKdl.g:901:7: (lv_imported_13_0= 'import' )
                            {
                            // InternalKdl.g:901:7: (lv_imported_13_0= 'import' )
                            // InternalKdl.g:902:8: lv_imported_13_0= 'import'
                            {
                            lv_imported_13_0=(Token)match(input,36,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_imported_13_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_1_4_2_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getActorDefinitionRule());
                              								}
                              								setWithLastConsumed(current, "imported", lv_imported_13_0 != null, "import");
                              							
                            }

                            }


                            }

                            // InternalKdl.g:914:6: ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )?
                            int alt19=3;
                            int LA19_0 = input.LA(1);

                            if ( (LA19_0==42) ) {
                                alt19=1;
                            }
                            else if ( (LA19_0==RULE_INT) ) {
                                alt19=2;
                            }
                            switch (alt19) {
                                case 1 :
                                    // InternalKdl.g:915:7: ( (lv_multiple_14_0= 'multiple' ) )
                                    {
                                    // InternalKdl.g:915:7: ( (lv_multiple_14_0= 'multiple' ) )
                                    // InternalKdl.g:916:8: (lv_multiple_14_0= 'multiple' )
                                    {
                                    // InternalKdl.g:916:8: (lv_multiple_14_0= 'multiple' )
                                    // InternalKdl.g:917:9: lv_multiple_14_0= 'multiple'
                                    {
                                    lv_multiple_14_0=(Token)match(input,42,FOLLOW_17); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_multiple_14_0, grammarAccess.getActorDefinitionAccess().getMultipleMultipleKeyword_1_4_2_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									setWithLastConsumed(current, "multiple", lv_multiple_14_0 != null, "multiple");
                                      								
                                    }

                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:930:7: ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? )
                                    {
                                    // InternalKdl.g:930:7: ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? )
                                    // InternalKdl.g:931:8: ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )?
                                    {
                                    // InternalKdl.g:931:8: ( (lv_arity_15_0= RULE_INT ) )
                                    // InternalKdl.g:932:9: (lv_arity_15_0= RULE_INT )
                                    {
                                    // InternalKdl.g:932:9: (lv_arity_15_0= RULE_INT )
                                    // InternalKdl.g:933:10: lv_arity_15_0= RULE_INT
                                    {
                                    lv_arity_15_0=(Token)match(input,RULE_INT,FOLLOW_22); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      										newLeafNode(lv_arity_15_0, grammarAccess.getActorDefinitionAccess().getArityINTTerminalRuleCall_1_4_2_1_1_0_0());
                                      									
                                    }
                                    if ( state.backtracking==0 ) {

                                      										if (current==null) {
                                      											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      										}
                                      										setWithLastConsumed(
                                      											current,
                                      											"arity",
                                      											lv_arity_15_0,
                                      											"org.eclipse.xtext.common.Terminals.INT");
                                      									
                                    }

                                    }


                                    }

                                    // InternalKdl.g:949:8: ( (lv_minimum_16_0= '+' ) )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==43) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // InternalKdl.g:950:9: (lv_minimum_16_0= '+' )
                                            {
                                            // InternalKdl.g:950:9: (lv_minimum_16_0= '+' )
                                            // InternalKdl.g:951:10: lv_minimum_16_0= '+'
                                            {
                                            lv_minimum_16_0=(Token)match(input,43,FOLLOW_17); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              										newLeafNode(lv_minimum_16_0, grammarAccess.getActorDefinitionAccess().getMinimumPlusSignKeyword_1_4_2_1_1_1_0());
                                              									
                                            }
                                            if ( state.backtracking==0 ) {

                                              										if (current==null) {
                                              											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                              										}
                                              										setWithLastConsumed(current, "minimum", lv_minimum_16_0 != null, "+");
                                              									
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

                    // InternalKdl.g:967:4: ( (lv_parameter_17_0= 'parameter' ) )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==44) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // InternalKdl.g:968:5: (lv_parameter_17_0= 'parameter' )
                            {
                            // InternalKdl.g:968:5: (lv_parameter_17_0= 'parameter' )
                            // InternalKdl.g:969:6: lv_parameter_17_0= 'parameter'
                            {
                            lv_parameter_17_0=(Token)match(input,44,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_parameter_17_0, grammarAccess.getActorDefinitionAccess().getParameterParameterKeyword_1_5_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "parameter", lv_parameter_17_0 != null, "parameter");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:981:4: ( (lv_type_18_0= ruleACTOR ) )
                    // InternalKdl.g:982:5: (lv_type_18_0= ruleACTOR )
                    {
                    // InternalKdl.g:982:5: (lv_type_18_0= ruleACTOR )
                    // InternalKdl.g:983:6: lv_type_18_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_1_6_0());
                      					
                    }
                    pushFollow(FOLLOW_23);
                    lv_type_18_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_18_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1000:4: ( (lv_expression_19_0= 'expression' ) )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==45) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalKdl.g:1001:5: (lv_expression_19_0= 'expression' )
                            {
                            // InternalKdl.g:1001:5: (lv_expression_19_0= 'expression' )
                            // InternalKdl.g:1002:6: lv_expression_19_0= 'expression'
                            {
                            lv_expression_19_0=(Token)match(input,45,FOLLOW_24); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_expression_19_0, grammarAccess.getActorDefinitionAccess().getExpressionExpressionKeyword_1_7_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(current, "expression", lv_expression_19_0 != null, "expression");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1014:4: ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) )
                    // InternalKdl.g:1015:5: ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) )
                    {
                    // InternalKdl.g:1015:5: ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) )
                    // InternalKdl.g:1016:6: (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' )
                    {
                    // InternalKdl.g:1016:6: (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' )
                    int alt23=4;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt23=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt23=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt23=3;
                        }
                        break;
                    case 46:
                        {
                        alt23=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 0, input);

                        throw nvae;
                    }

                    switch (alt23) {
                        case 1 :
                            // InternalKdl.g:1017:7: lv_name_20_1= RULE_LOWERCASE_ID
                            {
                            lv_name_20_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_20_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_8_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_20_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1032:7: lv_name_20_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_20_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_20_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_1_8_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_20_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:1047:7: lv_name_20_3= RULE_STRING
                            {
                            lv_name_20_3=(Token)match(input,RULE_STRING,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_20_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_1_8_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_20_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;
                        case 4 :
                            // InternalKdl.g:1062:7: lv_name_20_4= '*'
                            {
                            lv_name_20_4=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_20_4, grammarAccess.getActorDefinitionAccess().getNameAsteriskKeyword_1_8_0_3());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(current, "name", lv_name_20_4, null);
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:1075:4: (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==47) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // InternalKdl.g:1076:5: otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) )
                            {
                            otherlv_21=(Token)match(input,47,FOLLOW_26); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_21, grammarAccess.getActorDefinitionAccess().getExtendsKeyword_1_9_0());
                              				
                            }
                            // InternalKdl.g:1080:5: ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) )
                            // InternalKdl.g:1081:6: ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) )
                            {
                            // InternalKdl.g:1081:6: ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) )
                            // InternalKdl.g:1082:7: (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING )
                            {
                            // InternalKdl.g:1082:7: (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING )
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
                                    // InternalKdl.g:1083:8: lv_extended_22_1= RULE_LOWERCASE_ID
                                    {
                                    lv_extended_22_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_22_1, grammarAccess.getActorDefinitionAccess().getExtendedLOWERCASE_IDTerminalRuleCall_1_9_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_22_1,
                                      									"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1098:8: lv_extended_22_2= RULE_LOWERCASE_DASHID
                                    {
                                    lv_extended_22_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_22_2, grammarAccess.getActorDefinitionAccess().getExtendedLOWERCASE_DASHIDTerminalRuleCall_1_9_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_22_2,
                                      									"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1113:8: lv_extended_22_3= RULE_STRING
                                    {
                                    lv_extended_22_3=(Token)match(input,RULE_STRING,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_extended_22_3, grammarAccess.getActorDefinitionAccess().getExtendedSTRINGTerminalRuleCall_1_9_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"extended",
                                      									lv_extended_22_3,
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

                    // InternalKdl.g:1131:4: (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==48) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalKdl.g:1132:5: otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )*
                            {
                            otherlv_23=(Token)match(input,48,FOLLOW_28); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_23, grammarAccess.getActorDefinitionAccess().getForKeyword_1_10_0());
                              				
                            }
                            // InternalKdl.g:1136:5: ( (lv_targets_24_0= ruleTARGET ) )
                            // InternalKdl.g:1137:6: (lv_targets_24_0= ruleTARGET )
                            {
                            // InternalKdl.g:1137:6: (lv_targets_24_0= ruleTARGET )
                            // InternalKdl.g:1138:7: lv_targets_24_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_1_10_1_0());
                              						
                            }
                            pushFollow(FOLLOW_29);
                            lv_targets_24_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_24_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1155:5: (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )*
                            loop26:
                            do {
                                int alt26=2;
                                int LA26_0 = input.LA(1);

                                if ( (LA26_0==31) ) {
                                    alt26=1;
                                }


                                switch (alt26) {
                            	case 1 :
                            	    // InternalKdl.g:1156:6: otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) )
                            	    {
                            	    otherlv_25=(Token)match(input,31,FOLLOW_28); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_25, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_10_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1160:6: ( (lv_targets_26_0= ruleTARGET ) )
                            	    // InternalKdl.g:1161:7: (lv_targets_26_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:1161:7: (lv_targets_26_0= ruleTARGET )
                            	    // InternalKdl.g:1162:8: lv_targets_26_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_1_10_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_29);
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


                            	    }
                            	    break;

                            	default :
                            	    break loop26;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:1181:4: ( (lv_docstring_27_0= RULE_STRING ) )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==RULE_STRING) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalKdl.g:1182:5: (lv_docstring_27_0= RULE_STRING )
                            {
                            // InternalKdl.g:1182:5: (lv_docstring_27_0= RULE_STRING )
                            // InternalKdl.g:1183:6: lv_docstring_27_0= RULE_STRING
                            {
                            lv_docstring_27_0=(Token)match(input,RULE_STRING,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_27_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_11_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_27_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1199:4: (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==37) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalKdl.g:1200:5: otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) )
                            {
                            otherlv_28=(Token)match(input,37,FOLLOW_6); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_28, grammarAccess.getActorDefinitionAccess().getLabelKeyword_1_12_0());
                              				
                            }
                            // InternalKdl.g:1204:5: ( (lv_label_29_0= RULE_STRING ) )
                            // InternalKdl.g:1205:6: (lv_label_29_0= RULE_STRING )
                            {
                            // InternalKdl.g:1205:6: (lv_label_29_0= RULE_STRING )
                            // InternalKdl.g:1206:7: lv_label_29_0= RULE_STRING
                            {
                            lv_label_29_0=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_label_29_0, grammarAccess.getActorDefinitionAccess().getLabelSTRINGTerminalRuleCall_1_12_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"label",
                              								lv_label_29_0,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1223:4: (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==49) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalKdl.g:1224:5: otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}'
                            {
                            otherlv_30=(Token)match(input,49,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_30, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_13_0());
                              				
                            }
                            // InternalKdl.g:1228:5: ( (lv_body_31_0= ruleDataflowBody ) )
                            // InternalKdl.g:1229:6: (lv_body_31_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1229:6: (lv_body_31_0= ruleDataflowBody )
                            // InternalKdl.g:1230:7: lv_body_31_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_13_1_0());
                              						
                            }
                            pushFollow(FOLLOW_33);
                            lv_body_31_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_31_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_32=(Token)match(input,50,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_32, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_13_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1252:4: ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )?
                    int alt35=3;
                    switch ( input.LA(1) ) {
                        case 51:
                        case 52:
                            {
                            alt35=1;
                            }
                            break;
                        case 53:
                            {
                            int LA35_2 = input.LA(2);

                            if ( (LA35_2==RULE_INT||LA35_2==43||LA35_2==112) ) {
                                alt35=1;
                            }
                            }
                            break;
                        case 55:
                            {
                            alt35=2;
                            }
                            break;
                    }

                    switch (alt35) {
                        case 1 :
                            // InternalKdl.g:1253:5: ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) )
                            {
                            // InternalKdl.g:1253:5: ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) )
                            int alt31=3;
                            switch ( input.LA(1) ) {
                            case 51:
                                {
                                alt31=1;
                                }
                                break;
                            case 52:
                                {
                                alt31=2;
                                }
                                break;
                            case 53:
                                {
                                alt31=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 31, 0, input);

                                throw nvae;
                            }

                            switch (alt31) {
                                case 1 :
                                    // InternalKdl.g:1254:6: (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1254:6: (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) )
                                    // InternalKdl.g:1255:7: otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) )
                                    {
                                    otherlv_33=(Token)match(input,51,FOLLOW_35); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_33, grammarAccess.getActorDefinitionAccess().getMinimumKeyword_1_14_0_0_0());
                                      						
                                    }
                                    // InternalKdl.g:1259:7: ( (lv_rangeMin_34_0= ruleNumber ) )
                                    // InternalKdl.g:1260:8: (lv_rangeMin_34_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1260:8: (lv_rangeMin_34_0= ruleNumber )
                                    // InternalKdl.g:1261:9: lv_rangeMin_34_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_14_0_0_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_36);
                                    lv_rangeMin_34_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMin",
                                      										lv_rangeMin_34_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1280:6: (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1280:6: (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) )
                                    // InternalKdl.g:1281:7: otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) )
                                    {
                                    otherlv_35=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_35, grammarAccess.getActorDefinitionAccess().getMaximumKeyword_1_14_0_1_0());
                                      						
                                    }
                                    // InternalKdl.g:1285:7: ( (lv_rangeMax_36_0= ruleNumber ) )
                                    // InternalKdl.g:1286:8: (lv_rangeMax_36_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1286:8: (lv_rangeMax_36_0= ruleNumber )
                                    // InternalKdl.g:1287:9: lv_rangeMax_36_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_14_0_1_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_36);
                                    lv_rangeMax_36_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMax",
                                      										lv_rangeMax_36_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1306:6: (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) )
                                    {
                                    // InternalKdl.g:1306:6: (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) )
                                    // InternalKdl.g:1307:7: otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) )
                                    {
                                    otherlv_37=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_37, grammarAccess.getActorDefinitionAccess().getRangeKeyword_1_14_0_2_0());
                                      						
                                    }
                                    // InternalKdl.g:1311:7: ( (lv_rangeMin_38_0= ruleNumber ) )
                                    // InternalKdl.g:1312:8: (lv_rangeMin_38_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1312:8: (lv_rangeMin_38_0= ruleNumber )
                                    // InternalKdl.g:1313:9: lv_rangeMin_38_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_14_0_2_1_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_37);
                                    lv_rangeMin_38_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMin",
                                      										lv_rangeMin_38_0,
                                      										"org.integratedmodelling.kdl.Kdl.Number");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }


                                    }

                                    otherlv_39=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      							newLeafNode(otherlv_39, grammarAccess.getActorDefinitionAccess().getToKeyword_1_14_0_2_2());
                                      						
                                    }
                                    // InternalKdl.g:1334:7: ( (lv_rangeMax_40_0= ruleNumber ) )
                                    // InternalKdl.g:1335:8: (lv_rangeMax_40_0= ruleNumber )
                                    {
                                    // InternalKdl.g:1335:8: (lv_rangeMax_40_0= ruleNumber )
                                    // InternalKdl.g:1336:9: lv_rangeMax_40_0= ruleNumber
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_14_0_2_3_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_36);
                                    lv_rangeMax_40_0=ruleNumber();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									set(
                                      										current,
                                      										"rangeMax",
                                      										lv_rangeMax_40_0,
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
                            // InternalKdl.g:1356:5: (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* )
                            {
                            // InternalKdl.g:1356:5: (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* )
                            // InternalKdl.g:1357:6: otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )*
                            {
                            otherlv_41=(Token)match(input,55,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_41, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_14_1_0());
                              					
                            }
                            // InternalKdl.g:1361:6: ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) )
                            // InternalKdl.g:1362:7: ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) )
                            {
                            // InternalKdl.g:1362:7: ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) )
                            // InternalKdl.g:1363:8: (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID )
                            {
                            // InternalKdl.g:1363:8: (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID )
                            int alt32=4;
                            switch ( input.LA(1) ) {
                            case RULE_STRING:
                                {
                                alt32=1;
                                }
                                break;
                            case RULE_UPPERCASE_ID:
                                {
                                alt32=2;
                                }
                                break;
                            case RULE_LOWERCASE_ID:
                                {
                                alt32=3;
                                }
                                break;
                            case RULE_CAMELCASE_ID:
                                {
                                alt32=4;
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
                                    // InternalKdl.g:1364:9: lv_enumValues_42_1= RULE_STRING
                                    {
                                    lv_enumValues_42_1=(Token)match(input,RULE_STRING,FOLLOW_39); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_42_1, grammarAccess.getActorDefinitionAccess().getEnumValuesSTRINGTerminalRuleCall_1_14_1_1_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_42_1,
                                      										"org.eclipse.xtext.common.Terminals.STRING");
                                      								
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1379:9: lv_enumValues_42_2= RULE_UPPERCASE_ID
                                    {
                                    lv_enumValues_42_2=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_39); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_42_2, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_14_1_1_0_1());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_42_2,
                                      										"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                                      								
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:1394:9: lv_enumValues_42_3= RULE_LOWERCASE_ID
                                    {
                                    lv_enumValues_42_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_39); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_42_3, grammarAccess.getActorDefinitionAccess().getEnumValuesLOWERCASE_IDTerminalRuleCall_1_14_1_1_0_2());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_42_3,
                                      										"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                                      								
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:1409:9: lv_enumValues_42_4= RULE_CAMELCASE_ID
                                    {
                                    lv_enumValues_42_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_39); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_enumValues_42_4, grammarAccess.getActorDefinitionAccess().getEnumValuesCAMELCASE_IDTerminalRuleCall_1_14_1_1_0_3());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      									}
                                      									addWithLastConsumed(
                                      										current,
                                      										"enumValues",
                                      										lv_enumValues_42_4,
                                      										"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
                                      								
                                    }

                                    }
                                    break;

                            }


                            }


                            }

                            // InternalKdl.g:1426:6: (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )*
                            loop34:
                            do {
                                int alt34=2;
                                int LA34_0 = input.LA(1);

                                if ( (LA34_0==31) ) {
                                    alt34=1;
                                }


                                switch (alt34) {
                            	case 1 :
                            	    // InternalKdl.g:1427:7: otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) )
                            	    {
                            	    otherlv_43=(Token)match(input,31,FOLLOW_38); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_43, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_14_1_2_0());
                            	      						
                            	    }
                            	    // InternalKdl.g:1431:7: ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) )
                            	    // InternalKdl.g:1432:8: ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) )
                            	    {
                            	    // InternalKdl.g:1432:8: ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) )
                            	    // InternalKdl.g:1433:9: (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID )
                            	    {
                            	    // InternalKdl.g:1433:9: (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID )
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
                            	            // InternalKdl.g:1434:10: lv_enumValues_44_1= RULE_STRING
                            	            {
                            	            lv_enumValues_44_1=(Token)match(input,RULE_STRING,FOLLOW_39); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_44_1, grammarAccess.getActorDefinitionAccess().getEnumValuesSTRINGTerminalRuleCall_1_14_1_2_1_0_0());
                            	              									
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
                            	            // InternalKdl.g:1449:10: lv_enumValues_44_2= RULE_UPPERCASE_ID
                            	            {
                            	            lv_enumValues_44_2=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_39); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_44_2, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_14_1_2_1_0_1());
                            	              									
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
                            	            // InternalKdl.g:1464:10: lv_enumValues_44_3= RULE_LOWERCASE_ID
                            	            {
                            	            lv_enumValues_44_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_39); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_44_3, grammarAccess.getActorDefinitionAccess().getEnumValuesLOWERCASE_IDTerminalRuleCall_1_14_1_2_1_0_2());
                            	              									
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
                            	            // InternalKdl.g:1479:10: lv_enumValues_44_4= RULE_CAMELCASE_ID
                            	            {
                            	            lv_enumValues_44_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_39); if (state.failed) return current;
                            	            if ( state.backtracking==0 ) {

                            	              										newLeafNode(lv_enumValues_44_4, grammarAccess.getActorDefinitionAccess().getEnumValuesCAMELCASE_IDTerminalRuleCall_1_14_1_2_1_0_3());
                            	              									
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


                            	    }
                            	    break;

                            	default :
                            	    break loop34;
                                }
                            } while (true);


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1499:4: ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) )
                    // InternalKdl.g:1500:5: ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) )
                    {
                    // InternalKdl.g:1500:5: ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) )
                    // InternalKdl.g:1501:6: ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* )
                    {
                    getUnorderedGroupHelper().enter(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15());
                    // InternalKdl.g:1504:6: ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* )
                    // InternalKdl.g:1505:7: ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )*
                    {
                    // InternalKdl.g:1505:7: ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )*
                    loop36:
                    do {
                        int alt36=3;
                        int LA36_0 = input.LA(1);

                        if ( LA36_0 == 56 && getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0) ) {
                            alt36=1;
                        }
                        else if ( LA36_0 == 57 && getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1) ) {
                            alt36=2;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalKdl.g:1506:5: ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) )
                    	    {
                    	    // InternalKdl.g:1506:5: ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) )
                    	    // InternalKdl.g:1507:6: {...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) )
                    	    {
                    	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0)");
                    	    }
                    	    // InternalKdl.g:1507:116: ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) )
                    	    // InternalKdl.g:1508:7: ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) )
                    	    {
                    	    getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0);
                    	    // InternalKdl.g:1511:10: ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) )
                    	    // InternalKdl.g:1511:11: {...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) )
                    	    {
                    	    if ( !((true)) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "true");
                    	    }
                    	    // InternalKdl.g:1511:20: (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) )
                    	    // InternalKdl.g:1511:21: otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) )
                    	    {
                    	    otherlv_46=(Token)match(input,56,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      										newLeafNode(otherlv_46, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_15_0_0());
                    	      									
                    	    }
                    	    // InternalKdl.g:1515:10: ( (lv_default_47_0= ruleValue ) )
                    	    // InternalKdl.g:1516:11: (lv_default_47_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:1516:11: (lv_default_47_0= ruleValue )
                    	    // InternalKdl.g:1517:12: lv_default_47_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      												newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_15_0_1_0());
                    	      											
                    	    }
                    	    pushFollow(FOLLOW_36);
                    	    lv_default_47_0=ruleValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      												if (current==null) {
                    	      													current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      												}
                    	      												set(
                    	      													current,
                    	      													"default",
                    	      													lv_default_47_0,
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
                    	    // InternalKdl.g:1540:5: ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) )
                    	    {
                    	    // InternalKdl.g:1540:5: ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) )
                    	    // InternalKdl.g:1541:6: {...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) )
                    	    {
                    	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1)");
                    	    }
                    	    // InternalKdl.g:1541:116: ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) )
                    	    // InternalKdl.g:1542:7: ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) )
                    	    {
                    	    getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1);
                    	    // InternalKdl.g:1545:10: ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) )
                    	    // InternalKdl.g:1545:11: {...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) )
                    	    {
                    	    if ( !((true)) ) {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        throw new FailedPredicateException(input, "ruleActorDefinition", "true");
                    	    }
                    	    // InternalKdl.g:1545:20: (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) )
                    	    // InternalKdl.g:1545:21: otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) )
                    	    {
                    	    otherlv_48=(Token)match(input,57,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      										newLeafNode(otherlv_48, grammarAccess.getActorDefinitionAccess().getUnitKeyword_1_15_1_0());
                    	      									
                    	    }
                    	    // InternalKdl.g:1549:10: ( (lv_unit_49_0= ruleUnit ) )
                    	    // InternalKdl.g:1550:11: (lv_unit_49_0= ruleUnit )
                    	    {
                    	    // InternalKdl.g:1550:11: (lv_unit_49_0= ruleUnit )
                    	    // InternalKdl.g:1551:12: lv_unit_49_0= ruleUnit
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      												newCompositeNode(grammarAccess.getActorDefinitionAccess().getUnitUnitParserRuleCall_1_15_1_1_0());
                    	      											
                    	    }
                    	    pushFollow(FOLLOW_36);
                    	    lv_unit_49_0=ruleUnit();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      												if (current==null) {
                    	      													current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                    	      												}
                    	      												set(
                    	      													current,
                    	      													"unit",
                    	      													lv_unit_49_0,
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
                    	    break loop36;
                        }
                    } while (true);


                    }


                    }

                    getUnorderedGroupHelper().leave(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15());

                    }

                    // InternalKdl.g:1581:4: (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==58) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // InternalKdl.g:1582:5: otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_50=(Token)match(input,58,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_50, grammarAccess.getActorDefinitionAccess().getAsKeyword_1_16_0());
                              				
                            }
                            // InternalKdl.g:1586:5: ( (lv_localName_51_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:1587:6: (lv_localName_51_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:1587:6: (lv_localName_51_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:1588:7: lv_localName_51_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_51_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_42); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_51_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_1_16_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_51_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1605:4: (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==59) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // InternalKdl.g:1606:5: otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )*
                            {
                            otherlv_52=(Token)match(input,59,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_52, grammarAccess.getActorDefinitionAccess().getOverKeyword_1_17_0());
                              				
                            }
                            // InternalKdl.g:1610:5: ( (lv_coverage_53_0= ruleFunction ) )
                            // InternalKdl.g:1611:6: (lv_coverage_53_0= ruleFunction )
                            {
                            // InternalKdl.g:1611:6: (lv_coverage_53_0= ruleFunction )
                            // InternalKdl.g:1612:7: lv_coverage_53_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_1_17_1_0());
                              						
                            }
                            pushFollow(FOLLOW_43);
                            lv_coverage_53_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_53_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1629:5: (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )*
                            loop38:
                            do {
                                int alt38=2;
                                int LA38_0 = input.LA(1);

                                if ( (LA38_0==31) ) {
                                    alt38=1;
                                }


                                switch (alt38) {
                            	case 1 :
                            	    // InternalKdl.g:1630:6: otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) )
                            	    {
                            	    otherlv_54=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_54, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_17_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1634:6: ( (lv_coverage_55_0= ruleFunction ) )
                            	    // InternalKdl.g:1635:7: (lv_coverage_55_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:1635:7: (lv_coverage_55_0= ruleFunction )
                            	    // InternalKdl.g:1636:8: lv_coverage_55_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_1_17_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_43);
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


                            	    }
                            	    break;

                            	default :
                            	    break loop38;
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
    // InternalKdl.g:1663:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKdl.g:1667:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1668:2: iv_ruleDataflowBody= ruleDataflowBody EOF
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
    // InternalKdl.g:1677:1: ruleDataflowBody returns [EObject current=null] : ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
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
            // InternalKdl.g:1686:2: ( ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1687:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1687:2: ( () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1688:3: () ( (lv_dataflows_1_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1688:3: ()
            // InternalKdl.g:1689:4: 
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

            // InternalKdl.g:1698:3: ( (lv_dataflows_1_0= ruleActorDefinition ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==RULE_ANNOTATION_ID||(LA41_0>=35 && LA41_0<=36)||(LA41_0>=38 && LA41_0<=41)||LA41_0==44||LA41_0==53||(LA41_0>=64 && LA41_0<=83)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalKdl.g:1699:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1699:4: (lv_dataflows_1_0= ruleActorDefinition )
            	    // InternalKdl.g:1700:5: lv_dataflows_1_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDataflowBodyAccess().getDataflowsActorDefinitionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    break loop41;
                }
            } while (true);

            // InternalKdl.g:1717:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1718:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1718:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1719:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2());
            // InternalKdl.g:1722:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1723:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1723:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+
            int cnt45=0;
            loop45:
            do {
                int alt45=4;
                alt45 = dfa45.predict(input);
                switch (alt45) {
            	case 1 :
            	    // InternalKdl.g:1724:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1724:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1725:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKdl.g:1725:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1726:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
            	    // InternalKdl.g:1729:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1729:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1729:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1729:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
            	    {
            	    otherlv_3=(Token)match(input,60,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_3, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1733:9: ( (lv_geometry_4_0= ruleGeometry ) )
            	    // InternalKdl.g:1734:10: (lv_geometry_4_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1734:10: (lv_geometry_4_0= ruleGeometry )
            	    // InternalKdl.g:1735:11: lv_geometry_4_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_45);
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
            	    // InternalKdl.g:1758:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
            	    {
            	    // InternalKdl.g:1758:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
            	    // InternalKdl.g:1759:5: {...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKdl.g:1759:109: ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
            	    // InternalKdl.g:1760:6: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
            	    // InternalKdl.g:1763:9: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
            	    // InternalKdl.g:1763:10: {...}? => ( (lv_computations_5_0= ruleComputation ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1763:19: ( (lv_computations_5_0= ruleComputation ) )
            	    // InternalKdl.g:1763:20: (lv_computations_5_0= ruleComputation )
            	    {
            	    // InternalKdl.g:1763:20: (lv_computations_5_0= ruleComputation )
            	    // InternalKdl.g:1764:10: lv_computations_5_0= ruleComputation
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_2_1_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_45);
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
            	    // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
            	    // InternalKdl.g:1787:5: {...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKdl.g:1787:109: ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
            	    // InternalKdl.g:1788:6: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
            	    // InternalKdl.g:1791:9: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
            	    // InternalKdl.g:1791:10: {...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1791:19: ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
            	    // InternalKdl.g:1791:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
            	    {
            	    // InternalKdl.g:1791:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )?
            	    int alt42=2;
            	    int LA42_0 = input.LA(1);

            	    if ( (LA42_0==61) ) {
            	        int LA42_1 = input.LA(2);

            	        if ( (synpred68_InternalKdl()) ) {
            	            alt42=1;
            	        }
            	    }
            	    switch (alt42) {
            	        case 1 :
            	            // InternalKdl.g:1792:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
            	            {
            	            otherlv_6=(Token)match(input,61,FOLLOW_46); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_6, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_2_2_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1796:10: ( (lv_metadata_7_0= ruleMetadata ) )
            	            // InternalKdl.g:1797:11: (lv_metadata_7_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1797:11: (lv_metadata_7_0= ruleMetadata )
            	            // InternalKdl.g:1798:12: lv_metadata_7_0= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_2_0_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_45);
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

            	    // InternalKdl.g:1816:9: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
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
            	            // InternalKdl.g:1817:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
            	            {
            	            otherlv_8=(Token)match(input,62,FOLLOW_9); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_8, grammarAccess.getDataflowBodyAccess().getClassKeyword_2_2_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1821:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
            	            // InternalKdl.g:1822:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
            	            {
            	            // InternalKdl.g:1822:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
            	            // InternalKdl.g:1823:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
            	            {
            	            // InternalKdl.g:1823:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
            	            int alt43=2;
            	            int LA43_0 = input.LA(1);

            	            if ( (LA43_0==RULE_LOWERCASE_ID) ) {
            	                alt43=1;
            	            }
            	            else if ( (LA43_0==RULE_STRING) ) {
            	                alt43=2;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return current;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 43, 0, input);

            	                throw nvae;
            	            }
            	            switch (alt43) {
            	                case 1 :
            	                    // InternalKdl.g:1824:13: lv_javaClass_9_1= ruleJavaClass
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      													newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_2_2_1_1_0_0());
            	                      												
            	                    }
            	                    pushFollow(FOLLOW_45);
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
            	                    // InternalKdl.g:1840:13: lv_javaClass_9_2= RULE_STRING
            	                    {
            	                    lv_javaClass_9_2=(Token)match(input,RULE_STRING,FOLLOW_45); if (state.failed) return current;
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
            	    if ( cnt45 >= 1 ) break loop45;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(45, input);
                        throw eee;
                }
                cnt45++;
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
    // InternalKdl.g:1879:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1879:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1880:2: iv_ruleComputation= ruleComputation EOF
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
    // InternalKdl.g:1886:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1892:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1893:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1893:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1894:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,63,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1898:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1899:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1899:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1900:5: lv_functions_1_0= ruleFunction
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_43);
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

            // InternalKdl.g:1917:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==31) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalKdl.g:1918:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1922:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1923:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1923:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1924:6: lv_functions_3_0= ruleFunction
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getComputationAccess().getFunctionsFunctionParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
            	    break loop46;
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
    // InternalKdl.g:1946:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1946:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1947:2: iv_ruleGeometry= ruleGeometry EOF
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
    // InternalKdl.g:1953:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1959:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1960:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1960:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==46) ) {
                alt48=1;
            }
            else if ( (LA48_0==RULE_SHAPE) ) {
                alt48=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // InternalKdl.g:1961:3: kw= '*'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1967:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1967:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1968:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_43); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:1975:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==31) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalKdl.g:1976:5: kw= ',' this_SHAPE_3= RULE_SHAPE
                    	    {
                    	    kw=(Token)match(input,31,FOLLOW_47); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getGeometryAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    this_SHAPE_3=(Token)match(input,RULE_SHAPE,FOLLOW_43); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_SHAPE_3);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_SHAPE_3, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_1_1());
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop47;
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
    // InternalKdl.g:1994:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:1994:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:1995:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalKdl.g:2001:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2007:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:2008:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:2008:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:2009:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:2009:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:2010:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:2010:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:2011:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
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

            // InternalKdl.g:2027:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:2028:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:2028:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:2029:5: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_48);
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

            // InternalKdl.g:2046:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==RULE_STRING) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalKdl.g:2047:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:2047:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:2048:5: lv_docstring_2_0= RULE_STRING
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
    // InternalKdl.g:2068:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:2068:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:2069:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKdl.g:2075:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKdl.g:2081:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKdl.g:2082:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKdl.g:2082:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_LOWERCASE_ID||(LA51_0>=RULE_UPPERCASE_ID && LA51_0<=RULE_CAMELCASE_ID)||LA51_0==RULE_BACKCASE_ID) ) {
                alt51=1;
            }
            else if ( (LA51_0==33) ) {
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
                    // InternalKdl.g:2083:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) )
                    {
                    // InternalKdl.g:2083:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) ) )
                    // InternalKdl.g:2084:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) )
                    {
                    // InternalKdl.g:2084:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID ) )
                    // InternalKdl.g:2085:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID )
                    {
                    // InternalKdl.g:2085:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID | lv_id_0_4= RULE_BACKCASE_ID )
                    int alt50=4;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt50=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt50=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt50=3;
                        }
                        break;
                    case RULE_BACKCASE_ID:
                        {
                        alt50=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 50, 0, input);

                        throw nvae;
                    }

                    switch (alt50) {
                        case 1 :
                            // InternalKdl.g:2086:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:2101:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKdl.g:2116:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                            // InternalKdl.g:2131:6: lv_id_0_4= RULE_BACKCASE_ID
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
                    // InternalKdl.g:2149:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKdl.g:2149:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKdl.g:2150:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:2154:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKdl.g:2155:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKdl.g:2155:5: (lv_unit_2_0= ruleUnit )
                    // InternalKdl.g:2156:6: lv_unit_2_0= ruleUnit
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
    // InternalKdl.g:2182:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:2182:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:2183:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKdl.g:2189:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2195:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:2196:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:2196:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:2197:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:2197:3: ()
            // InternalKdl.g:2198:4: 
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

            // InternalKdl.g:2207:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_LOWERCASE_ID||(LA52_0>=RULE_UPPERCASE_ID && LA52_0<=RULE_CAMELCASE_ID)||LA52_0==RULE_BACKCASE_ID||LA52_0==33) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalKdl.g:2208:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:2208:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:2209:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_50);
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

            // InternalKdl.g:2226:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==46||LA53_0==101||LA53_0==115) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalKdl.g:2227:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:2227:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:2228:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:2234:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:2235:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:2235:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:2236:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_51);
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

            	    // InternalKdl.g:2254:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:2255:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:2255:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:2256:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
            	    break loop53;
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
    // InternalKdl.g:2278:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:2278:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:2279:2: iv_ruleACTOR= ruleACTOR EOF
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
    // InternalKdl.g:2285:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2291:2: ( (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' ) )
            // InternalKdl.g:2292:2: (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' )
            {
            // InternalKdl.g:2292:2: (kw= 'object' | kw= 'event' | kw= 'observation' | kw= 'value' | kw= 'process' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'list' | kw= 'table' | kw= 'map' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'enum' | kw= 'range' | kw= 'void' | kw= 'partition' | kw= 'resolve' )
            int alt54=21;
            switch ( input.LA(1) ) {
            case 64:
                {
                alt54=1;
                }
                break;
            case 65:
                {
                alt54=2;
                }
                break;
            case 66:
                {
                alt54=3;
                }
                break;
            case 67:
                {
                alt54=4;
                }
                break;
            case 68:
                {
                alt54=5;
                }
                break;
            case 69:
                {
                alt54=6;
                }
                break;
            case 70:
                {
                alt54=7;
                }
                break;
            case 71:
                {
                alt54=8;
                }
                break;
            case 72:
                {
                alt54=9;
                }
                break;
            case 73:
                {
                alt54=10;
                }
                break;
            case 74:
                {
                alt54=11;
                }
                break;
            case 75:
                {
                alt54=12;
                }
                break;
            case 76:
                {
                alt54=13;
                }
                break;
            case 77:
                {
                alt54=14;
                }
                break;
            case 78:
                {
                alt54=15;
                }
                break;
            case 79:
                {
                alt54=16;
                }
                break;
            case 80:
                {
                alt54=17;
                }
                break;
            case 53:
                {
                alt54=18;
                }
                break;
            case 81:
                {
                alt54=19;
                }
                break;
            case 82:
                {
                alt54=20;
                }
                break;
            case 83:
                {
                alt54=21;
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
                    // InternalKdl.g:2293:3: kw= 'object'
                    {
                    kw=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2299:3: kw= 'event'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getEventKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2305:3: kw= 'observation'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObservationKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2311:3: kw= 'value'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2317:3: kw= 'process'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:2323:3: kw= 'number'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:2329:3: kw= 'concept'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:2335:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:2341:3: kw= 'text'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:2347:3: kw= 'list'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getListKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:2353:3: kw= 'table'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTableKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:2359:3: kw= 'map'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getMapKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:2365:3: kw= 'extent'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:2371:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalKdl.g:2377:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    // InternalKdl.g:2383:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    // InternalKdl.g:2389:3: kw= 'enum'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getEnumKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    // InternalKdl.g:2395:3: kw= 'range'
                    {
                    kw=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getRangeKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    // InternalKdl.g:2401:3: kw= 'void'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    // InternalKdl.g:2407:3: kw= 'partition'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getPartitionKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    // InternalKdl.g:2413:3: kw= 'resolve'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2422:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:2422:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:2423:2: iv_ruleTARGET= ruleTARGET EOF
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
    // InternalKdl.g:2429:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2435:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' ) )
            // InternalKdl.g:2436:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' )
            {
            // InternalKdl.g:2436:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' | kw= 'definitions' | kw= 'dependencies' )
            int alt55=5;
            switch ( input.LA(1) ) {
            case 84:
                {
                alt55=1;
                }
                break;
            case 85:
                {
                alt55=2;
                }
                break;
            case 86:
                {
                alt55=3;
                }
                break;
            case 87:
                {
                alt55=4;
                }
                break;
            case 88:
                {
                alt55=5;
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
                    // InternalKdl.g:2437:3: kw= 'models'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2443:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2449:3: kw= 'observers'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getObserversKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2455:3: kw= 'definitions'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getDefinitionsKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2461:3: kw= 'dependencies'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2470:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:2470:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:2471:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
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
    // InternalKdl.g:2477:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
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
            // InternalKdl.g:2483:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:2484:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:2484:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt60=10;
            alt60 = dfa60.predict(input);
            switch (alt60) {
                case 1 :
                    // InternalKdl.g:2485:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:2485:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==89) ) {
                        alt56=1;
                    }
                    else if ( (LA56_0==90) ) {
                        alt56=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 56, 0, input);

                        throw nvae;
                    }
                    switch (alt56) {
                        case 1 :
                            // InternalKdl.g:2486:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:2486:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:2487:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:2487:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:2488:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2501:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:2501:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:2502:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:2502:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:2503:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2517:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:2517:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:2518:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:2518:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:2519:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:2519:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:2520:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_52);
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

                    // InternalKdl.g:2537:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt57=3;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==91) ) {
                        alt57=1;
                    }
                    else if ( (LA57_0==92) ) {
                        alt57=2;
                    }
                    switch (alt57) {
                        case 1 :
                            // InternalKdl.g:2538:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2538:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:2539:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:2539:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:2540:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,91,FOLLOW_37); if (state.failed) return current;
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
                            // InternalKdl.g:2553:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,92,FOLLOW_37); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:2558:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:2559:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:2565:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:2566:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:2570:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:2571:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
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

                    // InternalKdl.g:2588:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt58=3;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==91) ) {
                        alt58=1;
                    }
                    else if ( (LA58_0==92) ) {
                        alt58=2;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalKdl.g:2589:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:2589:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:2590:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:2590:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:2591:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2604:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2611:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2611:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:2612:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:2612:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:2613:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:2631:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:2631:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:2632:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,93,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:2636:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:2637:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:2637:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:2638:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:2657:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2657:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:2658:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:2658:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:2659:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:2676:3: ( (lv_map_13_0= ruleMap ) )
                    {
                    // InternalKdl.g:2676:3: ( (lv_map_13_0= ruleMap ) )
                    // InternalKdl.g:2677:4: (lv_map_13_0= ruleMap )
                    {
                    // InternalKdl.g:2677:4: (lv_map_13_0= ruleMap )
                    // InternalKdl.g:2678:5: lv_map_13_0= ruleMap
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
                    // InternalKdl.g:2696:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:2696:3: (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:2697:4: otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,33,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:2701:4: ( (lv_toResolve_15_0= RULE_STRING ) )
                    // InternalKdl.g:2702:5: (lv_toResolve_15_0= RULE_STRING )
                    {
                    // InternalKdl.g:2702:5: (lv_toResolve_15_0= RULE_STRING )
                    // InternalKdl.g:2703:6: lv_toResolve_15_0= RULE_STRING
                    {
                    lv_toResolve_15_0=(Token)match(input,RULE_STRING,FOLLOW_55); if (state.failed) return current;
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

                    // InternalKdl.g:2719:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )*
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==31) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // InternalKdl.g:2720:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    {
                    	    // InternalKdl.g:2720:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:2721:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,31,FOLLOW_6); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:2727:5: ( (lv_toResolve_17_0= RULE_STRING ) )
                    	    // InternalKdl.g:2728:6: (lv_toResolve_17_0= RULE_STRING )
                    	    {
                    	    // InternalKdl.g:2728:6: (lv_toResolve_17_0= RULE_STRING )
                    	    // InternalKdl.g:2729:7: lv_toResolve_17_0= RULE_STRING
                    	    {
                    	    lv_toResolve_17_0=(Token)match(input,RULE_STRING,FOLLOW_55); if (state.failed) return current;
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
                    	    break loop59;
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
                    // InternalKdl.g:2752:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2752:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:2753:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2753:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:2754:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:2754:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:2755:6: lv_op_19_0= ruleREL_OPERATOR
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

                    // InternalKdl.g:2772:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:2773:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:2773:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:2774:6: lv_expression_20_0= ruleNumber
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
                    // InternalKdl.g:2793:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:2793:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:2794:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:2794:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:2795:5: lv_nodata_21_0= 'unknown'
                    {
                    lv_nodata_21_0=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:2808:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:2808:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:2809:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:2809:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:2810:5: lv_star_22_0= '*'
                    {
                    lv_star_22_0=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2826:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:2826:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:2827:2: iv_ruleList= ruleList EOF
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
    // InternalKdl.g:2833:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2839:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:2840:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:2840:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:2841:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:2841:3: ()
            // InternalKdl.g:2842:4: 
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
            // InternalKdl.g:2855:3: ( (lv_contents_2_0= ruleValue ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( ((LA61_0>=RULE_STRING && LA61_0<=RULE_LOWERCASE_ID)||(LA61_0>=RULE_INT && LA61_0<=RULE_CAMELCASE_ID)||(LA61_0>=RULE_ID && LA61_0<=RULE_EXPR)||LA61_0==31||LA61_0==33||LA61_0==43||LA61_0==49||(LA61_0>=89 && LA61_0<=90)||LA61_0==95||LA61_0==98||LA61_0==112) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // InternalKdl.g:2856:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:2856:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:2857:5: lv_contents_2_0= ruleValue
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
            	    break loop61;
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
    // InternalKdl.g:2882:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:2882:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:2883:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKdl.g:2889:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
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
            // InternalKdl.g:2895:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:2896:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:2896:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt63=4;
            alt63 = dfa63.predict(input);
            switch (alt63) {
                case 1 :
                    // InternalKdl.g:2897:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2897:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:2898:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:2898:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:2899:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:2917:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:2917:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:2918:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:2918:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:2919:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:2919:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:2920:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_37);
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
                    // InternalKdl.g:2941:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:2942:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:2942:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:2943:6: lv_to_3_0= ruleNumber
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
                    // InternalKdl.g:2962:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:2962:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:2963:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:2963:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:2964:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:2981:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:2981:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:2982:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:2982:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:2983:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:2983:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==89) ) {
                        alt62=1;
                    }
                    else if ( (LA62_0==90) ) {
                        alt62=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 62, 0, input);

                        throw nvae;
                    }
                    switch (alt62) {
                        case 1 :
                            // InternalKdl.g:2984:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:2995:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3012:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:3012:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:3013:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
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
    // InternalKdl.g:3019:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
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
            // InternalKdl.g:3025:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:3026:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:3026:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt66=6;
            alt66 = dfa66.predict(input);
            switch (alt66) {
                case 1 :
                    // InternalKdl.g:3027:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3027:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:3028:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3028:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:3029:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3029:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:3030:6: lv_from_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getFromNumberParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_37);
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

                    // InternalKdl.g:3047:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:3048:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:3054:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:3055:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3059:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:3060:6: lv_to_2_0= ruleNumber
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
                    // InternalKdl.g:3079:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3079:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:3080:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3080:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:3081:5: lv_number_3_0= ruleNumber
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
                    // InternalKdl.g:3099:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3099:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3100:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3100:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3101:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3118:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3118:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3119:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3119:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3120:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3120:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt64=2;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==89) ) {
                        alt64=1;
                    }
                    else if ( (LA64_0==90) ) {
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
                            // InternalKdl.g:3121:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3132:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3146:3: ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKdl.g:3146:3: ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKdl.g:3147:4: ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3147:4: ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3148:5: (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3148:5: (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID )
                    int alt65=3;
                    switch ( input.LA(1) ) {
                    case RULE_ID:
                        {
                        alt65=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt65=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt65=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 65, 0, input);

                        throw nvae;
                    }

                    switch (alt65) {
                        case 1 :
                            // InternalKdl.g:3149:6: lv_id_6_1= RULE_ID
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
                            // InternalKdl.g:3164:6: lv_id_6_2= RULE_LOWERCASE_ID
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
                            // InternalKdl.g:3179:6: lv_id_6_3= RULE_UPPERCASE_ID
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
                    // InternalKdl.g:3197:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:3197:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:3198:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:3198:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:3199:5: lv_comma_7_0= ','
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
    // InternalKdl.g:3215:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:3215:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:3216:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
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
    // InternalKdl.g:3222:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3228:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:3229:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:3229:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt68=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 43:
            case 112:
                {
                alt68=1;
                }
                break;
            case RULE_STRING:
                {
                alt68=2;
                }
                break;
            case 89:
            case 90:
                {
                alt68=3;
                }
                break;
            case RULE_ID:
                {
                alt68=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // InternalKdl.g:3230:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3230:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:3231:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3231:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:3232:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:3250:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3250:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:3251:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:3251:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:3252:5: lv_string_1_0= RULE_STRING
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
                    // InternalKdl.g:3269:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3269:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:3270:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:3270:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:3271:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:3271:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==89) ) {
                        alt67=1;
                    }
                    else if ( (LA67_0==90) ) {
                        alt67=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 67, 0, input);

                        throw nvae;
                    }
                    switch (alt67) {
                        case 1 :
                            // InternalKdl.g:3272:6: lv_boolean_2_1= 'true'
                            {
                            lv_boolean_2_1=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3283:6: lv_boolean_2_2= 'false'
                            {
                            lv_boolean_2_2=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3297:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:3297:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:3298:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:3298:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:3299:5: lv_id_3_0= RULE_ID
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
    // InternalKdl.g:3319:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:3319:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:3320:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKdl.g:3326:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
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
            // InternalKdl.g:3332:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:3333:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:3333:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:3334:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:3334:3: ()
            // InternalKdl.g:3335:4: 
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

            otherlv_1=(Token)match(input,49,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMetadataAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3348:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==RULE_LOWERCASE_ID) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // InternalKdl.g:3349:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:3349:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:3350:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:3350:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:3351:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:3351:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt69=2;
            	    int LA69_0 = input.LA(1);

            	    if ( (LA69_0==RULE_LOWERCASE_ID) ) {
            	        int LA69_1 = input.LA(2);

            	        if ( (LA69_1==RULE_STRING||LA69_1==RULE_INT||LA69_1==RULE_ID||LA69_1==33||LA69_1==43||LA69_1==49||(LA69_1>=89 && LA69_1<=90)||LA69_1==112) ) {
            	            alt69=1;
            	        }
            	        else if ( (LA69_1==96||LA69_1==102) ) {
            	            alt69=2;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 69, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 69, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt69) {
            	        case 1 :
            	            // InternalKdl.g:3352:7: lv_ids_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_ids_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_57); if (state.failed) return current;
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
            	            // InternalKdl.g:3367:7: lv_ids_2_2= rulePropertyId
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getIdsPropertyIdParserRuleCall_2_0_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_57);
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

            	    // InternalKdl.g:3385:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:3386:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:3386:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:3387:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:3387:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt70=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 43:
            	    case 89:
            	    case 90:
            	    case 112:
            	        {
            	        alt70=1;
            	        }
            	        break;
            	    case 49:
            	        {
            	        alt70=2;
            	        }
            	        break;
            	    case 33:
            	        {
            	        alt70=3;
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
            	            // InternalKdl.g:3388:7: lv_values_3_1= ruleLiteralOrID
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesLiteralOrIDParserRuleCall_2_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_56);
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
            	            // InternalKdl.g:3404:7: lv_values_3_2= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesMetadataParserRuleCall_2_1_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_56);
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
            	            // InternalKdl.g:3420:7: lv_values_3_3= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesListParserRuleCall_2_1_0_2());
            	              						
            	            }
            	            pushFollow(FOLLOW_56);
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
            	    break loop71;
                }
            } while (true);

            otherlv_4=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3447:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:3447:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:3448:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKdl.g:3454:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
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
            // InternalKdl.g:3460:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:3461:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:3461:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt74=2;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INT:
            case RULE_UPPERCASE_ID:
            case RULE_CAMELCASE_ID:
            case RULE_ID:
            case RULE_EXPR:
            case 31:
            case 33:
            case 43:
            case 49:
            case 89:
            case 90:
            case 95:
            case 98:
            case 112:
                {
                alt74=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                int LA74_2 = input.LA(2);

                if ( (LA74_2==EOF||LA74_2==31||(LA74_2>=33 && LA74_2<=34)||(LA74_2>=96 && LA74_2<=97)||(LA74_2>=101 && LA74_2<=102)||(LA74_2>=105 && LA74_2<=106)) ) {
                    alt74=1;
                }
                else if ( ((LA74_2>=103 && LA74_2<=104)) ) {
                    alt74=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                int LA74_3 = input.LA(2);

                if ( (LA74_3==EOF||LA74_3==31||LA74_3==34||LA74_3==97||(LA74_3>=101 && LA74_3<=102)||LA74_3==105) ) {
                    alt74=1;
                }
                else if ( ((LA74_3>=103 && LA74_3<=104)) ) {
                    alt74=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 3, input);

                    throw nvae;
                }
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
                    // InternalKdl.g:3462:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:3462:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:3463:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:3463:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:3464:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:3464:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:3465:6: lv_values_0_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
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

                    // InternalKdl.g:3482:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( (LA72_0==31) ) {
                            alt72=1;
                        }


                        switch (alt72) {
                    	case 1 :
                    	    // InternalKdl.g:3483:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,31,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:3487:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:3488:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:3488:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:3489:7: lv_values_2_0= ruleValue
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getValuesValueParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_43);
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
                    	    break loop72;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:3509:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:3509:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:3510:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:3510:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:3511:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:3511:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:3512:6: lv_pairs_3_0= ruleKeyValuePair
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
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

                    // InternalKdl.g:3529:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==31) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // InternalKdl.g:3530:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:3530:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:3531:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,31,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3537:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:3538:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:3538:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:3539:7: lv_pairs_5_0= ruleKeyValuePair
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_43);
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
                    	    break loop73;
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
    // InternalKdl.g:3562:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:3562:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:3563:2: iv_ruleValue= ruleValue EOF
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
    // InternalKdl.g:3569:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) ;
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
            // InternalKdl.g:3575:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:3576:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:3576:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )
            int alt75=8;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // InternalKdl.g:3577:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:3577:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:3578:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:3578:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:3579:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
                    // InternalKdl.g:3597:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:3597:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:3598:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:3598:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:3599:5: lv_function_1_0= ruleFunction
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
                    // InternalKdl.g:3617:3: ( (lv_urn_2_0= ruleUrn ) )
                    {
                    // InternalKdl.g:3617:3: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:3618:4: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:3618:4: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:3619:5: lv_urn_2_0= ruleUrn
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
                    // InternalKdl.g:3637:3: ( (lv_list_3_0= ruleList ) )
                    {
                    // InternalKdl.g:3637:3: ( (lv_list_3_0= ruleList ) )
                    // InternalKdl.g:3638:4: (lv_list_3_0= ruleList )
                    {
                    // InternalKdl.g:3638:4: (lv_list_3_0= ruleList )
                    // InternalKdl.g:3639:5: lv_list_3_0= ruleList
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
                    // InternalKdl.g:3657:3: ( (lv_map_4_0= ruleMap ) )
                    {
                    // InternalKdl.g:3657:3: ( (lv_map_4_0= ruleMap ) )
                    // InternalKdl.g:3658:4: (lv_map_4_0= ruleMap )
                    {
                    // InternalKdl.g:3658:4: (lv_map_4_0= ruleMap )
                    // InternalKdl.g:3659:5: lv_map_4_0= ruleMap
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
                    // InternalKdl.g:3677:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:3677:3: ( (lv_expression_5_0= RULE_EXPR ) )
                    // InternalKdl.g:3678:4: (lv_expression_5_0= RULE_EXPR )
                    {
                    // InternalKdl.g:3678:4: (lv_expression_5_0= RULE_EXPR )
                    // InternalKdl.g:3679:5: lv_expression_5_0= RULE_EXPR
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
                    // InternalKdl.g:3696:3: ( (lv_table_6_0= ruleLookupTable ) )
                    {
                    // InternalKdl.g:3696:3: ( (lv_table_6_0= ruleLookupTable ) )
                    // InternalKdl.g:3697:4: (lv_table_6_0= ruleLookupTable )
                    {
                    // InternalKdl.g:3697:4: (lv_table_6_0= ruleLookupTable )
                    // InternalKdl.g:3698:5: lv_table_6_0= ruleLookupTable
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
                    // InternalKdl.g:3716:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:3716:3: ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:3717:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:3717:4: (lv_enumId_7_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:3718:5: lv_enumId_7_0= RULE_UPPERCASE_ID
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
    // InternalKdl.g:3738:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:3738:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:3739:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKdl.g:3745:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:3751:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:3752:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:3752:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:3753:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:3753:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:3754:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:3754:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt76=3;
            switch ( input.LA(1) ) {
            case 95:
                {
                alt76=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                switch ( input.LA(2) ) {
                case 102:
                    {
                    int LA76_5 = input.LA(3);

                    if ( (LA76_5==RULE_LOWERCASE_ID) ) {
                        int LA76_6 = input.LA(4);

                        if ( (LA76_6==96||LA76_6==102) ) {
                            alt76=1;
                        }
                        else if ( (LA76_6==EOF||(LA76_6>=RULE_STRING && LA76_6<=RULE_CAMELCASE_ID)||(LA76_6>=RULE_ID && LA76_6<=RULE_EXPR)||(LA76_6>=20 && LA76_6<=36)||(LA76_6>=38 && LA76_6<=41)||(LA76_6>=43 && LA76_6<=44)||(LA76_6>=49 && LA76_6<=50)||LA76_6==53||(LA76_6>=56 && LA76_6<=83)||(LA76_6>=89 && LA76_6<=90)||LA76_6==95||(LA76_6>=97 && LA76_6<=98)||LA76_6==105||LA76_6==112) ) {
                            alt76=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 76, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 76, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 96:
                    {
                    alt76=1;
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
                case 38:
                case 39:
                case 40:
                case 41:
                case 43:
                case 44:
                case 49:
                case 50:
                case 53:
                case 56:
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
                case 89:
                case 90:
                case 95:
                case 97:
                case 98:
                case 101:
                case 105:
                case 112:
                    {
                    alt76=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 76, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt76=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
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
                    // InternalKdl.g:3755:5: lv_name_0_1= ruleUrnId
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
                    // InternalKdl.g:3771:5: lv_name_0_2= RULE_STRING
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
                    // InternalKdl.g:3786:5: lv_name_0_3= ruleLocalFilePath
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
    // InternalKdl.g:3807:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:3807:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:3808:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKdl.g:3814:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:3820:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:3821:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:3821:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:3822:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:3822:3: (kw= 'urn:klab:' )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==95) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalKdl.g:3823:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,95,FOLLOW_3); if (state.failed) return current;
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
            pushFollow(FOLLOW_58);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,96,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_58);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,96,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_58);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,96,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_59);
            this_Path_7=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_Path_7);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalKdl.g:3884:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==96) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalKdl.g:3885:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
                    {
                    kw=(Token)match(input,96,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_60);
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

            // InternalKdl.g:3901:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==97) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKdl.g:3902:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,97,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:3919:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKdl.g:3919:44: (iv_ruleMap= ruleMap EOF )
            // InternalKdl.g:3920:2: iv_ruleMap= ruleMap EOF
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
    // InternalKdl.g:3926:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3932:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKdl.g:3933:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKdl.g:3933:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKdl.g:3934:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKdl.g:3934:3: ()
            // InternalKdl.g:3935:4: 
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

            otherlv_1=(Token)match(input,49,FOLLOW_61); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:3948:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==RULE_STRING||LA81_0==RULE_INT||LA81_0==33||LA81_0==43||LA81_0==46||LA81_0==49||(LA81_0>=89 && LA81_0<=90)||(LA81_0>=93 && LA81_0<=94)||LA81_0==104||(LA81_0>=107 && LA81_0<=112)) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalKdl.g:3949:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKdl.g:3949:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKdl.g:3950:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKdl.g:3950:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKdl.g:3951:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_62);
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

                    // InternalKdl.g:3968:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop80:
                    do {
                        int alt80=2;
                        int LA80_0 = input.LA(1);

                        if ( (LA80_0==31) ) {
                            alt80=1;
                        }


                        switch (alt80) {
                    	case 1 :
                    	    // InternalKdl.g:3969:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKdl.g:3969:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKdl.g:3970:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_63); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3977:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKdl.g:3978:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKdl.g:3978:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKdl.g:3979:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_62);
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
                    	    break loop80;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4006:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKdl.g:4006:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKdl.g:4007:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKdl.g:4013:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4019:2: ( ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKdl.g:4020:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKdl.g:4020:2: ( ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKdl.g:4021:3: ( (lv_classifier_0_0= ruleClassifierRHS ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKdl.g:4021:3: ( (lv_classifier_0_0= ruleClassifierRHS ) )
            // InternalKdl.g:4022:4: (lv_classifier_0_0= ruleClassifierRHS )
            {
            // InternalKdl.g:4022:4: (lv_classifier_0_0= ruleClassifierRHS )
            // InternalKdl.g:4023:5: lv_classifier_0_0= ruleClassifierRHS
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierRHSParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_58);
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

            otherlv_1=(Token)match(input,96,FOLLOW_40); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:4044:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKdl.g:4045:4: (lv_value_2_0= ruleValue )
            {
            // InternalKdl.g:4045:4: (lv_value_2_0= ruleValue )
            // InternalKdl.g:4046:5: lv_value_2_0= ruleValue
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
    // InternalKdl.g:4067:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKdl.g:4067:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKdl.g:4068:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKdl.g:4074:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4080:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKdl.g:4081:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKdl.g:4081:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKdl.g:4082:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKdl.g:4082:3: ()
            // InternalKdl.g:4083:4: 
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

            otherlv_1=(Token)match(input,98,FOLLOW_64); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:4096:3: ( (lv_table_2_0= ruleTable ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_STRING||LA82_0==RULE_INT||LA82_0==RULE_EXPR||LA82_0==43||LA82_0==46||(LA82_0>=89 && LA82_0<=90)||(LA82_0>=93 && LA82_0<=94)||LA82_0==97||LA82_0==104||(LA82_0>=107 && LA82_0<=112)) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalKdl.g:4097:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKdl.g:4097:4: (lv_table_2_0= ruleTable )
                    // InternalKdl.g:4098:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_65);
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

            otherlv_3=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4123:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKdl.g:4123:46: (iv_ruleTable= ruleTable EOF )
            // InternalKdl.g:4124:2: iv_ruleTable= ruleTable EOF
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
    // InternalKdl.g:4130:1: ruleTable returns [EObject current=null] : ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_rows_0_0 = null;

        EObject lv_rows_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4136:2: ( ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* ) )
            // InternalKdl.g:4137:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            {
            // InternalKdl.g:4137:2: ( ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )* )
            // InternalKdl.g:4138:3: ( (lv_rows_0_0= ruleTableRow ) ) (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            {
            // InternalKdl.g:4138:3: ( (lv_rows_0_0= ruleTableRow ) )
            // InternalKdl.g:4139:4: (lv_rows_0_0= ruleTableRow )
            {
            // InternalKdl.g:4139:4: (lv_rows_0_0= ruleTableRow )
            // InternalKdl.g:4140:5: lv_rows_0_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_43);
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

            // InternalKdl.g:4157:3: (otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) ) )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==31) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalKdl.g:4158:4: otherlv_1= ',' ( (lv_rows_2_0= ruleTableRow ) )
            	    {
            	    otherlv_1=(Token)match(input,31,FOLLOW_66); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4162:4: ( (lv_rows_2_0= ruleTableRow ) )
            	    // InternalKdl.g:4163:5: (lv_rows_2_0= ruleTableRow )
            	    {
            	    // InternalKdl.g:4163:5: (lv_rows_2_0= ruleTableRow )
            	    // InternalKdl.g:4164:6: lv_rows_2_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_43);
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
            	    break loop83;
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
    // InternalKdl.g:4186:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKdl.g:4186:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKdl.g:4187:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKdl.g:4193:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4199:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKdl.g:4200:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKdl.g:4200:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKdl.g:4201:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKdl.g:4201:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKdl.g:4202:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKdl.g:4202:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKdl.g:4203:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_67);
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

            // InternalKdl.g:4220:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==100) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalKdl.g:4221:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,100,FOLLOW_66); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4225:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKdl.g:4226:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKdl.g:4226:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKdl.g:4227:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_67);
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
    // $ANTLR end "ruleTableRow"


    // $ANTLR start "entryRuleTableClassifier"
    // InternalKdl.g:4249:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKdl.g:4249:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKdl.g:4250:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKdl.g:4256:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) ;
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
            // InternalKdl.g:4262:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) ) )
            // InternalKdl.g:4263:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            {
            // InternalKdl.g:4263:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )
            int alt88=10;
            alt88 = dfa88.predict(input);
            switch (alt88) {
                case 1 :
                    // InternalKdl.g:4264:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:4264:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==89) ) {
                        alt85=1;
                    }
                    else if ( (LA85_0==90) ) {
                        alt85=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 85, 0, input);

                        throw nvae;
                    }
                    switch (alt85) {
                        case 1 :
                            // InternalKdl.g:4265:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:4265:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:4266:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:4266:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:4267:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:4280:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:4280:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:4281:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:4281:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:4282:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4296:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:4296:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:4297:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:4297:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:4298:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:4298:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:4299:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_52);
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

                    // InternalKdl.g:4316:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt86=3;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==91) ) {
                        alt86=1;
                    }
                    else if ( (LA86_0==92) ) {
                        alt86=2;
                    }
                    switch (alt86) {
                        case 1 :
                            // InternalKdl.g:4317:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:4317:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:4318:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:4318:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:4319:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,91,FOLLOW_37); if (state.failed) return current;
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
                            // InternalKdl.g:4332:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,92,FOLLOW_37); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4337:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:4338:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getTableClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:4344:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:4345:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:4349:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:4350:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
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

                    // InternalKdl.g:4367:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt87=3;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==91) ) {
                        alt87=1;
                    }
                    else if ( (LA87_0==92) ) {
                        alt87=2;
                    }
                    switch (alt87) {
                        case 1 :
                            // InternalKdl.g:4368:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:4368:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:4369:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:4369:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:4370:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:4383:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4390:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4390:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:4391:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:4391:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:4392:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:4410:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:4410:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:4411:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,93,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:4415:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:4416:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:4416:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:4417:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:4436:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:4436:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:4437:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:4437:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:4438:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:4455:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:4455:3: ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) )
                    // InternalKdl.g:4456:4: ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4456:4: ( (lv_op_13_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:4457:5: (lv_op_13_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:4457:5: (lv_op_13_0= ruleREL_OPERATOR )
                    // InternalKdl.g:4458:6: lv_op_13_0= ruleREL_OPERATOR
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

                    // InternalKdl.g:4475:4: ( (lv_expression_14_0= ruleNumber ) )
                    // InternalKdl.g:4476:5: (lv_expression_14_0= ruleNumber )
                    {
                    // InternalKdl.g:4476:5: (lv_expression_14_0= ruleNumber )
                    // InternalKdl.g:4477:6: lv_expression_14_0= ruleNumber
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
                    // InternalKdl.g:4496:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    {
                    // InternalKdl.g:4496:3: ( (lv_expr_15_0= RULE_EXPR ) )
                    // InternalKdl.g:4497:4: (lv_expr_15_0= RULE_EXPR )
                    {
                    // InternalKdl.g:4497:4: (lv_expr_15_0= RULE_EXPR )
                    // InternalKdl.g:4498:5: lv_expr_15_0= RULE_EXPR
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
                    // InternalKdl.g:4515:3: ( (lv_nodata_16_0= 'unknown' ) )
                    {
                    // InternalKdl.g:4515:3: ( (lv_nodata_16_0= 'unknown' ) )
                    // InternalKdl.g:4516:4: (lv_nodata_16_0= 'unknown' )
                    {
                    // InternalKdl.g:4516:4: (lv_nodata_16_0= 'unknown' )
                    // InternalKdl.g:4517:5: lv_nodata_16_0= 'unknown'
                    {
                    lv_nodata_16_0=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4530:3: ( (lv_star_17_0= '*' ) )
                    {
                    // InternalKdl.g:4530:3: ( (lv_star_17_0= '*' ) )
                    // InternalKdl.g:4531:4: (lv_star_17_0= '*' )
                    {
                    // InternalKdl.g:4531:4: (lv_star_17_0= '*' )
                    // InternalKdl.g:4532:5: lv_star_17_0= '*'
                    {
                    lv_star_17_0=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4545:3: ( (lv_anything_18_0= '#' ) )
                    {
                    // InternalKdl.g:4545:3: ( (lv_anything_18_0= '#' ) )
                    // InternalKdl.g:4546:4: (lv_anything_18_0= '#' )
                    {
                    // InternalKdl.g:4546:4: (lv_anything_18_0= '#' )
                    // InternalKdl.g:4547:5: lv_anything_18_0= '#'
                    {
                    lv_anything_18_0=(Token)match(input,97,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4563:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:4563:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:4564:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
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
    // InternalKdl.g:4570:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4576:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4577:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4577:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4578:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4578:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt89=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt89=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt89=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                alt89=3;
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
                    // InternalKdl.g:4579:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_CAMELCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4587:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:4595:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_2, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4603:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==101) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalKdl.g:4604:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,101,FOLLOW_69); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4609:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
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
            	            // InternalKdl.g:4610:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
            	            {
            	            this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_68); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_CAMELCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CAMELCASE_ID_4, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4618:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_5, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:4626:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
            	            {
            	            this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_68); if (state.failed) return current;
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
            	    break loop91;
                }
            } while (true);

            // InternalKdl.g:4635:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==102) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // InternalKdl.g:4636:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getFullStopKeyword_2_0());
                      			
                    }
                    this_LOWERCASE_ID_8=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_8, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4649:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==97) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalKdl.g:4650:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,97,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:4667:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4667:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4668:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKdl.g:4674:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_1=null;
        Token lv_name_0_2=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4680:2: ( ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4681:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4681:2: ( ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4682:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4682:3: ( ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:4683:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:4683:4: ( (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:4684:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            {
            // InternalKdl.g:4684:5: (lv_name_0_1= RULE_LOWERCASE_ID | lv_name_0_2= RULE_LOWERCASE_DASHID )
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==RULE_LOWERCASE_ID) ) {
                alt94=1;
            }
            else if ( (LA94_0==RULE_LOWERCASE_DASHID) ) {
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
                    // InternalKdl.g:4685:6: lv_name_0_1= RULE_LOWERCASE_ID
                    {
                    lv_name_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
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
                    // InternalKdl.g:4700:6: lv_name_0_2= RULE_LOWERCASE_DASHID
                    {
                    lv_name_0_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_70); if (state.failed) return current;
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

            // InternalKdl.g:4717:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==103) ) {
                alt95=1;
            }
            else if ( (LA95_0==104) ) {
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
                    // InternalKdl.g:4718:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4718:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4719:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4719:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4720:6: lv_interactive_1_0= '=?'
                    {
                    lv_interactive_1_0=(Token)match(input,103,FOLLOW_40); if (state.failed) return current;
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
                    // InternalKdl.g:4733:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,104,FOLLOW_40); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4738:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4739:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4739:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4740:5: lv_value_3_0= ruleValue
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
    // InternalKdl.g:4761:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4761:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4762:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalKdl.g:4768:1: ruleFunction returns [EObject current=null] : ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) ) ;
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
            // InternalKdl.g:4774:2: ( ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) ) )
            // InternalKdl.g:4775:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) )
            {
            // InternalKdl.g:4775:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' ) )
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( ((LA101_0>=RULE_STRING && LA101_0<=RULE_LOWERCASE_ID)||(LA101_0>=RULE_INT && LA101_0<=RULE_LOWERCASE_DASHID)||LA101_0==RULE_CAMELCASE_ID||LA101_0==RULE_EXPR||LA101_0==43||(LA101_0>=89 && LA101_0<=90)||LA101_0==95||LA101_0==112) ) {
                alt101=1;
            }
            else if ( (LA101_0==33) ) {
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
                    // InternalKdl.g:4776:3: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4776:3: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4777:4: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )? ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) ) (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4777:4: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) | ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' ) )?
                    int alt96=3;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==RULE_LOWERCASE_ID) ) {
                        int LA96_1 = input.LA(2);

                        if ( (LA96_1==105) ) {
                            int LA96_3 = input.LA(3);

                            if ( (LA96_3==RULE_LOWERCASE_ID) ) {
                                int LA96_5 = input.LA(4);

                                if ( (synpred195_InternalKdl()) ) {
                                    alt96=1;
                                }
                            }
                            else if ( (LA96_3==RULE_STRING||(LA96_3>=RULE_INT && LA96_3<=RULE_LOWERCASE_DASHID)||LA96_3==RULE_CAMELCASE_ID||LA96_3==RULE_EXPR||LA96_3==43||(LA96_3>=89 && LA96_3<=90)||LA96_3==95||LA96_3==112) ) {
                                alt96=1;
                            }
                        }
                        else if ( (LA96_1==106) ) {
                            alt96=2;
                        }
                    }
                    switch (alt96) {
                        case 1 :
                            // InternalKdl.g:4778:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
                            {
                            // InternalKdl.g:4778:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
                            // InternalKdl.g:4779:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4779:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4780:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4780:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4781:8: lv_mediated_0_0= RULE_LOWERCASE_ID
                            {
                            lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_71); if (state.failed) return current;
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

                            otherlv_1=(Token)match(input,105,FOLLOW_72); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_0_0_1());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4803:5: ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' )
                            {
                            // InternalKdl.g:4803:5: ( ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-' )
                            // InternalKdl.g:4804:6: ( (lv_variable_2_0= RULE_LOWERCASE_ID ) ) otherlv_3= '<-'
                            {
                            // InternalKdl.g:4804:6: ( (lv_variable_2_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4805:7: (lv_variable_2_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4805:7: (lv_variable_2_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4806:8: lv_variable_2_0= RULE_LOWERCASE_ID
                            {
                            lv_variable_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
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

                            otherlv_3=(Token)match(input,106,FOLLOW_72); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLessThanSignHyphenMinusKeyword_0_0_1_1());
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:4828:4: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) )
                    int alt98=4;
                    alt98 = dfa98.predict(input);
                    switch (alt98) {
                        case 1 :
                            // InternalKdl.g:4829:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
                            {
                            // InternalKdl.g:4829:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
                            // InternalKdl.g:4830:6: ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')'
                            {
                            // InternalKdl.g:4830:6: ( (lv_name_4_0= rulePathName ) )
                            // InternalKdl.g:4831:7: (lv_name_4_0= rulePathName )
                            {
                            // InternalKdl.g:4831:7: (lv_name_4_0= rulePathName )
                            // InternalKdl.g:4832:8: lv_name_4_0= rulePathName
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_54);
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
                            // InternalKdl.g:4853:6: ( (lv_parameters_6_0= ruleParameterList ) )?
                            int alt97=2;
                            int LA97_0 = input.LA(1);

                            if ( ((LA97_0>=RULE_STRING && LA97_0<=RULE_LOWERCASE_ID)||(LA97_0>=RULE_INT && LA97_0<=RULE_CAMELCASE_ID)||(LA97_0>=RULE_ID && LA97_0<=RULE_EXPR)||LA97_0==31||LA97_0==33||LA97_0==43||LA97_0==49||(LA97_0>=89 && LA97_0<=90)||LA97_0==95||LA97_0==98||LA97_0==112) ) {
                                alt97=1;
                            }
                            switch (alt97) {
                                case 1 :
                                    // InternalKdl.g:4854:7: (lv_parameters_6_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4854:7: (lv_parameters_6_0= ruleParameterList )
                                    // InternalKdl.g:4855:8: lv_parameters_6_0= ruleParameterList
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

                            otherlv_7=(Token)match(input,34,FOLLOW_74); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4878:5: ( (lv_urn_8_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4878:5: ( (lv_urn_8_0= ruleUrn ) )
                            // InternalKdl.g:4879:6: (lv_urn_8_0= ruleUrn )
                            {
                            // InternalKdl.g:4879:6: (lv_urn_8_0= ruleUrn )
                            // InternalKdl.g:4880:7: lv_urn_8_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_74);
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
                            // InternalKdl.g:4898:5: ( (lv_value_9_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4898:5: ( (lv_value_9_0= ruleLiteral ) )
                            // InternalKdl.g:4899:6: (lv_value_9_0= ruleLiteral )
                            {
                            // InternalKdl.g:4899:6: (lv_value_9_0= ruleLiteral )
                            // InternalKdl.g:4900:7: lv_value_9_0= ruleLiteral
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getValueLiteralParserRuleCall_0_1_2_0());
                              						
                            }
                            pushFollow(FOLLOW_74);
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
                            // InternalKdl.g:4918:5: ( (lv_expression_10_0= RULE_EXPR ) )
                            {
                            // InternalKdl.g:4918:5: ( (lv_expression_10_0= RULE_EXPR ) )
                            // InternalKdl.g:4919:6: (lv_expression_10_0= RULE_EXPR )
                            {
                            // InternalKdl.g:4919:6: (lv_expression_10_0= RULE_EXPR )
                            // InternalKdl.g:4920:7: lv_expression_10_0= RULE_EXPR
                            {
                            lv_expression_10_0=(Token)match(input,RULE_EXPR,FOLLOW_74); if (state.failed) return current;
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

                    // InternalKdl.g:4937:4: (otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) ) )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==105) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // InternalKdl.g:4938:5: otherlv_11= '>>' ( (lv_target_12_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_11=(Token)match(input,105,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4942:5: ( (lv_target_12_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4943:6: (lv_target_12_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4943:6: (lv_target_12_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4944:7: lv_target_12_0= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4963:3: (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' )
                    {
                    // InternalKdl.g:4963:3: (otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')' )
                    // InternalKdl.g:4964:4: otherlv_13= '(' ( (lv_chain_14_0= ruleFunction ) ) (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )* otherlv_17= ')'
                    {
                    otherlv_13=(Token)match(input,33,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:4968:4: ( (lv_chain_14_0= ruleFunction ) )
                    // InternalKdl.g:4969:5: (lv_chain_14_0= ruleFunction )
                    {
                    // InternalKdl.g:4969:5: (lv_chain_14_0= ruleFunction )
                    // InternalKdl.g:4970:6: lv_chain_14_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
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

                    // InternalKdl.g:4987:4: (otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) ) )*
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==31) ) {
                            alt100=1;
                        }


                        switch (alt100) {
                    	case 1 :
                    	    // InternalKdl.g:4988:5: otherlv_15= ',' ( (lv_chain_16_0= ruleFunction ) )
                    	    {
                    	    otherlv_15=(Token)match(input,31,FOLLOW_10); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_15, grammarAccess.getFunctionAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:4992:5: ( (lv_chain_16_0= ruleFunction ) )
                    	    // InternalKdl.g:4993:6: (lv_chain_16_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:4993:6: (lv_chain_16_0= ruleFunction )
                    	    // InternalKdl.g:4994:7: lv_chain_16_0= ruleFunction
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_55);
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
                    	    break loop100;
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
    // InternalKdl.g:5021:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:5021:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:5022:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKdl.g:5028:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKdl.g:5034:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:5035:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:5035:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt102=6;
            switch ( input.LA(1) ) {
            case 107:
                {
                alt102=1;
                }
                break;
            case 108:
                {
                alt102=2;
                }
                break;
            case 104:
                {
                alt102=3;
                }
                break;
            case 109:
                {
                alt102=4;
                }
                break;
            case 110:
                {
                alt102=5;
                }
                break;
            case 111:
                {
                alt102=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }

            switch (alt102) {
                case 1 :
                    // InternalKdl.g:5036:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:5036:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:5037:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:5037:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:5038:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,107,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5051:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:5051:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:5052:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:5052:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:5053:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,108,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5066:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:5066:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:5067:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:5067:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:5068:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,104,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5081:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:5081:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:5082:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:5082:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:5083:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,109,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5096:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:5096:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:5097:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:5097:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:5098:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,110,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5111:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:5111:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:5112:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:5112:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:5113:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,111,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:5129:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:5129:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:5130:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKdl.g:5136:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKdl.g:5142:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:5143:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:5143:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:5144:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:5144:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt103=3;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==43) ) {
                alt103=1;
            }
            else if ( (LA103_0==112) ) {
                alt103=2;
            }
            switch (alt103) {
                case 1 :
                    // InternalKdl.g:5145:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,43,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5150:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:5150:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:5151:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:5151:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:5152:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,112,FOLLOW_7); if (state.failed) return current;
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

            // InternalKdl.g:5165:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:5166:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:5170:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:5171:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
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

            // InternalKdl.g:5187:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==102) && (synpred212_InternalKdl())) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalKdl.g:5188:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5201:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:5202:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5202:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:5203:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:5203:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:5204:7: lv_decimal_3_0= '.'
                    {
                    lv_decimal_3_0=(Token)match(input,102,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:5216:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:5217:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:5217:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:5218:7: lv_decimalPart_4_0= RULE_INT
                    {
                    lv_decimalPart_4_0=(Token)match(input,RULE_INT,FOLLOW_76); if (state.failed) return current;
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

            // InternalKdl.g:5236:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==113) && (synpred216_InternalKdl())) {
                alt107=1;
            }
            else if ( (LA107_0==114) && (synpred216_InternalKdl())) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // InternalKdl.g:5237:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5263:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:5264:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5264:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:5265:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:5265:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:5266:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:5266:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==113) ) {
                        alt105=1;
                    }
                    else if ( (LA105_0==114) ) {
                        alt105=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 105, 0, input);

                        throw nvae;
                    }
                    switch (alt105) {
                        case 1 :
                            // InternalKdl.g:5267:8: lv_exponential_5_1= 'e'
                            {
                            lv_exponential_5_1=(Token)match(input,113,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKdl.g:5278:8: lv_exponential_5_2= 'E'
                            {
                            lv_exponential_5_2=(Token)match(input,114,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKdl.g:5291:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt106=3;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==43) ) {
                        alt106=1;
                    }
                    else if ( (LA106_0==112) ) {
                        alt106=2;
                    }
                    switch (alt106) {
                        case 1 :
                            // InternalKdl.g:5292:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,43,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5297:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:5297:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:5298:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:5298:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:5299:8: lv_expNegative_7_0= '-'
                            {
                            lv_expNegative_7_0=(Token)match(input,112,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:5312:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:5313:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:5313:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:5314:7: lv_exp_8_0= RULE_INT
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
    // InternalKdl.g:5336:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:5336:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:5337:2: iv_rulePathName= rulePathName EOF
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
    // InternalKdl.g:5343:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5349:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5350:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5350:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5351:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_77); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5358:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( (LA108_0==102) ) {
                    int LA108_2 = input.LA(2);

                    if ( (LA108_2==RULE_LOWERCASE_ID) ) {
                        alt108=1;
                    }


                }


                switch (alt108) {
            	case 1 :
            	    // InternalKdl.g:5359:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_77); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop108;
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
    // InternalKdl.g:5376:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:5376:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:5377:2: iv_rulePath= rulePath EOF
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
    // InternalKdl.g:5383:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:5389:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5390:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5390:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5391:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_78); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5398:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( ((LA110_0>=101 && LA110_0<=102)) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // InternalKdl.g:5399:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:5399:4: (kw= '.' | kw= '/' )
            	    int alt109=2;
            	    int LA109_0 = input.LA(1);

            	    if ( (LA109_0==102) ) {
            	        alt109=1;
            	    }
            	    else if ( (LA109_0==101) ) {
            	        alt109=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 109, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt109) {
            	        case 1 :
            	            // InternalKdl.g:5400:5: kw= '.'
            	            {
            	            kw=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:5406:5: kw= '/'
            	            {
            	            kw=(Token)match(input,101,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_78); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_3, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
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
    // $ANTLR end "rulePath"


    // $ANTLR start "entryRuleJavaClass"
    // InternalKdl.g:5424:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5424:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5425:2: iv_ruleJavaClass= ruleJavaClass EOF
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
    // InternalKdl.g:5431:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5437:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5438:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5438:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5439:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getJavaClassAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_79);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,102,FOLLOW_80); if (state.failed) return current;
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
    // InternalKdl.g:5465:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5465:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5466:2: iv_rulePropertyId= rulePropertyId EOF
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
    // InternalKdl.g:5472:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5478:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5479:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5479:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5480:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPropertyIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_58);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,96,FOLLOW_81); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getPropertyIdAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:5495:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==RULE_LOWERCASE_ID) ) {
                alt111=1;
            }
            else if ( (LA111_0==RULE_LOWERCASE_DASHID) ) {
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
                    // InternalKdl.g:5496:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5504:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
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
    // InternalKdl.g:5516:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5516:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5517:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKdl.g:5523:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKdl.g:5529:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:5530:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:5530:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:5531:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_82); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5538:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==102) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // InternalKdl.g:5539:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,102,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_82); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKdl.g:5551:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==102) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // InternalKdl.g:5552:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,102,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_83); if (state.failed) return current;
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

            // InternalKdl.g:5566:3: (kw= '-' )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==112) ) {
                int LA114_1 = input.LA(2);

                if ( (synpred226_InternalKdl()) ) {
                    alt114=1;
                }
            }
            switch (alt114) {
                case 1 :
                    // InternalKdl.g:5567:4: kw= '-'
                    {
                    kw=(Token)match(input,112,FOLLOW_84); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:5573:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt115=3;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==RULE_LOWERCASE_ID) ) {
                int LA115_1 = input.LA(2);

                if ( (synpred227_InternalKdl()) ) {
                    alt115=1;
                }
            }
            else if ( (LA115_0==RULE_UPPERCASE_ID) ) {
                int LA115_2 = input.LA(2);

                if ( (synpred228_InternalKdl()) ) {
                    alt115=2;
                }
            }
            switch (alt115) {
                case 1 :
                    // InternalKdl.g:5574:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5582:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKdl.g:5594:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5600:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:5601:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:5601:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt116=3;
            switch ( input.LA(1) ) {
            case 101:
                {
                alt116=1;
                }
                break;
            case 115:
                {
                alt116=2;
                }
                break;
            case 46:
                {
                alt116=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // InternalKdl.g:5602:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:5602:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:5603:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,101,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:5610:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:5610:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:5611:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,115,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:5618:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:5618:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:5619:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
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
        int alt117=2;
        alt117 = dfa117.predict(input);
        switch (alt117) {
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
        int cnt118=0;
        loop118:
        do {
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==21) && ((true))) {
                alt118=1;
            }


            switch (alt118) {
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
        	    pushFollow(FOLLOW_85);
        	    lv_variables_4_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt118 >= 1 ) break loop118;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(118, input);
                    throw eee;
            }
            cnt118++;
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
        int cnt119=0;
        loop119:
        do {
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==22) && ((true))) {
                alt119=1;
            }


            switch (alt119) {
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
        	    pushFollow(FOLLOW_86);
        	    lv_constants_6_0=ruleParameter();

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
        int cnt120=0;
        loop120:
        do {
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==23) && ((true))) {
                alt120=1;
            }


            switch (alt120) {
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
        	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_87); if (state.failed) return ;

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
        int alt121=2;
        int LA121_0 = input.LA(1);

        if ( (LA121_0==RULE_LOWERCASE_ID) ) {
            alt121=1;
        }
        else if ( (LA121_0==RULE_STRING) ) {
            alt121=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 121, 0, input);

            throw nvae;
        }
        switch (alt121) {
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
        pushFollow(FOLLOW_43);
        lv_scale_22_0=ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:504:9: (otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) ) )*
        loop122:
        do {
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==31) ) {
                alt122=1;
            }


            switch (alt122) {
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
        	    pushFollow(FOLLOW_43);
        	    lv_scale_24_0=ruleFunction();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop122;
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

    // $ANTLR start synpred60_InternalKdl
    public final void synpred60_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_46=null;
        EObject lv_default_47_0 = null;


        // InternalKdl.g:1506:5: ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) )
        // InternalKdl.g:1506:5: ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) )
        {
        // InternalKdl.g:1506:5: ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) )
        // InternalKdl.g:1507:6: {...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred60_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0)");
        }
        // InternalKdl.g:1507:116: ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) )
        // InternalKdl.g:1508:7: ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 0);
        // InternalKdl.g:1511:10: ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) )
        // InternalKdl.g:1511:11: {...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred60_InternalKdl", "true");
        }
        // InternalKdl.g:1511:20: (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) )
        // InternalKdl.g:1511:21: otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) )
        {
        otherlv_46=(Token)match(input,56,FOLLOW_40); if (state.failed) return ;
        // InternalKdl.g:1515:10: ( (lv_default_47_0= ruleValue ) )
        // InternalKdl.g:1516:11: (lv_default_47_0= ruleValue )
        {
        // InternalKdl.g:1516:11: (lv_default_47_0= ruleValue )
        // InternalKdl.g:1517:12: lv_default_47_0= ruleValue
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_15_0_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_default_47_0=ruleValue();

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
    // $ANTLR end synpred60_InternalKdl

    // $ANTLR start synpred61_InternalKdl
    public final void synpred61_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_48=null;
        EObject lv_unit_49_0 = null;


        // InternalKdl.g:1540:5: ( ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1540:5: ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1540:5: ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1541:6: {...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred61_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1)");
        }
        // InternalKdl.g:1541:116: ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1542:7: ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getActorDefinitionAccess().getUnorderedGroup_1_15(), 1);
        // InternalKdl.g:1545:10: ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) )
        // InternalKdl.g:1545:11: {...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred61_InternalKdl", "true");
        }
        // InternalKdl.g:1545:20: (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) )
        // InternalKdl.g:1545:21: otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) )
        {
        otherlv_48=(Token)match(input,57,FOLLOW_88); if (state.failed) return ;
        // InternalKdl.g:1549:10: ( (lv_unit_49_0= ruleUnit ) )
        // InternalKdl.g:1550:11: (lv_unit_49_0= ruleUnit )
        {
        // InternalKdl.g:1550:11: (lv_unit_49_0= ruleUnit )
        // InternalKdl.g:1551:12: lv_unit_49_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getActorDefinitionAccess().getUnitUnitParserRuleCall_1_15_1_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_unit_49_0=ruleUnit();

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

    // $ANTLR start synpred66_InternalKdl
    public final void synpred66_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_geometry_4_0 = null;


        // InternalKdl.g:1724:4: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1724:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1724:4: ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1725:5: {...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred66_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKdl.g:1725:109: ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1726:6: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0);
        // InternalKdl.g:1729:9: ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1729:10: {...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred66_InternalKdl", "true");
        }
        // InternalKdl.g:1729:19: (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) )
        // InternalKdl.g:1729:20: otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) )
        {
        otherlv_3=(Token)match(input,60,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1733:9: ( (lv_geometry_4_0= ruleGeometry ) )
        // InternalKdl.g:1734:10: (lv_geometry_4_0= ruleGeometry )
        {
        // InternalKdl.g:1734:10: (lv_geometry_4_0= ruleGeometry )
        // InternalKdl.g:1735:11: lv_geometry_4_0= ruleGeometry
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
    // $ANTLR end synpred66_InternalKdl

    // $ANTLR start synpred67_InternalKdl
    public final void synpred67_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_5_0 = null;


        // InternalKdl.g:1758:4: ( ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) )
        // InternalKdl.g:1758:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
        {
        // InternalKdl.g:1758:4: ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) )
        // InternalKdl.g:1759:5: {...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred67_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKdl.g:1759:109: ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) )
        // InternalKdl.g:1760:6: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1);
        // InternalKdl.g:1763:9: ({...}? => ( (lv_computations_5_0= ruleComputation ) ) )
        // InternalKdl.g:1763:10: {...}? => ( (lv_computations_5_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred67_InternalKdl", "true");
        }
        // InternalKdl.g:1763:19: ( (lv_computations_5_0= ruleComputation ) )
        // InternalKdl.g:1763:20: (lv_computations_5_0= ruleComputation )
        {
        // InternalKdl.g:1763:20: (lv_computations_5_0= ruleComputation )
        // InternalKdl.g:1764:10: lv_computations_5_0= ruleComputation
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
    // $ANTLR end synpred67_InternalKdl

    // $ANTLR start synpred68_InternalKdl
    public final void synpred68_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_6=null;
        EObject lv_metadata_7_0 = null;


        // InternalKdl.g:1792:10: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )
        // InternalKdl.g:1792:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
        {
        otherlv_6=(Token)match(input,61,FOLLOW_46); if (state.failed) return ;
        // InternalKdl.g:1796:10: ( (lv_metadata_7_0= ruleMetadata ) )
        // InternalKdl.g:1797:11: (lv_metadata_7_0= ruleMetadata )
        {
        // InternalKdl.g:1797:11: (lv_metadata_7_0= ruleMetadata )
        // InternalKdl.g:1798:12: lv_metadata_7_0= ruleMetadata
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
    // $ANTLR end synpred68_InternalKdl

    // $ANTLR start synpred70_InternalKdl
    public final void synpred70_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;


        // InternalKdl.g:1817:10: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )
        // InternalKdl.g:1817:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
        {
        otherlv_8=(Token)match(input,62,FOLLOW_9); if (state.failed) return ;
        // InternalKdl.g:1821:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
        // InternalKdl.g:1822:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
        {
        // InternalKdl.g:1822:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
        // InternalKdl.g:1823:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
        {
        // InternalKdl.g:1823:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
        int alt138=2;
        int LA138_0 = input.LA(1);

        if ( (LA138_0==RULE_LOWERCASE_ID) ) {
            alt138=1;
        }
        else if ( (LA138_0==RULE_STRING) ) {
            alt138=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 138, 0, input);

            throw nvae;
        }
        switch (alt138) {
            case 1 :
                // InternalKdl.g:1824:13: lv_javaClass_9_1= ruleJavaClass
                {
                pushFollow(FOLLOW_2);
                lv_javaClass_9_1=ruleJavaClass();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:1840:13: lv_javaClass_9_2= RULE_STRING
                {
                lv_javaClass_9_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }


        }


        }
    }
    // $ANTLR end synpred70_InternalKdl

    // $ANTLR start synpred71_InternalKdl
    public final void synpred71_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_javaClass_9_2=null;
        EObject lv_metadata_7_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_9_1 = null;


        // InternalKdl.g:1786:4: ( ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )
        // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
        {
        // InternalKdl.g:1786:4: ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) )
        // InternalKdl.g:1787:5: {...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred71_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKdl.g:1787:109: ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) )
        // InternalKdl.g:1788:6: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2);
        // InternalKdl.g:1791:9: ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) )
        // InternalKdl.g:1791:10: {...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred71_InternalKdl", "true");
        }
        // InternalKdl.g:1791:19: ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? )
        // InternalKdl.g:1791:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
        {
        // InternalKdl.g:1791:20: (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )?
        int alt139=2;
        int LA139_0 = input.LA(1);

        if ( (LA139_0==61) ) {
            alt139=1;
        }
        switch (alt139) {
            case 1 :
                // InternalKdl.g:1792:10: otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) )
                {
                otherlv_6=(Token)match(input,61,FOLLOW_46); if (state.failed) return ;
                // InternalKdl.g:1796:10: ( (lv_metadata_7_0= ruleMetadata ) )
                // InternalKdl.g:1797:11: (lv_metadata_7_0= ruleMetadata )
                {
                // InternalKdl.g:1797:11: (lv_metadata_7_0= ruleMetadata )
                // InternalKdl.g:1798:12: lv_metadata_7_0= ruleMetadata
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_2_2_0_1_0());
                  											
                }
                pushFollow(FOLLOW_89);
                lv_metadata_7_0=ruleMetadata();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:1816:9: (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )?
        int alt141=2;
        int LA141_0 = input.LA(1);

        if ( (LA141_0==62) ) {
            alt141=1;
        }
        switch (alt141) {
            case 1 :
                // InternalKdl.g:1817:10: otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
                {
                otherlv_8=(Token)match(input,62,FOLLOW_9); if (state.failed) return ;
                // InternalKdl.g:1821:10: ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) )
                // InternalKdl.g:1822:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
                {
                // InternalKdl.g:1822:11: ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) )
                // InternalKdl.g:1823:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
                {
                // InternalKdl.g:1823:12: (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING )
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
                        // InternalKdl.g:1824:13: lv_javaClass_9_1= ruleJavaClass
                        {
                        pushFollow(FOLLOW_2);
                        lv_javaClass_9_1=ruleJavaClass();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 2 :
                        // InternalKdl.g:1840:13: lv_javaClass_9_2= RULE_STRING
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
    // $ANTLR end synpred71_InternalKdl

    // $ANTLR start synpred111_InternalKdl
    public final void synpred111_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2559:5: ( 'to' )
        // InternalKdl.g:2559:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred111_InternalKdl

    // $ANTLR start synpred152_InternalKdl
    public final void synpred152_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:3577:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:3577:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:3577:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:3578:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:3578:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:3579:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
    // $ANTLR end synpred152_InternalKdl

    // $ANTLR start synpred153_InternalKdl
    public final void synpred153_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:3597:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:3597:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:3597:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:3598:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:3598:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:3599:5: lv_function_1_0= ruleFunction
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
    // $ANTLR end synpred153_InternalKdl

    // $ANTLR start synpred154_InternalKdl
    public final void synpred154_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;


        // InternalKdl.g:3617:3: ( ( (lv_urn_2_0= ruleUrn ) ) )
        // InternalKdl.g:3617:3: ( (lv_urn_2_0= ruleUrn ) )
        {
        // InternalKdl.g:3617:3: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:3618:4: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:3618:4: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:3619:5: lv_urn_2_0= ruleUrn
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
    // $ANTLR end synpred154_InternalKdl

    // $ANTLR start synpred155_InternalKdl
    public final void synpred155_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_3_0 = null;


        // InternalKdl.g:3637:3: ( ( (lv_list_3_0= ruleList ) ) )
        // InternalKdl.g:3637:3: ( (lv_list_3_0= ruleList ) )
        {
        // InternalKdl.g:3637:3: ( (lv_list_3_0= ruleList ) )
        // InternalKdl.g:3638:4: (lv_list_3_0= ruleList )
        {
        // InternalKdl.g:3638:4: (lv_list_3_0= ruleList )
        // InternalKdl.g:3639:5: lv_list_3_0= ruleList
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
    // $ANTLR end synpred155_InternalKdl

    // $ANTLR start synpred157_InternalKdl
    public final void synpred157_InternalKdl_fragment() throws RecognitionException {   
        Token lv_expression_5_0=null;

        // InternalKdl.g:3677:3: ( ( (lv_expression_5_0= RULE_EXPR ) ) )
        // InternalKdl.g:3677:3: ( (lv_expression_5_0= RULE_EXPR ) )
        {
        // InternalKdl.g:3677:3: ( (lv_expression_5_0= RULE_EXPR ) )
        // InternalKdl.g:3678:4: (lv_expression_5_0= RULE_EXPR )
        {
        // InternalKdl.g:3678:4: (lv_expression_5_0= RULE_EXPR )
        // InternalKdl.g:3679:5: lv_expression_5_0= RULE_EXPR
        {
        lv_expression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred157_InternalKdl

    // $ANTLR start synpred174_InternalKdl
    public final void synpred174_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:4338:5: ( 'to' )
        // InternalKdl.g:4338:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred174_InternalKdl

    // $ANTLR start synpred195_InternalKdl
    public final void synpred195_InternalKdl_fragment() throws RecognitionException {   
        Token lv_mediated_0_0=null;
        Token otherlv_1=null;

        // InternalKdl.g:4778:5: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' ) )
        // InternalKdl.g:4778:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
        {
        // InternalKdl.g:4778:5: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )
        // InternalKdl.g:4779:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
        {
        // InternalKdl.g:4779:6: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
        // InternalKdl.g:4780:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
        {
        // InternalKdl.g:4780:7: (lv_mediated_0_0= RULE_LOWERCASE_ID )
        // InternalKdl.g:4781:8: lv_mediated_0_0= RULE_LOWERCASE_ID
        {
        lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_71); if (state.failed) return ;

        }


        }

        otherlv_1=(Token)match(input,105,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred195_InternalKdl

    // $ANTLR start synpred198_InternalKdl
    public final void synpred198_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject lv_parameters_6_0 = null;


        // InternalKdl.g:4829:5: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) )
        // InternalKdl.g:4829:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
        {
        // InternalKdl.g:4829:5: ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' )
        // InternalKdl.g:4830:6: ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')'
        {
        // InternalKdl.g:4830:6: ( (lv_name_4_0= rulePathName ) )
        // InternalKdl.g:4831:7: (lv_name_4_0= rulePathName )
        {
        // InternalKdl.g:4831:7: (lv_name_4_0= rulePathName )
        // InternalKdl.g:4832:8: lv_name_4_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
          							
        }
        pushFollow(FOLLOW_54);
        lv_name_4_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_5=(Token)match(input,33,FOLLOW_15); if (state.failed) return ;
        // InternalKdl.g:4853:6: ( (lv_parameters_6_0= ruleParameterList ) )?
        int alt158=2;
        int LA158_0 = input.LA(1);

        if ( ((LA158_0>=RULE_STRING && LA158_0<=RULE_LOWERCASE_ID)||(LA158_0>=RULE_INT && LA158_0<=RULE_CAMELCASE_ID)||(LA158_0>=RULE_ID && LA158_0<=RULE_EXPR)||LA158_0==31||LA158_0==33||LA158_0==43||LA158_0==49||(LA158_0>=89 && LA158_0<=90)||LA158_0==95||LA158_0==98||LA158_0==112) ) {
            alt158=1;
        }
        switch (alt158) {
            case 1 :
                // InternalKdl.g:4854:7: (lv_parameters_6_0= ruleParameterList )
                {
                // InternalKdl.g:4854:7: (lv_parameters_6_0= ruleParameterList )
                // InternalKdl.g:4855:8: lv_parameters_6_0= ruleParameterList
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
    // $ANTLR end synpred198_InternalKdl

    // $ANTLR start synpred199_InternalKdl
    public final void synpred199_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_8_0 = null;


        // InternalKdl.g:4878:5: ( ( (lv_urn_8_0= ruleUrn ) ) )
        // InternalKdl.g:4878:5: ( (lv_urn_8_0= ruleUrn ) )
        {
        // InternalKdl.g:4878:5: ( (lv_urn_8_0= ruleUrn ) )
        // InternalKdl.g:4879:6: (lv_urn_8_0= ruleUrn )
        {
        // InternalKdl.g:4879:6: (lv_urn_8_0= ruleUrn )
        // InternalKdl.g:4880:7: lv_urn_8_0= ruleUrn
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
    // $ANTLR end synpred199_InternalKdl

    // $ANTLR start synpred200_InternalKdl
    public final void synpred200_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_value_9_0 = null;


        // InternalKdl.g:4898:5: ( ( (lv_value_9_0= ruleLiteral ) ) )
        // InternalKdl.g:4898:5: ( (lv_value_9_0= ruleLiteral ) )
        {
        // InternalKdl.g:4898:5: ( (lv_value_9_0= ruleLiteral ) )
        // InternalKdl.g:4899:6: (lv_value_9_0= ruleLiteral )
        {
        // InternalKdl.g:4899:6: (lv_value_9_0= ruleLiteral )
        // InternalKdl.g:4900:7: lv_value_9_0= ruleLiteral
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
    // $ANTLR end synpred200_InternalKdl

    // $ANTLR start synpred211_InternalKdl
    public final void synpred211_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5166:4: ( ( RULE_INT ) )
        // InternalKdl.g:5166:5: ( RULE_INT )
        {
        // InternalKdl.g:5166:5: ( RULE_INT )
        // InternalKdl.g:5167:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred211_InternalKdl

    // $ANTLR start synpred212_InternalKdl
    public final void synpred212_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5188:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5188:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5188:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:5189:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:5189:5: ( ( '.' ) )
        // InternalKdl.g:5190:6: ( '.' )
        {
        // InternalKdl.g:5190:6: ( '.' )
        // InternalKdl.g:5191:7: '.'
        {
        match(input,102,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:5194:5: ( ( RULE_INT ) )
        // InternalKdl.g:5195:6: ( RULE_INT )
        {
        // InternalKdl.g:5195:6: ( RULE_INT )
        // InternalKdl.g:5196:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred212_InternalKdl

    // $ANTLR start synpred216_InternalKdl
    public final void synpred216_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5237:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5237:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5237:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:5238:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:5238:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:5239:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:5239:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:5240:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=113 && input.LA(1)<=114) ) {
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

        // InternalKdl.g:5247:5: ( '+' | ( ( '-' ) ) )?
        int alt163=3;
        int LA163_0 = input.LA(1);

        if ( (LA163_0==43) ) {
            alt163=1;
        }
        else if ( (LA163_0==112) ) {
            alt163=2;
        }
        switch (alt163) {
            case 1 :
                // InternalKdl.g:5248:6: '+'
                {
                match(input,43,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5250:6: ( ( '-' ) )
                {
                // InternalKdl.g:5250:6: ( ( '-' ) )
                // InternalKdl.g:5251:7: ( '-' )
                {
                // InternalKdl.g:5251:7: ( '-' )
                // InternalKdl.g:5252:8: '-'
                {
                match(input,112,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:5256:5: ( ( RULE_INT ) )
        // InternalKdl.g:5257:6: ( RULE_INT )
        {
        // InternalKdl.g:5257:6: ( RULE_INT )
        // InternalKdl.g:5258:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred216_InternalKdl

    // $ANTLR start synpred226_InternalKdl
    public final void synpred226_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:5567:4: (kw= '-' )
        // InternalKdl.g:5567:4: kw= '-'
        {
        kw=(Token)match(input,112,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred226_InternalKdl

    // $ANTLR start synpred227_InternalKdl
    public final void synpred227_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:5574:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:5574:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred227_InternalKdl

    // $ANTLR start synpred228_InternalKdl
    public final void synpred228_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:5582:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:5582:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred228_InternalKdl

    // Delegated rules

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
    public final boolean synpred226_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred226_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred216_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred216_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred198_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred198_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred66_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred66_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred111_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred111_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred174_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred174_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred195_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred195_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred152_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred152_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred211_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred211_InternalKdl_fragment(); // can never throw exception
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
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA60 dfa60 = new DFA60(this);
    protected DFA63 dfa63 = new DFA63(this);
    protected DFA66 dfa66 = new DFA66(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA88 dfa88 = new DFA88(this);
    protected DFA98 dfa98 = new DFA98(this);
    protected DFA117 dfa117 = new DFA117(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\6\15\uffff";
    static final String dfa_4s = "\1\123\15\uffff";
    static final String dfa_5s = "\1\uffff\1\15\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14";
    static final String dfa_6s = "\1\0\15\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\15\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\2\uffff\2\1\1\uffff\4\1\2\uffff\1\1\10\uffff\1\1\12\uffff\24\1",
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
                        if ( (LA7_0==EOF||LA7_0==RULE_ANNOTATION_ID||(LA7_0>=35 && LA7_0<=36)||(LA7_0>=38 && LA7_0<=41)||LA7_0==44||LA7_0==53||(LA7_0>=64 && LA7_0<=83)) ) {s = 1;}

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
    static final String dfa_11s = "\1\137\1\146\1\uffff\1\5\1\uffff\1\146";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\1\uffff";
    static final String dfa_13s = "\6\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\131\uffff\1\2",
            "\1\4\15\uffff\13\4\1\uffff\1\4\2\uffff\2\4\1\uffff\4\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4\14\uffff\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\4\15\uffff\13\4\1\uffff\1\4\2\uffff\2\4\1\uffff\4\4\2\uffff\1\4\10\uffff\1\4\12\uffff\24\4\14\uffff\1\2\4\uffff\1\4\1\3"
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
    static final String dfa_16s = "\1\6\1\54\1\7\1\uffff\25\4\1\uffff";
    static final String dfa_17s = "\3\123\1\uffff\25\56\1\uffff";
    static final String dfa_18s = "\3\uffff\1\2\25\uffff\1\1";
    static final String dfa_19s = "\32\uffff}>";
    static final String[] dfa_20s = {
            "\1\3\34\uffff\1\1\1\2\1\uffff\4\3\2\uffff\1\3\10\uffff\1\3\12\uffff\24\3",
            "\1\3\10\uffff\1\25\12\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\26\1\27\1\30",
            "\1\3\42\uffff\1\3\1\uffff\1\3\10\uffff\1\25\12\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\26\1\27\1\30",
            "",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            "\2\3\1\31\1\uffff\1\3\44\uffff\2\3",
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "692:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( (lv_imported_1_0= 'import' ) ) ) ( (lv_type_2_0= ruleACTOR ) ) ( (lv_annotationTag_3_0= RULE_ANNOTATION_ID ) ) ( (lv_docstring_4_0= RULE_STRING ) )? (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ) | ( ( (lv_annotations_7_0= ruleAnnotation ) )* ( (lv_abstract_8_0= 'abstract' ) )? ( (lv_final_9_0= 'final' ) )? ( (lv_optional_10_0= 'optional' ) )? ( ( (lv_exported_11_0= 'export' ) ) | ( (lv_filter_12_0= 'filter' ) ) | ( ( (lv_imported_13_0= 'import' ) ) ( ( (lv_multiple_14_0= 'multiple' ) ) | ( ( (lv_arity_15_0= RULE_INT ) ) ( (lv_minimum_16_0= '+' ) )? ) )? ) )? ( (lv_parameter_17_0= 'parameter' ) )? ( (lv_type_18_0= ruleACTOR ) ) ( (lv_expression_19_0= 'expression' ) )? ( ( (lv_name_20_1= RULE_LOWERCASE_ID | lv_name_20_2= RULE_LOWERCASE_DASHID | lv_name_20_3= RULE_STRING | lv_name_20_4= '*' ) ) ) (otherlv_21= 'extends' ( ( (lv_extended_22_1= RULE_LOWERCASE_ID | lv_extended_22_2= RULE_LOWERCASE_DASHID | lv_extended_22_3= RULE_STRING ) ) ) )? (otherlv_23= 'for' ( (lv_targets_24_0= ruleTARGET ) ) (otherlv_25= ',' ( (lv_targets_26_0= ruleTARGET ) ) )* )? ( (lv_docstring_27_0= RULE_STRING ) )? (otherlv_28= 'label' ( (lv_label_29_0= RULE_STRING ) ) )? (otherlv_30= '{' ( (lv_body_31_0= ruleDataflowBody ) ) otherlv_32= '}' )? ( ( (otherlv_33= 'minimum' ( (lv_rangeMin_34_0= ruleNumber ) ) ) | (otherlv_35= 'maximum' ( (lv_rangeMax_36_0= ruleNumber ) ) ) | (otherlv_37= 'range' ( (lv_rangeMin_38_0= ruleNumber ) ) otherlv_39= 'to' ( (lv_rangeMax_40_0= ruleNumber ) ) ) ) | (otherlv_41= 'values' ( ( (lv_enumValues_42_1= RULE_STRING | lv_enumValues_42_2= RULE_UPPERCASE_ID | lv_enumValues_42_3= RULE_LOWERCASE_ID | lv_enumValues_42_4= RULE_CAMELCASE_ID ) ) ) (otherlv_43= ',' ( ( (lv_enumValues_44_1= RULE_STRING | lv_enumValues_44_2= RULE_UPPERCASE_ID | lv_enumValues_44_3= RULE_LOWERCASE_ID | lv_enumValues_44_4= RULE_CAMELCASE_ID ) ) ) )* ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_46= 'default' ( (lv_default_47_0= ruleValue ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_48= 'unit' ( (lv_unit_49_0= ruleUnit ) ) ) ) ) ) )* ) ) ) (otherlv_50= 'as' ( (lv_localName_51_0= RULE_LOWERCASE_ID ) ) )? (otherlv_52= 'over' ( (lv_coverage_53_0= ruleFunction ) ) (otherlv_54= ',' ( (lv_coverage_55_0= ruleFunction ) ) )* )? ) )";
        }
    }
    static final String dfa_21s = "\12\uffff";
    static final String dfa_22s = "\1\2\11\uffff";
    static final String dfa_23s = "\1\62\4\0\5\uffff";
    static final String dfa_24s = "\1\77\4\0\5\uffff";
    static final String dfa_25s = "\5\uffff\2\3\1\4\1\1\1\2";
    static final String dfa_26s = "\1\4\1\0\1\1\1\2\1\3\5\uffff}>";
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

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "()+ loopback of 1723:6: ( ({...}? => ( ({...}? => (otherlv_3= 'geometry' ( (lv_geometry_4_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_5_0= ruleComputation ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_6= 'metadata' ( (lv_metadata_7_0= ruleMetadata ) ) )? (otherlv_8= 'class' ( ( (lv_javaClass_9_1= ruleJavaClass | lv_javaClass_9_2= RULE_STRING ) ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA45_1 = input.LA(1);

                         
                        int index45_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred71_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 7;}

                         
                        input.seek(index45_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA45_2 = input.LA(1);

                         
                        int index45_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred71_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2()) ) {s = 7;}

                         
                        input.seek(index45_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA45_3 = input.LA(1);

                         
                        int index45_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred66_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 0) ) {s = 8;}

                        else if ( synpred71_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index45_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA45_4 = input.LA(1);

                         
                        int index45_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred67_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 1) ) {s = 9;}

                        else if ( synpred71_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index45_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA45_0 = input.LA(1);

                         
                        int index45_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA45_0==50) ) {s = 1;}

                        else if ( (LA45_0==EOF) ) {s = 2;}

                        else if ( (LA45_0==60) ) {s = 3;}

                        else if ( (LA45_0==63) ) {s = 4;}

                        else if ( LA45_0 == 61 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 5;}

                        else if ( LA45_0 == 62 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_2(), 2) ) {s = 6;}

                         
                        input.seek(index45_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 45, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_28s = "\25\uffff";
    static final String dfa_29s = "\4\uffff\1\20\14\uffff\1\20\2\uffff\1\20";
    static final String dfa_30s = "\1\4\1\uffff\2\7\1\66\7\uffff\3\7\2\uffff\1\66\2\7\1\66";
    static final String dfa_31s = "\1\160\1\uffff\2\7\1\162\7\uffff\1\7\2\160\2\uffff\1\162\2\7\1\140";
    static final String dfa_32s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\2\1\3\4\uffff";
    static final String dfa_33s = "\25\uffff}>";
    static final String[] dfa_34s = {
            "\1\6\2\uffff\1\4\31\uffff\1\10\11\uffff\1\2\2\uffff\1\13\2\uffff\1\7\47\uffff\2\1\2\uffff\1\5\1\12\11\uffff\1\11\2\uffff\5\11\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\17\44\uffff\2\17\3\uffff\1\20\5\uffff\1\14\12\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\43\uffff\1\22\104\uffff\1\23",
            "\1\24\43\uffff\1\22\104\uffff\1\23",
            "",
            "",
            "\1\17\44\uffff\2\17\3\uffff\1\20\20\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\17\44\uffff\2\17\3\uffff\1\20"
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[][] dfa_34 = unpackEncodedStringArray(dfa_34s);

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "2484:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_map_13_0= ruleMap ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= RULE_STRING ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= RULE_STRING ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
        }
    }
    static final String dfa_35s = "\17\uffff";
    static final String dfa_36s = "\3\uffff\1\12\7\uffff\1\12\2\uffff\1\12";
    static final String dfa_37s = "\1\4\2\7\1\4\2\uffff\3\7\2\uffff\1\4\2\7\1\4";
    static final String dfa_38s = "\1\160\2\7\1\162\2\uffff\1\7\2\160\2\uffff\1\162\2\7\1\160";
    static final String dfa_39s = "\4\uffff\1\3\1\4\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_40s = "\17\uffff}>";
    static final String[] dfa_41s = {
            "\1\4\2\uffff\1\3\43\uffff\1\1\55\uffff\2\5\25\uffff\1\2",
            "\1\3",
            "\1\3",
            "\7\12\2\uffff\2\12\5\uffff\21\12\1\uffff\4\12\1\uffff\2\12\4\uffff\2\12\2\uffff\1\12\1\11\1\uffff\34\12\5\uffff\2\12\4\uffff\1\12\2\uffff\1\12\3\uffff\1\6\2\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "",
            "",
            "\1\13",
            "\1\16\43\uffff\1\14\104\uffff\1\15",
            "\1\16\43\uffff\1\14\104\uffff\1\15",
            "",
            "",
            "\7\12\2\uffff\2\12\5\uffff\21\12\1\uffff\4\12\1\uffff\2\12\4\uffff\2\12\2\uffff\1\12\1\11\1\uffff\34\12\5\uffff\2\12\4\uffff\1\12\2\uffff\1\12\6\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "\1\16",
            "\1\16",
            "\7\12\2\uffff\2\12\5\uffff\21\12\1\uffff\4\12\1\uffff\2\12\4\uffff\2\12\2\uffff\1\12\1\11\1\uffff\34\12\5\uffff\2\12\4\uffff\1\12\2\uffff\1\12\6\uffff\1\12\6\uffff\1\12"
    };

    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[][] dfa_41 = unpackEncodedStringArray(dfa_41s);

    class DFA63 extends DFA {

        public DFA63(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 63;
            this.eot = dfa_35;
            this.eof = dfa_36;
            this.min = dfa_37;
            this.max = dfa_38;
            this.accept = dfa_39;
            this.special = dfa_40;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "2896:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_42s = "\21\uffff";
    static final String dfa_43s = "\3\uffff\1\13\11\uffff\1\13\2\uffff\1\13";
    static final String dfa_44s = "\1\4\2\7\1\4\4\uffff\3\7\2\uffff\1\4\2\7\1\4";
    static final String dfa_45s = "\1\160\2\7\1\162\4\uffff\1\7\2\160\2\uffff\1\162\2\7\1\160";
    static final String dfa_46s = "\4\uffff\1\3\1\4\1\5\1\6\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_47s = "\21\uffff}>";
    static final String[] dfa_48s = {
            "\1\4\1\6\1\uffff\1\3\1\uffff\1\6\3\uffff\1\6\21\uffff\1\7\13\uffff\1\1\55\uffff\2\5\25\uffff\1\2",
            "\1\3",
            "\1\3",
            "\7\13\2\uffff\2\13\5\uffff\21\13\1\uffff\4\13\1\uffff\2\13\4\uffff\2\13\2\uffff\1\13\1\14\1\uffff\34\13\5\uffff\2\13\4\uffff\1\13\2\uffff\1\13\3\uffff\1\10\11\uffff\1\13\1\11\1\12",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\20\43\uffff\1\16\104\uffff\1\17",
            "\1\20\43\uffff\1\16\104\uffff\1\17",
            "",
            "",
            "\7\13\2\uffff\2\13\5\uffff\21\13\1\uffff\4\13\1\uffff\2\13\4\uffff\2\13\2\uffff\1\13\1\14\1\uffff\34\13\5\uffff\2\13\4\uffff\1\13\2\uffff\1\13\15\uffff\1\13\1\11\1\12",
            "\1\20",
            "\1\20",
            "\7\13\2\uffff\2\13\5\uffff\21\13\1\uffff\4\13\1\uffff\2\13\4\uffff\2\13\2\uffff\1\13\1\14\1\uffff\34\13\5\uffff\2\13\4\uffff\1\13\2\uffff\1\13\15\uffff\1\13"
    };

    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final char[] dfa_45 = DFA.unpackEncodedStringToUnsignedChars(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final short[][] dfa_48 = unpackEncodedStringArray(dfa_48s);

    class DFA66 extends DFA {

        public DFA66(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 66;
            this.eot = dfa_42;
            this.eof = dfa_43;
            this.min = dfa_44;
            this.max = dfa_45;
            this.accept = dfa_46;
            this.special = dfa_47;
            this.transition = dfa_48;
        }
        public String getDescription() {
            return "3026:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( ( (lv_id_6_1= RULE_ID | lv_id_6_2= RULE_LOWERCASE_ID | lv_id_6_3= RULE_UPPERCASE_ID ) ) ) | ( (lv_comma_7_0= ',' ) ) )";
        }
    }
    static final String dfa_49s = "\27\uffff";
    static final String dfa_50s = "\1\4\6\0\1\uffff\2\0\1\uffff\5\0\7\uffff";
    static final String dfa_51s = "\1\160\6\0\1\uffff\2\0\1\uffff\5\0\7\uffff";
    static final String dfa_52s = "\7\uffff\1\1\10\uffff\1\5\1\7\1\2\1\3\1\10\1\6\1\4";
    static final String dfa_53s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\uffff\1\6\1\7\1\uffff\1\10\1\11\1\12\1\13\1\14\7\uffff}>";
    static final String[] dfa_54s = {
            "\1\4\1\10\1\uffff\1\3\1\15\1\11\1\14\2\uffff\1\7\1\16\20\uffff\1\7\1\uffff\1\17\11\uffff\1\1\5\uffff\1\20\47\uffff\1\5\1\6\4\uffff\1\13\2\uffff\1\21\15\uffff\1\2",
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

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = dfa_49;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "3576:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( (lv_urn_2_0= ruleUrn ) ) | ( (lv_list_3_0= ruleList ) ) | ( (lv_map_4_0= ruleMap ) ) | ( (lv_expression_5_0= RULE_EXPR ) ) | ( (lv_table_6_0= ruleLookupTable ) ) | ( (lv_enumId_7_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA75_1 = input.LA(1);

                         
                        int index75_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index75_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA75_2 = input.LA(1);

                         
                        int index75_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index75_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA75_3 = input.LA(1);

                         
                        int index75_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index75_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA75_4 = input.LA(1);

                         
                        int index75_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred154_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index75_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA75_5 = input.LA(1);

                         
                        int index75_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index75_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA75_6 = input.LA(1);

                         
                        int index75_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index75_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA75_8 = input.LA(1);

                         
                        int index75_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred154_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index75_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA75_9 = input.LA(1);

                         
                        int index75_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred152_InternalKdl()) ) {s = 7;}

                        else if ( (true) ) {s = 20;}

                         
                        input.seek(index75_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA75_11 = input.LA(1);

                         
                        int index75_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred154_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index75_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA75_12 = input.LA(1);

                         
                        int index75_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred154_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index75_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA75_13 = input.LA(1);

                         
                        int index75_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred154_InternalKdl()) ) {s = 19;}

                         
                        input.seek(index75_13);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA75_14 = input.LA(1);

                         
                        int index75_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred157_InternalKdl()) ) {s = 21;}

                         
                        input.seek(index75_14);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA75_15 = input.LA(1);

                         
                        int index75_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred153_InternalKdl()) ) {s = 18;}

                        else if ( (synpred155_InternalKdl()) ) {s = 22;}

                         
                        input.seek(index75_15);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 75, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_55s = "\4\uffff\1\17\14\uffff\1\17\2\uffff\1\17";
    static final String dfa_56s = "\1\4\1\uffff\2\7\1\37\7\uffff\3\7\2\uffff\1\37\2\7\1\37";
    static final String dfa_57s = "\1\160\1\uffff\2\7\1\162\7\uffff\1\7\2\160\2\uffff\1\162\2\7\1\144";
    static final String dfa_58s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\3\uffff\1\3\1\2\4\uffff";
    static final String[] dfa_59s = {
            "\1\6\2\uffff\1\4\6\uffff\1\10\34\uffff\1\2\2\uffff\1\12\52\uffff\2\1\2\uffff\1\5\1\11\2\uffff\1\13\6\uffff\1\7\2\uffff\5\7\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\17\26\uffff\1\20\44\uffff\2\20\6\uffff\2\17\1\uffff\1\14\12\uffff\1\15\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\24\43\uffff\1\22\104\uffff\1\23",
            "\1\24\43\uffff\1\22\104\uffff\1\23",
            "",
            "",
            "\1\17\26\uffff\1\20\44\uffff\2\20\6\uffff\2\17\14\uffff\1\15\1\16",
            "\1\24",
            "\1\24",
            "\1\17\26\uffff\1\20\44\uffff\2\20\6\uffff\2\17"
    };
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final char[] dfa_57 = DFA.unpackEncodedStringToUnsignedChars(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA88 extends DFA {

        public DFA88(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 88;
            this.eot = dfa_28;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_33;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "4263:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( ( (lv_op_13_0= ruleREL_OPERATOR ) ) ( (lv_expression_14_0= ruleNumber ) ) ) | ( (lv_expr_15_0= RULE_EXPR ) ) | ( (lv_nodata_16_0= 'unknown' ) ) | ( (lv_star_17_0= '*' ) ) | ( (lv_anything_18_0= '#' ) ) )";
        }
    }
    static final String dfa_60s = "\15\uffff";
    static final String dfa_61s = "\1\4\1\0\1\uffff\1\0\11\uffff";
    static final String dfa_62s = "\1\160\1\0\1\uffff\1\0\11\uffff";
    static final String dfa_63s = "\2\uffff\1\2\3\uffff\1\3\4\uffff\1\4\1\1";
    static final String dfa_64s = "\1\uffff\1\0\1\uffff\1\1\11\uffff}>";
    static final String[] dfa_65s = {
            "\1\3\1\1\1\uffff\1\6\1\2\1\uffff\1\2\3\uffff\1\13\34\uffff\1\6\55\uffff\2\6\4\uffff\1\2\20\uffff\1\6",
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

    class DFA98 extends DFA {

        public DFA98(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 98;
            this.eot = dfa_60;
            this.eof = dfa_60;
            this.min = dfa_61;
            this.max = dfa_62;
            this.accept = dfa_63;
            this.special = dfa_64;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "4828:4: ( ( ( (lv_name_4_0= rulePathName ) ) otherlv_5= '(' ( (lv_parameters_6_0= ruleParameterList ) )? otherlv_7= ')' ) | ( (lv_urn_8_0= ruleUrn ) ) | ( (lv_value_9_0= ruleLiteral ) ) | ( (lv_expression_10_0= RULE_EXPR ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA98_1 = input.LA(1);

                         
                        int index98_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred198_InternalKdl()) ) {s = 12;}

                        else if ( (synpred199_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index98_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA98_3 = input.LA(1);

                         
                        int index98_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred199_InternalKdl()) ) {s = 2;}

                        else if ( (synpred200_InternalKdl()) ) {s = 6;}

                         
                        input.seek(index98_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 98, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_66s = "\1\5\1\140\1\uffff\1\5\1\uffff\1\140";
    static final String[] dfa_67s = {
            "\1\1\131\uffff\1\2",
            "\1\2\4\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\2\4\uffff\1\4\1\3"
    };
    static final char[] dfa_66 = DFA.unpackEncodedStringToUnsignedChars(dfa_66s);
    static final short[][] dfa_67 = unpackEncodedStringArray(dfa_67s);

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
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
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L,0x0000000080000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x002013D97FF00042L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000400000000800L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000030L,0x0000000080000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000802000045B0L,0x0001000086000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x002013D9FFF00042L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000530L,0x0000000080000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x002013D800000042L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00020806800067B0L,0x0001000486000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x002013D800000040L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000002000000012L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x002017D8000000C0L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00201BD800000040L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000600000000130L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000400000000130L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0FBB802000000012L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000130L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0FBB002000000012L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000001F00000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0FBA002080000012L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0FBA002000000002L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0FBA000000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0xF02413D800000040L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0FB8000000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000080000000080L,0x0001000000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0F00000000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000630L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0F00000080000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x00020802800067B0L,0x0001000486000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0F00400200001620L,0x0008002000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0xF02013D800000042L,0x00000000000FFFFFL});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0xF000000000000002L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000400600001620L,0x0008002000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000400000000002L,0x0008002000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000200001620L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0040000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000000018000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0004000000000020L,0x0000000080000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0002080200002090L,0x0001000006000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000002L,0x0000000300000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0006480200000090L,0x0001F90066000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0004000080000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0002480200000090L,0x0001F90066000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000480000004090L,0x0001F90A66000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000480000004090L,0x0001F90266000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000006200000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000520L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000018000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x00000800000045B0L,0x0001000086000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x0006004000000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000002L,0x0006000000000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000002L,0x0000006000000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000222L,0x0001004000000000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000222L,0x0001000000000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000222L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000400200001620L,0x0008002000000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x4000000000000002L});

}
