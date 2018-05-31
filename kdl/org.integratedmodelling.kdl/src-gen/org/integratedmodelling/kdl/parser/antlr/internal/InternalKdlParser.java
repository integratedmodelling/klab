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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_LOWERCASE_ID", "RULE_INT", "RULE_LOWERCASE_DASHID", "RULE_UPPERCASE_ID", "RULE_SHAPE", "RULE_CAMELCASE_ID", "RULE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'@dataflow'", "'@var'", "'@val'", "'@author'", "'@version'", "'@klab'", "'@worldview'", "'@geometry'", "'@endpoint'", "'@namespace'", "'@coverage'", "','", "'@context'", "'export'", "'optional'", "'import'", "'multiple'", "'+'", "'parameter'", "'for'", "'{'", "'}'", "'as'", "'over'", "'number'", "'boolean'", "'text'", "'list'", "'enum'", "'input'", "'values'", "'default'", "'minimum'", "'maximum'", "'range'", "'to'", "'observe'", "'new'", "'geometry'", "'units'", "'semantics'", "'metadata'", "'class'", "'compute'", "'*'", "'of'", "'within'", "'not'", "'identified'", "'by'", "'presence'", "'count'", "'distance'", "'from'", "'probability'", "'uncertainty'", "'proportion'", "'in'", "'ratio'", "'value'", "'occurrence'", "'('", "')'", "'down'", "'per'", "'object'", "'process'", "'concept'", "'extent'", "'spatialextent'", "'temporalextent'", "'annotation'", "'contextualizer'", "'void'", "'partition'", "'models'", "'concepts'", "'observers'", "'otherwise'", "'if'", "'unless'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'unknown'", "'urn:klab:'", "':'", "'#'", "'/'", "'.'", "'=?'", "'='", "'>>'", "'classify'", "'>'", "'<'", "'!='", "'<='", "'>='", "'@'", "'-'", "'e'", "'E'", "'^'"
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
    public static final int T__121=121;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__120=120;
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=14;
    public static final int T__77=77;
    public static final int T__119=119;
    public static final int T__78=78;
    public static final int T__118=118;
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

                if ( ((LA7_0>=30 && LA7_0<=32)||LA7_0==35||(LA7_0>=41 && LA7_0<=45)||LA7_0==76||(LA7_0>=82 && LA7_0<=91)) ) {
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
    // InternalKdl.g:592:1: ruleActorDefinition returns [EObject current=null] : ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )? ) ) ;
    public final EObject ruleActorDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_exported_0_0=null;
        Token lv_optional_1_0=null;
        Token lv_imported_2_0=null;
        Token lv_multiple_3_0=null;
        Token lv_arity_4_0=null;
        Token lv_minimum_5_0=null;
        Token lv_parameter_6_0=null;
        Token lv_name_8_1=null;
        Token lv_name_8_2=null;
        Token lv_name_8_3=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token lv_docstring_13_0=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token lv_localName_18_0=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token lv_optional_23_0=null;
        Token lv_type_24_1=null;
        Token lv_type_24_2=null;
        Token lv_type_24_3=null;
        Token lv_type_24_4=null;
        Token lv_type_24_5=null;
        Token lv_parameter_25_0=null;
        Token lv_name_26_0=null;
        Token otherlv_27=null;
        Token lv_enumValues_28_0=null;
        Token otherlv_29=null;
        Token lv_enumValues_30_0=null;
        Token lv_docstring_31_0=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_37=null;
        Token otherlv_39=null;
        Token otherlv_41=null;
        Token otherlv_43=null;
        AntlrDatatypeRuleToken lv_type_7_0 = null;

        AntlrDatatypeRuleToken lv_targets_10_0 = null;

        AntlrDatatypeRuleToken lv_targets_12_0 = null;

        EObject lv_body_15_0 = null;

        EObject lv_coverage_20_0 = null;

        EObject lv_coverage_22_0 = null;

        EObject lv_default_33_0 = null;

        EObject lv_body_35_0 = null;

        EObject lv_rangeMin_38_0 = null;

        EObject lv_rangeMax_40_0 = null;

        EObject lv_rangeMin_42_0 = null;

        EObject lv_rangeMax_44_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:598:2: ( ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )? ) ) )
            // InternalKdl.g:599:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )? ) )
            {
            // InternalKdl.g:599:2: ( ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )? ) | ( ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )? ) )
            int alt28=2;
            switch ( input.LA(1) ) {
            case 30:
            case 32:
            case 35:
            case 76:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
                {
                alt28=1;
                }
                break;
            case 31:
                {
                int LA28_2 = input.LA(2);

                if ( (LA28_2==46) ) {
                    alt28=2;
                }
                else if ( (LA28_2==32) ) {
                    alt28=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 2, input);

                    throw nvae;
                }
                }
                break;
            case 41:
                {
                int LA28_3 = input.LA(2);

                if ( ((LA28_3>=RULE_STRING && LA28_3<=RULE_LOWERCASE_ID)||LA28_3==RULE_LOWERCASE_DASHID) ) {
                    alt28=1;
                }
                else if ( (LA28_3==46) ) {
                    alt28=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 3, input);

                    throw nvae;
                }
                }
                break;
            case 42:
                {
                int LA28_4 = input.LA(2);

                if ( ((LA28_4>=RULE_STRING && LA28_4<=RULE_LOWERCASE_ID)||LA28_4==RULE_LOWERCASE_DASHID) ) {
                    alt28=1;
                }
                else if ( (LA28_4==46) ) {
                    alt28=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 4, input);

                    throw nvae;
                }
                }
                break;
            case 43:
                {
                int LA28_5 = input.LA(2);

                if ( ((LA28_5>=RULE_STRING && LA28_5<=RULE_LOWERCASE_ID)||LA28_5==RULE_LOWERCASE_DASHID) ) {
                    alt28=1;
                }
                else if ( (LA28_5==46) ) {
                    alt28=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 5, input);

                    throw nvae;
                }
                }
                break;
            case 44:
            case 45:
                {
                alt28=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalKdl.g:600:3: ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )? )
                    {
                    // InternalKdl.g:600:3: ( ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )? )
                    // InternalKdl.g:601:4: ( ( (lv_exported_0_0= 'export' ) ) | ( ( ( (lv_optional_1_0= 'optional' ) )? ( (lv_imported_2_0= 'import' ) ) ) ( ( (lv_multiple_3_0= 'multiple' ) ) | ( ( (lv_arity_4_0= RULE_INT ) ) ( (lv_minimum_5_0= '+' ) )? ) )? ) )? ( (lv_parameter_6_0= 'parameter' ) )? ( (lv_type_7_0= ruleACTOR ) ) ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) ) (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )? ( (lv_docstring_13_0= RULE_STRING ) )? (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )? (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )? (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )?
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

                    // InternalKdl.g:701:4: ( (lv_parameter_6_0= 'parameter' ) )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==35) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalKdl.g:702:5: (lv_parameter_6_0= 'parameter' )
                            {
                            // InternalKdl.g:702:5: (lv_parameter_6_0= 'parameter' )
                            // InternalKdl.g:703:6: lv_parameter_6_0= 'parameter'
                            {
                            lv_parameter_6_0=(Token)match(input,35,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_parameter_6_0, grammarAccess.getActorDefinitionAccess().getParameterParameterKeyword_0_1_0());
                              					
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

                    // InternalKdl.g:715:4: ( (lv_type_7_0= ruleACTOR ) )
                    // InternalKdl.g:716:5: (lv_type_7_0= ruleACTOR )
                    {
                    // InternalKdl.g:716:5: (lv_type_7_0= ruleACTOR )
                    // InternalKdl.g:717:6: lv_type_7_0= ruleACTOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActorDefinitionAccess().getTypeACTORParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_type_7_0=ruleACTOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"type",
                      							lv_type_7_0,
                      							"org.integratedmodelling.kdl.Kdl.ACTOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKdl.g:734:4: ( ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) ) )
                    // InternalKdl.g:735:5: ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) )
                    {
                    // InternalKdl.g:735:5: ( (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING ) )
                    // InternalKdl.g:736:6: (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING )
                    {
                    // InternalKdl.g:736:6: (lv_name_8_1= RULE_LOWERCASE_ID | lv_name_8_2= RULE_LOWERCASE_DASHID | lv_name_8_3= RULE_STRING )
                    int alt13=3;
                    switch ( input.LA(1) ) {
                    case RULE_LOWERCASE_ID:
                        {
                        alt13=1;
                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt13=2;
                        }
                        break;
                    case RULE_STRING:
                        {
                        alt13=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }

                    switch (alt13) {
                        case 1 :
                            // InternalKdl.g:737:7: lv_name_8_1= RULE_LOWERCASE_ID
                            {
                            lv_name_8_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_8_1, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_0_3_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_8_1,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:752:7: lv_name_8_2= RULE_LOWERCASE_DASHID
                            {
                            lv_name_8_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_8_2, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_DASHIDTerminalRuleCall_0_3_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_8_2,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_DASHID");
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalKdl.g:767:7: lv_name_8_3= RULE_STRING
                            {
                            lv_name_8_3=(Token)match(input,RULE_STRING,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_name_8_3, grammarAccess.getActorDefinitionAccess().getNameSTRINGTerminalRuleCall_0_3_0_2());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"name",
                              								lv_name_8_3,
                              								"org.eclipse.xtext.common.Terminals.STRING");
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKdl.g:784:4: (otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )* )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==36) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKdl.g:785:5: otherlv_9= 'for' ( (lv_targets_10_0= ruleTARGET ) ) (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )*
                            {
                            otherlv_9=(Token)match(input,36,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getActorDefinitionAccess().getForKeyword_0_4_0());
                              				
                            }
                            // InternalKdl.g:789:5: ( (lv_targets_10_0= ruleTARGET ) )
                            // InternalKdl.g:790:6: (lv_targets_10_0= ruleTARGET )
                            {
                            // InternalKdl.g:790:6: (lv_targets_10_0= ruleTARGET )
                            // InternalKdl.g:791:7: lv_targets_10_0= ruleTARGET
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_4_1_0());
                              						
                            }
                            pushFollow(FOLLOW_20);
                            lv_targets_10_0=ruleTARGET();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"targets",
                              								lv_targets_10_0,
                              								"org.integratedmodelling.kdl.Kdl.TARGET");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:808:5: (otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) ) )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==28) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // InternalKdl.g:809:6: otherlv_11= ',' ( (lv_targets_12_0= ruleTARGET ) )
                            	    {
                            	    otherlv_11=(Token)match(input,28,FOLLOW_19); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_11, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_4_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:813:6: ( (lv_targets_12_0= ruleTARGET ) )
                            	    // InternalKdl.g:814:7: (lv_targets_12_0= ruleTARGET )
                            	    {
                            	    // InternalKdl.g:814:7: (lv_targets_12_0= ruleTARGET )
                            	    // InternalKdl.g:815:8: lv_targets_12_0= ruleTARGET
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getTargetsTARGETParserRuleCall_0_4_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_20);
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


                            	    }
                            	    break;

                            	default :
                            	    break loop14;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:834:4: ( (lv_docstring_13_0= RULE_STRING ) )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==RULE_STRING) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKdl.g:835:5: (lv_docstring_13_0= RULE_STRING )
                            {
                            // InternalKdl.g:835:5: (lv_docstring_13_0= RULE_STRING )
                            // InternalKdl.g:836:6: lv_docstring_13_0= RULE_STRING
                            {
                            lv_docstring_13_0=(Token)match(input,RULE_STRING,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_docstring_13_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_0_5_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getActorDefinitionRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"docstring",
                              							lv_docstring_13_0,
                              							"org.eclipse.xtext.common.Terminals.STRING");
                              					
                            }

                            }


                            }
                            break;

                    }

                    // InternalKdl.g:852:4: (otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}' )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==37) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:853:5: otherlv_14= '{' ( (lv_body_15_0= ruleDataflowBody ) ) otherlv_16= '}'
                            {
                            otherlv_14=(Token)match(input,37,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_14, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_0_6_0());
                              				
                            }
                            // InternalKdl.g:857:5: ( (lv_body_15_0= ruleDataflowBody ) )
                            // InternalKdl.g:858:6: (lv_body_15_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:858:6: (lv_body_15_0= ruleDataflowBody )
                            // InternalKdl.g:859:7: lv_body_15_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_0_6_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_body_15_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_15_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_16=(Token)match(input,38,FOLLOW_24); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_16, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_0_6_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:881:4: (otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==39) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKdl.g:882:5: otherlv_17= 'as' ( (lv_localName_18_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_17=(Token)match(input,39,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_17, grammarAccess.getActorDefinitionAccess().getAsKeyword_0_7_0());
                              				
                            }
                            // InternalKdl.g:886:5: ( (lv_localName_18_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:887:6: (lv_localName_18_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:887:6: (lv_localName_18_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:888:7: lv_localName_18_0= RULE_LOWERCASE_ID
                            {
                            lv_localName_18_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_localName_18_0, grammarAccess.getActorDefinitionAccess().getLocalNameLOWERCASE_IDTerminalRuleCall_0_7_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"localName",
                              								lv_localName_18_0,
                              								"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:905:4: (otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )* )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==40) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalKdl.g:906:5: otherlv_19= 'over' ( (lv_coverage_20_0= ruleFunction ) ) (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )*
                            {
                            otherlv_19=(Token)match(input,40,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_19, grammarAccess.getActorDefinitionAccess().getOverKeyword_0_8_0());
                              				
                            }
                            // InternalKdl.g:910:5: ( (lv_coverage_20_0= ruleFunction ) )
                            // InternalKdl.g:911:6: (lv_coverage_20_0= ruleFunction )
                            {
                            // InternalKdl.g:911:6: (lv_coverage_20_0= ruleFunction )
                            // InternalKdl.g:912:7: lv_coverage_20_0= ruleFunction
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_8_1_0());
                              						
                            }
                            pushFollow(FOLLOW_26);
                            lv_coverage_20_0=ruleFunction();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							add(
                              								current,
                              								"coverage",
                              								lv_coverage_20_0,
                              								"org.integratedmodelling.kdl.Kdl.Function");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKdl.g:929:5: (otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) ) )*
                            loop19:
                            do {
                                int alt19=2;
                                int LA19_0 = input.LA(1);

                                if ( (LA19_0==28) ) {
                                    alt19=1;
                                }


                                switch (alt19) {
                            	case 1 :
                            	    // InternalKdl.g:930:6: otherlv_21= ',' ( (lv_coverage_22_0= ruleFunction ) )
                            	    {
                            	    otherlv_21=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_21, grammarAccess.getActorDefinitionAccess().getCommaKeyword_0_8_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:934:6: ( (lv_coverage_22_0= ruleFunction ) )
                            	    // InternalKdl.g:935:7: (lv_coverage_22_0= ruleFunction )
                            	    {
                            	    // InternalKdl.g:935:7: (lv_coverage_22_0= ruleFunction )
                            	    // InternalKdl.g:936:8: lv_coverage_22_0= ruleFunction
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getActorDefinitionAccess().getCoverageFunctionParserRuleCall_0_8_2_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_26);
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


                            	    }
                            	    break;

                            	default :
                            	    break loop19;
                                }
                            } while (true);


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:957:3: ( ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )? )
                    {
                    // InternalKdl.g:957:3: ( ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )? )
                    // InternalKdl.g:958:4: ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) ) ( (lv_parameter_25_0= 'input' ) ) ( (lv_name_26_0= RULE_LOWERCASE_ID ) ) (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )? ( (lv_docstring_31_0= RULE_STRING ) ) (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )? (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )? ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )?
                    {
                    // InternalKdl.g:958:4: ( ( (lv_optional_23_0= 'optional' ) ) | ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) ) )
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==31) ) {
                        alt22=1;
                    }
                    else if ( ((LA22_0>=41 && LA22_0<=45)) ) {
                        alt22=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 22, 0, input);

                        throw nvae;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalKdl.g:959:5: ( (lv_optional_23_0= 'optional' ) )
                            {
                            // InternalKdl.g:959:5: ( (lv_optional_23_0= 'optional' ) )
                            // InternalKdl.g:960:6: (lv_optional_23_0= 'optional' )
                            {
                            // InternalKdl.g:960:6: (lv_optional_23_0= 'optional' )
                            // InternalKdl.g:961:7: lv_optional_23_0= 'optional'
                            {
                            lv_optional_23_0=(Token)match(input,31,FOLLOW_27); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_optional_23_0, grammarAccess.getActorDefinitionAccess().getOptionalOptionalKeyword_1_0_0_0());
                              						
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
                            // InternalKdl.g:974:5: ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) )
                            {
                            // InternalKdl.g:974:5: ( ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) ) )
                            // InternalKdl.g:975:6: ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) )
                            {
                            // InternalKdl.g:975:6: ( (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' ) )
                            // InternalKdl.g:976:7: (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' )
                            {
                            // InternalKdl.g:976:7: (lv_type_24_1= 'number' | lv_type_24_2= 'boolean' | lv_type_24_3= 'text' | lv_type_24_4= 'list' | lv_type_24_5= 'enum' )
                            int alt21=5;
                            switch ( input.LA(1) ) {
                            case 41:
                                {
                                alt21=1;
                                }
                                break;
                            case 42:
                                {
                                alt21=2;
                                }
                                break;
                            case 43:
                                {
                                alt21=3;
                                }
                                break;
                            case 44:
                                {
                                alt21=4;
                                }
                                break;
                            case 45:
                                {
                                alt21=5;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 21, 0, input);

                                throw nvae;
                            }

                            switch (alt21) {
                                case 1 :
                                    // InternalKdl.g:977:8: lv_type_24_1= 'number'
                                    {
                                    lv_type_24_1=(Token)match(input,41,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_24_1, grammarAccess.getActorDefinitionAccess().getTypeNumberKeyword_1_0_1_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_24_1, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKdl.g:988:8: lv_type_24_2= 'boolean'
                                    {
                                    lv_type_24_2=(Token)match(input,42,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_24_2, grammarAccess.getActorDefinitionAccess().getTypeBooleanKeyword_1_0_1_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_24_2, null);
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKdl.g:999:8: lv_type_24_3= 'text'
                                    {
                                    lv_type_24_3=(Token)match(input,43,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_24_3, grammarAccess.getActorDefinitionAccess().getTypeTextKeyword_1_0_1_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_24_3, null);
                                      							
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalKdl.g:1010:8: lv_type_24_4= 'list'
                                    {
                                    lv_type_24_4=(Token)match(input,44,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_24_4, grammarAccess.getActorDefinitionAccess().getTypeListKeyword_1_0_1_0_3());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_24_4, null);
                                      							
                                    }

                                    }
                                    break;
                                case 5 :
                                    // InternalKdl.g:1021:8: lv_type_24_5= 'enum'
                                    {
                                    lv_type_24_5=(Token)match(input,45,FOLLOW_27); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_type_24_5, grammarAccess.getActorDefinitionAccess().getTypeEnumKeyword_1_0_1_0_4());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getActorDefinitionRule());
                                      								}
                                      								setWithLastConsumed(current, "type", lv_type_24_5, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1035:4: ( (lv_parameter_25_0= 'input' ) )
                    // InternalKdl.g:1036:5: (lv_parameter_25_0= 'input' )
                    {
                    // InternalKdl.g:1036:5: (lv_parameter_25_0= 'input' )
                    // InternalKdl.g:1037:6: lv_parameter_25_0= 'input'
                    {
                    lv_parameter_25_0=(Token)match(input,46,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_parameter_25_0, grammarAccess.getActorDefinitionAccess().getParameterInputKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(current, "parameter", true, "input");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1049:4: ( (lv_name_26_0= RULE_LOWERCASE_ID ) )
                    // InternalKdl.g:1050:5: (lv_name_26_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKdl.g:1050:5: (lv_name_26_0= RULE_LOWERCASE_ID )
                    // InternalKdl.g:1051:6: lv_name_26_0= RULE_LOWERCASE_ID
                    {
                    lv_name_26_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_28); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_26_0, grammarAccess.getActorDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_26_0,
                      							"org.integratedmodelling.kdl.Kdl.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1067:4: (otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )* )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==47) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalKdl.g:1068:5: otherlv_27= 'values' ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) ) (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )*
                            {
                            otherlv_27=(Token)match(input,47,FOLLOW_29); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_27, grammarAccess.getActorDefinitionAccess().getValuesKeyword_1_3_0());
                              				
                            }
                            // InternalKdl.g:1072:5: ( (lv_enumValues_28_0= RULE_UPPERCASE_ID ) )
                            // InternalKdl.g:1073:6: (lv_enumValues_28_0= RULE_UPPERCASE_ID )
                            {
                            // InternalKdl.g:1073:6: (lv_enumValues_28_0= RULE_UPPERCASE_ID )
                            // InternalKdl.g:1074:7: lv_enumValues_28_0= RULE_UPPERCASE_ID
                            {
                            lv_enumValues_28_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_enumValues_28_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getActorDefinitionRule());
                              							}
                              							addWithLastConsumed(
                              								current,
                              								"enumValues",
                              								lv_enumValues_28_0,
                              								"org.integratedmodelling.kdl.Kdl.UPPERCASE_ID");
                              						
                            }

                            }


                            }

                            // InternalKdl.g:1090:5: (otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) ) )*
                            loop23:
                            do {
                                int alt23=2;
                                int LA23_0 = input.LA(1);

                                if ( (LA23_0==28) ) {
                                    alt23=1;
                                }


                                switch (alt23) {
                            	case 1 :
                            	    // InternalKdl.g:1091:6: otherlv_29= ',' ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) )
                            	    {
                            	    otherlv_29=(Token)match(input,28,FOLLOW_29); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_29, grammarAccess.getActorDefinitionAccess().getCommaKeyword_1_3_2_0());
                            	      					
                            	    }
                            	    // InternalKdl.g:1095:6: ( (lv_enumValues_30_0= RULE_UPPERCASE_ID ) )
                            	    // InternalKdl.g:1096:7: (lv_enumValues_30_0= RULE_UPPERCASE_ID )
                            	    {
                            	    // InternalKdl.g:1096:7: (lv_enumValues_30_0= RULE_UPPERCASE_ID )
                            	    // InternalKdl.g:1097:8: lv_enumValues_30_0= RULE_UPPERCASE_ID
                            	    {
                            	    lv_enumValues_30_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_30); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								newLeafNode(lv_enumValues_30_0, grammarAccess.getActorDefinitionAccess().getEnumValuesUPPERCASE_IDTerminalRuleCall_1_3_2_1_0());
                            	      							
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


                            	    }
                            	    break;

                            	default :
                            	    break loop23;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalKdl.g:1115:4: ( (lv_docstring_31_0= RULE_STRING ) )
                    // InternalKdl.g:1116:5: (lv_docstring_31_0= RULE_STRING )
                    {
                    // InternalKdl.g:1116:5: (lv_docstring_31_0= RULE_STRING )
                    // InternalKdl.g:1117:6: lv_docstring_31_0= RULE_STRING
                    {
                    lv_docstring_31_0=(Token)match(input,RULE_STRING,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_docstring_31_0, grammarAccess.getActorDefinitionAccess().getDocstringSTRINGTerminalRuleCall_1_4_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getActorDefinitionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"docstring",
                      							lv_docstring_31_0,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    // InternalKdl.g:1133:4: (otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==48) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // InternalKdl.g:1134:5: otherlv_32= 'default' ( (lv_default_33_0= ruleValue ) )
                            {
                            otherlv_32=(Token)match(input,48,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_32, grammarAccess.getActorDefinitionAccess().getDefaultKeyword_1_5_0());
                              				
                            }
                            // InternalKdl.g:1138:5: ( (lv_default_33_0= ruleValue ) )
                            // InternalKdl.g:1139:6: (lv_default_33_0= ruleValue )
                            {
                            // InternalKdl.g:1139:6: (lv_default_33_0= ruleValue )
                            // InternalKdl.g:1140:7: lv_default_33_0= ruleValue
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getDefaultValueParserRuleCall_1_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_33);
                            lv_default_33_0=ruleValue();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"default",
                              								lv_default_33_0,
                              								"org.integratedmodelling.kdl.Kdl.Value");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKdl.g:1158:4: (otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}' )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==37) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // InternalKdl.g:1159:5: otherlv_34= '{' ( (lv_body_35_0= ruleDataflowBody ) ) otherlv_36= '}'
                            {
                            otherlv_34=(Token)match(input,37,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_34, grammarAccess.getActorDefinitionAccess().getLeftCurlyBracketKeyword_1_6_0());
                              				
                            }
                            // InternalKdl.g:1163:5: ( (lv_body_35_0= ruleDataflowBody ) )
                            // InternalKdl.g:1164:6: (lv_body_35_0= ruleDataflowBody )
                            {
                            // InternalKdl.g:1164:6: (lv_body_35_0= ruleDataflowBody )
                            // InternalKdl.g:1165:7: lv_body_35_0= ruleDataflowBody
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getActorDefinitionAccess().getBodyDataflowBodyParserRuleCall_1_6_1_0());
                              						
                            }
                            pushFollow(FOLLOW_23);
                            lv_body_35_0=ruleDataflowBody();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              							}
                              							set(
                              								current,
                              								"body",
                              								lv_body_35_0,
                              								"org.integratedmodelling.kdl.Kdl.DataflowBody");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            otherlv_36=(Token)match(input,38,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_36, grammarAccess.getActorDefinitionAccess().getRightCurlyBracketKeyword_1_6_2());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:1187:4: ( (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) ) | (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) ) | (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) ) )?
                    int alt27=4;
                    switch ( input.LA(1) ) {
                        case 49:
                            {
                            alt27=1;
                            }
                            break;
                        case 50:
                            {
                            alt27=2;
                            }
                            break;
                        case 51:
                            {
                            alt27=3;
                            }
                            break;
                    }

                    switch (alt27) {
                        case 1 :
                            // InternalKdl.g:1188:5: (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1188:5: (otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) ) )
                            // InternalKdl.g:1189:6: otherlv_37= 'minimum' ( (lv_rangeMin_38_0= ruleNumber ) )
                            {
                            otherlv_37=(Token)match(input,49,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_37, grammarAccess.getActorDefinitionAccess().getMinimumKeyword_1_7_0_0());
                              					
                            }
                            // InternalKdl.g:1193:6: ( (lv_rangeMin_38_0= ruleNumber ) )
                            // InternalKdl.g:1194:7: (lv_rangeMin_38_0= ruleNumber )
                            {
                            // InternalKdl.g:1194:7: (lv_rangeMin_38_0= ruleNumber )
                            // InternalKdl.g:1195:8: lv_rangeMin_38_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_7_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
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


                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:1214:5: (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1214:5: (otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) ) )
                            // InternalKdl.g:1215:6: otherlv_39= 'maximum' ( (lv_rangeMax_40_0= ruleNumber ) )
                            {
                            otherlv_39=(Token)match(input,50,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_39, grammarAccess.getActorDefinitionAccess().getMaximumKeyword_1_7_1_0());
                              					
                            }
                            // InternalKdl.g:1219:6: ( (lv_rangeMax_40_0= ruleNumber ) )
                            // InternalKdl.g:1220:7: (lv_rangeMax_40_0= ruleNumber )
                            {
                            // InternalKdl.g:1220:7: (lv_rangeMax_40_0= ruleNumber )
                            // InternalKdl.g:1221:8: lv_rangeMax_40_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_7_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
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
                        case 3 :
                            // InternalKdl.g:1240:5: (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) )
                            {
                            // InternalKdl.g:1240:5: (otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) ) )
                            // InternalKdl.g:1241:6: otherlv_41= 'range' ( (lv_rangeMin_42_0= ruleNumber ) ) otherlv_43= 'to' ( (lv_rangeMax_44_0= ruleNumber ) )
                            {
                            otherlv_41=(Token)match(input,51,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_41, grammarAccess.getActorDefinitionAccess().getRangeKeyword_1_7_2_0());
                              					
                            }
                            // InternalKdl.g:1245:6: ( (lv_rangeMin_42_0= ruleNumber ) )
                            // InternalKdl.g:1246:7: (lv_rangeMin_42_0= ruleNumber )
                            {
                            // InternalKdl.g:1246:7: (lv_rangeMin_42_0= ruleNumber )
                            // InternalKdl.g:1247:8: lv_rangeMin_42_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMinNumberParserRuleCall_1_7_2_1_0());
                              							
                            }
                            pushFollow(FOLLOW_36);
                            lv_rangeMin_42_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMin",
                              									lv_rangeMin_42_0,
                              									"org.integratedmodelling.kdl.Kdl.Number");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_43=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_43, grammarAccess.getActorDefinitionAccess().getToKeyword_1_7_2_2());
                              					
                            }
                            // InternalKdl.g:1268:6: ( (lv_rangeMax_44_0= ruleNumber ) )
                            // InternalKdl.g:1269:7: (lv_rangeMax_44_0= ruleNumber )
                            {
                            // InternalKdl.g:1269:7: (lv_rangeMax_44_0= ruleNumber )
                            // InternalKdl.g:1270:8: lv_rangeMax_44_0= ruleNumber
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getActorDefinitionAccess().getRangeMaxNumberParserRuleCall_1_7_2_3_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_rangeMax_44_0=ruleNumber();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getActorDefinitionRule());
                              								}
                              								set(
                              									current,
                              									"rangeMax",
                              									lv_rangeMax_44_0,
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
    // InternalKdl.g:1294:1: entryRuleDataflowBody returns [EObject current=null] : iv_ruleDataflowBody= ruleDataflowBody EOF ;
    public final EObject entryRuleDataflowBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataflowBody = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()
        	);

        try {
            // InternalKdl.g:1298:2: (iv_ruleDataflowBody= ruleDataflowBody EOF )
            // InternalKdl.g:1299:2: iv_ruleDataflowBody= ruleDataflowBody EOF
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
    // InternalKdl.g:1308:1: ruleDataflowBody returns [EObject current=null] : ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) ;
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
            // InternalKdl.g:1317:2: ( ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) ) )
            // InternalKdl.g:1318:2: ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            {
            // InternalKdl.g:1318:2: ( () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKdl.g:1319:3: () (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )? ( (lv_dataflows_5_0= ruleActorDefinition ) )* ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKdl.g:1319:3: ()
            // InternalKdl.g:1320:4: 
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

            // InternalKdl.g:1329:3: (otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==53) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalKdl.g:1330:4: otherlv_1= 'observe' ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) )
                    {
                    otherlv_1=(Token)match(input,53,FOLLOW_37); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDataflowBodyAccess().getObserveKeyword_1_0());
                      			
                    }
                    // InternalKdl.g:1334:4: ( (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) ) | ( (lv_urnObservation_4_0= ruleUrn ) ) )
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==54) ) {
                        alt29=1;
                    }
                    else if ( ((LA29_0>=RULE_STRING && LA29_0<=RULE_LOWERCASE_ID)||LA29_0==RULE_LOWERCASE_DASHID||LA29_0==RULE_CAMELCASE_ID||LA29_0==103) ) {
                        alt29=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 0, input);

                        throw nvae;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalKdl.g:1335:5: (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) )
                            {
                            // InternalKdl.g:1335:5: (otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) ) )
                            // InternalKdl.g:1336:6: otherlv_2= 'new' ( (lv_newObservation_3_0= ruleObservableSemantics ) )
                            {
                            otherlv_2=(Token)match(input,54,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getDataflowBodyAccess().getNewKeyword_1_1_0_0());
                              					
                            }
                            // InternalKdl.g:1340:6: ( (lv_newObservation_3_0= ruleObservableSemantics ) )
                            // InternalKdl.g:1341:7: (lv_newObservation_3_0= ruleObservableSemantics )
                            {
                            // InternalKdl.g:1341:7: (lv_newObservation_3_0= ruleObservableSemantics )
                            // InternalKdl.g:1342:8: lv_newObservation_3_0= ruleObservableSemantics
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getDataflowBodyAccess().getNewObservationObservableSemanticsParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_39);
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
                            // InternalKdl.g:1361:5: ( (lv_urnObservation_4_0= ruleUrn ) )
                            {
                            // InternalKdl.g:1361:5: ( (lv_urnObservation_4_0= ruleUrn ) )
                            // InternalKdl.g:1362:6: (lv_urnObservation_4_0= ruleUrn )
                            {
                            // InternalKdl.g:1362:6: (lv_urnObservation_4_0= ruleUrn )
                            // InternalKdl.g:1363:7: lv_urnObservation_4_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getDataflowBodyAccess().getUrnObservationUrnParserRuleCall_1_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_39);
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

            // InternalKdl.g:1382:3: ( (lv_dataflows_5_0= ruleActorDefinition ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=30 && LA31_0<=32)||LA31_0==35||(LA31_0>=41 && LA31_0<=45)||LA31_0==76||(LA31_0>=82 && LA31_0<=91)) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalKdl.g:1383:4: (lv_dataflows_5_0= ruleActorDefinition )
            	    {
            	    // InternalKdl.g:1383:4: (lv_dataflows_5_0= ruleActorDefinition )
            	    // InternalKdl.g:1384:5: lv_dataflows_5_0= ruleActorDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDataflowBodyAccess().getDataflowsActorDefinitionParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_39);
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
            	    break loop31;
                }
            } while (true);

            // InternalKdl.g:1401:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKdl.g:1402:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKdl.g:1402:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?) )
            // InternalKdl.g:1403:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());
            // InternalKdl.g:1406:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?)
            // InternalKdl.g:1407:6: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+ {...}?
            {
            // InternalKdl.g:1407:6: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+
            int cnt35=0;
            loop35:
            do {
                int alt35=6;
                alt35 = dfa35.predict(input);
                switch (alt35) {
            	case 1 :
            	    // InternalKdl.g:1408:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1408:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
            	    // InternalKdl.g:1409:5: {...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0)");
            	    }
            	    // InternalKdl.g:1409:109: ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
            	    // InternalKdl.g:1410:6: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0);
            	    // InternalKdl.g:1413:9: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
            	    // InternalKdl.g:1413:10: {...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1413:19: (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
            	    // InternalKdl.g:1413:20: otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) )
            	    {
            	    otherlv_7=(Token)match(input,55,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getDataflowBodyAccess().getGeometryKeyword_3_0_0());
            	      								
            	    }
            	    // InternalKdl.g:1417:9: ( (lv_geometry_8_0= ruleGeometry ) )
            	    // InternalKdl.g:1418:10: (lv_geometry_8_0= ruleGeometry )
            	    {
            	    // InternalKdl.g:1418:10: (lv_geometry_8_0= ruleGeometry )
            	    // InternalKdl.g:1419:11: lv_geometry_8_0= ruleGeometry
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getGeometryGeometryParserRuleCall_3_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    // InternalKdl.g:1442:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1442:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
            	    // InternalKdl.g:1443:5: {...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1)");
            	    }
            	    // InternalKdl.g:1443:109: ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
            	    // InternalKdl.g:1444:6: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1);
            	    // InternalKdl.g:1447:9: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
            	    // InternalKdl.g:1447:10: {...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1447:19: (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
            	    // InternalKdl.g:1447:20: otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) )
            	    {
            	    otherlv_9=(Token)match(input,56,FOLLOW_41); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_9, grammarAccess.getDataflowBodyAccess().getUnitsKeyword_3_1_0());
            	      								
            	    }
            	    // InternalKdl.g:1451:9: ( (lv_units_10_0= ruleUnit ) )
            	    // InternalKdl.g:1452:10: (lv_units_10_0= ruleUnit )
            	    {
            	    // InternalKdl.g:1452:10: (lv_units_10_0= ruleUnit )
            	    // InternalKdl.g:1453:11: lv_units_10_0= ruleUnit
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getUnitsUnitParserRuleCall_3_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    // InternalKdl.g:1476:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
            	    {
            	    // InternalKdl.g:1476:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
            	    // InternalKdl.g:1477:5: {...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2)");
            	    }
            	    // InternalKdl.g:1477:109: ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
            	    // InternalKdl.g:1478:6: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2);
            	    // InternalKdl.g:1481:9: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
            	    int cnt32=0;
            	    loop32:
            	    do {
            	        int alt32=2;
            	        int LA32_0 = input.LA(1);

            	        if ( (LA32_0==60) ) {
            	            int LA32_2 = input.LA(2);

            	            if ( ((synpred53_InternalKdl()&&(true))) ) {
            	                alt32=1;
            	            }


            	        }


            	        switch (alt32) {
            	    	case 1 :
            	    	    // InternalKdl.g:1481:10: {...}? => ( (lv_computations_11_0= ruleComputation ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    	    }
            	    	    // InternalKdl.g:1481:19: ( (lv_computations_11_0= ruleComputation ) )
            	    	    // InternalKdl.g:1481:20: (lv_computations_11_0= ruleComputation )
            	    	    {
            	    	    // InternalKdl.g:1481:20: (lv_computations_11_0= ruleComputation )
            	    	    // InternalKdl.g:1482:10: lv_computations_11_0= ruleComputation
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_3_2_0());
            	    	      									
            	    	    }
            	    	    pushFollow(FOLLOW_40);
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
            	    	    if ( cnt32 >= 1 ) break loop32;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(32, input);
            	                throw eee;
            	        }
            	        cnt32++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKdl.g:1504:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:1504:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
            	    // InternalKdl.g:1505:5: {...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3)");
            	    }
            	    // InternalKdl.g:1505:109: ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
            	    // InternalKdl.g:1506:6: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3);
            	    // InternalKdl.g:1509:9: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
            	    // InternalKdl.g:1509:10: {...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1509:19: (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
            	    // InternalKdl.g:1509:20: otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) )
            	    {
            	    otherlv_12=(Token)match(input,57,FOLLOW_38); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_12, grammarAccess.getDataflowBodyAccess().getSemanticsKeyword_3_3_0());
            	      								
            	    }
            	    // InternalKdl.g:1513:9: ( (lv_semantics_13_0= ruleObservableSemantics ) )
            	    // InternalKdl.g:1514:10: (lv_semantics_13_0= ruleObservableSemantics )
            	    {
            	    // InternalKdl.g:1514:10: (lv_semantics_13_0= ruleObservableSemantics )
            	    // InternalKdl.g:1515:11: lv_semantics_13_0= ruleObservableSemantics
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getDataflowBodyAccess().getSemanticsObservableSemanticsParserRuleCall_3_3_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    // InternalKdl.g:1538:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
            	    {
            	    // InternalKdl.g:1538:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
            	    // InternalKdl.g:1539:5: {...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4)");
            	    }
            	    // InternalKdl.g:1539:109: ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
            	    // InternalKdl.g:1540:6: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4);
            	    // InternalKdl.g:1543:9: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
            	    // InternalKdl.g:1543:10: {...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleDataflowBody", "true");
            	    }
            	    // InternalKdl.g:1543:19: ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
            	    // InternalKdl.g:1543:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
            	    {
            	    // InternalKdl.g:1543:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )?
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
            	            // InternalKdl.g:1544:10: otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) )
            	            {
            	            otherlv_14=(Token)match(input,58,FOLLOW_42); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_14, grammarAccess.getDataflowBodyAccess().getMetadataKeyword_3_4_0_0());
            	              									
            	            }
            	            // InternalKdl.g:1548:10: ( (lv_metadata_15_0= ruleMetadata ) )
            	            // InternalKdl.g:1549:11: (lv_metadata_15_0= ruleMetadata )
            	            {
            	            // InternalKdl.g:1549:11: (lv_metadata_15_0= ruleMetadata )
            	            // InternalKdl.g:1550:12: lv_metadata_15_0= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_3_4_0_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_40);
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

            	    // InternalKdl.g:1568:9: (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
            	    int alt34=2;
            	    int LA34_0 = input.LA(1);

            	    if ( (LA34_0==59) ) {
            	        int LA34_1 = input.LA(2);

            	        if ( (synpred57_InternalKdl()) ) {
            	            alt34=1;
            	        }
            	    }
            	    switch (alt34) {
            	        case 1 :
            	            // InternalKdl.g:1569:10: otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) )
            	            {
            	            otherlv_16=(Token)match(input,59,FOLLOW_3); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_16, grammarAccess.getDataflowBodyAccess().getClassKeyword_3_4_1_0());
            	              									
            	            }
            	            // InternalKdl.g:1573:10: ( (lv_javaClass_17_0= ruleJavaClass ) )
            	            // InternalKdl.g:1574:11: (lv_javaClass_17_0= ruleJavaClass )
            	            {
            	            // InternalKdl.g:1574:11: (lv_javaClass_17_0= ruleJavaClass )
            	            // InternalKdl.g:1575:12: lv_javaClass_17_0= ruleJavaClass
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getDataflowBodyAccess().getJavaClassJavaClassParserRuleCall_3_4_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_40);
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
            	    if ( cnt35 >= 1 ) break loop35;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(35, input);
                        throw eee;
                }
                cnt35++;
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
    // InternalKdl.g:1614:1: entryRuleComputation returns [EObject current=null] : iv_ruleComputation= ruleComputation EOF ;
    public final EObject entryRuleComputation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComputation = null;


        try {
            // InternalKdl.g:1614:52: (iv_ruleComputation= ruleComputation EOF )
            // InternalKdl.g:1615:2: iv_ruleComputation= ruleComputation EOF
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
    // InternalKdl.g:1621:1: ruleComputation returns [EObject current=null] : (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) ;
    public final EObject ruleComputation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functions_1_0 = null;

        EObject lv_functions_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:1627:2: ( (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* ) )
            // InternalKdl.g:1628:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            {
            // InternalKdl.g:1628:2: (otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )* )
            // InternalKdl.g:1629:3: otherlv_0= 'compute' ( (lv_functions_1_0= ruleFunction ) ) (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            {
            otherlv_0=(Token)match(input,60,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getComputationAccess().getComputeKeyword_0());
              		
            }
            // InternalKdl.g:1633:3: ( (lv_functions_1_0= ruleFunction ) )
            // InternalKdl.g:1634:4: (lv_functions_1_0= ruleFunction )
            {
            // InternalKdl.g:1634:4: (lv_functions_1_0= ruleFunction )
            // InternalKdl.g:1635:5: lv_functions_1_0= ruleFunction
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

            // InternalKdl.g:1652:3: (otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==28) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalKdl.g:1653:4: otherlv_2= ',' ( (lv_functions_3_0= ruleFunction ) )
            	    {
            	    otherlv_2=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_2, grammarAccess.getComputationAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKdl.g:1657:4: ( (lv_functions_3_0= ruleFunction ) )
            	    // InternalKdl.g:1658:5: (lv_functions_3_0= ruleFunction )
            	    {
            	    // InternalKdl.g:1658:5: (lv_functions_3_0= ruleFunction )
            	    // InternalKdl.g:1659:6: lv_functions_3_0= ruleFunction
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
            	    break loop36;
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
    // InternalKdl.g:1681:1: entryRuleGeometry returns [String current=null] : iv_ruleGeometry= ruleGeometry EOF ;
    public final String entryRuleGeometry() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeometry = null;


        try {
            // InternalKdl.g:1681:48: (iv_ruleGeometry= ruleGeometry EOF )
            // InternalKdl.g:1682:2: iv_ruleGeometry= ruleGeometry EOF
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
    // InternalKdl.g:1688:1: ruleGeometry returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) ;
    public final AntlrDatatypeRuleToken ruleGeometry() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_SHAPE_1=null;
        Token this_SHAPE_3=null;


        	enterRule();

        try {
            // InternalKdl.g:1694:2: ( (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) ) )
            // InternalKdl.g:1695:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            {
            // InternalKdl.g:1695:2: (kw= '*' | (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* ) )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==61) ) {
                alt38=1;
            }
            else if ( (LA38_0==RULE_SHAPE) ) {
                alt38=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // InternalKdl.g:1696:3: kw= '*'
                    {
                    kw=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getGeometryAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:1702:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    {
                    // InternalKdl.g:1702:3: (this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )* )
                    // InternalKdl.g:1703:4: this_SHAPE_1= RULE_SHAPE (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    {
                    this_SHAPE_1=(Token)match(input,RULE_SHAPE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SHAPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SHAPE_1, grammarAccess.getGeometryAccess().getSHAPETerminalRuleCall_1_0());
                      			
                    }
                    // InternalKdl.g:1710:4: (kw= ',' this_SHAPE_3= RULE_SHAPE )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0==28) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // InternalKdl.g:1711:5: kw= ',' this_SHAPE_3= RULE_SHAPE
                    	    {
                    	    kw=(Token)match(input,28,FOLLOW_43); if (state.failed) return current;
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
                    	    break loop37;
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
    // InternalKdl.g:1729:1: entryRuleConceptDeclaration returns [EObject current=null] : iv_ruleConceptDeclaration= ruleConceptDeclaration EOF ;
    public final EObject entryRuleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConceptDeclaration = null;


        try {
            // InternalKdl.g:1729:59: (iv_ruleConceptDeclaration= ruleConceptDeclaration EOF )
            // InternalKdl.g:1730:2: iv_ruleConceptDeclaration= ruleConceptDeclaration EOF
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
    // InternalKdl.g:1736:1: ruleConceptDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? ) ;
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
            // InternalKdl.g:1742:2: ( ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? ) )
            // InternalKdl.g:1743:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? )
            {
            // InternalKdl.g:1743:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )? )
            // InternalKdl.g:1744:3: ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )? (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )?
            {
            // InternalKdl.g:1744:3: ( (lv_name_0_0= RULE_STRING ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==RULE_STRING) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalKdl.g:1745:4: (lv_name_0_0= RULE_STRING )
                    {
                    // InternalKdl.g:1745:4: (lv_name_0_0= RULE_STRING )
                    // InternalKdl.g:1746:5: lv_name_0_0= RULE_STRING
                    {
                    lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_38); if (state.failed) return current;
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

            // InternalKdl.g:1762:3: ( (lv_main_1_0= ruleConcept ) )+
            int cnt40=0;
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==76) ) {
                    int LA40_2 = input.LA(2);

                    if ( (LA40_2==62) ) {
                        alt40=1;
                    }


                }
                else if ( (LA40_0==RULE_LOWERCASE_ID||LA40_0==RULE_CAMELCASE_ID||LA40_0==64||(LA40_0>=67 && LA40_0<=69)||(LA40_0>=71 && LA40_0<=73)||LA40_0==75||(LA40_0>=77 && LA40_0<=78)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalKdl.g:1763:4: (lv_main_1_0= ruleConcept )
            	    {
            	    // InternalKdl.g:1763:4: (lv_main_1_0= ruleConcept )
            	    // InternalKdl.g:1764:5: lv_main_1_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getConceptDeclarationAccess().getMainConceptParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_44);
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
            	    if ( cnt40 >= 1 ) break loop40;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(40, input);
                        throw eee;
                }
                cnt40++;
            } while (true);

            // InternalKdl.g:1781:3: (otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==62) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalKdl.g:1782:4: otherlv_2= 'of' ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) )
                    {
                    otherlv_2=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getConceptDeclarationAccess().getOfKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:1786:4: ( (lv_inherency_3_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:1787:5: (lv_inherency_3_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:1787:5: (lv_inherency_3_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:1788:6: lv_inherency_3_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptDeclarationAccess().getInherencySimpleConceptDeclarationParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_45);
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

            // InternalKdl.g:1806:3: (otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==63) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalKdl.g:1807:4: otherlv_4= 'within' ( (lv_context_5_0= ruleSimpleConceptDeclaration ) )
                    {
                    otherlv_4=(Token)match(input,63,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getConceptDeclarationAccess().getWithinKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:1811:4: ( (lv_context_5_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:1812:5: (lv_context_5_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:1812:5: (lv_context_5_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:1813:6: lv_context_5_0= ruleSimpleConceptDeclaration
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
    // InternalKdl.g:1835:1: entryRuleConceptReference returns [String current=null] : iv_ruleConceptReference= ruleConceptReference EOF ;
    public final String entryRuleConceptReference() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleConceptReference = null;


        try {
            // InternalKdl.g:1835:56: (iv_ruleConceptReference= ruleConceptReference EOF )
            // InternalKdl.g:1836:2: iv_ruleConceptReference= ruleConceptReference EOF
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
    // InternalKdl.g:1842:1: ruleConceptReference returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId ) ;
    public final AntlrDatatypeRuleToken ruleConceptReference() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_CAMELCASE_ID_0=null;
        AntlrDatatypeRuleToken this_NamespaceId_1 = null;



        	enterRule();

        try {
            // InternalKdl.g:1848:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId ) )
            // InternalKdl.g:1849:2: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId )
            {
            // InternalKdl.g:1849:2: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_NamespaceId_1= ruleNamespaceId )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RULE_CAMELCASE_ID) ) {
                alt43=1;
            }
            else if ( (LA43_0==RULE_LOWERCASE_ID) ) {
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
                    // InternalKdl.g:1850:3: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
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
                    // InternalKdl.g:1858:3: this_NamespaceId_1= ruleNamespaceId
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
    // InternalKdl.g:1872:1: entryRuleConcept returns [EObject current=null] : iv_ruleConcept= ruleConcept EOF ;
    public final EObject entryRuleConcept() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConcept = null;


        try {
            // InternalKdl.g:1872:48: (iv_ruleConcept= ruleConcept EOF )
            // InternalKdl.g:1873:2: iv_ruleConcept= ruleConcept EOF
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
    // InternalKdl.g:1879:1: ruleConcept returns [EObject current=null] : ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) ) ;
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
            // InternalKdl.g:1885:2: ( ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) ) )
            // InternalKdl.g:1886:2: ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) )
            {
            // InternalKdl.g:1886:2: ( ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? ) | ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) ) | ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? ) | ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) ) | (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' ) )
            int alt52=11;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
            case RULE_CAMELCASE_ID:
            case 64:
                {
                alt52=1;
                }
                break;
            case 67:
                {
                alt52=2;
                }
                break;
            case 68:
                {
                alt52=3;
                }
                break;
            case 69:
                {
                alt52=4;
                }
                break;
            case 71:
                {
                alt52=5;
                }
                break;
            case 72:
                {
                alt52=6;
                }
                break;
            case 73:
                {
                alt52=7;
                }
                break;
            case 75:
                {
                alt52=8;
                }
                break;
            case 76:
                {
                alt52=9;
                }
                break;
            case 77:
                {
                alt52=10;
                }
                break;
            case 78:
                {
                alt52=11;
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
                    // InternalKdl.g:1887:3: ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? )
                    {
                    // InternalKdl.g:1887:3: ( ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )? )
                    // InternalKdl.g:1888:4: ( (lv_negated_0_0= 'not' ) )? ( (lv_name_1_0= ruleConceptReference ) ) (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )?
                    {
                    // InternalKdl.g:1888:4: ( (lv_negated_0_0= 'not' ) )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==64) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // InternalKdl.g:1889:5: (lv_negated_0_0= 'not' )
                            {
                            // InternalKdl.g:1889:5: (lv_negated_0_0= 'not' )
                            // InternalKdl.g:1890:6: lv_negated_0_0= 'not'
                            {
                            lv_negated_0_0=(Token)match(input,64,FOLLOW_46); if (state.failed) return current;
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

                    // InternalKdl.g:1902:4: ( (lv_name_1_0= ruleConceptReference ) )
                    // InternalKdl.g:1903:5: (lv_name_1_0= ruleConceptReference )
                    {
                    // InternalKdl.g:1903:5: (lv_name_1_0= ruleConceptReference )
                    // InternalKdl.g:1904:6: lv_name_1_0= ruleConceptReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getNameConceptReferenceParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
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

                    // InternalKdl.g:1921:4: (otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) ) )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==65) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // InternalKdl.g:1922:5: otherlv_2= 'identified' otherlv_3= 'as' ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) ) otherlv_6= 'by' ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) )
                            {
                            otherlv_2=(Token)match(input,65,FOLLOW_48); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getConceptAccess().getIdentifiedKeyword_0_2_0());
                              				
                            }
                            otherlv_3=(Token)match(input,39,FOLLOW_49); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_3, grammarAccess.getConceptAccess().getAsKeyword_0_2_1());
                              				
                            }
                            // InternalKdl.g:1930:5: ( ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) ) | ( (lv_intIdentifier_5_0= RULE_INT ) ) )
                            int alt46=2;
                            int LA46_0 = input.LA(1);

                            if ( (LA46_0==RULE_STRING||LA46_0==RULE_ID) ) {
                                alt46=1;
                            }
                            else if ( (LA46_0==RULE_INT) ) {
                                alt46=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 46, 0, input);

                                throw nvae;
                            }
                            switch (alt46) {
                                case 1 :
                                    // InternalKdl.g:1931:6: ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) )
                                    {
                                    // InternalKdl.g:1931:6: ( ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) ) )
                                    // InternalKdl.g:1932:7: ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) )
                                    {
                                    // InternalKdl.g:1932:7: ( (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING ) )
                                    // InternalKdl.g:1933:8: (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING )
                                    {
                                    // InternalKdl.g:1933:8: (lv_stringIdentifier_4_1= RULE_ID | lv_stringIdentifier_4_2= RULE_STRING )
                                    int alt45=2;
                                    int LA45_0 = input.LA(1);

                                    if ( (LA45_0==RULE_ID) ) {
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
                                            // InternalKdl.g:1934:9: lv_stringIdentifier_4_1= RULE_ID
                                            {
                                            lv_stringIdentifier_4_1=(Token)match(input,RULE_ID,FOLLOW_50); if (state.failed) return current;
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
                                            // InternalKdl.g:1949:9: lv_stringIdentifier_4_2= RULE_STRING
                                            {
                                            lv_stringIdentifier_4_2=(Token)match(input,RULE_STRING,FOLLOW_50); if (state.failed) return current;
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
                                    // InternalKdl.g:1967:6: ( (lv_intIdentifier_5_0= RULE_INT ) )
                                    {
                                    // InternalKdl.g:1967:6: ( (lv_intIdentifier_5_0= RULE_INT ) )
                                    // InternalKdl.g:1968:7: (lv_intIdentifier_5_0= RULE_INT )
                                    {
                                    // InternalKdl.g:1968:7: (lv_intIdentifier_5_0= RULE_INT )
                                    // InternalKdl.g:1969:8: lv_intIdentifier_5_0= RULE_INT
                                    {
                                    lv_intIdentifier_5_0=(Token)match(input,RULE_INT,FOLLOW_50); if (state.failed) return current;
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

                            otherlv_6=(Token)match(input,66,FOLLOW_51); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_6, grammarAccess.getConceptAccess().getByKeyword_0_2_3());
                              				
                            }
                            // InternalKdl.g:1990:5: ( ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) ) )
                            // InternalKdl.g:1991:6: ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) )
                            {
                            // InternalKdl.g:1991:6: ( (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH ) )
                            // InternalKdl.g:1992:7: (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH )
                            {
                            // InternalKdl.g:1992:7: (lv_authority_7_1= RULE_UPPERCASE_ID | lv_authority_7_2= RULE_UPPERCASE_PATH )
                            int alt47=2;
                            int LA47_0 = input.LA(1);

                            if ( (LA47_0==RULE_UPPERCASE_ID) ) {
                                alt47=1;
                            }
                            else if ( (LA47_0==RULE_UPPERCASE_PATH) ) {
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
                                    // InternalKdl.g:1993:8: lv_authority_7_1= RULE_UPPERCASE_ID
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
                                    // InternalKdl.g:2008:8: lv_authority_7_2= RULE_UPPERCASE_PATH
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
                    // InternalKdl.g:2028:3: ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2028:3: ( ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2029:4: ( (lv_presence_8_0= 'presence' ) ) otherlv_9= 'of' ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2029:4: ( (lv_presence_8_0= 'presence' ) )
                    // InternalKdl.g:2030:5: (lv_presence_8_0= 'presence' )
                    {
                    // InternalKdl.g:2030:5: (lv_presence_8_0= 'presence' )
                    // InternalKdl.g:2031:6: lv_presence_8_0= 'presence'
                    {
                    lv_presence_8_0=(Token)match(input,67,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_9=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getConceptAccess().getOfKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:2047:4: ( (lv_concept_10_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2048:5: (lv_concept_10_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2048:5: (lv_concept_10_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2049:6: lv_concept_10_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2068:3: ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2068:3: ( ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2069:4: ( (lv_count_11_0= 'count' ) ) otherlv_12= 'of' ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2069:4: ( (lv_count_11_0= 'count' ) )
                    // InternalKdl.g:2070:5: (lv_count_11_0= 'count' )
                    {
                    // InternalKdl.g:2070:5: (lv_count_11_0= 'count' )
                    // InternalKdl.g:2071:6: lv_count_11_0= 'count'
                    {
                    lv_count_11_0=(Token)match(input,68,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_12=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getConceptAccess().getOfKeyword_2_1());
                      			
                    }
                    // InternalKdl.g:2087:4: ( (lv_concept_13_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2088:5: (lv_concept_13_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2088:5: (lv_concept_13_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2089:6: lv_concept_13_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2108:3: ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2108:3: ( ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2109:4: ( (lv_distance_14_0= 'distance' ) ) (otherlv_15= 'to' | otherlv_16= 'from' ) ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2109:4: ( (lv_distance_14_0= 'distance' ) )
                    // InternalKdl.g:2110:5: (lv_distance_14_0= 'distance' )
                    {
                    // InternalKdl.g:2110:5: (lv_distance_14_0= 'distance' )
                    // InternalKdl.g:2111:6: lv_distance_14_0= 'distance'
                    {
                    lv_distance_14_0=(Token)match(input,69,FOLLOW_53); if (state.failed) return current;
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

                    // InternalKdl.g:2123:4: (otherlv_15= 'to' | otherlv_16= 'from' )
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==52) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==70) ) {
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
                            // InternalKdl.g:2124:5: otherlv_15= 'to'
                            {
                            otherlv_15=(Token)match(input,52,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_15, grammarAccess.getConceptAccess().getToKeyword_3_1_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:2129:5: otherlv_16= 'from'
                            {
                            otherlv_16=(Token)match(input,70,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_16, grammarAccess.getConceptAccess().getFromKeyword_3_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:2134:4: ( (lv_concept_17_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2135:5: (lv_concept_17_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2135:5: (lv_concept_17_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2136:6: lv_concept_17_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2155:3: ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2155:3: ( ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2156:4: ( (lv_probability_18_0= 'probability' ) ) otherlv_19= 'of' ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2156:4: ( (lv_probability_18_0= 'probability' ) )
                    // InternalKdl.g:2157:5: (lv_probability_18_0= 'probability' )
                    {
                    // InternalKdl.g:2157:5: (lv_probability_18_0= 'probability' )
                    // InternalKdl.g:2158:6: lv_probability_18_0= 'probability'
                    {
                    lv_probability_18_0=(Token)match(input,71,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_19=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getConceptAccess().getOfKeyword_4_1());
                      			
                    }
                    // InternalKdl.g:2174:4: ( (lv_concept_20_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2175:5: (lv_concept_20_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2175:5: (lv_concept_20_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2176:6: lv_concept_20_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2195:3: ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2195:3: ( ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2196:4: ( (lv_uncertainty_21_0= 'uncertainty' ) ) otherlv_22= 'of' ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2196:4: ( (lv_uncertainty_21_0= 'uncertainty' ) )
                    // InternalKdl.g:2197:5: (lv_uncertainty_21_0= 'uncertainty' )
                    {
                    // InternalKdl.g:2197:5: (lv_uncertainty_21_0= 'uncertainty' )
                    // InternalKdl.g:2198:6: lv_uncertainty_21_0= 'uncertainty'
                    {
                    lv_uncertainty_21_0=(Token)match(input,72,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_22=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_22, grammarAccess.getConceptAccess().getOfKeyword_5_1());
                      			
                    }
                    // InternalKdl.g:2214:4: ( (lv_concept_23_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2215:5: (lv_concept_23_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2215:5: (lv_concept_23_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2216:6: lv_concept_23_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2235:3: ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    {
                    // InternalKdl.g:2235:3: ( ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    // InternalKdl.g:2236:4: ( (lv_proportion_24_0= 'proportion' ) ) otherlv_25= 'of' ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )?
                    {
                    // InternalKdl.g:2236:4: ( (lv_proportion_24_0= 'proportion' ) )
                    // InternalKdl.g:2237:5: (lv_proportion_24_0= 'proportion' )
                    {
                    // InternalKdl.g:2237:5: (lv_proportion_24_0= 'proportion' )
                    // InternalKdl.g:2238:6: lv_proportion_24_0= 'proportion'
                    {
                    lv_proportion_24_0=(Token)match(input,73,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_25=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_25, grammarAccess.getConceptAccess().getOfKeyword_6_1());
                      			
                    }
                    // InternalKdl.g:2254:4: ( (lv_concept_26_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2255:5: (lv_concept_26_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2255:5: (lv_concept_26_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2256:6: lv_concept_26_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_6_2_0());
                      					
                    }
                    pushFollow(FOLLOW_54);
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

                    // InternalKdl.g:2273:4: ( ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) ) )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==74) ) {
                        int LA50_1 = input.LA(2);

                        if ( (synpred79_InternalKdl()) ) {
                            alt50=1;
                        }
                    }
                    switch (alt50) {
                        case 1 :
                            // InternalKdl.g:2274:5: ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) )
                            {
                            // InternalKdl.g:2283:5: (otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) ) )
                            // InternalKdl.g:2284:6: otherlv_27= 'in' ( (lv_other_28_0= ruleSimpleConceptDeclaration ) )
                            {
                            otherlv_27=(Token)match(input,74,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_27, grammarAccess.getConceptAccess().getInKeyword_6_3_0_0());
                              					
                            }
                            // InternalKdl.g:2288:6: ( (lv_other_28_0= ruleSimpleConceptDeclaration ) )
                            // InternalKdl.g:2289:7: (lv_other_28_0= ruleSimpleConceptDeclaration )
                            {
                            // InternalKdl.g:2289:7: (lv_other_28_0= ruleSimpleConceptDeclaration )
                            // InternalKdl.g:2290:8: lv_other_28_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2311:3: ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2311:3: ( ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2312:4: ( (lv_ratio_29_0= 'ratio' ) ) otherlv_30= 'of' ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) ) ( ( 'to' )=>otherlv_32= 'to' ) ( (lv_other_33_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2312:4: ( (lv_ratio_29_0= 'ratio' ) )
                    // InternalKdl.g:2313:5: (lv_ratio_29_0= 'ratio' )
                    {
                    // InternalKdl.g:2313:5: (lv_ratio_29_0= 'ratio' )
                    // InternalKdl.g:2314:6: lv_ratio_29_0= 'ratio'
                    {
                    lv_ratio_29_0=(Token)match(input,75,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_30=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_30, grammarAccess.getConceptAccess().getOfKeyword_7_1());
                      			
                    }
                    // InternalKdl.g:2330:4: ( (lv_concept_31_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2331:5: (lv_concept_31_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2331:5: (lv_concept_31_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2332:6: lv_concept_31_0= ruleSimpleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getConceptSimpleConceptDeclarationParserRuleCall_7_2_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    // InternalKdl.g:2349:4: ( ( 'to' )=>otherlv_32= 'to' )
                    // InternalKdl.g:2350:5: ( 'to' )=>otherlv_32= 'to'
                    {
                    otherlv_32=(Token)match(input,52,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_32, grammarAccess.getConceptAccess().getToKeyword_7_3());
                      				
                    }

                    }

                    // InternalKdl.g:2356:4: ( (lv_other_33_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2357:5: (lv_other_33_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2357:5: (lv_other_33_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2358:6: lv_other_33_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2377:3: ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    {
                    // InternalKdl.g:2377:3: ( ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )? )
                    // InternalKdl.g:2378:4: ( (lv_value_34_0= 'value' ) ) otherlv_35= 'of' ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) ) ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )?
                    {
                    // InternalKdl.g:2378:4: ( (lv_value_34_0= 'value' ) )
                    // InternalKdl.g:2379:5: (lv_value_34_0= 'value' )
                    {
                    // InternalKdl.g:2379:5: (lv_value_34_0= 'value' )
                    // InternalKdl.g:2380:6: lv_value_34_0= 'value'
                    {
                    lv_value_34_0=(Token)match(input,76,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_35=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getConceptAccess().getOfKeyword_8_1());
                      			
                    }
                    // InternalKdl.g:2396:4: ( (lv_concept_36_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2397:5: (lv_concept_36_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2397:5: (lv_concept_36_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2398:6: lv_concept_36_0= ruleSimpleConceptDeclaration
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

                    // InternalKdl.g:2415:4: ( ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) ) )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==40) ) {
                        int LA51_1 = input.LA(2);

                        if ( (synpred83_InternalKdl()) ) {
                            alt51=1;
                        }
                    }
                    switch (alt51) {
                        case 1 :
                            // InternalKdl.g:2416:5: ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )=> (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) )
                            {
                            // InternalKdl.g:2425:5: (otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) ) )
                            // InternalKdl.g:2426:6: otherlv_37= 'over' ( (lv_other_38_0= ruleSimpleConceptDeclaration ) )
                            {
                            otherlv_37=(Token)match(input,40,FOLLOW_38); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_37, grammarAccess.getConceptAccess().getOverKeyword_8_3_0_0());
                              					
                            }
                            // InternalKdl.g:2430:6: ( (lv_other_38_0= ruleSimpleConceptDeclaration ) )
                            // InternalKdl.g:2431:7: (lv_other_38_0= ruleSimpleConceptDeclaration )
                            {
                            // InternalKdl.g:2431:7: (lv_other_38_0= ruleSimpleConceptDeclaration )
                            // InternalKdl.g:2432:8: lv_other_38_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2453:3: ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) )
                    {
                    // InternalKdl.g:2453:3: ( ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) ) )
                    // InternalKdl.g:2454:4: ( (lv_occurrence_39_0= 'occurrence' ) ) otherlv_40= 'of' ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) )
                    {
                    // InternalKdl.g:2454:4: ( (lv_occurrence_39_0= 'occurrence' ) )
                    // InternalKdl.g:2455:5: (lv_occurrence_39_0= 'occurrence' )
                    {
                    // InternalKdl.g:2455:5: (lv_occurrence_39_0= 'occurrence' )
                    // InternalKdl.g:2456:6: lv_occurrence_39_0= 'occurrence'
                    {
                    lv_occurrence_39_0=(Token)match(input,77,FOLLOW_52); if (state.failed) return current;
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

                    otherlv_40=(Token)match(input,62,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_40, grammarAccess.getConceptAccess().getOfKeyword_9_1());
                      			
                    }
                    // InternalKdl.g:2472:4: ( (lv_concept_41_0= ruleSimpleConceptDeclaration ) )
                    // InternalKdl.g:2473:5: (lv_concept_41_0= ruleSimpleConceptDeclaration )
                    {
                    // InternalKdl.g:2473:5: (lv_concept_41_0= ruleSimpleConceptDeclaration )
                    // InternalKdl.g:2474:6: lv_concept_41_0= ruleSimpleConceptDeclaration
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
                    // InternalKdl.g:2493:3: (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' )
                    {
                    // InternalKdl.g:2493:3: (otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')' )
                    // InternalKdl.g:2494:4: otherlv_42= '(' ( (lv_declaration_43_0= ruleConceptDeclaration ) ) otherlv_44= ')'
                    {
                    otherlv_42=(Token)match(input,78,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_42, grammarAccess.getConceptAccess().getLeftParenthesisKeyword_10_0());
                      			
                    }
                    // InternalKdl.g:2498:4: ( (lv_declaration_43_0= ruleConceptDeclaration ) )
                    // InternalKdl.g:2499:5: (lv_declaration_43_0= ruleConceptDeclaration )
                    {
                    // InternalKdl.g:2499:5: (lv_declaration_43_0= ruleConceptDeclaration )
                    // InternalKdl.g:2500:6: lv_declaration_43_0= ruleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getConceptAccess().getDeclarationConceptDeclarationParserRuleCall_10_1_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
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

                    otherlv_44=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:2526:1: entryRuleObservableSemantics returns [EObject current=null] : iv_ruleObservableSemantics= ruleObservableSemantics EOF ;
    public final EObject entryRuleObservableSemantics() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObservableSemantics = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1()
        	);

        try {
            // InternalKdl.g:2530:2: (iv_ruleObservableSemantics= ruleObservableSemantics EOF )
            // InternalKdl.g:2531:2: iv_ruleObservableSemantics= ruleObservableSemantics EOF
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
    // InternalKdl.g:2540:1: ruleObservableSemantics returns [EObject current=null] : ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) ) ;
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
            // InternalKdl.g:2549:2: ( ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) ) )
            // InternalKdl.g:2550:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) )
            {
            // InternalKdl.g:2550:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) ) )
            // InternalKdl.g:2551:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) )
            {
            // InternalKdl.g:2551:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) )
            // InternalKdl.g:2552:4: (lv_declaration_0_0= ruleConceptDeclaration )
            {
            // InternalKdl.g:2552:4: (lv_declaration_0_0= ruleConceptDeclaration )
            // InternalKdl.g:2553:5: lv_declaration_0_0= ruleConceptDeclaration
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObservableSemanticsAccess().getDeclarationConceptDeclarationParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_56);
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

            // InternalKdl.g:2570:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) ) )
            // InternalKdl.g:2571:4: ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) )
            {
            // InternalKdl.g:2571:4: ( ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* ) )
            // InternalKdl.g:2572:5: ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1());
            // InternalKdl.g:2575:5: ( ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )* )
            // InternalKdl.g:2576:6: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )*
            {
            // InternalKdl.g:2576:6: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )*
            loop56:
            do {
                int alt56=6;
                alt56 = dfa56.predict(input);
                switch (alt56) {
            	case 1 :
            	    // InternalKdl.g:2577:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2577:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
            	    // InternalKdl.g:2578:5: {...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0)");
            	    }
            	    // InternalKdl.g:2578:116: ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
            	    // InternalKdl.g:2579:6: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0);
            	    // InternalKdl.g:2582:9: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
            	    // InternalKdl.g:2582:10: {...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2582:19: (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
            	    // InternalKdl.g:2582:20: otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) )
            	    {
            	    otherlv_2=(Token)match(input,66,FOLLOW_38); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_2, grammarAccess.getObservableSemanticsAccess().getByKeyword_1_0_0());
            	      								
            	    }
            	    // InternalKdl.g:2586:9: ( (lv_by_3_0= ruleConcept ) )
            	    // InternalKdl.g:2587:10: (lv_by_3_0= ruleConcept )
            	    {
            	    // InternalKdl.g:2587:10: (lv_by_3_0= ruleConcept )
            	    // InternalKdl.g:2588:11: lv_by_3_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getByConceptParserRuleCall_1_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_56);
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
            	    // InternalKdl.g:2611:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2611:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
            	    // InternalKdl.g:2612:5: {...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1)");
            	    }
            	    // InternalKdl.g:2612:116: ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
            	    // InternalKdl.g:2613:6: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1);
            	    // InternalKdl.g:2616:9: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
            	    // InternalKdl.g:2616:10: {...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2616:19: (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
            	    // InternalKdl.g:2616:20: otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
            	    {
            	    otherlv_4=(Token)match(input,80,FOLLOW_36); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_4, grammarAccess.getObservableSemanticsAccess().getDownKeyword_1_1_0());
            	      								
            	    }
            	    otherlv_5=(Token)match(input,52,FOLLOW_57); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_5, grammarAccess.getObservableSemanticsAccess().getToKeyword_1_1_1());
            	      								
            	    }
            	    // InternalKdl.g:2624:9: ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
            	    // InternalKdl.g:2625:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
            	    {
            	    // InternalKdl.g:2625:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
            	    // InternalKdl.g:2626:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
            	    {
            	    // InternalKdl.g:2626:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
            	    int alt53=2;
            	    int LA53_0 = input.LA(1);

            	    if ( (LA53_0==RULE_CAMELCASE_ID) ) {
            	        alt53=1;
            	    }
            	    else if ( (LA53_0==RULE_LOWERCASE_ID) ) {
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
            	            // InternalKdl.g:2627:12: lv_downTo_6_1= RULE_CAMELCASE_ID
            	            {
            	            lv_downTo_6_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_56); if (state.failed) return current;
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
            	            // InternalKdl.g:2642:12: lv_downTo_6_2= ruleNamespaceId
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getObservableSemanticsAccess().getDownToNamespaceIdParserRuleCall_1_1_2_0_1());
            	              											
            	            }
            	            pushFollow(FOLLOW_56);
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
            	    // InternalKdl.g:2666:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2666:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
            	    // InternalKdl.g:2667:5: {...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2)");
            	    }
            	    // InternalKdl.g:2667:116: ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
            	    // InternalKdl.g:2668:6: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2);
            	    // InternalKdl.g:2671:9: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
            	    // InternalKdl.g:2671:10: {...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2671:19: (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
            	    // InternalKdl.g:2671:20: otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) )
            	    {
            	    otherlv_7=(Token)match(input,39,FOLLOW_38); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getObservableSemanticsAccess().getAsKeyword_1_2_0());
            	      								
            	    }
            	    // InternalKdl.g:2675:9: ( (lv_role_8_0= ruleConcept ) )
            	    // InternalKdl.g:2676:10: (lv_role_8_0= ruleConcept )
            	    {
            	    // InternalKdl.g:2676:10: (lv_role_8_0= ruleConcept )
            	    // InternalKdl.g:2677:11: lv_role_8_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getRoleConceptParserRuleCall_1_2_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_56);
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
            	    // InternalKdl.g:2700:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2700:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
            	    // InternalKdl.g:2701:5: {...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3)");
            	    }
            	    // InternalKdl.g:2701:116: ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
            	    // InternalKdl.g:2702:6: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3);
            	    // InternalKdl.g:2705:9: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
            	    // InternalKdl.g:2705:10: {...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2705:19: ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
            	    int alt55=2;
            	    int LA55_0 = input.LA(1);

            	    if ( (LA55_0==74) ) {
            	        alt55=1;
            	    }
            	    else if ( (LA55_0==81) ) {
            	        alt55=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 55, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt55) {
            	        case 1 :
            	            // InternalKdl.g:2705:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
            	            {
            	            // InternalKdl.g:2705:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
            	            // InternalKdl.g:2706:10: otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
            	            {
            	            otherlv_9=(Token)match(input,74,FOLLOW_58); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_9, grammarAccess.getObservableSemanticsAccess().getInKeyword_1_3_0_0());
            	              									
            	            }
            	            // InternalKdl.g:2710:10: ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
            	            int alt54=2;
            	            switch ( input.LA(1) ) {
            	            case RULE_CAMELCASE_ID:
            	                {
            	                int LA54_1 = input.LA(2);

            	                if ( (synpred90_InternalKdl()) ) {
            	                    alt54=1;
            	                }
            	                else if ( (true) ) {
            	                    alt54=2;
            	                }
            	                else {
            	                    if (state.backtracking>0) {state.failed=true; return current;}
            	                    NoViableAltException nvae =
            	                        new NoViableAltException("", 54, 1, input);

            	                    throw nvae;
            	                }
            	                }
            	                break;
            	            case RULE_LOWERCASE_ID:
            	                {
            	                int LA54_2 = input.LA(2);

            	                if ( (LA54_2==104||LA54_2==107) ) {
            	                    alt54=2;
            	                }
            	                else if ( (LA54_2==EOF||LA54_2==RULE_INT||(LA54_2>=30 && LA54_2<=32)||(LA54_2>=34 && LA54_2<=35)||(LA54_2>=38 && LA54_2<=39)||(LA54_2>=41 && LA54_2<=45)||(LA54_2>=55 && LA54_2<=61)||LA54_2==66||LA54_2==74||LA54_2==76||(LA54_2>=80 && LA54_2<=91)||LA54_2==106||LA54_2==118||LA54_2==121) ) {
            	                    alt54=1;
            	                }
            	                else {
            	                    if (state.backtracking>0) {state.failed=true; return current;}
            	                    NoViableAltException nvae =
            	                        new NoViableAltException("", 54, 2, input);

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
            	            case 35:
            	            case 38:
            	            case 39:
            	            case 41:
            	            case 42:
            	            case 43:
            	            case 44:
            	            case 45:
            	            case 55:
            	            case 56:
            	            case 57:
            	            case 58:
            	            case 59:
            	            case 60:
            	            case 61:
            	            case 66:
            	            case 74:
            	            case 76:
            	            case 78:
            	            case 80:
            	            case 81:
            	            case 82:
            	            case 83:
            	            case 84:
            	            case 85:
            	            case 86:
            	            case 87:
            	            case 88:
            	            case 89:
            	            case 90:
            	            case 91:
            	            case 106:
            	            case 118:
            	            case 121:
            	                {
            	                alt54=1;
            	                }
            	                break;
            	            case RULE_ID:
            	                {
            	                alt54=2;
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
            	                    // InternalKdl.g:2711:11: ( (lv_unit_10_0= ruleUnit ) )
            	                    {
            	                    // InternalKdl.g:2711:11: ( (lv_unit_10_0= ruleUnit ) )
            	                    // InternalKdl.g:2712:12: (lv_unit_10_0= ruleUnit )
            	                    {
            	                    // InternalKdl.g:2712:12: (lv_unit_10_0= ruleUnit )
            	                    // InternalKdl.g:2713:13: lv_unit_10_0= ruleUnit
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_0_1_0_0());
            	                      												
            	                    }
            	                    pushFollow(FOLLOW_56);
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
            	                    // InternalKdl.g:2731:11: ( (lv_currency_11_0= ruleCurrency ) )
            	                    {
            	                    // InternalKdl.g:2731:11: ( (lv_currency_11_0= ruleCurrency ) )
            	                    // InternalKdl.g:2732:12: (lv_currency_11_0= ruleCurrency )
            	                    {
            	                    // InternalKdl.g:2732:12: (lv_currency_11_0= ruleCurrency )
            	                    // InternalKdl.g:2733:13: lv_currency_11_0= ruleCurrency
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      													newCompositeNode(grammarAccess.getObservableSemanticsAccess().getCurrencyCurrencyParserRuleCall_1_3_0_1_1_0());
            	                      												
            	                    }
            	                    pushFollow(FOLLOW_56);
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
            	            // InternalKdl.g:2753:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
            	            {
            	            // InternalKdl.g:2753:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
            	            // InternalKdl.g:2754:10: otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) )
            	            {
            	            otherlv_12=(Token)match(input,81,FOLLOW_59); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(otherlv_12, grammarAccess.getObservableSemanticsAccess().getPerKeyword_1_3_1_0());
            	              									
            	            }
            	            // InternalKdl.g:2758:10: ( (lv_unit_13_0= ruleUnit ) )
            	            // InternalKdl.g:2759:11: (lv_unit_13_0= ruleUnit )
            	            {
            	            // InternalKdl.g:2759:11: (lv_unit_13_0= ruleUnit )
            	            // InternalKdl.g:2760:12: lv_unit_13_0= ruleUnit
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getObservableSemanticsAccess().getUnitUnitParserRuleCall_1_3_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_56);
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
            	    // InternalKdl.g:2784:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
            	    {
            	    // InternalKdl.g:2784:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
            	    // InternalKdl.g:2785:5: {...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4)");
            	    }
            	    // InternalKdl.g:2785:116: ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
            	    // InternalKdl.g:2786:6: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4);
            	    // InternalKdl.g:2789:9: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
            	    // InternalKdl.g:2789:10: {...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleObservableSemantics", "true");
            	    }
            	    // InternalKdl.g:2789:19: ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
            	    // InternalKdl.g:2789:20: ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) )
            	    {
            	    // InternalKdl.g:2789:20: ( (lv_from_14_0= ruleNumber ) )
            	    // InternalKdl.g:2790:10: (lv_from_14_0= ruleNumber )
            	    {
            	    // InternalKdl.g:2790:10: (lv_from_14_0= ruleNumber )
            	    // InternalKdl.g:2791:11: lv_from_14_0= ruleNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getFromNumberParserRuleCall_1_4_0_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_36);
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

            	    otherlv_15=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_15, grammarAccess.getObservableSemanticsAccess().getToKeyword_1_4_1());
            	      								
            	    }
            	    // InternalKdl.g:2812:9: ( (lv_to_16_0= ruleNumber ) )
            	    // InternalKdl.g:2813:10: (lv_to_16_0= ruleNumber )
            	    {
            	    // InternalKdl.g:2813:10: (lv_to_16_0= ruleNumber )
            	    // InternalKdl.g:2814:11: lv_to_16_0= ruleNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getToNumberParserRuleCall_1_4_2_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_56);
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
            	    break loop56;
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
    // InternalKdl.g:2851:1: entryRuleSimpleConceptDeclaration returns [EObject current=null] : iv_ruleSimpleConceptDeclaration= ruleSimpleConceptDeclaration EOF ;
    public final EObject entryRuleSimpleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleConceptDeclaration = null;


        try {
            // InternalKdl.g:2851:65: (iv_ruleSimpleConceptDeclaration= ruleSimpleConceptDeclaration EOF )
            // InternalKdl.g:2852:2: iv_ruleSimpleConceptDeclaration= ruleSimpleConceptDeclaration EOF
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
    // InternalKdl.g:2858:1: ruleSimpleConceptDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ ) ;
    public final EObject ruleSimpleConceptDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_main_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2864:2: ( ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ ) )
            // InternalKdl.g:2865:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ )
            {
            // InternalKdl.g:2865:2: ( ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+ )
            // InternalKdl.g:2866:3: ( (lv_name_0_0= RULE_STRING ) )? ( (lv_main_1_0= ruleConcept ) )+
            {
            // InternalKdl.g:2866:3: ( (lv_name_0_0= RULE_STRING ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_STRING) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalKdl.g:2867:4: (lv_name_0_0= RULE_STRING )
                    {
                    // InternalKdl.g:2867:4: (lv_name_0_0= RULE_STRING )
                    // InternalKdl.g:2868:5: lv_name_0_0= RULE_STRING
                    {
                    lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_38); if (state.failed) return current;
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

            // InternalKdl.g:2884:3: ( (lv_main_1_0= ruleConcept ) )+
            int cnt58=0;
            loop58:
            do {
                int alt58=2;
                alt58 = dfa58.predict(input);
                switch (alt58) {
            	case 1 :
            	    // InternalKdl.g:2885:4: (lv_main_1_0= ruleConcept )
            	    {
            	    // InternalKdl.g:2885:4: (lv_main_1_0= ruleConcept )
            	    // InternalKdl.g:2886:5: lv_main_1_0= ruleConcept
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getSimpleConceptDeclarationAccess().getMainConceptParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_60);
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
            	    if ( cnt58 >= 1 ) break loop58;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(58, input);
                        throw eee;
                }
                cnt58++;
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
    // InternalKdl.g:2907:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalKdl.g:2907:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalKdl.g:2908:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalKdl.g:2914:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_docstring_2_0=null;
        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:2920:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? ) )
            // InternalKdl.g:2921:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            {
            // InternalKdl.g:2921:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )? )
            // InternalKdl.g:2922:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( (lv_value_1_0= ruleValue ) ) ( (lv_docstring_2_0= RULE_STRING ) )?
            {
            // InternalKdl.g:2922:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:2923:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:2923:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:2924:5: lv_name_0_0= RULE_LOWERCASE_ID
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

            // InternalKdl.g:2940:3: ( (lv_value_1_0= ruleValue ) )
            // InternalKdl.g:2941:4: (lv_value_1_0= ruleValue )
            {
            // InternalKdl.g:2941:4: (lv_value_1_0= ruleValue )
            // InternalKdl.g:2942:5: lv_value_1_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_61);
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

            // InternalKdl.g:2959:3: ( (lv_docstring_2_0= RULE_STRING ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==RULE_STRING) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalKdl.g:2960:4: (lv_docstring_2_0= RULE_STRING )
                    {
                    // InternalKdl.g:2960:4: (lv_docstring_2_0= RULE_STRING )
                    // InternalKdl.g:2961:5: lv_docstring_2_0= RULE_STRING
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
    // InternalKdl.g:2981:1: entryRuleACTOR returns [String current=null] : iv_ruleACTOR= ruleACTOR EOF ;
    public final String entryRuleACTOR() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleACTOR = null;


        try {
            // InternalKdl.g:2981:45: (iv_ruleACTOR= ruleACTOR EOF )
            // InternalKdl.g:2982:2: iv_ruleACTOR= ruleACTOR EOF
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
    // InternalKdl.g:2988:1: ruleACTOR returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) ;
    public final AntlrDatatypeRuleToken ruleACTOR() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:2994:2: ( (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' ) )
            // InternalKdl.g:2995:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            {
            // InternalKdl.g:2995:2: (kw= 'object' | kw= 'process' | kw= 'value' | kw= 'number' | kw= 'concept' | kw= 'boolean' | kw= 'text' | kw= 'extent' | kw= 'spatialextent' | kw= 'temporalextent' | kw= 'annotation' | kw= 'contextualizer' | kw= 'void' | kw= 'partition' )
            int alt60=14;
            switch ( input.LA(1) ) {
            case 82:
                {
                alt60=1;
                }
                break;
            case 83:
                {
                alt60=2;
                }
                break;
            case 76:
                {
                alt60=3;
                }
                break;
            case 41:
                {
                alt60=4;
                }
                break;
            case 84:
                {
                alt60=5;
                }
                break;
            case 42:
                {
                alt60=6;
                }
                break;
            case 43:
                {
                alt60=7;
                }
                break;
            case 85:
                {
                alt60=8;
                }
                break;
            case 86:
                {
                alt60=9;
                }
                break;
            case 87:
                {
                alt60=10;
                }
                break;
            case 88:
                {
                alt60=11;
                }
                break;
            case 89:
                {
                alt60=12;
                }
                break;
            case 90:
                {
                alt60=13;
                }
                break;
            case 91:
                {
                alt60=14;
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
                    // InternalKdl.g:2996:3: kw= 'object'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getObjectKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:3002:3: kw= 'process'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getProcessKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:3008:3: kw= 'value'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getValueKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalKdl.g:3014:3: kw= 'number'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getNumberKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalKdl.g:3020:3: kw= 'concept'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getConceptKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalKdl.g:3026:3: kw= 'boolean'
                    {
                    kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getBooleanKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalKdl.g:3032:3: kw= 'text'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTextKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalKdl.g:3038:3: kw= 'extent'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getExtentKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalKdl.g:3044:3: kw= 'spatialextent'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getSpatialextentKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalKdl.g:3050:3: kw= 'temporalextent'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getTemporalextentKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalKdl.g:3056:3: kw= 'annotation'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getAnnotationKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalKdl.g:3062:3: kw= 'contextualizer'
                    {
                    kw=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getContextualizerKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalKdl.g:3068:3: kw= 'void'
                    {
                    kw=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getACTORAccess().getVoidKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalKdl.g:3074:3: kw= 'partition'
                    {
                    kw=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3083:1: entryRuleTARGET returns [String current=null] : iv_ruleTARGET= ruleTARGET EOF ;
    public final String entryRuleTARGET() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTARGET = null;


        try {
            // InternalKdl.g:3083:46: (iv_ruleTARGET= ruleTARGET EOF )
            // InternalKdl.g:3084:2: iv_ruleTARGET= ruleTARGET EOF
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
    // InternalKdl.g:3090:1: ruleTARGET returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'models' | kw= 'concepts' | kw= 'observers' ) ;
    public final AntlrDatatypeRuleToken ruleTARGET() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalKdl.g:3096:2: ( (kw= 'models' | kw= 'concepts' | kw= 'observers' ) )
            // InternalKdl.g:3097:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' )
            {
            // InternalKdl.g:3097:2: (kw= 'models' | kw= 'concepts' | kw= 'observers' )
            int alt61=3;
            switch ( input.LA(1) ) {
            case 92:
                {
                alt61=1;
                }
                break;
            case 93:
                {
                alt61=2;
                }
                break;
            case 94:
                {
                alt61=3;
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
                    // InternalKdl.g:3098:3: kw= 'models'
                    {
                    kw=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getModelsKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:3104:3: kw= 'concepts'
                    {
                    kw=(Token)match(input,93,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getTARGETAccess().getConceptsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:3110:3: kw= 'observers'
                    {
                    kw=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3119:1: entryRuleClassification returns [EObject current=null] : iv_ruleClassification= ruleClassification EOF ;
    public final EObject entryRuleClassification() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassification = null;


        try {
            // InternalKdl.g:3119:55: (iv_ruleClassification= ruleClassification EOF )
            // InternalKdl.g:3120:2: iv_ruleClassification= ruleClassification EOF
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
    // InternalKdl.g:3126:1: ruleClassification returns [EObject current=null] : ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* ) ;
    public final EObject ruleClassification() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifiers_0_0 = null;

        EObject lv_classifiers_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3132:2: ( ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* ) )
            // InternalKdl.g:3133:2: ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* )
            {
            // InternalKdl.g:3133:2: ( ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )* )
            // InternalKdl.g:3134:3: ( (lv_classifiers_0_0= ruleClassifier ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )*
            {
            // InternalKdl.g:3134:3: ( (lv_classifiers_0_0= ruleClassifier ) )
            // InternalKdl.g:3135:4: (lv_classifiers_0_0= ruleClassifier )
            {
            // InternalKdl.g:3135:4: (lv_classifiers_0_0= ruleClassifier )
            // InternalKdl.g:3136:5: lv_classifiers_0_0= ruleClassifier
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

            // InternalKdl.g:3153:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==28) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalKdl.g:3154:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_classifiers_2_0= ruleClassifier ) )
            	    {
            	    // InternalKdl.g:3154:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKdl.g:3155:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,28,FOLLOW_38); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getClassificationAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKdl.g:3161:4: ( (lv_classifiers_2_0= ruleClassifier ) )
            	    // InternalKdl.g:3162:5: (lv_classifiers_2_0= ruleClassifier )
            	    {
            	    // InternalKdl.g:3162:5: (lv_classifiers_2_0= ruleClassifier )
            	    // InternalKdl.g:3163:6: lv_classifiers_2_0= ruleClassifier
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
            	    break loop62;
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
    // InternalKdl.g:3185:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKdl.g:3185:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKdl.g:3186:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKdl.g:3192:1: ruleClassifier returns [EObject current=null] : ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? ) ;
    public final EObject ruleClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_otherwise_1_0=null;
        Token otherlv_2=null;
        Token lv_negated_3_0=null;
        EObject lv_declaration_0_0 = null;

        EObject lv_classifier_4_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3198:2: ( ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? ) )
            // InternalKdl.g:3199:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? )
            {
            // InternalKdl.g:3199:2: ( ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )? )
            // InternalKdl.g:3200:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) ) ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )?
            {
            // InternalKdl.g:3200:3: ( (lv_declaration_0_0= ruleConceptDeclaration ) )
            // InternalKdl.g:3201:4: (lv_declaration_0_0= ruleConceptDeclaration )
            {
            // InternalKdl.g:3201:4: (lv_declaration_0_0= ruleConceptDeclaration )
            // InternalKdl.g:3202:5: lv_declaration_0_0= ruleConceptDeclaration
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getClassifierAccess().getDeclarationConceptDeclarationParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_62);
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

            // InternalKdl.g:3219:3: ( ( (lv_otherwise_1_0= 'otherwise' ) ) | ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) ) )?
            int alt64=3;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==95) ) {
                alt64=1;
            }
            else if ( ((LA64_0>=96 && LA64_0<=97)) ) {
                alt64=2;
            }
            switch (alt64) {
                case 1 :
                    // InternalKdl.g:3220:4: ( (lv_otherwise_1_0= 'otherwise' ) )
                    {
                    // InternalKdl.g:3220:4: ( (lv_otherwise_1_0= 'otherwise' ) )
                    // InternalKdl.g:3221:5: (lv_otherwise_1_0= 'otherwise' )
                    {
                    // InternalKdl.g:3221:5: (lv_otherwise_1_0= 'otherwise' )
                    // InternalKdl.g:3222:6: lv_otherwise_1_0= 'otherwise'
                    {
                    lv_otherwise_1_0=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3235:4: ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) )
                    {
                    // InternalKdl.g:3235:4: ( (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) ) )
                    // InternalKdl.g:3236:5: (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) ) ( (lv_classifier_4_0= ruleClassifierRHS ) )
                    {
                    // InternalKdl.g:3236:5: (otherlv_2= 'if' | ( (lv_negated_3_0= 'unless' ) ) )
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==96) ) {
                        alt63=1;
                    }
                    else if ( (LA63_0==97) ) {
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
                            // InternalKdl.g:3237:6: otherlv_2= 'if'
                            {
                            otherlv_2=(Token)match(input,96,FOLLOW_63); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_2, grammarAccess.getClassifierAccess().getIfKeyword_1_1_0_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:3242:6: ( (lv_negated_3_0= 'unless' ) )
                            {
                            // InternalKdl.g:3242:6: ( (lv_negated_3_0= 'unless' ) )
                            // InternalKdl.g:3243:7: (lv_negated_3_0= 'unless' )
                            {
                            // InternalKdl.g:3243:7: (lv_negated_3_0= 'unless' )
                            // InternalKdl.g:3244:8: lv_negated_3_0= 'unless'
                            {
                            lv_negated_3_0=(Token)match(input,97,FOLLOW_63); if (state.failed) return current;
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

                    // InternalKdl.g:3257:5: ( (lv_classifier_4_0= ruleClassifierRHS ) )
                    // InternalKdl.g:3258:6: (lv_classifier_4_0= ruleClassifierRHS )
                    {
                    // InternalKdl.g:3258:6: (lv_classifier_4_0= ruleClassifierRHS )
                    // InternalKdl.g:3259:7: lv_classifier_4_0= ruleClassifierRHS
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
    // InternalKdl.g:3282:1: entryRuleClassifierRHS returns [EObject current=null] : iv_ruleClassifierRHS= ruleClassifierRHS EOF ;
    public final EObject entryRuleClassifierRHS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifierRHS = null;


        try {
            // InternalKdl.g:3282:54: (iv_ruleClassifierRHS= ruleClassifierRHS EOF )
            // InternalKdl.g:3283:2: iv_ruleClassifierRHS= ruleClassifierRHS EOF
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
    // InternalKdl.g:3289:1: ruleClassifierRHS returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) ;
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
            // InternalKdl.g:3295:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) ) )
            // InternalKdl.g:3296:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            {
            // InternalKdl.g:3296:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )
            int alt69=10;
            alt69 = dfa69.predict(input);
            switch (alt69) {
                case 1 :
                    // InternalKdl.g:3297:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKdl.g:3297:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==98) ) {
                        alt65=1;
                    }
                    else if ( (LA65_0==99) ) {
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
                            // InternalKdl.g:3298:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKdl.g:3298:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKdl.g:3299:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKdl.g:3299:5: (lv_boolean_0_0= 'true' )
                            // InternalKdl.g:3300:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3313:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKdl.g:3313:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKdl.g:3314:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKdl.g:3314:5: (lv_boolean_1_0= 'false' )
                            // InternalKdl.g:3315:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3329:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKdl.g:3329:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKdl.g:3330:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKdl.g:3330:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKdl.g:3331:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3331:5: (lv_int0_2_0= ruleNumber )
                    // InternalKdl.g:3332:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_64);
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

                    // InternalKdl.g:3349:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt66=3;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==100) ) {
                        alt66=1;
                    }
                    else if ( (LA66_0==101) ) {
                        alt66=2;
                    }
                    switch (alt66) {
                        case 1 :
                            // InternalKdl.g:3350:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3350:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKdl.g:3351:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKdl.g:3351:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKdl.g:3352:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,100,FOLLOW_36); if (state.failed) return current;
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
                            // InternalKdl.g:3365:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,101,FOLLOW_36); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierRHSAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:3370:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKdl.g:3371:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierRHSAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKdl.g:3377:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKdl.g:3378:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKdl.g:3382:5: (lv_int1_6_0= ruleNumber )
                    // InternalKdl.g:3383:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_65);
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

                    // InternalKdl.g:3400:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt67=3;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==100) ) {
                        alt67=1;
                    }
                    else if ( (LA67_0==101) ) {
                        alt67=2;
                    }
                    switch (alt67) {
                        case 1 :
                            // InternalKdl.g:3401:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKdl.g:3401:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKdl.g:3402:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKdl.g:3402:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKdl.g:3403:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,100,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3416:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,101,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3423:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3423:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKdl.g:3424:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKdl.g:3424:4: (lv_num_9_0= ruleNumber )
                    // InternalKdl.g:3425:5: lv_num_9_0= ruleNumber
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
                    // InternalKdl.g:3443:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKdl.g:3443:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKdl.g:3444:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,74,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierRHSAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKdl.g:3448:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKdl.g:3449:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKdl.g:3449:5: (lv_set_11_0= ruleList )
                    // InternalKdl.g:3450:6: lv_set_11_0= ruleList
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
                    // InternalKdl.g:3469:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3469:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKdl.g:3470:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKdl.g:3470:4: (lv_string_12_0= RULE_STRING )
                    // InternalKdl.g:3471:5: lv_string_12_0= RULE_STRING
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
                    // InternalKdl.g:3488:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
                    {
                    // InternalKdl.g:3488:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
                    // InternalKdl.g:3489:4: (lv_concept_13_0= ruleConceptDeclaration )
                    {
                    // InternalKdl.g:3489:4: (lv_concept_13_0= ruleConceptDeclaration )
                    // InternalKdl.g:3490:5: lv_concept_13_0= ruleConceptDeclaration
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
                    // InternalKdl.g:3508:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
                    {
                    // InternalKdl.g:3508:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
                    // InternalKdl.g:3509:4: otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')'
                    {
                    otherlv_14=(Token)match(input,78,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getClassifierRHSAccess().getLeftParenthesisKeyword_6_0());
                      			
                    }
                    // InternalKdl.g:3513:4: ( (lv_toResolve_15_0= ruleConceptDeclaration ) )
                    // InternalKdl.g:3514:5: (lv_toResolve_15_0= ruleConceptDeclaration )
                    {
                    // InternalKdl.g:3514:5: (lv_toResolve_15_0= ruleConceptDeclaration )
                    // InternalKdl.g:3515:6: lv_toResolve_15_0= ruleConceptDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_67);
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

                    // InternalKdl.g:3532:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )*
                    loop68:
                    do {
                        int alt68=2;
                        int LA68_0 = input.LA(1);

                        if ( (LA68_0==28) ) {
                            alt68=1;
                        }


                        switch (alt68) {
                    	case 1 :
                    	    // InternalKdl.g:3533:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
                    	    {
                    	    // InternalKdl.g:3533:5: ( ( ',' )=>otherlv_16= ',' )
                    	    // InternalKdl.g:3534:6: ( ',' )=>otherlv_16= ','
                    	    {
                    	    otherlv_16=(Token)match(input,28,FOLLOW_38); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_16, grammarAccess.getClassifierRHSAccess().getCommaKeyword_6_2_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:3540:5: ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
                    	    // InternalKdl.g:3541:6: (lv_toResolve_17_0= ruleConceptDeclaration )
                    	    {
                    	    // InternalKdl.g:3541:6: (lv_toResolve_17_0= ruleConceptDeclaration )
                    	    // InternalKdl.g:3542:7: lv_toResolve_17_0= ruleConceptDeclaration
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_67);
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
                    	    break loop68;
                        }
                    } while (true);

                    otherlv_18=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getClassifierRHSAccess().getRightParenthesisKeyword_6_3());
                      			
                    }

                    }


                    }
                    break;
                case 8 :
                    // InternalKdl.g:3566:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3566:3: ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) )
                    // InternalKdl.g:3567:4: ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3567:4: ( (lv_op_19_0= ruleREL_OPERATOR ) )
                    // InternalKdl.g:3568:5: (lv_op_19_0= ruleREL_OPERATOR )
                    {
                    // InternalKdl.g:3568:5: (lv_op_19_0= ruleREL_OPERATOR )
                    // InternalKdl.g:3569:6: lv_op_19_0= ruleREL_OPERATOR
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

                    // InternalKdl.g:3586:4: ( (lv_expression_20_0= ruleNumber ) )
                    // InternalKdl.g:3587:5: (lv_expression_20_0= ruleNumber )
                    {
                    // InternalKdl.g:3587:5: (lv_expression_20_0= ruleNumber )
                    // InternalKdl.g:3588:6: lv_expression_20_0= ruleNumber
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
                    // InternalKdl.g:3607:3: ( (lv_nodata_21_0= 'unknown' ) )
                    {
                    // InternalKdl.g:3607:3: ( (lv_nodata_21_0= 'unknown' ) )
                    // InternalKdl.g:3608:4: (lv_nodata_21_0= 'unknown' )
                    {
                    // InternalKdl.g:3608:4: (lv_nodata_21_0= 'unknown' )
                    // InternalKdl.g:3609:5: lv_nodata_21_0= 'unknown'
                    {
                    lv_nodata_21_0=(Token)match(input,102,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3622:3: ( (lv_star_22_0= '*' ) )
                    {
                    // InternalKdl.g:3622:3: ( (lv_star_22_0= '*' ) )
                    // InternalKdl.g:3623:4: (lv_star_22_0= '*' )
                    {
                    // InternalKdl.g:3623:4: (lv_star_22_0= '*' )
                    // InternalKdl.g:3624:5: lv_star_22_0= '*'
                    {
                    lv_star_22_0=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3640:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKdl.g:3640:45: (iv_ruleList= ruleList EOF )
            // InternalKdl.g:3641:2: iv_ruleList= ruleList EOF
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
    // InternalKdl.g:3647:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:3653:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKdl.g:3654:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKdl.g:3654:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKdl.g:3655:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKdl.g:3655:3: ()
            // InternalKdl.g:3656:4: 
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

            otherlv_1=(Token)match(input,78,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKdl.g:3669:3: ( (lv_contents_2_0= ruleValue ) )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( ((LA70_0>=RULE_STRING && LA70_0<=RULE_UPPERCASE_ID)||(LA70_0>=RULE_CAMELCASE_ID && LA70_0<=RULE_ID)||LA70_0==28||LA70_0==34||LA70_0==78||(LA70_0>=98 && LA70_0<=99)||LA70_0==103||LA70_0==111||LA70_0==118) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // InternalKdl.g:3670:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKdl.g:3670:4: (lv_contents_2_0= ruleValue )
            	    // InternalKdl.g:3671:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_68);
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
            	    break loop70;
                }
            } while (true);

            otherlv_3=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3696:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKdl.g:3696:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKdl.g:3697:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKdl.g:3703:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) ;
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
            // InternalKdl.g:3709:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) ) )
            // InternalKdl.g:3710:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            {
            // InternalKdl.g:3710:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )
            int alt72=4;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // InternalKdl.g:3711:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3711:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:3712:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3712:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:3713:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:3731:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3731:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKdl.g:3732:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3732:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKdl.g:3733:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKdl.g:3733:5: (lv_from_1_0= ruleNumber )
                    // InternalKdl.g:3734:6: lv_from_1_0= ruleNumber
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

                    otherlv_2=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:3755:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKdl.g:3756:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3756:5: (lv_to_3_0= ruleNumber )
                    // InternalKdl.g:3757:6: lv_to_3_0= ruleNumber
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
                    // InternalKdl.g:3776:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3776:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3777:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3777:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3778:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3795:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3795:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3796:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3796:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3797:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3797:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==98) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==99) ) {
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
                            // InternalKdl.g:3798:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3809:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:3826:1: entryRuleLiteralOrIdOrComma returns [EObject current=null] : iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF ;
    public final EObject entryRuleLiteralOrIdOrComma() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrIdOrComma = null;


        try {
            // InternalKdl.g:3826:59: (iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF )
            // InternalKdl.g:3827:2: iv_ruleLiteralOrIdOrComma= ruleLiteralOrIdOrComma EOF
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
    // InternalKdl.g:3833:1: ruleLiteralOrIdOrComma returns [EObject current=null] : ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) ;
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
            // InternalKdl.g:3839:2: ( ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) ) )
            // InternalKdl.g:3840:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            {
            // InternalKdl.g:3840:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )
            int alt74=6;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // InternalKdl.g:3841:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    {
                    // InternalKdl.g:3841:3: ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) )
                    // InternalKdl.g:3842:4: ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3842:4: ( (lv_from_0_0= ruleNumber ) )
                    // InternalKdl.g:3843:5: (lv_from_0_0= ruleNumber )
                    {
                    // InternalKdl.g:3843:5: (lv_from_0_0= ruleNumber )
                    // InternalKdl.g:3844:6: lv_from_0_0= ruleNumber
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

                    // InternalKdl.g:3861:4: ( ( 'to' )=>otherlv_1= 'to' )
                    // InternalKdl.g:3862:5: ( 'to' )=>otherlv_1= 'to'
                    {
                    otherlv_1=(Token)match(input,52,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getLiteralOrIdOrCommaAccess().getToKeyword_0_1());
                      				
                    }

                    }

                    // InternalKdl.g:3868:4: ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) )
                    // InternalKdl.g:3869:5: ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber )
                    {
                    // InternalKdl.g:3873:5: (lv_to_2_0= ruleNumber )
                    // InternalKdl.g:3874:6: lv_to_2_0= ruleNumber
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
                    // InternalKdl.g:3893:3: ( (lv_number_3_0= ruleNumber ) )
                    {
                    // InternalKdl.g:3893:3: ( (lv_number_3_0= ruleNumber ) )
                    // InternalKdl.g:3894:4: (lv_number_3_0= ruleNumber )
                    {
                    // InternalKdl.g:3894:4: (lv_number_3_0= ruleNumber )
                    // InternalKdl.g:3895:5: lv_number_3_0= ruleNumber
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
                    // InternalKdl.g:3913:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:3913:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKdl.g:3914:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKdl.g:3914:4: (lv_string_4_0= RULE_STRING )
                    // InternalKdl.g:3915:5: lv_string_4_0= RULE_STRING
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
                    // InternalKdl.g:3932:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    {
                    // InternalKdl.g:3932:3: ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) )
                    // InternalKdl.g:3933:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    {
                    // InternalKdl.g:3933:4: ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) )
                    // InternalKdl.g:3934:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    {
                    // InternalKdl.g:3934:5: (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' )
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==98) ) {
                        alt73=1;
                    }
                    else if ( (LA73_0==99) ) {
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
                            // InternalKdl.g:3935:6: lv_boolean_5_1= 'true'
                            {
                            lv_boolean_5_1=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:3946:6: lv_boolean_5_2= 'false'
                            {
                            lv_boolean_5_2=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:3960:3: ( (lv_id_6_0= RULE_ID ) )
                    {
                    // InternalKdl.g:3960:3: ( (lv_id_6_0= RULE_ID ) )
                    // InternalKdl.g:3961:4: (lv_id_6_0= RULE_ID )
                    {
                    // InternalKdl.g:3961:4: (lv_id_6_0= RULE_ID )
                    // InternalKdl.g:3962:5: lv_id_6_0= RULE_ID
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
                    // InternalKdl.g:3979:3: ( (lv_comma_7_0= ',' ) )
                    {
                    // InternalKdl.g:3979:3: ( (lv_comma_7_0= ',' ) )
                    // InternalKdl.g:3980:4: (lv_comma_7_0= ',' )
                    {
                    // InternalKdl.g:3980:4: (lv_comma_7_0= ',' )
                    // InternalKdl.g:3981:5: lv_comma_7_0= ','
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
    // InternalKdl.g:3997:1: entryRuleLiteralOrID returns [EObject current=null] : iv_ruleLiteralOrID= ruleLiteralOrID EOF ;
    public final EObject entryRuleLiteralOrID() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteralOrID = null;


        try {
            // InternalKdl.g:3997:52: (iv_ruleLiteralOrID= ruleLiteralOrID EOF )
            // InternalKdl.g:3998:2: iv_ruleLiteralOrID= ruleLiteralOrID EOF
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
    // InternalKdl.g:4004:1: ruleLiteralOrID returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) ;
    public final EObject ruleLiteralOrID() throws RecognitionException {
        EObject current = null;

        Token lv_string_1_0=null;
        Token lv_boolean_2_1=null;
        Token lv_boolean_2_2=null;
        Token lv_id_3_0=null;
        EObject lv_number_0_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4010:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) ) )
            // InternalKdl.g:4011:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            {
            // InternalKdl.g:4011:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( (lv_string_1_0= RULE_STRING ) ) | ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) ) | ( (lv_id_3_0= RULE_ID ) ) )
            int alt76=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 34:
            case 118:
                {
                alt76=1;
                }
                break;
            case RULE_STRING:
                {
                alt76=2;
                }
                break;
            case 98:
            case 99:
                {
                alt76=3;
                }
                break;
            case RULE_ID:
                {
                alt76=4;
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
                    // InternalKdl.g:4012:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKdl.g:4012:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKdl.g:4013:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKdl.g:4013:4: (lv_number_0_0= ruleNumber )
                    // InternalKdl.g:4014:5: lv_number_0_0= ruleNumber
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
                    // InternalKdl.g:4032:3: ( (lv_string_1_0= RULE_STRING ) )
                    {
                    // InternalKdl.g:4032:3: ( (lv_string_1_0= RULE_STRING ) )
                    // InternalKdl.g:4033:4: (lv_string_1_0= RULE_STRING )
                    {
                    // InternalKdl.g:4033:4: (lv_string_1_0= RULE_STRING )
                    // InternalKdl.g:4034:5: lv_string_1_0= RULE_STRING
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
                    // InternalKdl.g:4051:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    {
                    // InternalKdl.g:4051:3: ( ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) ) )
                    // InternalKdl.g:4052:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    {
                    // InternalKdl.g:4052:4: ( (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' ) )
                    // InternalKdl.g:4053:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    {
                    // InternalKdl.g:4053:5: (lv_boolean_2_1= 'true' | lv_boolean_2_2= 'false' )
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==98) ) {
                        alt75=1;
                    }
                    else if ( (LA75_0==99) ) {
                        alt75=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 75, 0, input);

                        throw nvae;
                    }
                    switch (alt75) {
                        case 1 :
                            // InternalKdl.g:4054:6: lv_boolean_2_1= 'true'
                            {
                            lv_boolean_2_1=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKdl.g:4065:6: lv_boolean_2_2= 'false'
                            {
                            lv_boolean_2_2=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:4079:3: ( (lv_id_3_0= RULE_ID ) )
                    {
                    // InternalKdl.g:4079:3: ( (lv_id_3_0= RULE_ID ) )
                    // InternalKdl.g:4080:4: (lv_id_3_0= RULE_ID )
                    {
                    // InternalKdl.g:4080:4: (lv_id_3_0= RULE_ID )
                    // InternalKdl.g:4081:5: lv_id_3_0= RULE_ID
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
    // InternalKdl.g:4101:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKdl.g:4101:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKdl.g:4102:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKdl.g:4108:1: ruleMetadata returns [EObject current=null] : ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) ;
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
            // InternalKdl.g:4114:2: ( ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' ) )
            // InternalKdl.g:4115:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            {
            // InternalKdl.g:4115:2: ( () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}' )
            // InternalKdl.g:4116:3: () otherlv_1= '{' ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )* otherlv_4= '}'
            {
            // InternalKdl.g:4116:3: ()
            // InternalKdl.g:4117:4: 
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

            otherlv_1=(Token)match(input,37,FOLLOW_69); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMetadataAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKdl.g:4130:3: ( ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==RULE_LOWERCASE_ID) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalKdl.g:4131:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) ) ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    {
            	    // InternalKdl.g:4131:4: ( ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) ) )
            	    // InternalKdl.g:4132:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    {
            	    // InternalKdl.g:4132:5: ( (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId ) )
            	    // InternalKdl.g:4133:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    {
            	    // InternalKdl.g:4133:6: (lv_ids_2_1= RULE_LOWERCASE_ID | lv_ids_2_2= rulePropertyId )
            	    int alt77=2;
            	    int LA77_0 = input.LA(1);

            	    if ( (LA77_0==RULE_LOWERCASE_ID) ) {
            	        int LA77_1 = input.LA(2);

            	        if ( (LA77_1==104||LA77_1==107) ) {
            	            alt77=2;
            	        }
            	        else if ( (LA77_1==RULE_STRING||LA77_1==RULE_INT||LA77_1==RULE_ID||LA77_1==34||LA77_1==37||LA77_1==78||(LA77_1>=98 && LA77_1<=99)||LA77_1==118) ) {
            	            alt77=1;
            	        }
            	        else {
            	            if (state.backtracking>0) {state.failed=true; return current;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 77, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 77, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt77) {
            	        case 1 :
            	            // InternalKdl.g:4134:7: lv_ids_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_ids_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
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
            	            // InternalKdl.g:4149:7: lv_ids_2_2= rulePropertyId
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getIdsPropertyIdParserRuleCall_2_0_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_70);
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

            	    // InternalKdl.g:4167:4: ( ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) ) )
            	    // InternalKdl.g:4168:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    {
            	    // InternalKdl.g:4168:5: ( (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList ) )
            	    // InternalKdl.g:4169:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    {
            	    // InternalKdl.g:4169:6: (lv_values_3_1= ruleLiteralOrID | lv_values_3_2= ruleMetadata | lv_values_3_3= ruleList )
            	    int alt78=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_STRING:
            	    case RULE_INT:
            	    case RULE_ID:
            	    case 34:
            	    case 98:
            	    case 99:
            	    case 118:
            	        {
            	        alt78=1;
            	        }
            	        break;
            	    case 37:
            	        {
            	        alt78=2;
            	        }
            	        break;
            	    case 78:
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
            	            // InternalKdl.g:4170:7: lv_values_3_1= ruleLiteralOrID
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesLiteralOrIDParserRuleCall_2_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_69);
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
            	            // InternalKdl.g:4186:7: lv_values_3_2= ruleMetadata
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesMetadataParserRuleCall_2_1_0_1());
            	              						
            	            }
            	            pushFollow(FOLLOW_69);
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
            	            // InternalKdl.g:4202:7: lv_values_3_3= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getMetadataAccess().getValuesListParserRuleCall_2_1_0_2());
            	              						
            	            }
            	            pushFollow(FOLLOW_69);
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
            	    break loop79;
                }
            } while (true);

            otherlv_4=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:4229:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKdl.g:4229:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKdl.g:4230:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKdl.g:4236:1: ruleParameterList returns [EObject current=null] : ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) ;
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
            // InternalKdl.g:4242:2: ( ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) ) )
            // InternalKdl.g:4243:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            {
            // InternalKdl.g:4243:2: ( ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* ) | ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* ) )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_STRING||(LA82_0>=RULE_INT && LA82_0<=RULE_UPPERCASE_ID)||(LA82_0>=RULE_CAMELCASE_ID && LA82_0<=RULE_ID)||LA82_0==28||LA82_0==34||LA82_0==78||(LA82_0>=98 && LA82_0<=99)||LA82_0==103||LA82_0==111||LA82_0==118) ) {
                alt82=1;
            }
            else if ( (LA82_0==RULE_LOWERCASE_ID) ) {
                int LA82_2 = input.LA(2);

                if ( (LA82_2==EOF||(LA82_2>=RULE_LOWERCASE_ID && LA82_2<=RULE_INT)||LA82_2==RULE_CAMELCASE_ID||LA82_2==28||LA82_2==34||LA82_2==39||LA82_2==61||(LA82_2>=78 && LA82_2<=79)||(LA82_2>=104 && LA82_2<=107)||LA82_2==110||LA82_2==118||LA82_2==121) ) {
                    alt82=1;
                }
                else if ( ((LA82_2>=108 && LA82_2<=109)) ) {
                    alt82=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 82, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // InternalKdl.g:4244:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    {
                    // InternalKdl.g:4244:3: ( ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )* )
                    // InternalKdl.g:4245:4: ( (lv_values_0_0= ruleValue ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    {
                    // InternalKdl.g:4245:4: ( (lv_values_0_0= ruleValue ) )
                    // InternalKdl.g:4246:5: (lv_values_0_0= ruleValue )
                    {
                    // InternalKdl.g:4246:5: (lv_values_0_0= ruleValue )
                    // InternalKdl.g:4247:6: lv_values_0_0= ruleValue
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

                    // InternalKdl.g:4264:4: (otherlv_1= ',' ( (lv_values_2_0= ruleValue ) ) )*
                    loop80:
                    do {
                        int alt80=2;
                        int LA80_0 = input.LA(1);

                        if ( (LA80_0==28) ) {
                            alt80=1;
                        }


                        switch (alt80) {
                    	case 1 :
                    	    // InternalKdl.g:4265:5: otherlv_1= ',' ( (lv_values_2_0= ruleValue ) )
                    	    {
                    	    otherlv_1=(Token)match(input,28,FOLLOW_32); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:4269:5: ( (lv_values_2_0= ruleValue ) )
                    	    // InternalKdl.g:4270:6: (lv_values_2_0= ruleValue )
                    	    {
                    	    // InternalKdl.g:4270:6: (lv_values_2_0= ruleValue )
                    	    // InternalKdl.g:4271:7: lv_values_2_0= ruleValue
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
                    	    break loop80;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:4291:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    {
                    // InternalKdl.g:4291:3: ( ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )* )
                    // InternalKdl.g:4292:4: ( (lv_pairs_3_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    {
                    // InternalKdl.g:4292:4: ( (lv_pairs_3_0= ruleKeyValuePair ) )
                    // InternalKdl.g:4293:5: (lv_pairs_3_0= ruleKeyValuePair )
                    {
                    // InternalKdl.g:4293:5: (lv_pairs_3_0= ruleKeyValuePair )
                    // InternalKdl.g:4294:6: lv_pairs_3_0= ruleKeyValuePair
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

                    // InternalKdl.g:4311:4: ( ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) ) )*
                    loop81:
                    do {
                        int alt81=2;
                        int LA81_0 = input.LA(1);

                        if ( (LA81_0==28) ) {
                            alt81=1;
                        }


                        switch (alt81) {
                    	case 1 :
                    	    // InternalKdl.g:4312:5: ( ( ',' )=>otherlv_4= ',' ) ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    {
                    	    // InternalKdl.g:4312:5: ( ( ',' )=>otherlv_4= ',' )
                    	    // InternalKdl.g:4313:6: ( ',' )=>otherlv_4= ','
                    	    {
                    	    otherlv_4=(Token)match(input,28,FOLLOW_5); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_4, grammarAccess.getParameterListAccess().getCommaKeyword_1_1_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKdl.g:4319:5: ( (lv_pairs_5_0= ruleKeyValuePair ) )
                    	    // InternalKdl.g:4320:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    {
                    	    // InternalKdl.g:4320:6: (lv_pairs_5_0= ruleKeyValuePair )
                    	    // InternalKdl.g:4321:7: lv_pairs_5_0= ruleKeyValuePair
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
                    	    break loop81;
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
    // InternalKdl.g:4344:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKdl.g:4344:46: (iv_ruleValue= ruleValue EOF )
            // InternalKdl.g:4345:2: iv_ruleValue= ruleValue EOF
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
    // InternalKdl.g:4351:1: ruleValue returns [EObject current=null] : ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) ) ;
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
            // InternalKdl.g:4357:2: ( ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) ) )
            // InternalKdl.g:4358:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) )
            {
            // InternalKdl.g:4358:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) )
            int alt83=6;
            alt83 = dfa83.predict(input);
            switch (alt83) {
                case 1 :
                    // InternalKdl.g:4359:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    {
                    // InternalKdl.g:4359:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
                    // InternalKdl.g:4360:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    {
                    // InternalKdl.g:4360:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
                    // InternalKdl.g:4361:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
                    // InternalKdl.g:4379:3: ( (lv_function_1_0= ruleFunction ) )
                    {
                    // InternalKdl.g:4379:3: ( (lv_function_1_0= ruleFunction ) )
                    // InternalKdl.g:4380:4: (lv_function_1_0= ruleFunction )
                    {
                    // InternalKdl.g:4380:4: (lv_function_1_0= ruleFunction )
                    // InternalKdl.g:4381:5: lv_function_1_0= ruleFunction
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
                    // InternalKdl.g:4399:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
                    {
                    // InternalKdl.g:4399:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
                    // InternalKdl.g:4400:4: ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKdl.g:4400:4: ( (lv_urn_2_0= ruleUrn ) )
                    // InternalKdl.g:4401:5: (lv_urn_2_0= ruleUrn )
                    {
                    // InternalKdl.g:4401:5: (lv_urn_2_0= ruleUrn )
                    // InternalKdl.g:4402:6: lv_urn_2_0= ruleUrn
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_71);
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

                    // InternalKdl.g:4419:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKdl.g:4420:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKdl.g:4420:5: (lv_unit_3_0= ruleUnit )
                    // InternalKdl.g:4421:6: lv_unit_3_0= ruleUnit
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
                    // InternalKdl.g:4440:3: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKdl.g:4440:3: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKdl.g:4441:4: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKdl.g:4441:4: (lv_currency_4_0= ruleCurrency )
                    // InternalKdl.g:4442:5: lv_currency_4_0= ruleCurrency
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
                    // InternalKdl.g:4460:3: ( (lv_list_5_0= ruleList ) )
                    {
                    // InternalKdl.g:4460:3: ( (lv_list_5_0= ruleList ) )
                    // InternalKdl.g:4461:4: (lv_list_5_0= ruleList )
                    {
                    // InternalKdl.g:4461:4: (lv_list_5_0= ruleList )
                    // InternalKdl.g:4462:5: lv_list_5_0= ruleList
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
                    // InternalKdl.g:4480:3: ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKdl.g:4480:3: ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) )
                    // InternalKdl.g:4481:4: (lv_enumId_6_0= RULE_UPPERCASE_ID )
                    {
                    // InternalKdl.g:4481:4: (lv_enumId_6_0= RULE_UPPERCASE_ID )
                    // InternalKdl.g:4482:5: lv_enumId_6_0= RULE_UPPERCASE_ID
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
    // InternalKdl.g:4502:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKdl.g:4502:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKdl.g:4503:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKdl.g:4509:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_2=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_3 = null;



        	enterRule();

        try {
            // InternalKdl.g:4515:2: ( ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) ) )
            // InternalKdl.g:4516:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            {
            // InternalKdl.g:4516:2: ( ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) ) )
            // InternalKdl.g:4517:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            {
            // InternalKdl.g:4517:3: ( (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath ) )
            // InternalKdl.g:4518:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            {
            // InternalKdl.g:4518:4: (lv_name_0_1= ruleUrnId | lv_name_0_2= RULE_STRING | lv_name_0_3= ruleLocalFilePath )
            int alt84=3;
            switch ( input.LA(1) ) {
            case 103:
                {
                alt84=1;
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
                case 35:
                case 37:
                case 38:
                case 39:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 49:
                case 50:
                case 51:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 76:
                case 78:
                case 79:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 98:
                case 99:
                case 103:
                case 105:
                case 106:
                case 111:
                case 118:
                case 121:
                    {
                    alt84=3;
                    }
                    break;
                case 107:
                    {
                    int LA84_5 = input.LA(3);

                    if ( (LA84_5==RULE_LOWERCASE_ID) ) {
                        int LA84_6 = input.LA(4);

                        if ( (LA84_6==104||LA84_6==107) ) {
                            alt84=1;
                        }
                        else if ( (LA84_6==EOF||(LA84_6>=RULE_STRING && LA84_6<=RULE_UPPERCASE_ID)||(LA84_6>=RULE_CAMELCASE_ID && LA84_6<=RULE_ID)||(LA84_6>=17 && LA84_6<=32)||(LA84_6>=34 && LA84_6<=35)||(LA84_6>=37 && LA84_6<=39)||(LA84_6>=41 && LA84_6<=45)||(LA84_6>=49 && LA84_6<=51)||(LA84_6>=55 && LA84_6<=61)||LA84_6==76||(LA84_6>=78 && LA84_6<=79)||(LA84_6>=82 && LA84_6<=91)||(LA84_6>=98 && LA84_6<=99)||LA84_6==103||(LA84_6>=105 && LA84_6<=106)||LA84_6==111||LA84_6==118||LA84_6==121) ) {
                            alt84=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 84, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 84, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case 104:
                    {
                    alt84=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 84, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                alt84=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
                {
                alt84=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // InternalKdl.g:4519:5: lv_name_0_1= ruleUrnId
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
                    // InternalKdl.g:4535:5: lv_name_0_2= RULE_STRING
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
                    // InternalKdl.g:4550:5: lv_name_0_3= ruleLocalFilePath
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
    // InternalKdl.g:4571:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKdl.g:4571:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKdl.g:4572:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKdl.g:4578:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4584:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4585:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4585:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4586:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' this_Path_7= rulePath (kw= ':' this_VersionNumber_9= ruleVersionNumber )? (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4586:3: (kw= 'urn:klab:' )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==103) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalKdl.g:4587:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,103,FOLLOW_3); if (state.failed) return current;
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
            pushFollow(FOLLOW_72);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,104,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_72);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,104,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_72);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,104,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_73);
            this_Path_7=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_Path_7);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalKdl.g:4648:3: (kw= ':' this_VersionNumber_9= ruleVersionNumber )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==104) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalKdl.g:4649:4: kw= ':' this_VersionNumber_9= ruleVersionNumber
                    {
                    kw=(Token)match(input,104,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_74);
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

            // InternalKdl.g:4665:3: (kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==105) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalKdl.g:4666:4: kw= '#' this_LOWERCASE_ID_11= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,105,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:4683:1: entryRuleLocalFilePath returns [String current=null] : iv_ruleLocalFilePath= ruleLocalFilePath EOF ;
    public final String entryRuleLocalFilePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLocalFilePath = null;


        try {
            // InternalKdl.g:4683:53: (iv_ruleLocalFilePath= ruleLocalFilePath EOF )
            // InternalKdl.g:4684:2: iv_ruleLocalFilePath= ruleLocalFilePath EOF
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
    // InternalKdl.g:4690:1: ruleLocalFilePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) ;
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
            // InternalKdl.g:4696:2: ( ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? ) )
            // InternalKdl.g:4697:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            {
            // InternalKdl.g:4697:2: ( (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )? )
            // InternalKdl.g:4698:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID ) (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )* (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )? (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            {
            // InternalKdl.g:4698:3: (this_CAMELCASE_ID_0= RULE_CAMELCASE_ID | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID )
            int alt88=3;
            switch ( input.LA(1) ) {
            case RULE_CAMELCASE_ID:
                {
                alt88=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt88=2;
                }
                break;
            case RULE_LOWERCASE_DASHID:
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
                    // InternalKdl.g:4699:4: this_CAMELCASE_ID_0= RULE_CAMELCASE_ID
                    {
                    this_CAMELCASE_ID_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_75); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_CAMELCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CAMELCASE_ID_0, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4707:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_75); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKdl.g:4715:4: this_LOWERCASE_DASHID_2= RULE_LOWERCASE_DASHID
                    {
                    this_LOWERCASE_DASHID_2=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_75); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_DASHID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_DASHID_2, grammarAccess.getLocalFilePathAccess().getLOWERCASE_DASHIDTerminalRuleCall_0_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4723:3: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==106) ) {
                    switch ( input.LA(2) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        int LA90_3 = input.LA(3);

                        if ( (synpred174_InternalKdl()) ) {
                            alt90=1;
                        }


                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        int LA90_4 = input.LA(3);

                        if ( (synpred174_InternalKdl()) ) {
                            alt90=1;
                        }


                        }
                        break;
                    case RULE_LOWERCASE_DASHID:
                        {
                        alt90=1;
                        }
                        break;

                    }

                }


                switch (alt90) {
            	case 1 :
            	    // InternalKdl.g:4724:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
            	    {
            	    kw=(Token)match(input,106,FOLLOW_76); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getSolidusKeyword_1_0());
            	      			
            	    }
            	    // InternalKdl.g:4729:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
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
            	            // InternalKdl.g:4730:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
            	            {
            	            this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_75); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_CAMELCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CAMELCASE_ID_4, grammarAccess.getLocalFilePathAccess().getCAMELCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:4738:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_75); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_5, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKdl.g:4746:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
            	            {
            	            this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_75); if (state.failed) return current;
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
            	    break loop90;
                }
            } while (true);

            // InternalKdl.g:4755:3: (kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==107) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalKdl.g:4756:4: kw= '.' this_LOWERCASE_ID_8= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,107,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getLocalFilePathAccess().getFullStopKeyword_2_0());
                      			
                    }
                    this_LOWERCASE_ID_8=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_8, grammarAccess.getLocalFilePathAccess().getLOWERCASE_IDTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4769:3: (kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==105) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // InternalKdl.g:4770:4: kw= '#' this_LOWERCASE_ID_10= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,105,FOLLOW_5); if (state.failed) return current;
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
    // InternalKdl.g:4787:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKdl.g:4787:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKdl.g:4788:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKdl.g:4794:1: ruleKeyValuePair returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:4800:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKdl.g:4801:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKdl.g:4801:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) ) )
            // InternalKdl.g:4802:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKdl.g:4802:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKdl.g:4803:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKdl.g:4803:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKdl.g:4804:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_77); if (state.failed) return current;
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

            // InternalKdl.g:4820:3: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==108) ) {
                alt93=1;
            }
            else if ( (LA93_0==109) ) {
                alt93=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }
            switch (alt93) {
                case 1 :
                    // InternalKdl.g:4821:4: ( (lv_interactive_1_0= '=?' ) )
                    {
                    // InternalKdl.g:4821:4: ( (lv_interactive_1_0= '=?' ) )
                    // InternalKdl.g:4822:5: (lv_interactive_1_0= '=?' )
                    {
                    // InternalKdl.g:4822:5: (lv_interactive_1_0= '=?' )
                    // InternalKdl.g:4823:6: lv_interactive_1_0= '=?'
                    {
                    lv_interactive_1_0=(Token)match(input,108,FOLLOW_32); if (state.failed) return current;
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
                    // InternalKdl.g:4836:4: otherlv_2= '='
                    {
                    otherlv_2=(Token)match(input,109,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:4841:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKdl.g:4842:4: (lv_value_3_0= ruleValue )
            {
            // InternalKdl.g:4842:4: (lv_value_3_0= ruleValue )
            // InternalKdl.g:4843:5: lv_value_3_0= ruleValue
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
    // InternalKdl.g:4864:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalKdl.g:4864:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalKdl.g:4865:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalKdl.g:4871:1: ruleFunction returns [EObject current=null] : ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) ) ;
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
            // InternalKdl.g:4877:2: ( ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) ) )
            // InternalKdl.g:4878:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) )
            {
            // InternalKdl.g:4878:2: ( ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? ) | (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? ) )
            int alt101=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_LOWERCASE_ID:
            case RULE_INT:
            case RULE_LOWERCASE_DASHID:
            case RULE_CAMELCASE_ID:
            case 34:
            case 98:
            case 99:
            case 103:
            case 118:
                {
                alt101=1;
                }
                break;
            case 111:
                {
                alt101=2;
                }
                break;
            case 78:
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
                    // InternalKdl.g:4879:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:4879:3: ( ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:4880:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )? ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) ) (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    // InternalKdl.g:4880:4: ( ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>' )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==RULE_LOWERCASE_ID) ) {
                        int LA94_1 = input.LA(2);

                        if ( (LA94_1==110) ) {
                            alt94=1;
                        }
                    }
                    switch (alt94) {
                        case 1 :
                            // InternalKdl.g:4881:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '>>'
                            {
                            // InternalKdl.g:4881:5: ( (lv_mediated_0_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:4882:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:4882:6: (lv_mediated_0_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:4883:7: lv_mediated_0_0= RULE_LOWERCASE_ID
                            {
                            lv_mediated_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_78); if (state.failed) return current;
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

                            otherlv_1=(Token)match(input,110,FOLLOW_79); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_1, grammarAccess.getFunctionAccess().getGreaterThanSignGreaterThanSignKeyword_0_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKdl.g:4904:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )
                    int alt96=3;
                    alt96 = dfa96.predict(input);
                    switch (alt96) {
                        case 1 :
                            // InternalKdl.g:4905:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            {
                            // InternalKdl.g:4905:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
                            // InternalKdl.g:4906:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
                            {
                            // InternalKdl.g:4906:6: ( (lv_name_2_0= rulePathName ) )
                            // InternalKdl.g:4907:7: (lv_name_2_0= rulePathName )
                            {
                            // InternalKdl.g:4907:7: (lv_name_2_0= rulePathName )
                            // InternalKdl.g:4908:8: lv_name_2_0= rulePathName
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_66);
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

                            otherlv_3=(Token)match(input,78,FOLLOW_68); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_1_0_1());
                              					
                            }
                            // InternalKdl.g:4929:6: ( (lv_parameters_4_0= ruleParameterList ) )?
                            int alt95=2;
                            int LA95_0 = input.LA(1);

                            if ( ((LA95_0>=RULE_STRING && LA95_0<=RULE_UPPERCASE_ID)||(LA95_0>=RULE_CAMELCASE_ID && LA95_0<=RULE_ID)||LA95_0==28||LA95_0==34||LA95_0==78||(LA95_0>=98 && LA95_0<=99)||LA95_0==103||LA95_0==111||LA95_0==118) ) {
                                alt95=1;
                            }
                            switch (alt95) {
                                case 1 :
                                    // InternalKdl.g:4930:7: (lv_parameters_4_0= ruleParameterList )
                                    {
                                    // InternalKdl.g:4930:7: (lv_parameters_4_0= ruleParameterList )
                                    // InternalKdl.g:4931:8: lv_parameters_4_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_55);
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

                            otherlv_5=(Token)match(input,79,FOLLOW_80); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_1_0_3());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalKdl.g:4954:5: ( (lv_urn_6_0= ruleUrn ) )
                            {
                            // InternalKdl.g:4954:5: ( (lv_urn_6_0= ruleUrn ) )
                            // InternalKdl.g:4955:6: (lv_urn_6_0= ruleUrn )
                            {
                            // InternalKdl.g:4955:6: (lv_urn_6_0= ruleUrn )
                            // InternalKdl.g:4956:7: lv_urn_6_0= ruleUrn
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getUrnUrnParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_80);
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
                            // InternalKdl.g:4974:5: ( (lv_value_7_0= ruleLiteral ) )
                            {
                            // InternalKdl.g:4974:5: ( (lv_value_7_0= ruleLiteral ) )
                            // InternalKdl.g:4975:6: (lv_value_7_0= ruleLiteral )
                            {
                            // InternalKdl.g:4975:6: (lv_value_7_0= ruleLiteral )
                            // InternalKdl.g:4976:7: lv_value_7_0= ruleLiteral
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getFunctionAccess().getValueLiteralParserRuleCall_0_1_2_0());
                              						
                            }
                            pushFollow(FOLLOW_80);
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

                    // InternalKdl.g:4994:4: (otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) ) )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==39) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // InternalKdl.g:4995:5: otherlv_8= 'as' ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_8=(Token)match(input,39,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getAsKeyword_0_2_0());
                              				
                            }
                            // InternalKdl.g:4999:5: ( (lv_variable_9_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:5000:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:5000:6: (lv_variable_9_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:5001:7: lv_variable_9_0= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5020:3: (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:5020:3: (otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:5021:4: otherlv_10= 'classify' otherlv_11= '(' ( (lv_classification_12_0= ruleClassification ) ) otherlv_13= ')' (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    otherlv_10=(Token)match(input,111,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getFunctionAccess().getClassifyKeyword_1_0());
                      			
                    }
                    otherlv_11=(Token)match(input,78,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_11, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_1_1());
                      			
                    }
                    // InternalKdl.g:5029:4: ( (lv_classification_12_0= ruleClassification ) )
                    // InternalKdl.g:5030:5: (lv_classification_12_0= ruleClassification )
                    {
                    // InternalKdl.g:5030:5: (lv_classification_12_0= ruleClassification )
                    // InternalKdl.g:5031:6: lv_classification_12_0= ruleClassification
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getClassificationClassificationParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
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

                    otherlv_13=(Token)match(input,79,FOLLOW_80); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }
                    // InternalKdl.g:5052:4: (otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) ) )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==39) ) {
                        alt98=1;
                    }
                    switch (alt98) {
                        case 1 :
                            // InternalKdl.g:5053:5: otherlv_14= 'as' ( (lv_variable_15_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_14=(Token)match(input,39,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_14, grammarAccess.getFunctionAccess().getAsKeyword_1_4_0());
                              				
                            }
                            // InternalKdl.g:5057:5: ( (lv_variable_15_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:5058:6: (lv_variable_15_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:5058:6: (lv_variable_15_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:5059:7: lv_variable_15_0= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5078:3: (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? )
                    {
                    // InternalKdl.g:5078:3: (otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )? )
                    // InternalKdl.g:5079:4: otherlv_16= '(' ( (lv_chain_17_0= ruleFunction ) ) (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )* otherlv_20= ')' (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )?
                    {
                    otherlv_16=(Token)match(input,78,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:5083:4: ( (lv_chain_17_0= ruleFunction ) )
                    // InternalKdl.g:5084:5: (lv_chain_17_0= ruleFunction )
                    {
                    // InternalKdl.g:5084:5: (lv_chain_17_0= ruleFunction )
                    // InternalKdl.g:5085:6: lv_chain_17_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_67);
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

                    // InternalKdl.g:5102:4: (otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) ) )*
                    loop99:
                    do {
                        int alt99=2;
                        int LA99_0 = input.LA(1);

                        if ( (LA99_0==28) ) {
                            alt99=1;
                        }


                        switch (alt99) {
                    	case 1 :
                    	    // InternalKdl.g:5103:5: otherlv_18= ',' ( (lv_chain_19_0= ruleFunction ) )
                    	    {
                    	    otherlv_18=(Token)match(input,28,FOLLOW_9); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_18, grammarAccess.getFunctionAccess().getCommaKeyword_2_2_0());
                    	      				
                    	    }
                    	    // InternalKdl.g:5107:5: ( (lv_chain_19_0= ruleFunction ) )
                    	    // InternalKdl.g:5108:6: (lv_chain_19_0= ruleFunction )
                    	    {
                    	    // InternalKdl.g:5108:6: (lv_chain_19_0= ruleFunction )
                    	    // InternalKdl.g:5109:7: lv_chain_19_0= ruleFunction
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getFunctionAccess().getChainFunctionParserRuleCall_2_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_67);
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
                    	    break loop99;
                        }
                    } while (true);

                    otherlv_20=(Token)match(input,79,FOLLOW_80); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_2_3());
                      			
                    }
                    // InternalKdl.g:5131:4: (otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) ) )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==39) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // InternalKdl.g:5132:5: otherlv_21= 'as' ( (lv_variable_22_0= RULE_LOWERCASE_ID ) )
                            {
                            otherlv_21=(Token)match(input,39,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_21, grammarAccess.getFunctionAccess().getAsKeyword_2_4_0());
                              				
                            }
                            // InternalKdl.g:5136:5: ( (lv_variable_22_0= RULE_LOWERCASE_ID ) )
                            // InternalKdl.g:5137:6: (lv_variable_22_0= RULE_LOWERCASE_ID )
                            {
                            // InternalKdl.g:5137:6: (lv_variable_22_0= RULE_LOWERCASE_ID )
                            // InternalKdl.g:5138:7: lv_variable_22_0= RULE_LOWERCASE_ID
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
    // InternalKdl.g:5160:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKdl.g:5160:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKdl.g:5161:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKdl.g:5167:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) ;
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
            // InternalKdl.g:5173:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) ) )
            // InternalKdl.g:5174:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            {
            // InternalKdl.g:5174:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) ) | ( (lv_num_1_0= ruleNumber ) ) | (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' ) )
            int alt103=3;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
            case RULE_CAMELCASE_ID:
                {
                alt103=1;
                }
                break;
            case RULE_INT:
            case 34:
            case 118:
                {
                alt103=2;
                }
                break;
            case 78:
                {
                alt103=3;
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
                    // InternalKdl.g:5175:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    {
                    // InternalKdl.g:5175:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) ) )
                    // InternalKdl.g:5176:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKdl.g:5176:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID ) )
                    // InternalKdl.g:5177:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    {
                    // InternalKdl.g:5177:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID )
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( (LA102_0==RULE_CAMELCASE_ID) ) {
                        alt102=1;
                    }
                    else if ( (LA102_0==RULE_LOWERCASE_ID) ) {
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
                            // InternalKdl.g:5178:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:5193:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5211:3: ( (lv_num_1_0= ruleNumber ) )
                    {
                    // InternalKdl.g:5211:3: ( (lv_num_1_0= ruleNumber ) )
                    // InternalKdl.g:5212:4: (lv_num_1_0= ruleNumber )
                    {
                    // InternalKdl.g:5212:4: (lv_num_1_0= ruleNumber )
                    // InternalKdl.g:5213:5: lv_num_1_0= ruleNumber
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
                    // InternalKdl.g:5231:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    {
                    // InternalKdl.g:5231:3: (otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')' )
                    // InternalKdl.g:5232:4: otherlv_2= '(' ( (lv_unit_3_0= ruleUnit ) ) otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,78,FOLLOW_81); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalKdl.g:5236:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKdl.g:5237:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKdl.g:5237:5: (lv_unit_3_0= ruleUnit )
                    // InternalKdl.g:5238:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
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

                    otherlv_4=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:5264:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKdl.g:5264:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKdl.g:5265:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKdl.g:5271:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKdl.g:5277:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKdl.g:5278:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKdl.g:5278:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt104=6;
            switch ( input.LA(1) ) {
            case 112:
                {
                alt104=1;
                }
                break;
            case 113:
                {
                alt104=2;
                }
                break;
            case 109:
                {
                alt104=3;
                }
                break;
            case 114:
                {
                alt104=4;
                }
                break;
            case 115:
                {
                alt104=5;
                }
                break;
            case 116:
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
                    // InternalKdl.g:5279:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKdl.g:5279:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKdl.g:5280:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKdl.g:5280:4: (lv_gt_0_0= '>' )
                    // InternalKdl.g:5281:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,112,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5294:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKdl.g:5294:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKdl.g:5295:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKdl.g:5295:4: (lv_lt_1_0= '<' )
                    // InternalKdl.g:5296:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,113,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5309:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKdl.g:5309:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKdl.g:5310:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKdl.g:5310:4: (lv_eq_2_0= '=' )
                    // InternalKdl.g:5311:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,109,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5324:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKdl.g:5324:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKdl.g:5325:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKdl.g:5325:4: (lv_ne_3_0= '!=' )
                    // InternalKdl.g:5326:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,114,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5339:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKdl.g:5339:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKdl.g:5340:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKdl.g:5340:4: (lv_le_4_0= '<=' )
                    // InternalKdl.g:5341:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,115,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKdl.g:5354:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKdl.g:5354:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKdl.g:5355:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKdl.g:5355:4: (lv_ge_5_0= '>=' )
                    // InternalKdl.g:5356:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,116,FOLLOW_2); if (state.failed) return current;
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
    // InternalKdl.g:5372:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKdl.g:5372:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKdl.g:5373:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKdl.g:5379:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5385:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKdl.g:5386:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKdl.g:5386:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKdl.g:5387:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKdl.g:5387:3: ()
            // InternalKdl.g:5388:4: 
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

            // InternalKdl.g:5397:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt105=2;
            alt105 = dfa105.predict(input);
            switch (alt105) {
                case 1 :
                    // InternalKdl.g:5398:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKdl.g:5398:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKdl.g:5399:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_82);
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

            // InternalKdl.g:5416:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==61||LA106_0==106||LA106_0==121) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // InternalKdl.g:5417:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKdl.g:5417:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKdl.g:5418:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKdl.g:5424:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKdl.g:5425:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKdl.g:5425:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKdl.g:5426:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_83);
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

            	    // InternalKdl.g:5444:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKdl.g:5445:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKdl.g:5445:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKdl.g:5446:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_82);
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
            	    break loop106;
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
    // InternalKdl.g:5468:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKdl.g:5468:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKdl.g:5469:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKdl.g:5475:1: ruleCurrency returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token lv_concept_3_1=null;
        AntlrDatatypeRuleToken lv_concept_3_2 = null;



        	enterRule();

        try {
            // InternalKdl.g:5481:2: ( ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) ) )
            // InternalKdl.g:5482:2: ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) )
            {
            // InternalKdl.g:5482:2: ( ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ) | ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) ) )
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==RULE_ID) ) {
                alt108=1;
            }
            else if ( (LA108_0==RULE_LOWERCASE_ID||LA108_0==RULE_CAMELCASE_ID) ) {
                alt108=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }
            switch (alt108) {
                case 1 :
                    // InternalKdl.g:5483:3: ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) )
                    {
                    // InternalKdl.g:5483:3: ( ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) )
                    // InternalKdl.g:5484:4: ( (lv_id_0_0= RULE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5484:4: ( (lv_id_0_0= RULE_ID ) )
                    // InternalKdl.g:5485:5: (lv_id_0_0= RULE_ID )
                    {
                    // InternalKdl.g:5485:5: (lv_id_0_0= RULE_ID )
                    // InternalKdl.g:5486:6: lv_id_0_0= RULE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_ID,FOLLOW_84); if (state.failed) return current;
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

                    // InternalKdl.g:5502:4: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
                    // InternalKdl.g:5503:5: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
                    {
                    otherlv_1=(Token)match(input,117,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_0_1_0());
                      				
                    }
                    // InternalKdl.g:5507:5: ( (lv_year_2_0= RULE_INT ) )
                    // InternalKdl.g:5508:6: (lv_year_2_0= RULE_INT )
                    {
                    // InternalKdl.g:5508:6: (lv_year_2_0= RULE_INT )
                    // InternalKdl.g:5509:7: lv_year_2_0= RULE_INT
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
                    // InternalKdl.g:5528:3: ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) )
                    {
                    // InternalKdl.g:5528:3: ( ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) ) )
                    // InternalKdl.g:5529:4: ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) )
                    {
                    // InternalKdl.g:5529:4: ( (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId ) )
                    // InternalKdl.g:5530:5: (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId )
                    {
                    // InternalKdl.g:5530:5: (lv_concept_3_1= RULE_CAMELCASE_ID | lv_concept_3_2= ruleNamespaceId )
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( (LA107_0==RULE_CAMELCASE_ID) ) {
                        alt107=1;
                    }
                    else if ( (LA107_0==RULE_LOWERCASE_ID) ) {
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
                            // InternalKdl.g:5531:6: lv_concept_3_1= RULE_CAMELCASE_ID
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
                            // InternalKdl.g:5546:6: lv_concept_3_2= ruleNamespaceId
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
    // InternalKdl.g:5568:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKdl.g:5568:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKdl.g:5569:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKdl.g:5575:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKdl.g:5581:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? ) )
            // InternalKdl.g:5582:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            {
            // InternalKdl.g:5582:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )? )
            // InternalKdl.g:5583:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            {
            // InternalKdl.g:5583:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt109=3;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==34) ) {
                alt109=1;
            }
            else if ( (LA109_0==118) ) {
                alt109=2;
            }
            switch (alt109) {
                case 1 :
                    // InternalKdl.g:5584:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,34,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5589:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKdl.g:5589:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKdl.g:5590:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKdl.g:5590:5: (lv_negative_1_0= '-' )
                    // InternalKdl.g:5591:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,118,FOLLOW_7); if (state.failed) return current;
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

            // InternalKdl.g:5604:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKdl.g:5605:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKdl.g:5609:4: (lv_real_2_0= RULE_INT )
            // InternalKdl.g:5610:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_85); if (state.failed) return current;
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

            // InternalKdl.g:5626:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) ) )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==107) && (synpred204_InternalKdl())) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // InternalKdl.g:5627:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5640:4: ( ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) ) )
                    // InternalKdl.g:5641:5: ( (lv_decimal_3_0= '.' ) ) ( (lv_decimalPart_4_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5641:5: ( (lv_decimal_3_0= '.' ) )
                    // InternalKdl.g:5642:6: (lv_decimal_3_0= '.' )
                    {
                    // InternalKdl.g:5642:6: (lv_decimal_3_0= '.' )
                    // InternalKdl.g:5643:7: lv_decimal_3_0= '.'
                    {
                    lv_decimal_3_0=(Token)match(input,107,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:5655:5: ( (lv_decimalPart_4_0= RULE_INT ) )
                    // InternalKdl.g:5656:6: (lv_decimalPart_4_0= RULE_INT )
                    {
                    // InternalKdl.g:5656:6: (lv_decimalPart_4_0= RULE_INT )
                    // InternalKdl.g:5657:7: lv_decimalPart_4_0= RULE_INT
                    {
                    lv_decimalPart_4_0=(Token)match(input,RULE_INT,FOLLOW_86); if (state.failed) return current;
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

            // InternalKdl.g:5675:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) ) )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==119) && (synpred208_InternalKdl())) {
                alt113=1;
            }
            else if ( (LA113_0==120) && (synpred208_InternalKdl())) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // InternalKdl.g:5676:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    {
                    // InternalKdl.g:5702:4: ( ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) ) )
                    // InternalKdl.g:5703:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) ) (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )? ( (lv_exp_8_0= RULE_INT ) )
                    {
                    // InternalKdl.g:5703:5: ( ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) ) )
                    // InternalKdl.g:5704:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    {
                    // InternalKdl.g:5704:6: ( (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' ) )
                    // InternalKdl.g:5705:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    {
                    // InternalKdl.g:5705:7: (lv_exponential_5_1= 'e' | lv_exponential_5_2= 'E' )
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==119) ) {
                        alt111=1;
                    }
                    else if ( (LA111_0==120) ) {
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
                            // InternalKdl.g:5706:8: lv_exponential_5_1= 'e'
                            {
                            lv_exponential_5_1=(Token)match(input,119,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKdl.g:5717:8: lv_exponential_5_2= 'E'
                            {
                            lv_exponential_5_2=(Token)match(input,120,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKdl.g:5730:5: (otherlv_6= '+' | ( (lv_expNegative_7_0= '-' ) ) )?
                    int alt112=3;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==34) ) {
                        alt112=1;
                    }
                    else if ( (LA112_0==118) ) {
                        alt112=2;
                    }
                    switch (alt112) {
                        case 1 :
                            // InternalKdl.g:5731:6: otherlv_6= '+'
                            {
                            otherlv_6=(Token)match(input,34,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getNumberAccess().getPlusSignKeyword_3_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKdl.g:5736:6: ( (lv_expNegative_7_0= '-' ) )
                            {
                            // InternalKdl.g:5736:6: ( (lv_expNegative_7_0= '-' ) )
                            // InternalKdl.g:5737:7: (lv_expNegative_7_0= '-' )
                            {
                            // InternalKdl.g:5737:7: (lv_expNegative_7_0= '-' )
                            // InternalKdl.g:5738:8: lv_expNegative_7_0= '-'
                            {
                            lv_expNegative_7_0=(Token)match(input,118,FOLLOW_7); if (state.failed) return current;
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

                    // InternalKdl.g:5751:5: ( (lv_exp_8_0= RULE_INT ) )
                    // InternalKdl.g:5752:6: (lv_exp_8_0= RULE_INT )
                    {
                    // InternalKdl.g:5752:6: (lv_exp_8_0= RULE_INT )
                    // InternalKdl.g:5753:7: lv_exp_8_0= RULE_INT
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
    // InternalKdl.g:5775:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKdl.g:5775:48: (iv_rulePathName= rulePathName EOF )
            // InternalKdl.g:5776:2: iv_rulePathName= rulePathName EOF
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
    // InternalKdl.g:5782:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKdl.g:5788:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5789:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5789:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5790:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_87); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5797:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop114:
            do {
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==107) ) {
                    int LA114_2 = input.LA(2);

                    if ( (LA114_2==RULE_LOWERCASE_ID) ) {
                        alt114=1;
                    }


                }


                switch (alt114) {
            	case 1 :
            	    // InternalKdl.g:5798:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,107,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_87); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
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
    // $ANTLR end "rulePathName"


    // $ANTLR start "entryRulePath"
    // InternalKdl.g:5815:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKdl.g:5815:44: (iv_rulePath= rulePath EOF )
            // InternalKdl.g:5816:2: iv_rulePath= rulePath EOF
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
    // InternalKdl.g:5822:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKdl.g:5828:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* ) )
            // InternalKdl.g:5829:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            {
            // InternalKdl.g:5829:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )* )
            // InternalKdl.g:5830:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_88); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:5837:3: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )*
            loop116:
            do {
                int alt116=2;
                int LA116_0 = input.LA(1);

                if ( (LA116_0==106) ) {
                    int LA116_2 = input.LA(2);

                    if ( (LA116_2==RULE_LOWERCASE_ID) ) {
                        int LA116_4 = input.LA(3);

                        if ( (synpred214_InternalKdl()) ) {
                            alt116=1;
                        }


                    }


                }
                else if ( (LA116_0==107) ) {
                    alt116=1;
                }


                switch (alt116) {
            	case 1 :
            	    // InternalKdl.g:5838:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
            	    {
            	    // InternalKdl.g:5838:4: (kw= '.' | kw= '/' )
            	    int alt115=2;
            	    int LA115_0 = input.LA(1);

            	    if ( (LA115_0==107) ) {
            	        alt115=1;
            	    }
            	    else if ( (LA115_0==106) ) {
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
            	            // InternalKdl.g:5839:5: kw= '.'
            	            {
            	            kw=(Token)match(input,107,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKdl.g:5845:5: kw= '/'
            	            {
            	            kw=(Token)match(input,106,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_88); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_3);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_3, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop116;
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
    // InternalKdl.g:5863:1: entryRuleJavaClass returns [String current=null] : iv_ruleJavaClass= ruleJavaClass EOF ;
    public final String entryRuleJavaClass() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaClass = null;


        try {
            // InternalKdl.g:5863:49: (iv_ruleJavaClass= ruleJavaClass EOF )
            // InternalKdl.g:5864:2: iv_ruleJavaClass= ruleJavaClass EOF
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
    // InternalKdl.g:5870:1: ruleJavaClass returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaClass() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5876:2: ( (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5877:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5877:2: (this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5878:3: this_PathName_0= rulePathName kw= '.' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getJavaClassAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_89);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,107,FOLLOW_90); if (state.failed) return current;
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
    // InternalKdl.g:5904:1: entryRuleNamespaceId returns [String current=null] : iv_ruleNamespaceId= ruleNamespaceId EOF ;
    public final String entryRuleNamespaceId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNamespaceId = null;


        try {
            // InternalKdl.g:5904:51: (iv_ruleNamespaceId= ruleNamespaceId EOF )
            // InternalKdl.g:5905:2: iv_ruleNamespaceId= ruleNamespaceId EOF
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
    // InternalKdl.g:5911:1: ruleNamespaceId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleNamespaceId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_CAMELCASE_ID_2=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5917:2: ( (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID ) )
            // InternalKdl.g:5918:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            {
            // InternalKdl.g:5918:2: (this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID )
            // InternalKdl.g:5919:3: this_PathName_0= rulePathName kw= ':' this_CAMELCASE_ID_2= RULE_CAMELCASE_ID
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getNamespaceIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_72);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,104,FOLLOW_90); if (state.failed) return current;
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
    // InternalKdl.g:5945:1: entryRulePropertyId returns [String current=null] : iv_rulePropertyId= rulePropertyId EOF ;
    public final String entryRulePropertyId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePropertyId = null;


        try {
            // InternalKdl.g:5945:50: (iv_rulePropertyId= rulePropertyId EOF )
            // InternalKdl.g:5946:2: iv_rulePropertyId= rulePropertyId EOF
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
    // InternalKdl.g:5952:1: rulePropertyId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) ;
    public final AntlrDatatypeRuleToken rulePropertyId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;
        Token this_LOWERCASE_DASHID_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;



        	enterRule();

        try {
            // InternalKdl.g:5958:2: ( (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) ) )
            // InternalKdl.g:5959:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            {
            // InternalKdl.g:5959:2: (this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID ) )
            // InternalKdl.g:5960:3: this_PathName_0= rulePathName kw= ':' (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPropertyIdAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_72);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,104,FOLLOW_91); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getPropertyIdAccess().getColonKeyword_1());
              		
            }
            // InternalKdl.g:5975:3: (this_LOWERCASE_ID_2= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID )
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==RULE_LOWERCASE_ID) ) {
                alt117=1;
            }
            else if ( (LA117_0==RULE_LOWERCASE_DASHID) ) {
                alt117=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }
            switch (alt117) {
                case 1 :
                    // InternalKdl.g:5976:4: this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:5984:4: this_LOWERCASE_DASHID_3= RULE_LOWERCASE_DASHID
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
    // InternalKdl.g:5996:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKdl.g:5996:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKdl.g:5997:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKdl.g:6003:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKdl.g:6009:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKdl.g:6010:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKdl.g:6010:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKdl.g:6011:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_92); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKdl.g:6018:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==107) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // InternalKdl.g:6019:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,107,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_92); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKdl.g:6031:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( (LA118_0==107) ) {
                        alt118=1;
                    }
                    switch (alt118) {
                        case 1 :
                            // InternalKdl.g:6032:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,107,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_93); if (state.failed) return current;
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

            // InternalKdl.g:6046:3: (kw= '-' )?
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==118) ) {
                int LA120_1 = input.LA(2);

                if ( (synpred218_InternalKdl()) ) {
                    alt120=1;
                }
            }
            switch (alt120) {
                case 1 :
                    // InternalKdl.g:6047:4: kw= '-'
                    {
                    kw=(Token)match(input,118,FOLLOW_94); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKdl.g:6053:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt121=3;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==RULE_LOWERCASE_ID) ) {
                int LA121_1 = input.LA(2);

                if ( (synpred219_InternalKdl()) ) {
                    alt121=1;
                }
            }
            else if ( (LA121_0==RULE_UPPERCASE_ID) ) {
                int LA121_2 = input.LA(2);

                if ( (synpred220_InternalKdl()) ) {
                    alt121=2;
                }
            }
            switch (alt121) {
                case 1 :
                    // InternalKdl.g:6054:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKdl.g:6062:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKdl.g:6074:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKdl.g:6080:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKdl.g:6081:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKdl.g:6081:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt122=3;
            switch ( input.LA(1) ) {
            case 106:
                {
                alt122=1;
                }
                break;
            case 121:
                {
                alt122=2;
                }
                break;
            case 61:
                {
                alt122=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 122, 0, input);

                throw nvae;
            }

            switch (alt122) {
                case 1 :
                    // InternalKdl.g:6082:3: (enumLiteral_0= '/' )
                    {
                    // InternalKdl.g:6082:3: (enumLiteral_0= '/' )
                    // InternalKdl.g:6083:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,106,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKdl.g:6090:3: (enumLiteral_1= '^' )
                    {
                    // InternalKdl.g:6090:3: (enumLiteral_1= '^' )
                    // InternalKdl.g:6091:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,121,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKdl.g:6098:3: (enumLiteral_2= '*' )
                    {
                    // InternalKdl.g:6098:3: (enumLiteral_2= '*' )
                    // InternalKdl.g:6099:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
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
        int alt123=2;
        alt123 = dfa123.predict(input);
        switch (alt123) {
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
        int cnt124=0;
        loop124:
        do {
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==18) && ((true))) {
                alt124=1;
            }


            switch (alt124) {
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
        	    pushFollow(FOLLOW_95);
        	    lv_variables_4_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

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
        int cnt125=0;
        loop125:
        do {
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==19) && ((true))) {
                alt125=1;
            }


            switch (alt125) {
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
        	    pushFollow(FOLLOW_96);
        	    lv_constants_6_0=ruleParameter();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt125 >= 1 ) break loop125;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(125, input);
                    throw eee;
            }
            cnt125++;
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
        int cnt126=0;
        loop126:
        do {
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==20) && ((true))) {
                alt126=1;
            }


            switch (alt126) {
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
        	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_97); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt126 >= 1 ) break loop126;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(126, input);
                    throw eee;
            }
            cnt126++;
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
        loop127:
        do {
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==28) ) {
                alt127=1;
            }


            switch (alt127) {
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
        	    break loop127;
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

    // $ANTLR start synpred51_InternalKdl
    public final void synpred51_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_geometry_8_0 = null;


        // InternalKdl.g:1408:4: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) )
        // InternalKdl.g:1408:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
        {
        // InternalKdl.g:1408:4: ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) )
        // InternalKdl.g:1409:5: {...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred51_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0)");
        }
        // InternalKdl.g:1409:109: ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) )
        // InternalKdl.g:1410:6: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0);
        // InternalKdl.g:1413:9: ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) )
        // InternalKdl.g:1413:10: {...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred51_InternalKdl", "true");
        }
        // InternalKdl.g:1413:19: (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) )
        // InternalKdl.g:1413:20: otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) )
        {
        otherlv_7=(Token)match(input,55,FOLLOW_8); if (state.failed) return ;
        // InternalKdl.g:1417:9: ( (lv_geometry_8_0= ruleGeometry ) )
        // InternalKdl.g:1418:10: (lv_geometry_8_0= ruleGeometry )
        {
        // InternalKdl.g:1418:10: (lv_geometry_8_0= ruleGeometry )
        // InternalKdl.g:1419:11: lv_geometry_8_0= ruleGeometry
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
    // $ANTLR end synpred51_InternalKdl

    // $ANTLR start synpred52_InternalKdl
    public final void synpred52_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        EObject lv_units_10_0 = null;


        // InternalKdl.g:1442:4: ( ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:1442:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
        {
        // InternalKdl.g:1442:4: ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:1443:5: {...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred52_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1)");
        }
        // InternalKdl.g:1443:109: ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:1444:6: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1);
        // InternalKdl.g:1447:9: ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) )
        // InternalKdl.g:1447:10: {...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred52_InternalKdl", "true");
        }
        // InternalKdl.g:1447:19: (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) )
        // InternalKdl.g:1447:20: otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) )
        {
        otherlv_9=(Token)match(input,56,FOLLOW_71); if (state.failed) return ;
        // InternalKdl.g:1451:9: ( (lv_units_10_0= ruleUnit ) )
        // InternalKdl.g:1452:10: (lv_units_10_0= ruleUnit )
        {
        // InternalKdl.g:1452:10: (lv_units_10_0= ruleUnit )
        // InternalKdl.g:1453:11: lv_units_10_0= ruleUnit
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
    // $ANTLR end synpred52_InternalKdl

    // $ANTLR start synpred53_InternalKdl
    public final void synpred53_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_11_0 = null;


        // InternalKdl.g:1481:10: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )
        // InternalKdl.g:1481:10: {...}? => ( (lv_computations_11_0= ruleComputation ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred53_InternalKdl", "true");
        }
        // InternalKdl.g:1481:19: ( (lv_computations_11_0= ruleComputation ) )
        // InternalKdl.g:1481:20: (lv_computations_11_0= ruleComputation )
        {
        // InternalKdl.g:1481:20: (lv_computations_11_0= ruleComputation )
        // InternalKdl.g:1482:10: lv_computations_11_0= ruleComputation
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
    // $ANTLR end synpred53_InternalKdl

    // $ANTLR start synpred54_InternalKdl
    public final void synpred54_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_computations_11_0 = null;


        // InternalKdl.g:1476:4: ( ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) )
        // InternalKdl.g:1476:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
        {
        // InternalKdl.g:1476:4: ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) )
        // InternalKdl.g:1477:5: {...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred54_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2)");
        }
        // InternalKdl.g:1477:109: ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ )
        // InternalKdl.g:1478:6: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2);
        // InternalKdl.g:1481:9: ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+
        int cnt149=0;
        loop149:
        do {
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==60) && ((true))) {
                alt149=1;
            }


            switch (alt149) {
        	case 1 :
        	    // InternalKdl.g:1481:10: {...}? => ( (lv_computations_11_0= ruleComputation ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred54_InternalKdl", "true");
        	    }
        	    // InternalKdl.g:1481:19: ( (lv_computations_11_0= ruleComputation ) )
        	    // InternalKdl.g:1481:20: (lv_computations_11_0= ruleComputation )
        	    {
        	    // InternalKdl.g:1481:20: (lv_computations_11_0= ruleComputation )
        	    // InternalKdl.g:1482:10: lv_computations_11_0= ruleComputation
        	    {
        	    if ( state.backtracking==0 ) {

        	      										newCompositeNode(grammarAccess.getDataflowBodyAccess().getComputationsComputationParserRuleCall_3_2_0());
        	      									
        	    }
        	    pushFollow(FOLLOW_98);
        	    lv_computations_11_0=ruleComputation();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt149 >= 1 ) break loop149;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(149, input);
                    throw eee;
            }
            cnt149++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred54_InternalKdl

    // $ANTLR start synpred55_InternalKdl
    public final void synpred55_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_12=null;
        EObject lv_semantics_13_0 = null;


        // InternalKdl.g:1504:4: ( ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) )
        // InternalKdl.g:1504:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
        {
        // InternalKdl.g:1504:4: ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) )
        // InternalKdl.g:1505:5: {...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred55_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3)");
        }
        // InternalKdl.g:1505:109: ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) )
        // InternalKdl.g:1506:6: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3);
        // InternalKdl.g:1509:9: ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) )
        // InternalKdl.g:1509:10: {...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred55_InternalKdl", "true");
        }
        // InternalKdl.g:1509:19: (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) )
        // InternalKdl.g:1509:20: otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) )
        {
        otherlv_12=(Token)match(input,57,FOLLOW_38); if (state.failed) return ;
        // InternalKdl.g:1513:9: ( (lv_semantics_13_0= ruleObservableSemantics ) )
        // InternalKdl.g:1514:10: (lv_semantics_13_0= ruleObservableSemantics )
        {
        // InternalKdl.g:1514:10: (lv_semantics_13_0= ruleObservableSemantics )
        // InternalKdl.g:1515:11: lv_semantics_13_0= ruleObservableSemantics
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
    // $ANTLR end synpred55_InternalKdl

    // $ANTLR start synpred56_InternalKdl
    public final void synpred56_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        EObject lv_metadata_15_0 = null;


        // InternalKdl.g:1544:10: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )
        // InternalKdl.g:1544:10: otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) )
        {
        otherlv_14=(Token)match(input,58,FOLLOW_42); if (state.failed) return ;
        // InternalKdl.g:1548:10: ( (lv_metadata_15_0= ruleMetadata ) )
        // InternalKdl.g:1549:11: (lv_metadata_15_0= ruleMetadata )
        {
        // InternalKdl.g:1549:11: (lv_metadata_15_0= ruleMetadata )
        // InternalKdl.g:1550:12: lv_metadata_15_0= ruleMetadata
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
    // $ANTLR end synpred56_InternalKdl

    // $ANTLR start synpred57_InternalKdl
    public final void synpred57_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        AntlrDatatypeRuleToken lv_javaClass_17_0 = null;


        // InternalKdl.g:1569:10: (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )
        // InternalKdl.g:1569:10: otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) )
        {
        otherlv_16=(Token)match(input,59,FOLLOW_3); if (state.failed) return ;
        // InternalKdl.g:1573:10: ( (lv_javaClass_17_0= ruleJavaClass ) )
        // InternalKdl.g:1574:11: (lv_javaClass_17_0= ruleJavaClass )
        {
        // InternalKdl.g:1574:11: (lv_javaClass_17_0= ruleJavaClass )
        // InternalKdl.g:1575:12: lv_javaClass_17_0= ruleJavaClass
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
    // $ANTLR end synpred57_InternalKdl

    // $ANTLR start synpred58_InternalKdl
    public final void synpred58_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject lv_metadata_15_0 = null;

        AntlrDatatypeRuleToken lv_javaClass_17_0 = null;


        // InternalKdl.g:1538:4: ( ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )
        // InternalKdl.g:1538:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
        {
        // InternalKdl.g:1538:4: ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) )
        // InternalKdl.g:1539:5: {...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred58_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4)");
        }
        // InternalKdl.g:1539:109: ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) )
        // InternalKdl.g:1540:6: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4);
        // InternalKdl.g:1543:9: ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) )
        // InternalKdl.g:1543:10: {...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred58_InternalKdl", "true");
        }
        // InternalKdl.g:1543:19: ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? )
        // InternalKdl.g:1543:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
        {
        // InternalKdl.g:1543:20: (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )?
        int alt150=2;
        int LA150_0 = input.LA(1);

        if ( (LA150_0==58) ) {
            alt150=1;
        }
        switch (alt150) {
            case 1 :
                // InternalKdl.g:1544:10: otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) )
                {
                otherlv_14=(Token)match(input,58,FOLLOW_42); if (state.failed) return ;
                // InternalKdl.g:1548:10: ( (lv_metadata_15_0= ruleMetadata ) )
                // InternalKdl.g:1549:11: (lv_metadata_15_0= ruleMetadata )
                {
                // InternalKdl.g:1549:11: (lv_metadata_15_0= ruleMetadata )
                // InternalKdl.g:1550:12: lv_metadata_15_0= ruleMetadata
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getDataflowBodyAccess().getMetadataMetadataParserRuleCall_3_4_0_1_0());
                  											
                }
                pushFollow(FOLLOW_99);
                lv_metadata_15_0=ruleMetadata();

                state._fsp--;
                if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:1568:9: (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )?
        int alt151=2;
        int LA151_0 = input.LA(1);

        if ( (LA151_0==59) ) {
            alt151=1;
        }
        switch (alt151) {
            case 1 :
                // InternalKdl.g:1569:10: otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) )
                {
                otherlv_16=(Token)match(input,59,FOLLOW_3); if (state.failed) return ;
                // InternalKdl.g:1573:10: ( (lv_javaClass_17_0= ruleJavaClass ) )
                // InternalKdl.g:1574:11: (lv_javaClass_17_0= ruleJavaClass )
                {
                // InternalKdl.g:1574:11: (lv_javaClass_17_0= ruleJavaClass )
                // InternalKdl.g:1575:12: lv_javaClass_17_0= ruleJavaClass
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
    // $ANTLR end synpred58_InternalKdl

    // $ANTLR start synpred79_InternalKdl
    public final void synpred79_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2274:5: ( ( 'in' ( ( ruleSimpleConceptDeclaration ) ) ) )
        // InternalKdl.g:2274:6: ( 'in' ( ( ruleSimpleConceptDeclaration ) ) )
        {
        // InternalKdl.g:2274:6: ( 'in' ( ( ruleSimpleConceptDeclaration ) ) )
        // InternalKdl.g:2275:6: 'in' ( ( ruleSimpleConceptDeclaration ) )
        {
        match(input,74,FOLLOW_38); if (state.failed) return ;
        // InternalKdl.g:2276:6: ( ( ruleSimpleConceptDeclaration ) )
        // InternalKdl.g:2277:7: ( ruleSimpleConceptDeclaration )
        {
        // InternalKdl.g:2277:7: ( ruleSimpleConceptDeclaration )
        // InternalKdl.g:2278:8: ruleSimpleConceptDeclaration
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

    // $ANTLR start synpred83_InternalKdl
    public final void synpred83_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:2416:5: ( ( 'over' ( ( ruleSimpleConceptDeclaration ) ) ) )
        // InternalKdl.g:2416:6: ( 'over' ( ( ruleSimpleConceptDeclaration ) ) )
        {
        // InternalKdl.g:2416:6: ( 'over' ( ( ruleSimpleConceptDeclaration ) ) )
        // InternalKdl.g:2417:6: 'over' ( ( ruleSimpleConceptDeclaration ) )
        {
        match(input,40,FOLLOW_38); if (state.failed) return ;
        // InternalKdl.g:2418:6: ( ( ruleSimpleConceptDeclaration ) )
        // InternalKdl.g:2419:7: ( ruleSimpleConceptDeclaration )
        {
        // InternalKdl.g:2419:7: ( ruleSimpleConceptDeclaration )
        // InternalKdl.g:2420:8: ruleSimpleConceptDeclaration
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
    // $ANTLR end synpred83_InternalKdl

    // $ANTLR start synpred86_InternalKdl
    public final void synpred86_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_2=null;
        EObject lv_by_3_0 = null;


        // InternalKdl.g:2577:4: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) )
        // InternalKdl.g:2577:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
        {
        // InternalKdl.g:2577:4: ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) )
        // InternalKdl.g:2578:5: {...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred86_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0)");
        }
        // InternalKdl.g:2578:116: ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) )
        // InternalKdl.g:2579:6: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0);
        // InternalKdl.g:2582:9: ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) )
        // InternalKdl.g:2582:10: {...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred86_InternalKdl", "true");
        }
        // InternalKdl.g:2582:19: (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) )
        // InternalKdl.g:2582:20: otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) )
        {
        otherlv_2=(Token)match(input,66,FOLLOW_38); if (state.failed) return ;
        // InternalKdl.g:2586:9: ( (lv_by_3_0= ruleConcept ) )
        // InternalKdl.g:2587:10: (lv_by_3_0= ruleConcept )
        {
        // InternalKdl.g:2587:10: (lv_by_3_0= ruleConcept )
        // InternalKdl.g:2588:11: lv_by_3_0= ruleConcept
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
    // $ANTLR end synpred86_InternalKdl

    // $ANTLR start synpred88_InternalKdl
    public final void synpred88_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_downTo_6_1=null;
        AntlrDatatypeRuleToken lv_downTo_6_2 = null;


        // InternalKdl.g:2611:4: ( ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) )
        // InternalKdl.g:2611:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
        {
        // InternalKdl.g:2611:4: ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) )
        // InternalKdl.g:2612:5: {...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred88_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1)");
        }
        // InternalKdl.g:2612:116: ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) )
        // InternalKdl.g:2613:6: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1);
        // InternalKdl.g:2616:9: ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) )
        // InternalKdl.g:2616:10: {...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred88_InternalKdl", "true");
        }
        // InternalKdl.g:2616:19: (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) )
        // InternalKdl.g:2616:20: otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
        {
        otherlv_4=(Token)match(input,80,FOLLOW_36); if (state.failed) return ;
        otherlv_5=(Token)match(input,52,FOLLOW_57); if (state.failed) return ;
        // InternalKdl.g:2624:9: ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) )
        // InternalKdl.g:2625:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
        {
        // InternalKdl.g:2625:10: ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) )
        // InternalKdl.g:2626:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
        {
        // InternalKdl.g:2626:11: (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId )
        int alt164=2;
        int LA164_0 = input.LA(1);

        if ( (LA164_0==RULE_CAMELCASE_ID) ) {
            alt164=1;
        }
        else if ( (LA164_0==RULE_LOWERCASE_ID) ) {
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
                // InternalKdl.g:2627:12: lv_downTo_6_1= RULE_CAMELCASE_ID
                {
                lv_downTo_6_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:2642:12: lv_downTo_6_2= ruleNamespaceId
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
    // $ANTLR end synpred88_InternalKdl

    // $ANTLR start synpred89_InternalKdl
    public final void synpred89_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_role_8_0 = null;


        // InternalKdl.g:2666:4: ( ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) )
        // InternalKdl.g:2666:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
        {
        // InternalKdl.g:2666:4: ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) )
        // InternalKdl.g:2667:5: {...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred89_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2)");
        }
        // InternalKdl.g:2667:116: ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) )
        // InternalKdl.g:2668:6: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2);
        // InternalKdl.g:2671:9: ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) )
        // InternalKdl.g:2671:10: {...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred89_InternalKdl", "true");
        }
        // InternalKdl.g:2671:19: (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) )
        // InternalKdl.g:2671:20: otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) )
        {
        otherlv_7=(Token)match(input,39,FOLLOW_38); if (state.failed) return ;
        // InternalKdl.g:2675:9: ( (lv_role_8_0= ruleConcept ) )
        // InternalKdl.g:2676:10: (lv_role_8_0= ruleConcept )
        {
        // InternalKdl.g:2676:10: (lv_role_8_0= ruleConcept )
        // InternalKdl.g:2677:11: lv_role_8_0= ruleConcept
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
    // $ANTLR end synpred89_InternalKdl

    // $ANTLR start synpred90_InternalKdl
    public final void synpred90_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_unit_10_0 = null;


        // InternalKdl.g:2711:11: ( ( (lv_unit_10_0= ruleUnit ) ) )
        // InternalKdl.g:2711:11: ( (lv_unit_10_0= ruleUnit ) )
        {
        // InternalKdl.g:2711:11: ( (lv_unit_10_0= ruleUnit ) )
        // InternalKdl.g:2712:12: (lv_unit_10_0= ruleUnit )
        {
        // InternalKdl.g:2712:12: (lv_unit_10_0= ruleUnit )
        // InternalKdl.g:2713:13: lv_unit_10_0= ruleUnit
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
    // $ANTLR end synpred90_InternalKdl

    // $ANTLR start synpred92_InternalKdl
    public final void synpred92_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        Token otherlv_12=null;
        EObject lv_unit_10_0 = null;

        EObject lv_currency_11_0 = null;

        EObject lv_unit_13_0 = null;


        // InternalKdl.g:2700:4: ( ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) )
        // InternalKdl.g:2700:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
        {
        // InternalKdl.g:2700:4: ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) )
        // InternalKdl.g:2701:5: {...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred92_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3)");
        }
        // InternalKdl.g:2701:116: ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) )
        // InternalKdl.g:2702:6: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3);
        // InternalKdl.g:2705:9: ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) )
        // InternalKdl.g:2705:10: {...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred92_InternalKdl", "true");
        }
        // InternalKdl.g:2705:19: ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) )
        int alt167=2;
        int LA167_0 = input.LA(1);

        if ( (LA167_0==74) ) {
            alt167=1;
        }
        else if ( (LA167_0==81) ) {
            alt167=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 167, 0, input);

            throw nvae;
        }
        switch (alt167) {
            case 1 :
                // InternalKdl.g:2705:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
                {
                // InternalKdl.g:2705:20: (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) )
                // InternalKdl.g:2706:10: otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
                {
                otherlv_9=(Token)match(input,74,FOLLOW_100); if (state.failed) return ;
                // InternalKdl.g:2710:10: ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) )
                int alt166=2;
                switch ( input.LA(1) ) {
                case RULE_CAMELCASE_ID:
                    {
                    int LA166_1 = input.LA(2);

                    if ( (synpred90_InternalKdl()) ) {
                        alt166=1;
                    }
                    else if ( (true) ) {
                        alt166=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 166, 1, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_LOWERCASE_ID:
                    {
                    int LA166_2 = input.LA(2);

                    if ( (LA166_2==EOF||LA166_2==61||LA166_2==106||LA166_2==121) ) {
                        alt166=1;
                    }
                    else if ( (LA166_2==104||LA166_2==107) ) {
                        alt166=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 166, 2, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case RULE_INT:
                case 34:
                case 61:
                case 78:
                case 106:
                case 118:
                case 121:
                    {
                    alt166=1;
                    }
                    break;
                case RULE_ID:
                    {
                    alt166=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 166, 0, input);

                    throw nvae;
                }

                switch (alt166) {
                    case 1 :
                        // InternalKdl.g:2711:11: ( (lv_unit_10_0= ruleUnit ) )
                        {
                        // InternalKdl.g:2711:11: ( (lv_unit_10_0= ruleUnit ) )
                        // InternalKdl.g:2712:12: (lv_unit_10_0= ruleUnit )
                        {
                        // InternalKdl.g:2712:12: (lv_unit_10_0= ruleUnit )
                        // InternalKdl.g:2713:13: lv_unit_10_0= ruleUnit
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
                        // InternalKdl.g:2731:11: ( (lv_currency_11_0= ruleCurrency ) )
                        {
                        // InternalKdl.g:2731:11: ( (lv_currency_11_0= ruleCurrency ) )
                        // InternalKdl.g:2732:12: (lv_currency_11_0= ruleCurrency )
                        {
                        // InternalKdl.g:2732:12: (lv_currency_11_0= ruleCurrency )
                        // InternalKdl.g:2733:13: lv_currency_11_0= ruleCurrency
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
                // InternalKdl.g:2753:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
                {
                // InternalKdl.g:2753:9: (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) )
                // InternalKdl.g:2754:10: otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) )
                {
                otherlv_12=(Token)match(input,81,FOLLOW_71); if (state.failed) return ;
                // InternalKdl.g:2758:10: ( (lv_unit_13_0= ruleUnit ) )
                // InternalKdl.g:2759:11: (lv_unit_13_0= ruleUnit )
                {
                // InternalKdl.g:2759:11: (lv_unit_13_0= ruleUnit )
                // InternalKdl.g:2760:12: lv_unit_13_0= ruleUnit
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
    // $ANTLR end synpred92_InternalKdl

    // $ANTLR start synpred93_InternalKdl
    public final void synpred93_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_15=null;
        EObject lv_from_14_0 = null;

        EObject lv_to_16_0 = null;


        // InternalKdl.g:2784:4: ( ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )
        // InternalKdl.g:2784:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
        {
        // InternalKdl.g:2784:4: ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) )
        // InternalKdl.g:2785:5: {...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred93_InternalKdl", "getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4)");
        }
        // InternalKdl.g:2785:116: ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) )
        // InternalKdl.g:2786:6: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4);
        // InternalKdl.g:2789:9: ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) )
        // InternalKdl.g:2789:10: {...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred93_InternalKdl", "true");
        }
        // InternalKdl.g:2789:19: ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) )
        // InternalKdl.g:2789:20: ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) )
        {
        // InternalKdl.g:2789:20: ( (lv_from_14_0= ruleNumber ) )
        // InternalKdl.g:2790:10: (lv_from_14_0= ruleNumber )
        {
        // InternalKdl.g:2790:10: (lv_from_14_0= ruleNumber )
        // InternalKdl.g:2791:11: lv_from_14_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getObservableSemanticsAccess().getFromNumberParserRuleCall_1_4_0_0());
          										
        }
        pushFollow(FOLLOW_36);
        lv_from_14_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_15=(Token)match(input,52,FOLLOW_35); if (state.failed) return ;
        // InternalKdl.g:2812:9: ( (lv_to_16_0= ruleNumber ) )
        // InternalKdl.g:2813:10: (lv_to_16_0= ruleNumber )
        {
        // InternalKdl.g:2813:10: (lv_to_16_0= ruleNumber )
        // InternalKdl.g:2814:11: lv_to_16_0= ruleNumber
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
    // $ANTLR end synpred93_InternalKdl

    // $ANTLR start synpred95_InternalKdl
    public final void synpred95_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_main_1_0 = null;


        // InternalKdl.g:2885:4: ( (lv_main_1_0= ruleConcept ) )
        // InternalKdl.g:2885:4: (lv_main_1_0= ruleConcept )
        {
        // InternalKdl.g:2885:4: (lv_main_1_0= ruleConcept )
        // InternalKdl.g:2886:5: lv_main_1_0= ruleConcept
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
    // $ANTLR end synpred95_InternalKdl

    // $ANTLR start synpred121_InternalKdl
    public final void synpred121_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:3371:5: ( 'to' )
        // InternalKdl.g:3371:6: 'to'
        {
        match(input,52,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred121_InternalKdl

    // $ANTLR start synpred125_InternalKdl
    public final void synpred125_InternalKdl_fragment() throws RecognitionException {   
        Token lv_leftLimit_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_rightLimit_7_0=null;
        Token otherlv_8=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;


        // InternalKdl.g:3329:3: ( ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) )
        // InternalKdl.g:3329:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
        {
        // InternalKdl.g:3329:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
        // InternalKdl.g:3330:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
        {
        // InternalKdl.g:3330:4: ( (lv_int0_2_0= ruleNumber ) )
        // InternalKdl.g:3331:5: (lv_int0_2_0= ruleNumber )
        {
        // InternalKdl.g:3331:5: (lv_int0_2_0= ruleNumber )
        // InternalKdl.g:3332:6: lv_int0_2_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt0NumberParserRuleCall_1_0_0());
          					
        }
        pushFollow(FOLLOW_64);
        lv_int0_2_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:3349:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
        int alt170=3;
        int LA170_0 = input.LA(1);

        if ( (LA170_0==100) ) {
            alt170=1;
        }
        else if ( (LA170_0==101) ) {
            alt170=2;
        }
        switch (alt170) {
            case 1 :
                // InternalKdl.g:3350:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                {
                // InternalKdl.g:3350:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                // InternalKdl.g:3351:6: (lv_leftLimit_3_0= 'inclusive' )
                {
                // InternalKdl.g:3351:6: (lv_leftLimit_3_0= 'inclusive' )
                // InternalKdl.g:3352:7: lv_leftLimit_3_0= 'inclusive'
                {
                lv_leftLimit_3_0=(Token)match(input,100,FOLLOW_36); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKdl.g:3365:5: otherlv_4= 'exclusive'
                {
                otherlv_4=(Token)match(input,101,FOLLOW_36); if (state.failed) return ;

                }
                break;

        }

        // InternalKdl.g:3370:4: ( ( 'to' )=>otherlv_5= 'to' )
        // InternalKdl.g:3371:5: ( 'to' )=>otherlv_5= 'to'
        {
        otherlv_5=(Token)match(input,52,FOLLOW_35); if (state.failed) return ;

        }

        // InternalKdl.g:3377:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
        // InternalKdl.g:3378:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
        {
        // InternalKdl.g:3382:5: (lv_int1_6_0= ruleNumber )
        // InternalKdl.g:3383:6: lv_int1_6_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getClassifierRHSAccess().getInt1NumberParserRuleCall_1_3_0());
          					
        }
        pushFollow(FOLLOW_65);
        lv_int1_6_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:3400:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
        int alt171=3;
        int LA171_0 = input.LA(1);

        if ( (LA171_0==100) ) {
            alt171=1;
        }
        else if ( (LA171_0==101) ) {
            alt171=2;
        }
        switch (alt171) {
            case 1 :
                // InternalKdl.g:3401:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                {
                // InternalKdl.g:3401:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                // InternalKdl.g:3402:6: (lv_rightLimit_7_0= 'inclusive' )
                {
                // InternalKdl.g:3402:6: (lv_rightLimit_7_0= 'inclusive' )
                // InternalKdl.g:3403:7: lv_rightLimit_7_0= 'inclusive'
                {
                lv_rightLimit_7_0=(Token)match(input,100,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKdl.g:3416:5: otherlv_8= 'exclusive'
                {
                otherlv_8=(Token)match(input,101,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred125_InternalKdl

    // $ANTLR start synpred126_InternalKdl
    public final void synpred126_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_num_9_0 = null;


        // InternalKdl.g:3423:3: ( ( (lv_num_9_0= ruleNumber ) ) )
        // InternalKdl.g:3423:3: ( (lv_num_9_0= ruleNumber ) )
        {
        // InternalKdl.g:3423:3: ( (lv_num_9_0= ruleNumber ) )
        // InternalKdl.g:3424:4: (lv_num_9_0= ruleNumber )
        {
        // InternalKdl.g:3424:4: (lv_num_9_0= ruleNumber )
        // InternalKdl.g:3425:5: lv_num_9_0= ruleNumber
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
    // $ANTLR end synpred126_InternalKdl

    // $ANTLR start synpred128_InternalKdl
    public final void synpred128_InternalKdl_fragment() throws RecognitionException {   
        Token lv_string_12_0=null;

        // InternalKdl.g:3469:3: ( ( (lv_string_12_0= RULE_STRING ) ) )
        // InternalKdl.g:3469:3: ( (lv_string_12_0= RULE_STRING ) )
        {
        // InternalKdl.g:3469:3: ( (lv_string_12_0= RULE_STRING ) )
        // InternalKdl.g:3470:4: (lv_string_12_0= RULE_STRING )
        {
        // InternalKdl.g:3470:4: (lv_string_12_0= RULE_STRING )
        // InternalKdl.g:3471:5: lv_string_12_0= RULE_STRING
        {
        lv_string_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred128_InternalKdl

    // $ANTLR start synpred129_InternalKdl
    public final void synpred129_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_concept_13_0 = null;


        // InternalKdl.g:3488:3: ( ( (lv_concept_13_0= ruleConceptDeclaration ) ) )
        // InternalKdl.g:3488:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
        {
        // InternalKdl.g:3488:3: ( (lv_concept_13_0= ruleConceptDeclaration ) )
        // InternalKdl.g:3489:4: (lv_concept_13_0= ruleConceptDeclaration )
        {
        // InternalKdl.g:3489:4: (lv_concept_13_0= ruleConceptDeclaration )
        // InternalKdl.g:3490:5: lv_concept_13_0= ruleConceptDeclaration
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
    // $ANTLR end synpred129_InternalKdl

    // $ANTLR start synpred132_InternalKdl
    public final void synpred132_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        EObject lv_toResolve_15_0 = null;

        EObject lv_toResolve_17_0 = null;


        // InternalKdl.g:3508:3: ( (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) )
        // InternalKdl.g:3508:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
        {
        // InternalKdl.g:3508:3: (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' )
        // InternalKdl.g:3509:4: otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')'
        {
        otherlv_14=(Token)match(input,78,FOLLOW_38); if (state.failed) return ;
        // InternalKdl.g:3513:4: ( (lv_toResolve_15_0= ruleConceptDeclaration ) )
        // InternalKdl.g:3514:5: (lv_toResolve_15_0= ruleConceptDeclaration )
        {
        // InternalKdl.g:3514:5: (lv_toResolve_15_0= ruleConceptDeclaration )
        // InternalKdl.g:3515:6: lv_toResolve_15_0= ruleConceptDeclaration
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_1_0());
          					
        }
        pushFollow(FOLLOW_67);
        lv_toResolve_15_0=ruleConceptDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:3532:4: ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )*
        loop172:
        do {
            int alt172=2;
            int LA172_0 = input.LA(1);

            if ( (LA172_0==28) ) {
                alt172=1;
            }


            switch (alt172) {
        	case 1 :
        	    // InternalKdl.g:3533:5: ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
        	    {
        	    // InternalKdl.g:3533:5: ( ( ',' )=>otherlv_16= ',' )
        	    // InternalKdl.g:3534:6: ( ',' )=>otherlv_16= ','
        	    {
        	    otherlv_16=(Token)match(input,28,FOLLOW_38); if (state.failed) return ;

        	    }

        	    // InternalKdl.g:3540:5: ( (lv_toResolve_17_0= ruleConceptDeclaration ) )
        	    // InternalKdl.g:3541:6: (lv_toResolve_17_0= ruleConceptDeclaration )
        	    {
        	    // InternalKdl.g:3541:6: (lv_toResolve_17_0= ruleConceptDeclaration )
        	    // InternalKdl.g:3542:7: lv_toResolve_17_0= ruleConceptDeclaration
        	    {
        	    if ( state.backtracking==0 ) {

        	      							newCompositeNode(grammarAccess.getClassifierRHSAccess().getToResolveConceptDeclarationParserRuleCall_6_2_1_0());
        	      						
        	    }
        	    pushFollow(FOLLOW_67);
        	    lv_toResolve_17_0=ruleConceptDeclaration();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop172;
            }
        } while (true);

        otherlv_18=(Token)match(input,79,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred132_InternalKdl

    // $ANTLR start synpred160_InternalKdl
    public final void synpred160_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_literal_0_0 = null;


        // InternalKdl.g:4359:3: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) )
        // InternalKdl.g:4359:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        {
        // InternalKdl.g:4359:3: ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) )
        // InternalKdl.g:4360:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        {
        // InternalKdl.g:4360:4: (lv_literal_0_0= ruleLiteralOrIdOrComma )
        // InternalKdl.g:4361:5: lv_literal_0_0= ruleLiteralOrIdOrComma
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
    // $ANTLR end synpred160_InternalKdl

    // $ANTLR start synpred161_InternalKdl
    public final void synpred161_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_function_1_0 = null;


        // InternalKdl.g:4379:3: ( ( (lv_function_1_0= ruleFunction ) ) )
        // InternalKdl.g:4379:3: ( (lv_function_1_0= ruleFunction ) )
        {
        // InternalKdl.g:4379:3: ( (lv_function_1_0= ruleFunction ) )
        // InternalKdl.g:4380:4: (lv_function_1_0= ruleFunction )
        {
        // InternalKdl.g:4380:4: (lv_function_1_0= ruleFunction )
        // InternalKdl.g:4381:5: lv_function_1_0= ruleFunction
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
    // $ANTLR end synpred161_InternalKdl

    // $ANTLR start synpred162_InternalKdl
    public final void synpred162_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_2_0 = null;

        EObject lv_unit_3_0 = null;


        // InternalKdl.g:4399:3: ( ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) )
        // InternalKdl.g:4399:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
        {
        // InternalKdl.g:4399:3: ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) )
        // InternalKdl.g:4400:4: ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) )
        {
        // InternalKdl.g:4400:4: ( (lv_urn_2_0= ruleUrn ) )
        // InternalKdl.g:4401:5: (lv_urn_2_0= ruleUrn )
        {
        // InternalKdl.g:4401:5: (lv_urn_2_0= ruleUrn )
        // InternalKdl.g:4402:6: lv_urn_2_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_2_0_0());
          					
        }
        pushFollow(FOLLOW_71);
        lv_urn_2_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKdl.g:4419:4: ( (lv_unit_3_0= ruleUnit ) )
        // InternalKdl.g:4420:5: (lv_unit_3_0= ruleUnit )
        {
        // InternalKdl.g:4420:5: (lv_unit_3_0= ruleUnit )
        // InternalKdl.g:4421:6: lv_unit_3_0= ruleUnit
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
    // $ANTLR end synpred162_InternalKdl

    // $ANTLR start synpred163_InternalKdl
    public final void synpred163_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_currency_4_0 = null;


        // InternalKdl.g:4440:3: ( ( (lv_currency_4_0= ruleCurrency ) ) )
        // InternalKdl.g:4440:3: ( (lv_currency_4_0= ruleCurrency ) )
        {
        // InternalKdl.g:4440:3: ( (lv_currency_4_0= ruleCurrency ) )
        // InternalKdl.g:4441:4: (lv_currency_4_0= ruleCurrency )
        {
        // InternalKdl.g:4441:4: (lv_currency_4_0= ruleCurrency )
        // InternalKdl.g:4442:5: lv_currency_4_0= ruleCurrency
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
    // $ANTLR end synpred163_InternalKdl

    // $ANTLR start synpred164_InternalKdl
    public final void synpred164_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_list_5_0 = null;


        // InternalKdl.g:4460:3: ( ( (lv_list_5_0= ruleList ) ) )
        // InternalKdl.g:4460:3: ( (lv_list_5_0= ruleList ) )
        {
        // InternalKdl.g:4460:3: ( (lv_list_5_0= ruleList ) )
        // InternalKdl.g:4461:4: (lv_list_5_0= ruleList )
        {
        // InternalKdl.g:4461:4: (lv_list_5_0= ruleList )
        // InternalKdl.g:4462:5: lv_list_5_0= ruleList
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
    // $ANTLR end synpred164_InternalKdl

    // $ANTLR start synpred174_InternalKdl
    public final void synpred174_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;
        Token this_CAMELCASE_ID_4=null;
        Token this_LOWERCASE_ID_5=null;
        Token this_LOWERCASE_DASHID_6=null;

        // InternalKdl.g:4724:4: (kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID ) )
        // InternalKdl.g:4724:4: kw= '/' (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
        {
        kw=(Token)match(input,106,FOLLOW_76); if (state.failed) return ;
        // InternalKdl.g:4729:4: (this_CAMELCASE_ID_4= RULE_CAMELCASE_ID | this_LOWERCASE_ID_5= RULE_LOWERCASE_ID | this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID )
        int alt178=3;
        switch ( input.LA(1) ) {
        case RULE_CAMELCASE_ID:
            {
            alt178=1;
            }
            break;
        case RULE_LOWERCASE_ID:
            {
            alt178=2;
            }
            break;
        case RULE_LOWERCASE_DASHID:
            {
            alt178=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 178, 0, input);

            throw nvae;
        }

        switch (alt178) {
            case 1 :
                // InternalKdl.g:4730:5: this_CAMELCASE_ID_4= RULE_CAMELCASE_ID
                {
                this_CAMELCASE_ID_4=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:4738:5: this_LOWERCASE_ID_5= RULE_LOWERCASE_ID
                {
                this_LOWERCASE_ID_5=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKdl.g:4746:5: this_LOWERCASE_DASHID_6= RULE_LOWERCASE_DASHID
                {
                this_LOWERCASE_DASHID_6=(Token)match(input,RULE_LOWERCASE_DASHID,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred174_InternalKdl

    // $ANTLR start synpred180_InternalKdl
    public final void synpred180_InternalKdl_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_parameters_4_0 = null;


        // InternalKdl.g:4905:5: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) )
        // InternalKdl.g:4905:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        {
        // InternalKdl.g:4905:5: ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' )
        // InternalKdl.g:4906:6: ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')'
        {
        // InternalKdl.g:4906:6: ( (lv_name_2_0= rulePathName ) )
        // InternalKdl.g:4907:7: (lv_name_2_0= rulePathName )
        {
        // InternalKdl.g:4907:7: (lv_name_2_0= rulePathName )
        // InternalKdl.g:4908:8: lv_name_2_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          								newCompositeNode(grammarAccess.getFunctionAccess().getNamePathNameParserRuleCall_0_1_0_0_0());
          							
        }
        pushFollow(FOLLOW_66);
        lv_name_2_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_3=(Token)match(input,78,FOLLOW_68); if (state.failed) return ;
        // InternalKdl.g:4929:6: ( (lv_parameters_4_0= ruleParameterList ) )?
        int alt179=2;
        int LA179_0 = input.LA(1);

        if ( ((LA179_0>=RULE_STRING && LA179_0<=RULE_UPPERCASE_ID)||(LA179_0>=RULE_CAMELCASE_ID && LA179_0<=RULE_ID)||LA179_0==28||LA179_0==34||LA179_0==78||(LA179_0>=98 && LA179_0<=99)||LA179_0==103||LA179_0==111||LA179_0==118) ) {
            alt179=1;
        }
        switch (alt179) {
            case 1 :
                // InternalKdl.g:4930:7: (lv_parameters_4_0= ruleParameterList )
                {
                // InternalKdl.g:4930:7: (lv_parameters_4_0= ruleParameterList )
                // InternalKdl.g:4931:8: lv_parameters_4_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterListParserRuleCall_0_1_0_2_0());
                  							
                }
                pushFollow(FOLLOW_55);
                lv_parameters_4_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_5=(Token)match(input,79,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred180_InternalKdl

    // $ANTLR start synpred181_InternalKdl
    public final void synpred181_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_urn_6_0 = null;


        // InternalKdl.g:4954:5: ( ( (lv_urn_6_0= ruleUrn ) ) )
        // InternalKdl.g:4954:5: ( (lv_urn_6_0= ruleUrn ) )
        {
        // InternalKdl.g:4954:5: ( (lv_urn_6_0= ruleUrn ) )
        // InternalKdl.g:4955:6: (lv_urn_6_0= ruleUrn )
        {
        // InternalKdl.g:4955:6: (lv_urn_6_0= ruleUrn )
        // InternalKdl.g:4956:7: lv_urn_6_0= ruleUrn
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
    // $ANTLR end synpred181_InternalKdl

    // $ANTLR start synpred196_InternalKdl
    public final void synpred196_InternalKdl_fragment() throws RecognitionException {   
        EObject lv_root_1_0 = null;


        // InternalKdl.g:5398:4: ( (lv_root_1_0= ruleUnitElement ) )
        // InternalKdl.g:5398:4: (lv_root_1_0= ruleUnitElement )
        {
        // InternalKdl.g:5398:4: (lv_root_1_0= ruleUnitElement )
        // InternalKdl.g:5399:5: lv_root_1_0= ruleUnitElement
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
    // $ANTLR end synpred196_InternalKdl

    // $ANTLR start synpred203_InternalKdl
    public final void synpred203_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5605:4: ( ( RULE_INT ) )
        // InternalKdl.g:5605:5: ( RULE_INT )
        {
        // InternalKdl.g:5605:5: ( RULE_INT )
        // InternalKdl.g:5606:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred203_InternalKdl

    // $ANTLR start synpred204_InternalKdl
    public final void synpred204_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5627:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5627:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5627:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKdl.g:5628:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKdl.g:5628:5: ( ( '.' ) )
        // InternalKdl.g:5629:6: ( '.' )
        {
        // InternalKdl.g:5629:6: ( '.' )
        // InternalKdl.g:5630:7: '.'
        {
        match(input,107,FOLLOW_7); if (state.failed) return ;

        }


        }

        // InternalKdl.g:5633:5: ( ( RULE_INT ) )
        // InternalKdl.g:5634:6: ( RULE_INT )
        {
        // InternalKdl.g:5634:6: ( RULE_INT )
        // InternalKdl.g:5635:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred204_InternalKdl

    // $ANTLR start synpred208_InternalKdl
    public final void synpred208_InternalKdl_fragment() throws RecognitionException {   
        // InternalKdl.g:5676:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKdl.g:5676:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKdl.g:5676:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKdl.g:5677:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKdl.g:5677:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKdl.g:5678:6: ( ( 'e' | 'E' ) )
        {
        // InternalKdl.g:5678:6: ( ( 'e' | 'E' ) )
        // InternalKdl.g:5679:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=119 && input.LA(1)<=120) ) {
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

        // InternalKdl.g:5686:5: ( '+' | ( ( '-' ) ) )?
        int alt186=3;
        int LA186_0 = input.LA(1);

        if ( (LA186_0==34) ) {
            alt186=1;
        }
        else if ( (LA186_0==118) ) {
            alt186=2;
        }
        switch (alt186) {
            case 1 :
                // InternalKdl.g:5687:6: '+'
                {
                match(input,34,FOLLOW_7); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5689:6: ( ( '-' ) )
                {
                // InternalKdl.g:5689:6: ( ( '-' ) )
                // InternalKdl.g:5690:7: ( '-' )
                {
                // InternalKdl.g:5690:7: ( '-' )
                // InternalKdl.g:5691:8: '-'
                {
                match(input,118,FOLLOW_7); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKdl.g:5695:5: ( ( RULE_INT ) )
        // InternalKdl.g:5696:6: ( RULE_INT )
        {
        // InternalKdl.g:5696:6: ( RULE_INT )
        // InternalKdl.g:5697:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred208_InternalKdl

    // $ANTLR start synpred214_InternalKdl
    public final void synpred214_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;

        // InternalKdl.g:5838:4: ( (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )
        // InternalKdl.g:5838:4: (kw= '.' | kw= '/' ) this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
        {
        // InternalKdl.g:5838:4: (kw= '.' | kw= '/' )
        int alt187=2;
        int LA187_0 = input.LA(1);

        if ( (LA187_0==107) ) {
            alt187=1;
        }
        else if ( (LA187_0==106) ) {
            alt187=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 187, 0, input);

            throw nvae;
        }
        switch (alt187) {
            case 1 :
                // InternalKdl.g:5839:5: kw= '.'
                {
                kw=(Token)match(input,107,FOLLOW_5); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKdl.g:5845:5: kw= '/'
                {
                kw=(Token)match(input,106,FOLLOW_5); if (state.failed) return ;

                }
                break;

        }

        this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred214_InternalKdl

    // $ANTLR start synpred218_InternalKdl
    public final void synpred218_InternalKdl_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKdl.g:6047:4: (kw= '-' )
        // InternalKdl.g:6047:4: kw= '-'
        {
        kw=(Token)match(input,118,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred218_InternalKdl

    // $ANTLR start synpred219_InternalKdl
    public final void synpred219_InternalKdl_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKdl.g:6054:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKdl.g:6054:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred219_InternalKdl

    // $ANTLR start synpred220_InternalKdl
    public final void synpred220_InternalKdl_fragment() throws RecognitionException {   
        Token this_UPPERCASE_ID_7=null;

        // InternalKdl.g:6062:4: (this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )
        // InternalKdl.g:6062:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
        {
        this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred220_InternalKdl

    // Delegated rules

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
    public final boolean synpred219_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred219_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred129_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred129_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred220_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred220_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred208_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred208_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred132_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred132_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred162_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred162_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred164_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred164_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred163_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred163_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred181_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred181_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred83_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred83_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred93_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred93_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred95_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred95_InternalKdl_fragment(); // can never throw exception
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
    public final boolean synpred161_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred161_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred90_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred90_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred92_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred92_InternalKdl_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred180_InternalKdl() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred180_InternalKdl_fragment(); // can never throw exception
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
    protected DFA35 dfa35 = new DFA35(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA58 dfa58 = new DFA58(this);
    protected DFA69 dfa69 = new DFA69(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA83 dfa83 = new DFA83(this);
    protected DFA96 dfa96 = new DFA96(this);
    protected DFA105 dfa105 = new DFA105(this);
    protected DFA123 dfa123 = new DFA123(this);
    static final String dfa_1s = "\16\uffff";
    static final String dfa_2s = "\1\1\15\uffff";
    static final String dfa_3s = "\1\21\15\uffff";
    static final String dfa_4s = "\1\133\15\uffff";
    static final String dfa_5s = "\1\uffff\1\15\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14";
    static final String dfa_6s = "\1\0\15\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\uffff\1\15\3\1\2\uffff\1\1\5\uffff\5\1\36\uffff\1\1\5\uffff\12\1",
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
                        if ( (LA6_0==EOF||(LA6_0>=30 && LA6_0<=32)||LA6_0==35||(LA6_0>=41 && LA6_0<=45)||LA6_0==76||(LA6_0>=82 && LA6_0<=91)) ) {s = 1;}

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
    static final String dfa_11s = "\1\147\1\153\1\uffff\1\5\1\uffff\1\153";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\1\uffff";
    static final String dfa_13s = "\6\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\141\uffff\1\2",
            "\13\4\1\uffff\4\4\2\uffff\1\4\5\uffff\5\4\36\uffff\1\4\5\uffff\12\4\14\uffff\1\2\1\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\13\4\1\uffff\4\4\2\uffff\1\4\5\uffff\5\4\36\uffff\1\4\5\uffff\12\4\14\uffff\1\2\1\uffff\1\4\1\3"
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
    static final String dfa_16s = "\1\46\6\0\7\uffff";
    static final String dfa_17s = "\1\74\6\0\7\uffff";
    static final String dfa_18s = "\7\uffff\2\5\1\6\1\1\1\2\1\3\1\4";
    static final String dfa_19s = "\1\3\1\1\1\6\1\0\1\5\1\2\1\4\7\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\20\uffff\1\3\1\4\1\6\1\7\1\10\1\5",
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

    class DFA35 extends DFA {

        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = dfa_1;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "()+ loopback of 1407:6: ( ({...}? => ( ({...}? => (otherlv_7= 'geometry' ( (lv_geometry_8_0= ruleGeometry ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'units' ( (lv_units_10_0= ruleUnit ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_computations_11_0= ruleComputation ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_12= 'semantics' ( (lv_semantics_13_0= ruleObservableSemantics ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_14= 'metadata' ( (lv_metadata_15_0= ruleMetadata ) ) )? (otherlv_16= 'class' ( (lv_javaClass_17_0= ruleJavaClass ) ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA35_3 = input.LA(1);

                         
                        int index35_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred51_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 0) ) {s = 10;}

                        else if ( synpred58_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index35_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA35_1 = input.LA(1);

                         
                        int index35_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred58_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()) ) {s = 9;}

                         
                        input.seek(index35_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA35_5 = input.LA(1);

                         
                        int index35_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred54_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 2) ) {s = 12;}

                        else if ( synpred58_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index35_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA35_0 = input.LA(1);

                         
                        int index35_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA35_0==38) ) {s = 1;}

                        else if ( (LA35_0==EOF) ) {s = 2;}

                        else if ( (LA35_0==55) ) {s = 3;}

                        else if ( (LA35_0==56) ) {s = 4;}

                        else if ( (LA35_0==60) ) {s = 5;}

                        else if ( (LA35_0==57) ) {s = 6;}

                        else if ( LA35_0 == 58 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 7;}

                        else if ( LA35_0 == 59 && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index35_0);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA35_6 = input.LA(1);

                         
                        int index35_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred55_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 3) ) {s = 13;}

                        else if ( synpred58_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index35_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA35_4 = input.LA(1);

                         
                        int index35_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred52_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 1) ) {s = 11;}

                        else if ( synpred58_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                         
                        input.seek(index35_4);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA35_2 = input.LA(1);

                         
                        int index35_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( synpred58_InternalKdl() && getUnorderedGroupHelper().canSelect(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3(), 4) ) {s = 8;}

                        else if ( getUnorderedGroupHelper().canLeave(grammarAccess.getDataflowBodyAccess().getUnorderedGroup_3()) ) {s = 9;}

                         
                        input.seek(index35_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 35, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_21s = "\12\uffff";
    static final String dfa_22s = "\1\1\11\uffff";
    static final String dfa_23s = "\1\6\11\uffff";
    static final String dfa_24s = "\1\166\11\uffff";
    static final String dfa_25s = "\1\uffff\1\6\1\1\1\2\1\3\2\4\3\5";
    static final String dfa_26s = "\1\0\11\uffff}>";
    static final String[] dfa_27s = {
            "\1\11\27\uffff\3\1\1\uffff\1\7\1\1\2\uffff\1\1\1\4\1\uffff\5\1\11\uffff\6\1\5\uffff\1\2\7\uffff\1\5\1\uffff\1\1\3\uffff\1\3\1\6\12\1\32\uffff\1\10",
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

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "()* loopback of 2576:6: ( ({...}? => ( ({...}? => (otherlv_2= 'by' ( (lv_by_3_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= 'down' otherlv_5= 'to' ( ( (lv_downTo_6_1= RULE_CAMELCASE_ID | lv_downTo_6_2= ruleNamespaceId ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'as' ( (lv_role_8_0= ruleConcept ) ) ) ) ) ) | ({...}? => ( ({...}? => ( (otherlv_9= 'in' ( ( (lv_unit_10_0= ruleUnit ) ) | ( (lv_currency_11_0= ruleCurrency ) ) ) ) | (otherlv_12= 'per' ( (lv_unit_13_0= ruleUnit ) ) ) ) ) ) ) | ({...}? => ( ({...}? => ( ( (lv_from_14_0= ruleNumber ) ) otherlv_15= 'to' ( (lv_to_16_0= ruleNumber ) ) ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_0 = input.LA(1);

                         
                        int index56_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA56_0==EOF||(LA56_0>=30 && LA56_0<=32)||LA56_0==35||LA56_0==38||(LA56_0>=41 && LA56_0<=45)||(LA56_0>=55 && LA56_0<=60)||LA56_0==76||(LA56_0>=82 && LA56_0<=91)) ) {s = 1;}

                        else if ( LA56_0 == 66 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 0) ) {s = 2;}

                        else if ( LA56_0 == 80 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 1) ) {s = 3;}

                        else if ( LA56_0 == 39 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 2) ) {s = 4;}

                        else if ( LA56_0 == 74 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {s = 5;}

                        else if ( LA56_0 == 81 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 3) ) {s = 6;}

                        else if ( LA56_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {s = 7;}

                        else if ( LA56_0 == 118 && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {s = 8;}

                        else if ( LA56_0 == RULE_INT && getUnorderedGroupHelper().canSelect(grammarAccess.getObservableSemanticsAccess().getUnorderedGroup_1(), 4) ) {s = 9;}

                         
                        input.seek(index56_0);
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
    static final String dfa_28s = "\20\uffff";
    static final String dfa_29s = "\1\1\17\uffff";
    static final String dfa_30s = "\1\5\1\uffff\15\0\1\uffff";
    static final String dfa_31s = "\1\166\1\uffff\15\0\1\uffff";
    static final String dfa_32s = "\1\uffff\1\2\15\uffff\1\1";
    static final String dfa_33s = "\2\uffff\1\11\1\7\1\13\1\5\1\14\1\10\1\0\1\12\1\3\1\1\1\2\1\6\1\4\1\uffff}>";
    static final String[] dfa_34s = {
            "\1\5\1\1\3\uffff\1\4\21\uffff\1\1\1\uffff\3\1\1\uffff\2\1\2\uffff\10\1\6\uffff\1\1\2\uffff\6\1\1\uffff\2\1\1\3\1\uffff\1\1\1\6\1\7\1\10\1\uffff\1\11\1\12\1\13\1\1\1\14\1\2\1\15\1\16\15\1\3\uffff\3\1\24\uffff\1\1",
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

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "()+ loopback of 2884:3: ( (lv_main_1_0= ruleConcept ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA58_8 = input.LA(1);

                         
                        int index58_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA58_11 = input.LA(1);

                         
                        int index58_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_11);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA58_12 = input.LA(1);

                         
                        int index58_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_12);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA58_10 = input.LA(1);

                         
                        int index58_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_10);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA58_14 = input.LA(1);

                         
                        int index58_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_14);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA58_5 = input.LA(1);

                         
                        int index58_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA58_13 = input.LA(1);

                         
                        int index58_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_13);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA58_3 = input.LA(1);

                         
                        int index58_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_3);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA58_7 = input.LA(1);

                         
                        int index58_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_7);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA58_2 = input.LA(1);

                         
                        int index58_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_2);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA58_9 = input.LA(1);

                         
                        int index58_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_9);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA58_4 = input.LA(1);

                         
                        int index58_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_4);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA58_6 = input.LA(1);

                         
                        int index58_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred95_InternalKdl()) ) {s = 15;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index58_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 58, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_35s = "\41\uffff";
    static final String dfa_36s = "\1\4\2\uffff\3\0\1\uffff\1\0\14\uffff\1\0\14\uffff";
    static final String dfa_37s = "\1\166\2\uffff\3\0\1\uffff\1\0\14\uffff\1\0\14\uffff";
    static final String dfa_38s = "\1\uffff\1\1\4\uffff\1\4\1\uffff\1\6\14\uffff\1\10\5\uffff\1\11\1\12\1\2\1\3\1\5\1\7";
    static final String dfa_39s = "\3\uffff\1\0\1\1\1\2\1\uffff\1\3\14\uffff\1\4\14\uffff}>";
    static final String[] dfa_40s = {
            "\1\7\1\10\1\5\3\uffff\1\10\27\uffff\1\3\32\uffff\1\34\2\uffff\1\10\2\uffff\3\10\1\uffff\3\10\1\6\3\10\1\24\23\uffff\2\1\2\uffff\1\33\6\uffff\1\25\2\uffff\5\25\1\uffff\1\4",
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

    class DFA69 extends DFA {

        public DFA69(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 69;
            this.eot = dfa_35;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "3296:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_concept_13_0= ruleConceptDeclaration ) ) | (otherlv_14= '(' ( (lv_toResolve_15_0= ruleConceptDeclaration ) ) ( ( ( ',' )=>otherlv_16= ',' ) ( (lv_toResolve_17_0= ruleConceptDeclaration ) ) )* otherlv_18= ')' ) | ( ( (lv_op_19_0= ruleREL_OPERATOR ) ) ( (lv_expression_20_0= ruleNumber ) ) ) | ( (lv_nodata_21_0= 'unknown' ) ) | ( (lv_star_22_0= '*' ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA69_3 = input.LA(1);

                         
                        int index69_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 29;}

                        else if ( (synpred126_InternalKdl()) ) {s = 30;}

                         
                        input.seek(index69_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA69_4 = input.LA(1);

                         
                        int index69_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 29;}

                        else if ( (synpred126_InternalKdl()) ) {s = 30;}

                         
                        input.seek(index69_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA69_5 = input.LA(1);

                         
                        int index69_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred125_InternalKdl()) ) {s = 29;}

                        else if ( (synpred126_InternalKdl()) ) {s = 30;}

                         
                        input.seek(index69_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA69_7 = input.LA(1);

                         
                        int index69_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_InternalKdl()) ) {s = 31;}

                        else if ( (synpred129_InternalKdl()) ) {s = 8;}

                         
                        input.seek(index69_7);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA69_20 = input.LA(1);

                         
                        int index69_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred129_InternalKdl()) ) {s = 8;}

                        else if ( (synpred132_InternalKdl()) ) {s = 32;}

                         
                        input.seek(index69_20);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 69, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_41s = "\17\uffff";
    static final String dfa_42s = "\3\uffff\1\12\7\uffff\1\12\2\uffff\1\12";
    static final String dfa_43s = "\1\4\2\6\1\4\2\uffff\3\6\2\uffff\1\4\2\6\1\4";
    static final String dfa_44s = "\1\166\2\6\1\170\2\uffff\1\6\2\166\2\uffff\1\170\2\6\1\166";
    static final String dfa_45s = "\4\uffff\1\3\1\4\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_46s = "\17\uffff}>";
    static final String[] dfa_47s = {
            "\1\4\1\uffff\1\3\33\uffff\1\1\77\uffff\2\5\22\uffff\1\2",
            "\1\3",
            "\1\3",
            "\5\12\1\uffff\2\12\5\uffff\20\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\2\uffff\6\12\17\uffff\1\12\1\uffff\2\12\2\uffff\12\12\6\uffff\2\12\3\uffff\1\12\3\uffff\1\6\3\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "",
            "",
            "\1\13",
            "\1\16\33\uffff\1\14\123\uffff\1\15",
            "\1\16\33\uffff\1\14\123\uffff\1\15",
            "",
            "",
            "\5\12\1\uffff\2\12\5\uffff\20\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\2\uffff\6\12\17\uffff\1\12\1\uffff\2\12\2\uffff\12\12\6\uffff\2\12\3\uffff\1\12\7\uffff\1\12\6\uffff\1\12\1\7\1\10",
            "\1\16",
            "\1\16",
            "\5\12\1\uffff\2\12\5\uffff\20\12\1\uffff\2\12\1\uffff\3\12\1\uffff\5\12\3\uffff\3\12\1\11\2\uffff\6\12\17\uffff\1\12\1\uffff\2\12\2\uffff\12\12\6\uffff\2\12\3\uffff\1\12\7\uffff\1\12\6\uffff\1\12"
    };

    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[][] dfa_47 = unpackEncodedStringArray(dfa_47s);

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = dfa_41;
            this.eof = dfa_42;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "3710:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_48s = "\21\uffff";
    static final String dfa_49s = "\3\uffff\1\13\11\uffff\1\13\2\uffff\1\13";
    static final String dfa_50s = "\1\4\2\6\1\4\4\uffff\3\6\2\uffff\1\4\2\6\1\4";
    static final String dfa_51s = "\1\166\2\6\1\170\4\uffff\1\6\2\166\2\uffff\1\170\2\6\1\166";
    static final String dfa_52s = "\4\uffff\1\3\1\4\1\5\1\6\3\uffff\1\2\1\1\4\uffff";
    static final String dfa_53s = "\21\uffff}>";
    static final String[] dfa_54s = {
            "\1\4\1\uffff\1\3\4\uffff\1\6\20\uffff\1\7\5\uffff\1\1\77\uffff\2\5\22\uffff\1\2",
            "\1\3",
            "\1\3",
            "\5\13\1\uffff\2\13\5\uffff\20\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\2\uffff\6\13\17\uffff\1\13\1\uffff\2\13\2\uffff\12\13\6\uffff\2\13\3\uffff\1\13\3\uffff\1\10\3\uffff\1\13\6\uffff\1\13\1\11\1\12",
            "",
            "",
            "",
            "",
            "\1\15",
            "\1\20\33\uffff\1\16\123\uffff\1\17",
            "\1\20\33\uffff\1\16\123\uffff\1\17",
            "",
            "",
            "\5\13\1\uffff\2\13\5\uffff\20\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\2\uffff\6\13\17\uffff\1\13\1\uffff\2\13\2\uffff\12\13\6\uffff\2\13\3\uffff\1\13\7\uffff\1\13\6\uffff\1\13\1\11\1\12",
            "\1\20",
            "\1\20",
            "\5\13\1\uffff\2\13\5\uffff\20\13\1\uffff\2\13\1\uffff\2\13\2\uffff\5\13\3\uffff\3\13\1\14\2\uffff\6\13\17\uffff\1\13\1\uffff\2\13\2\uffff\12\13\6\uffff\2\13\3\uffff\1\13\7\uffff\1\13\6\uffff\1\13"
    };

    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final char[] dfa_51 = DFA.unpackEncodedStringToUnsignedChars(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[][] dfa_54 = unpackEncodedStringArray(dfa_54s);

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = dfa_48;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "3840:2: ( ( ( (lv_from_0_0= ruleNumber ) ) ( ( 'to' )=>otherlv_1= 'to' ) ( ( ( ruleNumber ) )=> (lv_to_2_0= ruleNumber ) ) ) | ( (lv_number_3_0= ruleNumber ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( ( (lv_boolean_5_1= 'true' | lv_boolean_5_2= 'false' ) ) ) | ( (lv_id_6_0= RULE_ID ) ) | ( (lv_comma_7_0= ',' ) ) )";
        }
    }
    static final String dfa_55s = "\23\uffff";
    static final String dfa_56s = "\1\4\7\0\1\uffff\4\0\1\uffff\1\0\4\uffff";
    static final String dfa_57s = "\1\166\7\0\1\uffff\4\0\1\uffff\1\0\4\uffff";
    static final String dfa_58s = "\10\uffff\1\1\4\uffff\1\2\1\uffff\1\6\1\3\1\4\1\5";
    static final String dfa_59s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\7\1\10\1\11\1\12\1\uffff\1\13\4\uffff}>";
    static final String[] dfa_60s = {
            "\1\4\1\11\1\3\1\14\1\17\1\uffff\1\13\1\7\20\uffff\1\10\5\uffff\1\1\53\uffff\1\16\23\uffff\1\5\1\6\3\uffff\1\12\7\uffff\1\15\6\uffff\1\2",
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

    class DFA83 extends DFA {

        public DFA83(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 83;
            this.eot = dfa_55;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_59;
            this.transition = dfa_60;
        }
        public String getDescription() {
            return "4358:2: ( ( (lv_literal_0_0= ruleLiteralOrIdOrComma ) ) | ( (lv_function_1_0= ruleFunction ) ) | ( ( (lv_urn_2_0= ruleUrn ) ) ( (lv_unit_3_0= ruleUnit ) ) ) | ( (lv_currency_4_0= ruleCurrency ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_enumId_6_0= RULE_UPPERCASE_ID ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA83_1 = input.LA(1);

                         
                        int index83_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred161_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index83_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA83_2 = input.LA(1);

                         
                        int index83_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred161_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index83_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA83_3 = input.LA(1);

                         
                        int index83_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred161_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index83_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA83_4 = input.LA(1);

                         
                        int index83_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred161_InternalKdl()) ) {s = 13;}

                        else if ( (synpred162_InternalKdl()) ) {s = 16;}

                         
                        input.seek(index83_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA83_5 = input.LA(1);

                         
                        int index83_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred161_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index83_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA83_6 = input.LA(1);

                         
                        int index83_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred161_InternalKdl()) ) {s = 13;}

                         
                        input.seek(index83_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA83_7 = input.LA(1);

                         
                        int index83_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKdl()) ) {s = 8;}

                        else if ( (synpred163_InternalKdl()) ) {s = 17;}

                         
                        input.seek(index83_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA83_9 = input.LA(1);

                         
                        int index83_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred161_InternalKdl()) ) {s = 13;}

                        else if ( (synpred162_InternalKdl()) ) {s = 16;}

                        else if ( (synpred163_InternalKdl()) ) {s = 17;}

                         
                        input.seek(index83_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA83_10 = input.LA(1);

                         
                        int index83_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred161_InternalKdl()) ) {s = 13;}

                        else if ( (synpred162_InternalKdl()) ) {s = 16;}

                         
                        input.seek(index83_10);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA83_11 = input.LA(1);

                         
                        int index83_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred161_InternalKdl()) ) {s = 13;}

                        else if ( (synpred162_InternalKdl()) ) {s = 16;}

                        else if ( (synpred163_InternalKdl()) ) {s = 17;}

                         
                        input.seek(index83_11);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA83_12 = input.LA(1);

                         
                        int index83_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred161_InternalKdl()) ) {s = 13;}

                        else if ( (synpred162_InternalKdl()) ) {s = 16;}

                         
                        input.seek(index83_12);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA83_14 = input.LA(1);

                         
                        int index83_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred161_InternalKdl()) ) {s = 13;}

                        else if ( (synpred164_InternalKdl()) ) {s = 18;}

                         
                        input.seek(index83_14);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 83, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_61s = "\14\uffff";
    static final String dfa_62s = "\1\4\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_63s = "\1\166\1\0\1\uffff\1\0\10\uffff";
    static final String dfa_64s = "\2\uffff\1\2\3\uffff\1\3\4\uffff\1\1";
    static final String dfa_65s = "\1\uffff\1\0\1\uffff\1\1\10\uffff}>";
    static final String[] dfa_66s = {
            "\1\3\1\1\1\6\1\2\2\uffff\1\2\27\uffff\1\6\77\uffff\2\6\3\uffff\1\2\16\uffff\1\6",
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

    class DFA96 extends DFA {

        public DFA96(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 96;
            this.eot = dfa_61;
            this.eof = dfa_61;
            this.min = dfa_62;
            this.max = dfa_63;
            this.accept = dfa_64;
            this.special = dfa_65;
            this.transition = dfa_66;
        }
        public String getDescription() {
            return "4904:4: ( ( ( (lv_name_2_0= rulePathName ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleParameterList ) )? otherlv_5= ')' ) | ( (lv_urn_6_0= ruleUrn ) ) | ( (lv_value_7_0= ruleLiteral ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA96_1 = input.LA(1);

                         
                        int index96_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalKdl()) ) {s = 11;}

                        else if ( (synpred181_InternalKdl()) ) {s = 2;}

                         
                        input.seek(index96_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA96_3 = input.LA(1);

                         
                        int index96_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred181_InternalKdl()) ) {s = 2;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index96_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 96, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_67s = "\106\uffff";
    static final String dfa_68s = "\1\7\105\uffff";
    static final String dfa_69s = "\1\4\6\0\77\uffff";
    static final String dfa_70s = "\1\171\6\0\77\uffff";
    static final String dfa_71s = "\7\uffff\1\2\75\uffff\1\1";
    static final String dfa_72s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\77\uffff}>";
    static final String[] dfa_73s = {
            "\1\7\1\2\1\5\2\7\1\uffff\1\1\1\7\5\uffff\20\7\1\uffff\1\3\1\7\1\uffff\3\7\1\uffff\5\7\3\uffff\3\7\3\uffff\7\7\4\uffff\1\7\7\uffff\1\7\1\uffff\1\7\1\uffff\1\6\15\7\6\uffff\2\7\3\uffff\1\7\2\uffff\1\7\4\uffff\1\7\6\uffff\1\4\2\uffff\1\7",
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

    class DFA105 extends DFA {

        public DFA105(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 105;
            this.eot = dfa_67;
            this.eof = dfa_68;
            this.min = dfa_69;
            this.max = dfa_70;
            this.accept = dfa_71;
            this.special = dfa_72;
            this.transition = dfa_73;
        }
        public String getDescription() {
            return "5397:3: ( (lv_root_1_0= ruleUnitElement ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA105_1 = input.LA(1);

                         
                        int index105_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred196_InternalKdl()) ) {s = 69;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index105_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA105_2 = input.LA(1);

                         
                        int index105_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred196_InternalKdl()) ) {s = 69;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index105_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA105_3 = input.LA(1);

                         
                        int index105_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred196_InternalKdl()) ) {s = 69;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index105_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA105_4 = input.LA(1);

                         
                        int index105_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred196_InternalKdl()) ) {s = 69;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index105_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA105_5 = input.LA(1);

                         
                        int index105_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred196_InternalKdl()) ) {s = 69;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index105_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA105_6 = input.LA(1);

                         
                        int index105_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred196_InternalKdl()) ) {s = 69;}

                        else if ( (true) ) {s = 7;}

                         
                        input.seek(index105_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 105, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_74s = "\1\5\1\150\1\uffff\1\5\1\uffff\1\150";
    static final String[] dfa_75s = {
            "\1\1\141\uffff\1\2",
            "\1\2\1\uffff\1\4\1\3",
            "",
            "\1\5",
            "",
            "\1\2\1\uffff\1\4\1\3"
    };
    static final char[] dfa_74 = DFA.unpackEncodedStringToUnsignedChars(dfa_74s);
    static final short[][] dfa_75 = unpackEncodedStringArray(dfa_75s);

    class DFA123 extends DFA {

        public DFA123(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 123;
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
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L,0x0000008000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00003E09EFFE0002L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x2000000000000200L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000004000004F0L,0x0040808C00004000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00003E09FFFE0002L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000000000004B0L,0x0000008000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00003E09C0000002L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000E09C0000000L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00000E0BC0000040L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x00000E0DC0000000L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x00000000000000B0L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000001B000000012L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000000L,0x0000000070000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x000001A010000012L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x000001A000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x1FA03E49C0000000L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000018000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000800000000010L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000010000010L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x000F002000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000410000DF0L,0x0040808C00004000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x000E002000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x000E000000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000400000040L,0x0040000000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x00400000000004B0L,0x0000008000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000C30L,0x0000008000007BB9L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x1F803E09C0000002L,0x000000000FFC1000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x1F80000000000002L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x3F80000400000460L,0x0240040000004000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0xC000000000000C32L,0x0000008000007BB9L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000C20L,0x0000008000000001L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000850L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000001100L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0010000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000008400000042L,0x0040000000030404L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000C20L,0x0000008000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x2000008400000C60L,0x0240048000034404L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x2000008400000460L,0x0240040000034404L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000C32L,0x0000008000007BB9L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000380000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x2000000400000C70L,0x005F20CC00007FB9L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0010000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0000003000000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000010000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000410000DF0L,0x0040808C0000C000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000004000000020L,0x0000008000000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000002400000850L,0x0040000C00004000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x2000000400000460L,0x0240040000004000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000002L,0x0000030000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x00000E0000000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x00000000000004A0L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x00000004000004F0L,0x0040008C00000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x2000000400000460L,0x024004000000C000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x2000000000000002L,0x0200040000000000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000400000460L,0x0040000000004000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000002L,0x0180080000000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000002L,0x0180000000000000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000002L,0x0000080000000000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000002L,0x00000C0000000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000122L,0x0040080000000000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000122L,0x0040000000000000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000122L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x2000000400000C60L,0x0240048000004000L});

}
