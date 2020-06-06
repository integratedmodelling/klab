package org.integratedmodelling.kactors.parser.antlr.internal;

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
import org.integratedmodelling.kactors.services.KactorsGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalKactorsParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_OBSERVABLE", "RULE_LOWERCASE_ID", "RULE_EMBEDDEDTEXT", "RULE_EXPR", "RULE_ARGVALUE", "RULE_CAMELCASE_ID", "RULE_REGEXP", "RULE_INT", "RULE_SEPARATOR", "RULE_ANNOTATION_ID", "RULE_UPPERCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'app'", "'job'", "'testcase'", "'user'", "'trait'", "'library'", "'behavior'", "'behaviour'", "'import'", "','", "'worldview'", "'observable'", "'description'", "'permissions'", "'author'", "'style'", "'version'", "'created'", "'modified'", "'action'", "':'", "'('", "')'", "'set'", "'if'", "'else'", "'while'", "'do'", "'for'", "'in'", "'->'", "'true'", "'false'", "'unknown'", "'*'", "'#'", "'urn:klab:'", "'&'", "'='", "'#{'", "'}'", "'<-'", "'inclusive'", "'exclusive'", "'to'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'!'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_EMBEDDEDTEXT=7;
    public static final int RULE_UPPERCASE_ID=15;
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
    public static final int RULE_ID=17;
    public static final int RULE_CAMELCASE_ID=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=12;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=18;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__68=68;
    public static final int T__25=25;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_ARGVALUE=9;
    public static final int RULE_STRING=4;
    public static final int RULE_SEPARATOR=13;
    public static final int RULE_SL_COMMENT=19;
    public static final int RULE_OBSERVABLE=5;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__79=79;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int RULE_REGEXP=11;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=20;
    public static final int RULE_ANY_OTHER=21;
    public static final int RULE_ANNOTATION_ID=14;
    public static final int T__48=48;
    public static final int RULE_LOWERCASE_ID=6;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__88=88;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=8;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__84=84;
    public static final int T__41=41;
    public static final int T__85=85;
    public static final int T__42=42;
    public static final int T__86=86;
    public static final int RULE_UPPERCASE_PATH=16;
    public static final int T__43=43;
    public static final int T__87=87;

    // delegates
    // delegators


        public InternalKactorsParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalKactorsParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalKactorsParser.tokenNames; }
    public String getGrammarFileName() { return "InternalKactors.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */

     	private KactorsGrammarAccess grammarAccess;

        public InternalKactorsParser(TokenStream input, KactorsGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected KactorsGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalKactors.g:72:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalKactors.g:72:46: (iv_ruleModel= ruleModel EOF )
            // InternalKactors.g:73:2: iv_ruleModel= ruleModel EOF
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
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalKactors.g:79:1: ruleModel returns [EObject current=null] : ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_preamble_1_0 = null;

        EObject lv_definitions_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:85:2: ( ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* ) )
            // InternalKactors.g:86:2: ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* )
            {
            // InternalKactors.g:86:2: ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* )
            // InternalKactors.g:87:3: () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )*
            {
            // InternalKactors.g:87:3: ()
            // InternalKactors.g:88:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getModelAccess().getModelAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:97:3: ( (lv_preamble_1_0= rulePreamble ) )?
            int alt1=2;
            switch ( input.LA(1) ) {
                case RULE_STRING:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                    {
                    alt1=1;
                    }
                    break;
                case RULE_ANNOTATION_ID:
                    {
                    int LA1_2 = input.LA(2);

                    if ( (synpred1_InternalKactors()) ) {
                        alt1=1;
                    }
                    }
                    break;
                case 41:
                    {
                    int LA1_3 = input.LA(2);

                    if ( (synpred1_InternalKactors()) ) {
                        alt1=1;
                    }
                    }
                    break;
                case EOF:
                    {
                    int LA1_4 = input.LA(2);

                    if ( (synpred1_InternalKactors()) ) {
                        alt1=1;
                    }
                    }
                    break;
            }

            switch (alt1) {
                case 1 :
                    // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
                    {
                    // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
                    // InternalKactors.g:99:5: lv_preamble_1_0= rulePreamble
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_3);
                    lv_preamble_1_0=rulePreamble();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getModelRule());
                      					}
                      					set(
                      						current,
                      						"preamble",
                      						lv_preamble_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Preamble");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:116:3: ( (lv_definitions_2_0= ruleDefinition ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==41) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:117:4: (lv_definitions_2_0= ruleDefinition )
            	    {
            	    // InternalKactors.g:117:4: (lv_definitions_2_0= ruleDefinition )
            	    // InternalKactors.g:118:5: lv_definitions_2_0= ruleDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getDefinitionsDefinitionParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_definitions_2_0=ruleDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"definitions",
            	      						lv_definitions_2_0,
            	      						"org.integratedmodelling.kactors.Kactors.Definition");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRulePreamble"
    // InternalKactors.g:139:1: entryRulePreamble returns [EObject current=null] : iv_rulePreamble= rulePreamble EOF ;
    public final EObject entryRulePreamble() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePreamble = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_3()
        	);

        try {
            // InternalKactors.g:143:2: (iv_rulePreamble= rulePreamble EOF )
            // InternalKactors.g:144:2: iv_rulePreamble= rulePreamble EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPreambleRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePreamble=rulePreamble();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePreamble; 
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
    // $ANTLR end "entryRulePreamble"


    // $ANTLR start "rulePreamble"
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( (lv_label_7_0= RULE_STRING ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token lv_app_1_1=null;
        Token lv_app_1_2=null;
        Token lv_test_2_0=null;
        Token lv_user_3_0=null;
        Token lv_library_4_1=null;
        Token lv_library_4_2=null;
        Token lv_behavior_5_1=null;
        Token lv_behavior_5_2=null;
        Token lv_label_7_0=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token lv_observable_16_0=null;
        Token otherlv_18=null;
        Token lv_description_19_0=null;
        Token otherlv_20=null;
        Token lv_permissions_21_0=null;
        Token otherlv_22=null;
        Token lv_authors_23_0=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token lv_createcomment_30_0=null;
        Token otherlv_31=null;
        Token lv_modcomment_33_0=null;
        AntlrDatatypeRuleToken lv_name_6_0 = null;

        AntlrDatatypeRuleToken lv_imports_10_0 = null;

        AntlrDatatypeRuleToken lv_imports_12_0 = null;

        AntlrDatatypeRuleToken lv_worldview_14_0 = null;

        EObject lv_observables_17_0 = null;

        AntlrDatatypeRuleToken lv_style_25_0 = null;

        AntlrDatatypeRuleToken lv_version_27_0 = null;

        EObject lv_created_29_0 = null;

        EObject lv_modified_32_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_3()
        	);

        try {
            // InternalKactors.g:162:2: ( ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( (lv_label_7_0= RULE_STRING ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( (lv_label_7_0= RULE_STRING ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( (lv_label_7_0= RULE_STRING ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( (lv_label_7_0= RULE_STRING ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            {
            // InternalKactors.g:164:3: ()
            // InternalKactors.g:165:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getPreambleAccess().getPreambleAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:174:3: ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( ((LA7_0>=22 && LA7_0<=29)) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalKactors.g:175:4: ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) )
                    {
                    // InternalKactors.g:175:4: ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) )
                    int alt6=5;
                    switch ( input.LA(1) ) {
                    case 22:
                    case 23:
                        {
                        alt6=1;
                        }
                        break;
                    case 24:
                        {
                        alt6=2;
                        }
                        break;
                    case 25:
                        {
                        alt6=3;
                        }
                        break;
                    case 26:
                    case 27:
                        {
                        alt6=4;
                        }
                        break;
                    case 28:
                    case 29:
                        {
                        alt6=5;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 0, input);

                        throw nvae;
                    }

                    switch (alt6) {
                        case 1 :
                            // InternalKactors.g:176:5: ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) )
                            {
                            // InternalKactors.g:176:5: ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) )
                            // InternalKactors.g:177:6: ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) )
                            {
                            // InternalKactors.g:177:6: ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) )
                            // InternalKactors.g:178:7: (lv_app_1_1= 'app' | lv_app_1_2= 'job' )
                            {
                            // InternalKactors.g:178:7: (lv_app_1_1= 'app' | lv_app_1_2= 'job' )
                            int alt3=2;
                            int LA3_0 = input.LA(1);

                            if ( (LA3_0==22) ) {
                                alt3=1;
                            }
                            else if ( (LA3_0==23) ) {
                                alt3=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 0, input);

                                throw nvae;
                            }
                            switch (alt3) {
                                case 1 :
                                    // InternalKactors.g:179:8: lv_app_1_1= 'app'
                                    {
                                    lv_app_1_1=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_app_1_1, grammarAccess.getPreambleAccess().getAppAppKeyword_1_0_0_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "app", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKactors.g:190:8: lv_app_1_2= 'job'
                                    {
                                    lv_app_1_2=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_app_1_2, grammarAccess.getPreambleAccess().getAppJobKeyword_1_0_0_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "app", true, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:204:5: ( (lv_test_2_0= 'testcase' ) )
                            {
                            // InternalKactors.g:204:5: ( (lv_test_2_0= 'testcase' ) )
                            // InternalKactors.g:205:6: (lv_test_2_0= 'testcase' )
                            {
                            // InternalKactors.g:205:6: (lv_test_2_0= 'testcase' )
                            // InternalKactors.g:206:7: lv_test_2_0= 'testcase'
                            {
                            lv_test_2_0=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_test_2_0, grammarAccess.getPreambleAccess().getTestTestcaseKeyword_1_0_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "test", true, "testcase");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKactors.g:219:5: ( (lv_user_3_0= 'user' ) )
                            {
                            // InternalKactors.g:219:5: ( (lv_user_3_0= 'user' ) )
                            // InternalKactors.g:220:6: (lv_user_3_0= 'user' )
                            {
                            // InternalKactors.g:220:6: (lv_user_3_0= 'user' )
                            // InternalKactors.g:221:7: lv_user_3_0= 'user'
                            {
                            lv_user_3_0=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_user_3_0, grammarAccess.getPreambleAccess().getUserUserKeyword_1_0_2_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "user", true, "user");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 4 :
                            // InternalKactors.g:234:5: ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) )
                            {
                            // InternalKactors.g:234:5: ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) )
                            // InternalKactors.g:235:6: ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) )
                            {
                            // InternalKactors.g:235:6: ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) )
                            // InternalKactors.g:236:7: (lv_library_4_1= 'trait' | lv_library_4_2= 'library' )
                            {
                            // InternalKactors.g:236:7: (lv_library_4_1= 'trait' | lv_library_4_2= 'library' )
                            int alt4=2;
                            int LA4_0 = input.LA(1);

                            if ( (LA4_0==26) ) {
                                alt4=1;
                            }
                            else if ( (LA4_0==27) ) {
                                alt4=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 4, 0, input);

                                throw nvae;
                            }
                            switch (alt4) {
                                case 1 :
                                    // InternalKactors.g:237:8: lv_library_4_1= 'trait'
                                    {
                                    lv_library_4_1=(Token)match(input,26,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_library_4_1, grammarAccess.getPreambleAccess().getLibraryTraitKeyword_1_0_3_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "library", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKactors.g:248:8: lv_library_4_2= 'library'
                                    {
                                    lv_library_4_2=(Token)match(input,27,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_library_4_2, grammarAccess.getPreambleAccess().getLibraryLibraryKeyword_1_0_3_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "library", true, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;
                        case 5 :
                            // InternalKactors.g:262:5: ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) )
                            {
                            // InternalKactors.g:262:5: ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) )
                            // InternalKactors.g:263:6: ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) )
                            {
                            // InternalKactors.g:263:6: ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) )
                            // InternalKactors.g:264:7: (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' )
                            {
                            // InternalKactors.g:264:7: (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' )
                            int alt5=2;
                            int LA5_0 = input.LA(1);

                            if ( (LA5_0==28) ) {
                                alt5=1;
                            }
                            else if ( (LA5_0==29) ) {
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
                                    // InternalKactors.g:265:8: lv_behavior_5_1= 'behavior'
                                    {
                                    lv_behavior_5_1=(Token)match(input,28,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_behavior_5_1, grammarAccess.getPreambleAccess().getBehaviorBehaviorKeyword_1_0_4_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "behavior", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKactors.g:276:8: lv_behavior_5_2= 'behaviour'
                                    {
                                    lv_behavior_5_2=(Token)match(input,29,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_behavior_5_2, grammarAccess.getPreambleAccess().getBehaviorBehaviourKeyword_1_0_4_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "behavior", true, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKactors.g:290:4: ( (lv_name_6_0= rulePathName ) )
                    // InternalKactors.g:291:5: (lv_name_6_0= rulePathName )
                    {
                    // InternalKactors.g:291:5: (lv_name_6_0= rulePathName )
                    // InternalKactors.g:292:6: lv_name_6_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_name_6_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPreambleRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_6_0,
                      							"org.integratedmodelling.kactors.Kactors.PathName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:310:3: ( (lv_label_7_0= RULE_STRING ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_STRING) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalKactors.g:311:4: (lv_label_7_0= RULE_STRING )
                    {
                    // InternalKactors.g:311:4: (lv_label_7_0= RULE_STRING )
                    // InternalKactors.g:312:5: lv_label_7_0= RULE_STRING
                    {
                    lv_label_7_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_label_7_0, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPreambleRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"label",
                      						lv_label_7_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:328:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:329:4: ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:329:4: ( ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:330:5: ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_3());
            // InternalKactors.g:333:5: ( ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:334:6: ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:334:6: ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )*
            loop14:
            do {
                int alt14=11;
                alt14 = dfa14.predict(input);
                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:335:4: ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:335:4: ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:336:5: {...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0)");
            	    }
            	    // InternalKactors.g:336:105: ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:337:6: ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0);
            	    // InternalKactors.g:340:9: ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:340:10: {...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:340:19: (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* )
            	    // InternalKactors.g:340:20: otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,30,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_9, grammarAccess.getPreambleAccess().getImportKeyword_3_0_0());
            	      								
            	    }
            	    // InternalKactors.g:344:9: ( (lv_imports_10_0= rulePathName ) )
            	    // InternalKactors.g:345:10: (lv_imports_10_0= rulePathName )
            	    {
            	    // InternalKactors.g:345:10: (lv_imports_10_0= rulePathName )
            	    // InternalKactors.g:346:11: lv_imports_10_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_3_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_7);
            	    lv_imports_10_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											add(
            	      												current,
            	      												"imports",
            	      												lv_imports_10_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:363:9: (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )*
            	    loop9:
            	    do {
            	        int alt9=2;
            	        int LA9_0 = input.LA(1);

            	        if ( (LA9_0==31) ) {
            	            alt9=1;
            	        }


            	        switch (alt9) {
            	    	case 1 :
            	    	    // InternalKactors.g:364:10: otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_11, grammarAccess.getPreambleAccess().getCommaKeyword_3_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:368:10: ( (lv_imports_12_0= rulePathName ) )
            	    	    // InternalKactors.g:369:11: (lv_imports_12_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:369:11: (lv_imports_12_0= rulePathName )
            	    	    // InternalKactors.g:370:12: lv_imports_12_0= rulePathName
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_3_0_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_7);
            	    	    lv_imports_12_0=rulePathName();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      												if (current==null) {
            	    	      													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	    	      												}
            	    	      												add(
            	    	      													current,
            	    	      													"imports",
            	    	      													lv_imports_12_0,
            	    	      													"org.integratedmodelling.kactors.Kactors.PathName");
            	    	      												afterParserOrEnumRuleCall();
            	    	      											
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop9;
            	        }
            	    } while (true);


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:394:4: ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:394:4: ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:395:5: {...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1)");
            	    }
            	    // InternalKactors.g:395:105: ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:396:6: ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1);
            	    // InternalKactors.g:399:9: ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) )
            	    // InternalKactors.g:399:10: {...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:399:19: (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) )
            	    // InternalKactors.g:399:20: otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) )
            	    {
            	    otherlv_13=(Token)match(input,32,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_13, grammarAccess.getPreambleAccess().getWorldviewKeyword_3_1_0());
            	      								
            	    }
            	    // InternalKactors.g:403:9: ( (lv_worldview_14_0= rulePathName ) )
            	    // InternalKactors.g:404:10: (lv_worldview_14_0= rulePathName )
            	    {
            	    // InternalKactors.g:404:10: (lv_worldview_14_0= rulePathName )
            	    // InternalKactors.g:405:11: lv_worldview_14_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_3_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_worldview_14_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_14_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKactors.g:428:4: ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:428:4: ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) )
            	    // InternalKactors.g:429:5: {...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2)");
            	    }
            	    // InternalKactors.g:429:105: ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) )
            	    // InternalKactors.g:430:6: ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2);
            	    // InternalKactors.g:433:9: ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) )
            	    // InternalKactors.g:433:10: {...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:433:19: (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) )
            	    // InternalKactors.g:433:20: otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) )
            	    {
            	    otherlv_15=(Token)match(input,33,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_15, grammarAccess.getPreambleAccess().getObservableKeyword_3_2_0());
            	      								
            	    }
            	    // InternalKactors.g:437:9: ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) )
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==RULE_OBSERVABLE) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==43) ) {
            	        alt10=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // InternalKactors.g:438:10: ( (lv_observable_16_0= RULE_OBSERVABLE ) )
            	            {
            	            // InternalKactors.g:438:10: ( (lv_observable_16_0= RULE_OBSERVABLE ) )
            	            // InternalKactors.g:439:11: (lv_observable_16_0= RULE_OBSERVABLE )
            	            {
            	            // InternalKactors.g:439:11: (lv_observable_16_0= RULE_OBSERVABLE )
            	            // InternalKactors.g:440:12: lv_observable_16_0= RULE_OBSERVABLE
            	            {
            	            lv_observable_16_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_6); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_observable_16_0, grammarAccess.getPreambleAccess().getObservableOBSERVABLETerminalRuleCall_3_2_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"observable",
            	              													lv_observable_16_0,
            	              													"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:457:10: ( (lv_observables_17_0= ruleList ) )
            	            {
            	            // InternalKactors.g:457:10: ( (lv_observables_17_0= ruleList ) )
            	            // InternalKactors.g:458:11: (lv_observables_17_0= ruleList )
            	            {
            	            // InternalKactors.g:458:11: (lv_observables_17_0= ruleList )
            	            // InternalKactors.g:459:12: lv_observables_17_0= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_3_2_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_6);
            	            lv_observables_17_0=ruleList();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	              												}
            	              												set(
            	              													current,
            	              													"observables",
            	              													lv_observables_17_0,
            	              													"org.integratedmodelling.kactors.Kactors.List");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKactors.g:483:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:483:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:484:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3)");
            	    }
            	    // InternalKactors.g:484:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:485:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3);
            	    // InternalKactors.g:488:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:488:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:488:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    // InternalKactors.g:488:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
            	    {
            	    otherlv_18=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_18, grammarAccess.getPreambleAccess().getDescriptionKeyword_3_3_0());
            	      								
            	    }
            	    // InternalKactors.g:492:9: ( (lv_description_19_0= RULE_STRING ) )
            	    // InternalKactors.g:493:10: (lv_description_19_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:493:10: (lv_description_19_0= RULE_STRING )
            	    // InternalKactors.g:494:11: lv_description_19_0= RULE_STRING
            	    {
            	    lv_description_19_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_description_19_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_3_3_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"description",
            	      												lv_description_19_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKactors.g:516:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:516:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:517:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4)");
            	    }
            	    // InternalKactors.g:517:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:518:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4);
            	    // InternalKactors.g:521:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:521:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:521:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    // InternalKactors.g:521:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
            	    {
            	    otherlv_20=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_20, grammarAccess.getPreambleAccess().getPermissionsKeyword_3_4_0());
            	      								
            	    }
            	    // InternalKactors.g:525:9: ( (lv_permissions_21_0= RULE_STRING ) )
            	    // InternalKactors.g:526:10: (lv_permissions_21_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:526:10: (lv_permissions_21_0= RULE_STRING )
            	    // InternalKactors.g:527:11: lv_permissions_21_0= RULE_STRING
            	    {
            	    lv_permissions_21_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_21_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_3_4_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"permissions",
            	      												lv_permissions_21_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:550:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5)");
            	    }
            	    // InternalKactors.g:550:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:551:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5);
            	    // InternalKactors.g:554:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    int cnt11=0;
            	    loop11:
            	    do {
            	        int alt11=2;
            	        int LA11_0 = input.LA(1);

            	        if ( (LA11_0==36) ) {
            	            int LA11_2 = input.LA(2);

            	            if ( ((synpred19_InternalKactors()&&(true))) ) {
            	                alt11=1;
            	            }


            	        }


            	        switch (alt11) {
            	    	case 1 :
            	    	    // InternalKactors.g:554:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:554:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:554:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_22=(Token)match(input,36,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_22, grammarAccess.getPreambleAccess().getAuthorKeyword_3_5_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:558:9: ( (lv_authors_23_0= RULE_STRING ) )
            	    	    // InternalKactors.g:559:10: (lv_authors_23_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:559:10: (lv_authors_23_0= RULE_STRING )
            	    	    // InternalKactors.g:560:11: lv_authors_23_0= RULE_STRING
            	    	    {
            	    	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_23_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_3_5_1_0());
            	    	      										
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElement(grammarAccess.getPreambleRule());
            	    	      											}
            	    	      											addWithLastConsumed(
            	    	      												current,
            	    	      												"authors",
            	    	      												lv_authors_23_0,
            	    	      												"org.eclipse.xtext.common.Terminals.STRING");
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt11 >= 1 ) break loop11;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(11, input);
            	                throw eee;
            	        }
            	        cnt11++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKactors.g:582:4: ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:582:4: ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:583:5: {...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6)");
            	    }
            	    // InternalKactors.g:583:105: ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:584:6: ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6);
            	    // InternalKactors.g:587:9: ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) )
            	    // InternalKactors.g:587:10: {...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:587:19: (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) )
            	    // InternalKactors.g:587:20: otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) )
            	    {
            	    otherlv_24=(Token)match(input,37,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_24, grammarAccess.getPreambleAccess().getStyleKeyword_3_6_0());
            	      								
            	    }
            	    // InternalKactors.g:591:9: ( (lv_style_25_0= rulePathName ) )
            	    // InternalKactors.g:592:10: (lv_style_25_0= rulePathName )
            	    {
            	    // InternalKactors.g:592:10: (lv_style_25_0= rulePathName )
            	    // InternalKactors.g:593:11: lv_style_25_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getStylePathNameParserRuleCall_3_6_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_style_25_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"style",
            	      												lv_style_25_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:616:4: ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:616:4: ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:617:5: {...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7)");
            	    }
            	    // InternalKactors.g:617:105: ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:618:6: ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7);
            	    // InternalKactors.g:621:9: ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:621:10: {...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:621:19: (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:621:20: otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) )
            	    {
            	    otherlv_26=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_26, grammarAccess.getPreambleAccess().getVersionKeyword_3_7_0());
            	      								
            	    }
            	    // InternalKactors.g:625:9: ( (lv_version_27_0= ruleVersionNumber ) )
            	    // InternalKactors.g:626:10: (lv_version_27_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:626:10: (lv_version_27_0= ruleVersionNumber )
            	    // InternalKactors.g:627:11: lv_version_27_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_3_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_version_27_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_27_0,
            	      												"org.integratedmodelling.kactors.Kactors.VersionNumber");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 9 :
            	    // InternalKactors.g:650:4: ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:650:4: ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:651:5: {...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8)");
            	    }
            	    // InternalKactors.g:651:105: ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:652:6: ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8);
            	    // InternalKactors.g:655:9: ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:655:10: {...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:655:19: (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? )
            	    // InternalKactors.g:655:20: otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )?
            	    {
            	    otherlv_28=(Token)match(input,39,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_28, grammarAccess.getPreambleAccess().getCreatedKeyword_3_8_0());
            	      								
            	    }
            	    // InternalKactors.g:659:9: ( (lv_created_29_0= ruleDate ) )
            	    // InternalKactors.g:660:10: (lv_created_29_0= ruleDate )
            	    {
            	    // InternalKactors.g:660:10: (lv_created_29_0= ruleDate )
            	    // InternalKactors.g:661:11: lv_created_29_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_3_8_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_created_29_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"created",
            	      												lv_created_29_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:678:9: ( (lv_createcomment_30_0= RULE_STRING ) )?
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==RULE_STRING) ) {
            	        alt12=1;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // InternalKactors.g:679:10: (lv_createcomment_30_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:679:10: (lv_createcomment_30_0= RULE_STRING )
            	            // InternalKactors.g:680:11: lv_createcomment_30_0= RULE_STRING
            	            {
            	            lv_createcomment_30_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_createcomment_30_0, grammarAccess.getPreambleAccess().getCreatecommentSTRINGTerminalRuleCall_3_8_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"createcomment",
            	              												lv_createcomment_30_0,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 10 :
            	    // InternalKactors.g:702:4: ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:702:4: ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:703:5: {...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9)");
            	    }
            	    // InternalKactors.g:703:105: ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:704:6: ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9);
            	    // InternalKactors.g:707:9: ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:707:10: {...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:707:19: (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? )
            	    // InternalKactors.g:707:20: otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )?
            	    {
            	    otherlv_31=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_31, grammarAccess.getPreambleAccess().getModifiedKeyword_3_9_0());
            	      								
            	    }
            	    // InternalKactors.g:711:9: ( (lv_modified_32_0= ruleDate ) )
            	    // InternalKactors.g:712:10: (lv_modified_32_0= ruleDate )
            	    {
            	    // InternalKactors.g:712:10: (lv_modified_32_0= ruleDate )
            	    // InternalKactors.g:713:11: lv_modified_32_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_3_9_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_modified_32_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"modified",
            	      												lv_modified_32_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:730:9: ( (lv_modcomment_33_0= RULE_STRING ) )?
            	    int alt13=2;
            	    int LA13_0 = input.LA(1);

            	    if ( (LA13_0==RULE_STRING) ) {
            	        alt13=1;
            	    }
            	    switch (alt13) {
            	        case 1 :
            	            // InternalKactors.g:731:10: (lv_modcomment_33_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:731:10: (lv_modcomment_33_0= RULE_STRING )
            	            // InternalKactors.g:732:11: lv_modcomment_33_0= RULE_STRING
            	            {
            	            lv_modcomment_33_0=(Token)match(input,RULE_STRING,FOLLOW_6); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_modcomment_33_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_3_9_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"modcomment",
            	              												lv_modcomment_33_0,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getPreambleAccess().getUnorderedGroup_3());

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
    // $ANTLR end "rulePreamble"


    // $ANTLR start "entryRuleDefinition"
    // InternalKactors.g:768:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:768:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:769:2: iv_ruleDefinition= ruleDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDefinition=ruleDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDefinition; 
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
    // $ANTLR end "entryRuleDefinition"


    // $ANTLR start "ruleDefinition"
    // InternalKactors.g:775:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) ;
    public final EObject ruleDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_arguments_3_0 = null;

        EObject lv_body_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:781:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) )
            // InternalKactors.g:782:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            {
            // InternalKactors.g:782:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            // InternalKactors.g:783:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) )
            {
            // InternalKactors.g:783:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ANNOTATION_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalKactors.g:784:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:784:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:785:5: lv_annotations_0_0= ruleAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDefinitionAccess().getAnnotationsAnnotationParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_annotations_0_0=ruleAnnotation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getDefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"annotations",
            	      						lv_annotations_0_0,
            	      						"org.integratedmodelling.kactors.Kactors.Annotation");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            otherlv_1=(Token)match(input,41,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getActionKeyword_1());
              		
            }
            // InternalKactors.g:806:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:807:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:807:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:808:5: lv_name_2_0= RULE_LOWERCASE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:824:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==43) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalKactors.g:825:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:825:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:826:5: lv_arguments_3_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_13);
                    lv_arguments_3_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getDefinitionRule());
                      					}
                      					set(
                      						current,
                      						"arguments",
                      						lv_arguments_3_0,
                      						"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,42,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:847:3: ( (lv_body_5_0= ruleMessageBody ) )
            // InternalKactors.g:848:4: (lv_body_5_0= ruleMessageBody )
            {
            // InternalKactors.g:848:4: (lv_body_5_0= ruleMessageBody )
            // InternalKactors.g:849:5: lv_body_5_0= ruleMessageBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDefinitionAccess().getBodyMessageBodyParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_5_0=ruleMessageBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDefinitionRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_5_0,
              						"org.integratedmodelling.kactors.Kactors.MessageBody");
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
    // $ANTLR end "ruleDefinition"


    // $ANTLR start "entryRuleArgumentDeclaration"
    // InternalKactors.g:870:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:870:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:871:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgumentDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArgumentDeclaration=ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArgumentDeclaration; 
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
    // $ANTLR end "entryRuleArgumentDeclaration"


    // $ANTLR start "ruleArgumentDeclaration"
    // InternalKactors.g:877:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:883:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:884:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:884:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:885:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:885:3: ()
            // InternalKactors.g:886:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getArgumentDeclarationAccess().getArgumentDeclarationAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,43,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:899:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_LOWERCASE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalKactors.g:900:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:900:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:901:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:901:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:902:6: lv_ids_2_0= RULE_LOWERCASE_ID
                    {
                    lv_ids_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_ids_2_0, grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getArgumentDeclarationRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"ids",
                      							lv_ids_2_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:918:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==31) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalKactors.g:919:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:923:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:924:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:924:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:925:7: lv_ids_4_0= RULE_LOWERCASE_ID
                    	    {
                    	    lv_ids_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_16); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							newLeafNode(lv_ids_4_0, grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElement(grammarAccess.getArgumentDeclarationRule());
                    	      							}
                    	      							addWithLastConsumed(
                    	      								current,
                    	      								"ids",
                    	      								lv_ids_4_0,
                    	      								"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getArgumentDeclarationAccess().getRightParenthesisKeyword_3());
              		
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
    // $ANTLR end "ruleArgumentDeclaration"


    // $ANTLR start "entryRuleMessageBody"
    // InternalKactors.g:951:1: entryRuleMessageBody returns [EObject current=null] : iv_ruleMessageBody= ruleMessageBody EOF ;
    public final EObject entryRuleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageBody = null;


        try {
            // InternalKactors.g:951:52: (iv_ruleMessageBody= ruleMessageBody EOF )
            // InternalKactors.g:952:2: iv_ruleMessageBody= ruleMessageBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageBody=ruleMessageBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageBody; 
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
    // $ANTLR end "entryRuleMessageBody"


    // $ANTLR start "ruleMessageBody"
    // InternalKactors.g:958:1: ruleMessageBody returns [EObject current=null] : ( () ( (lv_lists_1_0= ruleStatementList ) )* ) ;
    public final EObject ruleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject lv_lists_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:964:2: ( ( () ( (lv_lists_1_0= ruleStatementList ) )* ) )
            // InternalKactors.g:965:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            {
            // InternalKactors.g:965:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            // InternalKactors.g:966:3: () ( (lv_lists_1_0= ruleStatementList ) )*
            {
            // InternalKactors.g:966:3: ()
            // InternalKactors.g:967:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getMessageBodyAccess().getMessageBodyAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:976:3: ( (lv_lists_1_0= ruleStatementList ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=RULE_STRING && LA19_0<=RULE_ARGVALUE)||LA19_0==RULE_INT||LA19_0==43||(LA19_0>=45 && LA19_0<=46)||(LA19_0>=48 && LA19_0<=50)||(LA19_0>=53 && LA19_0<=54)||LA19_0==58||LA19_0==61||LA19_0==67||(LA19_0>=80 && LA19_0<=81)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalKactors.g:977:4: (lv_lists_1_0= ruleStatementList )
            	    {
            	    // InternalKactors.g:977:4: (lv_lists_1_0= ruleStatementList )
            	    // InternalKactors.g:978:5: lv_lists_1_0= ruleStatementList
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getMessageBodyAccess().getListsStatementListParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_lists_1_0=ruleStatementList();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getMessageBodyRule());
            	      					}
            	      					add(
            	      						current,
            	      						"lists",
            	      						lv_lists_1_0,
            	      						"org.integratedmodelling.kactors.Kactors.StatementList");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
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
    // $ANTLR end "ruleMessageBody"


    // $ANTLR start "entryRuleMessageCall"
    // InternalKactors.g:999:1: entryRuleMessageCall returns [EObject current=null] : iv_ruleMessageCall= ruleMessageCall EOF ;
    public final EObject entryRuleMessageCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageCall = null;


        try {
            // InternalKactors.g:999:52: (iv_ruleMessageCall= ruleMessageCall EOF )
            // InternalKactors.g:1000:2: iv_ruleMessageCall= ruleMessageCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageCall=ruleMessageCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageCall; 
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
    // $ANTLR end "entryRuleMessageCall"


    // $ANTLR start "ruleMessageCall"
    // InternalKactors.g:1006:1: ruleMessageCall returns [EObject current=null] : ( ( ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) ;
    public final EObject ruleMessageCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_group_4_0 = null;

        EObject lv_actions_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1012:2: ( ( ( ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) )
            // InternalKactors.g:1013:2: ( ( ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            {
            // InternalKactors.g:1013:2: ( ( ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            // InternalKactors.g:1014:3: ( ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            {
            // InternalKactors.g:1014:3: ( ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_LOWERCASE_ID||LA22_0==RULE_ARGVALUE) ) {
                alt22=1;
            }
            else if ( (LA22_0==43) ) {
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
                    // InternalKactors.g:1015:4: ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    {
                    // InternalKactors.g:1015:4: ( ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    // InternalKactors.g:1016:5: ( (lv_name_0_0= ruleArgPathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    {
                    // InternalKactors.g:1016:5: ( (lv_name_0_0= ruleArgPathName ) )
                    // InternalKactors.g:1017:6: (lv_name_0_0= ruleArgPathName )
                    {
                    // InternalKactors.g:1017:6: (lv_name_0_0= ruleArgPathName )
                    // InternalKactors.g:1018:7: lv_name_0_0= ruleArgPathName
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getMessageCallAccess().getNameArgPathNameParserRuleCall_0_0_0_0());
                      						
                    }
                    pushFollow(FOLLOW_18);
                    lv_name_0_0=ruleArgPathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getMessageCallRule());
                      							}
                      							set(
                      								current,
                      								"name",
                      								lv_name_0_0,
                      								"org.integratedmodelling.kactors.Kactors.ArgPathName");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    // InternalKactors.g:1035:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    int alt21=2;
                    alt21 = dfa21.predict(input);
                    switch (alt21) {
                        case 1 :
                            // InternalKactors.g:1036:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                            {
                            otherlv_1=(Token)match(input,43,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getMessageCallAccess().getLeftParenthesisKeyword_0_0_1_0());
                              					
                            }
                            // InternalKactors.g:1040:6: ( (lv_parameters_2_0= ruleParameterList ) )?
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( ((LA20_0>=RULE_STRING && LA20_0<=RULE_LOWERCASE_ID)||(LA20_0>=RULE_EXPR && LA20_0<=RULE_ARGVALUE)||LA20_0==RULE_INT||LA20_0==43||(LA20_0>=53 && LA20_0<=54)||LA20_0==58||LA20_0==61||LA20_0==67||(LA20_0>=80 && LA20_0<=81)) ) {
                                alt20=1;
                            }
                            switch (alt20) {
                                case 1 :
                                    // InternalKactors.g:1041:7: (lv_parameters_2_0= ruleParameterList )
                                    {
                                    // InternalKactors.g:1041:7: (lv_parameters_2_0= ruleParameterList )
                                    // InternalKactors.g:1042:8: lv_parameters_2_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getMessageCallAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_20);
                                    lv_parameters_2_0=ruleParameterList();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElementForParent(grammarAccess.getMessageCallRule());
                                      								}
                                      								set(
                                      									current,
                                      									"parameters",
                                      									lv_parameters_2_0,
                                      									"org.integratedmodelling.kactors.Kactors.ParameterList");
                                      								afterParserOrEnumRuleCall();
                                      							
                                    }

                                    }


                                    }
                                    break;

                            }

                            otherlv_3=(Token)match(input,44,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getMessageCallAccess().getRightParenthesisKeyword_0_0_1_2());
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1066:4: ( (lv_group_4_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1066:4: ( (lv_group_4_0= ruleStatementGroup ) )
                    // InternalKactors.g:1067:5: (lv_group_4_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1067:5: (lv_group_4_0= ruleStatementGroup )
                    // InternalKactors.g:1068:6: lv_group_4_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMessageCallAccess().getGroupStatementGroupParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
                    lv_group_4_0=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMessageCallRule());
                      						}
                      						set(
                      							current,
                      							"group",
                      							lv_group_4_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementGroup");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:1086:3: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==42) ) {
                int LA23_1 = input.LA(2);

                if ( (synpred35_InternalKactors()) ) {
                    alt23=1;
                }
            }
            switch (alt23) {
                case 1 :
                    // InternalKactors.g:1087:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,42,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getMessageCallAccess().getColonKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1091:4: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:1092:5: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:1092:5: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:1093:6: lv_actions_6_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMessageCallAccess().getActionsActionsParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_actions_6_0=ruleActions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMessageCallRule());
                      						}
                      						set(
                      							current,
                      							"actions",
                      							lv_actions_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Actions");
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
    // $ANTLR end "ruleMessageCall"


    // $ANTLR start "entryRuleStatementGroup"
    // InternalKactors.g:1115:1: entryRuleStatementGroup returns [EObject current=null] : iv_ruleStatementGroup= ruleStatementGroup EOF ;
    public final EObject entryRuleStatementGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementGroup = null;


        try {
            // InternalKactors.g:1115:55: (iv_ruleStatementGroup= ruleStatementGroup EOF )
            // InternalKactors.g:1116:2: iv_ruleStatementGroup= ruleStatementGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatementGroup=ruleStatementGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatementGroup; 
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
    // $ANTLR end "entryRuleStatementGroup"


    // $ANTLR start "ruleStatementGroup"
    // InternalKactors.g:1122:1: ruleStatementGroup returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ( (lv_metadata_4_0= ruleMetadata ) )? (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) ;
    public final EObject ruleStatementGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_body_2_0 = null;

        EObject lv_metadata_4_0 = null;

        EObject lv_actions_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1128:2: ( ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ( (lv_metadata_4_0= ruleMetadata ) )? (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) )
            // InternalKactors.g:1129:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ( (lv_metadata_4_0= ruleMetadata ) )? (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            {
            // InternalKactors.g:1129:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ( (lv_metadata_4_0= ruleMetadata ) )? (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            // InternalKactors.g:1130:3: () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ( (lv_metadata_4_0= ruleMetadata ) )? (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            {
            // InternalKactors.g:1130:3: ()
            // InternalKactors.g:1131:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getStatementGroupAccess().getStatementGroupAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,43,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStatementGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1144:3: ( (lv_body_2_0= ruleMessageBody ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=RULE_STRING && LA24_0<=RULE_ARGVALUE)||LA24_0==RULE_INT||LA24_0==43||(LA24_0>=45 && LA24_0<=46)||(LA24_0>=48 && LA24_0<=50)||(LA24_0>=53 && LA24_0<=54)||LA24_0==58||LA24_0==61||LA24_0==67||(LA24_0>=80 && LA24_0<=81)) ) {
                alt24=1;
            }
            else if ( (LA24_0==44) ) {
                int LA24_2 = input.LA(2);

                if ( (synpred36_InternalKactors()) ) {
                    alt24=1;
                }
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1145:4: (lv_body_2_0= ruleMessageBody )
                    {
                    // InternalKactors.g:1145:4: (lv_body_2_0= ruleMessageBody )
                    // InternalKactors.g:1146:5: lv_body_2_0= ruleMessageBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementGroupAccess().getBodyMessageBodyParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_20);
                    lv_body_2_0=ruleMessageBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementGroupRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_2_0,
                      						"org.integratedmodelling.kactors.Kactors.MessageBody");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,44,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getStatementGroupAccess().getRightParenthesisKeyword_3());
              		
            }
            // InternalKactors.g:1167:3: ( (lv_metadata_4_0= ruleMetadata ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==42) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==RULE_LOWERCASE_ID) ) {
                    int LA25_4 = input.LA(3);

                    if ( (synpred37_InternalKactors()) ) {
                        alt25=1;
                    }
                }
            }
            else if ( (LA25_0==73) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalKactors.g:1168:4: (lv_metadata_4_0= ruleMetadata )
                    {
                    // InternalKactors.g:1168:4: (lv_metadata_4_0= ruleMetadata )
                    // InternalKactors.g:1169:5: lv_metadata_4_0= ruleMetadata
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementGroupAccess().getMetadataMetadataParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_21);
                    lv_metadata_4_0=ruleMetadata();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementGroupRule());
                      					}
                      					set(
                      						current,
                      						"metadata",
                      						lv_metadata_4_0,
                      						"org.integratedmodelling.kactors.Kactors.Metadata");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:1186:3: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            int alt26=2;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1187:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,42,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getStatementGroupAccess().getColonKeyword_5_0());
                      			
                    }
                    // InternalKactors.g:1191:4: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:1192:5: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:1192:5: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:1193:6: lv_actions_6_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementGroupAccess().getActionsActionsParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_actions_6_0=ruleActions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementGroupRule());
                      						}
                      						set(
                      							current,
                      							"actions",
                      							lv_actions_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Actions");
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
    // $ANTLR end "ruleStatementGroup"


    // $ANTLR start "entryRuleMetadata"
    // InternalKactors.g:1215:1: entryRuleMetadata returns [EObject current=null] : iv_ruleMetadata= ruleMetadata EOF ;
    public final EObject entryRuleMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadata = null;


        try {
            // InternalKactors.g:1215:49: (iv_ruleMetadata= ruleMetadata EOF )
            // InternalKactors.g:1216:2: iv_ruleMetadata= ruleMetadata EOF
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
    // InternalKactors.g:1222:1: ruleMetadata returns [EObject current=null] : ( (lv_pairs_0_0= ruleMetadataPair ) )+ ;
    public final EObject ruleMetadata() throws RecognitionException {
        EObject current = null;

        EObject lv_pairs_0_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1228:2: ( ( (lv_pairs_0_0= ruleMetadataPair ) )+ )
            // InternalKactors.g:1229:2: ( (lv_pairs_0_0= ruleMetadataPair ) )+
            {
            // InternalKactors.g:1229:2: ( (lv_pairs_0_0= ruleMetadataPair ) )+
            int cnt27=0;
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==42) ) {
                    int LA27_1 = input.LA(2);

                    if ( (LA27_1==RULE_LOWERCASE_ID) ) {
                        int LA27_4 = input.LA(3);

                        if ( (synpred39_InternalKactors()) ) {
                            alt27=1;
                        }


                    }


                }
                else if ( (LA27_0==73) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalKactors.g:1230:3: (lv_pairs_0_0= ruleMetadataPair )
            	    {
            	    // InternalKactors.g:1230:3: (lv_pairs_0_0= ruleMetadataPair )
            	    // InternalKactors.g:1231:4: lv_pairs_0_0= ruleMetadataPair
            	    {
            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getMetadataAccess().getPairsMetadataPairParserRuleCall_0());
            	      			
            	    }
            	    pushFollow(FOLLOW_23);
            	    lv_pairs_0_0=ruleMetadataPair();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				if (current==null) {
            	      					current = createModelElementForParent(grammarAccess.getMetadataRule());
            	      				}
            	      				add(
            	      					current,
            	      					"pairs",
            	      					lv_pairs_0_0,
            	      					"org.integratedmodelling.kactors.Kactors.MetadataPair");
            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt27 >= 1 ) break loop27;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(27, input);
                        throw eee;
                }
                cnt27++;
            } while (true);


            }

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


    // $ANTLR start "entryRuleMetadataPair"
    // InternalKactors.g:1251:1: entryRuleMetadataPair returns [EObject current=null] : iv_ruleMetadataPair= ruleMetadataPair EOF ;
    public final EObject entryRuleMetadataPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadataPair = null;


        try {
            // InternalKactors.g:1251:53: (iv_ruleMetadataPair= ruleMetadataPair EOF )
            // InternalKactors.g:1252:2: iv_ruleMetadataPair= ruleMetadataPair EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMetadataPairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMetadataPair=ruleMetadataPair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMetadataPair; 
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
    // $ANTLR end "entryRuleMetadataPair"


    // $ANTLR start "ruleMetadataPair"
    // InternalKactors.g:1258:1: ruleMetadataPair returns [EObject current=null] : ( ( (lv_key_0_0= ruleMetadataKey ) ) ( (lv_value_1_0= ruleValue ) )? ) ;
    public final EObject ruleMetadataPair() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1264:2: ( ( ( (lv_key_0_0= ruleMetadataKey ) ) ( (lv_value_1_0= ruleValue ) )? ) )
            // InternalKactors.g:1265:2: ( ( (lv_key_0_0= ruleMetadataKey ) ) ( (lv_value_1_0= ruleValue ) )? )
            {
            // InternalKactors.g:1265:2: ( ( (lv_key_0_0= ruleMetadataKey ) ) ( (lv_value_1_0= ruleValue ) )? )
            // InternalKactors.g:1266:3: ( (lv_key_0_0= ruleMetadataKey ) ) ( (lv_value_1_0= ruleValue ) )?
            {
            // InternalKactors.g:1266:3: ( (lv_key_0_0= ruleMetadataKey ) )
            // InternalKactors.g:1267:4: (lv_key_0_0= ruleMetadataKey )
            {
            // InternalKactors.g:1267:4: (lv_key_0_0= ruleMetadataKey )
            // InternalKactors.g:1268:5: lv_key_0_0= ruleMetadataKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMetadataPairAccess().getKeyMetadataKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_key_0_0=ruleMetadataKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMetadataPairRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"org.integratedmodelling.kactors.Kactors.MetadataKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:1285:3: ( (lv_value_1_0= ruleValue ) )?
            int alt28=2;
            alt28 = dfa28.predict(input);
            switch (alt28) {
                case 1 :
                    // InternalKactors.g:1286:4: (lv_value_1_0= ruleValue )
                    {
                    // InternalKactors.g:1286:4: (lv_value_1_0= ruleValue )
                    // InternalKactors.g:1287:5: lv_value_1_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getMetadataPairAccess().getValueValueParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_1_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getMetadataPairRule());
                      					}
                      					set(
                      						current,
                      						"value",
                      						lv_value_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Value");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleMetadataPair"


    // $ANTLR start "entryRuleStatementList"
    // InternalKactors.g:1308:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalKactors.g:1308:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalKactors.g:1309:2: iv_ruleStatementList= ruleStatementList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatementList=ruleStatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatementList; 
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
    // $ANTLR end "entryRuleStatementList"


    // $ANTLR start "ruleStatementList"
    // InternalKactors.g:1315:1: ruleStatementList returns [EObject current=null] : ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        EObject lv_next_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1321:2: ( ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) )
            // InternalKactors.g:1322:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            {
            // InternalKactors.g:1322:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            // InternalKactors.g:1323:3: ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )*
            {
            // InternalKactors.g:1323:3: ( (lv_first_0_0= ruleStatement ) )
            // InternalKactors.g:1324:4: (lv_first_0_0= ruleStatement )
            {
            // InternalKactors.g:1324:4: (lv_first_0_0= ruleStatement )
            // InternalKactors.g:1325:5: lv_first_0_0= ruleStatement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStatementListAccess().getFirstStatementParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
            lv_first_0_0=ruleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStatementListRule());
              					}
              					set(
              						current,
              						"first",
              						lv_first_0_0,
              						"org.integratedmodelling.kactors.Kactors.Statement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:1342:3: ( (lv_next_1_0= ruleNextStatement ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==31) ) {
                    int LA29_2 = input.LA(2);

                    if ( (synpred41_InternalKactors()) ) {
                        alt29=1;
                    }


                }


                switch (alt29) {
            	case 1 :
            	    // InternalKactors.g:1343:4: (lv_next_1_0= ruleNextStatement )
            	    {
            	    // InternalKactors.g:1343:4: (lv_next_1_0= ruleNextStatement )
            	    // InternalKactors.g:1344:5: lv_next_1_0= ruleNextStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getStatementListAccess().getNextNextStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_24);
            	    lv_next_1_0=ruleNextStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getStatementListRule());
            	      					}
            	      					add(
            	      						current,
            	      						"next",
            	      						lv_next_1_0,
            	      						"org.integratedmodelling.kactors.Kactors.NextStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
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
    // $ANTLR end "ruleStatementList"


    // $ANTLR start "entryRuleStatement"
    // InternalKactors.g:1365:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:1365:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:1366:2: iv_ruleStatement= ruleStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatement=ruleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatement; 
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
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalKactors.g:1372:1: ruleStatement returns [EObject current=null] : ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_3_0=null;
        EObject lv_assignment_0_0 = null;

        EObject lv_group_1_0 = null;

        EObject lv_verb_2_0 = null;

        EObject lv_if_4_0 = null;

        EObject lv_while_5_0 = null;

        EObject lv_do_6_0 = null;

        EObject lv_for_7_0 = null;

        EObject lv_value_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1378:2: ( ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            // InternalKactors.g:1379:2: ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            {
            // InternalKactors.g:1379:2: ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            int alt30=9;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1380:3: ( (lv_assignment_0_0= ruleAssignment ) )
                    {
                    // InternalKactors.g:1380:3: ( (lv_assignment_0_0= ruleAssignment ) )
                    // InternalKactors.g:1381:4: (lv_assignment_0_0= ruleAssignment )
                    {
                    // InternalKactors.g:1381:4: (lv_assignment_0_0= ruleAssignment )
                    // InternalKactors.g:1382:5: lv_assignment_0_0= ruleAssignment
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getAssignmentAssignmentParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_assignment_0_0=ruleAssignment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"assignment",
                      						lv_assignment_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Assignment");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1400:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1400:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    // InternalKactors.g:1401:4: (lv_group_1_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1401:4: (lv_group_1_0= ruleStatementGroup )
                    // InternalKactors.g:1402:5: lv_group_1_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementGroupParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_1_0=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"group",
                      						lv_group_1_0,
                      						"org.integratedmodelling.kactors.Kactors.StatementGroup");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1420:3: ( (lv_verb_2_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1420:3: ( (lv_verb_2_0= ruleMessageCall ) )
                    // InternalKactors.g:1421:4: (lv_verb_2_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1421:4: (lv_verb_2_0= ruleMessageCall )
                    // InternalKactors.g:1422:5: lv_verb_2_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getVerbMessageCallParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_2_0=ruleMessageCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"verb",
                      						lv_verb_2_0,
                      						"org.integratedmodelling.kactors.Kactors.MessageCall");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1440:3: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1440:3: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1441:4: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1441:4: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1442:5: lv_text_3_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_3_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_text_3_0, grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getStatementRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"text",
                      						lv_text_3_0,
                      						"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:1459:3: ( (lv_if_4_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1459:3: ( (lv_if_4_0= ruleIfStatement ) )
                    // InternalKactors.g:1460:4: (lv_if_4_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1460:4: (lv_if_4_0= ruleIfStatement )
                    // InternalKactors.g:1461:5: lv_if_4_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_4_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"if",
                      						lv_if_4_0,
                      						"org.integratedmodelling.kactors.Kactors.IfStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:1479:3: ( (lv_while_5_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1479:3: ( (lv_while_5_0= ruleWhileStatement ) )
                    // InternalKactors.g:1480:4: (lv_while_5_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1480:4: (lv_while_5_0= ruleWhileStatement )
                    // InternalKactors.g:1481:5: lv_while_5_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getWhileWhileStatementParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_5_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"while",
                      						lv_while_5_0,
                      						"org.integratedmodelling.kactors.Kactors.WhileStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:1499:3: ( (lv_do_6_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1499:3: ( (lv_do_6_0= ruleDoStatement ) )
                    // InternalKactors.g:1500:4: (lv_do_6_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1500:4: (lv_do_6_0= ruleDoStatement )
                    // InternalKactors.g:1501:5: lv_do_6_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getDoDoStatementParserRuleCall_6_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_6_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"do",
                      						lv_do_6_0,
                      						"org.integratedmodelling.kactors.Kactors.DoStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:1519:3: ( (lv_for_7_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1519:3: ( (lv_for_7_0= ruleForStatement ) )
                    // InternalKactors.g:1520:4: (lv_for_7_0= ruleForStatement )
                    {
                    // InternalKactors.g:1520:4: (lv_for_7_0= ruleForStatement )
                    // InternalKactors.g:1521:5: lv_for_7_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getForForStatementParserRuleCall_7_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_7_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"for",
                      						lv_for_7_0,
                      						"org.integratedmodelling.kactors.Kactors.ForStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:1539:3: ( (lv_value_8_0= ruleValue ) )
                    {
                    // InternalKactors.g:1539:3: ( (lv_value_8_0= ruleValue ) )
                    // InternalKactors.g:1540:4: (lv_value_8_0= ruleValue )
                    {
                    // InternalKactors.g:1540:4: (lv_value_8_0= ruleValue )
                    // InternalKactors.g:1541:5: lv_value_8_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getValueValueParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_8_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"value",
                      						lv_value_8_0,
                      						"org.integratedmodelling.kactors.Kactors.Value");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleNextStatement"
    // InternalKactors.g:1562:1: entryRuleNextStatement returns [EObject current=null] : iv_ruleNextStatement= ruleNextStatement EOF ;
    public final EObject entryRuleNextStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNextStatement = null;


        try {
            // InternalKactors.g:1562:54: (iv_ruleNextStatement= ruleNextStatement EOF )
            // InternalKactors.g:1563:2: iv_ruleNextStatement= ruleNextStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNextStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNextStatement=ruleNextStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNextStatement; 
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
    // $ANTLR end "entryRuleNextStatement"


    // $ANTLR start "ruleNextStatement"
    // InternalKactors.g:1569:1: ruleNextStatement returns [EObject current=null] : (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) ) ;
    public final EObject ruleNextStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_text_4_0=null;
        EObject lv_assignment_1_0 = null;

        EObject lv_verb_2_0 = null;

        EObject lv_group_3_0 = null;

        EObject lv_if_5_0 = null;

        EObject lv_while_6_0 = null;

        EObject lv_do_7_0 = null;

        EObject lv_for_8_0 = null;

        EObject lv_value_9_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1575:2: ( (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) ) )
            // InternalKactors.g:1576:2: (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) )
            {
            // InternalKactors.g:1576:2: (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) )
            // InternalKactors.g:1577:3: otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) )
            {
            otherlv_0=(Token)match(input,31,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getNextStatementAccess().getCommaKeyword_0());
              		
            }
            // InternalKactors.g:1581:3: ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) )
            int alt31=9;
            alt31 = dfa31.predict(input);
            switch (alt31) {
                case 1 :
                    // InternalKactors.g:1582:4: ( (lv_assignment_1_0= ruleAssignment ) )
                    {
                    // InternalKactors.g:1582:4: ( (lv_assignment_1_0= ruleAssignment ) )
                    // InternalKactors.g:1583:5: (lv_assignment_1_0= ruleAssignment )
                    {
                    // InternalKactors.g:1583:5: (lv_assignment_1_0= ruleAssignment )
                    // InternalKactors.g:1584:6: lv_assignment_1_0= ruleAssignment
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getAssignmentAssignmentParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_assignment_1_0=ruleAssignment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"assignment",
                      							lv_assignment_1_0,
                      							"org.integratedmodelling.kactors.Kactors.Assignment");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1602:4: ( (lv_verb_2_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1602:4: ( (lv_verb_2_0= ruleMessageCall ) )
                    // InternalKactors.g:1603:5: (lv_verb_2_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1603:5: (lv_verb_2_0= ruleMessageCall )
                    // InternalKactors.g:1604:6: lv_verb_2_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbMessageCallParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_2_0=ruleMessageCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"verb",
                      							lv_verb_2_0,
                      							"org.integratedmodelling.kactors.Kactors.MessageCall");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1622:4: ( (lv_group_3_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1622:4: ( (lv_group_3_0= ruleStatementGroup ) )
                    // InternalKactors.g:1623:5: (lv_group_3_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1623:5: (lv_group_3_0= ruleStatementGroup )
                    // InternalKactors.g:1624:6: lv_group_3_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupStatementGroupParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_3_0=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"group",
                      							lv_group_3_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementGroup");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1642:4: ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1642:4: ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1643:5: (lv_text_4_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1643:5: (lv_text_4_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1644:6: lv_text_4_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_4_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_4_0, grammarAccess.getNextStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_3_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNextStatementRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"text",
                      							lv_text_4_0,
                      							"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:1661:4: ( (lv_if_5_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1661:4: ( (lv_if_5_0= ruleIfStatement ) )
                    // InternalKactors.g:1662:5: (lv_if_5_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1662:5: (lv_if_5_0= ruleIfStatement )
                    // InternalKactors.g:1663:6: lv_if_5_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getIfIfStatementParserRuleCall_1_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_5_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"if",
                      							lv_if_5_0,
                      							"org.integratedmodelling.kactors.Kactors.IfStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:1681:4: ( (lv_while_6_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1681:4: ( (lv_while_6_0= ruleWhileStatement ) )
                    // InternalKactors.g:1682:5: (lv_while_6_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1682:5: (lv_while_6_0= ruleWhileStatement )
                    // InternalKactors.g:1683:6: lv_while_6_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getWhileWhileStatementParserRuleCall_1_5_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_6_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"while",
                      							lv_while_6_0,
                      							"org.integratedmodelling.kactors.Kactors.WhileStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:1701:4: ( (lv_do_7_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1701:4: ( (lv_do_7_0= ruleDoStatement ) )
                    // InternalKactors.g:1702:5: (lv_do_7_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1702:5: (lv_do_7_0= ruleDoStatement )
                    // InternalKactors.g:1703:6: lv_do_7_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getDoDoStatementParserRuleCall_1_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_7_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"do",
                      							lv_do_7_0,
                      							"org.integratedmodelling.kactors.Kactors.DoStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:1721:4: ( (lv_for_8_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1721:4: ( (lv_for_8_0= ruleForStatement ) )
                    // InternalKactors.g:1722:5: (lv_for_8_0= ruleForStatement )
                    {
                    // InternalKactors.g:1722:5: (lv_for_8_0= ruleForStatement )
                    // InternalKactors.g:1723:6: lv_for_8_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getForForStatementParserRuleCall_1_7_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_8_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"for",
                      							lv_for_8_0,
                      							"org.integratedmodelling.kactors.Kactors.ForStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:1741:4: ( (lv_value_9_0= ruleValue ) )
                    {
                    // InternalKactors.g:1741:4: ( (lv_value_9_0= ruleValue ) )
                    // InternalKactors.g:1742:5: (lv_value_9_0= ruleValue )
                    {
                    // InternalKactors.g:1742:5: (lv_value_9_0= ruleValue )
                    // InternalKactors.g:1743:6: lv_value_9_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getValueValueParserRuleCall_1_8_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_9_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_9_0,
                      							"org.integratedmodelling.kactors.Kactors.Value");
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
    // $ANTLR end "ruleNextStatement"


    // $ANTLR start "entryRuleAssignment"
    // InternalKactors.g:1765:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // InternalKactors.g:1765:51: (iv_ruleAssignment= ruleAssignment EOF )
            // InternalKactors.g:1766:2: iv_ruleAssignment= ruleAssignment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssignmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAssignment=ruleAssignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAssignment; 
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
    // $ANTLR end "entryRuleAssignment"


    // $ANTLR start "ruleAssignment"
    // InternalKactors.g:1772:1: ruleAssignment returns [EObject current=null] : (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_variable_1_0=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1778:2: ( (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:1779:2: (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:1779:2: (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:1780:3: otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getAssignmentAccess().getSetKeyword_0());
              		
            }
            // InternalKactors.g:1784:3: ( (lv_variable_1_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:1785:4: (lv_variable_1_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:1785:4: (lv_variable_1_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:1786:5: lv_variable_1_0= RULE_LOWERCASE_ID
            {
            lv_variable_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_variable_1_0, grammarAccess.getAssignmentAccess().getVariableLOWERCASE_IDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getAssignmentRule());
              					}
              					setWithLastConsumed(
              						current,
              						"variable",
              						lv_variable_1_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:1802:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:1803:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:1803:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:1804:5: lv_value_2_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getAssignmentAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getAssignmentRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.integratedmodelling.kactors.Kactors.Value");
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
    // $ANTLR end "ruleAssignment"


    // $ANTLR start "entryRuleIfStatement"
    // InternalKactors.g:1825:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:1825:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:1826:2: iv_ruleIfStatement= ruleIfStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIfStatement=ruleIfStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIfStatement; 
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
    // $ANTLR end "entryRuleIfStatement"


    // $ANTLR start "ruleIfStatement"
    // InternalKactors.g:1832:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) ;
    public final EObject ruleIfStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        Token otherlv_7=null;
        EObject lv_body_2_0 = null;

        EObject lv_elseIfBody_6_0 = null;

        EObject lv_elseCall_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1838:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) )
            // InternalKactors.g:1839:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            {
            // InternalKactors.g:1839:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            // InternalKactors.g:1840:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            {
            otherlv_0=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:1844:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1845:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1845:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1846:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_expression_1_0, grammarAccess.getIfStatementAccess().getExpressionEXPRTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getIfStatementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"expression",
              						lv_expression_1_0,
              						"org.integratedmodelling.kactors.Kactors.EXPR");
              				
            }

            }


            }

            // InternalKactors.g:1862:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1863:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1863:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1864:5: lv_body_2_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getBodyStatementBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_26);
            lv_body_2_0=ruleStatementBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getIfStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_2_0,
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:1881:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==47) ) {
                    int LA32_1 = input.LA(2);

                    if ( (synpred58_InternalKactors()) ) {
                        alt32=1;
                    }


                }


                switch (alt32) {
            	case 1 :
            	    // InternalKactors.g:1882:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    {
            	    otherlv_3=(Token)match(input,47,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:1890:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:1891:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:1891:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:1892:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(lv_elseIfExpression_5_0, grammarAccess.getIfStatementAccess().getElseIfExpressionEXPRTerminalRuleCall_3_2_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElement(grammarAccess.getIfStatementRule());
            	      						}
            	      						addWithLastConsumed(
            	      							current,
            	      							"elseIfExpression",
            	      							lv_elseIfExpression_5_0,
            	      							"org.integratedmodelling.kactors.Kactors.EXPR");
            	      					
            	    }

            	    }


            	    }

            	    // InternalKactors.g:1908:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    // InternalKactors.g:1909:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    {
            	    // InternalKactors.g:1909:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    // InternalKactors.g:1910:6: lv_elseIfBody_6_0= ruleStatementBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfBodyStatementBodyParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_26);
            	    lv_elseIfBody_6_0=ruleStatementBody();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getIfStatementRule());
            	      						}
            	      						add(
            	      							current,
            	      							"elseIfBody",
            	      							lv_elseIfBody_6_0,
            	      							"org.integratedmodelling.kactors.Kactors.StatementBody");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            // InternalKactors.g:1928:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==47) ) {
                int LA33_1 = input.LA(2);

                if ( (synpred59_InternalKactors()) ) {
                    alt33=1;
                }
            }
            switch (alt33) {
                case 1 :
                    // InternalKactors.g:1929:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
                    {
                    otherlv_7=(Token)match(input,47,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:1933:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
                    // InternalKactors.g:1934:5: (lv_elseCall_8_0= ruleStatementBody )
                    {
                    // InternalKactors.g:1934:5: (lv_elseCall_8_0= ruleStatementBody )
                    // InternalKactors.g:1935:6: lv_elseCall_8_0= ruleStatementBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallStatementBodyParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_elseCall_8_0=ruleStatementBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getIfStatementRule());
                      						}
                      						set(
                      							current,
                      							"elseCall",
                      							lv_elseCall_8_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementBody");
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
    // $ANTLR end "ruleIfStatement"


    // $ANTLR start "entryRuleStatementBody"
    // InternalKactors.g:1957:1: entryRuleStatementBody returns [EObject current=null] : iv_ruleStatementBody= ruleStatementBody EOF ;
    public final EObject entryRuleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementBody = null;


        try {
            // InternalKactors.g:1957:54: (iv_ruleStatementBody= ruleStatementBody EOF )
            // InternalKactors.g:1958:2: iv_ruleStatementBody= ruleStatementBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatementBody=ruleStatementBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatementBody; 
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
    // $ANTLR end "entryRuleStatementBody"


    // $ANTLR start "ruleStatementBody"
    // InternalKactors.g:1964:1: ruleStatementBody returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) ;
    public final EObject ruleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject lv_verb_0_0 = null;

        EObject lv_value_1_0 = null;

        EObject lv_group_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1970:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) )
            // InternalKactors.g:1971:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            {
            // InternalKactors.g:1971:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            int alt34=3;
            alt34 = dfa34.predict(input);
            switch (alt34) {
                case 1 :
                    // InternalKactors.g:1972:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1972:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1973:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1973:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1974:5: lv_verb_0_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementBodyAccess().getVerbMessageCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_0_0=ruleMessageCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementBodyRule());
                      					}
                      					set(
                      						current,
                      						"verb",
                      						lv_verb_0_0,
                      						"org.integratedmodelling.kactors.Kactors.MessageCall");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1992:3: ( (lv_value_1_0= ruleValue ) )
                    {
                    // InternalKactors.g:1992:3: ( (lv_value_1_0= ruleValue ) )
                    // InternalKactors.g:1993:4: (lv_value_1_0= ruleValue )
                    {
                    // InternalKactors.g:1993:4: (lv_value_1_0= ruleValue )
                    // InternalKactors.g:1994:5: lv_value_1_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementBodyAccess().getValueValueParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_1_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementBodyRule());
                      					}
                      					set(
                      						current,
                      						"value",
                      						lv_value_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Value");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2012:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:2012:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:2013:4: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:2013:4: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:2014:5: lv_group_2_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementBodyAccess().getGroupStatementGroupParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_2_0=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementBodyRule());
                      					}
                      					set(
                      						current,
                      						"group",
                      						lv_group_2_0,
                      						"org.integratedmodelling.kactors.Kactors.StatementGroup");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleStatementBody"


    // $ANTLR start "entryRuleWhileStatement"
    // InternalKactors.g:2035:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:2035:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:2036:2: iv_ruleWhileStatement= ruleWhileStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWhileStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWhileStatement=ruleWhileStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWhileStatement; 
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
    // $ANTLR end "entryRuleWhileStatement"


    // $ANTLR start "ruleWhileStatement"
    // InternalKactors.g:2042:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2048:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:2049:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:2049:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            // InternalKactors.g:2050:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,48,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:2054:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:2055:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:2055:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:2056:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_expression_1_0, grammarAccess.getWhileStatementAccess().getExpressionEXPRTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getWhileStatementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"expression",
              						lv_expression_1_0,
              						"org.integratedmodelling.kactors.Kactors.EXPR");
              				
            }

            }


            }

            // InternalKactors.g:2072:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:2073:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:2073:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:2074:5: lv_body_2_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getWhileStatementAccess().getBodyStatementBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_2_0=ruleStatementBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getWhileStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_2_0,
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
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
    // $ANTLR end "ruleWhileStatement"


    // $ANTLR start "entryRuleDoStatement"
    // InternalKactors.g:2095:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:2095:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:2096:2: iv_ruleDoStatement= ruleDoStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDoStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDoStatement=ruleDoStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDoStatement; 
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
    // $ANTLR end "entryRuleDoStatement"


    // $ANTLR start "ruleDoStatement"
    // InternalKactors.g:2102:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2108:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:2109:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:2109:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:2110:3: otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,49,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:2114:3: ( (lv_body_1_0= ruleStatementBody ) )
            // InternalKactors.g:2115:4: (lv_body_1_0= ruleStatementBody )
            {
            // InternalKactors.g:2115:4: (lv_body_1_0= ruleStatementBody )
            // InternalKactors.g:2116:5: lv_body_1_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDoStatementAccess().getBodyStatementBodyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_28);
            lv_body_1_0=ruleStatementBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDoStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_1_0,
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,48,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
              		
            }
            // InternalKactors.g:2137:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:2138:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:2138:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:2139:5: lv_expression_3_0= RULE_EXPR
            {
            lv_expression_3_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_expression_3_0, grammarAccess.getDoStatementAccess().getExpressionEXPRTerminalRuleCall_3_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDoStatementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"expression",
              						lv_expression_3_0,
              						"org.integratedmodelling.kactors.Kactors.EXPR");
              				
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
    // $ANTLR end "ruleDoStatement"


    // $ANTLR start "entryRuleForStatement"
    // InternalKactors.g:2159:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:2159:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:2160:2: iv_ruleForStatement= ruleForStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getForStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleForStatement=ruleForStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleForStatement; 
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
    // $ANTLR end "entryRuleForStatement"


    // $ANTLR start "ruleForStatement"
    // InternalKactors.g:2166:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2172:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:2173:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:2173:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            // InternalKactors.g:2174:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,50,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:2178:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_LOWERCASE_ID) ) {
                int LA35_1 = input.LA(2);

                if ( (LA35_1==51) ) {
                    alt35=1;
                }
            }
            switch (alt35) {
                case 1 :
                    // InternalKactors.g:2179:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:2179:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2180:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2180:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2181:6: lv_id_1_0= RULE_LOWERCASE_ID
                    {
                    lv_id_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_29); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_id_1_0, grammarAccess.getForStatementAccess().getIdLOWERCASE_IDTerminalRuleCall_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getForStatementRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"id",
                      							lv_id_1_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_2=(Token)match(input,51,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2202:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:2203:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:2203:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:2204:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_14);
            lv_value_3_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getForStatementRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_3_0,
              						"org.integratedmodelling.kactors.Kactors.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:2221:3: ( (lv_body_4_0= ruleStatementBody ) )
            // InternalKactors.g:2222:4: (lv_body_4_0= ruleStatementBody )
            {
            // InternalKactors.g:2222:4: (lv_body_4_0= ruleStatementBody )
            // InternalKactors.g:2223:5: lv_body_4_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getBodyStatementBodyParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_4_0=ruleStatementBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getForStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_4_0,
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
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
    // $ANTLR end "ruleForStatement"


    // $ANTLR start "entryRuleActions"
    // InternalKactors.g:2244:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:2244:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:2245:2: iv_ruleActions= ruleActions EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActions=ruleActions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActions; 
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
    // $ANTLR end "entryRuleActions"


    // $ANTLR start "ruleActions"
    // InternalKactors.g:2251:1: ruleActions returns [EObject current=null] : ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_match_0_0 = null;

        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;

        EObject lv_statement_5_0 = null;

        EObject lv_statements_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2257:2: ( ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) )
            // InternalKactors.g:2258:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            {
            // InternalKactors.g:2258:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            int alt37=4;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:2259:3: ( (lv_match_0_0= ruleMatch ) )
                    {
                    // InternalKactors.g:2259:3: ( (lv_match_0_0= ruleMatch ) )
                    // InternalKactors.g:2260:4: (lv_match_0_0= ruleMatch )
                    {
                    // InternalKactors.g:2260:4: (lv_match_0_0= ruleMatch )
                    // InternalKactors.g:2261:5: lv_match_0_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_match_0_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"match",
                      						lv_match_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Match");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2279:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    {
                    // InternalKactors.g:2279:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    // InternalKactors.g:2280:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2284:4: ( (lv_matches_2_0= ruleMatch ) )
                    // InternalKactors.g:2285:5: (lv_matches_2_0= ruleMatch )
                    {
                    // InternalKactors.g:2285:5: (lv_matches_2_0= ruleMatch )
                    // InternalKactors.g:2286:6: lv_matches_2_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
                    lv_matches_2_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"matches",
                      							lv_matches_2_0,
                      							"org.integratedmodelling.kactors.Kactors.Match");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2303:4: ( (lv_matches_3_0= ruleMatch ) )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( ((LA36_0>=RULE_STRING && LA36_0<=RULE_LOWERCASE_ID)||LA36_0==RULE_EXPR||(LA36_0>=RULE_CAMELCASE_ID && LA36_0<=RULE_INT)||LA36_0==43||LA36_0==51||(LA36_0>=53 && LA36_0<=57)||(LA36_0>=80 && LA36_0<=81)) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalKactors.g:2304:5: (lv_matches_3_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:2304:5: (lv_matches_3_0= ruleMatch )
                    	    // InternalKactors.g:2305:6: lv_matches_3_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_31);
                    	    lv_matches_3_0=ruleMatch();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"matches",
                    	      							lv_matches_3_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Match");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getActionsAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2328:3: ( (lv_statement_5_0= ruleStatement ) )
                    {
                    // InternalKactors.g:2328:3: ( (lv_statement_5_0= ruleStatement ) )
                    // InternalKactors.g:2329:4: (lv_statement_5_0= ruleStatement )
                    {
                    // InternalKactors.g:2329:4: (lv_statement_5_0= ruleStatement )
                    // InternalKactors.g:2330:5: lv_statement_5_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getStatementStatementParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_statement_5_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"statement",
                      						lv_statement_5_0,
                      						"org.integratedmodelling.kactors.Kactors.Statement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:2348:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    {
                    // InternalKactors.g:2348:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    // InternalKactors.g:2349:4: otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,43,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:2353:4: ( (lv_statements_7_0= ruleStatementList ) )
                    // InternalKactors.g:2354:5: (lv_statements_7_0= ruleStatementList )
                    {
                    // InternalKactors.g:2354:5: (lv_statements_7_0= ruleStatementList )
                    // InternalKactors.g:2355:6: lv_statements_7_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getStatementsStatementListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
                    lv_statements_7_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						set(
                      							current,
                      							"statements",
                      							lv_statements_7_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_8=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_2());
                      			
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
    // $ANTLR end "ruleActions"


    // $ANTLR start "entryRuleValue"
    // InternalKactors.g:2381:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:2381:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:2382:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:2388:1: ruleValue returns [EObject current=null] : ( ( (lv_tree_0_0= ruleTree ) ) | ( (lv_argvalue_1_0= RULE_ARGVALUE ) ) | ( (lv_literal_2_0= ruleLiteral ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_id_4_0= rulePathName ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_map_6_0= ruleMap ) ) | ( (lv_observable_7_0= RULE_OBSERVABLE ) ) | ( (lv_expression_8_0= RULE_EXPR ) ) | ( (lv_table_9_0= ruleLookupTable ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_argvalue_1_0=null;
        Token lv_observable_7_0=null;
        Token lv_expression_8_0=null;
        EObject lv_tree_0_0 = null;

        EObject lv_literal_2_0 = null;

        AntlrDatatypeRuleToken lv_urn_3_0 = null;

        AntlrDatatypeRuleToken lv_id_4_0 = null;

        EObject lv_list_5_0 = null;

        EObject lv_map_6_0 = null;

        EObject lv_table_9_0 = null;

        EObject lv_quantity_10_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2394:2: ( ( ( (lv_tree_0_0= ruleTree ) ) | ( (lv_argvalue_1_0= RULE_ARGVALUE ) ) | ( (lv_literal_2_0= ruleLiteral ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_id_4_0= rulePathName ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_map_6_0= ruleMap ) ) | ( (lv_observable_7_0= RULE_OBSERVABLE ) ) | ( (lv_expression_8_0= RULE_EXPR ) ) | ( (lv_table_9_0= ruleLookupTable ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) ) )
            // InternalKactors.g:2395:2: ( ( (lv_tree_0_0= ruleTree ) ) | ( (lv_argvalue_1_0= RULE_ARGVALUE ) ) | ( (lv_literal_2_0= ruleLiteral ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_id_4_0= rulePathName ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_map_6_0= ruleMap ) ) | ( (lv_observable_7_0= RULE_OBSERVABLE ) ) | ( (lv_expression_8_0= RULE_EXPR ) ) | ( (lv_table_9_0= ruleLookupTable ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) )
            {
            // InternalKactors.g:2395:2: ( ( (lv_tree_0_0= ruleTree ) ) | ( (lv_argvalue_1_0= RULE_ARGVALUE ) ) | ( (lv_literal_2_0= ruleLiteral ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_id_4_0= rulePathName ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_map_6_0= ruleMap ) ) | ( (lv_observable_7_0= RULE_OBSERVABLE ) ) | ( (lv_expression_8_0= RULE_EXPR ) ) | ( (lv_table_9_0= ruleLookupTable ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) )
            int alt38=11;
            alt38 = dfa38.predict(input);
            switch (alt38) {
                case 1 :
                    // InternalKactors.g:2396:3: ( (lv_tree_0_0= ruleTree ) )
                    {
                    // InternalKactors.g:2396:3: ( (lv_tree_0_0= ruleTree ) )
                    // InternalKactors.g:2397:4: (lv_tree_0_0= ruleTree )
                    {
                    // InternalKactors.g:2397:4: (lv_tree_0_0= ruleTree )
                    // InternalKactors.g:2398:5: lv_tree_0_0= ruleTree
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getTreeTreeParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_tree_0_0=ruleTree();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"tree",
                      						lv_tree_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Tree");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2416:3: ( (lv_argvalue_1_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:2416:3: ( (lv_argvalue_1_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:2417:4: (lv_argvalue_1_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:2417:4: (lv_argvalue_1_0= RULE_ARGVALUE )
                    // InternalKactors.g:2418:5: lv_argvalue_1_0= RULE_ARGVALUE
                    {
                    lv_argvalue_1_0=(Token)match(input,RULE_ARGVALUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_argvalue_1_0, grammarAccess.getValueAccess().getArgvalueARGVALUETerminalRuleCall_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"argvalue",
                      						lv_argvalue_1_0,
                      						"org.integratedmodelling.kactors.Kactors.ARGVALUE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2435:3: ( (lv_literal_2_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:2435:3: ( (lv_literal_2_0= ruleLiteral ) )
                    // InternalKactors.g:2436:4: (lv_literal_2_0= ruleLiteral )
                    {
                    // InternalKactors.g:2436:4: (lv_literal_2_0= ruleLiteral )
                    // InternalKactors.g:2437:5: lv_literal_2_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_literal_2_0=ruleLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"literal",
                      						lv_literal_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Literal");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:2455:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:2455:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:2456:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:2456:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:2457:5: lv_urn_3_0= ruleUrnId
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnIdParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_urn_3_0=ruleUrnId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"urn",
                      						lv_urn_3_0,
                      						"org.integratedmodelling.kactors.Kactors.UrnId");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:2475:3: ( (lv_id_4_0= rulePathName ) )
                    {
                    // InternalKactors.g:2475:3: ( (lv_id_4_0= rulePathName ) )
                    // InternalKactors.g:2476:4: (lv_id_4_0= rulePathName )
                    {
                    // InternalKactors.g:2476:4: (lv_id_4_0= rulePathName )
                    // InternalKactors.g:2477:5: lv_id_4_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getIdPathNameParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_id_4_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"id",
                      						lv_id_4_0,
                      						"org.integratedmodelling.kactors.Kactors.PathName");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:2495:3: ( (lv_list_5_0= ruleList ) )
                    {
                    // InternalKactors.g:2495:3: ( (lv_list_5_0= ruleList ) )
                    // InternalKactors.g:2496:4: (lv_list_5_0= ruleList )
                    {
                    // InternalKactors.g:2496:4: (lv_list_5_0= ruleList )
                    // InternalKactors.g:2497:5: lv_list_5_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_5_0());
                      				
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
                      						"org.integratedmodelling.kactors.Kactors.List");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:2515:3: ( (lv_map_6_0= ruleMap ) )
                    {
                    // InternalKactors.g:2515:3: ( (lv_map_6_0= ruleMap ) )
                    // InternalKactors.g:2516:4: (lv_map_6_0= ruleMap )
                    {
                    // InternalKactors.g:2516:4: (lv_map_6_0= ruleMap )
                    // InternalKactors.g:2517:5: lv_map_6_0= ruleMap
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getMapMapParserRuleCall_6_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_map_6_0=ruleMap();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"map",
                      						lv_map_6_0,
                      						"org.integratedmodelling.kactors.Kactors.Map");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:2535:3: ( (lv_observable_7_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:2535:3: ( (lv_observable_7_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2536:4: (lv_observable_7_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2536:4: (lv_observable_7_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2537:5: lv_observable_7_0= RULE_OBSERVABLE
                    {
                    lv_observable_7_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_7_0, grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_7_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:2554:3: ( (lv_expression_8_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:2554:3: ( (lv_expression_8_0= RULE_EXPR ) )
                    // InternalKactors.g:2555:4: (lv_expression_8_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2555:4: (lv_expression_8_0= RULE_EXPR )
                    // InternalKactors.g:2556:5: lv_expression_8_0= RULE_EXPR
                    {
                    lv_expression_8_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expression_8_0, grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_8_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expression",
                      						lv_expression_8_0,
                      						"org.integratedmodelling.kactors.Kactors.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:2573:3: ( (lv_table_9_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:2573:3: ( (lv_table_9_0= ruleLookupTable ) )
                    // InternalKactors.g:2574:4: (lv_table_9_0= ruleLookupTable )
                    {
                    // InternalKactors.g:2574:4: (lv_table_9_0= ruleLookupTable )
                    // InternalKactors.g:2575:5: lv_table_9_0= ruleLookupTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getTableLookupTableParserRuleCall_9_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_table_9_0=ruleLookupTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"table",
                      						lv_table_9_0,
                      						"org.integratedmodelling.kactors.Kactors.LookupTable");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKactors.g:2593:3: ( (lv_quantity_10_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:2593:3: ( (lv_quantity_10_0= ruleQuantity ) )
                    // InternalKactors.g:2594:4: (lv_quantity_10_0= ruleQuantity )
                    {
                    // InternalKactors.g:2594:4: (lv_quantity_10_0= ruleQuantity )
                    // InternalKactors.g:2595:5: lv_quantity_10_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getQuantityQuantityParserRuleCall_10_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_quantity_10_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"quantity",
                      						lv_quantity_10_0,
                      						"org.integratedmodelling.kactors.Kactors.Quantity");
                      					afterParserOrEnumRuleCall();
                      				
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


    // $ANTLR start "entryRuleValueWithoutTree"
    // InternalKactors.g:2616:1: entryRuleValueWithoutTree returns [EObject current=null] : iv_ruleValueWithoutTree= ruleValueWithoutTree EOF ;
    public final EObject entryRuleValueWithoutTree() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValueWithoutTree = null;


        try {
            // InternalKactors.g:2616:57: (iv_ruleValueWithoutTree= ruleValueWithoutTree EOF )
            // InternalKactors.g:2617:2: iv_ruleValueWithoutTree= ruleValueWithoutTree EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValueWithoutTreeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValueWithoutTree=ruleValueWithoutTree();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValueWithoutTree; 
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
    // $ANTLR end "entryRuleValueWithoutTree"


    // $ANTLR start "ruleValueWithoutTree"
    // InternalKactors.g:2623:1: ruleValueWithoutTree returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) ) ;
    public final EObject ruleValueWithoutTree() throws RecognitionException {
        EObject current = null;

        Token lv_argvalue_0_0=null;
        Token lv_observable_6_0=null;
        Token lv_expression_7_0=null;
        EObject lv_literal_1_0 = null;

        AntlrDatatypeRuleToken lv_id_2_0 = null;

        AntlrDatatypeRuleToken lv_urn_3_0 = null;

        EObject lv_list_4_0 = null;

        EObject lv_map_5_0 = null;

        EObject lv_table_8_0 = null;

        EObject lv_quantity_9_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2629:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) ) )
            // InternalKactors.g:2630:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) )
            {
            // InternalKactors.g:2630:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) )
            int alt39=10;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // InternalKactors.g:2631:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:2631:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:2632:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:2632:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:2633:5: lv_argvalue_0_0= RULE_ARGVALUE
                    {
                    lv_argvalue_0_0=(Token)match(input,RULE_ARGVALUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_argvalue_0_0, grammarAccess.getValueWithoutTreeAccess().getArgvalueARGVALUETerminalRuleCall_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"argvalue",
                      						lv_argvalue_0_0,
                      						"org.integratedmodelling.kactors.Kactors.ARGVALUE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2650:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:2650:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:2651:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:2651:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:2652:5: lv_literal_1_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getLiteralLiteralParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_literal_1_0=ruleLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"literal",
                      						lv_literal_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Literal");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2670:3: ( (lv_id_2_0= rulePathName ) )
                    {
                    // InternalKactors.g:2670:3: ( (lv_id_2_0= rulePathName ) )
                    // InternalKactors.g:2671:4: (lv_id_2_0= rulePathName )
                    {
                    // InternalKactors.g:2671:4: (lv_id_2_0= rulePathName )
                    // InternalKactors.g:2672:5: lv_id_2_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getIdPathNameParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_id_2_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"id",
                      						lv_id_2_0,
                      						"org.integratedmodelling.kactors.Kactors.PathName");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:2690:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:2690:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:2691:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:2691:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:2692:5: lv_urn_3_0= ruleUrnId
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getUrnUrnIdParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_urn_3_0=ruleUrnId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"urn",
                      						lv_urn_3_0,
                      						"org.integratedmodelling.kactors.Kactors.UrnId");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:2710:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:2710:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:2711:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:2711:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:2712:5: lv_list_4_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getListListParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_list_4_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"list",
                      						lv_list_4_0,
                      						"org.integratedmodelling.kactors.Kactors.List");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:2730:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:2730:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:2731:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:2731:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:2732:5: lv_map_5_0= ruleMap
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getMapMapParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_map_5_0=ruleMap();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"map",
                      						lv_map_5_0,
                      						"org.integratedmodelling.kactors.Kactors.Map");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:2750:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:2750:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2751:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2751:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2752:5: lv_observable_6_0= RULE_OBSERVABLE
                    {
                    lv_observable_6_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_6_0, grammarAccess.getValueWithoutTreeAccess().getObservableOBSERVABLETerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_6_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:2769:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:2769:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:2770:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2770:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:2771:5: lv_expression_7_0= RULE_EXPR
                    {
                    lv_expression_7_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expression_7_0, grammarAccess.getValueWithoutTreeAccess().getExpressionEXPRTerminalRuleCall_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expression",
                      						lv_expression_7_0,
                      						"org.integratedmodelling.kactors.Kactors.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:2788:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:2788:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:2789:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:2789:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:2790:5: lv_table_8_0= ruleLookupTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getTableLookupTableParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_table_8_0=ruleLookupTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"table",
                      						lv_table_8_0,
                      						"org.integratedmodelling.kactors.Kactors.LookupTable");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:2808:3: ( (lv_quantity_9_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:2808:3: ( (lv_quantity_9_0= ruleQuantity ) )
                    // InternalKactors.g:2809:4: (lv_quantity_9_0= ruleQuantity )
                    {
                    // InternalKactors.g:2809:4: (lv_quantity_9_0= ruleQuantity )
                    // InternalKactors.g:2810:5: lv_quantity_9_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getQuantityQuantityParserRuleCall_9_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_quantity_9_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueWithoutTreeRule());
                      					}
                      					set(
                      						current,
                      						"quantity",
                      						lv_quantity_9_0,
                      						"org.integratedmodelling.kactors.Kactors.Quantity");
                      					afterParserOrEnumRuleCall();
                      				
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
    // $ANTLR end "ruleValueWithoutTree"


    // $ANTLR start "entryRuleMatch"
    // InternalKactors.g:2831:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:2831:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:2832:2: iv_ruleMatch= ruleMatch EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMatchRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMatch=ruleMatch();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMatch; 
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
    // $ANTLR end "entryRuleMatch"


    // $ANTLR start "ruleMatch"
    // InternalKactors.g:2838:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) ) ;
    public final EObject ruleMatch() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        Token lv_type_6_0=null;
        Token otherlv_7=null;
        Token lv_regexp_9_0=null;
        Token otherlv_10=null;
        Token lv_observable_12_0=null;
        Token otherlv_13=null;
        Token otherlv_16=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_26=null;
        Token lv_expr_28_0=null;
        Token otherlv_29=null;
        Token lv_nodata_31_0=null;
        Token otherlv_32=null;
        Token lv_star_34_0=null;
        Token otherlv_35=null;
        Token lv_anything_37_0=null;
        Token otherlv_38=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_list_18_0 = null;

        EObject lv_body_20_0 = null;

        EObject lv_set_22_0 = null;

        EObject lv_body_24_0 = null;

        EObject lv_quantity_25_0 = null;

        EObject lv_body_27_0 = null;

        EObject lv_body_30_0 = null;

        EObject lv_body_33_0 = null;

        EObject lv_body_36_0 = null;

        EObject lv_body_39_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2844:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) ) )
            // InternalKactors.g:2845:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )
            {
            // InternalKactors.g:2845:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )
            int alt41=13;
            alt41 = dfa41.predict(input);
            switch (alt41) {
                case 1 :
                    // InternalKactors.g:2846:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2846:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    // InternalKactors.g:2847:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2847:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2848:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2848:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2849:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_id_0_0, grammarAccess.getMatchAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"id",
                      							lv_id_0_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:2869:4: ( (lv_body_2_0= ruleStatementList ) )
                    // InternalKactors.g:2870:5: (lv_body_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:2870:5: (lv_body_2_0= ruleStatementList )
                    // InternalKactors.g:2871:6: lv_body_2_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_2_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_2_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2890:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2890:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    // InternalKactors.g:2891:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2891:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
                    // InternalKactors.g:2892:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    {
                    // InternalKactors.g:2892:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    // InternalKactors.g:2893:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    {
                    // InternalKactors.g:2893:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==53) ) {
                        alt40=1;
                    }
                    else if ( (LA40_0==54) ) {
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
                            // InternalKactors.g:2894:7: lv_boolean_3_1= 'true'
                            {
                            lv_boolean_3_1=(Token)match(input,53,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_boolean_3_1, grammarAccess.getMatchAccess().getBooleanTrueKeyword_1_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "boolean", lv_boolean_3_1, null);
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2905:7: lv_boolean_3_2= 'false'
                            {
                            lv_boolean_3_2=(Token)match(input,54,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_boolean_3_2, grammarAccess.getMatchAccess().getBooleanFalseKeyword_1_0_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "boolean", lv_boolean_3_2, null);
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    otherlv_4=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:2922:4: ( (lv_body_5_0= ruleStatementList ) )
                    // InternalKactors.g:2923:5: (lv_body_5_0= ruleStatementList )
                    {
                    // InternalKactors.g:2923:5: (lv_body_5_0= ruleStatementList )
                    // InternalKactors.g:2924:6: lv_body_5_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_5_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_5_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2943:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2943:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    // InternalKactors.g:2944:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2944:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) )
                    // InternalKactors.g:2945:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    {
                    // InternalKactors.g:2945:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    // InternalKactors.g:2946:6: lv_type_6_0= RULE_CAMELCASE_ID
                    {
                    lv_type_6_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_type_6_0, grammarAccess.getMatchAccess().getTypeCAMELCASE_IDTerminalRuleCall_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"type",
                      							lv_type_6_0,
                      							"org.integratedmodelling.kactors.Kactors.CAMELCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_7=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:2966:4: ( (lv_body_8_0= ruleStatementList ) )
                    // InternalKactors.g:2967:5: (lv_body_8_0= ruleStatementList )
                    {
                    // InternalKactors.g:2967:5: (lv_body_8_0= ruleStatementList )
                    // InternalKactors.g:2968:6: lv_body_8_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_8_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_8_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:2987:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2987:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    // InternalKactors.g:2988:4: ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2988:4: ( (lv_regexp_9_0= RULE_REGEXP ) )
                    // InternalKactors.g:2989:5: (lv_regexp_9_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:2989:5: (lv_regexp_9_0= RULE_REGEXP )
                    // InternalKactors.g:2990:6: lv_regexp_9_0= RULE_REGEXP
                    {
                    lv_regexp_9_0=(Token)match(input,RULE_REGEXP,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_regexp_9_0, grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_3_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"regexp",
                      							lv_regexp_9_0,
                      							"org.integratedmodelling.kactors.Kactors.REGEXP");
                      					
                    }

                    }


                    }

                    otherlv_10=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:3010:4: ( (lv_body_11_0= ruleStatementList ) )
                    // InternalKactors.g:3011:5: (lv_body_11_0= ruleStatementList )
                    {
                    // InternalKactors.g:3011:5: (lv_body_11_0= ruleStatementList )
                    // InternalKactors.g:3012:6: lv_body_11_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_11_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_11_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:3031:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3031:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    // InternalKactors.g:3032:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3032:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:3033:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:3033:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    // InternalKactors.g:3034:6: lv_observable_12_0= RULE_OBSERVABLE
                    {
                    lv_observable_12_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_observable_12_0, grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_4_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"observable",
                      							lv_observable_12_0,
                      							"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      					
                    }

                    }


                    }

                    otherlv_13=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:3054:4: ( (lv_body_14_0= ruleStatementList ) )
                    // InternalKactors.g:3055:5: (lv_body_14_0= ruleStatementList )
                    {
                    // InternalKactors.g:3055:5: (lv_body_14_0= ruleStatementList )
                    // InternalKactors.g:3056:6: lv_body_14_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_14_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_14_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:3075:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3075:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    // InternalKactors.g:3076:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3076:4: ( (lv_literal_15_0= ruleLiteral ) )
                    // InternalKactors.g:3077:5: (lv_literal_15_0= ruleLiteral )
                    {
                    // InternalKactors.g:3077:5: (lv_literal_15_0= ruleLiteral )
                    // InternalKactors.g:3078:6: lv_literal_15_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_literal_15_0=ruleLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"literal",
                      							lv_literal_15_0,
                      							"org.integratedmodelling.kactors.Kactors.Literal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_16=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:3099:4: ( (lv_body_17_0= ruleStatementList ) )
                    // InternalKactors.g:3100:5: (lv_body_17_0= ruleStatementList )
                    {
                    // InternalKactors.g:3100:5: (lv_body_17_0= ruleStatementList )
                    // InternalKactors.g:3101:6: lv_body_17_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_5_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_17_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_17_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:3120:3: ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3120:3: ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    // InternalKactors.g:3121:4: ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3121:4: ( (lv_list_18_0= ruleList ) )
                    // InternalKactors.g:3122:5: (lv_list_18_0= ruleList )
                    {
                    // InternalKactors.g:3122:5: (lv_list_18_0= ruleList )
                    // InternalKactors.g:3123:6: lv_list_18_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getListListParserRuleCall_6_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_list_18_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"list",
                      							lv_list_18_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_19=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:3144:4: ( (lv_body_20_0= ruleStatementList ) )
                    // InternalKactors.g:3145:5: (lv_body_20_0= ruleStatementList )
                    {
                    // InternalKactors.g:3145:5: (lv_body_20_0= ruleStatementList )
                    // InternalKactors.g:3146:6: lv_body_20_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_6_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_20_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_20_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:3165:3: (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3165:3: (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) )
                    // InternalKactors.g:3166:4: otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) )
                    {
                    otherlv_21=(Token)match(input,51,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_21, grammarAccess.getMatchAccess().getInKeyword_7_0());
                      			
                    }
                    // InternalKactors.g:3170:4: ( (lv_set_22_0= ruleList ) )
                    // InternalKactors.g:3171:5: (lv_set_22_0= ruleList )
                    {
                    // InternalKactors.g:3171:5: (lv_set_22_0= ruleList )
                    // InternalKactors.g:3172:6: lv_set_22_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getSetListParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_set_22_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_22_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_23=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_23, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_2());
                      			
                    }
                    // InternalKactors.g:3193:4: ( (lv_body_24_0= ruleStatementList ) )
                    // InternalKactors.g:3194:5: (lv_body_24_0= ruleStatementList )
                    {
                    // InternalKactors.g:3194:5: (lv_body_24_0= ruleStatementList )
                    // InternalKactors.g:3195:6: lv_body_24_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_24_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_24_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:3214:3: ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3214:3: ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) )
                    // InternalKactors.g:3215:4: ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3215:4: ( (lv_quantity_25_0= ruleQuantity ) )
                    // InternalKactors.g:3216:5: (lv_quantity_25_0= ruleQuantity )
                    {
                    // InternalKactors.g:3216:5: (lv_quantity_25_0= ruleQuantity )
                    // InternalKactors.g:3217:6: lv_quantity_25_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_8_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_quantity_25_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"quantity",
                      							lv_quantity_25_0,
                      							"org.integratedmodelling.kactors.Kactors.Quantity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_26=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_26, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_1());
                      			
                    }
                    // InternalKactors.g:3238:4: ( (lv_body_27_0= ruleStatementList ) )
                    // InternalKactors.g:3239:5: (lv_body_27_0= ruleStatementList )
                    {
                    // InternalKactors.g:3239:5: (lv_body_27_0= ruleStatementList )
                    // InternalKactors.g:3240:6: lv_body_27_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_27_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_27_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:3259:3: ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3259:3: ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) )
                    // InternalKactors.g:3260:4: ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3260:4: ( (lv_expr_28_0= RULE_EXPR ) )
                    // InternalKactors.g:3261:5: (lv_expr_28_0= RULE_EXPR )
                    {
                    // InternalKactors.g:3261:5: (lv_expr_28_0= RULE_EXPR )
                    // InternalKactors.g:3262:6: lv_expr_28_0= RULE_EXPR
                    {
                    lv_expr_28_0=(Token)match(input,RULE_EXPR,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_expr_28_0, grammarAccess.getMatchAccess().getExprEXPRTerminalRuleCall_9_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"expr",
                      							lv_expr_28_0,
                      							"org.integratedmodelling.kactors.Kactors.EXPR");
                      					
                    }

                    }


                    }

                    otherlv_29=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_29, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_1());
                      			
                    }
                    // InternalKactors.g:3282:4: ( (lv_body_30_0= ruleStatementList ) )
                    // InternalKactors.g:3283:5: (lv_body_30_0= ruleStatementList )
                    {
                    // InternalKactors.g:3283:5: (lv_body_30_0= ruleStatementList )
                    // InternalKactors.g:3284:6: lv_body_30_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_9_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_30_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_30_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKactors.g:3303:3: ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3303:3: ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) )
                    // InternalKactors.g:3304:4: ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3304:4: ( (lv_nodata_31_0= 'unknown' ) )
                    // InternalKactors.g:3305:5: (lv_nodata_31_0= 'unknown' )
                    {
                    // InternalKactors.g:3305:5: (lv_nodata_31_0= 'unknown' )
                    // InternalKactors.g:3306:6: lv_nodata_31_0= 'unknown'
                    {
                    lv_nodata_31_0=(Token)match(input,55,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nodata_31_0, grammarAccess.getMatchAccess().getNodataUnknownKeyword_10_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "nodata", lv_nodata_31_0, "unknown");
                      					
                    }

                    }


                    }

                    otherlv_32=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_32, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:3322:4: ( (lv_body_33_0= ruleStatementList ) )
                    // InternalKactors.g:3323:5: (lv_body_33_0= ruleStatementList )
                    {
                    // InternalKactors.g:3323:5: (lv_body_33_0= ruleStatementList )
                    // InternalKactors.g:3324:6: lv_body_33_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_10_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_33_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_33_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalKactors.g:3343:3: ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3343:3: ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    // InternalKactors.g:3344:4: ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3344:4: ( (lv_star_34_0= '*' ) )
                    // InternalKactors.g:3345:5: (lv_star_34_0= '*' )
                    {
                    // InternalKactors.g:3345:5: (lv_star_34_0= '*' )
                    // InternalKactors.g:3346:6: lv_star_34_0= '*'
                    {
                    lv_star_34_0=(Token)match(input,56,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_star_34_0, grammarAccess.getMatchAccess().getStarAsteriskKeyword_11_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "star", true, "*");
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:3362:4: ( (lv_body_36_0= ruleStatementList ) )
                    // InternalKactors.g:3363:5: (lv_body_36_0= ruleStatementList )
                    {
                    // InternalKactors.g:3363:5: (lv_body_36_0= ruleStatementList )
                    // InternalKactors.g:3364:6: lv_body_36_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_11_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_36_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_36_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalKactors.g:3383:3: ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:3383:3: ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    // InternalKactors.g:3384:4: ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:3384:4: ( (lv_anything_37_0= '#' ) )
                    // InternalKactors.g:3385:5: (lv_anything_37_0= '#' )
                    {
                    // InternalKactors.g:3385:5: (lv_anything_37_0= '#' )
                    // InternalKactors.g:3386:6: lv_anything_37_0= '#'
                    {
                    lv_anything_37_0=(Token)match(input,57,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_anything_37_0, grammarAccess.getMatchAccess().getAnythingNumberSignKeyword_12_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "anything", true, "#");
                      					
                    }

                    }


                    }

                    otherlv_38=(Token)match(input,52,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_38, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_12_1());
                      			
                    }
                    // InternalKactors.g:3402:4: ( (lv_body_39_0= ruleStatementList ) )
                    // InternalKactors.g:3403:5: (lv_body_39_0= ruleStatementList )
                    {
                    // InternalKactors.g:3403:5: (lv_body_39_0= ruleStatementList )
                    // InternalKactors.g:3404:6: lv_body_39_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_12_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_39_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_39_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

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
    // $ANTLR end "ruleMatch"


    // $ANTLR start "entryRuleUrnId"
    // InternalKactors.g:3426:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:3426:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:3427:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:3433:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
    public final AntlrDatatypeRuleToken ruleUrnId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_8=null;
        AntlrDatatypeRuleToken this_PathName_1 = null;

        AntlrDatatypeRuleToken this_PathName_3 = null;

        AntlrDatatypeRuleToken this_PathName_5 = null;

        AntlrDatatypeRuleToken this_Path_7 = null;

        AntlrDatatypeRuleToken this_VersionNumber_10 = null;

        AntlrDatatypeRuleToken this_Path_12 = null;

        AntlrDatatypeRuleToken this_UrnKvp_13 = null;

        AntlrDatatypeRuleToken this_Path_15 = null;

        AntlrDatatypeRuleToken this_UrnKvp_16 = null;



        	enterRule();

        try {
            // InternalKactors.g:3439:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:3440:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:3440:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:3441:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:3441:3: (kw= 'urn:klab:' )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==58) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalKactors.g:3442:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,58,FOLLOW_4); if (state.failed) return current;
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
            pushFollow(FOLLOW_13);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_13);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_13);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_33); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:3493:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RULE_LOWERCASE_ID||LA43_0==RULE_UPPERCASE_ID) ) {
                alt43=1;
            }
            else if ( (LA43_0==RULE_INT) ) {
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
                    // InternalKactors.g:3494:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_34);
                    this_Path_7=rulePath();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_Path_7);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:3505:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3513:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==42) ) {
                int LA44_1 = input.LA(2);

                if ( (LA44_1==RULE_INT) ) {
                    int LA44_3 = input.LA(3);

                    if ( (synpred101_InternalKactors()) ) {
                        alt44=1;
                    }
                }
            }
            switch (alt44) {
                case 1 :
                    // InternalKactors.g:3514:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_35);
                    this_VersionNumber_10=ruleVersionNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_VersionNumber_10);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3530:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==57) ) {
                int LA48_1 = input.LA(2);

                if ( (LA48_1==RULE_LOWERCASE_ID||LA48_1==RULE_UPPERCASE_ID) ) {
                    alt48=1;
                }
            }
            switch (alt48) {
                case 1 :
                    // InternalKactors.g:3531:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,57,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:3536:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt45=2;
                    alt45 = dfa45.predict(input);
                    switch (alt45) {
                        case 1 :
                            // InternalKactors.g:3537:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_37);
                            this_Path_12=rulePath();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_Path_12);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:3548:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_37);
                            this_UrnKvp_13=ruleUrnKvp();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_UrnKvp_13);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:3559:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==59) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalKactors.g:3560:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,59,FOLLOW_36); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:3565:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt46=2;
                    	    alt46 = dfa46.predict(input);
                    	    switch (alt46) {
                    	        case 1 :
                    	            // InternalKactors.g:3566:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_37);
                    	            this_Path_15=rulePath();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						current.merge(this_Path_15);
                    	              					
                    	            }
                    	            if ( state.backtracking==0 ) {

                    	              						afterParserOrEnumRuleCall();
                    	              					
                    	            }

                    	            }
                    	            break;
                    	        case 2 :
                    	            // InternalKactors.g:3577:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_37);
                    	            this_UrnKvp_16=ruleUrnKvp();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						current.merge(this_UrnKvp_16);
                    	              					
                    	            }
                    	            if ( state.backtracking==0 ) {

                    	              						afterParserOrEnumRuleCall();
                    	              					
                    	            }

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);


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


    // $ANTLR start "entryRuleUrnKvp"
    // InternalKactors.g:3594:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:3594:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:3595:2: iv_ruleUrnKvp= ruleUrnKvp EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUrnKvpRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUrnKvp=ruleUrnKvp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUrnKvp.getText(); 
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
    // $ANTLR end "entryRuleUrnKvp"


    // $ANTLR start "ruleUrnKvp"
    // InternalKactors.g:3601:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:3607:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:3608:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:3608:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:3609:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_38);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,60,FOLLOW_33); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:3624:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==RULE_LOWERCASE_ID||LA49_0==RULE_UPPERCASE_ID) ) {
                alt49=1;
            }
            else if ( (LA49_0==RULE_INT) ) {
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
                    // InternalKactors.g:3625:4: this_Path_2= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnKvpAccess().getPathParserRuleCall_2_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Path_2=rulePath();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_Path_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:3636:4: this_INT_3= RULE_INT
                    {
                    this_INT_3=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_3, grammarAccess.getUrnKvpAccess().getINTTerminalRuleCall_2_1());
                      			
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
    // $ANTLR end "ruleUrnKvp"


    // $ANTLR start "entryRuleList"
    // InternalKactors.g:3648:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:3648:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:3649:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:3655:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3661:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:3662:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:3662:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:3663:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:3663:3: ()
            // InternalKactors.g:3664:4: 
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

            otherlv_1=(Token)match(input,43,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:3677:3: ( (lv_contents_2_0= ruleValue ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=RULE_STRING && LA50_0<=RULE_LOWERCASE_ID)||(LA50_0>=RULE_EXPR && LA50_0<=RULE_ARGVALUE)||LA50_0==RULE_INT||LA50_0==43||(LA50_0>=53 && LA50_0<=54)||LA50_0==58||LA50_0==61||LA50_0==67||(LA50_0>=80 && LA50_0<=81)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalKactors.g:3678:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:3678:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:3679:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_19);
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
            	      						"org.integratedmodelling.kactors.Kactors.Value");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);

            otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleMap"
    // InternalKactors.g:3704:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:3704:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:3705:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:3711:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '#{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3717:2: ( ( () otherlv_1= '#{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:3718:2: ( () otherlv_1= '#{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:3718:2: ( () otherlv_1= '#{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:3719:3: () otherlv_1= '#{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:3719:3: ()
            // InternalKactors.g:3720:4: 
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

            otherlv_1=(Token)match(input,61,FOLLOW_39); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getNumberSignLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3733:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RULE_STRING && LA52_0<=RULE_LOWERCASE_ID)||LA52_0==RULE_INT||LA52_0==51||(LA52_0>=53 && LA52_0<=56)||LA52_0==60||(LA52_0>=75 && LA52_0<=81)) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalKactors.g:3734:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:3734:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:3735:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:3735:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:3736:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_40);
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
                      							"org.integratedmodelling.kactors.Kactors.MapEntry");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3753:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==31) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // InternalKactors.g:3754:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:3754:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:3755:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_41); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:3762:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:3763:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:3763:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:3764:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_40);
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
                    	      								"org.integratedmodelling.kactors.Kactors.MapEntry");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop51;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleTree"
    // InternalKactors.g:3791:1: entryRuleTree returns [EObject current=null] : iv_ruleTree= ruleTree EOF ;
    public final EObject entryRuleTree() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTree = null;


        try {
            // InternalKactors.g:3791:45: (iv_ruleTree= ruleTree EOF )
            // InternalKactors.g:3792:2: iv_ruleTree= ruleTree EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTreeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTree=ruleTree();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTree; 
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
    // $ANTLR end "entryRuleTree"


    // $ANTLR start "ruleTree"
    // InternalKactors.g:3798:1: ruleTree returns [EObject current=null] : ( ( (lv_root_0_0= ruleValueWithoutTree ) ) ( ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) ) )+ ) ;
    public final EObject ruleTree() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_root_0_0 = null;

        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3804:2: ( ( ( (lv_root_0_0= ruleValueWithoutTree ) ) ( ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) ) )+ ) )
            // InternalKactors.g:3805:2: ( ( (lv_root_0_0= ruleValueWithoutTree ) ) ( ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) ) )+ )
            {
            // InternalKactors.g:3805:2: ( ( (lv_root_0_0= ruleValueWithoutTree ) ) ( ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) ) )+ )
            // InternalKactors.g:3806:3: ( (lv_root_0_0= ruleValueWithoutTree ) ) ( ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) ) )+
            {
            // InternalKactors.g:3806:3: ( (lv_root_0_0= ruleValueWithoutTree ) )
            // InternalKactors.g:3807:4: (lv_root_0_0= ruleValueWithoutTree )
            {
            // InternalKactors.g:3807:4: (lv_root_0_0= ruleValueWithoutTree )
            // InternalKactors.g:3808:5: lv_root_0_0= ruleValueWithoutTree
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTreeAccess().getRootValueWithoutTreeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_42);
            lv_root_0_0=ruleValueWithoutTree();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTreeRule());
              					}
              					set(
              						current,
              						"root",
              						lv_root_0_0,
              						"org.integratedmodelling.kactors.Kactors.ValueWithoutTree");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:3825:3: ( ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) ) )+
            int cnt54=0;
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==63) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalKactors.g:3826:4: ( ( '<-' )=>otherlv_1= '<-' ) ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) )
            	    {
            	    // InternalKactors.g:3826:4: ( ( '<-' )=>otherlv_1= '<-' )
            	    // InternalKactors.g:3827:5: ( '<-' )=>otherlv_1= '<-'
            	    {
            	    otherlv_1=(Token)match(input,63,FOLLOW_43); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getTreeAccess().getLessThanSignHyphenMinusKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:3833:4: ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) )
            	    int alt53=2;
            	    alt53 = dfa53.predict(input);
            	    switch (alt53) {
            	        case 1 :
            	            // InternalKactors.g:3834:5: ( (lv_value_2_0= ruleValueWithoutTree ) )
            	            {
            	            // InternalKactors.g:3834:5: ( (lv_value_2_0= ruleValueWithoutTree ) )
            	            // InternalKactors.g:3835:6: (lv_value_2_0= ruleValueWithoutTree )
            	            {
            	            // InternalKactors.g:3835:6: (lv_value_2_0= ruleValueWithoutTree )
            	            // InternalKactors.g:3836:7: lv_value_2_0= ruleValueWithoutTree
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getTreeAccess().getValueValueWithoutTreeParserRuleCall_1_1_0_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_44);
            	            lv_value_2_0=ruleValueWithoutTree();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getTreeRule());
            	              							}
            	              							add(
            	              								current,
            	              								"value",
            	              								lv_value_2_0,
            	              								"org.integratedmodelling.kactors.Kactors.ValueWithoutTree");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:3854:5: (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' )
            	            {
            	            // InternalKactors.g:3854:5: (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' )
            	            // InternalKactors.g:3855:6: otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')'
            	            {
            	            otherlv_3=(Token)match(input,43,FOLLOW_43); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_3, grammarAccess.getTreeAccess().getLeftParenthesisKeyword_1_1_1_0());
            	              					
            	            }
            	            // InternalKactors.g:3859:6: ( (lv_value_4_0= ruleTree ) )
            	            // InternalKactors.g:3860:7: (lv_value_4_0= ruleTree )
            	            {
            	            // InternalKactors.g:3860:7: (lv_value_4_0= ruleTree )
            	            // InternalKactors.g:3861:8: lv_value_4_0= ruleTree
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getTreeAccess().getValueTreeParserRuleCall_1_1_1_1_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_20);
            	            lv_value_4_0=ruleTree();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElementForParent(grammarAccess.getTreeRule());
            	              								}
            	              								add(
            	              									current,
            	              									"value",
            	              									lv_value_4_0,
            	              									"org.integratedmodelling.kactors.Kactors.Tree");
            	              								afterParserOrEnumRuleCall();
            	              							
            	            }

            	            }


            	            }

            	            otherlv_5=(Token)match(input,44,FOLLOW_44); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_5, grammarAccess.getTreeAccess().getRightParenthesisKeyword_1_1_1_2());
            	              					
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt54 >= 1 ) break loop54;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(54, input);
                        throw eee;
                }
                cnt54++;
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
    // $ANTLR end "ruleTree"


    // $ANTLR start "entryRuleMapEntry"
    // InternalKactors.g:3889:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:3889:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:3890:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:3896:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3902:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:3903:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:3903:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:3904:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:3904:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:3905:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:3905:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:3906:5: lv_classifier_0_0= ruleClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_classifier_0_0=ruleClassifier();

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
              						"org.integratedmodelling.kactors.Kactors.Classifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,42,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:3927:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:3928:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:3928:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:3929:5: lv_value_2_0= ruleValue
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
              						"org.integratedmodelling.kactors.Kactors.Value");
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


    // $ANTLR start "entryRuleClassifier"
    // InternalKactors.g:3950:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:3950:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:3951:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:3957:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
    public final EObject ruleClassifier() throws RecognitionException {
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
        Token lv_observable_13_0=null;
        Token lv_id_14_0=null;
        Token lv_nodata_17_0=null;
        Token lv_star_18_0=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;

        EObject lv_num_9_0 = null;

        EObject lv_set_11_0 = null;

        EObject lv_op_15_0 = null;

        EObject lv_expression_16_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3963:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:3964:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:3964:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt58=10;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // InternalKactors.g:3965:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3965:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==53) ) {
                        alt55=1;
                    }
                    else if ( (LA55_0==54) ) {
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
                            // InternalKactors.g:3966:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3966:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3967:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3967:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3968:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_0_0, grammarAccess.getClassifierAccess().getBooleanTrueKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_0_0, "true");
                              					
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:3981:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3981:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:3982:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:3982:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:3983:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_1_0, grammarAccess.getClassifierAccess().getBooleanFalseKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getClassifierRule());
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
                    // InternalKactors.g:3997:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:3997:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:3998:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:3998:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:3999:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:3999:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:4000:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_45);
                    lv_int0_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_2_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4017:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt56=3;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==64) ) {
                        alt56=1;
                    }
                    else if ( (LA56_0==65) ) {
                        alt56=2;
                    }
                    switch (alt56) {
                        case 1 :
                            // InternalKactors.g:4018:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4018:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:4019:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:4019:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:4020:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,64,FOLLOW_46); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_3_0, grammarAccess.getClassifierAccess().getLeftLimitInclusiveKeyword_1_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getClassifierRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_3_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4033:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,65,FOLLOW_46); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4038:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:4039:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,66,FOLLOW_47); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:4045:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:4046:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:4050:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:4051:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_48);
                    lv_int1_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4068:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt57=3;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==64) ) {
                        alt57=1;
                    }
                    else if ( (LA57_0==65) ) {
                        alt57=2;
                    }
                    switch (alt57) {
                        case 1 :
                            // InternalKactors.g:4069:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4069:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:4070:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:4070:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:4071:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_7_0, grammarAccess.getClassifierAccess().getRightLimitInclusiveKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getClassifierRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_7_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4084:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4091:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4091:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:4092:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:4092:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:4093:5: lv_num_9_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getClassifierAccess().getNumNumberParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_9_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getClassifierRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_9_0,
                      						"org.integratedmodelling.kactors.Kactors.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4111:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:4111:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:4112:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,51,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:4116:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:4117:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:4117:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:4118:6: lv_set_11_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getSetListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_11_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_11_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:4137:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4137:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:4138:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:4138:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:4139:5: lv_string_12_0= RULE_STRING
                    {
                    lv_string_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_12_0, grammarAccess.getClassifierAccess().getStringSTRINGTerminalRuleCall_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
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
                    // InternalKactors.g:4156:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4156:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4157:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4157:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4158:5: lv_observable_13_0= RULE_OBSERVABLE
                    {
                    lv_observable_13_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_13_0, grammarAccess.getClassifierAccess().getObservableOBSERVABLETerminalRuleCall_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_13_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:4175:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:4175:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4176:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4176:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4177:5: lv_id_14_0= RULE_LOWERCASE_ID
                    {
                    lv_id_14_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_id_14_0, grammarAccess.getClassifierAccess().getIdLOWERCASE_IDTerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"id",
                      						lv_id_14_0,
                      						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:4194:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4194:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:4195:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4195:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:4196:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:4196:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:4197:6: lv_op_15_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
                    lv_op_15_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_15_0,
                      							"org.integratedmodelling.kactors.Kactors.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4214:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:4215:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:4215:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:4216:6: lv_expression_16_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getExpressionNumberParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_16_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_16_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:4235:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:4235:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:4236:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:4236:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:4237:5: lv_nodata_17_0= 'unknown'
                    {
                    lv_nodata_17_0=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nodata_17_0, grammarAccess.getClassifierAccess().getNodataUnknownKeyword_8_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(current, "nodata", lv_nodata_17_0, "unknown");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:4250:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:4250:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:4251:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:4251:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:4252:5: lv_star_18_0= '*'
                    {
                    lv_star_18_0=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_18_0, grammarAccess.getClassifierAccess().getStarAsteriskKeyword_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
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
    // $ANTLR end "ruleClassifier"


    // $ANTLR start "entryRuleLookupTable"
    // InternalKactors.g:4268:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:4268:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:4269:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:4275:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4281:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:4282:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:4282:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:4283:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:4283:3: ()
            // InternalKactors.g:4284:4: 
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

            otherlv_1=(Token)match(input,67,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:4297:3: ( (lv_table_2_0= ruleTable ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( ((LA59_0>=RULE_STRING && LA59_0<=RULE_LOWERCASE_ID)||LA59_0==RULE_EXPR||LA59_0==RULE_INT||LA59_0==51||(LA59_0>=53 && LA59_0<=57)||LA59_0==60||(LA59_0>=75 && LA59_0<=81)) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalKactors.g:4298:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:4298:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:4299:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_50);
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
                      						"org.integratedmodelling.kactors.Kactors.Table");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4324:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:4324:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:4325:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:4331:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4337:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:4338:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:4338:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:4339:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:4339:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt60=2;
            alt60 = dfa60.predict(input);
            switch (alt60) {
                case 1 :
                    // InternalKactors.g:4340:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:4340:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:4341:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:4341:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:4342:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_51);
                    lv_headers_0_0=ruleHeaderRow();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableRule());
                      						}
                      						set(
                      							current,
                      							"headers",
                      							lv_headers_0_0,
                      							"org.integratedmodelling.kactors.Kactors.HeaderRow");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4364:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:4365:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:4365:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:4366:5: lv_rows_2_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_24);
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
              						"org.integratedmodelling.kactors.Kactors.TableRow");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:4383:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==31) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // InternalKactors.g:4384:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,31,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:4388:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:4389:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:4389:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:4390:6: lv_rows_4_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_24);
            	    lv_rows_4_0=ruleTableRow();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTableRule());
            	      						}
            	      						add(
            	      							current,
            	      							"rows",
            	      							lv_rows_4_0,
            	      							"org.integratedmodelling.kactors.Kactors.TableRow");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop61;
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


    // $ANTLR start "entryRuleHeaderRow"
    // InternalKactors.g:4412:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:4412:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:4413:2: iv_ruleHeaderRow= ruleHeaderRow EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getHeaderRowRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleHeaderRow=ruleHeaderRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleHeaderRow; 
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
    // $ANTLR end "entryRuleHeaderRow"


    // $ANTLR start "ruleHeaderRow"
    // InternalKactors.g:4419:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:4425:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:4426:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:4426:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:4427:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:4427:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:4428:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:4428:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:4429:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:4429:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_LOWERCASE_ID) ) {
                alt62=1;
            }
            else if ( (LA62_0==RULE_STRING) ) {
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
                    // InternalKactors.g:4430:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_elements_0_1, grammarAccess.getHeaderRowAccess().getElementsLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeaderRowRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"elements",
                      							lv_elements_0_1,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:4445:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_elements_0_2, grammarAccess.getHeaderRowAccess().getElementsSTRINGTerminalRuleCall_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeaderRowRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"elements",
                      							lv_elements_0_2,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalKactors.g:4462:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==69) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalKactors.g:4463:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,69,FOLLOW_54); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:4467:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:4468:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:4468:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:4469:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:4469:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt63=2;
            	    int LA63_0 = input.LA(1);

            	    if ( (LA63_0==RULE_LOWERCASE_ID) ) {
            	        alt63=1;
            	    }
            	    else if ( (LA63_0==RULE_STRING) ) {
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
            	            // InternalKactors.g:4470:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_53); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							newLeafNode(lv_elements_2_1, grammarAccess.getHeaderRowAccess().getElementsLOWERCASE_IDTerminalRuleCall_1_1_0_0());
            	              						
            	            }
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElement(grammarAccess.getHeaderRowRule());
            	              							}
            	              							addWithLastConsumed(
            	              								current,
            	              								"elements",
            	              								lv_elements_2_1,
            	              								"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              						
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:4485:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_53); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							newLeafNode(lv_elements_2_2, grammarAccess.getHeaderRowAccess().getElementsSTRINGTerminalRuleCall_1_1_0_1());
            	              						
            	            }
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElement(grammarAccess.getHeaderRowRule());
            	              							}
            	              							addWithLastConsumed(
            	              								current,
            	              								"elements",
            	              								lv_elements_2_2,
            	              								"org.eclipse.xtext.common.Terminals.STRING");
            	              						
            	            }

            	            }
            	            break;

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


            }

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
    // $ANTLR end "ruleHeaderRow"


    // $ANTLR start "entryRuleTableRow"
    // InternalKactors.g:4507:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:4507:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:4508:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:4514:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4520:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:4521:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:4521:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:4522:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:4522:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:4523:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:4523:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:4524:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_53);
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
              						"org.integratedmodelling.kactors.Kactors.TableClassifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:4541:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==69) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // InternalKactors.g:4542:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,69,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:4546:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:4547:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:4547:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:4548:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_53);
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
            	      							"org.integratedmodelling.kactors.Kactors.TableClassifier");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop65;
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
    // InternalKactors.g:4570:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:4570:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:4571:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:4577:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
    public final EObject ruleTableClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_boolean_0_0=null;
        Token lv_boolean_1_0=null;
        Token lv_string_3_0=null;
        Token lv_observable_4_0=null;
        Token lv_leftLimit_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_rightLimit_12_0=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token lv_expr_18_0=null;
        Token lv_nodata_19_0=null;
        Token lv_star_20_0=null;
        Token lv_anything_21_0=null;
        EObject lv_num_2_0 = null;

        EObject lv_op_5_0 = null;

        EObject lv_expression_6_0 = null;

        EObject lv_int0_7_0 = null;

        EObject lv_int1_11_0 = null;

        EObject lv_set_15_0 = null;

        EObject lv_quantity_16_0 = null;

        EObject lv_date_17_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4583:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:4584:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:4584:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt69=13;
            alt69 = dfa69.predict(input);
            switch (alt69) {
                case 1 :
                    // InternalKactors.g:4585:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:4585:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==53) ) {
                        alt66=1;
                    }
                    else if ( (LA66_0==54) ) {
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
                            // InternalKactors.g:4586:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:4586:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:4587:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:4587:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:4588:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4601:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:4601:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:4602:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:4602:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:4603:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4617:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4617:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:4618:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:4618:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:4619:5: lv_num_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getNumNumberParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4637:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4637:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:4638:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:4638:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:4639:5: lv_string_3_0= RULE_STRING
                    {
                    lv_string_3_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_3_0, grammarAccess.getTableClassifierAccess().getStringSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_3_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4656:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4656:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4657:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4657:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4658:5: lv_observable_4_0= RULE_OBSERVABLE
                    {
                    lv_observable_4_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_4_0, grammarAccess.getTableClassifierAccess().getObservableOBSERVABLETerminalRuleCall_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_4_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:4675:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4675:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:4676:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4676:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:4677:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:4677:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:4678:6: lv_op_5_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
                    lv_op_5_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_5_0,
                      							"org.integratedmodelling.kactors.Kactors.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4695:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:4696:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:4696:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:4697:6: lv_expression_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getExpressionNumberParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:4716:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:4716:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:4717:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:4717:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:4718:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:4718:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:4719:6: lv_int0_7_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_45);
                    lv_int0_7_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_7_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4736:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt67=3;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==64) ) {
                        alt67=1;
                    }
                    else if ( (LA67_0==65) ) {
                        alt67=2;
                    }
                    switch (alt67) {
                        case 1 :
                            // InternalKactors.g:4737:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4737:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:4738:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:4738:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:4739:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,64,FOLLOW_46); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_8_0, grammarAccess.getTableClassifierAccess().getLeftLimitInclusiveKeyword_5_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_8_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4752:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,65,FOLLOW_46); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4757:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:4758:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,66,FOLLOW_47); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:4764:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:4765:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:4769:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:4770:6: lv_int1_11_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_5_3_0());
                      					
                    }
                    pushFollow(FOLLOW_48);
                    lv_int1_11_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_11_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4787:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt68=3;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==64) ) {
                        alt68=1;
                    }
                    else if ( (LA68_0==65) ) {
                        alt68=2;
                    }
                    switch (alt68) {
                        case 1 :
                            // InternalKactors.g:4788:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4788:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:4789:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:4789:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:4790:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_12_0, grammarAccess.getTableClassifierAccess().getRightLimitInclusiveKeyword_5_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_12_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4803:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_13, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:4810:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:4810:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:4811:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,51,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:4815:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:4816:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:4816:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:4817:6: lv_set_15_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getSetListParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_15_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_15_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:4836:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:4836:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:4837:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:4837:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:4838:5: lv_quantity_16_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getQuantityQuantityParserRuleCall_7_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_quantity_16_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"quantity",
                      						lv_quantity_16_0,
                      						"org.integratedmodelling.kactors.Kactors.Quantity");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:4856:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:4856:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:4857:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:4857:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:4858:5: lv_date_17_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getDateDateParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_date_17_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"date",
                      						lv_date_17_0,
                      						"org.integratedmodelling.kactors.Kactors.Date");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:4876:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4876:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:4877:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4877:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:4878:5: lv_expr_18_0= RULE_EXPR
                    {
                    lv_expr_18_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expr_18_0, grammarAccess.getTableClassifierAccess().getExprEXPRTerminalRuleCall_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expr",
                      						lv_expr_18_0,
                      						"org.integratedmodelling.kactors.Kactors.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKactors.g:4895:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:4895:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:4896:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:4896:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:4897:5: lv_nodata_19_0= 'unknown'
                    {
                    lv_nodata_19_0=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nodata_19_0, grammarAccess.getTableClassifierAccess().getNodataUnknownKeyword_10_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "nodata", lv_nodata_19_0, "unknown");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalKactors.g:4910:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:4910:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:4911:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:4911:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:4912:5: lv_star_20_0= '*'
                    {
                    lv_star_20_0=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_20_0, grammarAccess.getTableClassifierAccess().getStarAsteriskKeyword_11_0());
                      				
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
                case 13 :
                    // InternalKactors.g:4925:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:4925:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:4926:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:4926:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:4927:5: lv_anything_21_0= '#'
                    {
                    lv_anything_21_0=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_anything_21_0, grammarAccess.getTableClassifierAccess().getAnythingNumberSignKeyword_12_0());
                      				
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


    // $ANTLR start "entryRuleQuantity"
    // InternalKactors.g:4943:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:4943:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:4944:2: iv_ruleQuantity= ruleQuantity EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQuantityRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQuantity=ruleQuantity();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQuantity; 
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
    // $ANTLR end "entryRuleQuantity"


    // $ANTLR start "ruleQuantity"
    // InternalKactors.g:4950:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4956:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:4957:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:4957:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:4958:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:4958:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:4959:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:4959:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:4960:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_55);
            lv_value_0_0=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getQuantityRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_0_0,
              						"org.integratedmodelling.kactors.Kactors.Number");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:4977:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==70) ) {
                alt70=1;
            }
            else if ( (LA70_0==71) ) {
                alt70=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // InternalKactors.g:4978:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:4978:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:4979:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:4979:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:4980:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,70,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_over_1_0, grammarAccess.getQuantityAccess().getOverSolidusKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getQuantityRule());
                      						}
                      						setWithLastConsumed(current, "over", true, "/");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4993:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,71,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4998:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==EOF||(LA71_0>=RULE_STRING && LA71_0<=RULE_INT)||LA71_0==RULE_ANNOTATION_ID||LA71_0==31||(LA71_0>=41 && LA71_0<=58)||(LA71_0>=61 && LA71_0<=63)||(LA71_0>=67 && LA71_0<=70)||LA71_0==73||(LA71_0>=80 && LA71_0<=81)||LA71_0==88) ) {
                alt71=1;
            }
            else if ( (LA71_0==RULE_UPPERCASE_ID) ) {
                int LA71_2 = input.LA(2);

                if ( (LA71_2==EOF||(LA71_2>=RULE_STRING && LA71_2<=RULE_INT)||LA71_2==RULE_ANNOTATION_ID||LA71_2==31||(LA71_2>=41 && LA71_2<=58)||(LA71_2>=61 && LA71_2<=63)||(LA71_2>=67 && LA71_2<=70)||LA71_2==73||(LA71_2>=80 && LA71_2<=81)||LA71_2==88) ) {
                    alt71=1;
                }
                else if ( (LA71_2==74) ) {
                    alt71=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 71, 2, input);

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
                    // InternalKactors.g:4999:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:4999:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:5000:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:5000:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:5001:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQuantityAccess().getUnitUnitParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_unit_3_0=ruleUnit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQuantityRule());
                      						}
                      						set(
                      							current,
                      							"unit",
                      							lv_unit_3_0,
                      							"org.integratedmodelling.kactors.Kactors.Unit");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5019:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:5019:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:5020:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:5020:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:5021:6: lv_currency_4_0= ruleCurrency
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQuantityAccess().getCurrencyCurrencyParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_currency_4_0=ruleCurrency();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQuantityRule());
                      						}
                      						set(
                      							current,
                      							"currency",
                      							lv_currency_4_0,
                      							"org.integratedmodelling.kactors.Kactors.Currency");
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
    // $ANTLR end "ruleQuantity"


    // $ANTLR start "entryRuleAnnotation"
    // InternalKactors.g:5043:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:5043:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:5044:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:5050:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5056:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:5057:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:5057:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:5058:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:5058:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:5059:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:5059:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:5060:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_57); if (state.failed) return current;
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
              						"org.integratedmodelling.kactors.Kactors.ANNOTATION_ID");
              				
            }

            }


            }

            // InternalKactors.g:5076:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==43) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalKactors.g:5077:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:5081:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( ((LA72_0>=RULE_STRING && LA72_0<=RULE_LOWERCASE_ID)||(LA72_0>=RULE_EXPR && LA72_0<=RULE_ARGVALUE)||LA72_0==RULE_INT||LA72_0==43||(LA72_0>=53 && LA72_0<=54)||LA72_0==58||LA72_0==61||LA72_0==67||(LA72_0>=80 && LA72_0<=81)) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // InternalKactors.g:5082:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:5082:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:5083:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_20);
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
                              							"org.integratedmodelling.kactors.Kactors.ParameterList");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleLiteral"
    // InternalKactors.g:5109:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:5109:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:5110:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:5116:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_string_4_0=null;
        Token lv_boolean_6_1=null;
        Token lv_boolean_6_2=null;
        EObject lv_number_0_0 = null;

        EObject lv_from_1_0 = null;

        EObject lv_to_3_0 = null;

        EObject lv_date_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5122:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:5123:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:5123:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt75=5;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // InternalKactors.g:5124:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:5124:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:5125:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:5125:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:5126:5: lv_number_0_0= ruleNumber
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
                      						"org.integratedmodelling.kactors.Kactors.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5144:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:5144:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:5145:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:5145:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:5146:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:5146:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:5147:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
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
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_2=(Token)match(input,66,FOLLOW_47); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:5168:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:5169:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:5169:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:5170:6: lv_to_3_0= ruleNumber
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
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:5189:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:5189:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:5190:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:5190:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:5191:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:5208:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:5208:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:5209:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:5209:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:5210:5: lv_date_5_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_date_5_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLiteralRule());
                      					}
                      					set(
                      						current,
                      						"date",
                      						lv_date_5_0,
                      						"org.integratedmodelling.kactors.Kactors.Date");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:5228:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:5228:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:5229:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:5229:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:5230:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:5230:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==53) ) {
                        alt74=1;
                    }
                    else if ( (LA74_0==54) ) {
                        alt74=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 74, 0, input);

                        throw nvae;
                    }
                    switch (alt74) {
                        case 1 :
                            // InternalKactors.g:5231:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_6_1, grammarAccess.getLiteralAccess().getBooleanTrueKeyword_4_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_6_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5242:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_6_2, grammarAccess.getLiteralAccess().getBooleanFalseKeyword_4_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_6_2, null);
                              					
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


    // $ANTLR start "entryRuleParameterList"
    // InternalKactors.g:5259:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:5259:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:5260:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:5266:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5272:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:5273:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:5273:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:5274:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:5274:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:5275:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:5275:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:5276:5: lv_pairs_0_0= ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
            lv_pairs_0_0=ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParameterListRule());
              					}
              					add(
              						current,
              						"pairs",
              						lv_pairs_0_0,
              						"org.integratedmodelling.kactors.Kactors.KeyValuePair");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:5293:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==31) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // InternalKactors.g:5294:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:5294:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:5295:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,31,FOLLOW_14); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:5301:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:5302:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:5302:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:5303:6: lv_pairs_2_0= ruleKeyValuePair
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_24);
            	    lv_pairs_2_0=ruleKeyValuePair();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getParameterListRule());
            	      						}
            	      						add(
            	      							current,
            	      							"pairs",
            	      							lv_pairs_2_0,
            	      							"org.integratedmodelling.kactors.Kactors.KeyValuePair");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop76;
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
    // $ANTLR end "ruleParameterList"


    // $ANTLR start "entryRuleKeyValuePair"
    // InternalKactors.g:5325:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:5325:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:5326:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:5332:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5338:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:5339:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:5339:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:5340:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:5340:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==RULE_LOWERCASE_ID) ) {
                int LA78_1 = input.LA(2);

                if ( (LA78_1==60||LA78_1==72) ) {
                    alt78=1;
                }
            }
            switch (alt78) {
                case 1 :
                    // InternalKactors.g:5341:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:5341:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:5342:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:5342:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:5343:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_0_0, grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getKeyValuePairRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_0_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:5359:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==72) ) {
                        alt77=1;
                    }
                    else if ( (LA77_0==60) ) {
                        alt77=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 0, input);

                        throw nvae;
                    }
                    switch (alt77) {
                        case 1 :
                            // InternalKactors.g:5360:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:5360:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:5361:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:5361:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:5362:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,72,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_interactive_1_0, grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0());
                              						
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
                            // InternalKactors.g:5375:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,60,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:5381:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:5382:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:5382:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:5383:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_1_0());
              				
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
              						"org.integratedmodelling.kactors.Kactors.Value");
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


    // $ANTLR start "entryRuleUnitElement"
    // InternalKactors.g:5404:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:5404:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:5405:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:5411:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
    public final EObject ruleUnitElement() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_1=null;
        Token lv_id_0_2=null;
        Token lv_id_0_3=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_unit_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5417:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:5418:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:5418:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==RULE_LOWERCASE_ID||LA80_0==RULE_CAMELCASE_ID||LA80_0==RULE_UPPERCASE_ID) ) {
                alt80=1;
            }
            else if ( (LA80_0==43) ) {
                alt80=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }
            switch (alt80) {
                case 1 :
                    // InternalKactors.g:5419:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:5419:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:5420:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:5420:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:5421:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:5421:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt79=3;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt79=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt79=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt79=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 79, 0, input);

                        throw nvae;
                    }

                    switch (alt79) {
                        case 1 :
                            // InternalKactors.g:5422:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                              							"org.integratedmodelling.kactors.Kactors.CAMELCASE_ID");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5437:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                              							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalKactors.g:5452:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                              							"org.integratedmodelling.kactors.Kactors.UPPERCASE_ID");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5470:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:5470:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:5471:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:5475:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:5476:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:5476:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:5477:6: lv_unit_2_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
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
                      							"org.integratedmodelling.kactors.Kactors.Unit");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleMetadataKey"
    // InternalKactors.g:5503:1: entryRuleMetadataKey returns [String current=null] : iv_ruleMetadataKey= ruleMetadataKey EOF ;
    public final String entryRuleMetadataKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMetadataKey = null;


        try {
            // InternalKactors.g:5503:51: (iv_ruleMetadataKey= ruleMetadataKey EOF )
            // InternalKactors.g:5504:2: iv_ruleMetadataKey= ruleMetadataKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMetadataKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMetadataKey=ruleMetadataKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMetadataKey.getText(); 
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
    // $ANTLR end "entryRuleMetadataKey"


    // $ANTLR start "ruleMetadataKey"
    // InternalKactors.g:5510:1: ruleMetadataKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= ':' | kw= '!' ) this_LOWERCASE_ID_2= RULE_LOWERCASE_ID ) ;
    public final AntlrDatatypeRuleToken ruleMetadataKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5516:2: ( ( (kw= ':' | kw= '!' ) this_LOWERCASE_ID_2= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:5517:2: ( (kw= ':' | kw= '!' ) this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:5517:2: ( (kw= ':' | kw= '!' ) this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )
            // InternalKactors.g:5518:3: (kw= ':' | kw= '!' ) this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            {
            // InternalKactors.g:5518:3: (kw= ':' | kw= '!' )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==42) ) {
                alt81=1;
            }
            else if ( (LA81_0==73) ) {
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
                    // InternalKactors.g:5519:4: kw= ':'
                    {
                    kw=(Token)match(input,42,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getMetadataKeyAccess().getColonKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5525:4: kw= '!'
                    {
                    kw=(Token)match(input,73,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getMetadataKeyAccess().getExclamationMarkKeyword_0_1());
                      			
                    }

                    }
                    break;

            }

            this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getMetadataKeyAccess().getLOWERCASE_IDTerminalRuleCall_1());
              		
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
    // $ANTLR end "ruleMetadataKey"


    // $ANTLR start "entryRuleUnit"
    // InternalKactors.g:5542:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:5542:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:5543:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:5549:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5555:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5556:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5556:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5557:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5557:3: ()
            // InternalKactors.g:5558:4: 
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

            // InternalKactors.g:5567:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt82=2;
            alt82 = dfa82.predict(input);
            switch (alt82) {
                case 1 :
                    // InternalKactors.g:5568:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:5568:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:5569:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_60);
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
                      						"org.integratedmodelling.kactors.Kactors.UnitElement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:5586:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==56) ) {
                    int LA83_2 = input.LA(2);

                    if ( (LA83_2==RULE_LOWERCASE_ID||LA83_2==RULE_CAMELCASE_ID||LA83_2==RULE_UPPERCASE_ID||LA83_2==43) ) {
                        alt83=1;
                    }


                }
                else if ( (LA83_0==70||LA83_0==88) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalKactors.g:5587:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5587:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:5588:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:5594:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:5595:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:5595:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:5596:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_61);
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
            	      								"org.integratedmodelling.kactors.Kactors.UnitOp");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalKactors.g:5614:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:5615:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5615:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:5616:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_60);
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
            	      							"org.integratedmodelling.kactors.Kactors.UnitElement");
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
    // $ANTLR end "ruleUnit"


    // $ANTLR start "entryRuleCurrency"
    // InternalKactors.g:5638:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:5638:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:5639:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:5645:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5651:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5652:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5652:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5653:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5653:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:5654:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:5654:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:5655:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_id_0_0, grammarAccess.getCurrencyAccess().getIdUPPERCASE_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getCurrencyRule());
              					}
              					setWithLastConsumed(
              						current,
              						"id",
              						lv_id_0_0,
              						"org.integratedmodelling.kactors.Kactors.UPPERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:5671:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:5672:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,74,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:5676:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:5677:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:5677:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:5678:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              						newLeafNode(lv_year_2_0, grammarAccess.getCurrencyAccess().getYearINTTerminalRuleCall_1_1_0());
              					
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

            // InternalKactors.g:5695:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==70) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalKactors.g:5696:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5696:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:5697:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,70,FOLLOW_61); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:5703:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:5704:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5704:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:5705:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_63);
            	    lv_units_4_0=ruleUnitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getCurrencyRule());
            	      						}
            	      						add(
            	      							current,
            	      							"units",
            	      							lv_units_4_0,
            	      							"org.integratedmodelling.kactors.Kactors.UnitElement");
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
    // $ANTLR end "ruleCurrency"


    // $ANTLR start "entryRuleREL_OPERATOR"
    // InternalKactors.g:5727:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:5727:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:5728:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:5734:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:5740:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:5741:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:5741:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt85=6;
            switch ( input.LA(1) ) {
            case 75:
                {
                alt85=1;
                }
                break;
            case 76:
                {
                alt85=2;
                }
                break;
            case 60:
                {
                alt85=3;
                }
                break;
            case 77:
                {
                alt85=4;
                }
                break;
            case 78:
                {
                alt85=5;
                }
                break;
            case 79:
                {
                alt85=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // InternalKactors.g:5742:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:5742:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:5743:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:5743:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:5744:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5757:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:5757:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:5758:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:5758:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:5759:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5772:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:5772:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:5773:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:5773:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:5774:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5787:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:5787:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:5788:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:5788:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:5789:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5802:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:5802:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:5803:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:5803:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:5804:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5817:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:5817:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:5818:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:5818:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:5819:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleNumber"
    // InternalKactors.g:5835:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5835:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5836:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:5842:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
    public final EObject ruleNumber() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_negative_1_0=null;
        Token lv_real_2_0=null;
        Token lv_long_3_0=null;
        Token lv_decimal_4_0=null;
        Token lv_decimalPart_5_0=null;
        Token lv_exponential_6_1=null;
        Token lv_exponential_6_2=null;
        Token otherlv_7=null;
        Token lv_expNegative_8_0=null;
        Token lv_exp_9_0=null;


        	enterRule();

        try {
            // InternalKactors.g:5848:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5849:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5849:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5850:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5850:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt86=3;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==80) ) {
                alt86=1;
            }
            else if ( (LA86_0==81) ) {
                alt86=2;
            }
            switch (alt86) {
                case 1 :
                    // InternalKactors.g:5851:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,80,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5856:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5856:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5857:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5857:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5858:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,81,FOLLOW_10); if (state.failed) return current;
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

            // InternalKactors.g:5871:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5872:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5876:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5877:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
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

            // InternalKactors.g:5893:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==82) && (synpred186_InternalKactors())) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalKactors.g:5894:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5898:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5899:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,82,FOLLOW_65); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_long_3_0, grammarAccess.getNumberAccess().getLongLKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getNumberRule());
                      					}
                      					setWithLastConsumed(current, "long", true, "l");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:5911:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==71) ) {
                int LA88_1 = input.LA(2);

                if ( (LA88_1==RULE_INT) ) {
                    int LA88_3 = input.LA(3);

                    if ( (synpred187_InternalKactors()) ) {
                        alt88=1;
                    }
                }
            }
            switch (alt88) {
                case 1 :
                    // InternalKactors.g:5912:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5925:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5926:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5926:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5927:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5927:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5928:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,71,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimal_4_0, grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(current, "decimal", true, ".");
                      						
                    }

                    }


                    }

                    // InternalKactors.g:5940:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5941:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5941:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5942:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimalPart_5_0, grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_3_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"decimalPart",
                      								lv_decimalPart_5_0,
                      								"org.eclipse.xtext.common.Terminals.INT");
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:5960:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==83) && (synpred191_InternalKactors())) {
                alt91=1;
            }
            else if ( (LA91_0==84) && (synpred191_InternalKactors())) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalKactors.g:5961:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5987:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5988:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5988:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5989:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5989:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5990:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5990:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==83) ) {
                        alt89=1;
                    }
                    else if ( (LA89_0==84) ) {
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
                            // InternalKactors.g:5991:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,83,FOLLOW_47); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_6_1, grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_0());
                              							
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
                            // InternalKactors.g:6002:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,84,FOLLOW_47); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_6_2, grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_1());
                              							
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

                    // InternalKactors.g:6015:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt90=3;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==80) ) {
                        alt90=1;
                    }
                    else if ( (LA90_0==81) ) {
                        alt90=2;
                    }
                    switch (alt90) {
                        case 1 :
                            // InternalKactors.g:6016:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,80,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:6021:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:6021:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:6022:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:6022:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:6023:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,81,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_expNegative_8_0, grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0());
                              							
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

                    // InternalKactors.g:6036:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:6037:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:6037:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:6038:7: lv_exp_9_0= RULE_INT
                    {
                    lv_exp_9_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_exp_9_0, grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_4_0_2_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"exp",
                      								lv_exp_9_0,
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


    // $ANTLR start "entryRuleDate"
    // InternalKactors.g:6060:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:6060:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:6061:2: iv_ruleDate= ruleDate EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDate=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDate; 
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
    // $ANTLR end "entryRuleDate"


    // $ANTLR start "ruleDate"
    // InternalKactors.g:6067:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
    public final EObject ruleDate() throws RecognitionException {
        EObject current = null;

        Token lv_year_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_bc_3_0=null;
        Token otherlv_4=null;
        Token lv_month_5_0=null;
        Token otherlv_6=null;
        Token lv_day_7_0=null;
        Token lv_hour_8_0=null;
        Token otherlv_9=null;
        Token lv_min_10_0=null;
        Token otherlv_11=null;
        Token lv_sec_12_0=null;
        Token otherlv_13=null;
        Token lv_ms_14_0=null;


        	enterRule();

        try {
            // InternalKactors.g:6073:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:6074:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:6074:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:6075:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:6075:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:6076:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:6076:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:6077:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_year_0_0, grammarAccess.getDateAccess().getYearINTTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDateRule());
              					}
              					setWithLastConsumed(
              						current,
              						"year",
              						lv_year_0_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            // InternalKactors.g:6093:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt92=4;
            switch ( input.LA(1) ) {
                case 85:
                    {
                    alt92=1;
                    }
                    break;
                case 86:
                    {
                    alt92=2;
                    }
                    break;
                case 87:
                    {
                    alt92=3;
                    }
                    break;
            }

            switch (alt92) {
                case 1 :
                    // InternalKactors.g:6094:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,85,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:6099:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,86,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:6104:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:6104:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:6105:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:6105:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:6106:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,87,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_bc_3_0, grammarAccess.getDateAccess().getBcBCKeyword_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDateRule());
                      						}
                      						setWithLastConsumed(current, "bc", true, "BC");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,81,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:6123:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:6124:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:6124:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:6125:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_month_5_0, grammarAccess.getDateAccess().getMonthINTTerminalRuleCall_3_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDateRule());
              					}
              					setWithLastConsumed(
              						current,
              						"month",
              						lv_month_5_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            otherlv_6=(Token)match(input,81,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:6145:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:6146:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:6146:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:6147:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_day_7_0, grammarAccess.getDateAccess().getDayINTTerminalRuleCall_5_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDateRule());
              					}
              					setWithLastConsumed(
              						current,
              						"day",
              						lv_day_7_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            // InternalKactors.g:6163:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==RULE_INT) ) {
                int LA95_1 = input.LA(2);

                if ( (LA95_1==42) ) {
                    int LA95_3 = input.LA(3);

                    if ( (LA95_3==RULE_INT) ) {
                        int LA95_4 = input.LA(4);

                        if ( (synpred200_InternalKactors()) ) {
                            alt95=1;
                        }
                    }
                }
            }
            switch (alt95) {
                case 1 :
                    // InternalKactors.g:6164:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:6164:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:6165:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:6165:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:6166:6: lv_hour_8_0= RULE_INT
                    {
                    lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_hour_8_0, grammarAccess.getDateAccess().getHourINTTerminalRuleCall_6_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDateRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"hour",
                      							lv_hour_8_0,
                      							"org.eclipse.xtext.common.Terminals.INT");
                      					
                    }

                    }


                    }

                    otherlv_9=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:6186:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:6187:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:6187:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:6188:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_min_10_0, grammarAccess.getDateAccess().getMinINTTerminalRuleCall_6_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDateRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"min",
                      							lv_min_10_0,
                      							"org.eclipse.xtext.common.Terminals.INT");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:6204:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==42) ) {
                        int LA94_1 = input.LA(2);

                        if ( (LA94_1==RULE_INT) ) {
                            int LA94_3 = input.LA(3);

                            if ( (synpred199_InternalKactors()) ) {
                                alt94=1;
                            }
                        }
                    }
                    switch (alt94) {
                        case 1 :
                            // InternalKactors.g:6205:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:6209:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:6210:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:6210:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:6211:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_sec_12_0, grammarAccess.getDateAccess().getSecINTTerminalRuleCall_6_3_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getDateRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"sec",
                              								lv_sec_12_0,
                              								"org.eclipse.xtext.common.Terminals.INT");
                              						
                            }

                            }


                            }

                            // InternalKactors.g:6227:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt93=2;
                            int LA93_0 = input.LA(1);

                            if ( (LA93_0==71) ) {
                                alt93=1;
                            }
                            switch (alt93) {
                                case 1 :
                                    // InternalKactors.g:6228:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,71,FOLLOW_10); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:6232:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:6233:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:6233:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:6234:8: lv_ms_14_0= RULE_INT
                                    {
                                    lv_ms_14_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_ms_14_0, grammarAccess.getDateAccess().getMsINTTerminalRuleCall_6_3_2_1_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getDateRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"ms",
                                      									lv_ms_14_0,
                                      									"org.eclipse.xtext.common.Terminals.INT");
                                      							
                                    }

                                    }


                                    }


                                    }
                                    break;

                            }


                            }
                            break;

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
    // $ANTLR end "ruleDate"


    // $ANTLR start "entryRulePathName"
    // InternalKactors.g:6257:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:6257:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:6258:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:6264:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:6270:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:6271:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:6271:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:6272:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:6279:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==71) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // InternalKactors.g:6280:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,71,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop96;
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


    // $ANTLR start "entryRuleArgPathName"
    // InternalKactors.g:6297:1: entryRuleArgPathName returns [String current=null] : iv_ruleArgPathName= ruleArgPathName EOF ;
    public final String entryRuleArgPathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleArgPathName = null;


        try {
            // InternalKactors.g:6297:51: (iv_ruleArgPathName= ruleArgPathName EOF )
            // InternalKactors.g:6298:2: iv_ruleArgPathName= ruleArgPathName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgPathNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArgPathName=ruleArgPathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArgPathName.getText(); 
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
    // $ANTLR end "entryRuleArgPathName"


    // $ANTLR start "ruleArgPathName"
    // InternalKactors.g:6304:1: ruleArgPathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ARGVALUE_0= RULE_ARGVALUE | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID ) (kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleArgPathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ARGVALUE_0=null;
        Token this_LOWERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_3=null;


        	enterRule();

        try {
            // InternalKactors.g:6310:2: ( ( (this_ARGVALUE_0= RULE_ARGVALUE | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID ) (kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )? ) )
            // InternalKactors.g:6311:2: ( (this_ARGVALUE_0= RULE_ARGVALUE | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID ) (kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )? )
            {
            // InternalKactors.g:6311:2: ( (this_ARGVALUE_0= RULE_ARGVALUE | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID ) (kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )? )
            // InternalKactors.g:6312:3: (this_ARGVALUE_0= RULE_ARGVALUE | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID ) (kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )?
            {
            // InternalKactors.g:6312:3: (this_ARGVALUE_0= RULE_ARGVALUE | this_LOWERCASE_ID_1= RULE_LOWERCASE_ID )
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==RULE_ARGVALUE) ) {
                alt97=1;
            }
            else if ( (LA97_0==RULE_LOWERCASE_ID) ) {
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
                    // InternalKactors.g:6313:4: this_ARGVALUE_0= RULE_ARGVALUE
                    {
                    this_ARGVALUE_0=(Token)match(input,RULE_ARGVALUE,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_ARGVALUE_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ARGVALUE_0, grammarAccess.getArgPathNameAccess().getARGVALUETerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:6321:4: this_LOWERCASE_ID_1= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_1, grammarAccess.getArgPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:6329:3: (kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==71) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // InternalKactors.g:6330:4: kw= '.' this_LOWERCASE_ID_3= RULE_LOWERCASE_ID
                    {
                    kw=(Token)match(input,71,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getArgPathNameAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_LOWERCASE_ID_3=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_3, grammarAccess.getArgPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
                      			
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
    // $ANTLR end "ruleArgPathName"


    // $ANTLR start "entryRulePath"
    // InternalKactors.g:6347:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:6347:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:6348:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:6354:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:6360:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:6361:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:6361:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:6362:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:6362:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==RULE_LOWERCASE_ID) ) {
                alt99=1;
            }
            else if ( (LA99_0==RULE_UPPERCASE_ID) ) {
                alt99=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // InternalKactors.g:6363:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_71); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:6371:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_71); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:6379:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( ((LA102_0>=70 && LA102_0<=71)) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // InternalKactors.g:6380:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:6380:4: (kw= '.' | kw= '/' )
            	    int alt100=2;
            	    int LA100_0 = input.LA(1);

            	    if ( (LA100_0==71) ) {
            	        alt100=1;
            	    }
            	    else if ( (LA100_0==70) ) {
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
            	            // InternalKactors.g:6381:5: kw= '.'
            	            {
            	            kw=(Token)match(input,71,FOLLOW_36); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:6387:5: kw= '/'
            	            {
            	            kw=(Token)match(input,70,FOLLOW_36); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:6393:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt101=2;
            	    int LA101_0 = input.LA(1);

            	    if ( (LA101_0==RULE_LOWERCASE_ID) ) {
            	        alt101=1;
            	    }
            	    else if ( (LA101_0==RULE_UPPERCASE_ID) ) {
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
            	            // InternalKactors.g:6394:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_71); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:6402:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_71); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_UPPERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_UPPERCASE_ID_5, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;

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


    // $ANTLR start "entryRuleVersionNumber"
    // InternalKactors.g:6415:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:6415:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:6416:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:6422:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:6428:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:6429:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:6429:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:6430:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:6437:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==71) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalKactors.g:6438:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,71,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:6450:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt103=2;
                    int LA103_0 = input.LA(1);

                    if ( (LA103_0==71) ) {
                        alt103=1;
                    }
                    switch (alt103) {
                        case 1 :
                            // InternalKactors.g:6451:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,71,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_73); if (state.failed) return current;
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

            // InternalKactors.g:6465:3: (kw= '-' )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==81) ) {
                int LA105_1 = input.LA(2);

                if ( (synpred210_InternalKactors()) ) {
                    alt105=1;
                }
            }
            switch (alt105) {
                case 1 :
                    // InternalKactors.g:6466:4: kw= '-'
                    {
                    kw=(Token)match(input,81,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:6472:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt106=3;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==RULE_LOWERCASE_ID) ) {
                int LA106_1 = input.LA(2);

                if ( (synpred211_InternalKactors()) ) {
                    alt106=1;
                }
            }
            else if ( (LA106_0==RULE_UPPERCASE_ID) ) {
                alt106=2;
            }
            switch (alt106) {
                case 1 :
                    // InternalKactors.g:6473:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:6481:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:6493:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:6499:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:6500:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:6500:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt107=3;
            switch ( input.LA(1) ) {
            case 70:
                {
                alt107=1;
                }
                break;
            case 88:
                {
                alt107=2;
                }
                break;
            case 56:
                {
                alt107=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }

            switch (alt107) {
                case 1 :
                    // InternalKactors.g:6501:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:6501:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:6502:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:6509:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:6509:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:6510:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:6517:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:6517:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:6518:4: enumLiteral_2= '*'
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

    // $ANTLR start synpred1_InternalKactors
    public final void synpred1_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_preamble_1_0 = null;


        // InternalKactors.g:98:4: ( (lv_preamble_1_0= rulePreamble ) )
        // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
        {
        // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
        // InternalKactors.g:99:5: lv_preamble_1_0= rulePreamble
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_preamble_1_0=rulePreamble();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_imports_10_0 = null;

        AntlrDatatypeRuleToken lv_imports_12_0 = null;


        // InternalKactors.g:335:4: ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:335:4: ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:335:4: ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:336:5: {...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0)");
        }
        // InternalKactors.g:336:105: ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:337:6: ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0);
        // InternalKactors.g:340:9: ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) )
        // InternalKactors.g:340:10: {...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        }
        // InternalKactors.g:340:19: (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* )
        // InternalKactors.g:340:20: otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )*
        {
        otherlv_9=(Token)match(input,30,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:344:9: ( (lv_imports_10_0= rulePathName ) )
        // InternalKactors.g:345:10: (lv_imports_10_0= rulePathName )
        {
        // InternalKactors.g:345:10: (lv_imports_10_0= rulePathName )
        // InternalKactors.g:346:11: lv_imports_10_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_3_0_1_0());
          										
        }
        pushFollow(FOLLOW_24);
        lv_imports_10_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:363:9: (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )*
        loop114:
        do {
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==31) ) {
                alt114=1;
            }


            switch (alt114) {
        	case 1 :
        	    // InternalKactors.g:364:10: otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) )
        	    {
        	    otherlv_11=(Token)match(input,31,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:368:10: ( (lv_imports_12_0= rulePathName ) )
        	    // InternalKactors.g:369:11: (lv_imports_12_0= rulePathName )
        	    {
        	    // InternalKactors.g:369:11: (lv_imports_12_0= rulePathName )
        	    // InternalKactors.g:370:12: lv_imports_12_0= rulePathName
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_3_0_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_24);
        	    lv_imports_12_0=rulePathName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop114;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred13_InternalKactors

    // $ANTLR start synpred14_InternalKactors
    public final void synpred14_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        AntlrDatatypeRuleToken lv_worldview_14_0 = null;


        // InternalKactors.g:394:4: ( ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:394:4: ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:394:4: ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:395:5: {...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1)");
        }
        // InternalKactors.g:395:105: ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) )
        // InternalKactors.g:396:6: ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1);
        // InternalKactors.g:399:9: ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) )
        // InternalKactors.g:399:10: {...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "true");
        }
        // InternalKactors.g:399:19: (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) )
        // InternalKactors.g:399:20: otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) )
        {
        otherlv_13=(Token)match(input,32,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:403:9: ( (lv_worldview_14_0= rulePathName ) )
        // InternalKactors.g:404:10: (lv_worldview_14_0= rulePathName )
        {
        // InternalKactors.g:404:10: (lv_worldview_14_0= rulePathName )
        // InternalKactors.g:405:11: lv_worldview_14_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_3_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_worldview_14_0=rulePathName();

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
    // $ANTLR end synpred14_InternalKactors

    // $ANTLR start synpred16_InternalKactors
    public final void synpred16_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_15=null;
        Token lv_observable_16_0=null;
        EObject lv_observables_17_0 = null;


        // InternalKactors.g:428:4: ( ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) )
        // InternalKactors.g:428:4: ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) )
        {
        // InternalKactors.g:428:4: ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) )
        // InternalKactors.g:429:5: {...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2)");
        }
        // InternalKactors.g:429:105: ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) )
        // InternalKactors.g:430:6: ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2);
        // InternalKactors.g:433:9: ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) )
        // InternalKactors.g:433:10: {...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "true");
        }
        // InternalKactors.g:433:19: (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) )
        // InternalKactors.g:433:20: otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) )
        {
        otherlv_15=(Token)match(input,33,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:437:9: ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) )
        int alt115=2;
        int LA115_0 = input.LA(1);

        if ( (LA115_0==RULE_OBSERVABLE) ) {
            alt115=1;
        }
        else if ( (LA115_0==43) ) {
            alt115=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 115, 0, input);

            throw nvae;
        }
        switch (alt115) {
            case 1 :
                // InternalKactors.g:438:10: ( (lv_observable_16_0= RULE_OBSERVABLE ) )
                {
                // InternalKactors.g:438:10: ( (lv_observable_16_0= RULE_OBSERVABLE ) )
                // InternalKactors.g:439:11: (lv_observable_16_0= RULE_OBSERVABLE )
                {
                // InternalKactors.g:439:11: (lv_observable_16_0= RULE_OBSERVABLE )
                // InternalKactors.g:440:12: lv_observable_16_0= RULE_OBSERVABLE
                {
                lv_observable_16_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:457:10: ( (lv_observables_17_0= ruleList ) )
                {
                // InternalKactors.g:457:10: ( (lv_observables_17_0= ruleList ) )
                // InternalKactors.g:458:11: (lv_observables_17_0= ruleList )
                {
                // InternalKactors.g:458:11: (lv_observables_17_0= ruleList )
                // InternalKactors.g:459:12: lv_observables_17_0= ruleList
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_3_2_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_observables_17_0=ruleList();

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
    // $ANTLR end synpred16_InternalKactors

    // $ANTLR start synpred17_InternalKactors
    public final void synpred17_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_18=null;
        Token lv_description_19_0=null;

        // InternalKactors.g:483:4: ( ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:483:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:483:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:484:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3)");
        }
        // InternalKactors.g:484:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:485:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3);
        // InternalKactors.g:488:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        // InternalKactors.g:488:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKactors", "true");
        }
        // InternalKactors.g:488:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        // InternalKactors.g:488:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
        {
        otherlv_18=(Token)match(input,34,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:492:9: ( (lv_description_19_0= RULE_STRING ) )
        // InternalKactors.g:493:10: (lv_description_19_0= RULE_STRING )
        {
        // InternalKactors.g:493:10: (lv_description_19_0= RULE_STRING )
        // InternalKactors.g:494:11: lv_description_19_0= RULE_STRING
        {
        lv_description_19_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred17_InternalKactors

    // $ANTLR start synpred18_InternalKactors
    public final void synpred18_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_20=null;
        Token lv_permissions_21_0=null;

        // InternalKactors.g:516:4: ( ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:516:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:516:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:517:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4)");
        }
        // InternalKactors.g:517:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:518:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4);
        // InternalKactors.g:521:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        // InternalKactors.g:521:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        }
        // InternalKactors.g:521:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        // InternalKactors.g:521:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
        {
        otherlv_20=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:525:9: ( (lv_permissions_21_0= RULE_STRING ) )
        // InternalKactors.g:526:10: (lv_permissions_21_0= RULE_STRING )
        {
        // InternalKactors.g:526:10: (lv_permissions_21_0= RULE_STRING )
        // InternalKactors.g:527:11: lv_permissions_21_0= RULE_STRING
        {
        lv_permissions_21_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred18_InternalKactors

    // $ANTLR start synpred19_InternalKactors
    public final void synpred19_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_22=null;
        Token lv_authors_23_0=null;

        // InternalKactors.g:554:10: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )
        // InternalKactors.g:554:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "true");
        }
        // InternalKactors.g:554:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        // InternalKactors.g:554:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        {
        otherlv_22=(Token)match(input,36,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:558:9: ( (lv_authors_23_0= RULE_STRING ) )
        // InternalKactors.g:559:10: (lv_authors_23_0= RULE_STRING )
        {
        // InternalKactors.g:559:10: (lv_authors_23_0= RULE_STRING )
        // InternalKactors.g:560:11: lv_authors_23_0= RULE_STRING
        {
        lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred19_InternalKactors

    // $ANTLR start synpred20_InternalKactors
    public final void synpred20_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_22=null;
        Token lv_authors_23_0=null;

        // InternalKactors.g:549:4: ( ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:550:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred20_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5)");
        }
        // InternalKactors.g:550:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:551:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5);
        // InternalKactors.g:554:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        int cnt116=0;
        loop116:
        do {
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==36) && ((true))) {
                alt116=1;
            }


            switch (alt116) {
        	case 1 :
        	    // InternalKactors.g:554:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred20_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:554:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    // InternalKactors.g:554:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        	    {
        	    otherlv_22=(Token)match(input,36,FOLLOW_9); if (state.failed) return ;
        	    // InternalKactors.g:558:9: ( (lv_authors_23_0= RULE_STRING ) )
        	    // InternalKactors.g:559:10: (lv_authors_23_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:559:10: (lv_authors_23_0= RULE_STRING )
        	    // InternalKactors.g:560:11: lv_authors_23_0= RULE_STRING
        	    {
        	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_75); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt116 >= 1 ) break loop116;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(116, input);
                    throw eee;
            }
            cnt116++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred20_InternalKactors

    // $ANTLR start synpred21_InternalKactors
    public final void synpred21_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_24=null;
        AntlrDatatypeRuleToken lv_style_25_0 = null;


        // InternalKactors.g:582:4: ( ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:582:4: ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:582:4: ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:583:5: {...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6)");
        }
        // InternalKactors.g:583:105: ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) )
        // InternalKactors.g:584:6: ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6);
        // InternalKactors.g:587:9: ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) )
        // InternalKactors.g:587:10: {...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "true");
        }
        // InternalKactors.g:587:19: (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) )
        // InternalKactors.g:587:20: otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) )
        {
        otherlv_24=(Token)match(input,37,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:591:9: ( (lv_style_25_0= rulePathName ) )
        // InternalKactors.g:592:10: (lv_style_25_0= rulePathName )
        {
        // InternalKactors.g:592:10: (lv_style_25_0= rulePathName )
        // InternalKactors.g:593:11: lv_style_25_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getStylePathNameParserRuleCall_3_6_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_style_25_0=rulePathName();

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
    // $ANTLR end synpred21_InternalKactors

    // $ANTLR start synpred22_InternalKactors
    public final void synpred22_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_26=null;
        AntlrDatatypeRuleToken lv_version_27_0 = null;


        // InternalKactors.g:616:4: ( ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:616:4: ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:616:4: ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:617:5: {...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred22_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7)");
        }
        // InternalKactors.g:617:105: ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:618:6: ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7);
        // InternalKactors.g:621:9: ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:621:10: {...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred22_InternalKactors", "true");
        }
        // InternalKactors.g:621:19: (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) )
        // InternalKactors.g:621:20: otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) )
        {
        otherlv_26=(Token)match(input,38,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:625:9: ( (lv_version_27_0= ruleVersionNumber ) )
        // InternalKactors.g:626:10: (lv_version_27_0= ruleVersionNumber )
        {
        // InternalKactors.g:626:10: (lv_version_27_0= ruleVersionNumber )
        // InternalKactors.g:627:11: lv_version_27_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_3_7_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_27_0=ruleVersionNumber();

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
    // $ANTLR end synpred22_InternalKactors

    // $ANTLR start synpred24_InternalKactors
    public final void synpred24_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_28=null;
        Token lv_createcomment_30_0=null;
        EObject lv_created_29_0 = null;


        // InternalKactors.g:650:4: ( ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:650:4: ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:650:4: ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:651:5: {...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred24_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8)");
        }
        // InternalKactors.g:651:105: ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:652:6: ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8);
        // InternalKactors.g:655:9: ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) )
        // InternalKactors.g:655:10: {...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred24_InternalKactors", "true");
        }
        // InternalKactors.g:655:19: (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? )
        // InternalKactors.g:655:20: otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )?
        {
        otherlv_28=(Token)match(input,39,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:659:9: ( (lv_created_29_0= ruleDate ) )
        // InternalKactors.g:660:10: (lv_created_29_0= ruleDate )
        {
        // InternalKactors.g:660:10: (lv_created_29_0= ruleDate )
        // InternalKactors.g:661:11: lv_created_29_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_3_8_1_0());
          										
        }
        pushFollow(FOLLOW_76);
        lv_created_29_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:678:9: ( (lv_createcomment_30_0= RULE_STRING ) )?
        int alt117=2;
        int LA117_0 = input.LA(1);

        if ( (LA117_0==RULE_STRING) ) {
            alt117=1;
        }
        switch (alt117) {
            case 1 :
                // InternalKactors.g:679:10: (lv_createcomment_30_0= RULE_STRING )
                {
                // InternalKactors.g:679:10: (lv_createcomment_30_0= RULE_STRING )
                // InternalKactors.g:680:11: lv_createcomment_30_0= RULE_STRING
                {
                lv_createcomment_30_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred24_InternalKactors

    // $ANTLR start synpred26_InternalKactors
    public final void synpred26_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_31=null;
        Token lv_modcomment_33_0=null;
        EObject lv_modified_32_0 = null;


        // InternalKactors.g:702:4: ( ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:702:4: ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:702:4: ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:703:5: {...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred26_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9)");
        }
        // InternalKactors.g:703:105: ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:704:6: ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9);
        // InternalKactors.g:707:9: ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) )
        // InternalKactors.g:707:10: {...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred26_InternalKactors", "true");
        }
        // InternalKactors.g:707:19: (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? )
        // InternalKactors.g:707:20: otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )?
        {
        otherlv_31=(Token)match(input,40,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:711:9: ( (lv_modified_32_0= ruleDate ) )
        // InternalKactors.g:712:10: (lv_modified_32_0= ruleDate )
        {
        // InternalKactors.g:712:10: (lv_modified_32_0= ruleDate )
        // InternalKactors.g:713:11: lv_modified_32_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_3_9_1_0());
          										
        }
        pushFollow(FOLLOW_76);
        lv_modified_32_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:730:9: ( (lv_modcomment_33_0= RULE_STRING ) )?
        int alt118=2;
        int LA118_0 = input.LA(1);

        if ( (LA118_0==RULE_STRING) ) {
            alt118=1;
        }
        switch (alt118) {
            case 1 :
                // InternalKactors.g:731:10: (lv_modcomment_33_0= RULE_STRING )
                {
                // InternalKactors.g:731:10: (lv_modcomment_33_0= RULE_STRING )
                // InternalKactors.g:732:11: lv_modcomment_33_0= RULE_STRING
                {
                lv_modcomment_33_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred26_InternalKactors

    // $ANTLR start synpred33_InternalKactors
    public final void synpred33_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:1036:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:1036:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,43,FOLLOW_19); if (state.failed) return ;
        // InternalKactors.g:1040:6: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt120=2;
        int LA120_0 = input.LA(1);

        if ( ((LA120_0>=RULE_STRING && LA120_0<=RULE_LOWERCASE_ID)||(LA120_0>=RULE_EXPR && LA120_0<=RULE_ARGVALUE)||LA120_0==RULE_INT||LA120_0==43||(LA120_0>=53 && LA120_0<=54)||LA120_0==58||LA120_0==61||LA120_0==67||(LA120_0>=80 && LA120_0<=81)) ) {
            alt120=1;
        }
        switch (alt120) {
            case 1 :
                // InternalKactors.g:1041:7: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:1041:7: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:1042:8: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getMessageCallAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                  							
                }
                pushFollow(FOLLOW_20);
                lv_parameters_2_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred33_InternalKactors

    // $ANTLR start synpred35_InternalKactors
    public final void synpred35_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        EObject lv_actions_6_0 = null;


        // InternalKactors.g:1087:4: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )
        // InternalKactors.g:1087:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
        {
        otherlv_5=(Token)match(input,42,FOLLOW_22); if (state.failed) return ;
        // InternalKactors.g:1091:4: ( (lv_actions_6_0= ruleActions ) )
        // InternalKactors.g:1092:5: (lv_actions_6_0= ruleActions )
        {
        // InternalKactors.g:1092:5: (lv_actions_6_0= ruleActions )
        // InternalKactors.g:1093:6: lv_actions_6_0= ruleActions
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMessageCallAccess().getActionsActionsParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_actions_6_0=ruleActions();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred35_InternalKactors

    // $ANTLR start synpred36_InternalKactors
    public final void synpred36_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_2_0 = null;


        // InternalKactors.g:1145:4: ( (lv_body_2_0= ruleMessageBody ) )
        // InternalKactors.g:1145:4: (lv_body_2_0= ruleMessageBody )
        {
        // InternalKactors.g:1145:4: (lv_body_2_0= ruleMessageBody )
        // InternalKactors.g:1146:5: lv_body_2_0= ruleMessageBody
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementGroupAccess().getBodyMessageBodyParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_body_2_0=ruleMessageBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred36_InternalKactors

    // $ANTLR start synpred37_InternalKactors
    public final void synpred37_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_metadata_4_0 = null;


        // InternalKactors.g:1168:4: ( (lv_metadata_4_0= ruleMetadata ) )
        // InternalKactors.g:1168:4: (lv_metadata_4_0= ruleMetadata )
        {
        // InternalKactors.g:1168:4: (lv_metadata_4_0= ruleMetadata )
        // InternalKactors.g:1169:5: lv_metadata_4_0= ruleMetadata
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementGroupAccess().getMetadataMetadataParserRuleCall_4_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_metadata_4_0=ruleMetadata();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred37_InternalKactors

    // $ANTLR start synpred38_InternalKactors
    public final void synpred38_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        EObject lv_actions_6_0 = null;


        // InternalKactors.g:1187:4: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )
        // InternalKactors.g:1187:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
        {
        otherlv_5=(Token)match(input,42,FOLLOW_22); if (state.failed) return ;
        // InternalKactors.g:1191:4: ( (lv_actions_6_0= ruleActions ) )
        // InternalKactors.g:1192:5: (lv_actions_6_0= ruleActions )
        {
        // InternalKactors.g:1192:5: (lv_actions_6_0= ruleActions )
        // InternalKactors.g:1193:6: lv_actions_6_0= ruleActions
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getStatementGroupAccess().getActionsActionsParserRuleCall_5_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_actions_6_0=ruleActions();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred38_InternalKactors

    // $ANTLR start synpred39_InternalKactors
    public final void synpred39_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_pairs_0_0 = null;


        // InternalKactors.g:1230:3: ( (lv_pairs_0_0= ruleMetadataPair ) )
        // InternalKactors.g:1230:3: (lv_pairs_0_0= ruleMetadataPair )
        {
        // InternalKactors.g:1230:3: (lv_pairs_0_0= ruleMetadataPair )
        // InternalKactors.g:1231:4: lv_pairs_0_0= ruleMetadataPair
        {
        if ( state.backtracking==0 ) {

          				newCompositeNode(grammarAccess.getMetadataAccess().getPairsMetadataPairParserRuleCall_0());
          			
        }
        pushFollow(FOLLOW_2);
        lv_pairs_0_0=ruleMetadataPair();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred39_InternalKactors

    // $ANTLR start synpred40_InternalKactors
    public final void synpred40_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_1_0 = null;


        // InternalKactors.g:1286:4: ( (lv_value_1_0= ruleValue ) )
        // InternalKactors.g:1286:4: (lv_value_1_0= ruleValue )
        {
        // InternalKactors.g:1286:4: (lv_value_1_0= ruleValue )
        // InternalKactors.g:1287:5: lv_value_1_0= ruleValue
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getMetadataPairAccess().getValueValueParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_value_1_0=ruleValue();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred40_InternalKactors

    // $ANTLR start synpred41_InternalKactors
    public final void synpred41_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_next_1_0 = null;


        // InternalKactors.g:1343:4: ( (lv_next_1_0= ruleNextStatement ) )
        // InternalKactors.g:1343:4: (lv_next_1_0= ruleNextStatement )
        {
        // InternalKactors.g:1343:4: (lv_next_1_0= ruleNextStatement )
        // InternalKactors.g:1344:5: lv_next_1_0= ruleNextStatement
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementListAccess().getNextNextStatementParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_next_1_0=ruleNextStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred41_InternalKactors

    // $ANTLR start synpred43_InternalKactors
    public final void synpred43_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_1_0 = null;


        // InternalKactors.g:1400:3: ( ( (lv_group_1_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1400:3: ( (lv_group_1_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1400:3: ( (lv_group_1_0= ruleStatementGroup ) )
        // InternalKactors.g:1401:4: (lv_group_1_0= ruleStatementGroup )
        {
        // InternalKactors.g:1401:4: (lv_group_1_0= ruleStatementGroup )
        // InternalKactors.g:1402:5: lv_group_1_0= ruleStatementGroup
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementGroupParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_group_1_0=ruleStatementGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred43_InternalKactors

    // $ANTLR start synpred44_InternalKactors
    public final void synpred44_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_2_0 = null;


        // InternalKactors.g:1420:3: ( ( (lv_verb_2_0= ruleMessageCall ) ) )
        // InternalKactors.g:1420:3: ( (lv_verb_2_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1420:3: ( (lv_verb_2_0= ruleMessageCall ) )
        // InternalKactors.g:1421:4: (lv_verb_2_0= ruleMessageCall )
        {
        // InternalKactors.g:1421:4: (lv_verb_2_0= ruleMessageCall )
        // InternalKactors.g:1422:5: lv_verb_2_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getVerbMessageCallParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_2_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred44_InternalKactors

    // $ANTLR start synpred51_InternalKactors
    public final void synpred51_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_2_0 = null;


        // InternalKactors.g:1602:4: ( ( (lv_verb_2_0= ruleMessageCall ) ) )
        // InternalKactors.g:1602:4: ( (lv_verb_2_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1602:4: ( (lv_verb_2_0= ruleMessageCall ) )
        // InternalKactors.g:1603:5: (lv_verb_2_0= ruleMessageCall )
        {
        // InternalKactors.g:1603:5: (lv_verb_2_0= ruleMessageCall )
        // InternalKactors.g:1604:6: lv_verb_2_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbMessageCallParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_verb_2_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred51_InternalKactors

    // $ANTLR start synpred52_InternalKactors
    public final void synpred52_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_3_0 = null;


        // InternalKactors.g:1622:4: ( ( (lv_group_3_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1622:4: ( (lv_group_3_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1622:4: ( (lv_group_3_0= ruleStatementGroup ) )
        // InternalKactors.g:1623:5: (lv_group_3_0= ruleStatementGroup )
        {
        // InternalKactors.g:1623:5: (lv_group_3_0= ruleStatementGroup )
        // InternalKactors.g:1624:6: lv_group_3_0= ruleStatementGroup
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupStatementGroupParserRuleCall_1_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_group_3_0=ruleStatementGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred52_InternalKactors

    // $ANTLR start synpred58_InternalKactors
    public final void synpred58_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfBody_6_0 = null;


        // InternalKactors.g:1882:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )
        // InternalKactors.g:1882:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        {
        otherlv_3=(Token)match(input,47,FOLLOW_27); if (state.failed) return ;
        otherlv_4=(Token)match(input,46,FOLLOW_25); if (state.failed) return ;
        // InternalKactors.g:1890:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:1891:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:1891:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:1892:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_14); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1908:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        // InternalKactors.g:1909:5: (lv_elseIfBody_6_0= ruleStatementBody )
        {
        // InternalKactors.g:1909:5: (lv_elseIfBody_6_0= ruleStatementBody )
        // InternalKactors.g:1910:6: lv_elseIfBody_6_0= ruleStatementBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfBodyStatementBodyParserRuleCall_3_3_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseIfBody_6_0=ruleStatementBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred58_InternalKactors

    // $ANTLR start synpred59_InternalKactors
    public final void synpred59_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:1929:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )
        // InternalKactors.g:1929:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
        {
        otherlv_7=(Token)match(input,47,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:1933:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
        // InternalKactors.g:1934:5: (lv_elseCall_8_0= ruleStatementBody )
        {
        // InternalKactors.g:1934:5: (lv_elseCall_8_0= ruleStatementBody )
        // InternalKactors.g:1935:6: lv_elseCall_8_0= ruleStatementBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallStatementBodyParserRuleCall_4_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseCall_8_0=ruleStatementBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred59_InternalKactors

    // $ANTLR start synpred60_InternalKactors
    public final void synpred60_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1972:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1972:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1972:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1973:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1973:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1974:5: lv_verb_0_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementBodyAccess().getVerbMessageCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_0_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred60_InternalKactors

    // $ANTLR start synpred61_InternalKactors
    public final void synpred61_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_1_0 = null;


        // InternalKactors.g:1992:3: ( ( (lv_value_1_0= ruleValue ) ) )
        // InternalKactors.g:1992:3: ( (lv_value_1_0= ruleValue ) )
        {
        // InternalKactors.g:1992:3: ( (lv_value_1_0= ruleValue ) )
        // InternalKactors.g:1993:4: (lv_value_1_0= ruleValue )
        {
        // InternalKactors.g:1993:4: (lv_value_1_0= ruleValue )
        // InternalKactors.g:1994:5: lv_value_1_0= ruleValue
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementBodyAccess().getValueValueParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_value_1_0=ruleValue();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred61_InternalKactors

    // $ANTLR start synpred63_InternalKactors
    public final void synpred63_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_0_0 = null;


        // InternalKactors.g:2259:3: ( ( (lv_match_0_0= ruleMatch ) ) )
        // InternalKactors.g:2259:3: ( (lv_match_0_0= ruleMatch ) )
        {
        // InternalKactors.g:2259:3: ( (lv_match_0_0= ruleMatch ) )
        // InternalKactors.g:2260:4: (lv_match_0_0= ruleMatch )
        {
        // InternalKactors.g:2260:4: (lv_match_0_0= ruleMatch )
        // InternalKactors.g:2261:5: lv_match_0_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_match_0_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred63_InternalKactors

    // $ANTLR start synpred65_InternalKactors
    public final void synpred65_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;


        // InternalKactors.g:2279:3: ( (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) )
        // InternalKactors.g:2279:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        {
        // InternalKactors.g:2279:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        // InternalKactors.g:2280:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
        {
        otherlv_1=(Token)match(input,43,FOLLOW_30); if (state.failed) return ;
        // InternalKactors.g:2284:4: ( (lv_matches_2_0= ruleMatch ) )
        // InternalKactors.g:2285:5: (lv_matches_2_0= ruleMatch )
        {
        // InternalKactors.g:2285:5: (lv_matches_2_0= ruleMatch )
        // InternalKactors.g:2286:6: lv_matches_2_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_31);
        lv_matches_2_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2303:4: ( (lv_matches_3_0= ruleMatch ) )*
        loop123:
        do {
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( ((LA123_0>=RULE_STRING && LA123_0<=RULE_LOWERCASE_ID)||LA123_0==RULE_EXPR||(LA123_0>=RULE_CAMELCASE_ID && LA123_0<=RULE_INT)||LA123_0==43||LA123_0==51||(LA123_0>=53 && LA123_0<=57)||(LA123_0>=80 && LA123_0<=81)) ) {
                alt123=1;
            }


            switch (alt123) {
        	case 1 :
        	    // InternalKactors.g:2304:5: (lv_matches_3_0= ruleMatch )
        	    {
        	    // InternalKactors.g:2304:5: (lv_matches_3_0= ruleMatch )
        	    // InternalKactors.g:2305:6: lv_matches_3_0= ruleMatch
        	    {
        	    if ( state.backtracking==0 ) {

        	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_2_0());
        	      					
        	    }
        	    pushFollow(FOLLOW_31);
        	    lv_matches_3_0=ruleMatch();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }
        	    break;

        	default :
        	    break loop123;
            }
        } while (true);

        otherlv_4=(Token)match(input,44,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred65_InternalKactors

    // $ANTLR start synpred66_InternalKactors
    public final void synpred66_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_statement_5_0 = null;


        // InternalKactors.g:2328:3: ( ( (lv_statement_5_0= ruleStatement ) ) )
        // InternalKactors.g:2328:3: ( (lv_statement_5_0= ruleStatement ) )
        {
        // InternalKactors.g:2328:3: ( (lv_statement_5_0= ruleStatement ) )
        // InternalKactors.g:2329:4: (lv_statement_5_0= ruleStatement )
        {
        // InternalKactors.g:2329:4: (lv_statement_5_0= ruleStatement )
        // InternalKactors.g:2330:5: lv_statement_5_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getStatementStatementParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_statement_5_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred66_InternalKactors

    // $ANTLR start synpred67_InternalKactors
    public final void synpred67_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_tree_0_0 = null;


        // InternalKactors.g:2396:3: ( ( (lv_tree_0_0= ruleTree ) ) )
        // InternalKactors.g:2396:3: ( (lv_tree_0_0= ruleTree ) )
        {
        // InternalKactors.g:2396:3: ( (lv_tree_0_0= ruleTree ) )
        // InternalKactors.g:2397:4: (lv_tree_0_0= ruleTree )
        {
        // InternalKactors.g:2397:4: (lv_tree_0_0= ruleTree )
        // InternalKactors.g:2398:5: lv_tree_0_0= ruleTree
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getTreeTreeParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_tree_0_0=ruleTree();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred67_InternalKactors

    // $ANTLR start synpred68_InternalKactors
    public final void synpred68_InternalKactors_fragment() throws RecognitionException {   
        Token lv_argvalue_1_0=null;

        // InternalKactors.g:2416:3: ( ( (lv_argvalue_1_0= RULE_ARGVALUE ) ) )
        // InternalKactors.g:2416:3: ( (lv_argvalue_1_0= RULE_ARGVALUE ) )
        {
        // InternalKactors.g:2416:3: ( (lv_argvalue_1_0= RULE_ARGVALUE ) )
        // InternalKactors.g:2417:4: (lv_argvalue_1_0= RULE_ARGVALUE )
        {
        // InternalKactors.g:2417:4: (lv_argvalue_1_0= RULE_ARGVALUE )
        // InternalKactors.g:2418:5: lv_argvalue_1_0= RULE_ARGVALUE
        {
        lv_argvalue_1_0=(Token)match(input,RULE_ARGVALUE,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred68_InternalKactors

    // $ANTLR start synpred69_InternalKactors
    public final void synpred69_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_literal_2_0 = null;


        // InternalKactors.g:2435:3: ( ( (lv_literal_2_0= ruleLiteral ) ) )
        // InternalKactors.g:2435:3: ( (lv_literal_2_0= ruleLiteral ) )
        {
        // InternalKactors.g:2435:3: ( (lv_literal_2_0= ruleLiteral ) )
        // InternalKactors.g:2436:4: (lv_literal_2_0= ruleLiteral )
        {
        // InternalKactors.g:2436:4: (lv_literal_2_0= ruleLiteral )
        // InternalKactors.g:2437:5: lv_literal_2_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_literal_2_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred69_InternalKactors

    // $ANTLR start synpred70_InternalKactors
    public final void synpred70_InternalKactors_fragment() throws RecognitionException {   
        AntlrDatatypeRuleToken lv_urn_3_0 = null;


        // InternalKactors.g:2455:3: ( ( (lv_urn_3_0= ruleUrnId ) ) )
        // InternalKactors.g:2455:3: ( (lv_urn_3_0= ruleUrnId ) )
        {
        // InternalKactors.g:2455:3: ( (lv_urn_3_0= ruleUrnId ) )
        // InternalKactors.g:2456:4: (lv_urn_3_0= ruleUrnId )
        {
        // InternalKactors.g:2456:4: (lv_urn_3_0= ruleUrnId )
        // InternalKactors.g:2457:5: lv_urn_3_0= ruleUrnId
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnIdParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_urn_3_0=ruleUrnId();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred70_InternalKactors

    // $ANTLR start synpred71_InternalKactors
    public final void synpred71_InternalKactors_fragment() throws RecognitionException {   
        AntlrDatatypeRuleToken lv_id_4_0 = null;


        // InternalKactors.g:2475:3: ( ( (lv_id_4_0= rulePathName ) ) )
        // InternalKactors.g:2475:3: ( (lv_id_4_0= rulePathName ) )
        {
        // InternalKactors.g:2475:3: ( (lv_id_4_0= rulePathName ) )
        // InternalKactors.g:2476:4: (lv_id_4_0= rulePathName )
        {
        // InternalKactors.g:2476:4: (lv_id_4_0= rulePathName )
        // InternalKactors.g:2477:5: lv_id_4_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getIdPathNameParserRuleCall_4_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_id_4_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred71_InternalKactors

    // $ANTLR start synpred72_InternalKactors
    public final void synpred72_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_5_0 = null;


        // InternalKactors.g:2495:3: ( ( (lv_list_5_0= ruleList ) ) )
        // InternalKactors.g:2495:3: ( (lv_list_5_0= ruleList ) )
        {
        // InternalKactors.g:2495:3: ( (lv_list_5_0= ruleList ) )
        // InternalKactors.g:2496:4: (lv_list_5_0= ruleList )
        {
        // InternalKactors.g:2496:4: (lv_list_5_0= ruleList )
        // InternalKactors.g:2497:5: lv_list_5_0= ruleList
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_5_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_list_5_0=ruleList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred72_InternalKactors

    // $ANTLR start synpred73_InternalKactors
    public final void synpred73_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_map_6_0 = null;


        // InternalKactors.g:2515:3: ( ( (lv_map_6_0= ruleMap ) ) )
        // InternalKactors.g:2515:3: ( (lv_map_6_0= ruleMap ) )
        {
        // InternalKactors.g:2515:3: ( (lv_map_6_0= ruleMap ) )
        // InternalKactors.g:2516:4: (lv_map_6_0= ruleMap )
        {
        // InternalKactors.g:2516:4: (lv_map_6_0= ruleMap )
        // InternalKactors.g:2517:5: lv_map_6_0= ruleMap
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getMapMapParserRuleCall_6_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_map_6_0=ruleMap();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred73_InternalKactors

    // $ANTLR start synpred74_InternalKactors
    public final void synpred74_InternalKactors_fragment() throws RecognitionException {   
        Token lv_observable_7_0=null;

        // InternalKactors.g:2535:3: ( ( (lv_observable_7_0= RULE_OBSERVABLE ) ) )
        // InternalKactors.g:2535:3: ( (lv_observable_7_0= RULE_OBSERVABLE ) )
        {
        // InternalKactors.g:2535:3: ( (lv_observable_7_0= RULE_OBSERVABLE ) )
        // InternalKactors.g:2536:4: (lv_observable_7_0= RULE_OBSERVABLE )
        {
        // InternalKactors.g:2536:4: (lv_observable_7_0= RULE_OBSERVABLE )
        // InternalKactors.g:2537:5: lv_observable_7_0= RULE_OBSERVABLE
        {
        lv_observable_7_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred74_InternalKactors

    // $ANTLR start synpred75_InternalKactors
    public final void synpred75_InternalKactors_fragment() throws RecognitionException {   
        Token lv_expression_8_0=null;

        // InternalKactors.g:2554:3: ( ( (lv_expression_8_0= RULE_EXPR ) ) )
        // InternalKactors.g:2554:3: ( (lv_expression_8_0= RULE_EXPR ) )
        {
        // InternalKactors.g:2554:3: ( (lv_expression_8_0= RULE_EXPR ) )
        // InternalKactors.g:2555:4: (lv_expression_8_0= RULE_EXPR )
        {
        // InternalKactors.g:2555:4: (lv_expression_8_0= RULE_EXPR )
        // InternalKactors.g:2556:5: lv_expression_8_0= RULE_EXPR
        {
        lv_expression_8_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred75_InternalKactors

    // $ANTLR start synpred76_InternalKactors
    public final void synpred76_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_table_9_0 = null;


        // InternalKactors.g:2573:3: ( ( (lv_table_9_0= ruleLookupTable ) ) )
        // InternalKactors.g:2573:3: ( (lv_table_9_0= ruleLookupTable ) )
        {
        // InternalKactors.g:2573:3: ( (lv_table_9_0= ruleLookupTable ) )
        // InternalKactors.g:2574:4: (lv_table_9_0= ruleLookupTable )
        {
        // InternalKactors.g:2574:4: (lv_table_9_0= ruleLookupTable )
        // InternalKactors.g:2575:5: lv_table_9_0= ruleLookupTable
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getTableLookupTableParserRuleCall_9_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_table_9_0=ruleLookupTable();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred76_InternalKactors

    // $ANTLR start synpred78_InternalKactors
    public final void synpred78_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_literal_1_0 = null;


        // InternalKactors.g:2650:3: ( ( (lv_literal_1_0= ruleLiteral ) ) )
        // InternalKactors.g:2650:3: ( (lv_literal_1_0= ruleLiteral ) )
        {
        // InternalKactors.g:2650:3: ( (lv_literal_1_0= ruleLiteral ) )
        // InternalKactors.g:2651:4: (lv_literal_1_0= ruleLiteral )
        {
        // InternalKactors.g:2651:4: (lv_literal_1_0= ruleLiteral )
        // InternalKactors.g:2652:5: lv_literal_1_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getLiteralLiteralParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_literal_1_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred78_InternalKactors

    // $ANTLR start synpred79_InternalKactors
    public final void synpred79_InternalKactors_fragment() throws RecognitionException {   
        AntlrDatatypeRuleToken lv_id_2_0 = null;


        // InternalKactors.g:2670:3: ( ( (lv_id_2_0= rulePathName ) ) )
        // InternalKactors.g:2670:3: ( (lv_id_2_0= rulePathName ) )
        {
        // InternalKactors.g:2670:3: ( (lv_id_2_0= rulePathName ) )
        // InternalKactors.g:2671:4: (lv_id_2_0= rulePathName )
        {
        // InternalKactors.g:2671:4: (lv_id_2_0= rulePathName )
        // InternalKactors.g:2672:5: lv_id_2_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getIdPathNameParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_id_2_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred79_InternalKactors

    // $ANTLR start synpred80_InternalKactors
    public final void synpred80_InternalKactors_fragment() throws RecognitionException {   
        AntlrDatatypeRuleToken lv_urn_3_0 = null;


        // InternalKactors.g:2690:3: ( ( (lv_urn_3_0= ruleUrnId ) ) )
        // InternalKactors.g:2690:3: ( (lv_urn_3_0= ruleUrnId ) )
        {
        // InternalKactors.g:2690:3: ( (lv_urn_3_0= ruleUrnId ) )
        // InternalKactors.g:2691:4: (lv_urn_3_0= ruleUrnId )
        {
        // InternalKactors.g:2691:4: (lv_urn_3_0= ruleUrnId )
        // InternalKactors.g:2692:5: lv_urn_3_0= ruleUrnId
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueWithoutTreeAccess().getUrnUrnIdParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_urn_3_0=ruleUrnId();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred80_InternalKactors

    // $ANTLR start synpred88_InternalKactors
    public final void synpred88_InternalKactors_fragment() throws RecognitionException {   
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        EObject lv_body_5_0 = null;


        // InternalKactors.g:2890:3: ( ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2890:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2890:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        // InternalKactors.g:2891:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
        {
        // InternalKactors.g:2891:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
        // InternalKactors.g:2892:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        {
        // InternalKactors.g:2892:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        // InternalKactors.g:2893:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        {
        // InternalKactors.g:2893:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        int alt124=2;
        int LA124_0 = input.LA(1);

        if ( (LA124_0==53) ) {
            alt124=1;
        }
        else if ( (LA124_0==54) ) {
            alt124=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 124, 0, input);

            throw nvae;
        }
        switch (alt124) {
            case 1 :
                // InternalKactors.g:2894:7: lv_boolean_3_1= 'true'
                {
                lv_boolean_3_1=(Token)match(input,53,FOLLOW_32); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:2905:7: lv_boolean_3_2= 'false'
                {
                lv_boolean_3_2=(Token)match(input,54,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }


        }


        }

        otherlv_4=(Token)match(input,52,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:2922:4: ( (lv_body_5_0= ruleStatementList ) )
        // InternalKactors.g:2923:5: (lv_body_5_0= ruleStatementList )
        {
        // InternalKactors.g:2923:5: (lv_body_5_0= ruleStatementList )
        // InternalKactors.g:2924:6: lv_body_5_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_1_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_5_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred88_InternalKactors

    // $ANTLR start synpred92_InternalKactors
    public final void synpred92_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;


        // InternalKactors.g:3075:3: ( ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) )
        // InternalKactors.g:3075:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:3075:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        // InternalKactors.g:3076:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
        {
        // InternalKactors.g:3076:4: ( (lv_literal_15_0= ruleLiteral ) )
        // InternalKactors.g:3077:5: (lv_literal_15_0= ruleLiteral )
        {
        // InternalKactors.g:3077:5: (lv_literal_15_0= ruleLiteral )
        // InternalKactors.g:3078:6: lv_literal_15_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_5_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_literal_15_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_16=(Token)match(input,52,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:3099:4: ( (lv_body_17_0= ruleStatementList ) )
        // InternalKactors.g:3100:5: (lv_body_17_0= ruleStatementList )
        {
        // InternalKactors.g:3100:5: (lv_body_17_0= ruleStatementList )
        // InternalKactors.g:3101:6: lv_body_17_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_5_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_17_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred92_InternalKactors

    // $ANTLR start synpred95_InternalKactors
    public final void synpred95_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_26=null;
        EObject lv_quantity_25_0 = null;

        EObject lv_body_27_0 = null;


        // InternalKactors.g:3214:3: ( ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) )
        // InternalKactors.g:3214:3: ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:3214:3: ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) )
        // InternalKactors.g:3215:4: ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) )
        {
        // InternalKactors.g:3215:4: ( (lv_quantity_25_0= ruleQuantity ) )
        // InternalKactors.g:3216:5: (lv_quantity_25_0= ruleQuantity )
        {
        // InternalKactors.g:3216:5: (lv_quantity_25_0= ruleQuantity )
        // InternalKactors.g:3217:6: lv_quantity_25_0= ruleQuantity
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_8_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_quantity_25_0=ruleQuantity();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_26=(Token)match(input,52,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:3238:4: ( (lv_body_27_0= ruleStatementList ) )
        // InternalKactors.g:3239:5: (lv_body_27_0= ruleStatementList )
        {
        // InternalKactors.g:3239:5: (lv_body_27_0= ruleStatementList )
        // InternalKactors.g:3240:6: lv_body_27_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_27_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred95_InternalKactors

    // $ANTLR start synpred101_InternalKactors
    public final void synpred101_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;
        AntlrDatatypeRuleToken this_VersionNumber_10 = null;


        // InternalKactors.g:3514:4: (kw= ':' this_VersionNumber_10= ruleVersionNumber )
        // InternalKactors.g:3514:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
        {
        kw=(Token)match(input,42,FOLLOW_10); if (state.failed) return ;
        pushFollow(FOLLOW_2);
        this_VersionNumber_10=ruleVersionNumber();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred101_InternalKactors

    // $ANTLR start synpred112_InternalKactors
    public final void synpred112_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_2_0 = null;


        // InternalKactors.g:3834:5: ( ( (lv_value_2_0= ruleValueWithoutTree ) ) )
        // InternalKactors.g:3834:5: ( (lv_value_2_0= ruleValueWithoutTree ) )
        {
        // InternalKactors.g:3834:5: ( (lv_value_2_0= ruleValueWithoutTree ) )
        // InternalKactors.g:3835:6: (lv_value_2_0= ruleValueWithoutTree )
        {
        // InternalKactors.g:3835:6: (lv_value_2_0= ruleValueWithoutTree )
        // InternalKactors.g:3836:7: lv_value_2_0= ruleValueWithoutTree
        {
        if ( state.backtracking==0 ) {

          							newCompositeNode(grammarAccess.getTreeAccess().getValueValueWithoutTreeParserRuleCall_1_1_0_0());
          						
        }
        pushFollow(FOLLOW_2);
        lv_value_2_0=ruleValueWithoutTree();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred112_InternalKactors

    // $ANTLR start synpred118_InternalKactors
    public final void synpred118_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4039:5: ( 'to' )
        // InternalKactors.g:4039:6: 'to'
        {
        match(input,66,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred118_InternalKactors

    // $ANTLR start synpred145_InternalKactors
    public final void synpred145_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4758:5: ( 'to' )
        // InternalKactors.g:4758:6: 'to'
        {
        match(input,66,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred145_InternalKactors

    // $ANTLR start synpred160_InternalKactors
    public final void synpred160_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:5124:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:5124:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:5124:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:5125:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:5125:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:5126:5: lv_number_0_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_number_0_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred160_InternalKactors

    // $ANTLR start synpred163_InternalKactors
    public final void synpred163_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:5208:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:5208:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:5208:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:5209:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:5209:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:5210:5: lv_date_5_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_date_5_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred163_InternalKactors

    // $ANTLR start synpred173_InternalKactors
    public final void synpred173_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_root_1_0 = null;


        // InternalKactors.g:5568:4: ( (lv_root_1_0= ruleUnitElement ) )
        // InternalKactors.g:5568:4: (lv_root_1_0= ruleUnitElement )
        {
        // InternalKactors.g:5568:4: (lv_root_1_0= ruleUnitElement )
        // InternalKactors.g:5569:5: lv_root_1_0= ruleUnitElement
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
    // $ANTLR end synpred173_InternalKactors

    // $ANTLR start synpred185_InternalKactors
    public final void synpred185_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5872:4: ( ( RULE_INT ) )
        // InternalKactors.g:5872:5: ( RULE_INT )
        {
        // InternalKactors.g:5872:5: ( RULE_INT )
        // InternalKactors.g:5873:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred185_InternalKactors

    // $ANTLR start synpred186_InternalKactors
    public final void synpred186_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5894:4: ( ( 'l' ) )
        // InternalKactors.g:5894:5: ( 'l' )
        {
        // InternalKactors.g:5894:5: ( 'l' )
        // InternalKactors.g:5895:5: 'l'
        {
        match(input,82,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred186_InternalKactors

    // $ANTLR start synpred187_InternalKactors
    public final void synpred187_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5912:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5912:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5912:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5913:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5913:5: ( ( '.' ) )
        // InternalKactors.g:5914:6: ( '.' )
        {
        // InternalKactors.g:5914:6: ( '.' )
        // InternalKactors.g:5915:7: '.'
        {
        match(input,71,FOLLOW_10); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5918:5: ( ( RULE_INT ) )
        // InternalKactors.g:5919:6: ( RULE_INT )
        {
        // InternalKactors.g:5919:6: ( RULE_INT )
        // InternalKactors.g:5920:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred187_InternalKactors

    // $ANTLR start synpred191_InternalKactors
    public final void synpred191_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5961:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5961:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5961:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5962:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5962:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5963:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5963:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5964:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=83 && input.LA(1)<=84) ) {
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

        // InternalKactors.g:5971:5: ( '+' | ( ( '-' ) ) )?
        int alt141=3;
        int LA141_0 = input.LA(1);

        if ( (LA141_0==80) ) {
            alt141=1;
        }
        else if ( (LA141_0==81) ) {
            alt141=2;
        }
        switch (alt141) {
            case 1 :
                // InternalKactors.g:5972:6: '+'
                {
                match(input,80,FOLLOW_10); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5974:6: ( ( '-' ) )
                {
                // InternalKactors.g:5974:6: ( ( '-' ) )
                // InternalKactors.g:5975:7: ( '-' )
                {
                // InternalKactors.g:5975:7: ( '-' )
                // InternalKactors.g:5976:8: '-'
                {
                match(input,81,FOLLOW_10); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5980:5: ( ( RULE_INT ) )
        // InternalKactors.g:5981:6: ( RULE_INT )
        {
        // InternalKactors.g:5981:6: ( RULE_INT )
        // InternalKactors.g:5982:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred191_InternalKactors

    // $ANTLR start synpred199_InternalKactors
    public final void synpred199_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_11=null;
        Token lv_sec_12_0=null;
        Token otherlv_13=null;
        Token lv_ms_14_0=null;

        // InternalKactors.g:6205:5: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )
        // InternalKactors.g:6205:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
        {
        otherlv_11=(Token)match(input,42,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:6209:5: ( (lv_sec_12_0= RULE_INT ) )
        // InternalKactors.g:6210:6: (lv_sec_12_0= RULE_INT )
        {
        // InternalKactors.g:6210:6: (lv_sec_12_0= RULE_INT )
        // InternalKactors.g:6211:7: lv_sec_12_0= RULE_INT
        {
        lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return ;

        }


        }

        // InternalKactors.g:6227:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
        int alt142=2;
        int LA142_0 = input.LA(1);

        if ( (LA142_0==71) ) {
            alt142=1;
        }
        switch (alt142) {
            case 1 :
                // InternalKactors.g:6228:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                {
                otherlv_13=(Token)match(input,71,FOLLOW_10); if (state.failed) return ;
                // InternalKactors.g:6232:6: ( (lv_ms_14_0= RULE_INT ) )
                // InternalKactors.g:6233:7: (lv_ms_14_0= RULE_INT )
                {
                // InternalKactors.g:6233:7: (lv_ms_14_0= RULE_INT )
                // InternalKactors.g:6234:8: lv_ms_14_0= RULE_INT
                {
                lv_ms_14_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;

        }


        }
    }
    // $ANTLR end synpred199_InternalKactors

    // $ANTLR start synpred200_InternalKactors
    public final void synpred200_InternalKactors_fragment() throws RecognitionException {   
        Token lv_hour_8_0=null;
        Token otherlv_9=null;
        Token lv_min_10_0=null;
        Token otherlv_11=null;
        Token lv_sec_12_0=null;
        Token otherlv_13=null;
        Token lv_ms_14_0=null;

        // InternalKactors.g:6164:4: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )
        // InternalKactors.g:6164:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
        {
        // InternalKactors.g:6164:4: ( (lv_hour_8_0= RULE_INT ) )
        // InternalKactors.g:6165:5: (lv_hour_8_0= RULE_INT )
        {
        // InternalKactors.g:6165:5: (lv_hour_8_0= RULE_INT )
        // InternalKactors.g:6166:6: lv_hour_8_0= RULE_INT
        {
        lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_13); if (state.failed) return ;

        }


        }

        otherlv_9=(Token)match(input,42,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:6186:4: ( (lv_min_10_0= RULE_INT ) )
        // InternalKactors.g:6187:5: (lv_min_10_0= RULE_INT )
        {
        // InternalKactors.g:6187:5: (lv_min_10_0= RULE_INT )
        // InternalKactors.g:6188:6: lv_min_10_0= RULE_INT
        {
        lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_21); if (state.failed) return ;

        }


        }

        // InternalKactors.g:6204:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
        int alt144=2;
        int LA144_0 = input.LA(1);

        if ( (LA144_0==42) ) {
            alt144=1;
        }
        switch (alt144) {
            case 1 :
                // InternalKactors.g:6205:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                {
                otherlv_11=(Token)match(input,42,FOLLOW_10); if (state.failed) return ;
                // InternalKactors.g:6209:5: ( (lv_sec_12_0= RULE_INT ) )
                // InternalKactors.g:6210:6: (lv_sec_12_0= RULE_INT )
                {
                // InternalKactors.g:6210:6: (lv_sec_12_0= RULE_INT )
                // InternalKactors.g:6211:7: lv_sec_12_0= RULE_INT
                {
                lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return ;

                }


                }

                // InternalKactors.g:6227:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                int alt143=2;
                int LA143_0 = input.LA(1);

                if ( (LA143_0==71) ) {
                    alt143=1;
                }
                switch (alt143) {
                    case 1 :
                        // InternalKactors.g:6228:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                        {
                        otherlv_13=(Token)match(input,71,FOLLOW_10); if (state.failed) return ;
                        // InternalKactors.g:6232:6: ( (lv_ms_14_0= RULE_INT ) )
                        // InternalKactors.g:6233:7: (lv_ms_14_0= RULE_INT )
                        {
                        // InternalKactors.g:6233:7: (lv_ms_14_0= RULE_INT )
                        // InternalKactors.g:6234:8: lv_ms_14_0= RULE_INT
                        {
                        lv_ms_14_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

                        }


                        }


                        }
                        break;

                }


                }
                break;

        }


        }
    }
    // $ANTLR end synpred200_InternalKactors

    // $ANTLR start synpred210_InternalKactors
    public final void synpred210_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:6466:4: (kw= '-' )
        // InternalKactors.g:6466:4: kw= '-'
        {
        kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred210_InternalKactors

    // $ANTLR start synpred211_InternalKactors
    public final void synpred211_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:6473:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:6473:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred211_InternalKactors

    // Delegated rules

    public final boolean synpred101_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred101_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred163_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred163_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred59_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred59_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred70_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred70_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred69_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred69_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred35_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred35_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred38_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred38_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred145_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred145_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred173_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred173_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred160_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred160_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred191_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred191_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred66_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred66_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred63_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred63_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred60_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred60_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred52_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred52_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred79_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred79_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred67_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred67_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred211_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred211_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred61_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred76_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred76_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred73_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred73_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred199_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred199_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred187_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred187_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred40_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred40_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred43_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred43_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred200_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred200_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred72_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred72_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred37_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred37_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred44_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred44_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred41_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred41_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred68_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred68_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred75_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred75_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred78_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred78_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred88_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred88_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred51_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred51_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred210_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred210_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred185_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred185_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred33_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred33_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred95_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred95_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred186_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred186_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred112_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred112_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred36_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred36_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred118_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred118_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred65_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred65_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred92_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred92_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred71_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred71_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred74_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred74_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred80_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred80_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA14 dfa14 = new DFA14(this);
    protected DFA21 dfa21 = new DFA21(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA28 dfa28 = new DFA28(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA31 dfa31 = new DFA31(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA38 dfa38 = new DFA38(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA46 dfa46 = new DFA46(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA58 dfa58 = new DFA58(this);
    protected DFA60 dfa60 = new DFA60(this);
    protected DFA69 dfa69 = new DFA69(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA82 dfa82 = new DFA82(this);
    static final String dfa_1s = "\14\uffff";
    static final String dfa_2s = "\1\1\13\uffff";
    static final String dfa_3s = "\1\16\13\uffff";
    static final String dfa_4s = "\1\51\13\uffff";
    static final String dfa_5s = "\1\uffff\1\13\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String dfa_6s = "\1\0\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\17\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\1",
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

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 334:6: ( ({...}? => ( ({...}? => (otherlv_9= 'import' ( (lv_imports_10_0= rulePathName ) ) (otherlv_11= ',' ( (lv_imports_12_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'worldview' ( (lv_worldview_14_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'observable' ( ( (lv_observable_16_0= RULE_OBSERVABLE ) ) | ( (lv_observables_17_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'style' ( (lv_style_25_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'version' ( (lv_version_27_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= 'created' ( (lv_created_29_0= ruleDate ) ) ( (lv_createcomment_30_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_31= 'modified' ( (lv_modified_32_0= ruleDate ) ) ( (lv_modcomment_33_0= RULE_STRING ) )? ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_0 = input.LA(1);

                         
                        int index14_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA14_0==EOF||LA14_0==RULE_ANNOTATION_ID||LA14_0==41) ) {s = 1;}

                        else if ( LA14_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 0) ) {s = 2;}

                        else if ( LA14_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 1) ) {s = 3;}

                        else if ( LA14_0 == 33 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 2) ) {s = 4;}

                        else if ( LA14_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 3) ) {s = 5;}

                        else if ( LA14_0 == 35 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 4) ) {s = 6;}

                        else if ( LA14_0 == 36 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 5) ) {s = 7;}

                        else if ( LA14_0 == 37 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 6) ) {s = 8;}

                        else if ( LA14_0 == 38 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 7) ) {s = 9;}

                        else if ( LA14_0 == 39 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 8) ) {s = 10;}

                        else if ( LA14_0 == 40 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_3(), 9) ) {s = 11;}

                         
                        input.seek(index14_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\43\uffff";
    static final String dfa_9s = "\1\2\42\uffff";
    static final String dfa_10s = "\1\4\1\0\41\uffff";
    static final String dfa_11s = "\1\121\1\0\41\uffff";
    static final String dfa_12s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_14s = {
            "\11\2\1\uffff\1\2\20\uffff\1\2\11\uffff\2\2\1\1\10\2\1\uffff\6\2\2\uffff\1\2\5\uffff\1\2\14\uffff\2\2",
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
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "1035:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_1 = input.LA(1);

                         
                        int index21_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred33_InternalKactors()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String[] dfa_15s = {
            "\11\2\1\uffff\1\2\20\uffff\1\2\11\uffff\1\2\1\1\11\2\1\uffff\6\2\2\uffff\1\2\5\uffff\1\2\14\uffff\2\2",
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
            ""
    };
    static final short[][] dfa_15 = unpackEncodedStringArray(dfa_15s);

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_15;
        }
        public String getDescription() {
            return "1186:3: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_1 = input.LA(1);

                         
                        int index26_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred38_InternalKactors()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index26_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_16s = "\44\uffff";
    static final String dfa_17s = "\1\17\43\uffff";
    static final String dfa_18s = "\1\4\16\0\25\uffff";
    static final String dfa_19s = "\1\121\16\0\25\uffff";
    static final String dfa_20s = "\17\uffff\1\2\23\uffff\1\1";
    static final String dfa_21s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\25\uffff}>";
    static final String[] dfa_22s = {
            "\1\5\1\14\1\10\1\17\1\15\1\1\2\17\1\4\1\uffff\1\17\20\uffff\1\17\11\uffff\2\17\1\12\10\17\1\uffff\1\6\1\7\3\17\1\11\2\uffff\1\13\5\uffff\1\16\5\uffff\1\17\6\uffff\1\2\1\3",
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
            ""
    };

    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final char[] dfa_19 = DFA.unpackEncodedStringToUnsignedChars(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[][] dfa_22 = unpackEncodedStringArray(dfa_22s);

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = dfa_16;
            this.eof = dfa_17;
            this.min = dfa_18;
            this.max = dfa_19;
            this.accept = dfa_20;
            this.special = dfa_21;
            this.transition = dfa_22;
        }
        public String getDescription() {
            return "1285:3: ( (lv_value_1_0= ruleValue ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA28_1 = input.LA(1);

                         
                        int index28_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA28_2 = input.LA(1);

                         
                        int index28_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA28_3 = input.LA(1);

                         
                        int index28_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA28_4 = input.LA(1);

                         
                        int index28_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA28_5 = input.LA(1);

                         
                        int index28_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA28_6 = input.LA(1);

                         
                        int index28_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA28_7 = input.LA(1);

                         
                        int index28_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA28_8 = input.LA(1);

                         
                        int index28_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA28_9 = input.LA(1);

                         
                        int index28_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA28_10 = input.LA(1);

                         
                        int index28_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA28_11 = input.LA(1);

                         
                        int index28_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA28_12 = input.LA(1);

                         
                        int index28_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA28_13 = input.LA(1);

                         
                        int index28_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA28_14 = input.LA(1);

                         
                        int index28_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 35;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index28_14);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 28, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_23s = "\27\uffff";
    static final String dfa_24s = "\1\4\1\uffff\3\0\22\uffff";
    static final String dfa_25s = "\1\121\1\uffff\3\0\22\uffff";
    static final String dfa_26s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\12\uffff\1\2\1\3";
    static final String dfa_27s = "\2\uffff\1\0\1\1\1\2\22\uffff}>";
    static final String[] dfa_28s = {
            "\2\12\1\4\1\5\1\12\1\3\2\uffff\1\12\36\uffff\1\2\1\uffff\1\1\1\6\1\uffff\1\7\1\10\1\11\2\uffff\2\12\3\uffff\1\12\2\uffff\1\12\5\uffff\1\12\14\uffff\2\12",
            "",
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
            ""
    };

    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = dfa_23;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "1379:2: ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_2 = input.LA(1);

                         
                        int index30_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred43_InternalKactors()) ) {s = 21;}

                        else if ( (synpred44_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index30_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_3 = input.LA(1);

                         
                        int index30_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred44_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index30_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA30_4 = input.LA(1);

                         
                        int index30_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred44_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index30_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 30, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String[] dfa_29s = {
            "\2\12\1\3\1\5\1\12\1\2\2\uffff\1\12\36\uffff\1\4\1\uffff\1\1\1\6\1\uffff\1\7\1\10\1\11\2\uffff\2\12\3\uffff\1\12\2\uffff\1\12\5\uffff\1\12\14\uffff\2\12",
            "",
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
            ""
    };
    static final short[][] dfa_29 = unpackEncodedStringArray(dfa_29s);

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = dfa_23;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_29;
        }
        public String getDescription() {
            return "1581:3: ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA31_2 = input.LA(1);

                         
                        int index31_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred51_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index31_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA31_3 = input.LA(1);

                         
                        int index31_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred51_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index31_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA31_4 = input.LA(1);

                         
                        int index31_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred51_InternalKactors()) ) {s = 21;}

                        else if ( (synpred52_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index31_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 31, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_30s = "\21\uffff";
    static final String dfa_31s = "\1\4\3\0\15\uffff";
    static final String dfa_32s = "\1\121\3\0\15\uffff";
    static final String dfa_33s = "\4\uffff\1\2\12\uffff\1\1\1\3";
    static final String dfa_34s = "\1\uffff\1\0\1\1\1\2\15\uffff}>";
    static final String[] dfa_35s = {
            "\2\4\1\2\1\uffff\1\4\1\1\2\uffff\1\4\36\uffff\1\3\11\uffff\2\4\3\uffff\1\4\2\uffff\1\4\5\uffff\1\4\14\uffff\2\4",
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
            ""
    };

    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[][] dfa_35 = unpackEncodedStringArray(dfa_35s);

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_30;
            this.eof = dfa_30;
            this.min = dfa_31;
            this.max = dfa_32;
            this.accept = dfa_33;
            this.special = dfa_34;
            this.transition = dfa_35;
        }
        public String getDescription() {
            return "1971:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_1 = input.LA(1);

                         
                        int index34_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 15;}

                        else if ( (synpred61_InternalKactors()) ) {s = 4;}

                         
                        input.seek(index34_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA34_2 = input.LA(1);

                         
                        int index34_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 15;}

                        else if ( (synpred61_InternalKactors()) ) {s = 4;}

                         
                        input.seek(index34_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA34_3 = input.LA(1);

                         
                        int index34_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 15;}

                        else if ( (synpred61_InternalKactors()) ) {s = 4;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index34_3);
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
    static final String dfa_36s = "\35\uffff";
    static final String dfa_37s = "\1\4\3\0\2\uffff\6\0\1\uffff\1\0\17\uffff";
    static final String dfa_38s = "\1\121\3\0\2\uffff\6\0\1\uffff\1\0\17\uffff";
    static final String dfa_39s = "\4\uffff\1\1\14\uffff\1\3\11\uffff\1\2\1\4";
    static final String dfa_40s = "\1\uffff\1\0\1\1\1\2\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\17\uffff}>";
    static final String[] dfa_41s = {
            "\1\12\1\6\1\1\1\21\1\15\1\21\2\4\1\11\36\uffff\1\13\1\uffff\2\21\1\uffff\3\21\1\4\1\uffff\1\2\1\3\3\4\1\21\2\uffff\1\21\5\uffff\1\21\14\uffff\1\7\1\10",
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
            "",
            "",
            ""
    };

    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[][] dfa_41 = unpackEncodedStringArray(dfa_41s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_36;
            this.eof = dfa_36;
            this.min = dfa_37;
            this.max = dfa_38;
            this.accept = dfa_39;
            this.special = dfa_40;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "2258:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_1 = input.LA(1);

                         
                        int index37_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_2 = input.LA(1);

                         
                        int index37_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_3 = input.LA(1);

                         
                        int index37_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_6 = input.LA(1);

                         
                        int index37_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_7 = input.LA(1);

                         
                        int index37_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_8 = input.LA(1);

                         
                        int index37_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA37_9 = input.LA(1);

                         
                        int index37_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA37_10 = input.LA(1);

                         
                        int index37_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA37_11 = input.LA(1);

                         
                        int index37_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred65_InternalKactors()) ) {s = 27;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index37_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA37_13 = input.LA(1);

                         
                        int index37_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_InternalKactors()) ) {s = 4;}

                        else if ( (synpred66_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index37_13);
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
    static final String dfa_42s = "\32\uffff";
    static final String dfa_43s = "\1\4\16\0\13\uffff";
    static final String dfa_44s = "\1\121\16\0\13\uffff";
    static final String dfa_45s = "\17\uffff\1\1\1\2\1\3\1\13\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String dfa_46s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\13\uffff}>";
    static final String[] dfa_47s = {
            "\1\5\1\14\1\10\1\uffff\1\15\1\1\2\uffff\1\4\36\uffff\1\12\11\uffff\1\6\1\7\3\uffff\1\11\2\uffff\1\13\5\uffff\1\16\14\uffff\1\2\1\3",
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
            ""
    };

    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[][] dfa_47 = unpackEncodedStringArray(dfa_47s);

    class DFA38 extends DFA {

        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = dfa_42;
            this.eof = dfa_42;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "2395:2: ( ( (lv_tree_0_0= ruleTree ) ) | ( (lv_argvalue_1_0= RULE_ARGVALUE ) ) | ( (lv_literal_2_0= ruleLiteral ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_id_4_0= rulePathName ) ) | ( (lv_list_5_0= ruleList ) ) | ( (lv_map_6_0= ruleMap ) ) | ( (lv_observable_7_0= RULE_OBSERVABLE ) ) | ( (lv_expression_8_0= RULE_EXPR ) ) | ( (lv_table_9_0= ruleLookupTable ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA38_1 = input.LA(1);

                         
                        int index38_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred68_InternalKactors()) ) {s = 16;}

                         
                        input.seek(index38_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA38_2 = input.LA(1);

                         
                        int index38_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred69_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 18;}

                         
                        input.seek(index38_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA38_3 = input.LA(1);

                         
                        int index38_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred69_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 18;}

                         
                        input.seek(index38_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA38_4 = input.LA(1);

                         
                        int index38_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred69_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 18;}

                         
                        input.seek(index38_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA38_5 = input.LA(1);

                         
                        int index38_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred69_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index38_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA38_6 = input.LA(1);

                         
                        int index38_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred69_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index38_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA38_7 = input.LA(1);

                         
                        int index38_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred69_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index38_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA38_8 = input.LA(1);

                         
                        int index38_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred70_InternalKactors()) ) {s = 19;}

                        else if ( (synpred71_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index38_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA38_9 = input.LA(1);

                         
                        int index38_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred70_InternalKactors()) ) {s = 19;}

                         
                        input.seek(index38_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA38_10 = input.LA(1);

                         
                        int index38_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred72_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index38_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA38_11 = input.LA(1);

                         
                        int index38_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred73_InternalKactors()) ) {s = 22;}

                         
                        input.seek(index38_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA38_12 = input.LA(1);

                         
                        int index38_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred74_InternalKactors()) ) {s = 23;}

                         
                        input.seek(index38_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA38_13 = input.LA(1);

                         
                        int index38_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred75_InternalKactors()) ) {s = 24;}

                         
                        input.seek(index38_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA38_14 = input.LA(1);

                         
                        int index38_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred67_InternalKactors()) ) {s = 15;}

                        else if ( (synpred76_InternalKactors()) ) {s = 25;}

                         
                        input.seek(index38_14);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 38, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_48s = "\105\uffff";
    static final String dfa_49s = "\4\uffff\1\5\1\uffff\1\25\6\uffff\2\5\1\22\11\uffff\1\5\2\25\2\uffff\2\25\4\uffff\3\25\7\uffff\2\25\4\uffff\1\25\4\uffff\1\25\2\uffff\1\25\4\uffff\1\25\3\uffff";
    static final String dfa_50s = "\1\4\1\uffff\2\14\1\4\1\uffff\1\4\6\uffff\3\4\2\14\1\uffff\1\6\1\4\1\uffff\1\0\2\14\3\4\1\6\3\4\1\6\1\4\1\6\4\4\2\0\1\4\1\6\2\4\1\6\2\4\4\0\1\4\1\6\1\4\1\6\2\4\2\0\1\4\2\0\1\6\2\4\1\0\1\4\1\0";
    static final String dfa_51s = "\1\121\1\uffff\2\14\1\127\1\uffff\1\121\6\uffff\2\124\1\130\2\121\1\uffff\1\6\1\121\1\uffff\1\0\2\14\3\121\1\6\3\121\1\6\1\121\1\6\4\121\2\0\1\121\1\6\2\121\1\6\2\121\4\0\1\121\1\6\1\121\1\6\2\121\2\0\1\121\2\0\1\6\2\121\1\0\1\121\1\0";
    static final String dfa_52s = "\1\uffff\1\1\3\uffff\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\5\uffff\1\12\2\uffff\1\3\57\uffff";
    static final String dfa_53s = "\26\uffff\1\5\20\uffff\1\12\1\11\7\uffff\1\0\1\7\1\13\1\3\6\uffff\1\2\1\10\1\uffff\1\1\1\6\3\uffff\1\14\1\uffff\1\4}>";
    static final String[] dfa_54s = {
            "\1\5\1\12\1\6\1\uffff\1\13\1\1\2\uffff\1\4\36\uffff\1\10\11\uffff\2\5\3\uffff\1\7\2\uffff\1\11\5\uffff\1\14\14\uffff\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\11\5\1\uffff\1\5\20\uffff\1\5\11\uffff\13\5\1\uffff\6\5\2\uffff\3\5\2\uffff\2\5\2\uffff\1\22\1\17\1\uffff\1\5\6\uffff\2\5\1\16\1\20\1\21\3\5",
            "",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\24\11\25\1\uffff\6\25\2\uffff\3\25\3\uffff\1\25\3\uffff\1\23\1\uffff\1\25\6\uffff\2\25",
            "",
            "",
            "",
            "",
            "",
            "",
            "\11\5\1\uffff\1\5\20\uffff\1\5\11\uffff\13\5\1\uffff\6\5\2\uffff\3\5\2\uffff\2\5\2\uffff\1\22\1\17\1\uffff\1\5\6\uffff\2\5\1\16\1\20\1\21",
            "\11\5\1\uffff\1\5\20\uffff\1\5\11\uffff\13\5\1\uffff\6\5\2\uffff\3\5\2\uffff\2\5\2\uffff\1\22\1\17\1\uffff\1\5\6\uffff\2\5\1\uffff\1\20\1\21",
            "\10\22\1\26\1\uffff\2\22\17\uffff\1\22\11\uffff\13\22\1\uffff\6\22\2\uffff\3\22\3\uffff\1\22\2\uffff\1\22\2\uffff\1\22\6\uffff\2\22\6\uffff\1\22",
            "\1\31\103\uffff\1\27\1\30",
            "\1\31\103\uffff\1\27\1\30",
            "",
            "\1\32",
            "\2\25\1\33\6\25\36\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "",
            "\1\uffff",
            "\1\31",
            "\1\31",
            "\11\5\1\uffff\1\5\20\uffff\1\5\11\uffff\13\5\1\uffff\6\5\2\uffff\3\5\2\uffff\2\5\2\uffff\2\22\1\uffff\1\5\6\uffff\2\5",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\24\11\25\1\uffff\6\25\2\uffff\3\25\3\uffff\1\25\3\uffff\1\23\1\uffff\1\25\6\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\35\20\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\34\1\uffff\1\25\6\uffff\2\25",
            "\1\36",
            "\2\25\1\37\6\25\36\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\41\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\40\10\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\43\20\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\42\1\uffff\1\25\6\uffff\2\25",
            "\1\44",
            "\2\25\1\45\6\25\36\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\1\46",
            "\2\25\1\47\5\25\1\50\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\51\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\40\10\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\53\20\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\52\10\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\54\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\55\10\uffff\2\25",
            "\1\uffff",
            "\1\uffff",
            "\2\25\1\56\6\25\36\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\1\57",
            "\2\25\1\60\5\25\1\61\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\2\25\1\62\5\25\1\63\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\1\64",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\66\20\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\65\10\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\53\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\67\10\uffff\2\25",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\70\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\55\10\uffff\2\25",
            "\1\71",
            "\2\25\1\72\5\25\1\73\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\1\74",
            "\2\25\1\75\5\25\1\76\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\66\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\77\10\uffff\2\25",
            "\1\uffff",
            "\1\uffff",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\100\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\67\10\uffff\2\25",
            "\1\uffff",
            "\1\uffff",
            "\1\101",
            "\2\25\1\102\5\25\1\76\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\11\25\1\uffff\1\25\20\uffff\1\25\11\uffff\1\25\1\103\11\25\1\uffff\6\25\2\uffff\1\25\1\uffff\1\25\3\uffff\1\25\3\uffff\1\77\10\uffff\2\25",
            "\1\uffff",
            "\2\25\1\104\5\25\1\76\2\uffff\1\7\33\uffff\1\25\1\uffff\2\25\1\uffff\4\25\1\uffff\6\25\2\uffff\1\25\5\uffff\1\25\14\uffff\2\25",
            "\1\uffff"
    };

    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final char[] dfa_51 = DFA.unpackEncodedStringToUnsignedChars(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[][] dfa_54 = unpackEncodedStringArray(dfa_54s);

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = dfa_48;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "2630:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA39_48 = input.LA(1);

                         
                        int index39_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_48);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA39_61 = input.LA(1);

                         
                        int index39_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_61);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA39_58 = input.LA(1);

                         
                        int index39_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_58);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA39_51 = input.LA(1);

                         
                        int index39_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_51);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA39_68 = input.LA(1);

                         
                        int index39_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_68);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA39_22 = input.LA(1);

                         
                        int index39_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred78_InternalKactors()) ) {s = 5;}

                        else if ( (true) ) {s = 18;}

                         
                        input.seek(index39_22);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA39_62 = input.LA(1);

                         
                        int index39_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_62);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA39_49 = input.LA(1);

                         
                        int index39_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_49);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA39_59 = input.LA(1);

                         
                        int index39_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_59);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA39_40 = input.LA(1);

                         
                        int index39_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_40);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA39_39 = input.LA(1);

                         
                        int index39_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_39);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA39_50 = input.LA(1);

                         
                        int index39_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_50);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA39_66 = input.LA(1);

                         
                        int index39_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 21;}

                        else if ( (synpred80_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index39_66);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 39, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_55s = "\23\uffff";
    static final String dfa_56s = "\1\4\1\uffff\2\0\3\uffff\3\0\11\uffff";
    static final String dfa_57s = "\1\121\1\uffff\2\0\3\uffff\3\0\11\uffff";
    static final String dfa_58s = "\1\uffff\1\1\2\uffff\1\3\1\4\1\5\3\uffff\1\6\1\7\1\10\1\12\1\13\1\14\1\15\1\2\1\11";
    static final String dfa_59s = "\2\uffff\1\0\1\1\3\uffff\1\2\1\3\1\4\11\uffff}>";
    static final String[] dfa_60s = {
            "\1\12\1\6\1\1\1\uffff\1\15\1\uffff\1\4\1\5\1\11\36\uffff\1\13\7\uffff\1\14\1\uffff\1\2\1\3\1\16\1\17\1\20\26\uffff\1\7\1\10",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
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
            ""
    };

    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final char[] dfa_57 = DFA.unpackEncodedStringToUnsignedChars(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[] dfa_59 = DFA.unpackEncodedString(dfa_59s);
    static final short[][] dfa_60 = unpackEncodedStringArray(dfa_60s);

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_55;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_59;
            this.transition = dfa_60;
        }
        public String getDescription() {
            return "2845:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_list_18_0= ruleList ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | (otherlv_21= 'in' ( (lv_set_22_0= ruleList ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_quantity_25_0= ruleQuantity ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_expr_28_0= RULE_EXPR ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_nodata_31_0= 'unknown' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_star_34_0= '*' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_anything_37_0= '#' ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA41_2 = input.LA(1);

                         
                        int index41_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred88_InternalKactors()) ) {s = 17;}

                        else if ( (synpred92_InternalKactors()) ) {s = 10;}

                         
                        input.seek(index41_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA41_3 = input.LA(1);

                         
                        int index41_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred88_InternalKactors()) ) {s = 17;}

                        else if ( (synpred92_InternalKactors()) ) {s = 10;}

                         
                        input.seek(index41_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA41_7 = input.LA(1);

                         
                        int index41_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred92_InternalKactors()) ) {s = 10;}

                        else if ( (synpred95_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index41_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA41_8 = input.LA(1);

                         
                        int index41_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred92_InternalKactors()) ) {s = 10;}

                        else if ( (synpred95_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index41_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA41_9 = input.LA(1);

                         
                        int index41_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred92_InternalKactors()) ) {s = 10;}

                        else if ( (synpred95_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index41_9);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 41, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_61s = "\6\uffff";
    static final String dfa_62s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_63s = "\1\6\1\4\1\uffff\1\6\1\uffff\1\4";
    static final String dfa_64s = "\1\17\1\121\1\uffff\1\17\1\uffff\1\121";
    static final String dfa_65s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_66s = "\6\uffff}>";
    static final String[] dfa_67s = {
            "\1\1\10\uffff\1\2",
            "\11\2\1\uffff\1\2\20\uffff\1\2\11\uffff\13\2\1\uffff\7\2\1\4\3\2\3\uffff\1\2\2\uffff\1\2\1\3\1\uffff\1\2\6\uffff\2\2",
            "",
            "\1\5\10\uffff\1\2",
            "",
            "\11\2\1\uffff\1\2\20\uffff\1\2\11\uffff\13\2\1\uffff\7\2\1\4\3\2\3\uffff\1\2\2\uffff\1\2\1\3\1\uffff\1\2\6\uffff\2\2"
    };

    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final short[] dfa_62 = DFA.unpackEncodedString(dfa_62s);
    static final char[] dfa_63 = DFA.unpackEncodedStringToUnsignedChars(dfa_63s);
    static final char[] dfa_64 = DFA.unpackEncodedStringToUnsignedChars(dfa_64s);
    static final short[] dfa_65 = DFA.unpackEncodedString(dfa_65s);
    static final short[] dfa_66 = DFA.unpackEncodedString(dfa_66s);
    static final short[][] dfa_67 = unpackEncodedStringArray(dfa_67s);

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = dfa_61;
            this.eof = dfa_62;
            this.min = dfa_63;
            this.max = dfa_64;
            this.accept = dfa_65;
            this.special = dfa_66;
            this.transition = dfa_67;
        }
        public String getDescription() {
            return "3536:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = dfa_61;
            this.eof = dfa_62;
            this.min = dfa_63;
            this.max = dfa_64;
            this.accept = dfa_65;
            this.special = dfa_66;
            this.transition = dfa_67;
        }
        public String getDescription() {
            return "3565:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_68s = "\20\uffff";
    static final String dfa_69s = "\1\4\11\uffff\1\0\5\uffff";
    static final String dfa_70s = "\1\121\11\uffff\1\0\5\uffff";
    static final String dfa_71s = "\1\uffff\1\1\15\uffff\1\2";
    static final String dfa_72s = "\12\uffff\1\0\5\uffff}>";
    static final String[] dfa_73s = {
            "\3\1\1\uffff\2\1\2\uffff\1\1\36\uffff\1\12\11\uffff\2\1\3\uffff\1\1\2\uffff\1\1\5\uffff\1\1\14\uffff\2\1",
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
            ""
    };

    static final short[] dfa_68 = DFA.unpackEncodedString(dfa_68s);
    static final char[] dfa_69 = DFA.unpackEncodedStringToUnsignedChars(dfa_69s);
    static final char[] dfa_70 = DFA.unpackEncodedStringToUnsignedChars(dfa_70s);
    static final short[] dfa_71 = DFA.unpackEncodedString(dfa_71s);
    static final short[] dfa_72 = DFA.unpackEncodedString(dfa_72s);
    static final short[][] dfa_73 = unpackEncodedStringArray(dfa_73s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_68;
            this.eof = dfa_68;
            this.min = dfa_69;
            this.max = dfa_70;
            this.accept = dfa_71;
            this.special = dfa_72;
            this.transition = dfa_73;
        }
        public String getDescription() {
            return "3833:4: ( ( (lv_value_2_0= ruleValueWithoutTree ) ) | (otherlv_3= '(' ( (lv_value_4_0= ruleTree ) ) otherlv_5= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA53_10 = input.LA(1);

                         
                        int index53_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred112_InternalKactors()) ) {s = 1;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index53_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 53, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_74s = "\26\uffff";
    static final String dfa_75s = "\4\uffff\1\21\7\uffff\1\21\5\uffff\1\21\2\uffff\1\21";
    static final String dfa_76s = "\1\4\1\uffff\2\14\1\52\7\uffff\1\52\3\14\2\uffff\1\52\2\14\1\52";
    static final String dfa_77s = "\1\121\1\uffff\2\14\1\124\7\uffff\1\124\1\14\2\121\2\uffff\1\124\2\14\1\102";
    static final String dfa_78s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\2\1\3\4\uffff";
    static final String dfa_79s = "\26\uffff}>";
    static final String[] dfa_80s = {
            "\1\6\1\7\1\10\5\uffff\1\4\46\uffff\1\5\1\uffff\2\1\1\12\1\13\3\uffff\1\11\16\uffff\5\11\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\21\25\uffff\3\20\4\uffff\1\15\12\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21\25\uffff\3\20\4\uffff\1\15\13\uffff\1\16\1\17",
            "\1\22",
            "\1\25\103\uffff\1\23\1\24",
            "\1\25\103\uffff\1\23\1\24",
            "",
            "",
            "\1\21\25\uffff\3\20\20\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\21\25\uffff\3\20"
    };

    static final short[] dfa_74 = DFA.unpackEncodedString(dfa_74s);
    static final short[] dfa_75 = DFA.unpackEncodedString(dfa_75s);
    static final char[] dfa_76 = DFA.unpackEncodedStringToUnsignedChars(dfa_76s);
    static final char[] dfa_77 = DFA.unpackEncodedStringToUnsignedChars(dfa_77s);
    static final short[] dfa_78 = DFA.unpackEncodedString(dfa_78s);
    static final short[] dfa_79 = DFA.unpackEncodedString(dfa_79s);
    static final short[][] dfa_80 = unpackEncodedStringArray(dfa_80s);

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = dfa_74;
            this.eof = dfa_75;
            this.min = dfa_76;
            this.max = dfa_77;
            this.accept = dfa_78;
            this.special = dfa_79;
            this.transition = dfa_80;
        }
        public String getDescription() {
            return "3964:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_81s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_82s = "\1\4\1\uffff\1\15\1\uffff\1\4\1\15";
    static final String dfa_83s = "\1\121\1\uffff\1\105\1\uffff\1\121\1\105";
    static final String dfa_84s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_85s = {
            "\1\2\1\3\1\1\1\uffff\1\3\3\uffff\1\3\46\uffff\1\3\1\uffff\5\3\2\uffff\1\3\16\uffff\7\3",
            "",
            "\1\1\21\uffff\1\3\44\uffff\1\3\1\4",
            "",
            "\1\5\1\3\1\1\1\uffff\1\3\3\uffff\1\3\46\uffff\1\3\1\uffff\5\3\2\uffff\1\3\16\uffff\7\3",
            "\1\1\21\uffff\1\3\44\uffff\1\3\1\4"
    };
    static final short[] dfa_81 = DFA.unpackEncodedString(dfa_81s);
    static final char[] dfa_82 = DFA.unpackEncodedStringToUnsignedChars(dfa_82s);
    static final char[] dfa_83 = DFA.unpackEncodedStringToUnsignedChars(dfa_83s);
    static final short[] dfa_84 = DFA.unpackEncodedString(dfa_84s);
    static final short[][] dfa_85 = unpackEncodedStringArray(dfa_85s);

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = dfa_61;
            this.eof = dfa_81;
            this.min = dfa_82;
            this.max = dfa_83;
            this.accept = dfa_84;
            this.special = dfa_66;
            this.transition = dfa_85;
        }
        public String getDescription() {
            return "4339:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_86s = "\4\uffff\1\23\10\uffff\2\23\1\22\6\uffff\1\23\2\uffff\1\23";
    static final String dfa_87s = "\1\4\1\uffff\2\14\1\37\10\uffff\2\37\1\6\2\14\4\uffff\1\37\2\14\1\37";
    static final String dfa_88s = "\1\121\1\uffff\2\14\1\127\10\uffff\2\124\1\130\2\121\4\uffff\1\124\2\14\1\107";
    static final String dfa_89s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\5\uffff\1\10\1\2\1\11\1\6\4\uffff";
    static final String dfa_90s = "\32\uffff}>";
    static final String[] dfa_91s = {
            "\1\5\1\6\2\uffff\1\11\3\uffff\1\4\46\uffff\1\10\1\uffff\2\1\1\12\1\13\1\14\2\uffff\1\7\16\uffff\5\7\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\23\40\uffff\3\25\1\uffff\2\23\1\22\1\17\11\uffff\1\24\1\16\1\20\1\21\3\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\23\40\uffff\3\25\1\uffff\2\23\1\22\1\17\12\uffff\1\16\1\20\1\21",
            "\1\23\40\uffff\3\25\1\uffff\2\23\1\22\1\17\13\uffff\1\20\1\21",
            "\1\22\3\uffff\1\22\1\uffff\1\26\2\uffff\1\22\17\uffff\1\22\13\uffff\1\22\14\uffff\1\22\13\uffff\3\22\21\uffff\1\22",
            "\1\31\103\uffff\1\27\1\30",
            "\1\31\103\uffff\1\27\1\30",
            "",
            "",
            "",
            "",
            "\1\23\40\uffff\3\25\1\uffff\2\23\2\22\13\uffff\1\20\1\21",
            "\1\31",
            "\1\31",
            "\1\23\40\uffff\3\25\1\uffff\2\23\2\22"
    };
    static final short[] dfa_86 = DFA.unpackEncodedString(dfa_86s);
    static final char[] dfa_87 = DFA.unpackEncodedStringToUnsignedChars(dfa_87s);
    static final char[] dfa_88 = DFA.unpackEncodedStringToUnsignedChars(dfa_88s);
    static final short[] dfa_89 = DFA.unpackEncodedString(dfa_89s);
    static final short[] dfa_90 = DFA.unpackEncodedString(dfa_90s);
    static final short[][] dfa_91 = unpackEncodedStringArray(dfa_91s);

    class DFA69 extends DFA {

        public DFA69(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 69;
            this.eot = dfa_42;
            this.eof = dfa_86;
            this.min = dfa_87;
            this.max = dfa_88;
            this.accept = dfa_89;
            this.special = dfa_90;
            this.transition = dfa_91;
        }
        public String getDescription() {
            return "4584:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_92s = "\3\uffff\1\16\2\uffff\2\16\7\uffff\1\16\2\uffff\2\16\2\uffff";
    static final String dfa_93s = "\1\4\2\14\1\4\2\uffff\2\4\3\14\2\uffff\1\14\1\uffff\1\4\2\14\2\4\1\14\1\0";
    static final String dfa_94s = "\1\121\2\14\1\127\2\uffff\2\124\1\14\2\121\2\uffff\1\14\1\uffff\1\124\2\14\1\121\1\124\1\14\1\0";
    static final String dfa_95s = "\4\uffff\1\3\1\5\5\uffff\1\2\1\4\1\uffff\1\1\7\uffff";
    static final String dfa_96s = "\25\uffff\1\0}>";
    static final String[] dfa_97s = {
            "\1\4\7\uffff\1\3\50\uffff\2\5\31\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\11\16\1\uffff\1\16\20\uffff\1\16\11\uffff\22\16\2\uffff\3\16\2\uffff\1\13\1\16\3\uffff\1\10\1\uffff\1\16\6\uffff\1\16\1\15\1\7\1\11\1\12\3\14",
            "",
            "",
            "\11\16\1\uffff\1\16\20\uffff\1\16\11\uffff\22\16\2\uffff\3\16\2\uffff\1\13\1\16\3\uffff\1\10\1\uffff\1\16\6\uffff\2\16\1\7\1\11\1\12",
            "\11\16\1\uffff\1\16\20\uffff\1\16\11\uffff\22\16\2\uffff\3\16\2\uffff\1\13\1\16\3\uffff\1\10\1\uffff\1\16\6\uffff\2\16\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\103\uffff\1\20\1\21",
            "\1\22\103\uffff\1\20\1\21",
            "",
            "",
            "\1\23",
            "",
            "\11\16\1\uffff\1\16\20\uffff\1\16\11\uffff\22\16\2\uffff\3\16\2\uffff\1\13\1\16\5\uffff\1\16\6\uffff\2\16\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\11\16\1\uffff\1\16\20\uffff\1\16\11\uffff\22\16\2\uffff\3\16\2\uffff\1\13\1\16\5\uffff\1\16\6\uffff\2\16",
            "\11\16\1\uffff\1\16\20\uffff\1\16\11\uffff\22\16\2\uffff\1\16\1\uffff\1\16\2\uffff\2\16\2\uffff\2\16\10\uffff\1\16\1\24\3\16",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_92 = DFA.unpackEncodedString(dfa_92s);
    static final char[] dfa_93 = DFA.unpackEncodedStringToUnsignedChars(dfa_93s);
    static final char[] dfa_94 = DFA.unpackEncodedStringToUnsignedChars(dfa_94s);
    static final short[] dfa_95 = DFA.unpackEncodedString(dfa_95s);
    static final short[] dfa_96 = DFA.unpackEncodedString(dfa_96s);
    static final short[][] dfa_97 = unpackEncodedStringArray(dfa_97s);

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = dfa_74;
            this.eof = dfa_92;
            this.min = dfa_93;
            this.max = dfa_94;
            this.accept = dfa_95;
            this.special = dfa_96;
            this.transition = dfa_97;
        }
        public String getDescription() {
            return "5123:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA75_21 = input.LA(1);

                         
                        int index75_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred160_InternalKactors()) ) {s = 14;}

                        else if ( (synpred163_InternalKactors()) ) {s = 12;}

                         
                        input.seek(index75_21);
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
    static final String dfa_98s = "\53\uffff";
    static final String dfa_99s = "\1\5\52\uffff";
    static final String dfa_100s = "\1\4\2\0\1\uffff\1\0\46\uffff";
    static final String dfa_101s = "\1\130\2\0\1\uffff\1\0\46\uffff";
    static final String dfa_102s = "\3\uffff\1\1\1\uffff\1\2\45\uffff";
    static final String dfa_103s = "\1\uffff\1\0\1\1\1\uffff\1\2\46\uffff}>";
    static final String[] dfa_104s = {
            "\2\5\1\2\3\5\1\1\2\5\1\uffff\1\5\1\3\17\uffff\1\5\11\uffff\2\5\1\4\17\5\2\uffff\3\5\3\uffff\4\5\2\uffff\1\5\6\uffff\2\5\6\uffff\1\5",
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

    static final short[] dfa_98 = DFA.unpackEncodedString(dfa_98s);
    static final short[] dfa_99 = DFA.unpackEncodedString(dfa_99s);
    static final char[] dfa_100 = DFA.unpackEncodedStringToUnsignedChars(dfa_100s);
    static final char[] dfa_101 = DFA.unpackEncodedStringToUnsignedChars(dfa_101s);
    static final short[] dfa_102 = DFA.unpackEncodedString(dfa_102s);
    static final short[] dfa_103 = DFA.unpackEncodedString(dfa_103s);
    static final short[][] dfa_104 = unpackEncodedStringArray(dfa_104s);

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = dfa_98;
            this.eof = dfa_99;
            this.min = dfa_100;
            this.max = dfa_101;
            this.accept = dfa_102;
            this.special = dfa_103;
            this.transition = dfa_104;
        }
        public String getDescription() {
            return "5567:3: ( (lv_root_1_0= ruleUnitElement ) )?";
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
                        if ( (synpred173_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index82_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA82_2 = input.LA(1);

                         
                        int index82_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred173_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index82_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA82_4 = input.LA(1);

                         
                        int index82_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred173_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index82_4);
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
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000020000004002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x000001FF40000012L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000001FF40000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000001FFC0000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000080000000020L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000020000004000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000C0000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x24676800000013F0L,0x0000000000030008L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000100000000040L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000100080000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x24676800000013F2L,0x0000000000030008L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x24677800000013F0L,0x0000000000030008L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x27EF680000001FF0L,0x0000000000030008L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000040000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x27E8080000001F70L,0x0000000000030008L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x27E8180000001F70L,0x0000000000030008L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000009040L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0200040000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000008040L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x51E8000000001070L,0x000000000003F800L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x4000000080000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x11E8000000001070L,0x000000000003F800L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x2460080000001370L,0x0000000000030008L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000001000L,0x0000000000030000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000003L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x37E8080000001370L,0x000000000003F818L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x37E8080000001370L,0x000000000003F808L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0100080000008440L,0x0000000001000040L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0100180000008440L,0x0000000001000040L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0100000000000002L,0x0000000001000040L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000080000008440L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000002L,0x00000000001C0080L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0000000000180080L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000000000180000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000000000E20000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x00000000000000C0L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000008042L,0x0000000000020080L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000008042L,0x0000000000020000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000008042L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000012L});

}
