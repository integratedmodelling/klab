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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_OBSERVABLE", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_EMBEDDEDTEXT", "RULE_EXPR", "RULE_ARGVALUE", "RULE_CAMELCASE_ID", "RULE_REGEXP", "RULE_INT", "RULE_SEPARATOR", "RULE_ANNOTATION_ID", "RULE_UPPERCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'app'", "'job'", "'testcase'", "'user'", "'trait'", "'library'", "'behavior'", "'behaviour'", "'import'", "','", "'worldview'", "'observable'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'action'", "':'", "'('", "')'", "'set'", "'if'", "'else'", "'while'", "'do'", "'for'", "'in'", "'->'", "'true'", "'false'", "'unknown'", "'*'", "'#'", "'urn:klab:'", "'&'", "'='", "'{'", "'}'", "'inclusive'", "'exclusive'", "'to'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_EMBEDDEDTEXT=8;
    public static final int RULE_UPPERCASE_ID=16;
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
    public static final int RULE_ID=6;
    public static final int RULE_CAMELCASE_ID=11;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=13;
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
    public static final int RULE_ARGVALUE=10;
    public static final int RULE_STRING=7;
    public static final int RULE_SEPARATOR=14;
    public static final int RULE_SL_COMMENT=19;
    public static final int RULE_OBSERVABLE=4;
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
    public static final int RULE_REGEXP=12;
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
    public static final int RULE_ANNOTATION_ID=15;
    public static final int T__48=48;
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=9;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__84=84;
    public static final int T__41=41;
    public static final int T__85=85;
    public static final int T__42=42;
    public static final int T__86=86;
    public static final int RULE_UPPERCASE_PATH=17;
    public static final int T__43=43;

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
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
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
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
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
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token lv_observable_15_0=null;
        Token otherlv_17=null;
        Token lv_label_18_1=null;
        Token lv_label_18_2=null;
        Token lv_label_18_3=null;
        Token otherlv_19=null;
        Token lv_description_20_0=null;
        Token otherlv_21=null;
        Token lv_permissions_22_0=null;
        Token otherlv_23=null;
        Token lv_authors_24_0=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token lv_createcomment_29_0=null;
        Token otherlv_30=null;
        Token lv_modcomment_32_0=null;
        AntlrDatatypeRuleToken lv_name_6_0 = null;

        AntlrDatatypeRuleToken lv_imports_9_0 = null;

        AntlrDatatypeRuleToken lv_imports_11_0 = null;

        AntlrDatatypeRuleToken lv_worldview_13_0 = null;

        EObject lv_observables_16_0 = null;

        AntlrDatatypeRuleToken lv_version_26_0 = null;

        EObject lv_created_28_0 = null;

        EObject lv_modified_31_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:162:2: ( ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' ) ) ) | ( (lv_test_2_0= 'testcase' ) ) | ( (lv_user_3_0= 'user' ) ) | ( ( (lv_library_4_1= 'trait' | lv_library_4_2= 'library' ) ) ) | ( ( (lv_behavior_5_1= 'behavior' | lv_behavior_5_2= 'behaviour' ) ) ) ) ( (lv_name_6_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
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

            // InternalKactors.g:310:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:311:4: ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:311:4: ( ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:312:5: ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:315:5: ( ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:316:6: ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:316:6: ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )*
            loop14:
            do {
                int alt14=11;
                alt14 = dfa14.predict(input);
                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:317:4: ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:317:4: ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:318:5: {...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:318:105: ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:319:6: ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:322:9: ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:322:10: {...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:322:19: (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* )
            	    // InternalKactors.g:322:20: otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )*
            	    {
            	    otherlv_8=(Token)match(input,30,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_8, grammarAccess.getPreambleAccess().getImportKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:326:9: ( (lv_imports_9_0= rulePathName ) )
            	    // InternalKactors.g:327:10: (lv_imports_9_0= rulePathName )
            	    {
            	    // InternalKactors.g:327:10: (lv_imports_9_0= rulePathName )
            	    // InternalKactors.g:328:11: lv_imports_9_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_imports_9_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											add(
            	      												current,
            	      												"imports",
            	      												lv_imports_9_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:345:9: (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )*
            	    loop8:
            	    do {
            	        int alt8=2;
            	        int LA8_0 = input.LA(1);

            	        if ( (LA8_0==31) ) {
            	            alt8=1;
            	        }


            	        switch (alt8) {
            	    	case 1 :
            	    	    // InternalKactors.g:346:10: otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) )
            	    	    {
            	    	    otherlv_10=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_10, grammarAccess.getPreambleAccess().getCommaKeyword_2_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:350:10: ( (lv_imports_11_0= rulePathName ) )
            	    	    // InternalKactors.g:351:11: (lv_imports_11_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:351:11: (lv_imports_11_0= rulePathName )
            	    	    // InternalKactors.g:352:12: lv_imports_11_0= rulePathName
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_imports_11_0=rulePathName();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      												if (current==null) {
            	    	      													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	    	      												}
            	    	      												add(
            	    	      													current,
            	    	      													"imports",
            	    	      													lv_imports_11_0,
            	    	      													"org.integratedmodelling.kactors.Kactors.PathName");
            	    	      												afterParserOrEnumRuleCall();
            	    	      											
            	    	    }

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

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:376:4: ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:376:4: ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:377:5: {...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:377:105: ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:378:6: ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:381:9: ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) )
            	    // InternalKactors.g:381:10: {...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:381:19: (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) )
            	    // InternalKactors.g:381:20: otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) )
            	    {
            	    otherlv_12=(Token)match(input,32,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_12, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:385:9: ( (lv_worldview_13_0= rulePathName ) )
            	    // InternalKactors.g:386:10: (lv_worldview_13_0= rulePathName )
            	    {
            	    // InternalKactors.g:386:10: (lv_worldview_13_0= rulePathName )
            	    // InternalKactors.g:387:11: lv_worldview_13_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_worldview_13_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_13_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKactors.g:410:4: ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:410:4: ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) )
            	    // InternalKactors.g:411:5: {...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:411:105: ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) )
            	    // InternalKactors.g:412:6: ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:415:9: ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) )
            	    // InternalKactors.g:415:10: {...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:415:19: (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) )
            	    // InternalKactors.g:415:20: otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) )
            	    {
            	    otherlv_14=(Token)match(input,33,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_14, grammarAccess.getPreambleAccess().getObservableKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:419:9: ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) )
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==RULE_OBSERVABLE) ) {
            	        alt9=1;
            	    }
            	    else if ( (LA9_0==43) ) {
            	        alt9=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 9, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // InternalKactors.g:420:10: ( (lv_observable_15_0= RULE_OBSERVABLE ) )
            	            {
            	            // InternalKactors.g:420:10: ( (lv_observable_15_0= RULE_OBSERVABLE ) )
            	            // InternalKactors.g:421:11: (lv_observable_15_0= RULE_OBSERVABLE )
            	            {
            	            // InternalKactors.g:421:11: (lv_observable_15_0= RULE_OBSERVABLE )
            	            // InternalKactors.g:422:12: lv_observable_15_0= RULE_OBSERVABLE
            	            {
            	            lv_observable_15_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_observable_15_0, grammarAccess.getPreambleAccess().getObservableOBSERVABLETerminalRuleCall_2_2_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"observable",
            	              													lv_observable_15_0,
            	              													"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:439:10: ( (lv_observables_16_0= ruleList ) )
            	            {
            	            // InternalKactors.g:439:10: ( (lv_observables_16_0= ruleList ) )
            	            // InternalKactors.g:440:11: (lv_observables_16_0= ruleList )
            	            {
            	            // InternalKactors.g:440:11: (lv_observables_16_0= ruleList )
            	            // InternalKactors.g:441:12: lv_observables_16_0= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_2_2_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_5);
            	            lv_observables_16_0=ruleList();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	              												}
            	              												set(
            	              													current,
            	              													"observables",
            	              													lv_observables_16_0,
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

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKactors.g:465:4: ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:465:4: ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:466:5: {...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:466:105: ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:467:6: ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:470:9: ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:470:10: {...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:470:19: (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:470:20: otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) )
            	    {
            	    otherlv_17=(Token)match(input,34,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_17, grammarAccess.getPreambleAccess().getLabelKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:474:9: ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) )
            	    // InternalKactors.g:475:10: ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:475:10: ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) )
            	    // InternalKactors.g:476:11: (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:476:11: (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING )
            	    int alt10=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt10=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt10=2;
            	        }
            	        break;
            	    case RULE_STRING:
            	        {
            	        alt10=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt10) {
            	        case 1 :
            	            // InternalKactors.g:477:12: lv_label_18_1= RULE_LOWERCASE_ID
            	            {
            	            lv_label_18_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_18_1, grammarAccess.getPreambleAccess().getLabelLOWERCASE_IDTerminalRuleCall_2_3_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_18_1,
            	              													"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:492:12: lv_label_18_2= RULE_ID
            	            {
            	            lv_label_18_2=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_18_2, grammarAccess.getPreambleAccess().getLabelIDTerminalRuleCall_2_3_1_0_1());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_18_2,
            	              													"org.eclipse.xtext.common.Terminals.ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKactors.g:507:12: lv_label_18_3= RULE_STRING
            	            {
            	            lv_label_18_3=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_18_3, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_3_1_0_2());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_18_3,
            	              													"org.eclipse.xtext.common.Terminals.STRING");
            	              											
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKactors.g:530:4: ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:530:4: ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:531:5: {...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
            	    }
            	    // InternalKactors.g:531:105: ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:532:6: ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
            	    // InternalKactors.g:535:9: ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:535:10: {...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:535:19: (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) )
            	    // InternalKactors.g:535:20: otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) )
            	    {
            	    otherlv_19=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_19, grammarAccess.getPreambleAccess().getDescriptionKeyword_2_4_0());
            	      								
            	    }
            	    // InternalKactors.g:539:9: ( (lv_description_20_0= RULE_STRING ) )
            	    // InternalKactors.g:540:10: (lv_description_20_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:540:10: (lv_description_20_0= RULE_STRING )
            	    // InternalKactors.g:541:11: lv_description_20_0= RULE_STRING
            	    {
            	    lv_description_20_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_description_20_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_2_4_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"description",
            	      												lv_description_20_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalKactors.g:563:4: ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:563:4: ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:564:5: {...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
            	    }
            	    // InternalKactors.g:564:105: ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:565:6: ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
            	    // InternalKactors.g:568:9: ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:568:10: {...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:568:19: (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) )
            	    // InternalKactors.g:568:20: otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) )
            	    {
            	    otherlv_21=(Token)match(input,36,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_21, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_5_0());
            	      								
            	    }
            	    // InternalKactors.g:572:9: ( (lv_permissions_22_0= RULE_STRING ) )
            	    // InternalKactors.g:573:10: (lv_permissions_22_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:573:10: (lv_permissions_22_0= RULE_STRING )
            	    // InternalKactors.g:574:11: lv_permissions_22_0= RULE_STRING
            	    {
            	    lv_permissions_22_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_22_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_5_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"permissions",
            	      												lv_permissions_22_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKactors.g:596:4: ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:596:4: ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:597:5: {...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
            	    }
            	    // InternalKactors.g:597:105: ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:598:6: ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
            	    // InternalKactors.g:601:9: ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+
            	    int cnt11=0;
            	    loop11:
            	    do {
            	        int alt11=2;
            	        int LA11_0 = input.LA(1);

            	        if ( (LA11_0==37) ) {
            	            int LA11_2 = input.LA(2);

            	            if ( ((synpred21_InternalKactors()&&(true))) ) {
            	                alt11=1;
            	            }


            	        }


            	        switch (alt11) {
            	    	case 1 :
            	    	    // InternalKactors.g:601:10: {...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:601:19: (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:601:20: otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_23=(Token)match(input,37,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_23, grammarAccess.getPreambleAccess().getAuthorKeyword_2_6_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:605:9: ( (lv_authors_24_0= RULE_STRING ) )
            	    	    // InternalKactors.g:606:10: (lv_authors_24_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:606:10: (lv_authors_24_0= RULE_STRING )
            	    	    // InternalKactors.g:607:11: lv_authors_24_0= RULE_STRING
            	    	    {
            	    	    lv_authors_24_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_24_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_6_1_0());
            	    	      										
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElement(grammarAccess.getPreambleRule());
            	    	      											}
            	    	      											addWithLastConsumed(
            	    	      												current,
            	    	      												"authors",
            	    	      												lv_authors_24_0,
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

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:629:4: ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:629:4: ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:630:5: {...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
            	    }
            	    // InternalKactors.g:630:105: ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:631:6: ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
            	    // InternalKactors.g:634:9: ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:634:10: {...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:634:19: (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:634:20: otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) )
            	    {
            	    otherlv_25=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_25, grammarAccess.getPreambleAccess().getVersionKeyword_2_7_0());
            	      								
            	    }
            	    // InternalKactors.g:638:9: ( (lv_version_26_0= ruleVersionNumber ) )
            	    // InternalKactors.g:639:10: (lv_version_26_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:639:10: (lv_version_26_0= ruleVersionNumber )
            	    // InternalKactors.g:640:11: lv_version_26_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_version_26_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_26_0,
            	      												"org.integratedmodelling.kactors.Kactors.VersionNumber");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 9 :
            	    // InternalKactors.g:663:4: ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:663:4: ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:664:5: {...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
            	    }
            	    // InternalKactors.g:664:105: ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:665:6: ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
            	    // InternalKactors.g:668:9: ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:668:10: {...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:668:19: (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? )
            	    // InternalKactors.g:668:20: otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )?
            	    {
            	    otherlv_27=(Token)match(input,39,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_27, grammarAccess.getPreambleAccess().getCreatedKeyword_2_8_0());
            	      								
            	    }
            	    // InternalKactors.g:672:9: ( (lv_created_28_0= ruleDate ) )
            	    // InternalKactors.g:673:10: (lv_created_28_0= ruleDate )
            	    {
            	    // InternalKactors.g:673:10: (lv_created_28_0= ruleDate )
            	    // InternalKactors.g:674:11: lv_created_28_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_created_28_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"created",
            	      												lv_created_28_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:691:9: ( (lv_createcomment_29_0= RULE_STRING ) )?
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==RULE_STRING) ) {
            	        alt12=1;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // InternalKactors.g:692:10: (lv_createcomment_29_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:692:10: (lv_createcomment_29_0= RULE_STRING )
            	            // InternalKactors.g:693:11: lv_createcomment_29_0= RULE_STRING
            	            {
            	            lv_createcomment_29_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_createcomment_29_0, grammarAccess.getPreambleAccess().getCreatecommentSTRINGTerminalRuleCall_2_8_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"createcomment",
            	              												lv_createcomment_29_0,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 10 :
            	    // InternalKactors.g:715:4: ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:715:4: ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:716:5: {...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
            	    }
            	    // InternalKactors.g:716:105: ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:717:6: ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
            	    // InternalKactors.g:720:9: ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:720:10: {...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:720:19: (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? )
            	    // InternalKactors.g:720:20: otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )?
            	    {
            	    otherlv_30=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_30, grammarAccess.getPreambleAccess().getModifiedKeyword_2_9_0());
            	      								
            	    }
            	    // InternalKactors.g:724:9: ( (lv_modified_31_0= ruleDate ) )
            	    // InternalKactors.g:725:10: (lv_modified_31_0= ruleDate )
            	    {
            	    // InternalKactors.g:725:10: (lv_modified_31_0= ruleDate )
            	    // InternalKactors.g:726:11: lv_modified_31_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_modified_31_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"modified",
            	      												lv_modified_31_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:743:9: ( (lv_modcomment_32_0= RULE_STRING ) )?
            	    int alt13=2;
            	    int LA13_0 = input.LA(1);

            	    if ( (LA13_0==RULE_STRING) ) {
            	        alt13=1;
            	    }
            	    switch (alt13) {
            	        case 1 :
            	            // InternalKactors.g:744:10: (lv_modcomment_32_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:744:10: (lv_modcomment_32_0= RULE_STRING )
            	            // InternalKactors.g:745:11: lv_modcomment_32_0= RULE_STRING
            	            {
            	            lv_modcomment_32_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_modcomment_32_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_2_9_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"modcomment",
            	              												lv_modcomment_32_0,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

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

            getUnorderedGroupHelper().leave(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

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
    // InternalKactors.g:781:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:781:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:782:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:788:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) ;
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
            // InternalKactors.g:794:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) )
            // InternalKactors.g:795:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            {
            // InternalKactors.g:795:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            // InternalKactors.g:796:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) )
            {
            // InternalKactors.g:796:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ANNOTATION_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalKactors.g:797:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:797:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:798:5: lv_annotations_0_0= ruleAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDefinitionAccess().getAnnotationsAnnotationParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            // InternalKactors.g:819:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:820:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:820:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:821:5: lv_name_2_0= RULE_LOWERCASE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_13); if (state.failed) return current;
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

            // InternalKactors.g:837:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==43) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalKactors.g:838:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:838:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:839:5: lv_arguments_3_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_14);
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

            otherlv_4=(Token)match(input,42,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:860:3: ( (lv_body_5_0= ruleMessageBody ) )
            // InternalKactors.g:861:4: (lv_body_5_0= ruleMessageBody )
            {
            // InternalKactors.g:861:4: (lv_body_5_0= ruleMessageBody )
            // InternalKactors.g:862:5: lv_body_5_0= ruleMessageBody
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
    // InternalKactors.g:883:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:883:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:884:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:890:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:896:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:897:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:897:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:898:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:898:3: ()
            // InternalKactors.g:899:4: 
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

            otherlv_1=(Token)match(input,43,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:912:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_LOWERCASE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalKactors.g:913:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:913:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:914:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:914:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:915:6: lv_ids_2_0= RULE_LOWERCASE_ID
                    {
                    lv_ids_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_17); if (state.failed) return current;
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

                    // InternalKactors.g:931:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==31) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalKactors.g:932:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:936:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:937:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:937:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:938:7: lv_ids_4_0= RULE_LOWERCASE_ID
                    	    {
                    	    lv_ids_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_17); if (state.failed) return current;
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
    // InternalKactors.g:964:1: entryRuleMessageBody returns [EObject current=null] : iv_ruleMessageBody= ruleMessageBody EOF ;
    public final EObject entryRuleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageBody = null;


        try {
            // InternalKactors.g:964:52: (iv_ruleMessageBody= ruleMessageBody EOF )
            // InternalKactors.g:965:2: iv_ruleMessageBody= ruleMessageBody EOF
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
    // InternalKactors.g:971:1: ruleMessageBody returns [EObject current=null] : ( () ( (lv_lists_1_0= ruleStatementList ) )* ) ;
    public final EObject ruleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject lv_lists_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:977:2: ( ( () ( (lv_lists_1_0= ruleStatementList ) )* ) )
            // InternalKactors.g:978:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            {
            // InternalKactors.g:978:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            // InternalKactors.g:979:3: () ( (lv_lists_1_0= ruleStatementList ) )*
            {
            // InternalKactors.g:979:3: ()
            // InternalKactors.g:980:4: 
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

            // InternalKactors.g:989:3: ( (lv_lists_1_0= ruleStatementList ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=RULE_OBSERVABLE && LA19_0<=RULE_LOWERCASE_ID)||(LA19_0>=RULE_STRING && LA19_0<=RULE_ARGVALUE)||LA19_0==RULE_INT||LA19_0==43||(LA19_0>=45 && LA19_0<=46)||(LA19_0>=48 && LA19_0<=50)||(LA19_0>=53 && LA19_0<=54)||LA19_0==58||LA19_0==61||LA19_0==66||(LA19_0>=78 && LA19_0<=79)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalKactors.g:990:4: (lv_lists_1_0= ruleStatementList )
            	    {
            	    // InternalKactors.g:990:4: (lv_lists_1_0= ruleStatementList )
            	    // InternalKactors.g:991:5: lv_lists_1_0= ruleStatementList
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getMessageBodyAccess().getListsStatementListParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_18);
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
    // InternalKactors.g:1012:1: entryRuleMessageCall returns [EObject current=null] : iv_ruleMessageCall= ruleMessageCall EOF ;
    public final EObject entryRuleMessageCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageCall = null;


        try {
            // InternalKactors.g:1012:52: (iv_ruleMessageCall= ruleMessageCall EOF )
            // InternalKactors.g:1013:2: iv_ruleMessageCall= ruleMessageCall EOF
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
    // InternalKactors.g:1019:1: ruleMessageCall returns [EObject current=null] : ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) ;
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
            // InternalKactors.g:1025:2: ( ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) )
            // InternalKactors.g:1026:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            {
            // InternalKactors.g:1026:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            // InternalKactors.g:1027:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            {
            // InternalKactors.g:1027:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | ( (lv_group_4_0= ruleStatementGroup ) ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_LOWERCASE_ID) ) {
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
                    // InternalKactors.g:1028:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    {
                    // InternalKactors.g:1028:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    // InternalKactors.g:1029:5: ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    {
                    // InternalKactors.g:1029:5: ( (lv_name_0_0= rulePathName ) )
                    // InternalKactors.g:1030:6: (lv_name_0_0= rulePathName )
                    {
                    // InternalKactors.g:1030:6: (lv_name_0_0= rulePathName )
                    // InternalKactors.g:1031:7: lv_name_0_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getMessageCallAccess().getNamePathNameParserRuleCall_0_0_0_0());
                      						
                    }
                    pushFollow(FOLLOW_19);
                    lv_name_0_0=rulePathName();

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
                      								"org.integratedmodelling.kactors.Kactors.PathName");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    // InternalKactors.g:1048:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    int alt21=2;
                    alt21 = dfa21.predict(input);
                    switch (alt21) {
                        case 1 :
                            // InternalKactors.g:1049:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                            {
                            otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getMessageCallAccess().getLeftParenthesisKeyword_0_0_1_0());
                              					
                            }
                            // InternalKactors.g:1053:6: ( (lv_parameters_2_0= ruleParameterList ) )?
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( ((LA20_0>=RULE_OBSERVABLE && LA20_0<=RULE_LOWERCASE_ID)||LA20_0==RULE_STRING||(LA20_0>=RULE_EXPR && LA20_0<=RULE_ARGVALUE)||LA20_0==RULE_INT||LA20_0==43||(LA20_0>=53 && LA20_0<=54)||LA20_0==58||LA20_0==61||LA20_0==66||(LA20_0>=78 && LA20_0<=79)) ) {
                                alt20=1;
                            }
                            switch (alt20) {
                                case 1 :
                                    // InternalKactors.g:1054:7: (lv_parameters_2_0= ruleParameterList )
                                    {
                                    // InternalKactors.g:1054:7: (lv_parameters_2_0= ruleParameterList )
                                    // InternalKactors.g:1055:8: lv_parameters_2_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getMessageCallAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_21);
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

                            otherlv_3=(Token)match(input,44,FOLLOW_22); if (state.failed) return current;
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
                    // InternalKactors.g:1079:4: ( (lv_group_4_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1079:4: ( (lv_group_4_0= ruleStatementGroup ) )
                    // InternalKactors.g:1080:5: (lv_group_4_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1080:5: (lv_group_4_0= ruleStatementGroup )
                    // InternalKactors.g:1081:6: lv_group_4_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMessageCallAccess().getGroupStatementGroupParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
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

            // InternalKactors.g:1099:3: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==42) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalKactors.g:1100:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,42,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getMessageCallAccess().getColonKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1104:4: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:1105:5: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:1105:5: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:1106:6: lv_actions_6_0= ruleActions
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
    // InternalKactors.g:1128:1: entryRuleStatementGroup returns [EObject current=null] : iv_ruleStatementGroup= ruleStatementGroup EOF ;
    public final EObject entryRuleStatementGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementGroup = null;


        try {
            // InternalKactors.g:1128:55: (iv_ruleStatementGroup= ruleStatementGroup EOF )
            // InternalKactors.g:1129:2: iv_ruleStatementGroup= ruleStatementGroup EOF
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
    // InternalKactors.g:1135:1: ruleStatementGroup returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) ;
    public final EObject ruleStatementGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1141:2: ( ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) )
            // InternalKactors.g:1142:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            {
            // InternalKactors.g:1142:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            // InternalKactors.g:1143:3: () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')'
            {
            // InternalKactors.g:1143:3: ()
            // InternalKactors.g:1144:4: 
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

            otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStatementGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1157:3: ( (lv_body_2_0= ruleMessageBody ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=RULE_OBSERVABLE && LA24_0<=RULE_LOWERCASE_ID)||(LA24_0>=RULE_STRING && LA24_0<=RULE_ARGVALUE)||LA24_0==RULE_INT||LA24_0==43||(LA24_0>=45 && LA24_0<=46)||(LA24_0>=48 && LA24_0<=50)||(LA24_0>=53 && LA24_0<=54)||LA24_0==58||LA24_0==61||LA24_0==66||(LA24_0>=78 && LA24_0<=79)) ) {
                alt24=1;
            }
            else if ( (LA24_0==44) ) {
                int LA24_2 = input.LA(2);

                if ( (synpred37_InternalKactors()) ) {
                    alt24=1;
                }
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1158:4: (lv_body_2_0= ruleMessageBody )
                    {
                    // InternalKactors.g:1158:4: (lv_body_2_0= ruleMessageBody )
                    // InternalKactors.g:1159:5: lv_body_2_0= ruleMessageBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementGroupAccess().getBodyMessageBodyParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_21);
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

            otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getStatementGroupAccess().getRightParenthesisKeyword_3());
              		
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


    // $ANTLR start "entryRuleStatementList"
    // InternalKactors.g:1184:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalKactors.g:1184:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalKactors.g:1185:2: iv_ruleStatementList= ruleStatementList EOF
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
    // InternalKactors.g:1191:1: ruleStatementList returns [EObject current=null] : ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        EObject lv_next_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1197:2: ( ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) )
            // InternalKactors.g:1198:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            {
            // InternalKactors.g:1198:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            // InternalKactors.g:1199:3: ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )*
            {
            // InternalKactors.g:1199:3: ( (lv_first_0_0= ruleStatement ) )
            // InternalKactors.g:1200:4: (lv_first_0_0= ruleStatement )
            {
            // InternalKactors.g:1200:4: (lv_first_0_0= ruleStatement )
            // InternalKactors.g:1201:5: lv_first_0_0= ruleStatement
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

            // InternalKactors.g:1218:3: ( (lv_next_1_0= ruleNextStatement ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==31) ) {
                    int LA25_2 = input.LA(2);

                    if ( (synpred38_InternalKactors()) ) {
                        alt25=1;
                    }


                }


                switch (alt25) {
            	case 1 :
            	    // InternalKactors.g:1219:4: (lv_next_1_0= ruleNextStatement )
            	    {
            	    // InternalKactors.g:1219:4: (lv_next_1_0= ruleNextStatement )
            	    // InternalKactors.g:1220:5: lv_next_1_0= ruleNextStatement
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
            	    break loop25;
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
    // InternalKactors.g:1241:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:1241:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:1242:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:1248:1: ruleStatement returns [EObject current=null] : ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ;
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
            // InternalKactors.g:1254:2: ( ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            // InternalKactors.g:1255:2: ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            {
            // InternalKactors.g:1255:2: ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            int alt26=9;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1256:3: ( (lv_assignment_0_0= ruleAssignment ) )
                    {
                    // InternalKactors.g:1256:3: ( (lv_assignment_0_0= ruleAssignment ) )
                    // InternalKactors.g:1257:4: (lv_assignment_0_0= ruleAssignment )
                    {
                    // InternalKactors.g:1257:4: (lv_assignment_0_0= ruleAssignment )
                    // InternalKactors.g:1258:5: lv_assignment_0_0= ruleAssignment
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
                    // InternalKactors.g:1276:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1276:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    // InternalKactors.g:1277:4: (lv_group_1_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1277:4: (lv_group_1_0= ruleStatementGroup )
                    // InternalKactors.g:1278:5: lv_group_1_0= ruleStatementGroup
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
                    // InternalKactors.g:1296:3: ( (lv_verb_2_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1296:3: ( (lv_verb_2_0= ruleMessageCall ) )
                    // InternalKactors.g:1297:4: (lv_verb_2_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1297:4: (lv_verb_2_0= ruleMessageCall )
                    // InternalKactors.g:1298:5: lv_verb_2_0= ruleMessageCall
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
                    // InternalKactors.g:1316:3: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1316:3: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1317:4: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1317:4: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1318:5: lv_text_3_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1335:3: ( (lv_if_4_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1335:3: ( (lv_if_4_0= ruleIfStatement ) )
                    // InternalKactors.g:1336:4: (lv_if_4_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1336:4: (lv_if_4_0= ruleIfStatement )
                    // InternalKactors.g:1337:5: lv_if_4_0= ruleIfStatement
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
                    // InternalKactors.g:1355:3: ( (lv_while_5_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1355:3: ( (lv_while_5_0= ruleWhileStatement ) )
                    // InternalKactors.g:1356:4: (lv_while_5_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1356:4: (lv_while_5_0= ruleWhileStatement )
                    // InternalKactors.g:1357:5: lv_while_5_0= ruleWhileStatement
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
                    // InternalKactors.g:1375:3: ( (lv_do_6_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1375:3: ( (lv_do_6_0= ruleDoStatement ) )
                    // InternalKactors.g:1376:4: (lv_do_6_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1376:4: (lv_do_6_0= ruleDoStatement )
                    // InternalKactors.g:1377:5: lv_do_6_0= ruleDoStatement
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
                    // InternalKactors.g:1395:3: ( (lv_for_7_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1395:3: ( (lv_for_7_0= ruleForStatement ) )
                    // InternalKactors.g:1396:4: (lv_for_7_0= ruleForStatement )
                    {
                    // InternalKactors.g:1396:4: (lv_for_7_0= ruleForStatement )
                    // InternalKactors.g:1397:5: lv_for_7_0= ruleForStatement
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
                    // InternalKactors.g:1415:3: ( (lv_value_8_0= ruleValue ) )
                    {
                    // InternalKactors.g:1415:3: ( (lv_value_8_0= ruleValue ) )
                    // InternalKactors.g:1416:4: (lv_value_8_0= ruleValue )
                    {
                    // InternalKactors.g:1416:4: (lv_value_8_0= ruleValue )
                    // InternalKactors.g:1417:5: lv_value_8_0= ruleValue
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
    // InternalKactors.g:1438:1: entryRuleNextStatement returns [EObject current=null] : iv_ruleNextStatement= ruleNextStatement EOF ;
    public final EObject entryRuleNextStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNextStatement = null;


        try {
            // InternalKactors.g:1438:54: (iv_ruleNextStatement= ruleNextStatement EOF )
            // InternalKactors.g:1439:2: iv_ruleNextStatement= ruleNextStatement EOF
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
    // InternalKactors.g:1445:1: ruleNextStatement returns [EObject current=null] : (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) ) ;
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
            // InternalKactors.g:1451:2: ( (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) ) )
            // InternalKactors.g:1452:2: (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) )
            {
            // InternalKactors.g:1452:2: (otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) ) )
            // InternalKactors.g:1453:3: otherlv_0= ',' ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) )
            {
            otherlv_0=(Token)match(input,31,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getNextStatementAccess().getCommaKeyword_0());
              		
            }
            // InternalKactors.g:1457:3: ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) )
            int alt27=9;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalKactors.g:1458:4: ( (lv_assignment_1_0= ruleAssignment ) )
                    {
                    // InternalKactors.g:1458:4: ( (lv_assignment_1_0= ruleAssignment ) )
                    // InternalKactors.g:1459:5: (lv_assignment_1_0= ruleAssignment )
                    {
                    // InternalKactors.g:1459:5: (lv_assignment_1_0= ruleAssignment )
                    // InternalKactors.g:1460:6: lv_assignment_1_0= ruleAssignment
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
                    // InternalKactors.g:1478:4: ( (lv_verb_2_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1478:4: ( (lv_verb_2_0= ruleMessageCall ) )
                    // InternalKactors.g:1479:5: (lv_verb_2_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1479:5: (lv_verb_2_0= ruleMessageCall )
                    // InternalKactors.g:1480:6: lv_verb_2_0= ruleMessageCall
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
                    // InternalKactors.g:1498:4: ( (lv_group_3_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1498:4: ( (lv_group_3_0= ruleStatementGroup ) )
                    // InternalKactors.g:1499:5: (lv_group_3_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1499:5: (lv_group_3_0= ruleStatementGroup )
                    // InternalKactors.g:1500:6: lv_group_3_0= ruleStatementGroup
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
                    // InternalKactors.g:1518:4: ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1518:4: ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1519:5: (lv_text_4_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1519:5: (lv_text_4_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1520:6: lv_text_4_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1537:4: ( (lv_if_5_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1537:4: ( (lv_if_5_0= ruleIfStatement ) )
                    // InternalKactors.g:1538:5: (lv_if_5_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1538:5: (lv_if_5_0= ruleIfStatement )
                    // InternalKactors.g:1539:6: lv_if_5_0= ruleIfStatement
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
                    // InternalKactors.g:1557:4: ( (lv_while_6_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1557:4: ( (lv_while_6_0= ruleWhileStatement ) )
                    // InternalKactors.g:1558:5: (lv_while_6_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1558:5: (lv_while_6_0= ruleWhileStatement )
                    // InternalKactors.g:1559:6: lv_while_6_0= ruleWhileStatement
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
                    // InternalKactors.g:1577:4: ( (lv_do_7_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1577:4: ( (lv_do_7_0= ruleDoStatement ) )
                    // InternalKactors.g:1578:5: (lv_do_7_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1578:5: (lv_do_7_0= ruleDoStatement )
                    // InternalKactors.g:1579:6: lv_do_7_0= ruleDoStatement
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
                    // InternalKactors.g:1597:4: ( (lv_for_8_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1597:4: ( (lv_for_8_0= ruleForStatement ) )
                    // InternalKactors.g:1598:5: (lv_for_8_0= ruleForStatement )
                    {
                    // InternalKactors.g:1598:5: (lv_for_8_0= ruleForStatement )
                    // InternalKactors.g:1599:6: lv_for_8_0= ruleForStatement
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
                    // InternalKactors.g:1617:4: ( (lv_value_9_0= ruleValue ) )
                    {
                    // InternalKactors.g:1617:4: ( (lv_value_9_0= ruleValue ) )
                    // InternalKactors.g:1618:5: (lv_value_9_0= ruleValue )
                    {
                    // InternalKactors.g:1618:5: (lv_value_9_0= ruleValue )
                    // InternalKactors.g:1619:6: lv_value_9_0= ruleValue
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
    // InternalKactors.g:1641:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // InternalKactors.g:1641:51: (iv_ruleAssignment= ruleAssignment EOF )
            // InternalKactors.g:1642:2: iv_ruleAssignment= ruleAssignment EOF
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
    // InternalKactors.g:1648:1: ruleAssignment returns [EObject current=null] : (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_variable_1_0=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1654:2: ( (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:1655:2: (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:1655:2: (otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:1656:3: otherlv_0= 'set' ( (lv_variable_1_0= RULE_LOWERCASE_ID ) ) ( (lv_value_2_0= ruleValue ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getAssignmentAccess().getSetKeyword_0());
              		
            }
            // InternalKactors.g:1660:3: ( (lv_variable_1_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:1661:4: (lv_variable_1_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:1661:4: (lv_variable_1_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:1662:5: lv_variable_1_0= RULE_LOWERCASE_ID
            {
            lv_variable_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_15); if (state.failed) return current;
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

            // InternalKactors.g:1678:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:1679:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:1679:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:1680:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:1701:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:1701:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:1702:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:1708:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) ;
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
            // InternalKactors.g:1714:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) )
            // InternalKactors.g:1715:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            {
            // InternalKactors.g:1715:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            // InternalKactors.g:1716:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            {
            otherlv_0=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:1720:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1721:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1721:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1722:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return current;
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

            // InternalKactors.g:1738:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1739:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1739:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1740:5: lv_body_2_0= ruleStatementBody
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

            // InternalKactors.g:1757:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==47) ) {
                    int LA28_1 = input.LA(2);

                    if ( (synpred55_InternalKactors()) ) {
                        alt28=1;
                    }


                }


                switch (alt28) {
            	case 1 :
            	    // InternalKactors.g:1758:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    {
            	    otherlv_3=(Token)match(input,47,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,46,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:1766:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:1767:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:1767:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:1768:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return current;
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

            	    // InternalKactors.g:1784:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    // InternalKactors.g:1785:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    {
            	    // InternalKactors.g:1785:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    // InternalKactors.g:1786:6: lv_elseIfBody_6_0= ruleStatementBody
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
            	    break loop28;
                }
            } while (true);

            // InternalKactors.g:1804:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==47) ) {
                int LA29_1 = input.LA(2);

                if ( (synpred56_InternalKactors()) ) {
                    alt29=1;
                }
            }
            switch (alt29) {
                case 1 :
                    // InternalKactors.g:1805:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
                    {
                    otherlv_7=(Token)match(input,47,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:1809:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
                    // InternalKactors.g:1810:5: (lv_elseCall_8_0= ruleStatementBody )
                    {
                    // InternalKactors.g:1810:5: (lv_elseCall_8_0= ruleStatementBody )
                    // InternalKactors.g:1811:6: lv_elseCall_8_0= ruleStatementBody
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
    // InternalKactors.g:1833:1: entryRuleStatementBody returns [EObject current=null] : iv_ruleStatementBody= ruleStatementBody EOF ;
    public final EObject entryRuleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementBody = null;


        try {
            // InternalKactors.g:1833:54: (iv_ruleStatementBody= ruleStatementBody EOF )
            // InternalKactors.g:1834:2: iv_ruleStatementBody= ruleStatementBody EOF
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
    // InternalKactors.g:1840:1: ruleStatementBody returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) ;
    public final EObject ruleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject lv_verb_0_0 = null;

        EObject lv_value_1_0 = null;

        EObject lv_group_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1846:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) )
            // InternalKactors.g:1847:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            {
            // InternalKactors.g:1847:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            int alt30=3;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1848:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1848:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1849:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1849:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1850:5: lv_verb_0_0= ruleMessageCall
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
                    // InternalKactors.g:1868:3: ( (lv_value_1_0= ruleValue ) )
                    {
                    // InternalKactors.g:1868:3: ( (lv_value_1_0= ruleValue ) )
                    // InternalKactors.g:1869:4: (lv_value_1_0= ruleValue )
                    {
                    // InternalKactors.g:1869:4: (lv_value_1_0= ruleValue )
                    // InternalKactors.g:1870:5: lv_value_1_0= ruleValue
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
                    // InternalKactors.g:1888:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1888:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1889:4: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1889:4: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1890:5: lv_group_2_0= ruleStatementGroup
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
    // InternalKactors.g:1911:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:1911:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:1912:2: iv_ruleWhileStatement= ruleWhileStatement EOF
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
    // InternalKactors.g:1918:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1924:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1925:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1925:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            // InternalKactors.g:1926:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,48,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:1930:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1931:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1931:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1932:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return current;
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

            // InternalKactors.g:1948:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1949:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1949:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1950:5: lv_body_2_0= ruleStatementBody
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
    // InternalKactors.g:1971:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:1971:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:1972:2: iv_ruleDoStatement= ruleDoStatement EOF
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
    // InternalKactors.g:1978:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1984:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:1985:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:1985:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:1986:3: otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:1990:3: ( (lv_body_1_0= ruleStatementBody ) )
            // InternalKactors.g:1991:4: (lv_body_1_0= ruleStatementBody )
            {
            // InternalKactors.g:1991:4: (lv_body_1_0= ruleStatementBody )
            // InternalKactors.g:1992:5: lv_body_1_0= ruleStatementBody
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
            // InternalKactors.g:2013:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:2014:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:2014:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:2015:5: lv_expression_3_0= RULE_EXPR
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
    // InternalKactors.g:2035:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:2035:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:2036:2: iv_ruleForStatement= ruleForStatement EOF
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
    // InternalKactors.g:2042:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2048:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:2049:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:2049:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            // InternalKactors.g:2050:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,50,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:2054:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_LOWERCASE_ID) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==51) ) {
                    alt31=1;
                }
            }
            switch (alt31) {
                case 1 :
                    // InternalKactors.g:2055:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:2055:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2056:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2056:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2057:6: lv_id_1_0= RULE_LOWERCASE_ID
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

                    otherlv_2=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2078:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:2079:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:2079:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:2080:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_15);
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

            // InternalKactors.g:2097:3: ( (lv_body_4_0= ruleStatementBody ) )
            // InternalKactors.g:2098:4: (lv_body_4_0= ruleStatementBody )
            {
            // InternalKactors.g:2098:4: (lv_body_4_0= ruleStatementBody )
            // InternalKactors.g:2099:5: lv_body_4_0= ruleStatementBody
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
    // InternalKactors.g:2120:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:2120:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:2121:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:2127:1: ruleActions returns [EObject current=null] : ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) ;
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
            // InternalKactors.g:2133:2: ( ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) )
            // InternalKactors.g:2134:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            {
            // InternalKactors.g:2134:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            int alt33=4;
            alt33 = dfa33.predict(input);
            switch (alt33) {
                case 1 :
                    // InternalKactors.g:2135:3: ( (lv_match_0_0= ruleMatch ) )
                    {
                    // InternalKactors.g:2135:3: ( (lv_match_0_0= ruleMatch ) )
                    // InternalKactors.g:2136:4: (lv_match_0_0= ruleMatch )
                    {
                    // InternalKactors.g:2136:4: (lv_match_0_0= ruleMatch )
                    // InternalKactors.g:2137:5: lv_match_0_0= ruleMatch
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
                    // InternalKactors.g:2155:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    {
                    // InternalKactors.g:2155:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    // InternalKactors.g:2156:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2160:4: ( (lv_matches_2_0= ruleMatch ) )
                    // InternalKactors.g:2161:5: (lv_matches_2_0= ruleMatch )
                    {
                    // InternalKactors.g:2161:5: (lv_matches_2_0= ruleMatch )
                    // InternalKactors.g:2162:6: lv_matches_2_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_30);
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

                    // InternalKactors.g:2179:4: ( (lv_matches_3_0= ruleMatch ) )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( ((LA32_0>=RULE_OBSERVABLE && LA32_0<=RULE_LOWERCASE_ID)||LA32_0==RULE_STRING||LA32_0==RULE_EXPR||(LA32_0>=RULE_CAMELCASE_ID && LA32_0<=RULE_INT)||LA32_0==51||(LA32_0>=53 && LA32_0<=57)||(LA32_0>=78 && LA32_0<=79)) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // InternalKactors.g:2180:5: (lv_matches_3_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:2180:5: (lv_matches_3_0= ruleMatch )
                    	    // InternalKactors.g:2181:6: lv_matches_3_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_30);
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
                    	    break loop32;
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
                    // InternalKactors.g:2204:3: ( (lv_statement_5_0= ruleStatement ) )
                    {
                    // InternalKactors.g:2204:3: ( (lv_statement_5_0= ruleStatement ) )
                    // InternalKactors.g:2205:4: (lv_statement_5_0= ruleStatement )
                    {
                    // InternalKactors.g:2205:4: (lv_statement_5_0= ruleStatement )
                    // InternalKactors.g:2206:5: lv_statement_5_0= ruleStatement
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
                    // InternalKactors.g:2224:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    {
                    // InternalKactors.g:2224:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    // InternalKactors.g:2225:4: otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,43,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:2229:4: ( (lv_statements_7_0= ruleStatementList ) )
                    // InternalKactors.g:2230:5: (lv_statements_7_0= ruleStatementList )
                    {
                    // InternalKactors.g:2230:5: (lv_statements_7_0= ruleStatementList )
                    // InternalKactors.g:2231:6: lv_statements_7_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getStatementsStatementListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
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
    // InternalKactors.g:2257:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:2257:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:2258:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:2264:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
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
            // InternalKactors.g:2270:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) ) )
            // InternalKactors.g:2271:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) )
            {
            // InternalKactors.g:2271:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) )
            int alt34=10;
            alt34 = dfa34.predict(input);
            switch (alt34) {
                case 1 :
                    // InternalKactors.g:2272:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:2272:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:2273:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:2273:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:2274:5: lv_argvalue_0_0= RULE_ARGVALUE
                    {
                    lv_argvalue_0_0=(Token)match(input,RULE_ARGVALUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_argvalue_0_0, grammarAccess.getValueAccess().getArgvalueARGVALUETerminalRuleCall_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2291:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:2291:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:2292:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:2292:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:2293:5: lv_literal_1_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_literal_1_0=ruleLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2311:3: ( (lv_id_2_0= rulePathName ) )
                    {
                    // InternalKactors.g:2311:3: ( (lv_id_2_0= rulePathName ) )
                    // InternalKactors.g:2312:4: (lv_id_2_0= rulePathName )
                    {
                    // InternalKactors.g:2312:4: (lv_id_2_0= rulePathName )
                    // InternalKactors.g:2313:5: lv_id_2_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getIdPathNameParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_id_2_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2331:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:2331:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:2332:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:2332:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:2333:5: lv_urn_3_0= ruleUrnId
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
                    // InternalKactors.g:2351:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:2351:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:2352:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:2352:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:2353:5: lv_list_4_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_list_4_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2371:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:2371:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:2372:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:2372:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:2373:5: lv_map_5_0= ruleMap
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getMapMapParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_map_5_0=ruleMap();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2391:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:2391:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2392:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2392:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2393:5: lv_observable_6_0= RULE_OBSERVABLE
                    {
                    lv_observable_6_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_6_0, grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2410:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:2410:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:2411:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2411:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:2412:5: lv_expression_7_0= RULE_EXPR
                    {
                    lv_expression_7_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expression_7_0, grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2429:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:2429:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:2430:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:2430:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:2431:5: lv_table_8_0= ruleLookupTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getTableLookupTableParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_table_8_0=ruleLookupTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
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
                    // InternalKactors.g:2449:3: ( (lv_quantity_9_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:2449:3: ( (lv_quantity_9_0= ruleQuantity ) )
                    // InternalKactors.g:2450:4: (lv_quantity_9_0= ruleQuantity )
                    {
                    // InternalKactors.g:2450:4: (lv_quantity_9_0= ruleQuantity )
                    // InternalKactors.g:2451:5: lv_quantity_9_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getQuantityQuantityParserRuleCall_9_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_quantity_9_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
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
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleMatch"
    // InternalKactors.g:2472:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:2472:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:2473:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:2479:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) ) | ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) ) ;
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
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_23=null;
        Token lv_expr_25_0=null;
        Token otherlv_26=null;
        Token lv_nodata_28_0=null;
        Token otherlv_29=null;
        Token lv_star_31_0=null;
        Token otherlv_32=null;
        Token lv_anything_34_0=null;
        Token otherlv_35=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_set_19_0 = null;

        EObject lv_body_21_0 = null;

        EObject lv_quantity_22_0 = null;

        EObject lv_body_24_0 = null;

        EObject lv_body_27_0 = null;

        EObject lv_body_30_0 = null;

        EObject lv_body_33_0 = null;

        EObject lv_body_36_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2485:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) ) | ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) ) )
            // InternalKactors.g:2486:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) ) | ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) )
            {
            // InternalKactors.g:2486:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) ) | ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) )
            int alt36=12;
            alt36 = dfa36.predict(input);
            switch (alt36) {
                case 1 :
                    // InternalKactors.g:2487:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2487:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    // InternalKactors.g:2488:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2488:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2489:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2489:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2490:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_31); if (state.failed) return current;
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

                    otherlv_1=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:2510:4: ( (lv_body_2_0= ruleStatementList ) )
                    // InternalKactors.g:2511:5: (lv_body_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:2511:5: (lv_body_2_0= ruleStatementList )
                    // InternalKactors.g:2512:6: lv_body_2_0= ruleStatementList
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
                    // InternalKactors.g:2531:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2531:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    // InternalKactors.g:2532:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2532:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
                    // InternalKactors.g:2533:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    {
                    // InternalKactors.g:2533:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    // InternalKactors.g:2534:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    {
                    // InternalKactors.g:2534:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==53) ) {
                        alt35=1;
                    }
                    else if ( (LA35_0==54) ) {
                        alt35=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 35, 0, input);

                        throw nvae;
                    }
                    switch (alt35) {
                        case 1 :
                            // InternalKactors.g:2535:7: lv_boolean_3_1= 'true'
                            {
                            lv_boolean_3_1=(Token)match(input,53,FOLLOW_31); if (state.failed) return current;
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
                            // InternalKactors.g:2546:7: lv_boolean_3_2= 'false'
                            {
                            lv_boolean_3_2=(Token)match(input,54,FOLLOW_31); if (state.failed) return current;
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

                    otherlv_4=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:2563:4: ( (lv_body_5_0= ruleStatementList ) )
                    // InternalKactors.g:2564:5: (lv_body_5_0= ruleStatementList )
                    {
                    // InternalKactors.g:2564:5: (lv_body_5_0= ruleStatementList )
                    // InternalKactors.g:2565:6: lv_body_5_0= ruleStatementList
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
                    // InternalKactors.g:2584:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2584:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    // InternalKactors.g:2585:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2585:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) )
                    // InternalKactors.g:2586:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    {
                    // InternalKactors.g:2586:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    // InternalKactors.g:2587:6: lv_type_6_0= RULE_CAMELCASE_ID
                    {
                    lv_type_6_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_31); if (state.failed) return current;
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

                    otherlv_7=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:2607:4: ( (lv_body_8_0= ruleStatementList ) )
                    // InternalKactors.g:2608:5: (lv_body_8_0= ruleStatementList )
                    {
                    // InternalKactors.g:2608:5: (lv_body_8_0= ruleStatementList )
                    // InternalKactors.g:2609:6: lv_body_8_0= ruleStatementList
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
                    // InternalKactors.g:2628:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2628:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    // InternalKactors.g:2629:4: ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2629:4: ( (lv_regexp_9_0= RULE_REGEXP ) )
                    // InternalKactors.g:2630:5: (lv_regexp_9_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:2630:5: (lv_regexp_9_0= RULE_REGEXP )
                    // InternalKactors.g:2631:6: lv_regexp_9_0= RULE_REGEXP
                    {
                    lv_regexp_9_0=(Token)match(input,RULE_REGEXP,FOLLOW_31); if (state.failed) return current;
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

                    otherlv_10=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:2651:4: ( (lv_body_11_0= ruleStatementList ) )
                    // InternalKactors.g:2652:5: (lv_body_11_0= ruleStatementList )
                    {
                    // InternalKactors.g:2652:5: (lv_body_11_0= ruleStatementList )
                    // InternalKactors.g:2653:6: lv_body_11_0= ruleStatementList
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
                    // InternalKactors.g:2672:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2672:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    // InternalKactors.g:2673:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2673:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2674:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2674:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2675:6: lv_observable_12_0= RULE_OBSERVABLE
                    {
                    lv_observable_12_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_31); if (state.failed) return current;
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

                    otherlv_13=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:2695:4: ( (lv_body_14_0= ruleStatementList ) )
                    // InternalKactors.g:2696:5: (lv_body_14_0= ruleStatementList )
                    {
                    // InternalKactors.g:2696:5: (lv_body_14_0= ruleStatementList )
                    // InternalKactors.g:2697:6: lv_body_14_0= ruleStatementList
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
                    // InternalKactors.g:2716:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2716:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    // InternalKactors.g:2717:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2717:4: ( (lv_literal_15_0= ruleLiteral ) )
                    // InternalKactors.g:2718:5: (lv_literal_15_0= ruleLiteral )
                    {
                    // InternalKactors.g:2718:5: (lv_literal_15_0= ruleLiteral )
                    // InternalKactors.g:2719:6: lv_literal_15_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
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

                    otherlv_16=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:2740:4: ( (lv_body_17_0= ruleStatementList ) )
                    // InternalKactors.g:2741:5: (lv_body_17_0= ruleStatementList )
                    {
                    // InternalKactors.g:2741:5: (lv_body_17_0= ruleStatementList )
                    // InternalKactors.g:2742:6: lv_body_17_0= ruleStatementList
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
                    // InternalKactors.g:2761:3: (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2761:3: (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) )
                    // InternalKactors.g:2762:4: otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) )
                    {
                    otherlv_18=(Token)match(input,51,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_18, grammarAccess.getMatchAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:2766:4: ( (lv_set_19_0= ruleList ) )
                    // InternalKactors.g:2767:5: (lv_set_19_0= ruleList )
                    {
                    // InternalKactors.g:2767:5: (lv_set_19_0= ruleList )
                    // InternalKactors.g:2768:6: lv_set_19_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getSetListParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
                    lv_set_19_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_19_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_20=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_20, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_2());
                      			
                    }
                    // InternalKactors.g:2789:4: ( (lv_body_21_0= ruleStatementList ) )
                    // InternalKactors.g:2790:5: (lv_body_21_0= ruleStatementList )
                    {
                    // InternalKactors.g:2790:5: (lv_body_21_0= ruleStatementList )
                    // InternalKactors.g:2791:6: lv_body_21_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_6_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_21_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_21_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:2810:3: ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2810:3: ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) )
                    // InternalKactors.g:2811:4: ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2811:4: ( (lv_quantity_22_0= ruleQuantity ) )
                    // InternalKactors.g:2812:5: (lv_quantity_22_0= ruleQuantity )
                    {
                    // InternalKactors.g:2812:5: (lv_quantity_22_0= ruleQuantity )
                    // InternalKactors.g:2813:6: lv_quantity_22_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
                    lv_quantity_22_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"quantity",
                      							lv_quantity_22_0,
                      							"org.integratedmodelling.kactors.Kactors.Quantity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_23=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_23, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_1());
                      			
                    }
                    // InternalKactors.g:2834:4: ( (lv_body_24_0= ruleStatementList ) )
                    // InternalKactors.g:2835:5: (lv_body_24_0= ruleStatementList )
                    {
                    // InternalKactors.g:2835:5: (lv_body_24_0= ruleStatementList )
                    // InternalKactors.g:2836:6: lv_body_24_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_2_0());
                      					
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
                    // InternalKactors.g:2855:3: ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2855:3: ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) )
                    // InternalKactors.g:2856:4: ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2856:4: ( (lv_expr_25_0= RULE_EXPR ) )
                    // InternalKactors.g:2857:5: (lv_expr_25_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2857:5: (lv_expr_25_0= RULE_EXPR )
                    // InternalKactors.g:2858:6: lv_expr_25_0= RULE_EXPR
                    {
                    lv_expr_25_0=(Token)match(input,RULE_EXPR,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_expr_25_0, grammarAccess.getMatchAccess().getExprEXPRTerminalRuleCall_8_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"expr",
                      							lv_expr_25_0,
                      							"org.integratedmodelling.kactors.Kactors.EXPR");
                      					
                    }

                    }


                    }

                    otherlv_26=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_26, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_1());
                      			
                    }
                    // InternalKactors.g:2878:4: ( (lv_body_27_0= ruleStatementList ) )
                    // InternalKactors.g:2879:5: (lv_body_27_0= ruleStatementList )
                    {
                    // InternalKactors.g:2879:5: (lv_body_27_0= ruleStatementList )
                    // InternalKactors.g:2880:6: lv_body_27_0= ruleStatementList
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
                    // InternalKactors.g:2899:3: ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2899:3: ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) )
                    // InternalKactors.g:2900:4: ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2900:4: ( (lv_nodata_28_0= 'unknown' ) )
                    // InternalKactors.g:2901:5: (lv_nodata_28_0= 'unknown' )
                    {
                    // InternalKactors.g:2901:5: (lv_nodata_28_0= 'unknown' )
                    // InternalKactors.g:2902:6: lv_nodata_28_0= 'unknown'
                    {
                    lv_nodata_28_0=(Token)match(input,55,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nodata_28_0, grammarAccess.getMatchAccess().getNodataUnknownKeyword_9_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "nodata", lv_nodata_28_0, "unknown");
                      					
                    }

                    }


                    }

                    otherlv_29=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_29, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_1());
                      			
                    }
                    // InternalKactors.g:2918:4: ( (lv_body_30_0= ruleStatementList ) )
                    // InternalKactors.g:2919:5: (lv_body_30_0= ruleStatementList )
                    {
                    // InternalKactors.g:2919:5: (lv_body_30_0= ruleStatementList )
                    // InternalKactors.g:2920:6: lv_body_30_0= ruleStatementList
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
                    // InternalKactors.g:2939:3: ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2939:3: ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) )
                    // InternalKactors.g:2940:4: ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2940:4: ( (lv_star_31_0= '*' ) )
                    // InternalKactors.g:2941:5: (lv_star_31_0= '*' )
                    {
                    // InternalKactors.g:2941:5: (lv_star_31_0= '*' )
                    // InternalKactors.g:2942:6: lv_star_31_0= '*'
                    {
                    lv_star_31_0=(Token)match(input,56,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_star_31_0, grammarAccess.getMatchAccess().getStarAsteriskKeyword_10_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "star", true, "*");
                      					
                    }

                    }


                    }

                    otherlv_32=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_32, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:2958:4: ( (lv_body_33_0= ruleStatementList ) )
                    // InternalKactors.g:2959:5: (lv_body_33_0= ruleStatementList )
                    {
                    // InternalKactors.g:2959:5: (lv_body_33_0= ruleStatementList )
                    // InternalKactors.g:2960:6: lv_body_33_0= ruleStatementList
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
                    // InternalKactors.g:2979:3: ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2979:3: ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    // InternalKactors.g:2980:4: ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2980:4: ( (lv_anything_34_0= '#' ) )
                    // InternalKactors.g:2981:5: (lv_anything_34_0= '#' )
                    {
                    // InternalKactors.g:2981:5: (lv_anything_34_0= '#' )
                    // InternalKactors.g:2982:6: lv_anything_34_0= '#'
                    {
                    lv_anything_34_0=(Token)match(input,57,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_anything_34_0, grammarAccess.getMatchAccess().getAnythingNumberSignKeyword_11_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "anything", true, "#");
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,52,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:2998:4: ( (lv_body_36_0= ruleStatementList ) )
                    // InternalKactors.g:2999:5: (lv_body_36_0= ruleStatementList )
                    {
                    // InternalKactors.g:2999:5: (lv_body_36_0= ruleStatementList )
                    // InternalKactors.g:3000:6: lv_body_36_0= ruleStatementList
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

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
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
    // InternalKactors.g:3022:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:3022:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:3023:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:3029:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
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
            // InternalKactors.g:3035:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:3036:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:3036:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:3037:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:3037:3: (kw= 'urn:klab:' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==58) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:3038:4: kw= 'urn:klab:'
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
            pushFollow(FOLLOW_14);
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
            pushFollow(FOLLOW_14);
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
            pushFollow(FOLLOW_14);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:3089:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_LOWERCASE_ID||LA38_0==RULE_UPPERCASE_ID) ) {
                alt38=1;
            }
            else if ( (LA38_0==RULE_INT) ) {
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
                    // InternalKactors.g:3090:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_33);
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
                    // InternalKactors.g:3101:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_33); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3109:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==42) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalKactors.g:3110:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_34);
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

            // InternalKactors.g:3126:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==57) ) {
                int LA43_1 = input.LA(2);

                if ( (LA43_1==RULE_LOWERCASE_ID||LA43_1==RULE_UPPERCASE_ID) ) {
                    alt43=1;
                }
            }
            switch (alt43) {
                case 1 :
                    // InternalKactors.g:3127:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,57,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:3132:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt40=2;
                    alt40 = dfa40.predict(input);
                    switch (alt40) {
                        case 1 :
                            // InternalKactors.g:3133:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_36);
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
                            // InternalKactors.g:3144:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_36);
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

                    // InternalKactors.g:3155:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==59) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // InternalKactors.g:3156:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,59,FOLLOW_35); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:3161:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt41=2;
                    	    alt41 = dfa41.predict(input);
                    	    switch (alt41) {
                    	        case 1 :
                    	            // InternalKactors.g:3162:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_36);
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
                    	            // InternalKactors.g:3173:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_36);
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
                    	    break loop42;
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
    // InternalKactors.g:3190:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:3190:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:3191:2: iv_ruleUrnKvp= ruleUrnKvp EOF
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
    // InternalKactors.g:3197:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:3203:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:3204:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:3204:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:3205:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_37);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,60,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:3220:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==RULE_LOWERCASE_ID||LA44_0==RULE_UPPERCASE_ID) ) {
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
                    // InternalKactors.g:3221:4: this_Path_2= rulePath
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
                    // InternalKactors.g:3232:4: this_INT_3= RULE_INT
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
    // InternalKactors.g:3244:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:3244:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:3245:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:3251:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3257:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:3258:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:3258:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:3259:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:3259:3: ()
            // InternalKactors.g:3260:4: 
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

            otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:3273:3: ( (lv_contents_2_0= ruleValue ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=RULE_OBSERVABLE && LA45_0<=RULE_LOWERCASE_ID)||LA45_0==RULE_STRING||(LA45_0>=RULE_EXPR && LA45_0<=RULE_ARGVALUE)||LA45_0==RULE_INT||LA45_0==43||(LA45_0>=53 && LA45_0<=54)||LA45_0==58||LA45_0==61||LA45_0==66||(LA45_0>=78 && LA45_0<=79)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalKactors.g:3274:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:3274:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:3275:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_20);
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
            	    break loop45;
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
    // InternalKactors.g:3300:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:3300:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:3301:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:3307:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3313:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:3314:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:3314:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:3315:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:3315:3: ()
            // InternalKactors.g:3316:4: 
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

            otherlv_1=(Token)match(input,61,FOLLOW_38); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3329:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( ((LA47_0>=RULE_OBSERVABLE && LA47_0<=RULE_LOWERCASE_ID)||LA47_0==RULE_STRING||LA47_0==RULE_INT||LA47_0==51||(LA47_0>=53 && LA47_0<=56)||LA47_0==60||(LA47_0>=73 && LA47_0<=79)) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalKactors.g:3330:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:3330:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:3331:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:3331:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:3332:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_39);
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

                    // InternalKactors.g:3349:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==31) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // InternalKactors.g:3350:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:3350:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:3351:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:3358:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:3359:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:3359:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:3360:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_39);
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
                    	    break loop46;
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


    // $ANTLR start "entryRuleMapEntry"
    // InternalKactors.g:3387:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:3387:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:3388:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:3394:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3400:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:3401:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:3401:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:3402:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:3402:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:3403:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:3403:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:3404:5: lv_classifier_0_0= ruleClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_14);
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

            otherlv_1=(Token)match(input,42,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:3425:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:3426:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:3426:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:3427:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:3448:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:3448:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:3449:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:3455:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
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
            // InternalKactors.g:3461:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:3462:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:3462:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt51=10;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalKactors.g:3463:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3463:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==53) ) {
                        alt48=1;
                    }
                    else if ( (LA48_0==54) ) {
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
                            // InternalKactors.g:3464:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3464:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3465:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3465:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3466:6: lv_boolean_0_0= 'true'
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
                            // InternalKactors.g:3479:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3479:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:3480:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:3480:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:3481:6: lv_boolean_1_0= 'false'
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
                    // InternalKactors.g:3495:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:3495:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:3496:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:3496:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:3497:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:3497:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:3498:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_41);
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

                    // InternalKactors.g:3515:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt49=3;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==63) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==64) ) {
                        alt49=2;
                    }
                    switch (alt49) {
                        case 1 :
                            // InternalKactors.g:3516:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3516:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:3517:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:3517:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:3518:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,63,FOLLOW_42); if (state.failed) return current;
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
                            // InternalKactors.g:3531:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,64,FOLLOW_42); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:3536:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:3537:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,65,FOLLOW_43); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:3543:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:3544:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:3548:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:3549:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKactors.g:3566:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt50=3;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==63) ) {
                        alt50=1;
                    }
                    else if ( (LA50_0==64) ) {
                        alt50=2;
                    }
                    switch (alt50) {
                        case 1 :
                            // InternalKactors.g:3567:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3567:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:3568:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:3568:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:3569:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3582:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3589:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3589:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:3590:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:3590:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:3591:5: lv_num_9_0= ruleNumber
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
                    // InternalKactors.g:3609:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:3609:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:3610:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,51,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3614:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:3615:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:3615:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:3616:6: lv_set_11_0= ruleList
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
                    // InternalKactors.g:3635:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3635:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:3636:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:3636:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:3637:5: lv_string_12_0= RULE_STRING
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
                    // InternalKactors.g:3654:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:3654:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:3655:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:3655:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:3656:5: lv_observable_13_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:3673:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:3673:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:3674:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:3674:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:3675:5: lv_id_14_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:3692:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3692:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:3693:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3693:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:3694:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:3694:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:3695:6: lv_op_15_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
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

                    // InternalKactors.g:3712:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:3713:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:3713:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:3714:6: lv_expression_16_0= ruleNumber
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
                    // InternalKactors.g:3733:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:3733:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:3734:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:3734:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:3735:5: lv_nodata_17_0= 'unknown'
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
                    // InternalKactors.g:3748:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:3748:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:3749:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:3749:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:3750:5: lv_star_18_0= '*'
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
    // InternalKactors.g:3766:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:3766:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:3767:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:3773:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3779:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:3780:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:3780:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:3781:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:3781:3: ()
            // InternalKactors.g:3782:4: 
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

            otherlv_1=(Token)match(input,66,FOLLOW_45); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3795:3: ( (lv_table_2_0= ruleTable ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RULE_OBSERVABLE && LA52_0<=RULE_LOWERCASE_ID)||LA52_0==RULE_STRING||LA52_0==RULE_EXPR||LA52_0==RULE_INT||LA52_0==51||(LA52_0>=53 && LA52_0<=57)||LA52_0==60||(LA52_0>=73 && LA52_0<=79)) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalKactors.g:3796:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:3796:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:3797:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_46);
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

            otherlv_3=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3822:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:3822:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:3823:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:3829:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3835:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:3836:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:3836:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:3837:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:3837:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt53=2;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalKactors.g:3838:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:3838:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:3839:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:3839:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:3840:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_47);
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

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_48); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3862:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:3863:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:3863:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:3864:5: lv_rows_2_0= ruleTableRow
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

            // InternalKactors.g:3881:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==31) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalKactors.g:3882:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,31,FOLLOW_48); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:3886:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:3887:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:3887:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:3888:6: lv_rows_4_0= ruleTableRow
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
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleHeaderRow"
    // InternalKactors.g:3910:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:3910:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:3911:2: iv_ruleHeaderRow= ruleHeaderRow EOF
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
    // InternalKactors.g:3917:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:3923:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:3924:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:3924:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:3925:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:3925:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:3926:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:3926:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:3927:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:3927:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_LOWERCASE_ID) ) {
                alt55=1;
            }
            else if ( (LA55_0==RULE_STRING) ) {
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
                    // InternalKactors.g:3928:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_49); if (state.failed) return current;
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
                    // InternalKactors.g:3943:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_49); if (state.failed) return current;
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

            // InternalKactors.g:3960:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==68) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalKactors.g:3961:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,68,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3965:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:3966:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:3966:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:3967:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:3967:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt56=2;
            	    int LA56_0 = input.LA(1);

            	    if ( (LA56_0==RULE_LOWERCASE_ID) ) {
            	        alt56=1;
            	    }
            	    else if ( (LA56_0==RULE_STRING) ) {
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
            	            // InternalKactors.g:3968:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_49); if (state.failed) return current;
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
            	            // InternalKactors.g:3983:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_49); if (state.failed) return current;
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
            	    break loop57;
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
    // InternalKactors.g:4005:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:4005:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:4006:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:4012:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4018:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:4019:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:4019:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:4020:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:4020:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:4021:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:4021:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:4022:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_49);
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

            // InternalKactors.g:4039:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==68) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalKactors.g:4040:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,68,FOLLOW_48); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:4044:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:4045:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:4045:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:4046:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
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
            	    break loop58;
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
    // InternalKactors.g:4068:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:4068:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:4069:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:4075:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
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
            // InternalKactors.g:4081:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:4082:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:4082:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt62=13;
            alt62 = dfa62.predict(input);
            switch (alt62) {
                case 1 :
                    // InternalKactors.g:4083:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:4083:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==53) ) {
                        alt59=1;
                    }
                    else if ( (LA59_0==54) ) {
                        alt59=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 59, 0, input);

                        throw nvae;
                    }
                    switch (alt59) {
                        case 1 :
                            // InternalKactors.g:4084:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:4084:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:4085:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:4085:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:4086:6: lv_boolean_0_0= 'true'
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
                            // InternalKactors.g:4099:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:4099:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:4100:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:4100:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:4101:6: lv_boolean_1_0= 'false'
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
                    // InternalKactors.g:4115:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4115:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:4116:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:4116:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:4117:5: lv_num_2_0= ruleNumber
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
                    // InternalKactors.g:4135:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4135:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:4136:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:4136:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:4137:5: lv_string_3_0= RULE_STRING
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
                    // InternalKactors.g:4154:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4154:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4155:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4155:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4156:5: lv_observable_4_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4173:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4173:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:4174:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4174:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:4175:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:4175:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:4176:6: lv_op_5_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
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

                    // InternalKactors.g:4193:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:4194:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:4194:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:4195:6: lv_expression_6_0= ruleNumber
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
                    // InternalKactors.g:4214:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:4214:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:4215:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:4215:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:4216:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:4216:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:4217:6: lv_int0_7_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_41);
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

                    // InternalKactors.g:4234:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt60=3;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==63) ) {
                        alt60=1;
                    }
                    else if ( (LA60_0==64) ) {
                        alt60=2;
                    }
                    switch (alt60) {
                        case 1 :
                            // InternalKactors.g:4235:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4235:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:4236:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:4236:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:4237:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,63,FOLLOW_42); if (state.failed) return current;
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
                            // InternalKactors.g:4250:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,64,FOLLOW_42); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4255:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:4256:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,65,FOLLOW_43); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:4262:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:4263:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:4267:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:4268:6: lv_int1_11_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_5_3_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKactors.g:4285:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt61=3;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==63) ) {
                        alt61=1;
                    }
                    else if ( (LA61_0==64) ) {
                        alt61=2;
                    }
                    switch (alt61) {
                        case 1 :
                            // InternalKactors.g:4286:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4286:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:4287:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:4287:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:4288:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4301:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4308:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:4308:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:4309:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,51,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:4313:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:4314:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:4314:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:4315:6: lv_set_15_0= ruleList
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
                    // InternalKactors.g:4334:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:4334:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:4335:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:4335:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:4336:5: lv_quantity_16_0= ruleQuantity
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
                    // InternalKactors.g:4354:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:4354:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:4355:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:4355:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:4356:5: lv_date_17_0= ruleDate
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
                    // InternalKactors.g:4374:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4374:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:4375:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4375:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:4376:5: lv_expr_18_0= RULE_EXPR
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
                    // InternalKactors.g:4393:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:4393:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:4394:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:4394:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:4395:5: lv_nodata_19_0= 'unknown'
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
                    // InternalKactors.g:4408:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:4408:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:4409:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:4409:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:4410:5: lv_star_20_0= '*'
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
                    // InternalKactors.g:4423:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:4423:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:4424:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:4424:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:4425:5: lv_anything_21_0= '#'
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
    // InternalKactors.g:4441:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:4441:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:4442:2: iv_ruleQuantity= ruleQuantity EOF
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
    // InternalKactors.g:4448:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4454:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:4455:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:4455:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:4456:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:4456:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:4457:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:4457:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:4458:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_51);
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

            // InternalKactors.g:4475:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==69) ) {
                alt63=1;
            }
            else if ( (LA63_0==70) ) {
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
                    // InternalKactors.g:4476:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:4476:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:4477:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:4477:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:4478:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,69,FOLLOW_52); if (state.failed) return current;
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
                    // InternalKactors.g:4491:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,70,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4496:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==EOF||(LA64_0>=RULE_OBSERVABLE && LA64_0<=RULE_LOWERCASE_ID)||(LA64_0>=RULE_STRING && LA64_0<=RULE_INT)||LA64_0==RULE_ANNOTATION_ID||LA64_0==31||LA64_0==41||(LA64_0>=43 && LA64_0<=58)||(LA64_0>=61 && LA64_0<=62)||(LA64_0>=66 && LA64_0<=69)||(LA64_0>=78 && LA64_0<=79)||LA64_0==86) ) {
                alt64=1;
            }
            else if ( (LA64_0==RULE_UPPERCASE_ID) ) {
                int LA64_2 = input.LA(2);

                if ( (LA64_2==72) ) {
                    alt64=2;
                }
                else if ( (LA64_2==EOF||(LA64_2>=RULE_OBSERVABLE && LA64_2<=RULE_LOWERCASE_ID)||(LA64_2>=RULE_STRING && LA64_2<=RULE_INT)||LA64_2==RULE_ANNOTATION_ID||LA64_2==31||LA64_2==41||(LA64_2>=43 && LA64_2<=58)||(LA64_2>=61 && LA64_2<=62)||(LA64_2>=66 && LA64_2<=69)||(LA64_2>=78 && LA64_2<=79)||LA64_2==86) ) {
                    alt64=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 64, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // InternalKactors.g:4497:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:4497:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:4498:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:4498:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:4499:6: lv_unit_3_0= ruleUnit
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
                    // InternalKactors.g:4517:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:4517:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:4518:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:4518:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:4519:6: lv_currency_4_0= ruleCurrency
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
    // InternalKactors.g:4541:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:4541:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:4542:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:4548:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4554:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:4555:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:4555:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:4556:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:4556:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:4557:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:4557:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:4558:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_53); if (state.failed) return current;
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

            // InternalKactors.g:4574:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==43) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalKactors.g:4575:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4579:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( ((LA65_0>=RULE_OBSERVABLE && LA65_0<=RULE_LOWERCASE_ID)||LA65_0==RULE_STRING||(LA65_0>=RULE_EXPR && LA65_0<=RULE_ARGVALUE)||LA65_0==RULE_INT||LA65_0==43||(LA65_0>=53 && LA65_0<=54)||LA65_0==58||LA65_0==61||LA65_0==66||(LA65_0>=78 && LA65_0<=79)) ) {
                        alt65=1;
                    }
                    switch (alt65) {
                        case 1 :
                            // InternalKactors.g:4580:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:4580:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:4581:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_21);
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
    // InternalKactors.g:4607:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:4607:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:4608:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:4614:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:4620:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:4621:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:4621:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt68=5;
            alt68 = dfa68.predict(input);
            switch (alt68) {
                case 1 :
                    // InternalKactors.g:4622:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4622:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:4623:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:4623:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:4624:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:4642:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4642:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:4643:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4643:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:4644:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:4644:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:4645:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_42);
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

                    otherlv_2=(Token)match(input,65,FOLLOW_43); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4666:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:4667:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:4667:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:4668:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:4687:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4687:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:4688:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:4688:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:4689:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:4706:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:4706:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:4707:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:4707:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:4708:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:4726:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:4726:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:4727:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:4727:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:4728:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:4728:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==53) ) {
                        alt67=1;
                    }
                    else if ( (LA67_0==54) ) {
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
                            // InternalKactors.g:4729:6: lv_boolean_6_1= 'true'
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
                            // InternalKactors.g:4740:6: lv_boolean_6_2= 'false'
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
    // InternalKactors.g:4757:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:4757:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:4758:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:4764:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4770:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:4771:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:4771:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:4772:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:4772:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:4773:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:4773:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:4774:5: lv_pairs_0_0= ruleKeyValuePair
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

            // InternalKactors.g:4791:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==31) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalKactors.g:4792:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:4792:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:4793:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,31,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:4799:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:4800:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:4800:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:4801:6: lv_pairs_2_0= ruleKeyValuePair
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
            	    break loop69;
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
    // InternalKactors.g:4823:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:4823:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:4824:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:4830:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4836:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:4837:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:4837:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:4838:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:4838:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_LOWERCASE_ID) ) {
                int LA71_1 = input.LA(2);

                if ( (LA71_1==60||LA71_1==71) ) {
                    alt71=1;
                }
            }
            switch (alt71) {
                case 1 :
                    // InternalKactors.g:4839:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:4839:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4840:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4840:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4841:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_54); if (state.failed) return current;
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

                    // InternalKactors.g:4857:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==71) ) {
                        alt70=1;
                    }
                    else if ( (LA70_0==60) ) {
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
                            // InternalKactors.g:4858:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:4858:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:4859:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:4859:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:4860:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,71,FOLLOW_15); if (state.failed) return current;
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
                            // InternalKactors.g:4873:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,60,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:4879:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:4880:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:4880:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:4881:5: lv_value_3_0= ruleValue
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
    // InternalKactors.g:4902:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:4902:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:4903:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:4909:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKactors.g:4915:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:4916:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:4916:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==RULE_LOWERCASE_ID||LA73_0==RULE_CAMELCASE_ID||LA73_0==RULE_UPPERCASE_ID) ) {
                alt73=1;
            }
            else if ( (LA73_0==43) ) {
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
                    // InternalKactors.g:4917:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:4917:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:4918:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:4918:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:4919:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:4919:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt72=3;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt72=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt72=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
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
                            // InternalKactors.g:4920:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKactors.g:4935:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKactors.g:4950:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                    // InternalKactors.g:4968:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:4968:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:4969:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4973:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:4974:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:4974:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:4975:6: lv_unit_2_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
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


    // $ANTLR start "entryRuleUnit"
    // InternalKactors.g:5001:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:5001:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:5002:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:5008:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5014:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5015:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5015:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5016:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5016:3: ()
            // InternalKactors.g:5017:4: 
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

            // InternalKactors.g:5026:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt74=2;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // InternalKactors.g:5027:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:5027:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:5028:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_56);
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

            // InternalKactors.g:5045:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==56) ) {
                    int LA75_2 = input.LA(2);

                    if ( (LA75_2==RULE_LOWERCASE_ID||LA75_2==RULE_CAMELCASE_ID||LA75_2==RULE_UPPERCASE_ID||LA75_2==43) ) {
                        alt75=1;
                    }


                }
                else if ( (LA75_0==69||LA75_0==86) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // InternalKactors.g:5046:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5046:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:5047:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:5053:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:5054:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:5054:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:5055:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_57);
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

            	    // InternalKactors.g:5073:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:5074:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5074:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:5075:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_56);
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
            	    break loop75;
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
    // InternalKactors.g:5097:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:5097:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:5098:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:5104:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5110:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5111:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5111:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5112:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5112:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:5113:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:5113:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:5114:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_58); if (state.failed) return current;
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

            // InternalKactors.g:5130:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:5131:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,72,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:5135:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:5136:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:5136:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:5137:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_59); if (state.failed) return current;
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

            // InternalKactors.g:5154:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==69) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // InternalKactors.g:5155:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5155:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:5156:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,69,FOLLOW_57); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:5162:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:5163:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5163:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:5164:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_59);
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
    // $ANTLR end "ruleCurrency"


    // $ANTLR start "entryRuleREL_OPERATOR"
    // InternalKactors.g:5186:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:5186:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:5187:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:5193:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:5199:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:5200:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:5200:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt77=6;
            switch ( input.LA(1) ) {
            case 73:
                {
                alt77=1;
                }
                break;
            case 74:
                {
                alt77=2;
                }
                break;
            case 60:
                {
                alt77=3;
                }
                break;
            case 75:
                {
                alt77=4;
                }
                break;
            case 76:
                {
                alt77=5;
                }
                break;
            case 77:
                {
                alt77=6;
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
                    // InternalKactors.g:5201:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:5201:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:5202:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:5202:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:5203:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5216:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:5216:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:5217:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:5217:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:5218:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5231:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:5231:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:5232:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:5232:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:5233:5: lv_eq_2_0= '='
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
                    // InternalKactors.g:5246:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:5246:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:5247:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:5247:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:5248:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5261:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:5261:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:5262:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:5262:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:5263:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5276:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:5276:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:5277:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:5277:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:5278:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:5294:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5294:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5295:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:5301:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:5307:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5308:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5308:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5309:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5309:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt78=3;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==78) ) {
                alt78=1;
            }
            else if ( (LA78_0==79) ) {
                alt78=2;
            }
            switch (alt78) {
                case 1 :
                    // InternalKactors.g:5310:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,78,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5315:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5315:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5316:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5316:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5317:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,79,FOLLOW_10); if (state.failed) return current;
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

            // InternalKactors.g:5330:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5331:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5335:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5336:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_60); if (state.failed) return current;
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

            // InternalKactors.g:5352:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==80) && (synpred168_InternalKactors())) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKactors.g:5353:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5357:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5358:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,80,FOLLOW_61); if (state.failed) return current;
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

            // InternalKactors.g:5370:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==70) ) {
                int LA80_1 = input.LA(2);

                if ( (LA80_1==RULE_INT) ) {
                    int LA80_3 = input.LA(3);

                    if ( (synpred169_InternalKactors()) ) {
                        alt80=1;
                    }
                }
            }
            switch (alt80) {
                case 1 :
                    // InternalKactors.g:5371:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5384:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5385:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5385:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5386:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5386:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5387:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,70,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5399:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5400:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5400:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5401:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
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

            // InternalKactors.g:5419:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==81) && (synpred173_InternalKactors())) {
                alt83=1;
            }
            else if ( (LA83_0==82) && (synpred173_InternalKactors())) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalKactors.g:5420:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5446:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5447:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5447:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5448:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5448:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5449:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5449:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==81) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==82) ) {
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
                            // InternalKactors.g:5450:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,81,FOLLOW_43); if (state.failed) return current;
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
                            // InternalKactors.g:5461:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,82,FOLLOW_43); if (state.failed) return current;
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

                    // InternalKactors.g:5474:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt82=3;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==78) ) {
                        alt82=1;
                    }
                    else if ( (LA82_0==79) ) {
                        alt82=2;
                    }
                    switch (alt82) {
                        case 1 :
                            // InternalKactors.g:5475:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,78,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5480:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:5480:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:5481:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:5481:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:5482:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,79,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5495:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:5496:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:5496:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:5497:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:5519:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:5519:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:5520:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:5526:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:5532:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:5533:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:5533:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:5534:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:5534:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:5535:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:5535:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:5536:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
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

            // InternalKactors.g:5552:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt84=4;
            switch ( input.LA(1) ) {
                case 83:
                    {
                    alt84=1;
                    }
                    break;
                case 84:
                    {
                    alt84=2;
                    }
                    break;
                case 85:
                    {
                    alt84=3;
                    }
                    break;
            }

            switch (alt84) {
                case 1 :
                    // InternalKactors.g:5553:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,83,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5558:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,84,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:5563:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:5563:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:5564:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:5564:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:5565:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,85,FOLLOW_64); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,79,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:5582:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:5583:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:5583:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:5584:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,79,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:5604:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:5605:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:5605:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:5606:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
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

            // InternalKactors.g:5622:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==RULE_INT) ) {
                int LA87_1 = input.LA(2);

                if ( (LA87_1==42) ) {
                    alt87=1;
                }
            }
            switch (alt87) {
                case 1 :
                    // InternalKactors.g:5623:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:5623:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:5624:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:5624:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:5625:6: lv_hour_8_0= RULE_INT
                    {
                    lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_14); if (state.failed) return current;
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
                    // InternalKactors.g:5645:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:5646:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:5646:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:5647:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_22); if (state.failed) return current;
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

                    // InternalKactors.g:5663:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==42) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // InternalKactors.g:5664:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:5668:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:5669:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:5669:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:5670:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
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

                            // InternalKactors.g:5686:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt85=2;
                            int LA85_0 = input.LA(1);

                            if ( (LA85_0==70) ) {
                                alt85=1;
                            }
                            switch (alt85) {
                                case 1 :
                                    // InternalKactors.g:5687:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,70,FOLLOW_10); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:5691:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:5692:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:5692:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:5693:8: lv_ms_14_0= RULE_INT
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
    // InternalKactors.g:5716:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:5716:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:5717:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:5723:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5729:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:5730:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:5730:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:5731:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_66); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5738:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==70) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalKactors.g:5739:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,70,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_66); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop88;
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
    // InternalKactors.g:5756:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:5756:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:5757:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:5763:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:5769:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:5770:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:5770:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:5771:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:5771:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==RULE_LOWERCASE_ID) ) {
                alt89=1;
            }
            else if ( (LA89_0==RULE_UPPERCASE_ID) ) {
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
                    // InternalKactors.g:5772:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5780:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5788:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( ((LA92_0>=69 && LA92_0<=70)) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalKactors.g:5789:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:5789:4: (kw= '.' | kw= '/' )
            	    int alt90=2;
            	    int LA90_0 = input.LA(1);

            	    if ( (LA90_0==70) ) {
            	        alt90=1;
            	    }
            	    else if ( (LA90_0==69) ) {
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
            	            // InternalKactors.g:5790:5: kw= '.'
            	            {
            	            kw=(Token)match(input,70,FOLLOW_35); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5796:5: kw= '/'
            	            {
            	            kw=(Token)match(input,69,FOLLOW_35); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:5802:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt91=2;
            	    int LA91_0 = input.LA(1);

            	    if ( (LA91_0==RULE_LOWERCASE_ID) ) {
            	        alt91=1;
            	    }
            	    else if ( (LA91_0==RULE_UPPERCASE_ID) ) {
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
            	            // InternalKactors.g:5803:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_67); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5811:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_67); if (state.failed) return current;
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
            	    break loop92;
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
    // InternalKactors.g:5824:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:5824:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:5825:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:5831:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:5837:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:5838:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:5838:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:5839:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5846:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==70) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalKactors.g:5847:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,70,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:5859:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==70) ) {
                        alt93=1;
                    }
                    switch (alt93) {
                        case 1 :
                            // InternalKactors.g:5860:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,70,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
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

            // InternalKactors.g:5874:3: (kw= '-' )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==79) ) {
                int LA95_1 = input.LA(2);

                if ( (synpred190_InternalKactors()) ) {
                    alt95=1;
                }
            }
            switch (alt95) {
                case 1 :
                    // InternalKactors.g:5875:4: kw= '-'
                    {
                    kw=(Token)match(input,79,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5881:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt96=3;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==RULE_LOWERCASE_ID) ) {
                int LA96_1 = input.LA(2);

                if ( (synpred191_InternalKactors()) ) {
                    alt96=1;
                }
            }
            else if ( (LA96_0==RULE_UPPERCASE_ID) ) {
                alt96=2;
            }
            switch (alt96) {
                case 1 :
                    // InternalKactors.g:5882:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:5890:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:5902:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5908:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:5909:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:5909:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt97=3;
            switch ( input.LA(1) ) {
            case 69:
                {
                alt97=1;
                }
                break;
            case 86:
                {
                alt97=2;
                }
                break;
            case 56:
                {
                alt97=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // InternalKactors.g:5910:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:5910:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:5911:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5918:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:5918:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:5919:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:5926:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:5926:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:5927:4: enumLiteral_2= '*'
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

    // $ANTLR start synpred12_InternalKactors
    public final void synpred12_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_imports_9_0 = null;

        AntlrDatatypeRuleToken lv_imports_11_0 = null;


        // InternalKactors.g:317:4: ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:317:4: ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:317:4: ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:318:5: {...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:318:105: ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:319:6: ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:322:9: ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) )
        // InternalKactors.g:322:10: {...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "true");
        }
        // InternalKactors.g:322:19: (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* )
        // InternalKactors.g:322:20: otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )*
        {
        otherlv_8=(Token)match(input,30,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:326:9: ( (lv_imports_9_0= rulePathName ) )
        // InternalKactors.g:327:10: (lv_imports_9_0= rulePathName )
        {
        // InternalKactors.g:327:10: (lv_imports_9_0= rulePathName )
        // InternalKactors.g:328:11: lv_imports_9_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
          										
        }
        pushFollow(FOLLOW_24);
        lv_imports_9_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:345:9: (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )*
        loop104:
        do {
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==31) ) {
                alt104=1;
            }


            switch (alt104) {
        	case 1 :
        	    // InternalKactors.g:346:10: otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) )
        	    {
        	    otherlv_10=(Token)match(input,31,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:350:10: ( (lv_imports_11_0= rulePathName ) )
        	    // InternalKactors.g:351:11: (lv_imports_11_0= rulePathName )
        	    {
        	    // InternalKactors.g:351:11: (lv_imports_11_0= rulePathName )
        	    // InternalKactors.g:352:12: lv_imports_11_0= rulePathName
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_24);
        	    lv_imports_11_0=rulePathName();

        	    state._fsp--;
        	    if (state.failed) return ;

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


        }


        }


        }
    }
    // $ANTLR end synpred12_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_worldview_13_0 = null;


        // InternalKactors.g:376:4: ( ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:376:4: ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:376:4: ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:377:5: {...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:377:105: ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) )
        // InternalKactors.g:378:6: ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:381:9: ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) )
        // InternalKactors.g:381:10: {...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        }
        // InternalKactors.g:381:19: (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) )
        // InternalKactors.g:381:20: otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) )
        {
        otherlv_12=(Token)match(input,32,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:385:9: ( (lv_worldview_13_0= rulePathName ) )
        // InternalKactors.g:386:10: (lv_worldview_13_0= rulePathName )
        {
        // InternalKactors.g:386:10: (lv_worldview_13_0= rulePathName )
        // InternalKactors.g:387:11: lv_worldview_13_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_worldview_13_0=rulePathName();

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
    // $ANTLR end synpred13_InternalKactors

    // $ANTLR start synpred15_InternalKactors
    public final void synpred15_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token lv_observable_15_0=null;
        EObject lv_observables_16_0 = null;


        // InternalKactors.g:410:4: ( ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) )
        // InternalKactors.g:410:4: ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) )
        {
        // InternalKactors.g:410:4: ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) )
        // InternalKactors.g:411:5: {...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:411:105: ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) )
        // InternalKactors.g:412:6: ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:415:9: ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) )
        // InternalKactors.g:415:10: {...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "true");
        }
        // InternalKactors.g:415:19: (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) )
        // InternalKactors.g:415:20: otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) )
        {
        otherlv_14=(Token)match(input,33,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:419:9: ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) )
        int alt105=2;
        int LA105_0 = input.LA(1);

        if ( (LA105_0==RULE_OBSERVABLE) ) {
            alt105=1;
        }
        else if ( (LA105_0==43) ) {
            alt105=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 105, 0, input);

            throw nvae;
        }
        switch (alt105) {
            case 1 :
                // InternalKactors.g:420:10: ( (lv_observable_15_0= RULE_OBSERVABLE ) )
                {
                // InternalKactors.g:420:10: ( (lv_observable_15_0= RULE_OBSERVABLE ) )
                // InternalKactors.g:421:11: (lv_observable_15_0= RULE_OBSERVABLE )
                {
                // InternalKactors.g:421:11: (lv_observable_15_0= RULE_OBSERVABLE )
                // InternalKactors.g:422:12: lv_observable_15_0= RULE_OBSERVABLE
                {
                lv_observable_15_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:439:10: ( (lv_observables_16_0= ruleList ) )
                {
                // InternalKactors.g:439:10: ( (lv_observables_16_0= ruleList ) )
                // InternalKactors.g:440:11: (lv_observables_16_0= ruleList )
                {
                // InternalKactors.g:440:11: (lv_observables_16_0= ruleList )
                // InternalKactors.g:441:12: lv_observables_16_0= ruleList
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_2_2_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_observables_16_0=ruleList();

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
    // $ANTLR end synpred15_InternalKactors

    // $ANTLR start synpred18_InternalKactors
    public final void synpred18_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_17=null;
        Token lv_label_18_1=null;
        Token lv_label_18_2=null;
        Token lv_label_18_3=null;

        // InternalKactors.g:465:4: ( ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:465:4: ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:465:4: ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:466:5: {...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:466:105: ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:467:6: ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:470:9: ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:470:10: {...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        }
        // InternalKactors.g:470:19: (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) )
        // InternalKactors.g:470:20: otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) )
        {
        otherlv_17=(Token)match(input,34,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:474:9: ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) )
        // InternalKactors.g:475:10: ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) )
        {
        // InternalKactors.g:475:10: ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) )
        // InternalKactors.g:476:11: (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING )
        {
        // InternalKactors.g:476:11: (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING )
        int alt106=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt106=1;
            }
            break;
        case RULE_ID:
            {
            alt106=2;
            }
            break;
        case RULE_STRING:
            {
            alt106=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 106, 0, input);

            throw nvae;
        }

        switch (alt106) {
            case 1 :
                // InternalKactors.g:477:12: lv_label_18_1= RULE_LOWERCASE_ID
                {
                lv_label_18_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:492:12: lv_label_18_2= RULE_ID
                {
                lv_label_18_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:507:12: lv_label_18_3= RULE_STRING
                {
                lv_label_18_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred18_InternalKactors

    // $ANTLR start synpred19_InternalKactors
    public final void synpred19_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_19=null;
        Token lv_description_20_0=null;

        // InternalKactors.g:530:4: ( ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:530:4: ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:530:4: ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:531:5: {...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
        }
        // InternalKactors.g:531:105: ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:532:6: ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
        // InternalKactors.g:535:9: ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) )
        // InternalKactors.g:535:10: {...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "true");
        }
        // InternalKactors.g:535:19: (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) )
        // InternalKactors.g:535:20: otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) )
        {
        otherlv_19=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:539:9: ( (lv_description_20_0= RULE_STRING ) )
        // InternalKactors.g:540:10: (lv_description_20_0= RULE_STRING )
        {
        // InternalKactors.g:540:10: (lv_description_20_0= RULE_STRING )
        // InternalKactors.g:541:11: lv_description_20_0= RULE_STRING
        {
        lv_description_20_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred19_InternalKactors

    // $ANTLR start synpred20_InternalKactors
    public final void synpred20_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_21=null;
        Token lv_permissions_22_0=null;

        // InternalKactors.g:563:4: ( ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:563:4: ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:563:4: ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:564:5: {...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred20_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
        }
        // InternalKactors.g:564:105: ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:565:6: ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
        // InternalKactors.g:568:9: ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) )
        // InternalKactors.g:568:10: {...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred20_InternalKactors", "true");
        }
        // InternalKactors.g:568:19: (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) )
        // InternalKactors.g:568:20: otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) )
        {
        otherlv_21=(Token)match(input,36,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:572:9: ( (lv_permissions_22_0= RULE_STRING ) )
        // InternalKactors.g:573:10: (lv_permissions_22_0= RULE_STRING )
        {
        // InternalKactors.g:573:10: (lv_permissions_22_0= RULE_STRING )
        // InternalKactors.g:574:11: lv_permissions_22_0= RULE_STRING
        {
        lv_permissions_22_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred20_InternalKactors

    // $ANTLR start synpred21_InternalKactors
    public final void synpred21_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_23=null;
        Token lv_authors_24_0=null;

        // InternalKactors.g:601:10: ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )
        // InternalKactors.g:601:10: {...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "true");
        }
        // InternalKactors.g:601:19: (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) )
        // InternalKactors.g:601:20: otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) )
        {
        otherlv_23=(Token)match(input,37,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:605:9: ( (lv_authors_24_0= RULE_STRING ) )
        // InternalKactors.g:606:10: (lv_authors_24_0= RULE_STRING )
        {
        // InternalKactors.g:606:10: (lv_authors_24_0= RULE_STRING )
        // InternalKactors.g:607:11: lv_authors_24_0= RULE_STRING
        {
        lv_authors_24_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred21_InternalKactors

    // $ANTLR start synpred22_InternalKactors
    public final void synpred22_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_23=null;
        Token lv_authors_24_0=null;

        // InternalKactors.g:596:4: ( ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:596:4: ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:596:4: ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:597:5: {...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred22_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
        }
        // InternalKactors.g:597:105: ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:598:6: ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
        // InternalKactors.g:601:9: ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+
        int cnt107=0;
        loop107:
        do {
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==37) && ((true))) {
                alt107=1;
            }


            switch (alt107) {
        	case 1 :
        	    // InternalKactors.g:601:10: {...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred22_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:601:19: (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) )
        	    // InternalKactors.g:601:20: otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) )
        	    {
        	    otherlv_23=(Token)match(input,37,FOLLOW_9); if (state.failed) return ;
        	    // InternalKactors.g:605:9: ( (lv_authors_24_0= RULE_STRING ) )
        	    // InternalKactors.g:606:10: (lv_authors_24_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:606:10: (lv_authors_24_0= RULE_STRING )
        	    // InternalKactors.g:607:11: lv_authors_24_0= RULE_STRING
        	    {
        	    lv_authors_24_0=(Token)match(input,RULE_STRING,FOLLOW_71); if (state.failed) return ;

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
    // $ANTLR end synpred22_InternalKactors

    // $ANTLR start synpred23_InternalKactors
    public final void synpred23_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_25=null;
        AntlrDatatypeRuleToken lv_version_26_0 = null;


        // InternalKactors.g:629:4: ( ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:629:4: ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:629:4: ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:630:5: {...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
        }
        // InternalKactors.g:630:105: ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:631:6: ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
        // InternalKactors.g:634:9: ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:634:10: {...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "true");
        }
        // InternalKactors.g:634:19: (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) )
        // InternalKactors.g:634:20: otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) )
        {
        otherlv_25=(Token)match(input,38,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:638:9: ( (lv_version_26_0= ruleVersionNumber ) )
        // InternalKactors.g:639:10: (lv_version_26_0= ruleVersionNumber )
        {
        // InternalKactors.g:639:10: (lv_version_26_0= ruleVersionNumber )
        // InternalKactors.g:640:11: lv_version_26_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_7_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_26_0=ruleVersionNumber();

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
    // $ANTLR end synpred23_InternalKactors

    // $ANTLR start synpred25_InternalKactors
    public final void synpred25_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_27=null;
        Token lv_createcomment_29_0=null;
        EObject lv_created_28_0 = null;


        // InternalKactors.g:663:4: ( ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:663:4: ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:663:4: ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:664:5: {...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred25_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
        }
        // InternalKactors.g:664:105: ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:665:6: ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
        // InternalKactors.g:668:9: ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) )
        // InternalKactors.g:668:10: {...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred25_InternalKactors", "true");
        }
        // InternalKactors.g:668:19: (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? )
        // InternalKactors.g:668:20: otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )?
        {
        otherlv_27=(Token)match(input,39,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:672:9: ( (lv_created_28_0= ruleDate ) )
        // InternalKactors.g:673:10: (lv_created_28_0= ruleDate )
        {
        // InternalKactors.g:673:10: (lv_created_28_0= ruleDate )
        // InternalKactors.g:674:11: lv_created_28_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
          										
        }
        pushFollow(FOLLOW_72);
        lv_created_28_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:691:9: ( (lv_createcomment_29_0= RULE_STRING ) )?
        int alt108=2;
        int LA108_0 = input.LA(1);

        if ( (LA108_0==RULE_STRING) ) {
            alt108=1;
        }
        switch (alt108) {
            case 1 :
                // InternalKactors.g:692:10: (lv_createcomment_29_0= RULE_STRING )
                {
                // InternalKactors.g:692:10: (lv_createcomment_29_0= RULE_STRING )
                // InternalKactors.g:693:11: lv_createcomment_29_0= RULE_STRING
                {
                lv_createcomment_29_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred25_InternalKactors

    // $ANTLR start synpred27_InternalKactors
    public final void synpred27_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_30=null;
        Token lv_modcomment_32_0=null;
        EObject lv_modified_31_0 = null;


        // InternalKactors.g:715:4: ( ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:715:4: ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:715:4: ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:716:5: {...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred27_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
        }
        // InternalKactors.g:716:105: ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:717:6: ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
        // InternalKactors.g:720:9: ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) )
        // InternalKactors.g:720:10: {...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred27_InternalKactors", "true");
        }
        // InternalKactors.g:720:19: (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? )
        // InternalKactors.g:720:20: otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )?
        {
        otherlv_30=(Token)match(input,40,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:724:9: ( (lv_modified_31_0= ruleDate ) )
        // InternalKactors.g:725:10: (lv_modified_31_0= ruleDate )
        {
        // InternalKactors.g:725:10: (lv_modified_31_0= ruleDate )
        // InternalKactors.g:726:11: lv_modified_31_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
          										
        }
        pushFollow(FOLLOW_72);
        lv_modified_31_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:743:9: ( (lv_modcomment_32_0= RULE_STRING ) )?
        int alt109=2;
        int LA109_0 = input.LA(1);

        if ( (LA109_0==RULE_STRING) ) {
            alt109=1;
        }
        switch (alt109) {
            case 1 :
                // InternalKactors.g:744:10: (lv_modcomment_32_0= RULE_STRING )
                {
                // InternalKactors.g:744:10: (lv_modcomment_32_0= RULE_STRING )
                // InternalKactors.g:745:11: lv_modcomment_32_0= RULE_STRING
                {
                lv_modcomment_32_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred27_InternalKactors

    // $ANTLR start synpred34_InternalKactors
    public final void synpred34_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:1049:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:1049:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return ;
        // InternalKactors.g:1053:6: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt111=2;
        int LA111_0 = input.LA(1);

        if ( ((LA111_0>=RULE_OBSERVABLE && LA111_0<=RULE_LOWERCASE_ID)||LA111_0==RULE_STRING||(LA111_0>=RULE_EXPR && LA111_0<=RULE_ARGVALUE)||LA111_0==RULE_INT||LA111_0==43||(LA111_0>=53 && LA111_0<=54)||LA111_0==58||LA111_0==61||LA111_0==66||(LA111_0>=78 && LA111_0<=79)) ) {
            alt111=1;
        }
        switch (alt111) {
            case 1 :
                // InternalKactors.g:1054:7: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:1054:7: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:1055:8: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getMessageCallAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                  							
                }
                pushFollow(FOLLOW_21);
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
    // $ANTLR end synpred34_InternalKactors

    // $ANTLR start synpred37_InternalKactors
    public final void synpred37_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_2_0 = null;


        // InternalKactors.g:1158:4: ( (lv_body_2_0= ruleMessageBody ) )
        // InternalKactors.g:1158:4: (lv_body_2_0= ruleMessageBody )
        {
        // InternalKactors.g:1158:4: (lv_body_2_0= ruleMessageBody )
        // InternalKactors.g:1159:5: lv_body_2_0= ruleMessageBody
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
    // $ANTLR end synpred37_InternalKactors

    // $ANTLR start synpred38_InternalKactors
    public final void synpred38_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_next_1_0 = null;


        // InternalKactors.g:1219:4: ( (lv_next_1_0= ruleNextStatement ) )
        // InternalKactors.g:1219:4: (lv_next_1_0= ruleNextStatement )
        {
        // InternalKactors.g:1219:4: (lv_next_1_0= ruleNextStatement )
        // InternalKactors.g:1220:5: lv_next_1_0= ruleNextStatement
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
    // $ANTLR end synpred38_InternalKactors

    // $ANTLR start synpred40_InternalKactors
    public final void synpred40_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_1_0 = null;


        // InternalKactors.g:1276:3: ( ( (lv_group_1_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1276:3: ( (lv_group_1_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1276:3: ( (lv_group_1_0= ruleStatementGroup ) )
        // InternalKactors.g:1277:4: (lv_group_1_0= ruleStatementGroup )
        {
        // InternalKactors.g:1277:4: (lv_group_1_0= ruleStatementGroup )
        // InternalKactors.g:1278:5: lv_group_1_0= ruleStatementGroup
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
    // $ANTLR end synpred40_InternalKactors

    // $ANTLR start synpred41_InternalKactors
    public final void synpred41_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_2_0 = null;


        // InternalKactors.g:1296:3: ( ( (lv_verb_2_0= ruleMessageCall ) ) )
        // InternalKactors.g:1296:3: ( (lv_verb_2_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1296:3: ( (lv_verb_2_0= ruleMessageCall ) )
        // InternalKactors.g:1297:4: (lv_verb_2_0= ruleMessageCall )
        {
        // InternalKactors.g:1297:4: (lv_verb_2_0= ruleMessageCall )
        // InternalKactors.g:1298:5: lv_verb_2_0= ruleMessageCall
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
    // $ANTLR end synpred41_InternalKactors

    // $ANTLR start synpred48_InternalKactors
    public final void synpred48_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_2_0 = null;


        // InternalKactors.g:1478:4: ( ( (lv_verb_2_0= ruleMessageCall ) ) )
        // InternalKactors.g:1478:4: ( (lv_verb_2_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1478:4: ( (lv_verb_2_0= ruleMessageCall ) )
        // InternalKactors.g:1479:5: (lv_verb_2_0= ruleMessageCall )
        {
        // InternalKactors.g:1479:5: (lv_verb_2_0= ruleMessageCall )
        // InternalKactors.g:1480:6: lv_verb_2_0= ruleMessageCall
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
    // $ANTLR end synpred48_InternalKactors

    // $ANTLR start synpred49_InternalKactors
    public final void synpred49_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_3_0 = null;


        // InternalKactors.g:1498:4: ( ( (lv_group_3_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1498:4: ( (lv_group_3_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1498:4: ( (lv_group_3_0= ruleStatementGroup ) )
        // InternalKactors.g:1499:5: (lv_group_3_0= ruleStatementGroup )
        {
        // InternalKactors.g:1499:5: (lv_group_3_0= ruleStatementGroup )
        // InternalKactors.g:1500:6: lv_group_3_0= ruleStatementGroup
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
    // $ANTLR end synpred49_InternalKactors

    // $ANTLR start synpred55_InternalKactors
    public final void synpred55_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfBody_6_0 = null;


        // InternalKactors.g:1758:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )
        // InternalKactors.g:1758:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        {
        otherlv_3=(Token)match(input,47,FOLLOW_27); if (state.failed) return ;
        otherlv_4=(Token)match(input,46,FOLLOW_25); if (state.failed) return ;
        // InternalKactors.g:1766:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:1767:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:1767:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:1768:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1784:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        // InternalKactors.g:1785:5: (lv_elseIfBody_6_0= ruleStatementBody )
        {
        // InternalKactors.g:1785:5: (lv_elseIfBody_6_0= ruleStatementBody )
        // InternalKactors.g:1786:6: lv_elseIfBody_6_0= ruleStatementBody
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
    // $ANTLR end synpred55_InternalKactors

    // $ANTLR start synpred56_InternalKactors
    public final void synpred56_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:1805:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )
        // InternalKactors.g:1805:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
        {
        otherlv_7=(Token)match(input,47,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:1809:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
        // InternalKactors.g:1810:5: (lv_elseCall_8_0= ruleStatementBody )
        {
        // InternalKactors.g:1810:5: (lv_elseCall_8_0= ruleStatementBody )
        // InternalKactors.g:1811:6: lv_elseCall_8_0= ruleStatementBody
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
    // $ANTLR end synpred56_InternalKactors

    // $ANTLR start synpred57_InternalKactors
    public final void synpred57_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1848:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1848:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1848:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1849:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1849:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1850:5: lv_verb_0_0= ruleMessageCall
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
    // $ANTLR end synpred57_InternalKactors

    // $ANTLR start synpred58_InternalKactors
    public final void synpred58_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_1_0 = null;


        // InternalKactors.g:1868:3: ( ( (lv_value_1_0= ruleValue ) ) )
        // InternalKactors.g:1868:3: ( (lv_value_1_0= ruleValue ) )
        {
        // InternalKactors.g:1868:3: ( (lv_value_1_0= ruleValue ) )
        // InternalKactors.g:1869:4: (lv_value_1_0= ruleValue )
        {
        // InternalKactors.g:1869:4: (lv_value_1_0= ruleValue )
        // InternalKactors.g:1870:5: lv_value_1_0= ruleValue
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
    // $ANTLR end synpred58_InternalKactors

    // $ANTLR start synpred60_InternalKactors
    public final void synpred60_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_0_0 = null;


        // InternalKactors.g:2135:3: ( ( (lv_match_0_0= ruleMatch ) ) )
        // InternalKactors.g:2135:3: ( (lv_match_0_0= ruleMatch ) )
        {
        // InternalKactors.g:2135:3: ( (lv_match_0_0= ruleMatch ) )
        // InternalKactors.g:2136:4: (lv_match_0_0= ruleMatch )
        {
        // InternalKactors.g:2136:4: (lv_match_0_0= ruleMatch )
        // InternalKactors.g:2137:5: lv_match_0_0= ruleMatch
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
    // $ANTLR end synpred60_InternalKactors

    // $ANTLR start synpred62_InternalKactors
    public final void synpred62_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;


        // InternalKactors.g:2155:3: ( (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) )
        // InternalKactors.g:2155:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        {
        // InternalKactors.g:2155:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        // InternalKactors.g:2156:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
        {
        otherlv_1=(Token)match(input,43,FOLLOW_23); if (state.failed) return ;
        // InternalKactors.g:2160:4: ( (lv_matches_2_0= ruleMatch ) )
        // InternalKactors.g:2161:5: (lv_matches_2_0= ruleMatch )
        {
        // InternalKactors.g:2161:5: (lv_matches_2_0= ruleMatch )
        // InternalKactors.g:2162:6: lv_matches_2_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_30);
        lv_matches_2_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2179:4: ( (lv_matches_3_0= ruleMatch ) )*
        loop114:
        do {
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( ((LA114_0>=RULE_OBSERVABLE && LA114_0<=RULE_LOWERCASE_ID)||LA114_0==RULE_STRING||LA114_0==RULE_EXPR||(LA114_0>=RULE_CAMELCASE_ID && LA114_0<=RULE_INT)||LA114_0==51||(LA114_0>=53 && LA114_0<=57)||(LA114_0>=78 && LA114_0<=79)) ) {
                alt114=1;
            }


            switch (alt114) {
        	case 1 :
        	    // InternalKactors.g:2180:5: (lv_matches_3_0= ruleMatch )
        	    {
        	    // InternalKactors.g:2180:5: (lv_matches_3_0= ruleMatch )
        	    // InternalKactors.g:2181:6: lv_matches_3_0= ruleMatch
        	    {
        	    if ( state.backtracking==0 ) {

        	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_2_0());
        	      					
        	    }
        	    pushFollow(FOLLOW_30);
        	    lv_matches_3_0=ruleMatch();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }
        	    break;

        	default :
        	    break loop114;
            }
        } while (true);

        otherlv_4=(Token)match(input,44,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred62_InternalKactors

    // $ANTLR start synpred63_InternalKactors
    public final void synpred63_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_statement_5_0 = null;


        // InternalKactors.g:2204:3: ( ( (lv_statement_5_0= ruleStatement ) ) )
        // InternalKactors.g:2204:3: ( (lv_statement_5_0= ruleStatement ) )
        {
        // InternalKactors.g:2204:3: ( (lv_statement_5_0= ruleStatement ) )
        // InternalKactors.g:2205:4: (lv_statement_5_0= ruleStatement )
        {
        // InternalKactors.g:2205:4: (lv_statement_5_0= ruleStatement )
        // InternalKactors.g:2206:5: lv_statement_5_0= ruleStatement
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
    // $ANTLR end synpred63_InternalKactors

    // $ANTLR start synpred65_InternalKactors
    public final void synpred65_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_literal_1_0 = null;


        // InternalKactors.g:2291:3: ( ( (lv_literal_1_0= ruleLiteral ) ) )
        // InternalKactors.g:2291:3: ( (lv_literal_1_0= ruleLiteral ) )
        {
        // InternalKactors.g:2291:3: ( (lv_literal_1_0= ruleLiteral ) )
        // InternalKactors.g:2292:4: (lv_literal_1_0= ruleLiteral )
        {
        // InternalKactors.g:2292:4: (lv_literal_1_0= ruleLiteral )
        // InternalKactors.g:2293:5: lv_literal_1_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_literal_1_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred65_InternalKactors

    // $ANTLR start synpred75_InternalKactors
    public final void synpred75_InternalKactors_fragment() throws RecognitionException {   
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        EObject lv_body_5_0 = null;


        // InternalKactors.g:2531:3: ( ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2531:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2531:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        // InternalKactors.g:2532:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
        {
        // InternalKactors.g:2532:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
        // InternalKactors.g:2533:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        {
        // InternalKactors.g:2533:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        // InternalKactors.g:2534:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        {
        // InternalKactors.g:2534:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        int alt115=2;
        int LA115_0 = input.LA(1);

        if ( (LA115_0==53) ) {
            alt115=1;
        }
        else if ( (LA115_0==54) ) {
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
                // InternalKactors.g:2535:7: lv_boolean_3_1= 'true'
                {
                lv_boolean_3_1=(Token)match(input,53,FOLLOW_31); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:2546:7: lv_boolean_3_2= 'false'
                {
                lv_boolean_3_2=(Token)match(input,54,FOLLOW_31); if (state.failed) return ;

                }
                break;

        }


        }


        }

        otherlv_4=(Token)match(input,52,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2563:4: ( (lv_body_5_0= ruleStatementList ) )
        // InternalKactors.g:2564:5: (lv_body_5_0= ruleStatementList )
        {
        // InternalKactors.g:2564:5: (lv_body_5_0= ruleStatementList )
        // InternalKactors.g:2565:6: lv_body_5_0= ruleStatementList
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
    // $ANTLR end synpred75_InternalKactors

    // $ANTLR start synpred79_InternalKactors
    public final void synpred79_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;


        // InternalKactors.g:2716:3: ( ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2716:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2716:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        // InternalKactors.g:2717:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
        {
        // InternalKactors.g:2717:4: ( (lv_literal_15_0= ruleLiteral ) )
        // InternalKactors.g:2718:5: (lv_literal_15_0= ruleLiteral )
        {
        // InternalKactors.g:2718:5: (lv_literal_15_0= ruleLiteral )
        // InternalKactors.g:2719:6: lv_literal_15_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_5_0_0());
          					
        }
        pushFollow(FOLLOW_31);
        lv_literal_15_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_16=(Token)match(input,52,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2740:4: ( (lv_body_17_0= ruleStatementList ) )
        // InternalKactors.g:2741:5: (lv_body_17_0= ruleStatementList )
        {
        // InternalKactors.g:2741:5: (lv_body_17_0= ruleStatementList )
        // InternalKactors.g:2742:6: lv_body_17_0= ruleStatementList
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
    // $ANTLR end synpred79_InternalKactors

    // $ANTLR start synpred81_InternalKactors
    public final void synpred81_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_23=null;
        EObject lv_quantity_22_0 = null;

        EObject lv_body_24_0 = null;


        // InternalKactors.g:2810:3: ( ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2810:3: ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2810:3: ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) )
        // InternalKactors.g:2811:4: ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) )
        {
        // InternalKactors.g:2811:4: ( (lv_quantity_22_0= ruleQuantity ) )
        // InternalKactors.g:2812:5: (lv_quantity_22_0= ruleQuantity )
        {
        // InternalKactors.g:2812:5: (lv_quantity_22_0= ruleQuantity )
        // InternalKactors.g:2813:6: lv_quantity_22_0= ruleQuantity
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_7_0_0());
          					
        }
        pushFollow(FOLLOW_31);
        lv_quantity_22_0=ruleQuantity();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_23=(Token)match(input,52,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2834:4: ( (lv_body_24_0= ruleStatementList ) )
        // InternalKactors.g:2835:5: (lv_body_24_0= ruleStatementList )
        {
        // InternalKactors.g:2835:5: (lv_body_24_0= ruleStatementList )
        // InternalKactors.g:2836:6: lv_body_24_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_24_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred81_InternalKactors

    // $ANTLR start synpred101_InternalKactors
    public final void synpred101_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:3537:5: ( 'to' )
        // InternalKactors.g:3537:6: 'to'
        {
        match(input,65,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred101_InternalKactors

    // $ANTLR start synpred128_InternalKactors
    public final void synpred128_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4256:5: ( 'to' )
        // InternalKactors.g:4256:6: 'to'
        {
        match(input,65,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred128_InternalKactors

    // $ANTLR start synpred143_InternalKactors
    public final void synpred143_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:4622:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:4622:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:4622:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:4623:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:4623:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:4624:5: lv_number_0_0= ruleNumber
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
    // $ANTLR end synpred143_InternalKactors

    // $ANTLR start synpred146_InternalKactors
    public final void synpred146_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:4706:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:4706:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:4706:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:4707:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:4707:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:4708:5: lv_date_5_0= ruleDate
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
    // $ANTLR end synpred146_InternalKactors

    // $ANTLR start synpred155_InternalKactors
    public final void synpred155_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_root_1_0 = null;


        // InternalKactors.g:5027:4: ( (lv_root_1_0= ruleUnitElement ) )
        // InternalKactors.g:5027:4: (lv_root_1_0= ruleUnitElement )
        {
        // InternalKactors.g:5027:4: (lv_root_1_0= ruleUnitElement )
        // InternalKactors.g:5028:5: lv_root_1_0= ruleUnitElement
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
    // $ANTLR end synpred155_InternalKactors

    // $ANTLR start synpred167_InternalKactors
    public final void synpred167_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5331:4: ( ( RULE_INT ) )
        // InternalKactors.g:5331:5: ( RULE_INT )
        {
        // InternalKactors.g:5331:5: ( RULE_INT )
        // InternalKactors.g:5332:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred167_InternalKactors

    // $ANTLR start synpred168_InternalKactors
    public final void synpred168_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5353:4: ( ( 'l' ) )
        // InternalKactors.g:5353:5: ( 'l' )
        {
        // InternalKactors.g:5353:5: ( 'l' )
        // InternalKactors.g:5354:5: 'l'
        {
        match(input,80,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred168_InternalKactors

    // $ANTLR start synpred169_InternalKactors
    public final void synpred169_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5371:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5371:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5371:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5372:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5372:5: ( ( '.' ) )
        // InternalKactors.g:5373:6: ( '.' )
        {
        // InternalKactors.g:5373:6: ( '.' )
        // InternalKactors.g:5374:7: '.'
        {
        match(input,70,FOLLOW_10); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5377:5: ( ( RULE_INT ) )
        // InternalKactors.g:5378:6: ( RULE_INT )
        {
        // InternalKactors.g:5378:6: ( RULE_INT )
        // InternalKactors.g:5379:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred169_InternalKactors

    // $ANTLR start synpred173_InternalKactors
    public final void synpred173_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5420:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5420:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5420:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5421:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5421:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5422:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5422:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5423:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=81 && input.LA(1)<=82) ) {
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

        // InternalKactors.g:5430:5: ( '+' | ( ( '-' ) ) )?
        int alt131=3;
        int LA131_0 = input.LA(1);

        if ( (LA131_0==78) ) {
            alt131=1;
        }
        else if ( (LA131_0==79) ) {
            alt131=2;
        }
        switch (alt131) {
            case 1 :
                // InternalKactors.g:5431:6: '+'
                {
                match(input,78,FOLLOW_10); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5433:6: ( ( '-' ) )
                {
                // InternalKactors.g:5433:6: ( ( '-' ) )
                // InternalKactors.g:5434:7: ( '-' )
                {
                // InternalKactors.g:5434:7: ( '-' )
                // InternalKactors.g:5435:8: '-'
                {
                match(input,79,FOLLOW_10); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5439:5: ( ( RULE_INT ) )
        // InternalKactors.g:5440:6: ( RULE_INT )
        {
        // InternalKactors.g:5440:6: ( RULE_INT )
        // InternalKactors.g:5441:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred173_InternalKactors

    // $ANTLR start synpred190_InternalKactors
    public final void synpred190_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:5875:4: (kw= '-' )
        // InternalKactors.g:5875:4: kw= '-'
        {
        kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred190_InternalKactors

    // $ANTLR start synpred191_InternalKactors
    public final void synpred191_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:5882:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:5882:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred191_InternalKactors

    // Delegated rules

    public final boolean synpred25_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
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
    public final boolean synpred128_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred128_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred169_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred169_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred56_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred56_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred23_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred81_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred81_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred57_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred57_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred48_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred48_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred190_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred190_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred155_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred155_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred143_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred143_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred167_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred167_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred62_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred62_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred168_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred168_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred27_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred55_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred55_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred146_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred146_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred49_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred49_InternalKactors_fragment(); // can never throw exception
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
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA33 dfa33 = new DFA33(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA51 dfa51 = new DFA51(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA62 dfa62 = new DFA62(this);
    protected DFA68 dfa68 = new DFA68(this);
    protected DFA74 dfa74 = new DFA74(this);
    static final String dfa_1s = "\14\uffff";
    static final String dfa_2s = "\1\1\13\uffff";
    static final String dfa_3s = "\1\17\13\uffff";
    static final String dfa_4s = "\1\51\13\uffff";
    static final String dfa_5s = "\1\uffff\1\13\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String dfa_6s = "\1\0\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\16\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\1",
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
            return "()* loopback of 316:6: ( ({...}? => ( ({...}? => (otherlv_8= 'import' ( (lv_imports_9_0= rulePathName ) ) (otherlv_10= ',' ( (lv_imports_11_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'worldview' ( (lv_worldview_13_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'observable' ( ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( (lv_observables_16_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'label' ( ( (lv_label_18_1= RULE_LOWERCASE_ID | lv_label_18_2= RULE_ID | lv_label_18_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= 'description' ( (lv_description_20_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_21= 'permissions' ( (lv_permissions_22_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'author' ( (lv_authors_24_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_25= 'version' ( (lv_version_26_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_27= 'created' ( (lv_created_28_0= ruleDate ) ) ( (lv_createcomment_29_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_30= 'modified' ( (lv_modified_31_0= ruleDate ) ) ( (lv_modcomment_32_0= RULE_STRING ) )? ) ) ) ) )*";
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

                        else if ( LA14_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {s = 2;}

                        else if ( LA14_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {s = 3;}

                        else if ( LA14_0 == 33 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {s = 4;}

                        else if ( LA14_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {s = 5;}

                        else if ( LA14_0 == 35 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {s = 6;}

                        else if ( LA14_0 == 36 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {s = 7;}

                        else if ( LA14_0 == 37 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {s = 8;}

                        else if ( LA14_0 == 38 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {s = 9;}

                        else if ( LA14_0 == 39 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {s = 10;}

                        else if ( LA14_0 == 40 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {s = 11;}

                         
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
    static final String dfa_11s = "\1\117\1\0\41\uffff";
    static final String dfa_12s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_14s = {
            "\2\2\1\uffff\7\2\1\uffff\1\2\17\uffff\1\2\11\uffff\2\2\1\1\10\2\1\uffff\6\2\2\uffff\1\2\4\uffff\1\2\13\uffff\2\2",
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
            return "1048:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
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
                        if ( (synpred34_InternalKactors()) ) {s = 34;}

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
    static final String dfa_15s = "\27\uffff";
    static final String dfa_16s = "\1\4\1\uffff\2\0\23\uffff";
    static final String dfa_17s = "\1\117\1\uffff\2\0\23\uffff";
    static final String dfa_18s = "\1\uffff\1\1\2\uffff\1\4\1\5\1\6\1\7\1\10\1\11\13\uffff\1\2\1\3";
    static final String dfa_19s = "\2\uffff\1\0\1\1\23\uffff}>";
    static final String[] dfa_20s = {
            "\1\11\1\3\1\uffff\1\11\1\4\2\11\2\uffff\1\11\35\uffff\1\2\1\uffff\1\1\1\5\1\uffff\1\6\1\7\1\10\2\uffff\2\11\3\uffff\1\11\2\uffff\1\11\4\uffff\1\11\13\uffff\2\11",
            "",
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
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1255:2: ( ( (lv_assignment_0_0= ruleAssignment ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_2 = input.LA(1);

                         
                        int index26_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred40_InternalKactors()) ) {s = 21;}

                        else if ( (synpred41_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index26_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_3 = input.LA(1);

                         
                        int index26_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred41_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index26_3);
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
    static final String[] dfa_21s = {
            "\1\11\1\2\1\uffff\1\11\1\4\2\11\2\uffff\1\11\35\uffff\1\3\1\uffff\1\1\1\5\1\uffff\1\6\1\7\1\10\2\uffff\2\11\3\uffff\1\11\2\uffff\1\11\4\uffff\1\11\13\uffff\2\11",
            "",
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
            ""
    };
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "1457:3: ( ( (lv_assignment_1_0= ruleAssignment ) ) | ( (lv_verb_2_0= ruleMessageCall ) ) | ( (lv_group_3_0= ruleStatementGroup ) ) | ( (lv_text_4_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_5_0= ruleIfStatement ) ) | ( (lv_while_6_0= ruleWhileStatement ) ) | ( (lv_do_7_0= ruleDoStatement ) ) | ( (lv_for_8_0= ruleForStatement ) ) | ( (lv_value_9_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_2 = input.LA(1);

                         
                        int index27_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_3 = input.LA(1);

                         
                        int index27_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_InternalKactors()) ) {s = 21;}

                        else if ( (synpred49_InternalKactors()) ) {s = 22;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_22s = "\21\uffff";
    static final String dfa_23s = "\1\4\2\0\16\uffff";
    static final String dfa_24s = "\1\117\2\0\16\uffff";
    static final String dfa_25s = "\3\uffff\1\2\13\uffff\1\1\1\3";
    static final String dfa_26s = "\1\uffff\1\0\1\1\16\uffff}>";
    static final String[] dfa_27s = {
            "\1\3\1\1\1\uffff\1\3\1\uffff\2\3\2\uffff\1\3\35\uffff\1\2\11\uffff\2\3\3\uffff\1\3\2\uffff\1\3\4\uffff\1\3\13\uffff\2\3",
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
            ""
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = dfa_22;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1847:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_1 = input.LA(1);

                         
                        int index30_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred57_InternalKactors()) ) {s = 15;}

                        else if ( (synpred58_InternalKactors()) ) {s = 3;}

                         
                        input.seek(index30_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_2 = input.LA(1);

                         
                        int index30_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred57_InternalKactors()) ) {s = 15;}

                        else if ( (synpred58_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index30_2);
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
    static final String dfa_28s = "\35\uffff";
    static final String dfa_29s = "\1\4\3\0\2\uffff\5\0\1\uffff\1\0\3\uffff\1\0\14\uffff";
    static final String dfa_30s = "\1\117\3\0\2\uffff\5\0\1\uffff\1\0\3\uffff\1\0\14\uffff";
    static final String dfa_31s = "\4\uffff\1\1\14\uffff\1\3\11\uffff\1\2\1\4";
    static final String dfa_32s = "\1\uffff\1\0\1\1\1\2\2\uffff\1\3\1\4\1\5\1\6\1\7\1\uffff\1\10\3\uffff\1\11\14\uffff}>";
    static final String[] dfa_33s = {
            "\1\6\1\1\1\uffff\1\12\1\21\1\14\1\21\2\4\1\11\35\uffff\1\20\1\uffff\2\21\1\uffff\3\21\1\4\1\uffff\1\2\1\3\3\4\1\21\2\uffff\1\21\4\uffff\1\21\13\uffff\1\7\1\10",
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
            "\1\uffff",
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

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[][] dfa_33 = unpackEncodedStringArray(dfa_33s);

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = dfa_28;
            this.eof = dfa_28;
            this.min = dfa_29;
            this.max = dfa_30;
            this.accept = dfa_31;
            this.special = dfa_32;
            this.transition = dfa_33;
        }
        public String getDescription() {
            return "2134:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA33_1 = input.LA(1);

                         
                        int index33_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA33_2 = input.LA(1);

                         
                        int index33_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA33_3 = input.LA(1);

                         
                        int index33_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA33_6 = input.LA(1);

                         
                        int index33_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA33_7 = input.LA(1);

                         
                        int index33_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA33_8 = input.LA(1);

                         
                        int index33_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA33_9 = input.LA(1);

                         
                        int index33_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA33_10 = input.LA(1);

                         
                        int index33_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA33_12 = input.LA(1);

                         
                        int index33_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_InternalKactors()) ) {s = 4;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_12);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA33_16 = input.LA(1);

                         
                        int index33_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred62_InternalKactors()) ) {s = 27;}

                        else if ( (synpred63_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index33_16);
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
    static final String dfa_34s = "\32\uffff";
    static final String dfa_35s = "\4\uffff\1\5\1\uffff\1\24\6\uffff\2\5\1\22\10\uffff\1\5\1\24";
    static final String dfa_36s = "\1\4\1\uffff\2\15\1\4\1\uffff\1\4\6\uffff\3\4\2\15\1\uffff\1\5\1\uffff\1\0\2\15\2\4";
    static final String dfa_37s = "\1\117\1\uffff\2\15\1\125\1\uffff\1\117\6\uffff\2\122\1\126\2\117\1\uffff\1\5\1\uffff\1\0\2\15\2\117";
    static final String dfa_38s = "\1\uffff\1\1\3\uffff\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\5\uffff\1\12\1\uffff\1\3\5\uffff";
    static final String dfa_39s = "\25\uffff\1\0\4\uffff}>";
    static final String[] dfa_40s = {
            "\1\12\1\6\1\uffff\1\5\1\uffff\1\13\1\1\2\uffff\1\4\35\uffff\1\10\11\uffff\2\5\3\uffff\1\7\2\uffff\1\11\4\uffff\1\14\13\uffff\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\2\5\1\uffff\7\5\1\uffff\1\5\17\uffff\1\5\11\uffff\1\5\1\uffff\11\5\1\uffff\6\5\2\uffff\2\5\2\uffff\2\5\2\uffff\1\22\1\17\7\uffff\2\5\1\16\1\20\1\21\3\5",
            "",
            "\2\24\1\uffff\7\24\1\uffff\1\24\17\uffff\1\24\11\uffff\1\24\1\7\11\24\1\uffff\6\24\2\uffff\2\24\3\uffff\1\24\3\uffff\1\23\7\uffff\2\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\5\1\uffff\7\5\1\uffff\1\5\17\uffff\1\5\11\uffff\1\5\1\uffff\11\5\1\uffff\6\5\2\uffff\2\5\2\uffff\2\5\2\uffff\1\22\1\17\7\uffff\2\5\1\16\1\20\1\21",
            "\2\5\1\uffff\7\5\1\uffff\1\5\17\uffff\1\5\11\uffff\1\5\1\uffff\11\5\1\uffff\6\5\2\uffff\2\5\2\uffff\2\5\2\uffff\1\22\1\17\7\uffff\2\5\1\uffff\1\20\1\21",
            "\2\22\1\uffff\6\22\1\25\1\uffff\2\22\16\uffff\1\22\11\uffff\1\22\1\uffff\11\22\1\uffff\6\22\2\uffff\2\22\3\uffff\1\22\2\uffff\1\22\10\uffff\2\22\6\uffff\1\22",
            "\1\30\100\uffff\1\26\1\27",
            "\1\30\100\uffff\1\26\1\27",
            "",
            "\1\31",
            "",
            "\1\uffff",
            "\1\30",
            "\1\30",
            "\2\5\1\uffff\7\5\1\uffff\1\5\17\uffff\1\5\11\uffff\1\5\1\uffff\11\5\1\uffff\6\5\2\uffff\2\5\2\uffff\2\5\2\uffff\2\22\7\uffff\2\5",
            "\2\24\1\uffff\7\24\1\uffff\1\24\17\uffff\1\24\11\uffff\1\24\1\7\11\24\1\uffff\6\24\2\uffff\2\24\3\uffff\1\24\3\uffff\1\23\7\uffff\2\24"
    };

    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[][] dfa_40 = unpackEncodedStringArray(dfa_40s);

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_34;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "2271:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) | ( (lv_quantity_9_0= ruleQuantity ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_21 = input.LA(1);

                         
                        int index34_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred65_InternalKactors()) ) {s = 5;}

                        else if ( (true) ) {s = 18;}

                         
                        input.seek(index34_21);
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
    static final String dfa_41s = "\22\uffff";
    static final String dfa_42s = "\1\4\1\uffff\2\0\3\uffff\3\0\10\uffff";
    static final String dfa_43s = "\1\117\1\uffff\2\0\3\uffff\3\0\10\uffff";
    static final String dfa_44s = "\1\uffff\1\1\2\uffff\1\3\1\4\1\5\3\uffff\1\6\1\7\1\11\1\12\1\13\1\14\1\2\1\10";
    static final String dfa_45s = "\2\uffff\1\0\1\1\3\uffff\1\2\1\3\1\4\10\uffff}>";
    static final String[] dfa_46s = {
            "\1\6\1\1\1\uffff\1\12\1\uffff\1\14\1\uffff\1\4\1\5\1\11\45\uffff\1\13\1\uffff\1\2\1\3\1\15\1\16\1\17\24\uffff\1\7\1\10",
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
            ""
    };

    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[][] dfa_46 = unpackEncodedStringArray(dfa_46s);

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = dfa_41;
            this.eof = dfa_41;
            this.min = dfa_42;
            this.max = dfa_43;
            this.accept = dfa_44;
            this.special = dfa_45;
            this.transition = dfa_46;
        }
        public String getDescription() {
            return "2486:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | (otherlv_18= 'in' ( (lv_set_19_0= ruleList ) ) otherlv_20= '->' ( (lv_body_21_0= ruleStatementList ) ) ) | ( ( (lv_quantity_22_0= ruleQuantity ) ) otherlv_23= '->' ( (lv_body_24_0= ruleStatementList ) ) ) | ( ( (lv_expr_25_0= RULE_EXPR ) ) otherlv_26= '->' ( (lv_body_27_0= ruleStatementList ) ) ) | ( ( (lv_nodata_28_0= 'unknown' ) ) otherlv_29= '->' ( (lv_body_30_0= ruleStatementList ) ) ) | ( ( (lv_star_31_0= '*' ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_anything_34_0= '#' ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA36_2 = input.LA(1);

                         
                        int index36_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred75_InternalKactors()) ) {s = 16;}

                        else if ( (synpred79_InternalKactors()) ) {s = 10;}

                         
                        input.seek(index36_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA36_3 = input.LA(1);

                         
                        int index36_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred75_InternalKactors()) ) {s = 16;}

                        else if ( (synpred79_InternalKactors()) ) {s = 10;}

                         
                        input.seek(index36_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA36_7 = input.LA(1);

                         
                        int index36_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 10;}

                        else if ( (synpred81_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index36_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA36_8 = input.LA(1);

                         
                        int index36_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 10;}

                        else if ( (synpred81_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index36_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA36_9 = input.LA(1);

                         
                        int index36_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred79_InternalKactors()) ) {s = 10;}

                        else if ( (synpred81_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index36_9);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 36, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_47s = "\6\uffff";
    static final String dfa_48s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_49s = "\1\5\1\4\1\uffff\1\5\1\uffff\1\4";
    static final String dfa_50s = "\1\20\1\117\1\uffff\1\20\1\uffff\1\117";
    static final String dfa_51s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_52s = "\6\uffff}>";
    static final String[] dfa_53s = {
            "\1\1\12\uffff\1\2",
            "\2\2\1\uffff\7\2\1\uffff\1\2\17\uffff\1\2\11\uffff\1\2\1\uffff\11\2\1\uffff\7\2\1\4\2\2\3\uffff\1\2\2\uffff\1\2\1\3\7\uffff\2\2",
            "",
            "\1\5\12\uffff\1\2",
            "",
            "\2\2\1\uffff\7\2\1\uffff\1\2\17\uffff\1\2\11\uffff\1\2\1\uffff\11\2\1\uffff\7\2\1\4\2\2\3\uffff\1\2\2\uffff\1\2\1\3\7\uffff\2\2"
    };

    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final short[] dfa_51 = DFA.unpackEncodedString(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[][] dfa_53 = unpackEncodedStringArray(dfa_53s);

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = dfa_47;
            this.eof = dfa_48;
            this.min = dfa_49;
            this.max = dfa_50;
            this.accept = dfa_51;
            this.special = dfa_52;
            this.transition = dfa_53;
        }
        public String getDescription() {
            return "3132:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_47;
            this.eof = dfa_48;
            this.min = dfa_49;
            this.max = dfa_50;
            this.accept = dfa_51;
            this.special = dfa_52;
            this.transition = dfa_53;
        }
        public String getDescription() {
            return "3161:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_54s = "\26\uffff";
    static final String dfa_55s = "\4\uffff\1\21\7\uffff\1\21\5\uffff\1\21\2\uffff\1\21";
    static final String dfa_56s = "\1\4\1\uffff\2\15\1\52\7\uffff\1\52\3\15\2\uffff\1\52\2\15\1\52";
    static final String dfa_57s = "\1\117\1\uffff\2\15\1\122\7\uffff\1\122\1\15\2\117\2\uffff\1\122\2\15\1\101";
    static final String dfa_58s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\2\1\3\4\uffff";
    static final String dfa_59s = "\26\uffff}>";
    static final String[] dfa_60s = {
            "\1\7\1\10\1\uffff\1\6\5\uffff\1\4\45\uffff\1\5\1\uffff\2\1\1\12\1\13\3\uffff\1\11\14\uffff\5\11\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\21\24\uffff\3\20\4\uffff\1\15\11\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21\24\uffff\3\20\4\uffff\1\15\12\uffff\1\16\1\17",
            "\1\22",
            "\1\25\100\uffff\1\23\1\24",
            "\1\25\100\uffff\1\23\1\24",
            "",
            "",
            "\1\21\24\uffff\3\20\17\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\21\24\uffff\3\20"
    };

    static final short[] dfa_54 = DFA.unpackEncodedString(dfa_54s);
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final char[] dfa_57 = DFA.unpackEncodedStringToUnsignedChars(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[] dfa_59 = DFA.unpackEncodedString(dfa_59s);
    static final short[][] dfa_60 = unpackEncodedStringArray(dfa_60s);

    class DFA51 extends DFA {

        public DFA51(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 51;
            this.eot = dfa_54;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_59;
            this.transition = dfa_60;
        }
        public String getDescription() {
            return "3462:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_61s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_62s = "\1\4\1\uffff\1\16\1\uffff\1\4\1\16";
    static final String dfa_63s = "\1\117\1\uffff\1\104\1\uffff\1\117\1\104";
    static final String dfa_64s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_65s = {
            "\1\3\1\1\1\uffff\1\2\1\uffff\1\3\3\uffff\1\3\45\uffff\1\3\1\uffff\5\3\2\uffff\1\3\14\uffff\7\3",
            "",
            "\1\1\20\uffff\1\3\43\uffff\1\3\1\4",
            "",
            "\1\3\1\1\1\uffff\1\5\1\uffff\1\3\3\uffff\1\3\45\uffff\1\3\1\uffff\5\3\2\uffff\1\3\14\uffff\7\3",
            "\1\1\20\uffff\1\3\43\uffff\1\3\1\4"
    };
    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final char[] dfa_63 = DFA.unpackEncodedStringToUnsignedChars(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[][] dfa_65 = unpackEncodedStringArray(dfa_65s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_47;
            this.eof = dfa_61;
            this.min = dfa_62;
            this.max = dfa_63;
            this.accept = dfa_64;
            this.special = dfa_52;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "3837:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_66s = "\4\uffff\1\22\10\uffff\2\22\1\23\6\uffff\1\22\2\uffff\1\22";
    static final String dfa_67s = "\1\4\1\uffff\2\15\1\37\10\uffff\2\37\1\5\2\15\4\uffff\1\37\2\15\1\37";
    static final String dfa_68s = "\1\117\1\uffff\2\15\1\125\10\uffff\2\122\1\126\2\117\4\uffff\1\122\2\15\1\106";
    static final String dfa_69s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\5\uffff\1\2\1\10\1\11\1\6\4\uffff";
    static final String dfa_70s = "\32\uffff}>";
    static final String[] dfa_71s = {
            "\1\6\2\uffff\1\5\1\uffff\1\11\3\uffff\1\4\45\uffff\1\10\1\uffff\2\1\1\12\1\13\1\14\2\uffff\1\7\14\uffff\5\7\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\22\37\uffff\3\25\1\uffff\2\22\1\23\1\17\10\uffff\1\24\1\16\1\20\1\21\3\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\37\uffff\3\25\1\uffff\2\22\1\23\1\17\11\uffff\1\16\1\20\1\21",
            "\1\22\37\uffff\3\25\1\uffff\2\22\1\23\1\17\12\uffff\1\20\1\21",
            "\1\23\5\uffff\1\23\1\uffff\1\26\2\uffff\1\23\16\uffff\1\23\13\uffff\1\23\14\uffff\1\23\12\uffff\3\23\20\uffff\1\23",
            "\1\31\100\uffff\1\27\1\30",
            "\1\31\100\uffff\1\27\1\30",
            "",
            "",
            "",
            "",
            "\1\22\37\uffff\3\25\1\uffff\2\22\2\23\12\uffff\1\20\1\21",
            "\1\31",
            "\1\31",
            "\1\22\37\uffff\3\25\1\uffff\2\22\2\23"
    };
    static final short[] dfa_66 = DFA.unpackEncodedString(dfa_66s);
    static final char[] dfa_67 = DFA.unpackEncodedStringToUnsignedChars(dfa_67s);
    static final char[] dfa_68 = DFA.unpackEncodedStringToUnsignedChars(dfa_68s);
    static final short[] dfa_69 = DFA.unpackEncodedString(dfa_69s);
    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[][] dfa_71 = unpackEncodedStringArray(dfa_71s);

    class DFA62 extends DFA {

        public DFA62(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 62;
            this.eot = dfa_34;
            this.eof = dfa_66;
            this.min = dfa_67;
            this.max = dfa_68;
            this.accept = dfa_69;
            this.special = dfa_70;
            this.transition = dfa_71;
        }
        public String getDescription() {
            return "4082:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_72s = "\3\uffff\1\16\2\uffff\2\16\7\uffff\1\16\2\uffff\2\16\2\uffff";
    static final String dfa_73s = "\1\7\2\15\1\4\2\uffff\2\4\3\15\2\uffff\1\15\1\uffff\1\4\2\15\2\4\1\15\1\0";
    static final String dfa_74s = "\1\117\2\15\1\125\2\uffff\2\122\1\15\2\117\2\uffff\1\15\1\uffff\1\122\2\15\1\117\1\122\1\15\1\0";
    static final String dfa_75s = "\4\uffff\1\3\1\5\5\uffff\1\2\1\4\1\uffff\1\1\7\uffff";
    static final String dfa_76s = "\25\uffff\1\0}>";
    static final String[] dfa_77s = {
            "\1\4\5\uffff\1\3\47\uffff\2\5\27\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\2\16\1\uffff\7\16\1\uffff\1\16\17\uffff\1\16\11\uffff\1\16\1\uffff\20\16\2\uffff\2\16\2\uffff\1\13\1\16\3\uffff\1\10\7\uffff\1\16\1\15\1\7\1\11\1\12\3\14",
            "",
            "",
            "\2\16\1\uffff\7\16\1\uffff\1\16\17\uffff\1\16\11\uffff\1\16\1\uffff\20\16\2\uffff\2\16\2\uffff\1\13\1\16\3\uffff\1\10\7\uffff\2\16\1\7\1\11\1\12",
            "\2\16\1\uffff\7\16\1\uffff\1\16\17\uffff\1\16\11\uffff\1\16\1\uffff\20\16\2\uffff\2\16\2\uffff\1\13\1\16\3\uffff\1\10\7\uffff\2\16\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\100\uffff\1\20\1\21",
            "\1\22\100\uffff\1\20\1\21",
            "",
            "",
            "\1\23",
            "",
            "\2\16\1\uffff\7\16\1\uffff\1\16\17\uffff\1\16\11\uffff\1\16\1\uffff\20\16\2\uffff\2\16\2\uffff\1\13\1\16\13\uffff\2\16\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\2\16\1\uffff\7\16\1\uffff\1\16\17\uffff\1\16\11\uffff\1\16\1\uffff\20\16\2\uffff\2\16\2\uffff\1\13\1\16\13\uffff\2\16",
            "\2\16\1\uffff\7\16\1\uffff\1\16\17\uffff\1\16\11\uffff\1\16\1\uffff\20\16\2\uffff\1\16\3\uffff\2\16\2\uffff\2\16\7\uffff\1\16\1\24\3\16",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_72 = DFA.unpackEncodedString(dfa_72s);
    static final char[] dfa_73 = DFA.unpackEncodedStringToUnsignedChars(dfa_73s);
    static final char[] dfa_74 = DFA.unpackEncodedStringToUnsignedChars(dfa_74s);
    static final short[] dfa_75 = DFA.unpackEncodedString(dfa_75s);
    static final short[] dfa_76 = DFA.unpackEncodedString(dfa_76s);
    static final short[][] dfa_77 = unpackEncodedStringArray(dfa_77s);

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = dfa_54;
            this.eof = dfa_72;
            this.min = dfa_73;
            this.max = dfa_74;
            this.accept = dfa_75;
            this.special = dfa_76;
            this.transition = dfa_77;
        }
        public String getDescription() {
            return "4621:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA68_21 = input.LA(1);

                         
                        int index68_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred143_InternalKactors()) ) {s = 14;}

                        else if ( (synpred146_InternalKactors()) ) {s = 12;}

                         
                        input.seek(index68_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 68, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_78s = "\50\uffff";
    static final String dfa_79s = "\1\5\47\uffff";
    static final String dfa_80s = "\1\4\2\0\1\uffff\1\0\43\uffff";
    static final String dfa_81s = "\1\126\2\0\1\uffff\1\0\43\uffff";
    static final String dfa_82s = "\3\uffff\1\1\1\uffff\1\2\42\uffff";
    static final String dfa_83s = "\1\uffff\1\0\1\1\1\uffff\1\2\43\uffff}>";
    static final String[] dfa_84s = {
            "\1\5\1\2\1\uffff\4\5\1\1\2\5\1\uffff\1\5\1\3\16\uffff\1\5\11\uffff\1\5\1\uffff\1\4\17\5\2\uffff\2\5\3\uffff\4\5\10\uffff\2\5\6\uffff\1\5",
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
            ""
    };

    static final short[] dfa_78 = DFA.unpackEncodedString(dfa_78s);
    static final short[] dfa_79 = DFA.unpackEncodedString(dfa_79s);
    static final char[] dfa_80 = DFA.unpackEncodedStringToUnsignedChars(dfa_80s);
    static final char[] dfa_81 = DFA.unpackEncodedStringToUnsignedChars(dfa_81s);
    static final short[] dfa_82 = DFA.unpackEncodedString(dfa_82s);
    static final short[] dfa_83 = DFA.unpackEncodedString(dfa_83s);
    static final short[][] dfa_84 = unpackEncodedStringArray(dfa_84s);

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = dfa_78;
            this.eof = dfa_79;
            this.min = dfa_80;
            this.max = dfa_81;
            this.accept = dfa_82;
            this.special = dfa_83;
            this.transition = dfa_84;
        }
        public String getDescription() {
            return "5026:3: ( (lv_root_1_0= ruleUnitElement ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA74_1 = input.LA(1);

                         
                        int index74_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index74_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA74_2 = input.LA(1);

                         
                        int index74_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index74_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA74_4 = input.LA(1);

                         
                        int index74_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred155_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index74_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 74, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000020000008002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x000001FF40000002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000001FFC0000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000080000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x000001FF40000082L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000020000008000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000C0000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x24676800000027B0L,0x000000000000C004L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000100000000020L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000100080000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x24676800000027B2L,0x000000000000C004L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x24677800000027B0L,0x000000000000C004L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x27EF680000003FB0L,0x000000000000C004L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x27EF780000003FB0L,0x000000000000C004L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000012020L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0200040000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000010020L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x51E80000000020B0L,0x000000000000FE00L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x4000000080000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x11E80000000020B0L,0x000000000000FE00L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000002000L,0x000000000000C000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x37EF6800000027B0L,0x000000000000FE0CL});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x37EF6800000027B0L,0x000000000000FE04L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0100080000010820L,0x0000000000400020L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0100180000010820L,0x0000000000400020L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0100000000000002L,0x0000000000400020L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000080000010820L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000000070040L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000060040L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000060000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000388000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000010022L,0x0000000000008040L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000010022L,0x0000000000008000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000082L});

}
