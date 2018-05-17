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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_LOWERCASE_ID", "RULE_INT", "RULE_LOWERCASE_DASHID", "RULE_UPPERCASE_ID", "RULE_SHAPE", "RULE_CAMELCASE_ID", "RULE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'@dataflow'", "'@var'", "'@val'", "'@author'", "'@version'", "'@klab'", "'@worldview'", "'@geometry'", "'@endpoint'", "'@namespace'", "'@coverage'", "','", "'@context'", "'export'", "'optional'", "'import'", "'multiple'", "'+'", "'for'", "'{'", "'}'", "'as'", "'over'", "'number'", "'boolean'", "'text'", "'list'", "'enum'", "'input'", "'values'", "'default'", "'observe'", "'new'", "'geometry'", "'units'", "'semantics'", "'metadata'", "'class'", "'compute'", "'*'", "'of'", "'within'", "'not'", "'identified'", "'by'", "'presence'", "'count'", "'distance'", "'to'", "'from'", "'probability'", "'uncertainty'", "'proportion'", "'in'", "'ratio'", "'value'", "'occurrence'", "'('", "')'", "'down'", "'per'", "'object'", "'process'", "'concept'", "'extent'", "'spatialextent'", "'temporalextent'", "'annotation'", "'contextualizer'", "'void'", "'partition'", "'models'", "'concepts'", "'observers'", "'otherwise'", "'if'", "'unless'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'unknown'", "'urn:klab:'", "':'", "'#'", "'/'", "'.'", "'=?'", "'='", "'>>'", "'classify'", "'>'", "'<'", "'!='", "'<='", "'>='", "'@'", "'-'", "'e'", "'E'", "'^'"
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
    public static final int RULE_ID=11;
    public static final int RULE_INT=6;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=13;
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
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=12;
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
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_LOWERCASE_DASHID=7;
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
    public static final int RULE_SL_COMMENT=14;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__114=114;
    public static final int T__75=75;
    public static final int T__117=117;
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
    public static final int RULE_WS=15;
    public static final int RULE_ANY_OTHER=16;
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
            	    otherlv_1=(Token)match(input,17,FOLLOW_3); if (state.failed) return current;
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

            	        if ( (LA2_0==18) ) {
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
            	    	    otherlv_3=(Token)match(input,18,FOLLOW_5); if (state.failed) return current;
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

            	        if ( (LA3_0==19) ) {
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
            	    	    otherlv_5=(Token)match(input,19,FOLLOW_5); if (state.failed) return current;
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

            	        if ( (LA4_0==20) ) {
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
            	    	    otherlv_7=(Token)match(input,20,FOLLOW_6); if (state.failed) return current;
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
            	    otherlv_9=(Token)match(input,21,FOLLOW_7); if (state.failed) return current;
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
            	    otherlv_11=(Token)match(input,22,FOLLOW_7); if (state.failed) return current;
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
            	    otherlv_13=(Token)match(input,23,FOLLOW_5); if (state.failed) return current;
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
            	    otherlv_15=(Token)match(input,24,FOLLOW_8); if (state.failed) return current;
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
            	    otherlv_17=(Token)match(input,25,FOLLOW_6); if (state.failed) return current;
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
            	    otherlv_19=(Token)match(input,26,FOLLOW_3); if (state.failed) return current;
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
            	    otherlv_21=(Token)match(input,27,FOLLOW_9); if (state.failed) return current;
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

            	        if ( (LA5_0==28) ) {
            	            alt5=1;
            	        }


            	        switch (alt5) {
            	    	case 1 :
            	    	    // InternalKdl.g:488:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
            	    	    {
            	    	    otherlv_23=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
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
            	    otherlv_25=(Token)match(input,29,FOLLOW_11); if (state.failed) return current;
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

                if ( ((LA7_0>=30 && LA7_0<=32)||(LA7_0>=40 && LA7_0<=44)||LA7_0==72||(LA7_0>=78 && LA7_0<=87)) ) {
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
    // InternalKdl.g:592:1: ruleActorDefinition returns [EObject current=null] : ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )? ) ) ;
    public final EObject ruleActorDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_exported_0_0=null;
        Token lv_optional_1_0=null;
        Token lv_imported_2_0=null;
        Token lv_multiple_3_0=null;
        Token lv_arity_4_0=null;
        Token lv_minimum_5_0=null;
        Token lv_name_7_1=null;
        Token lv_name_7_2=null;
        Token lv_name_7_3=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token lv_docstring_12_0=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_localName_17_0=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token lv_optional_22_0=null;
        Token lv_type_23_1=null;
        Token lv_type_23_2=null;
        Token lv_type_23_3=null;
        Token lv_type_23_4=null;
        Token lv_type_23_5=null;
        Token lv_parameter_24_0=null;
        Token lv_name_25_0=null;
        Token otherlv_26=null;
        Token lv_enumValues_27_0=null;
        Token otherlv_28=null;
        Token lv_enumValues_29_0=null;
        Token lv_docstring_30_0=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        AntlrDatatypeRuleToken lv_type_6_0 = null;

        AntlrDatatypeRuleToken lv_targets_9_0 = null;

        AntlrDatatypeRuleToken lv_targets_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_coverage_19_0 = null;

        EObject lv_coverage_21_0 = null;

        EObject lv_default_32_0 = null;

        EObject lv_body_34_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:598:2: ( ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )? ) ) )
            // InternalKdl.g:599:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )? ) )
            {
            // InternalKdl.g:599:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )? ) )
            int alt26=2;
            switch ( input.LA(1) ) {
            case 30:
            case 32:
            case 72:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
                {
                alt26=1;
                }
                break;
            case 31:
                {
                int LA26_2 = input.LA(2);

                if ( (LA26_2==32) ) {
                    alt26=1;
                }
                else if ( (LA26_2==45) ) {
                    alt26=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 2, input);

                    throw nvae;
                }
                }
                break;
            case 40:
                {
                int LA26_3 = input.LA(2);

                if ( (LA26_3==45) ) {
                    alt26=2;
                }
                else if ( ((LA26_3>=RULE_STRING && LA26_3<=RULE_LOWERCASE_ID)||LA26_3==RULE_LOWERCASE_DASHID) ) {
                    alt26=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 3, input);

                    throw nvae;
                }
                }
                break;
            case 41:
                {
                int LA26_4 = input.LA(2);

                if ( (LA26_4==45) ) {
                    alt26=2;
                }
                else if ( ((LA26_4>=RULE_STRING && LA26_4<=RULE_LOWERCASE_ID)||LA26_4==RULE_LOWERCASE_DASHID) ) {
                    alt26=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 4, input);

                    throw nvae;
                }
                }
                break;
            case 42:
                {
                int LA26_5 = input.LA(2);

                if ( ((LA26_5>=RULE_STRING && LA26_5<=RULE_LOWERCASE_ID)||LA26_5==RULE_LOWERCASE_DASHID) ) {
                    alt26=1;
                }
                else if ( (LA26_5==45) ) {
                    alt26=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 5, input);

                    throw nvae;
                }
                }
                break;
            case 43:
            case 44:
                {
                alt26=2;
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
                    // InternalKdl.g:600:3: ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:600:3: ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:601:4: ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_type_6_0= ruleACTOR ) ) ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) ) (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )? ( (lv_docstring_12_0= RULE_STRING ) )? (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )? (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )? (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )?
                    {
                    // InternalKdl.g:601:4: ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )?
                    int alt11=3;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==30) ) {
                        alt11=1;
                    }
                    else if ( ((LA11_0>=31 && LA11_0<=32)) ) {
                        alt11=2;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalKdl.g:602:5: ( (lv_exported_0_0= 'export' ) )
                            {
                            // InternalKdl.g:602:5: ( (lv_exported_0_0= 'export' ) )
                            // InternalKdl.g:603:6: (lv_exported_0_0= 'export' )
                            {
                            // InternalKdl.g:603:6: (lv_exported_0_0= 'export' )
                            // InternalKdl.g:604:7: lv_exported_0_0= 'export'
                            {
                            lv_exported_0_0=(Token)match(input,30,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_exported_0_0, grammarAccess.getActorDefinitionAccess().getExportedExportKeyword_0_0_0_0());
                              						
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
                            // InternalKdl.g:617:5: ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? )
                            {
                            // InternalKdl.g:617:5: ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? )
                            // InternalKdl.g:618:6: ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )?
                            {
                            // InternalKdl.g:618:6: ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) )
                            // InternalKdl.g:619:7: ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) )
                            {
                            // InternalKdl.g:619:7: ( (lv_optional_1_0= 'optional' ) )?
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0==31) ) {
                                alt8=1;
                            }
                            switch (alt8) {
                                case 1 :
                                    // InternalKdl.g:620:8: (lv_optional_1_0= 'optional' )
                                    {
                                    // InternalKdl.g:620:8: (lv_optional_1_0= 'optional' )
                                    // InternalKdl.g:621:9: lv_optional_1_0= 'optional'
                                    {
                                    lv_optional_1_0=(Token)match(input,31,FOLLOW_14); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_optional_1_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_0_0_1_0_0_0());
                                      								
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

                            // InternalKdl.g:633:7: ( (lv_imported_2_0= 'import' ) )
                            // InternalKdl.g:634:8: (lv_imported_2_0= 'import' )
                            {
                            // InternalKdl.g:634:8: (lv_imported_2_0= 'import' )
                            // InternalKdl.g:635:9: lv_imported_2_0= 'import'
                            {
                            lv_imported_2_0=(Token)match(input,32,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              									newLeafNode(lv_imported_2_0, grammarAccess.getActorDefinitionAccess().getImportedImportKeyword_0_0_1_0_1_0());
                              								
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

                            // InternalKdl.g:648:6: ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )?
                            int alt10=3;
                            int LA10_0 = input.LA(1);

                            if ( (LA10_0==33) ) {
                                alt10=1;
                            }
                            else if ( (LA10_0==RULE_INT) ) {
                                alt10=2;
                            }
                            switch (alt10) {
                                case 1 :
                                    // InternalKdl.g:649:7: ( (lv_multiple_3_0= 'multiple' ) )
                                    {
                                    // InternalKdl.g:649:7: ( (lv_multiple_3_0= 'multiple' ) )
                                    // InternalKdl.g:650:8: (lv_multiple_3_0= 'multiple' )
                                    {
                                    // InternalKdl.g:650:8: (lv_multiple_3_0= 'multiple' )
                                    // InternalKdl.g:651:9: lv_multiple_3_0= 'multiple'
                                    {
                                    lv_multiple_3_0=(Token)match(input,33,FOLLOW_13); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_multiple_3_0, grammarAccess.getActorDefinitionAccess().getMultipleMultipleKeyword_0_0_1_1_0_0());
                                      								
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
                                    // InternalKdl.g:664:7: ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? )
                                    {
                                    // InternalKdl.g:664:7: ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? )
                                    // InternalKdl.g:665:8: ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )?
                                    {
                                    // InternalKdl.g:665:8: ( (lv_arity_4_0= RULE_INT ) )
                                    // InternalKdl.g:666:9: (lv_arity_4_0= RULE_INT )
                                    {
                                    // InternalKdl.g:666:9: (lv_arity_4_0= RULE_INT )
                                    // InternalKdl.g:667:10: lv_arity_4_0= RULE_INT
                                    {
                                    lv_arity_4_0=(Token)match(input,RULE_INT,FOLLOW_16); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      										newLeafNode(lv_arity_4_0, grammarAccess.getActorDefinitionAccess().getArityINTTerminalRuleCall_0_0_1_1_1_0_0());
                                      									
                                    }
                                    if ( state.backtracking==0 ) {

                                      										if (current==null) {
                                      											current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      										}
                                      										setWithLastConsumed(
                                      											current,
                                      											"arity",
                                      											lv_arity_4_0,
                                      											"org.eclipse.xtext.common.Terminals.INT");
                                      									
                                    }

                                    }


                                    }

                                    // InternalKdl.g:683:8: ( (lv_minimum_5_0= '+' ) )?
                                    int alt9=2;
                                    int LA9_0 = input.LA(1);

                                    if ( (LA9_0==34) ) {
                                        alt9=1;
                                    }
                                    switch (alt9) {
                                        case 1 :
                                            // InternalKdl.g:684:9: (lv_minimum_5_0= '+' )
                                            {
                                            // InternalKdl.g:684:9: (lv_minimum_5_0= '+' )
                                            // InternalKdl.g:685:10: lv_minimum_5_0= '+'
                                            {
                                            lv_minimum_5_0=(Token)match(input,34,FOLLOW_13); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              										newLeafNode(lv_minimum_5_0, grammarAccess.getActorDefinitionAccess().getMinimumPlusSignKeyword_0_0_1_1_1_1_0());
                                              									
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

                    // InternalKdl.g:701:4: ( (lv_type_6_0= ruleACTOR ) )
                    // InternalKdl.g:702:5: (lv_type_6_0= ruleACTOR )
                    {
                    // InternalKdl.g:702:5: (lv_type_6_0= ruleACTOR )
                    // InternalKdl.g:703:6: lv_type_6_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_type_6_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_6_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:720:4: ( ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) ) )
                    // InternalKdl.g:721:5: ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) )
                    {
                    // InternalKdl.g:721:5: ( (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING ) )
                    // InternalKdl.g:722:6: (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING )
                    {
                    // InternalKdl.g:722:6: (lv_name_7_1= RULE_LOWERCASE_ID | lv_name_7_2= RULE_LOWERCASE_DASHID | lv_name_7_3= RULE_STRING )
                    int alt12=3;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt12=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt12=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt12=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }

                    switch (alt12) {
                        case 1 :
                            // InternalKdl.g:723:7: lv_name_7_1= RULE_LOWERCASE_ID
                            {
                            lv_name_7_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_7_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_0_2_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_7_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:738:7: lv_name_7_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_7_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_7_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_0_2_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_7_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:753:7: lv_name_7_3= RULE_STRING
                            {
                            lv_name_7_3=(Token)match(input,RULE_STRING,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_7_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_0_2_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_7_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:770:4: (otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )* )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==35) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalKdl.g:771:5: otherlv_8= 'for' ( (lv_targets_9_0= ruleTARGET ) ) (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )*
                            {
                            otherlv_8=(Token)match(input,35,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getActorDefinitionAccess().getForKeyword_0_3_0());
                              				
                            }
                            // InternalKdl.g:775:5: ( (lv_targets_9_0= ruleTARGET ) )
                            // InternalKdl.g:776:6: (lv_targets_9_0= ruleTARGET )
                            {
                            // InternalKdl.g:776:6: (lv_targets_9_0= ruleTARGET )
                            // InternalKdl.g:777:7: lv_targets_9_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_3_1_0());
                              						
                            }
                            pushFollow(FOLLOW_20);
                            lv_targets_9_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_9_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:794:5: (otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) ) )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==28) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // InternalKdl.g:795:6: otherlv_10= ',' ( (lv_targets_11_0= ruleTARGET ) )
                            	    {
                            	    otherlv_10=(Token)match(input,28,FOLLOW_19); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_10, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_3_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:799:6: ( (lv_targets_11_0= ruleTARGET ) )
                            	    // InternalKdl.g:800:7: (lv_targets_11_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:800:7: (lv_targets_11_0= ruleTARGET )
                            	    // InternalKdl.g:801:8: lv_targets_11_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_3_2_1_0());
                            	      							
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


                            	    }
                            	    break;

                            	default :
                            	    break loop13;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:820:4: ( (lv_docstring_12_0= RULE_STRING ) )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==RULE_STRING) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKdl.g:821:5: (lv_docstring_12_0= RULE_STRING )
                            {
                            // InternalKdl.g:821:5: (lv_docstring_12_0= RULE_STRING )
                            // InternalKdl.g:822:6: lv_docstring_12_0= RULE_STRING
                            {
                            lv_docstring_12_0=(Token)match(input,RULE_STRING,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_12_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_0_4_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_12_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:838:4: (otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==36) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKdl.g:839:5: otherlv_13= '{' ( (lv_body_14_0= ruleDataflowBody ) ) otherlv_15= '}'
                            {
                            otherlv_13=(Token)match(input,36,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_13, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_0_5_0());
                              				
                            }
                            // InternalKdl.g:843:5: ( (lv_body_14_0= ruleDataflowBody ) )
                            // InternalKdl.g:844:6: (lv_body_14_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:844:6: (lv_body_14_0= ruleDataflowBody )
                            // InternalKdl.g:845:7: lv_body_14_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_0_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_body_14_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_14_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_15=(Token)match(input,37,FOLLOW_24); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_15, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_0_5_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:867:4: (otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==38) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:868:5: otherlv_16= 'as' ( (lv_localName_17_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_16=(Token)match(input,38,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_16, grammarAccess.getActorDefinitionAccess().getAsKeyword_0_6_0());
                              				
                            }
                            // InternalKdl.g:872:5: ( (lv_localName_17_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:873:6: (lv_localName_17_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:873:6: (lv_localName_17_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:874:7: lv_localName_17_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_17_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_17_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_0_6_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_17_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:891:4: (otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )* )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==39) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKdl.g:892:5: otherlv_18= 'over' ( (lv_coverage_19_0= ruleFunction ) ) (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )*
                            {
                            otherlv_18=(Token)match(input,39,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_18, grammarAccess.getActorDefinitionAccess().getOverKeyword_0_7_0());
                              				
                            }
                            // InternalKdl.g:896:5: ( (lv_coverage_19_0= ruleFunction ) )
                            // InternalKdl.g:897:6: (lv_coverage_19_0= ruleFunction )
                            {
                            // InternalKdl.g:897:6: (lv_coverage_19_0= ruleFunction )
                            // InternalKdl.g:898:7: lv_coverage_19_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_7_1_0());
                              						
                            }
                            pushFollow(FOLLOW_26);
                            lv_coverage_19_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_19_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:915:5: (otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) ) )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==28) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // InternalKdl.g:916:6: otherlv_20= ',' ( (lv_coverage_21_0= ruleFunction ) )
                            	    {
                            	    otherlv_20=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_20, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_7_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:920:6: ( (lv_coverage_21_0= ruleFunction ) )
                            	    // InternalKdl.g:921:7: (lv_coverage_21_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:921:7: (lv_coverage_21_0= ruleFunction )
                            	    // InternalKdl.g:922:8: lv_coverage_21_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_7_2_1_0());
                            	      							
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


                            	    }
                            	    break;

                            	default :
                            	    break loop18;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:943:3: ( ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )? )
                    {
                    // InternalKdl.g:943:3: ( ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )? )
                    // InternalKdl.g:944:4: ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) ) ( (lv_parameter_24_0= 'input' ) ) ( (lv_name_25_0= RULE_LOWERCASE_ID ) ) (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_30_0= RULE_STRING ) ) (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )? (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )?
                    {
                    // InternalKdl.g:944:4: ( ( (lv_optional_22_0= 'optional' ) ) | ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) ) )
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==31) ) {
                        alt21=1;
                    }
                    else if ( ((LA21_0>=40 && LA21_0<=44)) ) {
                        alt21=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 21, 0, input);

                        throw nvae;
                    }
                    switch (alt21) {
                        case 1 :
                            // InternalKdl.g:945:5: ( (lv_optional_22_0= 'optional' ) )
                            {
                            // InternalKdl.g:945:5: ( (lv_optional_22_0= 'optional' ) )
                            // InternalKdl.g:946:6: (lv_optional_22_0= 'optional' )
                            {
                            // InternalKdl.g:946:6: (lv_optional_22_0= 'optional' )
                            // InternalKdl.g:947:7: lv_optional_22_0= 'optional'
                            {
                            lv_optional_22_0=(Token)match(input,31,FOLLOW_27); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_optional_22_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_0_0_0());
                              						
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
                            // InternalKdl.g:960:5: ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) )
                            {
                            // InternalKdl.g:960:5: ( ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) ) )
                            // InternalKdl.g:961:6: ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) )
                            {
                            // InternalKdl.g:961:6: ( (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' ) )
                            // InternalKdl.g:962:7: (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' )
                            {
                            // InternalKdl.g:962:7: (lv_type_23_1= 'number' | lv_type_23_2= 'boolean' | lv_type_23_3= 'text' | lv_type_23_4= 'list' | lv_type_23_5= 'enum' )
                            int alt20=5;
                            switch ( input.LA(1) ) {
                            case 40:
                                {
                                alt20=1;
                                }
                                break;
                            case 41:
                                {
                                alt20=2;
                                }
                                break;
                            case 42:
                                {
                                alt20=3;
                                }
                                break;
                            case 43:
                                {
                                alt20=4;
                                }
                                break;
                            case 44:
                                {
                                alt20=5;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 20, 0, input);

                                throw nvae;
                            }

                            switch (alt20) {
                                case 1 :
                                    // InternalKdl.g:963:8: lv_type_23_1= 'number'
                                    {
                                    lv_type_23_1=(Token)match(input,40,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_23_1, grammarAccess.getActorDefinitionAccess().getTypeNumberKeyword_1_0_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_23_1, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:974:8: lv_type_23_2= 'boolean'
                                    {
                                    lv_type_23_2=(Token)match(input,41,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_23_2, grammarAccess.getActorDefinitionAccess().getTypeBooleanKeyword_1_0_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_23_2, null);
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:985:8: lv_type_23_3= 'text'
                                    {
                                    lv_type_23_3=(Token)match(input,42,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_23_3, grammarAccess.getActorDefinitionAccess().getTypeTextKeyword_1_0_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_23_3, null);
                                      							
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:996:8: lv_type_23_4= 'list'
                                    {
                                    lv_type_23_4=(Token)match(input,43,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_23_4, grammarAccess.getActorDefinitionAccess().getTypeListKeyword_1_0_1_0_3());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_23_4, null);
                                      							
                                    }

                                    }
                                    break;
                                case 5 :
                                    // InternalKdl.g:1007:8: lv_type_23_5= 'enum'
                                    {
                                    lv_type_23_5=(Token)match(input,44,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_23_5, grammarAccess.getActorDefinitionAccess().getTypeEnumKeyword_1_0_1_0_4());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_23_5, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1021:4: ( (lv_parameter_24_0= 'input' ) )
                    // InternalKdl.g:1022:5: (lv_parameter_24_0= 'input' )
                    {
                    // InternalKdl.g:1022:5: (lv_parameter_24_0= 'input' )
                    // InternalKdl.g:1023:6: lv_parameter_24_0= 'input'
                    {
                    lv_parameter_24_0=(Token)match(input,45,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_parameter_24_0, grammarAccess.getActorDefinitionAccess().getParameterInputKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(current, "parameter", true, "input");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1035:4: ( (lv_name_25_0= RULE_LOWERCASE_ID ) )
                    // InternalKdl.g:1036:5: (lv_name_25_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKdl.g:1036:5: (lv_name_25_0= RULE_LOWERCASE_ID )
                    // InternalKdl.g:1037:6: lv_name_25_0= RULE_LOWERCASE_ID
                    {
                    lv_name_25_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_25_0, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_25_0,
                      							"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1053:4: (otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )* )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==46) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKdl.g:1054:5: otherlv_26= 'values' ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) ) (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )*
                            {
                            otherlv_26=(Token)match(input,46,FOLLOW_29); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_26, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_3_0());
                              				
                            }
                            // InternalKdl.g:1058:5: ( (lv_enumValues_27_0= RULE_UPPERCASE_ID ) )
                            // InternalKdl.g:1059:6: (lv_enumValues_27_0= RULE_UPPERCASE_ID )
                            {
                            // InternalKdl.g:1059:6: (lv_enumValues_27_0= RULE_UPPERCASE_ID )
                            // InternalKdl.g:1060:7: lv_enumValues_27_0= RULE_UPPERCASE_ID
                            {
                            lv_enumValues_27_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_enumValues_27_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							addWithLastConsumed(
                              								current,
                              								"enumValues",
                              								lv_enumValues_27_0,
                              								"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1076:5: (otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) ) )*
                            loop22:
                            do {
                                int alt22=2;
                                int LA22_0 = input.LA(1);

                                if ( (LA22_0==28) ) {
                                    alt22=1;
                                }


                                switch (alt22) {
                            	case 1 :
                            	    // InternalKdl.g:1077:6: otherlv_28= ',' ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) )
                            	    {
                            	    otherlv_28=(Token)match(input,28,FOLLOW_29); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_28, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_3_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1081:6: ( (lv_enumValues_29_0= RULE_UPPERCASE_ID ) )
                            	    // InternalKdl.g:1082:7: (lv_enumValues_29_0= RULE_UPPERCASE_ID )
                            	    {
                            	    // InternalKdl.g:1082:7: (lv_enumValues_29_0= RULE_UPPERCASE_ID )
                            	    // InternalKdl.g:1083:8: lv_enumValues_29_0= RULE_UPPERCASE_ID
                            	    {
                            	    lv_enumValues_29_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_30); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								newLeafNode(lv_enumValues_29_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_2_1_0());
                            	      							
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


                            	    }
                            	    break;

                            	default :
                            	    break loop22;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:1101:4: ( (lv_docstring_30_0= RULE_STRING ) )
                    // InternalKdl.g:1102:5: (lv_docstring_30_0= RULE_STRING )
                    {
                    // InternalKdl.g:1102:5: (lv_docstring_30_0= RULE_STRING )
                    // InternalKdl.g:1103:6: lv_docstring_30_0= RULE_STRING
                    {
                    lv_docstring_30_0=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_docstring_30_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_4_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"docstring",
                      							lv_docstring_30_0,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1119:4: (otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) ) )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==47) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalKdl.g:1120:5: otherlv_31= 'default' ( (lv_default_32_0= ruleValue ) )
                            {
                            otherlv_31=(Token)match(input,47,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_31, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_5_0());
                              				
                            }
                            // InternalKdl.g:1124:5: ( (lv_default_32_0= ruleValue ) )
                            // InternalKdl.g:1125:6: (lv_default_32_0= ruleValue )
                            {
                            // InternalKdl.g:1125:6: (lv_default_32_0= ruleValue )
                            // InternalKdl.g:1126:7: lv_default_32_0= ruleValue
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_33);
                            lv_default_32_0=ruleValue();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"default",
                              								lv_default_32_0,
                              								"org.integratedmodelling.kdl.Kdl.Value");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1144:4: (otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}' )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==36) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // InternalKdl.g:1145:5: otherlv_33= '{' ( (lv_body_34_0= ruleDataflowBody ) ) otherlv_35= '}'
                            {
                            otherlv_33=(Token)match(input,36,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_33, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_6_0());
                              				
                            }
                            // InternalKdl.g:1149:5: ( (lv_body_34_0= ruleDataflowBody ) )
                            // InternalKdl.g:1150:6: (lv_body_34_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1150:6: (lv_body_34_0= ruleDataflowBody )
                            // InternalKdl.g:1151:7: lv_body_34_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_6_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_body_34_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_34_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_35=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_35, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_6_2());
                              				
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
    // InternalKdl.g:1178:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()
        	);

        try {
            // InternalKdl.g:1182:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1183:2: iv_ruleDataflowBody= ruleDataflowBody EOF
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
    // InternalKdl.g:1192:1: ruleDataflowBody returns [EObject current=null] : ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
    public final EObject ruleDataflowBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject lv_newObservation_3_0 = null;

        EObject lv_urnObservation_4_0 = null;

        EObject lv_dataflows_5_0 = null;

        AntlrDatatypeRuleToken lv_geometry_8_0 = null;

        EObject lv_units_10_0 = null;

        EObject lv_computations_11_0 = null;

        EObject lv_semantics_13_0 = null;

        EObject lv_metadata_15_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_17_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()
        	);

        try {
            // InternalKdl.g:1201:2: ( ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1202:2: ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1202:2: ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1203:3: () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1203:3: ()
            // InternalKdl.g:1204:4: 
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

            // InternalKdl.g:1213:3: (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==48) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalKdl.g:1214:4: otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) )
                    {
                    otherlv_1=(Token)match(input,48,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDataflowBodyAccess().getObserveKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:1218:4: ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==49) ) {
                        alt27=1;
                    }
                    else if ( ((LA27_0>=RULE_STRING && LA27_0<=RULE_LOWERCASE_ID)||LA27_0==RULE_LOWERCASE_DASHID||LA27_0==RULE_CAMELCASE_ID||LA27_0==99) ) {
                        alt27=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalKdl.g:1219:5: (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) )
                            {
                            // InternalKdl.g:1219:5: (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) )
                            // InternalKdl.g:1220:6: otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) )
                            {
                            otherlv_2=(Token)match(input,49,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getDataflowBodyAccess().getNewKeyword_1_1_0_0());
                              					
                            }
                            // InternalKdl.g:1224:6: ( (lv_newObservation_3_0= ruleObservableSemantics ) )
                            // InternalKdl.g:1225:7: (lv_newObservation_3_0= ruleObservableSemantics )
                            {
                            // InternalKdl.g:1225:7: (lv_newObservation_3_0= ruleObservableSemantics )
                            // InternalKdl.g:1226:8: lv_newObservation_3_0= ruleObservableSemantics
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getDataflowBodyAccess().getNewObservationObservableSemanticsParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_36);
                            lv_newObservation_3_0=ruleObservableSemantics();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
                              								}
                              								set(
                              									current,
                              									"newObservation",
                              									lv_newObservation_3_0,
                              									"org.integratedmodelling.kdl.Kdl.ObservableSemantics");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1245:5: ( (lv_urnObservation_4_0= ruleUrn ) )
                            {
                            // InternalKdl.g:1245:5: ( (lv_urnObservation_4_0= ruleUrn ) )
                            // InternalKdl.g:1246:6: (lv_urnObservation_4_0= ruleUrn )
                            {
                            // InternalKdl.g:1246:6: (lv_urnObservation_4_0= ruleUrn )
                            // InternalKdl.g:1247:7: lv_urnObservation_4_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getDataflowBodyAccess().getUrnObservationUrnParserRuleCall_1_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_36);
                            lv_urnObservation_4_0=ruleUrn();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
                              							}
                              							set(
                              								current,
                              								"urnObservation",
                              								lv_urnObservation_4_0,
                              								"org.integratedmodelling.kdl.Kdl.Urn");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKdl.g:1266:3: ( (lv_dataflows_5_0= ruleActorDefinition ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=30 && LA29_0<=32)||(LA29_0>=40 && LA29_0<=44)||LA29_0==72||(LA29_0>=78 && LA29_0<=87)) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalKdl.g:1267:4: (lv_dataflows_5_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1267:4: (lv_dataflows_5_0= ruleActorDefinition )
            	    // InternalKdl.g:1268:5: lv_dataflows_5_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDataflowBodyAccess().getDataflowsActorDefinitionParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_36);
            	    lv_dataflows_5_0=ruleActorDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      					}
            	      					add(
            	      						current,
            	      						"dataflows",
            	      						lv_dataflows_5_0,
            	      						"org.integratedmodelling.kdl.Kdl.ActorDefinition");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            // InternalKdl.g:1285:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1286:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1286:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1287:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());
            // InternalKdl.g:1290:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1291:6: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1291:6: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+
            int cnt33=0;
            loop33:
            do {
                int alt33=6;
                alt33 = dfa33.predict(input);
                switch (alt33) {
            	case 1 :
            	    // InternalKdl.g:1292:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1292:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1293:5: {...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0)");
            	    }
            	    // InternalKdl.g:1293:109: ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1294:6: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0);
            	    // InternalKdl.g:1297:9: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1297:10: {...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1297:19: (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1297:20: otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) )
            	    {
            	    otherlv_7=(Token)match(input,50,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_3_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1301:9: ( (lv_geometry_8_0= ruleGeometry ) )
            	    // InternalKdl.g:1302:10: (lv_geometry_8_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1302:10: (lv_geometry_8_0= ruleGeometry )
            	    // InternalKdl.g:1303:11: lv_geometry_8_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_3_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_37);
            	    lv_geometry_8_0=ruleGeometry();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      											}
            	      											set(
            	      												current,
            	      												"geometry",
            	      												lv_geometry_8_0,
            	      												"org.integratedmodelling.kdl.Kdl.Geometry");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKdl.g:1326:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1326:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
            	    // InternalKdl.g:1327:5: {...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1)");
            	    }
            	    // InternalKdl.g:1327:109: ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
            	    // InternalKdl.g:1328:6: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1);
            	    // InternalKdl.g:1331:9: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
            	    // InternalKdl.g:1331:10: {...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1331:19: (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
            	    // InternalKdl.g:1331:20: otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) )
            	    {
            	    otherlv_9=(Token)match(input,51,FOLLOW_38); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_9, grammarAccess.getDataflowBodyAccess().getUnitsKeyword_3_1_0());
            	      								
            	    }
            	    // InternalKdl.g:1335:9: ( (lv_units_10_0= ruleUnit ) )
            	    // InternalKdl.g:1336:10: (lv_units_10_0= ruleUnit )
            	    {
            	    // InternalKdl.g:1336:10: (lv_units_10_0= ruleUnit )
            	    // InternalKdl.g:1337:11: lv_units_10_0= ruleUnit
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getUnitsUnitParserRuleCall_3_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_37);
            	    lv_units_10_0=ruleUnit();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      											}
            	      											set(
            	      												current,
            	      												"units",
            	      												lv_units_10_0,
            	      												"org.integratedmodelling.kdl.Kdl.Unit");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKdl.g:1360:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
            	    {
            	    // InternalKdl.g:1360:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
            	    // InternalKdl.g:1361:5: {...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2)");
            	    }
            	    // InternalKdl.g:1361:109: ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
            	    // InternalKdl.g:1362:6: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2);
            	    // InternalKdl.g:1365:9: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
            	    int cnt30=0;
            	    loop30:
            	    do {
            	        int alt30=2;
            	        int LA30_0 = input.LA(1);

            	        if ( (LA30_0==55) ) {
            	            int LA30_2 = input.LA(2);

            	            if ( ((synpred49_InternalKdl()&&(true))) ) {
            	                alt30=1;
            	            }


            	        }


            	        switch (alt30) {
            	    	case 1 :
            	    	    // InternalKdl.g:1365:10: {...}? => ( (lv_computations_11_0= ruleComputation ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    	    }
            	    	    // InternalKdl.g:1365:19: ( (lv_computations_11_0= ruleComputation ) )
            	    	    // InternalKdl.g:1365:20: (lv_computations_11_0= ruleComputation )
            	    	    {
            	    	    // InternalKdl.g:1365:20: (lv_computations_11_0= ruleComputation )
            	    	    // InternalKdl.g:1366:10: lv_computations_11_0= ruleComputation
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_3_2_0());
            	    	      									
            	    	    }
            	    	    pushFollow(FOLLOW_37);
            	    	    lv_computations_11_0=ruleComputation();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										if (current==null) {
            	    	      											current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	    	      										}
            	    	      										add(
            	    	      											current,
            	    	      											"computations",
            	    	      											lv_computations_11_0,
            	    	      											"org.integratedmodelling.kdl.Kdl.Computation");
            	    	      										afterParserOrEnumRuleCall();
            	    	      									
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt30 >= 1 ) break loop30;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(30, input);
            	                throw eee;
            	        }
            	        cnt30++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKdl.g:1388:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1388:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
            	    // InternalKdl.g:1389:5: {...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3)");
            	    }
            	    // InternalKdl.g:1389:109: ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
            	    // InternalKdl.g:1390:6: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3);
            	    // InternalKdl.g:1393:9: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
            	    // InternalKdl.g:1393:10: {...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1393:19: (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
            	    // InternalKdl.g:1393:20: otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) )
            	    {
            	    otherlv_12=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_12, grammarAccess.getDataflowBodyAccess().getSemanticsKeyword_3_3_0());
            	      								
            	    }
            	    // InternalKdl.g:1397:9: ( (lv_semantics_13_0= ruleObservableSemantics ) )
            	    // InternalKdl.g:1398:10: (lv_semantics_13_0= ruleObservableSemantics )
            	    {
            	    // InternalKdl.g:1398:10: (lv_semantics_13_0= ruleObservableSemantics )
            	    // InternalKdl.g:1399:11: lv_semantics_13_0= ruleObservableSemantics
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getSemanticsObservableSemanticsParserRuleCall_3_3_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_37);
            	    lv_semantics_13_0=ruleObservableSemantics();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	      											}
            	      											set(
            	      												current,
            	      												"semantics",
            	      												lv_semantics_13_0,
            	      												"org.integratedmodelling.kdl.Kdl.ObservableSemantics");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKdl.g:1422:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1422:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
            	    // InternalKdl.g:1423:5: {...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4)");
            	    }
            	    // InternalKdl.g:1423:109: ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
            	    // InternalKdl.g:1424:6: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4);
            	    // InternalKdl.g:1427:9: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
            	    // InternalKdl.g:1427:10: {...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1427:19: ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
            	    // InternalKdl.g:1427:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
            	    {
            	    // InternalKdl.g:1427:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )?
            	    int alt31=2;
            	    int LA31_0 = input.LA(1);

            	    if ( (LA31_0==53) ) {
            	        int LA31_1 = input.LA(2);

            	        if ( (synpred52_InternalKdl()) ) {
            	            alt31=1;
            	        }
            	    }
            	    switch (alt31) {
            	        case 1 :
            	            // InternalKdl.g:1428:10: otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) )
            	            {
            	            otherlv_14=(Token)match(input,53,FOLLOW_39); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_14, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_3_4_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1432:10: ( (lv_metadata_15_0= ruleMetadata ) )
            	            // InternalKdl.g:1433:11: (lv_metadata_15_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1433:11: (lv_metadata_15_0= ruleMetadata )
            	            // InternalKdl.g:1434:12: lv_metadata_15_0= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_3_4_0_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_37);
            	            lv_metadata_15_0=ruleMetadata();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	              												}
            	              												set(
            	              													current,
            	              													"metadata",
            	              													lv_metadata_15_0,
            	              													"org.integratedmodelling.kdl.Kdl.Metadata");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }

            	    // InternalKdl.g:1452:9: (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
            	    int alt32=2;
            	    int LA32_0 = input.LA(1);

            	    if ( (LA32_0==54) ) {
            	        int LA32_1 = input.LA(2);

            	        if ( (synpred53_InternalKdl()) ) {
            	            alt32=1;
            	        }
            	    }
            	    switch (alt32) {
            	        case 1 :
            	            // InternalKdl.g:1453:10: otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) )
            	            {
            	            otherlv_16=(Token)match(input,54,FOLLOW_3); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_16, grammarAccess.getDataflowBodyAccess().getClassKeyword_3_4_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1457:10: ( (lv_javaClass_17_0= ruleJavaClass ) )
            	            // InternalKdl.g:1458:11: (lv_javaClass_17_0= ruleJavaClass )
            	            {
            	            // InternalKdl.g:1458:11: (lv_javaClass_17_0= ruleJavaClass )
            	            // InternalKdl.g:1459:12: lv_javaClass_17_0= ruleJavaClass
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_3_4_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_37);
            	            lv_javaClass_17_0=ruleJavaClass();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getDataflowBodyRule());
            	              												}
            	              												set(
            	              													current,
            	              													"javaClass",
            	              													lv_javaClass_17_0,
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

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt33 >= 1 ) break loop33;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(33, input);
                        throw eee;
                }
                cnt33++;
            } while (true);

            if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()) ) {
                if (state.backtracking>0) {state.failed=true; return current;}
                throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3())");
            }

            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

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
    // InternalKdl.g:1498:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1498:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1499:2: iv_ruleComputation= ruleComputation EOF
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
    // InternalKdl.g:1505:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1511:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1512:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1512:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1513:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,55,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1517:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1518:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1518:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1519:5: lv_functions_1_0= ruleFunction
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

            // InternalKdl.g:1536:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==28) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalKdl.g:1537:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1541:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1542:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1542:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1543:6: lv_functions_3_0= ruleFunction
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
            	    break loop34;
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
    // InternalKdl.g:1565:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1565:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1566:2: iv_ruleGeometry= ruleGeometry EOF
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
    // InternalKdl.g:1572:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1578:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1579:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1579:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==56) ) {
                alt36=1;
            }
            else if ( (LA36_0==RULE_SHAPE) ) {
                alt36=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // InternalKdl.g:1580:3: kw= '*'
                    {
                    kw=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1586:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1586:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1587:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:1594:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( (LA35_0==28) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // InternalKdl.g:1595:5: kw= ',' this_SHAPE_3= RULE_SHAPE
                    	    {
                    	    kw=(Token)match(input,28,FOLLOW_40); if (state.failed) return current;
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
                    	    break loop35;
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


    // $ANTLR start "entryRuleConceptDeclaration"
    // InternalKdl.g:1613:1: entryRuleConceptDeclaration returns [EObject current=null] : iv_ruleConceptDeclaration= ruleConceptDeclaration EOF ;
    public final EObject entryRuleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConceptDeclaration = null;


        try {
            // InternalKdl.g:1613:59: (iv_ruleConceptDeclaration= ruleConceptDeclaration EOF )
            // InternalKdl.g:1614:2: iv_ruleConceptDeclaration= ruleConceptDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConceptDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleConceptDeclaration=ruleConceptDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleConceptDeclaration; 
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
    // $ANTLR end "entryRuleConceptDeclaration"


    // $ANTLR start "ruleConceptDeclaration"
    // InternalKdl.g:1620:1: ruleConceptDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? ) ;
    public final EObject ruleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_main_1_0 = null;

        EObject lv_inherency_3_0 = null;

        EObject lv_context_5_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1626:2: ( ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? ) )
            // InternalKdl.g:1627:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? )
            {
            // InternalKdl.g:1627:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? )
            // InternalKdl.g:1628:3: ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )?
            {
            // InternalKdl.g:1628:3: ( (lv_name_0_0= RULE_STRING ) )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_STRING) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKdl.g:1629:4: (lv_name_0_0= RULE_STRING )
                    {
                    // InternalKdl.g:1629:4: (lv_name_0_0= RULE_STRING )
                    // InternalKdl.g:1630:5: lv_name_0_0= RULE_STRING
                    {
                    lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_name_0_0, grammarAccess.getConceptDeclarationAccess().getNameSTRINGTerminalRuleCall_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getConceptDeclarationRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"name",
                      						lv_name_0_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKdl.g:1646:3: ( (lv_main_1_0= ruleConcept ) )+
            int cnt38=0;
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==72) ) {
                    int LA38_2 = input.LA(2);

                    if ( (LA38_2==57) ) {
                        alt38=1;
                    }


                }
                else if ( (LA38_0==RULE_LOWERCASE_ID||LA38_0==RULE_CAMELCASE_ID||LA38_0==59||(LA38_0>=62 && LA38_0<=64)||(LA38_0>=67 && LA38_0<=69)||LA38_0==71||(LA38_0>=73 && LA38_0<=74)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalKdl.g:1647:4: (lv_main_1_0= ruleConcept )
            	    {
            	    // InternalKdl.g:1647:4: (lv_main_1_0= ruleConcept )
            	    // InternalKdl.g:1648:5: lv_main_1_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getConceptDeclarationAccess().getMainConceptParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_main_1_0=ruleConcept();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getConceptDeclarationRule());
            	      					}
            	      					add(
            	      						current,
            	      						"main",
            	      						lv_main_1_0,
            	      						"org.integratedmodelling.kdl.Kdl.Concept");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt38 >= 1 ) break loop38;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(38, input);
                        throw eee;
                }
                cnt38++;
            } while (true);

            // InternalKdl.g:1665:3: (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==57) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalKdl.g:1666:4: otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) )
                    {
                    otherlv_2=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getConceptDeclarationAccess().getOfKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:1670:4: ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:1671:5: (lv_inherency_3_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:1671:5: (lv_inherency_3_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:1672:6: lv_inherency_3_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptDeclarationAccess().getInherencySimpleConceptDeclarationParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_42);
                    lv_inherency_3_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"inherency",
                      							lv_inherency_3_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKdl.g:1690:3: (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==58) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalKdl.g:1691:4: otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) )
                    {
                    otherlv_4=(Token)match(input,58,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getConceptDeclarationAccess().getWithinKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:1695:4: ( (lv_context_5_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:1696:5: (lv_context_5_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:1696:5: (lv_context_5_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:1697:6: lv_context_5_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptDeclarationAccess().getContextSimpleConceptDeclarationParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_context_5_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"context",
                      							lv_context_5_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
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
    // $ANTLR end "ruleConceptDeclaration"


    // $ANTLR start "entryRuleConceptReference"
    // InternalKdl.g:1719:1: entryRuleConceptReference returns [String current=null] : iv_ruleConceptReference= ruleConceptReference EOF ;
    public final String entryRuleConceptReference() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleConceptReference = null;


        try {
            // InternalKdl.g:1719:56: (iv_ruleConceptReference= ruleConceptReference EOF )
            // InternalKdl.g:1720:2: iv_ruleConceptReference= ruleConceptReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConceptReferenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleConceptReference=ruleConceptReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleConceptReference.getText(); 
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
    // $ANTLR end "entryRuleConceptReference"


    // $ANTLR start "ruleConceptReference"
    // InternalKdl.g:1726:1: ruleConceptReference returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId ) ;
    public final AntlrDatatypeRuleToken ruleConceptReference() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_CAMELCASE_ID_0=null;
        AntlrDatatypeRuleToken this_NamespaceId_1 = null;



        	enterRule();

        try {
            // InternalKdl.g:1732:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId ) )
            // InternalKdl.g:1733:2: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId )
            {
            // InternalKdl.g:1733:2: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_CAMELCASE_ID) ) {
                alt41=1;
            }
            else if ( (LA41_0==RULE_LOWERCASE_ID) ) {
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
                    // InternalKdl.g:1734:3: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_CAMELCASE_ID_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getConceptReferenceAccess().getCAMELCASE_IDTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1742:3: this_NamespaceId_1= ruleNamespaceId
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getConceptReferenceAccess().getNamespaceIdParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_NamespaceId_1=ruleNamespaceId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_NamespaceId_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
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
    // $ANTLR end "ruleConceptReference"


    // $ANTLR start "entryRuleConcept"
    // InternalKdl.g:1756:1: entryRuleConcept returns [EObject current=null] : iv_ruleConcept= ruleConcept EOF ;
    public final EObject entryRuleConcept() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConcept = null;


        try {
            // InternalKdl.g:1756:48: (iv_ruleConcept= ruleConcept EOF )
            // InternalKdl.g:1757:2: iv_ruleConcept= ruleConcept EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConceptRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleConcept=ruleConcept();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleConcept; 
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
    // $ANTLR end "entryRuleConcept"


    // $ANTLR start "ruleConcept"
    // InternalKdl.g:1763:1: ruleConcept returns [EObject current=null] : ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) ) ;
    public final EObject ruleConcept() throws RecognitionException {
        EObject current = null;

        Token lv_negated_0_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_stringIdentifier_4_1=null;
        Token lv_stringIdentifier_4_2=null;
        Token lv_intIdentifier_5_0=null;
        Token otherlv_6=null;
        Token lv_authority_7_1=null;
        Token lv_authority_7_2=null;
        Token lv_presence_8_0=null;
        Token otherlv_9=null;
        Token lv_count_11_0=null;
        Token otherlv_12=null;
        Token lv_distance_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_probability_18_0=null;
        Token otherlv_19=null;
        Token lv_uncertainty_21_0=null;
        Token otherlv_22=null;
        Token lv_proportion_24_0=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token lv_ratio_29_0=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token lv_value_34_0=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token lv_occurrence_39_0=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token otherlv_44=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_concept_10_0 = null;

        EObject lv_concept_13_0 = null;

        EObject lv_concept_17_0 = null;

        EObject lv_concept_20_0 = null;

        EObject lv_concept_23_0 = null;

        EObject lv_concept_26_0 = null;

        EObject lv_other_28_0 = null;

        EObject lv_concept_31_0 = null;

        EObject lv_other_33_0 = null;

        EObject lv_concept_36_0 = null;

        EObject lv_other_38_0 = null;

        EObject lv_concept_41_0 = null;

        EObject lv_declaration_43_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1769:2: ( ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) ) )
            // InternalKdl.g:1770:2: ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) )
            {
            // InternalKdl.g:1770:2: ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) )
            int alt50=11;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
            case RULE_CAMELCASE_ID:
            case 59:
                {
                alt50=1;
                }
                break;
            case 62:
                {
                alt50=2;
                }
                break;
            case 63:
                {
                alt50=3;
                }
                break;
            case 64:
                {
                alt50=4;
                }
                break;
            case 67:
                {
                alt50=5;
                }
                break;
            case 68:
                {
                alt50=6;
                }
                break;
            case 69:
                {
                alt50=7;
                }
                break;
            case 71:
                {
                alt50=8;
                }
                break;
            case 72:
                {
                alt50=9;
                }
                break;
            case 73:
                {
                alt50=10;
                }
                break;
            case 74:
                {
                alt50=11;
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
                    // InternalKdl.g:1771:3: ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? )
                    {
                    // InternalKdl.g:1771:3: ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? )
                    // InternalKdl.g:1772:4: ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )?
                    {
                    // InternalKdl.g:1772:4: ( (lv_negated_0_0= 'not' ) )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==59) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // InternalKdl.g:1773:5: (lv_negated_0_0= 'not' )
                            {
                            // InternalKdl.g:1773:5: (lv_negated_0_0= 'not' )
                            // InternalKdl.g:1774:6: lv_negated_0_0= 'not'
                            {
                            lv_negated_0_0=(Token)match(input,59,FOLLOW_43); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_negated_0_0, grammarAccess.getConceptAccess().getNegatedNotKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getConceptRule());
                              						}
                              						setWithLastConsumed(current, "negated", true, "not");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1786:4: ( (lv_name_1_0= ruleConceptReference ) )
                    // InternalKdl.g:1787:5: (lv_name_1_0= ruleConceptReference )
                    {
                    // InternalKdl.g:1787:5: (lv_name_1_0= ruleConceptReference )
                    // InternalKdl.g:1788:6: lv_name_1_0= ruleConceptReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getNameConceptReferenceParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
                    lv_name_1_0=ruleConceptReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_0,
                      							"org.integratedmodelling.kdl.Kdl.ConceptReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1805:4: (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==60) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // InternalKdl.g:1806:5: otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) )
                            {
                            otherlv_2=(Token)match(input,60,FOLLOW_45); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getConceptAccess().getIdentifiedKeyword_0_2_0());
                              				
                            }
                            otherlv_3=(Token)match(input,38,FOLLOW_46); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_3, grammarAccess.getConceptAccess().getAsKeyword_0_2_1());
                              				
                            }
                            // InternalKdl.g:1814:5: ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) )
                            int alt44=2;
                            int LA44_0 = input.LA(1);

                            if ( (LA44_0==RULE_STRING||LA44_0==RULE_ID) ) {
                                alt44=1;
                            }
                            else if ( (LA44_0==RULE_INT) ) {
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
                                    // InternalKdl.g:1815:6: ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) )
                                    {
                                    // InternalKdl.g:1815:6: ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) )
                                    // InternalKdl.g:1816:7: ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) )
                                    {
                                    // InternalKdl.g:1816:7: ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) )
                                    // InternalKdl.g:1817:8: (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING )
                                    {
                                    // InternalKdl.g:1817:8: (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING )
                                    int alt43=2;
                                    int LA43_0 = input.LA(1);

                                    if ( (LA43_0==RULE_ID) ) {
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
                                            // InternalKdl.g:1818:9: lv_stringIdentifier_4_1= RULE_ID
                                            {
                                            lv_stringIdentifier_4_1=(Token)match(input,RULE_ID,FOLLOW_47); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              									newLeafNode(lv_stringIdentifier_4_1, grammarAccess.getConceptAccess().getStringIdentifierIDTerminalRuleCall_0_2_2_0_0_0());
                                              								
                                            }
                                            if ( state.backtracking==0 ) {

                                              									if (current==null) {
                                              										current = createModelElement(grammarAccess.getConceptRule());
                                              									}
                                              									setWithLastConsumed(
                                              										current,
                                              										"stringIdentifier",
                                              										lv_stringIdentifier_4_1,
                                              										"org.eclipse.xtext.common.Terminals.ID");
                                              								
                                            }

                                            }
                                            break;
                                        case 2 :
                                            // InternalKdl.g:1833:9: lv_stringIdentifier_4_2= RULE_STRING
                                            {
                                            lv_stringIdentifier_4_2=(Token)match(input,RULE_STRING,FOLLOW_47); if (state.failed) return current;
                                            if ( state.backtracking==0 ) {

                                              									newLeafNode(lv_stringIdentifier_4_2, grammarAccess.getConceptAccess().getStringIdentifierSTRINGTerminalRuleCall_0_2_2_0_0_1());
                                              								
                                            }
                                            if ( state.backtracking==0 ) {

                                              									if (current==null) {
                                              										current = createModelElement(grammarAccess.getConceptRule());
                                              									}
                                              									setWithLastConsumed(
                                              										current,
                                              										"stringIdentifier",
                                              										lv_stringIdentifier_4_2,
                                              										"org.eclipse.xtext.common.Terminals.STRING");
                                              								
                                            }

                                            }
                                            break;

                                    }


                                    }


                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1851:6: ( (lv_intIdentifier_5_0= RULE_INT ) )
                                    {
                                    // InternalKdl.g:1851:6: ( (lv_intIdentifier_5_0= RULE_INT ) )
                                    // InternalKdl.g:1852:7: (lv_intIdentifier_5_0= RULE_INT )
                                    {
                                    // InternalKdl.g:1852:7: (lv_intIdentifier_5_0= RULE_INT )
                                    // InternalKdl.g:1853:8: lv_intIdentifier_5_0= RULE_INT
                                    {
                                    lv_intIdentifier_5_0=(Token)match(input,RULE_INT,FOLLOW_47); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_intIdentifier_5_0, grammarAccess.getConceptAccess().getIntIdentifierINTTerminalRuleCall_0_2_2_1_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getConceptRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"intIdentifier",
                                      									lv_intIdentifier_5_0,
                                      									"org.eclipse.xtext.common.Terminals.INT");
                                      							
                                    }

                                    }


                                    }


                                    }
                                    break;

                            }

                            otherlv_6=(Token)match(input,61,FOLLOW_48); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_6, grammarAccess.getConceptAccess().getByKeyword_0_2_3());
                              				
                            }
                            // InternalKdl.g:1874:5: ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) )
                            // InternalKdl.g:1875:6: ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) )
                            {
                            // InternalKdl.g:1875:6: ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) )
                            // InternalKdl.g:1876:7: (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH )
                            {
                            // InternalKdl.g:1876:7: (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH )
                            int alt45=2;
                            int LA45_0 = input.LA(1);

                            if ( (LA45_0==RULE_UPPERCASE_ID) ) {
                                alt45=1;
                            }
                            else if ( (LA45_0==RULE_UPPERCASE_PATH) ) {
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
                                    // InternalKdl.g:1877:8: lv_authority_7_1= RULE_UPPERCASE_ID
                                    {
                                    lv_authority_7_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_authority_7_1, grammarAccess.getConceptAccess().getAuthorityUPPERCASE_IDTerminalRuleCall_0_2_4_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getConceptRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"authority",
                                      									lv_authority_7_1,
                                      									"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:1892:8: lv_authority_7_2= RULE_UPPERCASE_PATH
                                    {
                                    lv_authority_7_2=(Token)match(input,RULE_UPPERCASE_PATH,FOLLOW_2); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_authority_7_2, grammarAccess.getConceptAccess().getAuthorityUPPERCASE_PATHTerminalRuleCall_0_2_4_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getConceptRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"authority",
                                      									lv_authority_7_2,
                                      									"org.integratedmodelling.kdl.Kdl.UPPERCASE_PATH");
                                      							
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
                    break;
                case 2 :
                    // InternalKdl.g:1912:3: ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:1912:3: ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:1913:4: ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:1913:4: ( (lv_presence_8_0= 'presence' ) )
                    // InternalKdl.g:1914:5: (lv_presence_8_0= 'presence' )
                    {
                    // InternalKdl.g:1914:5: (lv_presence_8_0= 'presence' )
                    // InternalKdl.g:1915:6: lv_presence_8_0= 'presence'
                    {
                    lv_presence_8_0=(Token)match(input,62,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_presence_8_0, grammarAccess.getConceptAccess().getPresencePresenceKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "presence", true, "presence");
                      					
                    }

                    }


                    }

                    otherlv_9=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getConceptAccess().getOfKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:1931:4: ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:1932:5: (lv_concept_10_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:1932:5: (lv_concept_10_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:1933:6: lv_concept_10_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_10_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_10_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:1952:3: ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:1952:3: ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:1953:4: ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:1953:4: ( (lv_count_11_0= 'count' ) )
                    // InternalKdl.g:1954:5: (lv_count_11_0= 'count' )
                    {
                    // InternalKdl.g:1954:5: (lv_count_11_0= 'count' )
                    // InternalKdl.g:1955:6: lv_count_11_0= 'count'
                    {
                    lv_count_11_0=(Token)match(input,63,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_count_11_0, grammarAccess.getConceptAccess().getCountCountKeyword_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "count", true, "count");
                      					
                    }

                    }


                    }

                    otherlv_12=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getConceptAccess().getOfKeyword_2_1());
                      			
                    }
                    // InternalKdl.g:1971:4: ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:1972:5: (lv_concept_13_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:1972:5: (lv_concept_13_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:1973:6: lv_concept_13_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_13_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_13_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:1992:3: ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:1992:3: ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:1993:4: ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:1993:4: ( (lv_distance_14_0= 'distance' ) )
                    // InternalKdl.g:1994:5: (lv_distance_14_0= 'distance' )
                    {
                    // InternalKdl.g:1994:5: (lv_distance_14_0= 'distance' )
                    // InternalKdl.g:1995:6: lv_distance_14_0= 'distance'
                    {
                    lv_distance_14_0=(Token)match(input,64,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_distance_14_0, grammarAccess.getConceptAccess().getDistanceDistanceKeyword_3_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "distance", true, "distance");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2007:4: (otherlv_15= 'to' | otherlv_16= 'from' )
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==65) ) {
                        alt47=1;
                    }
                    else if ( (LA47_0==66) ) {
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
                            // InternalKdl.g:2008:5: otherlv_15= 'to'
                            {
                            otherlv_15=(Token)match(input,65,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_15, grammarAccess.getConceptAccess().getToKeyword_3_1_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2013:5: otherlv_16= 'from'
                            {
                            otherlv_16=(Token)match(input,66,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_16, grammarAccess.getConceptAccess().getFromKeyword_3_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:2018:4: ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2019:5: (lv_concept_17_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2019:5: (lv_concept_17_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2020:6: lv_concept_17_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_17_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_17_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:2039:3: ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2039:3: ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2040:4: ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2040:4: ( (lv_probability_18_0= 'probability' ) )
                    // InternalKdl.g:2041:5: (lv_probability_18_0= 'probability' )
                    {
                    // InternalKdl.g:2041:5: (lv_probability_18_0= 'probability' )
                    // InternalKdl.g:2042:6: lv_probability_18_0= 'probability'
                    {
                    lv_probability_18_0=(Token)match(input,67,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_probability_18_0, grammarAccess.getConceptAccess().getProbabilityProbabilityKeyword_4_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "probability", true, "probability");
                      					
                    }

                    }


                    }

                    otherlv_19=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getConceptAccess().getOfKeyword_4_1());
                      			
                    }
                    // InternalKdl.g:2058:4: ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2059:5: (lv_concept_20_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2059:5: (lv_concept_20_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2060:6: lv_concept_20_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_20_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_20_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:2079:3: ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2079:3: ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2080:4: ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2080:4: ( (lv_uncertainty_21_0= 'uncertainty' ) )
                    // InternalKdl.g:2081:5: (lv_uncertainty_21_0= 'uncertainty' )
                    {
                    // InternalKdl.g:2081:5: (lv_uncertainty_21_0= 'uncertainty' )
                    // InternalKdl.g:2082:6: lv_uncertainty_21_0= 'uncertainty'
                    {
                    lv_uncertainty_21_0=(Token)match(input,68,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_uncertainty_21_0, grammarAccess.getConceptAccess().getUncertaintyUncertaintyKeyword_5_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "uncertainty", true, "uncertainty");
                      					
                    }

                    }


                    }

                    otherlv_22=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_22, grammarAccess.getConceptAccess().getOfKeyword_5_1());
                      			
                    }
                    // InternalKdl.g:2098:4: ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2099:5: (lv_concept_23_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2099:5: (lv_concept_23_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2100:6: lv_concept_23_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_5_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_23_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_23_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKdl.g:2119:3: ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    {
                    // InternalKdl.g:2119:3: ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    // InternalKdl.g:2120:4: ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )?
                    {
                    // InternalKdl.g:2120:4: ( (lv_proportion_24_0= 'proportion' ) )
                    // InternalKdl.g:2121:5: (lv_proportion_24_0= 'proportion' )
                    {
                    // InternalKdl.g:2121:5: (lv_proportion_24_0= 'proportion' )
                    // InternalKdl.g:2122:6: lv_proportion_24_0= 'proportion'
                    {
                    lv_proportion_24_0=(Token)match(input,69,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_proportion_24_0, grammarAccess.getConceptAccess().getProportionProportionKeyword_6_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "proportion", true, "proportion");
                      					
                    }

                    }


                    }

                    otherlv_25=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_25, grammarAccess.getConceptAccess().getOfKeyword_6_1());
                      			
                    }
                    // InternalKdl.g:2138:4: ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2139:5: (lv_concept_26_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2139:5: (lv_concept_26_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2140:6: lv_concept_26_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_6_2_0());
                      					
                    }
                    pushFollow(FOLLOW_51);
                    lv_concept_26_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_26_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2157:4: ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==70) ) {
                        int LA48_1 = input.LA(2);

                        if ( (synpred75_InternalKdl()) ) {
                            alt48=1;
                        }
                    }
                    switch (alt48) {
                        case 1 :
                            // InternalKdl.g:2158:5: ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) )
                            {
                            // InternalKdl.g:2167:5: (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) )
                            // InternalKdl.g:2168:6: otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) )
                            {
                            otherlv_27=(Token)match(input,70,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_27, grammarAccess.getConceptAccess().getInKeyword_6_3_0_0());
                              					
                            }
                            // InternalKdl.g:2172:6: ( (lv_other_28_0= ruleSimpleConceptDeclaration ) )
                            // InternalKdl.g:2173:7: (lv_other_28_0= ruleSimpleConceptDeclaration )
                            {
                            // InternalKdl.g:2173:7: (lv_other_28_0= ruleSimpleConceptDeclaration )
                            // InternalKdl.g:2174:8: lv_other_28_0= ruleSimpleConceptDeclaration
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getConceptAccess().getOtherSimpleConceptDeclarationParserRuleCall_6_3_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_other_28_0=ruleSimpleConceptDeclaration();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getConceptRule());
                              								}
                              								set(
                              									current,
                              									"other",
                              									lv_other_28_0,
                              									"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
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
                case 8 :
                    // InternalKdl.g:2195:3: ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2195:3: ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2196:4: ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2196:4: ( (lv_ratio_29_0= 'ratio' ) )
                    // InternalKdl.g:2197:5: (lv_ratio_29_0= 'ratio' )
                    {
                    // InternalKdl.g:2197:5: (lv_ratio_29_0= 'ratio' )
                    // InternalKdl.g:2198:6: lv_ratio_29_0= 'ratio'
                    {
                    lv_ratio_29_0=(Token)match(input,71,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_ratio_29_0, grammarAccess.getConceptAccess().getRatioRatioKeyword_7_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "ratio", true, "ratio");
                      					
                    }

                    }


                    }

                    otherlv_30=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_30, grammarAccess.getConceptAccess().getOfKeyword_7_1());
                      			
                    }
                    // InternalKdl.g:2214:4: ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2215:5: (lv_concept_31_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2215:5: (lv_concept_31_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2216:6: lv_concept_31_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_7_2_0());
                      					
                    }
                    pushFollow(FOLLOW_52);
                    lv_concept_31_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_31_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2233:4: ( ( 'to' )=>otherlv_32= 'to' )
                    // InternalKdl.g:2234:5: ( 'to' )=>otherlv_32= 'to'
                    {
                    otherlv_32=(Token)match(input,65,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_32, grammarAccess.getConceptAccess().getToKeyword_7_3());
                      				
                    }

                    }

                    // InternalKdl.g:2240:4: ( (lv_other_33_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2241:5: (lv_other_33_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2241:5: (lv_other_33_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2242:6: lv_other_33_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getOtherSimpleConceptDeclarationParserRuleCall_7_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_other_33_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"other",
                      							lv_other_33_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKdl.g:2261:3: ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    {
                    // InternalKdl.g:2261:3: ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    // InternalKdl.g:2262:4: ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )?
                    {
                    // InternalKdl.g:2262:4: ( (lv_value_34_0= 'value' ) )
                    // InternalKdl.g:2263:5: (lv_value_34_0= 'value' )
                    {
                    // InternalKdl.g:2263:5: (lv_value_34_0= 'value' )
                    // InternalKdl.g:2264:6: lv_value_34_0= 'value'
                    {
                    lv_value_34_0=(Token)match(input,72,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_34_0, grammarAccess.getConceptAccess().getValueValueKeyword_8_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "value", true, "value");
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getConceptAccess().getOfKeyword_8_1());
                      			
                    }
                    // InternalKdl.g:2280:4: ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2281:5: (lv_concept_36_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2281:5: (lv_concept_36_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2282:6: lv_concept_36_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_8_2_0());
                      					
                    }
                    pushFollow(FOLLOW_25);
                    lv_concept_36_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_36_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:2299:4: ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )?
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==39) ) {
                        int LA49_1 = input.LA(2);

                        if ( (synpred79_InternalKdl()) ) {
                            alt49=1;
                        }
                    }
                    switch (alt49) {
                        case 1 :
                            // InternalKdl.g:2300:5: ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) )
                            {
                            // InternalKdl.g:2309:5: (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) )
                            // InternalKdl.g:2310:6: otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) )
                            {
                            otherlv_37=(Token)match(input,39,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_37, grammarAccess.getConceptAccess().getOverKeyword_8_3_0_0());
                              					
                            }
                            // InternalKdl.g:2314:6: ( (lv_other_38_0= ruleSimpleConceptDeclaration ) )
                            // InternalKdl.g:2315:7: (lv_other_38_0= ruleSimpleConceptDeclaration )
                            {
                            // InternalKdl.g:2315:7: (lv_other_38_0= ruleSimpleConceptDeclaration )
                            // InternalKdl.g:2316:8: lv_other_38_0= ruleSimpleConceptDeclaration
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getConceptAccess().getOtherSimpleConceptDeclarationParserRuleCall_8_3_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_other_38_0=ruleSimpleConceptDeclaration();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getConceptRule());
                              								}
                              								set(
                              									current,
                              									"other",
                              									lv_other_38_0,
                              									"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
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
                case 10 :
                    // InternalKdl.g:2337:3: ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2337:3: ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2338:4: ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2338:4: ( (lv_occurrence_39_0= 'occurrence' ) )
                    // InternalKdl.g:2339:5: (lv_occurrence_39_0= 'occurrence' )
                    {
                    // InternalKdl.g:2339:5: (lv_occurrence_39_0= 'occurrence' )
                    // InternalKdl.g:2340:6: lv_occurrence_39_0= 'occurrence'
                    {
                    lv_occurrence_39_0=(Token)match(input,73,FOLLOW_49); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_occurrence_39_0, grammarAccess.getConceptAccess().getOccurrenceOccurrenceKeyword_9_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getConceptRule());
                      						}
                      						setWithLastConsumed(current, "occurrence", true, "occurrence");
                      					
                    }

                    }


                    }

                    otherlv_40=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_40, grammarAccess.getConceptAccess().getOfKeyword_9_1());
                      			
                    }
                    // InternalKdl.g:2356:4: ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2357:5: (lv_concept_41_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2357:5: (lv_concept_41_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2358:6: lv_concept_41_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_9_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_41_0=ruleSimpleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"concept",
                      							lv_concept_41_0,
                      							"org.integratedmodelling.kdl.Kdl.SimpleConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKdl.g:2377:3: (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' )
                    {
                    // InternalKdl.g:2377:3: (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' )
                    // InternalKdl.g:2378:4: otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')'
                    {
                    otherlv_42=(Token)match(input,74,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_42, grammarAccess.getConceptAccess().getLeftParenthesisKeyword_10_0());
                      			
                    }
                    // InternalKdl.g:2382:4: ( (lv_declaration_43_0= ruleConceptDeclaration ) )
                    // InternalKdl.g:2383:5: (lv_declaration_43_0= ruleConceptDeclaration )
                    {
                    // InternalKdl.g:2383:5: (lv_declaration_43_0= ruleConceptDeclaration )
                    // InternalKdl.g:2384:6: lv_declaration_43_0= ruleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getDeclarationConceptDeclarationParserRuleCall_10_1_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
                    lv_declaration_43_0=ruleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getConceptRule());
                      						}
                      						set(
                      							current,
                      							"declaration",
                      							lv_declaration_43_0,
                      							"org.integratedmodelling.kdl.Kdl.ConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_44=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_44, grammarAccess.getConceptAccess().getRightParenthesisKeyword_10_2());
                      			
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
    // $ANTLR end "ruleConcept"


    // $ANTLR start "entryRuleObservableSemantics"
    // InternalKdl.g:2410:1: entryRuleObservableSemantics returns [EObject current=null] : iv_ruleObservableSemantics= ruleObservableSemantics EOF ;
    public final EObject entryRuleObservableSemantics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObservableSemantics = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1()
        	);

        try {
            // InternalKdl.g:2414:2: (iv_ruleObservableSemantics= ruleObservableSemantics EOF )
            // InternalKdl.g:2415:2: iv_ruleObservableSemantics= ruleObservableSemantics EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getObservableSemanticsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleObservableSemantics=ruleObservableSemantics();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleObservableSemantics; 
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
    // $ANTLR end "entryRuleObservableSemantics"


    // $ANTLR start "ruleObservableSemantics"
    // InternalKdl.g:2424:1: ruleObservableSemantics returns [EObject current=null] : ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) ) ;
    public final EObject ruleObservableSemantics() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_downTo_6_1=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_12=null;
        Token otherlv_15=null;
        EObject lv_declaration_0_0 = null;

        EObject lv_by_3_0 = null;

        AntlrDatatypeRuleToken lv_downTo_6_2 = null;

        EObject lv_role_8_0 = null;

        EObject lv_unit_10_0 = null;

        EObject lv_currency_11_0 = null;

        EObject lv_unit_13_0 = null;

        EObject lv_from_14_0 = null;

        EObject lv_to_16_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1()
        	);

        try {
            // InternalKdl.g:2433:2: ( ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) ) )
            // InternalKdl.g:2434:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) )
            {
            // InternalKdl.g:2434:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) )
            // InternalKdl.g:2435:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) )
            {
            // InternalKdl.g:2435:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) )
            // InternalKdl.g:2436:4: (lv_declaration_0_0= ruleConceptDeclaration )
            {
            // InternalKdl.g:2436:4: (lv_declaration_0_0= ruleConceptDeclaration )
            // InternalKdl.g:2437:5: lv_declaration_0_0= ruleConceptDeclaration
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObservableSemanticsAccess().getDeclarationConceptDeclarationParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_54);
            lv_declaration_0_0=ruleConceptDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
              					}
              					set(
              						current,
              						"declaration",
              						lv_declaration_0_0,
              						"org.integratedmodelling.kdl.Kdl.ConceptDeclaration");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:2454:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) )
            // InternalKdl.g:2455:4: ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) )
            {
            // InternalKdl.g:2455:4: ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) )
            // InternalKdl.g:2456:5: ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());
            // InternalKdl.g:2459:5: ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* )
            // InternalKdl.g:2460:6: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )*
            {
            // InternalKdl.g:2460:6: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )*
            loop54:
            do {
                int alt54=6;
                alt54 = dfa54.predict(input);
                switch (alt54) {
            	case 1 :
            	    // InternalKdl.g:2461:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2461:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
            	    // InternalKdl.g:2462:5: {...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0)");
            	    }
            	    // InternalKdl.g:2462:116: ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
            	    // InternalKdl.g:2463:6: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0);
            	    // InternalKdl.g:2466:9: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
            	    // InternalKdl.g:2466:10: {...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2466:19: (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
            	    // InternalKdl.g:2466:20: otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) )
            	    {
            	    otherlv_2=(Token)match(input,61,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_2, grammarAccess.getObservableSemanticsAccess().getByKeyword_1_0_0());
            	      								
            	    }
            	    // InternalKdl.g:2470:9: ( (lv_by_3_0= ruleConcept ) )
            	    // InternalKdl.g:2471:10: (lv_by_3_0= ruleConcept )
            	    {
            	    // InternalKdl.g:2471:10: (lv_by_3_0= ruleConcept )
            	    // InternalKdl.g:2472:11: lv_by_3_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getByConceptParserRuleCall_1_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_54);
            	    lv_by_3_0=ruleConcept();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	      											}
            	      											set(
            	      												current,
            	      												"by",
            	      												lv_by_3_0,
            	      												"org.integratedmodelling.kdl.Kdl.Concept");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKdl.g:2495:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2495:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
            	    // InternalKdl.g:2496:5: {...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1)");
            	    }
            	    // InternalKdl.g:2496:116: ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
            	    // InternalKdl.g:2497:6: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1);
            	    // InternalKdl.g:2500:9: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
            	    // InternalKdl.g:2500:10: {...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2500:19: (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
            	    // InternalKdl.g:2500:20: otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
            	    {
            	    otherlv_4=(Token)match(input,76,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_4, grammarAccess.getObservableSemanticsAccess().getDownKeyword_1_1_0());
            	      								
            	    }
            	    otherlv_5=(Token)match(input,65,FOLLOW_55); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_5, grammarAccess.getObservableSemanticsAccess().getToKeyword_1_1_1());
            	      								
            	    }
            	    // InternalKdl.g:2508:9: ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
            	    // InternalKdl.g:2509:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
            	    {
            	    // InternalKdl.g:2509:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
            	    // InternalKdl.g:2510:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
            	    {
            	    // InternalKdl.g:2510:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
            	    int alt51=2;
            	    int LA51_0 = input.LA(1);

            	    if ( (LA51_0==RULE_CAMELCASE_ID) ) {
            	        alt51=1;
            	    }
            	    else if ( (LA51_0==RULE_LOWERCASE_ID) ) {
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
            	            // InternalKdl.g:2511:12: lv_downTo_6_1= RULE_CAMELCASE_ID
            	            {
            	            lv_downTo_6_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_54); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_downTo_6_1, grammarAccess.getObservableSemanticsAccess().getDownToCAMELCASE_IDTerminalRuleCall_1_1_2_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getObservableSemanticsRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"downTo",
            	              													lv_downTo_6_1,
            	              													"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:2526:12: lv_downTo_6_2= ruleNamespaceId
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getObservableSemanticsAccess().getDownToNamespaceIdParserRuleCall_1_1_2_0_1());
            	              											
            	            }
            	            pushFollow(FOLLOW_54);
            	            lv_downTo_6_2=ruleNamespaceId();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	              												}
            	              												set(
            	              													current,
            	              													"downTo",
            	              													lv_downTo_6_2,
            	              													"org.integratedmodelling.kdl.Kdl.NamespaceId");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKdl.g:2550:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2550:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
            	    // InternalKdl.g:2551:5: {...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2)");
            	    }
            	    // InternalKdl.g:2551:116: ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
            	    // InternalKdl.g:2552:6: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2);
            	    // InternalKdl.g:2555:9: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
            	    // InternalKdl.g:2555:10: {...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2555:19: (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
            	    // InternalKdl.g:2555:20: otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) )
            	    {
            	    otherlv_7=(Token)match(input,38,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getObservableSemanticsAccess().getAsKeyword_1_2_0());
            	      								
            	    }
            	    // InternalKdl.g:2559:9: ( (lv_role_8_0= ruleConcept ) )
            	    // InternalKdl.g:2560:10: (lv_role_8_0= ruleConcept )
            	    {
            	    // InternalKdl.g:2560:10: (lv_role_8_0= ruleConcept )
            	    // InternalKdl.g:2561:11: lv_role_8_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getRoleConceptParserRuleCall_1_2_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_54);
            	    lv_role_8_0=ruleConcept();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	      											}
            	      											set(
            	      												current,
            	      												"role",
            	      												lv_role_8_0,
            	      												"org.integratedmodelling.kdl.Kdl.Concept");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKdl.g:2584:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2584:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
            	    // InternalKdl.g:2585:5: {...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3)");
            	    }
            	    // InternalKdl.g:2585:116: ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
            	    // InternalKdl.g:2586:6: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3);
            	    // InternalKdl.g:2589:9: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
            	    // InternalKdl.g:2589:10: {...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2589:19: ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
            	    int alt53=2;
            	    int LA53_0 = input.LA(1);

            	    if ( (LA53_0==70) ) {
            	        alt53=1;
            	    }
            	    else if ( (LA53_0==77) ) {
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
            	            // InternalKdl.g:2589:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
            	            {
            	            // InternalKdl.g:2589:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
            	            // InternalKdl.g:2590:10: otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
            	            {
            	            otherlv_9=(Token)match(input,70,FOLLOW_56); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_9, grammarAccess.getObservableSemanticsAccess().getInKeyword_1_3_0_0());
            	              									
            	            }
            	            // InternalKdl.g:2594:10: ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
            	            int alt52=2;
            	            switch ( input.LA(1) ) {
            	            case RULE_CAMELCASE_ID:
            	                {
            	                int LA52_1 = input.LA(2);

            	                if ( (synpred86_InternalKdl()) ) {
            	                    alt52=1;
            	                }
            	                else if ( (true) ) {
            	                    alt52=2;
            	                }
            	                else {
            	                    if (state.backtracking>0) {state.failed=true; return current;}
            	                    NoViableAltException nvae =
            	                        new NoViableAltException("", 52, 1, input);

            	                    throw nvae;
            	                }
            	                }
            	                break;
            	            case RULE_LOWERCASE_ID:
            	                {
            	                int LA52_2 = input.LA(2);

            	                if ( (LA52_2==EOF||LA52_2==RULE_INT||(LA52_2>=30 && LA52_2<=32)||LA52_2==34||(LA52_2>=37 && LA52_2<=38)||(LA52_2>=40 && LA52_2<=44)||(LA52_2>=50 && LA52_2<=56)||LA52_2==61||LA52_2==70||LA52_2==72||(LA52_2>=76 && LA52_2<=87)||LA52_2==102||LA52_2==114||LA52_2==117) ) {
            	                    alt52=1;
            	                }
            	                else if ( (LA52_2==100||LA52_2==103) ) {
            	                    alt52=2;
            	                }
            	                else {
            	                    if (state.backtracking>0) {state.failed=true; return current;}
            	                    NoViableAltException nvae =
            	                        new NoViableAltException("", 52, 2, input);

            	                    throw nvae;
            	                }
            	                }
            	                break;
            	            case EOF:
            	            case RULE_INT:
            	            case 30:
            	            case 31:
            	            case 32:
            	            case 34:
            	            case 37:
            	            case 38:
            	            case 40:
            	            case 41:
            	            case 42:
            	            case 43:
            	            case 44:
            	            case 50:
            	            case 51:
            	            case 52:
            	            case 53:
            	            case 54:
            	            case 55:
            	            case 56:
            	            case 61:
            	            case 70:
            	            case 72:
            	            case 74:
            	            case 76:
            	            case 77:
            	            case 78:
            	            case 79:
            	            case 80:
            	            case 81:
            	            case 82:
            	            case 83:
            	            case 84:
            	            case 85:
            	            case 86:
            	            case 87:
            	            case 102:
            	            case 114:
            	            case 117:
            	                {
            	                alt52=1;
            	                }
            	                break;
            	            case RULE_ID:
            	                {
            	                alt52=2;
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
            	                    // InternalKdl.g:2595:11: ( (lv_unit_10_0= ruleUnit ) )
            	                    {
            	                    // InternalKdl.g:2595:11: ( (lv_unit_10_0= ruleUnit ) )
            	                    // InternalKdl.g:2596:12: (lv_unit_10_0= ruleUnit )
            	                    {
            	                    // InternalKdl.g:2596:12: (lv_unit_10_0= ruleUnit )
            	                    // InternalKdl.g:2597:13: lv_unit_10_0= ruleUnit
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_0_1_0_0());
            	                      												
            	                    }
            	                    pushFollow(FOLLOW_54);
            	                    lv_unit_10_0=ruleUnit();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      													if (current==null) {
            	                      														current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	                      													}
            	                      													set(
            	                      														current,
            	                      														"unit",
            	                      														lv_unit_10_0,
            	                      														"org.integratedmodelling.kdl.Kdl.Unit");
            	                      													afterParserOrEnumRuleCall();
            	                      												
            	                    }

            	                    }


            	                    }


            	                    }
            	                    break;
            	                case 2 :
            	                    // InternalKdl.g:2615:11: ( (lv_currency_11_0= ruleCurrency ) )
            	                    {
            	                    // InternalKdl.g:2615:11: ( (lv_currency_11_0= ruleCurrency ) )
            	                    // InternalKdl.g:2616:12: (lv_currency_11_0= ruleCurrency )
            	                    {
            	                    // InternalKdl.g:2616:12: (lv_currency_11_0= ruleCurrency )
            	                    // InternalKdl.g:2617:13: lv_currency_11_0= ruleCurrency
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getCurrencyCurrencyParserRuleCall_1_3_0_1_1_0());
            	                      												
            	                    }
            	                    pushFollow(FOLLOW_54);
            	                    lv_currency_11_0=ruleCurrency();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      													if (current==null) {
            	                      														current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	                      													}
            	                      													set(
            	                      														current,
            	                      														"currency",
            	                      														lv_currency_11_0,
            	                      														"org.integratedmodelling.kdl.Kdl.Currency");
            	                      													afterParserOrEnumRuleCall();
            	                      												
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
            	            // InternalKdl.g:2637:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
            	            {
            	            // InternalKdl.g:2637:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
            	            // InternalKdl.g:2638:10: otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) )
            	            {
            	            otherlv_12=(Token)match(input,77,FOLLOW_57); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_12, grammarAccess.getObservableSemanticsAccess().getPerKeyword_1_3_1_0());
            	              									
            	            }
            	            // InternalKdl.g:2642:10: ( (lv_unit_13_0= ruleUnit ) )
            	            // InternalKdl.g:2643:11: (lv_unit_13_0= ruleUnit )
            	            {
            	            // InternalKdl.g:2643:11: (lv_unit_13_0= ruleUnit )
            	            // InternalKdl.g:2644:12: lv_unit_13_0= ruleUnit
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_54);
            	            lv_unit_13_0=ruleUnit();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	              												}
            	              												set(
            	              													current,
            	              													"unit",
            	              													lv_unit_13_0,
            	              													"org.integratedmodelling.kdl.Kdl.Unit");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }


            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKdl.g:2668:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2668:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
            	    // InternalKdl.g:2669:5: {...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4)");
            	    }
            	    // InternalKdl.g:2669:116: ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
            	    // InternalKdl.g:2670:6: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4);
            	    // InternalKdl.g:2673:9: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
            	    // InternalKdl.g:2673:10: {...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2673:19: ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
            	    // InternalKdl.g:2673:20: ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) )
            	    {
            	    // InternalKdl.g:2673:20: ( (lv_from_14_0= ruleNumber ) )
            	    // InternalKdl.g:2674:10: (lv_from_14_0= ruleNumber )
            	    {
            	    // InternalKdl.g:2674:10: (lv_from_14_0= ruleNumber )
            	    // InternalKdl.g:2675:11: lv_from_14_0= ruleNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getFromNumberParserRuleCall_1_4_0_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_52);
            	    lv_from_14_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	      											}
            	      											set(
            	      												current,
            	      												"from",
            	      												lv_from_14_0,
            	      												"org.integratedmodelling.kdl.Kdl.Number");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    otherlv_15=(Token)match(input,65,FOLLOW_58); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_15, grammarAccess.getObservableSemanticsAccess().getToKeyword_1_4_1());
            	      								
            	    }
            	    // InternalKdl.g:2696:9: ( (lv_to_16_0= ruleNumber ) )
            	    // InternalKdl.g:2697:10: (lv_to_16_0= ruleNumber )
            	    {
            	    // InternalKdl.g:2697:10: (lv_to_16_0= ruleNumber )
            	    // InternalKdl.g:2698:11: lv_to_16_0= ruleNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getToNumberParserRuleCall_1_4_2_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_54);
            	    lv_to_16_0=ruleNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getObservableSemanticsRule());
            	      											}
            	      											set(
            	      												current,
            	      												"to",
            	      												lv_to_16_0,
            	      												"org.integratedmodelling.kdl.Kdl.Number");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());

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

            getUnorderedGroupHelper().leave(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());

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
    // $ANTLR end "ruleObservableSemantics"


    // $ANTLR start "entryRuleSimpleConceptDeclaration"
    // InternalKdl.g:2735:1: entryRuleSimpleConceptDeclaration returns [EObject current=null] : iv_ruleSimpleConceptDeclaration= ruleSimpleConceptDeclaration EOF ;
    public final EObject entryRuleSimpleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleConceptDeclaration = null;


        try {
            // InternalKdl.g:2735:65: (iv_ruleSimpleConceptDeclaration= ruleSimpleConceptDeclaration EOF )
            // InternalKdl.g:2736:2: iv_ruleSimpleConceptDeclaration= ruleSimpleConceptDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSimpleConceptDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSimpleConceptDeclaration=ruleSimpleConceptDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSimpleConceptDeclaration; 
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
    // $ANTLR end "entryRuleSimpleConceptDeclaration"


    // $ANTLR start "ruleSimpleConceptDeclaration"
    // InternalKdl.g:2742:1: ruleSimpleConceptDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ ) ;
    public final EObject ruleSimpleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_main_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2748:2: ( ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ ) )
            // InternalKdl.g:2749:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ )
            {
            // InternalKdl.g:2749:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ )
            // InternalKdl.g:2750:3: ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+
            {
            // InternalKdl.g:2750:3: ( (lv_name_0_0= RULE_STRING ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_STRING) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalKdl.g:2751:4: (lv_name_0_0= RULE_STRING )
                    {
                    // InternalKdl.g:2751:4: (lv_name_0_0= RULE_STRING )
                    // InternalKdl.g:2752:5: lv_name_0_0= RULE_STRING
                    {
                    lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_name_0_0, grammarAccess.getSimpleConceptDeclarationAccess().getNameSTRINGTerminalRuleCall_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getSimpleConceptDeclarationRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"name",
                      						lv_name_0_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKdl.g:2768:3: ( (lv_main_1_0= ruleConcept ) )+
            int cnt56=0;
            loop56:
            do {
                int alt56=2;
                alt56 = dfa56.predict(input);
                switch (alt56) {
            	case 1 :
            	    // InternalKdl.g:2769:4: (lv_main_1_0= ruleConcept )
            	    {
            	    // InternalKdl.g:2769:4: (lv_main_1_0= ruleConcept )
            	    // InternalKdl.g:2770:5: lv_main_1_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSimpleConceptDeclarationAccess().getMainConceptParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_59);
            	    lv_main_1_0=ruleConcept();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getSimpleConceptDeclarationRule());
            	      					}
            	      					add(
            	      						current,
            	      						"main",
            	      						lv_main_1_0,
            	      						"org.integratedmodelling.kdl.Kdl.Concept");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt56 >= 1 ) break loop56;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(56, input);
                        throw eee;
                }
                cnt56++;
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
    // $ANTLR end "ruleSimpleConceptDeclaration"


    // $ANTLR start "entryRuleParameter"
    // InternalKdl.g:2791:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:2791:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:2792:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalKdl.g:2798:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2804:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:2805:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:2805:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:2806:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:2806:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:2807:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:2807:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:2808:5: lv_name_0_0= RULE_LOWERCASE_ID
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

            // InternalKdl.g:2824:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:2825:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:2825:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:2826:5: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_60);
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

            // InternalKdl.g:2843:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_STRING) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalKdl.g:2844:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:2844:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:2845:5: lv_docstring_2_0= RULE_STRING
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
    // InternalKdl.g:2865:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:2865:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:2866:2: iv_ruleACTOR= ruleACTOR EOF
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
    // InternalKdl.g:2872:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2878:2: ( (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) )
            // InternalKdl.g:2879:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            {
            // InternalKdl.g:2879:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            int alt58=14;
            switch ( input.LA(1) ) {
            case 78:
                {
                alt58=1;
                }
                break;
            case 79:
                {
                alt58=2;
                }
                break;
            case 72:
                {
                alt58=3;
                }
                break;
            case 40:
                {
                alt58=4;
                }
                break;
            case 80:
                {
                alt58=5;
                }
                break;
            case 41:
                {
                alt58=6;
                }
                break;
            case 42:
                {
                alt58=7;
                }
                break;
            case 81:
                {
                alt58=8;
                }
                break;
            case 82:
                {
                alt58=9;
                }
                break;
            case 83:
                {
                alt58=10;
                }
                break;
            case 84:
                {
                alt58=11;
                }
                break;
            case 85:
                {
                alt58=12;
                }
                break;
            case 86:
                {
                alt58=13;
                }
                break;
            case 87:
                {
                alt58=14;
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
                    // InternalKdl.g:2880:3: kw= 'object'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2886:3: kw= 'process'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2892:3: kw= 'value'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:2898:3: kw= 'number'
                    {
                    kw=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:2904:3: kw= 'concept'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:2910:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:2916:3: kw= 'text'
                    {
                    kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:2922:3: kw= 'extent'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:2928:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:2934:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:2940:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:2946:3: kw= 'contextualizer'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getContextualizerKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:2952:3: kw= 'void'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:2958:3: kw= 'partition'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2967:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:2967:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:2968:2: iv_ruleTARGET= ruleTARGET EOF
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
    // InternalKdl.g:2974:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2980:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' ) )
            // InternalKdl.g:2981:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' )
            {
            // InternalKdl.g:2981:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' )
            int alt59=3;
            switch ( input.LA(1) ) {
            case 88:
                {
                alt59=1;
                }
                break;
            case 89:
                {
                alt59=2;
                }
                break;
            case 90:
                {
                alt59=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // InternalKdl.g:2982:3: kw= 'models'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:2988:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:2994:3: kw= 'observers'
                    {
                    kw=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getObserversKeyword_2());
                      		
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


    // $ANTLR start "entryRuleClassification"
    // InternalKdl.g:3003:1: entryRuleClassification returns [EObject current=null] : iv_ruleClassification= ruleClassification EOF ;
    public final EObject entryRuleClassification() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassification = null;


        try {
            // InternalKdl.g:3003:55: (iv_ruleClassification= ruleClassification EOF )
            // InternalKdl.g:3004:2: iv_ruleClassification= ruleClassification EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassificationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassification=ruleClassification();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassification; 
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
    // $ANTLR end "entryRuleClassification"


    // $ANTLR start "ruleClassification"
    // InternalKdl.g:3010:1: ruleClassification returns [EObject current=null] : ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* ) ;
    public final EObject ruleClassification() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifiers_0_0 = null;

        EObject lv_classifiers_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3016:2: ( ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* ) )
            // InternalKdl.g:3017:2: ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* )
            {
            // InternalKdl.g:3017:2: ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* )
            // InternalKdl.g:3018:3: ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )*
            {
            // InternalKdl.g:3018:3: ( (lv_classifiers_0_0= ruleClassifier ) )
            // InternalKdl.g:3019:4: (lv_classifiers_0_0= ruleClassifier )
            {
            // InternalKdl.g:3019:4: (lv_classifiers_0_0= ruleClassifier )
            // InternalKdl.g:3020:5: lv_classifiers_0_0= ruleClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getClassificationAccess().getClassifiersClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_26);
            lv_classifiers_0_0=ruleClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getClassificationRule());
              					}
              					add(
              						current,
              						"classifiers",
              						lv_classifiers_0_0,
              						"org.integratedmodelling.kdl.Kdl.Classifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:3037:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==28) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalKdl.g:3038:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) )
            	    {
            	    // InternalKdl.g:3038:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKdl.g:3039:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,28,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getClassificationAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKdl.g:3045:4: ( (lv_classifiers_2_0= ruleClassifier ) )
            	    // InternalKdl.g:3046:5: (lv_classifiers_2_0= ruleClassifier )
            	    {
            	    // InternalKdl.g:3046:5: (lv_classifiers_2_0= ruleClassifier )
            	    // InternalKdl.g:3047:6: lv_classifiers_2_0= ruleClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getClassificationAccess().getClassifiersClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_26);
            	    lv_classifiers_2_0=ruleClassifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getClassificationRule());
            	      						}
            	      						add(
            	      							current,
            	      							"classifiers",
            	      							lv_classifiers_2_0,
            	      							"org.integratedmodelling.kdl.Kdl.Classifier");
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
    // $ANTLR end "ruleClassification"


    // $ANTLR start "entryRuleClassifier"
    // InternalKdl.g:3069:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKdl.g:3069:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKdl.g:3070:2: iv_ruleClassifier= ruleClassifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassifier=ruleClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassifier; 
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
    // $ANTLR end "entryRuleClassifier"


    // $ANTLR start "ruleClassifier"
    // InternalKdl.g:3076:1: ruleClassifier returns [EObject current=null] : ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? ) ;
    public final EObject ruleClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_otherwise_1_0=null;
        Token otherlv_2=null;
        Token lv_negated_3_0=null;
        EObject lv_declaration_0_0 = null;

        EObject lv_classifier_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3082:2: ( ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? ) )
            // InternalKdl.g:3083:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? )
            {
            // InternalKdl.g:3083:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? )
            // InternalKdl.g:3084:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )?
            {
            // InternalKdl.g:3084:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) )
            // InternalKdl.g:3085:4: (lv_declaration_0_0= ruleConceptDeclaration )
            {
            // InternalKdl.g:3085:4: (lv_declaration_0_0= ruleConceptDeclaration )
            // InternalKdl.g:3086:5: lv_declaration_0_0= ruleConceptDeclaration
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getClassifierAccess().getDeclarationConceptDeclarationParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_61);
            lv_declaration_0_0=ruleConceptDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getClassifierRule());
              					}
              					set(
              						current,
              						"declaration",
              						lv_declaration_0_0,
              						"org.integratedmodelling.kdl.Kdl.ConceptDeclaration");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKdl.g:3103:3: ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )?
            int alt62=3;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==91) ) {
                alt62=1;
            }
            else if ( ((LA62_0>=92 && LA62_0<=93)) ) {
                alt62=2;
            }
            switch (alt62) {
                case 1 :
                    // InternalKdl.g:3104:4: ( (lv_otherwise_1_0= 'otherwise' ) )
                    {
                    // InternalKdl.g:3104:4: ( (lv_otherwise_1_0= 'otherwise' ) )
                    // InternalKdl.g:3105:5: (lv_otherwise_1_0= 'otherwise' )
                    {
                    // InternalKdl.g:3105:5: (lv_otherwise_1_0= 'otherwise' )
                    // InternalKdl.g:3106:6: lv_otherwise_1_0= 'otherwise'
                    {
                    lv_otherwise_1_0=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_otherwise_1_0, grammarAccess.getClassifierAccess().getOtherwiseOtherwiseKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getClassifierRule());
                      						}
                      						setWithLastConsumed(current, "otherwise", true, "otherwise");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:3119:4: ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) )
                    {
                    // InternalKdl.g:3119:4: ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) )
                    // InternalKdl.g:3120:5: (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) )
                    {
                    // InternalKdl.g:3120:5: (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) )
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==92) ) {
                        alt61=1;
                    }
                    else if ( (LA61_0==93) ) {
                        alt61=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 61, 0, input);

                        throw nvae;
                    }
                    switch (alt61) {
                        case 1 :
                            // InternalKdl.g:3121:6: otherlv_2= 'if'
                            {
                            otherlv_2=(Token)match(input,92,FOLLOW_62); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getClassifierAccess().getIfKeyword_1_1_0_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:3126:6: ( (lv_negated_3_0= 'unless' ) )
                            {
                            // InternalKdl.g:3126:6: ( (lv_negated_3_0= 'unless' ) )
                            // InternalKdl.g:3127:7: (lv_negated_3_0= 'unless' )
                            {
                            // InternalKdl.g:3127:7: (lv_negated_3_0= 'unless' )
                            // InternalKdl.g:3128:8: lv_negated_3_0= 'unless'
                            {
                            lv_negated_3_0=(Token)match(input,93,FOLLOW_62); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_negated_3_0, grammarAccess.getClassifierAccess().getNegatedUnlessKeyword_1_1_0_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getClassifierRule());
                              								}
                              								setWithLastConsumed(current, "negated", true, "unless");
                              							
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:3141:5: ( (lv_classifier_4_0= ruleClassifierRHS ) )
                    // InternalKdl.g:3142:6: (lv_classifier_4_0= ruleClassifierRHS )
                    {
                    // InternalKdl.g:3142:6: (lv_classifier_4_0= ruleClassifierRHS )
                    // InternalKdl.g:3143:7: lv_classifier_4_0= ruleClassifierRHS
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getClassifierAccess().getClassifierClassifierRHSParserRuleCall_1_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_classifier_4_0=ruleClassifierRHS();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getClassifierRule());
                      							}
                      							set(
                      								current,
                      								"classifier",
                      								lv_classifier_4_0,
                      								"org.integratedmodelling.kdl.Kdl.ClassifierRHS");
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
    // $ANTLR end "ruleClassifier"


    // $ANTLR start "entryRuleClassifierRHS"
    // InternalKdl.g:3166:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:3166:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:3167:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
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
    // InternalKdl.g:3173:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
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
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token lv_nodata_21_0=null;
        Token lv_star_22_0=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;

        EObject lv_num_9_0 = null;

        EObject lv_set_11_0 = null;

        EObject lv_concept_13_0 = null;

        EObject lv_toResolve_15_0 = null;

        EObject lv_toResolve_17_0 = null;

        EObject lv_op_19_0 = null;

        EObject lv_expression_20_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3179:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:3180:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:3180:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt67=10;
            alt67 = dfa67.predict(input);
            switch (alt67) {
                case 1 :
                    // InternalKdl.g:3181:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:3181:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==94) ) {
                        alt63=1;
                    }
                    else if ( (LA63_0==95) ) {
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
                            // InternalKdl.g:3182:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:3182:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:3183:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:3183:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:3184:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3197:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:3197:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:3198:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:3198:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:3199:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3213:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:3213:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:3214:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:3214:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:3215:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3215:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:3216:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
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

                    // InternalKdl.g:3233:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt64=3;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==96) ) {
                        alt64=1;
                    }
                    else if ( (LA64_0==97) ) {
                        alt64=2;
                    }
                    switch (alt64) {
                        case 1 :
                            // InternalKdl.g:3234:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3234:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:3235:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:3235:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:3236:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,96,FOLLOW_52); if (state.failed) return current;
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
                            // InternalKdl.g:3249:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,97,FOLLOW_52); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:3254:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:3255:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,65,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:3261:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:3262:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:3266:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:3267:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_64);
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

                    // InternalKdl.g:3284:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt65=3;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==96) ) {
                        alt65=1;
                    }
                    else if ( (LA65_0==97) ) {
                        alt65=2;
                    }
                    switch (alt65) {
                        case 1 :
                            // InternalKdl.g:3285:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3285:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:3286:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:3286:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:3287:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,96,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3300:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,97,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3307:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3307:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:3308:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:3308:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:3309:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:3327:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:3327:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:3328:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,70,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:3332:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:3333:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:3333:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:3334:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:3353:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3353:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:3354:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:3354:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:3355:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:3372:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
                    {
                    // InternalKdl.g:3372:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
                    // InternalKdl.g:3373:4: (lv_concept_13_0= ruleConceptDeclaration )
                    {
                    // InternalKdl.g:3373:4: (lv_concept_13_0= ruleConceptDeclaration )
                    // InternalKdl.g:3374:5: lv_concept_13_0= ruleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getClassifierRHSAccess().getConceptConceptDeclarationParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_concept_13_0=ruleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      					}
                      					set(
                      						current,
                      						"concept",
                      						lv_concept_13_0,
                      						"org.integratedmodelling.kdl.Kdl.ConceptDeclaration");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKdl.g:3392:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:3392:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:3393:4: otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,74,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:3397:4: ( (lv_toResolve_15_0= ruleConceptDeclaration ) )
                    // InternalKdl.g:3398:5: (lv_toResolve_15_0= ruleConceptDeclaration )
                    {
                    // InternalKdl.g:3398:5: (lv_toResolve_15_0= ruleConceptDeclaration )
                    // InternalKdl.g:3399:6: lv_toResolve_15_0= ruleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_66);
                    lv_toResolve_15_0=ruleConceptDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                      						}
                      						add(
                      							current,
                      							"toResolve",
                      							lv_toResolve_15_0,
                      							"org.integratedmodelling.kdl.Kdl.ConceptDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:3416:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )*
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==28) ) {
                            alt66=1;
                        }


                        switch (alt66) {
                    	case 1 :
                    	    // InternalKdl.g:3417:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
                    	    {
                    	    // InternalKdl.g:3417:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:3418:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,28,FOLLOW_35); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3424:5: ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
                    	    // InternalKdl.g:3425:6: (lv_toResolve_17_0= ruleConceptDeclaration )
                    	    {
                    	    // InternalKdl.g:3425:6: (lv_toResolve_17_0= ruleConceptDeclaration )
                    	    // InternalKdl.g:3426:7: lv_toResolve_17_0= ruleConceptDeclaration
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_66);
                    	    lv_toResolve_17_0=ruleConceptDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getClassifierRHSRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"toResolve",
                    	      								lv_toResolve_17_0,
                    	      								"org.integratedmodelling.kdl.Kdl.ConceptDeclaration");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop66;
                        }
                    } while (true);

                    otherlv_18=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getClassifierRHSAccess().getRightParenthesisKeyword_6_3());
                      			
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:3450:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3450:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:3451:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3451:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:3452:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:3452:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:3453:6: lv_op_19_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_58);
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

                    // InternalKdl.g:3470:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:3471:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:3471:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:3472:6: lv_expression_20_0= ruleNumber
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
                    // InternalKdl.g:3491:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:3491:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:3492:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:3492:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:3493:5: lv_nodata_21_0= 'unknown'
                    {
                    lv_nodata_21_0=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3506:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:3506:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:3507:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:3507:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:3508:5: lv_star_22_0= '*'
                    {
                    lv_star_22_0=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3524:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:3524:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:3525:2: iv_ruleList= ruleList EOF
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
    // InternalKdl.g:3531:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3537:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:3538:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:3538:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:3539:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:3539:3: ()
            // InternalKdl.g:3540:4: 
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

            otherlv_1=(Token)match(input,74,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKdl.g:3553:3: ( (lv_contents_2_0= ruleValue ) )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( ((LA68_0>=RULE_STRING && LA68_0<=RULE_UPPERCASE_ID)||(LA68_0>=RULE_CAMELCASE_ID && LA68_0<=RULE_ID)||LA68_0==28||LA68_0==34||LA68_0==74||(LA68_0>=94 && LA68_0<=95)||LA68_0==99||LA68_0==107||LA68_0==114) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // InternalKdl.g:3554:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:3554:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:3555:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_67);
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
            	    break loop68;
                }
            } while (true);

            otherlv_3=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3580:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:3580:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:3581:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKdl.g:3587:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
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
            // InternalKdl.g:3593:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:3594:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:3594:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt70=4;
            alt70 = dfa70.predict(input);
            switch (alt70) {
                case 1 :
                    // InternalKdl.g:3595:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3595:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:3596:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3596:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:3597:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:3615:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3615:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:3616:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3616:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:3617:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:3617:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:3618:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_52);
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

                    otherlv_2=(Token)match(input,65,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:3639:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:3640:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3640:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:3641:6: lv_to_3_0= ruleNumber
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
                    // InternalKdl.g:3660:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3660:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3661:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3661:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3662:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3679:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3679:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3680:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3680:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3681:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3681:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==94) ) {
                        alt69=1;
                    }
                    else if ( (LA69_0==95) ) {
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
                            // InternalKdl.g:3682:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3693:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3710:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:3710:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:3711:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
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
    // InternalKdl.g:3717:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
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
            // InternalKdl.g:3723:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:3724:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:3724:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt72=6;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // InternalKdl.g:3725:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3725:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:3726:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3726:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:3727:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3727:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:3728:6: lv_from_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralOrIdOrCommaAccess().getFromNumberParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_52);
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

                    // InternalKdl.g:3745:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:3746:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,65,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:3752:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:3753:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3757:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:3758:6: lv_to_2_0= ruleNumber
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
                    // InternalKdl.g:3777:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3777:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:3778:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3778:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:3779:5: lv_number_3_0= ruleNumber
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
                    // InternalKdl.g:3797:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3797:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3798:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3798:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3799:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3816:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3816:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3817:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3817:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3818:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3818:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==94) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==95) ) {
                        alt71=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 71, 0, input);

                        throw nvae;
                    }
                    switch (alt71) {
                        case 1 :
                            // InternalKdl.g:3819:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3830:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3844:3: ( (lv_id_6_0= RULE_ID ) )
                    {
                    // InternalKdl.g:3844:3: ( (lv_id_6_0= RULE_ID ) )
                    // InternalKdl.g:3845:4: (lv_id_6_0= RULE_ID )
                    {
                    // InternalKdl.g:3845:4: (lv_id_6_0= RULE_ID )
                    // InternalKdl.g:3846:5: lv_id_6_0= RULE_ID
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
                    // InternalKdl.g:3863:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:3863:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:3864:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:3864:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:3865:5: lv_comma_7_0= ','
                    {
                    lv_comma_7_0=(Token)match(input,28,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3881:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:3881:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:3882:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
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
    // InternalKdl.g:3888:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3894:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:3895:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:3895:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt74=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 34:
            case 114:
                {
                alt74=1;
                }
                break;
            case RULE_STRING:
                {
                alt74=2;
                }
                break;
            case 94:
            case 95:
                {
                alt74=3;
                }
                break;
            case RULE_ID:
                {
                alt74=4;
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
                    // InternalKdl.g:3896:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3896:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:3897:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3897:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:3898:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:3916:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3916:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:3917:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:3917:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:3918:5: lv_string_1_0= RULE_STRING
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
                    // InternalKdl.g:3935:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3935:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:3936:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:3936:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:3937:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:3937:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==94) ) {
                        alt73=1;
                    }
                    else if ( (LA73_0==95) ) {
                        alt73=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 73, 0, input);

                        throw nvae;
                    }
                    switch (alt73) {
                        case 1 :
                            // InternalKdl.g:3938:6: lv_boolean_2_1= 'true'
                            {
                            lv_boolean_2_1=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3949:6: lv_boolean_2_2= 'false'
                            {
                            lv_boolean_2_2=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3963:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:3963:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:3964:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:3964:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:3965:5: lv_id_3_0= RULE_ID
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
    // InternalKdl.g:3985:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:3985:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:3986:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKdl.g:3992:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
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
            // InternalKdl.g:3998:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:3999:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:3999:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:4000:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:4000:3: ()
            // InternalKdl.g:4001:4: 
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

            otherlv_1=(Token)match(input,36,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMetadataAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:4014:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==RULE_LOWERCASE_ID) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // InternalKdl.g:4015:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:4015:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:4016:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:4016:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:4017:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:4017:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt75=2;
            	    int LA75_0 = input.LA(1);

            	    if ( (LA75_0==RULE_LOWERCASE_ID) ) {
            	        int LA75_1 = input.LA(2);

            	        if ( (LA75_1==RULE_STRING||LA75_1==RULE_INT||LA75_1==RULE_ID||LA75_1==34||LA75_1==36||LA75_1==74||(LA75_1>=94 && LA75_1<=95)||LA75_1==114) ) {
            	            alt75=1;
            	        }
            	        else if ( (LA75_1==100||LA75_1==103) ) {
            	            alt75=2;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 75, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 75, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt75) {
            	        case 1 :
            	            // InternalKdl.g:4018:7: lv_ids_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_ids_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
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
            	            // InternalKdl.g:4033:7: lv_ids_2_2= rulePropertyId
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getIdsPropertyIdParserRuleCall_2_0_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_69);
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

            	    // InternalKdl.g:4051:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:4052:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:4052:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:4053:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:4053:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt76=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 34:
            	    case 94:
            	    case 95:
            	    case 114:
            	        {
            	        alt76=1;
            	        }
            	        break;
            	    case 36:
            	        {
            	        alt76=2;
            	        }
            	        break;
            	    case 74:
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
            	            // InternalKdl.g:4054:7: lv_values_3_1= ruleLiteralOrID
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesLiteralOrIDParserRuleCall_2_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_68);
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
            	            // InternalKdl.g:4070:7: lv_values_3_2= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesMetadataParserRuleCall_2_1_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_68);
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
            	            // InternalKdl.g:4086:7: lv_values_3_3= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesListParserRuleCall_2_1_0_2());
            	              						
            	            }
            	            pushFollow(FOLLOW_68);
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
            	    break loop77;
                }
            } while (true);

            otherlv_4=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4113:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:4113:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:4114:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKdl.g:4120:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
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
            // InternalKdl.g:4126:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:4127:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:4127:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==RULE_STRING||(LA80_0>=RULE_INT && LA80_0<=RULE_UPPERCASE_ID)||(LA80_0>=RULE_CAMELCASE_ID && LA80_0<=RULE_ID)||LA80_0==28||LA80_0==34||LA80_0==74||(LA80_0>=94 && LA80_0<=95)||LA80_0==99||LA80_0==107||LA80_0==114) ) {
                alt80=1;
            }
            else if ( (LA80_0==RULE_LOWERCASE_ID) ) {
                int LA80_2 = input.LA(2);

                if ( (LA80_2==EOF||(LA80_2>=RULE_LOWERCASE_ID && LA80_2<=RULE_INT)||LA80_2==RULE_CAMELCASE_ID||LA80_2==28||LA80_2==34||LA80_2==38||LA80_2==56||(LA80_2>=74 && LA80_2<=75)||(LA80_2>=100 && LA80_2<=103)||LA80_2==106||LA80_2==114||LA80_2==117) ) {
                    alt80=1;
                }
                else if ( ((LA80_2>=104 && LA80_2<=105)) ) {
                    alt80=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 80, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }
            switch (alt80) {
                case 1 :
                    // InternalKdl.g:4128:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:4128:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:4129:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:4129:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:4130:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:4130:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:4131:6: lv_values_0_0= ruleValue
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

                    // InternalKdl.g:4148:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==28) ) {
                            alt78=1;
                        }


                        switch (alt78) {
                    	case 1 :
                    	    // InternalKdl.g:4149:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,28,FOLLOW_32); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:4153:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:4154:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:4154:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:4155:7: lv_values_2_0= ruleValue
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
                    	    break loop78;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4175:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:4175:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:4176:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:4176:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:4177:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:4177:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:4178:6: lv_pairs_3_0= ruleKeyValuePair
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

                    // InternalKdl.g:4195:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==28) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // InternalKdl.g:4196:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:4196:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:4197:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,28,FOLLOW_5); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:4203:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:4204:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:4204:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:4205:7: lv_pairs_5_0= ruleKeyValuePair
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
                    	    break loop79;
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
    // InternalKdl.g:4228:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:4228:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:4229:2: iv_ruleValue= ruleValue EOF
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
    // InternalKdl.g:4235:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_enumId_6_0=null;
        EObject lv_literal_0_0 = null;

        EObject lv_function_1_0 = null;

        EObject lv_urn_2_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;

        EObject lv_list_5_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4241:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:4242:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:4242:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) )
            int alt81=6;
            alt81 = dfa81.predict(input);
            switch (alt81) {
                case 1 :
                    // InternalKdl.g:4243:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:4243:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:4244:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:4244:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:4245:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
                    // InternalKdl.g:4263:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:4263:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:4264:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:4264:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:4265:5: lv_function_1_0= ruleFunction
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
                    // InternalKdl.g:4283:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
                    {
                    // InternalKdl.g:4283:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
                    // InternalKdl.g:4284:4: ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKdl.g:4284:4: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:4285:5: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:4285:5: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:4286:6: lv_urn_2_0= ruleUrn
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_70);
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

                    // InternalKdl.g:4303:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKdl.g:4304:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKdl.g:4304:5: (lv_unit_3_0= ruleUnit )
                    // InternalKdl.g:4305:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getValueAccess().getUnitUnitParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_unit_3_0=ruleUnit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getValueRule());
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


                    }


                    }
                    break;
                case 4 :
                    // InternalKdl.g:4324:3: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKdl.g:4324:3: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKdl.g:4325:4: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKdl.g:4325:4: (lv_currency_4_0= ruleCurrency )
                    // InternalKdl.g:4326:5: lv_currency_4_0= ruleCurrency
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getCurrencyCurrencyParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_currency_4_0=ruleCurrency();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"currency",
                      						lv_currency_4_0,
                      						"org.integratedmodelling.kdl.Kdl.Currency");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKdl.g:4344:3: ( (lv_list_5_0= ruleList ) )
                    {
                    // InternalKdl.g:4344:3: ( (lv_list_5_0= ruleList ) )
                    // InternalKdl.g:4345:4: (lv_list_5_0= ruleList )
                    {
                    // InternalKdl.g:4345:4: (lv_list_5_0= ruleList )
                    // InternalKdl.g:4346:5: lv_list_5_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_list_5_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"list",
                      						lv_list_5_0,
                      						"org.integratedmodelling.kdl.Kdl.List");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKdl.g:4364:3: ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:4364:3: ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:4365:4: (lv_enumId_6_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:4365:4: (lv_enumId_6_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:4366:5: lv_enumId_6_0= RULE_UPPERCASE_ID
                    {
                    lv_enumId_6_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_enumId_6_0, grammarAccess.getValueAccess().getEnumIdUPPERCASE_IDTerminalRuleCall_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"enumId",
                      						lv_enumId_6_0,
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
    // InternalKdl.g:4386:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:4386:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:4387:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKdl.g:4393:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:4399:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:4400:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:4400:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:4401:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:4401:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:4402:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:4402:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt82=3;
            switch ( input.LA(1) ) {
            case 99:
                {
                alt82=1;
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
                case RULE_CAMELCASE_ID:
                case RULE_ID:
                case 17:
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
                case 34:
                case 36:
                case 37:
                case 38:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 72:
                case 74:
                case 75:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 94:
                case 95:
                case 99:
                case 101:
                case 102:
                case 107:
                case 114:
                case 117:
                    {
                    alt82=3;
                    }
                    break;
                case 103:
                    {
                    int LA82_5 = input.LA(3);

                    if ( (LA82_5==RULE_LOWERCASE_ID) ) {
                        int LA82_6 = input.LA(4);

                        if ( (LA82_6==100||LA82_6==103) ) {
                            alt82=1;
                        }
                        else if ( (LA82_6==EOF||(LA82_6>=RULE_STRING && LA82_6<=RULE_UPPERCASE_ID)||(LA82_6>=RULE_CAMELCASE_ID && LA82_6<=RULE_ID)||(LA82_6>=17 && LA82_6<=32)||LA82_6==34||(LA82_6>=36 && LA82_6<=38)||(LA82_6>=40 && LA82_6<=44)||(LA82_6>=50 && LA82_6<=56)||LA82_6==72||(LA82_6>=74 && LA82_6<=75)||(LA82_6>=78 && LA82_6<=87)||(LA82_6>=94 && LA82_6<=95)||LA82_6==99||(LA82_6>=101 && LA82_6<=102)||LA82_6==107||LA82_6==114||LA82_6==117) ) {
                            alt82=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 82, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 82, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 100:
                    {
                    alt82=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 82, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt82=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
                {
                alt82=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // InternalKdl.g:4403:5: lv_name_0_1= ruleUrnId
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
                    // InternalKdl.g:4419:5: lv_name_0_2= RULE_STRING
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
                    // InternalKdl.g:4434:5: lv_name_0_3= ruleLocalFilePath
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
    // InternalKdl.g:4455:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:4455:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:4456:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKdl.g:4462:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4468:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4469:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4469:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4470:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4470:3: (kw= 'urn:klab:' )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==99) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalKdl.g:4471:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,99,FOLLOW_3); if (state.failed) return current;
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
            pushFollow(FOLLOW_71);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,100,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_71);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,100,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_71);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,100,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_72);
            this_Path_7=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_Path_7);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalKdl.g:4532:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==100) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalKdl.g:4533:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
                    {
                    kw=(Token)match(input,100,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_73);
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

            // InternalKdl.g:4549:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==101) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalKdl.g:4550:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,101,FOLLOW_5); if (state.failed) return current;
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


    // $ANTLR start "entryRuleLocalFilePath"
    // InternalKdl.g:4567:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:4567:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:4568:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
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
    // InternalKdl.g:4574:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4580:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4581:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4581:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4582:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4582:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt86=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt86=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt86=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
                {
                alt86=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }

            switch (alt86) {
                case 1 :
                    // InternalKdl.g:4583:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_CAMELCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4591:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:4599:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_2, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4607:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==102) ) {
                    switch ( input.LA(2) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        int LA88_3 = input.LA(3);

                        if ( (synpred170_InternalKdl()) ) {
                            alt88=1;
                        }


                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        int LA88_4 = input.LA(3);

                        if ( (synpred170_InternalKdl()) ) {
                            alt88=1;
                        }


                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt88=1;
                        }
                        break;

                    }

                }


                switch (alt88) {
            	case 1 :
            	    // InternalKdl.g:4608:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,102,FOLLOW_75); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4613:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    int alt87=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_CAMELCASE_ID:
            	        {
            	        alt87=1;
            	        }
            	        break;
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt87=2;
            	        }
            	        break;
            	    case RULE_LOWERCASE_DASHID:
            	        {
            	        alt87=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 87, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt87) {
            	        case 1 :
            	            // InternalKdl.g:4614:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
            	            {
            	            this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_74); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_CAMELCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CAMELCASE_ID_4, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4622:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_5, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:4630:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
            	            {
            	            this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_74); if (state.failed) return current;
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
            	    break loop88;
                }
            } while (true);

            // InternalKdl.g:4639:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==103) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // InternalKdl.g:4640:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getFullStopKeyword_2_0());
                      			
                    }
                    this_LOWERCASE_ID_8=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_8, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4653:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==101) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalKdl.g:4654:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,101,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:4671:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4671:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4672:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKdl.g:4678:1: ruleKeyValuePair returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4684:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4685:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4685:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4686:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4686:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:4687:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:4687:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:4688:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getKeyValuePairRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKdl.g:4704:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==104) ) {
                alt91=1;
            }
            else if ( (LA91_0==105) ) {
                alt91=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }
            switch (alt91) {
                case 1 :
                    // InternalKdl.g:4705:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4705:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4706:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4706:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4707:6: lv_interactive_1_0= '=?'
                    {
                    lv_interactive_1_0=(Token)match(input,104,FOLLOW_32); if (state.failed) return current;
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
                    // InternalKdl.g:4720:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,105,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4725:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4726:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4726:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4727:5: lv_value_3_0= ruleValue
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
    // InternalKdl.g:4748:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4748:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4749:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalKdl.g:4755:1: ruleFunction returns [EObject current=null] : ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token lv_mediated_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token lv_variable_9_0=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token lv_variable_15_0=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token lv_variable_22_0=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_parameters_4_0 = null;

        EObject lv_urn_6_0 = null;

        EObject lv_value_7_0 = null;

        EObject lv_classification_12_0 = null;

        EObject lv_chain_17_0 = null;

        EObject lv_chain_19_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4761:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) ) )
            // InternalKdl.g:4762:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) )
            {
            // InternalKdl.g:4762:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) )
            int alt99=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_LOWERCASE_ID:
            case RULE_INT:
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
            case 34:
            case 94:
            case 95:
            case 99:
            case 114:
                {
                alt99=1;
                }
                break;
            case 107:
                {
                alt99=2;
                }
                break;
            case 74:
                {
                alt99=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }

            switch (alt99) {
                case 1 :
                    // InternalKdl.g:4763:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4763:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4764:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4764:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==RULE_LOWERCASE_ID) ) {
                        int LA92_1 = input.LA(2);

                        if ( (LA92_1==106) ) {
                            alt92=1;
                        }
                    }
                    switch (alt92) {
                        case 1 :
                            // InternalKdl.g:4765:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4765:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4766:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4766:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4767:7: lv_mediated_0_0= RULE_LOWERCASE_ID
                            {
                            lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_77); if (state.failed) return current;
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

                            otherlv_1=(Token)match(input,106,FOLLOW_78); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4788:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )
                    int alt94=3;
                    alt94 = dfa94.predict(input);
                    switch (alt94) {
                        case 1 :
                            // InternalKdl.g:4789:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            {
                            // InternalKdl.g:4789:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            // InternalKdl.g:4790:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
                            {
                            // InternalKdl.g:4790:6: ( (lv_name_2_0= rulePathName ) )
                            // InternalKdl.g:4791:7: (lv_name_2_0= rulePathName )
                            {
                            // InternalKdl.g:4791:7: (lv_name_2_0= rulePathName )
                            // InternalKdl.g:4792:8: lv_name_2_0= rulePathName
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_65);
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

                            otherlv_3=(Token)match(input,74,FOLLOW_67); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_1_0_1());
                              					
                            }
                            // InternalKdl.g:4813:6: ( (lv_parameters_4_0= ruleParameterList ) )?
                            int alt93=2;
                            int LA93_0 = input.LA(1);

                            if ( ((LA93_0>=RULE_STRING && LA93_0<=RULE_UPPERCASE_ID)||(LA93_0>=RULE_CAMELCASE_ID && LA93_0<=RULE_ID)||LA93_0==28||LA93_0==34||LA93_0==74||(LA93_0>=94 && LA93_0<=95)||LA93_0==99||LA93_0==107||LA93_0==114) ) {
                                alt93=1;
                            }
                            switch (alt93) {
                                case 1 :
                                    // InternalKdl.g:4814:7: (lv_parameters_4_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4814:7: (lv_parameters_4_0= ruleParameterList )
                                    // InternalKdl.g:4815:8: lv_parameters_4_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_53);
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

                            otherlv_5=(Token)match(input,75,FOLLOW_79); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4838:5: ( (lv_urn_6_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4838:5: ( (lv_urn_6_0= ruleUrn ) )
                            // InternalKdl.g:4839:6: (lv_urn_6_0= ruleUrn )
                            {
                            // InternalKdl.g:4839:6: (lv_urn_6_0= ruleUrn )
                            // InternalKdl.g:4840:7: lv_urn_6_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_79);
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
                            // InternalKdl.g:4858:5: ( (lv_value_7_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4858:5: ( (lv_value_7_0= ruleLiteral ) )
                            // InternalKdl.g:4859:6: (lv_value_7_0= ruleLiteral )
                            {
                            // InternalKdl.g:4859:6: (lv_value_7_0= ruleLiteral )
                            // InternalKdl.g:4860:7: lv_value_7_0= ruleLiteral
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getValueLiteralParserRuleCall_0_1_2_0());
                              						
                            }
                            pushFollow(FOLLOW_79);
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

                    // InternalKdl.g:4878:4: (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==38) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // InternalKdl.g:4879:5: otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_8=(Token)match(input,38,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getAsKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4883:5: ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4884:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4884:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4885:7: lv_variable_9_0= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:4904:3: (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4904:3: (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4905:4: otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    otherlv_10=(Token)match(input,107,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getFunctionAccess().getClassifyKeyword_1_0());
                      			
                    }
                    otherlv_11=(Token)match(input,74,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_11, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:4913:4: ( (lv_classification_12_0= ruleClassification ) )
                    // InternalKdl.g:4914:5: (lv_classification_12_0= ruleClassification )
                    {
                    // InternalKdl.g:4914:5: (lv_classification_12_0= ruleClassification )
                    // InternalKdl.g:4915:6: lv_classification_12_0= ruleClassification
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getClassificationClassificationParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
                    lv_classification_12_0=ruleClassification();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getFunctionRule());
                      						}
                      						set(
                      							current,
                      							"classification",
                      							lv_classification_12_0,
                      							"org.integratedmodelling.kdl.Kdl.Classification");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_13=(Token)match(input,75,FOLLOW_79); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }
                    // InternalKdl.g:4936:4: (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )?
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==38) ) {
                        alt96=1;
                    }
                    switch (alt96) {
                        case 1 :
                            // InternalKdl.g:4937:5: otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_14=(Token)match(input,38,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_14, grammarAccess.getFunctionAccess().getAsKeyword_1_4_0());
                              				
                            }
                            // InternalKdl.g:4941:5: ( (lv_variable_15_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4942:6: (lv_variable_15_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4942:6: (lv_variable_15_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4943:7: lv_variable_15_0= RULE_LOWERCASE_ID
                            {
                            lv_variable_15_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_variable_15_0, grammarAccess.getFunctionAccess().getVariableLOWERCASE_IDTerminalRuleCall_1_4_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"variable",
                              								lv_variable_15_0,
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
                case 3 :
                    // InternalKdl.g:4962:3: (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4962:3: (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4963:4: otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    otherlv_16=(Token)match(input,74,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:4967:4: ( (lv_chain_17_0= ruleFunction ) )
                    // InternalKdl.g:4968:5: (lv_chain_17_0= ruleFunction )
                    {
                    // InternalKdl.g:4968:5: (lv_chain_17_0= ruleFunction )
                    // InternalKdl.g:4969:6: lv_chain_17_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_66);
                    lv_chain_17_0=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getFunctionRule());
                      						}
                      						add(
                      							current,
                      							"chain",
                      							lv_chain_17_0,
                      							"org.integratedmodelling.kdl.Kdl.Function");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:4986:4: (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )*
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( (LA97_0==28) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // InternalKdl.g:4987:5: otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) )
                    	    {
                    	    otherlv_18=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_18, grammarAccess.getFunctionAccess().getCommaKeyword_2_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:4991:5: ( (lv_chain_19_0= ruleFunction ) )
                    	    // InternalKdl.g:4992:6: (lv_chain_19_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:4992:6: (lv_chain_19_0= ruleFunction )
                    	    // InternalKdl.g:4993:7: lv_chain_19_0= ruleFunction
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_2_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_66);
                    	    lv_chain_19_0=ruleFunction();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"chain",
                    	      								lv_chain_19_0,
                    	      								"org.integratedmodelling.kdl.Kdl.Function");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop97;
                        }
                    } while (true);

                    otherlv_20=(Token)match(input,75,FOLLOW_79); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_2_3());
                      			
                    }
                    // InternalKdl.g:5015:4: (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==38) ) {
                        alt98=1;
                    }
                    switch (alt98) {
                        case 1 :
                            // InternalKdl.g:5016:5: otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_21=(Token)match(input,38,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_21, grammarAccess.getFunctionAccess().getAsKeyword_2_4_0());
                              				
                            }
                            // InternalKdl.g:5020:5: ( (lv_variable_22_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:5021:6: (lv_variable_22_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:5021:6: (lv_variable_22_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:5022:7: lv_variable_22_0= RULE_LOWERCASE_ID
                            {
                            lv_variable_22_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_variable_22_0, grammarAccess.getFunctionAccess().getVariableLOWERCASE_IDTerminalRuleCall_2_4_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getFunctionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"variable",
                              								lv_variable_22_0,
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
    // InternalKdl.g:5044:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:5044:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:5045:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKdl.g:5051:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) ;
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
            // InternalKdl.g:5057:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) )
            // InternalKdl.g:5058:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            {
            // InternalKdl.g:5058:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            int alt101=3;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
            case RULE_CAMELCASE_ID:
                {
                alt101=1;
                }
                break;
            case RULE_INT:
            case 34:
            case 114:
                {
                alt101=2;
                }
                break;
            case 74:
                {
                alt101=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // InternalKdl.g:5059:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    {
                    // InternalKdl.g:5059:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    // InternalKdl.g:5060:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKdl.g:5060:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    // InternalKdl.g:5061:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    {
                    // InternalKdl.g:5061:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==RULE_CAMELCASE_ID) ) {
                        alt100=1;
                    }
                    else if ( (LA100_0==RULE_LOWERCASE_ID) ) {
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
                            // InternalKdl.g:5062:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:5077:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5095:3: ( (lv_num_1_0= ruleNumber ) )
                    {
                    // InternalKdl.g:5095:3: ( (lv_num_1_0= ruleNumber ) )
                    // InternalKdl.g:5096:4: (lv_num_1_0= ruleNumber )
                    {
                    // InternalKdl.g:5096:4: (lv_num_1_0= ruleNumber )
                    // InternalKdl.g:5097:5: lv_num_1_0= ruleNumber
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
                    // InternalKdl.g:5115:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    {
                    // InternalKdl.g:5115:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    // InternalKdl.g:5116:4: otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,74,FOLLOW_80); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:5120:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKdl.g:5121:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKdl.g:5121:5: (lv_unit_3_0= ruleUnit )
                    // InternalKdl.g:5122:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
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

                    otherlv_4=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:5148:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:5148:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:5149:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKdl.g:5155:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKdl.g:5161:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:5162:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:5162:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt102=6;
            switch ( input.LA(1) ) {
            case 108:
                {
                alt102=1;
                }
                break;
            case 109:
                {
                alt102=2;
                }
                break;
            case 105:
                {
                alt102=3;
                }
                break;
            case 110:
                {
                alt102=4;
                }
                break;
            case 111:
                {
                alt102=5;
                }
                break;
            case 112:
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
                    // InternalKdl.g:5163:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:5163:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:5164:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:5164:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:5165:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,108,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5178:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:5178:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:5179:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:5179:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:5180:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,109,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5193:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:5193:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:5194:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:5194:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:5195:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,105,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5208:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:5208:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:5209:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:5209:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:5210:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,110,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5223:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:5223:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:5224:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:5224:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:5225:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,111,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5238:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:5238:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:5239:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:5239:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:5240:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,112,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:5256:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:5256:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:5257:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKdl.g:5263:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5269:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:5270:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:5270:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:5271:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:5271:3: ()
            // InternalKdl.g:5272:4: 
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

            // InternalKdl.g:5281:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt103=2;
            alt103 = dfa103.predict(input);
            switch (alt103) {
                case 1 :
                    // InternalKdl.g:5282:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:5282:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:5283:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_81);
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

            // InternalKdl.g:5300:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==56||LA104_0==102||LA104_0==117) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // InternalKdl.g:5301:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:5301:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:5302:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:5308:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:5309:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:5309:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:5310:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_82);
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

            	    // InternalKdl.g:5328:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:5329:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:5329:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:5330:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_81);
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
            	    break loop104;
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


    // $ANTLR start "entryRuleCurrency"
    // InternalKdl.g:5352:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKdl.g:5352:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKdl.g:5353:2: iv_ruleCurrency= ruleCurrency EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCurrencyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCurrency=ruleCurrency();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCurrency; 
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
    // $ANTLR end "entryRuleCurrency"


    // $ANTLR start "ruleCurrency"
    // InternalKdl.g:5359:1: ruleCurrency returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token lv_concept_3_1=null;
        AntlrDatatypeRuleToken lv_concept_3_2 = null;



        	enterRule();

        try {
            // InternalKdl.g:5365:2: ( ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) ) )
            // InternalKdl.g:5366:2: ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) )
            {
            // InternalKdl.g:5366:2: ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) )
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==RULE_ID) ) {
                alt106=1;
            }
            else if ( (LA106_0==RULE_LOWERCASE_ID||LA106_0==RULE_CAMELCASE_ID) ) {
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
                    // InternalKdl.g:5367:3: ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) )
                    {
                    // InternalKdl.g:5367:3: ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) )
                    // InternalKdl.g:5368:4: ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5368:4: ( (lv_id_0_0= RULE_ID ) )
                    // InternalKdl.g:5369:5: (lv_id_0_0= RULE_ID )
                    {
                    // InternalKdl.g:5369:5: (lv_id_0_0= RULE_ID )
                    // InternalKdl.g:5370:6: lv_id_0_0= RULE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_ID,FOLLOW_83); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_id_0_0, grammarAccess.getCurrencyAccess().getIdIDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getCurrencyRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"id",
                      							lv_id_0_0,
                      							"org.eclipse.xtext.common.Terminals.ID");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:5386:4: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
                    // InternalKdl.g:5387:5: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
                    {
                    otherlv_1=(Token)match(input,113,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_0_1_0());
                      				
                    }
                    // InternalKdl.g:5391:5: ( (lv_year_2_0= RULE_INT ) )
                    // InternalKdl.g:5392:6: (lv_year_2_0= RULE_INT )
                    {
                    // InternalKdl.g:5392:6: (lv_year_2_0= RULE_INT )
                    // InternalKdl.g:5393:7: lv_year_2_0= RULE_INT
                    {
                    lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_year_2_0, grammarAccess.getCurrencyAccess().getYearINTTerminalRuleCall_0_1_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getCurrencyRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"year",
                      								lv_year_2_0,
                      								"org.eclipse.xtext.common.Terminals.INT");
                      						
                    }

                    }


                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:5412:3: ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) )
                    {
                    // InternalKdl.g:5412:3: ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) )
                    // InternalKdl.g:5413:4: ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) )
                    {
                    // InternalKdl.g:5413:4: ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) )
                    // InternalKdl.g:5414:5: (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId )
                    {
                    // InternalKdl.g:5414:5: (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId )
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==RULE_CAMELCASE_ID) ) {
                        alt105=1;
                    }
                    else if ( (LA105_0==RULE_LOWERCASE_ID) ) {
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
                            // InternalKdl.g:5415:6: lv_concept_3_1= RULE_CAMELCASE_ID
                            {
                            lv_concept_3_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_concept_3_1, grammarAccess.getCurrencyAccess().getConceptCAMELCASE_IDTerminalRuleCall_1_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getCurrencyRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"concept",
                              							lv_concept_3_1,
                              							"org.integratedmodelling.kdl.Kdl.CAMELCASE_ID");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5430:6: lv_concept_3_2= ruleNamespaceId
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getCurrencyAccess().getConceptNamespaceIdParserRuleCall_1_0_1());
                              					
                            }
                            pushFollow(FOLLOW_2);
                            lv_concept_3_2=ruleNamespaceId();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getCurrencyRule());
                              						}
                              						set(
                              							current,
                              							"concept",
                              							lv_concept_3_2,
                              							"org.integratedmodelling.kdl.Kdl.NamespaceId");
                              						afterParserOrEnumRuleCall();
                              					
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
    // $ANTLR end "ruleCurrency"


    // $ANTLR start "entryRuleNumber"
    // InternalKdl.g:5452:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:5452:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:5453:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKdl.g:5459:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKdl.g:5465:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:5466:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:5466:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:5467:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:5467:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt107=3;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==34) ) {
                alt107=1;
            }
            else if ( (LA107_0==114) ) {
                alt107=2;
            }
            switch (alt107) {
                case 1 :
                    // InternalKdl.g:5468:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,34,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5473:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:5473:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:5474:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:5474:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:5475:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,114,FOLLOW_7); if (state.failed) return current;
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

            // InternalKdl.g:5488:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:5489:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:5493:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:5494:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_84); if (state.failed) return current;
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

            // InternalKdl.g:5510:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==103) && (synpred200_InternalKdl())) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalKdl.g:5511:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5524:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:5525:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5525:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:5526:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:5526:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:5527:7: lv_decimal_3_0= '.'
                    {
                    lv_decimal_3_0=(Token)match(input,103,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:5539:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:5540:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:5540:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:5541:7: lv_decimalPart_4_0= RULE_INT
                    {
                    lv_decimalPart_4_0=(Token)match(input,RULE_INT,FOLLOW_85); if (state.failed) return current;
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

            // InternalKdl.g:5559:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==115) && (synpred204_InternalKdl())) {
                alt111=1;
            }
            else if ( (LA111_0==116) && (synpred204_InternalKdl())) {
                alt111=1;
            }
            switch (alt111) {
                case 1 :
                    // InternalKdl.g:5560:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5586:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:5587:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5587:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:5588:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:5588:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:5589:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:5589:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt109=2;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==115) ) {
                        alt109=1;
                    }
                    else if ( (LA109_0==116) ) {
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
                            // InternalKdl.g:5590:8: lv_exponential_5_1= 'e'
                            {
                            lv_exponential_5_1=(Token)match(input,115,FOLLOW_58); if (state.failed) return current;
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
                            // InternalKdl.g:5601:8: lv_exponential_5_2= 'E'
                            {
                            lv_exponential_5_2=(Token)match(input,116,FOLLOW_58); if (state.failed) return current;
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

                    // InternalKdl.g:5614:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt110=3;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==34) ) {
                        alt110=1;
                    }
                    else if ( (LA110_0==114) ) {
                        alt110=2;
                    }
                    switch (alt110) {
                        case 1 :
                            // InternalKdl.g:5615:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,34,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5620:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:5620:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:5621:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:5621:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:5622:8: lv_expNegative_7_0= '-'
                            {
                            lv_expNegative_7_0=(Token)match(input,114,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:5635:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:5636:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:5636:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:5637:7: lv_exp_8_0= RULE_INT
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
    // InternalKdl.g:5659:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:5659:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:5660:2: iv_rulePathName= rulePathName EOF
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
    // InternalKdl.g:5666:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5672:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5673:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5673:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5674:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_86); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5681:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop112:
            do {
                int alt112=2;
                int LA112_0 = input.LA(1);

                if ( (LA112_0==103) ) {
                    int LA112_2 = input.LA(2);

                    if ( (LA112_2==RULE_LOWERCASE_ID) ) {
                        alt112=1;
                    }


                }


                switch (alt112) {
            	case 1 :
            	    // InternalKdl.g:5682:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_86); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
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
    // $ANTLR end "rulePathName"


    // $ANTLR start "entryRulePath"
    // InternalKdl.g:5699:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:5699:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:5700:2: iv_rulePath= rulePath EOF
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
    // InternalKdl.g:5706:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:5712:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5713:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5713:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5714:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_87); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5721:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop114:
            do {
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==102) ) {
                    int LA114_2 = input.LA(2);

                    if ( (LA114_2==RULE_LOWERCASE_ID) ) {
                        int LA114_4 = input.LA(3);

                        if ( (synpred210_InternalKdl()) ) {
                            alt114=1;
                        }


                    }


                }
                else if ( (LA114_0==103) ) {
                    alt114=1;
                }


                switch (alt114) {
            	case 1 :
            	    // InternalKdl.g:5722:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:5722:4: (kw= '.' | kw= '/' )
            	    int alt113=2;
            	    int LA113_0 = input.LA(1);

            	    if ( (LA113_0==103) ) {
            	        alt113=1;
            	    }
            	    else if ( (LA113_0==102) ) {
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
            	            // InternalKdl.g:5723:5: kw= '.'
            	            {
            	            kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:5729:5: kw= '/'
            	            {
            	            kw=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_87); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_3, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop114;
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
    // InternalKdl.g:5747:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5747:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5748:2: iv_ruleJavaClass= ruleJavaClass EOF
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
    // InternalKdl.g:5754:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5760:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5761:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5761:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5762:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getJavaClassAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_88);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,103,FOLLOW_89); if (state.failed) return current;
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
    // InternalKdl.g:5788:1: entryRuleNamespaceId returns [String current=null] : iv_ruleNamespaceId= ruleNamespaceId EOF ;
    public final String entryRuleNamespaceId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNamespaceId = null;


        try {
            // InternalKdl.g:5788:51: (iv_ruleNamespaceId= ruleNamespaceId EOF )
            // InternalKdl.g:5789:2: iv_ruleNamespaceId= ruleNamespaceId EOF
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
    // InternalKdl.g:5795:1: ruleNamespaceId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleNamespaceId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5801:2: ( (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5802:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5802:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5803:3: this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getNamespaceIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_71);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,100,FOLLOW_89); if (state.failed) return current;
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
    // InternalKdl.g:5829:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5829:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5830:2: iv_rulePropertyId= rulePropertyId EOF
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
    // InternalKdl.g:5836:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5842:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5843:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5843:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5844:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPropertyIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_71);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,100,FOLLOW_90); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getPropertyIdAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:5859:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==RULE_LOWERCASE_ID) ) {
                alt115=1;
            }
            else if ( (LA115_0==RULE_LOWERCASE_DASHID) ) {
                alt115=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }
            switch (alt115) {
                case 1 :
                    // InternalKdl.g:5860:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5868:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
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
    // InternalKdl.g:5880:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5880:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5881:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKdl.g:5887:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKdl.g:5893:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:5894:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:5894:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:5895:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_91); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5902:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==103) ) {
                alt117=1;
            }
            switch (alt117) {
                case 1 :
                    // InternalKdl.g:5903:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,103,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_91); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKdl.g:5915:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt116=2;
                    int LA116_0 = input.LA(1);

                    if ( (LA116_0==103) ) {
                        alt116=1;
                    }
                    switch (alt116) {
                        case 1 :
                            // InternalKdl.g:5916:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,103,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_92); if (state.failed) return current;
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

            // InternalKdl.g:5930:3: (kw= '-' )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==114) ) {
                int LA118_1 = input.LA(2);

                if ( (synpred214_InternalKdl()) ) {
                    alt118=1;
                }
            }
            switch (alt118) {
                case 1 :
                    // InternalKdl.g:5931:4: kw= '-'
                    {
                    kw=(Token)match(input,114,FOLLOW_93); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:5937:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt119=3;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==RULE_LOWERCASE_ID) ) {
                int LA119_1 = input.LA(2);

                if ( (synpred215_InternalKdl()) ) {
                    alt119=1;
                }
            }
            else if ( (LA119_0==RULE_UPPERCASE_ID) ) {
                int LA119_2 = input.LA(2);

                if ( (synpred216_InternalKdl()) ) {
                    alt119=2;
                }
            }
            switch (alt119) {
                case 1 :
                    // InternalKdl.g:5938:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5946:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKdl.g:5958:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5964:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:5965:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:5965:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt120=3;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt120=1;
                }
                break;
            case 117:
                {
                alt120=2;
                }
                break;
            case 56:
                {
                alt120=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }

            switch (alt120) {
                case 1 :
                    // InternalKdl.g:5966:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:5966:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:5967:4: enumLiteral_0= '/'
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
                    // InternalKdl.g:5974:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:5974:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:5975:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,117,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:5982:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:5982:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:5983:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
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
        otherlv_1=(Token)match(input,17,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:113:9: ( ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) ) )
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        {
        // InternalKdl.g:114:10: ( (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId ) )
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        {
        // InternalKdl.g:115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )
        int alt121=2;
        alt121 = dfa121.predict(input);
        switch (alt121) {
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
        otherlv_3=(Token)match(input,18,FOLLOW_5); if (state.failed) return ;
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
        int cnt122=0;
        loop122:
        do {
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==18) && ((true))) {
                alt122=1;
            }


            switch (alt122) {
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
        	    otherlv_3=(Token)match(input,18,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:165:9: ( (lv_variables_4_0= ruleParameter ) )
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    {
        	    // InternalKdl.g:166:10: (lv_variables_4_0= ruleParameter )
        	    // InternalKdl.g:167:11: lv_variables_4_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getVariablesParameterParserRuleCall_0_1_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_94);
        	    lv_variables_4_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

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
        otherlv_5=(Token)match(input,19,FOLLOW_5); if (state.failed) return ;
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
        int cnt123=0;
        loop123:
        do {
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==19) && ((true))) {
                alt123=1;
            }


            switch (alt123) {
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
        	    otherlv_5=(Token)match(input,19,FOLLOW_5); if (state.failed) return ;
        	    // InternalKdl.g:199:9: ( (lv_constants_6_0= ruleParameter ) )
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    {
        	    // InternalKdl.g:200:10: (lv_constants_6_0= ruleParameter )
        	    // InternalKdl.g:201:11: lv_constants_6_0= ruleParameter
        	    {
        	    if ( state.backtracking==0 ) {

        	      											newCompositeNode(grammarAccess.getModelAccess().getConstantsParameterParserRuleCall_0_2_1_0());
        	      										
        	    }
        	    pushFollow(FOLLOW_95);
        	    lv_constants_6_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt123 >= 1 ) break loop123;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(123, input);
                    throw eee;
            }
            cnt123++;
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
        otherlv_7=(Token)match(input,20,FOLLOW_6); if (state.failed) return ;
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
        int cnt124=0;
        loop124:
        do {
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==20) && ((true))) {
                alt124=1;
            }


            switch (alt124) {
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
        	    otherlv_7=(Token)match(input,20,FOLLOW_6); if (state.failed) return ;
        	    // InternalKdl.g:233:9: ( (lv_authors_8_0= RULE_STRING ) )
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    {
        	    // InternalKdl.g:234:10: (lv_authors_8_0= RULE_STRING )
        	    // InternalKdl.g:235:11: lv_authors_8_0= RULE_STRING
        	    {
        	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_96); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt124 >= 1 ) break loop124;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(124, input);
                    throw eee;
            }
            cnt124++;
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
        otherlv_9=(Token)match(input,21,FOLLOW_7); if (state.failed) return ;
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
        otherlv_11=(Token)match(input,22,FOLLOW_7); if (state.failed) return ;
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
        otherlv_13=(Token)match(input,23,FOLLOW_5); if (state.failed) return ;
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
        otherlv_15=(Token)match(input,24,FOLLOW_8); if (state.failed) return ;
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
        otherlv_17=(Token)match(input,25,FOLLOW_6); if (state.failed) return ;
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
        otherlv_19=(Token)match(input,26,FOLLOW_3); if (state.failed) return ;
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
        otherlv_21=(Token)match(input,27,FOLLOW_9); if (state.failed) return ;
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
        loop125:
        do {
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==28) ) {
                alt125=1;
            }


            switch (alt125) {
        	case 1 :
        	    // InternalKdl.g:488:10: otherlv_23= ',' ( (lv_scale_24_0= ruleFunction ) )
        	    {
        	    otherlv_23=(Token)match(input,28,FOLLOW_9); if (state.failed) return ;
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
        	    break loop125;
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
        otherlv_25=(Token)match(input,29,FOLLOW_11); if (state.failed) return ;
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

    // $ANTLR start synpred47_InternalKdl
    public final void synpred47_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_geometry_8_0 = null;


        // InternalKdl.g:1292:4: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1292:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1292:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1293:5: {...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred47_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0)");
        }
        // InternalKdl.g:1293:109: ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1294:6: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0);
        // InternalKdl.g:1297:9: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1297:10: {...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred47_InternalKdl", "true");
        }
        // InternalKdl.g:1297:19: (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
        // InternalKdl.g:1297:20: otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) )
        {
        otherlv_7=(Token)match(input,50,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1301:9: ( (lv_geometry_8_0= ruleGeometry ) )
        // InternalKdl.g:1302:10: (lv_geometry_8_0= ruleGeometry )
        {
        // InternalKdl.g:1302:10: (lv_geometry_8_0= ruleGeometry )
        // InternalKdl.g:1303:11: lv_geometry_8_0= ruleGeometry
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_3_0_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_geometry_8_0=ruleGeometry();

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
    // $ANTLR end synpred47_InternalKdl

    // $ANTLR start synpred48_InternalKdl
    public final void synpred48_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        EObject lv_units_10_0 = null;


        // InternalKdl.g:1326:4: ( ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1326:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1326:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1327:5: {...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred48_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1)");
        }
        // InternalKdl.g:1327:109: ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1328:6: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1);
        // InternalKdl.g:1331:9: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
        // InternalKdl.g:1331:10: {...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred48_InternalKdl", "true");
        }
        // InternalKdl.g:1331:19: (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
        // InternalKdl.g:1331:20: otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) )
        {
        otherlv_9=(Token)match(input,51,FOLLOW_70); if (state.failed) return ;
        // InternalKdl.g:1335:9: ( (lv_units_10_0= ruleUnit ) )
        // InternalKdl.g:1336:10: (lv_units_10_0= ruleUnit )
        {
        // InternalKdl.g:1336:10: (lv_units_10_0= ruleUnit )
        // InternalKdl.g:1337:11: lv_units_10_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getDataflowBodyAccess().getUnitsUnitParserRuleCall_3_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_units_10_0=ruleUnit();

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
    // $ANTLR end synpred48_InternalKdl

    // $ANTLR start synpred49_InternalKdl
    public final void synpred49_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_11_0 = null;


        // InternalKdl.g:1365:10: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )
        // InternalKdl.g:1365:10: {...}? => ( (lv_computations_11_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred49_InternalKdl", "true");
        }
        // InternalKdl.g:1365:19: ( (lv_computations_11_0= ruleComputation ) )
        // InternalKdl.g:1365:20: (lv_computations_11_0= ruleComputation )
        {
        // InternalKdl.g:1365:20: (lv_computations_11_0= ruleComputation )
        // InternalKdl.g:1366:10: lv_computations_11_0= ruleComputation
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_3_2_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_computations_11_0=ruleComputation();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred49_InternalKdl

    // $ANTLR start synpred50_InternalKdl
    public final void synpred50_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_11_0 = null;


        // InternalKdl.g:1360:4: ( ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) )
        // InternalKdl.g:1360:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
        {
        // InternalKdl.g:1360:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
        // InternalKdl.g:1361:5: {...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred50_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2)");
        }
        // InternalKdl.g:1361:109: ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
        // InternalKdl.g:1362:6: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2);
        // InternalKdl.g:1365:9: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
        int cnt146=0;
        loop146:
        do {
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( (LA146_0==55) && ((true))) {
                alt146=1;
            }


            switch (alt146) {
        	case 1 :
        	    // InternalKdl.g:1365:10: {...}? => ( (lv_computations_11_0= ruleComputation ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred50_InternalKdl", "true");
        	    }
        	    // InternalKdl.g:1365:19: ( (lv_computations_11_0= ruleComputation ) )
        	    // InternalKdl.g:1365:20: (lv_computations_11_0= ruleComputation )
        	    {
        	    // InternalKdl.g:1365:20: (lv_computations_11_0= ruleComputation )
        	    // InternalKdl.g:1366:10: lv_computations_11_0= ruleComputation
        	    {
        	    if ( state.backtracking==0 ) {

        	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_3_2_0());
        	      									
        	    }
        	    pushFollow(FOLLOW_97);
        	    lv_computations_11_0=ruleComputation();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt146 >= 1 ) break loop146;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(146, input);
                    throw eee;
            }
            cnt146++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred50_InternalKdl

    // $ANTLR start synpred51_InternalKdl
    public final void synpred51_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_12=null;
        EObject lv_semantics_13_0 = null;


        // InternalKdl.g:1388:4: ( ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) )
        // InternalKdl.g:1388:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
        {
        // InternalKdl.g:1388:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
        // InternalKdl.g:1389:5: {...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred51_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3)");
        }
        // InternalKdl.g:1389:109: ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
        // InternalKdl.g:1390:6: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3);
        // InternalKdl.g:1393:9: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
        // InternalKdl.g:1393:10: {...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred51_InternalKdl", "true");
        }
        // InternalKdl.g:1393:19: (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
        // InternalKdl.g:1393:20: otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) )
        {
        otherlv_12=(Token)match(input,52,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:1397:9: ( (lv_semantics_13_0= ruleObservableSemantics ) )
        // InternalKdl.g:1398:10: (lv_semantics_13_0= ruleObservableSemantics )
        {
        // InternalKdl.g:1398:10: (lv_semantics_13_0= ruleObservableSemantics )
        // InternalKdl.g:1399:11: lv_semantics_13_0= ruleObservableSemantics
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getDataflowBodyAccess().getSemanticsObservableSemanticsParserRuleCall_3_3_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_semantics_13_0=ruleObservableSemantics();

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
    // $ANTLR end synpred51_InternalKdl

    // $ANTLR start synpred52_InternalKdl
    public final void synpred52_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        EObject lv_metadata_15_0 = null;


        // InternalKdl.g:1428:10: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )
        // InternalKdl.g:1428:10: otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) )
        {
        otherlv_14=(Token)match(input,53,FOLLOW_39); if (state.failed) return ;
        // InternalKdl.g:1432:10: ( (lv_metadata_15_0= ruleMetadata ) )
        // InternalKdl.g:1433:11: (lv_metadata_15_0= ruleMetadata )
        {
        // InternalKdl.g:1433:11: (lv_metadata_15_0= ruleMetadata )
        // InternalKdl.g:1434:12: lv_metadata_15_0= ruleMetadata
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_3_4_0_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_metadata_15_0=ruleMetadata();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred52_InternalKdl

    // $ANTLR start synpred53_InternalKdl
    public final void synpred53_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        AntlrDatatypeRuleToken lv_javaClass_17_0 = null;


        // InternalKdl.g:1453:10: (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )
        // InternalKdl.g:1453:10: otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) )
        {
        otherlv_16=(Token)match(input,54,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:1457:10: ( (lv_javaClass_17_0= ruleJavaClass ) )
        // InternalKdl.g:1458:11: (lv_javaClass_17_0= ruleJavaClass )
        {
        // InternalKdl.g:1458:11: (lv_javaClass_17_0= ruleJavaClass )
        // InternalKdl.g:1459:12: lv_javaClass_17_0= ruleJavaClass
        {
        if ( state.backtracking==0 ) {

          												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_3_4_1_1_0());
          											
        }
        pushFollow(FOLLOW_2);
        lv_javaClass_17_0=ruleJavaClass();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred53_InternalKdl

    // $ANTLR start synpred54_InternalKdl
    public final void synpred54_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject lv_metadata_15_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_17_0 = null;


        // InternalKdl.g:1422:4: ( ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )
        // InternalKdl.g:1422:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
        {
        // InternalKdl.g:1422:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
        // InternalKdl.g:1423:5: {...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred54_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4)");
        }
        // InternalKdl.g:1423:109: ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
        // InternalKdl.g:1424:6: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4);
        // InternalKdl.g:1427:9: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
        // InternalKdl.g:1427:10: {...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred54_InternalKdl", "true");
        }
        // InternalKdl.g:1427:19: ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
        // InternalKdl.g:1427:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
        {
        // InternalKdl.g:1427:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )?
        int alt147=2;
        int LA147_0 = input.LA(1);

        if ( (LA147_0==53) ) {
            alt147=1;
        }
        switch (alt147) {
            case 1 :
                // InternalKdl.g:1428:10: otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) )
                {
                otherlv_14=(Token)match(input,53,FOLLOW_39); if (state.failed) return ;
                // InternalKdl.g:1432:10: ( (lv_metadata_15_0= ruleMetadata ) )
                // InternalKdl.g:1433:11: (lv_metadata_15_0= ruleMetadata )
                {
                // InternalKdl.g:1433:11: (lv_metadata_15_0= ruleMetadata )
                // InternalKdl.g:1434:12: lv_metadata_15_0= ruleMetadata
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_3_4_0_1_0());
                  											
                }
                pushFollow(FOLLOW_98);
                lv_metadata_15_0=ruleMetadata();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:1452:9: (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
        int alt148=2;
        int LA148_0 = input.LA(1);

        if ( (LA148_0==54) ) {
            alt148=1;
        }
        switch (alt148) {
            case 1 :
                // InternalKdl.g:1453:10: otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) )
                {
                otherlv_16=(Token)match(input,54,FOLLOW_3); if (state.failed) return ;
                // InternalKdl.g:1457:10: ( (lv_javaClass_17_0= ruleJavaClass ) )
                // InternalKdl.g:1458:11: (lv_javaClass_17_0= ruleJavaClass )
                {
                // InternalKdl.g:1458:11: (lv_javaClass_17_0= ruleJavaClass )
                // InternalKdl.g:1459:12: lv_javaClass_17_0= ruleJavaClass
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_3_4_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_javaClass_17_0=ruleJavaClass();

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
    // $ANTLR end synpred54_InternalKdl

    // $ANTLR start synpred75_InternalKdl
    public final void synpred75_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2158:5: ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )
        // InternalKdl.g:2158:6: ( 'in' ( ( ruleSimpleConceptDeclaration ) ) )
        {
        // InternalKdl.g:2158:6: ( 'in' ( ( ruleSimpleConceptDeclaration ) ) )
        // InternalKdl.g:2159:6: 'in' ( ( ruleSimpleConceptDeclaration ) )
        {
        match(input,70,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:2160:6: ( ( ruleSimpleConceptDeclaration ) )
        // InternalKdl.g:2161:7: ( ruleSimpleConceptDeclaration )
        {
        // InternalKdl.g:2161:7: ( ruleSimpleConceptDeclaration )
        // InternalKdl.g:2162:8: ruleSimpleConceptDeclaration
        {
        pushFollow(FOLLOW_2);
        ruleSimpleConceptDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred75_InternalKdl

    // $ANTLR start synpred79_InternalKdl
    public final void synpred79_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2300:5: ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )
        // InternalKdl.g:2300:6: ( 'over' ( ( ruleSimpleConceptDeclaration ) ) )
        {
        // InternalKdl.g:2300:6: ( 'over' ( ( ruleSimpleConceptDeclaration ) ) )
        // InternalKdl.g:2301:6: 'over' ( ( ruleSimpleConceptDeclaration ) )
        {
        match(input,39,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:2302:6: ( ( ruleSimpleConceptDeclaration ) )
        // InternalKdl.g:2303:7: ( ruleSimpleConceptDeclaration )
        {
        // InternalKdl.g:2303:7: ( ruleSimpleConceptDeclaration )
        // InternalKdl.g:2304:8: ruleSimpleConceptDeclaration
        {
        pushFollow(FOLLOW_2);
        ruleSimpleConceptDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred79_InternalKdl

    // $ANTLR start synpred82_InternalKdl
    public final void synpred82_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_2=null;
        EObject lv_by_3_0 = null;


        // InternalKdl.g:2461:4: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) )
        // InternalKdl.g:2461:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
        {
        // InternalKdl.g:2461:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
        // InternalKdl.g:2462:5: {...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred82_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0)");
        }
        // InternalKdl.g:2462:116: ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
        // InternalKdl.g:2463:6: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0);
        // InternalKdl.g:2466:9: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
        // InternalKdl.g:2466:10: {...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred82_InternalKdl", "true");
        }
        // InternalKdl.g:2466:19: (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
        // InternalKdl.g:2466:20: otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) )
        {
        otherlv_2=(Token)match(input,61,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:2470:9: ( (lv_by_3_0= ruleConcept ) )
        // InternalKdl.g:2471:10: (lv_by_3_0= ruleConcept )
        {
        // InternalKdl.g:2471:10: (lv_by_3_0= ruleConcept )
        // InternalKdl.g:2472:11: lv_by_3_0= ruleConcept
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getByConceptParserRuleCall_1_0_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_by_3_0=ruleConcept();

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
    // $ANTLR end synpred82_InternalKdl

    // $ANTLR start synpred84_InternalKdl
    public final void synpred84_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_downTo_6_1=null;
        AntlrDatatypeRuleToken lv_downTo_6_2 = null;


        // InternalKdl.g:2495:4: ( ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) )
        // InternalKdl.g:2495:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
        {
        // InternalKdl.g:2495:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
        // InternalKdl.g:2496:5: {...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred84_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1)");
        }
        // InternalKdl.g:2496:116: ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
        // InternalKdl.g:2497:6: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1);
        // InternalKdl.g:2500:9: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
        // InternalKdl.g:2500:10: {...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred84_InternalKdl", "true");
        }
        // InternalKdl.g:2500:19: (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
        // InternalKdl.g:2500:20: otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
        {
        otherlv_4=(Token)match(input,76,FOLLOW_52); if (state.failed) return ;
        otherlv_5=(Token)match(input,65,FOLLOW_55); if (state.failed) return ;
        // InternalKdl.g:2508:9: ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
        // InternalKdl.g:2509:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
        {
        // InternalKdl.g:2509:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
        // InternalKdl.g:2510:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
        {
        // InternalKdl.g:2510:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
        int alt161=2;
        int LA161_0 = input.LA(1);

        if ( (LA161_0==RULE_CAMELCASE_ID) ) {
            alt161=1;
        }
        else if ( (LA161_0==RULE_LOWERCASE_ID) ) {
            alt161=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 161, 0, input);

            throw nvae;
        }
        switch (alt161) {
            case 1 :
                // InternalKdl.g:2511:12: lv_downTo_6_1= RULE_CAMELCASE_ID
                {
                lv_downTo_6_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:2526:12: lv_downTo_6_2= ruleNamespaceId
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getObservableSemanticsAccess().getDownToNamespaceIdParserRuleCall_1_1_2_0_1());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_downTo_6_2=ruleNamespaceId();

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
    // $ANTLR end synpred84_InternalKdl

    // $ANTLR start synpred85_InternalKdl
    public final void synpred85_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_role_8_0 = null;


        // InternalKdl.g:2550:4: ( ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) )
        // InternalKdl.g:2550:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
        {
        // InternalKdl.g:2550:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
        // InternalKdl.g:2551:5: {...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred85_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2)");
        }
        // InternalKdl.g:2551:116: ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
        // InternalKdl.g:2552:6: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2);
        // InternalKdl.g:2555:9: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
        // InternalKdl.g:2555:10: {...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred85_InternalKdl", "true");
        }
        // InternalKdl.g:2555:19: (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
        // InternalKdl.g:2555:20: otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) )
        {
        otherlv_7=(Token)match(input,38,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:2559:9: ( (lv_role_8_0= ruleConcept ) )
        // InternalKdl.g:2560:10: (lv_role_8_0= ruleConcept )
        {
        // InternalKdl.g:2560:10: (lv_role_8_0= ruleConcept )
        // InternalKdl.g:2561:11: lv_role_8_0= ruleConcept
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getRoleConceptParserRuleCall_1_2_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_role_8_0=ruleConcept();

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
    // $ANTLR end synpred85_InternalKdl

    // $ANTLR start synpred86_InternalKdl
    public final void synpred86_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_unit_10_0 = null;


        // InternalKdl.g:2595:11: ( ( (lv_unit_10_0= ruleUnit ) ) )
        // InternalKdl.g:2595:11: ( (lv_unit_10_0= ruleUnit ) )
        {
        // InternalKdl.g:2595:11: ( (lv_unit_10_0= ruleUnit ) )
        // InternalKdl.g:2596:12: (lv_unit_10_0= ruleUnit )
        {
        // InternalKdl.g:2596:12: (lv_unit_10_0= ruleUnit )
        // InternalKdl.g:2597:13: lv_unit_10_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_0_1_0_0());
          												
        }
        pushFollow(FOLLOW_2);
        lv_unit_10_0=ruleUnit();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred86_InternalKdl

    // $ANTLR start synpred88_InternalKdl
    public final void synpred88_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        Token otherlv_12=null;
        EObject lv_unit_10_0 = null;

        EObject lv_currency_11_0 = null;

        EObject lv_unit_13_0 = null;


        // InternalKdl.g:2584:4: ( ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) )
        // InternalKdl.g:2584:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
        {
        // InternalKdl.g:2584:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:2585:5: {...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred88_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3)");
        }
        // InternalKdl.g:2585:116: ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:2586:6: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3);
        // InternalKdl.g:2589:9: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:2589:10: {...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred88_InternalKdl", "true");
        }
        // InternalKdl.g:2589:19: ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
        int alt164=2;
        int LA164_0 = input.LA(1);

        if ( (LA164_0==70) ) {
            alt164=1;
        }
        else if ( (LA164_0==77) ) {
            alt164=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 164, 0, input);

            throw nvae;
        }
        switch (alt164) {
            case 1 :
                // InternalKdl.g:2589:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
                {
                // InternalKdl.g:2589:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
                // InternalKdl.g:2590:10: otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
                {
                otherlv_9=(Token)match(input,70,FOLLOW_99); if (state.failed) return ;
                // InternalKdl.g:2594:10: ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
                int alt163=2;
                switch ( input.LA(1) ) {
                case RULE_CAMELCASE_ID:
                    {
                    int LA163_1 = input.LA(2);

                    if ( (synpred86_InternalKdl()) ) {
                        alt163=1;
                    }
                    else if ( (true) ) {
                        alt163=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 163, 1, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_LOWERCASE_ID:
                    {
                    int LA163_2 = input.LA(2);

                    if ( (LA163_2==100||LA163_2==103) ) {
                        alt163=2;
                    }
                    else if ( (LA163_2==EOF||LA163_2==56||LA163_2==102||LA163_2==117) ) {
                        alt163=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 163, 2, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case RULE_INT:
                case 34:
                case 56:
                case 74:
                case 102:
                case 114:
                case 117:
                    {
                    alt163=1;
                    }
                    break;
                case RULE_ID:
                    {
                    alt163=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 163, 0, input);

                    throw nvae;
                }

                switch (alt163) {
                    case 1 :
                        // InternalKdl.g:2595:11: ( (lv_unit_10_0= ruleUnit ) )
                        {
                        // InternalKdl.g:2595:11: ( (lv_unit_10_0= ruleUnit ) )
                        // InternalKdl.g:2596:12: (lv_unit_10_0= ruleUnit )
                        {
                        // InternalKdl.g:2596:12: (lv_unit_10_0= ruleUnit )
                        // InternalKdl.g:2597:13: lv_unit_10_0= ruleUnit
                        {
                        if ( state.backtracking==0 ) {

                          													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_0_1_0_0());
                          												
                        }
                        pushFollow(FOLLOW_2);
                        lv_unit_10_0=ruleUnit();

                        state._fsp--;
                        if (state.failed) return ;

                        }


                        }


                        }
                        break;
                    case 2 :
                        // InternalKdl.g:2615:11: ( (lv_currency_11_0= ruleCurrency ) )
                        {
                        // InternalKdl.g:2615:11: ( (lv_currency_11_0= ruleCurrency ) )
                        // InternalKdl.g:2616:12: (lv_currency_11_0= ruleCurrency )
                        {
                        // InternalKdl.g:2616:12: (lv_currency_11_0= ruleCurrency )
                        // InternalKdl.g:2617:13: lv_currency_11_0= ruleCurrency
                        {
                        if ( state.backtracking==0 ) {

                          													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getCurrencyCurrencyParserRuleCall_1_3_0_1_1_0());
                          												
                        }
                        pushFollow(FOLLOW_2);
                        lv_currency_11_0=ruleCurrency();

                        state._fsp--;
                        if (state.failed) return ;

                        }


                        }


                        }
                        break;

                }


                }


                }
                break;
            case 2 :
                // InternalKdl.g:2637:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
                {
                // InternalKdl.g:2637:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
                // InternalKdl.g:2638:10: otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) )
                {
                otherlv_12=(Token)match(input,77,FOLLOW_70); if (state.failed) return ;
                // InternalKdl.g:2642:10: ( (lv_unit_13_0= ruleUnit ) )
                // InternalKdl.g:2643:11: (lv_unit_13_0= ruleUnit )
                {
                // InternalKdl.g:2643:11: (lv_unit_13_0= ruleUnit )
                // InternalKdl.g:2644:12: lv_unit_13_0= ruleUnit
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_unit_13_0=ruleUnit();

                state._fsp--;
                if (state.failed) return ;

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
    // $ANTLR end synpred88_InternalKdl

    // $ANTLR start synpred89_InternalKdl
    public final void synpred89_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_15=null;
        EObject lv_from_14_0 = null;

        EObject lv_to_16_0 = null;


        // InternalKdl.g:2668:4: ( ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )
        // InternalKdl.g:2668:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
        {
        // InternalKdl.g:2668:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
        // InternalKdl.g:2669:5: {...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred89_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4)");
        }
        // InternalKdl.g:2669:116: ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
        // InternalKdl.g:2670:6: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4);
        // InternalKdl.g:2673:9: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
        // InternalKdl.g:2673:10: {...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred89_InternalKdl", "true");
        }
        // InternalKdl.g:2673:19: ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
        // InternalKdl.g:2673:20: ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) )
        {
        // InternalKdl.g:2673:20: ( (lv_from_14_0= ruleNumber ) )
        // InternalKdl.g:2674:10: (lv_from_14_0= ruleNumber )
        {
        // InternalKdl.g:2674:10: (lv_from_14_0= ruleNumber )
        // InternalKdl.g:2675:11: lv_from_14_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getFromNumberParserRuleCall_1_4_0_0());
          										
        }
        pushFollow(FOLLOW_52);
        lv_from_14_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_15=(Token)match(input,65,FOLLOW_58); if (state.failed) return ;
        // InternalKdl.g:2696:9: ( (lv_to_16_0= ruleNumber ) )
        // InternalKdl.g:2697:10: (lv_to_16_0= ruleNumber )
        {
        // InternalKdl.g:2697:10: (lv_to_16_0= ruleNumber )
        // InternalKdl.g:2698:11: lv_to_16_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getToNumberParserRuleCall_1_4_2_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_to_16_0=ruleNumber();

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
    // $ANTLR end synpred89_InternalKdl

    // $ANTLR start synpred91_InternalKdl
    public final void synpred91_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_main_1_0 = null;


        // InternalKdl.g:2769:4: ( (lv_main_1_0= ruleConcept ) )
        // InternalKdl.g:2769:4: (lv_main_1_0= ruleConcept )
        {
        // InternalKdl.g:2769:4: (lv_main_1_0= ruleConcept )
        // InternalKdl.g:2770:5: lv_main_1_0= ruleConcept
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getSimpleConceptDeclarationAccess().getMainConceptParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_main_1_0=ruleConcept();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred91_InternalKdl

    // $ANTLR start synpred117_InternalKdl
    public final void synpred117_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:3255:5: ( 'to' )
        // InternalKdl.g:3255:6: 'to'
        {
        match(input,65,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred117_InternalKdl

    // $ANTLR start synpred121_InternalKdl
    public final void synpred121_InternalKdl_fragment() throws RecognitionException {   
        Token lv_leftLimit_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_rightLimit_7_0=null;
        Token otherlv_8=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;


        // InternalKdl.g:3213:3: ( ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) )
        // InternalKdl.g:3213:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
        {
        // InternalKdl.g:3213:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
        // InternalKdl.g:3214:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
        {
        // InternalKdl.g:3214:4: ( (lv_int0_2_0= ruleNumber ) )
        // InternalKdl.g:3215:5: (lv_int0_2_0= ruleNumber )
        {
        // InternalKdl.g:3215:5: (lv_int0_2_0= ruleNumber )
        // InternalKdl.g:3216:6: lv_int0_2_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_int0_2_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:3233:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
        int alt167=3;
        int LA167_0 = input.LA(1);

        if ( (LA167_0==96) ) {
            alt167=1;
        }
        else if ( (LA167_0==97) ) {
            alt167=2;
        }
        switch (alt167) {
            case 1 :
                // InternalKdl.g:3234:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                {
                // InternalKdl.g:3234:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                // InternalKdl.g:3235:6: (lv_leftLimit_3_0= 'inclusive' )
                {
                // InternalKdl.g:3235:6: (lv_leftLimit_3_0= 'inclusive' )
                // InternalKdl.g:3236:7: lv_leftLimit_3_0= 'inclusive'
                {
                lv_leftLimit_3_0=(Token)match(input,96,FOLLOW_52); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKdl.g:3249:5: otherlv_4= 'exclusive'
                {
                otherlv_4=(Token)match(input,97,FOLLOW_52); if (state.failed) return ;

                }
                break;

        }

        // InternalKdl.g:3254:4: ( ( 'to' )=>otherlv_5= 'to' )
        // InternalKdl.g:3255:5: ( 'to' )=>otherlv_5= 'to'
        {
        otherlv_5=(Token)match(input,65,FOLLOW_58); if (state.failed) return ;

        }

        // InternalKdl.g:3261:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
        // InternalKdl.g:3262:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
        {
        // InternalKdl.g:3266:5: (lv_int1_6_0= ruleNumber )
        // InternalKdl.g:3267:6: lv_int1_6_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
          					
        }
        pushFollow(FOLLOW_64);
        lv_int1_6_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:3284:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
        int alt168=3;
        int LA168_0 = input.LA(1);

        if ( (LA168_0==96) ) {
            alt168=1;
        }
        else if ( (LA168_0==97) ) {
            alt168=2;
        }
        switch (alt168) {
            case 1 :
                // InternalKdl.g:3285:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                {
                // InternalKdl.g:3285:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                // InternalKdl.g:3286:6: (lv_rightLimit_7_0= 'inclusive' )
                {
                // InternalKdl.g:3286:6: (lv_rightLimit_7_0= 'inclusive' )
                // InternalKdl.g:3287:7: lv_rightLimit_7_0= 'inclusive'
                {
                lv_rightLimit_7_0=(Token)match(input,96,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKdl.g:3300:5: otherlv_8= 'exclusive'
                {
                otherlv_8=(Token)match(input,97,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred121_InternalKdl

    // $ANTLR start synpred122_InternalKdl
    public final void synpred122_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_num_9_0 = null;


        // InternalKdl.g:3307:3: ( ( (lv_num_9_0= ruleNumber ) ) )
        // InternalKdl.g:3307:3: ( (lv_num_9_0= ruleNumber ) )
        {
        // InternalKdl.g:3307:3: ( (lv_num_9_0= ruleNumber ) )
        // InternalKdl.g:3308:4: (lv_num_9_0= ruleNumber )
        {
        // InternalKdl.g:3308:4: (lv_num_9_0= ruleNumber )
        // InternalKdl.g:3309:5: lv_num_9_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getClassifierRHSAccess().getNumNumberParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_num_9_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred122_InternalKdl

    // $ANTLR start synpred124_InternalKdl
    public final void synpred124_InternalKdl_fragment() throws RecognitionException {   
        Token lv_string_12_0=null;

        // InternalKdl.g:3353:3: ( ( (lv_string_12_0= RULE_STRING ) ) )
        // InternalKdl.g:3353:3: ( (lv_string_12_0= RULE_STRING ) )
        {
        // InternalKdl.g:3353:3: ( (lv_string_12_0= RULE_STRING ) )
        // InternalKdl.g:3354:4: (lv_string_12_0= RULE_STRING )
        {
        // InternalKdl.g:3354:4: (lv_string_12_0= RULE_STRING )
        // InternalKdl.g:3355:5: lv_string_12_0= RULE_STRING
        {
        lv_string_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred124_InternalKdl

    // $ANTLR start synpred125_InternalKdl
    public final void synpred125_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_concept_13_0 = null;


        // InternalKdl.g:3372:3: ( ( (lv_concept_13_0= ruleConceptDeclaration ) ) )
        // InternalKdl.g:3372:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
        {
        // InternalKdl.g:3372:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
        // InternalKdl.g:3373:4: (lv_concept_13_0= ruleConceptDeclaration )
        {
        // InternalKdl.g:3373:4: (lv_concept_13_0= ruleConceptDeclaration )
        // InternalKdl.g:3374:5: lv_concept_13_0= ruleConceptDeclaration
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getClassifierRHSAccess().getConceptConceptDeclarationParserRuleCall_5_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_concept_13_0=ruleConceptDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred125_InternalKdl

    // $ANTLR start synpred128_InternalKdl
    public final void synpred128_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        EObject lv_toResolve_15_0 = null;

        EObject lv_toResolve_17_0 = null;


        // InternalKdl.g:3392:3: ( (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) )
        // InternalKdl.g:3392:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
        {
        // InternalKdl.g:3392:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
        // InternalKdl.g:3393:4: otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')'
        {
        otherlv_14=(Token)match(input,74,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:3397:4: ( (lv_toResolve_15_0= ruleConceptDeclaration ) )
        // InternalKdl.g:3398:5: (lv_toResolve_15_0= ruleConceptDeclaration )
        {
        // InternalKdl.g:3398:5: (lv_toResolve_15_0= ruleConceptDeclaration )
        // InternalKdl.g:3399:6: lv_toResolve_15_0= ruleConceptDeclaration
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_1_0());
          					
        }
        pushFollow(FOLLOW_66);
        lv_toResolve_15_0=ruleConceptDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:3416:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )*
        loop169:
        do {
            int alt169=2;
            int LA169_0 = input.LA(1);

            if ( (LA169_0==28) ) {
                alt169=1;
            }


            switch (alt169) {
        	case 1 :
        	    // InternalKdl.g:3417:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
        	    {
        	    // InternalKdl.g:3417:5: ( ( ',' )=>otherlv_16= ',' )
        	    // InternalKdl.g:3418:6: ( ',' )=>otherlv_16= ','
        	    {
        	    otherlv_16=(Token)match(input,28,FOLLOW_35); if (state.failed) return ;

        	    }

        	    // InternalKdl.g:3424:5: ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
        	    // InternalKdl.g:3425:6: (lv_toResolve_17_0= ruleConceptDeclaration )
        	    {
        	    // InternalKdl.g:3425:6: (lv_toResolve_17_0= ruleConceptDeclaration )
        	    // InternalKdl.g:3426:7: lv_toResolve_17_0= ruleConceptDeclaration
        	    {
        	    if ( state.backtracking==0 ) {

        	      							newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_2_1_0());
        	      						
        	    }
        	    pushFollow(FOLLOW_66);
        	    lv_toResolve_17_0=ruleConceptDeclaration();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop169;
            }
        } while (true);

        otherlv_18=(Token)match(input,75,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred128_InternalKdl

    // $ANTLR start synpred156_InternalKdl
    public final void synpred156_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:4243:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:4243:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:4243:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:4244:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:4244:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:4245:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
    // $ANTLR end synpred156_InternalKdl

    // $ANTLR start synpred157_InternalKdl
    public final void synpred157_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:4263:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:4263:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:4263:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:4264:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:4264:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:4265:5: lv_function_1_0= ruleFunction
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
    // $ANTLR end synpred157_InternalKdl

    // $ANTLR start synpred158_InternalKdl
    public final void synpred158_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;

        EObject lv_unit_3_0 = null;


        // InternalKdl.g:4283:3: ( ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) )
        // InternalKdl.g:4283:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
        {
        // InternalKdl.g:4283:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
        // InternalKdl.g:4284:4: ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) )
        {
        // InternalKdl.g:4284:4: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:4285:5: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:4285:5: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:4286:6: lv_urn_2_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_2_0_0());
          					
        }
        pushFollow(FOLLOW_70);
        lv_urn_2_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:4303:4: ( (lv_unit_3_0= ruleUnit ) )
        // InternalKdl.g:4304:5: (lv_unit_3_0= ruleUnit )
        {
        // InternalKdl.g:4304:5: (lv_unit_3_0= ruleUnit )
        // InternalKdl.g:4305:6: lv_unit_3_0= ruleUnit
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getValueAccess().getUnitUnitParserRuleCall_2_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_unit_3_0=ruleUnit();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred158_InternalKdl

    // $ANTLR start synpred159_InternalKdl
    public final void synpred159_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_currency_4_0 = null;


        // InternalKdl.g:4324:3: ( ( (lv_currency_4_0= ruleCurrency ) ) )
        // InternalKdl.g:4324:3: ( (lv_currency_4_0= ruleCurrency ) )
        {
        // InternalKdl.g:4324:3: ( (lv_currency_4_0= ruleCurrency ) )
        // InternalKdl.g:4325:4: (lv_currency_4_0= ruleCurrency )
        {
        // InternalKdl.g:4325:4: (lv_currency_4_0= ruleCurrency )
        // InternalKdl.g:4326:5: lv_currency_4_0= ruleCurrency
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getCurrencyCurrencyParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_currency_4_0=ruleCurrency();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred159_InternalKdl

    // $ANTLR start synpred160_InternalKdl
    public final void synpred160_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_5_0 = null;


        // InternalKdl.g:4344:3: ( ( (lv_list_5_0= ruleList ) ) )
        // InternalKdl.g:4344:3: ( (lv_list_5_0= ruleList ) )
        {
        // InternalKdl.g:4344:3: ( (lv_list_5_0= ruleList ) )
        // InternalKdl.g:4345:4: (lv_list_5_0= ruleList )
        {
        // InternalKdl.g:4345:4: (lv_list_5_0= ruleList )
        // InternalKdl.g:4346:5: lv_list_5_0= ruleList
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_4_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_list_5_0=ruleList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred160_InternalKdl

    // $ANTLR start synpred170_InternalKdl
    public final void synpred170_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;
        Token this_CAMELCASE_ID_4=null;
        Token this_LOWERCASE_ID_5=null;
        Token this_LOWERCASE_DASHID_6=null;

        // InternalKdl.g:4608:4: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )
        // InternalKdl.g:4608:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
        {
        kw=(Token)match(input,102,FOLLOW_75); if (state.failed) return ;
        // InternalKdl.g:4613:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
        int alt175=3;
        switch ( input.LA(1) ) {
        case RULE_CAMELCASE_ID:
            {
            alt175=1;
            }
            break;
        case RULE_LOWERCASE_ID:
            {
            alt175=2;
            }
            break;
        case RULE_LOWERCASE_DASHID:
            {
            alt175=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 175, 0, input);

            throw nvae;
        }

        switch (alt175) {
            case 1 :
                // InternalKdl.g:4614:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
                {
                this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:4622:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
                {
                this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKdl.g:4630:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
                {
                this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred170_InternalKdl

    // $ANTLR start synpred176_InternalKdl
    public final void synpred176_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_parameters_4_0 = null;


        // InternalKdl.g:4789:5: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) )
        // InternalKdl.g:4789:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        {
        // InternalKdl.g:4789:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        // InternalKdl.g:4790:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
        {
        // InternalKdl.g:4790:6: ( (lv_name_2_0= rulePathName ) )
        // InternalKdl.g:4791:7: (lv_name_2_0= rulePathName )
        {
        // InternalKdl.g:4791:7: (lv_name_2_0= rulePathName )
        // InternalKdl.g:4792:8: lv_name_2_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
          							
        }
        pushFollow(FOLLOW_65);
        lv_name_2_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_3=(Token)match(input,74,FOLLOW_67); if (state.failed) return ;
        // InternalKdl.g:4813:6: ( (lv_parameters_4_0= ruleParameterList ) )?
        int alt176=2;
        int LA176_0 = input.LA(1);

        if ( ((LA176_0>=RULE_STRING && LA176_0<=RULE_UPPERCASE_ID)||(LA176_0>=RULE_CAMELCASE_ID && LA176_0<=RULE_ID)||LA176_0==28||LA176_0==34||LA176_0==74||(LA176_0>=94 && LA176_0<=95)||LA176_0==99||LA176_0==107||LA176_0==114) ) {
            alt176=1;
        }
        switch (alt176) {
            case 1 :
                // InternalKdl.g:4814:7: (lv_parameters_4_0= ruleParameterList )
                {
                // InternalKdl.g:4814:7: (lv_parameters_4_0= ruleParameterList )
                // InternalKdl.g:4815:8: lv_parameters_4_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                  							
                }
                pushFollow(FOLLOW_53);
                lv_parameters_4_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_5=(Token)match(input,75,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred176_InternalKdl

    // $ANTLR start synpred177_InternalKdl
    public final void synpred177_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_6_0 = null;


        // InternalKdl.g:4838:5: ( ( (lv_urn_6_0= ruleUrn ) ) )
        // InternalKdl.g:4838:5: ( (lv_urn_6_0= ruleUrn ) )
        {
        // InternalKdl.g:4838:5: ( (lv_urn_6_0= ruleUrn ) )
        // InternalKdl.g:4839:6: (lv_urn_6_0= ruleUrn )
        {
        // InternalKdl.g:4839:6: (lv_urn_6_0= ruleUrn )
        // InternalKdl.g:4840:7: lv_urn_6_0= ruleUrn
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
    // $ANTLR end synpred177_InternalKdl

    // $ANTLR start synpred192_InternalKdl
    public final void synpred192_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_root_1_0 = null;


        // InternalKdl.g:5282:4: ( (lv_root_1_0= ruleUnitElement ) )
        // InternalKdl.g:5282:4: (lv_root_1_0= ruleUnitElement )
        {
        // InternalKdl.g:5282:4: (lv_root_1_0= ruleUnitElement )
        // InternalKdl.g:5283:5: lv_root_1_0= ruleUnitElement
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_root_1_0=ruleUnitElement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred192_InternalKdl

    // $ANTLR start synpred199_InternalKdl
    public final void synpred199_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5489:4: ( ( RULE_INT ) )
        // InternalKdl.g:5489:5: ( RULE_INT )
        {
        // InternalKdl.g:5489:5: ( RULE_INT )
        // InternalKdl.g:5490:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred199_InternalKdl

    // $ANTLR start synpred200_InternalKdl
    public final void synpred200_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5511:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5511:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5511:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:5512:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:5512:5: ( ( '.' ) )
        // InternalKdl.g:5513:6: ( '.' )
        {
        // InternalKdl.g:5513:6: ( '.' )
        // InternalKdl.g:5514:7: '.'
        {
        match(input,103,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:5517:5: ( ( RULE_INT ) )
        // InternalKdl.g:5518:6: ( RULE_INT )
        {
        // InternalKdl.g:5518:6: ( RULE_INT )
        // InternalKdl.g:5519:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred200_InternalKdl

    // $ANTLR start synpred204_InternalKdl
    public final void synpred204_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5560:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5560:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5560:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:5561:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:5561:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:5562:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:5562:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:5563:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=115 && input.LA(1)<=116) ) {
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

        // InternalKdl.g:5570:5: ( '+' | ( ( '-' ) ) )?
        int alt183=3;
        int LA183_0 = input.LA(1);

        if ( (LA183_0==34) ) {
            alt183=1;
        }
        else if ( (LA183_0==114) ) {
            alt183=2;
        }
        switch (alt183) {
            case 1 :
                // InternalKdl.g:5571:6: '+'
                {
                match(input,34,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5573:6: ( ( '-' ) )
                {
                // InternalKdl.g:5573:6: ( ( '-' ) )
                // InternalKdl.g:5574:7: ( '-' )
                {
                // InternalKdl.g:5574:7: ( '-' )
                // InternalKdl.g:5575:8: '-'
                {
                match(input,114,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:5579:5: ( ( RULE_INT ) )
        // InternalKdl.g:5580:6: ( RULE_INT )
        {
        // InternalKdl.g:5580:6: ( RULE_INT )
        // InternalKdl.g:5581:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred204_InternalKdl

    // $ANTLR start synpred210_InternalKdl
    public final void synpred210_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;

        // InternalKdl.g:5722:4: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )
        // InternalKdl.g:5722:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
        {
        // InternalKdl.g:5722:4: (kw= '.' | kw= '/' )
        int alt184=2;
        int LA184_0 = input.LA(1);

        if ( (LA184_0==103) ) {
            alt184=1;
        }
        else if ( (LA184_0==102) ) {
            alt184=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 184, 0, input);

            throw nvae;
        }
        switch (alt184) {
            case 1 :
                // InternalKdl.g:5723:5: kw= '.'
                {
                kw=(Token)match(input,103,FOLLOW_5); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5729:5: kw= '/'
                {
                kw=(Token)match(input,102,FOLLOW_5); if (state.failed) return ;

                }
                break;

        }

        this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred210_InternalKdl

    // $ANTLR start synpred214_InternalKdl
    public final void synpred214_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:5931:4: (kw= '-' )
        // InternalKdl.g:5931:4: kw= '-'
        {
        kw=(Token)match(input,114,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred214_InternalKdl

    // $ANTLR start synpred215_InternalKdl
    public final void synpred215_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:5938:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:5938:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred215_InternalKdl

    // $ANTLR start synpred216_InternalKdl
    public final void synpred216_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:5946:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:5946:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred216_InternalKdl

    // Delegated rules

    public final boolean synpred215_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred215_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred210_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred210_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred177_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred177_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred88_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred88_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred47_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred47_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred79_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred79_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred89_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred89_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred49_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred49_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred48_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred48_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred128_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred128_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred117_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred117_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred75_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred75_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred86_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred86_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred84_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred84_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred192_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred192_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred160_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred160_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred51_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred51_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred91_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred91_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred50_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_InternalKdl_fragment(); // can never throw exception
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


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA1 dfa1 = new DFA1(this);
    protected DFA33 dfa33 = new DFA33(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA67 dfa67 = new DFA67(this);
    protected DFA70 dfa70 = new DFA70(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA81 dfa81 = new DFA81(this);
    protected DFA94 dfa94 = new DFA94(this);
    protected DFA103 dfa103 = new DFA103(this);
    protected DFA121 dfa121 = new DFA121(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\21\15\uffff";
    static final String dfa_4s = "\1\127\15\uffff";
    static final String dfa_5s = "\1\uffff\1\15\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14";
    static final String dfa_6s = "\1\0\15\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\3\1\7\uffff\5\1\33\uffff\1\1\5\uffff\12\1",
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
                        if ( (LA6_0==EOF||(LA6_0>=30 && LA6_0<=32)||(LA6_0>=40 && LA6_0<=44)||LA6_0==72||(LA6_0>=78 && LA6_0<=87)) ) {s = 1;}

                        else if ( LA6_0 == 17 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 0) ) {s = 2;}

                        else if ( LA6_0 == 18 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 1) ) {s = 3;}

                        else if ( LA6_0 == 19 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 2) ) {s = 4;}

                        else if ( LA6_0 == 20 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 3) ) {s = 5;}

                        else if ( LA6_0 == 21 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 4) ) {s = 6;}

                        else if ( LA6_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 5) ) {s = 7;}

                        else if ( LA6_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 6) ) {s = 8;}

                        else if ( LA6_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 7) ) {s = 9;}

                        else if ( LA6_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 8) ) {s = 10;}

                        else if ( LA6_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 9) ) {s = 11;}

                        else if ( LA6_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 10) ) {s = 12;}

                        else if ( LA6_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getModelAccess().getUnorderedGroup_0(), 11) ) {s = 13;}

                         
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
    static final String dfa_10s = "\1\5\1\21\1\uffff\1\5\1\uffff\1\21";
    static final String dfa_11s = "\1\143\1\147\1\uffff\1\5\1\uffff\1\147";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\1\uffff";
    static final String dfa_13s = "\6\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\135\uffff\1\2",
            "\13\4\1\uffff\4\4\7\uffff\5\4\33\uffff\1\4\5\uffff\12\4\14\uffff\1\2\1\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\13\4\1\uffff\4\4\7\uffff\5\4\33\uffff\1\4\5\uffff\12\4\14\uffff\1\2\1\uffff\1\4\1\3"
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
    static final String dfa_15s = "\1\2\15\uffff";
    static final String dfa_16s = "\1\45\6\0\7\uffff";
    static final String dfa_17s = "\1\67\6\0\7\uffff";
    static final String dfa_18s = "\7\uffff\2\5\1\6\1\1\1\2\1\3\1\4";
    static final String dfa_19s = "\1\1\1\6\1\4\1\2\1\0\1\3\1\5\7\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\14\uffff\1\3\1\4\1\6\1\7\1\10\1\5",
            "\1\uffff",
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
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = dfa_1;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "()+ loopback of 1291:6: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA33_4 = input.LA(1);

                         
                        int index33_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred48_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1) ) {s = 11;}

                        else if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index33_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA33_0 = input.LA(1);

                         
                        int index33_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA33_0==37) ) {s = 1;}

                        else if ( (LA33_0==EOF) ) {s = 2;}

                        else if ( (LA33_0==50) ) {s = 3;}

                        else if ( (LA33_0==51) ) {s = 4;}

                        else if ( (LA33_0==55) ) {s = 5;}

                        else if ( (LA33_0==52) ) {s = 6;}

                        else if ( LA33_0 == 53 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 7;}

                        else if ( LA33_0 == 54 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index33_0);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA33_3 = input.LA(1);

                         
                        int index33_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred47_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0) ) {s = 10;}

                        else if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index33_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA33_5 = input.LA(1);

                         
                        int index33_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred50_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2) ) {s = 12;}

                        else if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index33_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA33_2 = input.LA(1);

                         
                        int index33_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()) ) {s = 9;}

                         
                        input.seek(index33_2);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA33_6 = input.LA(1);

                         
                        int index33_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred51_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3) ) {s = 13;}

                        else if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index33_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA33_1 = input.LA(1);

                         
                        int index33_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()) ) {s = 9;}

                         
                        input.seek(index33_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 33, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_21s = "\12\uffff";
    static final String dfa_22s = "\1\1\11\uffff";
    static final String dfa_23s = "\1\6\11\uffff";
    static final String dfa_24s = "\1\162\11\uffff";
    static final String dfa_25s = "\1\uffff\1\6\1\1\1\2\1\3\2\4\3\5";
    static final String dfa_26s = "\1\0\11\uffff}>";
    static final String[] dfa_27s = {
            "\1\11\27\uffff\3\1\1\uffff\1\7\2\uffff\1\1\1\4\1\uffff\5\1\5\uffff\6\1\5\uffff\1\2\10\uffff\1\5\1\uffff\1\1\3\uffff\1\3\1\6\12\1\32\uffff\1\10",
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

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA54 extends DFA {

        public DFA54(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 54;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "()* loopback of 2460:6: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA54_0 = input.LA(1);

                         
                        int index54_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_0==EOF||(LA54_0>=30 && LA54_0<=32)||LA54_0==37||(LA54_0>=40 && LA54_0<=44)||(LA54_0>=50 && LA54_0<=55)||LA54_0==72||(LA54_0>=78 && LA54_0<=87)) ) {s = 1;}

                        else if ( LA54_0 == 61 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0) ) {s = 2;}

                        else if ( LA54_0 == 76 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1) ) {s = 3;}

                        else if ( LA54_0 == 38 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2) ) {s = 4;}

                        else if ( LA54_0 == 70 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {s = 5;}

                        else if ( LA54_0 == 77 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {s = 6;}

                        else if ( LA54_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {s = 7;}

                        else if ( LA54_0 == 114 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {s = 8;}

                        else if ( LA54_0 == RULE_INT && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {s = 9;}

                         
                        input.seek(index54_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 54, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_28s = "\20\uffff";
    static final String dfa_29s = "\1\1\17\uffff";
    static final String dfa_30s = "\1\5\1\uffff\15\0\1\uffff";
    static final String dfa_31s = "\1\162\1\uffff\15\0\1\uffff";
    static final String dfa_32s = "\1\uffff\1\2\15\uffff\1\1";
    static final String dfa_33s = "\2\uffff\1\3\1\12\1\4\1\13\1\14\1\10\1\2\1\11\1\5\1\0\1\1\1\7\1\6\1\uffff}>";
    static final String[] dfa_34s = {
            "\1\5\1\1\3\uffff\1\4\21\uffff\1\1\1\uffff\3\1\1\uffff\1\1\2\uffff\10\1\5\uffff\6\1\1\uffff\2\1\1\3\1\uffff\1\1\1\6\1\7\1\10\1\1\1\uffff\1\11\1\12\1\13\1\1\1\14\1\2\1\15\1\16\15\1\3\uffff\3\1\24\uffff\1\1",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[][] dfa_34 = unpackEncodedStringArray(dfa_34s);

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "()+ loopback of 2768:3: ( (lv_main_1_0= ruleConcept ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_11 = input.LA(1);

                         
                        int index56_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_11);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA56_12 = input.LA(1);

                         
                        int index56_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA56_8 = input.LA(1);

                         
                        int index56_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_8);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA56_2 = input.LA(1);

                         
                        int index56_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_2);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA56_4 = input.LA(1);

                         
                        int index56_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA56_10 = input.LA(1);

                         
                        int index56_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA56_14 = input.LA(1);

                         
                        int index56_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_14);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA56_13 = input.LA(1);

                         
                        int index56_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_13);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA56_7 = input.LA(1);

                         
                        int index56_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_7);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA56_9 = input.LA(1);

                         
                        int index56_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA56_3 = input.LA(1);

                         
                        int index56_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_3);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA56_5 = input.LA(1);

                         
                        int index56_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_5);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA56_6 = input.LA(1);

                         
                        int index56_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred91_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index56_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 56, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_35s = "\41\uffff";
    static final String dfa_36s = "\1\4\2\uffff\3\0\1\uffff\1\0\14\uffff\1\0\14\uffff";
    static final String dfa_37s = "\1\162\2\uffff\3\0\1\uffff\1\0\14\uffff\1\0\14\uffff";
    static final String dfa_38s = "\1\uffff\1\1\4\uffff\1\4\1\uffff\1\6\14\uffff\1\10\5\uffff\1\11\1\12\1\2\1\3\1\5\1\7";
    static final String dfa_39s = "\3\uffff\1\0\1\1\1\2\1\uffff\1\3\14\uffff\1\4\14\uffff}>";
    static final String[] dfa_40s = {
            "\1\7\1\10\1\5\3\uffff\1\10\27\uffff\1\3\25\uffff\1\34\2\uffff\1\10\2\uffff\3\10\2\uffff\3\10\1\6\3\10\1\24\23\uffff\2\1\2\uffff\1\33\6\uffff\1\25\2\uffff\5\25\1\uffff\1\4",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
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
            "",
            "",
            "",
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
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[][] dfa_40 = unpackEncodedStringArray(dfa_40s);

    class DFA67 extends DFA {

        public DFA67(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 67;
            this.eot = dfa_35;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "3180:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA67_3 = input.LA(1);

                         
                        int index67_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 29;}

                        else if ( (synpred122_InternalKdl()) ) {s = 30;}

                         
                        input.seek(index67_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA67_4 = input.LA(1);

                         
                        int index67_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 29;}

                        else if ( (synpred122_InternalKdl()) ) {s = 30;}

                         
                        input.seek(index67_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA67_5 = input.LA(1);

                         
                        int index67_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred121_InternalKdl()) ) {s = 29;}

                        else if ( (synpred122_InternalKdl()) ) {s = 30;}

                         
                        input.seek(index67_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA67_7 = input.LA(1);

                         
                        int index67_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred124_InternalKdl()) ) {s = 31;}

                        else if ( (synpred125_InternalKdl()) ) {s = 8;}

                         
                        input.seek(index67_7);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA67_20 = input.LA(1);

                         
                        int index67_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 8;}

                        else if ( (synpred128_InternalKdl()) ) {s = 32;}

                         
                        input.seek(index67_20);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 67, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_41s = "\17\uffff";
    static final String dfa_42s = "\3\uffff\1\12\7\uffff\1\12\2\uffff\1\12";
    static final String dfa_43s = "\1\4\2\6\1\4\2\uffff\3\6\2\uffff\1\4\2\6\1\4";
    static final String dfa_44s = "\1\162\2\6\1\164\2\uffff\1\6\2\162\2\uffff\1\164\2\6\1\162";
    static final String dfa_45s = "\4\uffff\1\3\1\4\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_46s = "\17\uffff}>";
    static final String[] dfa_47s = {
            "\1\4\1\uffff\1\3\33\uffff\1\1\73\uffff\2\5\22\uffff\1\2",
            "\1\3",
            "\1\3",
            "\5\12\1\uffff\2\12\5\uffff\20\12\1\uffff\1\12\1\uffff\3\12\1\uffff\5\12\5\uffff\6\12\11\uffff\1\11\6\uffff\1\12\1\uffff\2\12\2\uffff\12\12\6\uffff\2\12\3\uffff\1\12\3\uffff\1\6\3\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "",
            "",
            "\1\13",
            "\1\16\33\uffff\1\14\117\uffff\1\15",
            "\1\16\33\uffff\1\14\117\uffff\1\15",
            "",
            "",
            "\5\12\1\uffff\2\12\5\uffff\20\12\1\uffff\1\12\1\uffff\3\12\1\uffff\5\12\5\uffff\6\12\11\uffff\1\11\6\uffff\1\12\1\uffff\2\12\2\uffff\12\12\6\uffff\2\12\3\uffff\1\12\7\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "\1\16",
            "\1\16",
            "\5\12\1\uffff\2\12\5\uffff\20\12\1\uffff\1\12\1\uffff\3\12\1\uffff\5\12\5\uffff\6\12\11\uffff\1\11\6\uffff\1\12\1\uffff\2\12\2\uffff\12\12\6\uffff\2\12\3\uffff\1\12\7\uffff\1\12\6\uffff\1\12"
    };

    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[][] dfa_47 = unpackEncodedStringArray(dfa_47s);

    class DFA70 extends DFA {

        public DFA70(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 70;
            this.eot = dfa_41;
            this.eof = dfa_42;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "3594:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_48s = "\21\uffff";
    static final String dfa_49s = "\3\uffff\1\13\11\uffff\1\13\2\uffff\1\13";
    static final String dfa_50s = "\1\4\2\6\1\4\4\uffff\3\6\2\uffff\1\4\2\6\1\4";
    static final String dfa_51s = "\1\162\2\6\1\164\4\uffff\1\6\2\162\2\uffff\1\164\2\6\1\162";
    static final String dfa_52s = "\4\uffff\1\3\1\4\1\5\1\6\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_53s = "\21\uffff}>";
    static final String[] dfa_54s = {
            "\1\4\1\uffff\1\3\4\uffff\1\6\20\uffff\1\7\5\uffff\1\1\73\uffff\2\5\22\uffff\1\2",
            "\1\3",
            "\1\3",
            "\5\13\1\uffff\2\13\5\uffff\20\13\1\uffff\1\13\1\uffff\2\13\2\uffff\5\13\5\uffff\6\13\11\uffff\1\14\6\uffff\1\13\1\uffff\2\13\2\uffff\12\13\6\uffff\2\13\3\uffff\1\13\3\uffff\1\10\3\uffff\1\13\6\uffff\1\13\1\11\1\12",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\20\33\uffff\1\16\117\uffff\1\17",
            "\1\20\33\uffff\1\16\117\uffff\1\17",
            "",
            "",
            "\5\13\1\uffff\2\13\5\uffff\20\13\1\uffff\1\13\1\uffff\2\13\2\uffff\5\13\5\uffff\6\13\11\uffff\1\14\6\uffff\1\13\1\uffff\2\13\2\uffff\12\13\6\uffff\2\13\3\uffff\1\13\7\uffff\1\13\6\uffff\1\13\1\11\1\12",
            "\1\20",
            "\1\20",
            "\5\13\1\uffff\2\13\5\uffff\20\13\1\uffff\1\13\1\uffff\2\13\2\uffff\5\13\5\uffff\6\13\11\uffff\1\14\6\uffff\1\13\1\uffff\2\13\2\uffff\12\13\6\uffff\2\13\3\uffff\1\13\7\uffff\1\13\6\uffff\1\13"
    };

    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final char[] dfa_51 = DFA.unpackEncodedStringToUnsignedChars(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[][] dfa_54 = unpackEncodedStringArray(dfa_54s);

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = dfa_48;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "3724:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )";
        }
    }
    static final String dfa_55s = "\23\uffff";
    static final String dfa_56s = "\1\4\7\0\1\uffff\4\0\1\uffff\1\0\4\uffff";
    static final String dfa_57s = "\1\162\7\0\1\uffff\4\0\1\uffff\1\0\4\uffff";
    static final String dfa_58s = "\10\uffff\1\1\4\uffff\1\2\1\uffff\1\6\1\3\1\4\1\5";
    static final String dfa_59s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\7\1\10\1\11\1\12\1\uffff\1\13\4\uffff}>";
    static final String[] dfa_60s = {
            "\1\4\1\11\1\3\1\14\1\17\1\uffff\1\13\1\7\20\uffff\1\10\5\uffff\1\1\47\uffff\1\16\23\uffff\1\5\1\6\3\uffff\1\12\7\uffff\1\15\6\uffff\1\2",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final char[] dfa_57 = DFA.unpackEncodedStringToUnsignedChars(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[] dfa_59 = DFA.unpackEncodedString(dfa_59s);
    static final short[][] dfa_60 = unpackEncodedStringArray(dfa_60s);

    class DFA81 extends DFA {

        public DFA81(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 81;
            this.eot = dfa_55;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_59;
            this.transition = dfa_60;
        }
        public String getDescription() {
            return "4242:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA81_1 = input.LA(1);

                         
                        int index81_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred157_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index81_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA81_2 = input.LA(1);

                         
                        int index81_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred157_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index81_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA81_3 = input.LA(1);

                         
                        int index81_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred157_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index81_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA81_4 = input.LA(1);

                         
                        int index81_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred157_InternalKdl()) ) {s = 13;}

                        else if ( (synpred158_InternalKdl()) ) {s = 16;}

                         
                        input.seek(index81_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA81_5 = input.LA(1);

                         
                        int index81_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred157_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index81_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA81_6 = input.LA(1);

                         
                        int index81_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred157_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index81_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA81_7 = input.LA(1);

                         
                        int index81_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred156_InternalKdl()) ) {s = 8;}

                        else if ( (synpred159_InternalKdl()) ) {s = 17;}

                         
                        input.seek(index81_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA81_9 = input.LA(1);

                         
                        int index81_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred157_InternalKdl()) ) {s = 13;}

                        else if ( (synpred158_InternalKdl()) ) {s = 16;}

                        else if ( (synpred159_InternalKdl()) ) {s = 17;}

                         
                        input.seek(index81_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA81_10 = input.LA(1);

                         
                        int index81_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred157_InternalKdl()) ) {s = 13;}

                        else if ( (synpred158_InternalKdl()) ) {s = 16;}

                         
                        input.seek(index81_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA81_11 = input.LA(1);

                         
                        int index81_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred157_InternalKdl()) ) {s = 13;}

                        else if ( (synpred158_InternalKdl()) ) {s = 16;}

                        else if ( (synpred159_InternalKdl()) ) {s = 17;}

                         
                        input.seek(index81_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA81_12 = input.LA(1);

                         
                        int index81_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred157_InternalKdl()) ) {s = 13;}

                        else if ( (synpred158_InternalKdl()) ) {s = 16;}

                         
                        input.seek(index81_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA81_14 = input.LA(1);

                         
                        int index81_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred157_InternalKdl()) ) {s = 13;}

                        else if ( (synpred160_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index81_14);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 81, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_61s = "\14\uffff";
    static final String dfa_62s = "\1\4\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_63s = "\1\162\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_64s = "\2\uffff\1\2\3\uffff\1\3\4\uffff\1\1";
    static final String dfa_65s = "\1\uffff\1\0\1\uffff\1\1\10\uffff}>";
    static final String[] dfa_66s = {
            "\1\3\1\1\1\6\1\2\2\uffff\1\2\27\uffff\1\6\73\uffff\2\6\3\uffff\1\2\16\uffff\1\6",
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

    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final char[] dfa_63 = DFA.unpackEncodedStringToUnsignedChars(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[] dfa_65 = DFA.unpackEncodedString(dfa_65s);
    static final short[][] dfa_66 = unpackEncodedStringArray(dfa_66s);

    class DFA94 extends DFA {

        public DFA94(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 94;
            this.eot = dfa_61;
            this.eof = dfa_61;
            this.min = dfa_62;
            this.max = dfa_63;
            this.accept = dfa_64;
            this.special = dfa_65;
            this.transition = dfa_66;
        }
        public String getDescription() {
            return "4788:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA94_1 = input.LA(1);

                         
                        int index94_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred176_InternalKdl()) ) {s = 11;}

                        else if ( (synpred177_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index94_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA94_3 = input.LA(1);

                         
                        int index94_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred177_InternalKdl()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index94_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 94, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_67s = "\102\uffff";
    static final String dfa_68s = "\1\7\101\uffff";
    static final String dfa_69s = "\1\4\6\0\73\uffff";
    static final String dfa_70s = "\1\165\6\0\73\uffff";
    static final String dfa_71s = "\7\uffff\1\2\71\uffff\1\1";
    static final String dfa_72s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\73\uffff}>";
    static final String[] dfa_73s = {
            "\1\7\1\2\1\5\2\7\1\uffff\1\1\1\7\5\uffff\20\7\1\uffff\1\3\1\uffff\3\7\1\uffff\5\7\5\uffff\7\7\4\uffff\1\7\10\uffff\1\7\1\uffff\1\7\1\uffff\1\6\15\7\6\uffff\2\7\3\uffff\1\7\2\uffff\1\7\4\uffff\1\7\6\uffff\1\4\2\uffff\1\7",
            "\1\uffff",
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
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_67 = DFA.unpackEncodedString(dfa_67s);
    static final short[] dfa_68 = DFA.unpackEncodedString(dfa_68s);
    static final char[] dfa_69 = DFA.unpackEncodedStringToUnsignedChars(dfa_69s);
    static final char[] dfa_70 = DFA.unpackEncodedStringToUnsignedChars(dfa_70s);
    static final short[] dfa_71 = DFA.unpackEncodedString(dfa_71s);
    static final short[] dfa_72 = DFA.unpackEncodedString(dfa_72s);
    static final short[][] dfa_73 = unpackEncodedStringArray(dfa_73s);

    class DFA103 extends DFA {

        public DFA103(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 103;
            this.eot = dfa_67;
            this.eof = dfa_68;
            this.min = dfa_69;
            this.max = dfa_70;
            this.accept = dfa_71;
            this.special = dfa_72;
            this.transition = dfa_73;
        }
        public String getDescription() {
            return "5281:3: ( (lv_root_1_0= ruleUnitElement ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA103_1 = input.LA(1);

                         
                        int index103_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred192_InternalKdl()) ) {s = 65;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index103_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA103_2 = input.LA(1);

                         
                        int index103_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred192_InternalKdl()) ) {s = 65;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index103_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA103_3 = input.LA(1);

                         
                        int index103_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred192_InternalKdl()) ) {s = 65;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index103_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA103_4 = input.LA(1);

                         
                        int index103_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred192_InternalKdl()) ) {s = 65;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index103_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA103_5 = input.LA(1);

                         
                        int index103_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred192_InternalKdl()) ) {s = 65;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index103_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA103_6 = input.LA(1);

                         
                        int index103_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred192_InternalKdl()) ) {s = 65;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index103_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 103, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_74s = "\1\5\1\144\1\uffff\1\5\1\uffff\1\144";
    static final String[] dfa_75s = {
            "\1\1\135\uffff\1\2",
            "\1\2\1\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\2\1\uffff\1\4\1\3"
    };
    static final char[] dfa_74 = DFA.unpackEncodedStringToUnsignedChars(dfa_74s);
    static final short[][] dfa_75 = unpackEncodedStringArray(dfa_75s);

    class DFA121 extends DFA {

        public DFA121(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 121;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_74;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_75;
        }
        public String getDescription() {
            return "115:11: (lv_name_2_1= rulePath | lv_name_2_2= ruleUrnId )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L,0x0000000800000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00001F01EFFE0002L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0100000000000200L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000004000004F0L,0x00040808C0000400L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00001F01FFFE0002L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000000000004B0L,0x0000000800000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00001F01C0000002L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000701C0000000L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00000703C0000040L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x00000705C0000000L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x00000000000000B0L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000000D800000012L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000007000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x000000D010000012L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x000000D000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00FD1F21C0000000L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x000000C000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000400000000010L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000010000010L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000801000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000410000DF0L,0x00040808C0000400L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x00020000000004B0L,0x0000000800000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0xC800000000000C30L,0x00000008000007B9L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x00FC1F01C0000002L,0x0000000000FFC100L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x00FC000000000002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x01FC000400000460L,0x0024004000000400L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0xCE00000000000C32L,0x00000008000007B9L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0800000000000C20L,0x0000000800000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000850L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000001100L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x2000004400000042L,0x0004000000003040L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000C20L,0x0000000800000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x2100004400000C60L,0x0024004800003440L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x2100004400000460L,0x0024004000003440L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000400000040L,0x0004000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0xC800000000000C32L,0x00000008000007B9L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000038000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0xC900000400000C70L,0x0005F20CC00007F9L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000300000002L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000002L,0x0000000300000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000010000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000410000DF0L,0x00040808C0000C00L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000002000000020L,0x0000000800000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000001400000850L,0x00040000C0000400L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0100000400000460L,0x0024004000000400L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000002L,0x0000003000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x000000E000000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x00000000000004A0L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000000L,0x0000030000000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x00000004000004F0L,0x00040008C0000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0100000400000460L,0x0024004000000C00L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0100000000000002L,0x0020004000000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000400000460L,0x0004000000000400L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000002L,0x0018008000000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000002L,0x0018000000000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000002L,0x000000C000000000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000000122L,0x0004008000000000L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000122L,0x0004000000000000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000122L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0100000400000C60L,0x0024004800000400L});

}
